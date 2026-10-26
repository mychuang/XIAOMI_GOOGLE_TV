package com.miller.tv.launcher.discovery

import com.miller.tv.launcher.adapter.HomeFocusViewBinder
import com.miller.tv.launcher.focus.FocusNode
import com.miller.tv.launcher.focus.HomeFocusStateMachine

/**
 * Coordinator bridging TV application scanning, atomic snapshot synchronization,
 * focus state machine updates, and external application launches.
 */
class AppDiscoveryCoordinator(
    private val scanner: TvAppScanner,
    private val launcher: AppLauncher,
    private val stateMachine: HomeFocusStateMachine,
    private val viewBinder: HomeFocusViewBinder? = null,
    private val onSnapshotLoadedCallback: ((AppSnapshot) -> Unit)? = null
) {
    private var latestSnapshotId: Long = 0L
    private var isRefreshing: Boolean = false
    private var isDestroyed: Boolean = false

    /**
     * Triggers a TV application discovery scan and updates the focus domain.
     * Returns true if the scan succeeded and was submitted, false otherwise.
     */
    fun refresh(): Boolean {
        if (isDestroyed) return false
        if (isRefreshing) return false

        isRefreshing = true
        return try {
            val nextId = latestSnapshotId + 1
            val result = scanner.scanTvApps(nextId)
            if (result.isSuccess) {
                val snapshot = result.getOrThrow()
                onSnapshotArrived(snapshot)
            } else {
                // Spec: On refresh failure, keep existing snapshot and focus without wiping
                false
            }
        } finally {
            isRefreshing = false
        }
    }

    /**
     * Atomically submits an incoming AppSnapshot to the HomeFocusStateMachine and ViewBinder.
     * Rejects out-of-order or stale snapshots (snapshotId <= latestSnapshotId).
     */
    fun onSnapshotArrived(snapshot: AppSnapshot): Boolean {
        if (isDestroyed) return false
        if (snapshot.snapshotId <= latestSnapshotId) {
            return false // Stale snapshot; reject
        }

        latestSnapshotId = snapshot.snapshotId

        // Map domain H apps (only designated services with verified identity)
        val hNodes = snapshot.hApps.mapNotNull { tvApp ->
            tvApp.service?.let { service ->
                FocusNode.Content.App(service = service, componentKey = tvApp.componentKey)
            }
        }

        // Map domain O apps
        val oNodes = snapshot.oApps.map { tvApp ->
            FocusNode.Drawer.App(componentKey = tvApp.componentKey)
        }

        // Atomic submission to state machine
        stateMachine.onSnapshotLoaded(hNodes)
        stateMachine.onDrawerSnapshotLoaded(oNodes)

        // Propagate user-readable application labels
        val labels = (snapshot.hApps + snapshot.oApps).associate { it.componentKey to it.displayName }
        viewBinder?.updateAppLabels(labels)

        // Sync view binder
        viewBinder?.syncState(stateMachine.state)
        onSnapshotLoadedCallback?.invoke(snapshot)
        return true
    }

    /**
     * Handles activation (Select / OK) on a focus node.
     */
    fun handleSelect(node: FocusNode): Boolean {
        return when (node) {
            is FocusNode.Content.App -> {
                launchApp(node.componentKey).isSuccess
            }
            is FocusNode.Drawer.App -> {
                launchApp(node.componentKey).isSuccess
            }
            FocusNode.Tool.Refresh -> {
                refresh()
            }
            FocusNode.Tool.Settings -> {
                // System settings launch is pending future stage; keep safe no-op
                false
            }
            else -> false
        }
    }

    /**
     * Launches external application via explicit component key safely without crashing.
     */
    fun launchApp(componentKey: String): Result<Unit> {
        return launcher.launchApp(componentKey)
    }

    /**
     * Marks the coordinator as destroyed to prevent leaking or updating destroyed views.
     */
    fun destroy() {
        isDestroyed = true
    }
}
