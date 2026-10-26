# Xiaomi Google TV Launcher

Stage 1 Android TV APK scaffold — Application ID `com.miller.tv.launcher`.

## Scope

Kotlin + Android Views + ViewBinding. The screen is an explicit Stage 1 placeholder, **not** the MVP Home A implementation. No Drawer, App discovery, focus engine, HOME registration, boot receiver, root or special permissions. Specification Baseline v1.1 is unchanged. No product scenarios have been executed.

## Local build

```sh
scripts/gradle-local --version
scripts/gradle-local :app:testDebugUnitTest :app:assembleDebug :app:lintDebug --no-daemon
```

`gradle-local` selects Android Studio's JBR for this process only, and stores Gradle downloads/caches in `.gradle-user-home/`. It does not change shell configuration or global Java. Plain `./gradlew` uses the caller's Java; use the script on this Mac.

- AGP 9.0.1, built-in Kotlin 2.2.10 (do not apply `org.jetbrains.kotlin.android`)
- Gradle Wrapper 9.1.0 (distribution SHA-256 pinned)
- JBR 25.0.3; Java/Kotlin bytecode target 17
- compileSdk 36 / targetSdk 34 / minSdk 30 / Build Tools 36.0.0
- JUnit 4.13.2: one infrastructure smoke test without Android runtime

The global SDK only contained API 37 when Stage 1 started. API 36 revision 2 and Build Tools 36.0.0 were downloaded to **`.android-sdk/`**, not the global SDK. Existing accepted SDK licenses were copied locally; no new license acceptance or global SDK modification was performed. `local.properties` points to this isolated SDK and is ignored. Another checkout must provide its own SDK and local.properties, or deliberately adapt the local helper. No automatic SDK installation is enabled in Gradle.

For Android Studio, open this root and select its bundled JBR as the **project Gradle JDK**. IDE UI Sync was not performed during Stage 1; CLI Gradle configuration/dependency resolution/build succeeded.

APK: `app/build/outputs/apk/debug/app-debug.apk` (ignored, debug-only, not a release artifact).

## Documentation

- Formal requirements: `XIAOMI_GOOGLE_TV_LAUNCHER_MVP_SPEC_20261025/01_...` through `05_...`.
- UI handoff: `docs/UIUX_DESIGN_BASELINE_v1.1.md`.
- Design source allowlist: `docs/STITCH_ASSET_MANIFEST.md`.
- Build evidence and compatibility notes: `docs/STAGE1_VERIFICATION.md`.
- Old handoff files are preserved as historical evidence, not current environment truth.

## Safety / next gate

Build success does not validate API 30 / armeabi-v7a Xiaomi behavior. No emulator or TV has been operated, no APK installed, and G1–G7 device checks remain pending. Stage 2 requires a separate instruction; no F03 TDD cycle has started.
