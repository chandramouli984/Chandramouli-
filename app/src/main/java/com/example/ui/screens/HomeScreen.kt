package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.SlowMotionVideo
import androidx.compose.material.icons.filled.VideoCameraFront
import androidx.compose.material.icons.filled.ViewTimeline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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
import com.example.model.MediaType
import com.example.model.StudioModule
import com.example.ui.components.tapOutsideToDismiss
import com.example.ui.theme.StudioAmber
import com.example.ui.theme.StudioBorder
import com.example.ui.theme.StudioCard
import com.example.ui.theme.StudioEmerald
import com.example.ui.theme.StudioPrimary
import com.example.ui.theme.StudioPrimaryGlow
import com.example.ui.theme.StudioSecondary
import com.example.ui.theme.StudioTertiary

data class ModuleTileItem(
    val module: StudioModule,
    val titleKey: String,
    val subtitle: String,
    val icon: ImageVector,
    val accentColor: Color
)

@Composable
fun HomeScreen(
    currentLanguage: AppLanguage,
    recentCreations: List<GeneratedMediaItem>,
    onNavigateToModule: (StudioModule) -> Unit,
    onShareItem: (GeneratedMediaItem) -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    val moduleTiles = listOf(
        ModuleTileItem(
            module = StudioModule.TEXT_TO_IMAGE,
            titleKey = "text_to_image",
            subtitle = "4K Photorealistic Visuals",
            icon = Icons.Default.Image,
            accentColor = StudioPrimary
        ),
        ModuleTileItem(
            module = StudioModule.TEXT_TO_VIDEO,
            titleKey = "text_to_video",
            subtitle = "No Limit: 5s to 30 mins",
            icon = Icons.Default.Movie,
            accentColor = StudioSecondary
        ),
        ModuleTileItem(
            module = StudioModule.IMAGE_TO_VIDEO,
            titleKey = "image_to_video",
            subtitle = "Camera Parallax & 3D Orbit",
            icon = Icons.Default.SlowMotionVideo,
            accentColor = StudioEmerald
        ),
        ModuleTileItem(
            module = StudioModule.TIMELINE_EDITOR,
            titleKey = "timeline_editor",
            subtitle = "Multi-Track 30-min Studio",
            icon = Icons.Default.ViewTimeline,
            accentColor = StudioAmber
        ),
        ModuleTileItem(
            module = StudioModule.PRODUCT_PRESENTER,
            titleKey = "product_studio",
            subtitle = "Dual-Image Commercial Ads",
            icon = Icons.Default.VideoCameraFront,
            accentColor = StudioTertiary
        )
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .tapOutsideToDismiss(focusManager)
            .imePadding()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(bottom = 90.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Hero Studio Banner
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .clickable { onNavigateToModule(StudioModule.TEXT_TO_VIDEO) }
                    .border(1.dp, StudioBorder, RoundedCornerShape(20.dp))
                    .testTag("hero_studio_banner_card"),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = StudioCard)
            ) {
                Box(modifier = Modifier.fillMaxWidth().height(160.dp)) {
                    val heroResId = context.resources.getIdentifier(
                        "hero_studio_banner", "drawable", context.packageName
                    )
                    if (heroResId != 0) {
                        Image(
                            painter = painterResource(id = heroResId),
                            contentDescription = "Studio Hero",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }

                    // Gradient overlay
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    listOf(Color.Transparent, Color(0xDD0B0F19))
                                )
                            )
                    )

                    // Text & Action
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        Surface(
                            color = StudioPrimary,
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = "ZERO TIME LIMIT CONSTRAINTS",
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = Color.White
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Next-Gen Multi-Modal AI Production",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Black),
                            color = Color.White
                        )
                        Text(
                            text = "Generate 4K Visuals, 30-Min Videos, Commercial Presenter Ads & Viral Content",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFFCBD5E1),
                            maxLines = 1
                        )
                    }
                }
            }
        }

        // PRIMARY DASHBOARD CARD: "Gen Z Studio" Hub Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .border(
                        1.5.dp,
                        Brush.horizontalGradient(listOf(StudioTertiary, StudioSecondary, StudioAmber)),
                        RoundedCornerShape(20.dp)
                    )
                    .clickable { onNavigateToModule(StudioModule.GENZ_STUDIO) }
                    .testTag("genz_studio_hub_card"),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = StudioCard)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                ) {
                    val genzResId = context.resources.getIdentifier(
                        "genz_viral_card", "drawable", context.packageName
                    )
                    if (genzResId != 0) {
                        Image(
                            painter = painterResource(id = genzResId),
                            contentDescription = "Gen Z Studio Banner",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }

                    // Scrim
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    listOf(Color(0x99111827), Color(0xFA0B0F19))
                                )
                            )
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Surface(
                                    color = StudioTertiary,
                                    shape = RoundedCornerShape(6.dp)
                                ) {
                                    Text(
                                        text = "⚡ VIRAL SUITE",
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Black),
                                        color = Color.White
                                    )
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "5 Specialized Sub-Tools",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = StudioAmber
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = LocalizedStrings.getString("genz_studio", currentLanguage),
                                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Black),
                                color = Color.White
                            )
                            Text(
                                text = "Talking Avatars • Kinetic Captions • Memes • Lo-Fi • 9:16 Export",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFFE2E8F0)
                            )
                        }

                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(CircleShape)
                                .background(StudioTertiary),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = "Open Gen Z Studio",
                                tint = Color.White,
                                modifier = Modifier.size(26.dp)
                            )
                        }
                    }
                }
            }
        }

        // Studio Modules Section Header
        item {
            Text(
                text = "Core Production Modules",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = Color.White
            )
        }

        // Grid of Module Tiles (2 per row or full rows)
        items(moduleTiles.chunked(2)) { pair ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                pair.forEach { tile ->
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(16.dp))
                            .border(1.dp, StudioBorder, RoundedCornerShape(16.dp))
                            .clickable { onNavigateToModule(tile.module) }
                            .testTag("tile_${tile.module.name.lowercase()}"),
                        colors = CardDefaults.cardColors(containerColor = StudioCard),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(14.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(tile.accentColor.copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = tile.icon,
                                    contentDescription = null,
                                    tint = tile.accentColor,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Text(
                                text = LocalizedStrings.getString(tile.titleKey, currentLanguage),
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                color = Color.White
                            )
                            Text(
                                text = tile.subtitle,
                                style = MaterialTheme.typography.labelSmall,
                                color = Color(0xFF94A3B8),
                                maxLines = 1
                            )
                        }
                    }
                }
                if (pair.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }

        // Recent Creations Header
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = LocalizedStrings.getString("recent_creations", currentLanguage),
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = Color.White
                )
                Text(
                    text = "${recentCreations.size} items",
                    style = MaterialTheme.typography.labelSmall,
                    color = StudioSecondary
                )
            }
        }

        // Recent Creations List
        items(recentCreations) { item ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .border(1.dp, StudioBorder, RoundedCornerShape(14.dp)),
                colors = CardDefaults.cardColors(containerColor = StudioCard),
                shape = RoundedCornerShape(14.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Thumbnail
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0xFF0F172A)),
                        contentAlignment = Alignment.Center
                    ) {
                        val thumbId = item.drawableResName?.let {
                            context.resources.getIdentifier(it, "drawable", context.packageName)
                        } ?: 0

                        if (thumbId != 0) {
                            Image(
                                painter = painterResource(id = thumbId),
                                contentDescription = item.title,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Icon(
                                imageVector = when (item.type) {
                                    MediaType.VIDEO -> Icons.Default.Movie
                                    MediaType.AUDIO -> Icons.Default.GraphicEq
                                    MediaType.COMMERCIAL_AD -> Icons.Default.VideoCameraFront
                                    MediaType.IMAGE -> Icons.Default.Image
                                },
                                contentDescription = null,
                                tint = StudioSecondary,
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        if (item.type == MediaType.VIDEO || item.type == MediaType.COMMERCIAL_AD) {
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(CircleShape)
                                    .background(Color(0x99000000)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.PlayArrow,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = item.title,
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = Color.White,
                            maxLines = 1
                        )
                        Text(
                            text = item.prompt,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF94A3B8),
                            maxLines = 1
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            item.tags.forEach { tag ->
                                Surface(
                                    color = Color(0xFF0F172A),
                                    shape = RoundedCornerShape(4.dp)
                                ) {
                                    Text(
                                        text = tag,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = StudioEmerald,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }
                            item.durationFormatted?.let { dur ->
                                Surface(
                                    color = Color(0xFF0F172A),
                                    shape = RoundedCornerShape(4.dp)
                                ) {
                                    Text(
                                        text = dur,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = StudioAmber,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }
                        }
                    }

                    IconButton(onClick = { onShareItem(item) }) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Share",
                            tint = Color(0xFF94A3B8)
                        )
                    }
                }
            }
        }
    }
}
