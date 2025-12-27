package com.findmydoctor.ctrlpluscare.ui.screens.logintypechoose

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.W400
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.findmydoctor.ctrlpluscare.R
import com.findmydoctor.ctrlpluscare.ui.navigation.AppRoute
import com.findmydoctor.ctrlpluscare.ui.resuablecomponents.CommonRoundCornersButton
import com.findmydoctor.ctrlpluscare.ui.resuablecomponents.TypeChooseButton
import com.findmydoctor.ctrlpluscare.ui.theme.BackgroundColor
import com.findmydoctor.ctrlpluscare.ui.theme.PrimaryBlue
import com.findmydoctor.ctrlpluscare.ui.theme.SuccessGreen
import com.findmydoctor.ctrlpluscare.ui.theme.TextPrimary

@Composable
fun LoginTypeChose(
    navController: NavController
){
    var loginType by remember { mutableStateOf(LoginType.NONE) }
    var selectedText by remember { mutableStateOf("Continue") }
    var selectedColor by remember { mutableStateOf(PrimaryBlue) }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding->

        Column(
            modifier = Modifier.fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(35.dp))
            Text(
                text = "Join Us Now",
                color = TextPrimary,
                style = MaterialTheme.typography.displaySmall
            )
            Spacer(Modifier.height(10.dp))
            Text(
                text = "Choose your account type",
                color = TextPrimary,
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(Modifier.height(55.dp))
            Column(
                modifier = Modifier.fillMaxWidth().padding(start = 30.dp, end = 30.dp)
            ) {
                TypeChooseButton(
                    heading = "I'm a Patient",
                    subHeading = "Find and book Doctors",
                    isSelected = loginType == LoginType.PATIENT,
                    icon = R.drawable.patienticon,
                    color = PrimaryBlue
                ) {
                    loginType = LoginType.PATIENT
                    selectedText = "Continue as a Patient"
                    selectedColor = PrimaryBlue
                }
                Spacer(Modifier.height(20.dp))
                TypeChooseButton(
                    heading = "I'm a Doctor",
                    subHeading = "Manage Appointments",
                    isSelected = loginType == LoginType.DOCTOR,
                    icon = R.drawable.doctoricon,
                    color = SuccessGreen
                ) {
                    loginType = LoginType.DOCTOR
                    selectedText = "Continue as a Doctor"
                    selectedColor = SuccessGreen
                }
            }
            Spacer(Modifier.weight(1f))

            CommonRoundCornersButton(
                text = selectedText,
                tint = selectedColor
            ) {
                if (loginType == LoginType.PATIENT){
                    navController.navigate(AppRoute.PatientSignUpScreen.route)
                }
                else if (loginType== LoginType.DOCTOR){
                    navController.navigate(AppRoute.DoctorSignUpScreen.route)

                }
            }
            Spacer(Modifier.height(15.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(start = 30.dp, end = 30.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Already have an account? ",
                    color = TextPrimary,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Log in"
                    , color = PrimaryBlue
                    , style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    modifier = Modifier.clickable{
                        navController.navigate(AppRoute.Login.route){
                            popUpTo(0){inclusive = true}
                        }
                    }
                )
            }
            Spacer(
                modifier = Modifier.height(40.dp)
            )

        }

    }
}

enum class LoginType{
    NONE,PATIENT,DOCTOR
}