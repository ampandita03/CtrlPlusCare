package com.findmydoctor.ctrlpluscare.ui.screens.doctorscreens.doctorprofile

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.findmydoctor.ctrlpluscare.R
import com.findmydoctor.ctrlpluscare.data.dto.UpdateClinicLocation
import com.findmydoctor.ctrlpluscare.data.dto.UpdateDoctorProfileRequest
import com.findmydoctor.ctrlpluscare.ui.navigation.AppRoute
import com.findmydoctor.ctrlpluscare.ui.resuablecomponents.RequestLocationPermissionOnce
import com.findmydoctor.ctrlpluscare.ui.theme.SuccessGreen
import com.findmydoctor.ctrlpluscare.utils.fetchUserLocation
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun DoctorProfileScreen(
    navController: NavController,
    viewModel: DoctorProfileScreenViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val profile by viewModel.profile.collectAsState()
    val imageUrl by viewModel.imageUrl.collectAsState()
    val isUploading by viewModel.isUploading.collectAsState()
    val updateProfile by viewModel.updateProfile.collectAsState()
    val location by viewModel.location.collectAsState()

    var profileImageUri by remember { mutableStateOf<Uri?>(null) }
    var showEditDialog by remember { mutableStateOf(false) }
    var editField by remember { mutableStateOf("") }
    var editValue by remember { mutableStateOf("") }
    var locationRequested by remember { mutableStateOf(false) }
    var showLocationDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.getDoctorProfile()
    }

    LaunchedEffect(imageUrl) {
        imageUrl?.let { url ->
            viewModel.updateDoctorProfile(
                UpdateDoctorProfileRequest(profileUrl = url)
            )
            viewModel.getDoctorProfile()
        }
    }

    LaunchedEffect(updateProfile) {
        if (updateProfile is UpdateDoctorProfileScreenUiStates.Success) {
            viewModel.getDoctorProfile()
            showEditDialog = false
            showLocationDialog = false
        }
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            profileImageUri = it
            viewModel.uploadProfileImage(it)
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

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.White
    ) { innerPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (profile) {
                is DoctorProfileScreenUiStates.Success -> {
                    val doctorData = (profile as DoctorProfileScreenUiStates.Success).data.data

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(40.dp))

                        // Profile Image with loading overlay
                        Box(
                            contentAlignment = Alignment.Center
                        ) {
                            AsyncImage(
                                model = if (profileImageUri != null) profileImageUri else doctorData.profileUrl.ifEmpty { R.drawable.doctoricon },
                                contentDescription = "Profile Image",
                                modifier = Modifier
                                    .size(120.dp)
                                    .clip(CircleShape)
                                    .clickable { launcher.launch("image/*") },
                                contentScale = ContentScale.Crop
                            )

                            if (isUploading) {
                                Box(
                                    modifier = Modifier
                                        .size(120.dp)
                                        .clip(CircleShape)
                                        .background(Color.Black.copy(alpha = 0.5f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator(
                                        color = Color.White,
                                        modifier = Modifier.size(32.dp)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = doctorData.name,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = Color.Black
                        )

                        Text(
                            text = doctorData.specialty,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray,
                            fontSize = 14.sp
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        // Stats Cards Row
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 40.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            DoctorStatCard(
                                icon = Icons.Default.Star,
                                label = "Rating",
                                value = "${doctorData.rating}"
                            )
                            DoctorStatCard(
                                icon = Icons.Default.MedicalServices,
                                label = "Consult Fee",
                                value = "₹${doctorData.consultationFee}"
                            )
                            DoctorStatCard(
                                icon = Icons.Default.LocationOn,
                                label = "Emergency",
                                value = "₹${doctorData.emergencyFee}"
                            )
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        // Professional Information Section
                        Text(
                            text = "Professional information",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = Color.Black,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp)
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        // Name Field
                        DoctorInfoField(
                            label = "Name",
                            value = doctorData.name,
                            onEditClick = {
                                editField = "name"
                                editValue = doctorData.name
                                showEditDialog = true
                            }
                        )

                        // Phone Number Field
                        DoctorInfoField(
                            label = "Contact Number",
                            value = doctorData.phoneNumber,
                            onEditClick = {
                                // Phone number usually not editable
                            }
                        )

                        // Email Field
                        DoctorInfoField(
                            label = "Email",
                            value = doctorData.email,
                            onEditClick = {
                                // Email usually not editable
                            }
                        )

                        // Clinic Address Field
                        DoctorInfoField(
                            label = "Clinic Address",
                            value = doctorData.clinicAddress,
                            onEditClick = {
                                editField = "clinicAddress"
                                editValue = doctorData.clinicAddress
                                showEditDialog = true
                            }
                        )

                        // About Field
                        DoctorInfoField(
                            label = "About",
                            value = doctorData.about,
                            onEditClick = {
                                editField = "about"
                                editValue = doctorData.about
                                showEditDialog = true
                            }
                        )

                        // Consultation Fee Field
                        DoctorInfoField(
                            label = "Consultation Fee",
                            value = "₹${doctorData.consultationFee}",
                            onEditClick = {
                                editField = "consultationFee"
                                editValue = doctorData.consultationFee.toString()
                                showEditDialog = true
                            }
                        )

                        // Emergency Fee Field
                        DoctorInfoField(
                            label = "Emergency Fee",
                            value = "₹${doctorData.emergencyFee}",
                            onEditClick = {
                                editField = "emergencyFee"
                                editValue = doctorData.emergencyFee.toString()
                                showEditDialog = true
                            }
                        )

                        // Clinic Location Field
                        DoctorInfoField(
                            label = "Clinic Location (Coordinates)",
                            value = "${doctorData.clinicLocation.coordinates[1]}, ${doctorData.clinicLocation.coordinates[0]}",
                            onEditClick = {
                                showLocationDialog = true
                            }
                        )

                        Spacer(modifier = Modifier.height(12.dp))
                    }

                    // Edit Dialog
                    if (showEditDialog) {
                        DoctorEditFieldDialog(
                            field = editField,
                            value = editValue,
                            onValueChange = { editValue = it },
                            onDismiss = { showEditDialog = false },
                            onSave = {
                                when (editField) {
                                    "name" -> viewModel.updateDoctorProfile(
                                        UpdateDoctorProfileRequest(name = editValue)
                                    )
                                    "clinicAddress" -> viewModel.updateDoctorProfile(
                                        UpdateDoctorProfileRequest(clinicAddress = editValue)
                                    )
                                    "about" -> viewModel.updateDoctorProfile(
                                        UpdateDoctorProfileRequest(about = editValue)
                                    )
                                    "consultationFee" -> viewModel.updateDoctorProfile(
                                        UpdateDoctorProfileRequest(
                                            consultationFee = editValue.toIntOrNull()
                                        )
                                    )
                                    "emergencyFee" -> viewModel.updateDoctorProfile(
                                        UpdateDoctorProfileRequest(
                                            emergencyFee = editValue.toIntOrNull()
                                        )
                                    )
                                }
                            }
                        )
                    }

                    // Location Update Dialog
                    if (showLocationDialog) {
                        AlertDialog(
                            onDismissRequest = { showLocationDialog = false },
                            title = {
                                Text(
                                    text = "Update Clinic Location",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp
                                )
                            },
                            text = {
                                Column {
                                    Text("Use your current location to update clinic location?")
                                    Spacer(modifier = Modifier.height(8.dp))
                                    if (location.latitude != 0.0 && location.longitude != 0.0) {
                                        Text(
                                            text = "Current location: ${location.latitude}, ${location.longitude}",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = SuccessGreen,
                                            fontWeight = FontWeight.Medium
                                        )
                                    }
                                }
                            },
                            confirmButton = {
                                TextButton(
                                    onClick = {
                                        if (location.latitude == 0.0 && location.longitude == 0.0) {
                                            locationRequested = true
                                        } else {
                                            viewModel.updateDoctorProfile(
                                                UpdateDoctorProfileRequest(
                                                    clinicLocation = UpdateClinicLocation(
                                                        type = "Point",
                                                        coordinates = listOf(
                                                            location.longitude,
                                                            location.latitude
                                                        )
                                                    )
                                                )
                                            )
                                        }
                                    }
                                ) {
                                    Text(
                                        if (location.latitude == 0.0) "Get Location" else "Update",
                                        color = SuccessGreen
                                    )
                                }
                            },
                            dismissButton = {
                                TextButton(onClick = { showLocationDialog = false }) {
                                    Text("Cancel", color = Color.Gray)
                                }
                            },
                            shape = RoundedCornerShape(16.dp)
                        )
                    }
                }

                DoctorProfileScreenUiStates.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = SuccessGreen)
                    }
                }

                is DoctorProfileScreenUiStates.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = (profile as DoctorProfileScreenUiStates.Error).message,
                            color = Color.Red
                        )
                    }
                }

                DoctorProfileScreenUiStates.Idle -> {}
            }

            // Logout Button
            MenuButton(
                icon = "🚪",
                text = "Logout",
                iconBackground = Color(0xFFE8F5E9),
                onClick = {
                    viewModel.logOut()
                    navController.navigate(AppRoute.Welcome.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )

            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}

@Composable
fun DoctorStatCard(
    icon: ImageVector,
    label: String,
    value: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(70.dp)
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .background(
                    color = Color(0xFFE8F5E9),
                    shape = RoundedCornerShape(10.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = SuccessGreen,
                modifier = Modifier.size(40.dp)
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = SuccessGreen,
            fontWeight = FontWeight.Normal,
            fontSize = 15.sp
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 17.sp
        )
    }
}

@Composable
fun DoctorInfoField(
    label: String,
    value: String,
    onEditClick: () -> Unit,
    showValue: Boolean = true
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 6.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = SuccessGreen,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp
        )
        Spacer(modifier = Modifier.height(2.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (showValue) {
                Text(
                    text = value,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.DarkGray,
                    fontSize = 14.sp,
                    modifier = Modifier.weight(1f)
                )
            } else {
                Spacer(modifier = Modifier.weight(1f))
            }
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Edit",
                tint = Color.Gray,
                modifier = Modifier
                    .size(18.dp)
                    .clickable { onEditClick() }
            )
        }
    }
}

@Composable
fun MenuButton(
    icon: String,
    text: String,
    iconBackground: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 4.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF8F9FA)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .background(
                            color = iconBackground,
                            shape = RoundedCornerShape(8.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = icon,
                        fontSize = 18.sp
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    color = Color.Black
                )
            }
            Icon(
                imageVector = Icons.Outlined.ChevronRight,
                contentDescription = "Arrow",
                tint = Color.Gray,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
fun DoctorEditFieldDialog(
    field: String,
    value: String,
    onValueChange: (String) -> Unit,
    onDismiss: () -> Unit,
    onSave: () -> Unit
) {
    val title = when (field) {
        "name" -> "Edit Name"
        "clinicAddress" -> "Edit Clinic Address"
        "about" -> "Edit About"
        "consultationFee" -> "Edit Consultation Fee"
        "emergencyFee" -> "Edit Emergency Fee"
        else -> "Edit Field"
    }

    val placeholder = when (field) {
        "name" -> "Enter your name"
        "clinicAddress" -> "Enter clinic address"
        "about" -> "Tell about yourself and your expertise"
        "consultationFee" -> "Enter consultation fee"
        "emergencyFee" -> "Enter emergency fee"
        else -> "Enter value"
    }

    val keyboardType = when (field) {
        "consultationFee", "emergencyFee" -> KeyboardType.Number
        else -> KeyboardType.Text
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        },
        text = {
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                placeholder = { Text(placeholder) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = field != "about" && field != "clinicAddress",
                maxLines = if (field == "about") 5 else 1,
                keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = SuccessGreen,
                    focusedLabelColor = SuccessGreen
                )
            )
        },
        confirmButton = {
            TextButton(onClick = onSave) {
                Text("Save", color = SuccessGreen)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = Color.Gray)
            }
        },
        shape = RoundedCornerShape(16.dp)
    )
}