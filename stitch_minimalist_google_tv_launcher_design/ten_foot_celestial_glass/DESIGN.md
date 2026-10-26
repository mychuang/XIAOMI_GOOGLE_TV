---
name: Ten-Foot Celestial Glass
colors:
  surface: '#10131a'
  surface-dim: '#10131a'
  surface-bright: '#363940'
  surface-container-lowest: '#0b0e14'
  surface-container-low: '#191c22'
  surface-container: '#1d2026'
  surface-container-high: '#272a31'
  surface-container-highest: '#32353c'
  on-surface: '#e0e2eb'
  on-surface-variant: '#bac9cc'
  inverse-surface: '#e0e2eb'
  inverse-on-surface: '#2d3037'
  outline: '#849396'
  outline-variant: '#3b494c'
  surface-tint: '#00daf3'
  primary: '#c3f5ff'
  on-primary: '#00363d'
  primary-container: '#00e5ff'
  on-primary-container: '#00626e'
  inverse-primary: '#006875'
  secondary: '#adc6ff'
  on-secondary: '#002e6a'
  secondary-container: '#0566d9'
  on-secondary-container: '#e6ecff'
  tertiary: '#e4edff'
  on-tertiary: '#00315d'
  tertiary-container: '#b5d2ff'
  on-tertiary-container: '#005aa2'
  error: '#ffb4ab'
  on-error: '#690005'
  error-container: '#93000a'
  on-error-container: '#ffdad6'
  primary-fixed: '#9cf0ff'
  primary-fixed-dim: '#00daf3'
  on-primary-fixed: '#001f24'
  on-primary-fixed-variant: '#004f58'
  secondary-fixed: '#d8e2ff'
  secondary-fixed-dim: '#adc6ff'
  on-secondary-fixed: '#001a42'
  on-secondary-fixed-variant: '#004395'
  tertiary-fixed: '#d4e3ff'
  tertiary-fixed-dim: '#a4c9ff'
  on-tertiary-fixed: '#001c39'
  on-tertiary-fixed-variant: '#004883'
  background: '#10131a'
  on-background: '#e0e2eb'
  surface-variant: '#32353c'
typography:
  display-time:
    fontFamily: Noto Sans
    fontSize: 72px
    fontWeight: '700'
    lineHeight: 80px
    letterSpacing: -0.02em
  headline-lg:
    fontFamily: Noto Sans
    fontSize: 36px
    fontWeight: '700'
    lineHeight: 44px
    letterSpacing: 0.04em
  headline-md:
    fontFamily: Noto Sans
    fontSize: 28px
    fontWeight: '600'
    lineHeight: 36px
    letterSpacing: 0.03em
  title-sm:
    fontFamily: Noto Sans
    fontSize: 22px
    fontWeight: '600'
    lineHeight: 28px
    letterSpacing: 0.02em
  body-md:
    fontFamily: Noto Sans
    fontSize: 20px
    fontWeight: '400'
    lineHeight: 28px
    letterSpacing: 0.02em
  label-md:
    fontFamily: Noto Sans
    fontSize: 18px
    fontWeight: '500'
    lineHeight: 24px
    letterSpacing: 0.04em
  label-sm:
    fontFamily: Noto Sans
    fontSize: 15px
    fontWeight: '500'
    lineHeight: 20px
    letterSpacing: 0.05em
rounded:
  sm: 0.25rem
  DEFAULT: 0.5rem
  md: 0.75rem
  lg: 1rem
  xl: 1.5rem
  full: 9999px
spacing:
  gutter: 28px
  margin: 96px
  margin-safe-top: 54px
  margin-safe-bottom: 54px
  margin-safe-x: 96px
  space-xs: 8px
  space-sm: 14px
  space-md: 20px
  space-lg: 32px
  space-xl: 48px
---

## Brand & Style

This design system establishes a focused, cinematic 10-foot television launcher interface engineered specifically for living room viewing environments (2–3 meters distance at 1080p). The design ethos balances atmospheric minimalism with tactical high-contrast visibility.

### Design Movement & Aesthetic
- **Atmospheric Glassmorphism meets Lean Utility:** Combines dark tinted optical acrylic layers, fine light-leaks, and deep ambient space. Content sits atop a deep void, eliminating television backlight bleed and eye fatigue in low-light environments.
- **Directional Clarity (D-Pad Driven):** Built entirely around deliberate spatial navigation (DPAD Up/Down/Left/Right/Center). Visual affordance is binary and immediate: elements are either resting in atmospheric calm or ignited with luminous, energetic certainty.
- **Zero-Friction Minimal Functionalism:** Eliminates visual clutter, auto-playing video noise, and oversized marketing banners. The UI acts as a tranquil, high-precision gateway directly into core media experiences.

## Colors

The palette is tuned specifically for emissive television panels (OLED and LED LCD), emphasizing high luminous contrast ratios to overcome distance and panel ambient reflections.

### Surface and Void
- **Atmospheric Void:** Gradients resolve from an ultra-deep charcoal black (`#080B11`) into an atmospheric navy slate (`#0E1420`).
- **Glass Surfaces:** Dark tinted acrylic surfaces use translucent base fills (`rgba(22, 28, 40, 0.72)`) bordered by directional top-lit borders (`rgba(255, 255, 255, 0.08)`).

### Luminous Focus Spectrum
- **Focused Foreground:** Crisp pure white (`#FFFFFF`) ensures absolute textual and graphical legibility across the living room.
- **Radial Field / Aura:** Active navigation nodes pulse with cyan-ice luminescence (`#00E5FF` blending into `#60A5FA`), projecting an electric aura that grounds the user's peripheral vision.
- **Unfocused Slate:** Inactive interactive elements settle back with muted slate tones (`rgba(255, 255, 255, 0.55)` for text; `rgba(255, 255, 255, 0.06)` for icon backgrounds).

## Typography

Typography is calibrated for instantaneous 10-foot recognition of Traditional Chinese (繁體中文) glyphs and Latin characters. Chinese characters require distinct stroke separation at a distance; therefore, extra-bold display weights, open counter spaces, and generous character tracking (`letter-spacing`) are enforced across all levels.

- **Typeface Stack:** System resolves in sequence: `PingFang TC`, `Noto Sans TC`, `Microsoft JhengHei UI`, followed by generic fallback `system-ui, sans-serif`.
- **Optical Weighting:** Standard body copy is bumped to a minimum of 20px, while titles and navigational labels utilize medium-to-bold weights (600–700) to resist visual degradation caused by TV anti-aliasing engines and motion blur.

## Layout & Spacing

The canvas is strictly locked to a fixed 1920x1080 landscape resolution (16:9 aspect ratio). 

### TV Overscan Safe Margins
- **Safe Zone Boundary:** A non-negotiable 5% perimeter overscan exclusion zone protects content from edge cropping on consumer display panels:
  - Horizontal Margins (`margin-safe-x`): **96px** left and right edges.
  - Vertical Margins (`margin-safe-top` / `margin-safe-bottom`): **54px** top and bottom edges.
- Interactive boundaries and text baselines never breach this perimeter.

### Grid & Compositional Rhythm
- **Top Utility Bar:** Positioned flush against the upper safe limit (`54px` from top edge), accommodating digital clock displays and the primary dual system triggers: Refresh (`重新整理`) and System Settings (`系統設定`).
- **Main Launch Anchor:** Positioned along the lower visual center-line, standardizing a single horizontal row with consistent item spacing (`28px` gutter) allowing linear Left/Right DPAD traversal.
- **Overlay Drawer:** A vertical modal surface spanning the full vertical height of the safe screen, fixed against the right safe margin with a 480px width profile.

## Elevation & Depth

Visual hierarchy on a 10-foot UI cannot rely on subtle drop shadows, as panel gamma curves flatten weak gradients. Depth is established through structural luminance, light leaks, and dynamic scale.

### Depth Layers
1. **Layer 0 (Canvas Base):** Deep void gradient (`#080B11` to `#0E1420`) with a static ambient cyan/blue backlight bloom centered behind the active zone.
2. **Layer 1 (Resting Glass Surfaces):** Backdrops utilize hardware-accelerated blur (`backdrop-filter: blur(24px)`), filled with `rgba(22, 28, 40, 0.65)` and bordered with an inner hairline stroke (`1px solid rgba(255, 255, 255, 0.08)`).
3. **Layer 2 (The Focused State):** When an element gains Android DPAD focus, it jumps forward physically and luminously:
   - **Scale Transform:** Hardware-accelerated scale expansion to `1.06x` via `transform: scale3d(1.06, 1.06, 1.0)`.
   - **Luminous Outline:** `3px solid #FFFFFF` high-contrast outline.
   - **Photonic Bloom:** Deep exterior halo composed of dual shadows: `box-shadow: 0 0 24px rgba(0, 229, 255, 0.5), 0 0 48px rgba(59, 130, 246, 0.3)`.

## Shapes

The design system incorporates geometric squircle-like curvatures that align with modern consumer hardware while retaining architectural precision.

- **Standard Interactive Elements (Cards, Badges):** 16px corner radius (`rounded-lg`). Under focus magnification, the curve scales proportionally without distortion.
- **Utility Buttons & Pills:** Fully rounded capsule curves (`rounded-full` / 9999px) to communicate utility and rapid navigational triggers.
- **Overlay Drawers & Sheets:** 24px corner radius (`rounded-xl`) applied to incoming edges, creating soft visual containment against the screen frame.

## Components

### 1. App Shortcut Badges (Main Row)
- **Role:** Direct access to preferred services (YouTube, Netflix, Disney+, 更多 APP).
- **Structure:** Functional square/squircle badges (180px x 180px) focused on high-contrast iconography rather than visual poster noise.
- **Resting:** Acrylic glass background (`rgba(22, 28, 40, 0.72)`), subtle 1px inner border, 64px monochrome or native branded icon with 80% opacity, title label rendered in `label-md` below badge.
- **Focused:** Scaled to 1.06x, bright white boundary stroke (3px), radiant cyan-blue glow, icon shifts to 100% luminosity, text label changes from muted slate to pure white (`#FFFFFF`) with distinct bold font weight.

### 2. Top Utility Actions
- **Role:** Immediate system controls for launcher refresh and TV configuration.
- **Structure:** Horizontal capsule buttons housing an icon and concise Traditional Chinese label (`重新整理` / `系統設定`).
- **Resting:** Translucent pill background (`rgba(255, 255, 255, 0.06)`), 14px horizontal padding, 8px vertical padding. Icon and text in slate white (`rgba(255, 255, 255, 0.65)`).
- **Focused:** Background fills with pure white (`#FFFFFF`), text and icon invert to deep void black (`#080B11`), illuminated by an ambient outer aura (`0 0 20px rgba(96, 165, 250, 0.5)`).

### 3. More Apps Linear Drawer ("更多 APP" Panel)
- **Role:** Deep list traversal without obscuring living room context.
- **Structure:** Right-docked acrylic sheet (width: 480px) stretching vertically between safe boundaries.
- **Behavior:** Triggered by selecting the "更多 APP" shortcut. Displays all installed applications in a disciplined single-column vertical list.
- **List Item Specification:** 72px height, horizontal flex orientation containing a 44px app icon, followed by primary title (`title-sm`) and package/system tag (`label-sm`).
- **Focus Mechanics:** Focus stays vertically locked; list smoothly autoscrolls to keep active item vertically centered. Focused row receives an electric cyan left-indicator strip (4px wide, `#00E5FF`) and elevated surface tint (`rgba(255, 255, 255, 0.12)`).

### 4. Clock & Environment Status
- **Role:** Ambient time and connectivity indicator.
- **Structure:** Positioned at the top-left overscan safe margin. Time displayed in `display-time` (`72px`, 700 weight) with non-flashing colon. Date and Wi-Fi state aligned underneath in `label-md` slate typography.