package com.miller.tv.launcher.adapter

import android.view.KeyEvent
import com.miller.tv.launcher.focus.DpadDirection

/**
 * Normalized key actions for the TV Launcher navigation.
 */
sealed interface HomeKeyAction {
    data class Dpad(val direction: DpadDirection) : HomeKeyAction
    data object Select : HomeKeyAction
    data object Back : HomeKeyAction
    data object Unhandled : HomeKeyAction
}

/**
 * Translates Android KeyEvents to high-level domain actions with strict debouncing and event filtering.
 * - Filters out ACTION_UP to prevent double-firing on single key press.
 * - Suppresses repeat events on Select and Back to avoid rapid double-activation or navigation punch-through.
 * - Allows repeat events on D-pad navigation for smooth continuous scrolling/navigation.
 */
class HomeKeyEventTranslator {

    fun translate(action: Int, keyCode: Int, repeatCount: Int): HomeKeyAction {
        if (action != KeyEvent.ACTION_DOWN) {
            return HomeKeyAction.Unhandled
        }

        return when (keyCode) {
            KeyEvent.KEYCODE_DPAD_UP -> HomeKeyAction.Dpad(DpadDirection.UP)
            KeyEvent.KEYCODE_DPAD_DOWN -> HomeKeyAction.Dpad(DpadDirection.DOWN)
            KeyEvent.KEYCODE_DPAD_LEFT -> HomeKeyAction.Dpad(DpadDirection.LEFT)
            KeyEvent.KEYCODE_DPAD_RIGHT -> HomeKeyAction.Dpad(DpadDirection.RIGHT)

            KeyEvent.KEYCODE_DPAD_CENTER,
            KeyEvent.KEYCODE_ENTER -> {
                if (repeatCount == 0) {
                    HomeKeyAction.Select
                } else {
                    HomeKeyAction.Unhandled
                }
            }

            KeyEvent.KEYCODE_BACK -> {
                if (repeatCount == 0) {
                    HomeKeyAction.Back
                } else {
                    HomeKeyAction.Unhandled
                }
            }

            else -> HomeKeyAction.Unhandled
        }
    }

    fun translate(event: KeyEvent): HomeKeyAction {
        return translate(event.action, event.keyCode, event.repeatCount)
    }
}
