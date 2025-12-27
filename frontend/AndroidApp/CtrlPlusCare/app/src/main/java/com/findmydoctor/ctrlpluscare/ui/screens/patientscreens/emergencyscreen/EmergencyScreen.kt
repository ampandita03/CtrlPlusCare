package com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.emergencyscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.findmydoctor.ctrlpluscare.ui.navigation.AppRoute
import com.findmydoctor.ctrlpluscare.ui.resuablecomponents.DoctorCard
import com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.patienthomescreen.CustomSearchBar
import com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.patienthomescreen.PatientHomeScreenUiState
import com.findmydoctor.ctrlpluscare.ui.theme.BackgroundColor
import com.findmydoctor.ctrlpluscare.ui.theme.EmergencyRed
import com.findmydoctor.ctrlpluscare.ui.theme.PrimaryBlue
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun EmergencyScreen(navController: NavController,viewModel: EmergencyScreenViewModel = koinViewModel()){

    val uiState by viewModel.uiState.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.getNearbyDoctors()
    }

    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it).padding(horizontal = 10.dp)
        ) {
            Box(Modifier.fillMaxWidth().background(color = EmergencyRed, shape = RoundedCornerShape(15.dp)), contentAlignment = Alignment.Center){

                Text(
                    "Emergency Mode",
                    color = BackgroundColor,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(20.dp)
                )
            }

            Spacer(Modifier.height(15.dp))

            CustomSearchBar(
                query = searchQuery,
                onQueryChange = {
                    searchQuery = it
                },
                onFilterClick = {

                },
                placeholder = "Search",
                startPaddingValue = 0.dp,
                endPaddingValues = 0.dp
            )
            Spacer(modifier = Modifier.height(10.dp))

            LazyColumn {

                when(uiState){
                    is PatientHomeScreenUiState.Error -> {
                        item {
                            Text("Couldn't Load Doctors")
                        }
                    }
                    PatientHomeScreenUiState.Idle -> {

                    }
                    PatientHomeScreenUiState.Loading -> {
                        item {
                            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()){
                                CircularProgressIndicator(
                                    color = PrimaryBlue
                                )
                            }
                        }
                    }
                    is PatientHomeScreenUiState.NearByDoctorsLoaded -> {
                        val data = (uiState as PatientHomeScreenUiState.NearByDoctorsLoaded).doctors.data
                        val filteredData = data.filter { doctor->
                            doctor.name.contains(searchQuery.trim(), ignoreCase = true) ||
                                    doctor.specialty.contains(searchQuery.trim(), ignoreCase = true)||
                                    doctor.clinicAddress.contains(searchQuery.trim(), ignoreCase = true)
                        }
                        items(filteredData) {
                            DoctorCard(doctor = it, onClick = {
                                viewModel.saveCurrentDoctor(it)
                                navController.navigate(AppRoute.EmergencyDoctorInfoScreen.route)
                            })
                        }
                    }
                }

            }
        }
    }

}