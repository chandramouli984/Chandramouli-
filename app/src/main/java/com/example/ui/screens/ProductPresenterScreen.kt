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
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.VideoCameraFront
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.ui.theme.StudioTertiary

@Composable
fun ProductPresenterScreen(
    presenterPhotoName: String,
    productPhotoName: String,
    adScript: String,
    onAdScriptChange: (String) -> Unit,
    isAutoDuration: Boolean,
    onToggleAutoDuration: (Boolean) -> Unit,
    calculatedAutoDuration: Int,
    manualDurationSeconds: Int,
    onManualDurationChange: (Int) -> Unit,
    selectedVoiceoverLanguage: AppLanguage,
    onSelectVoiceoverLanguage: (AppLanguage) -> Unit,
    onAutoGenerateScript: () -> Unit,
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

    val effectiveDuration = if (isAutoDuration) calculatedAutoDuration else manualDurationSeconds

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
                    text = LocalizedStrings.getString("product_studio", currentLanguage),
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Black),
                    color = Color.White
                )
                Text(
                    text = "Dual-Image Commercial Ad Generator with Neural Lip-Sync",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF94A3B8)
                )
            }

            Surface(
                color = StudioTertiary.copy(alpha = 0.2f),
                border = androidx.compose.foundation.BorderStroke(1.dp, StudioTertiary),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "SADTALKER AI",
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                    color = StudioTertiary
                )
            }
        }

        // Dual Visual Showcase: Presenter Photo + Product Visual
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Presenter Card
            Card(
                modifier = Modifier
                    .weight(1f)
                    .border(1.5.dp, StudioTertiary, RoundedCornerShape(14.dp)),
                colors = CardDefaults.cardColors(containerColor = StudioCard),
                shape = RoundedCornerShape(14.dp)
            ) {
                Column(
                    modifier = Modifier.padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "1. Presenter Model",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = StudioTertiary
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(130.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color.Black),
                        contentAlignment = Alignment.Center
                    ) {
                        val presenterId = context.resources.getIdentifier(
                            presenterPhotoName, "drawable", context.packageName
                        )
                        if (presenterId != 0) {
                            Image(
                                painter = painterResource(id = presenterId),
                                contentDescription = "Presenter Photo",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }

                        // Neural lip sync tracker badge
                        Surface(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(4.dp),
                            color = Color(0xCC000000),
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                text = "Lip-Sync Ready",
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall,
                                color = StudioEmerald
                            )
                        }
                    }
                }
            }

            // Product Visual Card
            Card(
                modifier = Modifier
                    .weight(1f)
                    .border(1.5.dp, StudioSecondary, RoundedCornerShape(14.dp)),
                colors = CardDefaults.cardColors(containerColor = StudioCard),
                shape = RoundedCornerShape(14.dp)
            ) {
                Column(
                    modifier = Modifier.padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "2. Product Visual",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = StudioSecondary
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(130.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color.Black),
                        contentAlignment = Alignment.Center
                    ) {
                        val productId = context.resources.getIdentifier(
                            productPhotoName, "drawable", context.packageName
                        )
                        if (productId != 0) {
                            Image(
                                painter = painterResource(id = productId),
                                contentDescription = "Product Visual",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }

                        Surface(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(4.dp),
                            color = Color(0xCC000000),
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                text = "Spotlight Studio",
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall,
                                color = StudioSecondary
                            )
                        }
                    }
                }
            }
        }

        // Script Input & Auto-Scriptwriter
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, StudioBorder, RoundedCornerShape(16.dp)),
            colors = CardDefaults.cardColors(containerColor = StudioCard),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = LocalizedStrings.getString("script_label", currentLanguage),
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        color = Color.White
                    )

                    Surface(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .clickable { onAutoGenerateScript() }
                            .border(1.dp, StudioAmber, RoundedCornerShape(8.dp))
                            .testTag("ai_auto_scriptwriter_button"),
                        color = StudioAmber.copy(alpha = 0.15f)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = null,
                                tint = StudioAmber,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "AI Auto-Pitch",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = StudioAmber
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = adScript,
                    onValueChange = onAdScriptChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(110.dp)
                        .testTag("product_ad_script_input"),
                    placeholder = {
                        Text(
                            text = "Enter commercial ad copy, offer, and call to action...",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF64748B)
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        cursorColor = StudioTertiary,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
                )
            }
        }

        // Neural Voiceover Regional Language Selector
        Text(
            text = "Neural Voiceover Language",
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
            color = Color.White
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AppLanguage.entries.forEach { lang ->
                val isSelected = lang == selectedVoiceoverLanguage
                Surface(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .border(
                            1.dp,
                            if (isSelected) StudioTertiary else StudioBorder,
                            RoundedCornerShape(10.dp)
                        )
                        .clickable { onSelectVoiceoverLanguage(lang) }
                        .testTag("voice_lang_${lang.code}"),
                    color = if (isSelected) StudioTertiary.copy(alpha = 0.25f) else StudioCard
                ) {
                    Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)) {
                        Text(
                            text = lang.nativeName,
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            ),
                            color = if (isSelected) Color.White else Color(0xFF94A3B8)
                        )
                        Text(
                            text = lang.voiceName,
                            style = MaterialTheme.typography.labelSmall,
                            color = if (isSelected) StudioTertiary else Color(0xFF64748B)
                        )
                    }
                }
            }
        }

        // Flexible Video Duration Modes: Auto vs Manual
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
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Auto Duration Mode (Speech Pace)",
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                            color = Color.White
                        )
                        Text(
                            text = if (isAutoDuration) "Paced to ~140 wpm: ${calculatedAutoDuration}s video" else "Manual duration override active",
                            style = MaterialTheme.typography.labelSmall,
                            color = StudioSecondary
                        )
                    }

                    Switch(
                        checked = isAutoDuration,
                        onCheckedChange = onToggleAutoDuration,
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = StudioTertiary,
                            checkedTrackColor = StudioTertiary.copy(alpha = 0.3f),
                            uncheckedThumbColor = Color(0xFF94A3B8),
                            uncheckedTrackColor = Color(0xFF1E293B)
                        )
                    )
                }

                AnimatedVisibility(visible = !isAutoDuration) {
                    Column(modifier = Modifier.padding(top = 10.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Manual Duration: ${manualDurationSeconds}s",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = StudioAmber
                            )
                        }
                        Slider(
                            value = manualDurationSeconds.toFloat(),
                            onValueChange = { onManualDurationChange(it.toInt()) },
                            valueRange = 5f..120f,
                            colors = SliderDefaults.colors(
                                thumbColor = StudioAmber,
                                activeTrackColor = StudioAmber,
                                inactiveTrackColor = Color(0xFF334155)
                            )
                        )
                    }
                }
            }
        }

        // Synthesize Button
        Button(
            onClick = {
                focusManager.clearFocus()
                onGenerate()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .testTag("synthesize_commercial_ad_button"),
            colors = ButtonDefaults.buttonColors(
                containerColor = StudioTertiary,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(14.dp),
            enabled = !isGenerating
        ) {
            if (isGenerating) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = Color.White,
                    strokeWidth = 2.dp
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "Synthesizing Commercial Ad...",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                )
            } else {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Campaign,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Synthesize ${effectiveDuration}s Commercial Ad",
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
                    color = StudioTertiary,
                    trackColor = Color(0xFF1E293B)
                )
                Text(
                    text = statusMessage,
                    style = MaterialTheme.typography.labelSmall,
                    color = StudioTertiary
                )
            }
        }

        // Composite Commercial Ad Output Player
        lastGeneratedItem?.let { adItem ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.5.dp, StudioTertiary, RoundedCornerShape(16.dp))
                    .testTag("commercial_ad_preview_card"),
                colors = CardDefaults.cardColors(containerColor = StudioCard),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    // Composite Ad Canvas: Split/PIP view
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(240.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.Black),
                        contentAlignment = Alignment.Center
                    ) {
                        // Background Product Hero
                        val prodId = context.resources.getIdentifier(
                            "product_sample", "drawable", context.packageName
                        )
                        if (prodId != 0) {
                            Image(
                                painter = painterResource(id = prodId),
                                contentDescription = "Product",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }

                        // Picture-in-Picture Talking Presenter in top-left
                        val presenterId = context.resources.getIdentifier(
                            "presenter_model", "drawable", context.packageName
                        )
                        if (presenterId != 0) {
                            Box(
                                modifier = Modifier
                                    .align(Alignment.TopStart)
                                    .padding(12.dp)
                                    .size(76.dp)
                                    .clip(CircleShape)
                                    .border(2.dp, StudioTertiary, CircleShape)
                            ) {
                                Image(
                                    painter = painterResource(id = presenterId),
                                    contentDescription = "Talking Presenter",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                                // Speaking soundwave pulse
                                Surface(
                                    modifier = Modifier
                                        .align(Alignment.BottomCenter)
                                        .padding(2.dp),
                                    color = StudioEmerald,
                                    shape = RoundedCornerShape(4.dp)
                                ) {
                                    Text(
                                        text = "LIVE SPEECH",
                                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp),
                                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 7.sp, fontWeight = FontWeight.Black),
                                        color = Color.Black
                                    )
                                }
                            }
                        }

                        // Price & Deal Tag Overlay
                        Surface(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(12.dp),
                            color = StudioAmber,
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = "SPECIAL OFFER",
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Black),
                                color = Color.Black
                            )
                        }

                        // Bottom Headline
                        Surface(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .fillMaxWidth()
                                .background(Color(0xCC000000))
                                .padding(horizontal = 12.dp, vertical = 8.dp)
                        ) {
                            Column {
                                Text(
                                    text = "Wireless Noise Cancelling • 40H Battery",
                                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                    color = Color.White
                                )
                                Text(
                                    text = "Neural Voiceover: ${selectedVoiceoverLanguage.voiceName}",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = StudioSecondary
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Commercial Ad Ready (${adItem.durationFormatted})",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = Color.White
                        )

                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            IconButton(onClick = { onShare(adItem) }) {
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
