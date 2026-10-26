package com.miller.tv.launcher.deviceinfo

import com.miller.tv.launcher.adapter.DrawerAppItemUiState
import com.miller.tv.launcher.adapter.HomeLauncherViewTarget
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.Instant
import java.time.ZoneId
import java.util.Locale

class DeviceInfoUiIntegrationTest {

    private class TestDeviceInfoTarget : HomeLauncherViewTarget {
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
        override fun bindDrawerApps(apps: List<DrawerAppItemUiState>) {}

        // F11 Text fields
        var timeText: String = ""
        var dateText: String = ""
        var networkText: String = ""
        var hdmiText: String = ""
        var storageText: String = ""

        override fun updateDeviceInfo(snapshot: DeviceInfoSnapshot) {
            timeText = when (val s = snapshot.timeState) {
                is InfoState.Available -> s.data
                is InfoState.Unknown -> "時間未知"
                is InfoState.Unavailable -> s.displayMessage
            }
            dateText = when (val s = snapshot.dateState) {
                is InfoState.Available -> s.data
                is InfoState.Unknown -> "日期未知"
                is InfoState.Unavailable -> s.displayMessage
            }
            networkText = when (val s = snapshot.networkState) {
                is InfoState.Available -> s.data.displayString
                is InfoState.Unknown -> "狀態未知"
                is InfoState.Unavailable -> s.displayMessage
            }
            hdmiText = when (val s = snapshot.hdmiState) {
                is InfoState.Available -> if (s.data.ports.isEmpty()) "無連接埠" else s.data.ports.joinToString { "${it.portId}:${it.isConnected}" }
                is InfoState.Unknown -> "狀態未知"
                is InfoState.Unavailable -> s.displayMessage
            }
            storageText = when (val s = snapshot.storageState) {
                is InfoState.Available -> s.data.displayString
                is InfoState.Unknown -> "狀態未知"
                is InfoState.Unavailable -> s.displayMessage
            }
        }
    }

    private class FakeSystemTimeProvider(
        var timeDateData: TimeDateData = TimeDateData("14:30", "10月26日 星期一")
    ) : SystemTimeProvider {
        var callCount = 0
        override fun getCurrentTimeAndDate(): TimeDateData {
            callCount++
            return timeDateData
        }
    }

    private class FakeNetworkInfoProvider(
        var state: InfoState<NetworkData> = InfoState.Available(NetworkData(NetworkType.WIFI, true, "Home-WiFi"))
    ) : NetworkInfoProvider {
        var callCount = 0
        override fun getNetworkInfo(): InfoState<NetworkData> {
            callCount++
            return state
        }
    }

    private class FakeHdmiInfoProvider(
        var state: InfoState<HdmiData> = InfoState.Unavailable(UnavailableReason.NOT_SUPPORTED)
    ) : HdmiInfoProvider {
        var callCount = 0
        override fun getHdmiInfo(): InfoState<HdmiData> {
            callCount++
            return state
        }
    }

    private class FakeStorageInfoProvider(
        var state: InfoState<StorageData> = InfoState.Available(StorageData(10_000_000_000L, 32_000_000_000L))
    ) : StorageInfoProvider {
        var callCount = 0
        override fun getStorageInfo(): InfoState<StorageData> {
            callCount++
            return state
        }
    }

    // 1. 五項資料正常展示
    @Test
    fun testAllFiveItemsDisplayNormally() {
        val target = TestDeviceInfoTarget()
        val timeProvider = FakeSystemTimeProvider(TimeDateData("20:45", "10月26日 星期一"))
        val netProvider = FakeNetworkInfoProvider(InfoState.Available(NetworkData(NetworkType.ETHERNET, true)))
        val hdmiProvider = FakeHdmiInfoProvider(InfoState.Unavailable(UnavailableReason.NOT_SUPPORTED))
        val storageProvider = FakeStorageInfoProvider(InfoState.Available(StorageData(24_800_000_000L, 32_000_000_000L)))

        val coordinator = DeviceInfoCoordinator(
            timeProvider = timeProvider,
            networkProvider = netProvider,
            hdmiProvider = hdmiProvider,
            storageProvider = storageProvider,
            target = target
        )

        coordinator.refreshAll()

        assertEquals("20:45", target.timeText)
        assertEquals("10月26日 星期一", target.dateText)
        assertEquals("乙太網路已連線", target.networkText)
        assertEquals("無法取得（不支援）", target.hdmiText)
        assertEquals("可用 24.8 GB／總計 32.0 GB (1 GB = 1,000,000,000 bytes)", target.storageText)
    }

    // 2. 獨立降級：單項失敗不影響其他資訊
    @Test
    fun testIndependentDegradation() {
        val target = TestDeviceInfoTarget()
        val coordinator = DeviceInfoCoordinator(
            timeProvider = FakeSystemTimeProvider(TimeDateData("09:00", "5月1日 星期五")),
            networkProvider = FakeNetworkInfoProvider(InfoState.Unavailable(UnavailableReason.PERMISSION_DENIED)),
            hdmiProvider = FakeHdmiInfoProvider(InfoState.Unavailable(UnavailableReason.NOT_SUPPORTED)),
            storageProvider = FakeStorageInfoProvider(InfoState.Unavailable(UnavailableReason.INVALID_DATA)),
            target = target
        )

        coordinator.refreshAll()

        assertEquals("09:00", target.timeText)
        assertEquals("5月1日 星期五", target.dateText)
        assertEquals("無法取得（權限不足）", target.networkText)
        assertEquals("無法取得（不支援）", target.hdmiText)
        assertEquals("無法取得（資料無效）", target.storageText)
    }

    // 3. 時間與日期使用同一個快照 (避免午夜跨日時間已到 00:00 但日期仍停留在前一天)
    @Test
    fun testTimeAndDateMidnightConsistency() {
        val target = TestDeviceInfoTarget()
        // 2026-10-26 23:59:59.999 UTC
        val justBeforeMidnight = Instant.parse("2026-10-26T23:59:59.999Z")
        val zone = ZoneId.of("UTC")
        val locale = Locale.TRADITIONAL_CHINESE

        val timeDate = DeviceInfoFormatter.formatTimeAndDate(
            instant = justBeforeMidnight,
            zoneId = zone,
            locale = locale,
            is24Hour = true
        )

        assertEquals("23:59", timeDate.formattedTime)
        assertEquals("10月26日 星期一", timeDate.formattedDateAndWeek)
    }

    // 4. 前景輪詢與背景暫停 (Lifecycle 安全性)
    @Test
    fun testLifecyclePollingControl() {
        val target = TestDeviceInfoTarget()
        val timeProvider = FakeSystemTimeProvider()
        val coordinator = DeviceInfoCoordinator(
            timeProvider = timeProvider,
            networkProvider = FakeNetworkInfoProvider(),
            hdmiProvider = FakeHdmiInfoProvider(),
            storageProvider = FakeStorageInfoProvider(),
            target = target
        )

        coordinator.refreshAll()
        assertEquals(1, timeProvider.callCount)

        // onPause 停止輪詢
        coordinator.onPause()
        assertFalse(coordinator.isPollingActive)

        // onResume 恢復並重新整理
        coordinator.onResume()
        assertTrue(coordinator.isPollingActive)
        assertEquals(2, timeProvider.callCount)

        // onDestroy 銷毀
        coordinator.destroy()
        assertFalse(coordinator.isPollingActive)
        assertTrue(coordinator.isDestroyed)
    }
}
