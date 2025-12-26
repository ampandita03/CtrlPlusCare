package com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.patientsignup

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Height
import androidx.compose.material.icons.outlined.LineWeight
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Mail
import androidx.compose.material.icons.outlined.Male
import androidx.compose.material.icons.outlined.Numbers
import androidx.compose.material.icons.outlined.Phone
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.findmydoctor.ctrlpluscare.R
import com.findmydoctor.ctrlpluscare.data.dto.ClinicLocation
import com.findmydoctor.ctrlpluscare.data.dto.PatientProfileRequest
import com.findmydoctor.ctrlpluscare.ui.navigation.AppRoute
import com.findmydoctor.ctrlpluscare.ui.resuablecomponents.CommonAuthTextField
import com.findmydoctor.ctrlpluscare.ui.resuablecomponents.CommonRoundCornersButton
import com.findmydoctor.ctrlpluscare.ui.resuablecomponents.RequestLocationPermissionOnce
import com.findmydoctor.ctrlpluscare.ui.theme.BackgroundColor
import com.findmydoctor.ctrlpluscare.ui.theme.PrimaryBlue
import com.findmydoctor.ctrlpluscare.utils.fetchUserLocation
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun PatientSignUpScreen(navController: NavHostController,viewModel: PatientSignUpViewModel = koinViewModel()) {


    val uiState by viewModel.uiState.collectAsState()
    val fcmToken by viewModel.fcmToken.collectAsState()


    LaunchedEffect(
        uiState
    ) {
        when(uiState) {
            is PatientSignUpUiState.Error ->{}

            PatientSignUpUiState.Idle -> {

            }
            PatientSignUpUiState.Loading -> {

            }
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


    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }


    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val imageUrl by viewModel.imageUrl.collectAsState()
    val isUploading by viewModel.isUploading.collectAsState()
    val location by viewModel.location.collectAsState()


    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            imageUri = it
            viewModel.uploadProfileImage(it) // 🔥 CALL HERE
        }
    }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = PrimaryBlue),
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
                    text = "Join as Patient",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontSize = 18.sp
                    ),
                    color = BackgroundColor
                )
            }
            Spacer(Modifier.height(7.dp))

        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(600.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            AsyncImage(
                model = imageUri ?: R.drawable.patienticon,
                contentDescription = "Profile Image",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .clickable {
                        launcher.launch("image/*")
                    }
                        ,
                contentScale = ContentScale.Crop
            )
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(start = 35.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                CommonAuthTextField(
                    value = name,
                    onValueChange = {
                        name = it
                    },
                    placeholder ="Enter your name" ,
                    title = "Full Name",
                    icon = Icons.Outlined.Mail,
                    isTrailingIcon = false,
                    onTrailingIconClick = {},
                    keyboardType = KeyboardType.Text
                )
                CommonAuthTextField(
                    value = phone,
                    onValueChange = {
                        phone = it
                    },
                    placeholder ="Enter your Phone no" ,
                    title = "Phone Number",
                    icon = Icons.Outlined.Phone,
                    isTrailingIcon = false,
                    onTrailingIconClick = {},
                    keyboardType = KeyboardType.Phone
                )
                CommonAuthTextField(
                    value = gender,
                    onValueChange = {
                        gender = it
                    },
                    placeholder ="Gender (M/F)" ,
                    title = "Gender",
                    icon = Icons.Outlined.Male,
                    isTrailingIcon = false,
                    onTrailingIconClick = {},
                    keyboardType = KeyboardType.Text
                )
                CommonAuthTextField(
                    value = age,
                    onValueChange = {
                        age = it
                    },
                    placeholder ="Enter your age" ,
                    title = "Age",
                    icon = Icons.Outlined.Numbers,
                    isTrailingIcon = false,
                    onTrailingIconClick = {},
                    keyboardType = KeyboardType.Number
                )
                CommonAuthTextField(
                    value = height,
                    onValueChange = {
                        height = it
                    },
                    placeholder ="Enter your height in cm" ,
                    title = "Height",
                    icon = Icons.Outlined.Height,
                    isTrailingIcon = false,
                    onTrailingIconClick = {},
                    keyboardType = KeyboardType.Number
                )
                CommonAuthTextField(
                    value = weight,
                    onValueChange = {
                        weight = it
                    },
                    placeholder ="Enter your Weight in kgs" ,
                    title = "Weight",
                    icon = Icons.Outlined.LineWeight,
                    isTrailingIcon = false,
                    onTrailingIconClick = {},
                    keyboardType = KeyboardType.Number
                )
                CommonAuthTextField(
                    value = address,
                    onValueChange = {
                        address = it
                    },
                    placeholder ="Enter your Address" ,
                    title = "Address",
                    icon = Icons.Outlined.LocationOn,
                    isTrailingIcon = false,
                    onTrailingIconClick = {},
                    keyboardType = KeyboardType.Text
                )
            }
        }

        Spacer(Modifier.height(10.dp))

            CommonRoundCornersButton(
                text = "Sign Up",
                tint = PrimaryBlue
            ) {
                val isLocationReady = location.latitude != 0.0 && location.longitude != 0.0

                if (isLocationReady && !isUploading){
                    viewModel.signUp(
                        patientProfileRequest = PatientProfileRequest(
                            name = name,
                            age = age.toIntOrNull()?:0,
                            gender = gender ,
                            address = address,
                            phoneNumber = phone,
                            height = height,
                            weight = weight,
                            location = ClinicLocation(
                                type = "Point",
                                coordinates = listOf(location.longitude,location.latitude)
                            ),
                            imageLink = imageUrl ?: "",
                            role = "PATIENT",
                            fcmToken = fcmToken ?: "none"

                        )
                    )
                }

            }


        Spacer(Modifier.height(15.dp))
    }
}