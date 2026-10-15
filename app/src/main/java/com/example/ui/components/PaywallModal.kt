package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Discount
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.model.AppLanguage
import com.example.model.LocalizedStrings
import com.example.model.SubscriptionState
import com.example.ui.theme.StudioAmber
import com.example.ui.theme.StudioBorder
import com.example.ui.theme.StudioCard
import com.example.ui.theme.StudioEmerald
import com.example.ui.theme.StudioPrimary
import com.example.ui.theme.StudioSecondary
import com.example.ui.theme.StudioTertiary

@Composable
fun PaywallModal(
    subscription: SubscriptionState,
    currentLanguage: AppLanguage,
    onDismiss: () -> Unit,
    onSubscribeSuccess: () -> Unit,
    onApplyCode: (String) -> Boolean = { false },
    modifier: Modifier = Modifier
) {
    var selectedPaymentMethod by remember { mutableStateOf("UPI") }
    var isProcessing by remember { mutableStateOf(false) }
    var promoCodeInput by remember { mutableStateOf("") }

    val features = listOf(
        "Zero Credit Limits • Truly Unlimited Generations",
        "Up to 30-Minute 4K Text-to-Video Engine",
        "Commercial Product Presenter with Neural Lip-Sync",
        "Gen Z Studio: Talking Avatars, Kinetic Captions & Memes",
        "Multi-lingual Regional Neural Voices (Telugu, Tamil, Malayalam, Kannada)",
        "1080x1920 One-Tap Social Export (Reels, Shorts, TikTok)"
    )

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = modifier
                .fillMaxWidth(0.94f)
                .clip(RoundedCornerShape(24.dp))
                .border(1.dp, StudioAmber.copy(alpha = 0.5f), RoundedCornerShape(24.dp))
                .imePadding(),
            color = Color(0xFF0F172A)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Top close button & badge
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        color = StudioAmber.copy(alpha = 0.2f),
                        border = androidx.compose.foundation.BorderStroke(1.dp, StudioAmber),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = StudioAmber,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "OFFICIAL PRO PASS",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Black),
                                color = StudioAmber
                            )
                        }
                    }

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.testTag("paywall_close_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = Color(0xFF94A3B8)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Title & Subtitle
                Text(
                    text = LocalizedStrings.getString("paywall_title", currentLanguage),
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 0.5.sp
                    ),
                    color = Color.White,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = LocalizedStrings.getString("paywall_subtitle", currentLanguage),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF94A3B8),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(18.dp))

                // Price Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.5.dp, Brush.horizontalGradient(listOf(StudioAmber, StudioPrimary)), RoundedCornerShape(16.dp)),
                    colors = CardDefaults.cardColors(containerColor = StudioCard),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            verticalAlignment = Alignment.Bottom,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "₹${subscription.monthlyPriceRupees}",
                                style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Black),
                                color = StudioAmber
                            )
                            if (subscription.appliedPromoDiscountRupees > 0) {
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "₹${subscription.baseMonthlyPriceRupees}",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        textDecoration = androidx.compose.ui.text.style.TextDecoration.LineThrough
                                    ),
                                    color = Color(0xFF94A3B8),
                                    modifier = Modifier.padding(bottom = 6.dp)
                                )
                            }
                            Text(
                                text = " / month",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.White,
                                modifier = Modifier.padding(bottom = 6.dp)
                            )
                        }

                        Text(
                            text = "Unrestricted All-Inclusive Pass • Zero Credit Depletion",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
                            color = StudioEmerald
                        )

                        if (subscription.appliedPromoCode != null) {
                            Spacer(modifier = Modifier.height(6.dp))
                            Surface(
                                color = StudioEmerald.copy(alpha = 0.2f),
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Text(
                                    text = "Code '${subscription.appliedPromoCode}' Applied: ₹${subscription.appliedPromoDiscountRupees} OFF + Extra Free Days!",
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                    color = StudioEmerald
                                )
                            }
                        }

                        if (subscription.isTrialActive) {
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "Active Free Trial: ${subscription.trialDaysRemaining} days remaining",
                                style = MaterialTheme.typography.labelSmall,
                                color = StudioSecondary
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Promo / Referral Code Box
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, StudioBorder, RoundedCornerShape(12.dp)),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = promoCodeInput,
                            onValueChange = { promoCodeInput = it.uppercase() },
                            placeholder = { Text("Promo or Invite Code (e.g. SPECIAL50)", color = Color(0xFF64748B), style = MaterialTheme.typography.bodySmall) },
                            modifier = Modifier.weight(1f).testTag("paywall_promo_input"),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedBorderColor = Color.Transparent,
                                unfocusedBorderColor = Color.Transparent
                            ),
                            singleLine = true
                        )

                        Button(
                            onClick = {
                                if (onApplyCode(promoCodeInput)) {
                                    promoCodeInput = ""
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = StudioSecondary),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.testTag("paywall_apply_promo_button")
                        ) {
                            Text("Apply", color = Color.Black, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Feature Checklist
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    features.forEach { feat ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(20.dp)
                                    .clip(CircleShape)
                                    .background(StudioEmerald.copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    tint = StudioEmerald,
                                    modifier = Modifier.size(14.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = feat,
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFFE2E8F0)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Payment Options Selector
                Text(
                    text = "Select Direct Payment Gateway",
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                    color = Color.White,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val methods = listOf("UPI", "Play Store", "Card")
                    methods.forEach { method ->
                        val isSelected = selectedPaymentMethod == method
                        Surface(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(10.dp))
                                .border(
                                    1.dp,
                                    if (isSelected) StudioSecondary else StudioBorder,
                                    RoundedCornerShape(10.dp)
                                )
                                .clickable { selectedPaymentMethod = method }
                                .testTag("payment_method_$method"),
                            color = if (isSelected) StudioSecondary.copy(alpha = 0.15f) else Color(0xFF1E293B)
                        ) {
                            Text(
                                text = method,
                                modifier = Modifier.padding(vertical = 10.dp),
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                ),
                                color = if (isSelected) StudioSecondary else Color(0xFF94A3B8)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Action Subscribe Button
                Button(
                    onClick = {
                        isProcessing = true
                        onSubscribeSuccess()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("confirm_subscribe_button"),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = StudioAmber,
                        contentColor = Color.Black
                    ),
                    shape = RoundedCornerShape(12.dp),
                    enabled = !isProcessing
                ) {
                    if (isProcessing) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = Color.Black,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.FlashOn,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Activate Pro for ₹${subscription.monthlyPriceRupees}/mo",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Black)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.AccountBalance,
                        contentDescription = null,
                        tint = StudioEmerald,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Direct Merchant Bank Payout via UPI & Razorpay",
                        style = MaterialTheme.typography.labelSmall,
                        color = StudioEmerald
                    )
                }
            }
        }
    }
}
