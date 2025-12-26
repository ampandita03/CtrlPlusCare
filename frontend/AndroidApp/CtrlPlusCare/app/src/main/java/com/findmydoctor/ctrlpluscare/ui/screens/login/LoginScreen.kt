package com.findmydoctor.ctrlpluscare.ui.screens.login

import android.widget.Toast
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Password
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.findmydoctor.ctrlpluscare.ui.navigation.AppRoute
import com.findmydoctor.ctrlpluscare.ui.resuablecomponents.CommonAuthTextField
import com.findmydoctor.ctrlpluscare.ui.resuablecomponents.CommonRoundCornersButton
import com.findmydoctor.ctrlpluscare.ui.theme.BackgroundColor
import com.findmydoctor.ctrlpluscare.ui.theme.EmergencyRed
import com.findmydoctor.ctrlpluscare.ui.theme.PrimaryBlue
import com.findmydoctor.ctrlpluscare.ui.theme.TextDisabled
import com.findmydoctor.ctrlpluscare.ui.theme.TextPrimary
import com.findmydoctor.ctrlpluscare.ui.theme.TextSecondary
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LoginScreen(navController: NavController,viewModel: LoginViewModel = koinViewModel()) {

    var phoneNumber by remember { mutableStateOf("") }
    var otp by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    val uiState by viewModel.uiState.collectAsState()

    var showTrailing by remember { mutableStateOf(false) }

    LaunchedEffect(uiState is LoginUiStates.OtpSent) {
        if (uiState is LoginUiStates.OtpSent) {
            showTrailing = true
        }
    }

    LaunchedEffect(uiState is LoginUiStates.Error) {
        if (uiState is LoginUiStates.Error) {
            errorMessage = (uiState as LoginUiStates.Error).message
            }
    }
    LaunchedEffect(
        uiState is LoginUiStates.Idle
    ) {
        showTrailing = false
    }
    LaunchedEffect(uiState is LoginUiStates.LoginSuccess){
        if (uiState is LoginUiStates.LoginSuccess){
            if ((uiState as LoginUiStates.LoginSuccess).data.data.user.role == "PATIENT")
            navController.navigate(AppRoute.PatientHomeScreen.route){
                popUpTo(0){
                    inclusive = true
                }
            }
            else if ((uiState as LoginUiStates.LoginSuccess).data.data.user.role == "DOCTOR")
                navController.navigate(AppRoute.DoctorHomeScreen.route){
                    popUpTo(0){
                        inclusive = true
                    }
                }
        }

    }


    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .background(
                    color = PrimaryBlue,
                    shape = RoundedCornerShape(
                        bottomStart = 150.dp, bottomEnd = 150.dp
                    )
                )
        ) {
            Text(
                "Welcome Back! Please Log in",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 19.sp,
                    fontWeight = FontWeight.SemiBold
                ),
                color = BackgroundColor,
                modifier = Modifier
                    .align(Alignment.Center)
                    .offset(y = 9.dp)

            )
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .offset(y = 170.dp),
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .background(
                        color = BackgroundColor
                    )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 12.dp, end = 10.dp)
                ) {
                    Spacer(Modifier.height(30.dp))
                    CommonAuthTextField(
                        value = phoneNumber,
                        onValueChange = {

                            if (it.length <= 10) {
                                phoneNumber = it

                            }
                            if (it.length<10){
                                viewModel.resetUiState()
                            }

                            if (it.length == 10) {
                                viewModel.signIn(it)
                            }

                        },
                        placeholder = "Enter your phone",
                        title = "Phone Number",
                        icon = Icons.Outlined.Phone,
                        isTrailingIcon = showTrailing,
                        onTrailingIconClick = {
                            viewModel.signIn(phoneNumber)
                        },
                        keyboardType = KeyboardType.Phone
                    )

                    Spacer(Modifier.height(15.dp))
                    CommonAuthTextField(
                        value = otp,
                        onValueChange = {
                            if (otp.length < 6)
                            otp = it
                        },
                        placeholder = "Enter your OTP",
                        title = "OTP",
                        icon = Icons.Outlined.Password,
                        keyboardType = KeyboardType.Phone
                    )
                    Spacer(Modifier.height(8.dp))

                    CommonErrorText(errorMessage)

                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = "Resend OTP",
                        color = PrimaryBlue,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = W500
                        ),
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(end = 10.dp)
                            .clickable{
                                if (phoneNumber.length == 10){
                                    viewModel.signIn(phoneNumber)
                                }
                            }
                    )
                    Spacer(Modifier.height(15.dp))
                    CommonRoundCornersButton(
                        text = "Login",
                        tint = PrimaryBlue,
                        onClick = {
                            if (phoneNumber.length ==10)
                            viewModel.signInOtp(phoneNumber,otp)
                            else
                                errorMessage = "Please enter a valid phone number"
                        },
                        paddingValues = 10.dp
                    )
                    Spacer(Modifier.height(30.dp))

                }
            }

        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp, end = 30.dp)
                .offset(y = 550.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Don't have an account? ",
                color = TextPrimary,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Sign up"
                , color = PrimaryBlue
                , style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                modifier = Modifier.clickable{
                    navController.navigate(AppRoute.LoginTypeChose.route){
                        popUpTo(0){
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun CommonErrorText(
    message: String?
) {
    if (!message.isNullOrBlank()) {
        Text(
            text = message,
            color = EmergencyRed,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(start = 8.dp, top = 4.dp)
        )
    }
}