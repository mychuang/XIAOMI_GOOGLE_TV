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
 * Focus node identity in the Home screen.
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
 * Immutable focus state snapshot for ViewBinding observation and decoupled testing.
 */
data class HomeFocusState(
    val currentFocus: FocusNode,
    val lastToolFocus: FocusNode.Tool,
    val lastContentFocus: FocusNode.Content?,
    val hasUserInteracted: Boolean,
    val hApps: List<FocusNode.Content.App>,
    val contentItems: List<FocusNode.Content>
)

/**
 * Pure Kotlin Home focus state machine.
 * Implements F03 (AC-F03-01, AC-F03-02, AC-F03-03) and SA-01 contract.
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
        contentItems = normalizedInitialH + FocusNode.Content.MoreApps
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
        return when (val focus = state.currentFocus) {
            is FocusNode.Tool -> handleToolNavigation(focus, direction)
            is FocusNode.Content -> handleContentNavigation(focus, direction)
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

    private fun resolveContentTarget(): FocusNode.Content {
        val last = state.lastContentFocus
        if (last != null && state.contentItems.contains(last)) {
            return last
        }
        return state.contentItems.firstOrNull() ?: FocusNode.Content.MoreApps
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
