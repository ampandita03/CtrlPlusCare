package com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.emergencyscreen.emergencydoctorinfoscreen

import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight.Companion.W400
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.navigation.NavController
import com.findmydoctor.ctrlpluscare.data.dto.EmergencyBookingRequest
import com.findmydoctor.ctrlpluscare.ui.navigation.AppRoute
import com.findmydoctor.ctrlpluscare.ui.resuablecomponents.CommonRoundCornersButton
import com.findmydoctor.ctrlpluscare.ui.resuablecomponents.ConsultationFeeCard
import com.findmydoctor.ctrlpluscare.ui.resuablecomponents.DoctorCard
import com.findmydoctor.ctrlpluscare.ui.resuablecomponents.TopAppBar
import com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.doctorinfoscreen.CurrentDoctorUiState
import com.findmydoctor.ctrlpluscare.ui.theme.EmergencyRed
import com.findmydoctor.ctrlpluscare.ui.theme.PrimaryBlue
import com.findmydoctor.ctrlpluscare.ui.theme.TextDisabled
import com.findmydoctor.ctrlpluscare.ui.theme.TextPrimary
import org.koin.compose.viewmodel.koinViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EmergencyDoctorInfoScreen(
    navController: NavController,
    viewModel: EmergencyDoctorInfoViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val doctor by viewModel.doctor.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getCurrentDoctor()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = "Emergency Consultation"
            ) {
                navController.popBackStack()
            }
        }
    ) { innerPadding ->
        when (doctor) {

            CurrentDoctorUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = PrimaryBlue
                    )
                }
            }

            is CurrentDoctorUiState.Success -> {
                val data = (doctor as CurrentDoctorUiState.Success).doctor

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(horizontal = 16.dp)
                        .verticalScroll(rememberScrollState())
                ) {

                    // 🔹 Doctor Card
                    DoctorCard(
                        doctor = data,
                        onClick = {},
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

                    Spacer(Modifier.height(12.dp))

                    // 🔹 About
                    Text(
                        text = "About",
                        color = TextPrimary,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = W500,
                            fontSize = 19.5.sp
                        )
                    )

                    Spacer(Modifier.height(8.dp))

                    Text(
                        text = data.about,
                        color = TextDisabled,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = W400,
                            textAlign = TextAlign.Justify,
                            fontSize = 14.sp,
                            lineHeight = 20.sp
                        )
                    )

                    Spacer(Modifier.height(16.dp))

                    // 🔹 Fee Card (Emergency Fee preferred if available)
                    ConsultationFeeCard(
                        fees = data.consultationFee,
                        emergenceFees = data.emergencyFee,
                        isEmergency = true
                    )

                    Spacer(Modifier.weight(1f))

                    // 🔥 Emergency Book Button
                    CommonRoundCornersButton(
                        text = "Book Emergency Appointment",
                        tint = EmergencyRed,
                        paddingValues = 0.dp
                    ) {
                        viewModel.bookEmergencyAppointment(
                            emergencyBookingRequest = EmergencyBookingRequest(
                                doctorId = data._id,
                                date = getTodayDate(),
                            )
                        )
                        navController.navigate(AppRoute.EmergencyBookingConfirmScreen.route)
                    }

                    Spacer(Modifier.height(12.dp))
                }
            }

            else -> Unit
        }
    }
}
@RequiresApi(Build.VERSION_CODES.O)
fun getTodayDate(): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    return LocalDate.now().format(formatter)
}