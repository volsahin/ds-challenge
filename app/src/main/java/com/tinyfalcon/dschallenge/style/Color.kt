package com.tinyfalcon.dschallenge.style

import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

val DsGray = Color(0xff1e1e1e)
val DsLightGray = Color(0xff333336)
val Yellow200 = Color(0xffffeb46)
val Blue200 = Color(0xff91a4fc)

val DarkColors = darkColors(
    primary = Yellow200,
    secondary = Blue200
)
val LightColors = lightColors(
    primary = Yellow200,
    background = Color.Black,
    primaryVariant = Yellow200,
    secondary = Blue200
)
