package com.findmydoctor.ctrlpluscare.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.findmydoctor.ctrlpluscare.localstorage.LocalStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TestViewModel(
    private val localStorage: LocalStorage
): ViewModel() {
    private val _token = MutableStateFlow("")
    val token : StateFlow<String> = _token

    fun getToken(){
        viewModelScope.launch {
            _token.value = localStorage.getToken() ?: ""
        }
    }
    fun saveToken(tokenTest: String){
        viewModelScope.launch {
            localStorage.saveToken(tokenTest)
        }
    }
}

