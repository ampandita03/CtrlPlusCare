package com.findmydoctor.ctrlpluscare.ui.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.findmydoctor.ctrlpluscare.domain.usecases.GetNotificationUseCase
import com.findmydoctor.ctrlpluscare.utils.LocalStorage
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(
    private val localStorage: LocalStorage,
    private val getNotificationUseCase: GetNotificationUseCase

) : ViewModel(){
    private val _unreadCount = MutableStateFlow(0)
    val unreadCount = _unreadCount.asStateFlow()

    init {
        startNotificationPolling()
    }

    private fun startNotificationPolling() {
        viewModelScope.launch {
            while (true) {
                fetchNotifications()
                delay(5_000) // ⏱️ every 5 seconds
            }
        }
    }

    private suspend fun fetchNotifications() {
        val result = getNotificationUseCase()

        result.onSuccess { response ->
            val unreadCount = response.data.count { !it.isRead }
            _unreadCount.value = unreadCount
        }.onFailure {
            // optional: log error
        }
    }

    val role: StateFlow<String> =
        localStorage.userRoleFlow.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            ""
        )


}