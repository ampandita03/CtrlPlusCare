package com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.patienthomescreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight.Companion.W400
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.findmydoctor.ctrlpluscare.ui.theme.TextPrimary

@Composable
fun PatientHomeScreen(navController: NavHostController) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues).padding(start = 5.dp, end = 5.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Welcome!",
                color = TextPrimary,
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = W500
                )
            )
            Text(
                text = "Ruchita",
                color = TextPrimary.copy(alpha = 0.9f),
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = W400
                )
            )
            Text(
                text = "How is it going today?",
                color = TextPrimary.copy(alpha = 0.5f),
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = W400,
                    fontSize = 19.5.sp
                )
            )
        }

    }
}



@Composable
fun CustomSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
    placeholder: String,
    startPaddingValue : Dp,
    endPaddingValues: Dp
){

}