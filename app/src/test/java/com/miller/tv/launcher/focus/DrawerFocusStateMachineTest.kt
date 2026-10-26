package com.miller.tv.launcher.focus

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Stage 2 — Slice 2: Drawer Focus & Back Navigation Test.
 *
 * Verifies Drawer focus behavior, overlay isolation, and Back navigation against:
 * - Feature: F03 (AC-F03-02, AC-F03-03, AC-F03-05, AC-F03-06)
 * - Feature: F05 (AC-F05-01, AC-F05-05)
 * - Baseline: SA-02, SA-03, and Spec line 115, 123 (v1.1)
 * - Scenarios: SC-026, SC-068, SC-069, SC-070, SC-071, SC-072, SC-073, SC-074, SC-088, SC-089, SC-091
 */
class DrawerFocusStateMachineTest {

    private val youtube = FocusNode.Content.App(DesignatedService.YOUTUBE, "com.google.android.youtube.tv/MainActivity")
    private val appA = FocusNode.Drawer.App("com.spotify.tv/MainActivity")
    private val appB = FocusNode.Drawer.App("com.plexapp.android/MainActivity")
    private val appC = FocusNode.Drawer.App("org.xbmc.kodi/MainActivity")

    // 1. SC-068: 只能由合法的「更多 APP」操作開啟面板 (AC-F03-05)
    @Test
    fun `drawer can only be opened when MoreApps is focused`() {
        val stateMachine = HomeFocusStateMachine(listOf(youtube))
        stateMachine.onDrawerSnapshotLoaded(listOf(appA, appB))
        // Currently focused on youtube (not MoreApps)
        assertEquals(youtube, stateMachine.currentFocus)

        // Attempting to open drawer from YouTube fails
        assertFalse(stateMachine.openDrawer())
        assertFalse(stateMachine.isDrawerOpen)
        assertEquals(youtube, stateMachine.currentFocus)

        // Navigate to MoreApps
        assertTrue(stateMachine.handleDpad(DpadDirection.RIGHT))
        assertEquals(FocusNode.Content.MoreApps, stateMachine.currentFocus)

        // Now opening drawer succeeds, initial focus goes to first O app (appA)
        assertTrue(stateMachine.openDrawer())
        assertTrue(stateMachine.isDrawerOpen)
        assertEquals(appA, stateMachine.currentFocus)
    }

    @Test
    fun `handleOk on MoreApps opens drawer`() {
        val stateMachine = HomeFocusStateMachine(listOf(youtube))
        stateMachine.onDrawerSnapshotLoaded(listOf(appA, appB))
        stateMachine.handleDpad(DpadDirection.RIGHT) // on MoreApps
        assertEquals(FocusNode.Content.MoreApps, stateMachine.currentFocus)

        // OK on MoreApps opens drawer
        assertTrue(stateMachine.handleOk())
        assertTrue(stateMachine.isDrawerOpen)
        assertEquals(appA, stateMachine.currentFocus)
    }

    // 2. SC-069: 面板開啟後，背景 Home 內容與工具列不再接收焦點 (AC-F03-06)
    @Test
    fun `drawer active state isolates background home from focus`() {
        val stateMachine = HomeFocusStateMachine(listOf(youtube))
        stateMachine.onDrawerSnapshotLoaded(listOf(appA, appB))
        stateMachine.handleDpad(DpadDirection.RIGHT) // on MoreApps
        stateMachine.openDrawer()
        assertTrue(stateMachine.isDrawerOpen)
        assertEquals(appA, stateMachine.currentFocus)

        // Only drawer items receive focus; background tools or apps cannot receive focus
        assertTrue(stateMachine.currentFocus is FocusNode.Drawer)
    }

    // 3. SC-068, SC-070: 面板包含「關閉面板」與可用 App 清單 (SA-02)
    @Test
    fun `drawer contains Close control followed by O apps`() {
        val stateMachine = HomeFocusStateMachine()
        stateMachine.onDrawerSnapshotLoaded(listOf(appA, appB))
        assertEquals(
            listOf(FocusNode.Drawer.Close, appA, appB),
            stateMachine.drawerItems
        )
    }

    // 4. SC-070: 面板為單欄垂直列表，Up Down 依合法節點相鄰移動 (AC-F03-02)
    @Test
    fun `drawer navigates linearly with Up and Down without triggering external actions`() {
        // Given 面板 O 依序 A、B、C，A 聚焦 (SC-070)
        val stateMachine = HomeFocusStateMachine()
        stateMachine.onDrawerSnapshotLoaded(listOf(appA, appB, appC))
        stateMachine.handleDpad(DpadDirection.DOWN) // to MoreApps
        stateMachine.openDrawer()
        assertEquals(appA, stateMachine.currentFocus)

        // When 依序按 Down, Down, Up, Up, Up, Down
        // Then 焦點依序為 B、C、B、A、關閉面板、A (SC-070)
        assertTrue(stateMachine.handleDpad(DpadDirection.DOWN))
        assertEquals(appB, stateMachine.currentFocus)

        assertTrue(stateMachine.handleDpad(DpadDirection.DOWN))
        assertEquals(appC, stateMachine.currentFocus)

        assertTrue(stateMachine.handleDpad(DpadDirection.UP))
        assertEquals(appB, stateMachine.currentFocus)

        assertTrue(stateMachine.handleDpad(DpadDirection.UP))
        assertEquals(appA, stateMachine.currentFocus)

        assertTrue(stateMachine.handleDpad(DpadDirection.UP))
        assertEquals(FocusNode.Drawer.Close, stateMachine.currentFocus)

        assertTrue(stateMachine.handleDpad(DpadDirection.DOWN))
        assertEquals(appA, stateMachine.currentFocus)
    }

    // 5. SC-069, SC-071: Left Right 不得穿透到首頁 (AC-F03-06, SA-02)
    @Test
    fun `drawer Left and Right are no-ops and do not bleed into background home`() {
        val stateMachine = HomeFocusStateMachine(listOf(youtube))
        stateMachine.onDrawerSnapshotLoaded(listOf(appA))
        stateMachine.handleDpad(DpadDirection.RIGHT) // to MoreApps
        stateMachine.openDrawer()
        assertEquals(appA, stateMachine.currentFocus)

        // Left and Right on appA stay on appA
        assertFalse(stateMachine.handleDpad(DpadDirection.LEFT))
        assertEquals(appA, stateMachine.currentFocus)

        assertFalse(stateMachine.handleDpad(DpadDirection.RIGHT))
        assertEquals(appA, stateMachine.currentFocus)

        // Move to Close
        stateMachine.handleDpad(DpadDirection.UP)
        assertEquals(FocusNode.Drawer.Close, stateMachine.currentFocus)

        // Left and Right on Close stay on Close
        assertFalse(stateMachine.handleDpad(DpadDirection.LEFT))
        assertEquals(FocusNode.Drawer.Close, stateMachine.currentFocus)

        assertFalse(stateMachine.handleDpad(DpadDirection.RIGHT))
        assertEquals(FocusNode.Drawer.Close, stateMachine.currentFocus)
    }

    // 6. SC-070, SC-071: 面板上下邊界不循環 (AC-F03-03, SA-02)
    @Test
    fun `drawer boundaries do not wrap`() {
        val stateMachine = HomeFocusStateMachine()
        stateMachine.onDrawerSnapshotLoaded(listOf(appA))
        stateMachine.handleDpad(DpadDirection.DOWN) // to MoreApps
        stateMachine.openDrawer()
        assertEquals(appA, stateMachine.currentFocus)

        // Down from the last app stays
        assertFalse(stateMachine.handleDpad(DpadDirection.DOWN))
        assertEquals(appA, stateMachine.currentFocus)

        // Up to Close
        stateMachine.handleDpad(DpadDirection.UP)
        assertEquals(FocusNode.Drawer.Close, stateMachine.currentFocus)

        // Up from Close stays (no wrap to bottom)
        assertFalse(stateMachine.handleDpad(DpadDirection.UP))
        assertEquals(FocusNode.Drawer.Close, stateMachine.currentFocus)
    }

    // 7. SC-072, SC-074: Empty 或 Error 時僅有合法可操作焦點 (關閉面板) (AC-F03-03)
    @Test
    fun `empty drawer opens with Close focused and boundaries hold`() {
        // Given 成功快照 O 空但 H 有 YouTube (SC-072)
        val stateMachine = HomeFocusStateMachine(listOf(youtube))
        stateMachine.onDrawerSnapshotLoaded(emptyList())
        stateMachine.handleDpad(DpadDirection.RIGHT) // to MoreApps

        // When 開啟更多 APP
        stateMachine.openDrawer()
        assertTrue(stateMachine.isDrawerOpen)
        // Then 關閉面板聚焦 (SC-072)
        assertEquals(FocusNode.Drawer.Close, stateMachine.currentFocus)

        // 依序按 Up、Down、Left、Right (SC-072)
        assertFalse(stateMachine.handleDpad(DpadDirection.UP))
        assertEquals(FocusNode.Drawer.Close, stateMachine.currentFocus)

        assertFalse(stateMachine.handleDpad(DpadDirection.DOWN))
        assertEquals(FocusNode.Drawer.Close, stateMachine.currentFocus)

        assertFalse(stateMachine.handleDpad(DpadDirection.LEFT))
        assertEquals(FocusNode.Drawer.Close, stateMachine.currentFocus)

        assertFalse(stateMachine.handleDpad(DpadDirection.RIGHT))
        assertEquals(FocusNode.Drawer.Close, stateMachine.currentFocus)
    }

    // 8. SC-073, AC-F05-05: 按 Back 關閉面板，回首頁聚焦「更多 APP」
    @Test
    fun `back closes drawer and restores sole focus to MoreApps without exiting`() {
        val stateMachine = HomeFocusStateMachine(listOf(youtube))
        stateMachine.onDrawerSnapshotLoaded(listOf(appA))
        stateMachine.handleDpad(DpadDirection.RIGHT) // to MoreApps
        stateMachine.openDrawer()
        assertTrue(stateMachine.isDrawerOpen)

        // Pressing Back closes the drawer
        val result = stateMachine.handleBack()
        assertEquals(BackResult.DrawerClosed, result)
        assertFalse(stateMachine.isDrawerOpen)
        assertEquals(FocusNode.Content.MoreApps, stateMachine.currentFocus)
    }

    // 9. SC-074, AC-F05-05: 使用「關閉面板」按鈕與 Back 具有一致的關閉與焦點恢復行為
    @Test
    fun `activating Close control closes drawer and restores focus to MoreApps`() {
        val stateMachine = HomeFocusStateMachine()
        stateMachine.onDrawerSnapshotLoaded(listOf(appA))
        stateMachine.handleDpad(DpadDirection.DOWN) // to MoreApps
        stateMachine.openDrawer()
        stateMachine.handleDpad(DpadDirection.UP) // to Close
        assertEquals(FocusNode.Drawer.Close, stateMachine.currentFocus)

        // Triggering OK on Close control closes drawer
        assertTrue(stateMachine.handleOk())
        assertFalse(stateMachine.isDrawerOpen)
        assertEquals(FocusNode.Content.MoreApps, stateMachine.currentFocus)
    }

    // 10. SC-026, SC-073, AC-F05-01: 面板關閉後，下一次獨立 Back 才退出根首頁
    @Test
    fun `two sequential back presses close drawer then exit root home`() {
        val stateMachine = HomeFocusStateMachine()
        stateMachine.onDrawerSnapshotLoaded(listOf(appA))
        stateMachine.handleDpad(DpadDirection.DOWN) // to MoreApps
        stateMachine.openDrawer()
        assertTrue(stateMachine.isDrawerOpen)

        // First Back: closes drawer
        val firstBack = stateMachine.handleBack()
        assertEquals(BackResult.DrawerClosed, firstBack)
        assertFalse(stateMachine.isDrawerOpen)
        assertEquals(FocusNode.Content.MoreApps, stateMachine.currentFocus)

        // Second independent Back: exits root launcher (SC-026 / SC-073)
        val secondBack = stateMachine.handleBack()
        assertEquals(BackResult.ExitLauncher, secondBack)
    }

    // 11. 規格行 123, SC-027: 面板資料更新後，保留仍有效的 stable key 焦點；若失效則依正式規格修復
    @Test
    fun `drawer data update preserves stable key or repairs within O`() {
        val stateMachine = HomeFocusStateMachine()
        stateMachine.onDrawerSnapshotLoaded(listOf(appA, appB, appC))
        stateMachine.handleDpad(DpadDirection.DOWN) // to MoreApps
        stateMachine.openDrawer()
        stateMachine.handleDpad(DpadDirection.DOWN) // to appB
        assertEquals(appB, stateMachine.currentFocus)

        // Update O: reordered to [appC, appA, appB] -> stable key appB is preserved
        stateMachine.onDrawerSnapshotLoaded(listOf(appC, appA, appB))
        assertEquals(appB, stateMachine.currentFocus)

        // Update O: appB disappears, new O is [appA, appC]
        // Old O: [appA, appB, appC] (appB index 1)
        // Spec line 123: "面板 App 消失：用舊 O 索引在新 O 取項，越尾取尾，O 空取關閉面板"
        // In new O [appA, appC], index 1 is appC!
        stateMachine.onDrawerSnapshotLoaded(listOf(appA, appC))
        assertEquals(appC, stateMachine.currentFocus)

        // When all apps in O disappear, repair fallback is Close control
        stateMachine.onDrawerSnapshotLoaded(emptyList())
        assertEquals(FocusNode.Drawer.Close, stateMachine.currentFocus)
    }

    // 12. 規格行 115: 面板關閉後，晚回應不得自行重新開啟面板
    @Test
    fun `late snapshot arriving after drawer closed does not reopen drawer`() {
        val stateMachine = HomeFocusStateMachine(listOf(youtube))
        stateMachine.onDrawerSnapshotLoaded(listOf(appA))
        stateMachine.handleDpad(DpadDirection.RIGHT) // to MoreApps
        stateMachine.openDrawer()
        assertTrue(stateMachine.isDrawerOpen)

        // User closes drawer via Back
        stateMachine.handleBack()
        assertFalse(stateMachine.isDrawerOpen)
        assertEquals(FocusNode.Content.MoreApps, stateMachine.currentFocus)

        // Late response arrives with new apps
        stateMachine.onDrawerSnapshotLoaded(listOf(appA, appB, appC))
        // Drawer must stay closed!
        assertFalse(stateMachine.isDrawerOpen)
        assertEquals(FocusNode.Content.MoreApps, stateMachine.currentFocus)
    }

    // SC-091: 面板 loading 時 Back 仍可關閉
    @Test
    fun `drawer can be closed via Back even when empty or loading`() {
        val stateMachine = HomeFocusStateMachine()
        stateMachine.onDrawerSnapshotLoaded(emptyList())
        stateMachine.handleDpad(DpadDirection.DOWN) // to MoreApps
        stateMachine.openDrawer()
        assertTrue(stateMachine.isDrawerOpen)
        assertEquals(FocusNode.Drawer.Close, stateMachine.currentFocus)

        assertEquals(BackResult.DrawerClosed, stateMachine.handleBack())
        assertFalse(stateMachine.isDrawerOpen)
        assertEquals(FocusNode.Content.MoreApps, stateMachine.currentFocus)
    }
}
