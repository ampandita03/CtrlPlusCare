package com.findmydoctor.ctrlpluscare.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun Test(
    testViewModel: TestViewModel = koinViewModel()
){
    val token by testViewModel.token.collectAsState()

    var value by remember { mutableStateOf("") }

    LaunchedEffect(token) {
        testViewModel.getToken()
    }
    Column {
        Text(
            token
        )

        TextField(
            value = value,
            onValueChange = { value = it }
        )

        Button(
            onClick = {
                testViewModel.saveToken(value)
                testViewModel.getToken()
                
            }
        ) {
            Text("Save")
        }
    }
}