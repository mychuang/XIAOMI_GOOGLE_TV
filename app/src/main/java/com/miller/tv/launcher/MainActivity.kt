package com.miller.tv.launcher

import android.app.Activity
import android.os.Bundle
import android.view.KeyEvent
import com.miller.tv.launcher.adapter.HomeFocusController
import com.miller.tv.launcher.adapter.HomeFocusViewBinder
import com.miller.tv.launcher.databinding.ActivityMainBinding
import com.miller.tv.launcher.focus.HomeFocusStateMachine

/**
 * Main activity implementing Home A UI and Focus Adapter integration for Xiaomi Google TV Launcher.
 */
class MainActivity : Activity() {

    lateinit var binding: ActivityMainBinding
        internal set

    lateinit var focusController: HomeFocusController
        internal set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val binder = HomeFocusViewBinder(binding)
        val stateMachine = HomeFocusStateMachine()
        focusController = HomeFocusController(
            stateMachine = stateMachine,
            viewBinder = binder,
            onExitLauncher = { finish() }
        )
    }

    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        if (::focusController.isInitialized && focusController.dispatchKeyEvent(event)) {
            return true
        }
        return super.dispatchKeyEvent(event)
    }
}
