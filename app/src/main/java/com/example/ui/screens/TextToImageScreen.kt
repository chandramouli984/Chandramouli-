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
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
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
import com.example.model.AspectRatio
import com.example.model.GeneratedMediaItem
import com.example.model.ImageStylePreset
import com.example.model.LocalizedStrings
import com.example.ui.components.tapOutsideToDismiss
import com.example.ui.theme.StudioAmber
import com.example.ui.theme.StudioBorder
import com.example.ui.theme.StudioCard
import com.example.ui.theme.StudioEmerald
import com.example.ui.theme.StudioPrimary
import com.example.ui.theme.StudioSecondary

@Composable
fun TextToImageScreen(
    prompt: String,
    onPromptChange: (String) -> Unit,
    selectedPreset: ImageStylePreset,
    onSelectPreset: (ImageStylePreset) -> Unit,
    selectedAspectRatio: AspectRatio,
    onSelectAspectRatio: (AspectRatio) -> Unit,
    onEnhancePrompt: () -> Unit,
    onGenerate: () -> Unit,
    isGenerating: Boolean,
    progress: Float,
    statusMessage: String,
    lastGeneratedItem: GeneratedMediaItem?,
    currentLanguage: AppLanguage,
    onSendToImageToVideo: () -> Unit,
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
        // Section Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = LocalizedStrings.getString("text_to_image", currentLanguage),
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Black),
                    color = Color.White
                )
                Text(
                    text = "High-Resolution 4K Neural Visual Generation",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF94A3B8)
                )
            }

            Surface(
                color = StudioPrimary.copy(alpha = 0.2f),
                border = androidx.compose.foundation.BorderStroke(1.dp, StudioPrimary),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "ZERO CREDITS",
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                    color = StudioPrimary
                )
            }
        }

        // Prompt Input Area
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
                        .testTag("text_to_image_prompt_input"),
                    placeholder = {
                        Text(
                            text = LocalizedStrings.getString("prompt_placeholder", currentLanguage),
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

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Surface(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .clickable {
                                focusManager.clearFocus()
                                onEnhancePrompt()
                            }
                            .border(1.dp, StudioSecondary, RoundedCornerShape(8.dp))
                            .testTag("ai_enhance_prompt_button"),
                        color = StudioSecondary.copy(alpha = 0.15f)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = null,
                                tint = StudioSecondary,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = LocalizedStrings.getString("enhance_prompt", currentLanguage),
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = StudioSecondary
                            )
                        }
                    }
                }
            }
        }

        // Style Preset Horizontal Scroll
        Text(
            text = LocalizedStrings.getString("style_preset", currentLanguage),
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
            color = Color.White
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ImageStylePreset.entries.forEach { preset ->
                val isSelected = preset == selectedPreset
                Surface(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .border(
                            1.dp,
                            if (isSelected) StudioPrimary else StudioBorder,
                            RoundedCornerShape(12.dp)
                        )
                        .clickable { onSelectPreset(preset) }
                        .testTag("preset_${preset.name.lowercase()}"),
                    color = if (isSelected) StudioPrimary.copy(alpha = 0.25f) else StudioCard
                ) {
                    Text(
                        text = preset.label,
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        ),
                        color = if (isSelected) Color.White else Color(0xFF94A3B8),
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                    )
                }
            }
        }

        // Aspect Ratio Selector
        Text(
            text = LocalizedStrings.getString("aspect_ratio", currentLanguage),
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
            color = Color.White
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AspectRatio.entries.forEach { ratio ->
                val isSelected = ratio == selectedAspectRatio
                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(10.dp))
                        .border(
                            1.dp,
                            if (isSelected) StudioSecondary else StudioBorder,
                            RoundedCornerShape(10.dp)
                        )
                        .clickable { onSelectAspectRatio(ratio) }
                        .testTag("ratio_${ratio.name.lowercase()}"),
                    color = if (isSelected) StudioSecondary.copy(alpha = 0.2f) else StudioCard
                ) {
                    Text(
                        text = ratio.label.substringBefore(" "),
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        ),
                        color = if (isSelected) StudioSecondary else Color(0xFF94A3B8),
                        modifier = Modifier.padding(vertical = 10.dp),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }
        }

        // Generate Action Button & Progress
        Button(
            onClick = {
                focusManager.clearFocus()
                onGenerate()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .testTag("generate_image_button"),
            colors = ButtonDefaults.buttonColors(
                containerColor = StudioPrimary,
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
                    text = LocalizedStrings.getString("generating", currentLanguage),
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
                        text = LocalizedStrings.getString("generate", currentLanguage),
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                    )
                }
            }
        }

        // Active Generation Progress Indicator
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
                    color = StudioPrimary,
                    trackColor = Color(0xFF1E293B)
                )
                Text(
                    text = statusMessage,
                    style = MaterialTheme.typography.labelSmall,
                    color = StudioSecondary
                )
            }
        }

        // Generated Visual Result Showcase
        lastGeneratedItem?.let { item ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.5.dp, StudioPrimary, RoundedCornerShape(16.dp))
                    .testTag("generated_image_preview_card"),
                colors = CardDefaults.cardColors(containerColor = StudioCard),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(240.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFF0F172A)),
                        contentAlignment = Alignment.Center
                    ) {
                        val imgResId = item.drawableResName?.let {
                            context.resources.getIdentifier(it, "drawable", context.packageName)
                        } ?: 0
                        if (imgResId != 0) {
                            Image(
                                painter = painterResource(id = imgResId),
                                contentDescription = item.title,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Animate to Video Quick Handoff Button
                        Button(
                            onClick = onSendToImageToVideo,
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(containerColor = StudioEmerald),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Movie,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp),
                                tint = Color.Black
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Animate to Video",
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                color = Color.Black
                            )
                        }

                        // Download / Save
                        Surface(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .border(1.dp, StudioBorder, RoundedCornerShape(10.dp))
                                .clickable { /* download action */ },
                            color = Color(0xFF0F172A)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.Download,
                                    contentDescription = "Download",
                                    tint = Color.White,
                                    modifier = Modifier.size(20.dp)
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
