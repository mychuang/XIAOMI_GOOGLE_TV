package com.miller.tv.launcher

import org.junit.Assert.assertEquals
import org.junit.Test

/** Infrastructure smoke test only; no product AC/Scenario coverage claimed. */
class ToolchainSmokeTest {
    @Test fun kotlinCollectionsRunOnJvmWithoutAndroidRuntime() {
        assertEquals(listOf("TV", "APK"), listOf("tv", "apk").map { it.uppercase(java.util.Locale.ROOT) })
    }
}
