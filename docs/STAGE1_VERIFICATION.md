# Stage 1 — 實際驗證紀錄

## 範圍與結果

本次只建立 APK scaffold、ViewBinding 畫面與 JVM 工具鏈 smoke test；沒有任何產品 AC／Scenario 被宣告 Pass，沒有 Stage 2 焦點或清單功能。五份 Specification Baseline v1.1 原檔的 SHA-256 在操作前後一致。

## 工具鏈

| 項目 | 實際使用 |
|---|---|
| AGP | 9.0.1 |
| Gradle Wrapper | 9.1.0 |
| AGP built-in Kotlin compiler / stdlib | 2.2.10（下載快取與 runtime dependency 報告核對） |
| Gradle 內嵌 Kotlin DSL | 2.2.0，與 App Kotlin compiler 不同 |
| JDK / Gradle launcher / daemon | Android Studio JBR 25.0.3，arm64 |
| Java / Kotlin bytecode | 17 |
| compile / target / min SDK | 36 / 34 / 30 |
| SDK platform | android-36 revision 2 |
| Build Tools | 36.0.0 |
| JVM tests | JUnit 4.13.2 |

官方文件實際下載核對：AGP 9.0 相容表列 Gradle 9.1.0、Build Tools 36.0.0、最低 JDK 17、KGP 2.2.10；Gradle 9.1 官方 Java 矩陣支持 Java 25。結合本機實際建置，不需改動使用者指定版本。

來源：
- https://developer.android.com/build/releases/agp-9-0-0-release-notes
- https://docs.gradle.org/9.1.0/userguide/compatibility.html

全域 SDK 原本只有 android-37.0。新增 SDK 36 和 Build Tools 僅位於專案 `.android-sdk/`；既有已接受 licenses 複製到專案，不改全域 SDK。Gradle 禁止自動 SDK download。使用 `scripts/gradle-local` 指定單次程序 JAVA_HOME / Gradle JVM 與專案 cache，不修改 shell rc 或全域 JDK。

## 執行證據

```text
scripts/gradle-local :app:testDebugUnitTest :app:assembleDebug :app:lintDebug --no-daemon --console=plain
BUILD SUCCESSFUL in 8s
52 actionable tasks: 29 executed, 23 up-to-date
```

- JUnit XML：1 tests / 0 failures / 0 errors / 0 skipped。
- `:app:assembleDebug`：成功。
- `:app:lintDebug`：成功、0 error、4 warnings（下節），沒有停用 lint。
- Wrapper 以官方 Gradle 9.1.0 發行檔產生，下載 SHA-256 校驗成功並寫入 wrapper properties：`a17ddd85a26b6a7f5ddb71ff8b05fc5104c0202c6e64782429790c933686c806`。
- CLI 配置／依賴解析成功；**未操作 Android Studio UI Sync**。
- APK：`app/build/outputs/apk/debug/app-debug.apk`，2,467,204 bytes。
- APK SHA-256：`3e2e5756a55bca97fcb28615c323579e224c15fd9d897ecf461c04cc7f0bc3e8`。
- `aapt dump badging`：Application ID 正確、min30 / target34 / compile36、Leanback Activity、touchscreen not required。
- `aapt dump xmltree`：只有 MAIN + LEANBACK_LAUNCHER，無 HOME、無 uses-permission。
- APK ZIP：native `.so` 清單為空；不是 API30 實機啟動驗證。
- MainActivity 已成功編譯連結 ViewBinding；螢幕實際渲染與原廠 App 清單曝光尚未驗證。

本地證據（忽略、不提交）：`.tooling/stage1-build-final.log`、`.tooling/dependencies.log`、`app/build/test-results/testDebugUnitTest/`、`app/build/reports/lint-results-debug.*`。

## 已恢復的錯誤與保留警告

1. 一開始用系統舊 Swift 5.3 製作 banner 時程序 crash，導致首次 resource linking 缺少 tv_banner；改用現有 Pillow 生成 320×180 原創文字 banner，未安裝或升級全域工具。之後 APK / test / lint 成功。
2. SDK XML v4 / AGP reader v3 警告：本機較新的 CLI 產生 metadata，未阻止 build。未為消除此訊息升級指定 AGP。
3. 隔離 SDK 未安裝 platform-tools：出現 warning，但本階段不需要 ADB，不下載或執行裝置工具。
4. Lint `OldTargetApi`：保留使用者指定 target34。
5. Lint `AndroidGradlePluginVersion`：提醒較新 Wrapper 可用，保留已核准且成功驗證的 9.1.0。
6. Lint `DataExtractionRules`：API30 提醒補 fullBackupContent；已有 allowBackup=false 與 API31+ extraction rules，本 scaffold 無使用者持久資料，留待資料功能加入前處理。
7. Lint `IconMissingDensityFolder`：Stage1 TV banner 提供 xhdpi；launcher icon 是 vector。此為開發入口資產，不宣稱最終品牌設計完成。

## 提交前審查

獨立唯讀 code review：passed=true，security_concerns=[]、logic_errors=[]。建議未來對 Android Studio 自動更新後的 JBR 加 major-version preflight；本次已由 --version 核對為 25.0.3。

`git diff --cached --check` 對既有 Markdown hard-break、歷史 diff 空白及官方 gradlew.bat CRLF 提示 whitespace；不修改已核准規格或官方 wrapper 來消除此類提示。新增 Android source／設定單獨檢查。

## 安全與未執行事項

未呼叫 ADB、未啟動／修改 Emulator、未連線 Xiaomi、未安裝 APK、未改 HOME／系統套件、未改全域 Java 或 SDK。普通 debug APK 的建置／靜態檢查不等於 G1–G7 裝置驗收。Stage 2 未開始。
