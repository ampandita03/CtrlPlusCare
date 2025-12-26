package com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.doctorinfoscreen

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CurrencyRupee
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontSynthesis
import androidx.compose.ui.text.font.FontWeight.Companion.W300
import androidx.compose.ui.text.font.FontWeight.Companion.W400
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.size.Scale
import com.findmydoctor.ctrlpluscare.ui.resuablecomponents.TopAppBar
import com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.patienthomescreen.DoctorCard
import org.koin.compose.viewmodel.koinViewModel
import androidx.core.net.toUri
import com.findmydoctor.ctrlpluscare.ui.theme.BackgroundColor
import com.findmydoctor.ctrlpluscare.ui.theme.PrimaryBlue
import com.findmydoctor.ctrlpluscare.ui.theme.TextDisabled
import com.findmydoctor.ctrlpluscare.ui.theme.TextPrimary

@Composable
fun DoctorInfoScreen(
    navController: NavController,
    viewModel: DoctorInfoScreenViewModel = koinViewModel()
){

    val context = LocalContext.current

    val uiState by viewModel.uiState.collectAsState()
    val doctor by viewModel.doctor.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getCurrentDoctor()
        viewModel.getAvailableSlots("2025-12-27")
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = "Doctor Detail"
            ) {
                navController.popBackStack()
            }
        }
    ) { innerPadding->

        when(doctor){
            CurrentDoctorUiState.Loading -> {

            }
            is CurrentDoctorUiState.Success -> {
                val data = (doctor as CurrentDoctorUiState.Success).doctor
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding).padding(start = 10.dp, end = 10.dp)
                ) {
                    DoctorCard(
                        doctor = data ,
                        onClick = {

                        },
                        border = false,
                        directions = true,
                        onDirectionClick = {
                            val latitude = data.clinicLocation.coordinates[1]
                            val longitude = data.clinicLocation.coordinates[0]
                            val uri = "geo:$latitude,$longitude?q=$latitude,$longitude".toUri()
                            val intent = Intent(Intent.ACTION_VIEW, uri)
                            intent.setPackage("com.google.android.apps.maps")

                            context.startActivity(intent)
                        },
                        isClickable = false
                    )
                    Text(
                        text = "About",
                        color = TextPrimary,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = W500,
                            fontSize = 19.5.sp
                        )
                    )
                    Text(
                        text = "Dr. Rishi has done his Degree from E.S.I.C. Medical College and have been practicing for 20+ years. In his career he has seen 50K Patients and multiple operations.",
                        color = TextDisabled,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = W400,
                            textAlign = TextAlign.Justify,
                            fontSize = 15.sp
                        )
                    )
                    Spacer(Modifier.height(20.dp))
                    ConsultationFeeCard(data.consultationFee)
                    when(uiState) {
                        DoctorInfoScreenUiStates.Idle -> {

                        }
                        DoctorInfoScreenUiStates.Loading -> {

                        }
                        is DoctorInfoScreenUiStates.Success -> {
                            val slots = (uiState as DoctorInfoScreenUiStates.Success).data

                            Text(
                                "${slots.data}"
                            )
                        }
                        is DoctorInfoScreenUiStates.Error -> {

                        }
                    }
                }
            }
        }


    }

}


@Composable
fun ConsultationFeeCard(
    fees : Int
){
    Row(
        Modifier
            .background(
                color = PrimaryBlue.copy(0.1f),
                shape = RoundedCornerShape(10.dp)
            )
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 10.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.CurrencyRupee,
                contentDescription = "Rupee",
                tint = BackgroundColor,
                modifier = Modifier.background(
                    color = PrimaryBlue,
                    shape = RoundedCornerShape(5.dp)
                )
                    .size(50.dp)
            )
            Column {
                Text(
                    text = "Consultation fee",
                    color = TextPrimary.copy(0.9f),
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = W300,
                        fontSize = 18.5.sp
                    )
                )
                Text(
                    text = "Rs. $fees per appointment",
                    color = TextPrimary,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = W500,
                        fontSize = 19.sp
                    )
                )
            }
        }

    }
}