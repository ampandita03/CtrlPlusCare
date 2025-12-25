package com.findmydoctor.ctrlpluscare.ui.screens.splashscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.findmydoctor.ctrlpluscare.R
import com.findmydoctor.ctrlpluscare.ui.navigation.AppRoute
import com.findmydoctor.ctrlpluscare.ui.theme.BackgroundColor
import com.findmydoctor.ctrlpluscare.ui.theme.PrimaryBlue
import kotlinx.coroutines.delay
import org.koin.compose.viewmodel.koinViewModel
import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.LaunchedEffect

@Composable
fun SplashScreen(navController: NavHostController,viewModel: SplashScreenViewModel = koinViewModel()) {

    val uiState by viewModel.uiState.collectAsState()
    val requestPermissionLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            // You don't need to do anything here for now
        }

    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissionLauncher.launch(
                Manifest.permission.POST_NOTIFICATIONS
            )
        }
    }

    LaunchedEffect(Unit,uiState) {
        delay(500)

        viewModel.generateFcmToken()
        viewModel.navigate()
        if (uiState is SplashScreenUiState.Welcome)
            navController.navigate(AppRoute.Welcome.route){
                popUpTo(0){inclusive = true}
            }
        else if (uiState is SplashScreenUiState.Home){
            navController.navigate(AppRoute.PatientHomeScreen.route){
                popUpTo(0){inclusive = true}
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().background(color = PrimaryBlue),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(R.drawable.ctrl_plus_care_icon),
            contentDescription = null,
            tint = BackgroundColor,
            modifier = Modifier.size(250.dp)
        )
        Text(
            text = "Ctrl + Care",
            color = BackgroundColor,
            style = MaterialTheme.typography.displaySmall.copy(
                fontWeight = W500
            )
        )
    }
}