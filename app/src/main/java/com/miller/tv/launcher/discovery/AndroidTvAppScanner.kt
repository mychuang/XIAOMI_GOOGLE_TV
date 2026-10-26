package com.miller.tv.launcher.discovery

import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.util.Log

/**
 * Contract for scanning installed TV applications.
 * Enables decoupling the Android PackageManager adapter from unit-testable components.
 */
interface TvAppScanner {
    fun scanTvApps(snapshotId: Long = 1L): Result<AppSnapshot>
}

/**
 * Android PackageManager adapter for discovering TV launcher activities.
 * Strictly adheres to Android 11 Package Visibility minimal scope (CATEGORY_LEANBACK_LAUNCHER).
 */
class AndroidTvAppScanner(
    private val packageManager: PackageManager,
    private val categorizer: TvAppCategorizer,
    private val hostPackageName: String = "com.miller.tv.launcher"
) : TvAppScanner {

    companion object {
        private const val TAG = "TvAppScanner"
    }

    override fun scanTvApps(snapshotId: Long): Result<AppSnapshot> {
        return try {
            val tvIntent = Intent(Intent.ACTION_MAIN).apply {
                addCategory(Intent.CATEGORY_LEANBACK_LAUNCHER)
            }

            // Query activities matching MAIN + LEANBACK_LAUNCHER (with null guard for non-compliant OEM ROMs)
            val resolveInfoList: List<ResolveInfo> = packageManager.queryIntentActivities(
                tvIntent,
                PackageManager.MATCH_ALL
            ) ?: emptyList()

            val candidates = mutableListOf<DiscoveredActivity>()
            for (info in resolveInfoList) {
                val activityInfo = info.activityInfo ?: continue
                val pkgName = activityInfo.packageName ?: continue
                val actName = activityInfo.name ?: continue

                val label: String? = try {
                    info.loadLabel(packageManager)?.toString()
                } catch (e: Exception) {
                    Log.w(TAG, "Failed to load label for $pkgName/$actName", e)
                    null
                }

                val isExported = activityInfo.exported
                val isEnabled = activityInfo.isEnabled

                // Check if the host launcher has the required permission to start the target activity
                val hasPermission = activityInfo.permission == null ||
                        packageManager.checkPermission(activityInfo.permission, hostPackageName) == PackageManager.PERMISSION_GRANTED

                candidates.add(
                    DiscoveredActivity(
                        packageName = pkgName,
                        activityName = actName,
                        rawLabel = label,
                        isTvLaunchable = true,
                        isEnabled = isEnabled,
                        isExported = isExported,
                        hasRequiredPermission = hasPermission
                    )
                )
            }

            val snapshot = categorizer.categorize(candidates, snapshotId)
            Result.success(snapshot)
        } catch (e: Exception) {
            Log.e(TAG, "Unexpected error during TV app discovery scan", e)
            Result.failure(e)
        }
    }
}
