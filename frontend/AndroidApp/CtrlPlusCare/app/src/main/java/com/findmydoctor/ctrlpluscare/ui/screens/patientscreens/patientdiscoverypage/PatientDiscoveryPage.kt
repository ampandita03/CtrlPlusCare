package com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.patientdiscoverypage

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
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
import com.findmydoctor.ctrlpluscare.ui.resuablecomponents.TopAppBar
import com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.patienthomescreen.CustomSearchBar
import com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.patienthomescreen.PatientHomeScreenUiState
import com.findmydoctor.ctrlpluscare.ui.theme.EmergencyRed
import com.findmydoctor.ctrlpluscare.ui.theme.PrimaryBlue
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun PatientDiscoveryPage(navController: NavController,viewModel: PatientDiscoveryViewModel = koinViewModel()){

    val uiState by viewModel.uiState.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.getAllDoctors()
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = "Discovery"
            ) {
                navController.popBackStack()
            }
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(it).padding(horizontal = 10.dp)
        ) {
            when(uiState) {
                is PatientHomeScreenUiState.Error -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                        Text(
                            "Couldnt load doctors",
                            color = EmergencyRed
                        )
                    }
                }
                PatientHomeScreenUiState.Idle -> {}

                PatientHomeScreenUiState.Loading -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                        CircularProgressIndicator(
                            color = PrimaryBlue
                        )
                    }
                }
                is PatientHomeScreenUiState.NearByDoctorsLoaded -> {
                    val data = (uiState as PatientHomeScreenUiState.NearByDoctorsLoaded).doctors.data
                    val filteredData = data.filter { doctor->
                        doctor.name.contains(searchQuery.trim(), ignoreCase = true) ||
                                doctor.specialty.contains(searchQuery.trim(), ignoreCase = true)||
                                doctor.clinicAddress.contains(searchQuery.trim(), ignoreCase = true)
                    }

                    LazyColumn {
                        item{
                            Spacer(Modifier.height(20.dp))
                        }
                        item{
                            CustomSearchBar(
                                query = searchQuery,
                                onQueryChange = {
                                    searchQuery = it
                                },
                                onFilterClick = {

                                },
                                placeholder = "Search",
                                startPaddingValue = 5.dp,
                                endPaddingValues = 5.dp
                            )

                            Spacer(Modifier.height(10.dp))


                        }
                        items(filteredData) {
                            DoctorCard(doctor = it, onClick = {
                                viewModel.saveCurrentDoctor(it)
                                navController.navigate(AppRoute.DoctorInfoScreen.route)
                            })
                        }
                    }

                }
            }
        }
    }
}