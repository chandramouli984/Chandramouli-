package com.example.ui.screens

import android.content.Intent
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
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Subtitles
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AppLanguage
import com.example.model.GenZSubTool
import com.example.model.LocalizedStrings
import com.example.ui.components.tapOutsideToDismiss
import com.example.ui.theme.StudioAmber
import com.example.ui.theme.StudioBorder
import com.example.ui.theme.StudioCard
import com.example.ui.theme.StudioEmerald
import com.example.ui.theme.StudioPrimary
import com.example.ui.theme.StudioSecondary
import com.example.ui.theme.StudioTertiary

@Composable
fun GenZStudioScreen(
    currentSubTool: GenZSubTool,
    onSelectSubTool: (GenZSubTool) -> Unit,
    memeTopText: String,
    memeBottomText: String,
    onUpdateMemeText: (String, String) -> Unit,
    selectedAudioFilter: String,
    onSelectAudioFilter: (String) -> Unit,
    isAudioPlaying: Boolean,
    onToggleAudioPlayback: () -> Unit,
    selectedCaptionStyle: String,
    onSelectCaptionStyle: (String) -> Unit,
    currentLanguage: AppLanguage,
    onTriggerSocialExport: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .tapOutsideToDismiss(focusManager)
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Gen Z Header Banner
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    1.5.dp,
                    Brush.horizontalGradient(listOf(StudioTertiary, StudioSecondary, StudioAmber)),
                    RoundedCornerShape(16.dp)
                ),
            colors = CardDefaults.cardColors(containerColor = StudioCard),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "GEN Z VIRAL HUB",
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Black),
                        color = Color.White
                    )
                    Surface(
                        color = StudioTertiary,
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = "5 VIRAL ENGINES",
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Black),
                            color = Color.White
                        )
                    }
                }
                Text(
                    text = "Craft viral 9:16 vertical content, talking avatars, dynamic captions & memes",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF94A3B8)
                )
            }
        }

        // Sub-tool switcher pills
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            GenZSubTool.entries.forEach { tool ->
                val isSelected = tool == currentSubTool
                Surface(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .border(
                            1.dp,
                            if (isSelected) StudioTertiary else StudioBorder,
                            RoundedCornerShape(12.dp)
                        )
                        .clickable { onSelectSubTool(tool) }
                        .testTag("genz_tab_${tool.name.lowercase()}"),
                    color = if (isSelected) StudioTertiary.copy(alpha = 0.25f) else StudioCard
                ) {
                    Text(
                        text = LocalizedStrings.getString(tool.titleKey, currentLanguage),
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        ),
                        color = if (isSelected) Color.White else Color(0xFF94A3B8),
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                    )
                }
            }
        }

        // SUB-TOOL 1: AI TALKING AVATARS
        if (currentSubTool == GenZSubTool.TALKING_AVATARS) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, StudioBorder, RoundedCornerShape(16.dp)),
                colors = CardDefaults.cardColors(containerColor = StudioCard),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Neural Talking Avatar Lip-Sync",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = Color.White
                    )

                    // Avatar Portrait Canvas
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.Black),
                        contentAlignment = Alignment.Center
                    ) {
                        val presenterId = context.resources.getIdentifier(
                            "presenter_model", "drawable", context.packageName
                        )
                        if (presenterId != 0) {
                            Image(
                                painter = painterResource(id = presenterId),
                                contentDescription = "Avatar",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }

                        // Simulated Speaking Soundwave Overlay
                        Surface(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(10.dp),
                            color = Color(0xCC000000),
                            shape = RoundedCornerShape(20.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, StudioEmerald)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.GraphicEq,
                                    contentDescription = null,
                                    tint = StudioEmerald,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Lip-Sync: ${currentLanguage.voiceName}",
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                    color = Color.White
                                )
                            }
                        }
                    }

                    // Script for Avatar
                    Text(
                        text = "Avatar Monologue Script (${currentLanguage.nativeName})",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = Color.White
                    )

                    Surface(
                        color = Color(0xFF0F172A),
                        shape = RoundedCornerShape(10.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, StudioBorder)
                    ) {
                        Text(
                            text = "\"Hey creators! This entire 9:16 vertical video was rendered with zero credit constraints in seconds!\"",
                            modifier = Modifier.padding(12.dp),
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFFCBD5E1)
                        )
                    }

                    Button(
                        onClick = { /* trigger avatar render */ },
                        modifier = Modifier.fillMaxWidth().height(48.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = StudioTertiary)
                    ) {
                        Text("Synthesize Avatar Video", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // SUB-TOOL 2: DYNAMIC KINETIC CAPTIONS
        if (currentSubTool == GenZSubTool.KINETIC_CAPTIONS) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, StudioBorder, RoundedCornerShape(16.dp)),
                colors = CardDefaults.cardColors(containerColor = StudioCard),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Dynamic Kinetic Word-by-Word Captions",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = Color.White
                    )

                    // 9:16 Video Preview Box
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(240.dp)
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
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }

                        // Glowing kinetic caption box
                        Surface(
                            color = Color(0xEE000000),
                            border = androidx.compose.foundation.BorderStroke(2.dp, StudioAmber),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = "🔥 THIS IS INSANE 🔥",
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                                style = MaterialTheme.typography.headlineSmall.copy(
                                    fontWeight = FontWeight.Black,
                                    letterSpacing = 1.sp
                                ),
                                color = StudioAmber
                            )
                        }
                    }

                    Text(
                        text = "Select Caption Visual Style",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = Color.White
                    )

                    val captionStyles = listOf(
                        "Viral Yellow Pop (MrBeast Style)",
                        "Neon Cyberpunk Glow",
                        "Clean Minimalist White",
                        "Bold Red Alert"
                    )

                    captionStyles.forEach { style ->
                        val isSelected = style == selectedCaptionStyle
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .border(
                                    1.dp,
                                    if (isSelected) StudioAmber else StudioBorder,
                                    RoundedCornerShape(10.dp)
                                )
                                .clickable { onSelectCaptionStyle(style) },
                            color = if (isSelected) StudioAmber.copy(alpha = 0.15f) else Color(0xFF0F172A)
                        ) {
                            Text(
                                text = style,
                                modifier = Modifier.padding(12.dp),
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                ),
                                color = if (isSelected) StudioAmber else Color(0xFFCBD5E1)
                            )
                        }
                    }
                }
            }
        }

        // SUB-TOOL 3: VIRAL MEME GENERATOR
        if (currentSubTool == GenZSubTool.VIRAL_MEMES) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, StudioBorder, RoundedCornerShape(16.dp)),
                colors = CardDefaults.cardColors(containerColor = StudioCard),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "AI Viral Meme Generator",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = Color.White
                    )

                    // Interactive Meme Canvas
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(240.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.Black),
                        contentAlignment = Alignment.Center
                    ) {
                        val genzId = context.resources.getIdentifier(
                            "genz_viral_card", "drawable", context.packageName
                        )
                        if (genzId != 0) {
                            Image(
                                painter = painterResource(id = genzId),
                                contentDescription = "Meme canvas",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }

                        // Top Meme Text
                        Text(
                            text = memeTopText.uppercase(),
                            modifier = Modifier
                                .align(Alignment.TopCenter)
                                .padding(12.dp)
                                .background(Color(0xCC000000), RoundedCornerShape(6.dp))
                                .padding(horizontal = 8.dp, vertical = 4.dp),
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Black),
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )

                        // Bottom Meme Text
                        Text(
                            text = memeBottomText.uppercase(),
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(12.dp)
                                .background(Color(0xCC000000), RoundedCornerShape(6.dp))
                                .padding(horizontal = 8.dp, vertical = 4.dp),
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Black),
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                    }

                    // Meme Text Inputs
                    OutlinedTextField(
                        value = memeTopText,
                        onValueChange = { onUpdateMemeText(it, memeBottomText) },
                        label = { Text("Top Caption", color = Color(0xFF94A3B8)) },
                        modifier = Modifier.fillMaxWidth().testTag("meme_top_text_input"),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = StudioTertiary,
                            unfocusedBorderColor = StudioBorder
                        ),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = memeBottomText,
                        onValueChange = { onUpdateMemeText(memeTopText, it) },
                        label = { Text("Bottom Caption", color = Color(0xFF94A3B8)) },
                        modifier = Modifier.fillMaxWidth().testTag("meme_bottom_text_input"),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = StudioTertiary,
                            unfocusedBorderColor = StudioBorder
                        ),
                        singleLine = true
                    )
                }
            }
        }

        // SUB-TOOL 4: LO-FI & AUDIO FILTERS
        if (currentSubTool == GenZSubTool.AUDIO_FILTERS) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, StudioBorder, RoundedCornerShape(16.dp)),
                colors = CardDefaults.cardColors(containerColor = StudioCard),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Lo-Fi & Trending Audio Filters",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = Color.White
                    )

                    // Waveform visualizer box
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFF0F172A))
                            .border(1.dp, StudioBorder, RoundedCornerShape(12.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            listOf(20, 45, 75, 30, 90, 60, 40, 85, 55, 35, 70, 95, 40, 60).forEach { h ->
                                Box(
                                    modifier = Modifier
                                        .width(6.dp)
                                        .height(if (isAudioPlaying) h.dp else 12.dp)
                                        .clip(RoundedCornerShape(3.dp))
                                        .background(
                                            Brush.verticalGradient(listOf(StudioSecondary, StudioTertiary))
                                        )
                                )
                            }
                        }

                        // Play/Pause button
                        Surface(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(CircleShape)
                                .clickable { onToggleAudioPlayback() }
                                .testTag("audio_filter_play_button"),
                            color = Color(0xDD000000)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = if (isAudioPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                    contentDescription = null,
                                    tint = StudioSecondary
                                )
                            }
                        }
                    }

                    val filters = listOf(
                        "Lo-Fi Acoustic Chill" to "Vinyl crackle + warm vintage tape saturation",
                        "Anime Kawaii Pitch" to "Speed up +15% with harmonic formant boost",
                        "Cinematic Cathedral Reverb" to "Massive atmospheric depth and low-end bass",
                        "Robot 8-Bit Vocoder" to "Retro synth vocoder with dynamic frequency modulation"
                    )

                    filters.forEach { (name, desc) ->
                        val isSelected = name == selectedAudioFilter
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .border(
                                    1.dp,
                                    if (isSelected) StudioSecondary else StudioBorder,
                                    RoundedCornerShape(10.dp)
                                )
                                .clickable { onSelectAudioFilter(name) },
                            color = if (isSelected) StudioSecondary.copy(alpha = 0.15f) else Color(0xFF0F172A)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(
                                    text = name,
                                    style = MaterialTheme.typography.titleSmall.copy(
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                                    ),
                                    color = if (isSelected) StudioSecondary else Color.White
                                )
                                Text(
                                    text = desc,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color(0xFF94A3B8)
                                )
                            }
                        }
                    }
                }
            }
        }

        // SUB-TOOL 5: ONE-TAP 9:16 SOCIAL EXPORT PRESETS
        if (currentSubTool == GenZSubTool.SOCIAL_EXPORT) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, StudioBorder, RoundedCornerShape(16.dp)),
                colors = CardDefaults.cardColors(containerColor = StudioCard),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "One-Tap 9:16 Social Export Presets",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = Color.White
                    )
                    Text(
                        text = "Export ultra-crisp 1080x1920 MP4 files formatted for social algorithms",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF94A3B8)
                    )

                    val presets = listOf(
                        "Instagram Reels Preset" to "1080x1920 • 60fps • Variable Bitrate 16Mbps",
                        "YouTube Shorts Preset" to "1080x1920 • Rec.709 Color Space • AAC 320kbps",
                        "TikTok Viral Preset" to "1080x1920 • H.264 Fast Start • Smart Audio Loudness (-14 LUFS)"
                    )

                    presets.forEach { (title, specs) ->
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .border(1.dp, StudioBorder, RoundedCornerShape(12.dp))
                                .clickable { onTriggerSocialExport(title) }
                                .testTag("export_preset_${title.take(10).replace(" ", "_").lowercase()}"),
                            color = Color(0xFF0F172A)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(14.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = title,
                                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                        color = Color.White
                                    )
                                    Text(
                                        text = specs,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = StudioEmerald
                                    )
                                }

                                Surface(
                                    color = StudioTertiary,
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Share,
                                            contentDescription = null,
                                            tint = Color.White,
                                            modifier = Modifier.size(14.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = "Export",
                                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                            color = Color.White
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}
