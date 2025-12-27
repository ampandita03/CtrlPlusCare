package com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.emergencyscreen.emergencybookingconfirmscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.findmydoctor.ctrlpluscare.R
import com.findmydoctor.ctrlpluscare.ui.navigation.AppRoute
import com.findmydoctor.ctrlpluscare.ui.resuablecomponents.CommonRoundCornersButton
import com.findmydoctor.ctrlpluscare.ui.resuablecomponents.TopAppBar
import com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.bookingconfirmed.AppointmentDetailsCard
import com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.bookingscreen.CurrentBookingUiState
import com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.doctorinfoscreen.CurrentDoctorUiState
import com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.emergencyscreen.emergencydoctorinfoscreen.EmergencyDoctorInfoUiState
import com.findmydoctor.ctrlpluscare.ui.theme.PrimaryBlue
import org.koin.compose.viewmodel.koinViewModel
@Composable
fun EmergencyBookingConfirmScreen(
    navController: NavHostController,
    viewModel: EmergencyBookingConfirmViewModel = koinViewModel()
) {
    val bookingState by viewModel.booking.collectAsState()
    val doctorState by viewModel.doctor.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getBookingData()
        viewModel.getCurrentDoctor()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = "Booking Confirmation"
            ) {
                navController.popBackStack()
            }
        },
        containerColor = Color.White
    ) { innerPadding ->

        when {
            doctorState is CurrentDoctorUiState.Success &&
                    bookingState is EmergencyDoctorInfoUiState.Success -> {

                val doctor =
                    (doctorState as CurrentDoctorUiState.Success).doctor

                val bookingResponse =
                    (bookingState as EmergencyDoctorInfoUiState.Success).data

                val booking = bookingResponse.data

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(horizontal = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Spacer(modifier = Modifier.height(60.dp))

                    Image(
                        painter = painterResource(id = R.drawable.tickmark),
                        contentDescription = "Success",
                        modifier = Modifier.size(180.dp)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "Booking",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                        fontSize = 32.sp
                    )
                    Text(
                        text = "Confirmed!",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                        fontSize = 32.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Your emergency appointment has been",
                        fontSize = 14.sp
                    )
                    Text(
                        text = "successfully booked",
                        fontSize = 14.sp
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    AppointmentDetailsCard(
                        doctor = doctor,
                        date = booking.date,
                        time = booking.startTime
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    CommonRoundCornersButton(
                        text = "Homepage",
                        tint = PrimaryBlue,
                        paddingValues = 0.dp
                    ) {
                        navController.navigate(AppRoute.PatientHomeScreen.route) {
                            popUpTo(0) { inclusive = true }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                }
            }

            bookingState is EmergencyDoctorInfoUiState.Error -> {
                Column {
                    Spacer(modifier = Modifier.height(60.dp))

                    Image(
                        painter = painterResource(id = R.drawable.tickmark),
                        contentDescription = "Success",
                        modifier = Modifier.size(180.dp)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "Booking",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                        fontSize = 32.sp
                    )
                    Text(
                        text = "Confirmed!",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                        fontSize = 32.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Your emergency appointment has been",
                        fontSize = 14.sp
                    )
                    Text(
                        text = "successfully booked",
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.weight(1f))

                    CommonRoundCornersButton(
                        text = "Homepage",
                        tint = PrimaryBlue,
                        paddingValues = 0.dp
                    ) {
                        navController.navigate(AppRoute.PatientHomeScreen.route) {
                            popUpTo(0) { inclusive = true }
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))


                }
            }

            else -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}
