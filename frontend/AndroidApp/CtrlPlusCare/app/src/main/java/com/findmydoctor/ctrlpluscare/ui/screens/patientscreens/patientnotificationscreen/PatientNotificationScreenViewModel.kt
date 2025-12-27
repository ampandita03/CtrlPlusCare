package com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.patientnotificationscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.findmydoctor.ctrlpluscare.domain.usecases.GetNotificationUseCase
import com.findmydoctor.ctrlpluscare.domain.usecases.MarkAllNotificationUseCase
import com.findmydoctor.ctrlpluscare.domain.usecases.MarkNotificationUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PatientNotificationScreenViewModel(
    private val getNotificationUseCase: GetNotificationUseCase,
    private val markNotificationUseCase: MarkNotificationUseCase,
    private val markAllNotificationUseCase: MarkAllNotificationUseCase
) : ViewModel(){

    private val _uiState = MutableStateFlow<NotificationScreenUiState>(NotificationScreenUiState.Loading)
    val uiState : StateFlow<NotificationScreenUiState> = _uiState

    private val _mark = MutableStateFlow< MarkNotificationUiState>(MarkNotificationUiState.Loading)
    val mark : StateFlow<MarkNotificationUiState> = _mark

    private val _markAll = MutableStateFlow< MarkAllNotificationUiState>(MarkAllNotificationUiState.Loading)
    val markAll : StateFlow<MarkAllNotificationUiState> = _markAll


    fun getAllNotifications(){
        _uiState.value = NotificationScreenUiState.Loading
        viewModelScope.launch {
            val result = getNotificationUseCase()

            result.onSuccess {
                _uiState.value = NotificationScreenUiState.Success(it)
            }
                .onFailure {
                    _uiState.value = NotificationScreenUiState.Error(it.message ?: "Unknown error")

                }
        }
    }

    fun markNotification(notificationId: String) {
        _mark.value = MarkNotificationUiState.Loading
        viewModelScope.launch {
            val result = markNotificationUseCase(notificationId)
            result.onSuccess {
                _mark.value = MarkNotificationUiState.Success

            }
                .onFailure {
                    _mark.value =
                        MarkNotificationUiState.Error(it.message ?: "Unknown error")

                }

        }
    }
    fun markAllNotification() {
        _mark.value = MarkNotificationUiState.Loading
        viewModelScope.launch {
            val result = markAllNotificationUseCase()
            result.onSuccess {
                _mark.value = MarkNotificationUiState.Success

            }
                .onFailure {
                    _mark.value =
                        MarkNotificationUiState.Error(it.message ?: "Unknown error")

                }

        }
    }
}