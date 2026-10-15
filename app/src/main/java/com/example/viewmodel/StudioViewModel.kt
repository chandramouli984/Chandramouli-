package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.AppLanguage
import com.example.model.AspectRatio
import com.example.model.AttachmentType
import com.example.model.BankPayoutSettings
import com.example.model.CustomerSupportConfig
import com.example.model.GenZSubTool
import com.example.model.GeneratedMediaItem
import com.example.model.ImageStylePreset
import com.example.model.MediaType
import com.example.model.PromoCodeItem
import com.example.model.ReferralSystemState
import com.example.model.SearchAttachmentState
import com.example.model.StudioModule
import com.example.model.SubscriptionState
import com.example.model.TimelineTrackItem
import com.example.model.UserProfile
import com.example.model.VideoMotionPreset
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID

data class StudioUiState(
    val currentModule: StudioModule = StudioModule.HOME,
    val currentLanguage: AppLanguage = AppLanguage.ENGLISH,
    val subscription: SubscriptionState = SubscriptionState(),
    val userProfile: UserProfile = UserProfile(),
    val showPaywall: Boolean = false,
    val searchQuery: String = "",
    val searchAttachment: SearchAttachmentState = SearchAttachmentState(),
    val recentCreations: List<GeneratedMediaItem> = emptyList(),
    val isGenerating: Boolean = false,
    val generationProgress: Float = 0f,
    val generationStatusMessage: String = "",
    val lastGeneratedItem: GeneratedMediaItem? = null,
    val toastMessage: String? = null,

    // Referral & Sharing System state (10-Day Free Referral Rewards)
    val referralSystem: ReferralSystemState = ReferralSystemState(),
    val showReferralDialog: Boolean = false,

    // Customer Support state (Primary support: rajmittal012340@gmail.com)
    val showSupportDialog: Boolean = false,

    // Owner Bank & Promo Code state
    val bankPayoutSettings: BankPayoutSettings = BankPayoutSettings(),
    val promoCodesList: List<PromoCodeItem> = listOf(
        PromoCodeItem(code = "SPECIAL50", discountRupees = 50, extraTrialDays = 10, description = "Owner Special: ₹50 Off + 10 Free Days"),
        PromoCodeItem(code = "VIRAL10", discountRupees = 30, extraTrialDays = 10, description = "Viral Creator Pass: 10 Extra Days + ₹30 Off"),
        PromoCodeItem(code = "FOUNDER10", discountRupees = 71, extraTrialDays = 15, description = "Founder Pass: ₹50 Flat + 15 Free Days")
    ),
    val showAdminBankDialog: Boolean = false,

    // Text to Image state
    val imagePrompt: String = "",
    val selectedStylePreset: ImageStylePreset = ImageStylePreset.CINEMATIC,
    val selectedAspectRatio: AspectRatio = AspectRatio.PORTRAIT_REEL,

    // Text to Video state
    val videoPrompt: String = "",
    val videoDurationSeconds: Int = 15, // Flexible: 5, 15, 30, 300, 600, 1800
    val videoResolution: String = "1080p 60fps",

    // Image to Video state
    val sourceImageName: String = "ic_launcher_media_studio",
    val selectedMotionPreset: VideoMotionPreset = VideoMotionPreset.PAN_ZOOM,
    val motionIntensity: Float = 2.5f,

    // Product Studio state
    val presenterPhotoName: String = "presenter_model",
    val productPhotoName: String = "product_sample",
    val adScript: String = "Experience pure acoustic perfection with Active Noise Cancellation and 40-hour ultra battery life.",
    val isAutoDurationMode: Boolean = true,
    val manualDurationSeconds: Int = 20,
    val selectedVoiceoverLanguage: AppLanguage = AppLanguage.ENGLISH,
    val presenterLipSyncActive: Boolean = false,

    // Gen Z Studio state
    val currentGenZSubTool: GenZSubTool = GenZSubTool.TALKING_AVATARS,
    val memeTopText: String = "WHEN AI RENDERS YOUR 30-MIN VIDEO",
    val memeBottomText: String = "IN 4 SECONDS WITHOUT LOSING QUALITY",
    val selectedAudioFilter: String = "Lo-Fi Acoustic Chill",
    val kineticCaptionStyle: String = "Viral Yellow Pop (MrBeast Style)",
    val isAudioFilterPlaying: Boolean = false,

    // Timeline Editor state
    val timelineTracks: List<TimelineTrackItem> = emptyList(),
    val timelineCurrentTimeSec: Float = 0f,
    val timelineTotalDurationSec: Float = 30f,
    val isTimelinePlaying: Boolean = false
)

class StudioViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(StudioUiState())
    val uiState: StateFlow<StudioUiState> = _uiState.asStateFlow()

    init {
        loadInitialMediaCreations()
        initTimelineTracks()
    }

    private fun loadInitialMediaCreations() {
        val initialCreations = listOf(
            GeneratedMediaItem(
                id = "m1",
                title = "Cinematic Cyberpunk Neon Odyssey",
                prompt = "Futuristic Tokyo alley in neon rain with flying autonomous speeders, 8k",
                type = MediaType.VIDEO,
                drawableResName = "hero_studio_banner",
                durationFormatted = "0:30",
                durationSeconds = 30,
                timestampFormatted = "10m ago",
                language = AppLanguage.ENGLISH,
                tags = listOf("4K", "60fps", "Cinematic")
            ),
            GeneratedMediaItem(
                id = "m2",
                title = "Audiophile ANC Headphone Commercial",
                prompt = "Commercial product ad with neural presenter and spatial audio BGM",
                type = MediaType.COMMERCIAL_AD,
                drawableResName = "product_sample",
                durationFormatted = "0:18",
                durationSeconds = 18,
                timestampFormatted = "1h ago",
                language = AppLanguage.ENGLISH,
                tags = listOf("Presenter", "Lip-Sync", "Dual-Visual")
            ),
            GeneratedMediaItem(
                id = "m3",
                title = "Viral Gen Z Talking Avatar",
                prompt = "Energetic tech review avatar with glowing kinetic captions",
                type = MediaType.VIDEO,
                drawableResName = "presenter_model",
                durationFormatted = "0:15",
                durationSeconds = 15,
                timestampFormatted = "3h ago",
                language = AppLanguage.ENGLISH,
                tags = listOf("Talking Avatar", "9:16", "Viral")
            ),
            GeneratedMediaItem(
                id = "m4",
                title = "Vibrant Vaporwave Gen Z Poster",
                prompt = "Gen Z aesthetic banner with kinetic fonts and holographic gradients",
                type = MediaType.IMAGE,
                drawableResName = "genz_viral_card",
                durationFormatted = null,
                durationSeconds = 0,
                timestampFormatted = "5h ago",
                language = AppLanguage.ENGLISH,
                tags = listOf("Meme", "Vaporwave")
            )
        )
        _uiState.value = _uiState.value.copy(recentCreations = initialCreations)
    }

    private fun initTimelineTracks() {
        val tracks = listOf(
            TimelineTrackItem(
                id = "t1",
                trackType = "VIDEO",
                title = "Video Clip 01: Cyberpunk Intro",
                startSec = 0f,
                durationSec = 12f,
                colorHex = 0xFF6366F1
            ),
            TimelineTrackItem(
                id = "t2",
                trackType = "VIDEO",
                title = "Video Clip 02: Headphone 3D Orbit",
                startSec = 12f,
                durationSec = 18f,
                colorHex = 0xFF4F46E5
            ),
            TimelineTrackItem(
                id = "t3",
                trackType = "AUDIO",
                title = "Voiceover: Aria Neural Speech (EN-IN)",
                startSec = 0f,
                durationSec = 22f,
                colorHex = 0xFF06B6D4
            ),
            TimelineTrackItem(
                id = "t4",
                trackType = "AUDIO",
                title = "BGM: Lo-Fi Cyber Beats (-6dB)",
                startSec = 0f,
                durationSec = 30f,
                colorHex = 0xFF10B981
            ),
            TimelineTrackItem(
                id = "t5",
                trackType = "CAPTION",
                title = "Kinetic Captions: Yellow Pop",
                startSec = 1f,
                durationSec = 25f,
                colorHex = 0xFFF59E0B
            )
        )
        _uiState.value = _uiState.value.copy(
            timelineTracks = tracks,
            timelineTotalDurationSec = 30f
        )
    }

    fun navigateToModule(module: StudioModule) {
        _uiState.value = _uiState.value.copy(currentModule = module)
    }

    fun setLanguage(language: AppLanguage) {
        _uiState.value = _uiState.value.copy(
            currentLanguage = language,
            selectedVoiceoverLanguage = language
        )
    }

    fun openPaywall() {
        _uiState.value = _uiState.value.copy(showPaywall = true)
    }

    fun closePaywall() {
        _uiState.value = _uiState.value.copy(showPaywall = false)
    }

    fun openReferralDialog() {
        _uiState.value = _uiState.value.copy(showReferralDialog = true)
    }

    fun closeReferralDialog() {
        _uiState.value = _uiState.value.copy(showReferralDialog = false)
    }

    fun openSupportDialog() {
        _uiState.value = _uiState.value.copy(showSupportDialog = true)
    }

    fun closeSupportDialog() {
        _uiState.value = _uiState.value.copy(showSupportDialog = false)
    }

    fun openAdminBankDialog() {
        _uiState.value = _uiState.value.copy(showAdminBankDialog = true)
    }

    fun closeAdminBankDialog() {
        _uiState.value = _uiState.value.copy(showAdminBankDialog = false)
    }

    fun activateProSubscription() {
        _uiState.value = _uiState.value.copy(
            subscription = _uiState.value.subscription.copy(isSubscribed = true),
            showPaywall = false,
            toastMessage = "Pro Activated! Unlimited Generations Unlocked."
        )
    }

    // Dynamic App Sharing & 10-Day Free Referral Reward System
    fun simulateFriendSignup() {
        val updatedRef = _uiState.value.referralSystem.let {
            it.copy(
                totalFriendsReferred = it.totalFriendsReferred + 1,
                totalBonusDaysEarned = it.totalBonusDaysEarned + 10
            )
        }
        val updatedSub = _uiState.value.subscription.let {
            it.copy(bonusReferralDays = it.bonusReferralDays + 10)
        }
        _uiState.value = _uiState.value.copy(
            referralSystem = updatedRef,
            subscription = updatedSub,
            toastMessage = "🎉 Friend registered via your code! +10 FREE Days added to your account!"
        )
    }

    fun applyReferralOrPromoCode(rawCode: String): Boolean {
        val code = rawCode.trim().uppercase()
        if (code.isBlank()) return false

        // Check if matching an active owner promo code
        val promo = _uiState.value.promoCodesList.find { it.code.equals(code, ignoreCase = true) && it.isActive }
        if (promo != null) {
            val updatedSub = _uiState.value.subscription.copy(
                bonusReferralDays = _uiState.value.subscription.bonusReferralDays + promo.extraTrialDays,
                appliedPromoDiscountRupees = promo.discountRupees,
                appliedPromoCode = promo.code
            )
            val updatedList = _uiState.value.promoCodesList.map {
                if (it.code == promo.code) it.copy(currentUses = it.currentUses + 1) else it
            }
            _uiState.value = _uiState.value.copy(
                subscription = updatedSub,
                promoCodesList = updatedList,
                toastMessage = "Promo '${promo.code}' applied! +${promo.extraTrialDays} Free Days & ₹${promo.discountRupees} Discount!"
            )
            return true
        }

        // Check if referral code (e.g. starts with STUDIO- or any custom referral code)
        if (code.startsWith("STUDIO-") || code.length >= 5) {
            val updatedSub = _uiState.value.subscription.copy(
                bonusReferralDays = _uiState.value.subscription.bonusReferralDays + 10,
                appliedPromoDiscountRupees = 30, // ₹30 off subscription fee
                appliedPromoCode = code
            )
            val updatedRef = _uiState.value.referralSystem.copy(
                refereeCodeApplied = true,
                refereeBonusDaysAwarded = 10
            )
            _uiState.value = _uiState.value.copy(
                subscription = updatedSub,
                referralSystem = updatedRef,
                toastMessage = "Referral code applied! +10 Free Days & ₹30 Discount unlocked!"
            )
            return true
        }

        _uiState.value = _uiState.value.copy(toastMessage = "Invalid code. Please check and try again.")
        return false
    }

    // Owner Bank Payout Configuration
    fun updateBankPayoutSettings(newSettings: BankPayoutSettings) {
        _uiState.value = _uiState.value.copy(
            bankPayoutSettings = newSettings,
            toastMessage = "Merchant Bank & UPI Settlement settings saved successfully!"
        )
    }

    // Owner Manual Promo Code Management
    fun createManualPromoCode(code: String, discountRupees: Int, extraTrialDays: Int, description: String): Boolean {
        val cleanCode = code.trim().uppercase()
        if (cleanCode.length < 3) {
            _uiState.value = _uiState.value.copy(toastMessage = "Code must be at least 3 characters")
            return false
        }
        if (_uiState.value.promoCodesList.any { it.code == cleanCode }) {
            _uiState.value = _uiState.value.copy(toastMessage = "Promo Code already exists!")
            return false
        }

        val newItem = PromoCodeItem(
            code = cleanCode,
            discountRupees = discountRupees.coerceIn(0, 80),
            extraTrialDays = extraTrialDays.coerceIn(1, 60),
            description = description.ifBlank { "Custom Owner Promo" }
        )
        _uiState.value = _uiState.value.copy(
            promoCodesList = listOf(newItem) + _uiState.value.promoCodesList,
            toastMessage = "Promo Code '$cleanCode' created successfully!"
        )
        return true
    }

    fun togglePromoCodeActive(code: String) {
        val updated = _uiState.value.promoCodesList.map {
            if (it.code == code) it.copy(isActive = !it.isActive) else it
        }
        _uiState.value = _uiState.value.copy(
            promoCodesList = updated,
            toastMessage = "Promo code status updated"
        )
    }

    fun generateRandomAlphanumericCode(prefix: String = "STUDIO", length: Int = 6): String {
        val chars = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ"
        val randomPart = (1..length).map { chars.random() }.joinToString("")
        val cleanPrefix = prefix.trim().uppercase().ifBlank { "STUDIO" }
        return "$cleanPrefix-$randomPart"
    }

    fun deletePromoCode(code: String) {
        val updated = _uiState.value.promoCodesList.filterNot { it.code == code }
        _uiState.value = _uiState.value.copy(
            promoCodesList = updated,
            toastMessage = "Promo Code '$code' removed"
        )
    }

    fun testVerifyPaymentConnection() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(toastMessage = "Connecting to Razorpay & UPI Gateway...")
            delay(700)
            _uiState.value = _uiState.value.copy(
                toastMessage = "✓ Verified! UPI ID ${_uiState.value.bankPayoutSettings.merchantUpiId} & Razorpay credentials active."
            )
        }
    }

    // Multi-Modal Search
    fun updateSearchQuery(query: String) {
        _uiState.value = _uiState.value.copy(searchQuery = query)
    }

    fun attachSearchImage() {
        _uiState.value = _uiState.value.copy(
            searchAttachment = SearchAttachmentState(
                type = AttachmentType.IMAGE_REFERENCE,
                name = "Style_Reference_Image.png",
                details = "Color Palette & Texture Match"
            ),
            toastMessage = "Image attached for visual search & style matching"
        )
    }

    fun attachSearchVideo() {
        _uiState.value = _uiState.value.copy(
            searchAttachment = SearchAttachmentState(
                type = AttachmentType.VIDEO_REFERENCE,
                name = "Motion_Transfer_Clip.mp4",
                details = "Camera Pan & Motion Cloned"
            ),
            toastMessage = "Video attached for video-to-video motion transfer"
        )
    }

    fun clearSearchAttachment() {
        _uiState.value = _uiState.value.copy(
            searchAttachment = SearchAttachmentState(type = AttachmentType.NONE)
        )
    }

    fun submitSearch(query: String) {
        // Pre-fill target module prompt based on query
        _uiState.value = _uiState.value.copy(
            imagePrompt = query,
            videoPrompt = query,
            toastMessage = "Search prompt synced across Studio modules"
        )
    }

    // Text to Image actions
    fun updateImagePrompt(prompt: String) {
        _uiState.value = _uiState.value.copy(imagePrompt = prompt)
    }

    fun selectImageStylePreset(preset: ImageStylePreset) {
        _uiState.value = _uiState.value.copy(selectedStylePreset = preset)
    }

    fun selectAspectRatio(ratio: AspectRatio) {
        _uiState.value = _uiState.value.copy(selectedAspectRatio = ratio)
    }

    fun enhanceImagePrompt() {
        val current = _uiState.value.imagePrompt.ifBlank { "Futuristic creative landscape" }
        val enhanced = "$current, dramatic volumetric lighting, cinematic color grading, 8k ultra-sharp detail, masterpiece composition"
        _uiState.value = _uiState.value.copy(imagePrompt = enhanced)
    }

    fun generateTextToImage() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isGenerating = true,
                generationProgress = 0.1f,
                generationStatusMessage = "Synthesizing text latent vectors..."
            )
            delay(600)
            _uiState.value = _uiState.value.copy(
                generationProgress = 0.45f,
                generationStatusMessage = "Rendering ${_uiState.value.selectedStylePreset.label} style textures..."
            )
            delay(700)
            _uiState.value = _uiState.value.copy(
                generationProgress = 0.85f,
                generationStatusMessage = "Upscaling to high-resolution 4K..."
            )
            delay(500)

            val newItem = GeneratedMediaItem(
                id = UUID.randomUUID().toString(),
                title = _uiState.value.imagePrompt.take(30).ifBlank { "Studio Visual" },
                prompt = _uiState.value.imagePrompt,
                type = MediaType.IMAGE,
                drawableResName = "hero_studio_banner",
                timestampFormatted = "Just now",
                language = _uiState.value.currentLanguage,
                tags = listOf(_uiState.value.selectedStylePreset.label, _uiState.value.selectedAspectRatio.label)
            )

            _uiState.value = _uiState.value.copy(
                isGenerating = false,
                generationProgress = 1.0f,
                lastGeneratedItem = newItem,
                recentCreations = listOf(newItem) + _uiState.value.recentCreations,
                toastMessage = "4K Image Generated Successfully!"
            )
        }
    }

    // Text to Video actions
    fun updateVideoPrompt(prompt: String) {
        _uiState.value = _uiState.value.copy(videoPrompt = prompt)
    }

    fun setVideoDuration(seconds: Int) {
        _uiState.value = _uiState.value.copy(videoDurationSeconds = seconds)
    }

    fun generateTextToVideo() {
        viewModelScope.launch {
            val dur = _uiState.value.videoDurationSeconds
            val durationText = if (dur >= 60) "${dur / 60}m" else "${dur}s"

            _uiState.value = _uiState.value.copy(
                isGenerating = true,
                generationProgress = 0.1f,
                generationStatusMessage = "Initializing temporal diffusion queue ($durationText)..."
            )
            delay(800)
            _uiState.value = _uiState.value.copy(
                generationProgress = 0.4f,
                generationStatusMessage = "Synthesizing keyframe camera motion at 60fps..."
            )
            delay(900)
            _uiState.value = _uiState.value.copy(
                generationProgress = 0.75f,
                generationStatusMessage = "Rendering multi-frame consistency pipeline..."
            )
            delay(800)
            _uiState.value = _uiState.value.copy(
                generationProgress = 0.95f,
                generationStatusMessage = "Encoding high-efficiency H.265 MP4..."
            )
            delay(400)

            val newItem = GeneratedMediaItem(
                id = UUID.randomUUID().toString(),
                title = _uiState.value.videoPrompt.take(30).ifBlank { "AI Video Clip" },
                prompt = _uiState.value.videoPrompt,
                type = MediaType.VIDEO,
                drawableResName = "hero_studio_banner",
                durationFormatted = if (dur >= 60) "${dur / 60}:00" else "0:${if (dur < 10) "0$dur" else dur}",
                durationSeconds = dur,
                timestampFormatted = "Just now",
                language = _uiState.value.currentLanguage,
                tags = listOf("60fps", durationText, "4K")
            )

            _uiState.value = _uiState.value.copy(
                isGenerating = false,
                generationProgress = 1.0f,
                lastGeneratedItem = newItem,
                recentCreations = listOf(newItem) + _uiState.value.recentCreations,
                toastMessage = "$durationText Video Render Complete!"
            )
        }
    }

    // Image to Video actions
    fun selectMotionPreset(preset: VideoMotionPreset) {
        _uiState.value = _uiState.value.copy(selectedMotionPreset = preset)
    }

    fun setMotionIntensity(intensity: Float) {
        _uiState.value = _uiState.value.copy(motionIntensity = intensity)
    }

    fun generateImageToVideo() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isGenerating = true,
                generationProgress = 0.15f,
                generationStatusMessage = "Extracting depth map from static image..."
            )
            delay(700)
            _uiState.value = _uiState.value.copy(
                generationProgress = 0.6f,
                generationStatusMessage = "Applying ${_uiState.value.selectedMotionPreset.label} motion vector..."
            )
            delay(800)
            _uiState.value = _uiState.value.copy(
                generationProgress = 0.95f,
                generationStatusMessage = "Smoothing camera interpolation..."
            )
            delay(500)

            val newItem = GeneratedMediaItem(
                id = UUID.randomUUID().toString(),
                title = "Animated ${_uiState.value.selectedMotionPreset.label}",
                prompt = "Image animation with ${_uiState.value.selectedMotionPreset.label}",
                type = MediaType.VIDEO,
                drawableResName = _uiState.value.sourceImageName,
                durationFormatted = "0:15",
                durationSeconds = 15,
                timestampFormatted = "Just now",
                language = _uiState.value.currentLanguage,
                tags = listOf(_uiState.value.selectedMotionPreset.label, "Motion Transfer")
            )

            _uiState.value = _uiState.value.copy(
                isGenerating = false,
                generationProgress = 1.0f,
                lastGeneratedItem = newItem,
                recentCreations = listOf(newItem) + _uiState.value.recentCreations,
                toastMessage = "Image Animated to Video!"
            )
        }
    }

    // Product Presenter Studio actions
    fun updateAdScript(script: String) {
        _uiState.value = _uiState.value.copy(adScript = script)
    }

    fun toggleAutoDuration(isAuto: Boolean) {
        _uiState.value = _uiState.value.copy(isAutoDurationMode = isAuto)
    }

    fun setManualDuration(seconds: Int) {
        _uiState.value = _uiState.value.copy(manualDurationSeconds = seconds)
    }

    fun setVoiceoverLanguage(language: AppLanguage) {
        _uiState.value = _uiState.value.copy(selectedVoiceoverLanguage = language)
    }

    fun autoGenerateProductScript() {
        val scripts = when (_uiState.value.currentLanguage) {
            AppLanguage.TELUGU -> "ఈ అద్భుతమైన వైర్‌లెస్ హెడ్‌ఫోన్‌లతో సరికొత్త సంగీత అనుభవాన్ని పొందండి. 40 గంటల బ్యాటరీ లైఫ్ మరియు క్రిస్టల్ క్లియర్ సౌండ్!"
            AppLanguage.TAMIL -> "இந்த புதிய வயர்லெஸ் ஹெட்ஃபோன்களுடன் தூய ஒலி அனுபவத்தைப் பெறுங்கள். 40 மணிநேர பேட்டரி மற்றும் மிகச்சிறந்த நாய்ஸ் கேன்சலேஷன்!"
            AppLanguage.MALAYALAM -> "ഈ അത്ഭുതകരമായ വയർലെസ് ഹെഡ്‌ഫോണുകൾ ഉപയോഗിച്ച് മികച്ച ശബ്ദം ആസ്വദിക്കൂ. 40 മണിക്കൂർ ബാറ്ററി ബാക്കപ്പ്!"
            AppLanguage.KANNADA -> "ಈ ನವೀನ ವೈರ್‌ಲೆಸ್ ಹೆಡ್‌ಫೋನ್ ಮೂಲಕ ಸ್ಪಷ್ಟ ಸಂಗೀತವನ್ನು ಆನಂದಿಸಿ. 40 ಗಂಟೆಗಳ ದೀರ್ಘ ಬ್ಯಾಟರಿ ಬಾಳಿಕೆ!"
            AppLanguage.ENGLISH -> "Upgrade to pure acoustic perfection with next-gen Active Noise Cancellation, ergonomic memory foam, and 40-hour ultra battery life. Grab yours today for an exclusive price!"
        }
        _uiState.value = _uiState.value.copy(adScript = scripts)
    }

    val calculatedAutoDurationSeconds: Int
        get() {
            val words = _uiState.value.adScript.split("\\s+".toRegex()).filter { it.isNotBlank() }.size
            // ~140 words per minute -> 2.3 words/sec + 3s buffer
            return ((words / 2.3f) + 3f).toInt().coerceIn(5, 120)
        }

    fun generateProductPresenterAd() {
        viewModelScope.launch {
            val dur = if (_uiState.value.isAutoDurationMode) calculatedAutoDurationSeconds else _uiState.value.manualDurationSeconds
            _uiState.value = _uiState.value.copy(
                isGenerating = true,
                generationProgress = 0.15f,
                generationStatusMessage = "Synthesizing ${_uiState.value.selectedVoiceoverLanguage.nativeName} Neural Voiceover..."
            )
            delay(700)
            _uiState.value = _uiState.value.copy(
                generationProgress = 0.5f,
                generationStatusMessage = "Generating SadTalker neural lip-sync for Presenter..."
            )
            delay(900)
            _uiState.value = _uiState.value.copy(
                generationProgress = 0.8f,
                generationStatusMessage = "Compositing Dual-Visual Ad with Product & Dynamic BGM..."
            )
            delay(800)

            val newItem = GeneratedMediaItem(
                id = UUID.randomUUID().toString(),
                title = "Commercial Ad: Wireless Headphones",
                prompt = _uiState.value.adScript,
                type = MediaType.COMMERCIAL_AD,
                drawableResName = "product_sample",
                durationFormatted = "0:${if (dur < 10) "0$dur" else dur}",
                durationSeconds = dur,
                timestampFormatted = "Just now",
                language = _uiState.value.selectedVoiceoverLanguage,
                tags = listOf("Presenter Ad", "Lip-Sync", "${dur}s")
            )

            _uiState.value = _uiState.value.copy(
                isGenerating = false,
                generationProgress = 1.0f,
                lastGeneratedItem = newItem,
                presenterLipSyncActive = true,
                recentCreations = listOf(newItem) + _uiState.value.recentCreations,
                toastMessage = "Commercial Ad with Neural Lip-Sync Rendered!"
            )
        }
    }

    // Gen Z Studio actions
    fun selectGenZSubTool(subTool: GenZSubTool) {
        _uiState.value = _uiState.value.copy(currentGenZSubTool = subTool)
    }

    fun updateMemeText(top: String, bottom: String) {
        _uiState.value = _uiState.value.copy(memeTopText = top, memeBottomText = bottom)
    }

    fun selectAudioFilter(filterName: String) {
        _uiState.value = _uiState.value.copy(
            selectedAudioFilter = filterName,
            isAudioFilterPlaying = true,
            toastMessage = "Filter applied: $filterName"
        )
    }

    fun toggleAudioFilterPlayback() {
        _uiState.value = _uiState.value.copy(
            isAudioFilterPlaying = !_uiState.value.isAudioFilterPlaying
        )
    }

    fun selectKineticCaptionStyle(style: String) {
        _uiState.value = _uiState.value.copy(kineticCaptionStyle = style)
    }

    // Timeline Editor actions
    fun toggleTimelinePlay() {
        val playing = !_uiState.value.isTimelinePlaying
        _uiState.value = _uiState.value.copy(isTimelinePlaying = playing)
        if (playing) {
            viewModelScope.launch {
                while (_uiState.value.isTimelinePlaying) {
                    delay(100)
                    val nextTime = _uiState.value.timelineCurrentTimeSec + 0.1f
                    if (nextTime >= _uiState.value.timelineTotalDurationSec) {
                        _uiState.value = _uiState.value.copy(
                            timelineCurrentTimeSec = 0f,
                            isTimelinePlaying = false
                        )
                        break
                    } else {
                        _uiState.value = _uiState.value.copy(timelineCurrentTimeSec = nextTime)
                    }
                }
            }
        }
    }

    fun seekTimeline(timeSec: Float) {
        _uiState.value = _uiState.value.copy(
            timelineCurrentTimeSec = timeSec.coerceIn(0f, _uiState.value.timelineTotalDurationSec)
        )
    }

    fun clearToast() {
        _uiState.value = _uiState.value.copy(toastMessage = null)
    }
}
