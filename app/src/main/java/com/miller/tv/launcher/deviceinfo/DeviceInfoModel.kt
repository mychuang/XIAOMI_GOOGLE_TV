package com.miller.tv.launcher.deviceinfo

import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

/**
 * Reason for info unavailability per F11 spec.
 * Strictly limited to: NOT_SUPPORTED, PERMISSION_DENIED, READ_FAILED, INVALID_DATA, TIMEOUT.
 */
enum class UnavailableReason(val displayReason: String) {
    NOT_SUPPORTED("不支援"),
    PERMISSION_DENIED("權限不足"),
    READ_FAILED("讀取失敗"),
    INVALID_DATA("資料無效"),
    TIMEOUT("逾時")
}

/**
 * Universal discrete state representation for an information item per F11.
 */
sealed interface InfoState<out T> {
    data class Available<T>(val data: T) : InfoState<T>
    data object Unknown : InfoState<Nothing>
    data class Unavailable(val reason: UnavailableReason) : InfoState<Nothing> {
        val displayMessage: String
            get() = "無法取得（${reason.displayReason}）"
    }
}

/**
 * 1. Time & Date Data Models
 */
data class TimeDateData(
    val formattedTime: String,
    val formattedDateAndWeek: String
)

/**
 * 2. Network Data Models
 */
enum class NetworkType(val displayName: String) {
    WIFI("Wi-Fi"),
    ETHERNET("乙太網路"),
    OTHER("其他網路"),
    NONE("無預設網路")
}

data class NetworkData(
    val type: NetworkType,
    val isConnected: Boolean,
    val ssid: String? = null,
    val isSsidUnavailable: Boolean = false
) {
    val displayString: String
        get() = when {
            type == NetworkType.NONE -> "無預設網路"
            type == NetworkType.WIFI -> {
                if (isConnected) {
                    when {
                        !ssid.isNullOrBlank() -> "Wi-Fi 已連線 • $ssid"
                        isSsidUnavailable -> "Wi-Fi 已連線 • 名稱無法取得"
                        else -> "Wi-Fi 已連線"
                    }
                } else {
                    "Wi-Fi 未連線"
                }
            }
            type == NetworkType.ETHERNET -> if (isConnected) "乙太網路已連線" else "乙太網路未連線"
            else -> if (isConnected) "其他網路已連線" else "未連線"
        }
}

/**
 * 3. HDMI Data Models
 */
data class HdmiPortStatus(
    val portId: String,
    val isConnected: Boolean
)

data class HdmiData(
    val ports: List<HdmiPortStatus>,
    val isPartial: Boolean = false
)

/**
 * 4. Internal Storage Data Models (P11-03)
 */
data class StorageData(
    val availableBytes: Long,
    val totalBytes: Long
) {
    companion object {
        const val BYTES_PER_GB = 1_000_000_000.0 // Decimal GB per P11-03
    }

    val displayString: String
        get() = "可用 ${formatBytes(availableBytes)}／總計 ${formatBytes(totalBytes)} (1 GB = 1,000,000,000 bytes)"

    private fun formatBytes(bytes: Long): String {
        val gb = bytes / BYTES_PER_GB
        return if (bytes in 1..99_999_999) {
            "<0.1 GB"
        } else {
            String.format(Locale.ROOT, "%.1f GB", gb)
        }
    }
}

/**
 * Composite immutable state of all five read-only device information items.
 */
data class DeviceInfoSnapshot(
    val timeState: InfoState<String> = InfoState.Unknown,
    val dateState: InfoState<String> = InfoState.Unknown,
    val networkState: InfoState<NetworkData> = InfoState.Unknown,
    val hdmiState: InfoState<HdmiData> = InfoState.Unknown,
    val storageState: InfoState<StorageData> = InfoState.Unknown
)

/**
 * Pure Kotlin formatter and domain validator for F11 device information.
 */
object DeviceInfoFormatter {

    /**
     * Formats system time and date from a single instant and zoneId snapshot (AC-F11-01 / SC-095).
     * No seconds displayed.
     */
    fun formatTimeAndDate(
        instant: Instant,
        zoneId: ZoneId,
        locale: Locale,
        is24Hour: Boolean
    ): TimeDateData {
        val zonedDateTime = ZonedDateTime.ofInstant(instant, zoneId)

        val timePattern = if (is24Hour) {
            "HH:mm"
        } else {
            // Check if Chinese or Asian locale
            if (locale.language == "zh") {
                "a h:mm"
            } else {
                "h:mm a"
            }
        }

        val timeFormatter = DateTimeFormatter.ofPattern(timePattern, locale)
        val formattedTime = zonedDateTime.format(timeFormatter)

        val datePattern = if (locale.language == "zh") {
            "M月d日 EEEE"
        } else {
            "EEEE, MMMM d"
        }
        val dateFormatter = DateTimeFormatter.ofPattern(datePattern, locale)
        val formattedDateAndWeek = zonedDateTime.format(dateFormatter)

        return TimeDateData(
            formattedTime = formattedTime,
            formattedDateAndWeek = formattedDateAndWeek
        )
    }

    /**
     * Validates storage byte values according to P11-03 and F11:
     * - availableBytes in 0..totalBytes
     * - totalBytes > 0
     * - nulls yield Unknown
     * - illegal values yield Unavailable(INVALID_DATA)
     */
    fun validateStorage(
        availableBytes: Long?,
        totalBytes: Long?
    ): InfoState<StorageData> {
        if (availableBytes == null || totalBytes == null) {
            return InfoState.Unknown
        }
        if (totalBytes <= 0L || availableBytes < 0L || availableBytes > totalBytes) {
            return InfoState.Unavailable(UnavailableReason.INVALID_DATA)
        }
        return InfoState.Available(StorageData(availableBytes, totalBytes))
    }
}

/**
 * Refresh schedule policy aligned with P11-01.
 */
class DeviceInfoRefreshPolicy {
    val timeRefreshIntervalMs: Long = 60_000L
    val networkRefreshIntervalMs: Long = 30_000L
    val hdmiRefreshIntervalMs: Long = 60_000L
    val storageRefreshIntervalMs: Long = 60_000L
    val queryDeadlineMs: Long = 5_000L

    var isPollingActive: Boolean = true
        private set

    fun onPause() {
        isPollingActive = false
    }

    fun onResume() {
        isPollingActive = true
    }

    fun shouldRefreshOnResume(): Boolean = true
}
