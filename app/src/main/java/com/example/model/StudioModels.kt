package com.example.model

enum class StudioModule {
    HOME,
    TEXT_TO_IMAGE,
    TEXT_TO_VIDEO,
    IMAGE_TO_VIDEO,
    TIMELINE_EDITOR,
    PRODUCT_PRESENTER,
    GENZ_STUDIO,
    ADMIN
}

enum class GenZSubTool(val titleKey: String, val subtitle: String) {
    TALKING_AVATARS("talking_avatars", "Photo upload + multi-lingual neural lip-sync"),
    KINETIC_CAPTIONS("kinetic_captions", "Whisper STT glowing 9:16 vertical caption overlays"),
    VIRAL_MEMES("viral_memes", "Text-prompt driven meme canvas & viral templates"),
    AUDIO_FILTERS("audio_filters", "Lo-Fi blends, Anime, Cinematic & Vocoder voice"),
    SOCIAL_EXPORT("social_export", "Direct 1080x1920 MP4 presets for Reels, Shorts & TikTok")
}

data class SubscriptionState(
    val isSubscribed: Boolean = false,
    val accountCreatedMillis: Long = System.currentTimeMillis() - (1L * 24 * 60 * 60 * 1000), // 1 day ago by default
    val baseTrialDays: Int = 15,
    val bonusReferralDays: Int = 0, // +10 days per friend or referral code
    val baseMonthlyPriceRupees: Int = 121,
    val appliedPromoDiscountRupees: Int = 0,
    val appliedPromoCode: String? = null,
    val renewalDate: String = "15 Nov 2026"
) {
    val monthlyPriceRupees: Int
        get() = (baseMonthlyPriceRupees - appliedPromoDiscountRupees).coerceAtLeast(49)

    val trialDaysPassed: Int
        get() {
            val diffMillis = System.currentTimeMillis() - accountCreatedMillis
            return (diffMillis / (1000 * 60 * 60 * 24)).toInt().coerceAtLeast(0)
        }

    val totalTrialDaysGranted: Int
        get() = baseTrialDays + bonusReferralDays

    val trialDaysRemaining: Int
        get() = (totalTrialDaysGranted - trialDaysPassed).coerceAtLeast(0)

    val isTrialActive: Boolean
        get() = !isSubscribed && trialDaysRemaining > 0

    val isExpired: Boolean
        get() = !isSubscribed && trialDaysRemaining <= 0
}

data class ReferralSystemState(
    val userReferralCode: String = "STUDIO-KR77",
    val referralLink: String = "https://aimediastudio.pro/join?ref=STUDIO-KR77",
    val totalFriendsReferred: Int = 0,
    val totalBonusDaysEarned: Int = 0,
    val refereeCodeApplied: Boolean = false,
    val refereeBonusDaysAwarded: Int = 0
)

data class BankPayoutSettings(
    val merchantUpiId: String = "rajmittal012340@okhdfcbank",
    val accountHolderName: String = "Raj Mittal Studio Ent",
    val accountNumber: String = "50100492817291",
    val ifscCode: String = "HDFC0001234",
    val bankName: String = "HDFC Bank Ltd",
    val razorpayKeyId: String = "rzp_live_media_studio_99",
    val razorpayKeySecret: String = "sk_live_9a8b7c6d5e4f3a2b1c",
    val webhookSecret: String = "whsec_studio_pro_auto_payout_99",
    val isLiveProductionMode: Boolean = true,
    val directBankSettlementEnabled: Boolean = true,
    val autoPayoutSchedule: String = "Instant / T+0 Settlement",
    val totalSettledRevenueRupees: Int = 18450,
    val activeSubscribedUsers: Int = 152
)

data class PromoCodeItem(
    val code: String,
    val discountRupees: Int,
    val extraTrialDays: Int,
    val description: String,
    val id: String = java.util.UUID.randomUUID().toString(),
    val maxUses: Int = 500,
    val currentUses: Int = 38,
    val expiryDate: String = "31 Dec 2026",
    val isActive: Boolean = true
)

object CustomerSupportConfig {
    const val PRIMARY_SUPPORT_EMAIL = "rajmittal012340@gmail.com"
    const val EMAIL_SUBJECT = "Support Request - AI Media Studio Pro"
}

data class UserProfile(
    val name: String = "K. Ramanathan",
    val email: String = "k22796325@gmail.com",
    val avatarInitials: String = "KR",
    val isSignedIn: Boolean = true,
    val isAdmin: Boolean = true
)

enum class ImageStylePreset(val label: String, val promptModifier: String) {
    CINEMATIC("Cinematic", "cinematic lighting, 35mm film photograph, dramatic rim light, shallow depth of field, photorealistic, 8k"),
    ANIME("Anime Studio", "vibrant anime illustration style, Makoto Shinkai aesthetic, luminous sky, detailed cel shading"),
    CYBERPUNK("Cyberpunk", "neon cyberpunk aesthetic, futuristic cityscape reflections, glowing purple and teal highlights"),
    RENDER_3D("3D Pixar", "hyper-detailed 3D render, octane render, soft ambient occlusion, charming animated style"),
    HYPER_REALISTIC("Hyper-Realistic", "ultra-detailed Hasselblad camera shot, 8k resolution, raw photo, natural pores and textures"),
    OIL_PAINTING("Oil Painting", "expressive oil on canvas, textured impasto brushstrokes, classical fine art composition"),
    MINIMALIST("Minimalist", "clean minimalist vector aesthetic, flat colors, elegant negative space, modern editorial"),
    WATERCOLOR("Watercolor", "delicate watercolor washes, expressive pigment blooms, handmade cotton paper texture")
}

enum class AspectRatio(val label: String, val ratioWidth: Float, val ratioHeight: Float) {
    SQUARE("1:1 Square", 1f, 1f),
    PORTRAIT_REEL("9:16 Reels/Shorts", 9f, 16f),
    LANDSCAPE_16_9("16:9 Landscape", 16f, 9f),
    STANDARD_4_3("4:3 Standard", 4f, 3f)
}

enum class VideoMotionPreset(val label: String, val description: String) {
    PAN_ZOOM("Pan & Dynamic Zoom", "Slow cinematic push-in with subtle lateral camera pan"),
    ORBIT_3D("3D Orbital Spin", "Smooth 360-degree floating rotational camera orbit"),
    LIQUID_FLOW("Liquid Flow Morph", "Continuous fluid particle wave animation"),
    PARALLAX("Cinematic Parallax", "Depth map layer separation with atmospheric perspective"),
    HYPERLAPSE("AI Hyperlapse", "High-speed timelapse glide with motion blur")
}

enum class MediaType {
    IMAGE,
    VIDEO,
    AUDIO,
    COMMERCIAL_AD
}

data class GeneratedMediaItem(
    val id: String,
    val title: String,
    val prompt: String,
    val type: MediaType,
    val drawableResName: String? = null,
    val durationFormatted: String? = null,
    val durationSeconds: Int = 0,
    val timestampFormatted: String = "Just now",
    val language: AppLanguage = AppLanguage.ENGLISH,
    val tags: List<String> = emptyList()
)

data class TimelineTrackItem(
    val id: String,
    val trackType: String, // "VIDEO", "AUDIO", "CAPTION"
    val title: String,
    val startSec: Float,
    val durationSec: Float,
    val colorHex: Long
)

enum class AttachmentType {
    NONE,
    IMAGE_REFERENCE,
    VIDEO_REFERENCE
}

data class SearchAttachmentState(
    val type: AttachmentType = AttachmentType.NONE,
    val name: String = "",
    val details: String = ""
)
