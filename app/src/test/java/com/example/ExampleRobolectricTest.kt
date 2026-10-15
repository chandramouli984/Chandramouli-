package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.model.AppLanguage
import com.example.model.CustomerSupportConfig
import com.example.model.StudioModule
import com.example.viewmodel.StudioViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("AI Media & Audio Studio Pro", appName)
  }

  @Test
  fun `verify studio view model trial and subscription flow`() {
    val viewModel = StudioViewModel()
    val state = viewModel.uiState.value

    assertEquals(StudioModule.HOME, state.currentModule)
    assertEquals(AppLanguage.ENGLISH, state.currentLanguage)
    assertTrue(state.subscription.isTrialActive)
    assertFalse(state.subscription.isSubscribed)
    assertTrue(state.subscription.trialDaysRemaining in 1..25)

    // Activate Pro
    viewModel.activateProSubscription()
    assertTrue(viewModel.uiState.value.subscription.isSubscribed)
    assertFalse(viewModel.uiState.value.showPaywall)
  }

  @Test
  fun `verify referral sharing system grants 10 free days`() {
    val viewModel = StudioViewModel()
    val initialDays = viewModel.uiState.value.subscription.trialDaysRemaining

    // Simulate a friend signing up with referral code
    viewModel.simulateFriendSignup()
    val updatedState = viewModel.uiState.value

    assertEquals(1, updatedState.referralSystem.totalFriendsReferred)
    assertEquals(10, updatedState.referralSystem.totalBonusDaysEarned)
    assertEquals(initialDays + 10, updatedState.subscription.trialDaysRemaining)
  }

  @Test
  fun `verify promo code discounts fee and adds trial days`() {
    val viewModel = StudioViewModel()
    val initialDays = viewModel.uiState.value.subscription.trialDaysRemaining

    // Apply SPECIAL50 promo code
    val result = viewModel.applyReferralOrPromoCode("SPECIAL50")
    assertTrue(result)

    val updatedState = viewModel.uiState.value
    assertEquals("SPECIAL50", updatedState.subscription.appliedPromoCode)
    assertEquals(71, updatedState.subscription.monthlyPriceRupees) // ₹121 - ₹50 = ₹71
    assertEquals(initialDays + 10, updatedState.subscription.trialDaysRemaining)
  }

  @Test
  fun `verify official customer support email config`() {
    assertEquals("rajmittal012340@gmail.com", CustomerSupportConfig.PRIMARY_SUPPORT_EMAIL)
  }
}
