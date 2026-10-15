package com.example.ui.screens

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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ContentCut
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Subtitles
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AppLanguage
import com.example.model.LocalizedStrings
import com.example.model.TimelineTrackItem
import com.example.ui.components.tapOutsideToDismiss
import com.example.ui.theme.StudioAmber
import com.example.ui.theme.StudioBorder
import com.example.ui.theme.StudioCard
import com.example.ui.theme.StudioEmerald
import com.example.ui.theme.StudioPrimary
import com.example.ui.theme.StudioSecondary

@Composable
fun TimelineEditorScreen(
    tracks: List<TimelineTrackItem>,
    currentTimeSec: Float,
    totalDurationSec: Float,
    isPlaying: Boolean,
    onTogglePlay: () -> Unit,
    onSeek: (Float) -> Unit,
    currentLanguage: AppLanguage,
    onExportTimeline: () -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    val formatTime: (Float) -> String = { sec ->
        val m = (sec / 60).toInt()
        val s = (sec % 60).toInt()
        val ms = ((sec - sec.toInt()) * 10).toInt()
        "${if (m < 10) "0$m" else "$m"}:${if (s < 10) "0$s" else "$s"}.$ms"
    }

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
                    text = LocalizedStrings.getString("timeline_editor", currentLanguage),
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Black),
                    color = Color.White
                )
                Text(
                    text = "Multi-Track Timeline Composer • Up to 30 Minutes",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF94A3B8)
                )
            }

            Surface(
                color = StudioAmber.copy(alpha = 0.2f),
                border = androidx.compose.foundation.BorderStroke(1.dp, StudioAmber),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "MULTI-TRACK",
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                    color = StudioAmber
                )
            }
        }

        // Live Composer Viewport
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, StudioBorder, RoundedCornerShape(16.dp)),
            colors = CardDefaults.cardColors(containerColor = StudioCard),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
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
                            contentDescription = "Viewport",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }

                    // Simulated Kinetic Caption Overlay on Video
                    Surface(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(14.dp),
                        color = Color(0xDD000000),
                        shape = RoundedCornerShape(8.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, StudioAmber)
                    ) {
                        Text(
                            text = "✨ UNLIMITED 4K VIDEO TIMELINE ✨",
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Black),
                            color = StudioAmber
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Playback controls row & Scrubber
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(CircleShape)
                                .clickable { onTogglePlay() }
                                .testTag("timeline_play_pause_button"),
                            color = StudioAmber
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                    contentDescription = if (isPlaying) "Pause" else "Play",
                                    tint = Color.Black,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column {
                            Text(
                                text = "${formatTime(currentTimeSec)} / ${formatTime(totalDurationSec)}",
                                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                                color = Color.White
                            )
                            Text(
                                text = "30-min buffer active",
                                style = MaterialTheme.typography.labelSmall,
                                color = StudioSecondary
                            )
                        }
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        IconButton(onClick = { /* Split action */ }) {
                            Icon(
                                imageVector = Icons.Default.ContentCut,
                                contentDescription = "Split Clip",
                                tint = Color(0xFF94A3B8)
                            )
                        }
                        IconButton(onClick = { /* Audio action */ }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                                contentDescription = "Audio Level",
                                tint = Color(0xFF94A3B8)
                            )
                        }
                    }
                }

                // Global timeline slider
                Slider(
                    value = currentTimeSec,
                    onValueChange = onSeek,
                    valueRange = 0f..totalDurationSec,
                    colors = SliderDefaults.colors(
                        thumbColor = StudioAmber,
                        activeTrackColor = StudioAmber,
                        inactiveTrackColor = Color(0xFF334155)
                    ),
                    modifier = Modifier.testTag("timeline_scrubber_slider")
                )
            }
        }

        // Multi-Track Timeline Canvas
        Text(
            text = "Multi-Track Layers",
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
            color = Color.White
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, StudioBorder, RoundedCornerShape(16.dp)),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                tracks.forEach { track ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Track Type Icon & Label
                        Box(
                            modifier = Modifier
                                .size(30.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(track.colorHex).copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = when (track.trackType) {
                                    "VIDEO" -> Icons.Default.Movie
                                    "AUDIO" -> Icons.Default.GraphicEq
                                    else -> Icons.Default.Subtitles
                                },
                                contentDescription = track.trackType,
                                tint = Color(track.colorHex),
                                modifier = Modifier.size(16.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        // Visual Clip Block
                        Surface(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(8.dp))
                                .border(1.dp, Color(track.colorHex), RoundedCornerShape(8.dp)),
                            color = Color(track.colorHex).copy(alpha = 0.25f)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 10.dp, vertical = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = track.title,
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
                                    color = Color.White,
                                    maxLines = 1
                                )
                                Text(
                                    text = "${track.durationSec.toInt()}s",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color(track.colorHex)
                                )
                            }
                        }
                    }
                }
            }
        }

        // Export Render Button
        Button(
            onClick = onExportTimeline,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .testTag("export_timeline_button"),
            colors = ButtonDefaults.buttonColors(
                containerColor = StudioAmber,
                contentColor = Color.Black
            ),
            shape = RoundedCornerShape(14.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Download,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Export Master 1080p Video",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}
