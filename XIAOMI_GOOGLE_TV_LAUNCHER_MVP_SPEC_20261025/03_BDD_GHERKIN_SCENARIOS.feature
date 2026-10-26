# Status: Specification Baseline v1.1 — Approved for UI/UX Design
# 規格日期：2026-10-25 | Version: v1.1 | Product tests: Not Run
# Change Log: D1–D7 已確認；首頁固定服務＋更多 APP 覆蓋式面板。
# Approved Product Requirements: D1～D7、F02 單一主要入口及 F05 同一存活階段恢復面板／焦點。
# Approved Behavioral Baseline: SA-01～SA-07；Q1～Q5 Closed。
# Pending Technical Verification: mapping、API/ABI、OEM 生命週期、HOME。
# 首頁 A 視覺方向由使用者選定；v1.1 需求與行為已核准；Pending Final UI/UX Design Review；Product Tests: Not Run。
# v1.1 Active 105：保留 v1.0 的 85，新增 F11 的 20（SC-095～SC-114）；Retired 9 不變。
# 歷史 0.1→0.2：新增 38、修改 35、沿用 12、Retired 9；本次不改這些歷史標籤。
# 此檔只含 Active 情境；舊 ID 歷史、原 AC 與替代對照見 04_REQUIREMENT_TRACEABILITY.md。
# 未指定初始操作層的 fixture 預設 Home（面板關閉）；有明示面板／背景／來源者依 Given。
# 根首頁指面板關閉；A/B/C 與 p.a/A 是非指定服務的合成 fixture，除非 Given 另述。
# YouTube/Netflix/Disney+ 的成功身分 fixture 假設核准映射存在；不是實機 mapping 證據。
# 真實三服務 package/component 均 TBD；沒有猜測套件名。
# 所有 AC、焦點序列與狀態定義以 02 為準；F01～F08 的 SA-07 預算已核准待實測；F11 的 P11-01 已核准為初始驗收目標、仍待實測。
# Review: Approved Product Requirements / Approved Behavioral Baseline。D8 與 P11-01～P11-03 已核准（Approved／Closed）。F11 全部情境為受控 fixture，不是 Xiaomi 資訊讀取證據。
# F11 是既有狀態訊息的唯讀擴充；不改原 85 個情境的入口、工具、焦點、離線或安全行為。
# 不建立 step definitions，不執行產品測試；安裝與裝置動作需未來另行授權。
# SC-009: Replaced (Retired) -> SC-058, SC-063; 舊單一全 App 首頁改為指定 App 與面板兩個集合
# SC-011: Replaced (Retired) -> SC-059, SC-072; 全清單為空不再等於首頁無可操作內容
# SC-012: Replaced (Retired) -> SC-064, SC-065; 首次焦點改為指定順序且三個都缺時到更多 APP
# SC-014: Replaced (Retired) -> SC-066, SC-070; 舊首頁全 App C 欄網格已不適用；面板需獨立焦點契約
# SC-015: Replaced (Retired) -> SC-066; 首頁工具與內容已加入固定更多 APP，不能沿用舊網格入口
# SC-016: Replaced (Retired) -> SC-067, SC-070, SC-071; 舊首頁尾列邊界改由面板範圍獨立驗收
# SC-017: Replaced (Retired) -> SC-067, SC-072; 零指定 App 仍有更多 APP，Down 不再停在工具
# SC-019: Replaced (Retired) -> SC-071, SC-088; 方向 repeat 必須按新版首頁／面板分開定義
# SC-041: Replaced (Retired) -> SC-077, SC-078; 最後 App 消失的焦點需按首頁／面板分開修復

@specification_baseline_v1_1 @approved_for_ui_ux_design @manual_entry @no_home_takeover
Feature: Xiaomi Google TV 指定影音首頁與更多 APP 覆蓋式面板
  手動進入，HOME 保留原廠；面板是 Launcher 內部狀態，不要求系統 Overlay 特殊權限。
  MiTV-MOOR0 API 30 armeabi-v7a 與 API 36 arm64-v8a 模擬器分別驗收，不能相互替代。

  # F01 手動進入、無廣告且離線可用的首頁

  @F01 @AC-F01-01 @SC-001 @modified_0_2
  Scenario: SC-001 — 從原廠 App 清單手動進入
    Given 已另行批准安裝的候選 APK 位於原廠 App 清單，且預設 HOME 保持原廠
    When 使用者以 D-pad 選取自訂首頁並按 OK
    Then 5 秒內看見含更多 APP、重新整理與系統設定的首頁及資料或明確失敗狀態，過程沒有變更預設 HOME、特殊權限或登入要求

  @F01 @AC-F01-02 @SC-002 @modified_0_2
  Scenario: SC-002 — 離線首頁不依賴推薦服務
    Given 網路不可用，指定服務映射已在 fixture 確認，本機查詢在 1 秒內回傳 YouTube、Netflix、Disney+ 的合格入口
    When 從原廠 App 清單開啟自訂首頁
    Then 5 秒內首頁依序顯示 YouTube、Netflix、Disney+、更多 APP 及原有兩個工具；自有首頁與面板沒有廣告、推薦、影片預覽或登入要求

  @F01 @AC-F01-02 @SC-003 @modified_0_2
  Scenario: SC-003 — 首頁無推薦與追蹤網路依賴
    Given 首頁顯示三個已確認映射的指定 App、更多 APP 與原有兩個工具，裝置連線可用
    When 檢視首頁與更多 APP 面板，並檢查本 APK 的核准相依清單與網路事件紀錄
    Then 自有畫面只有規定入口及狀態訊息，沒有廣告或追蹤相依項且本 APK 沒有網路請求；其他 App 的流量另行排除

  # F02 指定 App 與其他 TV App 探索、識別及分區呈現

  @F02 @AC-F02-01 @SC-004 @modified_0_2
  Scenario: SC-004 — 篩選 TV 入口而非所有套件
    Given 查詢資料包含合格 TV 入口 A、LAUNCHER-only B、HOME-only C、停用 D、非 exported E、未獲授權限的 F、本 APK，以及基線三個原廠 HOME／復原套件；A 是不屬於三個指定服務的其他 App
    When 完成探索並開啟更多 APP 面板
    Then 面板 App 清單只有 A，沒有被排除候選；首頁沒有把 A 當成指定 App 替補，且不因排除候選而顯示錯誤

  @F02 @AC-F02-01 @SC-005 @modified_0_2
  Scenario: SC-005 — 比對已知實機 TV App 防止可見性漏查
    Given 核准基線含至少 2 個不屬於指定服務且符合 E 的其他 TV component，已記錄其身分與資格
    When 在各目標環境用本 APK 探索、開啟更多 APP 面板並比對
    Then 每個基線其他 App component 在面板恰出現一次，首頁不顯示它們；缺漏不得以空狀態判通過

  @F02 @AC-F02-02 @SC-006 @modified_0_2
  Scenario: SC-006 — 去重而不合併同套件的不同入口
    Given 輸入包含同一 component A 兩次，以及相同套件下不同 component B 與 C，B 和 C 顯示名稱相同；上述入口皆為非指定服務的其他 App
    When 完成探索與排序並檢視更多 APP 面板
    Then A 僅顯示一次，B 與 C 各一次且皆帶不同 component 輔助標籤；三個入口可分別識別

  @F02 @AC-F02-02 @SC-007 @modified_0_2
  Scenario: SC-007 — 排序固定且名稱平手時可重現
    Given 三個入口顯示名稱依序為 beta、Alpha、alpha，其中 Alpha 的 stable key 為 p.a/A 而 alpha 為 p.b/B；上述入口皆為非指定服務的其他 App
    When 以相反輸入順序各刷新一次並檢視更多 APP 面板
    Then 兩次結果均依序為 p.a/A、p.b/B、beta 的入口，且沒有依使用頻率重排

  @F02 @AC-F02-03 @SC-008 @modified_0_2
  Scenario: SC-008 — 單筆名稱與圖示缺損可降級
    Given A 的名稱為空白且圖示讀取失敗，B 的名稱讀取拋錯，C 的名稱與圖示可讀；上述入口皆為非指定服務的其他 App
    When 載入這三個合格入口並檢視更多 APP 面板
    Then A 與 B 以各自 package/component 顯示名稱，A 使用本機佔位圖，C 的名稱與圖示保留，面板清單仍有三筆

  @F02 @AC-F02-04 @SC-010 @modified_0_2
  Scenario: SC-010 — 一百個 App 均可抵達
    Given O 為 stable key 不同且排序已知的 100 個其他入口，面板已開啟
    When 以面板 Up／Down 契約遍歷 App 清單
    Then 100 個入口各可取得焦點且集合與 O 相同，沒有第 101 個 App、尾端截斷或首頁焦點穿透

  @F02 @AC-F02-02 @SC-056 @modified_0_2
  Scenario: SC-056 — 名稱正規化後平手仍使用穩定鍵
    Given 兩筆名稱分別為含前後空白的 É 與分解形式 E 加 combining acute，stable key 分別為 p.a/A 與 p.b/B；上述入口皆為非指定服務的其他 App
    When 以 p.b/B 在前的輸入完成排序並檢視更多 APP 面板
    Then 顯示次序為 p.a/A、p.b/B，名稱正規化不改寫它們的 stable key

  @F02 @AC-F02-06 @SC-057 @added_0_2
  Scenario: SC-057 — 三個指定 App 全可用時依固定順序
    Given fixture 映射已確認三服務各一個合格入口，另有其他 App A，查詢輸入順序為 Disney+、A、Netflix、YouTube
    When 完成首次成功查詢
    Then 首頁主要 App 恰依序 YouTube、Netflix、Disney+，其後是更多 APP，A 不直接出現在首頁且無空格

  @F02 @AC-F02-06 @SC-058 @added_0_2
  Scenario: SC-058 — 部分指定 App 缺少時壓縮順序
    Given 三服務映射均已確認，只有 YouTube 與 Disney+ 合格，Netflix 未安裝
    When 完成查詢
    Then 首頁主要 App 依序 YouTube、Disney+，其後是更多 APP；沒有 Netflix 空格、替代 App 或商店跳轉

  @F02 @AC-F02-06 @SC-059 @added_0_2
  Scenario: SC-059 — 指定 App 全缺但其他 App 仍可用
    Given 映射均已確認，三個指定服務沒有合格入口，O 有 A、B
    When 查詢成功並開啟更多 APP
    Then 首頁沒有指定 App 或空白格，保留兩個原工具與更多 APP；面板顯示 A、B，未自動跳商店

  @F02 @AC-F02-05 @SC-060 @added_0_2
  Scenario: SC-060 — 同名其他 App 不冒充指定服務
    Given 已核准映射不包含其他 App A 的 package/component，但 A 的顯示名稱是 YouTube；真正 YouTube 未安裝
    When 查詢成功
    Then 首頁沒有 YouTube 指定入口；A 只在面板中以其真實 stable key 顯示，不被當成指定服務

  @F02 @AC-F02-05 @SC-061 @added_0_2
  Scenario: SC-061 — 映射未確認不得冒充未安裝
    Given YouTube 映射為 TBD，Netflix 與 Disney+ 映射已確認；E 中有未分類入口 A
    When 完成查詢
    Then YouTube 指定入口不建立，首頁顯示「指定 App 身分設定待確認」而不宣稱 YouTube 未安裝；A 依其他 App 規則存在 O；G3 身分驗收仍標未完成

  @F02 @AC-F02-07 @SC-062 @added_0_2
  Scenario: SC-062 — 指定服務多入口不重複放進面板
    Given fixture 已核准 YouTube 的入口優先序 Y1、Y2，並驗證 Y1、Y2、同服務其他入口 Y3 的歸屬；三者都合格，另有其他 App A
    When 完成查詢並開啟面板
    Then 首頁 YouTube 僅對應 Y1，面板 App 只有 A，不含 Y1、Y2 或 Y3

  @F02 @AC-F02-04 @SC-063 @added_0_2
  Scenario: SC-063 — 面板僅一個其他 App
    Given 成功快照 O 只有 A
    When 在更多 APP 按 OK
    Then 面板顯示關閉面板與 A，只有 A 聚焦，沒有第二個 App 佔位入口

  @F02 @AC-F02-03 @SC-093 @added_0_2
  Scenario: SC-093 — 指定 App 改名不改身分與首頁順序
    Given fixture 已確認 YouTube 與 Netflix 映射，其名稱分別更新成 ZZZ 與 AAA，圖示有一筆失敗
    When 成功刷新
    Then 首頁仍是 YouTube 身分入口在 Netflix 身分入口之前，名稱採資源結果且失敗圖示用佔位圖；兩者不因改名進入面板

  # F03 首頁與覆蓋式面板 D-pad 焦點契約

  @F03 @AC-F03-01 @SC-013 @unchanged_0_1
  Scenario: SC-013 — 初次載入尚未完成仍可操作工具
    Given 首次開啟且查詢尚未完成，沒有任何舊快照
    When 觀察 loading 狀態並按 Right
    Then 起初重新整理具有唯一焦點，Right 後 1 秒內唯一焦點移到系統設定，且沒有空白 App 取得焦點

  @F03 @AC-F03-04 @SC-018 @modified_0_2
  Scenario: SC-018 — 焦點移到視窗外時項目完整可見
    Given 面板 O 有 100 筆，首個可見區只容納前 6 筆，焦點在第 6 筆
    When 按 Down 移往第 7 筆
    Then 1 秒內第 7 筆與唯一焦點標記完整可見；首頁沒有焦點且沒有 App 被啟動

  @F03 @AC-F03-01 @SC-055 @modified_0_2
  Scenario: SC-055 — 初次載入完成不搶走已操作的工具焦點
    Given 首次查詢尚未完成，使用者已由重新整理移到系統設定
    When 查詢成功回傳 A、B
    Then 首頁兩個工具與更多 APP 可見，H 依映射呈現，但唯一焦點仍在系統設定，不因首次結果抵達而跳到內容區

  @F03 @AC-F03-01 @SC-064 @added_0_2
  Scenario: SC-064 — 首次成功依服務順序設定焦點
    Given 首頁首次 loading 尚未有人操作；查詢回傳合格 Netflix、Disney+，YouTube 無合格入口
    When 提交首次成功快照
    Then Netflix 為唯一可見焦點；更多 APP、Disney+ 與工具沒有焦點

  @F03 @AC-F03-01 @SC-065 @added_0_2
  Scenario: SC-065 — 首次指定 App 全缺聚焦更多 APP
    Given 三服務映射已確認且首次成功快照 H 空，使用者未操作
    When 提交首次結果
    Then 更多 APP 具有唯一可見焦點，不聚焦隱藏 App；兩個工具仍可達

  @F03 @AC-F03-02 @SC-066 @added_0_2
  Scenario: SC-066 — 新版首頁內容與工具雙向導覽
    Given H 依序 YouTube、Netflix、Disney+，Netflix 聚焦且最後工具為重新整理
    When 依序按 Right、Right、Up、Right、Down
    Then 每次 1 秒內焦點依序為 Disney+、更多 APP、重新整理、系統設定、更多 APP，啟動請求數為 0

  @F03 @AC-F03-03 @SC-067 @added_0_2
  Scenario: SC-067 — 沒有指定 App 的首頁仍有內容出口
    Given H 空、面板關閉、重新整理聚焦，無內容焦點歷史
    When 依序按 Down、Left、Right、Down、Up
    Then 焦點依序為更多 APP、更多 APP、更多 APP、更多 APP、重新整理，不循環且不建立空白 App

  @F03 @AC-F03-05 @SC-068 @added_0_2
  Scenario: SC-068 — 更多 APP 開啟 Launcher 內覆蓋式面板
    Given O 依序 A、B，首頁更多 APP 聚焦
    When 按一次 OK
    Then 1 秒內面板覆蓋在仍保留的首頁之上且 A 聚焦；沒有切換獨立頁或在首頁下方展開，也沒有特殊權限提示

  @F03 @AC-F03-06 @SC-069 @added_0_2
  Scenario: SC-069 — 面板焦點與操作不穿透首頁
    Given 面板 O 依序為 A、B，A 具有唯一焦點，背景首頁有合格 YouTube、重新整理與系統設定
    And 受控 UI fixture 已在開面板前保存這三個背景控制項的唯一識別；Click 表示經 UI 事件派送的一次控制項啟用事件，不直接呼叫啟動、查詢或設定功能
    And 初次查詢已完成，沒有待處理查詢、套件通知或 resume 事件；從此刻起記錄各類新增請求，初始計數均為 0
    When 依序輸入 Up、Up、Left、Right、Down，每鍵各按下並放開一次，無 repeat，且每次等待焦點結果
    Then 每次 1 秒內唯一焦點依序為關閉面板、關閉面板、關閉面板、關閉面板、A，背景控制項均未取得焦點
    When 受控 UI fixture 依序向已保存識別的背景 YouTube、重新整理與系統設定各派送 1 次 Click，逐次等待處理結果，不強制改變焦點
    Then 每次 Click 處理後面板仍開啟且 A 保持唯一焦點，沒有顯示背景 App 或系統設定畫面
    And YouTube 啟動請求、首頁重新整理查詢請求與系統設定啟動請求的新增計數各為 0
    And 面板內 App 啟動請求的新增計數也為 0；背景目標若已被隔離而無法接受 Click，記為拒絕輸入，不改向面板項目派送

  @F03 @AC-F03-02 @SC-070 @added_0_2
  Scenario: SC-070 — 面板獨立的線性導覽
    Given 面板 O 依序 A、B、C，A 聚焦
    When 依序按 Down、Down、Up、Up、Up、Down
    Then 焦點依序為 B、C、B、A、關閉面板、A，每步只有一個面板焦點且啟動請求數為 0

  @F03 @AC-F03-04 @SC-071 @added_0_2
  Scenario: SC-071 — 面板 repeat 與邊界不循環
    Given 面板 O 依序 A、B、C，A 聚焦
    When 輸入 Down 初始事件與 3 次 repeat，再按 Right 與 Left
    Then 焦點依序為 B、C、C、C、C、C，沒有 App 被啟動，沒有焦點離開面板

  @F03 @AC-F03-03 @SC-072 @added_0_2
  Scenario: SC-072 — 更多 APP 為空仍有可聚焦出口
    Given 成功快照 O 空但 H 有 YouTube
    When 開啟更多 APP 後依序按 Up、Down、Left、Right
    Then 面板顯示「沒有其他可顯示的 TV App」，每步都只有關閉面板聚焦，背景 YouTube 與工具未取得焦點

  @F03 @AC-F03-06 @SC-088 @added_0_2
  Scenario: SC-088 — 開啟面板的 OK 不穿透啟動第一個 App
    Given 首頁更多 APP 聚焦，O 有 A
    When 按住 OK 產生初始與 repeat 事件後放開
    Then 只開一個面板且 A 聚焦，App 啟動請求數為 0；放開後另一次 OK 才能啟動 A

  @F03 @AC-F03-06 @SC-089 @added_0_2
  Scenario: SC-089 — 關閉面板的 Back repeat 不退出 Launcher
    Given 面板有 A 且 A 聚焦
    When 按住 Back 產生初始與 repeat 事件後放開
    Then 面板關閉且更多 APP 聚焦，Launcher 仍在前景，不把同一次按鍵後續事件當成根首頁 Back

  # F04 精確啟動與可恢復的啟動失敗

  @F04 @AC-F04-01 @SC-020 @modified_0_2
  Scenario: SC-020 — 啟動精確的同套件 TV 入口
    Given 同一套件有 A 與 B 兩個合格入口，焦點為 B，受控目標會在 2 秒內顯示 B 專屬標記；A 與 B 不屬於指定服務且位於已開啟面板
    When 按一次 OK
    Then 恰有一次指向 B 的啟動請求且 2 秒內前景顯示 B 專屬標記，沒有出現 A 畫面

  @F04 @AC-F04-01 @SC-021 @modified_0_2
  Scenario: SC-021 — 核准的真實 App 前景可辨識
    Given 指定服務映射及實機基線已有核准 App 的精確 component 與啟動畫面，首頁焦點在該指定 App
    When 按一次 OK 並觀察最多 10 秒
    Then 目標 App 的可辨識前景畫面出現且沒有啟動其他 App；若未出現不得僅以已送出請求判通過

  @F04 @AC-F04-02 @SC-022 @modified_0_2
  Scenario: SC-022 — 點擊前入口已移除
    Given 面板 O 為 A、B 且焦點在 A，A 已移除但快照尚未更新，新查詢回傳 B
    When 按 OK 取得找不到入口的結果並完成清單同步
    Then 錯誤結果後 1 秒內面板顯示「無法開啟此 App，請重新整理後再試」；同步後只剩 B 且 B 聚焦，面板仍開啟，不開商店

  @F04 @AC-F04-02 @SC-023 @modified_0_2
  Scenario: SC-023 — 啟動被系統拒絕
    Given 面板 O 為 A、B，焦點在 A 且 A 仍合格，但啟動被系統拒絕
    When 按 OK
    Then 1 秒內面板顯示「無法開啟此 App，請重新整理後再試」，保留 A 焦點，Down 可到 B，面板不關閉且首頁不可操作

  @F04 @AC-F04-03 @SC-024 @modified_0_2
  Scenario: SC-024 — OK 長按與忙碌期間不重複啟動
    Given 首頁 YouTube 由 fixture 確認合格且已聚焦，啟動後 3 秒才離開 Launcher
    When 按住 OK 產生 4 次 repeat，放開後於第 1 秒再按 OK
    Then 離開 Launcher 前啟動請求總數為 1，repeat 沒有啟動第二個 App

  @F04 @AC-F04-03 @SC-025 @modified_0_2
  Scenario: SC-025 — 未離開首頁的啟動可重試
    Given 面板焦點在合格 App A，沒有啟動中的請求，本情境的啟動請求計數為 0
    And 受控啟動目標會接受請求但不回報明確錯誤，也不使 Launcher 或面板離開前景
    When 使用者按下並放開一次 OK，將首次啟動請求送出的時間記為 t=0
    Then 指向 A 的啟動請求計數為 1，A 處於啟動忙碌狀態
    When 不再輸入按鍵，等待至 t=5 秒
    Then 面板顯示「尚未離開 Launcher，請再試一次」，沒有顯示啟動成功
    And 啟動忙碌狀態已解除，A 的啟動操作恢復可接受 OK 的狀態，面板仍開啟且 A 保持唯一焦點
    And 此時指向 A 的啟動請求計數仍為 1，沒有自動重試
    When 使用者再次按下並放開一次 OK，不產生 repeat 事件
    Then 指向 A 的啟動請求計數由 1 增加為 2，這次 OK 恰好新增 1 次請求
    And 沒有向其他 App 發出啟動請求，面板未被關閉

  @F04 @AC-F04-02 @SC-087 @added_0_2
  Scenario: SC-087 — 首頁指定 App 點擊時已停用
    Given 首頁 Netflix 聚焦但系統剛停用其入口，H 的 YouTube 仍合格，清單尚未更新
    When 按 OK 得到無法啟動結果後完成刷新
    Then 1 秒內首頁顯示啟動失敗，不導商店；成功刷新後 Netflix 隱藏且 YouTube 聚焦，沒有替代 App 或自動面板

  @F04 @AC-F04-03 @SC-090 @added_0_2
  Scenario: SC-090 — 面板啟動中關閉不被晚到回呼重開
    Given 面板 A 正在啟動且 Launcher 仍前景，使用者按 Back 已關閉面板，更多 APP 聚焦
    When 舊啟動的第 5 秒逾時處理抵達
    Then 面板不重開且更多 APP 保持焦點，不送第二個啟動；過期逾時訊息不覆蓋新狀態，若外部 App 後續出現則其返回來源以目前首頁為準

  # F05 Back、HOME、面板關閉與返回狀態恢復

  @F05 @AC-F05-01 @SC-026 @unchanged_0_1
  Scenario: SC-026 — ready 根首頁 Back 退出而非攔截
    Given 本 APK 由原廠首頁手動開啟，目前為 ready 根狀態且首頁仍在前景
    When 按一次 Back
    Then 1 秒內本頁退出並顯示原廠首頁，沒有退出確認、重新開啟本 APK 或要求變更 HOME

  @F05 @AC-F05-02 @SC-027 @modified_0_2
  Scenario: SC-027 — 從受控目標返回保留 stable key
    Given 面板 O 依序 A、B、C，從 B 啟動受控目標，程序存活且其 Back 會返回同一 Launcher 操作階段
    When 目標 Back 返回後 resume 查詢將 B 改名並排到最後
    Then 自查詢開始 5 秒內面板仍開啟，B 新名稱可見且 stable key 保持唯一焦點；不聚焦 C 或背後首頁

  @F05 @AC-F05-02 @SC-028 @modified_0_2
  Scenario: SC-028 — 從其他來源回到存活的首頁
    Given 首頁焦點為 Netflix 後進入背景，程序與操作階段仍存活；第三方 Back 不會返回 Launcher
    When 使用者從原廠 App 清單再次開啟同一操作階段，Netflix 仍合格
    Then resume 查詢後 5 秒內面板保持關閉，首頁 Netflix 聚焦，不要求第三方改變 Back 行為

  @F05 @AC-F05-03 @SC-029 @modified_0_2
  Scenario: SC-029 — 程序被回收後採首次焦點
    Given 舊程序面板曾聚焦 A，但已由受控生命週期 fixture 結束；新查詢回傳已確認映射的三個指定 App
    When 重新建立程序並手動開啟首頁
    Then 5 秒內首頁 YouTube 取得唯一焦點且面板關閉，不要求清資料、登入或設定 HOME

  @F05 @AC-F05-04 @SC-030 @unchanged_0_1
  Scenario: SC-030 — HOME 交由原廠處理
    Given 預設 HOME 仍是基線原廠 Launcher，本 APK 位於前景
    When 按 HOME 後從原廠 App 清單再次手動開啟本 APK
    Then HOME 後顯示原廠首頁而非本 APK，手動重開後兩個工具可用，預設 HOME 紀錄未改變

  @F05 @AC-F05-01 @SC-050 @unchanged_0_1
  Scenario: SC-050 — loading 根首頁 Back 可退出
    Given 本 APK 由原廠首頁手動開啟，目前為 loading 根狀態且首頁仍在前景
    When 按一次 Back
    Then 1 秒內本頁退出並顯示原廠首頁，沒有退出確認、重新開啟本 APK 或要求變更 HOME

  @F05 @AC-F05-01 @SC-051 @modified_0_2
  Scenario: SC-051 — 無指定 App 的根首頁 Back 可退出
    Given 本 APK 從原廠首頁手動開啟，成功快照 H 為空且面板關閉，更多 APP 聚焦
    When 按一次 Back
    Then 1 秒內本頁退出並顯示原廠首頁，沒有退出確認、重新開啟本 APK 或要求變更 HOME

  @F05 @AC-F05-01 @SC-052 @unchanged_0_1
  Scenario: SC-052 — error 根首頁 Back 可退出
    Given 本 APK 由原廠首頁手動開啟，目前為 error 根狀態且首頁仍在前景
    When 按一次 Back
    Then 1 秒內本頁退出並顯示原廠首頁，沒有退出確認、重新開啟本 APK 或要求變更 HOME

  @F05 @AC-F05-01 @SC-053 @unchanged_0_1
  Scenario: SC-053 — launching 根首頁 Back 可退出
    Given 本 APK 由原廠首頁手動開啟，目前為 launching 根狀態且首頁仍在前景
    When 按一次 Back
    Then 1 秒內本頁退出並顯示原廠首頁，沒有退出確認、重新開啟本 APK 或要求變更 HOME

  @F05 @AC-F05-05 @SC-073 @added_0_2
  Scenario: SC-073 — 面板 Back 只關面板再一次 Back 才退出
    Given 從原廠首頁手動開啟 Launcher，面板 O 有 A 且 A 聚焦
    When 按一次 Back 並完全放開，觀察後再按一次獨立 Back
    Then 第一次 1 秒內面板關閉且更多 APP 聚焦，Launcher 仍在前景；第二次才退出到原廠首頁

  @F05 @AC-F05-05 @SC-074 @added_0_2
  Scenario: SC-074 — 空面板可以用 OK 關閉
    Given 面板 O 空且關閉面板聚焦
    When 按一次 OK
    Then 1 秒內面板關閉，首頁更多 APP 取得唯一焦點；沒有啟動 App 或退出 Launcher

  @F05 @AC-F05-04 @SC-075 @added_0_2
  Scenario: SC-075 — 面板內 HOME 仍交給系統並恢復原範圍
    Given 預設 HOME 保持原廠，面板 O 為 A、B 且 B 聚焦；程序與操作階段會保持存活
    When 按 HOME 後從原廠清單再次開啟同一 Launcher 操作階段
    Then 先顯示原廠 HOME；resume 查詢後面板恢復且 B 聚焦，預設 HOME 不變

  @F05 @AC-F05-02 @SC-084 @added_0_2
  Scenario: SC-084 — 外部 App 返回時來源項消失仍留在面板
    Given 從面板唯一 App A 啟動外部目標，程序存活；背景期間 A 變為不合格
    When 外部目標使 Launcher 再次前景，resume 查詢成功回傳 O 空
    Then 面板仍開啟，顯示空狀態且關閉面板聚焦，不退出 Launcher 或改聚焦首頁

  @F05 @AC-F05-03 @SC-085 @added_0_2
  Scenario: SC-085 — 重建後不保留面板且無指定 App 可用
    Given 舊程序曾開啟面板但已結束，三服務映射已確認，新查詢 H 空、O 有 A
    When 全新手動開啟 Launcher
    Then 5 秒內首頁更多 APP 聚焦且面板關閉，不自動開 A 或還原舊面板

  @F05 @AC-F05-05 @SC-091 @added_0_2
  Scenario: SC-091 — 面板 loading 時 Back 仍可關閉
    Given 初次查詢未完成且沒有快照，使用者開啟更多 APP，關閉面板聚焦
    When 按一次 Back 並放開
    Then 1 秒內面板關閉且更多 APP 聚焦，Launcher 不退出；查詢是否完成不影響關閉

  # F06 雙區清單同步、空狀態與焦點修復

  @F06 @AC-F06-01 @SC-031 @modified_0_2
  Scenario: SC-031 — 首次查詢與手動重整更新資料
    Given 映射已確認，首次 H 僅有 Netflix、O 為 A；之後安裝 YouTube 但通知未送達，焦點在重新整理
    When 按 OK 重新整理且新查詢成功
    Then 5 秒內 H 依序 YouTube、Netflix，O 仍為 A，更多 APP 與工具保留，焦點仍在重新整理

  @F06 @AC-F06-01 @SC-032 @modified_0_2
  Scenario: SC-032 — 背景變更在 resume 時補償
    Given 映射已確認，本 APK 在背景且首頁更多 APP 原本聚焦；已提交停用 YouTube、啟用 Netflix、更新其他 App A 的名稱與圖示，背景通知未送達
    When 本 APK 回到前景且查詢成功
    Then 5 秒內 H 不含 YouTube 且含 Netflix，O 的 A 名稱／圖示更新並重排，更多 APP 保持焦點，不要求重開電視

  @F06 @AC-F06-02 @SC-033 @modified_0_2
  Scenario: SC-033 — 前景套件事件刷新
    Given 首頁 H 為 YouTube、Netflix 且 YouTube 聚焦，映射已確認；系統已完成移除 YouTube 並送達通知
    When 本 APK 接收到該已完成變更通知
    Then 5 秒內 H 只剩 Netflix 且聚焦 Netflix，O 不出現 YouTube 殘留入口，更多 APP 保留

  @F06 @AC-F06-02 @SC-034 @modified_0_2
  Scenario: SC-034 — 同套件更新入口不殘留舊 component
    Given 已開啟面板顯示非指定服務 p/A，套件更新已提交 p/B 並送達通知
    When 處理變更且新查詢完成
    Then 5 秒內面板只顯示 p/B 而無 p/A，名稱／圖示採更新結果，面板仍開啟

  @F06 @AC-F06-03 @SC-035 @modified_0_2
  Scenario: SC-035 — 空清單仍可重試恢復
    Given 映射已確認且成功快照 H、O 皆空，面板正顯示「沒有其他可顯示的 TV App」；之後其他 App A 安裝完成
    When 按 Back 關閉面板，移至首頁重新整理按 OK，成功後再開更多 APP
    Then 面板 O 只有 A 且聚焦 A，不再顯示空訊息；首頁 H 仍空且保留更多 APP 及兩個工具

  @F06 @AC-F06-03 @SC-036 @modified_0_2
  Scenario: SC-036 — 首次查詢明確錯誤不冒充空集合
    Given 首次開啟沒有舊快照，查詢回報存取失敗
    When 顯示該次結果
    Then 1 秒內首頁顯示「無法更新 App 清單，請重新整理」，不宣稱沒有指定 App 或沒有其他 App；重新整理聚焦，系統設定與更多 APP 可達

  @F06 @AC-F06-03 @SC-037 @modified_0_2
  Scenario: SC-037 — 刷新失敗保留舊資料並可恢復
    Given 舊快照 H 為 YouTube、O 為 A，重新整理聚焦；下一次查詢失敗，再下一次成功回傳 H 為 YouTube、O 為 B
    When 先重新整理得到失敗，再按一次 OK 重試
    Then 失敗時兩區保留舊快照並標示「清單可能不是最新」及重試訊息；重試後 5 秒內 H 為 YouTube、O 為 B，錯誤標示消失且重新整理保持焦點

  @F06 @AC-F06-03 @SC-038 @modified_0_2
  Scenario: SC-038 — 查詢逾時後拒絕晚到結果
    Given 首頁沒有舊快照，查詢第 6 秒才會回傳其他 App A
    When 從查詢開始等待到第 5 秒與第 6 秒
    Then 第 5 秒顯示查詢失敗並保留重新整理焦點；第 6 秒 H 與 O 仍未提交 A，更多 APP 可開啟顯示查詢錯誤而非成功空狀態

  @F06 @AC-F06-04 @SC-039 @modified_0_2
  Scenario: SC-039 — 移除焦點以原索引修復
    Given 面板舊 O 為 A、B、C、D，索引 1 的 B 聚焦；新 O 為 A、C、D
    When 套用新快照
    Then 面板仍開啟，新索引 1 的 C 取得唯一可見焦點，不跳到 A 或首頁

  @F06 @AC-F06-04 @SC-040 @modified_0_2
  Scenario: SC-040 — 移除尾端焦點選最後項目
    Given 面板舊 O 為 A、B、C 且 C 聚焦，新 O 只剩 A
    When 套用新快照
    Then 面板仍開啟，A 取得唯一可見焦點，沒有指向舊索引或首頁的焦點

  @F06 @AC-F06-05 @SC-042 @modified_0_2
  Scenario: SC-042 — 較舊查詢不得覆蓋較新結果
    Given Q1 較早啟動且已邏輯取消仍可能晚到；Q2 較晚啟動先回傳 H 為 Netflix、O 為 B
    When 先提交 Q2，再收到 Q1 的 H 為 YouTube、O 為 A
    Then H、O 都保持 Q2 版本，不短暫回退 YouTube 或 A，焦點不因 Q1 改變

  @F06 @AC-F06-02 @SC-076 @added_0_2
  Scenario: SC-076 — 面板開啟時安裝指定 App 不搶焦點
    Given H 為 Netflix，面板 O 為 A、B 且 B 聚焦；YouTube 已完成安裝並有已核准映射
    When 接收到已提交安裝通知且查詢成功
    Then 5 秒內 H 更新為 YouTube、Netflix，O 仍為 A、B；面板不關閉且 B 保持焦點，首頁沒有焦點

  @F06 @AC-F06-04 @SC-077 @added_0_2
  Scenario: SC-077 — 移除面板最後 App 不跳回首頁
    Given 面板 O 僅有 A 且 A 聚焦，H 有 YouTube，新快照 O 空
    When 提交新快照
    Then 面板仍開啟並顯示空狀態，關閉面板取得唯一焦點，首頁 YouTube 不取得焦點

  @F06 @AC-F06-04 @SC-078 @added_0_2
  Scenario: SC-078 — 移除首頁最後指定 App 回更多 APP
    Given 首頁 H 只有 Netflix 且 Netflix 聚焦，面板關閉；新快照 H 空且 O 有 A
    When 提交新快照
    Then 更多 APP 取得唯一焦點，不拿 A 替補 Netflix；兩個工具仍可操作

  @F06 @AC-F06-03 @SC-079 @added_0_2
  Scenario: SC-079 — 面板初查失敗可關閉後重試
    Given 沒有舊快照、初查失敗，使用者開啟更多 APP
    When 觀察錯誤後按 Back，再從首頁重新整理取得成功快照 O 為 A，最後再開面板
    Then 錯誤時面板顯示「無法更新 App 清單，請重新整理」及「按 Back 關閉後可重新整理」，不顯示成功空狀態；關閉後更多 APP 聚焦，重試後面板顯示 A 且無錯誤

  @F06 @AC-F06-03 @SC-080 @added_0_2
  Scenario: SC-080 — 面板刷新失敗保留兩區舊快照
    Given 已提交舊快照 V1，H 為 YouTube、O 依序為 A、B，面板開啟且 B 具有唯一焦點，上次工具焦點為重新整理
    And 套件變更通知已觸發查詢 Q1，受控查詢將回報失敗；下一次手動查詢 Q2 將在 5 秒期限前成功回傳 V2，H 為 Netflix、O 僅有 B
    When 收到 Q1 的失敗結果，尚未輸入 Back 或重新整理
    Then 1 秒內面板顯示「無法更新 App 清單，請重新整理」、「清單可能不是最新」及「按 Back 關閉後可重新整理」
    And H 仍為 V1 的 YouTube，O 仍依序為 V1 的 A、B，沒有提交空快照或顯示成功空狀態
    And 面板仍開啟且 B 的 stable key 保持唯一焦點，背景首頁沒有焦點
    When 使用者按下並放開一次 Back
    Then 面板關閉且首頁更多 APP 取得唯一焦點，舊快照 V1 仍保留
    When 使用者按下並放開 Up 到重新整理，再按下並放開一次 OK，並收到 Q2 的成功結果
    Then 自 Q2 被接受起 5 秒內 H 與 O 同時提交 V2：H 僅有 Netflix，O 僅有 B
    And 查詢失敗與非最新標示消失，面板保持關閉且重新整理具有唯一焦點
    When 使用者按下並放開 Down 回到更多 APP，再按下並放開一次 OK
    Then 面板只顯示 App B 並由 B 取得唯一焦點，沒有 A 或查詢失敗與非最新標示

  @F06 @AC-F06-05 @SC-081 @added_0_2
  Scenario: SC-081 — 首頁與面板提交同一版本不得混用
    Given 舊快照 V1 的 H 為 YouTube、O 為 A，面板開啟；新快照 V2 的 H 為 Netflix、O 為 B
    When 提交 V2 並逐次觀察提交前後的畫面狀態
    Then 可觀察狀態只有 V1 的成對資料或 V2 的成對資料，沒有 YouTube 配 B 或 Netflix 配 A 的混合畫面

  @F06 @AC-F06-05 @SC-082 @added_0_2
  Scenario: SC-082 — 面板關閉後晚到成功不重開面板
    Given 面板開啟且正在刷新；使用者已按 Back 關閉，更多 APP 聚焦
    When 原查詢成功回傳新的 H、O
    Then 兩區資料更新但面板保持關閉，更多 APP 保持焦點；結果不還原舊面板焦點

  @F06 @AC-F06-04 @SC-083 @added_0_2
  Scenario: SC-083 — 空面板變有 App 不搶關閉控制項焦點
    Given 成功空面板的關閉面板控制項已聚焦，其他 App A 安裝完成
    When 套件通知觸發成功快照 O 為 A
    Then 5 秒內面板顯示 A 且空訊息消失，但關閉面板保持焦點；Down 才移到 A

  @F06 @AC-F06-05 @SC-092 @added_0_2
  Scenario: SC-092 — 查詢中開面板不被首頁首次結果搶焦點
    Given 首頁首次 loading 時已開面板，關閉面板聚焦；原查詢尚未完成
    When 查詢成功回傳 H 為 YouTube、O 為 A
    Then 面板保持開啟且關閉面板保持焦點，O 顯示 A，首頁不取得焦點；沒有為開面板建立第二個獨立查詢

  @F06 @AC-F06-04 @SC-094 @added_0_2
  Scenario: SC-094 — 面板內停用 App 只在面板修復焦點
    Given 面板 O 為 A、B、C 且 B 聚焦，系統完成停用 B，H 不變
    When 收到通知並提交新快照 O 為 A、C
    Then 5 秒內 B 消失且舊索引 1 的 C 聚焦，面板保持開啟，首頁未被操作

  # F07 系統設定入口與失敗降級

  @F07 @AC-F07-01 @SC-043 @unchanged_0_1
  Scenario: SC-043 — 開啟既有系統設定不代改值
    Given 系統設定存在可啟動 handler，焦點為系統設定，已記錄網路、音量與輸入源的前值
    When 按一次 OK 並且不在設定內提交任何操作
    Then 系統設定畫面出現，已記錄的設定值均未被 Launcher 改變

  @F07 @AC-F07-02 @SC-044 @modified_0_2
  Scenario: SC-044 — 設定入口不存在時安全降級
    Given 沒有可用的系統設定 handler，焦點為系統設定；H 為空且沒有內容焦點歷史
    When 按 OK
    Then 1 秒內顯示「無法開啟系統設定，請按 HOME 使用原廠入口」，系統設定仍聚焦，Left 可到重新整理，Down 可到更多 APP

  @F07 @AC-F07-03 @SC-045 @unchanged_0_1
  Scenario: SC-045 — 設定返回保持工具焦點
    Given 本 APK 從系統設定入口開啟系統設定且程序仍存活
    When 返回使本 APK 再次前景，清單查詢成功
    Then 5 秒內清單更新且唯一焦點仍為系統設定，不跳至 App

  @F07 @AC-F07-02 @SC-054 @modified_0_2
  Scenario: SC-054 — 設定啟動被拒絕時安全降級
    Given 設定 handler 可解析但實際啟動被拒絕，焦點為系統設定；H 為空且沒有內容焦點歷史
    When 按 OK
    Then 1 秒內顯示「無法開啟系統設定，請按 HOME 使用原廠入口」，系統設定仍聚焦，Left 可到重新整理，Down 可到更多 APP

  # F08 實機相容性、非破壞性與可移除性

  @F08 @AC-F08-01 @SC-046 @unchanged_0_1
  Scenario: SC-046 — 兩種環境分開記錄相容性證據
    Given 候選版本已依核准流程產出且已逐次授權安裝；驗收有 API 36 arm64-v8a 模擬器與 MiTV-MOOR0 API 30 armeabi-v7a 兩個獨立目標
    When 未來分別檢查 ABI 與安裝開啟結果，執行各目標適用的 F01–F07 與 F08 安全／回復驗收，彙整而不遞迴執行本 Scenario
    Then 兩個目標各有版本／APK 雜湊、裝置型號、API、ABI、Scenario 結果與證據；任一未執行或不通過均不得標示本版實機相容通過

  @F08 @AC-F08-02 @SC-047 @unchanged_0_1
  Scenario: SC-047 — 保留原廠 HOME 與復原套件
    Given 已記錄基線 HOME 解析與三個原廠套件啟用狀態，候選 APK 的權限與依賴已審核
    When 完成本 APK 安裝、手動開啟、第三方啟動與 Back/Home 的核准路徑
    Then 預設仍為原廠 HOME，三個套件均仍啟用；交付物與操作紀錄沒有 root、攔 HOME、特權要求、停用／移除系統套件或自動改設定

  @F08 @AC-F08-02 @SC-048 @unchanged_0_1
  Scenario: SC-048 — 原廠 HDMI 設定與串流路徑回歸
    Given 實機在安裝前已記錄原廠設定入口、現有 HDMI 輸入與使用者核准串流 App 的可用 smoke path，測試來源與網路條件固定
    When 使用候選 APK 後回到原廠首頁，重走同一組 smoke path
    Then 原廠設定可開啟、原有 HDMI 輸入可選且顯示相同來源、核准串流仍達成基線播放檢查；任一路徑新增失敗均阻擋發布並調查差異

  @F08 @AC-F08-03 @SC-049 @unchanged_0_1
  Scenario: SC-049 — 僅卸載自訂 APK 即可回復
    Given 預設 HOME 從未變更，已記錄自訂 APK package ID 與原廠基線，使用者另行批准此次卸載
    When 僅卸載該自訂 APK 並按 HOME，再開啟一個原有 App
    Then 自訂入口消失、原廠首頁可見且原有 App 可開啟，不需清資料、root、停用其他套件、重刷或修改預設 HOME

  @F08 @AC-F08-04 @SC-086 @added_0_2
  Scenario: SC-086 — 內部面板不要求系統 Overlay 授權
    Given 未來候選交付物已經權限審查且用戶未授予顯示在其他應用程式上層權限
    When 手動開啟更多 APP 面板再啟動其中受控 App，並檢視權限與操作紀錄
    Then 面板可用且沒有 SYSTEM_ALERT_WINDOW 請求或授權導頁；外部 App 前景不被面板覆蓋，HOME 預設未改


  # F11 本機時間與唯讀裝置資訊；D8 與細化行為已核准；技術驗證與 UI/UX 最終驗收尚未完成。

  @F11 @AC-F11-01 @SC-095 @D8 @added_1_1 @approved_v1_1
  Scenario: SC-095 — 時間日期依同一系統快照與格式呈現
    Given 合成時間來源回傳同一有效快照 T，系統時區 Z、locale L、24 小時及日期格式 D 已固定；fixture 同時提供系統格式器對 T 的時間、日期與星期預期字串
    When 首次讀取完成並呈現首頁資訊
    Then 時間、日期與星期各與 fixture 的系統格式器預期字串一致且不顯示秒；沒有網路請求或修改系統時間，五項資訊標籤均保留

  @F11 @AC-F11-01 @SC-096 @D8 @added_1_1 @approved_v1_1 @approved_initial_target
  Scenario: SC-096 — 系統格式與時區變更同步重算
    Given 合成時間快照 T 已顯示，來源由 24 小時改為 12 小時並更換時區與 locale；fixture 已提供新設定的時間、日期及星期預期字串
    When 受控來源送出設定變更事件並立即提供同一 T 在新設定下的快照
    Then 依已核准 P11-01 在事件後 1 秒內，三項字串皆符合新系統格式，包含 12 小時所需的日間時段標示，沒有保留舊時區日期或修改系統設定

  @F11 @AC-F11-01 @SC-097 @D8 @added_1_1 @approved_v1_1 @approved_initial_target
  Scenario: SC-097 — 跨午夜時間日期星期一起更新
    Given 受控系統時鐘固定在某本機日期的最後一分鐘，fixture 已指定下一日期與星期的格式器預期字串，資料可立即讀取
    When 受控時鐘跨越本機午夜且排程器送出分鐘更新事件
    Then 依已核准 P11-01 在 1 秒內顯示新分鐘、新日期與新星期；可觀察提交狀態不出現新時間配舊日期／星期

  @F11 @AC-F11-01 @SC-098 @D8 @added_1_1 @approved_v1_1
  Scenario: SC-098 — 時間來源失敗不得使用設計稿固定時間
    Given 受控時間來源初始未完成，其他資訊來源有效；設計 fixture 另有不屬於資料來源的示意時間字串
    When 先觀察未完成狀態，再令時間來源回報讀取失敗
    Then 未完成時顯示時間未知及日期未知；失敗後時間與日期欄均顯示無法取得（讀取失敗），不顯示示意時間或推測星期，其他有效資訊保留

  @F11 @AC-F11-02 @SC-099 @D8 @added_1_1 @approved_v1_1
  Scenario: SC-099 — 已知網路類型與名稱可部分降級
    Given 受控預設網路來源確認 Wi-Fi 已連線且提供合成名稱 TEST-NET-A，未提供 Internet 可用證據
    When 提交該有效快照
    Then 網路欄顯示 Wi-Fi 已連線及 TEST-NET-A，不顯示已連上網際網路
    When 下一有效來源仍確認 Wi-Fi 已連線但名稱被遮蔽
    Then 保留 Wi-Fi 已連線並顯示名稱無法取得，不沿用 TEST-NET-A，也不顯示未連線
    When 再依序提交確認乙太網路連線及確認無預設網路的兩次有效快照
    Then 分別顯示乙太網路已連線及無預設網路，不殘留 Wi-Fi 名稱，不推論 Wi-Fi 無線電開關狀態

  @F11 @AC-F11-02 @SC-100 @D8 @added_1_1 @approved_v1_1
  Scenario: SC-100 — 網路未知與拒絕不能當成斷線
    Given 受控網路來源尚未回傳任何有效狀態
    When 先觀察初始狀態，再令來源回報存取被拒
    Then 初始網路欄顯示狀態未知；拒絕後顯示無法取得（權限不足），兩階段均不顯示未連線、假名稱或權限授權提示

  @F11 @AC-F11-03 @SC-101 @D8 @added_1_1 @approved_v1_1
  Scenario: SC-101 — HDMI 連接證據不等於目前輸入
    Given 合成 HDMI 來源明示完整涵蓋 fixture 的兩個埠，TEST-HDMI-A 為已連接、TEST-HDMI-B 為未連接，沒有目前輸入或訊號證據
    When 讀取成功並提交資訊
    Then HDMI 連接狀態欄依來源列出兩個埠及各自連接狀態，不標為目前使用中或播放中，輸入源切換請求數為 0；本情境結果不構成 Xiaomi 實值證據

  @F11 @AC-F11-03 @SC-102 @D8 @added_1_1 @approved_v1_1
  Scenario: SC-102 — HDMI 部分或空來源不冒充全部斷線
    Given 合成 HDMI 來源只確認 TEST-HDMI-A 未連接並明示覆蓋範圍不完整
    When 提交部分結果，再在下一讀取提交覆蓋範圍未知的空集合
    Then 第一階段顯示 TEST-HDMI-A 未連接及部分資訊，不推測其他埠；第二階段顯示狀態未知，不顯示全部未連接或憑空補 HDMI 編號

  @F11 @AC-F11-03 @SC-103 @D8 @added_1_1 @approved_v1_1
  Scenario: SC-103 — HDMI 無安全來源時保留欄位降級
    Given 受控能力來源回報一般 APK 無受支援的 HDMI 資料來源，主要 App 清單有效
    When 提交該能力結果
    Then HDMI 連接狀態欄保留並顯示無法取得（不支援）；沒有敏感／特權授權要求、設定導頁或切換輸入請求，更多 APP 仍可開啟

  @F11 @AC-F11-04 @SC-104 @D8 @added_1_1 @approved_v1_1
  Scenario: SC-104 — 儲存容量具有明確單位及磁碟範圍
    Given 合成來源回傳 Launcher 所在本機資料磁碟同一快照：總量 32000000000 bytes、可用量 12000000000 bytes；另有 USB／NAS fixture 但不屬於此來源
    When 依已核准 P11-03 呈現容量
    Then 標籤為本機資料磁碟（Launcher 所在），顯示可用 12.0 GB／總計 32.0 GB 並可見 1 GB = 1,000,000,000 bytes 的單位說明；不合計 USB／NAS、不標為硬體容量或 App 配額

  @F11 @AC-F11-04 @SC-105 @D8 @added_1_1 @approved_v1_1
  Scenario: SC-105 — 儲存零值小值及非法數值可辨識
    Given 受控容量來源每次皆回傳總量 32000000000 bytes；測試序列的可用量為 0、50000000、-1、33000000000 bytes
    When 依序完成四次獨立讀取，每次觀察完成後畫面
    Then 可用量依序顯示 0.0 GB、<0.1 GB、無法取得（資料無效）、無法取得（資料無效）；非法階段不顯示有效總量／可用量配對，不以零代替錯誤
    When 再提交總量 0 bytes、可用量 0 bytes 的快照
    Then 顯示無法取得（資料無效），不顯示正常 0.0 GB 磁碟

  @F11 @AC-F11-04 @SC-106 @D8 @added_1_1 @approved_v1_1
  Scenario: SC-106 — 儲存缺值及讀取失敗不假造容量
    Given 受控儲存來源在一次完成結果缺少可用量，其他資訊來源有效
    When 呈現缺值結果，下一次讀取再回報明確錯誤
    Then 缺值時儲存欄為狀態未知；錯誤後為無法取得（讀取失敗），不沿用其他快照容量或設計稿數字，其他資訊不被清除

  @F11 @AC-F11-05 @SC-107 @D8 @added_1_1 @approved_v1_1
  Scenario: SC-107 — 資訊區不加入既有方向鍵焦點序列
    Given 首頁 H 為 YouTube、Netflix、Disney+，Netflix 聚焦，最後工具為重新整理；五個資訊項分別含正常、未知與不可用狀態且可識別
    When 依序按下並放開 Right、Right、Up、Right、Down，每次觀察焦點
    Then 唯一焦點依序為 Disney+、更多 APP、重新整理、系統設定、更多 APP；五個資訊項從未取得焦點，焦點圖沒有新增節點或 App 啟動請求

  @F11 @AC-F11-05 @SC-108 @D8 @added_1_1 @approved_v1_1
  Scenario: SC-108 — 資訊區啟用事件不導頁不改設定
    Given 首頁更多 APP 聚焦，受控 UI fixture 可識別五項資訊區但不強制改焦點；初始設定開啟、App 啟動、網路變更、HDMI 切換與權限請求計數皆為 0
    When 經 UI 事件派送向五個資訊項逐一送出一次 Click 啟用事件，不直接呼叫功能；不能接收事件的項目記為拒絕而不轉送
    Then 每次後全部請求計數仍為 0、更多 APP 仍唯一聚焦、面板未開啟；資訊項沒有新操作畫面或啟用動作

  @F11 @AC-F11-06 @SC-109 @D8 @added_1_1 @approved_v1_1
  Scenario: SC-109 — 首頁資訊更新不搶 App 焦點
    Given 首頁 Netflix stable key 為唯一焦點，H/O 快照 V1、App 顯示位置與可見區已記錄；沒有 App 查詢或按鍵事件
    When 受控資訊來源依序提交有效時間／日期、網路失敗、HDMI 更新及儲存更新結果
    Then 每次提交後 Netflix stable key 仍唯一聚焦，Home 層、H/O V1、App 可見區均不變，面板沒有自動開啟；只有相應資訊欄位更新

  @F11 @AC-F11-06 @SC-110 @D8 @added_1_1 @approved_v1_1
  Scenario: SC-110 — 面板資訊更新不改焦點與開關狀態
    Given 面板 O 為 A、B，B 為唯一焦點且其可見區已記錄，H/O 為 V1；受控資訊來源將同時提供時間、網路、HDMI 與儲存的新結果
    When 在面板開啟時提交資訊結果，不輸入按鍵
    Then 面板仍開啟且 B stable key 與可見區不變，背景資訊不取得焦點也不覆蓋面板；H/O 保持 V1
    When 使用者另按一次 Back 關閉面板
    Then 更多 APP 唯一聚焦，首頁資訊顯示此次有效結果或各欄降級狀態，不為更新而重開面板

  @F11 @AC-F11-06 @SC-111 @D8 @added_1_1 @approved_v1_1
  Scenario: SC-111 — 資訊來源全部失敗仍可使用主要入口
    Given 所有資訊來源受控為讀取失敗，H/O 有效，H 有 YouTube、O 有 A，更多 APP 聚焦；App A 與系統設定有會顯示可辨識畫面的受控目標
    When 提交資訊失敗後在更多 APP 按 OK
    Then 面板開啟且 A 聚焦，不等待資訊恢復
    When 按 OK 啟動 A，再由受控目標返回同一操作階段
    Then 曾顯示 A 的受控畫面，返回後仍為面板且 A 聚焦
    When 按 Back 關閉面板、Up 到重新整理並按 OK，取得成功 App 快照後按 Right 到系統設定再按 OK
    Then 重新整理查詢完成且系統設定受控畫面出現；上述過程不出現資訊授權提示，F06 不等待或觸發資訊讀取

  @F11 @AC-F11-07 @SC-112 @D8 @added_1_1 @approved_v1_1 @approved_initial_target
  Scenario: SC-112 — 逾時與舊回應不冒充目前資訊
    Given 網路欄已有合成 TEST-NET-A，受控排程器在 t=0 開始新查詢 Q1；Q1 到第 6 秒才回傳，採已核准 P11-01 的 5 秒期限
    When 開始 Q1 並觀察 t=0、t=5 秒，再在 t=6 秒送達 Q1 舊值
    Then t=0 顯示狀態未知而不顯示 TEST-NET-A 為目前值；t=5 秒判定逾時並於顯示預算內顯示無法取得（逾時）；Q1 晚到不恢復舊名稱
    When 下一合法讀取 Q2 回傳確認乙太網路已連線
    Then 顯示乙太網路已連線並清除該欄逾時，其他有效欄位不受影響

  @F11 @AC-F11-07 @SC-113 @D8 @added_1_1 @approved_v1_1 @approved_initial_target
  Scenario: SC-113 — 前景週期與恢復補償不建立背景輪詢
    Given 受控排程器採已核准 P11-01，t=0 四個來源均已完成一次讀取；來源後續可立即回傳，沒有變更事件或 resume
    When 前景推進至 t=30 秒，再至 t=60 秒
    Then 網路分別在 30 及 60 秒開始重讀；HDMI 與儲存於 30 秒無新增讀取而於 60 秒各重讀一次；同欄有效查詢不重疊
    When 轉入背景並推進 120 秒，再返回同一存活操作階段
    Then 背景期間沒有週期讀取；resume 對資訊重新讀取，舊值先標未知再呈現新結果，操作層與焦點依原 F05 保留

  @F11 @AC-F11-07 @SC-114 @D8 @added_1_1 @approved_v1_1
  Scenario: SC-114 — 正式資料缺失不能由示意資料補值
    Given 受控整合環境選用正式資料路徑，所有本機資訊讀取均回報不支援；測試資源另含明示示意的時間、SSID、HDMI 及容量字串
    When 手動開啟首頁並檢視資訊畫面與資料來源記錄
    Then 五項資訊標籤保留且各顯示無法取得（不支援），示意字串均未出現於資訊值；來源記錄沒有切換為 Mock、網路服務或特權讀取，不將此結果記為任何實機實值讀取通過

