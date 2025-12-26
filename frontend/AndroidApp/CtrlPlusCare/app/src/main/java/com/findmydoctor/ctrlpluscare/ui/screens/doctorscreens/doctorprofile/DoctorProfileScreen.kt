package com.findmydoctor.ctrlpluscare.ui.screens.doctorscreens.doctorprofile

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.findmydoctor.ctrlpluscare.ui.navigation.AppRoute
import com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.patientprofilescreen.PatientProfileScreenViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun DoctorProfileScreen(navController: NavController,viewModel: DoctorProfileScreenViewModel = koinViewModel()){
    Button(
        onClick = {
            viewModel.logOut()
            navController.navigate(AppRoute.Welcome.route){
                popUpTo(0){inclusive = true}
            }
        }
    ) {
        Text("LogOut")
    }
}