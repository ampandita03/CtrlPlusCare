package com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.bookingscreen

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavHostController
import com.findmydoctor.ctrlpluscare.R
import com.findmydoctor.ctrlpluscare.ui.navigation.AppRoute
import com.findmydoctor.ctrlpluscare.ui.resuablecomponents.CommonRoundCornersButton
import com.findmydoctor.ctrlpluscare.ui.resuablecomponents.ConsultationFeeCard
import com.findmydoctor.ctrlpluscare.ui.resuablecomponents.DoctorCard
import com.findmydoctor.ctrlpluscare.ui.resuablecomponents.TopAppBar
import com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.doctorinfoscreen.CurrentDoctorUiState
import com.findmydoctor.ctrlpluscare.ui.theme.PrimaryBlue
import org.koin.compose.viewmodel.koinViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun BookingScreen(
    navController: NavHostController,
    viewModel: BookingScreenViewModel = koinViewModel()
) {
    val booking by viewModel.booking.collectAsState()
    val doctor by viewModel.doctor.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    var reasonForVisit by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.getBookingData()
        viewModel.getCurrentDoctor()
    }

    val context = LocalContext.current

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = "Appointment Booking"
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
                    CircularProgressIndicator()
                }
            }
            is CurrentDoctorUiState.Success -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(horizontal = 16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    val doctorData = (doctor as CurrentDoctorUiState.Success).doctor
                    val bookingData = (booking as? CurrentBookingUiState.Success)?.data

                    Spacer(modifier = Modifier.height(16.dp))

                    // Doctor Card
                    DoctorCard(
                        doctor = doctorData,
                        onClick = {},
                        border = false,
                        directions = true,
                        onDirectionClick = {
                            val latitude = doctorData.clinicLocation.coordinates[1]
                            val longitude = doctorData.clinicLocation.coordinates[0]
                            val uri = "geo:$latitude,$longitude?q=$latitude,$longitude".toUri()
                            val intent = Intent(Intent.ACTION_VIEW, uri)
                            intent.setPackage("com.google.android.apps.maps")
                            context.startActivity(intent)
                        },
                        isClickable = false
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Consultation Fee Card
                    ConsultationFeeCard(
                        fees = doctorData.consultationFee
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Date and Time Card
                    if (bookingData != null) {
                        DateTimeCard(
                            date = bookingData.date,
                            time = bookingData.startTime
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Reason for visit
                    Text(
                        text = "Reason for visit (Optional)",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = reasonForVisit,
                        onValueChange = { reasonForVisit = it },
                        placeholder = {
                            Text(
                                text = "Describe your symptoms or reason for consultation...",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.LightGray,
                            unfocusedBorderColor = Color.LightGray,
                            focusedContainerColor = Color(0xFFF5F5F5),
                            unfocusedContainerColor = Color(0xFFF5F5F5)
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )

                    Spacer(modifier = Modifier.height(85.dp))

                    // Book Appointment Button
                    CommonRoundCornersButton(
                        text = "Book Appointment",
                        tint = PrimaryBlue,
                        paddingValues = 0.dp
                    ) {
                        Log.d("BookingData","$bookingData")
                        if (bookingData != null) {
                            viewModel.bookAppointment(bookingData)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }

    // Handle booking states
    LaunchedEffect(uiState) {
        when (uiState) {
            is BookingScreenUiState.Success -> {
                Toast.makeText(context, "Appointment booked successfully!", Toast.LENGTH_SHORT).show()
                navController.navigate(AppRoute.BookingConfirmScreen.route)
            }
            is BookingScreenUiState.Error -> {
                Toast.makeText(
                    context,
                    (uiState as BookingScreenUiState.Error).message,
                    Toast.LENGTH_SHORT
                ).show()
                Log.e("Booking","${(uiState as BookingScreenUiState.Error).message}")
            }
            else -> {}
        }
    }
}

@Composable
fun DateTimeCard(
    date: String,
    time: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        border = BorderStroke(1.dp, Color(0xFFE0E0E0))
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Date Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(
                            color = Color(0xFFE3F2FD),
                            shape = RoundedCornerShape(8.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.CalendarMonth, // Use your calendar icon
                        contentDescription = "Calendar",
                        tint = Color(0xFF2196F3),
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = "Date:",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )
                    Text(
                        text = formatDate(date),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Divider(color = Color(0xFFE0E0E0))

            Spacer(modifier = Modifier.height(12.dp))

            // Time Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(
                            color = Color(0xFFE8F5E9),
                            shape = RoundedCornerShape(8.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Timer, // Use your clock icon
                        contentDescription = "Clock",
                        tint = Color(0xFF4CAF50),
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = "Time:",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )
                    Text(
                        text = formatTime(time),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

// Helper function to format date
fun formatDate(date: String): String {
    // Expected input format: "2025-12-28" or similar
    // Output format: "Wednesday, Dec 28, 2025"
    return try {
        val inputFormat = SimpleDateFormat(/* pattern = */ "yyyy-MM-dd", /* locale = */ Locale.getDefault())
        val outputFormat = SimpleDateFormat("EEEE, MMM dd, yyyy", Locale.getDefault())
        val parsedDate = inputFormat.parse(date)
        outputFormat.format(parsedDate ?: Date())
    } catch (e: Exception) {
        date
    }
}

// Helper function to format time
fun formatTime(time: String): String {
    // Expected input format: "15:00" or "03:00"
    // Output format: "03:00 PM"
    return try {
        val inputFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val outputFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
        val parsedTime = inputFormat.parse(time)
        outputFormat.format(parsedTime ?: Date())
    } catch (e: Exception) {
        time
    }
}