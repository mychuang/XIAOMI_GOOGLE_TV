package com.miller.tv.launcher

import android.app.Activity
import android.os.Bundle
import android.view.KeyEvent
import com.miller.tv.launcher.adapter.HomeFocusController
import com.miller.tv.launcher.adapter.HomeFocusViewBinder
import com.miller.tv.launcher.databinding.ActivityMainBinding
import com.miller.tv.launcher.discovery.AndroidTvAppScanner
import com.miller.tv.launcher.discovery.AppDiscoveryCoordinator
import com.miller.tv.launcher.discovery.ContextAppLauncher
import com.miller.tv.launcher.discovery.TvAppCategorizer
import com.miller.tv.launcher.focus.HomeFocusStateMachine

/**
 * Main activity implementing Home A UI, Focus Adapter, App Discovery and Launch Integration.
 */
class MainActivity : Activity() {

    lateinit var binding: ActivityMainBinding
        internal set

    lateinit var focusController: HomeFocusController
        internal set

    lateinit var discoveryCoordinator: AppDiscoveryCoordinator
        internal set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val binder = HomeFocusViewBinder(binding)
        val stateMachine = HomeFocusStateMachine()

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

        focusController = HomeFocusController(
            stateMachine = stateMachine,
            viewBinder = binder,
            onSelectAction = { node -> discoveryCoordinator.handleSelect(node) },
            onExitLauncher = { finish() }
        )

        // Perform initial discovery scan on startup
        discoveryCoordinator.refresh()
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
        super.onDestroy()
    }
}
