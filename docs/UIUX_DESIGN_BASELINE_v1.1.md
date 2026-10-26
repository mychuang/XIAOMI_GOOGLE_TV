# Xiaomi Google TV Launcher — UI/UX Design Baseline v1.1

> **Stage 1 文件交付完成；不是全部畫面／像素鎖版，也不是產品驗收通過。**
> 本版已讀取本機五份規格、舊交接 v1.0、全部 17 份 HTML 與 DESIGN.md，並以 `vision_analyze` 檢視 8 張代表 PNG。先前的來源存取阻擋已由本機匯出物部分解除；Stitch 原始 Screen ID、匯出修訂與逐張核准對應仍未取得。
> **撤回／不沿用「所有尺寸已逐一核准」的無證據推論。** 使用者核准 Home A／Celestial Glass 視覺方向與主要狀態，不等於每個匯出檔、像素、覆寫值或 Screen ID 已逐一核准。

## 1. 範圍、權威與核准界線

- 整理日期：2026-10-26；根目錄：`/Users/miller/Documents/GitPRoject/TV/`。
- 設計盤點子任務僅新增本文件與 `docs/STITCH_ASSET_MANIFEST.md`；原規格、v1.0 交接、HTML、PNG、DESIGN.md 均唯讀。Stage 1 主任務另外完成 Android scaffold、Git 與本機 Build/Test，證據見 `STAGE1_VERIFICATION.md`；未部署或操作裝置。
- [U] 最新任務脈絡確認：**Kotlin 原生 Android Views + ViewBinding**；普通、由原廠入口手動啟動的 TV APK，**不註冊／接管 HOME**。舊規格 T2 尚寫 Compose／Views 未定，是文件時點差異；本次不回寫原檔。框架選定不表示工具鏈、API／ABI 已驗證。
- [U] 舊交接 v1.0 記載使用者已核准視覺及主要狀態；[B] D8 選定首頁 A；本次採 **Home A／Ten-Foot Celestial Glass** 為唯一方向，不混入品牌色焦點或其他版面流派。
- [B] 規格行為優先：五份 Specification Baseline v1.1 的 D1～D8、SA-01～SA-07、F01～F08／F11 與 P11-01～03 不變。
- [E] DESIGN.md 為設計來源，不是需求增補書；HTML 是設計參考，不是 Android 結構、可直接部署程式或已驗證遙控邏輯。來源彼此衝突時不以「較新檔名」或「畫面看似完整」決定勝負。

### 證據標記

| 標記 | 意義 | 不能推論的事項 |
|---|---|---|
| [U] | 使用者／本次委派明示的核准與限制 | 不是逐像素或逐 Screen ID 的簽核 |
| [B] | 本機五份規格的既有已核准行為 | 不是設計稿已畫出或產品已測通 |
| [E] | 本機 HTML／DESIGN.md／PNG 可直接讀取的匯出事實 | 不是 Stitch 線上現況、最新修訂或已核准畫面身分 |
| [P] | 尚缺證據、待處理差異或後續驗證 | 不填入猜測值，不把建議升格為核准 |

### 本地來源

規格資料夾 `XIAOMI_GOOGLE_TV_LAUNCHER_MVP_SPEC_20261025/`：

1. `01_PRODUCT_VISION_AND_SCOPE.md`：D／SA、範圍及核准界線。
2. `02_MVP_FEATURE_SPEC.md`：現行 Feature／AC、焦點、狀態與資訊契約。
3. `03_BDD_GHERKIN_SCENARIOS.feature`：Active 情境與原始 ID。
4. `04_REQUIREMENT_TRACEABILITY.md`：AC／Scenario 對照、歷史退役。
5. `05_OPEN_QUESTIONS_AND_RISKS.md`：技術 Gate、P11 與風險。
6. 同資料夾 `XIAOMI_GOOGLE_TV_LAUNCHER_UIUX_DESIGN_BASELINE_v1.0.md`：舊交接及核准聲明；保留其當時 Blocked 紀錄。

設計來源：`stitch_minimalist_google_tv_launcher_design/` 下 17 組 `code.html`／`screen.png`，以及 `ten_foot_celestial_glass/DESIGN.md`。逐檔分類、指紋與追蹤建議見 `STITCH_ASSET_MANIFEST.md`。舊交接所載 Project ID `18320264029442117914` 僅為歷史來源關聯；本次未重新登入線上專案，也不能由匯出資料夾名稱推得 Screen ID。

## 2. 正式視覺方向與匯出物選用

**Home A／Celestial Glass 的方向已確認；原匯出物不是全部可直接照抄的正式畫面。**

可沿用的方向：[U/E] 深色背景、青／冰藍環境光、玻璃感面、白色高對比焦點、兩個工具、單列首頁入口、右側單欄覆蓋式面板。品牌圖示可以保留自身辨識色，但不能因此把整套焦點改成 Netflix 紅或 Plex 黃。

已選出最少的**局部視覺候選組**（尚不列入可直接追蹤的白名單）：

| 匯出名稱 | 僅可評估的區域／目的 | 為何尚不能整張升格 |
|---|---|---|
| `home_a_app_app` | Home A 資訊／雙工具配置、H 空時單一更多 APP、青白玻璃焦點方向 | 有「尚無精選應用程式推薦」；卡片 220px／scale 1.08 不等於 DESIGN 的 180px／1.06；資訊標籤與值不合完整 F11 |
| `app_5` | 480px 右側單欄面板、72px 項目、青色焦點、stale 錯誤提示區 | 背景仍有 `cloud_off`／同步文字；JS 無法導覽到關閉控制；列表順序不是正式排序；焦點 strip／scale 與 DESIGN 不同 |
| `app_2` | 同寬空面板與可見關閉焦點、empty 主文案 | 背景把 Prime Video／Spotify 放首頁；副文案暗示所有 TV App 都在首頁；關閉 JS 只是視覺回饋 |

選三組是避免同一狀態重複帶入多份：首頁全缺、面板有資料且 stale、面板 empty／close 分別提供不同參考。**選取候選不等於准許追蹤含衝突的原稿。** 依「衝突稿留磁碟、不追蹤」限制，目前推薦白名單只含兩份新文件及必要 DESIGN.md；上述三組需另行確認「僅局部證據」追蹤方式或取得乾淨匯出後才可擴充。完整無衝突、可逐張宣稱核准的 HTML／PNG 組數目前為零；不為湊白名單而偽造一組。

## 3. 尺寸、Token 與原生轉譯界線

下表為 **[E] DESIGN.md 明文來源值，不是新核准的 Android dimens**。定位皆指 `ten_foot_celestial_glass/DESIGN.md` 的章節／YAML key。

| 類別 | 來源值 | 定位及備註 |
|---|---|---|
| 設計畫布 | 1920 × 1080、橫向 16:9 | Layout & Spacing；目標幾何，不是所有 PNG 尺寸 |
| 安全區 | 左右各 96px、上下各 54px | `spacing.margin-safe-x/top/bottom`；互動邊界及文字不得越界 |
| 主列間距 | 28px | `spacing.gutter`；HTML 另有 34px／36px，不可混為同一鎖版 |
| 一般間距 | 8／14／20／32／48px | `space-xs/sm/md/lg/xl` |
| 主入口 | 180 × 180px，圖示 64px | Components / App Shortcut Badges；HTML 存在 184、200×180、220、260×180 等異體 |
| 面板 | 480px 寬，右側安全區內、上下安全邊界間 | Layout / Overlay Drawer、Components / More Apps；不能把其他稿 640px 當等價 |
| 列表項 | 高 72px、圖示 44px | More Apps Linear Drawer |
| 焦點 | scale 1.06；3px 白色外框；青／藍雙光暈 | Elevation & Depth；光暈 `0 0 24px rgba(0,229,255,0.5), 0 0 48px rgba(59,130,246,0.3)` |
| 列表焦點 | 左指示條 4px、`#00E5FF`，表面白色 0.12 alpha | More Apps Linear Drawer；不是另一個焦點節點 |
| 圓角 | 卡片 16px、面板 24px、工具 full | Shapes；YAML lg=1rem、xl=1.5rem；HTML 的 lg=0.5rem／xl=0.75rem 等覆寫與此不一致 |
| 背景／表面 | YAML background=`#10131a`；文字背景漸層 `#080B11`→`#0E1420` | 分清語意底色與層疊漸層，不把兩者當數值相同 |
| 文字／強調 | on-surface=`#e0e2eb`、variant=`#bac9cc`、primary-container=`#00e5ff`、focused white=`#FFFFFF` | Colors／YAML；錯誤色 `#ffb4ab` |
| 玻璃表面 | `rgba(22,28,40,0.72)`；深度段另記 0.65；邊框白色 0.08；blur 24px | Colors／Elevation 的不同用途值，不任意合併成唯一 opacity |
| 工具 | 膠囊；14px 水平／8px 垂直 padding；focus 白底深色字 | Components / Top Utility Actions |

### 字體來源

| Token | size／line-height | weight／letter-spacing |
|---|---|---|
| display-time | 72px／80px | 700／-0.02em |
| headline-lg | 36px／44px | 700／0.04em |
| headline-md | 28px／36px | 600／0.03em |
| title-sm | 22px／28px | 600／0.02em |
| body-md | 20px／28px | 400／0.02em |
| label-md | 18px／24px | 500／0.04em |
| label-sm | 15px／20px | 500／0.05em |

[E] YAML 寫 `Noto Sans`，Typography 內文則列 `PingFang TC` → `Noto Sans TC` → `Microsoft JhengHei UI` → system fallback；這是需明示的來源差異，不代表 Android 裝置都有這些字型。[P] 最終繁中字體、離線字型來源／授權、換行、長名稱、字體縮放與可讀性要以原生實機驗證；本次不下載字型或新增依賴。

### PNG 不等於原生 dp

- 17 張 PNG 的實際 IHDR：13 張 **1600 × 1350**，4 張 **1280 × 1080**；沒有一張實際 PNG 為 1920 × 1080。逐張尺寸見 manifest。
- [E] 代表圖 `home_a_netflix`／`home_a_app_1` 右側內容或工具在 PNG 中不可完整看見，而 HTML 有相關節點；不能由裁切截圖推論規格刪除了工具。
- HTML 有固定寬高、`w-full`、max-width 及 viewport 混用，不能假定 PNG 是均勻縮放的標準畫板，也不從截圖倒算卡片精確尺寸。
- **不得假設 1dp=2px、把 px 一律除二，或以 PNG 寬高決定 dp／sp。** 後續須先記錄裝置實際 viewport、density、fontScale、可用視窗及縮放策略，再建立可追溯 dimens／文字尺度；目前換算尚未鎖定。
- blur、shadow、CSS transform 與 Android Views 的呈現／效能要另行驗證；不把 CSS `backdrop-filter` 直接當 API 30 可用的原生效果承諾。不新增瀏覽器殼層以求貼圖相似。

## 4. 行為交接：首頁、面板與焦點

本節為 [B] 02 共用契約與 SA-01～SA-06；不是 HTML 已實作正確的證明。

### 首頁

- 只有「重新整理」「系統設定」兩工具；時間、日期與星期、網路、HDMI、本機儲存皆唯讀、不取焦點。
- H 只含可證據化辨識的合格 YouTube → Netflix → Disney+，各服務最多一個入口；缺項壓縮、不補空位、不替補、不導商店；更多 APP 永遠存在。
- mapping 未確認不是已確認未安裝；名稱不能作服務身分；本交接不把 HTML 的示意 package 字串升格為實機映射。
- 首次 loading／查詢 error 聚焦重新整理；首次成功未互動時聚焦第一個 H，H 空則更多 APP；已有有效使用者焦點不可被晚回應搶走。
- 內容 Left／Right 相鄰、不循環；Down 保持；Up 回最後工具，無歷史取重新整理。工具 Left／Right 相鄰；Up／外側保持；Down 回最近有效內容，無歷史取第一 H 或更多 APP。

### 更多 APP

- Launcher 前景內右側覆蓋面板，不另開頁、不下方展開、不用系統 Overlay 權限；背景沒有可見焦點與啟用能力。
- O 排除所有已確認的三指定服務入口／變體；其餘依 component 去重、名稱 trim→NFC→casefold code point 排序，以 package/component 原字串破同分。匯出示意的 Spotify、Plex、Kodi 排列不是核准排序。
- 唯一序列為 `[關閉面板, O 全部項目]`。Up／Down 線性、不循環，Left／Right 保持；新開有 O 聚焦第一 App，否則關閉。0／1／100 筆皆需可達，100 不是上限。
- 捲動必須讓焦點項及標記完整可見；不能把 DESIGN 的「居中捲動」擴寫為每次更新必須捲動或搶焦點。
- Back 或關閉 OK 只關面板、焦點回更多 APP；同一次按鍵 repeat／key-up 不穿透。根首頁另一次 Back 才退出，HOME 交系統原廠處理。
- 同一存活操作階段返回保留 Home／Panel 及 stable key；程序重建則從關閉面板的首頁開始。關閉後晚回應不得重開面板。
- 更新 H/O 同版原子提交；消失項按原範圍舊索引修復、越尾取尾；H 空→更多 APP，O 空→關閉。資訊更新不影響這些狀態。

## 5. 五項唯讀資訊（F11）

| 項目 | 正式語意與降級 | 不採用匯出示意 |
|---|---|---|
| 時間 | 同一次本機時間／時區快照，依系統 locale、12/24 設定，不顯示秒 | 固定 06:02／20:45、錯配 PM 或只更新時鐘不更新日期 |
| 日期與星期 | 與時間一致；跨午夜、時區及格式改變同步 | 固定 10 月 24 日與星期文字 |
| Wi-Fi／網路 | 可靠來源的預設網路類型及連接狀態；SSID 只在可安全取得時顯示 | Wi-Fi 6／5 GHz／連線良好、Internet 可用保證、假 SSID；未知不畫成已確認斷線 |
| HDMI 連接狀態 | 僅來源確認的埠識別與連接狀態；部分來源明示部分資訊 | 固定 HDMI 1 (ARC)、待命中、當前輸入或訊號／4K／HDR 宣告 |
| 本機儲存 | 明示「本機資料磁碟（Launcher 所在）」；同快照可用／總容量，十進位 GB 一位小數，非零不足 0.1 顯示 `<0.1 GB` | 固定 24.8 GB、只有可用量、硬體標稱總量、已用百分比或外接容量合計 |

- 每欄各自區分正常、未知「狀態未知」、不可用「無法取得（原因）」；原因限不支援／權限不足／讀取失敗／資料無效／逾時。時間、日期可分別「時間未知」「日期未知」。
- 五項皆保留可識別標籤；名稱不可得不推翻已知網路狀態。HDMI 空來源且完整性未明不能顯示全部未連接；非法容量不可用、缺值未知。
- 重讀清除該欄過期值為未知；舊結果不覆蓋新資料，一欄失敗不清其他欄。正式值必須來自本機可靠資料，不拿設計稿／Mock 補值，不升權。
- P11-01 已核准初始目標：分鐘邊界讀時鐘；前景網路 30 秒、HDMI／儲存 60 秒；首次／resume／收到變更事件安排讀取；每欄 deadline 5 秒、結果後顯示預算 1 秒，同 deadline 逾時優先；背景不輪詢。**非實測結果。**
- F11 與 F06 查詢分離，不增加資訊重試控制，不擋 App 操作、不搶焦點、不開關面板。

## 6. 必要狀態與證據覆蓋

這是行為要求到現有匯出物的差距表；不是聲稱每個 Scenario 都有完成的正式畫面。AC 原文與完整映射仍以 02／04 為準。

| 狀態／能力 | 追溯錨點 | 現有視覺證據與缺口 |
|---|---|---|
| 手動、離線且無推薦首頁 | AC-F01-01／02；SC-001～003 | 多稿混入雲端／推薦，須排除；沒有產品離線測試 |
| 首頁固定入口與缺項 | AC-F02-05／06；SC-057～061 | `home_a_netflix` 全列、`home_a_app_1` 少 Disney+、`home_a_app_app` 全缺；各有衝突，非整張正式 |
| 首次 loading／ready 焦點 | AC-F03-01；SC-013、SC-055、SC-064／065 | `home_a_1/2` loading 含雲端與佔位錯誤；缺合規 loading 與工具焦點鎖版 |
| 面板 normal／linear／trap | AC-F03-02～06；SC-068～072、SC-088／089 | 單欄外觀存在；HTML 關閉導覽、repeat／keyup 不足，不等於原生驗證 |
| 面板 empty | AC-F06-03；SC-035、SC-072 | `app_2` 主文案及 close 可作局部參考，副文案／背景不合規 |
| 無舊快照 error／timeout | AC-F06-03；SC-036／038／079 | 未識別可整張採用的對應畫面；不可用 empty 代替 error |
| 有舊快照 stale | AC-F06-03；SC-037／080 | `app_4/5` 有規格主文案、stale、Back 後重整提示；背景同步概念排除 |
| 啟動失敗／busy／guard | AC-F04-02／03；SC-022～025、SC-087、SC-090 | 未識別明確可用的完整狀態匯出；當前層錯誤與 Back 安全出口仍須涵蓋 |
| 根 Back／關閉／恢復／重建 | AC-F05-01～05；SC-026～030、SC-073～075、SC-084／085／091 | 靜態圖不能證明；不可移植原型的 console log 作驗收 |
| 更新／消失／晚回應 | AC-F06-01～05；SC-076～083、SC-092／094 | 必須另驗 H/O 原子更新、stable key、關閉後晚回應；來源圖不足 |
| 設定不可用／返回 | AC-F07-02／03；SC-044／045／054 | 未識別明確的設定錯誤視覺；保留設定焦點與 HOME 原廠指引 |
| F11 正常／未知／不可用／部分資訊 | AC-F11-01～07；SC-095～114 | `home_a_app_1` 只示意部分不可用；五欄完整降級、部分資料及長字排版未鎖定 |
| 非破壞性／最小權限 | AC-F08-01～04；SC-046～049、SC-086 | 設計參考不是 API／ABI、系統路徑、rollback 或權限驗證 |

[B] 固定錯誤文案：啟動失敗「無法開啟此 App，請重新整理後再試」；guard「尚未離開 Launcher，請再試一次」；設定不可用「無法開啟系統設定，請按 HOME 使用原廠入口」。面板不因 App 啟動錯誤自動關閉；無舊快照 error 不冒充 empty；有舊快照保留 H/O 並明示「清單可能不是最新」。

## 7. 原型雜訊／衝突處理規則

| 差異 | 本次處置 | 來源例子 |
|---|---|---|
| 天氣、雲端驗證／同步、推薦、商店導流 | 排除，不視為新增需求；原檔保留不改 | `app_1_spotify`、`app_2_plex`、`home_a_1/2`、`home_a_app_2`、`home_a_app_app`、`app_3/4/5`、`app_1` |
| 首頁額外服務／全部 App | 排除；僅 H＋更多 APP | `app_1/2` 背景、`app_2_plex` 背景 |
| 品牌紅／黃焦點、面板 640px | 不混入 Celestial Glass 正式樣式 | `netflix`、`home_a_netflix`、`home_a_app_1`、`app_app_plex`、`app_2_plex`；Spotify 面板亦有 640px 異體 |
| 裝置型號、OS／Build、Dolby、影片／NAS 描述 | 非本機證據，不帶入 runtime 文案 | `app_app_plex`、`app_1_app_spotify`、`app_1_spotify` 等 |
| 附加分類標題、教學大區塊、D-pad 提示列 | 本次不提升為產品功能或固定佔位；必要狀態文案仍保留 | `app_1_app_spotify` 的左側導航說明、各首頁 footer |
| 動態環境脈衝、滑鼠 hover、硬寫初始焦點 | 不當作核准互動／動畫 | 多份 HTML；方向焦點回歸 02 契約 |
| CDN、Google Fonts、Material Symbols 網路引用 | 僅設計依賴；不變成 APK 執行時網路依賴 | 全部 HTML 的 head；本次未下載或重新渲染 |

## 8. 尚待補齊與下一階段入口

1. [P] 原始 Screen ID、匯出版本／時間及核准對應；本地雜湊只能辨識檔案內容，不能替代核准。
2. [P] 從混合原稿中取得符合 Home A／Celestial Glass 的乾淨正式畫面，或明確授權僅追蹤局部參考並排除其衝突內容。不得宣稱 17 張都是正式稿。
3. [P] DESIGN 與 HTML 的卡片尺寸、焦點 scale、圓角、面板寬度、字體與透明度差異；由設計審核決定最終原生映射，不默認所有值已核准。
4. [P] 首次 loading、無快照 error、啟動失敗／busy、設定不可用、資訊 Unknown／Unavailable／partial 的視覺完整性，以及長字、捲動、焦點外框安全區。
5. [P] 裝置 density／fontScale 與 px→dp／sp 對應、API 30 的效果與效能；實際 component mapping、公開資訊來源與 OEM lifecycle。
6. 後續開發／建置／裝置測試需在相應授權與 Gate 下進行；本文件不變更 G1～G7 或獨立 G-HOME。Product Tests 仍 **Not Run**。

## 9. 本次驗證與完成判定

- 本機盤點核對：17 份 HTML、17 張 PNG、1 份 DESIGN.md；沒有以「線上專案全部畫面」描述這個本地數量。
- 代表 PNG 人工視覺檢視：`home_a_netflix`、`netflix`、`home_a_app_1`、`home_a_app_app`、`app_1_app_spotify`、`app_app_plex`、`app_2`、`app_5`。其餘以 HTML 與 PNG 檔頭盤點，**沒有宣稱所有 PNG 都逐張目視核准**。
- 03 靜態解析有 105 個 Active Scenario，標頭對應 42 個 Must AC／9 個 Must Feature；9 個退役 ID 僅歷史，不混算 Active。文件引用 ID 另做機械核對。
- 原檔內容指紋於讀取／寫入後比對，未修改；設計子任務不執行原型或產品測試。Git／Android scaffold 的主流程結果另見 Stage 1 驗證紀錄。
- **完成的是可追溯的 Stage 1 v1.1 交接與追蹤決策清單；尚未完成的是無衝突逐畫面鎖版及原生產品驗收。**
