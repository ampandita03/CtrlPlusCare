package com.findmydoctor.ctrlpluscare.ui.resuablecomponents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TopAppBar(
    title: String,
    onBackClick: () -> Unit
){
    Box(
        modifier = Modifier.fillMaxWidth().padding(top = 40.dp),

    ) {
        Icon(
            imageVector = Icons.Default.ArrowBackIosNew,
            contentDescription = "Back",
            modifier = Modifier.padding(start = 15.dp, top = 10.dp, bottom = 10.dp).size(25.dp).clickable(

            ){
                onBackClick()
            }
        )

        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = W600,
                fontSize = 18.sp
            ),
            modifier = Modifier.align(Alignment.Center).padding(10.dp)
        )
    }
}