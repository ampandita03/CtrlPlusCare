package com.findmydoctor.ctrlpluscare.ui.screens.doctorscreens.doctorsignup

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.outlined.AttachFile
import androidx.compose.material.icons.outlined.CurrencyRupee
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.MedicalServices
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.findmydoctor.ctrlpluscare.R
import com.findmydoctor.ctrlpluscare.data.dto.ClinicLocation
import com.findmydoctor.ctrlpluscare.data.dto.DoctorSignUpRequest
import com.findmydoctor.ctrlpluscare.ui.navigation.AppRoute
import com.findmydoctor.ctrlpluscare.ui.resuablecomponents.CommonAuthTextField
import com.findmydoctor.ctrlpluscare.ui.resuablecomponents.CommonRoundCornersButton
import com.findmydoctor.ctrlpluscare.ui.resuablecomponents.RequestLocationPermissionOnce
import com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.patientsignup.PatientSignUpUiState
import com.findmydoctor.ctrlpluscare.ui.theme.BackgroundColor
import com.findmydoctor.ctrlpluscare.ui.theme.PrimaryBlue
import com.findmydoctor.ctrlpluscare.ui.theme.SuccessGreen
import com.findmydoctor.ctrlpluscare.utils.fetchUserLocation
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun DoctorSignUpScreen(
    navController: NavHostController,
    viewModel: DoctorSignUpViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val fcmToken by viewModel.fcmToken.collectAsState()

    LaunchedEffect(uiState) {
        when (uiState) {
            is PatientSignUpUiState.Error -> {}
            PatientSignUpUiState.Idle -> {}
            PatientSignUpUiState.Loading -> {}
            PatientSignUpUiState.Success -> {
                navController.navigate(AppRoute.Login.route)
            }
        }
    }

    val context = LocalContext.current
    var locationRequested by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.getFcmToken()
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

    // Form fields
    var name by remember { mutableStateOf("") }
    var specialty by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var clinicAddress by remember { mutableStateOf("") }
    var consultationFee by remember { mutableStateOf("") }
    var about by remember { mutableStateOf("") }

    // Image states
    var profileImageUri by remember { mutableStateOf<Uri?>(null) }
    var documentImageUri by remember { mutableStateOf<Uri?>(null) }

    val profileImageUrl by viewModel.imageUrl.collectAsState()
    val documentImageUrl by viewModel.documentUrl.collectAsState()
    val isUploadingProfile by viewModel.isUploadingProfile.collectAsState()
    val isUploadingDocument by viewModel.isUploadingDocument.collectAsState()
    val location by viewModel.location.collectAsState()

    // Profile image launcher
    val profileLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            profileImageUri = it
            viewModel.uploadProfileImage(it)
        }
    }

    // Document launcher
    val documentLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            documentImageUri = it
            viewModel.uploadDocumentImage(it)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = SuccessGreen),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(50.dp))
            Column(
                modifier = Modifier.padding(end = 40.dp)
            ) {
                Text(
                    text = "Create Account",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontSize = 25.sp
                    ),
                    color = BackgroundColor
                )
                Text(
                    text = "Join as Doctor",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontSize = 18.sp
                    ),
                    color = BackgroundColor
                )
            }
            Spacer(Modifier.height(7.dp))
        }

        // Form content
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(16.dp))

            // Profile Image
            AsyncImage(
                model = profileImageUri ?: R.drawable.patienticon,
                contentDescription = "Profile Image",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .clickable {
                        profileLauncher.launch("image/*")
                    },
                contentScale = ContentScale.Crop
            )

            if (isUploadingProfile) {
                Spacer(Modifier.height(8.dp))
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = SuccessGreen
                )
            }

            Spacer(Modifier.height(16.dp))

            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 35.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                CommonAuthTextField(
                    value = name,
                    onValueChange = { name = it },
                    placeholder = "Enter your name",
                    title = "Full Name",
                    icon = Icons.Outlined.Person,
                    isTrailingIcon = false,
                    onTrailingIconClick = {},
                    keyboardType = KeyboardType.Text
                )

                CommonAuthTextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = "Enter your email",
                    title = "Email",
                    icon = Icons.Outlined.Email,
                    isTrailingIcon = false,
                    onTrailingIconClick = {},
                    keyboardType = KeyboardType.Email
                )

                CommonAuthTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    placeholder = "Enter your phone number",
                    title = "Phone Number",
                    icon = Icons.Outlined.Phone,
                    isTrailingIcon = false,
                    onTrailingIconClick = {},
                    keyboardType = KeyboardType.Phone
                )

                CommonAuthTextField(
                    value = specialty,
                    onValueChange = { specialty = it },
                    placeholder = "Enter your specialty",
                    title = "Specialty",
                    icon = Icons.Outlined.MedicalServices,
                    isTrailingIcon = false,
                    onTrailingIconClick = {},
                    keyboardType = KeyboardType.Text
                )

                CommonAuthTextField(
                    value = consultationFee,
                    onValueChange = { consultationFee = it },
                    placeholder = "Enter the amount",
                    title = "Consultation Fees",
                    icon = Icons.Outlined.CurrencyRupee,
                    isTrailingIcon = false,
                    onTrailingIconClick = {},
                    keyboardType = KeyboardType.Number
                )

                CommonAuthTextField(
                    value = clinicAddress,
                    onValueChange = { clinicAddress = it },
                    placeholder = "Enter your clinic address",
                    title = "Clinic Address",
                    icon = Icons.Outlined.LocationOn,
                    isTrailingIcon = false,
                    onTrailingIconClick = {},
                    keyboardType = KeyboardType.Text
                )

                CommonAuthTextField(
                    value = about,
                    onValueChange = { about = it },
                    placeholder = "Tell us about yourself",
                    title = "About Section",
                    icon = Icons.Outlined.Description,
                    isTrailingIcon = false,
                    onTrailingIconClick = {},
                    keyboardType = KeyboardType.Text
                )

                // Upload Document Card
                Text(
                    text = "Upload your Document",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp)
                )

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .clickable {
                            documentLauncher.launch("image/*")
                        },
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFF5F5F5)
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        if (isUploadingDocument) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = SuccessGreen
                            )
                        } else if (documentImageUri != null) {
                            Icon(
                                imageVector = Icons.Outlined.AttachFile,
                                contentDescription = "Document attached",
                                tint = SuccessGreen,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(
                                text = "Document uploaded",
                                style = MaterialTheme.typography.bodyMedium,
                                color = SuccessGreen,
                                fontWeight = FontWeight.Medium
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Outlined.AttachFile,
                                contentDescription = "Upload document",
                                tint = Color.Gray,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(
                                text = "Tap here to upload (PNG, JPG)",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Gray
                            )
                        }
                    }
                }

                Spacer(Modifier.height(8.dp))

                Text(
                    text = "Use your current location",
                    style = MaterialTheme.typography.bodySmall,
                    color = SuccessGreen,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(Modifier.height(16.dp))
        }

        // Sign Up Button
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            CommonRoundCornersButton(
                text = "Sign Up",
                tint = SuccessGreen,

            ) {
                val isLocationReady = location.latitude != 0.0 && location.longitude != 0.0

                if (isLocationReady &&
                    !isUploadingProfile &&
                    !isUploadingDocument &&
                    profileImageUrl != null &&
                    documentImageUrl != null) {
                    viewModel.signUp(
                        doctorSignUpRequest = DoctorSignUpRequest(
                            name = name,
                            specialty = specialty,
                            clinicLocation = ClinicLocation(
                                type = "Point",
                                coordinates = listOf(location.longitude, location.latitude)
                            ),
                            clinicAddress = clinicAddress,
                            consultationFee = consultationFee.toIntOrNull() ?: 0,
                            email = email,
                            phoneNumber = phone,
                            about = about,
                            profileUrl = profileImageUrl ?: "",
                            documentUrl = documentImageUrl ?: "",
                            role = "DOCTOR",
                            fcmToken = fcmToken ?: "none"
                        )
                    )
                }
            }

            Spacer(Modifier.height(15.dp))
        }
    }
}