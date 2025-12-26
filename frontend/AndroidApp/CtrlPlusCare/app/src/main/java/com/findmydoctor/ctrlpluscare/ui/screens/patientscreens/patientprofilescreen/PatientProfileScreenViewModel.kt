package com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.patientprofilescreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.findmydoctor.ctrlpluscare.utils.LocalStorage
import kotlinx.coroutines.launch

class PatientProfileScreenViewModel(
    private val localStorage: LocalStorage
) : ViewModel(){

    fun logOut(){
        viewModelScope.launch {
            localStorage.clearAll()
        }
    }

}