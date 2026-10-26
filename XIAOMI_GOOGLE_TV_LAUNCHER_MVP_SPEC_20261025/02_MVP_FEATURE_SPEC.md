# 02｜MVP Feature Specification

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

## 規範狀態

Feature ID F01–F16 延續。D1–D7 與 F01～F08 的既有 MVP AC 為 Approved Product Requirements；D8 與 F11 細化 AC／BDD 已核准；SA-01～SA-07 為 Approved Behavioral Baseline。實際 mapping 與技術能力仍 Pending Technical Verification，不得把產品核准當成實機事實。舊 AC 同責任保留 ID 並標 Modified，新增 AC 追加編號；舊文字與變更關係見 04。

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

## v1.0 共用行為契約

**Approved Product Requirements：** D1–D7。**Approved Behavioral Baseline：** SA-01～SA-07；定義行為與可觀察狀態，不產出 UI 視覺設計，也不綁 Kotlin class／UI framework。

### 1. 合格集合與身分映射

- E 沿用目前 Android 使用者可見、已安裝、啟用、exported、沒有未授予啟動權限限制的 MAIN + LEANBACK_LAUNCHER activity/alias；stable key 是 package/component，alias 用 alias component。本 APK 與原三個 HOME／復原套件仍排除。
- 「可啟動」表示目前可解析且符合上述資格；不是預先替使用者啟動 App 來探測，更不是保證第三方不會當機。點擊時消失／停用等競態走 F04，成功快照要移除不再合格的入口；未知或暫時啟動拒絕不能假冒已確認卸載。
- H 是首頁指定入口集合：服務順序 YouTube→Netflix→Disney+，每服務按映射的核准優先序取一個仍合格的入口。名稱只顯示、不辨識服務；缺項直接壓縮。沒有可用的已核准候選時不隨機選另一 component。
- S 是經映射證據確認屬於上述三服務的 package／component／變體集合；O 為 E 中不屬於 S 的其他入口。**H 與 O 不重複，但不要求 H∪O=E**：指定服務其他入口會被抑制，不變成面板重複捷徑。O 才沿用名稱正規化排序與同套件多入口識別。
- 映射未確認的服務不建立首頁入口；顯示「指定 App 身分設定待確認」，不可宣稱未安裝。不能以名稱猜分類；尚未被證據分類的 E 入口暫按 O 顯示，且 G3 不得因此通過。發行前須解決所有指定服務的身分規則；某服務確實未安裝時用來源證據確認映射並記錄裝置缺少，不能把缺少歸因於 visibility。
- 新元件出現於已知服務但未列為核准可選首頁入口時，不能猜測啟動；在 S 範圍內則不放 O，保留待確認診斷。其他核准候選仍合格可依優先序選擇；否則隱藏該首頁入口，標示映射需檢查。這與整個服務真的未安裝不同。

| 服務 | 真實 package/component mapping | 證據與排序 | 本次驗證狀態 |
|---|---|---|---|
| YouTube | TBD — 不提供猜測套件名 | 需官方／可信套件身分來源、核准裝置盤點、TV component、版本／變體與優先序 | Not Verified |
| Netflix | TBD — 不提供猜測套件名 | 同上；不能用顯示名稱證明身分 | Not Verified |
| Disney+ | TBD — 不提供猜測套件名 | 同上；地區／版本變體須明列 | Not Verified |

### 2. 首頁焦點契約（SA-01）

- 上方功能列恰為「重新整理」「系統設定」；Left/Right 在兩者間移動，外側及 Up 保持。沒有第三個工具項。
- 首頁內容序列是 H（0～3 個，固定服務順序）後接「更多 APP」。Left/Right 移到相鄰項，兩端保持；Down 保持；Up 到最後工具焦點（沒有歷史則重新整理），並記住內容 stable key。工具 Down 回最近有效內容；無歷史／已失效修復後依序取 H 第一項、更多 APP。
- 首次 loading／error 聚焦重新整理；首次成功且未互動時 H 非空選第一服務入口，H 空選更多 APP。使用者一旦操作工具、內容或開面板，後到結果不得套用首次焦點搶走目前焦點。
- 隱藏 App 不佔欄、不佔焦點位置。此為抽象焦點圖，不決定像素、尺寸或版型；0.1 的首頁 C 欄全 App 網格規則已停止適用。

### 3. 覆蓋式面板焦點契約（SA-02／SA-03）

- OK 在更多 APP 開啟單一 Launcher 內部面板；首頁留在背後但無焦點標記、不接收操作，不形成獨立頁、不在首頁下方展開。這不是系統跨 App Overlay。
- 面板唯一互動序列：[關閉面板, O 的全部 App]。Up/Down 移相鄰項、首尾保持，Left/Right 保持；長按方向依收到的 repeat 逐項移動，不循環。新開面板有資料則第一 App 聚焦，否則關閉面板。
- 關閉面板是面板內控制項，不是新增首頁功能列。沒有面板內的設定／刷新副本。loading、empty、error 都保留它，Back 也能關閉。空或 loading 後新資料抵達時保留已有效的關閉焦點，Down 才到第一 App。
- 面板 App 的 OK 精確啟動該 component；Back 或關閉控制項的 OK 只關面板，回更多 APP。第二次獨立 Back 才退出根首頁。
- 一次實體按鍵（按下→repeat→放開）只歸屬開始時的層；開／關面板後不把同一次 OK／Back 再送新層。方向鍵沒有啟動語意。無需長按、滑鼠或觸控；額外受控背景輸入也不得觸發背景控制。
- 焦點項與標記要完整可見；焦點移往畫面外時捲動。全 Launcher 前景只有一個可見焦點，系統／外部 App 接管前景時不強求本 APK 有焦點。

### 4. 狀態模型與轉移（SA-03／SA-05／SA-06）

操作層與資料狀態分開：層為 Home／Panel／Background(origin)；資料為 loading／ready／empty／error／stale／refreshing；啟動中是附加 busy 狀態，不自行改變層。H 空不代表 O 空。

| 當前層／事件 | 下一層 | 可觀察結果／焦點 |
|---|---|---|
| Home → 更多 APP 的獨立 OK | Panel | 有 O 選第一 App；無資料／空／錯誤選關閉面板；背景失去焦點與操作能力 |
| Panel → Back 或關閉面板 OK | Home | 1 秒內更多 APP 聚焦，不退出；清除本次面板游標，背景查詢可繼續 |
| Home → Back | 外部原 task | 直接退出；原廠手動開啟 fixture 回原廠首頁，不硬編 component |
| Home/Panel → App OK | 原層＋launching，成功離開後 Background(origin) | 單次啟動；保持來源層與 stable key，不宣告只送請求即成功 |
| Panel＋launching → Back | Home | 只關面板；作廢原層 guard／本地錯誤回呼，不鎖 Back；不能保證撤銷已交給系統的外部啟動 |
| Home/Panel → HOME | 系統原廠 HOME | 不攔截、不改預設；保存同一存活操作階段的 origin |
| Background → 同一操作階段 resume | 保存的 Home 或 Panel | 立即查詢，存活來源仍可見；結果後保留 stable key，消失按原範圍修復 |
| 程序重建／全新開啟 | Home | 面板關閉；首次 loading／成功焦點規則，不持久保存焦點 |
| 任一層 → 成功更新 | 原層不變 | H/O 同版原子提交；只在當前範圍修復焦點，不自動開關面板 |
| 任一層 → 失敗／timeout | 原層不變 | 有舊資料保留 H/O 並標示 stale；無資料顯示 error，不當成 empty |
| Panel 關閉後晚到資料／舊啟動 guard | Home | 有效資料可更新；不重開面板、不搶更多 APP 焦點；過期 guard 訊息丟棄 |

同一操作階段指系統返回或再次帶回原存活 Launcher 實例；若 OEM 建立全新實例，按首次規則，不假定僅程序仍在就必然恢復舊畫面。第三方 Back 不保證會返回本 APK；只有真正再次前景才驗恢復。若面板啟動中已被使用者關閉，之後外部 App 才出現，其返回 origin 取目前 Home，不復活已關閉面板。從系統設定返回固定 Home＋系統設定焦點，因面板內根本沒有設定入口。

### 5. 更新、空狀態與錯誤恢復

- 首次、每次 resume、首頁手動刷新及前景已完成套件事件皆更新 E→H/O；開／關面板只切本機狀態，不另建立獨立快照或因開啟而自動重查。
- H、O 使用同一個完整成功快照提交，任一可見狀態都不能混用版本。只有最後有效查詢可提交；timeout／較舊／邏輯取消回應不覆蓋新資料。最多一個邏輯有效查詢與一個合併需求；底層不可取消工作仍可晚到但不採用。
- 首頁 App 消失：用舊 H 索引在新 H 取項，越尾取尾，H 空取更多 APP；面板 App 消失：用舊 O 索引在新 O 取項，越尾取尾，O 空取關閉面板。索引從 0 起，兩範圍不得混用。存在的 stable key 優先於索引。
- 有效控制項焦點（重新整理、系統設定、更多 APP、關閉面板）不被資料更新搶走；背景所記住的首頁內容也按 H 修復，但不得顯示第二個焦點。面板資料變空不自動關閉。
- 已確認映射且查詢成功後 H 空：首頁不放指定 App、不放佔位，保留兩工具及更多 APP；不必宣稱全部 TV App 不存在。成功 O 空：面板顯示「沒有其他可顯示的 TV App」及「僅顯示其他可遙控啟動的 TV App」。
- 查詢失敗：顯示「無法更新 App 清單，請重新整理」；有舊快照附「清單可能不是最新」。Panel 另顯示「按 Back 關閉後可重新整理」。失敗時不追加成功空狀態文案；舊空快照也以錯誤優先。恢復成功後清除錯誤／stale，再按新集合顯示 empty 或資料。
- 面板重試路徑：Back→更多 APP 焦點→Up 到工具→必要時 Left 到重新整理→OK；成功後 Down 回更多 APP→OK。不增加新的工具列或面板刷新按鈕。
- 1 秒錯誤反應由取得錯誤結果起算；5 秒查詢由接受該次有效查詢起算，deadline 相同時 timeout 優先。啟動 guard 5 秒只判斷 Launcher 是否仍前景，不保證外部 App 健康；字句改為「尚未離開 Launcher，請再試一次」，涵蓋首頁與面板。
- F01～F08 的時間與 100 筆負載都是 SA-07 已核准的初始驗收目標，仍需後續實機測量，不是實測。Fixture 中 A/B/C、Y1/Y2/Y3、p.a/A 等皆為合成識別子；真實指定服務 mapping 仍是 TBD。本版不額外加入帳號、跨 Android 使用者或 Google TV profile 管理。

## 探索排除集合與平台界線

排除本自訂 APK 的全部入口，以及 B0 明確列出的三個原廠 HOME／復原套件：

- `com.google.android.apps.tv.launcherx`
- `com.google.android.tungsten.setupwraith`
- `com.android.tv.settings`

排除整個 settings 套件只是避免重複捷徑；仍由 F07 的專用工具提供公開系統設定入口，不停用套件。原廠 Launcher 由 Back／HOME 保留可達，不用顯示其內部 recovery activity。其他系統 App 若符合 E 則按 H/O 身分分區規則處理。

「可見集合」不是逃避漏查的定義：G3 必須使用外部已知的合格入口清單核對完整性。Package Visibility 的宣告與 TV 入口選擇屬未來技術設計，不在此生成 Manifest。從安全性看，列入清單也不保證點擊時仍可啟動，因此 F04 必須處理競態。

## 功能詳細規格

### F01｜手動進入、無廣告且離線可用的首頁

- **優先級：** Must Have
- **User Story：** 身為家庭觀看者，我要直接看到自己的 App，而不是推薦內容，以便決定要看什麼。
- **功能描述：** 作為一般 TV App 手動開啟的替代首頁；不是已接管 HOME 的系統 Launcher。
- **行為規則：** 首頁包含上方重新整理／系統設定、H 的條件式入口、更多 APP 與必要訊息；D8 的 F11 唯讀資訊亦屬首頁狀態訊息，不是可互動工具；內部面板顯示 O 與關閉控制項（SA-02）。內建靜態背景；無帳號、廣告、推薦、追蹤或外部素材下載；限制只適用本 APK 自有畫面。
- **相依性：** G0/G1/G2；F02、F03；F11 為獨立非阻擋資訊區，不是啟動首頁的先決條件；原廠入口須另行實測
- **技術風險：** 高：LEANBACK 入口在 Xiaomi 上的實際曝光未知。離線不代表第三方串流可播放。
- **分類／驗證：** 產品需求與 AC：Approved；SA-01～SA-07：Approved Behavioral Baseline；技術：Pending Technical Verification；產品測試：Not Run。

| Acceptance Criteria | v1.0 可驗收條件 |
|---|---|
| AC-F01-01 | 從原廠 App 清單選取本 APK 可進入首頁；不要求變更預設 HOME、授權特殊權限或登入。 |
| AC-F01-02 | 網路斷線且本機查詢可讀時，5 秒內提供指定 App 的條件式入口、更多 APP、重新整理與系統設定；自有首頁與面板皆無推薦、廣告、內容預覽、登入要求或網路依賴。 |

### F02｜指定 App 與其他 TV App 探索、識別及分區呈現

- **優先級：** Must Have
- **User Story：** 身為家庭觀看者，我要在首頁直接選擇 YouTube、Netflix、Disney+，並從更多 APP 找到其他 TV App。
- **功能描述：** 沿用 E 的安全探索及 stable key，改為首頁 H 的服務固定順序與面板 O 的名稱排序；實際指定服務身分映射仍待 G3 證據確認。
- **行為規則：** 依共用集合與映射契約處理 E、H、S、O；三個指定服務每個最多一個首頁入口，無合格入口隱藏；面板排除全部已確認服務入口，其他入口去重與排序。不得只按顯示名稱識別，不猜實際套件，不導商店。
- **相依性：** G3 的可見性與指定服務身分／元件映射；F06 的雙區原子快照；SA-04 的多入口政策已核准，真實映射與優先序仍待技術確認
- **技術風險：** 高：身分映射、地區變體、未知新元件與 Visibility 都可能導致錯分／漏查；mapping 未確認不能當成未安裝，亦不能宣告 G3 通過。
- **分類／驗證：** 產品需求與 AC：Approved；SA-01～SA-07：Approved Behavioral Baseline；技術：Pending Technical Verification；產品測試：Not Run。

| Acceptance Criteria | v1.0 可驗收條件 |
|---|---|
| AC-F02-01 | 探索合格 TV 入口集合 E，依已確認身分映射分配到首頁 H 與面板 O；排除 LAUNCHER-only、HOME-only、不合格入口及原有排除套件；已知合格基線漏查不得當成未安裝。 |
| AC-F02-02 | 其他 App 集合 O 依 component 去重，同套件不同入口保留且可辨識；依名稱 trim→Unicode NFC→casefold 的 code point 序，再以 package/component 原始字串序排序。此名稱排序只適用面板，不改首頁指定順序。 |
| AC-F02-03 | 名稱空白／讀取失敗時以 package/component 作名稱；圖示失敗用本機佔位圖；名稱／圖示錯誤不改變指定 App 的身分歸屬與固定順序，也不使其他 App 消失。 |
| AC-F02-04 | 面板 O 為 0、1、100 筆均可表示且全部可經遙控抵達，無靜默截斷；首頁指定 App 數量為 0 至 3，更多 APP 永遠存在；100 是負載基準而非硬上限。 |
| AC-F02-05 | YouTube／Netflix／Disney+ 由經核准且有來源證據的 package/component 映射辨識，不能比對顯示名稱。映射未確認與已確認但入口不存在分開標示；不能猜測實際 mapping 或將未確認當成已驗證未安裝。 |
| AC-F02-06 | 首頁 H 每個指定服務最多一個合格啟動入口，固定 YouTube→Netflix→Disney+ 順序；不可用者隱藏且不留空格、不替補、不導商店。三者皆缺仍有更多 APP 與原有兩個工具。 |
| AC-F02-07 | 面板 O 排除已確認屬於三個指定服務的全部 TV 入口（含同服務其他元件／已核准變體），不僅排除當下首頁選中的元件；其餘 E 入口依面板規則完整保留。多入口按映射中已核准優先序選首頁入口；不得用名稱或字串序猜選。 |

### F03｜首頁與覆蓋式面板 D-pad 焦點契約

- **優先級：** Must Have
- **User Story：** 身為只使用遙控器的家人，我要知道目前選到哪個項目，並能抵達每一個控制項。
- **功能描述：** 分開定義首頁工具／內容與覆蓋式面板的抽象焦點序列；不產出視覺 UI 設計。
- **行為規則：** 採 v1.0 共用焦點契約，0.1 首頁 C 欄網格已失效。面板 focus trap、關閉控制項、按鍵不穿透與預設焦點為已核准的 SA-01～SA-03 行為基線。
- **相依性：** F02；F06；G4；之後 Stitch 僅實現／審核此契約
- **技術風險：** 中高：不同遙控 repeat、overscan、低記憶體與 UI framework 焦點恢復差異。
- **分類／驗證：** 產品需求與 AC：Approved；SA-01～SA-07：Approved Behavioral Baseline；技術：Pending Technical Verification；產品測試：Not Run。

| Acceptance Criteria | v1.0 可驗收條件 |
|---|---|
| AC-F03-01 | 首次 loading／查詢失敗預設聚焦重新整理；首次成功且使用者未操作時，聚焦 H 第一項，H 空則更多 APP。使用者已操作工具或面板就保留該有效焦點；前景無系統覆蓋時只有一個可見焦點。 |
| AC-F03-02 | 首頁焦點遵循新版工具列與 H＋更多 APP 內容序列契約；面板遵循獨立的關閉面板＋O 導覽契約；每次方向鍵 1 秒內可觀察結果，不會自動啟動 App。 |
| AC-F03-03 | 首頁與面板各自邊界不循環；被隱藏入口不佔焦點位置；面板 O 空、loading 或無舊快照的 error 時，關閉面板仍可聚焦並操作；背後工具不成為面板的邊界出口。 |
| AC-F03-04 | 面板焦點移往可見區外的 App 時，1 秒內捲動使項目與標記完整可見；方向 repeat 僅依新版契約移動，不觸發 OK、跨範圍或循環。 |
| AC-F03-05 | OK 在更多 APP 開啟同一 Launcher 內的覆蓋式面板，不切獨立頁、不於首頁下方展開；有 O 時初始聚焦第一個 App，否則聚焦關閉面板；面板不要求系統 Overlay 特殊權限。 |
| AC-F03-06 | 面板開啟時只有面板互動項可取得焦點與接收操作；首頁 App、更多 APP、重新整理及系統設定均不可觸發；同一次 OK 或 Back 的 repeat／key-up 不得穿透到另一層。 |

### F04｜精確啟動與可恢復的啟動失敗

- **優先級：** Must Have
- **User Story：** 身為觀看者，我要按一次 OK 開啟選定 App，失敗時仍能操作首頁。
- **功能描述：** 啟動選定 TV component，不以名稱猜測或跳至商店。
- **行為規則：** 首頁／面板只在前景對目前焦點精確啟動；同一次 OK 與忙碌期間不重送。保持來源層；Back/HOME 不鎖住。面板關閉作廢舊層回呼但不能保證撤回已交系統的啟動；第三方登入／DRM／Back 仍不受本 APK 控制。
- **相依性：** F02、F03、F06；G5
- **技術風險：** 高：exported／權限競態、第三方特殊 task 行為；系統接受請求不等於目標成功顯示。
- **分類／驗證：** 產品需求與 AC：Approved；SA-01～SA-07：Approved Behavioral Baseline；技術：Pending Technical Verification；產品測試：Not Run。

| Acceptance Criteria | v1.0 可驗收條件 |
|---|---|
| AC-F04-01 | OK 對首頁或面板當下選定的精確 component 發出一次啟動；成功須有目標 App 可辨識前景畫面，未拋例外或發出請求不能當成成功。 |
| AC-F04-02 | 入口消失、停用或啟動拒絕時，1 秒內在當前操作層顯示「無法開啟此 App，請重新整理後再試」；不重導商店；仍存在則保留焦點，否則按 F06 範圍內修復；面板不因錯誤自動關閉。 |
| AC-F04-03 | 同一次 OK 的 repeat 與未完成啟動期間不重送啟動；5 秒內 Launcher 仍前景且無明確錯誤時解除忙碌並於當前層顯示「尚未離開 Launcher，請再試一次」。Back／HOME 不鎖住；離開原啟動層後的舊結果不得重新開面板或搶焦點。 |

### F05｜Back、HOME、面板關閉與返回狀態恢復

- **優先級：** Must Have
- **User Story：** 身為觀看者，我要能退出自訂首頁並回到原廠功能，返回時保留正在找的 App。
- **功能描述：** 僅定義本 APK 可控制的生命週期；不攔截系統 HOME 或強迫第三方 App 返回。
- **行為規則：** 根首頁 Back 直接退出；面板內 Back 只關面板並回更多 APP（SA-03）。錯誤非模態。存活同一操作階段依來源範圍恢復；全新實例面板關閉。不得以重新開原廠 component 模擬系統 HOME。
- **相依性：** F03、F06；G5；F15 不在本版
- **技術風險：** 高：外部 App Back 可能不返回本 APK，驗收須區分 controlled fixture 與真實 App，不能把未收到 resume 判為焦點失敗。
- **分類／驗證：** 產品需求與 AC：Approved；SA-01～SA-07：Approved Behavioral Baseline；技術：Pending Technical Verification；產品測試：Not Run。

| Acceptance Criteria | v1.0 可驗收條件 |
|---|---|
| AC-F05-01 | 面板未開的根首頁在 ready/loading/無指定 App/error/launching 狀態按一次 Back 退出，不確認、不循環；從原廠首頁手動開啟的 fixture 回到原廠首頁。 |
| AC-F05-02 | 程序存活且同一 Launcher 操作階段返回前景時重新查詢：首頁來源保留首頁與 stable key；面板來源保留面板與其 stable key；工具來源保留工具焦點。已消失則按原範圍修復，不強迫第三方 Back 返回。 |
| AC-F05-03 | 程序重建／全新開啟從面板關閉的首頁開始，依首次焦點規則，不要求持久保存面板／焦點、清資料、重設 HOME 或登入。 |
| AC-F05-04 | 根首頁或面板內按 HOME 都交由系統處理，基線預設不變時到原廠 HOME；再次手動開啟同一存活操作階段依保存範圍恢復，重建依 AC-F05-03；不宣稱接管 HOME。 |
| AC-F05-05 | 面板內 Back 或聚焦關閉面板後 OK 只關閉面板，1 秒內恢復首頁更多 APP 的唯一焦點，不退出 Launcher；loading/empty/error/launching 也能關閉；必須再一次獨立 Back 才退出根首頁。 |

### F06｜雙區清單同步、空狀態與焦點修復

- **優先級：** Must Have
- **User Story：** 身為觀看者，我要在安裝或移除 App 後看到更新，而且查詢失敗時知道如何重試。
- **功能描述：** 首次、resume、手動刷新及已送達的前景套件事件，皆從同一查詢更新 H 與 O；面板開關不另建快照。
- **行為規則：** H/O 同版原子提交；每次查詢最多 5 秒，丟棄逾時／失效／較舊結果。refresh 保留兩區舊資料；明確區別初錯／stale／成功空集合。按當前範圍修復焦點，更新不自動開關面板。
- **相依性：** F02、F03；G3/G4
- **技術風險：** 中高：廣播遺漏、套件更新中間態、慢 I/O、舊結果競態；不能承諾 OEM 廣播一定送達。
- **分類／驗證：** 產品需求與 AC：Approved；SA-01～SA-07：Approved Behavioral Baseline；技術：Pending Technical Verification；產品測試：Not Run。

| Acceptance Criteria | v1.0 可驗收條件 |
|---|---|
| AC-F06-01 | 首次、resume、首頁重新整理都查詢；自接受查詢起 5 秒內以同一成功快照更新 H 與 O，反映安裝、移除、啟用、停用及名稱／圖示／元件變更；首頁固定服務順序，面板名稱排序。 |
| AC-F06-02 | 前景收到已提交套件變更通知後 5 秒內同步 H 與 O，面板開啟也適用；背景不承諾即時，通知遺漏由 resume 或手動刷新補償。 |
| AC-F06-03 | 成功且 O 空才顯示「沒有其他可顯示的 TV App」；H 空不等於 O 空。初查失敗／5 秒 timeout 顯示「無法更新 App 清單，請重新整理」，不冒充空狀態；有舊快照則保留兩區資料並標示「清單可能不是最新」。面板可關閉後用原工具列重試。 |
| AC-F06-04 | 更新先保留當前範圍仍存在的 stable key／控制項；首頁 App 消失按舊 H 索引取新 H、超出取尾項、H 空取更多 APP；面板 App 消失按舊 O 索引取新 O、超出取尾項、O 空取關閉面板；焦點不可穿透／關閉面板。 |
| AC-F06-05 | H 與 O 原子提交同版本快照，舊查詢／逾時結果不得覆蓋新版；面板開關不觸發獨立不一致資料查詢；開關後抵達的結果只更新資料、依當前層修復焦點，不重開／關閉面板或把焦點還給舊層。 |

### F07｜系統設定入口與失敗降級

- **優先級：** Must Have
- **User Story：** 身為家庭使用者，我要進入既有電視設定，而不由 Launcher 代替我修改設定。
- **功能描述：** 提供一個「系統設定」工具入口；不複製設定面板、不自動變更系統值。
- **行為規則：** 優先使用公開系統設定 action，技術可行性由 G6 判定。不猜測 Xiaomi 私有 component；入口無法啟動時保留可聚焦項目並解釋。
- **相依性：** F03、F05、F06；G6
- **技術風險：** 中：公開 action 存在不等於 OEM handler 可用；若不可用可接受明示降級，但不能標記直接設定開啟通過。
- **分類／驗證：** 產品需求與 AC：Approved；SA-01～SA-07：Approved Behavioral Baseline；技術：Pending Technical Verification；產品測試：Not Run。

| Acceptance Criteria | v1.0 可驗收條件 |
|---|---|
| AC-F07-01 | OK 在有可啟動設定 handler 時顯示系統設定畫面；Launcher 不自動提交任何設定變更。 |
| AC-F07-02 | 沒有 handler 或啟動被拒絕時，1 秒內顯示「無法開啟系統設定，請按 HOME 使用原廠入口」，保留設定焦點；重新整理、指定 App（若有）及更多 APP 仍可達。 |
| AC-F07-03 | 系統設定返回且本 APK 再次前景時，重新整理清單並恢復設定入口焦點；不把焦點跳到第一個 App。 |

### F08｜實機相容性、非破壞性與可移除性

- **優先級：** Must Have
- **User Story：** 身為電視擁有者，我要隨時回到原有使用方式，不因自訂首頁失去電視功能。
- **功能描述：** 將安全與實機相容視為發布驗收，不把環境盤點當成 APK 通過。
- **行為規則：** 只安裝一般權限 Kotlin APK；不用 root、無障礙攔鍵、device owner、shell 權限、系統跨 App Overlay 特殊權限、原廠套件停用／移除或重刷；不改全域 Java、預設 HOME、開機自啟、網路／音量／輸入源／偵錯。允許本 APK 內的更多 APP 覆蓋式面板，兩種 overlay 不混淆。
- **相依性：** G0–G7；F01–F07；未來每次安裝／卸載需明確授權
- **技術風險：** 高：32-bit 相依庫、API 30 支援、OEM 設定與實機效能均未知；整機問題須先與變更前基線比較。
- **分類／驗證：** 產品需求與 AC：Approved；SA-01～SA-07：Approved Behavioral Baseline；技術：Pending Technical Verification；產品測試：Not Run。

| Acceptance Criteria | v1.0 可驗收條件 |
|---|---|
| AC-F08-01 | 同一版本交付物分別在 API 36 arm64-v8a 模擬器與 MiTV-MOOR0 API 30 armeabi-v7a 完成各自驗收；兩欄獨立記錄，不以模擬器通過替代實機；native 依賴若存在須含相應 ABI。 |
| AC-F08-02 | 在不變更預設 HOME 的前提，原廠 Launcher、setupwraith、FallbackHome 仍啟用；HOME、原廠設定、原有 HDMI 切換與核准串流 App 的基線 smoke path 無新增失效。 |
| AC-F08-03 | 經另行批准只卸載本自訂 APK 後，原廠 HOME 與既有 App 仍可使用；rollback 不要求清資料、root、停用套件或重刷。 |
| AC-F08-04 | 更多 APP 面板只存在本 Launcher 前景內；交付權限及流程不要求 SYSTEM_ALERT_WINDOW 或系統「顯示在其他應用程式上層」授權，不覆蓋外部 App，也不以特殊權限取得 HOME 控制。 |

### F09｜收藏常用 App

- **優先級：** Should Have
- **User Story：** 身為固定觀看者，我要少按幾次就找到常用 App。
- **功能描述：** Must 驗收後才評估的下一增量，不承諾納入本次 MVP。
- **行為規則：** 若納入，以 stable key 收藏／取消，獨立常用區，不改變完整 App 集合；移除 App 清除失效收藏；不讀使用紀錄。
- **相依性：** F02/F03/F06；需新增 BDD 並審核
- **技術風險：** 中：持久化、多區焦點與失效入口管理增加範圍。
- **分類／驗證：** 本版排除／延期決策已核准；未來功能細節仍屬候選，非本版實作授權；產品測試 Not Run，能力依相依 Gate 判定。

| Acceptance Criteria | v1.0 可驗收條件 |
|---|---|
| AC-F09-01 | 未來納入時：收藏後重新開啟仍存在；取消及套件移除後不保留失效捷徑。 |

### F10｜手動排序

- **優先級：** Could Have
- **User Story：** 身為觀看者，我想自行安排 App 順序。
- **功能描述：** 固定排序已滿足 MVP；拖拉或遙控重排不納入。
- **行為規則：** 若納入需明確移動模式、取消與重啟保存，不能覆寫系統 App 順序。
- **相依性：** F02/F03；需另訂收藏與完整區排序規則
- **技術風險：** 中高：遙控編輯模式及復原增加學習成本。
- **分類／驗證：** 本版排除／延期決策已核准；未來功能細節仍屬候選，非本版實作授權；產品測試 Not Run，能力依相依 Gate 判定。

| Acceptance Criteria | v1.0 可驗收條件 |
|---|---|
| AC-F10-01 | 未來納入時：移動一筆後順序持久保存，取消操作不改順序。 |

### F11｜本機時間與唯讀裝置資訊

- **優先級：** Must Have
- **User Story：** 身為家庭觀看者，我要在首頁查看時間、日期及裝置資訊，而不打斷遙控選 App 或誤入設定。
- **功能描述：** D8 將五項唯讀資訊納入 MVP；不新增首頁工具或面板控制項。
- **行為規則：** 遵循下列資料契約；保持 F01～F08 的離線、焦點、返回、App 查詢與安全行為。資訊是既有「狀態訊息」的擴充，不是推薦、操作入口或網路服務。
- **相依性：** D8；F01、F03、F05、F08；G1、G4、G6、G7；P11-01～P11-03 Approved／Closed。
- **技術風險：** 一般 APK 能讀到的欄位、來源可信度、隱私遮蔽、OEM／API 差異與前景更新負載均未驗證；資料不可用必須明示而非升權。
- **分類／驗證：** D8 與 F11 本節細化／AC／BDD 為 Approved Product Requirements／Approved Behavioral Baseline；技術 Pending Technical Verification；產品測試 Not Run。

| Acceptance Criteria | v1.1 已核准可驗收條件（均追溯 D8） |
|---|---|
| AC-F11-01 | 時間、日期與星期均由同一次本機系統時間／時區快照及系統 locale、日期格式、12/24 小時設定產生；不顯示秒。系統格式／時區／日期改變後更新，無網路校時、不改系統時間；未取得可用快照時顯示未知或不可用，不顯示設計稿時間。更新預算依已核准 P11-01。 |
| AC-F11-02 | Wi-Fi／網路欄只呈現可靠來源確認的目前預設網路類型及連接狀態；可安全取得 Wi-Fi 名稱才顯示，否則明示名稱無法取得。未連線僅由成功來源確認；未知、權限不足、查詢失敗不能顯示為未連線，也不能以 Wi-Fi 已連線推論網際網路可用；不掃描、不探測外網、不要求敏感權限。 |
| AC-F11-03 | HDMI 欄呈現可靠來源確認的連接埠識別及連接狀態，欄位標示「HDMI 連接狀態」；連接不等於目前選用輸入、訊號或播放。來源只覆蓋部分埠時標示部分資訊；空集合但完整性未確認屬未知。不能安全可靠讀取時保留欄位並顯示無法取得；不得猜 HDMI 編號或切換輸入。 |
| AC-F11-04 | 儲存欄明示「本機資料磁碟（Launcher 所在）」，只呈現同一快照的可用容量與總容量，依已核准 P11-03 使用十進位 GB（1 GB = 1,000,000,000 bytes），四捨五入一位小數；非零且不足 0.1 GB 顯示「<0.1 GB」。不合計 USB／NAS／其他磁碟，不稱硬體標稱容量或本 App 配額；總量須大於零、可用量介於零與總量，無效或存取失敗依資訊狀態契約降級。 |
| AC-F11-05 | 首頁五項資訊在正常、未知及不可用狀態都保留其標籤，皆非可聚焦／可啟用項；不新增 D-pad 節點。針對資訊區的啟用事件不得開設定、啟動 App、改網路／輸入源或觸發權限要求；D-pad、OK、Back、HOME 原契約不變。 |
| AC-F11-06 | 資訊成功、失敗、逾時及重試更新都不改 H/O 快照、目前 Home／Panel 層、App stable key 或工具／關閉控制項焦點；不自動開關面板、捲動 App 或取得第二焦點。資訊查詢獨立於 F06，不等待資訊即可開啟更多 APP、重新整理或系統設定。 |
| AC-F11-07 | 每個資訊欄位分別區分正常、未知、不可用，依下列狀態契約顯示；失效舊值不冒充最新值、過期回應不覆蓋新結果。正式執行值必須來自本機真實可讀資料；無資料不以固定範例、Mock、設計稿或上次已失效值補值。一般 APK 無安全可靠來源時以明示降級滿足資訊欄存在與可操作性要求，不要求敏感／特權權限；Mock 結果不證明實機能力。 |

#### F11 資訊資料契約（v1.1 已核准行為基線）

以下是 D8 已核准的驗收細化，不是已實機驗證的 API 能力；不指定 Kotlin class、UI framework 或私有元件。正式資料只讀本機系統，不做網路連線探測，不讀其他 App 私有資料，不加入裝置管理、設定捷徑、儲存清理或 HDMI 切換。一般非敏感權限如有必要仍須未來 Manifest 審查；本次不新增或請求任何權限，若需敏感／特權權限則降級。

| 狀態 | 判定 | 畫面（每個欄位獨立） |
|---|---|---|
| 正常 Available | 本次有效來源有值、範圍及語意已確認且未失效 | 只顯示已確認值；已確認未連線／未連接是正常的否定值，不是查詢錯誤 |
| 未知 Unknown | 初始／重新查詢尚未完成；資料空白、被遮蔽、無法判定或來源覆蓋不明 | 「狀態未知」；時間／日期可用「時間未知」「日期未知」；不填入零容量、假 SSID、預設 HDMI 或固定時間 |
| 不可用 Unavailable | 來源不支援、存取被拒、明確失敗、無效容量或超過已核准的初始驗收期限 | 「無法取得（原因）」；原因限不支援／權限不足／讀取失敗／資料無效／逾時，不要求授權或開設定 |

- 五個可識別資訊項為「時間」「日期與星期」「Wi-Fi／網路」「HDMI 連接狀態」「本機儲存」。子欄位可部分成功：網路已確認 Wi-Fi 連線但名稱被遮蔽，顯示連線狀態並明示「名稱無法取得」；未知子欄位不能推翻已知連線事實。
- P11-02（Approved／Closed）最小內容：網路只讀目前預設網路（Wi-Fi／乙太網路／其他／無預設網路）；不顯示 IP、MAC、訊號強度、網速或宣稱 Internet 可用。SSID 僅已有不需敏感權限的可靠來源才顯示；不可取得不阻擋。HDMI 只列來源確定的埠與連接狀態，不承諾列齊所有實體埠，不顯示目前使用中、解析度或訊號品質。空來源不是「全部未連接」的證據；只在明確完整覆蓋且全部否定時可顯示無已連接埠。
- P11-03（Approved／Closed）儲存只讀 Launcher 所在本機資料磁碟的 available-to-app 與 total bytes；可用量不是裝置全部 free bytes，總量不是本 App 空間配額；不計已用量或百分比，避免把系統保留量誤認為使用者資料。總量／可用量須同來源同快照；不跨快照配對，不猜硬體標稱容量。零可用量有效；負值、缺值、可用量大於總量或總量非正數均不能作有效容量呈現。缺值為未知，非法數值為不可用（資料無效）。
- 時間與日期／星期使用同一次快照，不混用跨午夜或跨時區值；時鐘不驗證外部世界的正確時間，也不擅自校時。時間資料失敗只降級時間／日期，不干擾其他資訊。
- 各欄位有獨立查詢世代；開始新讀取、resume、已知變更或資料失效時清除該欄舊的「目前值」，顯示未知；完成後改為正常或不可用。一次失敗不清除其他仍有效欄位。逾時／前次生命週期／被取代查詢的晚回應丟棄。資訊狀態與 H/O 狀態彼此獨立。
- 首次、resume、已接收的本機變更事件觸發讀取；前景週期補償漏事件，背景不輪詢、不保證即時。原「重新整理」仍只執行 F06，**不擴張工具行為**；資訊自動重試在下一次週期或 resume，不新增重試按鈕。面板開啟不阻止讀取，但不能為顯示資訊而關面板或遮蔽面板焦點。
- **P11-01（Approved／Closed，初始驗收目標仍需實機測量）**：時間每個分鐘邊界重讀；時間／日期／格式變更事件後 1 秒內顯示有效結果。網路前景每 30 秒、HDMI／儲存每 60 秒重讀；首次／resume／已收到變更事件立即安排讀取；每欄讀取 deadline 5 秒，結果後顯示預算 1 秒。相同 deadline 時逾時優先；下一週期以最近一次讀取開始時間起算，不重疊同欄有效請求。時間讀取也受 5 秒期限，未完成即顯示未知，逾時不可用；1 秒為來源已可讀時的目標，不要求等待故障來源。這些數字不是 SA-07 已核准預算的自動延伸，也不是測量結果。
- Gherkin 的時間、容量、SSID、HDMI 埠名僅為明示合成 fixture，用受控時鐘／來源／排程器驗規則；產品整合驗收必須分開記錄裝置、來源、權限與實際值。合法降級可滿足本資訊分支，但不得記為「HDMI 實值讀取成功」。

### F12｜可更換背景

- **優先級：** Could Have
- **User Story：** 身為電視擁有者，我想個人化背景。
- **功能描述：** 本版僅內建靜態背景，不提供圖片匯入、主題編輯或動態背景。
- **行為規則：** 若納入需離線本機來源、可還原預設；不引入線上素材 feed。
- **相依性：** 儲存權限／URI 生命週期另議
- **技術風險：** 中：大圖記憶體壓力與檔案存取權限。
- **分類／驗證：** 本版排除／延期決策已核准；未來功能細節仍屬候選，非本版實作授權；產品測試 Not Run，能力依相依 Gate 判定。

| Acceptance Criteria | v1.0 可驗收條件 |
|---|---|
| AC-F12-01 | 未來納入時：無效或被刪除圖片回退內建背景且 App 仍可操作。 |

### F13｜HDMI／輸入源直達

- **優先級：** Could Have
- **User Story：** 身為使用遊戲機的家人，我想直接切換輸入源。
- **功能描述：** 本版保留原廠 HDMI 路徑（F08），不交付直接切換。
- **行為規則：** 僅可在 G6 有公開且可安全啟動入口的證據後提案；不實作 TIF provider、私有 binder 或特權切換。
- **相依性：** G6；需獨立範圍核准及 BDD
- **技術風險：** 高：OEM 實作、權限與實體連接；模擬器無法證明 Xiaomi HDMI 可行。
- **分類／驗證：** 本版排除／延期決策已核准；未來功能細節仍屬候選，非本版實作授權；產品測試 Not Run，能力依相依 Gate 判定。

| Acceptance Criteria | v1.0 可驗收條件 |
|---|---|
| AC-F13-01 | 未來納入時：選擇已核准入口能到達系統輸入源選擇器；不可用時明示並保留原廠路徑。 |

### F14｜手機 App 與進階 App 管理

- **優先級：** Won't Have
- **User Story：** 身為進階使用者，我可能想開啟側載手機 App 或管理套件。
- **功能描述：** 本版排除 LAUNCHER-only App、安裝／卸載 UI、隱藏套件、資料夾與搜尋。
- **行為規則：** 不把無 TV 入口的套件硬塞進首頁；不承擔觸控轉遙控。
- **相依性：** 未來另開產品決策
- **技術風險：** 高：輸入相容與破壞性套件管理擴張。
- **分類／驗證：** 本版排除／延期決策已核准；未來功能細節仍屬候選，非本版實作授權；產品測試 Not Run，能力依相依 Gate 判定。

| Acceptance Criteria | v1.0 可驗收條件 |
|---|---|
| AC-F14-01 | 範圍驗收：首頁沒有手機 App 專區、安裝／卸載控制項、搜尋或資料夾；E 的規則不變。 |

### F15｜預設 HOME 接管（後續高優先級目標）

- **優先級：** Won't Have
- **User Story：** 身為電視擁有者，我最終希望按 HOME 就回到自訂首頁。
- **功能描述：** D1 已確認未來希望接管 HOME，列後續高優先級目標；本版 MoSCoW 仍為 Won't Have（本版不做），不是低優先級或永久排除。開機直達不因 HOME 目標而自動獲核准。
- **行為規則：** 不得根據 HOME 候選清單推論可接管；不以停用原廠、root、無障礙或重新映射按鍵繞過限制；失敗維持手動模式。
- **相依性：** G-HOME；後續試驗範圍、系統變更與 rollback 仍需明確授權
- **技術風險：** 高：OEM 政策、按鍵路由、重啟行為未知。
- **分類／驗證：** 本版排除／延期決策已核准；未來功能細節仍屬候選，非本版實作授權；產品測試 Not Run，能力依相依 Gate 判定。

| Acceptance Criteria | v1.0 可驗收條件 |
|---|---|
| AC-F15-01 | 範圍驗收：本版不註冊／請求替換預設 HOME，不改開機行為；未來試驗必有另行授權、可驗證的原廠恢復步驟及失敗停止條件。 |

### F16｜推薦、雲端與 OS 改造

- **優先級：** Won't Have
- **User Story：** 身為重視簡潔的使用者，我不要為首頁管理帳號或改造整台電視。
- **功能描述：** 不做內容推薦、廣告、雲端同步、多使用者管理、語音助理替代、天氣、Root 或刷機。
- **行為規則：** 不修改 Google TV OS，不攔截原有語音／音量／輸入源按鍵。
- **相依性：** F01/F08 安全原則
- **技術風險：** 高：超出授權且偏離本機 App Launcher 目的。
- **分類／驗證：** 本版排除／延期決策已核准；未來功能細節仍屬候選，非本版實作授權；產品測試 Not Run，能力依相依 Gate 判定。

| Acceptance Criteria | v1.0 可驗收條件 |
|---|---|
| AC-F16-01 | 範圍驗收：交付內容沒有上述模組、帳號要求或系統改造步驟。 |

## 平台依據（不等於實機證據）

- 當 App target Android 11/API 30 或以上時，套件查詢預設會被可見性規則過濾；可按套件或 intent signature 宣告必要可見範圍。本版方向是最小必要可見性，不預設要求 QUERY_ALL_PACKAGES。[1][2]
- TV App 的 LEANBACK_LAUNCHER 入口用於 TV launcher 曝光；HOME 是另一類入口。能出現在 App 清單不等於可取代系統 HOME。[3][7]
- Android TV 的官方導覽指南強調遙控器與可辨識焦點。本版的面板焦點序列、邊界、時限與焦點恢復是已核准的產品行為基線，不是官方強制版型。[4]
- 公開 ACTION_SETTINGS 用於開啟系統設定；它不構成 Xiaomi 此機型的可用性證明。[5]
- native shared library 的 ABI 必須匹配目標架構。本版規劃逐一檢查所有直接與間接 native 依賴；不是因 Kotlin 就假設全部相依性與 ABI 無關。[6]

- TV input service 是提供媒體來源並與系統 TV app 合作的機制，不能據此推論自訂 Launcher 可切換 Xiaomi HDMI；因此 F13 保持非 MVP 候選，實際能力另驗。[8]

來源完整登錄亦見 05；所有平台結論均需套用 G1–G7 的實際證據。

## Sources

[1] https://developer.android.com/training/package-visibility
[2] https://developer.android.com/training/package-visibility/declaring
[3] https://developer.android.com/training/tv/get-started/create
[4] https://developer.android.com/training/tv/get-started/navigation
[5] https://developer.android.com/reference/android/provider/Settings
[6] https://developer.android.com/ndk/guides/abis
[7] https://developer.android.com/reference/android/content/Intent
[8] https://developer.android.com/training/tv/tif/tvinput
