package com.findmydoctor.ctrlpluscare.ui.screens.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.findmydoctor.ctrlpluscare.R
import com.findmydoctor.ctrlpluscare.ui.theme.PrimaryBlue

@Composable
fun LoginPage(){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(R.drawable.ctrl_plus_care_icon),
            contentDescription = null,
            tint = PrimaryBlue,
            modifier = Modifier.size(100.dp)
        )

        Text(
            text = "Welcome to Ctrl+Care",
            color = Color.Red,
            style = MaterialTheme.typography.titleMedium
        )
    }

}