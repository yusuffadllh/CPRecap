package com.yusuffdllh.smartfinance.presentation.setting.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yusuffdllh.smartfinance.data.local.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val userPreferences: UserPreferences,
    @dagger.hilt.android.qualifiers.ApplicationContext private val context: android.content.Context
) : ViewModel() {

    private val _isPermissionGranted = MutableStateFlow(false)
    val isPermissionGranted: StateFlow<Boolean> = _isPermissionGranted.asStateFlow()

    val isGoogleLinked: StateFlow<Boolean> = userPreferences.gmailAuthorized
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    private val _linkState = MutableStateFlow<GmailLinkState>(GmailLinkState.Idle)
    val linkState: StateFlow<GmailLinkState> = _linkState.asStateFlow()

    init {
        checkPermission()
    }

    fun checkPermission() {
        val enabledPackages = androidx.core.app.NotificationManagerCompat.getEnabledListenerPackages(context)
        _isPermissionGranted.value = enabledPackages.contains(context.packageName)
    }

    fun setGmailLinking() {
        _linkState.value = GmailLinkState.Loading
    }

    fun onGmailAuthorized(accountName: String) {
        viewModelScope.launch {
            userPreferences.setGmailAuthorized(true, accountName)
            userPreferences.setEmailSyncEnabled(true)
            _linkState.value = GmailLinkState.Success
        }
    }

    fun onGmailAuthorizationFailed(message: String) {
        _linkState.value = GmailLinkState.Error(message)
    }

    fun consumeLinkState() {
        _linkState.value = GmailLinkState.Idle
    }

    val bankReaderEnabled = userPreferences.bankReaderEnabled
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    val emailSyncEnabled = userPreferences.emailSyncEnabled
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    val budgetWarningEnabled = userPreferences.budgetWarningEnabled
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)

    val minRp = userPreferences.minDailyRp
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "")

    val maxRp = userPreferences.maxDailyRp
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "")

    val lastDetectedPackage = userPreferences.lastDetectedPackage
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "None")

    // ---- AI (Gemini-compatible) configuration ----
    val aiBaseUrl = userPreferences.aiBaseUrl
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UserPreferences.DEFAULT_AI_BASE_URL)

    val aiApiKey = userPreferences.geminiApiKey
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "")

    val aiModel = userPreferences.aiModel
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UserPreferences.DEFAULT_AI_MODEL)

    private val _aiConfigSaved = MutableStateFlow(false)
    val aiConfigSaved: StateFlow<Boolean> = _aiConfigSaved.asStateFlow()

    fun saveAiConfig(baseUrl: String, apiKey: String, model: String) {
        viewModelScope.launch {
            userPreferences.saveAiConfig(baseUrl, apiKey, model)
            _aiConfigSaved.value = true
        }
    }

    fun consumeAiConfigSaved() {
        _aiConfigSaved.value = false
    }

    fun setBankReader(enabled: Boolean) {
        viewModelScope.launch { userPreferences.setBankReaderEnabled(enabled) }
    }

    fun setEmailSync(enabled: Boolean) {
        viewModelScope.launch { userPreferences.setEmailSyncEnabled(enabled) }
    }

    fun setBudgetWarning(enabled: Boolean) {
        viewModelScope.launch { userPreferences.setBudgetWarningEnabled(enabled) }
    }

    fun setRange(min: String, max: String) {
        viewModelScope.launch { userPreferences.setDailyRange(min, max) }
    }

    // ---- Manual Gmail sync ("Sinkronkan Sekarang") ----
    private val _gmailSyncState = MutableStateFlow<GmailSyncState>(GmailSyncState.Idle)
    val gmailSyncState: StateFlow<GmailSyncState> = _gmailSyncState.asStateFlow()

    fun syncGmailNow() {
        _gmailSyncState.value = GmailSyncState.Running
        val workName = com.yusuffdllh.smartfinance.service.WorkManagerHelper.syncGmailNow(context)

        viewModelScope.launch {
            androidx.work.WorkManager.getInstance(context)
                .getWorkInfosForUniqueWorkFlow(workName)
                .collect { infos ->
                    val info = infos.firstOrNull() ?: return@collect
                    when (info.state) {
                        androidx.work.WorkInfo.State.SUCCEEDED ->
                            _gmailSyncState.value = GmailSyncState.Success
                        androidx.work.WorkInfo.State.FAILED,
                        androidx.work.WorkInfo.State.CANCELLED ->
                            _gmailSyncState.value = GmailSyncState.Error
                        else -> {}
                    }
                }
        }
    }

    fun consumeGmailSyncState() {
        _gmailSyncState.value = GmailSyncState.Idle
    }
}

sealed interface GmailLinkState {
    data object Idle : GmailLinkState
    data object Loading : GmailLinkState
    data object Success : GmailLinkState
    data class Error(val message: String) : GmailLinkState
}

sealed interface GmailSyncState {
    data object Idle : GmailSyncState
    data object Running : GmailSyncState
    data object Success : GmailSyncState
    data object Error : GmailSyncState
}
