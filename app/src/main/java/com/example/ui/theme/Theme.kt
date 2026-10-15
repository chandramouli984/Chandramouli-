package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val StudioColorScheme = darkColorScheme(
    primary = StudioPrimary,
    onPrimary = Color.White,
    primaryContainer = StudioPrimaryGlow.copy(alpha = 0.2f),
    onPrimaryContainer = Color.White,
    secondary = StudioSecondary,
    onSecondary = Color.Black,
    secondaryContainer = StudioSecondary.copy(alpha = 0.2f),
    onSecondaryContainer = StudioSecondary,
    tertiary = StudioTertiary,
    onTertiary = Color.White,
    background = StudioBackground,
    onBackground = StudioTextPrimary,
    surface = StudioSurface,
    onSurface = StudioTextPrimary,
    surfaceVariant = StudioCard,
    onSurfaceVariant = StudioTextSecondary,
    outline = StudioBorder,
    outlineVariant = StudioBorder.copy(alpha = 0.5f),
    error = StudioError,
    onError = Color.White
)

@Composable
fun MyApplicationTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = StudioColorScheme,
        typography = Typography,
        content = content
    )
}
