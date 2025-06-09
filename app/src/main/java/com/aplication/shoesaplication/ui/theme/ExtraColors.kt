package com.aplication.shoesaplication.ui.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color


data class ExtraColors(
    val backgroundScreen: Color
)

val LightExtraColors = ExtraColors(
    backgroundScreen = WhiteBackGround // Cor de fundo clara
)

val DarkExtraColors = ExtraColors(
    backgroundScreen = BlacBackGround // Cor de fundo escura
)

val LocalExtraColors = compositionLocalOf { LightExtraColors }