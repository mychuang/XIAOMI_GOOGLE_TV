package com.miller.tv.launcher.discovery

import com.miller.tv.launcher.focus.DesignatedService
import java.text.Normalizer
import java.util.Locale

/**
 * Raw TV activity discovery candidate from system queries.
 * Pure Kotlin data model independent of Android runtime.
 */
data class DiscoveredActivity(
    val packageName: String,
    val activityName: String,
    val rawLabel: String? = null,
    val isTvLaunchable: Boolean = true,
    val isEnabled: Boolean = true,
    val isExported: Boolean = true,
    val hasRequiredPermission: Boolean = true
) {
    val componentKey: String
        get() = "$packageName/$activityName"
}

/**
 * A resolved TV app item ready for display.
 */
data class DiscoveredTvApp(
    val componentKey: String,
    val displayName: String,
    val packageName: String,
    val service: DesignatedService? = null
)

/**
 * Status of designated service identity mapping per G3 / AC-F02-05.
 */
enum class MappingStatus {
    VERIFIED,
    UNVERIFIED_TBD
}

/**
 * Identity mapping definition for a designated service.
 */
data class DesignatedServiceMapping(
    val service: DesignatedService,
    val status: MappingStatus,
    val primaryCandidates: List<String> = emptyList(),
    val allKnownEntries: Set<String> = emptySet()
)

/**
 * Immutable atomic snapshot of H and O app collections (F06 / AC-F06-01).
 */
data class AppSnapshot(
    val snapshotId: Long,
    val hApps: List<DiscoveredTvApp>,
    val oApps: List<DiscoveredTvApp>,
    val unconfirmedServices: Set<DesignatedService> = emptySet()
)

/**
 * Pure Kotlin engine for TV App discovery, categorization, identity mapping, and ordering.
 * Implements F02 (AC-F02-01 to AC-F02-07), SA-04, and baseline exclusion rules.
 */
class TvAppCategorizer(
    private val ownPackageName: String = "com.miller.tv.launcher",
    private val mappings: Map<DesignatedService, DesignatedServiceMapping> = DEFAULT_DESIGNATED_MAPPINGS
) {
    companion object {
        /**
         * Baseline OEM launcher, recovery, and settings packages strictly excluded from TV App listings
         * (Spec line 131-139 / AC-F02-01 / SC-004).
         */
        val DEFAULT_EXCLUDED_PACKAGES: Set<String> = setOf(
            "com.google.android.apps.tv.launcherx",
            "com.google.android.tungsten.setupwraith",
            "com.android.tv.settings"
        )

        /**
         * Verified official Leanback launcher component mappings for designated services.
         * Enforces strict package/component identity; never guesses by app display name (AC-F02-05).
         */
        val DEFAULT_DESIGNATED_MAPPINGS: Map<DesignatedService, DesignatedServiceMapping> = mapOf(
            DesignatedService.YOUTUBE to DesignatedServiceMapping(
                service = DesignatedService.YOUTUBE,
                status = MappingStatus.VERIFIED,
                primaryCandidates = listOf(
                    "com.google.android.youtube.tv/com.google.android.apps.youtube.tv.activity.ShellActivity",
                    "com.google.android.youtube.tv/com.google.android.youtube.tv.MainActivity",
                    "com.google.android.youtube.tv/.MainActivity"
                ),
                allKnownEntries = setOf(
                    "com.google.android.youtube.tv/com.google.android.apps.youtube.tv.activity.ShellActivity",
                    "com.google.android.youtube.tv/com.google.android.youtube.tv.MainActivity",
                    "com.google.android.youtube.tv/.MainActivity",
                    "com.google.android.youtube.tv/com.google.android.apps.youtube.tv.activity.TvGuideActivity"
                )
            ),
            DesignatedService.NETFLIX to DesignatedServiceMapping(
                service = DesignatedService.NETFLIX,
                status = MappingStatus.VERIFIED,
                primaryCandidates = listOf(
                    "com.netflix.ninja/com.netflix.ninja.MainActivity",
                    "com.netflix.ninja/.MainActivity"
                ),
                allKnownEntries = setOf(
                    "com.netflix.ninja/com.netflix.ninja.MainActivity",
                    "com.netflix.ninja/.MainActivity"
                )
            ),
            DesignatedService.DISNEY_PLUS to DesignatedServiceMapping(
                service = DesignatedService.DISNEY_PLUS,
                status = MappingStatus.VERIFIED,
                primaryCandidates = listOf(
                    "com.disney.disneyplus/com.bamtechmedia.dominguez.main.MainActivity",
                    "com.disney.disneyplus/com.disney.disneyplus.MainActivity",
                    "com.disney.disneyplus/.MainActivity"
                ),
                allKnownEntries = setOf(
                    "com.disney.disneyplus/com.bamtechmedia.dominguez.main.MainActivity",
                    "com.disney.disneyplus/com.disney.disneyplus.MainActivity",
                    "com.disney.disneyplus/.MainActivity"
                )
            )
        )

        /**
         * Standard fixed service display order for H: YouTube -> Netflix -> Disney+ (AC-F02-06).
         */
        val DESIGNATED_SERVICE_ORDER: List<DesignatedService> = listOf(
            DesignatedService.YOUTUBE,
            DesignatedService.NETFLIX,
            DesignatedService.DISNEY_PLUS
        )

        /**
         * Normalizes a display name for collation per AC-F02-02 / SC-007 / SC-056:
         * trim -> Unicode NFC -> casefold (lowercase).
         */
        fun normalizeName(name: String): String {
            val trimmed = name.trim()
            val nfc = Normalizer.normalize(trimmed, Normalizer.Form.NFC)
            return nfc.lowercase(Locale.ROOT)
        }
    }

    /**
     * Categorizes raw discovered activities into atomic H and O app collections.
     */
    fun categorize(
        candidates: List<DiscoveredActivity>,
        snapshotId: Long = 1L
    ): AppSnapshot {
        // 1. Filter eligible candidates (E) per AC-F02-01 / SC-004
        val eligible = mutableListOf<DiscoveredActivity>()
        val seenComponentKeys = mutableSetOf<String>()

        for (c in candidates) {
            if (!c.isTvLaunchable || !c.isEnabled || !c.isExported || !c.hasRequiredPermission) {
                continue
            }
            if (c.packageName == ownPackageName || DEFAULT_EXCLUDED_PACKAGES.contains(c.packageName)) {
                continue
            }
            // Component deduplication (AC-F02-02 / SC-006)
            if (seenComponentKeys.add(c.componentKey)) {
                eligible.add(c)
            }
        }

        // Map candidate to DiscoveredTvApp model
        val candidateMap = eligible.associateBy { it.componentKey }

        // 2. Identify designated services (S) and construct H per AC-F02-05, AC-F02-06, AC-F02-07
        val hApps = mutableListOf<DiscoveredTvApp>()
        val unconfirmed = mutableSetOf<DesignatedService>()
        val allDesignatedComponentKeys = mutableSetOf<String>()

        for (service in DESIGNATED_SERVICE_ORDER) {
            val mapping = mappings[service]
            if (mapping == null || mapping.status == MappingStatus.UNVERIFIED_TBD) {
                // AC-F02-05 / SC-061: Unconfirmed mapping
                unconfirmed.add(service)
                continue
            }

            // Record all known entries for suppression from O (AC-F02-07 / SC-062)
            allDesignatedComponentKeys.addAll(mapping.primaryCandidates)
            allDesignatedComponentKeys.addAll(mapping.allKnownEntries)

            // Select primary candidate according to approved priority order
            var selectedCandidateKey: String? = null
            for (candidateKey in mapping.primaryCandidates) {
                if (candidateMap.containsKey(candidateKey)) {
                    selectedCandidateKey = candidateKey
                    break
                }
            }

            if (selectedCandidateKey != null) {
                val candidate = candidateMap[selectedCandidateKey]!!
                val displayName = resolveDisplayName(candidate)
                hApps.add(
                    DiscoveredTvApp(
                        componentKey = candidate.componentKey,
                        displayName = displayName,
                        packageName = candidate.packageName,
                        service = service
                    )
                )
            }
        }

        // 3. Construct O: eligible apps excluding all designated service entries (S)
        val oCandidates = eligible.filter { !allDesignatedComponentKeys.contains(it.componentKey) }

        // Sort O per AC-F02-02, SC-007, SC-056:
        // Pre-normalize display name to avoid repeated Normalizer.normalize calls during comparison
        val sortedO = oCandidates.map { candidate ->
            val displayName = resolveDisplayName(candidate)
            val normalized = normalizeName(displayName)
            Triple(candidate, displayName, normalized)
        }.sortedWith(
            Comparator { a, b ->
                val nameComparison = a.third.compareTo(b.third)
                if (nameComparison != 0) {
                    nameComparison
                } else {
                    a.first.componentKey.compareTo(b.first.componentKey)
                }
            }
        ).map { (candidate, displayName, _) ->
            DiscoveredTvApp(
                componentKey = candidate.componentKey,
                displayName = displayName,
                packageName = candidate.packageName,
                service = null
            )
        }

        return AppSnapshot(
            snapshotId = snapshotId,
            hApps = hApps,
            oApps = sortedO,
            unconfirmedServices = unconfirmed
        )
    }

    private fun resolveDisplayName(candidate: DiscoveredActivity): String {
        val label = candidate.rawLabel
        return if (label.isNullOrBlank()) {
            candidate.componentKey // AC-F02-03 / SC-008: degradation to componentKey
        } else {
            label.trim()
        }
    }
}
