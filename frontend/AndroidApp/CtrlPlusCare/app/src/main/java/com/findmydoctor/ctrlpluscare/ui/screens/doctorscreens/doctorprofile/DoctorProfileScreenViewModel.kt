package com.findmydoctor.ctrlpluscare.ui.screens.doctorscreens.doctorprofile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.findmydoctor.ctrlpluscare.utils.LocalStorage
import kotlinx.coroutines.launch

class DoctorProfileScreenViewModel(
    private val localStorage: LocalStorage
) : ViewModel(){

    fun logOut(){
        viewModelScope.launch {
            localStorage.clearAll()
        }
    }

}