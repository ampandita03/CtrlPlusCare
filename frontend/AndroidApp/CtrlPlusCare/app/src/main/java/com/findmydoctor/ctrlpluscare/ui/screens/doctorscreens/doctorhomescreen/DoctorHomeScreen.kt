package com.findmydoctor.ctrlpluscare.ui.screens.doctorscreens.doctorhomescreen

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.W400
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.findmydoctor.ctrlpluscare.ui.resuablecomponents.RequestLocationPermissionOnce
import com.findmydoctor.ctrlpluscare.ui.theme.BackgroundColor
import com.findmydoctor.ctrlpluscare.ui.theme.SuccessGreen
import com.findmydoctor.ctrlpluscare.ui.theme.TextDisabled
import com.findmydoctor.ctrlpluscare.ui.theme.TextPrimary
import com.findmydoctor.ctrlpluscare.utils.fetchUserLocation
import org.koin.compose.viewmodel.koinViewModel
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DoctorHomeScreen(navController: NavController, viewModel: DoctorHomeScreenViewModel = koinViewModel()) {

    val profile by viewModel.profile.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.getDoctorProfile()
        viewModel.getPatients()
    }
    var locationRequested by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (!locationRequested) {
            locationRequested = true
        }
    }

    if (locationRequested) {
        RequestLocationPermissionOnce(
            onGranted = {
                fetchUserLocation(context) { lat, lng ->
                    viewModel.saveUserLocation(lat, lng)
                }
            },
            onDenied = {
                Log.e("Location", "User denied location permission")
            }
        )
    }

    Scaffold {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(color = SuccessGreen)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.Start
            ) {
                item {
                    Spacer(modifier = Modifier.height(16.dp))

                    // Profile Header
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 15.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(110.dp)
                        ) {
                            when (profile) {
                                is DoctorProfileUiState.Error -> {
                                    Row(
                                        modifier = Modifier.fillMaxSize(),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "Unable to load profile",
                                            color = Color.Red,
                                            style = MaterialTheme.typography.bodyLarge
                                        )
                                    }
                                }

                                DoctorProfileUiState.Idle -> {
                                    Row(
                                        modifier = Modifier.fillMaxSize(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column {
                                            Text("Welcome!", color = TextPrimary)
                                            Spacer(Modifier.height(6.dp))
                                            Box(
                                                modifier = Modifier
                                                    .width(140.dp)
                                                    .height(18.dp)
                                                    .background(Color.Transparent)
                                            )
                                        }

                                        Box(
                                            modifier = Modifier
                                                .size(80.dp)
                                                .clip(CircleShape)
                                                .background(Color.Transparent)
                                        )
                                    }
                                }

                                DoctorProfileUiState.Loading -> {
                                    Row(
                                        modifier = Modifier.fillMaxSize(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                            Box(
                                                modifier = Modifier
                                                    .width(120.dp)
                                                    .height(20.dp)
                                                    .background(Color.LightGray.copy(alpha = 0.4f))
                                            )
                                            Box(
                                                modifier = Modifier
                                                    .width(160.dp)
                                                    .height(18.dp)
                                                    .background(Color.LightGray.copy(alpha = 0.4f))
                                            )
                                            Box(
                                                modifier = Modifier
                                                    .width(200.dp)
                                                    .height(16.dp)
                                                    .background(Color.LightGray.copy(alpha = 0.3f))
                                            )
                                        }

                                        Box(
                                            modifier = Modifier
                                                .size(80.dp)
                                                .clip(CircleShape)
                                                .background(Color.LightGray.copy(alpha = 0.4f))
                                        )
                                    }
                                }

                                is DoctorProfileUiState.DoctorProfileLoaded -> {
                                    val data =
                                        (profile as DoctorProfileUiState.DoctorProfileLoaded).doctorProfileResponse.data

                                    Row(
                                        modifier = Modifier.fillMaxSize(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column {
                                            Text(
                                                text = data.name,
                                                color = BackgroundColor,
                                                style = MaterialTheme.typography.headlineSmall.copy(
                                                    fontWeight = W500,
                                                    fontSize = 30.sp
                                                )
                                            )
                                            Text(
                                                text = data.specialty,
                                                color = BackgroundColor,
                                                style = MaterialTheme.typography.titleLarge.copy(
                                                    fontWeight = W400,
                                                    fontSize = 19.5.sp
                                                )
                                            )
                                        }

                                        AsyncImage(
                                            model = data.profileUrl,
                                            contentDescription = null,
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier
                                                .size(80.dp)
                                                .clip(CircleShape)
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Appointment Cards
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 15.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        AppointmentCountCard(
                            modifier = Modifier.weight(1f),
                            title = "Today's\nAppointment",
                            count = when (uiState) {
                                is DoctorHomeScreenUiState.Success -> {
                                    val today = LocalDate.now()
                                    (uiState as DoctorHomeScreenUiState.Success).data.data.count { appointment ->
                                        appointment.status != "CANCELLED" &&
                                                parseDate(appointment.date) == today
                                    }
                                }
                                else -> 0
                            }
                        )


                        AppointmentCountCard(
                            modifier = Modifier.weight(1f),
                            title = "Upcoming\nAppointment's",
                            count = when (uiState) {
                                is DoctorHomeScreenUiState.Success -> {
                                    val today = LocalDate.now()
                                    (uiState as DoctorHomeScreenUiState.Success).data.data.count { appointment ->
                                        appointment.status != "CANCELLED" &&
                                                parseDate(appointment.date).isAfter(today)
                                    }
                                }
                                else -> 0
                            }
                        )

                    }

                    Spacer(modifier = Modifier.height(20.dp))

                }

                // Appointment Items
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                BackgroundColor,
                                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                            ) // 👈 your desired background
                            .padding(vertical = 12.dp)
                    ) {

                        when (uiState) {

                            is DoctorHomeScreenUiState.Success -> {
                                val appointments =
                                    (uiState as DoctorHomeScreenUiState.Success).data.data
                                        .sortedBy { it.date }


                                if (appointments.isEmpty()){
                                    Box(modifier = Modifier.fillMaxWidth().height(370.dp), contentAlignment = Alignment.Center) {
                                        Text("No Current Appointments", color = TextDisabled, fontSize = 20.sp)

                                    }
                                }
                                appointments.forEach { appointment ->
                                    DoctorAppointmentCard(
                                        appointment = appointment,
                                        modifier = Modifier
                                            .padding(horizontal = 16.dp, vertical = 8.dp)
                                    )
                                }
                            }

                            is DoctorHomeScreenUiState.Loading -> {
                                repeat(3) {
                                    LoadingAppointmentCard(
                                        modifier = Modifier
                                            .padding(horizontal = 16.dp, vertical = 8.dp)
                                    )
                                }
                            }

                            is DoctorHomeScreenUiState.Error -> {
                                Text(
                                    text = "Failed to load appointments",
                                    modifier = Modifier.padding(16.dp),
                                    color = Color.Red
                                )
                            }

                            DoctorHomeScreenUiState.Idle -> {}
                        }
                        Spacer(modifier = Modifier.height(300.dp))
                    }
                }


                item {
                    Spacer(modifier = Modifier.height(55.dp))
                }
            }
        }
    }
}

@Composable
fun AppointmentCountCard(
    modifier: Modifier = Modifier,
    title: String,
    count: Int
) {
    Card(
        modifier = modifier.height(150.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = BackgroundColor
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    lineHeight = 20.sp
                ),
                color = SuccessGreen
            )

            Text(
                text = count.toString(),
                style = MaterialTheme.typography.displayLarge.copy(
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = SuccessGreen
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DoctorAppointmentCard(
    appointment: com.findmydoctor.ctrlpluscare.data.dto.PatientAppointment,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        border = BorderStroke(
            width = 1.dp,
            color = Color.LightGray
        )

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Patient Image Placeholder
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFE3F2FD)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "👤",
                    fontSize = 32.sp
                )
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Patient #${appointment.patientId.takeLast(5)}",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = Color.Black
                    )

                    // Status Badge
                    val statusText = when (appointment.status) {
                        "BOOKED" -> "Confirmed"
                        "CANCELLED" -> "Cancelled"
                        else -> appointment.status
                    }

                    val statusColor = when (appointment.status) {
                        "BOOKED" -> Color(0xFF4CAF50)
                        "CANCELLED" -> Color(0xFFF44336)
                        else -> Color.Gray
                    }

                    val statusBgColor = when (appointment.status) {
                        "BOOKED" -> Color(0xFFE8F5E9)
                        "CANCELLED" -> Color(0xFFFFEBEE)
                        else -> Color.LightGray
                    }

                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = statusBgColor
                    ) {
                        Text(
                            text = statusText,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                            style = MaterialTheme.typography.labelSmall,
                            color = statusColor,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                // Show emergency badge if applicable
                if (appointment.isEmergency) {
                    Text(
                        text = "⚠️ Emergency Appointment",
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.Medium
                        ),
                        color = Color(0xFFFF9800)
                    )
                }

                Text(
                    text = if(!appointment.isEmergency)"Fee: ₹${appointment.fees}" else "Fee: ₹${(appointment.fees)+500}",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Medium
                    ),
                    color = Color.Black
                )

                Text(
                    text = "${formatDate(appointment.date)}, ${appointment.startTime}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )

                // Payment status
                Text(
                    text = "Payment: ${appointment.paymentStatus}",
                    style = MaterialTheme.typography.bodySmall,
                    color = if (appointment.paymentStatus == "PAID") Color(0xFF4CAF50) else Color(0xFFFF9800)
                )
            }
        }
    }
}

@Composable
fun LoadingAppointmentCard(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.LightGray.copy(alpha = 0.4f))
            )

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .width(150.dp)
                        .height(20.dp)
                        .background(Color.LightGray.copy(alpha = 0.4f))
                )
                Box(
                    modifier = Modifier
                        .width(100.dp)
                        .height(16.dp)
                        .background(Color.LightGray.copy(alpha = 0.3f))
                )
                Box(
                    modifier = Modifier
                        .width(200.dp)
                        .height(16.dp)
                        .background(Color.LightGray.copy(alpha = 0.3f))
                )
            }
        }
    }
}

// Helper function to format date
@RequiresApi(Build.VERSION_CODES.O)
private fun formatDate(dateString: String): String {
    return try {
        val instant = Instant.parse(dateString)
        val appointmentDate = instant.atZone(ZoneId.systemDefault()).toLocalDate()
        val today = LocalDate.now()

        when {
            appointmentDate == today -> "Today"
            appointmentDate == today.plusDays(1) -> "Tomorrow"
            else -> {
                val month = appointmentDate.month.name.lowercase()
                    .replaceFirstChar { it.uppercase() }.take(3)
                "$month ${appointmentDate.dayOfMonth}"
            }
        }
    } catch (e: Exception) {
        dateString
    }
}

@RequiresApi(Build.VERSION_CODES.O)
private fun parseDate(date: String): LocalDate =
    LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE)
