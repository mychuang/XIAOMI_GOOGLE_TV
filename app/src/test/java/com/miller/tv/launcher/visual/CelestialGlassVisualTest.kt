package com.miller.tv.launcher.visual

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import com.miller.tv.launcher.adapter.DrawerAppItemUiState
import com.miller.tv.launcher.adapter.HomeFocusViewBinder
import com.miller.tv.launcher.adapter.HomeLauncherViewTarget
import com.miller.tv.launcher.deviceinfo.DeviceInfoSnapshot
import com.miller.tv.launcher.focus.DesignatedService
import com.miller.tv.launcher.focus.FocusNode
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class CelestialGlassVisualTest {

    private class VisualVerificationTarget : HomeLauncherViewTarget {
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

        var drawerItems: List<DrawerAppItemUiState> = emptyList()
        var appIcons: MutableMap<String, Drawable> = mutableMapOf()

        override fun bindDrawerApps(apps: List<DrawerAppItemUiState>) {
            this.drawerItems = apps
        }

        override fun updateDeviceInfo(snapshot: DeviceInfoSnapshot) {}

        override fun bindAppIcon(componentKey: String, icon: Drawable?) {
            if (icon != null) {
                appIcons[componentKey] = icon
            }
        }
    }

    // 1. Ten-Foot Celestial Glass Tokens: 焦點目標縮放必須恰為 1.06x，非焦點 1.0f
    @Test
    fun testFocusScaleTokens() {
        val target = VisualVerificationTarget()
        val binder = HomeFocusViewBinder(target)

        assertEquals(1.06f, HomeFocusViewBinder.FOCUSED_SCALE, 0.0001f)
        assertEquals(1.0f, HomeFocusViewBinder.DEFAULT_SCALE, 0.0001f)

        // 套用 YouTube 焦點
        binder.bindHomeApps(listOf(FocusNode.Content.App(DesignatedService.YOUTUBE, "com.google.android.youtube.tv/Entry")))
        binder.applyFocus(FocusNode.Content.App(DesignatedService.YOUTUBE, "com.google.android.youtube.tv/Entry"))

        assertTrue("YouTube must be selected", target.isYoutubeSelected)
        assertEquals("YouTube focus scale must be 1.06x", 1.06f, target.youtubeScale, 0.0001f)
        assertEquals("MoreApps scale must be 1.0x", 1.0f, target.moreAppsScale, 0.0001f)
    }

    // 2. Drawer 開啟時，背景視圖全面重置為非選中且縮放為 1.0x (背景焦點徹底隔離)
    @Test
    fun testDrawerVisualBackgroundIsolation() {
        val target = VisualVerificationTarget()
        val binder = HomeFocusViewBinder(target)

        val yt = FocusNode.Content.App(DesignatedService.YOUTUBE, "com.google.android.youtube.tv/Entry")
        binder.bindHomeApps(listOf(yt))

        // 在首頁聚焦在 MoreApps
        binder.applyFocus(FocusNode.Content.MoreApps)
        assertTrue(target.isMoreAppsSelected)
        assertEquals(1.06f, target.moreAppsScale, 0.001f)

        // 開啟 Drawer，焦點轉入 Drawer
        binder.updateDrawerVisibility(true)
        binder.applyFocus(FocusNode.Drawer.Close)

        assertTrue("Drawer must be visible", target.isDrawerVisible)
        assertTrue("Drawer Close must be focused", target.isDrawerCloseSelected)
        assertEquals(1.06f, target.drawerCloseScale, 0.001f)

        // 驗證背景元件全部無焦點且無縮放
        assertFalse("Background MoreApps must lose selection", target.isMoreAppsSelected)
        assertEquals(1.0f, target.moreAppsScale, 0.001f)
        assertFalse("Background YouTube must lose selection", target.isYoutubeSelected)
        assertEquals(1.0f, target.youtubeScale, 0.001f)
        assertFalse("Background Refresh must lose selection", target.isRefreshSelected)
        assertEquals(1.0f, target.refreshScale, 0.001f)
        assertFalse("Background Settings must lose selection", target.isSettingsSelected)
        assertEquals(1.0f, target.settingsScale, 0.001f)
    }

    // 3. H=0, 1, 2, 3 卡片可見性與缺項壓縮，More Apps 永遠存在
    @Test
    fun testHomeRowCardVisibilityAndFixedOrder() {
        val target = VisualVerificationTarget()
        val binder = HomeFocusViewBinder(target)

        // H=0
        binder.bindHomeApps(emptyList())
        assertFalse(target.isYoutubeVisible)
        assertFalse(target.isNetflixVisible)
        assertFalse(target.isDisneyPlusVisible)
        assertTrue("More Apps must always be visible", target.isMoreAppsVisible)

        // H=1 (只有 Disney+)
        binder.bindHomeApps(listOf(FocusNode.Content.App(DesignatedService.DISNEY_PLUS, "com.disney.disneyplus/Entry")))
        assertFalse(target.isYoutubeVisible)
        assertFalse(target.isNetflixVisible)
        assertTrue(target.isDisneyPlusVisible)
        assertTrue(target.isMoreAppsVisible)

        // H=3 (YouTube -> Netflix -> Disney+)
        binder.bindHomeApps(listOf(
            FocusNode.Content.App(DesignatedService.YOUTUBE, "com.google.android.youtube.tv/Entry"),
            FocusNode.Content.App(DesignatedService.NETFLIX, "com.netflix.ninja/Entry"),
            FocusNode.Content.App(DesignatedService.DISNEY_PLUS, "com.disney.disneyplus/Entry")
        ))
        assertTrue(target.isYoutubeVisible)
        assertTrue(target.isNetflixVisible)
        assertTrue(target.isDisneyPlusVisible)
        assertTrue(target.isMoreAppsVisible)
    }

    // 4. App Icon 安全載入與綁定
    @Test
    fun testAppIconBindingAndSafeFallback() {
        val target = VisualVerificationTarget()
        val dummyDrawable = ColorDrawable(0xFF00E5FF.toInt())

        target.bindAppIcon("com.google.android.youtube.tv/Entry", dummyDrawable)
        assertNotNull(target.appIcons["com.google.android.youtube.tv/Entry"])

        // 載入 null 時不覆寫或安全忽略
        target.bindAppIcon("com.unknown.app/Entry", null)
        assertFalse(target.appIcons.containsKey("com.unknown.app/Entry"))
    }
}
