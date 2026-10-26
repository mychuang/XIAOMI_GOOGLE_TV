# 04｜Requirement Traceability and Revision Review

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

## v1.1 現行統計與 v1.0 → v1.1 變更

| 項目 | v1.0 歷史 | v1.1 現行已核准基線 |
|---|---:|---:|
| Feature 總數 | 16 | 16 |
| Must Feature | 8 | 9（F01～F08、F11） |
| Must AC | 35 | 42 |
| 非 MVP AC | 8 | 7 |
| 全部 AC | 43 | 49 |
| Active Scenario | 85 | 105 |
| Retired Scenario | 9 | 9 |
| 所有曾分配 Scenario ID | 94 | 114 |
| Scenario Outline／Examples | 0／0 | 0／0 |
| 產品測試 | Not Run | Not Run |

- D8：首頁 A 及五項唯讀資訊的範圍已核准；以下細化／AC／BDD 亦已核准。
- F01～F08：35 個 AC、85 個原 Active Scenario 的原文／ID／標籤／主要追溯全數保留；F01 只補充唯讀狀態資訊的顯示範圍，不增可互動工具。
- F11：Should → Must；更名為「本機時間與唯讀裝置資訊」。AC-F11-01 保留同一時間責任 ID，但由候選改為本版時間／日期／星期／系統格式需求；原候選「下一分鐘內」改由已核准 P11-01 明定預算，此為本次 v1.1 核准，不回溯冒稱原候選已核准。新增 AC-F11-02～AC-F11-07，共 7 個 F11 Must AC。
- 新增 20 個 Scenario：SC-095～SC-114；原 85 個沒有新增修改或退役，原 9 個 Retired 不復用，無新增退役 AC。
- 本次 AC 差異：42 個原 AC 原文不變、1 個修訂（AC-F11-01）、6 個新增；F11 原候選 AC 移入 Must，不重複計數。
- 原 85 列的 Revision 欄與 Scenario 的 0.1／0.2 tags 仍指原歷史遷移；本次全部 Unchanged from v1.0。F11 的 Added v1.1 才是本次新增。

### D8 → F11 → AC → Scenario（雙向追溯）

| 決策 | Feature／MoSCoW | AC | 主要責任 | Active Scenario |
|---|---|---|---|---|
| D8 | F11 / Must Have | AC-F11-01 | 時間、日期、星期及系統格式 | SC-095, SC-096, SC-097, SC-098 |
| D8 | F11 / Must Have | AC-F11-02 | 網路可靠值與降級 | SC-099, SC-100 |
| D8 | F11 / Must Have | AC-F11-03 | HDMI 連接狀態與降級 | SC-101, SC-102, SC-103 |
| D8 | F11 / Must Have | AC-F11-04 | 儲存單位、資料範圍與邊界 | SC-104, SC-105, SC-106 |
| D8 | F11 / Must Have | AC-F11-05 | 唯讀、不聚焦、不啟用 | SC-107, SC-108 |
| D8 | F11 / Must Have | AC-F11-06 | 更新不改焦點與主要操作 | SC-109, SC-110, SC-111 |
| D8 | F11 / Must Have | AC-F11-07 | 分欄狀態、更新與真實資料界線 | SC-112, SC-113, SC-114 |

F11 新 AC 與情境皆適用 P11-01～P11-03 已核准細化；未來測試需記錄該次採用的已核准預算。低層 fixture 證明呈現及降級規則，不證明 Xiaomi 有 HDMI／SSID 等真實讀取能力。G6 必須另存來源、權限、裝置與實值或不可用原因。既有 F08 相容性情境不改文；v1.1 發布另需 F11 在兩目標的資料來源與降級證據，不以 SC-046 的原範圍遺漏 F11。

## 歷史 v1.0 靜態複核統計（非 v1.1 現行數量）

| 項目 | 數量／狀態 |
|---|---:|
| Feature（保留 F01–F16） | 16 |
| Must Feature（F01–F08） | 8 |
| 現行 Must AC | 35 |
| 非 MVP 候選 AC | 8 |
| 全部現行 AC | 43 |
| 由 0.1 延續的 AC ID | 35 |
| 新增 AC ID | 8 |
| 修改文字的既有 AC ID | 21 |
| 沿用文字的既有 AC ID | 14 |
| 退役 AC ID | 0 |
| Active Scenario | 85 |
| 既有 Scenario 沿用 | 12 |
| 既有 Scenario 修改 | 35 |
| 新增 Scenario | 38 |
| Replaced（已退役，不計入 Active） | 9 |
| 所有曾分配的 Scenario ID | 94 |
| Scenario Outline／Examples | 0／0 |
| 產品測試 | Not Run |

0.1 的 56 是歷史基線，不是現行數量；0.2 Active = 沿用＋修改＋新增，不包括 Replaced。ID 未重用；所有原 AC 仍存在，但部分責任邊界已細化，完整舊新關係見下表。

### 歷史 v1.0 Feature 分布（F11 當時不在 Must）

| Feature ID | 功能 | Must AC | Active Scenario |
|---|---|---:|---:|
| F01 | 手動進入、無廣告且離線可用的首頁 | 2 | 3 |
| F02 | 指定 App 與其他 TV App 探索、識別及分區呈現 | 7 | 15 |
| F03 | 首頁與覆蓋式面板 D-pad 焦點契約 | 6 | 14 |
| F04 | 精確啟動與可恢復的啟動失敗 | 3 | 8 |
| F05 | Back、HOME、面板關閉與返回狀態恢復 | 5 | 15 |
| F06 | 雙區清單同步、空狀態與焦點修復 | 5 | 21 |
| F07 | 系統設定入口與失敗降級 | 3 | 4 |
| F08 | 實機相容性、非破壞性與可移除性 | 4 | 5 |

## Test Level 的責任分工

- **Unit Test**：純規則的資格篩選、名稱正規化／排序、去重、索引修復與版本提交規則。未來需可控制資料，不將 mock 呼叫當作實機可啟動證據。
- **Component Test**：受控查詢結果、逾時、生命週期、啟動拒絕／失效／競態；證明狀態轉換而非 OEM 能力。
- **UI Test**：D-pad／OK／Back、單焦點、訊息、捲動與空狀態；使用受控 fixture，不能只看視覺截圖猜測 stable key。
- **Emulator Integration Test**：API 36 arm64-v8a 的真 package／intent／task 與入口整合。
- **Physical Device Acceptance Test**：MiTV-MOOR0 API 30 armeabi-v7a、原廠 UI、真遙控、設定、HDMI、串流前後基線及 rollback。

每個標了 Physical Device Acceptance Test 的適用行為都要有 Xiaomi 證據；錯誤注入可由低層控制，不能為了測試而改壞家庭電視。標了 Emulator Integration Test 的真實 App 若該 image 無法取得，記 N/A／原因並保留受控目標結果，不能推論 Xiaomi Pass。F08 的相容性情境彙整其他驗收證據，不遞迴呼叫自身。


新增面板相關的 UI／Component 層級以可控 fixture 驗 scope、焦點與競態，Emulator／Physical 的 UI 現象需要各自證據。具體服務映射未確認時，即使合成 fixture 通過也不等於 G3 通過。Q1～Q5 已 Closed，對應行為已核准；Proposed Test Level 仍是未來測試分工，不是實作或測試執行授權。

## Active：Feature → Scenario → AC → Proposed Test Level

以下每列為主要 AC。共用契約仍適用；表中 `Not Run` 指產品狀態，Added／Modified 等僅指文件修訂。

| Feature ID | Scenario ID | 情境 | AC ID | Proposed Test Level | Revision | 產品結果 |
|---|---|---|---|---|---|---|
| F01 | SC-001 | 從原廠 App 清單手動進入 | AC-F01-01 | Emulator Integration Test / Physical Device Acceptance Test | Modified | Not Run |
| F01 | SC-002 | 離線首頁不依賴推薦服務 | AC-F01-02 | Component Test / UI Test / Emulator Integration Test / Physical Device Acceptance Test | Modified | Not Run |
| F01 | SC-003 | 首頁無推薦與追蹤網路依賴 | AC-F01-02 | Component Test / Emulator Integration Test / Physical Device Acceptance Test | Modified | Not Run |
| F02 | SC-004 | 篩選 TV 入口而非所有套件 | AC-F02-01 | Unit Test / Component Test | Modified | Not Run |
| F02 | SC-005 | 比對已知實機 TV App 防止可見性漏查 | AC-F02-01 | Emulator Integration Test / Physical Device Acceptance Test | Modified | Not Run |
| F02 | SC-006 | 去重而不合併同套件的不同入口 | AC-F02-02 | Unit Test / Component Test / UI Test | Modified | Not Run |
| F02 | SC-007 | 排序固定且名稱平手時可重現 | AC-F02-02 | Unit Test / Component Test | Modified | Not Run |
| F02 | SC-008 | 單筆名稱與圖示缺損可降級 | AC-F02-03 | Unit Test / Component Test / UI Test | Modified | Not Run |
| F02 | SC-010 | 一百個 App 均可抵達 | AC-F02-04 | Component Test / UI Test / Emulator Integration Test / Physical Device Acceptance Test | Modified | Not Run |
| F02 | SC-056 | 名稱正規化後平手仍使用穩定鍵 | AC-F02-02 | Unit Test / Component Test | Modified | Not Run |
| F02 | SC-057 | 三個指定 App 全可用時依固定順序 | AC-F02-06 | Unit Test / Component Test / UI Test / Emulator Integration Test / Physical Device Acceptance Test | Added | Not Run |
| F02 | SC-058 | 部分指定 App 缺少時壓縮順序 | AC-F02-06 | Unit Test / Component Test / UI Test / Emulator Integration Test / Physical Device Acceptance Test | Added | Not Run |
| F02 | SC-059 | 指定 App 全缺但其他 App 仍可用 | AC-F02-06 | Component Test / UI Test / Emulator Integration Test / Physical Device Acceptance Test | Added | Not Run |
| F02 | SC-060 | 同名其他 App 不冒充指定服務 | AC-F02-05 | Unit Test / Component Test / UI Test | Added | Not Run |
| F02 | SC-061 | 映射未確認不得冒充未安裝 | AC-F02-05 | Unit Test / Component Test / UI Test | Added | Not Run |
| F02 | SC-062 | 指定服務多入口不重複放進面板 | AC-F02-07 | Unit Test / Component Test / UI Test | Added | Not Run |
| F02 | SC-063 | 面板僅一個其他 App | AC-F02-04 | Component Test / UI Test | Added | Not Run |
| F02 | SC-093 | 指定 App 改名不改身分與首頁順序 | AC-F02-03 | Unit Test / Component Test / UI Test | Added | Not Run |
| F03 | SC-013 | 初次載入尚未完成仍可操作工具 | AC-F03-01 | UI Test | Unchanged | Not Run |
| F03 | SC-018 | 焦點移到視窗外時項目完整可見 | AC-F03-04 | UI Test / Emulator Integration Test / Physical Device Acceptance Test | Modified | Not Run |
| F03 | SC-055 | 初次載入完成不搶走已操作的工具焦點 | AC-F03-01 | UI Test / Component Test | Modified | Not Run |
| F03 | SC-064 | 首次成功依服務順序設定焦點 | AC-F03-01 | UI Test / Emulator Integration Test / Physical Device Acceptance Test | Added | Not Run |
| F03 | SC-065 | 首次指定 App 全缺聚焦更多 APP | AC-F03-01 | UI Test / Emulator Integration Test / Physical Device Acceptance Test | Added | Not Run |
| F03 | SC-066 | 新版首頁內容與工具雙向導覽 | AC-F03-02 | Unit Test / UI Test / Emulator Integration Test / Physical Device Acceptance Test | Added | Not Run |
| F03 | SC-067 | 沒有指定 App 的首頁仍有內容出口 | AC-F03-03 | Unit Test / UI Test / Emulator Integration Test / Physical Device Acceptance Test | Added | Not Run |
| F03 | SC-068 | 更多 APP 開啟 Launcher 內覆蓋式面板 | AC-F03-05 | UI Test / Emulator Integration Test / Physical Device Acceptance Test | Added | Not Run |
| F03 | SC-069 | 面板焦點與操作不穿透首頁 | AC-F03-06 | Component Test / UI Test / Emulator Integration Test / Physical Device Acceptance Test | Added | Not Run |
| F03 | SC-070 | 面板獨立的線性導覽 | AC-F03-02 | Unit Test / UI Test / Emulator Integration Test / Physical Device Acceptance Test | Added | Not Run |
| F03 | SC-071 | 面板 repeat 與邊界不循環 | AC-F03-04 | Unit Test / UI Test / Emulator Integration Test / Physical Device Acceptance Test | Added | Not Run |
| F03 | SC-072 | 更多 APP 為空仍有可聚焦出口 | AC-F03-03 | Component Test / UI Test / Emulator Integration Test / Physical Device Acceptance Test | Added | Not Run |
| F03 | SC-088 | 開啟面板的 OK 不穿透啟動第一個 App | AC-F03-06 | Component Test / UI Test / Emulator Integration Test / Physical Device Acceptance Test | Added | Not Run |
| F03 | SC-089 | 關閉面板的 Back repeat 不退出 Launcher | AC-F03-06 | Component Test / UI Test / Emulator Integration Test / Physical Device Acceptance Test | Added | Not Run |
| F04 | SC-020 | 啟動精確的同套件 TV 入口 | AC-F04-01 | Component Test / Emulator Integration Test / Physical Device Acceptance Test | Modified | Not Run |
| F04 | SC-021 | 核准的真實 App 前景可辨識 | AC-F04-01 | Emulator Integration Test / Physical Device Acceptance Test | Modified | Not Run |
| F04 | SC-022 | 點擊前入口已移除 | AC-F04-02 | Component Test / UI Test / Emulator Integration Test | Modified | Not Run |
| F04 | SC-023 | 啟動被系統拒絕 | AC-F04-02 | Component Test / UI Test / Emulator Integration Test | Modified | Not Run |
| F04 | SC-024 | OK 長按與忙碌期間不重複啟動 | AC-F04-03 | Component Test / UI Test / Emulator Integration Test / Physical Device Acceptance Test | Modified | Not Run |
| F04 | SC-025 | 未離開首頁的啟動可重試 | AC-F04-03 | Component Test / UI Test | Modified | Not Run |
| F04 | SC-087 | 首頁指定 App 點擊時已停用 | AC-F04-02 | Component Test / UI Test / Emulator Integration Test | Added | Not Run |
| F04 | SC-090 | 面板啟動中關閉不被晚到回呼重開 | AC-F04-03 | Component Test / UI Test | Added | Not Run |
| F05 | SC-026 | ready 根首頁 Back 退出而非攔截 | AC-F05-01 | UI Test / Emulator Integration Test / Physical Device Acceptance Test | Unchanged | Not Run |
| F05 | SC-027 | 從受控目標返回保留 stable key | AC-F05-02 | Component Test / UI Test / Emulator Integration Test / Physical Device Acceptance Test | Modified | Not Run |
| F05 | SC-028 | 從其他來源回到存活的首頁 | AC-F05-02 | Component Test / Emulator Integration Test / Physical Device Acceptance Test | Modified | Not Run |
| F05 | SC-029 | 程序被回收後採首次焦點 | AC-F05-03 | Component Test / UI Test / Emulator Integration Test / Physical Device Acceptance Test | Modified | Not Run |
| F05 | SC-030 | HOME 交由原廠處理 | AC-F05-04 | Emulator Integration Test / Physical Device Acceptance Test | Unchanged | Not Run |
| F05 | SC-050 | loading 根首頁 Back 可退出 | AC-F05-01 | UI Test / Emulator Integration Test / Physical Device Acceptance Test | Unchanged | Not Run |
| F05 | SC-051 | 無指定 App 的根首頁 Back 可退出 | AC-F05-01 | UI Test / Emulator Integration Test / Physical Device Acceptance Test | Modified | Not Run |
| F05 | SC-052 | error 根首頁 Back 可退出 | AC-F05-01 | UI Test / Emulator Integration Test / Physical Device Acceptance Test | Unchanged | Not Run |
| F05 | SC-053 | launching 根首頁 Back 可退出 | AC-F05-01 | UI Test / Emulator Integration Test / Physical Device Acceptance Test | Unchanged | Not Run |
| F05 | SC-073 | 面板 Back 只關面板再一次 Back 才退出 | AC-F05-05 | UI Test / Emulator Integration Test / Physical Device Acceptance Test | Added | Not Run |
| F05 | SC-074 | 空面板可以用 OK 關閉 | AC-F05-05 | UI Test / Emulator Integration Test / Physical Device Acceptance Test | Added | Not Run |
| F05 | SC-075 | 面板內 HOME 仍交給系統並恢復原範圍 | AC-F05-04 | Emulator Integration Test / Physical Device Acceptance Test | Added | Not Run |
| F05 | SC-084 | 外部 App 返回時來源項消失仍留在面板 | AC-F05-02 | Component Test / UI Test / Emulator Integration Test / Physical Device Acceptance Test | Added | Not Run |
| F05 | SC-085 | 重建後不保留面板且無指定 App 可用 | AC-F05-03 | Component Test / UI Test / Emulator Integration Test | Added | Not Run |
| F05 | SC-091 | 面板 loading 時 Back 仍可關閉 | AC-F05-05 | UI Test / Emulator Integration Test / Physical Device Acceptance Test | Added | Not Run |
| F06 | SC-031 | 首次查詢與手動重整更新資料 | AC-F06-01 | Component Test / UI Test / Emulator Integration Test / Physical Device Acceptance Test | Modified | Not Run |
| F06 | SC-032 | 背景變更在 resume 時補償 | AC-F06-01 | Component Test / Emulator Integration Test / Physical Device Acceptance Test | Modified | Not Run |
| F06 | SC-033 | 前景套件事件刷新 | AC-F06-02 | Component Test / UI Test / Emulator Integration Test / Physical Device Acceptance Test | Modified | Not Run |
| F06 | SC-034 | 同套件更新入口不殘留舊 component | AC-F06-02 | Component Test / Emulator Integration Test | Modified | Not Run |
| F06 | SC-035 | 空清單仍可重試恢復 | AC-F06-03 | Component Test / UI Test / Emulator Integration Test | Modified | Not Run |
| F06 | SC-036 | 首次查詢明確錯誤不冒充空集合 | AC-F06-03 | Component Test / UI Test | Modified | Not Run |
| F06 | SC-037 | 刷新失敗保留舊資料並可恢復 | AC-F06-03 | Component Test / UI Test / Emulator Integration Test | Modified | Not Run |
| F06 | SC-038 | 查詢逾時後拒絕晚到結果 | AC-F06-03 | Component Test / UI Test | Modified | Not Run |
| F06 | SC-039 | 移除焦點以原索引修復 | AC-F06-04 | Unit Test / Component Test / UI Test | Modified | Not Run |
| F06 | SC-040 | 移除尾端焦點選最後項目 | AC-F06-04 | Unit Test / Component Test / UI Test | Modified | Not Run |
| F06 | SC-042 | 較舊查詢不得覆蓋較新結果 | AC-F06-05 | Unit Test / Component Test / UI Test | Modified | Not Run |
| F06 | SC-076 | 面板開啟時安裝指定 App 不搶焦點 | AC-F06-02 | Component Test / UI Test / Emulator Integration Test / Physical Device Acceptance Test | Added | Not Run |
| F06 | SC-077 | 移除面板最後 App 不跳回首頁 | AC-F06-04 | Unit Test / Component Test / UI Test / Emulator Integration Test / Physical Device Acceptance Test | Added | Not Run |
| F06 | SC-078 | 移除首頁最後指定 App 回更多 APP | AC-F06-04 | Unit Test / Component Test / UI Test / Emulator Integration Test / Physical Device Acceptance Test | Added | Not Run |
| F06 | SC-079 | 面板初查失敗可關閉後重試 | AC-F06-03 | Component Test / UI Test / Emulator Integration Test | Added | Not Run |
| F06 | SC-080 | 面板刷新失敗保留兩區舊快照 | AC-F06-03 | Component Test / UI Test / Emulator Integration Test | Added | Not Run |
| F06 | SC-081 | 首頁與面板提交同一版本不得混用 | AC-F06-05 | Component Test / UI Test | Added | Not Run |
| F06 | SC-082 | 面板關閉後晚到成功不重開面板 | AC-F06-05 | Component Test / UI Test | Added | Not Run |
| F06 | SC-083 | 空面板變有 App 不搶關閉控制項焦點 | AC-F06-04 | Component Test / UI Test / Emulator Integration Test | Added | Not Run |
| F06 | SC-092 | 查詢中開面板不被首頁首次結果搶焦點 | AC-F06-05 | Component Test / UI Test | Added | Not Run |
| F06 | SC-094 | 面板內停用 App 只在面板修復焦點 | AC-F06-04 | Component Test / UI Test / Emulator Integration Test / Physical Device Acceptance Test | Added | Not Run |
| F07 | SC-043 | 開啟既有系統設定不代改值 | AC-F07-01 | Emulator Integration Test / Physical Device Acceptance Test | Unchanged | Not Run |
| F07 | SC-044 | 設定入口不存在時安全降級 | AC-F07-02 | Component Test / UI Test / Emulator Integration Test | Modified | Not Run |
| F07 | SC-045 | 設定返回保持工具焦點 | AC-F07-03 | Component Test / UI Test / Emulator Integration Test / Physical Device Acceptance Test | Unchanged | Not Run |
| F07 | SC-054 | 設定啟動被拒絕時安全降級 | AC-F07-02 | Component Test / UI Test / Emulator Integration Test | Modified | Not Run |
| F08 | SC-046 | 兩種環境分開記錄相容性證據 | AC-F08-01 | Emulator Integration Test / Physical Device Acceptance Test | Unchanged | Not Run |
| F08 | SC-047 | 保留原廠 HOME 與復原套件 | AC-F08-02 | Emulator Integration Test / Physical Device Acceptance Test | Unchanged | Not Run |
| F08 | SC-048 | 原廠 HDMI 設定與串流路徑回歸 | AC-F08-02 | Physical Device Acceptance Test | Unchanged | Not Run |
| F08 | SC-049 | 僅卸載自訂 APK 即可回復 | AC-F08-03 | Emulator Integration Test / Physical Device Acceptance Test | Unchanged | Not Run |
| F08 | SC-086 | 內部面板不要求系統 Overlay 授權 | AC-F08-04 | Emulator Integration Test / Physical Device Acceptance Test | Added | Not Run |
| F11 | SC-095 | 時間日期依同一系統快照與格式呈現 | AC-F11-01 | Unit Test / Component Test / UI Test | Added v1.1 / D8 | Not Run |
| F11 | SC-096 | 系統格式與時區變更同步重算 | AC-F11-01 | Unit Test / Component Test / UI Test | Added v1.1 / D8 | Not Run |
| F11 | SC-097 | 跨午夜時間日期星期一起更新 | AC-F11-01 | Unit Test / Component Test / UI Test | Added v1.1 / D8 | Not Run |
| F11 | SC-098 | 時間來源失敗不得使用設計稿固定時間 | AC-F11-01 | Unit Test / Component Test / UI Test | Added v1.1 / D8 | Not Run |
| F11 | SC-099 | 已知網路類型與名稱可部分降級 | AC-F11-02 | Component Test / UI Test | Added v1.1 / D8 | Not Run |
| F11 | SC-100 | 網路未知與拒絕不能當成斷線 | AC-F11-02 | Component Test / UI Test | Added v1.1 / D8 | Not Run |
| F11 | SC-101 | HDMI 連接證據不等於目前輸入 | AC-F11-03 | Component Test / UI Test | Added v1.1 / D8 | Not Run |
| F11 | SC-102 | HDMI 部分或空來源不冒充全部斷線 | AC-F11-03 | Component Test / UI Test | Added v1.1 / D8 | Not Run |
| F11 | SC-103 | HDMI 無安全來源時保留欄位降級 | AC-F11-03 | Component Test / UI Test | Added v1.1 / D8 | Not Run |
| F11 | SC-104 | 儲存容量具有明確單位及磁碟範圍 | AC-F11-04 | Unit Test / Component Test / UI Test | Added v1.1 / D8 | Not Run |
| F11 | SC-105 | 儲存零值小值及非法數值可辨識 | AC-F11-04 | Unit Test / Component Test / UI Test | Added v1.1 / D8 | Not Run |
| F11 | SC-106 | 儲存缺值及讀取失敗不假造容量 | AC-F11-04 | Unit Test / Component Test / UI Test | Added v1.1 / D8 | Not Run |
| F11 | SC-107 | 資訊區不加入既有方向鍵焦點序列 | AC-F11-05 | Component Test / UI Test | Added v1.1 / D8 | Not Run |
| F11 | SC-108 | 資訊區啟用事件不導頁不改設定 | AC-F11-05 | Component Test / UI Test | Added v1.1 / D8 | Not Run |
| F11 | SC-109 | 首頁資訊更新不搶 App 焦點 | AC-F11-06 | Component Test / UI Test | Added v1.1 / D8 | Not Run |
| F11 | SC-110 | 面板資訊更新不改焦點與開關狀態 | AC-F11-06 | Component Test / UI Test | Added v1.1 / D8 | Not Run |
| F11 | SC-111 | 資訊來源全部失敗仍可使用主要入口 | AC-F11-06 | Component Test / UI Test | Added v1.1 / D8 | Not Run |
| F11 | SC-112 | 逾時與舊回應不冒充目前資訊 | AC-F11-07 | Component Test / UI Test | Added v1.1 / D8 | Not Run |
| F11 | SC-113 | 前景週期與恢復補償不建立背景輪詢 | AC-F11-07 | Component Test / UI Test | Added v1.1 / D8 | Not Run |
| F11 | SC-114 | 正式資料缺失不能由示意資料補值 | AC-F11-07 | Component Test / UI Test / Emulator Integration Test | Added v1.1 / D8 | Not Run |

## 現行 Must AC 反向覆蓋

| Feature ID | AC ID | Active Scenario IDs |
|---|---|---|
| F01 | AC-F01-01 | SC-001 |
| F01 | AC-F01-02 | SC-002, SC-003 |
| F02 | AC-F02-01 | SC-004, SC-005 |
| F02 | AC-F02-02 | SC-006, SC-007, SC-056 |
| F02 | AC-F02-03 | SC-008, SC-093 |
| F02 | AC-F02-04 | SC-010, SC-063 |
| F02 | AC-F02-05 | SC-060, SC-061 |
| F02 | AC-F02-06 | SC-057, SC-058, SC-059 |
| F02 | AC-F02-07 | SC-062 |
| F03 | AC-F03-01 | SC-013, SC-055, SC-064, SC-065 |
| F03 | AC-F03-02 | SC-066, SC-070 |
| F03 | AC-F03-03 | SC-067, SC-072 |
| F03 | AC-F03-04 | SC-018, SC-071 |
| F03 | AC-F03-05 | SC-068 |
| F03 | AC-F03-06 | SC-069, SC-088, SC-089 |
| F04 | AC-F04-01 | SC-020, SC-021 |
| F04 | AC-F04-02 | SC-022, SC-023, SC-087 |
| F04 | AC-F04-03 | SC-024, SC-025, SC-090 |
| F05 | AC-F05-01 | SC-026, SC-050, SC-051, SC-052, SC-053 |
| F05 | AC-F05-02 | SC-027, SC-028, SC-084 |
| F05 | AC-F05-03 | SC-029, SC-085 |
| F05 | AC-F05-04 | SC-030, SC-075 |
| F05 | AC-F05-05 | SC-073, SC-074, SC-091 |
| F06 | AC-F06-01 | SC-031, SC-032 |
| F06 | AC-F06-02 | SC-033, SC-034, SC-076 |
| F06 | AC-F06-03 | SC-035, SC-036, SC-037, SC-038, SC-079, SC-080 |
| F06 | AC-F06-04 | SC-039, SC-040, SC-077, SC-078, SC-083, SC-094 |
| F06 | AC-F06-05 | SC-042, SC-081, SC-082, SC-092 |
| F07 | AC-F07-01 | SC-043 |
| F07 | AC-F07-02 | SC-044, SC-054 |
| F07 | AC-F07-03 | SC-045 |
| F08 | AC-F08-01 | SC-046 |
| F08 | AC-F08-02 | SC-047, SC-048 |
| F08 | AC-F08-03 | SC-049 |
| F08 | AC-F08-04 | SC-086 |
| F11 | AC-F11-01 | SC-095, SC-096, SC-097, SC-098 |
| F11 | AC-F11-02 | SC-099, SC-100 |
| F11 | AC-F11-03 | SC-101, SC-102, SC-103 |
| F11 | AC-F11-04 | SC-104, SC-105, SC-106 |
| F11 | AC-F11-05 | SC-107, SC-108 |
| F11 | AC-F11-06 | SC-109, SC-110, SC-111 |
| F11 | AC-F11-07 | SC-112, SC-113, SC-114 |

## 原 Scenario 逐項歷史對照（0.1 → 0.2）

此表是歷史，不是可執行情境。Replaced 代表原 ID 已退役，03 不再宣告該 Scenario；替代 ID 的主要 Feature／AC 可能因新分區責任而不同，詳 Active 表。Modified 保留原驗收目的，改 fixture／範圍／輸出；不冒稱文字未變。

| 原 Scenario | 原 Feature／AC | 原情境名稱 | 處置 | 現行／替代 ID | 原因／差異 |
|---|---|---|---|---|---|
| SC-001 | F01 / AC-F01-01 | 從原廠 App 清單手動進入 | Modified | SC-001 | 首頁內容與離線範圍含指定服務、更多 APP 及面板 |
| SC-002 | F01 / AC-F01-02 | 離線首頁不依賴推薦服務 | Modified | SC-002 | 首頁內容與離線範圍含指定服務、更多 APP 及面板 |
| SC-003 | F01 / AC-F01-02 | 首頁無推薦與追蹤網路依賴 | Modified | SC-003 | 首頁內容與離線範圍含指定服務、更多 APP 及面板 |
| SC-004 | F02 / AC-F02-01 | 篩選 TV 入口而非所有套件 | Modified | SC-004 | 探索／排序／metadata 驗收改在 H/O 正確範圍 |
| SC-005 | F02 / AC-F02-01 | 比對已知實機 TV App 防止可見性漏查 | Modified | SC-005 | 探索／排序／metadata 驗收改在 H/O 正確範圍 |
| SC-006 | F02 / AC-F02-02 | 去重而不合併同套件的不同入口 | Modified | SC-006 | 探索／排序／metadata 驗收改在 H/O 正確範圍 |
| SC-007 | F02 / AC-F02-02 | 排序固定且名稱平手時可重現 | Modified | SC-007 | 探索／排序／metadata 驗收改在 H/O 正確範圍 |
| SC-008 | F02 / AC-F02-03 | 單筆名稱與圖示缺損可降級 | Modified | SC-008 | 探索／排序／metadata 驗收改在 H/O 正確範圍 |
| SC-009 | F02 / AC-F02-04 | 只有一個 App 不製造空捷徑 | Replaced（Retired） | SC-058, SC-063 | 舊單一全 App 首頁改為指定 App 與面板兩個集合 |
| SC-010 | F02 / AC-F02-04 | 一百個 App 均可抵達 | Modified | SC-010 | 探索／排序／metadata 驗收改在 H/O 正確範圍 |
| SC-011 | F02 / AC-F02-04 | 零個合格入口不是查詢錯誤 | Replaced（Retired） | SC-059, SC-072 | 全清單為空不再等於首頁無可操作內容 |
| SC-012 | F03 / AC-F03-01 | 第一次有資料時單一焦點 | Replaced（Retired） | SC-064, SC-065 | 首次焦點改為指定順序且三個都缺時到更多 APP |
| SC-013 | F03 / AC-F03-01 | 初次載入尚未完成仍可操作工具 | Unchanged | SC-013 | 原行為與 D1–D7 不衝突，文字及主要 AC 沿用 |
| SC-014 | F03 / AC-F03-02 | 依邏輯列欄移動不啟動 App | Replaced（Retired） | SC-066, SC-070 | 舊首頁全 App C 欄網格已不適用；面板需獨立焦點契約 |
| SC-015 | F03 / AC-F03-02 | App 與工具雙向可達 | Replaced（Retired） | SC-066 | 首頁工具與內容已加入固定更多 APP，不能沿用舊網格入口 |
| SC-016 | F03 / AC-F03-03 | 不完整尾列及邊界不循環 | Replaced（Retired） | SC-067, SC-070, SC-071 | 舊首頁尾列邊界改由面板範圍獨立驗收 |
| SC-017 | F03 / AC-F03-03 | 空清單時工具不形成陷阱 | Replaced（Retired） | SC-067, SC-072 | 零指定 App 仍有更多 APP，Down 不再停在工具 |
| SC-018 | F03 / AC-F03-04 | 焦點移到視窗外時項目完整可見 | Modified | SC-018 | 焦點或捲動移至新版首頁／面板契約 |
| SC-019 | F03 / AC-F03-04 | 長按方向只重複導覽 | Replaced（Retired） | SC-071, SC-088 | 方向 repeat 必須按新版首頁／面板分開定義 |
| SC-020 | F04 / AC-F04-01 | 啟動精確的同套件 TV 入口 | Modified | SC-020 | 啟動來源明列首頁或面板；guard 文案涵蓋 Launcher |
| SC-021 | F04 / AC-F04-01 | 核准的真實 App 前景可辨識 | Modified | SC-021 | 啟動來源明列首頁或面板；guard 文案涵蓋 Launcher |
| SC-022 | F04 / AC-F04-02 | 點擊前入口已移除 | Modified | SC-022 | 啟動來源明列首頁或面板；guard 文案涵蓋 Launcher |
| SC-023 | F04 / AC-F04-02 | 啟動被系統拒絕 | Modified | SC-023 | 啟動來源明列首頁或面板；guard 文案涵蓋 Launcher |
| SC-024 | F04 / AC-F04-03 | OK 長按與忙碌期間不重複啟動 | Modified | SC-024 | 啟動來源明列首頁或面板；guard 文案涵蓋 Launcher |
| SC-025 | F04 / AC-F04-03 | 未離開首頁的啟動可重試 | Modified | SC-025 | 啟動來源明列首頁或面板；guard 文案涵蓋 Launcher |
| SC-026 | F05 / AC-F05-01 | ready 根首頁 Back 退出而非攔截 | Unchanged | SC-026 | 原行為與 D1–D7 不衝突，文字及主要 AC 沿用 |
| SC-027 | F05 / AC-F05-02 | 從受控目標返回保留 stable key | Modified | SC-027 | 原生命週期目的保留，新增來源層或無指定 App 邊界 |
| SC-028 | F05 / AC-F05-02 | 從其他來源回到存活的首頁 | Modified | SC-028 | 原生命週期目的保留，新增來源層或無指定 App 邊界 |
| SC-029 | F05 / AC-F05-03 | 程序被回收後採首次焦點 | Modified | SC-029 | 原生命週期目的保留，新增來源層或無指定 App 邊界 |
| SC-030 | F05 / AC-F05-04 | HOME 交由原廠處理 | Unchanged | SC-030 | 原行為與 D1–D7 不衝突，文字及主要 AC 沿用 |
| SC-031 | F06 / AC-F06-01 | 首次查詢與手動重整更新資料 | Modified | SC-031 | 更新與錯誤以 H/O 雙區、目前操作層驗收 |
| SC-032 | F06 / AC-F06-01 | 背景變更在 resume 時補償 | Modified | SC-032 | 更新與錯誤以 H/O 雙區、目前操作層驗收 |
| SC-033 | F06 / AC-F06-02 | 前景套件事件刷新 | Modified | SC-033 | 更新與錯誤以 H/O 雙區、目前操作層驗收 |
| SC-034 | F06 / AC-F06-02 | 同套件更新入口不殘留舊 component | Modified | SC-034 | 更新與錯誤以 H/O 雙區、目前操作層驗收 |
| SC-035 | F06 / AC-F06-03 | 空清單仍可重試恢復 | Modified | SC-035 | 更新與錯誤以 H/O 雙區、目前操作層驗收 |
| SC-036 | F06 / AC-F06-03 | 首次查詢明確錯誤不冒充空集合 | Modified | SC-036 | 更新與錯誤以 H/O 雙區、目前操作層驗收 |
| SC-037 | F06 / AC-F06-03 | 刷新失敗保留舊資料並可恢復 | Modified | SC-037 | 更新與錯誤以 H/O 雙區、目前操作層驗收 |
| SC-038 | F06 / AC-F06-03 | 查詢逾時後拒絕晚到結果 | Modified | SC-038 | 更新與錯誤以 H/O 雙區、目前操作層驗收 |
| SC-039 | F06 / AC-F06-04 | 移除焦點以原索引修復 | Modified | SC-039 | 更新與錯誤以 H/O 雙區、目前操作層驗收 |
| SC-040 | F06 / AC-F06-04 | 移除尾端焦點選最後項目 | Modified | SC-040 | 更新與錯誤以 H/O 雙區、目前操作層驗收 |
| SC-041 | F06 / AC-F06-04 | 移除最後 App 導向工具 | Replaced（Retired） | SC-077, SC-078 | 最後 App 消失的焦點需按首頁／面板分開修復 |
| SC-042 | F06 / AC-F06-04 | 較舊查詢不得覆蓋較新結果 | Modified | SC-042 | 更新與錯誤以 H/O 雙區、目前操作層驗收；快照版本責任由 AC-F06-04 移至新增 AC-F06-05，AC-F06-04 保留範圍焦點修復 |
| SC-043 | F07 / AC-F07-01 | 開啟既有系統設定不代改值 | Unchanged | SC-043 | 原行為與 D1–D7 不衝突，文字及主要 AC 沿用 |
| SC-044 | F07 / AC-F07-02 | 設定入口不存在時安全降級 | Modified | SC-044 | 無指定 App 時更多 APP 仍可達 |
| SC-045 | F07 / AC-F07-03 | 設定返回保持工具焦點 | Unchanged | SC-045 | 原行為與 D1–D7 不衝突，文字及主要 AC 沿用 |
| SC-046 | F08 / AC-F08-01 | 兩種環境分開記錄相容性證據 | Unchanged | SC-046 | 原行為與 D1–D7 不衝突，文字及主要 AC 沿用 |
| SC-047 | F08 / AC-F08-02 | 保留原廠 HOME 與復原套件 | Unchanged | SC-047 | 原行為與 D1–D7 不衝突，文字及主要 AC 沿用 |
| SC-048 | F08 / AC-F08-02 | 原廠 HDMI 設定與串流路徑回歸 | Unchanged | SC-048 | 原行為與 D1–D7 不衝突，文字及主要 AC 沿用 |
| SC-049 | F08 / AC-F08-03 | 僅卸載自訂 APK 即可回復 | Unchanged | SC-049 | 原行為與 D1–D7 不衝突，文字及主要 AC 沿用 |
| SC-050 | F05 / AC-F05-01 | loading 根首頁 Back 可退出 | Unchanged | SC-050 | 原行為與 D1–D7 不衝突，文字及主要 AC 沿用 |
| SC-051 | F05 / AC-F05-01 | empty 根首頁 Back 可退出 | Modified | SC-051 | 原生命週期目的保留，新增來源層或無指定 App 邊界 |
| SC-052 | F05 / AC-F05-01 | error 根首頁 Back 可退出 | Unchanged | SC-052 | 原行為與 D1–D7 不衝突，文字及主要 AC 沿用 |
| SC-053 | F05 / AC-F05-01 | launching 根首頁 Back 可退出 | Unchanged | SC-053 | 原行為與 D1–D7 不衝突，文字及主要 AC 沿用 |
| SC-054 | F07 / AC-F07-02 | 設定啟動被拒絕時安全降級 | Modified | SC-054 | 無指定 App 時更多 APP 仍可達 |
| SC-055 | F03 / AC-F03-01 | 初次載入完成不搶走已操作的工具焦點 | Modified | SC-055 | 焦點或捲動移至新版首頁／面板契約 |
| SC-056 | F02 / AC-F02-02 | 名稱正規化後平手仍使用穩定鍵 | Modified | SC-056 | 探索／排序／metadata 驗收改在 H/O 正確範圍 |

## 歷史新增 Scenario 對照（0.1 → 0.2）

| ID | Feature／AC | 新增目的 |
|---|---|---|
| SC-057 | F02 / AC-F02-06 | 三個指定 App 全可用時依固定順序 |
| SC-058 | F02 / AC-F02-06 | 部分指定 App 缺少時壓縮順序 |
| SC-059 | F02 / AC-F02-06 | 指定 App 全缺但其他 App 仍可用 |
| SC-060 | F02 / AC-F02-05 | 同名其他 App 不冒充指定服務 |
| SC-061 | F02 / AC-F02-05 | 映射未確認不得冒充未安裝 |
| SC-062 | F02 / AC-F02-07 | 指定服務多入口不重複放進面板 |
| SC-063 | F02 / AC-F02-04 | 面板僅一個其他 App |
| SC-064 | F03 / AC-F03-01 | 首次成功依服務順序設定焦點 |
| SC-065 | F03 / AC-F03-01 | 首次指定 App 全缺聚焦更多 APP |
| SC-066 | F03 / AC-F03-02 | 新版首頁內容與工具雙向導覽 |
| SC-067 | F03 / AC-F03-03 | 沒有指定 App 的首頁仍有內容出口 |
| SC-068 | F03 / AC-F03-05 | 更多 APP 開啟 Launcher 內覆蓋式面板 |
| SC-069 | F03 / AC-F03-06 | 面板焦點與操作不穿透首頁 |
| SC-070 | F03 / AC-F03-02 | 面板獨立的線性導覽 |
| SC-071 | F03 / AC-F03-04 | 面板 repeat 與邊界不循環 |
| SC-072 | F03 / AC-F03-03 | 更多 APP 為空仍有可聚焦出口 |
| SC-073 | F05 / AC-F05-05 | 面板 Back 只關面板再一次 Back 才退出 |
| SC-074 | F05 / AC-F05-05 | 空面板可以用 OK 關閉 |
| SC-075 | F05 / AC-F05-04 | 面板內 HOME 仍交給系統並恢復原範圍 |
| SC-076 | F06 / AC-F06-02 | 面板開啟時安裝指定 App 不搶焦點 |
| SC-077 | F06 / AC-F06-04 | 移除面板最後 App 不跳回首頁 |
| SC-078 | F06 / AC-F06-04 | 移除首頁最後指定 App 回更多 APP |
| SC-079 | F06 / AC-F06-03 | 面板初查失敗可關閉後重試 |
| SC-080 | F06 / AC-F06-03 | 面板刷新失敗保留兩區舊快照 |
| SC-081 | F06 / AC-F06-05 | 首頁與面板提交同一版本不得混用 |
| SC-082 | F06 / AC-F06-05 | 面板關閉後晚到成功不重開面板 |
| SC-083 | F06 / AC-F06-04 | 空面板變有 App 不搶關閉控制項焦點 |
| SC-084 | F05 / AC-F05-02 | 外部 App 返回時來源項消失仍留在面板 |
| SC-085 | F05 / AC-F05-03 | 重建後不保留面板且無指定 App 可用 |
| SC-086 | F08 / AC-F08-04 | 內部面板不要求系統 Overlay 授權 |
| SC-087 | F04 / AC-F04-02 | 首頁指定 App 點擊時已停用 |
| SC-088 | F03 / AC-F03-06 | 開啟面板的 OK 不穿透啟動第一個 App |
| SC-089 | F03 / AC-F03-06 | 關閉面板的 Back repeat 不退出 Launcher |
| SC-090 | F04 / AC-F04-03 | 面板啟動中關閉不被晚到回呼重開 |
| SC-091 | F05 / AC-F05-05 | 面板 loading 時 Back 仍可關閉 |
| SC-092 | F06 / AC-F06-05 | 查詢中開面板不被首頁首次結果搶焦點 |
| SC-093 | F02 / AC-F02-03 | 指定 App 改名不改身分與首頁順序 |
| SC-094 | F06 / AC-F06-04 | 面板內停用 App 只在面板修復焦點 |

## 歷史 AC ID 修訂對照（0.1 → 0.2；F11 現行條文見 02）

所有 0.1 AC ID 均保留；沒有借用退役 ID。Modified 意味同一需求責任在 0.2 的文字已有變更，驗收必讀 02 的現行文字；新增責任使用新尾碼。非 Must AC 只是候選／範圍條款，沒有隱含加入 MVP。

| AC ID | 狀態 | 0.1 責任摘要 | 0.2 責任／主要差異 |
|---|---|---|---|
| AC-F01-01 | Unchanged | 從原廠 App 清單選取本 APK 可進入首頁；不要求變更預設 HOME、授權特殊權限或登入。 | 同責任與文字沿用；仍受現行共用契約約束 |
| AC-F01-02 | Modified | 網路斷線且本機資料可讀時，5 秒內顯示合格 App 與工具入口；首頁沒有推薦、廣告、內容預覽與登入要求。 | 網路斷線且本機查詢可讀時，5 秒內提供指定 App 的條件式入口、更多 APP、重新整理與系統設定；自有首頁與面板皆無推薦、廣告、內容預覽、登入要求或網路依賴。 |
| AC-F02-01 | Modified | 完整列出 E，排除不合格入口；LAUNCHER-only 手機 App 不納入；僅有 HOME 的 activity 不視為 TV App。 | 探索合格 TV 入口集合 E，依已確認身分映射分配到首頁 H 與面板 O；排除 LAUNCHER-only、HOME-only、不合格入口及原有排除套件；已知合格基線漏查不得當成未安裝。 |
| AC-F02-02 | Modified | 相同 component 僅一筆；同套件多入口可分辨。依顯示名稱 trim 後 Unicode NFC、casefold 的 Unicode code point 序排序，平手依 package/component 原始字串序；不宣稱中文筆畫或拼音排序。 | 其他 App 集合 O 依 component 去重，同套件不同入口保留且可辨識；依名稱 trim→Unicode NFC→casefold 的 code point 序，再以 package/component 原始字串序排序。此名稱排序只適用面板，不改首頁指定順序。 |
| AC-F02-03 | Modified | 名稱空白或讀取失敗時以 package/component 作名稱；圖示讀取失敗時使用本機佔位圖；單筆資源錯誤不得使其他 App 消失。 | 名稱空白／讀取失敗時以 package/component 作名稱；圖示失敗用本機佔位圖；名稱／圖示錯誤不改變指定 App 的身分歸屬與固定順序，也不使其他 App 消失。 |
| AC-F02-04 | Modified | 0、1、100 筆合格入口皆可表示；100 筆全部可經遙控到達，無靜默截斷；100 為驗收負載而非硬上限。 | 面板 O 為 0、1、100 筆均可表示且全部可經遙控抵達，無靜默截斷；首頁指定 App 數量為 0 至 3，更多 APP 永遠存在；100 是負載基準而非硬上限。 |
| AC-F02-05 | Added | —（新增） | YouTube／Netflix／Disney+ 由經核准且有來源證據的 package/component 映射辨識，不能比對顯示名稱。映射未確認與已確認但入口不存在分開標示；不能猜測實際 mapping 或將未確認當成已驗證未安裝。 |
| AC-F02-06 | Added | —（新增） | 首頁 H 每個指定服務最多一個合格啟動入口，固定 YouTube→Netflix→Disney+ 順序；不可用者隱藏且不留空格、不替補、不導商店。三者皆缺仍有更多 APP 與原有兩個工具。 |
| AC-F02-07 | Added | —（新增） | 面板 O 排除已確認屬於三個指定服務的全部 TV 入口（含同服務其他元件／已核准變體），不僅排除當下首頁選中的元件；其餘 E 入口依面板規則完整保留。多入口按映射中已核准優先序選首頁入口；不得用名稱或字串序猜選。 |
| AC-F03-01 | Modified | 首次成功載入有 App 且尚未操作工具時聚焦排序第一筆；loading、無 App 或初次載入失敗時預設聚焦重新整理。若使用者已操作工具則保留該工具焦點。Launcher 位於前景且沒有系統覆蓋時永遠只有一個可見焦點。 | 首次 loading／查詢失敗預設聚焦重新整理；首次成功且使用者未操作時，聚焦 H 第一項，H 空則更多 APP。使用者已操作工具或面板就保留該有效焦點；前景無系統覆蓋時只有一個可見焦點。 |
| AC-F03-02 | Modified | 方向鍵依焦點契約移動，App 與兩個工具入口皆可互相抵達；焦點變更在按鍵事件後 1 秒內可觀察，不能自動啟動 App。 | 首頁焦點遵循新版工具列與 H＋更多 APP 內容序列契約；面板遵循獨立的關閉面板＋O 導覽契約；每次方向鍵 1 秒內可觀察結果，不會自動啟動 App。 |
| AC-F03-03 | Modified | 無相鄰項目的邊界保持原焦點；不循環、不跳到不存在的空格；空清單仍可在兩個工具間移動。 | 首頁與面板各自邊界不循環；被隱藏入口不佔焦點位置；面板 O 空、loading 或無舊快照的 error 時，關閉面板仍可聚焦並操作；背後工具不成為面板的邊界出口。 |
| AC-F03-04 | Modified | 移往畫面外項目時同時捲動，使該項目及其焦點標記完整可見；方向鍵 repeat 依序移動但不得產生 OK 或跨界循環。 | 面板焦點移往可見區外的 App 時，1 秒內捲動使項目與標記完整可見；方向 repeat 僅依新版契約移動，不觸發 OK、跨範圍或循環。 |
| AC-F03-05 | Added | —（新增） | OK 在更多 APP 開啟同一 Launcher 內的覆蓋式面板，不切獨立頁、不於首頁下方展開；有 O 時初始聚焦第一個 App，否則聚焦關閉面板；面板不要求系統 Overlay 特殊權限。 |
| AC-F03-06 | Added | —（新增） | 面板開啟時只有面板互動項可取得焦點與接收操作；首頁 App、更多 APP、重新整理及系統設定均不可觸發；同一次 OK 或 Back 的 repeat／key-up 不得穿透到另一層。 |
| AC-F04-01 | Modified | OK 對選定 component 發出一次啟動；成功驗收以該 App 的可辨識前景畫面為證據，不以呼叫未拋例外代替。 | OK 對首頁或面板當下選定的精確 component 發出一次啟動；成功須有目標 App 可辨識前景畫面，未拋例外或發出請求不能當成成功。 |
| AC-F04-02 | Modified | 入口消失、停用或被拒絕啟動時，1 秒內顯示「無法開啟此 App，請重新整理後再試」；首頁仍可操作且不重導商店；原項仍在則焦點保留，否則依 F06 修復。 | 入口消失、停用或啟動拒絕時，1 秒內在當前操作層顯示「無法開啟此 App，請重新整理後再試」；不重導商店；仍存在則保留焦點，否則按 F06 範圍內修復；面板不因錯誤自動關閉。 |
| AC-F04-03 | Modified | 同一次或尚未完成的啟動不重複送出；若 5 秒內沒有離開首頁且沒有明確錯誤，解除忙碌並顯示「尚未離開首頁，請再試一次」；不得宣稱外部 App 已成功。 | 同一次 OK 的 repeat 與未完成啟動期間不重送啟動；5 秒內 Launcher 仍前景且無明確錯誤時解除忙碌並於當前層顯示「尚未離開 Launcher，請再試一次」。Back／HOME 不鎖住；離開原啟動層後的舊結果不得重新開面板或搶焦點。 |
| AC-F05-01 | Modified | 根首頁（包含 loading、empty、error、launching）按 Back 可退出，無確認或退出循環；從原廠首頁開啟的驗收路徑回到原廠首頁。 | 面板未開的根首頁在 ready/loading/無指定 App/error/launching 狀態按一次 Back 退出，不確認、不循環；從原廠首頁手動開啟的 fixture 回到原廠首頁。 |
| AC-F05-02 | Modified | 程序仍存活且本 APK 再次前景時，重新查詢 App；原焦點 stable key 仍在則保留並使其可見；原為工具焦點則保留該工具。 | 程序存活且同一 Launcher 操作階段返回前景時重新查詢：首頁來源保留首頁與 stable key；面板來源保留面板與其 stable key；工具來源保留工具焦點。已消失則按原範圍修復，不強迫第三方 Back 返回。 |
| AC-F05-03 | Modified | 程序重建／全新開啟不要求持久保存焦點，採 F03 首次焦點；不得因此要求清除資料、重設 HOME 或重新設定。 | 程序重建／全新開啟從面板關閉的首頁開始，依首次焦點規則，不要求持久保存面板／焦點、清資料、重設 HOME 或登入。 |
| AC-F05-04 | Modified | HOME 按鍵交由系統處理，不攔截、不宣稱回到自訂首頁；基線預設未改時回到原廠 HOME，之後手動再次開啟可操作。 | 根首頁或面板內按 HOME 都交由系統處理，基線預設不變時到原廠 HOME；再次手動開啟同一存活操作階段依保存範圍恢復，重建依 AC-F05-03；不宣稱接管 HOME。 |
| AC-F05-05 | Added | —（新增） | 面板內 Back 或聚焦關閉面板後 OK 只關閉面板，1 秒內恢復首頁更多 APP 的唯一焦點，不退出 Launcher；loading/empty/error/launching 也能關閉；必須再一次獨立 Back 才退出根首頁。 |
| AC-F06-01 | Modified | 首次、resume 與按重新整理會查詢；成功後 5 秒內反映已提交的安裝、移除、啟用、停用、更新名稱／圖示／入口變化，排序重新計算。 | 首次、resume、首頁重新整理都查詢；自接受查詢起 5 秒內以同一成功快照更新 H 與 O，反映安裝、移除、啟用、停用及名稱／圖示／元件變更；首頁固定服務順序，面板名稱排序。 |
| AC-F06-02 | Modified | 前景接收到已完成套件變更通知時，5 秒內更新；背景不承諾即時；若 OEM 未送通知，resume 或手動重新整理必須補償。 | 前景收到已提交套件變更通知後 5 秒內同步 H 與 O，面板開啟也適用；背景不承諾即時，通知遺漏由 resume 或手動刷新補償。 |
| AC-F06-03 | Modified | 成功查得 0 筆時顯示「沒有可顯示的 TV App」及「僅顯示可遙控啟動的 TV App」；查詢拋錯或 5 秒未完成則顯示「無法更新 App 清單，請重新整理」，有舊資料則標示「清單可能不是最新」；重新整理可恢復。 | 成功且 O 空才顯示「沒有其他可顯示的 TV App」；H 空不等於 O 空。初查失敗／5 秒 timeout 顯示「無法更新 App 清單，請重新整理」，不冒充空狀態；有舊快照則保留兩區資料並標示「清單可能不是最新」。面板可關閉後用原工具列重試。 |
| AC-F06-04 | Modified | 刷新不搶走仍存在的焦點；消失時選舊索引在新排序中的項目，超出尾端選最後一筆，無 App 選重新整理；較舊查詢及逾時回應不覆蓋較新狀態。 | 更新先保留當前範圍仍存在的 stable key／控制項；首頁 App 消失按舊 H 索引取新 H、超出取尾項、H 空取更多 APP；面板 App 消失按舊 O 索引取新 O、超出取尾項、O 空取關閉面板；焦點不可穿透／關閉面板。 |
| AC-F06-05 | Added | —（新增） | H 與 O 原子提交同版本快照，舊查詢／逾時結果不得覆蓋新版；面板開關不觸發獨立不一致資料查詢；開關後抵達的結果只更新資料、依當前層修復焦點，不重開／關閉面板或把焦點還給舊層。 |
| AC-F07-01 | Unchanged | OK 在有可啟動設定 handler 時顯示系統設定畫面；Launcher 不自動提交任何設定變更。 | 同責任與文字沿用；仍受現行共用契約約束 |
| AC-F07-02 | Modified | 沒有 handler 或啟動被拒絕時，1 秒內顯示「無法開啟系統設定，請按 HOME 使用原廠入口」，保留設定焦點；App 區與重新整理仍可達。 | 沒有 handler 或啟動被拒絕時，1 秒內顯示「無法開啟系統設定，請按 HOME 使用原廠入口」，保留設定焦點；重新整理、指定 App（若有）及更多 APP 仍可達。 |
| AC-F07-03 | Unchanged | 系統設定返回且本 APK 再次前景時，重新整理清單並恢復設定入口焦點；不把焦點跳到第一個 App。 | 同責任與文字沿用；仍受現行共用契約約束 |
| AC-F08-01 | Unchanged | 同一版本交付物分別在 API 36 arm64-v8a 模擬器與 MiTV-MOOR0 API 30 armeabi-v7a 完成各自驗收；兩欄獨立記錄，不以模擬器通過替代實機；native 依賴若存在須含相應 ABI。 | 同責任與文字沿用；仍受現行共用契約約束 |
| AC-F08-02 | Unchanged | 在不變更預設 HOME 的前提，原廠 Launcher、setupwraith、FallbackHome 仍啟用；HOME、原廠設定、原有 HDMI 切換與核准串流 App 的基線 smoke path 無新增失效。 | 同責任與文字沿用；仍受現行共用契約約束 |
| AC-F08-03 | Unchanged | 經另行批准只卸載本自訂 APK 後，原廠 HOME 與既有 App 仍可使用；rollback 不要求清資料、root、停用套件或重刷。 | 同責任與文字沿用；仍受現行共用契約約束 |
| AC-F08-04 | Added | —（新增） | 更多 APP 面板只存在本 Launcher 前景內；交付權限及流程不要求 SYSTEM_ALERT_WINDOW 或系統「顯示在其他應用程式上層」授權，不覆蓋外部 App，也不以特殊權限取得 HOME 控制。 |
| AC-F09-01 | Unchanged | 未來納入時：收藏後重新開啟仍存在；取消及套件移除後不保留失效捷徑。 | 同責任與文字沿用；仍受現行共用契約約束 |
| AC-F10-01 | Unchanged | 未來納入時：移動一筆後順序持久保存，取消操作不改順序。 | 同責任與文字沿用；仍受現行共用契約約束 |
| AC-F11-01 | Unchanged | 未來納入時：系統時間或時區變更後下一分鐘內對齊；不改系統時間。 | 同責任與文字沿用；仍受現行共用契約約束 |
| AC-F12-01 | Unchanged | 未來納入時：無效或被刪除圖片回退內建背景且 App 仍可操作。 | 同責任與文字沿用；仍受現行共用契約約束 |
| AC-F13-01 | Unchanged | 未來納入時：選擇已核准入口能到達系統輸入源選擇器；不可用時明示並保留原廠路徑。 | 同責任與文字沿用；仍受現行共用契約約束 |
| AC-F14-01 | Unchanged | 範圍驗收：首頁沒有手機 App 專區、安裝／卸載控制項、搜尋或資料夾；E 的規則不變。 | 同責任與文字沿用；仍受現行共用契約約束 |
| AC-F15-01 | Unchanged | 範圍驗收：本版不註冊／請求替換預設 HOME，不改開機行為；未來試驗必有另行授權、可驗證的原廠恢復步驟及失敗停止條件。 | 同責任與文字沿用；仍受現行共用契約約束 |
| AC-F16-01 | Unchanged | 範圍驗收：交付內容沒有上述模組、帳號要求或系統改造步驟。 | 同責任與文字沿用；仍受現行共用契約約束 |

## BDD → 未來 TDD 的執行契約

1. 規格與 Stitch 設計都獲批准後，按使用價值取一個最小行為切片；從 Scenario 的可觀察結果導出最小測試，不一次先堆所有測試再一次實作。
2. **Red**：建立該切片測試，保留因缺少行為而失敗的真實輸出；環境／編譯錯誤不能冒充 Red。
3. **Green**：最小實作使該測試通過；不順便加入 Should／Could。
4. **Refactor**：重構但保留行為，跑相關回歸，保留前後證據。
5. 以 Feature／AC／Scenario tags 或測試 metadata 建立真實測試位置映射；一個 Scenario 可對應數個測試，但不能漏掉 Then。
6. CI／本機低層成功不等於裝置驗收；G1–G7 的環境專屬證據獨立記錄，HOME 試驗另開規格版本。

### 未來證據最小欄位（目前全部待填）

Scenario ID、AC ID、測試名稱／檔案位置、候選版本與 APK 雜湊、裝置型號／API／ABI、操作前提、預期與實際結果、Pass/Fail/Not Run/N/A/Degraded Accepted、時間戳、日誌／影片／截圖位置、授權與 rollback 記錄。

N/A 僅表示目標不適用，不是 Pass；v1.0 的 Degraded Accepted 只允許核准的 F07 無可用 handler／啟動遭拒絕分支；v1.1 F11 的不可用顯示另記「降級行為結果」與「實值讀取能力」，不得把降級呈現通過寫成實值可讀或假裝 N/A 免驗資訊區。若實機有 handler，仍須完成直接啟動驗收。真實裝置無法製造的失敗情境用低層受控 fixture 覆蓋，不暗示已在實機重現。

## 非 MVP 的追溯狀態

F09、F10、F12～F16 的 AC 是未來納入條件或範圍約束；F11 已由 D8 納入 Must，不屬此排除集合。因此上述非 MVP AC 本次不產生對應功能 Scenario，也不列為 Must 覆蓋缺口。Won't 中的安全／排除邊界已有 F01、F02、F08 的 Must 情境支撐。若任何項目升為 Must，必須先新增 AC／BDD／本表，再取得範圍核准。


### v1.1 本次定稿核對

本次僅將 D8／P11 的核准狀態、F11 狀態標籤及「待審 P11」引用改為已核准；沒有新增、刪除或重新設計 AC／Scenario。保留 v1.0→v1.1 的歷史新增／修改紀錄，不把歷史差異誤認為本次變更。

實際重算：9 個 Must Feature、42 個 Must AC、105 個唯一 Active Scenario、9 個 Retired；F11 為 7 個 AC／20 個 Scenario，雙向追溯完整。原有 85 個 Scenario 記錄逐項完全相同；全部 105 個 Scenario 與 49 個 AC 在僅正規化核准狀態文字後完全相同，數值、輸入與可觀察結果未改。

Gherkin 使用既有有限語法結構檢查（含多階段 When／Then）；未安裝官方 parser，未執行產品測試。Feature 狀態標籤已改為 approved_for_ui_ux_design；F11 狀態標籤為 approved_v1_1／approved_initial_target，Feature／AC／Scenario／D8 ID 與歷史 revision tags 保留。未發現需要更改行為的明確矛盾。

### v1.1 靜態檢查結果

- 重新解析文件：16 Feature；9 Must（F01～F08、F11）；42 Must AC 全覆蓋，49 全部 AC；105 個唯一 Active Scenario；9 個 Retired，替代 ID 均有效。
- v1.0 的 85 個 Scenario（含 SC-025／SC-069／SC-080）標題、標籤及逐步行為均原樣保留；F01～F08 的 35 個 AC 原文不變。
- 唯一修訂的既有 AC 為 AC-F11-01；新增 AC-F11-02～07 與 SC-095～114。F11 全部新情境含 @D8、@F11 及唯一 AC／Scenario tags。
- Active 表與 Gherkin 三元組／標題完全相符；42 列反向 AC 覆蓋與正向對應相同；Markdown 表格欄數一致。
- Gherkin 檢查只涵蓋本檔使用的 Feature／Scenario／tags／Given–When–Then／And 多階段結構，不是官方 Cucumber parser 驗證，不建立或執行 step definitions。
- 語意複核：D4 時鐘排除僅作歷史，D8 不新增操作；正常否定值與未知／不可用分開；資訊刷新不影響 F06／F05；HDMI 讀取與切換分開；P11 預算已核准為初始驗收目標，仍待實機測量；降級不是實值能力證據。
- 本次為文件靜態檢查，產品測試與裝置能力驗證仍 Not Run；核准來自使用者明確決策，而非本靜態檢查。

## 靜態一致性檢查與語意複核

僅檢查 Markdown／Gherkin 結構與文件追溯，不執行任何產品 Scenario、測試程式、模擬器或實機。

- Feature／AC／Active Scenario 定義唯一；所有 Active 的主要 AC 屬於其 Feature；全部 Must AC 至少一個 Active Scenario。
- 03 的 tag／標題 ID 與 04 Active 三元組完全相同；原 56 個 ID 全部有處置，Replaced 不出現在 Active 宣告且每個替代 ID 存在。
- 0.2 歷史新情境從 SC-057 追加；本次 v1.1 從 SC-095 追加，不重用舊 ID；D8 已核准範圍、P11 已核准細化與技術未知分開。
- 五文件均為 Specification Baseline v1.1 — Approved for UI/UX Design，產品需求與行為已核准，UI/UX 最終驗收仍 Pending；產品結果維持 Not Run。重新計算數量，舊 56 僅留在歷史欄位。
- 01 的環境基線與原安全流程保留；G0–G7、G-HOME 仍在 05；F15 未因已確認未來高優先級而擅自變成 MVP。
- 新首頁不再套用 C 欄網格；面板開啟時無背景焦點／操作；H 空與 O 空、查詢 error 與成功 empty 分開；覆蓋式面板與系統 Overlay 權限分開。
- 映射未確認不宣稱未安裝，指定服務全部入口抑制政策與未知映射降級列 SA-04；不臆造真實 package/component。
- Back 階層、repeat 消費、資料原子提交、來源層恢復與晚回呼不重開面板均有可觀察情境。

v1.0 產品需求／行為基線及 D8 範圍已由使用者核准；F11 細化已核准。本次靜態檢查不等於 Android 相容性或產品測試通過。Q1～Q5 Closed；SA-01～SA-07 Approved Behavioral Baseline；實際 mapping 與 G1～G7／G-HOME 仍 Not Run／Not Verified。G0 的 v1.0 規格已核准；D8 首頁 A 方向與資訊範圍已核准，v1.1 規格已核准，完整設計對齊及最終 UI/UX 審核仍 Pending。完成後停止，等待下一步指示。
