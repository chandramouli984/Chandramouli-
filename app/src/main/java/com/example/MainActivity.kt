package com.example

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.VideoCameraFront
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.model.LocalizedStrings
import com.example.model.StudioModule
import com.example.ui.components.AdminBankModal
import com.example.ui.components.MultiModalSearchBar
import com.example.ui.components.PaywallModal
import com.example.ui.components.ReferralShareModal
import com.example.ui.components.SupportModal
import com.example.ui.components.TopHeaderBanner
import com.example.ui.components.tapOutsideToDismiss
import com.example.ui.screens.AdminScreen
import com.example.ui.screens.GenZStudioScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.ImageToVideoScreen
import com.example.ui.screens.ProductPresenterScreen
import com.example.ui.screens.TextToImageScreen
import com.example.ui.screens.TextToVideoScreen
import com.example.ui.screens.TimelineEditorScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.StudioAmber
import com.example.ui.theme.StudioBackground
import com.example.ui.theme.StudioBorder
import com.example.ui.theme.StudioCard
import com.example.ui.theme.StudioPrimary
import com.example.ui.theme.StudioSecondary
import com.example.ui.theme.StudioTertiary
import com.example.viewmodel.StudioViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                StudioAppMain()
            }
        }
    }
}

@Composable
fun StudioAppMain(
    viewModel: StudioViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.toastMessage) {
        uiState.toastMessage?.let { msg ->
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
            viewModel.clearToast()
        }
    }

    // BackHandler: navigate back to Home if inside a sub-module
    BackHandler(enabled = uiState.currentModule != StudioModule.HOME) {
        viewModel.navigateToModule(StudioModule.HOME)
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(StudioBackground)
            .tapOutsideToDismiss(focusManager),
        contentWindowInsets = WindowInsets.safeDrawing,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            NavigationBar(
                modifier = Modifier
                    .windowInsetsPadding(WindowInsets.navigationBars)
                    .testTag("studio_bottom_navigation"),
                containerColor = StudioCard,
                tonalElevation = 8.dp
            ) {
                NavigationBarItem(
                    selected = uiState.currentModule == StudioModule.HOME,
                    onClick = { viewModel.navigateToModule(StudioModule.HOME) },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = StudioPrimary,
                        selectedTextColor = StudioPrimary,
                        indicatorColor = StudioPrimary.copy(alpha = 0.2f),
                        unselectedIconColor = Color(0xFF94A3B8),
                        unselectedTextColor = Color(0xFF94A3B8)
                    )
                )

                NavigationBarItem(
                    selected = uiState.currentModule == StudioModule.TEXT_TO_IMAGE,
                    onClick = { viewModel.navigateToModule(StudioModule.TEXT_TO_IMAGE) },
                    icon = { Icon(Icons.Default.Image, contentDescription = "Image") },
                    label = { Text("Image") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = StudioSecondary,
                        selectedTextColor = StudioSecondary,
                        indicatorColor = StudioSecondary.copy(alpha = 0.2f),
                        unselectedIconColor = Color(0xFF94A3B8),
                        unselectedTextColor = Color(0xFF94A3B8)
                    )
                )

                NavigationBarItem(
                    selected = uiState.currentModule == StudioModule.TEXT_TO_VIDEO,
                    onClick = { viewModel.navigateToModule(StudioModule.TEXT_TO_VIDEO) },
                    icon = { Icon(Icons.Default.Movie, contentDescription = "Video") },
                    label = { Text("Video") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = StudioAmber,
                        selectedTextColor = StudioAmber,
                        indicatorColor = StudioAmber.copy(alpha = 0.2f),
                        unselectedIconColor = Color(0xFF94A3B8),
                        unselectedTextColor = Color(0xFF94A3B8)
                    )
                )

                NavigationBarItem(
                    selected = uiState.currentModule == StudioModule.PRODUCT_PRESENTER,
                    onClick = { viewModel.navigateToModule(StudioModule.PRODUCT_PRESENTER) },
                    icon = { Icon(Icons.Default.VideoCameraFront, contentDescription = "Presenter") },
                    label = { Text("Presenter") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = StudioTertiary,
                        selectedTextColor = StudioTertiary,
                        indicatorColor = StudioTertiary.copy(alpha = 0.2f),
                        unselectedIconColor = Color(0xFF94A3B8),
                        unselectedTextColor = Color(0xFF94A3B8)
                    )
                )

                NavigationBarItem(
                    selected = uiState.currentModule == StudioModule.GENZ_STUDIO,
                    onClick = { viewModel.navigateToModule(StudioModule.GENZ_STUDIO) },
                    icon = { Icon(Icons.Default.AutoAwesome, contentDescription = "Gen Z") },
                    label = { Text("Gen Z") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = StudioTertiary,
                        selectedTextColor = StudioTertiary,
                        indicatorColor = StudioTertiary.copy(alpha = 0.2f),
                        unselectedIconColor = Color(0xFF94A3B8),
                        unselectedTextColor = Color(0xFF94A3B8)
                    )
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(StudioBackground)
        ) {
            // Header Status Banner (Trial remaining / Pro status, Language Switcher, Referral, Support, and Admin)
            TopHeaderBanner(
                subscription = uiState.subscription,
                currentLanguage = uiState.currentLanguage,
                userProfile = uiState.userProfile,
                onOpenPaywall = { viewModel.openPaywall() },
                onLanguageSelected = { viewModel.setLanguage(it) },
                onOpenReferral = { viewModel.openReferralDialog() },
                onOpenSupport = { viewModel.openSupportDialog() },
                onOpenAdminBank = { viewModel.navigateToModule(StudioModule.ADMIN) }
            )

            // Multi-Modal Universal Search Bar (Text, Image, Video attachments)
            MultiModalSearchBar(
                query = uiState.searchQuery,
                onQueryChange = { viewModel.updateSearchQuery(it) },
                attachmentState = uiState.searchAttachment,
                onAttachImage = { viewModel.attachSearchImage() },
                onAttachVideo = { viewModel.attachSearchVideo() },
                onClearAttachment = { viewModel.clearSearchAttachment() },
                onSearchSubmit = { viewModel.submitSearch(it) },
                currentLanguage = uiState.currentLanguage
            )

            // Dynamic Module Content
            Box(modifier = Modifier.weight(1f)) {
                when (uiState.currentModule) {
                    StudioModule.HOME -> {
                        HomeScreen(
                            currentLanguage = uiState.currentLanguage,
                            recentCreations = uiState.recentCreations,
                            onNavigateToModule = { viewModel.navigateToModule(it) },
                            onShareItem = { item ->
                                val sendIntent = Intent().apply {
                                    action = Intent.ACTION_SEND
                                    putExtra(Intent.EXTRA_TEXT, "Created with AI Media & Audio Studio Pro: ${item.title}\n${item.prompt}")
                                    type = "text/plain"
                                }
                                context.startActivity(Intent.createChooser(sendIntent, "Share Studio Creation"))
                            }
                        )
                    }

                    StudioModule.TEXT_TO_IMAGE -> {
                        TextToImageScreen(
                            prompt = uiState.imagePrompt,
                            onPromptChange = { viewModel.updateImagePrompt(it) },
                            selectedPreset = uiState.selectedStylePreset,
                            onSelectPreset = { viewModel.selectImageStylePreset(it) },
                            selectedAspectRatio = uiState.selectedAspectRatio,
                            onSelectAspectRatio = { viewModel.selectAspectRatio(it) },
                            onEnhancePrompt = { viewModel.enhanceImagePrompt() },
                            onGenerate = { viewModel.generateTextToImage() },
                            isGenerating = uiState.isGenerating,
                            progress = uiState.generationProgress,
                            statusMessage = uiState.generationStatusMessage,
                            lastGeneratedItem = uiState.lastGeneratedItem,
                            currentLanguage = uiState.currentLanguage,
                            onSendToImageToVideo = { viewModel.navigateToModule(StudioModule.IMAGE_TO_VIDEO) }
                        )
                    }

                    StudioModule.TEXT_TO_VIDEO -> {
                        TextToVideoScreen(
                            prompt = uiState.videoPrompt,
                            onPromptChange = { viewModel.updateVideoPrompt(it) },
                            selectedDurationSeconds = uiState.videoDurationSeconds,
                            onSelectDuration = { viewModel.setVideoDuration(it) },
                            onGenerate = { viewModel.generateTextToVideo() },
                            isGenerating = uiState.isGenerating,
                            progress = uiState.generationProgress,
                            statusMessage = uiState.generationStatusMessage,
                            lastGeneratedItem = uiState.lastGeneratedItem,
                            currentLanguage = uiState.currentLanguage,
                            onShare = { item ->
                                val sendIntent = Intent().apply {
                                    action = Intent.ACTION_SEND
                                    putExtra(Intent.EXTRA_TEXT, "Check out this AI Video: ${item.title}")
                                    type = "text/plain"
                                }
                                context.startActivity(Intent.createChooser(sendIntent, "Share AI Video"))
                            }
                        )
                    }

                    StudioModule.IMAGE_TO_VIDEO -> {
                        ImageToVideoScreen(
                            sourceImageName = uiState.sourceImageName,
                            selectedMotionPreset = uiState.selectedMotionPreset,
                            motionIntensity = uiState.motionIntensity,
                            onSelectMotionPreset = { viewModel.selectMotionPreset(it) },
                            onMotionIntensityChange = { viewModel.setMotionIntensity(it) },
                            onGenerate = { viewModel.generateImageToVideo() },
                            isGenerating = uiState.isGenerating,
                            progress = uiState.generationProgress,
                            statusMessage = uiState.generationStatusMessage,
                            lastGeneratedItem = uiState.lastGeneratedItem,
                            currentLanguage = uiState.currentLanguage
                        )
                    }

                    StudioModule.TIMELINE_EDITOR -> {
                        TimelineEditorScreen(
                            tracks = uiState.timelineTracks,
                            currentTimeSec = uiState.timelineCurrentTimeSec,
                            totalDurationSec = uiState.timelineTotalDurationSec,
                            isPlaying = uiState.isTimelinePlaying,
                            onTogglePlay = { viewModel.toggleTimelinePlay() },
                            onSeek = { viewModel.seekTimeline(it) },
                            currentLanguage = uiState.currentLanguage,
                            onExportTimeline = {
                                Toast.makeText(context, "Exporting 1080p Master Video...", Toast.LENGTH_SHORT).show()
                            }
                        )
                    }

                    StudioModule.PRODUCT_PRESENTER -> {
                        ProductPresenterScreen(
                            presenterPhotoName = uiState.presenterPhotoName,
                            productPhotoName = uiState.productPhotoName,
                            adScript = uiState.adScript,
                            onAdScriptChange = { viewModel.updateAdScript(it) },
                            isAutoDuration = uiState.isAutoDurationMode,
                            onToggleAutoDuration = { viewModel.toggleAutoDuration(it) },
                            calculatedAutoDuration = viewModel.calculatedAutoDurationSeconds,
                            manualDurationSeconds = uiState.manualDurationSeconds,
                            onManualDurationChange = { viewModel.setManualDuration(it) },
                            selectedVoiceoverLanguage = uiState.selectedVoiceoverLanguage,
                            onSelectVoiceoverLanguage = { viewModel.setVoiceoverLanguage(it) },
                            onAutoGenerateScript = { viewModel.autoGenerateProductScript() },
                            onGenerate = { viewModel.generateProductPresenterAd() },
                            isGenerating = uiState.isGenerating,
                            progress = uiState.generationProgress,
                            statusMessage = uiState.generationStatusMessage,
                            lastGeneratedItem = uiState.lastGeneratedItem,
                            currentLanguage = uiState.currentLanguage,
                            onShare = { item ->
                                val sendIntent = Intent().apply {
                                    action = Intent.ACTION_SEND
                                    putExtra(Intent.EXTRA_TEXT, "Commercial Ad: ${item.title}")
                                    type = "text/plain"
                                }
                                context.startActivity(Intent.createChooser(sendIntent, "Share Ad Video"))
                            }
                        )
                    }

                    StudioModule.GENZ_STUDIO -> {
                        GenZStudioScreen(
                            currentSubTool = uiState.currentGenZSubTool,
                            onSelectSubTool = { viewModel.selectGenZSubTool(it) },
                            memeTopText = uiState.memeTopText,
                            memeBottomText = uiState.memeBottomText,
                            onUpdateMemeText = { top, bottom -> viewModel.updateMemeText(top, bottom) },
                            selectedAudioFilter = uiState.selectedAudioFilter,
                            onSelectAudioFilter = { viewModel.selectAudioFilter(it) },
                            isAudioPlaying = uiState.isAudioFilterPlaying,
                            onToggleAudioPlayback = { viewModel.toggleAudioFilterPlayback() },
                            selectedCaptionStyle = uiState.kineticCaptionStyle,
                            onSelectCaptionStyle = { viewModel.selectKineticCaptionStyle(it) },
                            currentLanguage = uiState.currentLanguage,
                            onTriggerSocialExport = { platformName ->
                                val sendIntent = Intent().apply {
                                    action = Intent.ACTION_SEND
                                    putExtra(Intent.EXTRA_TEXT, "Exported 9:16 Vertical Video for $platformName!")
                                    type = "text/plain"
                                }
                                context.startActivity(Intent.createChooser(sendIntent, "Share 9:16 Video"))
                            }
                        )
                    }

                    StudioModule.ADMIN -> {
                        AdminScreen(
                            bankSettings = uiState.bankPayoutSettings,
                            promoCodes = uiState.promoCodesList,
                            onSaveBankSettings = { viewModel.updateBankPayoutSettings(it) },
                            onGenerateAlphanumericCode = { prefix, length -> viewModel.generateRandomAlphanumericCode(prefix, length) },
                            onCreatePromoCode = { code, discount, days, desc -> viewModel.createManualPromoCode(code, discount, days, desc) },
                            onTogglePromoCode = { viewModel.togglePromoCodeActive(it) },
                            onDeletePromoCode = { viewModel.deletePromoCode(it) },
                            onVerifyPaymentConnection = { viewModel.testVerifyPaymentConnection() },
                            onNavigateBack = { viewModel.navigateToModule(StudioModule.HOME) }
                        )
                    }
                }
            }
        }

        // Paywall Dialog
        if (uiState.showPaywall) {
            PaywallModal(
                subscription = uiState.subscription,
                currentLanguage = uiState.currentLanguage,
                onDismiss = { viewModel.closePaywall() },
                onSubscribeSuccess = { viewModel.activateProSubscription() },
                onApplyCode = { viewModel.applyReferralOrPromoCode(it) }
            )
        }

        // Referral & 10-Day Free Sharing Dialog
        if (uiState.showReferralDialog) {
            ReferralShareModal(
                referralState = uiState.referralSystem,
                subscription = uiState.subscription,
                onDismiss = { viewModel.closeReferralDialog() },
                onSimulateFriendSignup = { viewModel.simulateFriendSignup() },
                onApplyCode = { viewModel.applyReferralOrPromoCode(it) }
            )
        }

        // Customer Support Dialog (Primary email: rajmittal012340@gmail.com)
        if (uiState.showSupportDialog) {
            SupportModal(
                onDismiss = { viewModel.closeSupportDialog() }
            )
        }

        // Owner Admin Dialog (Bank Payout & Manual Promo Codes)
        if (uiState.showAdminBankDialog) {
            AdminBankModal(
                bankSettings = uiState.bankPayoutSettings,
                promoCodes = uiState.promoCodesList,
                onDismiss = { viewModel.closeAdminBankDialog() },
                onSaveBankSettings = { viewModel.updateBankPayoutSettings(it) },
                onCreatePromoCode = { code, disc, days, desc -> viewModel.createManualPromoCode(code, disc, days, desc) },
                onTogglePromoCode = { viewModel.togglePromoCodeActive(it) }
            )
        }
    }
}
