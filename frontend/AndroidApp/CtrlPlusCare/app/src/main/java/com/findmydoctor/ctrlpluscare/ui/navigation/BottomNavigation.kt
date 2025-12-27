package com.findmydoctor.ctrlpluscare.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.findmydoctor.ctrlpluscare.ui.theme.BackgroundColor
import com.findmydoctor.ctrlpluscare.ui.theme.PrimaryBlue
import com.findmydoctor.ctrlpluscare.ui.theme.SuccessGreen
import com.findmydoctor.ctrlpluscare.ui.theme.TextDisabled
import com.findmydoctor.ctrlpluscare.ui.theme.TextSecondary

enum class UserRole {
    PATIENT,
    DOCTOR
}
data class BottomBarItem(
    val route: String,
    val icon: ImageVector,
    val label: String,
    val tint: Color
)
val patientBottomBarItems = listOf(
    BottomBarItem(
        route = AppRoute.PatientHomeScreen.route,
        icon = Icons.Filled.Home,
        label = "Home",
        PrimaryBlue
    ),
    BottomBarItem(
        route = AppRoute.PatientNotificationScreen.route,
        icon = Icons.Filled.Notifications,
        label = "Notifications",
        PrimaryBlue
    ),
    BottomBarItem(
        route = AppRoute.PatientProfileScreen.route,
        icon = Icons.Filled.Person,
        label = "Profile",
        PrimaryBlue
    )
)

val doctorBottomBarItems = listOf(
    BottomBarItem(
        route = AppRoute.DoctorHomeScreen.route,
        icon = Icons.Filled.Home,
        label = "Home",
        SuccessGreen
    ),
    BottomBarItem(
      route = AppRoute.DoctorSlotsScreen.route,
        icon = Icons.Filled.CalendarMonth,
        label = "Slots",
        SuccessGreen
    ),
    BottomBarItem(
        route = AppRoute.DoctorNotificationScreen.route,
        icon = Icons.Filled.Notifications,
        label = "Notifications",
        SuccessGreen
    ),
    BottomBarItem(
        route = AppRoute.DoctorProfileScreen.route,
        icon = Icons.Filled.Person,
        label = "Profile",
        SuccessGreen
    )
)

@Composable
fun AppBottomBar(
    navController: NavHostController,
    items: List<BottomBarItem>
) {
    val currentRoute =
        navController.currentBackStackEntryAsState().value
            ?.destination?.route

    Column(
        modifier = Modifier
            .fillMaxWidth().padding(bottom = 10.dp)
            .background(Color.White)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(BackgroundColor)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp), // matches screenshot
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEach { item ->
                CustomNavigationBarItem(
                    selected = currentRoute == item.route,
                    onClick = {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = item.icon,
                    label = item.label,
                    tint = item.tint
                )
            }
        }
    }
}

@Composable
fun CustomNavigationBarItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: ImageVector,
    label: String,
    tint: Color
) {
    val iconColor = if (selected) tint else {
        TextDisabled
    }
    val textColor = if (selected) tint else TextSecondary

    Column(
        modifier = Modifier
            .clickable { onClick() }
            .padding(vertical = 6.dp, horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = iconColor,
            modifier = Modifier.size(22.dp)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = label,
            color = textColor,
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
                fontSize = 11.sp
            )
        )
    }
}



