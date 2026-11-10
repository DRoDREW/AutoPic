package com.autopic.app

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColors = darkColorScheme(
    primary = Color(0xFF7DD3FC),
    secondary = Color(0xFF9AE6B4),
    tertiary = Color(0xFFF9A8D4),
    background = Color(0xFF071421),
    surface = Color(0xFF102231),
    onPrimary = Color(0xFF04131D),
    onSecondary = Color(0xFF081C1B),
    onBackground = Color(0xFFEAF6FF),
    onSurface = Color(0xFFEAF6FF)
)

private val LightColors = lightColorScheme(
    primary = Color(0xFF0EA5E9),
    secondary = Color(0xFF10B981),
    tertiary = Color(0xFFE879F9),
    background = Color(0xFFF7FBFF),
    surface = Color(0xFFFFFFFF),
    onPrimary = Color(0xFFFFFFFF),
    onSecondary = Color(0xFF083B3A),
    onBackground = Color(0xFF102030),
    onSurface = Color(0xFF102030)
)

@Composable
fun AutoPicTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors
    MaterialTheme(
        colorScheme = colors,
        content = content
    )
}
