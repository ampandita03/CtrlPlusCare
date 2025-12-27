package com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.schedule

// Compose UI
import android.content.Intent
import android.os.Build
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape

// Material Design
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text

// Compose Runtime
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

// Compose UI Modifiers & Utils
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavController

// Coil for Image Loading
import coil.compose.AsyncImage
import com.findmydoctor.ctrlpluscare.data.dto.Appointment
import com.findmydoctor.ctrlpluscare.ui.resuablecomponents.TopAppBar
import com.findmydoctor.ctrlpluscare.ui.theme.EmergencyRed
import com.findmydoctor.ctrlpluscare.ui.theme.PrimaryBlue
import com.findmydoctor.ctrlpluscare.ui.theme.TextDisabled
import org.koin.compose.viewmodel.koinViewModel
import java.time.Duration

// Java Time
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PatientScheduleScreen(
    navController: NavController,
    viewModel: PatientScheduleScreenViewModel = koinViewModel()
){
    val uiState by viewModel.uiState.collectAsState()
    val cancelAppointmentUiState by viewModel.cancelAppointmentUiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.getAppointments()
    }

    LaunchedEffect(cancelAppointmentUiState is CancelAppointmentUiState.Success) {
        if(cancelAppointmentUiState is CancelAppointmentUiState.Success){
            viewModel.getAppointments()
        }
    }
    Scaffold (
        topBar = {
            TopAppBar(
                title = "My Appointments"
            ) {
                navController.popBackStack()
            }
        }
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            when(uiState) {
                is PatientScheduleScreenUiState.Error -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                        Text(
                            text = (uiState as PatientScheduleScreenUiState.Error).message,
                            color = EmergencyRed,
                            modifier = Modifier.padding(16.dp),
                        )
                    }
                }
                PatientScheduleScreenUiState.Idle -> {

                }
                PatientScheduleScreenUiState.Loading -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                        CircularProgressIndicator(
                            color = PrimaryBlue
                        )
                    }
                }
                is PatientScheduleScreenUiState.Success -> {
                    val data = (uiState as PatientScheduleScreenUiState.Success).data.data
                    val sortedData = data.filter {
                        it.status != "CANCELLED"
                    }.sortedBy {
                        it.date + it.startTime
                    }
                    if (sortedData.isEmpty()) {
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                            Text(
                                text = "No appointments found",
                                color = TextDisabled,
                                modifier = Modifier.padding(16.dp),
                            )
                        }

                    }
                    else
                    LazyColumn {

                        items(sortedData){appointment->
                            val allowCancel = canCancelAppointment(
                                date = appointment.date,
                                startTime = appointment.startTime
                            )

                            AppointmentCard(
                                appointment = appointment,
                                onGetDirectionsClick = {
                                    val latitude = appointment.doctorId.clinicLocation.coordinates[1]
                                    val longitude = appointment.doctorId.clinicLocation.coordinates[0]
                                    val uri = "geo:$latitude,$longitude?q=$latitude,$longitude".toUri()
                                    val intent = Intent(Intent.ACTION_VIEW, uri)
                                    intent.setPackage("com.google.android.apps.maps")
                                    context.startActivity(intent)
                                },
                                onCancelClick = if (allowCancel) {
                                    { viewModel.cancelAppointments(appointment._id) }
                                } else {
                                    null
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}



@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppointmentCard(
    appointment: Appointment,
    onGetDirectionsClick: () -> Unit,
    onCancelClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        border = BorderStroke(
            width = 1.dp,
            color = TextDisabled.copy(0.5f)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Doctor Info Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Doctor Image
                AsyncImage(
                    model = appointment.doctorId.profileUrl,
                    contentDescription = "Doctor profile",
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.LightGray),
                    contentScale = ContentScale.Crop
                )

                // Doctor Details
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = appointment.doctorId.name,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )

                        // Confirmed Badge
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = Color(0xFFE8F5E9)
                        ) {
                            Text(
                                text = "Confirmed",
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                style = MaterialTheme.typography.labelSmall,
                                color = Color(0xFF4CAF50),
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    Text(
                        text = appointment.doctorId.specialty,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )

                    // Date and Time
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = Color.Gray
                        )
                        Text(
                            text = formatAppointmentDate(appointment.date),
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Schedule,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = Color.Gray
                        )
                        Text(
                            text = appointment.startTime,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (onCancelClick != null) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = onCancelClick,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = EmergencyRed
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "Cancel",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Button(
                        onClick = onGetDirectionsClick,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PrimaryBlue
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "Directions",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            } else {
                Button(
                    onClick = onGetDirectionsClick,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PrimaryBlue
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Get Directions",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

// Helper function to format date
// Helper function to format date using java.time
@RequiresApi(Build.VERSION_CODES.O)
private fun formatAppointmentDate(dateString: String): String {
    return try {
        val instant = Instant.parse(dateString)
        val appointmentDate = instant.atZone(ZoneId.systemDefault()).toLocalDate()
        val today = LocalDate.now()
        val tomorrow = today.plusDays(1)

        when {
            appointmentDate == today -> "Today"
            appointmentDate == tomorrow -> "Tomorrow"
            else -> {
                val formatter = DateTimeFormatter.ofPattern("MMM d, yyyy")
                appointmentDate.format(formatter)
            }
        }
    } catch (e: Exception) {
        dateString
    }
}
@RequiresApi(Build.VERSION_CODES.O)
fun canCancelAppointment(
    date: String,
    startTime: String
): Boolean {
    return try {
        val appointmentDateTime = LocalDateTime.parse(
            "$date $startTime",
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        )

        val now = LocalDateTime.now()
        val minutesDiff = Duration.between(now, appointmentDateTime).toMinutes()

        minutesDiff >= 60
    } catch (e: Exception) {
        false
    }
}
