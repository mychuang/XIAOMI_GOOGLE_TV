# 01｜Product Vision and Scope

**Status: Specification Baseline v1.1 — Approved for UI/UX Design**  
**規格日期：2026-10-25｜版本：v1.1｜產品測試狀態：Not Run**

## v1.1 受控變更與核准界線

**審核狀態：Approved Product Requirements / Approved Behavioral Baseline**

- **D8 已核准的變更範圍**：採 Google Stitch 第一張「首頁 A」為正式視覺方向；首頁保留本機時間、日期與星期、Wi-Fi／網路、HDMI、本機儲存五項唯讀資訊，不新增焦點或操作。
- **既有已核准基線**：F01～F08 的 AC 與原 85 個 Active Scenario 行為不變；SA-01～SA-07 與 Q1～Q5 Closed 保留。D4 僅「時鐘／資訊排除」由 D8 修訂，其餘限制不變。
- **Approved Product Requirements**：D1～D8 與 v1.1 MVP AC／BDD 已核准；F11 維持 Must Have。
- **Approved Behavioral Baseline**：SA-01～SA-07 保留；P11-01～P11-03 全部 Approved／Closed。所有效能數字仍是初始驗收目標，需實機測量。
- **Pending Final UI/UX Design Review**：首頁 A 方向已選定，不表示完整 UI/UX 已驗收；最終畫面、焦點及錯誤／降級狀態對齊仍待審核。
- **設計證據**：首頁 A 選擇依本次使用者陳述記錄；本次未操作 Stitch 或檢視其圖稿，未取得可鎖版的畫面 ID／匯出版本，不能據此宣稱全部畫面、焦點及錯誤狀態設計已核准。
- **Pending Technical Verification / Product Tests: Not Run**：一般 APK 可讀資訊及 Xiaomi 真實行為均待驗證；受控 fixture 不是裝置證據。手動啟動及獨立 G-HOME 不變。

### Change Log — v1.0 → v1.1

F11 由 Should 升為 Must「本機時間與唯讀裝置資訊」；修訂 AC-F11-01，追加 F11 AC 及從 SC-095 起的新情境。F01 只補資訊顯示範圍，工具仍恰為重新整理／系統設定。F11 不加入 F06 的 App 清單查詢或焦點狀態；資訊失敗只在本欄降級。詳細 ID 與計數見 04。本次定稿只更新核准狀態，不修改行為、數值或 ID。以下 v1.0 及更早的核准／Change Log 保留當時狀態；現行核准狀態以本節為準，不代表技術或設計驗收通過。

## 歷史核准紀錄（Draft 0.2 → v1.0）

- **Approved Product Requirements**：D1～D7 及本版 MVP 需求已核准；F02 每指定服務僅一個主要入口、其餘同服務入口隱藏；F05 同一存活操作階段返回時保留面板與原焦點（失效項按既定規則修復）。
- **Approved Behavioral Baseline**：SA-01～SA-07 已核准；Q1～Q5 均 Closed。保留 SA ID 供追溯，不再表示待核准假設。
- **Pending Technical Verification**：真實 package/component mapping、API／ABI、OEM 生命週期及 HOME 等能力仍未驗證。
- **Pending UI/UX Design**：符合進入設計的規格條件；尚未設計或核准 UI/UX，本次不啟動 Stitch。
- **Not Run Product Tests**：產品測試狀態為 Not Run；SA-07 數字是已核准的初始驗收目標，仍需後續實機測量，不是效能實測結果。

本次僅整理核准狀態與一致性；Feature／AC／Scenario ID、驗收行為及歷史修訂分類不變。以下 Draft 0.1 → Draft 0.2 Change Log 為歷史紀錄，其當時待審狀態已由本節取代。技術驗證、設計執行、開發與部署均須遵守原流程及另行授權。

## Change Log — Draft 0.1 → Draft 0.2

- 本次為原五份文件的 Revision；交接基線、Feature ID F01–F16 及安全 Gate 保留，沒有重新建立專案。
- 依最新兩輪審查輸入確認 D1–D7；首頁改為 YouTube／Netflix／Disney+ 的條件式固定順序入口，加上更多 APP；保留重新整理與系統設定，不增加上方工具。
- 更多 APP 明確為 Launcher 內部覆蓋式面板；分開首頁與面板焦點、Back、啟動／返回及雙區更新；細節標為 Suggested Assumptions，未冒充逐項核准。
- HOME 接管仍非本版前提，但 F15 已標為後續高優先級產品目標；G-HOME 安全、授權與 rollback 不放寬。
- 原 AC 按責任延續 ID，新能力追加 ID；失效 Scenario 標 Replaced（已退役）並指向新 ID，未重用。完整逐項對照及重新計算的數量見 04。
- 本次只做文件與靜態一致性檢查。所有產品 Scenario／技術能力驗證仍為 Not Run；沒有 Stitch、專案、Build、APK、裝置操作或產品測試。

## 結論與本版範圍

首版為可由原廠 Google TV 手動開啟的無推薦首頁：上方只保留重新整理與系統設定；主要 App 僅在合格時依 YouTube→Netflix→Disney+ 顯示，另有永遠可用的更多 APP 入口。其他合格 TV App 由 Launcher 內部覆蓋式面板探索及啟動；首頁不再直接鋪滿所有 App。依 D8 加入五項唯讀資訊區：本機時間、日期與星期、Wi-Fi／網路、HDMI、本機儲存；這些都是狀態訊息而非工具，資訊未知或不可用時仍保留各欄位。

未安裝或沒有合格可啟動入口就隱藏並壓縮，不補空位、不用其他 App 替代、不自動導商店。三者全缺時仍保留兩工具與更多 APP。實際 package/component mapping 未確認，不把名稱或推測當身分證據。

無廣告承諾仍限本 APK 自有首頁及面板；原廠與外部 App 的廣告、登入或播放不是本 APK 可消除的。HOME 維持原廠；未來接管是已確認的高優先級產品目標，但不是本版完成條件，也不是當前試驗授權。

## 文件範圍與權威

01 為基線與決策；02 為目前有效 Feature／AC／行為契約；03 只含 Active Gherkin；04 為現行與歷史 ID 追溯、覆蓋與靜態檢查；05 為已關閉產品決策、待驗證技術 Gate 與風險。舊版行為只保留在 04 的明示歷史對照，不再當作現行要求。所有文件版本統一為 Specification Baseline v1.1 — Approved for UI/UX Design；產品需求與行為基線已核准，完整 UI/UX 最終驗收仍 Pending。

## Approved Product Requirements（D1～D8；D8 為本次核准變更）

D1～D7 全部 Approved；SA-01～SA-07 亦已核准為行為基線。兩者均不等於技術能力已驗證。

| 決策 | 已確認內容 | 規格落點 |
|---|---|---|
| D1 | 首版從原廠 Google TV 手動進入，不接管／更改 HOME；未來接管為高優先級目標 | F01、F05、F08、F15；G-HOME 獨立 |
| D2 | 首頁只放實際合格的 YouTube、Netflix、Disney+，依此固定順序；其他 TV App 進更多 APP；用可驗證身分而非名稱 | F02、F06；實際 mapping 仍待確認 |
| D3 | D-pad、單一可見焦點、不循環、OK 操作、合理返回焦點、根 Back 退出、HOME 仍原廠 | F03–F05；新面板轉移細節依已核准 SA |
| D4（原始歷史決策） | v1.0：上方只保留重新整理、系統設定；下方功能為指定 App 與更多 APP；收藏編輯、排序編輯、桌布、時鐘不在本版 | D8 已修訂時鐘／唯讀資訊的排除；其他限制仍有效，F09／F10／F12 延期 |
| D5 | 保留 F07／F08 的公開設定入口與錯誤降級；HDMI 走原廠，不直接切換 | F07、F08、F13 |
| D6 | 更多 APP 使用覆蓋式面板，不是獨立頁或首頁下方展開；不要求系統 Overlay 權限；排除指定服務重複入口 | F02–F06、AC-F08-04 |
| D7 | 指定 App 無合格可啟動入口則隱藏、壓縮、不替補、不導商店；三者全缺仍保留更多 APP 與兩個工具 | F02、F03、F06 |
| D8 — Approved Product Requirements | 採第一張「首頁 A」視覺方向；保留時間、日期與星期、Wi-Fi／網路、HDMI、本機儲存五項唯讀資訊；不增加焦點、設定或輸入源切換功能 | F01 顯示範圍；F11 Must；細化 AC／BDD 已核准；完整 UI/UX 待最終驗收 |

## 基線與知識分類

**基線來源 B0：** `/Users/miller/Downloads/XIAOMI_GOOGLE_TV_LAUNCHER_SESSION_HANDOFF_20261025.md`（已完整閱讀）。以下「已驗證」指該交接文件記載的歷史驗證，不代表本次重新連線或驗證。

| 類型 | 已知內容 | 對規格的限制 |
|---|---|---|
| 已驗證事實 B0-V1 | Xiaomi `MiTV-MOOR0`，device/product `venom`，Android 11、API 30、`armeabi-v7a`（32-bit ARM） | 第一優先實機；不能只交 arm64 native 依賴 |
| 已驗證事實 B0-V2 | 模擬器 Television 1080p、Google TV Android 16/API 36、`arm64-v8a`，model `sdk_google_atv64_amati_arm64`，serial `emulator-5554`，曾完成 boot/device 驗收 | 它是另一測試目標，不是 Xiaomi 替身 |
| 已驗證事實 B0-V3 | 兩端當時原廠 HOME 為 `com.google.android.apps.tv.launcherx/.home.HomeActivity` | 不硬改、不移除；先保持此路徑 |
| 已驗證事實 B0-V4 | 實機另有 `com.google.android.tungsten.setupwraith/.RecoveryActivity`（priority 1）、`com.android.tv.settings/.system.FallbackHome`（priority -1000）；原廠 HOME priority 2 | 查到候選並不證明第三方接管可行；三套件均不得停用 |
| 已驗證事實 B0-V5 | 實機當次 IP `192.168.1.117`，TCP `192.168.1.117:5555` 可連，USB debugging 已啟用、沒有獨立 Wireless debugging 選項 | IP 會變；未來操作先確認，ADB 始終明確指定 `-s`；本次不連線 |
| 既定產品／架構約束 B0-R1 | Kotlin 原生 APK，不修改／重刷 Google TV OS | 不是 browser shell、系統 ROM 或特權常駐工具 |
| 需要實機驗證 B0-U1 | 未安裝測試 Launcher、未改預設 HOME、未 Root；第三方啟動、播放與 HOME 接管未驗證 | 所有本版行為 Scenario 均為 Not Run |
| 技術假設 T1 | 以公開 TV 入口探索、精確 component 啟動、公開設定 action 可實現手動首頁 | 官方 API 只支持方案方向；OEM 行為仍待 Gate |
| 待技術決策 T2 | Compose for TV 或 Android Views；minSdk=30 只是候選；compileSdk、targetSdk、AGP/Kotlin/Gradle/JVM 尚未鎖定 | 不在需求文件偷渡工具鏈與框架選型 |

### 原環境與工作流程仍有效

- MacBook Pro Apple Silicon `arm64`；shell `/opt/homebrew/bin/zsh`；Antigravity IDE／`agy-ide` 1.107.0 arm64，未來以該 IDE 審核 Diff。
- Android Studio Rabbit 1／2026.2.1；SDK `/Users/miller/Library/Android/sdk`，ANDROID_HOME／PATH 已驗證；Platform `android-37.0`（API 37.0、Extension Level 22）、Platform-Tools 37.0.1、Build-Tools 36.0.0、Command-line Tools 23.0、Emulator 37.2.12。這些是 B0 的盤點值，不是本次查詢的最新版本。
- Studio JBR 25.0.3 arm64；Terminal 預設 Microsoft OpenJDK 11.0.16.1 x86_64（Rosetta），另有 Temurin 8 x86_64；當時無全域 JAVA_HOME／Gradle。保留舊 JDK，不改全域 Java，未來使用專案 Wrapper，必要時僅單次命令指定 JVM。
- AGP 9.4 + Gradle 9.6 + JBR 25 只屬候選，未經 Build/Sync 驗證；不得因已安裝 SDK 就稱可建置。
- 原定未來流程：**ChatGPT 規格審核 → Google Stitch UI/UX 與焦點／狀態審核 → Codex 按 BDD 做逐切片 TDD Red→Green→Refactor → Antigravity 審查 Git Diff／權限／相依性／部署 → 模擬器 → 另行批准實機手動開啟 → 最後才可考慮獨立 HOME Gate**。Codex 的實際連接方式待確認。
- 未來任何系統變更先取得明確批准與 rollback 計畫；本次沒有該授權。偵錯與 TCP 5555 的安全檢查保留給使用者核准的維護時段，不自動關閉或修改網路。

## Approved Behavioral Baseline（SA-01～SA-07）

以下行為已由使用者全部核准（Approved）；SA ID 僅保留追溯用途。D1～D7 與 Q1～Q5 均無待裁決產品事項。

| ID | 已核准規則（Approved） | 對應已關閉項（Closed） |
|---|---|---|
| SA-01 | 首頁內容焦點為壓縮後的指定服務序列＋更多 APP；左右相鄰、不換列，Up 到最近工具，工具 Down 回最近有效內容；首查未互動時到第一指定 App，無則更多 APP | Q1 |
| SA-02 | 面板提供一個面板內「關閉面板」控制項；它不是上方功能列項目。面板焦點序列為關閉面板＋其他 App，Up/Down 前後移動、Left/Right 保持。新開面板有 App 聚焦第一項，否則關閉面板 | Q1 |
| SA-03 | 面板 Back／關閉控制項只關面板並聚焦更多 APP；同一次按鍵的 repeat／key-up 不穿透。面板無刷新或設定副本，錯誤時關閉後用原工具列重試 | Q2 |
| SA-04 | 指定服務每個最多一個首頁入口；多元件／變體用證據化映射內的優先序選擇，面板排除所有已確認屬於該服務的入口；未確認映射顯示身分設定待確認，不冒充未安裝 | Q3 |
| SA-05 | 程序存活且同一操作階段恢復原所在範圍／stable key；程序重建從面板關閉的首頁開始。使用者關面板後的晚回應不可重新打開；重新打開面板是新一次，從第一項開始 | Q4 |
| SA-06 | 更新原子提交 H/O；移除焦點在原範圍按舊索引修復；首頁全缺到更多 APP，面板全空到關閉面板；有效工具／關閉控制項不被新 App 搶焦點 | Q4 |
| SA-07 | 初始驗收目標（待實機測量）：查詢／啟動 guard 5 秒，本機焦點／錯誤／關閉 1 秒，100 個其他 App 負載，真實 App 10 秒觀察；繁體中文自有文字、目前 Android 使用者邊界 | Q5 |

0.1 的 A01／A05／A06 中與 D1／D4／D5 相同部分已獲確認；A02 的全 App 首頁已被 D2 取代；A03／A04 的基本操作已由 D3 確認，但具體面板與恢復細節改列 SA-01～SA-06。A07／A08 延續為 SA-07；本次 SA-01～SA-07 全部已核准，但時間與負載目標尚未實測。

## MoSCoW 與範圍邊界

| Feature ID | 功能 | 本版 MoSCoW | 範圍／優先序 |
|---|---|---|---|
| F01 | 手動進入、無廣告且離線可用的首頁 | Must Have | MVP；Approved Product Requirements／Approved Behavioral Baseline |
| F02 | 指定 App 與其他 TV App 探索、識別及分區呈現 | Must Have | MVP；Approved Product Requirements／Approved Behavioral Baseline |
| F03 | 首頁與覆蓋式面板 D-pad 焦點契約 | Must Have | MVP；Approved Product Requirements／Approved Behavioral Baseline |
| F04 | 精確啟動與可恢復的啟動失敗 | Must Have | MVP；Approved Product Requirements／Approved Behavioral Baseline |
| F05 | Back、HOME、面板關閉與返回狀態恢復 | Must Have | MVP；Approved Product Requirements／Approved Behavioral Baseline |
| F06 | 雙區清單同步、空狀態與焦點修復 | Must Have | MVP；Approved Product Requirements／Approved Behavioral Baseline |
| F07 | 系統設定入口與失敗降級 | Must Have | MVP；Approved Product Requirements／Approved Behavioral Baseline |
| F08 | 實機相容性、非破壞性與可移除性 | Must Have | MVP；Approved Product Requirements／Approved Behavioral Baseline |
| F09 | 收藏常用 App | Should Have | 不納入本次 MVP |
| F10 | 手動排序 | Could Have | 不納入本次 MVP |
| F11 | 本機時間與唯讀裝置資訊 | Must Have | D8／F11 需求與行為已核准；技術能力仍待驗證 |
| F12 | 可更換背景 | Could Have | 不納入本次 MVP |
| F13 | HDMI／輸入源直達 | Could Have | 不納入本次 MVP |
| F14 | 手機 App 與進階 App 管理 | Won't Have | 不納入本次 MVP |
| F15 | 預設 HOME 接管（後續高優先級目標） | Won't Have | 不納入本次 MVP；未來高優先級，獨立 G-HOME |
| F16 | 推薦、雲端與 OS 改造 | Won't Have | 不納入本次 MVP |

F01–F08 加上 F11 為本版 Must 集合，面板是既有 Feature 的修訂而非額外不受控專案。收藏、使用者排序、自訂桌布仍不在本版；時鐘與五項唯讀資訊由 D8 納入 F11；不因本次面板設計需求偷偷加入新工具、搜尋或資料夾。覆蓋式面板的「關閉面板」屬已核准的面板內控制項，不是第三個上方工具。

## 完成定義與停止條件

本次完成指五份原檔更新為 Specification Baseline v1.1 已核准需求基線、Change Log 與舊／新 ID 追溯完整、全部現行 Must AC 有 Scenario、計數重新計算且靜態檢查通過；**不代表產品測試通過**。

v1.1 需求與行為基線已核准；未來 MVP 完成仍須完整設計獲批准、TDD／程式審查及 G1–G7 各自證據，模擬器不代替 Xiaomi API 30 armeabi-v7a。指定服務身分映射與多入口選擇須完成 G3；未裝指定 App 是支援分支，mapping 未確認或漏查不是成功分支。F07 的明示降級已由 D5 接受，但仍須驗證降級行為，不可記為直接設定成功。

G-HOME 不阻擋手動 MVP；所有系統變更仍另行批准。無原廠入口、實機 ABI 失敗、焦點陷阱、誤啟動背景首頁、原廠功能回歸或無法 rollback 都阻擋發布，不得用 root／停用原廠套件繞過。

完成本次修訂後停止，等待米勒下一步指示；不進 Stitch、程式開發、產品測試或任何裝置操作。
