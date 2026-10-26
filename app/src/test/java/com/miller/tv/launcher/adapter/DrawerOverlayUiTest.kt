package com.miller.tv.launcher.adapter

import android.view.KeyEvent
import com.miller.tv.launcher.focus.BackResult
import com.miller.tv.launcher.focus.DpadDirection
import com.miller.tv.launcher.focus.FocusNode
import com.miller.tv.launcher.focus.HomeFocusStateMachine
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class DrawerOverlayUiTest {

    private class TestDrawerLauncherViewTarget : HomeLauncherViewTarget {
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

        // O App items state in drawer
        var drawerAppItems: List<DrawerAppItemUiState> = emptyList()

        override fun bindDrawerApps(apps: List<DrawerAppItemUiState>) {
            this.drawerAppItems = apps
        }
    }

    // 1. Drawer 開啟與關閉，以及背景焦點隔離
    @Test
    fun testDrawerOpenAndBackgroundIsolation() {
        val stateMachine = HomeFocusStateMachine()
        val target = TestDrawerLauncherViewTarget()
        val binder = HomeFocusViewBinder(target)
        val controller = HomeFocusController(stateMachine, binder)

        // 移至 MoreApps 並開啟 Drawer
        controller.dispatchKey(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_DOWN, 0)
        assertEquals(FocusNode.Content.MoreApps, stateMachine.currentFocus)
        assertTrue(target.isMoreAppsSelected)

        // OK 開啟 Drawer
        controller.dispatchKey(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_CENTER, 0)
        assertTrue(stateMachine.isDrawerOpen)
        assertTrue("Drawer must be visible when open", target.isDrawerVisible)
        assertFalse("Background MoreApps must lose focus highlight", target.isMoreAppsSelected)
        assertFalse("Background Refresh must not be focused", target.isRefreshSelected)
        assertFalse("Background Settings must not be focused", target.isSettingsSelected)
    }

    // 2. O=0 時，新開 Drawer 預設聚焦在 Close
    @Test
    fun testDrawerOpenWithEmptyO() {
        val stateMachine = HomeFocusStateMachine()
        val target = TestDrawerLauncherViewTarget()
        val binder = HomeFocusViewBinder(target)
        val controller = HomeFocusController(stateMachine, binder)

        controller.dispatchKey(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_DOWN, 0)
        controller.dispatchKey(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_CENTER, 0)

        // O 為空時，新開焦點為 Close (SA-02)
        assertEquals(FocusNode.Drawer.Close, stateMachine.currentFocus)
        assertTrue(target.isDrawerCloseSelected)
        assertEquals(1.06f, target.drawerCloseScale, 0.001f)
        assertTrue(target.drawerAppItems.isEmpty())
    }

    // 3. O=1 或多筆時，新開 Drawer 預設聚焦在第一款 O App
    @Test
    fun testDrawerOpenWithMultipleOApps() {
        val stateMachine = HomeFocusStateMachine()
        val oApps = listOf(
            FocusNode.Drawer.App("com.plexapp.android/Entry"),
            FocusNode.Drawer.App("org.xbmc.kodi/Entry"),
            FocusNode.Drawer.App("com.spotify.tv.android/Entry")
        )
        stateMachine.onDrawerSnapshotLoaded(oApps)

        val target = TestDrawerLauncherViewTarget()
        val binder = HomeFocusViewBinder(target)
        val controller = HomeFocusController(stateMachine, binder)

        controller.dispatchKey(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_DOWN, 0)
        controller.dispatchKey(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_CENTER, 0)

        assertTrue(stateMachine.isDrawerOpen)
        assertEquals(FocusNode.Drawer.App("com.plexapp.android/Entry"), stateMachine.currentFocus)
        assertEquals(3, target.drawerAppItems.size)
        assertTrue("First O App must be selected", target.drawerAppItems[0].isSelected)
        assertEquals(1.06f, target.drawerAppItems[0].scale, 0.001f)
        assertFalse(target.drawerAppItems[1].isSelected)
        assertFalse(target.isDrawerCloseSelected)
    }

    // 4. Drawer 內上下邊界停留與單欄不循環
    @Test
    fun testDrawerVerticalNavigationAndBoundaryStay() {
        val stateMachine = HomeFocusStateMachine()
        val oApps = listOf(
            FocusNode.Drawer.App("com.plexapp.android/Entry"),
            FocusNode.Drawer.App("org.xbmc.kodi/Entry")
        )
        stateMachine.onDrawerSnapshotLoaded(oApps)

        val target = TestDrawerLauncherViewTarget()
        val binder = HomeFocusViewBinder(target)
        val controller = HomeFocusController(stateMachine, binder)

        controller.dispatchKey(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_DOWN, 0)
        controller.dispatchKey(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_CENTER, 0)
        assertEquals(FocusNode.Drawer.App("com.plexapp.android/Entry"), stateMachine.currentFocus)

        // UP 移至 Close
        val consumedUp1 = controller.dispatchKey(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_UP, 0)
        assertTrue(consumedUp1)
        assertEquals(FocusNode.Drawer.Close, stateMachine.currentFocus)
        assertTrue(target.isDrawerCloseSelected)

        // 再 UP 碰壁停留 (Top Boundary Stay, 不循環)
        val consumedUp2 = controller.dispatchKey(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_UP, 0)
        assertTrue("Top boundary must be consumed", consumedUp2)
        assertEquals(FocusNode.Drawer.Close, stateMachine.currentFocus)
        assertTrue(target.isDrawerCloseSelected)

        // DOWN 回第一 App
        controller.dispatchKey(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_DOWN, 0)
        assertEquals(FocusNode.Drawer.App("com.plexapp.android/Entry"), stateMachine.currentFocus)

        // DOWN 到第二 App
        controller.dispatchKey(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_DOWN, 0)
        assertEquals(FocusNode.Drawer.App("org.xbmc.kodi/Entry"), stateMachine.currentFocus)

        // 再 DOWN 碰壁停留 (Bottom Boundary Stay, 不循環)
        val consumedDown2 = controller.dispatchKey(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_DOWN, 0)
        assertTrue("Bottom boundary must be consumed", consumedDown2)
        assertEquals(FocusNode.Drawer.App("org.xbmc.kodi/Entry"), stateMachine.currentFocus)
    }

    // 5. Drawer 開啟時 Left/Right 不移動焦點 (No-op 且消耗)
    @Test
    fun testDrawerLeftRightNoOpConsumed() {
        val stateMachine = HomeFocusStateMachine()
        stateMachine.onDrawerSnapshotLoaded(listOf(FocusNode.Drawer.App("com.plexapp.android/Entry")))

        val target = TestDrawerLauncherViewTarget()
        val binder = HomeFocusViewBinder(target)
        val controller = HomeFocusController(stateMachine, binder)

        controller.dispatchKey(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_DOWN, 0)
        controller.dispatchKey(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_CENTER, 0)
        val initialFocus = stateMachine.currentFocus

        val consumedLeft = controller.dispatchKey(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_LEFT, 0)
        assertTrue("LEFT must be consumed to block focus escape", consumedLeft)
        assertEquals(initialFocus, stateMachine.currentFocus)

        val consumedRight = controller.dispatchKey(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_RIGHT, 0)
        assertTrue("RIGHT must be consumed to block focus escape", consumedRight)
        assertEquals(initialFocus, stateMachine.currentFocus)
    }

    // 6. Close 控制項點擊關閉 Drawer 並恢復 MoreApps
    @Test
    fun testCloseButtonClosesDrawer() {
        val stateMachine = HomeFocusStateMachine()
        val target = TestDrawerLauncherViewTarget()
        val binder = HomeFocusViewBinder(target)
        val controller = HomeFocusController(stateMachine, binder)

        controller.dispatchKey(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_DOWN, 0)
        controller.dispatchKey(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_CENTER, 0)
        assertEquals(FocusNode.Drawer.Close, stateMachine.currentFocus)

        // OK 在 Close 按鈕上
        controller.dispatchKey(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_CENTER, 0)
        assertFalse("Drawer must be closed", stateMachine.isDrawerOpen)
        assertFalse(target.isDrawerVisible)
        assertEquals(FocusNode.Content.MoreApps, stateMachine.currentFocus)
        assertTrue(target.isMoreAppsSelected)
    }

    // 7. Back 關閉 Drawer 並恢復 MoreApps (不退出 Launcher)
    @Test
    fun testBackClosesDrawerRestoresMoreApps() {
        val stateMachine = HomeFocusStateMachine()
        stateMachine.onDrawerSnapshotLoaded(listOf(FocusNode.Drawer.App("com.plexapp.android/Entry")))

        var exitCalled = false
        val target = TestDrawerLauncherViewTarget()
        val binder = HomeFocusViewBinder(target)
        val controller = HomeFocusController(
            stateMachine = stateMachine,
            viewBinder = binder,
            onExitLauncher = { exitCalled = true }
        )

        controller.dispatchKey(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_DOWN, 0)
        controller.dispatchKey(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_CENTER, 0)
        assertTrue(stateMachine.isDrawerOpen)

        // 按 Back
        val consumed = controller.dispatchKey(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK, 0)
        assertTrue(consumed)
        assertFalse(stateMachine.isDrawerOpen)
        assertFalse(target.isDrawerVisible)
        assertFalse("Exit launcher must not be called", exitCalled)
        assertEquals(FocusNode.Content.MoreApps, stateMachine.currentFocus)
        assertTrue(target.isMoreAppsSelected)
    }

    // 8. O App 消失後焦點自動修復至鄰近項目或 Close
    @Test
    fun testOAppDisappearFocusRepair() {
        val stateMachine = HomeFocusStateMachine()
        val app1 = FocusNode.Drawer.App("com.plexapp.android/Entry")
        val app2 = FocusNode.Drawer.App("org.xbmc.kodi/Entry")
        stateMachine.onDrawerSnapshotLoaded(listOf(app1, app2))

        val target = TestDrawerLauncherViewTarget()
        val binder = HomeFocusViewBinder(target)
        val controller = HomeFocusController(stateMachine, binder)

        controller.dispatchKey(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_DOWN, 0)
        controller.dispatchKey(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_CENTER, 0)
        // 移至第二項 app2
        controller.dispatchKey(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_DOWN, 0)
        assertEquals(app2, stateMachine.currentFocus)

        // 更新快照：app2 消失，只剩 app1
        stateMachine.onDrawerSnapshotLoaded(listOf(app1))
        binder.syncState(stateMachine.state)

        assertEquals("Focus must be clamped to app1", app1, stateMachine.currentFocus)
        assertEquals(1, target.drawerAppItems.size)
        assertTrue(target.drawerAppItems[0].isSelected)

        // 若全部 O 消失，焦點修復至 Close
        stateMachine.onDrawerSnapshotLoaded(emptyList())
        binder.syncState(stateMachine.state)
        assertEquals(FocusNode.Drawer.Close, stateMachine.currentFocus)
        assertTrue(target.isDrawerCloseSelected)
    }

    // 9. 關閉 Drawer 後晚到的非同步快照絕不重開 Drawer
    @Test
    fun testLateSnapshotDoesNotReopenDrawer() {
        val stateMachine = HomeFocusStateMachine()
        val target = TestDrawerLauncherViewTarget()
        val binder = HomeFocusViewBinder(target)
        val controller = HomeFocusController(stateMachine, binder)

        // 在首頁未開 Drawer
        assertFalse(stateMachine.isDrawerOpen)

        // 晚到的 O 快照抵達
        stateMachine.onDrawerSnapshotLoaded(listOf(FocusNode.Drawer.App("com.plexapp.android/Entry")))
        binder.syncState(stateMachine.state)

        assertFalse("Drawer must remain closed", stateMachine.isDrawerOpen)
        assertFalse(target.isDrawerVisible)
    }
}
