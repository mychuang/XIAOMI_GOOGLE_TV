package com.miller.tv.launcher.discovery

import android.content.ComponentName
import android.content.Context
import android.content.Intent

/**
 * Contract for launching external TV applications via explicit ComponentName.
 */
interface AppLauncher {
    fun launchApp(componentKey: String): Result<Unit>
}

/**
 * Android Context adapter for launching TV application intents.
 * Strictly adheres to explicit component targeting and Leanback category without guessing.
 */
class ContextAppLauncher(
    private val context: Context
) : AppLauncher {

    override fun launchApp(componentKey: String): Result<Unit> {
        return try {
            val parts = componentKey.split('/')
            if (parts.size != 2) {
                return Result.failure(IllegalArgumentException("Invalid componentKey format: $componentKey"))
            }
            val pkg = parts[0]
            val cls = parts[1]
            val fullCls = if (cls.startsWith(".")) "$pkg$cls" else cls

            val intent = Intent(Intent.ACTION_MAIN).apply {
                addCategory(Intent.CATEGORY_LEANBACK_LAUNCHER)
                component = ComponentName(pkg, fullCls)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
