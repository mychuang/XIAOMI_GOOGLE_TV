package com.miller.tv.launcher

import android.app.Activity
import android.os.Bundle
import android.view.KeyEvent
import com.miller.tv.launcher.adapter.HomeFocusController
import com.miller.tv.launcher.adapter.HomeFocusViewBinder
import com.miller.tv.launcher.databinding.ActivityMainBinding
import com.miller.tv.launcher.deviceinfo.AndroidHdmiProvider
import com.miller.tv.launcher.deviceinfo.AndroidNetworkProvider
import com.miller.tv.launcher.deviceinfo.AndroidStorageProvider
import com.miller.tv.launcher.deviceinfo.AndroidTimeProvider
import com.miller.tv.launcher.deviceinfo.DeviceInfoCoordinator
import com.miller.tv.launcher.discovery.AndroidTvAppScanner
import com.miller.tv.launcher.discovery.AppDiscoveryCoordinator
import com.miller.tv.launcher.discovery.ContextAppLauncher
import com.miller.tv.launcher.discovery.TvAppCategorizer
import com.miller.tv.launcher.focus.HomeFocusStateMachine

/**
 * Main activity implementing Home A UI, Focus Adapter, App Discovery, and F11 Device Telemetry.
 */
class MainActivity : Activity() {

    lateinit var binding: ActivityMainBinding
        internal set

    lateinit var focusController: HomeFocusController
        internal set

    lateinit var discoveryCoordinator: AppDiscoveryCoordinator
        internal set

    lateinit var deviceInfoCoordinator: DeviceInfoCoordinator
        internal set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val binder = HomeFocusViewBinder(binding)
        val stateMachine = HomeFocusStateMachine()

        // 1. App Discovery & Launch
        val categorizer = TvAppCategorizer(ownPackageName = packageName)
        val scanner = AndroidTvAppScanner(
            packageManager = packageManager,
            categorizer = categorizer,
            hostPackageName = packageName
        )
        val launcher = ContextAppLauncher(this)

        discoveryCoordinator = AppDiscoveryCoordinator(
            scanner = scanner,
            launcher = launcher,
            stateMachine = stateMachine,
            viewBinder = binder
        )

        // 2. F11 Device Information Providers
        deviceInfoCoordinator = DeviceInfoCoordinator(
            timeProvider = AndroidTimeProvider(this),
            networkProvider = AndroidNetworkProvider(this),
            hdmiProvider = AndroidHdmiProvider(),
            storageProvider = AndroidStorageProvider(filesDir),
            target = binder.target,
            uiDispatcher = { runOnUiThread(it) }
        )

        // 3. Focus Controller
        focusController = HomeFocusController(
            stateMachine = stateMachine,
            viewBinder = binder,
            onSelectAction = { node -> discoveryCoordinator.handleSelect(node) },
            onExitLauncher = { finish() }
        )

        // Initial discovery scan on startup
        discoveryCoordinator.refresh()
    }

    override fun onResume() {
        super.onResume()
        if (::deviceInfoCoordinator.isInitialized) {
            deviceInfoCoordinator.onResume()
        }
    }

    override fun onPause() {
        if (::deviceInfoCoordinator.isInitialized) {
            deviceInfoCoordinator.onPause()
        }
        super.onPause()
    }

    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        if (::focusController.isInitialized && focusController.dispatchKeyEvent(event)) {
            return true
        }
        return super.dispatchKeyEvent(event)
    }

    override fun onDestroy() {
        if (::discoveryCoordinator.isInitialized) {
            discoveryCoordinator.destroy()
        }
        if (::deviceInfoCoordinator.isInitialized) {
            deviceInfoCoordinator.destroy()
        }
        super.onDestroy()
    }
}
