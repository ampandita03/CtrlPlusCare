package com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.patientsignup

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.text.toUpperCase
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
import com.findmydoctor.ctrlpluscare.ui.theme.TextDisabled
import com.findmydoctor.ctrlpluscare.utils.fetchUserLocation
import org.koin.compose.viewmodel.koinViewModel
import java.util.Locale

@Composable
fun PatientSignUpScreen(navController: NavHostController,viewModel: PatientSignUpViewModel = koinViewModel()) {


    val uiState by viewModel.uiState.collectAsState()
    val fcmToken by viewModel.fcmToken.collectAsState()
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var nameError by remember { mutableStateOf<String?>(null) }
    var phoneError by remember { mutableStateOf<String?>(null) }
    var ageError by remember { mutableStateOf<String?>(null) }
    var heightError by remember { mutableStateOf<String?>(null) }
    var weightError by remember { mutableStateOf<String?>(null) }
    var addressError by remember { mutableStateOf<String?>(null) }



    LaunchedEffect(uiState) {
        when (uiState) {
            is PatientSignUpUiState.Error -> {
                errorMessage = (uiState as PatientSignUpUiState.Error).message
            }
            PatientSignUpUiState.Success -> {
                navController.navigate(AppRoute.Login.route) {
                    popUpTo(AppRoute.Login.route) { inclusive = true }
                }
            }
            else -> {}
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

    fun validateFields(): Boolean {
        var valid = true

        nameError = if (name.isBlank()) {
            valid = false; "Name is required"
        } else null

        phoneError = if (phone.length != 10) {
            valid = false; "Enter valid 10 digit phone number"
        } else null

        ageError = if (age.toIntOrNull() == null || age.toInt() !in 1..120) {
            valid = false; "Enter valid age"
        } else null

        heightError = if (height.toIntOrNull() == null) {
            valid = false; "Enter valid height"
        } else null

        weightError = if (weight.toIntOrNull() == null) {
            valid = false; "Enter valid weight"
        } else null

        addressError = if (address.isBlank()) {
            valid = false; "Address is required"
        } else null

        return valid
    }



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
                    .clickable(enabled = !isUploading) {
                        launcher.launch("image/*")
                    }

                ,
                contentScale = ContentScale.Crop
            )
            if (isUploading) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp))
            }

            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(start = 35.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                errorMessage?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(end = 24.dp)
                    )
                }
                CommonAuthTextField(
                    value = name,
                    onValueChange = {
                        name = it
                        nameError = null
                    },
                    placeholder = "Enter your name",
                    title = "Full Name",
                    icon = Icons.Outlined.Mail,
                    isTrailingIcon = false,
                    onTrailingIconClick = {},
                    keyboardType = KeyboardType.Text
                )

                nameError?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 12.sp,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                CommonAuthTextField(
                    value = phone,
                    onValueChange = {
                        phone = it
                        phoneError = null
                    },
                    placeholder = "Enter your Phone no",
                    title = "Phone Number",
                    icon = Icons.Outlined.Phone,
                    isTrailingIcon = false,
                    onTrailingIconClick = {},
                    keyboardType = KeyboardType.Phone
                )

                phoneError?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 12.sp,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                CommonAuthTextField(
                    value = age,
                    onValueChange = {
                        age = it
                        ageError = null
                    },
                    placeholder = "Enter your age",
                    title = "Age",
                    icon = Icons.Outlined.Numbers,
                    isTrailingIcon = false,
                    onTrailingIconClick = {},
                    keyboardType = KeyboardType.Number
                )

                ageError?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 12.sp,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                CommonAuthTextField(
                    value = height,
                    onValueChange = {
                        height = it
                        heightError = null
                    },
                    placeholder = "Enter your height in cm",
                    title = "Height",
                    icon = Icons.Outlined.Height,
                    isTrailingIcon = false,
                    onTrailingIconClick = {},
                    keyboardType = KeyboardType.Number
                )

                heightError?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 12.sp,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                CommonAuthTextField(
                    value = weight,
                    onValueChange = {
                        weight = it
                        weightError = null
                    },
                    placeholder = "Enter your Weight in kgs",
                    title = "Weight",
                    icon = Icons.Outlined.LineWeight,
                    isTrailingIcon = false,
                    onTrailingIconClick = {},
                    keyboardType = KeyboardType.Number
                )

                weightError?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 12.sp,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                CommonAuthTextField(
                    value = address,
                    onValueChange = {
                        address = it
                        addressError = null
                    },
                    placeholder = "Enter your Address",
                    title = "Address",
                    icon = Icons.Outlined.LocationOn,
                    isTrailingIcon = false,
                    onTrailingIconClick = {},
                    keyboardType = KeyboardType.Text
                )

                addressError?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 12.sp,
                        modifier = Modifier.fillMaxWidth()
                    )
                }



            }
        }


        Spacer(Modifier.height(10.dp))
        val isLocationReady = location.latitude != 0.0 && location.longitude != 0.0
        val isFormValid = validateForm(
            name, phone, age, height, weight, address
        ) == null

        val canSubmit =
            isFormValid &&
                    isLocationReady &&
                    imageUrl?.isNotEmpty() == true &&
                    !isUploading &&
                    uiState !is PatientSignUpUiState.Loading
        CommonRoundCornersButton(
            text = if (uiState is PatientSignUpUiState.Loading) "Signing Up..." else "Sign Up",
            tint = if (canSubmit) PrimaryBlue else TextDisabled
        ) {
            val validationError = validateForm(
                name, phone, age, height, weight, address
            )
            if (!validateFields()) return@CommonRoundCornersButton

            if (validationError != null) {
                errorMessage = validationError
                return@CommonRoundCornersButton
            }
            if (canSubmit)
            viewModel.signUp(
                patientProfileRequest = PatientProfileRequest(
                    name = name,
                    age = age.toInt(),
                    address = address,
                    phoneNumber = phone,
                    height = height,
                    weight = weight,
                    location = ClinicLocation(
                        type = "Point",
                        coordinates = listOf(location.longitude, location.latitude)
                    ),
                    imageLink = imageUrl ?:"",
                    role = "PATIENT",
                    fcmToken = fcmToken ?: "none"
                )
            )
            if (imageUrl?.isNotEmpty() != true || imageUrl == ""){
                errorMessage = "Pls upload profile picture"
            }
        }
        Spacer(Modifier.height(15.dp))
    }

}

fun validateForm(
    name: String,
    phone: String,
    age: String,
    height: String,
    weight: String,
    address: String
): String? {
    return when {
        name.isBlank() -> "Name is required"
        phone.length != 10 -> "Enter valid 10 digit phone number"
        age.toIntOrNull() == null || age.toInt() !in 1..120 -> "Enter valid age"
        height.toIntOrNull() == null -> "Enter valid height"
        weight.toIntOrNull() == null -> "Enter valid weight"
        address.isBlank() -> "Address is required"
        else -> null
    }
}
