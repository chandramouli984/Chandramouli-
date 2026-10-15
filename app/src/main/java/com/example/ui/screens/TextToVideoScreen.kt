package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AppLanguage
import com.example.model.GeneratedMediaItem
import com.example.model.LocalizedStrings
import com.example.ui.components.tapOutsideToDismiss
import com.example.ui.theme.StudioAmber
import com.example.ui.theme.StudioBorder
import com.example.ui.theme.StudioCard
import com.example.ui.theme.StudioEmerald
import com.example.ui.theme.StudioPrimary
import com.example.ui.theme.StudioSecondary

@Composable
fun TextToVideoScreen(
    prompt: String,
    onPromptChange: (String) -> Unit,
    selectedDurationSeconds: Int,
    onSelectDuration: (Int) -> Unit,
    onGenerate: () -> Unit,
    isGenerating: Boolean,
    progress: Float,
    statusMessage: String,
    lastGeneratedItem: GeneratedMediaItem?,
    currentLanguage: AppLanguage,
    onShare: (GeneratedMediaItem) -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    var isPlayingPreview by remember { mutableStateOf(false) }
    var previewPlaybackSec by remember { mutableFloatStateOf(0f) }

    val durationPresets = listOf(
        5 to "5s",
        15 to "15s",
        30 to "30s",
        60 to "1 min",
        300 to "5 min",
        600 to "10 min",
        1800 to "30 min"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .tapOutsideToDismiss(focusManager)
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Section Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = LocalizedStrings.getString("text_to_video", currentLanguage),
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Black),
                    color = Color.White
                )
                Text(
                    text = "Flexible Lengths • 5s Clips to 30-Minute Feature Videos",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF94A3B8)
                )
            }

            Surface(
                color = StudioSecondary.copy(alpha = 0.2f),
                border = androidx.compose.foundation.BorderStroke(1.dp, StudioSecondary),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "UNLIMITED 4K",
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                    color = StudioSecondary
                )
            }
        }

        // Prompt Input
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, StudioBorder, RoundedCornerShape(16.dp)),
            colors = CardDefaults.cardColors(containerColor = StudioCard),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                OutlinedTextField(
                    value = prompt,
                    onValueChange = onPromptChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(110.dp)
                        .testTag("text_to_video_prompt_input"),
                    placeholder = {
                        Text(
                            text = "Describe camera movement, lighting, actions & scene...",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF64748B)
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        cursorColor = StudioSecondary,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
                )
            }
        }

        // Duration Presets (No Time Limit Constraints)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Timer,
                    contentDescription = null,
                    tint = StudioAmber,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Duration Settings (Zero Limit)",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                    color = Color.White
                )
            }
            Text(
                text = if (selectedDurationSeconds >= 60) "${selectedDurationSeconds / 60}m" else "${selectedDurationSeconds}s",
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Black),
                color = StudioAmber
            )
        }

        // Presets row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            durationPresets.forEach { (seconds, label) ->
                val isSelected = seconds == selectedDurationSeconds
                Surface(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .border(
                            1.dp,
                            if (isSelected) StudioAmber else StudioBorder,
                            RoundedCornerShape(10.dp)
                        )
                        .clickable { onSelectDuration(seconds) }
                        .testTag("duration_${label.replace(" ", "_")}"),
                    color = if (isSelected) StudioAmber.copy(alpha = 0.25f) else StudioCard
                ) {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        ),
                        color = if (isSelected) StudioAmber else Color(0xFF94A3B8),
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                    )
                }
            }
        }

        // Resolution & Quality Settings
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Surface(
                modifier = Modifier
                    .weight(1f)
                    .border(1.dp, StudioBorder, RoundedCornerShape(10.dp)),
                color = StudioCard,
                shape = RoundedCornerShape(10.dp)
            ) {
                Row(
                    modifier = Modifier.padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Videocam,
                        contentDescription = null,
                        tint = StudioSecondary,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "1080p • 60 FPS",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = Color.White
                    )
                }
            }

            Surface(
                modifier = Modifier
                    .weight(1f)
                    .border(1.dp, StudioBorder, RoundedCornerShape(10.dp)),
                color = StudioCard,
                shape = RoundedCornerShape(10.dp)
            ) {
                Row(
                    modifier = Modifier.padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Movie,
                        contentDescription = null,
                        tint = StudioEmerald,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Async Diffusion Engine",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = Color.White
                    )
                }
            }
        }

        // Generate Button
        Button(
            onClick = {
                focusManager.clearFocus()
                onGenerate()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .testTag("generate_video_button"),
            colors = ButtonDefaults.buttonColors(
                containerColor = StudioSecondary,
                contentColor = Color.Black
            ),
            shape = RoundedCornerShape(14.dp),
            enabled = !isGenerating
        ) {
            if (isGenerating) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = Color.Black,
                    strokeWidth = 2.dp
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "Rendering Video Pipeline...",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                )
            } else {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Movie,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Synthesize ${if (selectedDurationSeconds >= 60) "${selectedDurationSeconds / 60}m" else "${selectedDurationSeconds}s"} Video",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                    )
                }
            }
        }

        // Active Queue Progress
        AnimatedVisibility(visible = isGenerating) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp)),
                    color = StudioSecondary,
                    trackColor = Color(0xFF1E293B)
                )
                Text(
                    text = statusMessage,
                    style = MaterialTheme.typography.labelSmall,
                    color = StudioSecondary
                )
            }
        }

        // Rendered Video Preview Player
        lastGeneratedItem?.let { video ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.5.dp, StudioSecondary, RoundedCornerShape(16.dp))
                    .testTag("generated_video_player_card"),
                colors = CardDefaults.cardColors(containerColor = StudioCard),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    // Simulated Video Canvas
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.Black),
                        contentAlignment = Alignment.Center
                    ) {
                        val heroId = context.resources.getIdentifier(
                            "hero_studio_banner", "drawable", context.packageName
                        )
                        if (heroId != 0) {
                            Image(
                                painter = painterResource(id = heroId),
                                contentDescription = video.title,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }

                        // Play/Pause Overlay Button
                        Surface(
                            modifier = Modifier
                                .size(56.dp)
                                .clip(CircleShape)
                                .clickable { isPlayingPreview = !isPlayingPreview },
                            color = Color(0xAA000000)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = if (isPlayingPreview) Icons.Default.Pause else Icons.Default.PlayArrow,
                                    contentDescription = if (isPlayingPreview) "Pause" else "Play",
                                    tint = StudioSecondary,
                                    modifier = Modifier.size(32.dp)
                                )
                            }
                        }

                        // Video Duration Pill
                        Surface(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(8.dp),
                            color = Color(0xCC000000),
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = video.durationFormatted ?: "0:15",
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.White
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = video.title,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Player Scrub Control
                    Slider(
                        value = previewPlaybackSec,
                        onValueChange = { previewPlaybackSec = it },
                        valueRange = 0f..(video.durationSeconds.toFloat().coerceAtLeast(1f)),
                        colors = SliderDefaults.colors(
                            thumbColor = StudioSecondary,
                            activeTrackColor = StudioSecondary,
                            inactiveTrackColor = Color(0xFF334155)
                        )
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "0:${if (previewPlaybackSec.toInt() < 10) "0${previewPlaybackSec.toInt()}" else previewPlaybackSec.toInt()}",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color(0xFF94A3B8)
                        )
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            IconButton(onClick = { onShare(video) }) {
                                Icon(
                                    imageVector = Icons.Default.Share,
                                    contentDescription = "Share",
                                    tint = Color.White
                                )
                            }
                            IconButton(onClick = { /* download action */ }) {
                                Icon(
                                    imageVector = Icons.Default.Download,
                                    contentDescription = "Export MP4",
                                    tint = Color.White
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}
