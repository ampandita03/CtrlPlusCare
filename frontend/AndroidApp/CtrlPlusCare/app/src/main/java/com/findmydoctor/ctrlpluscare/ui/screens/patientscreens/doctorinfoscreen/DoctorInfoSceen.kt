package com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.doctorinfoscreen

import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight.Companion.W400
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.navigation.NavController
import androidx.core.net.toUri
import com.findmydoctor.ctrlpluscare.data.dto.DateItem
import com.findmydoctor.ctrlpluscare.data.dto.TimeSlot
import com.findmydoctor.ctrlpluscare.ui.navigation.AppRoute
import com.findmydoctor.ctrlpluscare.ui.resuablecomponents.CommonRoundCornersButton
import com.findmydoctor.ctrlpluscare.ui.resuablecomponents.ConsultationFeeCard
import com.findmydoctor.ctrlpluscare.ui.resuablecomponents.DoctorCard
import com.findmydoctor.ctrlpluscare.ui.resuablecomponents.TopAppBar
import com.findmydoctor.ctrlpluscare.ui.theme.PrimaryBlue
import com.findmydoctor.ctrlpluscare.ui.theme.TextDisabled
import com.findmydoctor.ctrlpluscare.ui.theme.TextPrimary
import org.koin.compose.viewmodel.koinViewModel
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DoctorInfoScreen(
    navController: NavController,
    viewModel: DoctorInfoScreenViewModel = koinViewModel()
){
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    val doctor by viewModel.doctor.collectAsState()

    var selectedDate by remember { mutableStateOf("") }
    var selectedTimeSlot by remember { mutableStateOf<TimeSlot?>(null) }

    val dates = remember {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val dayFormat = SimpleDateFormat("EEE", Locale.getDefault())

        List(7) { index ->
            val date = Calendar.getInstance().apply {
                add(Calendar.DAY_OF_YEAR, index + 1) // 🔥 skip today
            }

            DateItem(
                dayName = dayFormat.format(date.time).take(3),
                dayNumber = date.get(Calendar.DAY_OF_MONTH),
                fullDate = dateFormat.format(date.time)
            )
        }
    }


    LaunchedEffect(Unit) {
        viewModel.getCurrentDoctor()
        selectedDate = dates.first().fullDate
        viewModel.getAvailableSlots(dates.first().fullDate)
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
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
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

                    Spacer(Modifier.height(11.dp))

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

                    Spacer(Modifier.height(15.dp))

                    ConsultationFeeCard(data.consultationFee)

                    Spacer(Modifier.height(15.dp))

                    // Date Selection
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(dates) { dateItem ->
                            DateCard(
                                dateItem = dateItem,
                                isSelected = selectedDate == dateItem.fullDate,
                                onClick = {
                                    selectedDate = dateItem.fullDate
                                    selectedTimeSlot = null
                                    viewModel.getAvailableSlots(dateItem.fullDate)
                                }
                            )
                        }
                    }

                    Spacer(Modifier.height(19.dp))

                    // Time Slots
                    when(uiState) {
                        DoctorInfoScreenUiStates.Idle -> {}
                        DoctorInfoScreenUiStates.Loading -> {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                                  ,
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(color = PrimaryBlue)
                            }
                        }
                        is DoctorInfoScreenUiStates.Success -> {
                            val rawSlots = (uiState as DoctorInfoScreenUiStates.Success).data.data

                            val slots = rawSlots?.filter { slot ->
                                isSlotBeyond24Hours(
                                    date = selectedDate,
                                    startTime = slot.startTime
                                )
                            }

                            if (slots.isNullOrEmpty()) {
                                Text(
                                    text = "No slots available for this date",
                                    color = TextDisabled,
                                    modifier = Modifier.padding(vertical = 32.dp)
                                )
                            } else {
                                LazyVerticalGrid(
                                    columns = GridCells.Fixed(3),
                                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                                    verticalArrangement = Arrangement.spacedBy(12.dp),
                                    modifier = Modifier.height((slots.size / 3 + 1) * 60.dp)
                                ) {
                                    items(slots) { slot ->
                                        TimeSlotCard(
                                            timeSlot = slot,
                                            isSelected = selectedTimeSlot == slot,
                                            onClick = {
                                                if (slot.isAvailable) {
                                                    selectedTimeSlot = slot
                                                }
                                            }
                                        )
                                    }
                                }
                            }
                        }
                        is DoctorInfoScreenUiStates.Error -> {
                            Text(
                                text = "Error loading slots",
                                color = Color.Red,
                                modifier = Modifier.padding(vertical = 32.dp)
                            )
                        }
                    }

                    Spacer(Modifier.weight(1f))

                    CommonRoundCornersButton(
                        text = "Book Appointment",
                        tint = PrimaryBlue,
                        paddingValues = 0.dp
                    ) {
                        if (selectedTimeSlot != null)
                        {
                            viewModel.saveBookingData(
                                selectedDate,
                                selectedTimeSlot?.startTime ?: "",
                                selectedTimeSlot?.endTime ?: ""
                            )
                            navController.navigate(AppRoute.BookingScreen.route)
                        }

                    }
                }
            }
        }
    }
}


@Composable
fun DateCard(
    dateItem: DateItem,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(
                if (isSelected) PrimaryBlue else Color.White
            )
            .border(
                width = 1.dp,
                color = if (isSelected) PrimaryBlue else Color.LightGray.copy(0.3f),
                shape = RoundedCornerShape(12.dp)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = dateItem.dayName,
            color = if (isSelected) Color.White else TextDisabled,
            fontSize = 13.sp,
            fontWeight = W400
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = dateItem.dayNumber.toString(),
            color = if (isSelected) Color.White else TextPrimary,
            fontSize = 18.sp,
            fontWeight = W600
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TimeSlotCard(
    timeSlot: TimeSlot,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val isBooked = !timeSlot.isAvailable

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(
                when {
                    isBooked -> Color.LightGray.copy(0.2f)
                    isSelected -> PrimaryBlue
                    else -> Color.White
                }
            )
            .border(
                width = 1.dp,
                color = when {
                    isBooked -> Color.LightGray.copy(0.3f)
                    isSelected -> PrimaryBlue
                    else -> Color.LightGray.copy(0.3f)
                },
                shape = RoundedCornerShape(8.dp)
            )
            .clickable(enabled = !isBooked, onClick = onClick)
            .padding(vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {val time = LocalTime.parse(timeSlot.startTime) // "09:00"

        val formatter = DateTimeFormatter.ofPattern("hh:mm a")

        Text(
            text = time.format(formatter), // 09:00 AM
            color = when {
                isBooked -> TextDisabled
                isSelected -> Color.White
                else -> TextPrimary
            },
            fontSize = 14.sp,
            fontWeight = W500
        )
    }
}
@RequiresApi(Build.VERSION_CODES.O)
fun isSlotBeyond24Hours(
    date: String,       // yyyy-MM-dd
    startTime: String   // HH:mm
): Boolean {
    return try {
        val slotDateTime = LocalDateTime.parse(
            "$date $startTime",
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        )

        val limitTime = LocalDateTime.now().plusHours(24)
        slotDateTime.isAfter(limitTime)
    } catch (e: Exception) {
        false
    }
}
