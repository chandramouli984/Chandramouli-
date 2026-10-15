package com.example

import com.example.model.BankPayoutSettings
import com.example.model.CustomerSupportConfig
import com.example.model.PromoCodeItem
import com.example.model.ReferralSystemState
import com.example.model.SubscriptionState
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {
    @Test
    fun testCustomerSupportEmailConfiguration() {
        assertEquals("rajmittal012340@gmail.com", CustomerSupportConfig.PRIMARY_SUPPORT_EMAIL)
        assertEquals("Support Request - AI Media Studio Pro", CustomerSupportConfig.EMAIL_SUBJECT)
    }

    @Test
    fun testFifteenDayTrialAndZeroCreditSystem() {
        val sub = SubscriptionState(
            isSubscribed = false,
            accountCreatedMillis = System.currentTimeMillis(),
            baseTrialDays = 15,
            bonusReferralDays = 0,
            baseMonthlyPriceRupees = 121
        )
        assertTrue(sub.isTrialActive)
        assertEquals(15, sub.trialDaysRemaining)
        assertEquals(121, sub.monthlyPriceRupees)
    }

    @Test
    fun testReferralBonusAddsTenDays() {
        val initialSub = SubscriptionState(
            isSubscribed = false,
            accountCreatedMillis = System.currentTimeMillis(),
            baseTrialDays = 15,
            bonusReferralDays = 0
        )
        assertEquals(15, initialSub.trialDaysRemaining)

        // Referral reward granted (+10 free days)
        val rewardedSub = initialSub.copy(bonusReferralDays = initialSub.bonusReferralDays + 10)
        assertEquals(25, rewardedSub.trialDaysRemaining)
        assertEquals(25, rewardedSub.totalTrialDaysGranted)
    }

    @Test
    fun testPromoCodeDiscountCalculation() {
        val sub = SubscriptionState(
            baseMonthlyPriceRupees = 121,
            appliedPromoDiscountRupees = 50,
            appliedPromoCode = "SPECIAL50"
        )
        // ₹121 - ₹50 = ₹71
        assertEquals(71, sub.monthlyPriceRupees)
    }

    @Test
    fun testBankPayoutSettings() {
        val bank = BankPayoutSettings()
        assertEquals("rajmittal012340@okhdfcbank", bank.merchantUpiId)
        assertTrue(bank.directBankSettlementEnabled)
        assertTrue(bank.isLiveProductionMode)
    }

    @Test
    fun testPromoCodeItemConstructor() {
        val promo = PromoCodeItem(
            code = "SPECIAL50",
            discountRupees = 50,
            extraTrialDays = 10,
            description = "Owner Special"
        )
        assertEquals("SPECIAL50", promo.code)
        assertEquals(50, promo.discountRupees)
        assertEquals(10, promo.extraTrialDays)
        assertTrue(promo.isActive)
    }
}
