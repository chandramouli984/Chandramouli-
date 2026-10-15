package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SlowMotionVideo
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
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
import com.example.model.GeneratedMediaItem
import com.example.model.LocalizedStrings
import com.example.model.VideoMotionPreset
import com.example.ui.components.tapOutsideToDismiss
import com.example.ui.theme.StudioBorder
import com.example.ui.theme.StudioCard
import com.example.ui.theme.StudioEmerald
import com.example.ui.theme.StudioSecondary

@Composable
fun ImageToVideoScreen(
    sourceImageName: String,
    selectedMotionPreset: VideoMotionPreset,
    motionIntensity: Float,
    onSelectMotionPreset: (VideoMotionPreset) -> Unit,
    onMotionIntensityChange: (Float) -> Unit,
    onGenerate: () -> Unit,
    isGenerating: Boolean,
    progress: Float,
    statusMessage: String,
    lastGeneratedItem: GeneratedMediaItem?,
    currentLanguage: AppLanguage,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    val imageSources = listOf(
        "hero_studio_banner" to "Studio Cyber City",
        "presenter_model" to "Presenter Portrait",
        "product_sample" to "Headphones 3D",
        "genz_viral_card" to "Vaporwave Art"
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
                    text = LocalizedStrings.getString("image_to_video", currentLanguage),
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Black),
                    color = Color.White
                )
                Text(
                    text = "Animate Static Images with 3D Depth & Camera Parallax",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF94A3B8)
                )
            }

            Surface(
                color = StudioEmerald.copy(alpha = 0.2f),
                border = androidx.compose.foundation.BorderStroke(1.dp, StudioEmerald),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "NEURAL FLOW",
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                    color = StudioEmerald
                )
            }
        }

        // Active Source Image Display
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, StudioBorder, RoundedCornerShape(16.dp)),
            colors = CardDefaults.cardColors(containerColor = StudioCard),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text(
                    text = "Input Static Image",
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(10.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.Black),
                    contentAlignment = Alignment.Center
                ) {
                    val imgId = context.resources.getIdentifier(
                        sourceImageName, "drawable", context.packageName
                    )
                    if (imgId != 0) {
                        Image(
                            painter = painterResource(id = imgId),
                            contentDescription = "Source Visual",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }

        // Motion Preset Selection
        Text(
            text = "Camera Motion Preset",
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
            color = Color.White
        )

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            VideoMotionPreset.entries.forEach { preset ->
                val isSelected = preset == selectedMotionPreset
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .border(
                            1.dp,
                            if (isSelected) StudioEmerald else StudioBorder,
                            RoundedCornerShape(12.dp)
                        )
                        .clickable { onSelectMotionPreset(preset) }
                        .testTag("motion_preset_${preset.name.lowercase()}"),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isSelected) StudioEmerald.copy(alpha = 0.15f) else StudioCard
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.SlowMotionVideo,
                            contentDescription = null,
                            tint = if (isSelected) StudioEmerald else Color(0xFF94A3B8),
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = preset.label,
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                                ),
                                color = if (isSelected) Color.White else Color(0xFFCBD5E1)
                            )
                            Text(
                                text = preset.description,
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF94A3B8)
                            )
                        }
                    }
                }
            }
        }

        // Motion Intensity Slider
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, StudioBorder, RoundedCornerShape(14.dp)),
            colors = CardDefaults.cardColors(containerColor = StudioCard),
            shape = RoundedCornerShape(14.dp)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Motion Dynamic Intensity",
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        color = Color.White
                    )
                    Text(
                        text = "${String.format("%.1f", motionIntensity)}x",
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Black),
                        color = StudioEmerald
                    )
                }

                Slider(
                    value = motionIntensity,
                    onValueChange = onMotionIntensityChange,
                    valueRange = 1.0f..5.0f,
                    colors = SliderDefaults.colors(
                        thumbColor = StudioEmerald,
                        activeTrackColor = StudioEmerald,
                        inactiveTrackColor = Color(0xFF334155)
                    )
                )
            }
        }

        // Animate Button
        Button(
            onClick = {
                focusManager.clearFocus()
                onGenerate()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .testTag("animate_image_button"),
            colors = ButtonDefaults.buttonColors(
                containerColor = StudioEmerald,
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
                    text = "Generating Motion Flow...",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                )
            } else {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Animate to 60fps Video",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                    )
                }
            }
        }

        // Generation Progress Indicator
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
                    color = StudioEmerald,
                    trackColor = Color(0xFF1E293B)
                )
                Text(
                    text = statusMessage,
                    style = MaterialTheme.typography.labelSmall,
                    color = StudioEmerald
                )
            }
        }

        // Result Preview Card
        lastGeneratedItem?.let { video ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.5.dp, StudioEmerald, RoundedCornerShape(16.dp))
                    .testTag("animated_video_result_card"),
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
                                contentDescription = video.title,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }

                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                                .background(Color(0x99000000)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = null,
                                tint = StudioEmerald,
                                modifier = Modifier.size(30.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = video.title,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = Color.White
                    )
                    Text(
                        text = "1080p • 60 FPS • Seamless Loop",
                        style = MaterialTheme.typography.labelSmall,
                        color = StudioEmerald
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}
