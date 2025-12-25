package com.findmydoctor.ctrlpluscare.ui.screens.welcomescreen

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight.Companion.W400
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.text.font.FontWeight.Companion.W700
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.findmydoctor.ctrlpluscare.R
import com.findmydoctor.ctrlpluscare.ui.theme.BackgroundColor
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
                onClick = {

                }
            )
            Spacer(
                modifier = Modifier.height(40.dp)
            )
        }
    }

}

@Composable
fun FeatureList(
    icon : ImageVector,
    title : String,
    subtitle : String
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().padding(start = 30.dp, end = 10.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            modifier = Modifier.background(
                color = PrimaryBlue.copy(alpha = 0.3f),
                shape = CircleShape
            ).padding(10.dp).size(30.dp),
            tint = PrimaryBlue
        )
        Spacer(modifier = Modifier.width(20.dp))
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                color = TextPrimary,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = W700,
                    fontSize = 17.5.sp
                )
            )
            Text(
                text = subtitle,
                color = TextPrimary,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = W400,
                    fontSize = 17.5 .sp
                )
            )
        }
    }

}

@Composable
fun CommonRoundCornersButton(
    text : String ,
    onClick : () -> Unit
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth().padding(start = 30.dp, end = 30.dp)
            .background(
                color = PrimaryBlue,
                shape = RoundedCornerShape(15.dp)
            )
    ){
        Text(
            text = text,
            color = BackgroundColor,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = W700,
                fontSize = 17.5.sp
            ),
            modifier = Modifier.padding(18.dp)
        )
    }
}














