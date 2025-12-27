package com.findmydoctor.ctrlpluscare.ui.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.findmydoctor.ctrlpluscare.utils.LocalStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(
    private val localStorage: LocalStorage
) : ViewModel(){
    val role: StateFlow<String> =
        localStorage.userRoleFlow.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            ""
        )


}