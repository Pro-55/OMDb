package com.example.omdb.util

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    showBackground = true,
    uiMode = UI_MODE_NIGHT_YES,
    device = Devices.PHONE,
    name = "Phone [Dark]"
)
annotation class PhoneDarkPreview

@Preview(
    showBackground = true,
    uiMode = UI_MODE_NIGHT_YES,
    device = Devices.FOLDABLE,
    name = "Foldable [Dark]"
)
annotation class FoldableDarkPreview

@Preview(
    showBackground = true,
    uiMode = UI_MODE_NIGHT_YES,
    device = Devices.TABLET,
    name = "Tablet [Dark]"
)
annotation class TabletDarkPreview

@Preview(
    showBackground = true,
    uiMode = UI_MODE_NIGHT_YES,
    device = Devices.DESKTOP,
    name = "Desktop [Dark]"
)
annotation class DesktopDarkPreview

@PhoneDarkPreview
@FoldableDarkPreview
@TabletDarkPreview
@DesktopDarkPreview
annotation class UltraDarkPreview

@Preview(
    showBackground = true,
    uiMode = UI_MODE_NIGHT_NO,
    device = Devices.PHONE,
    name = "Phone [Light]"
)
annotation class PhoneLightPreview

@Preview(
    showBackground = true,
    uiMode = UI_MODE_NIGHT_NO,
    device = Devices.FOLDABLE,
    name = "Foldable [Light]"
)
annotation class FoldableLightPreview

@Preview(
    showBackground = true,
    uiMode = UI_MODE_NIGHT_NO,
    device = Devices.TABLET,
    name = "Tablet [Light]"
)
annotation class TabletLightPreview

@Preview(
    showBackground = true,
    uiMode = UI_MODE_NIGHT_NO,
    device = Devices.DESKTOP,
    name = "Desktop [Light]"
)
annotation class DesktopLightPreview

@PhoneLightPreview
@FoldableLightPreview
@TabletLightPreview
@DesktopLightPreview
annotation class UltraLightPreview

@UltraDarkPreview
@UltraLightPreview
annotation class UltraPreview