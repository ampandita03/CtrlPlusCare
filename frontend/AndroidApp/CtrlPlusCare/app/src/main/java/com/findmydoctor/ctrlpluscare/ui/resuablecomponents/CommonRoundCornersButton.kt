package com.findmydoctor.ctrlpluscare.ui.resuablecomponents

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight.Companion.W700
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.findmydoctor.ctrlpluscare.ui.theme.BackgroundColor
import com.findmydoctor.ctrlpluscare.ui.theme.PrimaryBlue


@Composable
fun CommonRoundCornersButton(
    text : String ,
    tint : Color,
    paddingValues: Dp = 30.dp,
    onClick : () -> Unit

){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth().padding(start = paddingValues, end = paddingValues)
            .background(
                color = tint,
                shape = RoundedCornerShape(15.dp)
            ).clickable{
                onClick()
            }
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