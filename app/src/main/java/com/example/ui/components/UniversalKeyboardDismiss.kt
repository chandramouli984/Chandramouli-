package com.example.ui.components

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.input.pointer.pointerInput

/**
 * Universal modifier that dismisses the software keyboard and clears focus
 * whenever the user taps anywhere on the background.
 */
fun Modifier.tapOutsideToDismiss(focusManager: FocusManager): Modifier =
    this.pointerInput(Unit) {
        detectTapGestures(onTap = {
            focusManager.clearFocus()
        })
    }
