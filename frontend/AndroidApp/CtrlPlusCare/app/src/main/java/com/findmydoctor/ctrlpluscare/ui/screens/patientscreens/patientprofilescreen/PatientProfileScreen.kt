package com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.patientprofilescreen

import android.net.Uri
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LineWeight
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.MonitorHeart
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.findmydoctor.ctrlpluscare.R
import com.findmydoctor.ctrlpluscare.data.dto.UpdatePatientProfile
import com.findmydoctor.ctrlpluscare.ui.navigation.AppRoute
import com.findmydoctor.ctrlpluscare.ui.theme.PrimaryBlue
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun PatientProfileScreen(
    navController: NavController,
    viewModel: PatientProfileScreenViewModel = koinViewModel()
) {
    val profile by viewModel.profile.collectAsState()
    val imageUrl by viewModel.imageUrl.collectAsState()
    val isUploading by viewModel.isUploading.collectAsState()
    val updateProfile by viewModel.updateProfile.collectAsState()

    var profileImageUri by remember { mutableStateOf<Uri?>(null) }
    var showEditDialog by remember { mutableStateOf(false) }
    var editField by remember { mutableStateOf("") }
    var editValue by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.getPatientProfile()
    }

    LaunchedEffect(imageUrl) {
        imageUrl?.let { url ->
            viewModel.updatePatientProfile(
                UpdatePatientProfile(imageLink = url)
            )
            viewModel.getPatientProfile()
        }
    }

    LaunchedEffect(updateProfile) {
        if (updateProfile is UpdatePatientProfileUiState.Success) {
            viewModel.getPatientProfile()
            showEditDialog = false
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

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.White
    ) { innerPadding ->
        Column(Modifier.fillMaxSize().padding(innerPadding).verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally ) {
            when (profile) {
                is PatientProfileUiState.PatientProfileLoaded -> {
                    val patientData = (profile as PatientProfileUiState.PatientProfileLoaded).patientProfileResponse.data

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            ,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(40.dp))

                        // Profile Image with loading overlay
                        Box(
                            contentAlignment = Alignment.Center
                        ) {
                            AsyncImage(
                                model = if (profileImageUri != null) profileImageUri else patientData.imageLink.ifEmpty { R.drawable.patienticon },
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
                            text = patientData.name,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = Color.Black
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        // Stats Cards Row
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 40.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            StatCard(
                                icon = Icons.Default.MonitorHeart,
                                label = "Age",
                                value = "${patientData.age}"
                            )
                            StatCard(
                                icon = Icons.Default.LocalFireDepartment,
                                label = "Height",
                                value = "${patientData.height}cm"
                            )
                            StatCard(
                                icon = Icons.Default.LineWeight,
                                label = "Weight",
                                value = "${patientData.weight}kg"
                            )
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        // Personal Information Section
                        Text(
                            text = "Personal information",
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
                        InfoField(
                            label = "Name",
                            value = patientData.name,
                            onEditClick = {
                                editField = "name"
                                editValue = patientData.name
                                showEditDialog = true
                            }
                        )

                        // Contact Number Field
                        InfoField(
                            label = "Contact Number",
                            value = patientData.phoneNumber,
                            onEditClick = {
                                editField = "phoneNumber"
                                editValue = patientData.phoneNumber
                                showEditDialog = true
                            }
                        )



                        // Location Field
                        InfoField(
                            label = "Location",
                            value = patientData.address,
                            onEditClick = {
                                editField = "address"
                                editValue = patientData.address
                                showEditDialog = true
                            }
                        )

                        // Age Height and Weight Combined Field
                        InfoField(
                            label = "Age Height and Weight",
                            value = "",
                            onEditClick = {
                                editField = "stats"
                                editValue = "${patientData.age},${patientData.height},${patientData.weight}"
                                showEditDialog = true
                            },
                            showValue = false
                        )

                        Spacer(modifier = Modifier.height(12.dp))


                    }

                    // Edit Dialog
                    if (showEditDialog) {
                        EditFieldDialog(
                            field = editField,
                            value = editValue,
                            onValueChange = { editValue = it },
                            onDismiss = { showEditDialog = false },
                            onSave = {
                                when (editField) {
                                    "name" -> viewModel.updatePatientProfile(
                                        UpdatePatientProfile(name = editValue)
                                    )
                                    "phoneNumber" -> viewModel.updatePatientProfile(
                                        UpdatePatientProfile(phoneNumber = editValue)
                                    )
                                    "address" -> viewModel.updatePatientProfile(
                                        UpdatePatientProfile(address = editValue)
                                    )
                                    "stats" -> {
                                        val parts = editValue.split(",")
                                        if (parts.size == 3) {
                                            viewModel.updatePatientProfile(
                                                UpdatePatientProfile(
                                                    age = parts[0].trim().toIntOrNull(),
                                                    height = parts[1].trim(),
                                                    weight = parts[2].trim()
                                                )
                                            )
                                        }
                                    }
                                }
                            }
                        )
                    }
                }
                PatientProfileUiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = PrimaryBlue)
                    }
                }
                is PatientProfileUiState.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = (profile as PatientProfileUiState.Error).message,
                            color = Color.Red
                        )
                    }
                }
                PatientProfileUiState.Idle -> {}
            }


            // Logout Button
            MenuButton(
                icon = "🚪",
                text = "Logout",
                iconBackground = Color(0xFFE3F2FD),
                onClick = {
                    viewModel.logOut()
                    navController.navigate(AppRoute.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )

            Spacer(modifier = Modifier.height(100.dp))
        }

    }
}

@Composable
fun StatCard(
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
                    color = Color(0xFFE3F2FD),
                    shape = RoundedCornerShape(10.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = "Edit",
                tint = PrimaryBlue,
                modifier = Modifier.size(40.dp)
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = PrimaryBlue,
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
fun InfoField(
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
            color = PrimaryBlue,
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
fun EditFieldDialog(
    field: String,
    value: String,
    onValueChange: (String) -> Unit,
    onDismiss: () -> Unit,
    onSave: () -> Unit
) {
    val title = when (field) {
        "name" -> "Edit Name"
        "phoneNumber" -> "Edit Contact Number"
        "email" -> "Edit Email"
        "address" -> "Edit Location"
        "stats" -> "Edit Age, Height & Weight"
        else -> "Edit Field"
    }

    val placeholder = when (field) {
        "name" -> "Enter your name"
        "phoneNumber" -> "Enter phone number"
        "email" -> "Enter email address"
        "address" -> "Enter your address"
        "stats" -> "Format: age,height,weight (e.g., 21,175,103)"
        else -> "Enter value"
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
                singleLine = field != "address"
            )
        },
        confirmButton = {
            TextButton(onClick = onSave) {
                Text("Save", color = PrimaryBlue)
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