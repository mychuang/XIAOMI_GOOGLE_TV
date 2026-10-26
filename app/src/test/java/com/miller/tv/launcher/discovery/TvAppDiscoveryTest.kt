package com.miller.tv.launcher.discovery

import com.miller.tv.launcher.focus.DesignatedService
import com.miller.tv.launcher.focus.DpadDirection
import com.miller.tv.launcher.focus.FocusNode
import com.miller.tv.launcher.focus.HomeFocusStateMachine
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Stage 2 — Slice 3: TV App Discovery, Identity Mapping & Ordering Test.
 *
 * Verifies app discovery, eligibility, designated mapping, and O ordering against:
 * - Feature: F02 (AC-F02-01, AC-F02-02, AC-F02-03, AC-F02-05, AC-F02-06, AC-F02-07)
 * - Baseline: SA-04, Spec line 70, 123, 131-139
 * - Scenarios: SC-004, SC-006, SC-007, SC-008, SC-056, SC-057, SC-058, SC-059, SC-060, SC-061, SC-062, SC-093
 */
class TvAppDiscoveryTest {

    private val ytKey = "com.google.android.youtube.tv/com.google.android.apps.youtube.tv.activity.ShellActivity"
    private val nfKey = "com.netflix.ninja/com.netflix.ninja.MainActivity"
    private val dpKey = "com.disney.disneyplus/com.bamtechmedia.dominguez.main.MainActivity"

    private val defaultVerifiedMappings = mapOf(
        DesignatedService.YOUTUBE to DesignatedServiceMapping(
            service = DesignatedService.YOUTUBE,
            status = MappingStatus.VERIFIED,
            primaryCandidates = listOf(ytKey),
            allKnownEntries = setOf(ytKey)
        ),
        DesignatedService.NETFLIX to DesignatedServiceMapping(
            service = DesignatedService.NETFLIX,
            status = MappingStatus.VERIFIED,
            primaryCandidates = listOf(nfKey),
            allKnownEntries = setOf(nfKey)
        ),
        DesignatedService.DISNEY_PLUS to DesignatedServiceMapping(
            service = DesignatedService.DISNEY_PLUS,
            status = MappingStatus.VERIFIED,
            primaryCandidates = listOf(dpKey),
            allKnownEntries = setOf(dpKey)
        )
    )

    // 1. SC-059: 沒有符合資格的指定 App 時，H 為空 (AC-F02-06)
    @Test
    fun `empty designated apps yields empty H but preserves other apps in O`() {
        val categorizer = TvAppCategorizer(mappings = defaultVerifiedMappings)
        val appA = DiscoveredActivity("com.spotify.tv", "MainActivity", "Spotify")
        val appB = DiscoveredActivity("com.plexapp.android", "MainActivity", "Plex")

        val snapshot = categorizer.categorize(listOf(appA, appB))

        assertTrue(snapshot.hApps.isEmpty())
        assertEquals(2, snapshot.oApps.size)
    }

    // 2. SC-057: YouTube、Netflix、Disney+ 依固定順序排列 (AC-F02-06)
    @Test
    fun `designated apps in H are strictly ordered YouTube then Netflix then DisneyPlus`() {
        val categorizer = TvAppCategorizer(mappings = defaultVerifiedMappings)
        // Given query input in random/reversed order: Disney+, Other, Netflix, YouTube
        val yt = DiscoveredActivity("com.google.android.youtube.tv", "com.google.android.apps.youtube.tv.activity.ShellActivity", "YouTube")
        val nf = DiscoveredActivity("com.netflix.ninja", "com.netflix.ninja.MainActivity", "Netflix")
        val dp = DiscoveredActivity("com.disney.disneyplus", "com.bamtechmedia.dominguez.main.MainActivity", "Disney+")
        val other = DiscoveredActivity("com.spotify.tv", "MainActivity", "Spotify")

        val snapshot = categorizer.categorize(listOf(dp, other, nf, yt))

        // Then H is strictly YouTube -> Netflix -> Disney+
        assertEquals(3, snapshot.hApps.size)
        assertEquals(DesignatedService.YOUTUBE, snapshot.hApps[0].service)
        assertEquals(DesignatedService.NETFLIX, snapshot.hApps[1].service)
        assertEquals(DesignatedService.DISNEY_PLUS, snapshot.hApps[2].service)

        // Other is in O, not in H
        assertEquals(1, snapshot.oApps.size)
        assertEquals("com.spotify.tv/MainActivity", snapshot.oApps[0].componentKey)
    }

    // 3. SC-058: 部分指定 App 缺少時壓縮順序 (AC-F02-06)
    @Test
    fun `missing designated apps are compressed out without leaving gaps`() {
        val categorizer = TvAppCategorizer(mappings = defaultVerifiedMappings)
        val yt = DiscoveredActivity("com.google.android.youtube.tv", "com.google.android.apps.youtube.tv.activity.ShellActivity", "YouTube")
        val dp = DiscoveredActivity("com.disney.disneyplus", "com.bamtechmedia.dominguez.main.MainActivity", "Disney+")

        val snapshot = categorizer.categorize(listOf(dp, yt))

        // H compresses to YouTube, Disney+ without gap or placeholder
        assertEquals(2, snapshot.hApps.size)
        assertEquals(DesignatedService.YOUTUBE, snapshot.hApps[0].service)
        assertEquals(DesignatedService.DISNEY_PLUS, snapshot.hApps[1].service)
    }

    // 4. SC-062: 每個指定服務最多只保留一個主要入口 (AC-F02-06)
    @Test
    fun `each designated service retains at most one primary entry in H according to candidate priority`() {
        val y1 = "com.google.android.youtube.tv/PrimaryActivity"
        val y2 = "com.google.android.youtube.tv/SecondaryActivity"
        val mappingsWithPriority = mapOf(
            DesignatedService.YOUTUBE to DesignatedServiceMapping(
                service = DesignatedService.YOUTUBE,
                status = MappingStatus.VERIFIED,
                primaryCandidates = listOf(y1, y2),
                allKnownEntries = setOf(y1, y2)
            )
        )
        val categorizer = TvAppCategorizer(mappings = mappingsWithPriority)

        val actY1 = DiscoveredActivity("com.google.android.youtube.tv", "PrimaryActivity", "YouTube 1")
        val actY2 = DiscoveredActivity("com.google.android.youtube.tv", "SecondaryActivity", "YouTube 2")

        val snapshot = categorizer.categorize(listOf(actY2, actY1))

        assertEquals(1, snapshot.hApps.size)
        assertEquals(y1, snapshot.hApps[0].componentKey)
    }

    // 5. SC-062: 已知屬於指定服務的其他入口不得混入 O (AC-F02-07)
    @Test
    fun `all known entries of designated services are excluded from O`() {
        val y1 = "com.google.android.youtube.tv/PrimaryActivity"
        val y2 = "com.google.android.youtube.tv/SecondaryActivity"
        val y3 = "com.google.android.youtube.tv/OtherActivity"
        val mappings = mapOf(
            DesignatedService.YOUTUBE to DesignatedServiceMapping(
                service = DesignatedService.YOUTUBE,
                status = MappingStatus.VERIFIED,
                primaryCandidates = listOf(y1, y2),
                allKnownEntries = setOf(y1, y2, y3)
            )
        )
        val categorizer = TvAppCategorizer(mappings = mappings)

        val actY1 = DiscoveredActivity("com.google.android.youtube.tv", "PrimaryActivity", "YouTube")
        val actY2 = DiscoveredActivity("com.google.android.youtube.tv", "SecondaryActivity", "YouTube Alt")
        val actY3 = DiscoveredActivity("com.google.android.youtube.tv", "OtherActivity", "YouTube Leanback")
        val other = DiscoveredActivity("com.spotify.tv", "MainActivity", "Spotify")

        val snapshot = categorizer.categorize(listOf(actY1, actY2, actY3, other))

        // H has only Y1
        assertEquals(1, snapshot.hApps.size)
        assertEquals(y1, snapshot.hApps[0].componentKey)

        // O has only Spotify; Y1, Y2, Y3 are all suppressed from O
        assertEquals(1, snapshot.oApps.size)
        assertEquals("com.spotify.tv/MainActivity", snapshot.oApps[0].componentKey)
    }

    // 6. SC-060: 未知 App 不得依顯示名稱冒充指定服務 (AC-F02-05)
    @Test
    fun `app with matching label but unmapped package is not treated as designated service`() {
        val categorizer = TvAppCategorizer(mappings = defaultVerifiedMappings)
        // Fake YouTube with same label but unverified package
        val fakeYt = DiscoveredActivity("com.fake.impostor", "MainActivity", "YouTube")

        val snapshot = categorizer.categorize(listOf(fakeYt))

        // H does NOT receive fake YouTube
        assertTrue(snapshot.hApps.isEmpty())
        // Instead fakeYt is placed in O
        assertEquals(1, snapshot.oApps.size)
        assertEquals("com.fake.impostor/MainActivity", snapshot.oApps[0].componentKey)
    }

    // 7. SC-006: 同一 Component Key 不可重複出現 (AC-F02-02)
    @Test
    fun `identical component keys are deduplicated while different components in same package are preserved`() {
        val categorizer = TvAppCategorizer(mappings = defaultVerifiedMappings)
        val act1 = DiscoveredActivity("com.pkg.multiact", "ActivityA", "Alpha")
        val act1Duplicate = DiscoveredActivity("com.pkg.multiact", "ActivityA", "Alpha Duplicate")
        val act2 = DiscoveredActivity("com.pkg.multiact", "ActivityB", "Alpha") // Same label, different component!

        val snapshot = categorizer.categorize(listOf(act1, act1Duplicate, act2))

        assertEquals(2, snapshot.oApps.size)
        val keys = snapshot.oApps.map { it.componentKey }.toSet()
        assertEquals(setOf("com.pkg.multiact/ActivityA", "com.pkg.multiact/ActivityB"), keys)
    }

    // 8. SC-007, SC-056: O 依正式規格排序 (trim -> Unicode NFC -> casefold -> tie-breaker componentKey)
    @Test
    fun `O ordering contract normalizes via NFC and casefold with componentKey tie breaker`() {
        val categorizer = TvAppCategorizer(mappings = defaultVerifiedMappings)

        // SC-007: beta, Alpha (p.a/A), alpha (p.b/B) -> order: p.a/A, p.b/B, beta
        val beta = DiscoveredActivity("com.beta", "Main", "beta")
        val alphaA = DiscoveredActivity("p.a", "A", "Alpha")
        val alphaB = DiscoveredActivity("p.b", "B", "alpha")

        val snapshot = categorizer.categorize(listOf(beta, alphaB, alphaA))
        val keys = snapshot.oApps.map { it.componentKey }
        assertEquals(listOf("p.a/A", "p.b/B", "com.beta/Main"), keys)

        // SC-056: É with spaces vs decomposed E + combining acute -> normalized tie-breaker
        val eDecomposed = DiscoveredActivity("p.b", "B", "E\u0301") // E + combining acute
        val eComposed = DiscoveredActivity("p.a", "A", "  \u00C9  ")  // É with spaces
        val snapshotUnicode = categorizer.categorize(listOf(eDecomposed, eComposed))
        assertEquals(listOf("p.a/A", "p.b/B"), snapshotUnicode.oApps.map { it.componentKey })
    }

    // 9. AC-F06-01: H 與 O 來自同一個不可變資料快照
    @Test
    fun `snapshot publishes atomic immutable H and O with matching snapshotId`() {
        val categorizer = TvAppCategorizer(mappings = defaultVerifiedMappings)
        val yt = DiscoveredActivity("com.google.android.youtube.tv", "com.google.android.apps.youtube.tv.activity.ShellActivity", "YouTube")
        val spotify = DiscoveredActivity("com.spotify.tv", "MainActivity", "Spotify")

        val snapshot = categorizer.categorize(listOf(yt, spotify), snapshotId = 42L)

        assertEquals(42L, snapshot.snapshotId)
        assertEquals(1, snapshot.hApps.size)
        assertEquals(1, snapshot.oApps.size)
    }

    // 10. SC-027, 規格行 123: App 更新後使用 Stable Key 讓既有焦點邏輯保留或修復
    @Test
    fun `snapshot integration into HomeFocusStateMachine retains stable key focus`() {
        val stateMachine = HomeFocusStateMachine()
        val categorizer = TvAppCategorizer(mappings = defaultVerifiedMappings)

        val yt = DiscoveredActivity("com.google.android.youtube.tv", "com.google.android.apps.youtube.tv.activity.ShellActivity", "YouTube")
        val nf = DiscoveredActivity("com.netflix.ninja", "com.netflix.ninja.MainActivity", "Netflix")
        val spotify = DiscoveredActivity("com.spotify.tv", "MainActivity", "Spotify")

        val snapshot = categorizer.categorize(listOf(yt, nf, spotify))

        // Feed snapshot to state machine
        stateMachine.onSnapshotLoaded(snapshot.hApps.map {
            FocusNode.Content.App(it.service!!, it.componentKey)
        })
        stateMachine.onDrawerSnapshotLoaded(snapshot.oApps.map {
            FocusNode.Drawer.App(it.componentKey)
        })

        assertEquals(ytKey, (stateMachine.currentFocus as FocusNode.Content.App).componentKey)

        // Move to Netflix
        stateMachine.handleDpad(DpadDirection.RIGHT)
        assertEquals(nfKey, (stateMachine.currentFocus as FocusNode.Content.App).componentKey)

        // SC-093: YouTube & Netflix rename in new snapshot -> stable key focus is retained
        val ytRenamed = DiscoveredActivity("com.google.android.youtube.tv", "com.google.android.apps.youtube.tv.activity.ShellActivity", "ZZZ")
        val nfRenamed = DiscoveredActivity("com.netflix.ninja", "com.netflix.ninja.MainActivity", "AAA")
        val snapshotRenamed = categorizer.categorize(listOf(ytRenamed, nfRenamed, spotify))

        stateMachine.onSnapshotLoaded(snapshotRenamed.hApps.map {
            FocusNode.Content.App(it.service!!, it.componentKey)
        })
        assertEquals(nfKey, (stateMachine.currentFocus as FocusNode.Content.App).componentKey)
    }

    // 11. SC-061: 候選映射未確認時不得冒充未安裝 (AC-F02-05)
    @Test
    fun `unverified mapping marks service as unconfirmed and does not create H entry`() {
        val tbdMappings = mapOf(
            DesignatedService.YOUTUBE to DesignatedServiceMapping(
                service = DesignatedService.YOUTUBE,
                status = MappingStatus.UNVERIFIED_TBD,
                primaryCandidates = emptyList()
            )
        )
        val categorizer = TvAppCategorizer(mappings = tbdMappings)
        val candidate = DiscoveredActivity("com.some.app", "Main", "Unknown")

        val snapshot = categorizer.categorize(listOf(candidate))

        assertTrue(snapshot.hApps.isEmpty())
        assertTrue(snapshot.unconfirmedServices.contains(DesignatedService.YOUTUBE))
        // candidate is safely kept in O
        assertEquals(1, snapshot.oApps.size)
    }

    // 12. SC-004: 排除本自訂 APK 與三個原廠套件 (AC-F02-01, 規格行 131-139)
    @Test
    fun `custom APK itself and OEM launcher recovery settings packages are strictly excluded`() {
        val categorizer = TvAppCategorizer(
            ownPackageName = "com.miller.tv.launcher",
            mappings = defaultVerifiedMappings
        )

        val ownApp = DiscoveredActivity("com.miller.tv.launcher", "MainActivity", "Launcher")
        val oemLauncher = DiscoveredActivity("com.google.android.apps.tv.launcherx", "HomeActivity", "Google TV")
        val recovery = DiscoveredActivity("com.google.android.tungsten.setupwraith", "RecoveryActivity", "Setup")
        val settings = DiscoveredActivity("com.android.tv.settings", "MainSettings", "Settings")
        val legit = DiscoveredActivity("com.spotify.tv", "MainActivity", "Spotify")

        val snapshot = categorizer.categorize(listOf(ownApp, oemLauncher, recovery, settings, legit))

        // All 4 excluded packages are suppressed; only legit is present in O
        assertEquals(1, snapshot.oApps.size)
        assertEquals("com.spotify.tv/MainActivity", snapshot.oApps[0].componentKey)
    }

    // 13. SC-008: 名稱空白或讀取失敗時降級為 package component (AC-F02-03)
    @Test
    fun `blank or missing label gracefully degrades to package component string`() {
        val categorizer = TvAppCategorizer(mappings = defaultVerifiedMappings)
        val blankLabel = DiscoveredActivity("com.test.app", "Main", "   ")
        val nullLabel = DiscoveredActivity("com.test.app2", "Main", null)

        val snapshot = categorizer.categorize(listOf(blankLabel, nullLabel))

        assertEquals("com.test.app/Main", snapshot.oApps[0].displayName)
        assertEquals("com.test.app2/Main", snapshot.oApps[1].displayName)
    }
}
