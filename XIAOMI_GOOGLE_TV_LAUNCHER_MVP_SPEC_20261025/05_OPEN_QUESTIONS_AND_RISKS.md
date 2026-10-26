# 05｜Open Questions, Risks and Technical Gates

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

## 目前狀態與證據界線

- B0 的環境盤點與原廠 HOME 查詢：**歷史已驗證事實**，詳 01；本次未重新操作裝置。
- 新 Launcher 的全部能力：**Not Run／未驗證**。此文件的 Gate 是未來計畫，不是批准執行。
- 官方文件支持平台 API 的用途，不支持「MiTV-MOOR0 已能接管 HOME／切換 HDMI」之類結論。
- 任何模擬器 Pass 只對 API 36 arm64-v8a 目標有效；API 30 armeabi-v7a 必須獨立證據。

## 已核准產品決策與已關閉問題

D1～D7、F02 單一主要入口與 F05 同一存活操作階段的面板／焦點恢復均 Approved。使用者已接受 Q1～Q5 與 SA-01～SA-07；下表全部 Closed；它們不因 D8 重開，P11-01～P11-03 亦已 Approved／Closed。技術未知不因產品核准而關閉。

| 已關閉問題 | Approved Behavioral Baseline | 影響 |
|---|---|---|
| Q1 — Closed | SA-01／SA-02：首頁內容左右移動，Up 到工具；面板用 Up/Down 的獨立序列，提供面板內關閉控制，Left/Right 保持；首次／新開面板焦點依 02 | 不改已確認 D-pad 原則；核准的是抽象焦點關係，不是 UI 設計 |
| Q2 — Closed | SA-03：Back／關閉控制只關面板回更多 APP，同次按鍵不穿透；面板無重複工具，錯誤時關閉後從原工具列重試 | 保持工具列恰兩項；若要求面板內重試，需另明確調整範圍 |
| Q3 — Closed | SA-04：每指定服務選一個核准入口、排除該服務全部已確認入口／變體的面板重複；未確認 mapping 顯示待確認而非未安裝 | 多入口優先序與 package/component 的具體值是技術確認，不請使用者猜套件名 |
| Q4 — Closed | SA-05／SA-06：同一存活操作階段回來源層與 stable key，重建回關閉面板的首頁；消失在原範圍以舊索引修復；更新不自動開關面板 | 新增／刪除／回前景時焦點與面板狀態可預測 |
| Q5 — Closed | SA-07：沿用 5 秒查詢／guard、1 秒本機反應、100 其他 App 負載、10 秒真實 App 觀察，以及繁體中文與目前 Android 使用者範圍 | 均為已核准行為／初始驗收目標，仍待實機測量；不是實測效能，也未核准支援全部 profile |

### 技術待確認事項（不是新的產品訪談）

- **T-MAP：** 三個指定服務的真實 package/component、來源證據、變體／版本、精確首頁候選優先序、全部應排除面板入口的身分範圍。三者目前均 TBD／Not Verified；不在這次用網路猜一組套件名填入。
- **T-MAP-UPDATE：** 已知套件更新出現未知元件時如何核對映射；不能把未知元件當可啟動指定入口。已核准候選都失效則隱藏並標示待檢查，與服務確實未裝分開。
- **T-LIFECYCLE：** Xiaomi 從原廠清單重開時是否回到原操作階段或新建實例；兩分支分別驗收，不宣稱已知。
- **T-UI／T-BUILD：** UI framework、SDK／JVM／AGP／Gradle、可見性宣告仍依原流程在之後核准；面板的行為契約不是框架選型。

## Gate 矩陣（v1.0 已核准；v1.1 需求已核准；UI/UX 待最終審核；技術驗證 Not Run）

所有「操作／產物」都是**未來批准後**要做的事。此階段不 Build、不安裝、不發 ADB 指令。

| Gate／先後 | 能力與依據 | 驗證目標／前提 | 未來方法與可觀察 Pass 條件 | 失敗處理與發布影響 | 對應需求 |
|---|---|---|---|---|---|
| G0 | 規格、安全、設計流程 | 米勒核准五文件；再單獨核准 Stitch 設計 | v1.0 的 D1～D7、SA／AC 已核准；D8 首頁 A 方向與新增範圍已核准；v1.1 AC／BDD 細化已核准，完整設計對齊仍待最終審核，不能據方向選定宣稱所有設計核准；部署與 rollback 另行授權 | 未核准即停；不自動開發或設計 | 全部 Must |
| G1 | Android 11/API 30、32-bit ARM、API 36 模擬器差異；native ABI [6] | B0 兩個目標；不更動全域 JDK | 先審核套件 API 需求與完整依賴樹，再於核准開發期實際 Sync/Build；候選 APK 逐一確認 native ABI，兩端各自可安裝與開啟；記錄 artifact 雜湊／API／ABI；沒有 native 時記錄 none | API 30 無法啟動或缺 armeabi-v7a 就阻擋；不能只驗 arm64；不私自升級系統或工具鏈 | F08；AC-F08-01 |
| G2 | 本 APK 的 LEANBACK_LAUNCHER 曝光與 HOME 區別 [3][7] | 手動啟動模式，無 HOME 註冊與切換 | 原廠 App 清單可見自訂入口，以遙控開啟；前後 HOME 解析不變；不是只以 shell 直接啟動成功 | 入口不曝光即阻擋；不能用 ADB 啟動假裝家庭可用，更不能停用原廠 | F01、F05、F08 |
| G3 | Android 11 Package Visibility、TV／Google TV App Discovery [1][2][3] | 核准目標 targetSdk，已知合格 App 基線與可控套件 fixture | 在本 APK 身分查詢，不拿 shell 查詢代替；合格集合比對無漏項、無被排除項；需核對三指定服務身分來源、package/component 優先序及 H/O 排除分區；分別驗全有／部分／全缺與 mapping 未確認；再驗多入口、metadata、安裝／停用／更新／移除；所需可見性最小化 | 漏查或三指定服務 mapping 未確認都不通過；不得假裝是未安裝或只呈空清單；不自動加入 QUERY_ALL_PACKAGES 或修改系統權限 | F02、F06 |
| G4 | D-pad／Focus 與實機遙控 [4] | 核准後的焦點設計；可控 0/1/100 筆 fixture | 首頁工具／指定 App／更多 APP 可達；面板單焦點、trap、不穿透、空面板安全關閉、按鍵 repeat 隔離、捲動與安全區；測原有 1 秒回應與 5 秒資料預算；追加 F11 正常／未知／不可用資訊不入焦點圖，更新及長文字不搶焦點、不遮住操作項；P11-01 已核准為初始驗收目標、仍待實測 | 只用滑鼠可操作或模擬器鍵盤通過不算實機 Pass；需真遙控證據 | F03；F06 焦點修復；F11 |
| G5 | 第三方 App 啟動、Back／Home／resume／程序重建 | 受控 App 加核准真實 TV App；無系統 HOME 變更 | 精確 component 的目標前景證據；長按不重啟、拒絕／消失可恢復；根 Back 退出、面板 Back 只關面板；HOME 仍原廠；來源層與 stable key 恢復、關閉後晚回呼及程序重建分開驗收 | 送出 intent 不等於成功；不能強制第三方 Back 返回；受控返回與真實 App 路徑分別報告 | F04、F05 |
| G6 | 系統設定入口、HDMI 原廠路徑與直達能力界線 [5][8] | 公開設定 handler 探索；實機有既有 HDMI 來源且取得操作批准 | 設定 handler 存在時進入原廠設定；不存在／被拒絕時顯示 F07 指引。原廠 HDMI 切換在安裝前後 smoke path 都可用；本版不做直接 HDMI 控制；追加 F11 網路／HDMI 連接狀態／儲存／時間來源的逐欄可讀性、語意、單位與不可用降級檢查，方法見下節 | 設定可依已核准 F07 分支標 Degraded Accepted（不可記為直開 Pass）；原廠功能回歸阻擋。HDMI 直達只記候選能力，不阻擋本版 | F07、AC-F08-02、F11；F13 切換不在範圍 |
| G7 | 原廠 Launcher／復原保留、最小權限、rollback | 已記錄原廠狀態及自訂 package ID；卸載另行批准 | 內部面板不要求系統跨 App Overlay 權限且不覆蓋外部 App；前後基線 HOME、三個套件啟用狀態不變；設定、HDMI、核准串流路徑無新增回歸；只卸載自訂 APK 後可用原廠 HOME 與既有 App | 不可恢復或須停用系統套件才可用即阻擋；不得在未批准下嘗試修復系統 | F08；AC-F08-02/03 |
| G-HOME（最後、獨立、非 MVP 前提） | 第三方 HOME 候選／預設選擇／真實按鍵路由 [7] | D1 已確認未來高優先級目標；手動 MVP 通過後仍需另行核准試驗範圍、系統變更授權與 rollback | 先核實 OEM 是否提供受支援選擇途徑；若有，另訂試驗包與可恢復流程，再驗真 HOME 鍵、Back／重啟及回復原廠的證據。僅列出候選不算成功 | 不支持、授權不足或無可證明回復途徑則不試／停止；維持手動 MVP。禁止 root、停用任何原廠套件、無障礙攔鍵或私有繞過 | F15；不影響 F01～F08 及 F11 手動 MVP 完成 |

### Android 16 模擬器與 Android 11 實機不可互代

| 差異 | 模擬器可提供的證據 | 仍需要 Xiaomi 的證據 |
|---|---|---|
| API 36 vs API 30 | 新系統分支與受控錯誤驗收 | API 30 相依性、可見性、生命週期與設定處理 |
| arm64-v8a vs armeabi-v7a | 64-bit artifact 啟動 | 32-bit native library／實際安裝執行 |
| 虛擬輸入 vs 實體遙控 | 邏輯焦點及 repeat fixture | 真按鍵、延遲、Home/Back OEM 路由與 overscan |
| Google TV image vs Xiaomi OEM | 公開 action 在該 image 的 handler | Xiaomi 原廠入口、設定與 recovery 保留 |
| 虛擬裝置 vs HDMI／串流硬體 | 可控 App task 與 UI 故障案例 | 真 HDMI 來源、核准串流登入／DRM 的變更前後 smoke path |
| 開發機資源 vs 電視資源 | 邏輯 100 筆及逾時分支 | 實際記憶體壓力、載入預算與冷／暖啟動 |

上述差異由 B0 裝置盤點與本產品的驗證責任分解而來；表格是測試計畫，並非聲稱每項差異已造成問題。

## 建議驗證順序與授權界線

1. **v1.1 定稿後停止**：v1.1 需求與行為已核准；後續依使用者授權對齊首頁 A 與完整設計並完成 UI/UX 最終驗收；不得因視覺方向選定自動啟動 Stitch 或 Codex TDD。
2. **核准開發後先做靜態相容性／安全審查**：G1 的 API／ABI／JVM 與權限需求；保留全域舊 Java，候選工具鏈實際驗證前不稱已相容。
3. **先完整走模擬器**：G1→G2→G3→G4→G5→G6→G7 的適用部分。無 HDMI／真實 DRM 證據一律標 N/A（環境限制），不能當 Pass。
4. **使用者批准部署後，才走 Xiaomi**：重新確認目標 serial/IP 並明確 `-s`；先讀取並記錄基線，再安裝候選 APK 並從原廠 App 清單手動開啟；重走 G1–G7 適用部分。每個有副作用步驟事先批准；不得為製造 fixture 而停用／移除原廠套件。
5. **最後才另行授權 G-HOME 試驗**：不將此試驗捆綁為手動 MVP 驗收，也不在目前文件埋入設定預設的指令。

受控停用／移除／權限錯誤優先用 Component/UI fixture 或模擬器測試專用套件；實機只針對另行核准的非系統測試套件進行，不用真實家庭 App 製造破壞性故障。

## Rollback 設計（計畫，不是執行命令）

- **手動 MVP 安裝前**：記錄原廠 HOME 解析、三個原廠套件狀態、設定與 HDMI／串流基線；記錄將安裝的自訂 package ID、版本與 artifact 雜湊，避免誤卸載。
- **發生錯誤時**：面板內 Back 先關面板、再一次獨立 Back 退出；HOME 仍離開至原廠；不持續自動啟動、不鎖 HOME。若未完成安裝則先保留原廠環境，不需回復系統預設。
- **授權移除時**：只卸載核對過的自訂 APK；讀回確認自訂入口消失、原廠 HOME 及既有 App 可用。自訂首頁沒有收藏或外部資料，無雲端清理需求。
- **若原廠路徑不符基線**：停止進一步系統變更、保留紀錄並請使用者審核，不擅自清資料／Root／停用套件。
- **未來 HOME 試驗**：必須另有從 OEM 支援途徑回復原廠的可驗證步驟及失敗救援途徑；單純卸載不能事先假定一定能復原預設。沒有可行回復證據就不改 HOME。

## 風險登錄

| ID | 風險／分類 | 嚴重度 | 緩解與證據要求 |
|---|---|---|---|
| R01 | HOME 不可接管（未驗證能力） | 高但不阻擋手動 MVP | F15 為已確認的後續高優先級目標；本版延期，G-HOME 獨立且未驗證 |
| R02 | Package Visibility 導致清單不完整（平台風險） | 高 | G3 用本 APK 身分與已知合格清單比對；不能以 shell 權限結果替代 |
| R03 | 套件包含 arm64-only native 依賴／API >30（技術假設） | 高 | G1 檢查 transitive 依賴及實機 artifact，不因 Kotlin 就忽略 ABI |
| R04 | 真實遙控焦點遺失／捲動裁切（產品品質） | 高 | G4 0/1/100 與真遙控；後續設計審核確認安全區 |
| R05 | App 消失、拒絕、慢啟動或不返回（外部行為） | 高 | F04 guard／錯誤與 F05 生命週期；成功看目標前景，不卡住 Back/Home |
| R06 | 套件通知遺漏／舊查詢覆蓋新查詢（資料一致性） | 中高 | F06 resume／手動補償，最新版本提交、逾時丟棄；不能保證所有 OEM 都送通知 |
| R07 | 設定／HDMI 沒有可用公開入口（未驗證能力） | 中高 | 設定明示降級；HDMI 直達不在本版，不猜私有 component |
| R08 | 純名稱排序中文順序不符家人期待（產品假設） | 中 | 首頁已由 D2 確認服務固定順序；只有面板沿用名稱排序，細節已核准但使用體驗尚未實測，不加收藏或複雜排序 |
| R09 | 失去原廠功能或無法回復（安全） | 極高 | F08 與 G7 必須通過；不動三個原廠套件，不自改預設 |
| R10 | 工具鏈候選被當成已驗證（流程風險） | 高 | 保留 B0 已知／未知分界；G1 真實驗證，避免全域 Java 變更 |
| R11 | ADB TCP 5555 暴露與誤選裝置（環境風險） | 高 | 未來先確認當次 IP／serial，明確 `-s`；非開發時由使用者核准停用偵錯及檢查授權；本次不調整 |
| R12 | 把第三方串流／原廠廣告誤列 Launcher 保證（範圍風險） | 中高 | F01 無廣告限自有畫面；F08 用前後同條件 smoke path 查新增回歸 |
| R13 | 指定 App 身分錯認／mapping 未確認卻視為缺少 | 高 | G3 記錄來源、真實元件與變體；不以名稱識別，未確認不算完成 |
| R14 | 面板焦點穿透／Back repeat 直接退出 | 高 | F03 的 focus trap 與按鍵歸屬；F05 只關面板，再一次獨立 Back 才退出 |
| R15 | H/O 更新不同版、資料刷新重新打開面板 | 高 | AC-F06-05 原子快照及當前操作層優先，舊結果／guard 作廢 |
| R16 | 把內部面板誤作系統 Overlay 授權 | 高 | AC-F08-04；只在本 APK 前景顯示，不要求 SYSTEM_ALERT_WINDOW |

## 平台來源與推論限制

- [1][2] 說明 target API 30+ 的可見性過濾與按 intent／套件宣告範圍。**推論**：適合以最小可見性探索 TV App，但具體宣告仍要在 G3 證明。
- [3][7] 說明 TV launcher category 與 HOME category 的不同角色；不證明 OEM 允許第三方成為預設。本規格選擇不在 MVP 註冊 HOME。
- [4] 支持以遙控器檢查所有控制項可達與焦點導覽；不要求本版面板序列或時間預算；首頁服務順序由 D2 指定，面板細則為已核准的 SA 行為基線。
- [5] 定義公開系統設定 action；沒有 Xiaomi 專屬實測證據，故設 G6。
- [6] 說明 ABI 與 32-bit ARM；native 相依性需匹配實際目標，不以模擬器替代。
- [8] 描述提供媒體來源的 TV input service 與系統 TV app 分工。**推論／範圍決策**：不能因有 TIF 文件就推斷任意第三方 Launcher 可切換本電視的 HDMI；不開發 input service 來繞過未知能力。

此處保留 Draft 0.1 的 Android 官方來源與用途，不新增或重新聲稱本輪已查驗最新平台能力；本輪為使用者決策驅動的規格 Revision。B0 仍是使用者交接證據，官方 API 的存在仍不等於實機驗證。

## v1.1 已核准細節與待驗證資訊能力

| ID／狀態 | Approved Behavioral Baseline | 驗證界線 |
|---|---|---|
| P11-01 — Approved／Closed | 分鐘邊界更新時間；變更事件後可讀結果 1 秒內呈現；前景網路 30 秒、HDMI／儲存 60 秒；單欄讀取 5 秒 deadline，結果後 1 秒顯示；背景不輪詢，resume 重讀 | 已由本次使用者明確核准，初始驗收目標仍需實機負載／效能測量；詳 02 的起算與逾時優先規則 |
| P11-02 — Approved／Closed | 網路只呈現預設網路連接類型／狀態及安全可讀名稱；不呈現 IP／MAC／速度或 Internet 保證。HDMI 只讀來源確認的埠連接狀態，不表示當前輸入或訊號。重新查詢清除該欄舊值為未知，再依結果顯示；不保留未標示過期的舊值 | 資料範圍與降級文字已核准；五個資訊欄本身與不升權限制已核准 |
| P11-03 — Approved／Closed | 本機儲存只取 Launcher 所在資料磁碟的可用／總量，十進位 GB 一位小數；非零小值 <0.1 GB；不合計外接裝置、不顯示已用百分比 | 單位及磁碟範圍已核准；不是宣稱目前已可取得 |

### 技術待驗證清單（全數 Pending Technical Verification）

- **T-INFO-TIME**：系統 locale、日期格式、12/24 小時、時區與跨午夜的一致快照及更新通知；不加網路校時。
- **T-INFO-NET**：一般 APK 可讀的預設網路狀態與種類、Wi-Fi 名稱是否被遮蔽、來源是否需要敏感權限；需要敏感／特權權限就降級，不為 SSID 索權。不以裝置有網路推論 Internet 可用。
- **T-INFO-HDMI**：一般 APK 是否有可靠公開唯讀來源；資料究竟表示埠連接、輸入選擇或訊號，是否完整涵蓋全部埠；不得混用語意。能力不存在或證據不足時顯示未知／無法取得，不以私有介面、ADB、root 或系統套件調整補救。
- **T-INFO-STORAGE**：讀取範圍是否確為 Launcher 所在本機資料磁碟；available-to-app／total 的來源與快照一致性、保留空間差異、無效與缺值處理。不得誤稱為硬體標稱容量，不讀檔案內容或其他 App 使用量。
- **T-INFO-UPDATE**：API 30／32-bit 與 API 36／64-bit 的前景定時、事件遺漏、resume、並行查詢及過期回應；P11-01 的效能數字已核准為初始驗收目標，仍須獨立實機測量。
- **T-DESIGN-A**：首頁 A 的來源畫面 ID／版本或匯出檔尚未在本次提供；只記錄選擇，不臆造版型、字體、尺寸或設計完成證據。後續核對圖稿的示意值不得進入正式資料。

### G6 追加唯讀資訊驗證（計畫，未執行）

1. 在另行批准的開發／部署階段，依 G1→G7 流程分別記錄 API 36 模擬器與 MiTV-MOOR0 API 30 armeabi-v7a 的來源、API／ABI、必要的一般非敏感權限、資料語意與可用範圍；不能用 shell 或 Mock 查得資料代替本 APK 身分。
2. 對時間／網路／HDMI／儲存逐欄記錄實際值或無法取得原因；在安全唯讀條件下比對使用者可見的系統基線。沒有可靠比對就保持 Not Verified，不為補齊表格變更網路、輸入源或權限。
3. **讀取 HDMI 連接狀態 ≠ 切換 HDMI 輸入源**。前者屬 F11，可明示降級；後者仍為 F13 非 MVP。F08 既有原廠 HDMI 回歸要求不因 F11 降級而免除。
4. 來源不可用不阻擋 Launcher 主操作；但五欄保留、真實狀態文案、無焦點與無升權的降級行為仍需驗收，不能把「什麼都不顯示」當通過。F11 降級行為可 Pass，但實值讀取能力必須另列 Unavailable／Not Verified，不能因 Mock 顯示成功寫成 Xiaomi 可讀。
5. G4 同時回歸首頁／面板焦點與資訊更新，G7 審查沒有敏感／特權權限、網路探測、設定變更或示意資料 fallback。禁止新增裝置管理、停用原廠套件或其他特權操作。

| 風險 ID | 新增風險 | 控制方式 |
|---|---|---|
| R17 | Wi-Fi 名稱或 HDMI 來源受限／語意誤讀 | G6 逐欄證據，未知與不可用明示，不升權、不把連接當輸入選用 |
| R18 | 時間／容量示意值誤進正式執行 | 正式資料路徑無 fixture fallback，資料缺少保留欄位與降級；SC-114 |
| R19 | 資訊刷新造成焦點重建、卡住主要操作或增加負載 | F11 獨立資料狀態、G4 回歸、P11-01 實測，不重建 H/O |
| R20 | 資料磁碟容量被當成硬體標稱容量 | 明列範圍、十進位單位、可用量語意與同快照條件；G6 比對 |

## 停止點

本次完成五份 Specification Baseline v1.1 已核准需求基線後停止，等待米勒下一步指示；Pending Final UI/UX Design Review，Product Tests: Not Run。禁止自動進入 Stitch、Codex、TDD、Build、安裝、HOME 試驗或任何電視設定變更。

## Sources

[1] https://developer.android.com/training/package-visibility
[2] https://developer.android.com/training/package-visibility/declaring
[3] https://developer.android.com/training/tv/get-started/create
[4] https://developer.android.com/training/tv/get-started/navigation
[5] https://developer.android.com/reference/android/provider/Settings
[6] https://developer.android.com/ndk/guides/abis
[7] https://developer.android.com/reference/android/content/Intent
[8] https://developer.android.com/training/tv/tif/tvinput
