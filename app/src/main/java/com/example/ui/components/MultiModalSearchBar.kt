package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AppLanguage
import com.example.model.AttachmentType
import com.example.model.LocalizedStrings
import com.example.model.SearchAttachmentState
import com.example.ui.theme.StudioAmber
import com.example.ui.theme.StudioBorder
import com.example.ui.theme.StudioCard
import com.example.ui.theme.StudioEmerald
import com.example.ui.theme.StudioPrimary
import com.example.ui.theme.StudioSecondary

@Composable
fun MultiModalSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    attachmentState: SearchAttachmentState,
    onAttachImage: () -> Unit,
    onAttachVideo: () -> Unit,
    onClearAttachment: () -> Unit,
    onSearchSubmit: (String) -> Unit,
    currentLanguage: AppLanguage,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current

    val suggestions = when (currentLanguage) {
        AppLanguage.TELUGU -> listOf("సైబర్‌పంక్ నగరం", "ప్రోడక్ట్ ప్రకటన", "లో-ఫై సంగీతం", "వైరల్ మీమ్")
        AppLanguage.TAMIL -> listOf("சைபர்பங்க் நகரம்", "தயாரிப்பு விளம்பரம்", "லோ-ஃபை இசை", "வைரல் மீம்")
        AppLanguage.MALAYALAM -> listOf("സൈബർപങ്ക് നഗരം", "പ്രൊഡക്റ്റ് പരസ്യം", "ലോ-ഫൈ സംഗീതം", "വൈറൽ മീം")
        AppLanguage.KANNADA -> listOf("ಸೈಬರ್‌ಪಂಕ್ ನಗರ", "ಉತ್ಪನ್ನ ಜಾಹೀರಾತು", "ಲೋ-ಫೈ ಆಡಿಯೋ", "ವೈರಲ್ ಮೀಮ್")
        AppLanguage.ENGLISH -> listOf("Cyberpunk City", "Presenter Ad 4K", "Lo-Fi Chill Beats", "Viral TikTok Meme")
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFF0F172A))
            .padding(horizontal = 16.dp, vertical = 6.dp)
    ) {
        // Main input bar container
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = StudioCard,
            border = androidx.compose.foundation.BorderStroke(1.dp, StudioBorder),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                // Active Attachment Pill if present
                AnimatedVisibility(visible = attachmentState.type != AttachmentType.NONE) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 6.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                if (attachmentState.type == AttachmentType.IMAGE_REFERENCE)
                                    StudioSecondary.copy(alpha = 0.15f)
                                else
                                    StudioAmber.copy(alpha = 0.15f)
                            )
                            .border(
                                1.dp,
                                if (attachmentState.type == AttachmentType.IMAGE_REFERENCE) StudioSecondary else StudioAmber,
                                RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = if (attachmentState.type == AttachmentType.IMAGE_REFERENCE) Icons.Default.Image else Icons.Default.Movie,
                                contentDescription = null,
                                tint = if (attachmentState.type == AttachmentType.IMAGE_REFERENCE) StudioSecondary else StudioAmber,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "${attachmentState.name} (${attachmentState.details})",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = Color.White
                            )
                        }
                        IconButton(
                            onClick = onClearAttachment,
                            modifier = Modifier.size(20.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Clear attachment",
                                tint = Color(0xFF94A3B8),
                                modifier = Modifier.size(14.dp)
                            )
                        }
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = StudioSecondary,
                        modifier = Modifier
                            .padding(start = 4.dp, end = 8.dp)
                            .size(20.dp)
                    )

                    OutlinedTextField(
                        value = query,
                        onValueChange = onQueryChange,
                        modifier = Modifier
                            .weight(1f)
                            .testTag("universal_search_input"),
                        placeholder = {
                            Text(
                                text = LocalizedStrings.getString("search_hint", currentLanguage),
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF64748B),
                                maxLines = 1
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent,
                            cursorColor = StudioSecondary,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        ),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                        keyboardActions = KeyboardActions(onSearch = {
                            focusManager.clearFocus()
                            onSearchSubmit(query)
                        })
                    )

                    // Multi-Modal Action Buttons
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        // Image Attachment Action Button
                        Surface(
                            modifier = Modifier
                                .size(34.dp)
                                .clip(CircleShape)
                                .clickable { onAttachImage() }
                                .testTag("search_attach_image_button"),
                            color = if (attachmentState.type == AttachmentType.IMAGE_REFERENCE) StudioSecondary else Color(0xFF334155)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.Image,
                                    contentDescription = "Attach reference image",
                                    tint = if (attachmentState.type == AttachmentType.IMAGE_REFERENCE) Color.Black else Color.White,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(6.dp))

                        // Video Attachment Action Button
                        Surface(
                            modifier = Modifier
                                .size(34.dp)
                                .clip(CircleShape)
                                .clickable { onAttachVideo() }
                                .testTag("search_attach_video_button"),
                            color = if (attachmentState.type == AttachmentType.VIDEO_REFERENCE) StudioAmber else Color(0xFF334155)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.Movie,
                                    contentDescription = "Attach reference video",
                                    tint = if (attachmentState.type == AttachmentType.VIDEO_REFERENCE) Color.Black else Color.White,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        // Quick Suggestion Chips
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 6.dp)
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            suggestions.forEach { tag ->
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFF1E293B),
                    border = androidx.compose.foundation.BorderStroke(0.8.dp, StudioBorder),
                    modifier = Modifier.clickable {
                        onQueryChange(tag)
                        onSearchSubmit(tag)
                        focusManager.clearFocus()
                    }
                ) {
                    Text(
                        text = "#$tag",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFF94A3B8),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }
    }
}
