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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.HeadsetMic
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AppLanguage
import com.example.model.CustomerSupportConfig
import com.example.model.LocalizedStrings
import com.example.model.SubscriptionState
import com.example.model.UserProfile
import com.example.ui.theme.ProBadgeBackground
import com.example.ui.theme.ProBadgeBorder
import com.example.ui.theme.StudioAmber
import com.example.ui.theme.StudioBorder
import com.example.ui.theme.StudioCard
import com.example.ui.theme.StudioEmerald
import com.example.ui.theme.StudioError
import com.example.ui.theme.StudioPrimary
import com.example.ui.theme.StudioSecondary
import com.example.ui.theme.StudioTertiary
import com.example.ui.theme.TrialBadgeBackground
import com.example.ui.theme.TrialBadgeBorder

@Composable
fun TopHeaderBanner(
    subscription: SubscriptionState,
    currentLanguage: AppLanguage,
    userProfile: UserProfile,
    onOpenPaywall: () -> Unit,
    onLanguageSelected: (AppLanguage) -> Unit,
    onOpenReferral: () -> Unit = {},
    onOpenSupport: () -> Unit = {},
    onOpenAdminBank: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var showLanguageMenu by remember { mutableStateOf(false) }
    var showAccountDialog by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFF0F172A))
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // App Branding Title
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { /* no-op */ }
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(
                            Brush.linearGradient(
                                listOf(StudioPrimary, StudioSecondary)
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = "Studio Logo",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = "STUDIO PRO",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Black,
                            letterSpacing = 1.sp
                        ),
                        color = Color.White
                    )
                    Text(
                        text = "AI Media & Audio Suite",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFF94A3B8)
                    )
                }
            }

            // Controls: Language Picker & User Avatar
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Language Switcher Chip
                Box {
                    Surface(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .clickable { showLanguageMenu = true }
                            .border(1.dp, StudioBorder, RoundedCornerShape(20.dp))
                            .testTag("language_switcher_button"),
                        color = Color(0xFF1E293B)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Language,
                                contentDescription = "Language",
                                tint = StudioSecondary,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = currentLanguage.flagLabel,
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                color = Color.White
                            )
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = null,
                                tint = Color(0xFF94A3B8),
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }

                    DropdownMenu(
                        expanded = showLanguageMenu,
                        onDismissRequest = { showLanguageMenu = false },
                        modifier = Modifier
                            .background(StudioCard)
                            .border(1.dp, StudioBorder, RoundedCornerShape(8.dp))
                    ) {
                        AppLanguage.entries.forEach { lang ->
                            DropdownMenuItem(
                                text = {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column {
                                            Text(
                                                text = "${lang.nativeName} (${lang.displayName})",
                                                color = Color.White,
                                                fontWeight = if (lang == currentLanguage) FontWeight.Bold else FontWeight.Normal
                                            )
                                            Text(
                                                text = lang.voiceName,
                                                style = MaterialTheme.typography.labelSmall,
                                                color = Color(0xFF94A3B8)
                                            )
                                        }
                                        if (lang == currentLanguage) {
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Icon(
                                                imageVector = Icons.Default.Check,
                                                contentDescription = "Selected",
                                                tint = StudioSecondary,
                                                modifier = Modifier.size(18.dp)
                                            )
                                        }
                                    }
                                },
                                onClick = {
                                    onLanguageSelected(lang)
                                    showLanguageMenu = false
                                }
                            )
                        }
                    }
                }

                // User Google Avatar / Button
                Surface(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .border(1.5.dp, StudioPrimary, CircleShape)
                        .clickable { showAccountDialog = true }
                        .testTag("user_profile_avatar"),
                    color = Color(0xFF312E81)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = userProfile.avatarInitials,
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                            color = Color.White
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Prominent Status Banner
        val bannerBg = when {
            subscription.isSubscribed -> ProBadgeBackground
            subscription.isTrialActive -> TrialBadgeBackground
            else -> Color(0xFF450A0A)
        }
        val bannerBorder = when {
            subscription.isSubscribed -> ProBadgeBorder
            subscription.isTrialActive -> TrialBadgeBorder
            else -> StudioError
        }
        val bannerIcon = when {
            subscription.isSubscribed -> Icons.Default.Star
            subscription.isTrialActive -> Icons.Default.AutoAwesome
            else -> Icons.Default.Warning
        }
        val bannerText = when {
            subscription.isSubscribed -> String.format(
                LocalizedStrings.getString("pro_active", currentLanguage),
                subscription.renewalDate
            )
            subscription.isTrialActive -> String.format(
                LocalizedStrings.getString("trial_status", currentLanguage),
                subscription.trialDaysRemaining
            )
            else -> LocalizedStrings.getString("trial_expired", currentLanguage)
        }

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .border(1.dp, bannerBorder, RoundedCornerShape(12.dp))
                .clickable { onOpenPaywall() }
                .testTag("subscription_status_banner"),
            color = bannerBg
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = bannerIcon,
                        contentDescription = null,
                        tint = if (subscription.isSubscribed) StudioAmber else if (subscription.isTrialActive) StudioSecondary else StudioError,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = bannerText,
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        color = Color.White
                    )
                }

                Surface(
                    color = if (subscription.isSubscribed) StudioAmber else StudioPrimary,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = if (subscription.isSubscribed) "PRO ACTIVE" else "₹${subscription.monthlyPriceRupees}/mo",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.ExtraBold),
                        color = if (subscription.isSubscribed) Color.Black else Color.White
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Quick Access Pills (Share & Earn, Customer Support, Admin/Bank)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            // Share & Earn Pill
            Surface(
                modifier = Modifier
                    .clip(RoundedCornerShape(14.dp))
                    .border(1.dp, StudioEmerald.copy(alpha = 0.5f), RoundedCornerShape(14.dp))
                    .clickable { onOpenReferral() }
                    .testTag("quick_action_referral"),
                color = StudioEmerald.copy(alpha = 0.12f)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.CardGiftcard,
                        contentDescription = null,
                        tint = StudioEmerald,
                        modifier = Modifier.size(13.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Share & Get 10d Free",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = StudioEmerald
                    )
                }
            }

            // Customer Support Pill
            Surface(
                modifier = Modifier
                    .clip(RoundedCornerShape(14.dp))
                    .border(1.dp, StudioSecondary.copy(alpha = 0.5f), RoundedCornerShape(14.dp))
                    .clickable { onOpenSupport() }
                    .testTag("quick_action_support"),
                color = StudioSecondary.copy(alpha = 0.12f)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.HeadsetMic,
                        contentDescription = null,
                        tint = StudioSecondary,
                        modifier = Modifier.size(13.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Support: ${CustomerSupportConfig.PRIMARY_SUPPORT_EMAIL}",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = StudioSecondary
                    )
                }
            }

            // Owner Bank & Promo Admin Pill
            Surface(
                modifier = Modifier
                    .clip(RoundedCornerShape(14.dp))
                    .border(1.dp, StudioAmber.copy(alpha = 0.5f), RoundedCornerShape(14.dp))
                    .clickable { onOpenAdminBank() }
                    .testTag("quick_action_admin_bank"),
                color = StudioAmber.copy(alpha = 0.12f)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.AccountBalance,
                        contentDescription = null,
                        tint = StudioAmber,
                        modifier = Modifier.size(13.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Bank & Promos",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = StudioAmber
                    )
                }
            }
        }
    }

    // Google Account & Cloud Sync Dialog
    if (showAccountDialog) {
        AlertDialog(
            onDismissRequest = { showAccountDialog = false },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = StudioSecondary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = LocalizedStrings.getString("google_signed_in", currentLanguage),
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "User: ${userProfile.name}",
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "Email: ${userProfile.email}",
                        color = Color(0xFF94A3B8),
                        style = MaterialTheme.typography.bodySmall
                    )

                    Surface(
                        color = Color(0xFF0F172A),
                        shape = RoundedCornerShape(8.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, StudioBorder)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.CloudDone,
                                contentDescription = null,
                                tint = StudioEmerald,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = LocalizedStrings.getString("cloud_synced", currentLanguage),
                                style = MaterialTheme.typography.labelMedium,
                                color = StudioEmerald
                            )
                        }
                    }

                    // Account Quick Action Buttons
                    Button(
                        onClick = {
                            showAccountDialog = false
                            onOpenReferral()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = StudioEmerald),
                        modifier = Modifier.fillMaxWidth().height(42.dp),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(imageVector = Icons.Default.CardGiftcard, contentDescription = null, tint = Color.Black, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Share & Get 10 Days Free", color = Color.Black, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelMedium)
                    }

                    Button(
                        onClick = {
                            showAccountDialog = false
                            onOpenSupport()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = StudioSecondary),
                        modifier = Modifier.fillMaxWidth().height(42.dp),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(imageVector = Icons.Default.HeadsetMic, contentDescription = null, tint = Color.Black, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Contact Support (${CustomerSupportConfig.PRIMARY_SUPPORT_EMAIL})", color = Color.Black, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelSmall)
                    }

                    Button(
                        onClick = {
                            showAccountDialog = false
                            onOpenAdminBank()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = StudioAmber),
                        modifier = Modifier.fillMaxWidth().height(42.dp),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(imageVector = Icons.Default.AccountBalance, contentDescription = null, tint = Color.Black, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Owner Bank Payout & Promo Settings", color = Color.Black, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelSmall)
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showAccountDialog = false }) {
                    Text("Close", color = StudioSecondary)
                }
            },
            containerColor = StudioCard
        )
    }
}
