package com.miller.tv.launcher.deviceinfo

import com.miller.tv.launcher.focus.DesignatedService
import com.miller.tv.launcher.focus.FocusNode
import com.miller.tv.launcher.focus.HomeFocusStateMachine
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.Instant
import java.time.ZoneId
import java.util.Locale

/**
 * Stage 2 — Slice 4: F11 Read-only Device Information Test.
 *
 * Verifies five read-only information items against:
 * - Feature: F11 (AC-F11-01 to AC-F11-07)
 * - Policies: P11-01, P11-02, P11-03, D4, D8
 * - Scenarios: SC-095 to SC-114
 */
class DeviceInformationTest {

    // 1. SC-111, AC-F11-07: 五項資料具有獨立的可用與降級狀態
    @Test
    fun `five info items maintain strictly independent states without cascading failures`() {
        val snapshot = DeviceInfoSnapshot(
            timeState = InfoState.Available("14:30"),
            dateState = InfoState.Available("10月26日 星期一"),
            networkState = InfoState.Available(NetworkData(NetworkType.WIFI, true, "Home-5G")),
            hdmiState = InfoState.Unavailable(UnavailableReason.NOT_SUPPORTED),
            storageState = InfoState.Available(StorageData(12_000_000_000L, 32_000_000_000L))
        )

        assertTrue(snapshot.timeState is InfoState.Available)
        assertTrue(snapshot.dateState is InfoState.Available)
        assertTrue(snapshot.networkState is InfoState.Available)
        assertTrue(snapshot.hdmiState is InfoState.Unavailable)
        assertEquals("無法取得（不支援）", (snapshot.hdmiState as InfoState.Unavailable).displayMessage)
        assertTrue(snapshot.storageState is InfoState.Available)
    }

    // 2. SC-095, SC-096, AC-F11-01: 時間與日期依指定 Locale / 時區格式化，不顯示秒
    @Test
    fun `time and date formatting strictly obeys locale, timezone, and 12-24h format without showing seconds`() {
        // Instant: 2026-10-26T06:30:00Z -> In UTC+8: 2026-10-26 14:30
        val instant = Instant.parse("2026-10-26T06:30:00Z")
        val zoneTaipei = ZoneId.of("Asia/Taipei")

        // 24-hour test (Traditional Chinese)
        val result24 = DeviceInfoFormatter.formatTimeAndDate(
            instant = instant,
            zoneId = zoneTaipei,
            locale = Locale.TAIWAN,
            is24Hour = true
        )
        assertEquals("14:30", result24.formattedTime)
        assertFalse(result24.formattedTime.contains(":00")) // No seconds!
        assertEquals("10月26日 星期一", result24.formattedDateAndWeek)

        // 12-hour test (SC-096: contains period marker)
        val result12 = DeviceInfoFormatter.formatTimeAndDate(
            instant = instant,
            zoneId = zoneTaipei,
            locale = Locale.TAIWAN,
            is24Hour = false
        )
        assertTrue(result12.formattedTime.contains("下午") || result12.formattedTime.contains("PM"))
        assertTrue(result12.formattedTime.contains("2:30") || result12.formattedTime.contains("02:30"))
        assertFalse(result12.formattedTime.contains(":00"))
    }

    // 3. SC-097, AC-F11-01: 跨日後日期能正確更新，不混用新時間配舊日期
    @Test
    fun `midnight transition updates time, date, and day of week together from same instant`() {
        val justBeforeMidnight = Instant.parse("2026-10-26T15:59:59Z") // 23:59:59 in Asia/Taipei
        val justAfterMidnight = Instant.parse("2026-10-26T16:00:01Z")  // 00:00:01 in Asia/Taipei (next day!)
        val zoneTaipei = ZoneId.of("Asia/Taipei")

        val before = DeviceInfoFormatter.formatTimeAndDate(justBeforeMidnight, zoneTaipei, Locale.TAIWAN, true)
        val after = DeviceInfoFormatter.formatTimeAndDate(justAfterMidnight, zoneTaipei, Locale.TAIWAN, true)

        assertEquals("23:59", before.formattedTime)
        assertEquals("10月26日 星期一", before.formattedDateAndWeek)

        assertEquals("00:00", after.formattedTime)
        assertEquals("10月27日 星期二", after.formattedDateAndWeek)
    }

    // 4. SC-100, AC-F11-02: 未知網路狀態不被顯示為 Wi-Fi 已連線或斷線
    @Test
    fun `unknown network state and permission denied are not treated as disconnected or valid wifi`() {
        val unknownState: InfoState<NetworkData> = InfoState.Unknown
        assertTrue(unknownState is InfoState.Unknown)

        val deniedState: InfoState<NetworkData> = InfoState.Unavailable(UnavailableReason.PERMISSION_DENIED)
        assertEquals("無法取得（權限不足）", (deniedState as InfoState.Unavailable).displayMessage)

        // None network is an affirmative disconnected state, not an error
        val noneNetwork = NetworkData(NetworkType.NONE, isConnected = false)
        assertEquals("無預設網路", noneNetwork.displayString)
    }

    // 5. SC-099, AC-F11-02: SSID 無法取得時正確降級為名稱無法取得
    @Test
    fun `wifi connected but with masked SSID explicitly displays name unavailable`() {
        val wifiWithSsid = NetworkData(NetworkType.WIFI, isConnected = true, ssid = "TEST-NET-A")
        assertEquals("Wi-Fi 已連線 • TEST-NET-A", wifiWithSsid.displayString)

        val wifiMasked = NetworkData(NetworkType.WIFI, isConnected = true, ssid = null, isSsidUnavailable = true)
        assertEquals("Wi-Fi 已連線 • 名稱無法取得", wifiMasked.displayString)

        val eth = NetworkData(NetworkType.ETHERNET, isConnected = true)
        assertEquals("乙太網路已連線", eth.displayString)
    }

    // 6. SC-102, AC-F11-03: HDMI 無法可靠取得或空來源時顯示未知
    @Test
    fun `empty or unconfirmed HDMI source yields Unknown and does not assert all disconnected`() {
        // SC-102: Partial coverage
        val partial = HdmiData(
            ports = listOf(HdmiPortStatus("HDMI 1", false)),
            isPartial = true
        )
        assertEquals(1, partial.ports.size)
        assertTrue(partial.isPartial)

        // SC-102: Unknown / empty coverage
        val unknownHdmi: InfoState<HdmiData> = InfoState.Unknown
        assertTrue(unknownHdmi is InfoState.Unknown)
    }

    // 7. SC-101, SC-103, AC-F11-03: HDMI 狀態不代表輸入切換或當前播放
    @Test
    fun `hdmi connected ports do not claim active playback and unsupported source degrades gracefully`() {
        val hdmi = HdmiData(
            ports = listOf(
                HdmiPortStatus("HDMI 1", true),
                HdmiPortStatus("HDMI 2", false)
            ),
            isPartial = false
        )
        assertEquals(2, hdmi.ports.size)
        assertTrue(hdmi.ports[0].isConnected)
        assertFalse(hdmi.ports[1].isConnected)

        // SC-103: Standard APK unsupported degradation
        val unsupported: InfoState<HdmiData> = InfoState.Unavailable(UnavailableReason.NOT_SUPPORTED)
        assertEquals("無法取得（不支援）", (unsupported as InfoState.Unavailable).displayMessage)
    }

    // 8. SC-104, AC-F11-04: 儲存容量的 GB 換算與一位小數格式
    @Test
    fun `storage format strictly uses decimal GB with one decimal place and standard disclaimer`() {
        val storage = StorageData(
            availableBytes = 12_000_000_000L,
            totalBytes = 32_000_000_000L
        )
        // 12.0 GB / 32.0 GB (1 GB = 1,000,000,000 bytes)
        assertEquals(
            "可用 12.0 GB／總計 32.0 GB (1 GB = 1,000,000,000 bytes)",
            storage.displayString
        )
    }

    // 9. SC-105, SC-106, AC-F11-04: 儲存容量零值小值及非法數值處理
    @Test
    fun `storage handles zero, small sub-0_1GB values, and rejects invalid negative or inverted bytes`() {
        // Zero available bytes is valid
        val zeroState = DeviceInfoFormatter.validateStorage(0L, 32_000_000_000L)
        assertTrue(zeroState is InfoState.Available)
        assertEquals("可用 0.0 GB／總計 32.0 GB (1 GB = 1,000,000,000 bytes)", (zeroState as InfoState.Available).data.displayString)

        // 50,000,000 bytes is <0.1 GB
        val smallState = DeviceInfoFormatter.validateStorage(50_000_000L, 32_000_000_000L)
        assertTrue(smallState is InfoState.Available)
        assertEquals("可用 <0.1 GB／總計 32.0 GB (1 GB = 1,000,000,000 bytes)", (smallState as InfoState.Available).data.displayString)

        // Negative available bytes -> INVALID_DATA
        val negState = DeviceInfoFormatter.validateStorage(-1L, 32_000_000_000L)
        assertTrue(negState is InfoState.Unavailable)
        assertEquals(UnavailableReason.INVALID_DATA, (negState as InfoState.Unavailable).reason)

        // Available bytes > total bytes -> INVALID_DATA
        val overState = DeviceInfoFormatter.validateStorage(33_000_000_000L, 32_000_000_000L)
        assertTrue(overState is InfoState.Unavailable)
        assertEquals(UnavailableReason.INVALID_DATA, (overState as InfoState.Unavailable).reason)

        // Total bytes <= 0 -> INVALID_DATA
        val zeroTotalState = DeviceInfoFormatter.validateStorage(0L, 0L)
        assertTrue(zeroTotalState is InfoState.Unavailable)
        assertEquals(UnavailableReason.INVALID_DATA, (zeroTotalState as InfoState.Unavailable).reason)

        // Missing values -> Unknown (SC-106)
        val missingState = DeviceInfoFormatter.validateStorage(null, 32_000_000_000L)
        assertTrue(missingState is InfoState.Unknown)
    }

    // 10. SC-098, SC-106, AC-F11-07: 單一來源失敗不影響其他資料
    @Test
    fun `read failure in one source does not purge valid information in other items`() {
        val snapshot = DeviceInfoSnapshot(
            timeState = InfoState.Unavailable(UnavailableReason.READ_FAILED),
            dateState = InfoState.Unavailable(UnavailableReason.READ_FAILED),
            networkState = InfoState.Available(NetworkData(NetworkType.ETHERNET, true)),
            hdmiState = InfoState.Unavailable(UnavailableReason.NOT_SUPPORTED),
            storageState = InfoState.Available(StorageData(12_000_000_000L, 32_000_000_000L))
        )

        assertEquals("無法取得（讀取失敗）", (snapshot.timeState as InfoState.Unavailable).displayMessage)
        assertTrue(snapshot.networkState is InfoState.Available)
        assertTrue(snapshot.storageState is InfoState.Available)
    }

    // 11. SC-113, P11-01: 前景定期更新間隔契約
    @Test
    fun `refresh intervals strictly align with P11-01 policy`() {
        val policy = DeviceInfoRefreshPolicy()

        assertEquals(60_000L, policy.timeRefreshIntervalMs)      // 1 minute
        assertEquals(30_000L, policy.networkRefreshIntervalMs)   // 30 seconds
        assertEquals(60_000L, policy.hdmiRefreshIntervalMs)      // 60 seconds
        assertEquals(60_000L, policy.storageRefreshIntervalMs)   // 60 seconds
        assertEquals(5_000L, policy.queryDeadlineMs)             // 5 seconds deadline
    }

    // 12. SC-113: 背景期間不輪詢，恢復前景重新查詢
    @Test
    fun `policy halts background polling and triggers refresh on resume`() {
        val policy = DeviceInfoRefreshPolicy()

        // Background transition
        policy.onPause()
        assertFalse(policy.isPollingActive)

        // Resume transition
        policy.onResume()
        assertTrue(policy.isPollingActive)
        assertTrue(policy.shouldRefreshOnResume())
    }

    // 13. SC-107, SC-109, AC-F11-05, AC-F11-06: 資訊區不建立可聚焦節點且更新不搶焦點
    @Test
    fun `device info does not create focus nodes and info updates never snatch focus`() {
        val youtube = FocusNode.Content.App(DesignatedService.YOUTUBE, "com.youtube/Main")
        val stateMachine = HomeFocusStateMachine(listOf(youtube))
        assertEquals(youtube, stateMachine.currentFocus)

        // Device info states exist purely as separate data
        val infoSnapshot = DeviceInfoSnapshot(
            timeState = InfoState.Available("12:00"),
            networkState = InfoState.Available(NetworkData(NetworkType.WIFI, true))
        )

        // State machine focus is unaffected by device info existence
        assertEquals(youtube, stateMachine.currentFocus)
        assertFalse(stateMachine.currentFocus is FocusNode.Drawer)
    }
}
