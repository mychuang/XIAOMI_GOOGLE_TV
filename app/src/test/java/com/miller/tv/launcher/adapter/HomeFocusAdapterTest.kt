package com.miller.tv.launcher.adapter

import android.view.KeyEvent
import com.miller.tv.launcher.focus.DesignatedService
import com.miller.tv.launcher.focus.DpadDirection
import com.miller.tv.launcher.focus.FocusNode
import com.miller.tv.launcher.focus.HomeFocusStateMachine
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class HomeFocusAdapterTest {

    private class TestHomeLauncherViewTarget : HomeLauncherViewTarget {
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
    }

    // 1. KeyEvent 到 Domain 事件映射
    @Test
    fun testKeyEventTranslationToDomainDpad() {
        val translator = HomeKeyEventTranslator()

        assertEquals(
            HomeKeyAction.Dpad(DpadDirection.UP),
            translator.translate(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_UP, 0)
        )
        assertEquals(
            HomeKeyAction.Dpad(DpadDirection.DOWN),
            translator.translate(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_DOWN, 0)
        )
        assertEquals(
            HomeKeyAction.Dpad(DpadDirection.LEFT),
            translator.translate(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_LEFT, 0)
        )
        assertEquals(
            HomeKeyAction.Dpad(DpadDirection.RIGHT),
            translator.translate(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_RIGHT, 0)
        )
        assertEquals(
            HomeKeyAction.Select,
            translator.translate(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_CENTER, 0)
        )
        assertEquals(
            HomeKeyAction.Select,
            translator.translate(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER, 0)
        )
        assertEquals(
            HomeKeyAction.Back,
            translator.translate(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK, 0)
        )
        assertEquals(
            HomeKeyAction.Unhandled,
            translator.translate(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_VOLUME_UP, 0)
        )
    }

    // 2. 非法或非目標 KeyEvent 的消耗策略 (ACTION_UP 不觸發, repeat 抑制)
    @Test
    fun testActionUpAndRepeatSuppression() {
        val translator = HomeKeyEventTranslator()

        // ACTION_UP 不消耗、不觸發
        assertEquals(
            HomeKeyAction.Unhandled,
            translator.translate(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_DPAD_UP, 0)
        )
        assertEquals(
            HomeKeyAction.Unhandled,
            translator.translate(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_DPAD_CENTER, 0)
        )

        // Select 與 Back 抑制長按 repeat
        assertEquals(
            HomeKeyAction.Unhandled,
            translator.translate(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_CENTER, 1)
        )
        assertEquals(
            HomeKeyAction.Unhandled,
            translator.translate(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER, 2)
        )
        assertEquals(
            HomeKeyAction.Unhandled,
            translator.translate(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK, 1)
        )

        // 方向鍵允許 repeat 以流暢連續移動
        assertEquals(
            HomeKeyAction.Dpad(DpadDirection.DOWN),
            translator.translate(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_DOWN, 1)
        )
    }

    // 3. D-pad 邊界行為 (邊界停駐時，事件依然被消耗，阻擋原生 FocusFinder)
    @Test
    fun testDpadBoundaryConsumed() {
        val stateMachine = HomeFocusStateMachine()
        val target = TestHomeLauncherViewTarget()
        val binder = HomeFocusViewBinder(target)
        val controller = HomeFocusController(stateMachine, binder)

        // 初始在 Refresh (工具列最左端與最頂端)
        assertEquals(FocusNode.Tool.Refresh, stateMachine.currentFocus)

        // UP 碰壁
        val consumedUp = controller.dispatchKey(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_UP, 0)
        assertTrue("Top boundary hit must still be consumed", consumedUp)
        assertEquals(FocusNode.Tool.Refresh, stateMachine.currentFocus)
        assertTrue(target.isRefreshSelected)

        // LEFT 碰壁
        val consumedLeft = controller.dispatchKey(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_LEFT, 0)
        assertTrue("Left boundary hit must still be consumed", consumedLeft)
        assertEquals(FocusNode.Tool.Refresh, stateMachine.currentFocus)
        assertTrue(target.isRefreshSelected)
    }

    // 4. OK 單次觸發
    @Test
    fun testOkSingleTrigger() {
        val stateMachine = HomeFocusStateMachine()
        var okTriggerCount = 0
        val controller = HomeFocusController(
            stateMachine = stateMachine,
            onSelectAction = { okTriggerCount++ }
        )

        // 首次按下 OK (repeatCount = 0)
        val okDown = controller.dispatchKey(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_CENTER, 0)
        assertTrue(okDown)
        assertEquals(1, okTriggerCount)

        // 長按 repeat (repeatCount = 1) -> 抑制，不重複觸發
        val okRepeat = controller.dispatchKey(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_CENTER, 1)
        assertFalse(okRepeat)
        assertEquals(1, okTriggerCount)
    }

    // 5. Back Result 消耗與退出行為
    @Test
    fun testBackNavigationBehavior() {
        val stateMachine = HomeFocusStateMachine()
        var exitLauncherCalled = false
        val target = TestHomeLauncherViewTarget()
        val binder = HomeFocusViewBinder(target)
        val controller = HomeFocusController(
            stateMachine = stateMachine,
            viewBinder = binder,
            onExitLauncher = { exitLauncherCalled = true }
        )

        // 首頁時 Back -> ExitLauncher，不消耗以交由系統退出
        val consumedExit = controller.dispatchKey(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK, 0)
        assertFalse(consumedExit)
        assertTrue("Exit launcher callback should be invoked", exitLauncherCalled)

        // 開啟 Drawer
        stateMachine.handleDpad(DpadDirection.DOWN) // 到 MoreApps
        stateMachine.openDrawer()
        assertTrue(stateMachine.isDrawerOpen)
        binder.syncState(stateMachine.state)
        assertTrue(target.isDrawerVisible)

        // 在 Drawer 內按 Back -> 關閉 Drawer 並消耗事件，不退出 Launcher
        exitLauncherCalled = false
        val consumedClose = controller.dispatchKey(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK, 0)
        assertTrue("Back when drawer is open must be consumed", consumedClose)
        assertFalse(stateMachine.isDrawerOpen)
        assertFalse(target.isDrawerVisible)
        assertFalse("Exit launcher should not be invoked when closing drawer", exitLauncherCalled)
        assertEquals(FocusNode.Content.MoreApps, stateMachine.currentFocus)
        assertTrue(target.isMoreAppsSelected)
    }

    // 6. H=0, 1, 2, 3 的動態卡片數量
    // 7. 固定指定服務順序與 More Apps 永遠存在
    @Test
    fun testHomeAppRowDynamicCards() {
        val target = TestHomeLauncherViewTarget()
        val binder = HomeFocusViewBinder(target)

        // Case H=0 (只有 MoreApps)
        binder.bindHomeApps(emptyList())
        assertFalse(target.isYoutubeVisible)
        assertFalse(target.isNetflixVisible)
        assertFalse(target.isDisneyPlusVisible)
        assertTrue("More Apps must always be visible", target.isMoreAppsVisible)

        // Case H=1 (只有 Netflix)
        val h1 = listOf(FocusNode.Content.App(DesignatedService.NETFLIX, "com.netflix.ninja/Entry"))
        binder.bindHomeApps(h1)
        assertFalse(target.isYoutubeVisible)
        assertTrue(target.isNetflixVisible)
        assertFalse(target.isDisneyPlusVisible)
        assertTrue(target.isMoreAppsVisible)

        // Case H=2 (YouTube, Disney+) - 驗證缺項壓縮
        val h2 = listOf(
            FocusNode.Content.App(DesignatedService.YOUTUBE, "com.google.android.youtube.tv/Entry"),
            FocusNode.Content.App(DesignatedService.DISNEY_PLUS, "com.disney.disneyplus/Entry")
        )
        binder.bindHomeApps(h2)
        assertTrue(target.isYoutubeVisible)
        assertFalse(target.isNetflixVisible)
        assertTrue(target.isDisneyPlusVisible)
        assertTrue(target.isMoreAppsVisible)

        // Case H=3 (YouTube -> Netflix -> Disney+)
        val h3 = listOf(
            FocusNode.Content.App(DesignatedService.YOUTUBE, "com.google.android.youtube.tv/Entry"),
            FocusNode.Content.App(DesignatedService.NETFLIX, "com.netflix.ninja/Entry"),
            FocusNode.Content.App(DesignatedService.DISNEY_PLUS, "com.disney.disneyplus/Entry")
        )
        binder.bindHomeApps(h3)
        assertTrue(target.isYoutubeVisible)
        assertTrue(target.isNetflixVisible)
        assertTrue(target.isDisneyPlusVisible)
        assertTrue(target.isMoreAppsVisible)
    }

    // 8. FocusState -> View 視覺同步 (scale 1.06x 與 focus 外觀)
    @Test
    fun testFocusVisualSynchronization() {
        val target = TestHomeLauncherViewTarget()
        val binder = HomeFocusViewBinder(target)

        // 聚焦在 Refresh
        binder.applyFocus(FocusNode.Tool.Refresh)
        assertTrue(target.isRefreshSelected)
        assertEquals(1.06f, target.refreshScale, 0.001f)
        assertFalse(target.isSettingsSelected)
        assertEquals(1.0f, target.settingsScale, 0.001f)

        // 轉移到 Settings
        binder.applyFocus(FocusNode.Tool.Settings)
        assertFalse(target.isRefreshSelected)
        assertEquals(1.0f, target.refreshScale, 0.001f)
        assertTrue(target.isSettingsSelected)
        assertEquals(1.06f, target.settingsScale, 0.001f)

        // 轉移到 MoreApps
        binder.applyFocus(FocusNode.Content.MoreApps)
        assertFalse(target.isSettingsSelected)
        assertTrue(target.isMoreAppsSelected)
        assertEquals(1.06f, target.moreAppsScale, 0.001f)
    }

    // 9. 隱藏 View 不可獲得選中/焦點樣式
    @Test
    fun testHiddenViewsCannotBeFocused() {
        val target = TestHomeLauncherViewTarget()
        val binder = HomeFocusViewBinder(target)

        // H 為空，YouTube 隱藏
        binder.bindHomeApps(emptyList())
        assertFalse(target.isYoutubeVisible)

        // 若意外收到 YouTube 焦點指令，隱藏 View 不得呈現選中樣式
        binder.applyFocus(FocusNode.Content.App(DesignatedService.YOUTUBE, "com.google.android.youtube.tv/Entry"))
        assertFalse(target.isYoutubeSelected)
        assertEquals(1.0f, target.youtubeScale, 0.001f)

        // 抽屜關閉時不可見
        binder.updateDrawerVisibility(false)
        assertFalse(target.isDrawerVisible)
    }

    // 10. 重複綁定 State 不改變導覽狀態 (Idempotence)
    @Test
    fun testIdempotentBinding() {
        val target = TestHomeLauncherViewTarget()
        val binder = HomeFocusViewBinder(target)

        binder.applyFocus(FocusNode.Tool.Refresh)
        assertEquals(1.06f, target.refreshScale, 0.001f)
        assertTrue(target.isRefreshSelected)

        // 再次綁定相同焦點
        binder.applyFocus(FocusNode.Tool.Refresh)
        assertEquals(1.06f, target.refreshScale, 0.001f)
        assertTrue(target.isRefreshSelected)
        assertEquals(1.0f, target.settingsScale, 0.001f)
        assertFalse(target.isSettingsSelected)
    }

    // 11. 基本 Activity 與 FocusController 結構整合
    @Test
    fun testActivityAndControllerIntegration() {
        val stateMachine = HomeFocusStateMachine()
        val controller = HomeFocusController(stateMachine)
        assertNotNull(controller.stateMachine)
        assertEquals(FocusNode.Tool.Refresh, controller.stateMachine.currentFocus)

        // 驗證按鍵流程與狀態機完全一致
        controller.dispatchKey(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_RIGHT, 0)
        assertEquals(FocusNode.Tool.Settings, controller.stateMachine.currentFocus)

        controller.dispatchKey(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_DOWN, 0)
        assertEquals(FocusNode.Content.MoreApps, controller.stateMachine.currentFocus)
    }
}
