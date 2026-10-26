package com.miller.tv.launcher.focus

/**
 * Designated media services per F02 / SA-01, in fixed order: YouTube -> Netflix -> Disney+.
 */
enum class DesignatedService {
    YOUTUBE,
    NETFLIX,
    DISNEY_PLUS
}

/**
 * Focus node identity in the Home screen and Drawer overlay.
 * Stable, semantic identifiers; not derived from UI label text or localized strings.
 */
sealed interface FocusNode {
    sealed interface Tool : FocusNode {
        data object Refresh : Tool
        data object Settings : Tool
    }

    sealed interface Content : FocusNode {
        data class App(
            val service: DesignatedService,
            val componentKey: String
        ) : Content

        data object MoreApps : Content
    }

    sealed interface Drawer : FocusNode {
        data object Close : Drawer
        data class App(val componentKey: String) : Drawer
    }
}

/**
 * D-pad navigation directions.
 */
enum class DpadDirection {
    UP,
    DOWN,
    LEFT,
    RIGHT
}

/**
 * Result of handling a Back key event.
 * Disambiguates drawer closure from root launcher exit (SA-03 / AC-F05-01 / AC-F05-05).
 */
sealed interface BackResult {
    data object DrawerClosed : BackResult
    data object ExitLauncher : BackResult
}

/**
 * Immutable focus state snapshot for ViewBinding observation and decoupled testing.
 */
data class HomeFocusState(
    val currentFocus: FocusNode,
    val lastToolFocus: FocusNode.Tool,
    val lastContentFocus: FocusNode.Content?,
    val hasUserInteracted: Boolean,
    val hApps: List<FocusNode.Content.App>,
    val contentItems: List<FocusNode.Content>,
    val isDrawerOpen: Boolean = false,
    val drawerApps: List<FocusNode.Drawer.App> = emptyList(),
    val drawerItems: List<FocusNode.Drawer> = listOf(FocusNode.Drawer.Close)
)

/**
 * Pure Kotlin Home focus state machine.
 * Implements F03 (AC-F03-01, AC-F03-02, AC-F03-03, AC-F03-05, AC-F03-06)
 * and F05 (AC-F05-01, AC-F05-05) contracts.
 */
class HomeFocusStateMachine(
    initialApps: List<FocusNode.Content.App> = emptyList()
) {
    private val normalizedInitialH = normalizeHApps(initialApps)

    var state: HomeFocusState = HomeFocusState(
        currentFocus = FocusNode.Tool.Refresh,
        lastToolFocus = FocusNode.Tool.Refresh,
        lastContentFocus = null,
        hasUserInteracted = false,
        hApps = normalizedInitialH,
        contentItems = normalizedInitialH + FocusNode.Content.MoreApps,
        isDrawerOpen = false,
        drawerApps = emptyList(),
        drawerItems = listOf(FocusNode.Drawer.Close)
    )
        private set

    val currentFocus: FocusNode
        get() = state.currentFocus

    val lastToolFocus: FocusNode.Tool
        get() = state.lastToolFocus

    val lastContentFocus: FocusNode.Content?
        get() = state.lastContentFocus

    val hasUserInteracted: Boolean
        get() = state.hasUserInteracted

    val hApps: List<FocusNode.Content.App>
        get() = state.hApps

    val contentItems: List<FocusNode.Content>
        get() = state.contentItems

    val isDrawerOpen: Boolean
        get() = state.isDrawerOpen

    val drawerApps: List<FocusNode.Drawer.App>
        get() = state.drawerApps

    val drawerItems: List<FocusNode.Drawer>
        get() = state.drawerItems

    init {
        if (initialApps.isNotEmpty()) {
            onSnapshotLoaded(initialApps)
        }
    }

    /**
     * D-pad direction input handler.
     * Returns true if focus moved to an adjacent node; false if hit a boundary (no-op).
     */
    fun handleDpad(direction: DpadDirection): Boolean {
        if (state.isDrawerOpen) {
            val focus = state.currentFocus
            return if (focus is FocusNode.Drawer) {
                handleDrawerNavigation(focus, direction)
            } else {
                false
            }
        }

        return when (val focus = state.currentFocus) {
            is FocusNode.Tool -> handleToolNavigation(focus, direction)
            is FocusNode.Content -> handleContentNavigation(focus, direction)
            is FocusNode.Drawer -> false
        }
    }

    private fun handleToolNavigation(tool: FocusNode.Tool, direction: DpadDirection): Boolean {
        return when (direction) {
            DpadDirection.UP -> false // Top boundary, stay
            DpadDirection.LEFT -> when (tool) {
                FocusNode.Tool.Refresh -> false // Left boundary, stay
                FocusNode.Tool.Settings -> updateToolFocus(FocusNode.Tool.Refresh)
            }
            DpadDirection.RIGHT -> when (tool) {
                FocusNode.Tool.Refresh -> updateToolFocus(FocusNode.Tool.Settings)
                FocusNode.Tool.Settings -> false // Right boundary, stay
            }
            DpadDirection.DOWN -> {
                // Down to content: resolve recent valid content -> first H -> MoreApps
                val target = resolveContentTarget()
                updateContentFocus(target)
            }
        }
    }

    private fun handleContentNavigation(content: FocusNode.Content, direction: DpadDirection): Boolean {
        return when (direction) {
            DpadDirection.DOWN -> false // Bottom boundary, stay
            DpadDirection.UP -> {
                // Up to last tool focus (defaults to Refresh if none)
                state = state.copy(
                    currentFocus = state.lastToolFocus,
                    lastContentFocus = content,
                    hasUserInteracted = true
                )
                true
            }
            DpadDirection.LEFT -> {
                val index = state.contentItems.indexOf(content)
                if (index > 0) {
                    updateContentFocus(state.contentItems[index - 1])
                } else {
                    false // Left boundary, stay
                }
            }
            DpadDirection.RIGHT -> {
                val index = state.contentItems.indexOf(content)
                if (index in 0 until state.contentItems.size - 1) {
                    updateContentFocus(state.contentItems[index + 1])
                } else {
                    false // Right boundary, stay
                }
            }
        }
    }

    private fun handleDrawerNavigation(drawerNode: FocusNode.Drawer, direction: DpadDirection): Boolean {
        return when (direction) {
            DpadDirection.LEFT, DpadDirection.RIGHT -> false // Non-traversing; stay on drawer, do not bleed into Home
            DpadDirection.UP -> {
                when (drawerNode) {
                    FocusNode.Drawer.Close -> false // Top boundary, stay
                    is FocusNode.Drawer.App -> {
                        val index = state.drawerApps.indexOf(drawerNode)
                        if (index > 0) {
                            updateDrawerFocus(state.drawerApps[index - 1])
                        } else {
                            updateDrawerFocus(FocusNode.Drawer.Close)
                        }
                    }
                }
            }
            DpadDirection.DOWN -> {
                when (drawerNode) {
                    FocusNode.Drawer.Close -> {
                        if (state.drawerApps.isNotEmpty()) {
                            updateDrawerFocus(state.drawerApps.first())
                        } else {
                            false // Bottom boundary when empty
                        }
                    }
                    is FocusNode.Drawer.App -> {
                        val index = state.drawerApps.indexOf(drawerNode)
                        if (index in 0 until state.drawerApps.size - 1) {
                            updateDrawerFocus(state.drawerApps[index + 1])
                        } else {
                            false // Bottom boundary, stay
                        }
                    }
                }
            }
        }
    }

    private fun updateToolFocus(newTool: FocusNode.Tool): Boolean {
        state = state.copy(
            currentFocus = newTool,
            lastToolFocus = newTool,
            hasUserInteracted = true
        )
        return true
    }

    private fun updateContentFocus(newContent: FocusNode.Content): Boolean {
        state = state.copy(
            currentFocus = newContent,
            lastContentFocus = newContent,
            hasUserInteracted = true
        )
        return true
    }

    private fun updateDrawerFocus(newDrawer: FocusNode.Drawer): Boolean {
        state = state.copy(
            currentFocus = newDrawer,
            hasUserInteracted = true
        )
        return true
    }

    private fun resolveContentTarget(): FocusNode.Content {
        val last = state.lastContentFocus
        if (last != null && state.contentItems.contains(last)) {
            return last
        }
        return state.contentItems.firstOrNull() ?: FocusNode.Content.MoreApps
    }

    /**
     * Opens the More Apps Drawer overlay.
     * Can ONLY be opened when the Home focus is on MoreApps (AC-F03-05 / SC-068).
     */
    fun openDrawer(): Boolean {
        if (state.isDrawerOpen || state.currentFocus != FocusNode.Content.MoreApps) {
            return false
        }

        val initialDrawerFocus: FocusNode.Drawer = state.drawerApps.firstOrNull()
            ?: FocusNode.Drawer.Close

        state = state.copy(
            isDrawerOpen = true,
            currentFocus = initialDrawerFocus,
            hasUserInteracted = true
        )
        return true
    }

    /**
     * Closes the More Apps Drawer overlay.
     * Restores sole focus to MoreApps on the Home screen (SA-03 / AC-F05-05).
     */
    fun closeDrawer(): Boolean {
        if (!state.isDrawerOpen) {
            return false
        }

        state = state.copy(
            isDrawerOpen = false,
            currentFocus = FocusNode.Content.MoreApps,
            hasUserInteracted = true
        )
        return true
    }

    /**
     * Handles Back key navigation.
     * Disambiguates closing drawer from exiting root launcher (SA-03 / AC-F05-01 / AC-F05-05 / SC-073).
     */
    fun handleBack(): BackResult {
        return if (state.isDrawerOpen) {
            closeDrawer()
            BackResult.DrawerClosed
        } else {
            BackResult.ExitLauncher
        }
    }

    /**
     * Handles OK key activation on current focus node.
     * - In Drawer on Close control: closes drawer and restores MoreApps focus (SC-074).
     * - On Home MoreApps: opens drawer (SC-068).
     */
    fun handleOk(): Boolean {
        return if (state.isDrawerOpen) {
            if (state.currentFocus == FocusNode.Drawer.Close) {
                closeDrawer()
                true
            } else {
                false
            }
        } else {
            if (state.currentFocus == FocusNode.Content.MoreApps) {
                openDrawer()
            } else {
                false
            }
        }
    }

    /**
     * Called when a new O-apps snapshot arrives.
     * Implements spec line 115 (late response does not reopen) and line 123 (repair within O).
     */
    fun onDrawerSnapshotLoaded(apps: List<FocusNode.Drawer.App>) {
        val oldO = state.drawerApps
        val newO = apps
        val newDrawerItems = listOf(FocusNode.Drawer.Close) + newO

        if (!state.isDrawerOpen) {
            // Spec line 115: Late response arriving after drawer is closed
            // updates data but must NOT reopen drawer or steal Home focus!
            state = state.copy(
                drawerApps = newO,
                drawerItems = newDrawerItems
            )
            return
        }

        // When drawer is open, repair focus if currently focused app disappeared
        val newFocus: FocusNode = when (val current = state.currentFocus) {
            FocusNode.Drawer.Close -> FocusNode.Drawer.Close
            is FocusNode.Drawer.App -> {
                if (newO.contains(current)) {
                    current // Retain stable key
                } else {
                    // Spec line 123: "面板 App 消失：用舊 O 索引在新 O 取項，越尾取尾，O 空取關閉面板。索引從 0 起，兩範圍不得混用。"
                    repairOFocus(oldO, newO, current)
                }
            }
            else -> {
                newO.firstOrNull() ?: FocusNode.Drawer.Close
            }
        }

        state = state.copy(
            currentFocus = newFocus,
            drawerApps = newO,
            drawerItems = newDrawerItems
        )
    }

    private fun repairOFocus(
        oldO: List<FocusNode.Drawer.App>,
        newO: List<FocusNode.Drawer.App>,
        target: FocusNode.Drawer.App
    ): FocusNode.Drawer {
        if (newO.isEmpty()) {
            return FocusNode.Drawer.Close
        }
        val oldIndex = oldO.indexOf(target)
        return if (oldIndex in newO.indices) {
            newO[oldIndex]
        } else {
            newO.last() // 越尾取尾
        }
    }

    /**
     * Called when initial loading starts.
     * Defaults focus to Refresh if user has not yet interacted.
     */
    fun onInitialLoading() {
        if (!state.hasUserInteracted) {
            state = state.copy(
                currentFocus = FocusNode.Tool.Refresh,
                lastToolFocus = FocusNode.Tool.Refresh
            )
        }
    }

    /**
     * Called when initial query fails.
     * Defaults focus to Refresh only if user has not interacted (SC-055 / AC-F03-01).
     */
    fun onInitialQueryFailed() {
        if (!state.hasUserInteracted) {
            state = state.copy(
                currentFocus = FocusNode.Tool.Refresh,
                lastToolFocus = FocusNode.Tool.Refresh
            )
        }
    }

    /**
     * Called when a new H-apps snapshot arrives.
     * Follows AC-F03-01 / SC-055 / SC-064 / SC-065 and spec line 123-124 focus repair.
     */
    fun onSnapshotLoaded(apps: List<FocusNode.Content.App>) {
        val oldH = state.hApps
        val newH = normalizeHApps(apps)
        val newContentItems = newH + FocusNode.Content.MoreApps

        val newFocus: FocusNode
        val newLastContentFocus: FocusNode.Content?

        if (!state.hasUserInteracted) {
            // SC-064 / SC-065: User has not interacted; set initial ready focus
            val initial = newH.firstOrNull() ?: FocusNode.Content.MoreApps
            newFocus = initial
            newLastContentFocus = initial
        } else {
            // SC-055: User already interacted; do NOT snatch focus from tools
            when (val current = state.currentFocus) {
                is FocusNode.Tool -> {
                    newFocus = current
                    // Spec line 124: "背景所記住的首頁內容也按 H 修復"
                    val lastContent = state.lastContentFocus
                    newLastContentFocus = when {
                        lastContent is FocusNode.Content.App && !newH.contains(lastContent) -> {
                            repairHFocus(oldH, newH, lastContent)
                        }
                        lastContent != null && newContentItems.contains(lastContent) -> {
                            lastContent
                        }
                        else -> null
                    }
                }
                is FocusNode.Content -> {
                    when {
                        current is FocusNode.Content.App && !newH.contains(current) -> {
                            // Spec line 123: "首頁 App 消失：用舊 H 索引在新 H 取項，越尾取尾，H 空取更多 APP"
                            val repaired = repairHFocus(oldH, newH, current)
                            newFocus = repaired
                            newLastContentFocus = repaired
                        }
                        newContentItems.contains(current) -> {
                            newFocus = current
                            newLastContentFocus = current
                        }
                        else -> {
                            newFocus = FocusNode.Content.MoreApps
                            newLastContentFocus = FocusNode.Content.MoreApps
                        }
                    }
                }
                is FocusNode.Drawer -> {
                    newFocus = current
                    val lastContent = state.lastContentFocus
                    newLastContentFocus = when {
                        lastContent is FocusNode.Content.App && !newH.contains(lastContent) -> {
                            repairHFocus(oldH, newH, lastContent)
                        }
                        lastContent != null && newContentItems.contains(lastContent) -> {
                            lastContent
                        }
                        else -> null
                    }
                }
            }
        }

        state = state.copy(
            currentFocus = newFocus,
            lastContentFocus = newLastContentFocus,
            hApps = newH,
            contentItems = newContentItems
        )
    }

    /**
     * Repairs an invalidated H-app focus against new H list per spec line 123:
     * "首頁 App 消失：用舊 H 索引在新 H 取項，越尾取尾，H 空取更多 APP；兩範圍不得混用"
     */
    private fun repairHFocus(
        oldH: List<FocusNode.Content.App>,
        newH: List<FocusNode.Content.App>,
        target: FocusNode.Content.App
    ): FocusNode.Content {
        if (newH.isEmpty()) {
            return FocusNode.Content.MoreApps
        }
        val oldIndex = oldH.indexOf(target)
        return if (oldIndex in newH.indices) {
            newH[oldIndex]
        } else {
            newH.last() // 越尾取尾
        }
    }

    companion object {
        /**
         * Enforces fixed service order: YouTube -> Netflix -> Disney+.
         * Compresses missing entries (no empty slots).
         */
        fun normalizeHApps(apps: List<FocusNode.Content.App>): List<FocusNode.Content.App> {
            val order = listOf(
                DesignatedService.YOUTUBE,
                DesignatedService.NETFLIX,
                DesignatedService.DISNEY_PLUS
            )

            val sortedApps = mutableListOf<FocusNode.Content.App>()
            for (service in order) {
                val app = apps.firstOrNull { it.service == service }
                if (app != null) {
                    sortedApps.add(app)
                }
            }

            return sortedApps
        }
    }
}
