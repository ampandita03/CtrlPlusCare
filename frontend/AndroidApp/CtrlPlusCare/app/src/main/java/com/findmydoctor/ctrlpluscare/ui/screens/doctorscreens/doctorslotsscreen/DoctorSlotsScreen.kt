package com.findmydoctor.ctrlpluscare.ui.screens.doctorscreens.doctorslotsscreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.W400
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.findmydoctor.ctrlpluscare.data.dto.DateItem
import com.findmydoctor.ctrlpluscare.data.dto.SetSlotsRequest
import com.findmydoctor.ctrlpluscare.ui.theme.BackgroundColor
import com.findmydoctor.ctrlpluscare.ui.theme.SuccessGreen
import com.findmydoctor.ctrlpluscare.ui.theme.TextDisabled
import com.findmydoctor.ctrlpluscare.ui.theme.TextPrimary
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoctorSlotsScreen(
    navController: NavHostController,
    viewModel: DoctorSlotsScreenViewModel = koinViewModel()
) {
    val profile by viewModel.profile.collectAsState()
    val getSlots by viewModel.getSlots.collectAsState()
    val createSlots by viewModel.createSlots.collectAsState()

    var selectedDate by remember { mutableStateOf("") }
    var showCreateSlotSheet by remember { mutableStateOf(false) }

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    val dates = remember {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val dayFormat = SimpleDateFormat("EEE", Locale.getDefault())

        List(7) { index ->
            val date = Calendar.getInstance().apply {
                add(Calendar.DAY_OF_YEAR, index)
            }

            DateItem(
                dayName = dayFormat.format(date.time).take(3),
                dayNumber = date.get(Calendar.DAY_OF_MONTH),
                fullDate = dateFormat.format(date.time)
            )
        }
    }

    LaunchedEffect(Unit) {
        viewModel.getProfile()
        selectedDate = dates.first().fullDate
        viewModel.getAvailableSlots(dates.first().fullDate)
    }

    LaunchedEffect(createSlots) {
        if (createSlots is CreateSlotsScreenUiState.Success) {
            showCreateSlotSheet = false
            viewModel.getAvailableSlots(selectedDate)
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showCreateSlotSheet = true },
                containerColor = SuccessGreen,
                modifier = Modifier.offset(y = (-60).dp,x= (-5).dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Create Slot",
                    tint = Color.White
                )
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(SuccessGreen)
                .padding(padding)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                // Profile Header
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    when (profile) {
                        is GetDoctorProfile.Error -> {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Unable to load profile",
                                    color = Color.Red,
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                        }

                        GetDoctorProfile.Loading -> {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
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
                                }

                                Box(
                                    modifier = Modifier
                                        .size(80.dp)
                                        .clip(CircleShape)
                                        .background(Color.LightGray.copy(alpha = 0.4f))
                                )
                            }
                        }

                        is GetDoctorProfile.Success -> {
                            val data = (profile as GetDoctorProfile.Success).data

                            Row(
                                modifier = Modifier.fillMaxWidth(),
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

                Spacer(modifier = Modifier.height(16.dp))

                // White Content Area
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                        .background(BackgroundColor)
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        text = "Manage Slots",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp
                        ),
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(16.dp))

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
                                    viewModel.getAvailableSlots(dateItem.fullDate)
                                }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Slots Display
                    when (getSlots) {
                        GetSlotsScreenUiState.Idle -> {}

                        GetSlotsScreenUiState.Loading -> {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(color = SuccessGreen)
                            }
                        }

                        is GetSlotsScreenUiState.Success -> {
                            val slots = (getSlots as GetSlotsScreenUiState.Success).data.data

                            if (slots.isNullOrEmpty()) {
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 32.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color.White
                                    )
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(24.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(
                                            text = "No slots available",
                                            style = MaterialTheme.typography.titleMedium,
                                            color = TextDisabled
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Text(
                                            text = "Create slots for this date",
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = TextDisabled
                                        )
                                    }
                                }
                            } else {
                                LazyVerticalGrid(
                                    columns = GridCells.Fixed(3),
                                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                                    verticalArrangement = Arrangement.spacedBy(12.dp),
                                    modifier = Modifier.height((slots.size / 3 + 1) * 60.dp)
                                ) {
                                    items(slots) { slot ->
                                        SlotDisplayCard(
                                            startTime = slot.startTime,
                                            isAvailable = slot.isAvailable
                                        )
                                    }
                                }
                            }
                        }

                        is GetSlotsScreenUiState.Error -> {
                            Text(
                                text = "Error loading slots",
                                color = Color.Red,
                                modifier = Modifier.padding(vertical = 32.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(80.dp))
                }
            }
        }

        // Create Slot Bottom Sheet
        if (showCreateSlotSheet) {
            ModalBottomSheet(
                onDismissRequest = { showCreateSlotSheet = false },
                sheetState = sheetState,
                containerColor = BackgroundColor
            ) {
                CreateSlotSheet(
                    selectedDate = selectedDate,
                    createSlots = createSlots,
                    onCreateSlot = { startTime, endTime, slotDuration ->
                        viewModel.createSlots(
                            SetSlotsRequest(
                                date = selectedDate,
                                startTime = startTime,
                                endTime = endTime,
                                slotDuration = slotDuration
                            )
                        )
                    },
                    onDismiss = {
                        scope.launch {
                            sheetState.hide()
                            showCreateSlotSheet = false
                        }
                    }
                )
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
                if (isSelected) SuccessGreen else Color.White
            )
            .border(
                width = 1.dp,
                color = if (isSelected) SuccessGreen else Color.LightGray.copy(0.3f),
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
fun SlotDisplayCard(
    startTime: String,
    isAvailable: Boolean
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(
                if (isAvailable) Color.White else Color.LightGray.copy(0.2f)
            )
            .border(
                width = 1.dp,
                color = if (isAvailable) SuccessGreen else Color.LightGray.copy(0.3f),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        val time = LocalTime.parse(startTime)
        val formatter = DateTimeFormatter.ofPattern("hh:mm a")

        Text(
            text = time.format(formatter),
            color = if (isAvailable) SuccessGreen else TextDisabled,
            fontSize = 14.sp,
            fontWeight = W500
        )
    }
}

@Composable
fun CreateSlotSheet(
    selectedDate: String,
    createSlots: CreateSlotsScreenUiState,
    onCreateSlot: (String, String, Int) -> Unit,
    onDismiss: () -> Unit
) {
    var startTime by remember { mutableStateOf("") }
    var endTime by remember { mutableStateOf("") }
    var slotDuration by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        Text(
            text = "Create Time Slots",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold
            ),
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Date: $selectedDate",
            style = MaterialTheme.typography.bodyMedium,
            color = TextDisabled
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = startTime,
            onValueChange = { startTime = it },
            label = { Text("Start Time (HH:mm)") },
            placeholder = { Text("09:00") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = SuccessGreen,
                focusedLabelColor = SuccessGreen
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = endTime,
            onValueChange = { endTime = it },
            label = { Text("End Time (HH:mm)") },
            placeholder = { Text("17:00") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = SuccessGreen,
                focusedLabelColor = SuccessGreen
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = slotDuration,
            onValueChange = { slotDuration = it },
            label = { Text("Slot Duration (minutes)") },
            placeholder = { Text("30") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = SuccessGreen,
                focusedLabelColor = SuccessGreen
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        when (createSlots) {
            is CreateSlotsScreenUiState.Error -> {
                Text(
                    text = createSlots.message,
                    color = Color.Red,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }
            else -> {}
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = onDismiss,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.LightGray
                )
            ) {
                Text("Cancel", color = Color.Black)
            }

            Button(
                onClick = {
                    if (startTime.isNotBlank() && endTime.isNotBlank() && slotDuration.isNotBlank()) {
                        onCreateSlot(startTime, endTime, slotDuration.toIntOrNull() ?: 30)
                    }
                },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = SuccessGreen
                ),
                enabled = createSlots !is CreateSlotsScreenUiState.Loading
            ) {
                if (createSlots is CreateSlotsScreenUiState.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = Color.White
                    )
                } else {
                    Text("Create Slots")
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}