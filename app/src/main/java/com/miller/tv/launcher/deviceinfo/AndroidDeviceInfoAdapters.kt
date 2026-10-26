package com.miller.tv.launcher.deviceinfo

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.StatFs
import android.text.format.DateFormat
import android.util.Log
import java.io.File
import java.time.Instant
import java.time.ZoneId
import java.util.Locale

/**
 * Android platform adapters for F11 read-only device telemetry.
 */

interface SystemTimeProvider {
    fun getCurrentTimeAndDate(): TimeDateData
}

class AndroidTimeProvider(
    private val context: Context
) : SystemTimeProvider {
    override fun getCurrentTimeAndDate(): TimeDateData {
        val now = Instant.now()
        val zoneId = ZoneId.systemDefault()
        val locale = Locale.getDefault()
        val is24Hour = DateFormat.is24HourFormat(context)

        return DeviceInfoFormatter.formatTimeAndDate(
            instant = now,
            zoneId = zoneId,
            locale = locale,
            is24Hour = is24Hour
        )
    }
}

interface NetworkInfoProvider {
    fun getNetworkInfo(): InfoState<NetworkData>
}

class AndroidNetworkProvider(
    private val context: Context
) : NetworkInfoProvider {

    companion object {
        private const val TAG = "NetworkProvider"
    }

    override fun getNetworkInfo(): InfoState<NetworkData> {
        return try {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
                ?: return InfoState.Unavailable(UnavailableReason.READ_FAILED)

            val activeNetwork = cm.activeNetwork
                ?: return InfoState.Available(NetworkData(NetworkType.NONE, isConnected = false))

            val capabilities = cm.getNetworkCapabilities(activeNetwork)
                ?: return InfoState.Available(NetworkData(NetworkType.NONE, isConnected = false))

            val isWifi = capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
            val isEthernet = capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)

            when {
                isWifi -> {
                    // In Android 11, reading SSID requires ACCESS_FINE_LOCATION.
                    // Per P11-02 and AC-F11-02, we do NOT request sensitive location permissions;
                    // therefore SSID is safely degraded to "名稱無法取得".
                    InfoState.Available(
                        NetworkData(
                            type = NetworkType.WIFI,
                            isConnected = true,
                            ssid = null,
                            isSsidUnavailable = true
                        )
                    )
                }
                isEthernet -> {
                    InfoState.Available(
                        NetworkData(
                            type = NetworkType.ETHERNET,
                            isConnected = true
                        )
                    )
                }
                else -> {
                    InfoState.Available(
                        NetworkData(
                            type = NetworkType.OTHER,
                            isConnected = true
                        )
                    )
                }
            }
        } catch (e: SecurityException) {
            Log.w(TAG, "Access network state permission denied", e)
            InfoState.Unavailable(UnavailableReason.PERMISSION_DENIED)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to query network info", e)
            InfoState.Unavailable(UnavailableReason.READ_FAILED)
        }
    }
}

interface HdmiInfoProvider {
    fun getHdmiInfo(): InfoState<HdmiData>
}

class AndroidHdmiProvider : HdmiInfoProvider {
    /**
     * Standard non-system APKs without OEM private APIs cannot reliably query HDMI port state.
     * Per AC-F11-03 / SC-103, gracefully degrades to Unavailable(NOT_SUPPORTED).
     */
    override fun getHdmiInfo(): InfoState<HdmiData> {
        return InfoState.Unavailable(UnavailableReason.NOT_SUPPORTED)
    }
}

interface StorageInfoProvider {
    fun getStorageInfo(): InfoState<StorageData>
}

class AndroidStorageProvider(
    private val dataDir: File
) : StorageInfoProvider {

    companion object {
        private const val TAG = "StorageProvider"
    }

    override fun getStorageInfo(): InfoState<StorageData> {
        return try {
            val stat = StatFs(dataDir.absolutePath)
            val availableBytes = stat.availableBytes
            val totalBytes = stat.totalBytes

            DeviceInfoFormatter.validateStorage(availableBytes, totalBytes)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to read internal storage stats", e)
            InfoState.Unavailable(UnavailableReason.READ_FAILED)
        }
    }
}
