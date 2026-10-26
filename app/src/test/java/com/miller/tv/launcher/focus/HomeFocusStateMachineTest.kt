package com.miller.tv.launcher.focus

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Stage 2 — Slice 1: Home D-pad Focus State Machine Test.
 *
 * Verifies Home focus behavior against:
 * - Feature: F03 (AC-F03-01, AC-F03-02, AC-F03-03)
 * - Scenarios: SC-013, SC-055, SC-064, SC-065, SC-066, SC-067
 * - Baseline: SA-01 (Specification Baseline v1.1)
 */
class HomeFocusStateMachineTest {

    private val youtube = FocusNode.Content.App(DesignatedService.YOUTUBE, "com.google.android.youtube.tv/MainActivity")
    private val netflix = FocusNode.Content.App(DesignatedService.NETFLIX, "com.netflix.ninja/MainActivity")
    private val disneyPlus = FocusNode.Content.App(DesignatedService.DISNEY_PLUS, "com.disney.disneyplus/MainActivity")

    // 1. SC-064: 首次 Ready 且指定 App 存在時的初始焦點 (AC-F03-01)
    @Test
    fun `initial ready with apps focuses first available designated app in fixed order`() {
        // Given 首頁首次 loading 尚未有人操作；查詢回傳合格 Netflix、Disney+，YouTube 無合格入口
        val stateMachine = HomeFocusStateMachine()
        stateMachine.onInitialLoading()
        assertEquals(FocusNode.Tool.Refresh, stateMachine.currentFocus)
        assertFalse(stateMachine.hasUserInteracted)

        // When 提交首次成功快照
        stateMachine.onSnapshotLoaded(listOf(netflix, disneyPlus))

        // Then Netflix 為唯一可見焦點；更多 APP、Disney+ 與工具沒有焦點 (SC-064)
        assertEquals(netflix, stateMachine.currentFocus)
    }

    // 2. SC-065: 三個指定 App 都不存在時，聚焦更多 APP (AC-F03-01)
    @Test
    fun `initial ready with empty H focuses MoreApps when user has not interacted`() {
        // Given 三服務映射已確認且首次成功快照 H 空，使用者未操作
        val stateMachine = HomeFocusStateMachine()
        stateMachine.onInitialLoading()

        // When 提交首次結果
        stateMachine.onSnapshotLoaded(emptyList())

        // Then 更多 APP 具有唯一可見焦點，不聚焦隱藏 App (SC-065)
        assertEquals(FocusNode.Content.MoreApps, stateMachine.currentFocus)
    }

    // 3. SC-066: 首頁內容列 Left Right 焦點轉移 (AC-F03-02)
    @Test
    fun `content row navigates left and right through adjacent items`() {
        val stateMachine = HomeFocusStateMachine()
        stateMachine.onSnapshotLoaded(listOf(youtube, netflix, disneyPlus))
        assertEquals(youtube, stateMachine.currentFocus)

        // Move Right: YouTube -> Netflix -> Disney+ -> MoreApps
        assertTrue(stateMachine.handleDpad(DpadDirection.RIGHT))
        assertEquals(netflix, stateMachine.currentFocus)

        assertTrue(stateMachine.handleDpad(DpadDirection.RIGHT))
        assertEquals(disneyPlus, stateMachine.currentFocus)

        assertTrue(stateMachine.handleDpad(DpadDirection.RIGHT))
        assertEquals(FocusNode.Content.MoreApps, stateMachine.currentFocus)

        // Move Left: MoreApps -> Disney+ -> Netflix -> YouTube
        assertTrue(stateMachine.handleDpad(DpadDirection.LEFT))
        assertEquals(disneyPlus, stateMachine.currentFocus)

        assertTrue(stateMachine.handleDpad(DpadDirection.LEFT))
        assertEquals(netflix, stateMachine.currentFocus)

        assertTrue(stateMachine.handleDpad(DpadDirection.LEFT))
        assertEquals(youtube, stateMachine.currentFocus)
    }

    // 4. SC-066: 首頁內容列 Up 至工具列 (AC-F03-02)
    @Test
    fun `content row Up moves to last tool focus or defaults to Refresh`() {
        val stateMachine = HomeFocusStateMachine()
        stateMachine.onSnapshotLoaded(listOf(youtube, netflix, disneyPlus))
        stateMachine.handleDpad(DpadDirection.RIGHT) // on Netflix

        // When moving Up without prior tool history
        assertTrue(stateMachine.handleDpad(DpadDirection.UP))
        // Then defaults to Refresh and remembers content node (Netflix)
        assertEquals(FocusNode.Tool.Refresh, stateMachine.currentFocus)
        assertEquals(netflix, stateMachine.lastContentFocus)
    }

    // 5. SC-066: 工具列 Down 返回最近有效的內容項目 (AC-F03-02)
    @Test
    fun `toolbar Down returns to last valid content focus`() {
        // Given H 依序 YouTube、Netflix、Disney+，Netflix 聚焦且最後工具為重新整理
        val stateMachine = HomeFocusStateMachine()
        stateMachine.onSnapshotLoaded(listOf(youtube, netflix, disneyPlus))
        stateMachine.handleDpad(DpadDirection.RIGHT) // focus Netflix
        assertEquals(netflix, stateMachine.currentFocus)

        // When 依序按 Right (Disney+), Right (更多 APP), Up (重新整理), Right (系統設定), Down
        assertTrue(stateMachine.handleDpad(DpadDirection.RIGHT))
        assertEquals(disneyPlus, stateMachine.currentFocus)

        assertTrue(stateMachine.handleDpad(DpadDirection.RIGHT))
        assertEquals(FocusNode.Content.MoreApps, stateMachine.currentFocus)

        assertTrue(stateMachine.handleDpad(DpadDirection.UP))
        assertEquals(FocusNode.Tool.Refresh, stateMachine.currentFocus)

        assertTrue(stateMachine.handleDpad(DpadDirection.RIGHT))
        assertEquals(FocusNode.Tool.Settings, stateMachine.currentFocus)

        // Down returns to last active content item (MoreApps)
        assertTrue(stateMachine.handleDpad(DpadDirection.DOWN))
        assertEquals(FocusNode.Content.MoreApps, stateMachine.currentFocus)
    }

    // 6. SC-013, SC-066, SC-067: 工具列內焦點移動及四周邊界不循環 (AC-F03-02, AC-F03-03)
    @Test
    fun `toolbar navigates left right and boundaries do not wrap`() {
        val stateMachine = HomeFocusStateMachine()
        stateMachine.onInitialLoading()
        assertEquals(FocusNode.Tool.Refresh, stateMachine.currentFocus)

        // Refresh Left boundary stays on Refresh
        assertFalse(stateMachine.handleDpad(DpadDirection.LEFT))
        assertEquals(FocusNode.Tool.Refresh, stateMachine.currentFocus)

        // Refresh Up boundary stays on Refresh
        assertFalse(stateMachine.handleDpad(DpadDirection.UP))
        assertEquals(FocusNode.Tool.Refresh, stateMachine.currentFocus)

        // Refresh Right moves to Settings
        assertTrue(stateMachine.handleDpad(DpadDirection.RIGHT))
        assertEquals(FocusNode.Tool.Settings, stateMachine.currentFocus)

        // Settings Right boundary stays on Settings
        assertFalse(stateMachine.handleDpad(DpadDirection.RIGHT))
        assertEquals(FocusNode.Tool.Settings, stateMachine.currentFocus)

        // Settings Up boundary stays on Settings
        assertFalse(stateMachine.handleDpad(DpadDirection.UP))
        assertEquals(FocusNode.Tool.Settings, stateMachine.currentFocus)

        // Settings Left moves back to Refresh
        assertTrue(stateMachine.handleDpad(DpadDirection.LEFT))
        assertEquals(FocusNode.Tool.Refresh, stateMachine.currentFocus)
    }

    @Test
    fun `content row boundaries do not wrap`() {
        val stateMachine = HomeFocusStateMachine()
        stateMachine.onSnapshotLoaded(listOf(youtube))
        assertEquals(youtube, stateMachine.currentFocus)

        // Left boundary on first item stays
        assertFalse(stateMachine.handleDpad(DpadDirection.LEFT))
        assertEquals(youtube, stateMachine.currentFocus)

        // Down boundary on content stays
        assertFalse(stateMachine.handleDpad(DpadDirection.DOWN))
        assertEquals(youtube, stateMachine.currentFocus)

        // Move to MoreApps (the rightmost item)
        assertTrue(stateMachine.handleDpad(DpadDirection.RIGHT))
        assertEquals(FocusNode.Content.MoreApps, stateMachine.currentFocus)

        // Right boundary on MoreApps stays
        assertFalse(stateMachine.handleDpad(DpadDirection.RIGHT))
        assertEquals(FocusNode.Content.MoreApps, stateMachine.currentFocus)

        // Down boundary on MoreApps stays
        assertFalse(stateMachine.handleDpad(DpadDirection.DOWN))
        assertEquals(FocusNode.Content.MoreApps, stateMachine.currentFocus)
    }

    // SC-067: 沒有指定 App 的首頁仍有內容出口 (AC-F03-03)
    @Test
    fun `empty H has MoreApps content exit and boundaries hold`() {
        // Given H 空、面板關閉、重新整理聚焦，無內容焦點歷史
        val stateMachine = HomeFocusStateMachine()
        stateMachine.onInitialLoading()
        assertEquals(FocusNode.Tool.Refresh, stateMachine.currentFocus)

        // When 依序按 Down, Left, Right, Down, Up
        // Then 焦點依序為更多 APP, 更多 APP, 更多 APP, 更多 APP, 重新整理，不循環且不建立空白 App
        assertTrue(stateMachine.handleDpad(DpadDirection.DOWN))
        assertEquals(FocusNode.Content.MoreApps, stateMachine.currentFocus)

        assertFalse(stateMachine.handleDpad(DpadDirection.LEFT))
        assertEquals(FocusNode.Content.MoreApps, stateMachine.currentFocus)

        assertFalse(stateMachine.handleDpad(DpadDirection.RIGHT))
        assertEquals(FocusNode.Content.MoreApps, stateMachine.currentFocus)

        assertFalse(stateMachine.handleDpad(DpadDirection.DOWN))
        assertEquals(FocusNode.Content.MoreApps, stateMachine.currentFocus)

        assertTrue(stateMachine.handleDpad(DpadDirection.UP))
        assertEquals(FocusNode.Tool.Refresh, stateMachine.currentFocus)
    }

    // 7. 首頁焦點唯一性 (AC-F03-01)
    @Test
    fun `focus remains strictly singular at all times`() {
        val stateMachine = HomeFocusStateMachine()
        stateMachine.onSnapshotLoaded(listOf(youtube, netflix))

        assertNotNull(stateMachine.currentFocus)
        val initialFocus = stateMachine.currentFocus

        stateMachine.handleDpad(DpadDirection.RIGHT)
        assertEquals(netflix, stateMachine.currentFocus)

        stateMachine.handleDpad(DpadDirection.UP)
        assertEquals(FocusNode.Tool.Refresh, stateMachine.currentFocus)

        // Exactly one node is active in the state model
        assertEquals(FocusNode.Tool.Refresh, stateMachine.state.currentFocus)
        assertEquals(FocusNode.Tool.Refresh, stateMachine.state.lastToolFocus)
    }

    // 8. 不存在或失效的 App 不得成為焦點目標 (SA-01, AC-F03-03, 02 行 123)
    @Test
    fun `unavailable apps are compressed out and do not take focus`() {
        // Only YouTube and Disney+ present; Netflix is missing (compressed out)
        val stateMachine = HomeFocusStateMachine()
        stateMachine.onSnapshotLoaded(listOf(youtube, disneyPlus))
        assertEquals(youtube, stateMachine.currentFocus)

        // Right moves directly to Disney+, skipping missing Netflix
        assertTrue(stateMachine.handleDpad(DpadDirection.RIGHT))
        assertEquals(disneyPlus, stateMachine.currentFocus)

        // Per spec line 123: "首頁 App 消失：用舊 H 索引在新 H 取項，越尾取尾，H 空取更多 APP"
        // When disneyPlus (old H index 1) disappears, new H has size 1 ([youtube]).
        // Clamping within H yields youtube (index 0), NOT MoreApps!
        stateMachine.onSnapshotLoaded(listOf(youtube))
        assertEquals(youtube, stateMachine.currentFocus)

        // When all apps in H disappear, clamp within H fails -> fallback to MoreApps
        stateMachine.onSnapshotLoaded(emptyList())
        assertEquals(FocusNode.Content.MoreApps, stateMachine.currentFocus)
    }

    @Test
    fun `disappeared lastContentFocus while in tools is repaired within H`() {
        // Given user focused on disneyPlus, then moved Up to Settings
        val stateMachine = HomeFocusStateMachine()
        stateMachine.onSnapshotLoaded(listOf(youtube, disneyPlus))
        stateMachine.handleDpad(DpadDirection.RIGHT) // on disneyPlus
        stateMachine.handleDpad(DpadDirection.UP) // on Refresh
        stateMachine.handleDpad(DpadDirection.RIGHT) // on Settings
        assertEquals(FocusNode.Tool.Settings, stateMachine.currentFocus)
        assertEquals(disneyPlus, stateMachine.lastContentFocus)

        // When disneyPlus disappears from H, leaving only youtube
        // Per spec line 124: "背景所記住的首頁內容也按 H 修復"
        stateMachine.onSnapshotLoaded(listOf(youtube))

        // Focus remains on Settings (does not snatch focus)
        assertEquals(FocusNode.Tool.Settings, stateMachine.currentFocus)
        // Background remembered content is repaired to youtube (clamped index in H)
        assertEquals(youtube, stateMachine.lastContentFocus)

        // Down returns to repaired last content (youtube)
        assertTrue(stateMachine.handleDpad(DpadDirection.DOWN))
        assertEquals(youtube, stateMachine.currentFocus)
    }

    @Test
    fun `onInitialQueryFailed preserves user-operated tool focus`() {
        val stateMachine = HomeFocusStateMachine()
        stateMachine.onInitialLoading()
        assertEquals(FocusNode.Tool.Refresh, stateMachine.currentFocus)

        // User moves to Settings
        assertTrue(stateMachine.handleDpad(DpadDirection.RIGHT))
        assertEquals(FocusNode.Tool.Settings, stateMachine.currentFocus)
        assertTrue(stateMachine.hasUserInteracted)

        // Initial query fails; per AC-F03-01 / SC-055, active user focus is preserved
        stateMachine.onInitialQueryFailed()
        assertEquals(FocusNode.Tool.Settings, stateMachine.currentFocus)
    }

    // 9. SC-055: 首次載入後不得無故覆蓋使用者已操作的焦點 (AC-F03-01)
    @Test
    fun `initial snapshot update does not overwrite focus if user already interacted`() {
        // Given 首次查詢尚未完成，使用者已由重新整理移到系統設定
        val stateMachine = HomeFocusStateMachine()
        stateMachine.onInitialLoading()
        assertEquals(FocusNode.Tool.Refresh, stateMachine.currentFocus)

        assertTrue(stateMachine.handleDpad(DpadDirection.RIGHT))
        assertEquals(FocusNode.Tool.Settings, stateMachine.currentFocus)
        assertTrue(stateMachine.hasUserInteracted)

        // When 查詢成功回傳 YouTube, Netflix
        stateMachine.onSnapshotLoaded(listOf(youtube, netflix))

        // Then 唯一焦點仍在系統設定，不因首次結果抵達而跳到內容區 (SC-055)
        assertEquals(FocusNode.Tool.Settings, stateMachine.currentFocus)
    }
}
