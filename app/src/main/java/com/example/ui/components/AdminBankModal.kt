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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Discount
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Security
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.model.BankPayoutSettings
import com.example.model.PromoCodeItem
import com.example.ui.theme.StudioAmber
import com.example.ui.theme.StudioBorder
import com.example.ui.theme.StudioCard
import com.example.ui.theme.StudioEmerald
import com.example.ui.theme.StudioPrimary
import com.example.ui.theme.StudioSecondary
import com.example.ui.theme.StudioTertiary

@Composable
fun AdminBankModal(
    bankSettings: BankPayoutSettings,
    promoCodes: List<PromoCodeItem>,
    onDismiss: () -> Unit,
    onSaveBankSettings: (BankPayoutSettings) -> Unit,
    onCreatePromoCode: (String, Int, Int, String) -> Boolean,
    onTogglePromoCode: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableIntStateOf(0) } // 0: Bank Payout, 1: Promo Codes

    // Bank Payout form state
    var upiId by remember { mutableStateOf(bankSettings.merchantUpiId) }
    var holderName by remember { mutableStateOf(bankSettings.accountHolderName) }
    var accountNum by remember { mutableStateOf(bankSettings.accountNumber) }
    var ifsc by remember { mutableStateOf(bankSettings.ifscCode) }
    var bankName by remember { mutableStateOf(bankSettings.bankName) }
    var razorpayKey by remember { mutableStateOf(bankSettings.razorpayKeyId) }
    var isDirectSettlement by remember { mutableStateOf(bankSettings.directBankSettlementEnabled) }

    // Promo Code form state
    var newCodeText by remember { mutableStateOf("") }
    var newDiscountText by remember { mutableStateOf("50") }
    var newExtraDaysText by remember { mutableStateOf("10") }
    var newDescText by remember { mutableStateOf("Special Promo Discount") }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = modifier
                .fillMaxWidth(0.95f)
                .clip(RoundedCornerShape(24.dp))
                .border(1.dp, StudioAmber.copy(alpha = 0.5f), RoundedCornerShape(24.dp))
                .imePadding(),
            color = Color(0xFF0F172A)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp)
            ) {
                // Top header
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
                                imageVector = Icons.Default.Security,
                                contentDescription = null,
                                tint = StudioAmber,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "OWNER ADMIN DASHBOARD",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Black),
                                color = StudioAmber
                            )
                        }
                    }

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.testTag("admin_modal_close_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = Color(0xFF94A3B8)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Tab Switcher
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(10.dp))
                            .border(
                                1.dp,
                                if (selectedTab == 0) StudioAmber else StudioBorder,
                                RoundedCornerShape(10.dp)
                            )
                            .clickable { selectedTab = 0 }
                            .testTag("admin_tab_bank"),
                        color = if (selectedTab == 0) StudioAmber.copy(alpha = 0.15f) else StudioCard
                    ) {
                        Row(
                            modifier = Modifier.padding(vertical = 10.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.AccountBalance,
                                contentDescription = null,
                                tint = if (selectedTab == 0) StudioAmber else Color(0xFF94A3B8),
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Bank Payouts",
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                color = if (selectedTab == 0) StudioAmber else Color(0xFF94A3B8)
                            )
                        }
                    }

                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(10.dp))
                            .border(
                                1.dp,
                                if (selectedTab == 1) StudioTertiary else StudioBorder,
                                RoundedCornerShape(10.dp)
                            )
                            .clickable { selectedTab = 1 }
                            .testTag("admin_tab_promo"),
                        color = if (selectedTab == 1) StudioTertiary.copy(alpha = 0.15f) else StudioCard
                    ) {
                        Row(
                            modifier = Modifier.padding(vertical = 10.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Discount,
                                contentDescription = null,
                                tint = if (selectedTab == 1) StudioTertiary else Color(0xFF94A3B8),
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Promo Codes",
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                color = if (selectedTab == 1) StudioTertiary else Color(0xFF94A3B8)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // TAB 0: BANK PAYOUT & PAYMENT SETTINGS
                if (selectedTab == 0) {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text(
                            text = "Direct Merchant Settlement Setup",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = Color.White
                        )
                        Text(
                            text = "Configure direct UPI ID and bank account where all ₹121/month user subscriptions will be deposited.",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF94A3B8)
                        )

                        // Direct Bank Settlement Toggle
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(1.dp, StudioBorder, RoundedCornerShape(12.dp)),
                            colors = CardDefaults.cardColors(containerColor = StudioCard)
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
                                        text = "Direct Bank Settlement Active",
                                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                        color = Color.White
                                    )
                                    Text(
                                        text = "Route subscription revenue directly to merchant UPI/Bank",
                                        style = MaterialTheme.typography.labelSmall,
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
                        }

                        // Merchant UPI ID Input
                        OutlinedTextField(
                            value = upiId,
                            onValueChange = { upiId = it },
                            label = { Text("Primary Merchant UPI ID (e.g., GPay / PhonePe)", color = Color(0xFF94A3B8)) },
                            modifier = Modifier.fillMaxWidth().testTag("admin_upi_input"),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedBorderColor = StudioAmber,
                                unfocusedBorderColor = StudioBorder
                            ),
                            singleLine = true
                        )

                        // Account Holder Name
                        OutlinedTextField(
                            value = holderName,
                            onValueChange = { holderName = it },
                            label = { Text("Account Holder / Business Name", color = Color(0xFF94A3B8)) },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedBorderColor = StudioAmber,
                                unfocusedBorderColor = StudioBorder
                            ),
                            singleLine = true
                        )

                        // Bank Account Number
                        OutlinedTextField(
                            value = accountNum,
                            onValueChange = { accountNum = it },
                            label = { Text("Bank Account Number", color = Color(0xFF94A3B8)) },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedBorderColor = StudioAmber,
                                unfocusedBorderColor = StudioBorder
                            ),
                            singleLine = true
                        )

                        // IFSC Code & Bank Name Row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedTextField(
                                value = ifsc,
                                onValueChange = { ifsc = it.uppercase() },
                                label = { Text("IFSC Code", color = Color(0xFF94A3B8)) },
                                modifier = Modifier.weight(1f),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedTextColor = Color.White,
                                    unfocusedTextColor = Color.White,
                                    focusedBorderColor = StudioAmber,
                                    unfocusedBorderColor = StudioBorder
                                ),
                                singleLine = true
                            )
                            OutlinedTextField(
                                value = bankName,
                                onValueChange = { bankName = it },
                                label = { Text("Bank Name", color = Color(0xFF94A3B8)) },
                                modifier = Modifier.weight(1f),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedTextColor = Color.White,
                                    unfocusedTextColor = Color.White,
                                    focusedBorderColor = StudioAmber,
                                    unfocusedBorderColor = StudioBorder
                                ),
                                singleLine = true
                            )
                        }

                        // Razorpay Key ID
                        OutlinedTextField(
                            value = razorpayKey,
                            onValueChange = { razorpayKey = it },
                            label = { Text("Razorpay / Payment Gateway Key ID", color = Color(0xFF94A3B8)) },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedBorderColor = StudioAmber,
                                unfocusedBorderColor = StudioBorder
                            ),
                            singleLine = true
                        )

                        // Save Button
                        Button(
                            onClick = {
                                val updated = bankSettings.copy(
                                    merchantUpiId = upiId,
                                    accountHolderName = holderName,
                                    accountNumber = accountNum,
                                    ifscCode = ifsc,
                                    bankName = bankName,
                                    razorpayKeyId = razorpayKey,
                                    directBankSettlementEnabled = isDirectSettlement
                                )
                                onSaveBankSettings(updated)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .testTag("save_bank_settings_button"),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = StudioAmber,
                                contentColor = Color.Black
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(imageVector = Icons.Default.Save, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Save Direct Merchant Settings", fontWeight = FontWeight.Bold)
                        }
                    }
                }

                // TAB 1: PROMO CODE MANAGEMENT
                if (selectedTab == 1) {
                    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                        Text(
                            text = "Manual Promo Code Creator",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = Color.White
                        )
                        Text(
                            text = "Create custom promo codes with discounts and trial extensions (e.g. SPECIAL50).",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF94A3B8)
                        )

                        // Create Promo Form Card
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(1.dp, StudioBorder, RoundedCornerShape(14.dp)),
                            colors = CardDefaults.cardColors(containerColor = StudioCard)
                        ) {
                            Column(
                                modifier = Modifier.padding(14.dp),
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                OutlinedTextField(
                                    value = newCodeText,
                                    onValueChange = { newCodeText = it.uppercase() },
                                    label = { Text("Promo Code (e.g. SPECIAL50)", color = Color(0xFF94A3B8)) },
                                    modifier = Modifier.fillMaxWidth().testTag("new_promo_code_input"),
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
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    OutlinedTextField(
                                        value = newDiscountText,
                                        onValueChange = { newDiscountText = it },
                                        label = { Text("Discount (₹)", color = Color(0xFF94A3B8)) },
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
                                        value = newExtraDaysText,
                                        onValueChange = { newExtraDaysText = it },
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

                                OutlinedTextField(
                                    value = newDescText,
                                    onValueChange = { newDescText = it },
                                    label = { Text("Description / Campaign Tag", color = Color(0xFF94A3B8)) },
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedTextColor = Color.White,
                                        unfocusedTextColor = Color.White,
                                        focusedBorderColor = StudioTertiary,
                                        unfocusedBorderColor = StudioBorder
                                    ),
                                    singleLine = true
                                )

                                Button(
                                    onClick = {
                                        val disc = newDiscountText.toIntOrNull() ?: 50
                                        val days = newExtraDaysText.toIntOrNull() ?: 10
                                        if (onCreatePromoCode(newCodeText, disc, days, newDescText)) {
                                            newCodeText = ""
                                        }
                                    },
                                    modifier = Modifier.fillMaxWidth().height(46.dp).testTag("create_promo_code_button"),
                                    colors = ButtonDefaults.buttonColors(containerColor = StudioTertiary),
                                    shape = RoundedCornerShape(10.dp)
                                ) {
                                    Icon(imageVector = Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Add Live Promo Code", fontWeight = FontWeight.Bold)
                                }
                            }
                        }

                        // Live Promo Codes List
                        Text(
                            text = "Active Promo Codes (${promoCodes.size})",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = Color.White
                        )

                        promoCodes.forEach { promo ->
                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .border(1.dp, if (promo.isActive) StudioEmerald else StudioBorder, RoundedCornerShape(12.dp)),
                                color = StudioCard,
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(14.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Text(
                                                text = promo.code,
                                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Black),
                                                color = Color.White
                                            )
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Surface(
                                                color = StudioEmerald.copy(alpha = 0.2f),
                                                shape = RoundedCornerShape(4.dp)
                                            ) {
                                                Text(
                                                    text = "₹${promo.discountRupees} OFF • +${promo.extraTrialDays}d FREE",
                                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                                    color = StudioEmerald
                                                )
                                            }
                                        }
                                        Text(
                                            text = "${promo.description} • Used ${promo.currentUses} times",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = Color(0xFF94A3B8)
                                        )
                                    }

                                    Switch(
                                        checked = promo.isActive,
                                        onCheckedChange = { onTogglePromoCode(promo.code) },
                                        colors = SwitchDefaults.colors(
                                            checkedThumbColor = StudioEmerald,
                                            checkedTrackColor = StudioEmerald.copy(alpha = 0.3f)
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
