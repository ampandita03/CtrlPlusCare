package com.findmydoctor.ctrlpluscare.ui.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.findmydoctor.ctrlpluscare.utils.LocalStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val localStorage: LocalStorage
) : ViewModel(){
    private val _role = MutableStateFlow("")
    val role: StateFlow<String> = _role



    fun getRole(){
        viewModelScope.launch {
            _role.value = localStorage.getUserRole() ?:""
        }
    }
}