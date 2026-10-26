package com.miller.tv.launcher.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.miller.tv.launcher.R
import com.miller.tv.launcher.databinding.ActivityMainBinding
import com.miller.tv.launcher.deviceinfo.DeviceInfoSnapshot
import com.miller.tv.launcher.deviceinfo.InfoState
import com.miller.tv.launcher.focus.DesignatedService
import com.miller.tv.launcher.focus.FocusNode
import com.miller.tv.launcher.focus.HomeFocusState

/**
 * Representation of an individual O App item in the Drawer overlay.
 */
data class DrawerAppItemUiState(
    val componentKey: String,
    val displayName: String = "",
    val isSelected: Boolean = false,
    val scale: Float = 1.0f
)

/**
 * Abstraction for Home Launcher UI view properties to allow decoupled, deterministic verification.
 */
interface HomeLauncherViewTarget {
    var isRefreshSelected: Boolean
    var refreshScale: Float

    var isSettingsSelected: Boolean
    var settingsScale: Float

    var isYoutubeVisible: Boolean
    var isYoutubeSelected: Boolean
    var youtubeScale: Float

    var isNetflixVisible: Boolean
    var isNetflixSelected: Boolean
    var netflixScale: Float

    var isDisneyPlusVisible: Boolean
    var isDisneyPlusSelected: Boolean
    var disneyPlusScale: Float

    var isMoreAppsVisible: Boolean
    var isMoreAppsSelected: Boolean
    var moreAppsScale: Float

    var isDrawerVisible: Boolean
    var isDrawerCloseSelected: Boolean
    var drawerCloseScale: Float

    fun bindDrawerApps(apps: List<DrawerAppItemUiState>) {}
    fun updateDeviceInfo(snapshot: DeviceInfoSnapshot) {}
    fun bindAppIcon(componentKey: String, icon: Drawable?) {}
}

/**
 * Production implementation binding directly to Android ViewBinding views.
 */
class BindingHomeLauncherViewTarget(
    private val binding: ActivityMainBinding
) : HomeLauncherViewTarget {

    private val appIcons = mutableMapOf<String, Drawable>()

    override var isRefreshSelected: Boolean
        get() = binding.btnRefresh.isSelected
        set(value) {
            binding.btnRefresh.isSelected = value
        }

    override var refreshScale: Float
        get() = binding.btnRefresh.scaleX
        set(value) {
            binding.btnRefresh.scaleX = value
            binding.btnRefresh.scaleY = value
        }

    override var isSettingsSelected: Boolean
        get() = binding.btnSettings.isSelected
        set(value) {
            binding.btnSettings.isSelected = value
        }

    override var settingsScale: Float
        get() = binding.btnSettings.scaleX
        set(value) {
            binding.btnSettings.scaleX = value
            binding.btnSettings.scaleY = value
        }

    override var isYoutubeVisible: Boolean
        get() = binding.cardYoutube.visibility == View.VISIBLE
        set(value) {
            binding.cardYoutube.visibility = if (value) View.VISIBLE else View.GONE
            binding.cardYoutube.isFocusable = false
        }

    override var isYoutubeSelected: Boolean
        get() = binding.cardYoutube.isSelected
        set(value) {
            binding.cardYoutube.isSelected = value
        }

    override var youtubeScale: Float
        get() = binding.cardYoutube.scaleX
        set(value) {
            binding.cardYoutube.scaleX = value
            binding.cardYoutube.scaleY = value
        }

    override var isNetflixVisible: Boolean
        get() = binding.cardNetflix.visibility == View.VISIBLE
        set(value) {
            binding.cardNetflix.visibility = if (value) View.VISIBLE else View.GONE
            binding.cardNetflix.isFocusable = false
        }

    override var isNetflixSelected: Boolean
        get() = binding.cardNetflix.isSelected
        set(value) {
            binding.cardNetflix.isSelected = value
        }

    override var netflixScale: Float
        get() = binding.cardNetflix.scaleX
        set(value) {
            binding.cardNetflix.scaleX = value
            binding.cardNetflix.scaleY = value
        }

    override var isDisneyPlusVisible: Boolean
        get() = binding.cardDisneyPlus.visibility == View.VISIBLE
        set(value) {
            binding.cardDisneyPlus.visibility = if (value) View.VISIBLE else View.GONE
            binding.cardDisneyPlus.isFocusable = false
        }

    override var isDisneyPlusSelected: Boolean
        get() = binding.cardDisneyPlus.isSelected
        set(value) {
            binding.cardDisneyPlus.isSelected = value
        }

    override var disneyPlusScale: Float
        get() = binding.cardDisneyPlus.scaleX
        set(value) {
            binding.cardDisneyPlus.scaleX = value
            binding.cardDisneyPlus.scaleY = value
        }

    override var isMoreAppsVisible: Boolean
        get() = binding.cardMoreApps.visibility == View.VISIBLE
        set(value) {
            binding.cardMoreApps.visibility = if (value) View.VISIBLE else View.GONE
            binding.cardMoreApps.isFocusable = false
        }

    override var isMoreAppsSelected: Boolean
        get() = binding.cardMoreApps.isSelected
        set(value) {
            binding.cardMoreApps.isSelected = value
        }

    override var moreAppsScale: Float
        get() = binding.cardMoreApps.scaleX
        set(value) {
            binding.cardMoreApps.scaleX = value
            binding.cardMoreApps.scaleY = value
        }

    override var isDrawerVisible: Boolean
        get() = binding.drawerOverlayContainer.visibility == View.VISIBLE
        set(value) {
            binding.drawerOverlayContainer.visibility = if (value) View.VISIBLE else View.GONE
            binding.drawerOverlayContainer.isFocusable = false
        }

    override var isDrawerCloseSelected: Boolean
        get() = binding.drawerClose.isSelected
        set(value) {
            binding.drawerClose.isSelected = value
        }

    override var drawerCloseScale: Float
        get() = binding.drawerClose.scaleX
        set(value) {
            binding.drawerClose.scaleX = value
            binding.drawerClose.scaleY = value
        }

    override fun bindDrawerApps(apps: List<DrawerAppItemUiState>) {
        val container = binding.drawerAppList
        val context = container.context ?: return
        val inflater = LayoutInflater.from(context)

        // Ensure container has exactly the same number of views
        while (container.childCount > apps.size) {
            container.removeViewAt(container.childCount - 1)
        }
        while (container.childCount < apps.size) {
            val itemView = inflater.inflate(R.layout.item_drawer_app, container, false)
            container.addView(itemView)
        }

        for (i in apps.indices) {
            val appState = apps[i]
            val view = container.getChildAt(i)
            view.isFocusable = false
            view.isSelected = appState.isSelected
            view.scaleX = appState.scale
            view.scaleY = appState.scale

            val tvName = view.findViewById<TextView>(R.id.tvDrawerItemName)
            if (tvName != null) {
                tvName.text = if (appState.displayName.isNotEmpty()) appState.displayName else appState.componentKey
            }

            val ivIcon = view.findViewById<ImageView>(R.id.ivDrawerItemIcon)
            if (ivIcon != null) {
                val iconDrawable = appIcons[appState.componentKey]
                if (iconDrawable != null) {
                    ivIcon.setImageDrawable(iconDrawable)
                    ivIcon.visibility = View.VISIBLE
                } else {
                    ivIcon.setImageDrawable(null)
                    ivIcon.visibility = View.GONE
                }
            }
        }
    }

    override fun bindAppIcon(componentKey: String, icon: Drawable?) {
        if (icon != null) {
            appIcons[componentKey] = icon
        } else {
            appIcons.remove(componentKey)
        }
    }

    override fun updateDeviceInfo(snapshot: DeviceInfoSnapshot) {
        binding.tvTime.text = when (val s = snapshot.timeState) {
            is InfoState.Available -> s.data
            is InfoState.Unknown -> "時間未知"
            is InfoState.Unavailable -> s.displayMessage
        }

        binding.tvDate.text = when (val s = snapshot.dateState) {
            is InfoState.Available -> s.data
            is InfoState.Unknown -> "日期未知"
            is InfoState.Unavailable -> s.displayMessage
        }

        binding.tvNetwork.text = when (val s = snapshot.networkState) {
            is InfoState.Available -> s.data.displayString
            is InfoState.Unknown -> "網路狀態: 狀態未知"
            is InfoState.Unavailable -> "網路狀態: ${s.displayMessage}"
        }

        binding.tvHdmi.text = when (val s = snapshot.hdmiState) {
            is InfoState.Available -> {
                if (s.data.ports.isEmpty()) "HDMI: 無連接埠" else "HDMI: " + s.data.ports.joinToString { "${it.portId} (${if (it.isConnected) "已連線" else "未連線"})" }
            }
            is InfoState.Unknown -> "HDMI: 狀態未知"
            is InfoState.Unavailable -> "HDMI: ${s.displayMessage}"
        }

        binding.tvStorage.text = when (val s = snapshot.storageState) {
            is InfoState.Available -> "儲存空間: ${s.data.displayString}"
            is InfoState.Unknown -> "儲存空間: 狀態未知"
            is InfoState.Unavailable -> "儲存空間: ${s.displayMessage}"
        }
    }
}

/**
 * Binds pure Kotlin focus domain states and snapshots to View targets.
 * Strictly adheres to Ten-Foot Celestial Glass tokens:
 * - Focus scale 1.06x target
 * - High-contrast selection state
 * - Ensures hidden views cannot receive focus or visual highlight
 * - Drawer container is GONE and non-focusable when closed
 */
class HomeFocusViewBinder(
    val target: HomeLauncherViewTarget
) {
    constructor(binding: ActivityMainBinding) : this(BindingHomeLauncherViewTarget(binding))

    companion object {
        const val FOCUSED_SCALE = 1.06f
        const val DEFAULT_SCALE = 1.0f
    }

    private var appLabels: Map<String, String> = emptyMap()

    /**
     * Updates user-readable application labels discovered by the scanner.
     */
    fun updateAppLabels(labels: Map<String, String>) {
        this.appLabels = labels
    }

    /**
     * Updates the horizontal Home App row to display 0..3 designated apps followed by More Apps.
     * Guaranteed fixed order: YouTube -> Netflix -> Disney+ -> More Apps.
     */
    fun bindHomeApps(hApps: List<FocusNode.Content.App>) {
        val hasYoutube = hApps.any { it.service == DesignatedService.YOUTUBE }
        val hasNetflix = hApps.any { it.service == DesignatedService.NETFLIX }
        val hasDisneyPlus = hApps.any { it.service == DesignatedService.DISNEY_PLUS }

        target.isYoutubeVisible = hasYoutube
        if (!hasYoutube) {
            target.isYoutubeSelected = false
            target.youtubeScale = DEFAULT_SCALE
        }

        target.isNetflixVisible = hasNetflix
        if (!hasNetflix) {
            target.isNetflixSelected = false
            target.netflixScale = DEFAULT_SCALE
        }

        target.isDisneyPlusVisible = hasDisneyPlus
        if (!hasDisneyPlus) {
            target.isDisneyPlusSelected = false
            target.disneyPlusScale = DEFAULT_SCALE
        }

        // More Apps is ALWAYS present and visible (F02 / SA-01 / D7)
        target.isMoreAppsVisible = true
    }

    /**
     * Synchronizes O App items inside the Drawer overlay with focus highlights and readable labels.
     */
    fun bindDrawerApps(drawerApps: List<FocusNode.Drawer.App>, currentFocus: FocusNode) {
        val items = drawerApps.map { app ->
            val isSelected = currentFocus == app
            val label = appLabels[app.componentKey] ?: app.componentKey.substringAfterLast('/')
            DrawerAppItemUiState(
                componentKey = app.componentKey,
                displayName = label,
                isSelected = isSelected,
                scale = if (isSelected) FOCUSED_SCALE else DEFAULT_SCALE
            )
        }
        target.bindDrawerApps(items)
    }

    /**
     * Applies visual focus styling based on the current domain FocusNode.
     */
    fun applyFocus(focus: FocusNode) {
        val isRefresh = focus == FocusNode.Tool.Refresh
        target.isRefreshSelected = isRefresh
        target.refreshScale = if (isRefresh) FOCUSED_SCALE else DEFAULT_SCALE

        val isSettings = focus == FocusNode.Tool.Settings
        target.isSettingsSelected = isSettings
        target.settingsScale = if (isSettings) FOCUSED_SCALE else DEFAULT_SCALE

        val isYoutube = focus is FocusNode.Content.App && focus.service == DesignatedService.YOUTUBE
        target.isYoutubeSelected = isYoutube && target.isYoutubeVisible
        target.youtubeScale = if (isYoutube && target.isYoutubeVisible) FOCUSED_SCALE else DEFAULT_SCALE

        val isNetflix = focus is FocusNode.Content.App && focus.service == DesignatedService.NETFLIX
        target.isNetflixSelected = isNetflix && target.isNetflixVisible
        target.netflixScale = if (isNetflix && target.isNetflixVisible) FOCUSED_SCALE else DEFAULT_SCALE

        val isDisneyPlus = focus is FocusNode.Content.App && focus.service == DesignatedService.DISNEY_PLUS
        target.isDisneyPlusSelected = isDisneyPlus && target.isDisneyPlusVisible
        target.disneyPlusScale = if (isDisneyPlus && target.isDisneyPlusVisible) FOCUSED_SCALE else DEFAULT_SCALE

        val isMoreApps = focus == FocusNode.Content.MoreApps
        target.isMoreAppsSelected = isMoreApps
        target.moreAppsScale = if (isMoreApps) FOCUSED_SCALE else DEFAULT_SCALE

        val isDrawerClose = focus == FocusNode.Drawer.Close
        target.isDrawerCloseSelected = isDrawerClose
        target.drawerCloseScale = if (isDrawerClose) FOCUSED_SCALE else DEFAULT_SCALE
    }

    /**
     * Controls Drawer Overlay visibility and focus isolation.
     */
    fun updateDrawerVisibility(isOpen: Boolean) {
        target.isDrawerVisible = isOpen
    }

    /**
     * Atomically synchronizes all UI elements with an immutable HomeFocusState snapshot.
     */
    fun syncState(state: HomeFocusState) {
        bindHomeApps(state.hApps)
        updateDrawerVisibility(state.isDrawerOpen)
        bindDrawerApps(state.drawerApps, state.currentFocus)
        applyFocus(state.currentFocus)
    }
}
