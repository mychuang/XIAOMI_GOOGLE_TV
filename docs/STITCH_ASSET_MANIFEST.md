# Stitch Asset Manifest — Stage 1 v1.1

> **本機完整盤點與正式追蹤決策清單；不是 17 張畫面全數核准證明。**
> 日期：2026-10-26。根目錄：`/Users/miller/Documents/GitPRoject/TV/`。設計子任務不操作 Git；本清單由主流程採納，Stage 1 僅將必要 DESIGN.md 加入來源追蹤，所有原始 HTML／PNG 保留本機且忽略。最終追蹤以 Git index／commit 核對。

## 1. 來源身分與核准證據

- 設計根路徑：`stitch_minimalist_google_tv_launcher_design/`。
- 本機實際有 **17 個畫面資料夾，每個各有 `code.html` 與 `screen.png`**，另有 `ten_foot_celestial_glass/DESIGN.md`。本地總計 35 個設計來源檔，不把 `.DS_Store` 算入。
- 原資料夾名是本 manifest 的 local key，**不是 Stitch Screen ID**。未取得每張的原始 Screen ID、原始標題、版本／匯出時間及核准 ID 對照；不以檔案修改時間猜「最新版」。
- v1.0 交接載有 Project ID `18320264029442117914`；本次只有本機匯出證據，沒有線上全面盤點。17 是本地數量，不保證線上專案只有 17 張。
- 使用者核准 Home A／Celestial Glass 方向與主要狀態的事實保留；核准不延伸為「17 張各像素、尺寸與 HTML JS 全部通過」。
- SHA-256 是本機位元內容指紋，不是核准簽章；本次未修改原素材、未重匯出／裁切／清理任何原檔。

## 2. 分類政策

| 分類 | 意義 | 本次追蹤決策 |
|---|---|---|
| R｜必要設計系統來源 | `DESIGN.md`：Celestial Glass 的直接來源，可保留衝突說明後追溯 | 推薦追蹤；不等於每個 token 已逐值簽核 |
| C｜局部視覺候選 | 部分區域符合 Home A／青白玻璃方向，但原檔仍混有範圍、數值或互動衝突 | **原檔暫不追蹤**；只選最少三組供後續確認／乾淨匯出 |
| X｜衝突、不完整或重複稿 | 不符合方向／規格，或沒有必要增加重複參考 | 保留磁碟、不納入推薦白名單 |

**沒有任一組 HTML／PNG 可誠實標為整張無衝突且已鎖定核准版本。** 為遵守「衝突稿留磁碟不追蹤」，本次推薦白名單不包含原始 HTML／PNG。不是忽略選圖要求：已選三組最小局部候選，但其提升為正式追蹤資產受現有內容阻擋。不能先追蹤再聲稱那些雜訊已從原檔移除。

## 3. 全部 17 畫面逐項盤點

每列的精確原路徑均為 `stitch_minimalist_google_tv_launcher_design/<local key>/code.html` 及同目錄 `screen.png`。兩個檔案一起分類；所有列的 Screen ID／核准版本皆「未取得」。

| Local key | 分類 | 畫面角色（由內容辨識） | PNG IHDR 實際尺寸 | 檢視證據 | 分類理由／不可採用項 |
|---|---|---|---|---|---|
| `app_1` | X｜衝突稿 | 面板 empty | 1600 × 1350 | HTML | 首頁背景含 Prime Video／Spotify；市集安裝導流；不實 empty 副文案。 |
| `app_1_app_spotify` | X｜衝突稿 | Spotify 第一項面板 | 1600 × 1350 | HTML＋PNG | 640px 面板、左側大型導航教學及「按左側離開」暗示；裝置型號／功能宣告；非 480px 精簡面板。 |
| `app_1_spotify` | X｜衝突稿 | Spotify 第一項面板 | 1280 × 1080 | HTML | 640px 面板；天氣、精選標題、虛構裝置／Dolby、內容描述；不作正式來源。 |
| `app_2` | C｜局部候選／暫不追蹤 | 面板 empty、close 焦點 | 1600 × 1350 | HTML＋PNG | 480px 面板與青白 close 可參考；背景六入口／empty 副文案衝突，JS 不真正關閉。 |
| `app_2_plex` | X｜衝突稿 | Plex 焦點面板 | 1280 × 1080 | HTML | 天氣、沙丘推薦海報文字、首頁額外入口、640px 面板、黃焦點；不混入正式稿。 |
| `app_3` | X｜衝突稿 | stale 面板、Kodi 焦點 | 1600 × 1350 | HTML | 背景同步未就緒；錯誤文案異體／額外啟動字樣；JS 導覽不含 close；僅留磁碟。 |
| `app_4` | X｜衝突稿／重複候選排除 | stale 面板、Kodi 焦點 | 1600 × 1350 | HTML | 規格主文案已有，但背景同步與 JS close 缺口仍在；與 app_5 重疊，不擴大候選。 |
| `app_5` | C｜局部候選／暫不追蹤 | stale 面板、Kodi 焦點 | 1600 × 1350 | HTML＋PNG | 480px／72px 單欄與三段錯誤文案可參考；cloud_off 背景、JS 無 close 導覽、scale 1.03／strip 異體及列表示意排序須排除。 |
| `app_app_plex` | X｜衝突稿 | Plex 焦點面板 | 1600 × 1350 | HTML＋PNG | 640px 面板、黃品牌焦點、語音助理、固定容量／OS 14／Build 等無依據宣告。 |
| `home_a_1` | X｜衝突稿 | 首頁 loading | 1600 × 1350 | HTML | 雲端憑證／本機授權驗證、固定服務載入徽章、固定 HDMI／容量／4K 資訊；不把初始化同步當需求。 |
| `home_a_2` | X｜衝突稿 | 首頁 loading／generic 佔位 | 1600 × 1350 | HTML | 雲端驗證；應用程式 1～4 佔位取代永遠存在的更多 APP；固定資訊。 |
| `home_a_app_1` | X｜衝突稿；語意補充證據 | 首頁少 Disney+／資訊 unavailable | 1280 × 1080 | HTML＋PNG | 缺項壓縮語意可觀察，但紅品牌焦點、wifi_off 暗示與 F11 原因／標籤不足；PNG 右側未完整顯示工具。 |
| `home_a_app_2` | X｜衝突稿 | 首頁 loading／更多 APP | 1600 × 1350 | HTML | 雲端憑證驗證、應用市集副標、placeholder；不能把更多 APP 轉成市集。 |
| `home_a_app_app` | C｜局部候選／暫不追蹤 | 首頁 H 空／更多 APP 焦點 | 1600 × 1350 | HTML＋PNG | Home A 五項資訊配置與青白方向可參考；推薦／同步句、220px 卡片／1.08 放大、F11 示意值與標籤衝突。 |
| `home_a_netflix` | X｜衝突稿 | 首頁全列／Netflix 焦點 | 1280 × 1080 | HTML＋PNG | 200×180px、scale-110、紅品牌光暈；固定 HDMI／容量資訊；PNG 裁切右側，不作畫布量測。 |
| `netflix` | X｜衝突稿 | 首頁全列／Netflix 焦點 | 1600 × 1350 | HTML＋PNG | 180px 但 scale 1.08／3.5px 框／紅品牌光暈；APP LAUNCHER 標題／提示；固定資訊不完整。 |
| `youtube` | X｜不完整方向稿 | 首頁 YouTube 焦點 | 1600 × 1350 | HTML | 260×180px 橫卡、36px gap，無五項 F11 資訊；JS Up 固定到設定、Down 固定第一項，不合最近有效焦點契約。 |

盤點結果：**C 3 組、X 14 組，共 17 組**；另有 R 1 份 DESIGN.md。13 張 PNG 為 1600×1350、4 張為 1280×1080；沒有 1920×1080 PNG。1920×1080 是 DESIGN.md 宣告的目標畫布，不能與 raster 尺寸混用，更不能推成 Android dp。

`HTML＋PNG` 表示 HTML 已讀取且該 PNG 已由 `vision_analyze` 目視檢視；`HTML` 表示 HTML 已讀取、PNG 僅核對檔頭／指紋，未宣稱目視過。共 8 張代表 PNG 已檢視。全體 HTML 已讀取其文字、樣式與 script；未以瀏覽器執行或連外下載資產。

### 代表 PNG 的可見差異

- `home_a_netflix`／`home_a_app_1`：右側內容／工具在匯出 PNG 未完整可見；HTML 仍含工具。不得把可見裁切當成最終版面或刪除控制的依據。
- `netflix`：資訊左上、雙工具右上、單列入口，Netflix 呈紅色光暈；方向局部接近，但不是統一青白焦點。
- `home_a_app_app`：更多 APP 單卡青白光暈、左上資訊、右上雙工具；推薦句在畫面內確實可見，不能忽略。
- `app_1_app_spotify`：左側大型教學、右側較寬單欄；不是精簡 480px drawer。
- `app_app_plex`：Plex 黃色光暈、面板底部容量與 OS 標記；非正式資訊來源。
- `app_2`：右側空面板、關閉控制有青白焦點、中心 empty 主文案；背景模糊不代表 HTML 額外入口已移除。
- `app_5`：右側單欄，Kodi 一個明顯焦點、stale 三段訊息；靜態視覺不證明可遙控抵達關閉。

## 4. 給 parent 的精確推薦 Git 白名單

### 4.1 現在可採用的保守白名單（3 檔）

以下均相對於 repository 根目錄；是明確列檔，不是遞迴目錄白名單：

```text
docs/UIUX_DESIGN_BASELINE_v1.1.md
docs/STITCH_ASSET_MANIFEST.md
stitch_minimalist_google_tv_launcher_design/ten_foot_celestial_glass/DESIGN.md
```

主流程已取得 Stage 1 Git／initial commit 授權，設計檔案只選上述精確路徑，不使用整個設計資料夾或 wildcard。既有五份規格、歷史差異與 v1.0 交接原文亦由主流程保留追蹤；不屬這份新增設計來源白名單。

### 4.2 已選最少 HTML／PNG 候選（6 檔；尚非生效白名單）

```text
stitch_minimalist_google_tv_launcher_design/home_a_app_app/code.html
stitch_minimalist_google_tv_launcher_design/home_a_app_app/screen.png
stitch_minimalist_google_tv_launcher_design/app_5/code.html
stitch_minimalist_google_tv_launcher_design/app_5/screen.png
stitch_minimalist_google_tv_launcher_design/app_2/code.html
stitch_minimalist_google_tv_launcher_design/app_2/screen.png
```

最小覆蓋理由：`home_a_app_app` 提供 Home A 全缺入口構圖；`app_5` 提供有資料／stale 與列表焦點；`app_2` 提供 empty／close 焦點。三組已足以保留不同區域的候選，不再加入同角色 `app_1`、`app_3/4` 或 Netflix／Plex 品牌焦點異體。

**生效條件，不能跳過：**

1. 取得可核對的核准身分，或使用者明確接受以這些 local key／雜湊定位「局部參考」而非整張簽核。
2. 對 C 各列的雜訊與幾何差異做明確設計裁決；若仍要求「衝突原稿不追蹤」，需乾淨匯出後建立新路徑／新指紋，不可把原衝突檔悄悄加入。
3. 未滿足前，保持全部 HTML／PNG 在磁碟但不推薦新增追蹤。本次未移除任何現有追蹤狀態；若 parent 發現已追蹤衝突素材，是否調整 index 須另行處理，不刪原檔。

這是來源限制，不是要求重新審核已確立的 D1～D8／SA／P11，也不是把先前 UI 核准推翻。若 parent 要求無條件含 HTML／PNG 的白名單，目前應回報阻擋，不能偽稱已有合格原稿。

### 4.3 一律排除的路徑範圍

- 上表所有 X 畫面資料夾內的 HTML／PNG；C 在條件未滿足前亦排除。
- `.DS_Store`、整包壓縮檔、衍生對照圖、暫存／cache、未審核遠端素材。
- 原規格的 `diffs_v1.0_to_v1.1/`、`diffs_v1.1_finalization/` 不因本次設計交接而加入白名單。
- 不新增 `.gitignore`，不改原始設計文件，不刪／移／改名衝突稿，不建立 Android code。

## 5. 資產與 runtime 依賴界線

- HTML 引用 Tailwind CDN、Google Fonts／Material Symbols；這些是匯出原型依賴，不是 APK 執行時依賴，也不代表已下載、離線可用或授權已審核。
- 服務商 Logo、SVG、示意 App 圖示與字型的產品使用來源／授權待確認；不把匯出中的固定 package 字串當真實 App component mapping。
- DESIGN.md 是必留可追溯來源，但其「all installed applications」須受 F02 合格集合／排除規則限制；字型 stack、YAML／prose opacity 與 HTML radius 覆寫等差異已列在 v1.1 交接。
- 所有 HTML JS 是示意：例如 `app_5` 僅在 App index 間移動，未包含關閉控制；`app_2` Enter／Back 只是透明度回饋。不得原封移植或把它當 BDD Pass。

## 6. 來源指紋（SHA-256）

本節由實際讀檔計算；用來驗證內容沒有在交接期間被改寫。每組兩檔完整相對路徑如下。

| 相對路徑 | SHA-256 |
|---|---|
| `stitch_minimalist_google_tv_launcher_design/ten_foot_celestial_glass/DESIGN.md` | `5522ac08a787e92645fa75d274fb84011a13a3f60742cfb2c155c618d77d6668` |
| `stitch_minimalist_google_tv_launcher_design/app_1/code.html` | `eb91e647a49e49f2924efb145fc5d04d25a360b3776c62b0ee18632e7afeffdc` |
| `stitch_minimalist_google_tv_launcher_design/app_1/screen.png` | `c08bf983aacf081cedfe30418ed74f40ebd94b6ef9208e7b007ef4b0f50286fa` |
| `stitch_minimalist_google_tv_launcher_design/app_1_app_spotify/code.html` | `8b149a2a58f8dbf3e17ef23607e32a1b11dda571e8d38109ffde7ad3fac07225` |
| `stitch_minimalist_google_tv_launcher_design/app_1_app_spotify/screen.png` | `32fb7535baa559a4564ad60b8df93c3796db6eec8bbfc4d4a87bb8166e6c218e` |
| `stitch_minimalist_google_tv_launcher_design/app_1_spotify/code.html` | `feb91d0ea71f902b3b5e7fbf7b6402bade36c06eb447e9109947b05aaa8c2a9e` |
| `stitch_minimalist_google_tv_launcher_design/app_1_spotify/screen.png` | `aef0420c1a54355cd47be637d18f4b3af26f38743780ff29bc24f7425104d33a` |
| `stitch_minimalist_google_tv_launcher_design/app_2/code.html` | `303d6109c857a86fe8069f0a66e59f7c622990331f9dc291473810438c09a721` |
| `stitch_minimalist_google_tv_launcher_design/app_2/screen.png` | `46cba5495d8e2e7610d0e4762d2d93e824343baa4160cd80a12aed38e50c633f` |
| `stitch_minimalist_google_tv_launcher_design/app_2_plex/code.html` | `c0607b865d975aa26f5d115d4295d1ce0f0adb44bf7390771286e3e2f57f8654` |
| `stitch_minimalist_google_tv_launcher_design/app_2_plex/screen.png` | `bcb5469cd2aa9885a39335eeae7d3e7e53b1b7517830a59d34b6fcbc796c3ee4` |
| `stitch_minimalist_google_tv_launcher_design/app_3/code.html` | `28a7ab20b820326b5bad668e7dc910daf66f49951cf73065e44fa7daa153229b` |
| `stitch_minimalist_google_tv_launcher_design/app_3/screen.png` | `81dcde9b10a12024180161b23cc8b3249325631bc744d323e1e4f270fd4b78d3` |
| `stitch_minimalist_google_tv_launcher_design/app_4/code.html` | `8d92ccccea5d868afefe8fce84ca19570aca06db60d36faa80b49833a352706a` |
| `stitch_minimalist_google_tv_launcher_design/app_4/screen.png` | `4acd5c0c720ccfc0ced57bf6c767b8433e5b431e99410b34323db10749264c0b` |
| `stitch_minimalist_google_tv_launcher_design/app_5/code.html` | `32877283cc82371f32ea8e259a711d8d3c3b23f53d6c685a0d154fe07d8c5774` |
| `stitch_minimalist_google_tv_launcher_design/app_5/screen.png` | `1eb240a172664d23206257cde7d696aed6015c9aff8bdce5cdbb6455f4bc16ba` |
| `stitch_minimalist_google_tv_launcher_design/app_app_plex/code.html` | `91276c20c098ef28af2941f52b0ee1cf5f3de36c4daf80d3a495be8284adaadf` |
| `stitch_minimalist_google_tv_launcher_design/app_app_plex/screen.png` | `0fd7e9fca88ce135e0d66ad6fef9ede9846b8d7efd5b1c51da1b62555593ba09` |
| `stitch_minimalist_google_tv_launcher_design/home_a_1/code.html` | `6e0de287345f16cc8d560886b7bf40e2e9265baf370e14d28595be6626562106` |
| `stitch_minimalist_google_tv_launcher_design/home_a_1/screen.png` | `f0795f69caecd6f6e9ace5a7da66d132e2eb1028ec6dbacc6dee01f19144474e` |
| `stitch_minimalist_google_tv_launcher_design/home_a_2/code.html` | `8268a3e60104ecd2ec4d4c99161f0417dca7f0d9c9947b4d386a7644b975e049` |
| `stitch_minimalist_google_tv_launcher_design/home_a_2/screen.png` | `1ec93d0d82d0fcdf4466490c9badd6a2ea09fb1dac61ec0a414f9bcd64c077c8` |
| `stitch_minimalist_google_tv_launcher_design/home_a_app_1/code.html` | `e4bfcf46efd519b0041c4392b487de8a27c5e295c787f87f996efc67c8dd17c5` |
| `stitch_minimalist_google_tv_launcher_design/home_a_app_1/screen.png` | `de6c5fcc533e55e85e15ddd44fbe1844b7af5a6930bfd3a29249c1746e9c5220` |
| `stitch_minimalist_google_tv_launcher_design/home_a_app_2/code.html` | `d975b4cffad449153bd7877f84c0a7a3cbc5a3b12edfa2e2b57893f451d85e8b` |
| `stitch_minimalist_google_tv_launcher_design/home_a_app_2/screen.png` | `0a81f566b92a954553dbaea2affb70fd9c08bb49bf068f20dc0eb29397cfd362` |
| `stitch_minimalist_google_tv_launcher_design/home_a_app_app/code.html` | `7b63306c4259dd443211c882770cd42f40b02f9e42ab7a3f047306af1267ddb4` |
| `stitch_minimalist_google_tv_launcher_design/home_a_app_app/screen.png` | `569a0230e6c66aaff9fa0d1cacdf7e36654d835034e2cdd7ee754658cfa0117d` |
| `stitch_minimalist_google_tv_launcher_design/home_a_netflix/code.html` | `e180bc3bec1a6bbf353d35457ba3693cb4122fb18f5bcbdcbc86b86f0203ad7c` |
| `stitch_minimalist_google_tv_launcher_design/home_a_netflix/screen.png` | `8009dd3359465456e8f930e6c3bd3d7b332ce4e018f3040d9e1cb258f2a1c007` |
| `stitch_minimalist_google_tv_launcher_design/netflix/code.html` | `10a82ed7bb0575ac8f291f3eefcf4830ed984631d054081a1c8e335c8af979f2` |
| `stitch_minimalist_google_tv_launcher_design/netflix/screen.png` | `8e7085bf1ec78dbdcf93dc6a9cf018b6892bb9ef877b730ffc032a60ecd532c8` |
| `stitch_minimalist_google_tv_launcher_design/youtube/code.html` | `f56251e98127de803902db1f41887a44b4a609c5d6c0b93e194cc62b61319ed3` |
| `stitch_minimalist_google_tv_launcher_design/youtube/screen.png` | `e076cf06c43a8fb73eabebdec72975a60daf97681dfcb6bf7ea9227c10ee5046` |

### 規格與舊交接指紋

| 相對路徑 | SHA-256 |
|---|---|
| `XIAOMI_GOOGLE_TV_LAUNCHER_MVP_SPEC_20261025/01_PRODUCT_VISION_AND_SCOPE.md` | `96b21d91b6b4c8258f7864af27323e17d783c6dd51ae629bd9e4994e338c5257` |
| `XIAOMI_GOOGLE_TV_LAUNCHER_MVP_SPEC_20261025/02_MVP_FEATURE_SPEC.md` | `d664beeb4a2cefe1cb22a939fb0bb2acd6ec8bd6b3e4b7f0244cb1f50d9a464b` |
| `XIAOMI_GOOGLE_TV_LAUNCHER_MVP_SPEC_20261025/03_BDD_GHERKIN_SCENARIOS.feature` | `5c91772ef16b5fbadb0bd2b99c46921c55be48ce1d1adbe8839fcd0cb34d3946` |
| `XIAOMI_GOOGLE_TV_LAUNCHER_MVP_SPEC_20261025/04_REQUIREMENT_TRACEABILITY.md` | `31af501fe8a3ceebee556732f2e347363c1f4aeac88d377522d525fd2dc9d95f` |
| `XIAOMI_GOOGLE_TV_LAUNCHER_MVP_SPEC_20261025/05_OPEN_QUESTIONS_AND_RISKS.md` | `78249c3ab42f9946f12de966aee922e98e922d1a3e58f31fb86519c33a2a8b80` |
| `XIAOMI_GOOGLE_TV_LAUNCHER_MVP_SPEC_20261025/XIAOMI_GOOGLE_TV_LAUNCHER_UIUX_DESIGN_BASELINE_v1.0.md` | `d8b9cb629ed7bdb0da595f54b718008f77f182ba76edbdddd61ebbb80e4c843b` |

## 7. 靜態驗證與停止點

- 17 local keys 去重，與磁碟 `*/code.html` 集合相等；每份均有對應 PNG。
- 兩份新文件的檔案路徑、候選路徑及來源指紋均以本機內容核對；原來源內容保持不變。
- 本清單不報告 Git 現況、不代表逐畫面最終驗收。Stage 1 文件可交接；合規 HTML／PNG 正式鎖版尚待上列條件。
- 本文件不授權 Stage 2、裝置操作或 HOME Gate；主流程的 Stage 1 Build／JVM smoke test 見驗證紀錄，Product Tests: Not Run。
