# Xiaomi Google TV Custom Launcher — Session Handoff

**整理日期：2026-10-25**  
**狀態：前置環境與實機唯讀盤點完成；尚未進入產品設計或程式開發。**

## 1. 專案目標與工作原則

- 專案目標：參考 Samsung Serif 以 Claude Code 客製化首頁的案例，在 **Xiaomi Google TV** 上製作簡潔、無原廠推薦廣告的自訂 Launcher；不重寫 Android/Google TV OS。
- 本機：MacBook Pro（Apple Silicon arm64）。偏好用 VS Code 類 IDE 檢視與審核程式，目前實際使用 **Antigravity IDE**。
- AI 實作：**Codex**（原規劃透過 Hermes Agent 使用 Codex；實際工具連接方式日後確認）。
- **使用者明確指定的未來工作流程（必須遵守）：**
  1. **與 ChatGPT 討論與審核規格文件**，以 BDD（Behavior-Driven Development）編寫可審核的 Gherkin / Given–When–Then 情境及驗收條件。
  2. **Google Stitch 設計 UI/UX**；將 Stitch 的設計輸出、操作流程、焦點行為和畫面狀態與規格對齊，交由使用者審核。
  3. **通過規格與設計審核後**，再交 **Codex** 開發；開發過程 **必須先遵守 TDD（Test-Driven Development）**：先建立失敗測試（Red）→ 最少實作使其通過（Green）→ 重構（Refactor），且每個 BDD scenario 可追溯至測試。
  4. 使用者使用 Antigravity IDE 審查 Git Diff、程式碼、Manifest、Gradle 設定及部署腳本。
  5. 先模擬器，再實機；最後才考慮 HOME 鍵接管。任何系統變更都需要使用者明確批准、先設計 rollback。
- 現階段**不要自動進入 Agent 開發**；新 Session 從規格討論開始，除非使用者另行指定。

## 2. Mac / 開發工具 — 已知已驗證資訊

| 項目 | 資訊 |
|---|---|
| Mac | MacBook Pro, Apple Silicon `arm64` |
| Shell | `/opt/homebrew/bin/zsh` |
| IDE | Antigravity IDE（VS Code-based）, CLI `agy-ide` 可執行，版本 `1.107.0`, arm64 |
| Android Studio | Rabbit 1 / `2026.2.1`（依安裝畫面） |
| SDK 位置 | `/Users/miller/Library/Android/sdk`；`ANDROID_HOME` 和 PATH 已設定並驗證 |
| Android SDK Platform | `android-37.0`，API 37.0，Extension Level 22 |
| Platform-Tools | `37.0.1`，ADB 可執行 |
| Build-Tools | `36.0.0` |
| SDK Command-line Tools | `23.0`，sdkmanager 可用 |
| Emulator | `37.2.12` |
| Android Studio JBR | OpenJDK/JBR `25.0.3`, JetBrains, `arm64`; java/javac 等工具已驗證 |
| macOS Terminal 預設 Java | Microsoft OpenJDK `11.0.16.1`, `x86_64`（經 Rosetta）；另有 Temurin 8 x86_64 |
| 全域 `JAVA_HOME` | 未設定（按當時 Audit） |
| 全域 Gradle | 未安裝；不需要預裝，未來使用專案 Gradle Wrapper |

Java/Gradle 方針：避免覆寫全域 Java 11 與刪除舊 JDK。未來專案建立時先檢查 AGP、Gradle Wrapper、Gradle Daemon JVM criteria 和實際 JVM。必要時對**單次命令**指定：

```bash
JBR_HOME="/Applications/Android Studio.app/Contents/jbr/Contents/Home"
JAVA_HOME="$JBR_HOME" ./gradlew --version
```

AGP 9.4 + Gradle 9.6、JBR 25 是**候選相容組合**，未建立專案，尚未實際 Build/Sync，切勿稱已驗證可建置。避免一開始鎖定特定 JDK 或升級 SDK；由專案實際依賴決定。

## 3. Google TV 模擬器 — 驗收完成

- 建立：**Television (1080p)**，Google TV ARM 64 v8a System Image，**Android 16 / API 36**。
- ADB serial：`emulator-5554`。
- `adb devices -l` 最終顯示 `device`；`sys.boot_completed=1`。
- `ro.product.model=sdk_google_atv64_amati_arm64`。
- `ro.product.cpu.abi=arm64-v8a`。
- HOME Activity：`com.google.android.apps.tv.launcherx/.home.HomeActivity`。
- 注意：模擬器與實機 API/CPU 架構不同，不能以模擬器驗收取代實機測試。

## 4. Xiaomi Google TV 實機 — 唯讀盤點完成

| 項目 | 已確認的值 |
|---|---|
| 型號 | `MiTV-MOOR0`（含連字號） |
| Device code / product | `venom` |
| 系統 | Google TV, Android **11** |
| API level | **30** |
| CPU ABI | **`armeabi-v7a`（32-bit ARM）** |
| 區網 IP（當次） | `192.168.1.117`，日後可能改變 |
| ADB TCP | `192.168.1.117:5555`，`adb devices -l` 顯示 `device`，shell 查詢成功 |
| 開發者選項 | 「關於」頁面的版本號連按七次成功啟用 |
| 偵錯選項 | 有 USB debugging，沒有獨立 Wireless debugging，但 TCP:5555 實測連通 |
| 當前 HOME | `com.google.android.apps.tv.launcherx/.home.HomeActivity` |

實機 `query-activities` 查到三個 HOME 候選：

1. `com.google.android.apps.tv.launcherx/.home.HomeActivity`（priority 2，當前解析到的原廠 Launcher）
2. `com.google.android.tungsten.setupwraith/.RecoveryActivity`（priority 1，復原相關）
3. `com.android.tv.settings/.system.FallbackHome`（priority -1000，備援）

**不能因 HOME 列表存在就斷言可接管 Home 鍵；禁止停用 `setupwraith`、`FallbackHome` 或任意原廠套件。** 目前尚未安裝測試 Launcher、未變更預設 Home、未 Root。

### 重要 ADB 唯讀指令

```bash
adb devices -l
adb -s 192.168.1.117:5555 shell getprop ro.product.model
adb -s 192.168.1.117:5555 shell getprop ro.build.version.release
adb -s 192.168.1.117:5555 shell getprop ro.build.version.sdk
adb -s 192.168.1.117:5555 shell getprop ro.product.cpu.abi
adb -s 192.168.1.117:5555 shell cmd package resolve-activity --brief -a android.intent.action.MAIN -c android.intent.category.HOME
adb -s 192.168.1.117:5555 shell cmd package query-activities --brief -a android.intent.action.MAIN -c android.intent.category.HOME
```

始終明確指定 `-s`，因模擬器和實機可能同時在線。需要時確認實際 IP，勿假設永遠為 `192.168.1.117`。

## 5. 架構與尚待決策

- 應開發 Android 原生 Launcher APK（Kotlin），**不是修改/重刷 Google TV OS**。
- UI 技術：Compose for TV vs Android Views 尚未決定；需依 **Google Stitch UI/UX 與 D-pad 焦點操作**、實機 API 30 相容性評估。
- `minSdk`：API 30 可作第一版候選，但**未正式決定**；`compileSdk`、`targetSdk`、AGP/Kotlin/Gradle 版本都須在規格/技術設計後決定。
- 如使用任何 native `.so`，需支援實機 `armeabi-v7a`；純 Kotlin/Java 通常不需雙 APK。
- Launcher 需要考慮 Android 11 package visibility（`<queries>`）、D-pad 焦點及遙控器、App 啟動、HDMI/系統設定入口、開機與返回、HOME intent 與 `LEANBACK_LAUNCHER` 差異。
- 僅確認原廠 HOME Activity；尚未驗證第三方 Launcher 能否被設為預設、HOME 鍵可否接管、APK 實機安裝或播放串流應用的行為。

## 6. 安全與驗收門檻

1. BDD 功能／例外／錯誤情境與驗收標準由使用者先審核。
2. Google Stitch UI/UX 原型由使用者審核；設計規範包含 TV 遙控器 D-pad/focus/overscan/1080p 等。
3. Codex 必須 TDD：**先測試失敗證據，再最小實作與通過測試，再重構**；保留驗收測試、單元測試、UI/整合測試可追溯關係。
4. 使用者審查 Git Diff、依賴、Manifest 權限、ADB/部署腳本。
5. APK 先在模擬器測，再實體 Google TV 上手動開啟；不立即指定系統 HOME。
6. Home 鍵接管單獨設 Gate，驗證可復原後才在使用者同意下試驗。
7. 禁止無同意即停用/移除系統套件、Root、刷機、清除資料、修改開機/Launcher 設定。
8. ADB TCP 5555 在區網可達，非開發時建議停用偵錯並檢查授權裝置。

## 7. 新 Session 建議起始任務

> 請讀取本交接檔。我們要為 Xiaomi MiTV-MOOR0 Google TV 自訂 Launcher **先做需求與規格**。先與我共同討論 MVP、使用情境、功能規則，再建立 BDD Gherkin Given/When/Then；我審核通過後再利用 Google Stitch 製作 TV UI/UX；Stitch 設計通過後交由 Codex 依 **TDD Red → Green → Refactor** 開發。不要在規格確認前生成正式專案、直接執行 Agent、安裝 APK 或修改電視系統。以既有 Android 11 API 30 armeabi-v7a 實機和 Android 16 arm64 Google TV 模擬器為相容性基準。
