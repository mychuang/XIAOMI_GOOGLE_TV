package com.miller.tv.launcher.adapter

import android.view.KeyEvent
import com.miller.tv.launcher.focus.BackResult
import com.miller.tv.launcher.focus.FocusNode
import com.miller.tv.launcher.focus.HomeFocusStateMachine

/**
 * Controller bridging Android KeyEvents, the pure Kotlin HomeFocusStateMachine, and ViewBinding.
 *
 * Guaranteed invariants:
 * - Domain is the SOLE authority for navigation decisions.
 * - All D-pad navigation events are consumed (returns true) even when hitting boundaries,
 *   completely blocking Android native FocusFinder from hijacking focus.
 * - Back events are consumed when closing the drawer; propagated to exit callback when at Home root.
 * - Select events trigger domain state transitions (e.g. open/close drawer) and optional action callbacks.
 */
class HomeFocusController(
    val stateMachine: HomeFocusStateMachine = HomeFocusStateMachine(),
    private val viewBinder: HomeFocusViewBinder? = null,
    private val translator: HomeKeyEventTranslator = HomeKeyEventTranslator(),
    private val onSelectAction: ((FocusNode) -> Unit)? = null,
    private val onExitLauncher: (() -> Unit)? = null
) {

    init {
        // Initial sync of focus state to the UI view binder
        viewBinder?.syncState(stateMachine.state)
    }

    /**
     * Dispatches key action by explicit parameters. Decoupled from android.jar stub limitations.
     */
    fun dispatchKey(action: Int, keyCode: Int, repeatCount: Int = 0): Boolean {
        return when (val keyAction = translator.translate(action, keyCode, repeatCount)) {
            is HomeKeyAction.Dpad -> {
                stateMachine.handleDpad(keyAction.direction)
                viewBinder?.syncState(stateMachine.state)
                // Always consume D-pad events to prevent uncontrolled native focus traversal
                true
            }

            HomeKeyAction.Select -> {
                val currentNode = stateMachine.currentFocus
                stateMachine.handleOk()
                viewBinder?.syncState(stateMachine.state)
                onSelectAction?.invoke(currentNode)
                true
            }

            HomeKeyAction.Back -> {
                when (stateMachine.handleBack()) {
                    BackResult.DrawerClosed -> {
                        viewBinder?.syncState(stateMachine.state)
                        true // Consumed: only closed drawer, did not exit launcher
                    }
                    BackResult.ExitLauncher -> {
                        onExitLauncher?.invoke()
                        false // Unconsumed: let Activity / system handle exit
                    }
                }
            }

            HomeKeyAction.Unhandled -> false
        }
    }

    /**
     * Intercepts key events dispatched to the Activity or root view.
     * Returns true if consumed, false to allow default handling.
     */
    fun dispatchKeyEvent(event: KeyEvent): Boolean {
        return dispatchKey(event.action, event.keyCode, event.repeatCount)
    }
}
