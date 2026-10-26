package com.miller.tv.launcher.discovery

import android.view.KeyEvent
import com.miller.tv.launcher.adapter.DrawerAppItemUiState
import com.miller.tv.launcher.adapter.HomeFocusController
import com.miller.tv.launcher.adapter.HomeFocusViewBinder
import com.miller.tv.launcher.adapter.HomeLauncherViewTarget
import com.miller.tv.launcher.focus.DesignatedService
import com.miller.tv.launcher.focus.FocusNode
import com.miller.tv.launcher.focus.HomeFocusStateMachine
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class AppDiscoveryIntegrationTest {

    private class FakeTvAppScanner(
        var snapshotResult: Result<AppSnapshot> = Result.success(AppSnapshot(1L, emptyList(), emptyList()))
    ) : TvAppScanner {
        var scanCallCount = 0

        override fun scanTvApps(snapshotId: Long): Result<AppSnapshot> {
            scanCallCount++
            return snapshotResult.map { it.copy(snapshotId = snapshotId) }
        }
    }

    private class FakeAppLauncher : AppLauncher {
        var lastLaunchedComponent: String? = null
        var launchCount = 0
        var shouldFail = false

        override fun launchApp(componentKey: String): Result<Unit> {
            launchCount++
            lastLaunchedComponent = componentKey
            return if (shouldFail) {
                Result.failure(SecurityException("Permission denied for component $componentKey"))
            } else {
                Result.success(Unit)
            }
        }
    }

    private class TestIntegrationTarget : HomeLauncherViewTarget {
        override var isRefreshSelected: Boolean = false
        override var refreshScale: Float = 1.0f

        override var isSettingsSelected: Boolean = false
        override var settingsScale: Float = 1.0f

        override var isYoutubeVisible: Boolean = false
        override var isYoutubeSelected: Boolean = false
        override var youtubeScale: Float = 1.0f

        override var isNetflixVisible: Boolean = false
        override var isNetflixSelected: Boolean = false
        override var netflixScale: Float = 1.0f

        override var isDisneyPlusVisible: Boolean = false
        override var isDisneyPlusSelected: Boolean = false
        override var disneyPlusScale: Float = 1.0f

        override var isMoreAppsVisible: Boolean = true
        override var isMoreAppsSelected: Boolean = false
        override var moreAppsScale: Float = 1.0f

        override var isDrawerVisible: Boolean = false
        override var isDrawerCloseSelected: Boolean = false
        override var drawerCloseScale: Float = 1.0f

        var drawerAppItems: List<DrawerAppItemUiState> = emptyList()

        override fun bindDrawerApps(apps: List<DrawerAppItemUiState>) {
            this.drawerAppItems = apps
        }
    }

    // 1. App 掃描成功與空結果 (H 為空時，僅 MoreApps 可見)
    @Test
    fun testEmptyScanResults() {
        val scanner = FakeTvAppScanner(Result.success(AppSnapshot(1L, emptyList(), emptyList())))
        val launcher = FakeAppLauncher()
        val target = TestIntegrationTarget()
        val stateMachine = HomeFocusStateMachine()
        val binder = HomeFocusViewBinder(target)
        val coordinator = AppDiscoveryCoordinator(scanner, launcher, stateMachine, binder)

        coordinator.refresh()

        assertFalse(target.isYoutubeVisible)
        assertFalse(target.isNetflixVisible)
        assertFalse(target.isDisneyPlusVisible)
        assertTrue(target.isMoreAppsVisible)
        // 初始首次焦點：因 H 為空，聚焦在 MoreApps
        assertEquals(FocusNode.Content.MoreApps, stateMachine.currentFocus)
        assertTrue(target.isMoreAppsSelected)
    }

    // 2. 指定服務固定順序 (YouTube -> Netflix -> Disney+)
    // 3. 不以名稱誤判指定服務 (只有經 mapping 的服務進入 H，其餘在 O)
    @Test
    fun testDesignatedServiceOrderAndNonMatchingApps() {
        val hApps = listOf(
            DiscoveredTvApp("com.google.android.youtube.tv/Entry", "YouTube TV", "com.google.android.youtube.tv", DesignatedService.YOUTUBE),
            DiscoveredTvApp("com.netflix.ninja/Entry", "Netflix", "com.netflix.ninja", DesignatedService.NETFLIX),
            DiscoveredTvApp("com.disney.disneyplus/Entry", "Disney+", "com.disney.disneyplus", DesignatedService.DISNEY_PLUS)
        )
        val oApps = listOf(
            DiscoveredTvApp("com.spotify.tv.android/Entry", "Spotify", "com.spotify.tv.android", null)
        )
        val scanner = FakeTvAppScanner(Result.success(AppSnapshot(1L, hApps, oApps)))
        val target = TestIntegrationTarget()
        val stateMachine = HomeFocusStateMachine()
        val binder = HomeFocusViewBinder(target)
        val coordinator = AppDiscoveryCoordinator(scanner, FakeAppLauncher(), stateMachine, binder)

        coordinator.refresh()

        assertTrue(target.isYoutubeVisible)
        assertTrue(target.isNetflixVisible)
        assertTrue(target.isDisneyPlusVisible)
        assertTrue(target.isMoreAppsVisible)
        assertEquals(1, target.drawerAppItems.size)
        assertEquals("com.spotify.tv.android/Entry", target.drawerAppItems[0].componentKey)
    }

    // 4. H/O 原子快照更新與快照 ID 遞增
    @Test
    fun testAtomicSnapshotSubmission() {
        val hApps = listOf(DiscoveredTvApp("com.netflix.ninja/Entry", "Netflix", "com.netflix.ninja", DesignatedService.NETFLIX))
        val oApps = listOf(DiscoveredTvApp("com.plexapp.android/Entry", "Plex", "com.plexapp.android", null))
        val scanner = FakeTvAppScanner(Result.success(AppSnapshot(10L, hApps, oApps)))

        val stateMachine = HomeFocusStateMachine()
        val target = TestIntegrationTarget()
        val binder = HomeFocusViewBinder(target)
        val coordinator = AppDiscoveryCoordinator(scanner, FakeAppLauncher(), stateMachine, binder)

        coordinator.refresh()

        assertEquals(1, stateMachine.hApps.size)
        assertEquals(DesignatedService.NETFLIX, stateMachine.hApps[0].service)
        assertEquals(1, stateMachine.drawerApps.size)
        assertEquals("com.plexapp.android/Entry", stateMachine.drawerApps[0].componentKey)
    }

    // 5. 舊快照不得覆蓋新快照 (Out-of-order 響應保護)
    @Test
    fun testOutOfOrderSnapshotsIgnored() {
        val scanner = FakeTvAppScanner()
        val stateMachine = HomeFocusStateMachine()
        val target = TestIntegrationTarget()
        val binder = HomeFocusViewBinder(target)
        val coordinator = AppDiscoveryCoordinator(scanner, FakeAppLauncher(), stateMachine, binder)

        // 提交 ID 5
        val snapshot5 = AppSnapshot(
            snapshotId = 5L,
            hApps = listOf(DiscoveredTvApp("com.netflix.ninja/Entry", "Netflix", "com.netflix.ninja", DesignatedService.NETFLIX)),
            oApps = emptyList()
        )
        coordinator.onSnapshotArrived(snapshot5)
        assertEquals(1, stateMachine.hApps.size)

        // 晚到的 ID 3 (過期)
        val snapshot3 = AppSnapshot(
            snapshotId = 3L,
            hApps = emptyList(),
            oApps = emptyList()
        )
        val accepted = coordinator.onSnapshotArrived(snapshot3)
        assertFalse("Stale snapshot must be rejected", accepted)
        assertEquals("H apps must not be overwritten by stale snapshot", 1, stateMachine.hApps.size)
    }

    // 6. 刷新失敗時安全降級 (不抹煞已有的快照與焦點)
    @Test
    fun testRefreshFailureDegradation() {
        val hApps = listOf(DiscoveredTvApp("com.netflix.ninja/Entry", "Netflix", "com.netflix.ninja", DesignatedService.NETFLIX))
        val scanner = FakeTvAppScanner(Result.success(AppSnapshot(1L, hApps, emptyList())))

        val stateMachine = HomeFocusStateMachine()
        val target = TestIntegrationTarget()
        val binder = HomeFocusViewBinder(target)
        val coordinator = AppDiscoveryCoordinator(scanner, FakeAppLauncher(), stateMachine, binder)

        coordinator.refresh()
        assertEquals(1, stateMachine.hApps.size)

        // 模擬刷新失敗
        scanner.snapshotResult = Result.failure(RuntimeException("Query failed"))
        val refreshSuccess = coordinator.refresh()
        assertFalse("Refresh should return false on error", refreshSuccess)
        // 舊快照與焦點被保留
        assertEquals("Existing snapshot data must be retained on scan failure", 1, stateMachine.hApps.size)
    }

    // 7. 使用者焦點記憶 (載入快照時不搶走使用者正在操作的焦點)
    @Test
    fun testUserFocusNotStolenOnSnapshotLoad() {
        val stateMachine = HomeFocusStateMachine()
        val target = TestIntegrationTarget()
        val binder = HomeFocusViewBinder(target)
        val controller = HomeFocusController(stateMachine, binder)
        val scanner = FakeTvAppScanner()
        val coordinator = AppDiscoveryCoordinator(scanner, FakeAppLauncher(), stateMachine, binder)

        // 使用者主動將焦點移至 Settings
        controller.dispatchKey(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_RIGHT, 0)
        assertEquals(FocusNode.Tool.Settings, stateMachine.currentFocus)

        // 非同步快照抵達
        val snapshot = AppSnapshot(
            snapshotId = 2L,
            hApps = listOf(DiscoveredTvApp("com.netflix.ninja/Entry", "Netflix", "com.netflix.ninja", DesignatedService.NETFLIX)),
            oApps = emptyList()
        )
        coordinator.onSnapshotArrived(snapshot)

        // 焦點依然維持在 Settings
        assertEquals(FocusNode.Tool.Settings, stateMachine.currentFocus)
        assertTrue(target.isSettingsSelected)
    }

    // 8. O 消失後焦點自動修復 (越尾取尾)
    @Test
    fun testDrawerAppDisappearFocusRepair() {
        val stateMachine = HomeFocusStateMachine()
        val target = TestIntegrationTarget()
        val binder = HomeFocusViewBinder(target)
        val controller = HomeFocusController(stateMachine, binder)
        val scanner = FakeTvAppScanner()
        val coordinator = AppDiscoveryCoordinator(scanner, FakeAppLauncher(), stateMachine, binder)

        // 初始包含 2 個 O App
        val o1 = DiscoveredTvApp("com.plexapp.android/Entry", "Plex", "com.plexapp.android", null)
        val o2 = DiscoveredTvApp("org.xbmc.kodi/Entry", "Kodi", "org.xbmc.kodi", null)
        coordinator.onSnapshotArrived(AppSnapshot(1L, emptyList(), listOf(o1, o2)))

        // 開啟 Drawer，移到第 2 個 App (Kodi)
        controller.dispatchKey(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_DOWN, 0) // 到 MoreApps
        controller.dispatchKey(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_CENTER, 0) // 開 Drawer, 到 Plex
        controller.dispatchKey(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_DOWN, 0) // 到 Kodi
        assertEquals(FocusNode.Drawer.App(o2.componentKey), stateMachine.currentFocus)

        // 新快照抵達：Kodi 消失，只剩 Plex
        coordinator.onSnapshotArrived(AppSnapshot(2L, emptyList(), listOf(o1)))

        // 依據修復規則：越尾取尾，焦點修復至 Plex
        assertEquals(FocusNode.Drawer.App(o1.componentKey), stateMachine.currentFocus)
    }

    // 9. 外部 App 啟動成功 (從 H 卡片及 Drawer O 清單啟動)
    @Test
    fun testAppLaunchSuccess() {
        val launcher = FakeAppLauncher()
        val stateMachine = HomeFocusStateMachine()
        val target = TestIntegrationTarget()
        val binder = HomeFocusViewBinder(target)
        val scanner = FakeTvAppScanner()
        val coordinator = AppDiscoveryCoordinator(scanner, launcher, stateMachine, binder)

        val hApp = DiscoveredTvApp("com.netflix.ninja/Entry", "Netflix", "com.netflix.ninja", DesignatedService.NETFLIX)
        coordinator.onSnapshotArrived(AppSnapshot(1L, listOf(hApp), emptyList()))

        val controller = HomeFocusController(
            stateMachine = stateMachine,
            viewBinder = binder,
            onSelectAction = { node -> coordinator.handleSelect(node) }
        )

        // 移至 Netflix 卡片並按 OK
        assertEquals(FocusNode.Content.App(DesignatedService.NETFLIX, hApp.componentKey), stateMachine.currentFocus)
        val okEvent = controller.dispatchKey(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_CENTER, 0)
        assertTrue(okEvent)
        assertEquals("com.netflix.ninja/Entry", launcher.lastLaunchedComponent)
        assertEquals(1, launcher.launchCount)
    }

    // 10. 外部 App 啟動失敗安全處理 (拋出 Exception 時不崩潰)
    @Test
    fun testAppLaunchFailureGraceful() {
        val launcher = FakeAppLauncher().apply { shouldFail = true }
        val stateMachine = HomeFocusStateMachine()
        val target = TestIntegrationTarget()
        val binder = HomeFocusViewBinder(target)
        val scanner = FakeTvAppScanner()
        val coordinator = AppDiscoveryCoordinator(scanner, launcher, stateMachine, binder)

        val hApp = DiscoveredTvApp("com.netflix.ninja/Entry", "Netflix", "com.netflix.ninja", DesignatedService.NETFLIX)
        coordinator.onSnapshotArrived(AppSnapshot(1L, listOf(hApp), emptyList()))

        // 啟動失敗
        val result = coordinator.launchApp(hApp.componentKey)
        assertFalse(result.isSuccess)
        assertTrue(result.exceptionOrNull() is SecurityException)
    }

    // 11. Activity 生命週期安全性 (Activity 銷毀後拒絕更新)
    @Test
    fun testLifecycleSafetyOnDestroy() {
        val scanner = FakeTvAppScanner()
        val stateMachine = HomeFocusStateMachine()
        val target = TestIntegrationTarget()
        val binder = HomeFocusViewBinder(target)
        val coordinator = AppDiscoveryCoordinator(scanner, FakeAppLauncher(), stateMachine, binder)

        coordinator.destroy()

        val snapshot = AppSnapshot(
            snapshotId = 1L,
            hApps = listOf(DiscoveredTvApp("com.netflix.ninja/Entry", "Netflix", "com.netflix.ninja", DesignatedService.NETFLIX)),
            oApps = emptyList()
        )
        val accepted = coordinator.onSnapshotArrived(snapshot)
        assertFalse("Destroyed coordinator must reject snapshot updates", accepted)
    }

    // 12. 預設 TvAppCategorizer 必須包含官方映射，真實 YouTube Component 進入 H 且自 O 排除
    @Test
    fun testDefaultCategorizerRecognizesOfficialYouTubeIntoH() {
        val defaultCategorizer = TvAppCategorizer()
        val ytActivity = DiscoveredActivity(
            packageName = "com.google.android.youtube.tv",
            activityName = "com.google.android.apps.youtube.tv.activity.ShellActivity",
            rawLabel = "YouTube"
        )
        val otherActivity = DiscoveredActivity(
            packageName = "com.android.vending",
            activityName = "com.google.android.finsky.tvmainactivity.TvMainActivity",
            rawLabel = "Google Play 商店"
        )

        val snapshot = defaultCategorizer.categorize(listOf(ytActivity, otherActivity))

        assertEquals("YouTube must be in H", 1, snapshot.hApps.size)
        assertEquals(DesignatedService.YOUTUBE, snapshot.hApps[0].service)
        assertEquals("YouTube", snapshot.hApps[0].displayName)

        assertEquals("O must only contain non-designated apps", 1, snapshot.oApps.size)
        assertEquals("com.android.vending/com.google.android.finsky.tvmainactivity.TvMainActivity", snapshot.oApps[0].componentKey)
        assertFalse("YouTube must be excluded from O", snapshot.oApps.any { it.packageName == "com.google.android.youtube.tv" })
    }

    // 13. Display Name 偽裝不會造成錯誤分類 (不以名稱判定服務)
    @Test
    fun testDisplayNameSpoofingRejectedFromH() {
        val defaultCategorizer = TvAppCategorizer()
        val fakeYt = DiscoveredActivity(
            packageName = "com.malicious.fake",
            activityName = "FakeActivity",
            rawLabel = "YouTube" // 顯示名稱假冒 YouTube
        )

        val snapshot = defaultCategorizer.categorize(listOf(fakeYt))

        assertTrue("Fake YouTube must not be placed in H", snapshot.hApps.isEmpty())
        assertEquals("Fake app must be in O", 1, snapshot.oApps.size)
    }

    // 14. App Label 正確傳遞至 UI (Drawer 項目顯示真實 Label 而非 Component Key)
    @Test
    fun testAppLabelPropagatesToDrawerUi() {
        val oApp = DiscoveredTvApp(
            componentKey = "com.google.android.play.games/com.google.android.apps.play.games.app.atv.features.home.HomeActivity",
            displayName = "Play 遊戲",
            packageName = "com.google.android.play.games",
            service = null
        )
        val snapshot = AppSnapshot(1L, emptyList(), listOf(oApp))
        val scanner = FakeTvAppScanner(Result.success(snapshot))
        val target = TestIntegrationTarget()
        val stateMachine = HomeFocusStateMachine()
        val binder = HomeFocusViewBinder(target)
        val coordinator = AppDiscoveryCoordinator(scanner, FakeAppLauncher(), stateMachine, binder)

        coordinator.refresh()

        assertEquals(1, target.drawerAppItems.size)
        assertEquals("Play 遊戲", target.drawerAppItems[0].displayName)
    }

    // 15. 預設 mappings 支援官方 Netflix 與 Disney+ Leanback 入口
    @Test
    fun testDefaultCategorizerRecognizesOfficialNetflixAndDisneyPlus() {
        val defaultCategorizer = TvAppCategorizer()
        val nfActivity = DiscoveredActivity(
            packageName = "com.netflix.ninja",
            activityName = "com.netflix.ninja.MainActivity",
            rawLabel = "Netflix"
        )
        val dpActivity = DiscoveredActivity(
            packageName = "com.disney.disneyplus",
            activityName = "com.bamtechmedia.dominguez.main.MainActivity",
            rawLabel = "Disney+"
        )

        val snapshot = defaultCategorizer.categorize(listOf(dpActivity, nfActivity))

        assertEquals("Both apps should be in H", 2, snapshot.hApps.size)
        // Fixed order: Netflix before Disney+
        assertEquals(DesignatedService.NETFLIX, snapshot.hApps[0].service)
        assertEquals(DesignatedService.DISNEY_PLUS, snapshot.hApps[1].service)
        assertTrue("O must be empty", snapshot.oApps.isEmpty())
    }
}
