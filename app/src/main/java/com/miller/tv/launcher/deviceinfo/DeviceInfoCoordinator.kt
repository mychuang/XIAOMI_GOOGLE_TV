package com.miller.tv.launcher.deviceinfo

import com.miller.tv.launcher.adapter.HomeLauncherViewTarget
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

/**
 * Coordinator for F11 read-only device telemetry.
 * Manages periodic background polling, discrete state assembly, and UI dispatching.
 */
class DeviceInfoCoordinator(
    private val timeProvider: SystemTimeProvider,
    private val networkProvider: NetworkInfoProvider,
    private val hdmiProvider: HdmiInfoProvider,
    private val storageProvider: StorageInfoProvider,
    private val target: HomeLauncherViewTarget,
    private val policy: DeviceInfoRefreshPolicy = DeviceInfoRefreshPolicy(),
    private val uiDispatcher: (Runnable) -> Unit = { it.run() }
) {
    private var scheduler: ScheduledExecutorService? = null

    var isPollingActive: Boolean = false
        private set

    var isDestroyed: Boolean = false
        private set

    @Volatile
    private var lastSnapshot = DeviceInfoSnapshot()

    /**
     * Gathers all five items, builds an immutable snapshot, and dispatches to UI.
     */
    fun refreshAll() {
        if (isDestroyed) return

        val timeDate = timeProvider.getCurrentTimeAndDate()
        val netState = networkProvider.getNetworkInfo()
        val hdmiState = hdmiProvider.getHdmiInfo()
        val storageState = storageProvider.getStorageInfo()

        val snapshot = DeviceInfoSnapshot(
            timeState = InfoState.Available(timeDate.formattedTime),
            dateState = InfoState.Available(timeDate.formattedDateAndWeek),
            networkState = netState,
            hdmiState = hdmiState,
            storageState = storageState
        )
        lastSnapshot = snapshot

        uiDispatcher {
            if (!isDestroyed) {
                target.updateDeviceInfo(snapshot)
            }
        }
    }

    /**
     * Refreshes time and date only (single snapshot per minute boundary).
     */
    fun refreshTime() {
        if (isDestroyed) return
        val timeDate = timeProvider.getCurrentTimeAndDate()
        val snapshot = lastSnapshot.copy(
            timeState = InfoState.Available(timeDate.formattedTime),
            dateState = InfoState.Available(timeDate.formattedDateAndWeek)
        )
        lastSnapshot = snapshot
        uiDispatcher {
            if (!isDestroyed) {
                target.updateDeviceInfo(snapshot)
            }
        }
    }

    /**
     * Refreshes network state only.
     */
    fun refreshNetwork() {
        if (isDestroyed) return
        val netState = networkProvider.getNetworkInfo()
        val snapshot = lastSnapshot.copy(networkState = netState)
        lastSnapshot = snapshot
        uiDispatcher {
            if (!isDestroyed) {
                target.updateDeviceInfo(snapshot)
            }
        }
    }

    /**
     * Refreshes HDMI and storage states only.
     */
    fun refreshHdmiAndStorage() {
        if (isDestroyed) return
        val hdmiState = hdmiProvider.getHdmiInfo()
        val storageState = storageProvider.getStorageInfo()
        val snapshot = lastSnapshot.copy(
            hdmiState = hdmiState,
            storageState = storageState
        )
        lastSnapshot = snapshot
        uiDispatcher {
            if (!isDestroyed) {
                target.updateDeviceInfo(snapshot)
            }
        }
    }

    /**
     * Starts periodic polling when Launcher enters foreground.
     */
    fun onResume() {
        if (isDestroyed) return
        isPollingActive = true
        policy.onResume()

        // Immediate snapshot refresh on resume
        refreshAll()

        startScheduler()
    }

    /**
     * Stops polling when Launcher enters background.
     */
    fun onPause() {
        isPollingActive = false
        policy.onPause()
        stopScheduler()
    }

    /**
     * Permanently halts and disposes of coordinator resources.
     */
    fun destroy() {
        isDestroyed = true
        isPollingActive = false
        policy.onPause()
        stopScheduler()
    }

    @Synchronized
    private fun startScheduler() {
        if (scheduler != null && !scheduler!!.isShutdown) return
        val s = Executors.newSingleThreadScheduledExecutor()
        scheduler = s

        // Periodic time: 60s
        s.scheduleAtFixedRate(
            { refreshTime() },
            policy.timeRefreshIntervalMs,
            policy.timeRefreshIntervalMs,
            TimeUnit.MILLISECONDS
        )

        // Periodic network: 30s
        s.scheduleAtFixedRate(
            { refreshNetwork() },
            policy.networkRefreshIntervalMs,
            policy.networkRefreshIntervalMs,
            TimeUnit.MILLISECONDS
        )

        // Periodic HDMI and Storage: 60s
        s.scheduleAtFixedRate(
            { refreshHdmiAndStorage() },
            policy.storageRefreshIntervalMs,
            policy.storageRefreshIntervalMs,
            TimeUnit.MILLISECONDS
        )
    }

    @Synchronized
    private fun stopScheduler() {
        scheduler?.shutdownNow()
        scheduler = null
    }
}
