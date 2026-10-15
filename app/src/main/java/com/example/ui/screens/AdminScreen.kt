package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Discount
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.ToggleOn
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.filled.WifiTethering
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.BankPayoutSettings
import com.example.model.PromoCodeItem
import com.example.ui.components.tapOutsideToDismiss
import com.example.ui.theme.StudioAmber
import com.example.ui.theme.StudioBorder
import com.example.ui.theme.StudioCard
import com.example.ui.theme.StudioCardElevated
import com.example.ui.theme.StudioEmerald
import com.example.ui.theme.StudioPrimary
import com.example.ui.theme.StudioSecondary
import com.example.ui.theme.StudioTertiary

@Composable
fun AdminScreen(
    bankSettings: BankPayoutSettings,
    promoCodes: List<PromoCodeItem>,
    onSaveBankSettings: (BankPayoutSettings) -> Unit,
    onGenerateAlphanumericCode: (String, Int) -> String,
    onCreatePromoCode: (String, Int, Int, String) -> Boolean,
    onTogglePromoCode: (String) -> Unit,
    onDeletePromoCode: (String) -> Unit,
    onVerifyPaymentConnection: () -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    var selectedSection by remember { mutableIntStateOf(0) } // 0: Payment & Gateway, 1: Promo Code Engine

    // UPI & Razorpay Credentials Form State
    var upiId by remember { mutableStateOf(bankSettings.merchantUpiId) }
    var accountHolderName by remember { mutableStateOf(bankSettings.accountHolderName) }
    var accountNumber by remember { mutableStateOf(bankSettings.accountNumber) }
    var ifscCode by remember { mutableStateOf(bankSettings.ifscCode) }
    var bankName by remember { mutableStateOf(bankSettings.bankName) }
    var razorpayKeyId by remember { mutableStateOf(bankSettings.razorpayKeyId) }
    var razorpayKeySecret by remember { mutableStateOf(bankSettings.razorpayKeySecret) }
    var webhookSecret by remember { mutableStateOf(bankSettings.webhookSecret) }
    var isLiveProduction by remember { mutableStateOf(bankSettings.isLiveProductionMode) }
    var isDirectSettlement by remember { mutableStateOf(bankSettings.directBankSettlementEnabled) }
    var isSecretVisible by remember { mutableStateOf(false) }

    // Alphanumeric Promo Code Generator Tool State
    var generatorPrefix by remember { mutableStateOf("STUDIO") }
    var generatorLength by remember { mutableIntStateOf(6) }
    var generatedPreviewCode by remember { mutableStateOf("STUDIO-7KM9X2") }

    // Create Promo Code Form State
    var promoCodeInput by remember { mutableStateOf("") }
    var promoDiscountInput by remember { mutableStateOf("50") }
    var promoExtraDaysInput by remember { mutableStateOf("10") }
    var promoDescInput by remember { mutableStateOf("Creator Launch Special") }
    var promoExpiryInput by remember { mutableStateOf("31 Dec 2026") }

    // Test Promo Code Simulator State
    var testCodeQuery by remember { mutableStateOf("") }
    var testSimulationResult by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .tapOutsideToDismiss(focusManager)
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Top Action Bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    onClick = onNavigateBack,
                    modifier = Modifier.testTag("admin_back_button")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
                Spacer(modifier = Modifier.width(6.dp))
                Column {
                    Text(
                        text = "OWNER ADMIN CONSOLE",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Black,
                            letterSpacing = 1.sp
                        ),
                        color = Color.White
                    )
                    Text(
                        text = "Payment Gateway Credentials & Alphanumeric Promo Tools",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFF94A3B8)
                    )
                }
            }

            Surface(
                color = StudioAmber.copy(alpha = 0.2f),
                border = androidx.compose.foundation.BorderStroke(1.dp, StudioAmber),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = if (isLiveProduction) "LIVE PRODUCTION" else "SANDBOX TEST",
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Black),
                    color = StudioAmber
                )
            }
        }

        // Executive Financial & Metrics Bar
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, StudioBorder, RoundedCornerShape(16.dp)),
            colors = CardDefaults.cardColors(containerColor = StudioCard),
            shape = RoundedCornerShape(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Settled Revenue",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFF94A3B8)
                    )
                    Text(
                        text = "₹${bankSettings.totalSettledRevenueRupees}",
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Black),
                        color = StudioEmerald
                    )
                    Text(
                        text = "Direct T+0 Settlement",
                        style = MaterialTheme.typography.labelSmall,
                        color = StudioSecondary
                    )
                }

                Box(
                    modifier = Modifier
                        .height(40.dp)
                        .width(1.dp)
                        .background(StudioBorder)
                )

                Column {
                    Text(
                        text = "Active Pro Users",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFF94A3B8)
                    )
                    Text(
                        text = "${bankSettings.activeSubscribedUsers}",
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Black),
                        color = Color.White
                    )
                    Text(
                        text = "@ ₹121/mo flat fee",
                        style = MaterialTheme.typography.labelSmall,
                        color = StudioAmber
                    )
                }

                Box(
                    modifier = Modifier
                        .height(40.dp)
                        .width(1.dp)
                        .background(StudioBorder)
                )

                Column {
                    Text(
                        text = "Promo Codes",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFF94A3B8)
                    )
                    Text(
                        text = "${promoCodes.size}",
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Black),
                        color = StudioTertiary
                    )
                    Text(
                        text = "${promoCodes.sumOf { it.currentUses }} Redeemed",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFF94A3B8)
                    )
                }
            }
        }

        // Section Tabs
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Surface(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(12.dp))
                    .border(
                        1.dp,
                        if (selectedSection == 0) StudioAmber else StudioBorder,
                        RoundedCornerShape(12.dp)
                    )
                    .clickable { selectedSection = 0 }
                    .testTag("admin_screen_tab_payments"),
                color = if (selectedSection == 0) StudioAmber.copy(alpha = 0.2f) else StudioCard
            ) {
                Row(
                    modifier = Modifier.padding(vertical = 12.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.AccountBalance,
                        contentDescription = null,
                        tint = if (selectedSection == 0) StudioAmber else Color(0xFF94A3B8),
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "UPI & Gateway Credentials",
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = if (selectedSection == 0) FontWeight.Bold else FontWeight.Medium
                        ),
                        color = if (selectedSection == 0) StudioAmber else Color(0xFFCBD5E1)
                    )
                }
            }

            Surface(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(12.dp))
                    .border(
                        1.dp,
                        if (selectedSection == 1) StudioTertiary else StudioBorder,
                        RoundedCornerShape(12.dp)
                    )
                    .clickable { selectedSection = 1 }
                    .testTag("admin_screen_tab_promos"),
                color = if (selectedSection == 1) StudioTertiary.copy(alpha = 0.2f) else StudioCard
            ) {
                Row(
                    modifier = Modifier.padding(vertical = 12.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Discount,
                        contentDescription = null,
                        tint = if (selectedSection == 1) StudioTertiary else Color(0xFF94A3B8),
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Promo Code Engine",
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = if (selectedSection == 1) FontWeight.Bold else FontWeight.Medium
                        ),
                        color = if (selectedSection == 1) StudioTertiary else Color(0xFFCBD5E1)
                    )
                }
            }
        }

        // ==========================================
        // SECTION 0: UPI & RAZORPAY PAYMENT CREDENTIALS
        // ==========================================
        if (selectedSection == 0) {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                // Direct Settlement & Mode Switches
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, StudioBorder, RoundedCornerShape(14.dp)),
                    colors = CardDefaults.cardColors(containerColor = StudioCard),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Direct Bank Settlement Active",
                                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                    color = Color.White
                                )
                                Text(
                                    text = "All ₹121/mo subscriptions deposited directly into owner bank account",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = StudioEmerald
                                )
                            }
                            Switch(
                                checked = isDirectSettlement,
                                onCheckedChange = { isDirectSettlement = it },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = StudioEmerald,
                                    checkedTrackColor = StudioEmerald.copy(alpha = 0.3f)
                                )
                            )
                        }

                        HorizontalDivider(color = StudioBorder)

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Live Production Mode",
                                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                    color = Color.White
                                )
                                Text(
                                    text = if (isLiveProduction) "Processing real financial UPI & Card transactions" else "Sandbox test simulated payments only",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = if (isLiveProduction) StudioAmber else Color(0xFF94A3B8)
                                )
                            }
                            Switch(
                                checked = isLiveProduction,
                                onCheckedChange = { isLiveProduction = it },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = StudioAmber,
                                    checkedTrackColor = StudioAmber.copy(alpha = 0.3f)
                                )
                            )
                        }
                    }
                }

                // UPI Merchant Configuration Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.5.dp, StudioAmber.copy(alpha = 0.6f), RoundedCornerShape(16.dp)),
                    colors = CardDefaults.cardColors(containerColor = StudioCard),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.QrCode,
                                contentDescription = null,
                                tint = StudioAmber,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Merchant UPI & Business Information",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = Color.White
                            )
                        }

                        OutlinedTextField(
                            value = upiId,
                            onValueChange = { upiId = it },
                            label = { Text("Primary Merchant UPI ID", color = Color(0xFF94A3B8)) },
                            placeholder = { Text("e.g. rajmittal012340@okhdfcbank", color = Color(0xFF64748B)) },
                            modifier = Modifier.fillMaxWidth().testTag("admin_screen_upi_field"),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedBorderColor = StudioAmber,
                                unfocusedBorderColor = StudioBorder
                            ),
                            singleLine = true
                        )

                        OutlinedTextField(
                            value = accountHolderName,
                            onValueChange = { accountHolderName = it },
                            label = { Text("Merchant / Business Registered Name", color = Color(0xFF94A3B8)) },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedBorderColor = StudioAmber,
                                unfocusedBorderColor = StudioBorder
                            ),
                            singleLine = true
                        )

                        // Bank Account & IFSC
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedTextField(
                                value = accountNumber,
                                onValueChange = { accountNumber = it },
                                label = { Text("Bank Account No.", color = Color(0xFF94A3B8)) },
                                modifier = Modifier.weight(1.2f),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedTextColor = Color.White,
                                    unfocusedTextColor = Color.White,
                                    focusedBorderColor = StudioAmber,
                                    unfocusedBorderColor = StudioBorder
                                ),
                                singleLine = true
                            )
                            OutlinedTextField(
                                value = ifscCode,
                                onValueChange = { ifscCode = it.uppercase() },
                                label = { Text("IFSC Code", color = Color(0xFF94A3B8)) },
                                modifier = Modifier.weight(0.8f),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedTextColor = Color.White,
                                    unfocusedTextColor = Color.White,
                                    focusedBorderColor = StudioAmber,
                                    unfocusedBorderColor = StudioBorder
                                ),
                                singleLine = true
                            )
                        }

                        OutlinedTextField(
                            value = bankName,
                            onValueChange = { bankName = it },
                            label = { Text("Bank Name & Branch", color = Color(0xFF94A3B8)) },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedBorderColor = StudioAmber,
                                unfocusedBorderColor = StudioBorder
                            ),
                            singleLine = true
                        )
                    }
                }

                // Razorpay API Credentials Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.5.dp, StudioSecondary.copy(alpha = 0.6f), RoundedCornerShape(16.dp)),
                    colors = CardDefaults.cardColors(containerColor = StudioCard),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Key,
                                contentDescription = null,
                                tint = StudioSecondary,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Razorpay / Gateway Credentials",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = Color.White
                            )
                        }

                        OutlinedTextField(
                            value = razorpayKeyId,
                            onValueChange = { razorpayKeyId = it },
                            label = { Text("Razorpay Key ID (rzp_live_...)", color = Color(0xFF94A3B8)) },
                            modifier = Modifier.fillMaxWidth().testTag("admin_razorpay_key_field"),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedBorderColor = StudioSecondary,
                                unfocusedBorderColor = StudioBorder
                            ),
                            singleLine = true
                        )

                        OutlinedTextField(
                            value = razorpayKeySecret,
                            onValueChange = { razorpayKeySecret = it },
                            label = { Text("Razorpay Key Secret", color = Color(0xFF94A3B8)) },
                            visualTransformation = if (isSecretVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            trailingIcon = {
                                IconButton(onClick = { isSecretVisible = !isSecretVisible }) {
                                    Icon(
                                        imageVector = if (isSecretVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                        contentDescription = "Toggle Secret",
                                        tint = Color(0xFF94A3B8)
                                    )
                                }
                            },
                            modifier = Modifier.fillMaxWidth().testTag("admin_razorpay_secret_field"),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedBorderColor = StudioSecondary,
                                unfocusedBorderColor = StudioBorder
                            ),
                            singleLine = true
                        )

                        OutlinedTextField(
                            value = webhookSecret,
                            onValueChange = { webhookSecret = it },
                            label = { Text("Webhook Signing Secret (whsec_...)", color = Color(0xFF94A3B8)) },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedBorderColor = StudioSecondary,
                                unfocusedBorderColor = StudioBorder
                            ),
                            singleLine = true
                        )

                        // Webhook URL display
                        Surface(
                            color = Color(0xFF0F172A),
                            shape = RoundedCornerShape(8.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, StudioBorder)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 12.dp, vertical = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "Webhook Listening URL",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = Color(0xFF94A3B8)
                                    )
                                    Text(
                                        text = "https://api.aimediastudio.pro/v1/webhook/razorpay",
                                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                                        color = StudioEmerald
                                    )
                                }
                                IconButton(
                                    onClick = {
                                        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                        val clip = ClipData.newPlainText("Webhook URL", "https://api.aimediastudio.pro/v1/webhook/razorpay")
                                        clipboard.setPrimaryClip(clip)
                                        Toast.makeText(context, "Webhook URL copied!", Toast.LENGTH_SHORT).show()
                                    },
                                    modifier = Modifier.size(28.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.ContentCopy,
                                        contentDescription = "Copy",
                                        tint = StudioSecondary,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                // Action Buttons: Verify & Save
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = onVerifyPaymentConnection,
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp)
                            .testTag("admin_verify_connection_button"),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF1E293B),
                            contentColor = StudioSecondary
                        ),
                        border = androidx.compose.foundation.BorderStroke(1.dp, StudioSecondary),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(imageVector = Icons.Default.WifiTethering, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Test Connection", fontWeight = FontWeight.Bold)
                    }

                    Button(
                        onClick = {
                            val updated = bankSettings.copy(
                                merchantUpiId = upiId,
                                accountHolderName = accountHolderName,
                                accountNumber = accountNumber,
                                ifscCode = ifscCode,
                                bankName = bankName,
                                razorpayKeyId = razorpayKeyId,
                                razorpayKeySecret = razorpayKeySecret,
                                webhookSecret = webhookSecret,
                                isLiveProductionMode = isLiveProduction,
                                directBankSettlementEnabled = isDirectSettlement
                            )
                            onSaveBankSettings(updated)
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp)
                            .testTag("admin_save_payment_settings_button"),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = StudioAmber,
                            contentColor = Color.Black
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Save, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Save Credentials", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // ==========================================
        // SECTION 1: CUSTOM ALPHANUMERIC PROMO CODE ENGINE
        // ==========================================
        if (selectedSection == 1) {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                // TOOL 1: ONE-CLICK RANDOM ALPHANUMERIC CODE GENERATOR
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.5.dp, Brush.horizontalGradient(listOf(StudioTertiary, StudioSecondary)), RoundedCornerShape(16.dp)),
                    colors = CardDefaults.cardColors(containerColor = StudioCard),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.AutoAwesome,
                                    contentDescription = null,
                                    tint = StudioTertiary,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Alphanumeric Code Generator Tool",
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Black),
                                    color = Color.White
                                )
                            }
                            Surface(
                                color = StudioTertiary.copy(alpha = 0.2f),
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Text(
                                    text = "SECURE RANDOM",
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                    color = StudioTertiary
                                )
                            }
                        }

                        Text(
                            text = "Instantly synthesize unique, non-guessable alphanumeric promo codes with custom prefixes for viral campaigns.",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF94A3B8)
                        )

                        // Generator Controls
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OutlinedTextField(
                                value = generatorPrefix,
                                onValueChange = { generatorPrefix = it.uppercase() },
                                label = { Text("Prefix (e.g. VIP)", color = Color(0xFF94A3B8)) },
                                modifier = Modifier.weight(1f).testTag("generator_prefix_input"),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedTextColor = Color.White,
                                    unfocusedTextColor = Color.White,
                                    focusedBorderColor = StudioTertiary,
                                    unfocusedBorderColor = StudioBorder
                                ),
                                singleLine = true
                            )

                            // Length Selector Pills
                            Row(
                                modifier = Modifier.weight(1f),
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                listOf(4, 6, 8).forEach { len ->
                                    val isSelected = generatorLength == len
                                    Surface(
                                        modifier = Modifier
                                            .weight(1f)
                                            .clip(RoundedCornerShape(8.dp))
                                            .border(
                                                1.dp,
                                                if (isSelected) StudioTertiary else StudioBorder,
                                                RoundedCornerShape(8.dp)
                                            )
                                            .clickable { generatorLength = len },
                                        color = if (isSelected) StudioTertiary.copy(alpha = 0.25f) else Color(0xFF0F172A)
                                    ) {
                                        Text(
                                            text = "${len}L",
                                            modifier = Modifier.padding(vertical = 12.dp),
                                            textAlign = TextAlign.Center,
                                            style = MaterialTheme.typography.labelMedium.copy(
                                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                            ),
                                            color = if (isSelected) StudioTertiary else Color(0xFF94A3B8)
                                        )
                                    }
                                }
                            }
                        }

                        // Generated Alphanumeric Output Card
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            color = Color(0xFF0F172A),
                            border = androidx.compose.foundation.BorderStroke(1.dp, StudioTertiary),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 14.dp, vertical = 10.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        text = "GENERATED CODE",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = Color(0xFF94A3B8)
                                    )
                                    Text(
                                        text = generatedPreviewCode,
                                        style = MaterialTheme.typography.headlineSmall.copy(
                                            fontWeight = FontWeight.Black,
                                            letterSpacing = 2.sp
                                        ),
                                        color = Color.White
                                    )
                                }

                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    // Use in creation form button
                                    Button(
                                        onClick = {
                                            promoCodeInput = generatedPreviewCode
                                            Toast.makeText(context, "Populated code into creation form!", Toast.LENGTH_SHORT).show()
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = StudioTertiary),
                                        shape = RoundedCornerShape(8.dp),
                                        modifier = Modifier.testTag("admin_use_generated_code_button")
                                    ) {
                                        Text("Use in Form", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold))
                                    }

                                    // Reroll random button
                                    IconButton(
                                        onClick = {
                                            generatedPreviewCode = onGenerateAlphanumericCode(generatorPrefix, generatorLength)
                                        },
                                        modifier = Modifier.size(36.dp).testTag("admin_reroll_code_button")
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Refresh,
                                            contentDescription = "Generate New",
                                            tint = StudioTertiary
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                // TOOL 2: CUSTOM PROMO CODE CREATION FORM
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, StudioBorder, RoundedCornerShape(16.dp)),
                    colors = CardDefaults.cardColors(containerColor = StudioCard),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "Create & Publish Custom Promo Code",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = Color.White
                        )

                        OutlinedTextField(
                            value = promoCodeInput,
                            onValueChange = { promoCodeInput = it.uppercase() },
                            label = { Text("Alphanumeric Promo Code (e.g. SPECIAL50, VIP77)", color = Color(0xFF94A3B8)) },
                            modifier = Modifier.fillMaxWidth().testTag("admin_promo_code_input"),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedBorderColor = StudioTertiary,
                                unfocusedBorderColor = StudioBorder
                            ),
                            singleLine = true
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            OutlinedTextField(
                                value = promoDiscountInput,
                                onValueChange = { promoDiscountInput = it },
                                label = { Text("Discount (₹ off)", color = Color(0xFF94A3B8)) },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier.weight(1f),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedTextColor = Color.White,
                                    unfocusedTextColor = Color.White,
                                    focusedBorderColor = StudioTertiary,
                                    unfocusedBorderColor = StudioBorder
                                ),
                                singleLine = true
                            )

                            OutlinedTextField(
                                value = promoExtraDaysInput,
                                onValueChange = { promoExtraDaysInput = it },
                                label = { Text("Extra Free Days", color = Color(0xFF94A3B8)) },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier.weight(1f),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedTextColor = Color.White,
                                    unfocusedTextColor = Color.White,
                                    focusedBorderColor = StudioTertiary,
                                    unfocusedBorderColor = StudioBorder
                                ),
                                singleLine = true
                            )
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            OutlinedTextField(
                                value = promoDescInput,
                                onValueChange = { promoDescInput = it },
                                label = { Text("Campaign Description", color = Color(0xFF94A3B8)) },
                                modifier = Modifier.weight(1.2f),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedTextColor = Color.White,
                                    unfocusedTextColor = Color.White,
                                    focusedBorderColor = StudioTertiary,
                                    unfocusedBorderColor = StudioBorder
                                ),
                                singleLine = true
                            )

                            OutlinedTextField(
                                value = promoExpiryInput,
                                onValueChange = { promoExpiryInput = it },
                                label = { Text("Expiry Date", color = Color(0xFF94A3B8)) },
                                modifier = Modifier.weight(0.8f),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedTextColor = Color.White,
                                    unfocusedTextColor = Color.White,
                                    focusedBorderColor = StudioTertiary,
                                    unfocusedBorderColor = StudioBorder
                                ),
                                singleLine = true
                            )
                        }

                        Button(
                            onClick = {
                                val discount = promoDiscountInput.toIntOrNull() ?: 50
                                val days = promoExtraDaysInput.toIntOrNull() ?: 10
                                if (onCreatePromoCode(promoCodeInput, discount, days, promoDescInput)) {
                                    promoCodeInput = ""
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .testTag("admin_publish_promo_button"),
                            colors = ButtonDefaults.buttonColors(containerColor = StudioTertiary),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(imageVector = Icons.Default.Add, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Publish Promo Code Live", fontWeight = FontWeight.Bold)
                        }
                    }
                }

                // TOOL 3: LIVE INVENTORY OF PROMO CODES
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Active Alphanumeric Promo Inventory (${promoCodes.size})",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = Color.White
                    )
                    Text(
                        text = "Real-Time Tracking",
                        style = MaterialTheme.typography.labelSmall,
                        color = StudioEmerald
                    )
                }

                promoCodes.forEach { promo ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                1.dp,
                                if (promo.isActive) StudioEmerald else StudioBorder,
                                RoundedCornerShape(14.dp)
                            ),
                        colors = CardDefaults.cardColors(containerColor = StudioCard),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Surface(
                                        color = if (promo.isActive) StudioEmerald.copy(alpha = 0.2f) else Color(0xFF334155),
                                        shape = RoundedCornerShape(8.dp),
                                        border = androidx.compose.foundation.BorderStroke(
                                            1.dp,
                                            if (promo.isActive) StudioEmerald else Color(0xFF64748B)
                                        )
                                    ) {
                                        Text(
                                            text = promo.code,
                                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Black),
                                            color = if (promo.isActive) Color.White else Color(0xFF94A3B8)
                                        )
                                    }

                                    Spacer(modifier = Modifier.width(8.dp))

                                    Surface(
                                        color = StudioAmber.copy(alpha = 0.2f),
                                        shape = RoundedCornerShape(6.dp)
                                    ) {
                                        Text(
                                            text = "₹${promo.discountRupees} OFF • +${promo.extraTrialDays}d FREE",
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                            color = StudioAmber
                                        )
                                    }
                                }

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Switch(
                                        checked = promo.isActive,
                                        onCheckedChange = { onTogglePromoCode(promo.code) },
                                        colors = SwitchDefaults.colors(
                                            checkedThumbColor = StudioEmerald,
                                            checkedTrackColor = StudioEmerald.copy(alpha = 0.3f)
                                        )
                                    )

                                    IconButton(
                                        onClick = { onDeletePromoCode(promo.code) },
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = "Delete",
                                            tint = Color(0xFFEF4444),
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "${promo.description} • Expires: ${promo.expiryDate}",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFFCBD5E1)
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            // Usage limit progress
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Redemptions: ${promo.currentUses} / ${promo.maxUses}",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color(0xFF94A3B8)
                                )
                                Text(
                                    text = "${((promo.currentUses.toFloat() / promo.maxUses) * 100).toInt()}% Used",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = StudioSecondary
                                )
                            }

                            Spacer(modifier = Modifier.height(4.dp))

                            LinearProgressIndicator(
                                progress = { (promo.currentUses.toFloat() / promo.maxUses).coerceIn(0f, 1f) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(4.dp)
                                    .clip(RoundedCornerShape(2.dp)),
                                color = StudioSecondary,
                                trackColor = Color(0xFF0F172A)
                            )
                        }
                    }
                }

                // TOOL 4: INTERACTIVE CODE REDEMPTION SIMULATOR
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, StudioBorder, RoundedCornerShape(14.dp)),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A))
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = "Admin Code Tester Simulator",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = Color.White
                        )
                        Text(
                            text = "Test how any code behaves at checkout without switching accounts.",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF94A3B8)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedTextField(
                                value = testCodeQuery,
                                onValueChange = { testCodeQuery = it.uppercase() },
                                placeholder = { Text("Enter code to test (e.g. SPECIAL50)", color = Color(0xFF64748B), style = MaterialTheme.typography.bodySmall) },
                                modifier = Modifier.weight(1f).testTag("admin_test_code_input"),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedTextColor = Color.White,
                                    unfocusedTextColor = Color.White,
                                    focusedBorderColor = StudioSecondary,
                                    unfocusedBorderColor = StudioBorder
                                ),
                                singleLine = true
                            )

                            Button(
                                onClick = {
                                    val matched = promoCodes.find { it.code.equals(testCodeQuery.trim(), ignoreCase = true) }
                                    if (matched != null) {
                                        val newPrice = (121 - matched.discountRupees).coerceAtLeast(49)
                                        testSimulationResult = "VALID: '${matched.code}' active! Discount: ₹${matched.discountRupees} off -> Final Price: ₹$newPrice/mo + ${matched.extraTrialDays} Extra Trial Days."
                                    } else {
                                        testSimulationResult = "INVALID: No active promo code found matching '$testCodeQuery'."
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = StudioSecondary),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.testTag("admin_run_test_code_button")
                            ) {
                                Text("Test", color = Color.Black, fontWeight = FontWeight.Bold)
                            }
                        }

                        testSimulationResult?.let { res ->
                            Spacer(modifier = Modifier.height(8.dp))
                            Surface(
                                color = if (res.startsWith("VALID")) StudioEmerald.copy(alpha = 0.15f) else Color(0xFF450A0A),
                                shape = RoundedCornerShape(8.dp),
                                border = androidx.compose.foundation.BorderStroke(
                                    1.dp,
                                    if (res.startsWith("VALID")) StudioEmerald else Color(0xFFEF4444)
                                )
                            ) {
                                Text(
                                    text = res,
                                    modifier = Modifier.padding(10.dp),
                                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                                    color = if (res.startsWith("VALID")) StudioEmerald else Color(0xFFEF4444)
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(60.dp))
    }
}
