package com.findmydoctor.ctrlpluscare.ui.screens.welcomescreen


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.EventAvailable
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.findmydoctor.ctrlpluscare.R
import com.findmydoctor.ctrlpluscare.ui.navigation.AppRoute
import com.findmydoctor.ctrlpluscare.ui.resuablecomponents.CommonRoundCornersButton
import com.findmydoctor.ctrlpluscare.ui.resuablecomponents.FeatureList
import com.findmydoctor.ctrlpluscare.ui.theme.PrimaryBlue
import com.findmydoctor.ctrlpluscare.ui.theme.TextPrimary

@Composable
fun WelcomeScreen(navController: NavHostController) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(R.drawable.ctrl_plus_care_icon),
                contentDescription = null,
                tint = PrimaryBlue,
                modifier = Modifier.size(250.dp)
            )

            Text(
                text = "Welcome to",
                color = TextPrimary,
                style = MaterialTheme.typography.displaySmall
            )
            Text(
                text = "Ctrl + Care",
                color = PrimaryBlue,
                style = MaterialTheme.typography.displaySmall.copy(
                    fontWeight = W500
                )
            )

            Spacer(
                modifier = Modifier.height(60.dp)
            )
            FeatureList(
                icon = Icons.Outlined.LocationOn,
                title = "Find Nearby Doctors",
                subtitle = "Location Based Search"
            )
            Spacer(
                modifier = Modifier.height(10.dp)
            )
            FeatureList(
                icon = Icons.Outlined.EventAvailable,
                title = "Instant Booking ",
                subtitle = "Real-time slot availability"
            )
            Spacer(
                modifier = Modifier.height(10.dp)
            )
            FeatureList(
                icon = Icons.Outlined.Notifications,
                title = "Smart Reminders",
                subtitle = "Never miss an appointment"
            )
            Spacer(
                modifier = Modifier.weight(1f)
            )
            CommonRoundCornersButton(
                text = "Get Started",
                tint = PrimaryBlue,
                onClick = {
                    navController.navigate(AppRoute.LoginTypeChose.route){
                       popUpTo(0){inclusive = true}
                    }
                }
            )
            Spacer(
                modifier = Modifier.height(40.dp)
            )
        }
    }

}
















