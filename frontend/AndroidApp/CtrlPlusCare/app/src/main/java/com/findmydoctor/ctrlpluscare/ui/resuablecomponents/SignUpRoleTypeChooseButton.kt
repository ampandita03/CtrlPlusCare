package com.findmydoctor.ctrlpluscare.ui.resuablecomponents

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.W400
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.findmydoctor.ctrlpluscare.ui.theme.BackgroundColor
import com.findmydoctor.ctrlpluscare.ui.theme.TextPrimary


@Composable
fun TypeChooseButton(
    heading : String,
    subHeading : String,
    isSelected : Boolean,
    icon : Int,
    color : Color,
    onClick : () -> Unit
){
    Row(
        modifier = Modifier.fillMaxWidth().clickable{
            onClick()
        }
            .background(
                color= if (isSelected) color.copy(alpha = 0.3f) else BackgroundColor,
                shape = RoundedCornerShape(15.dp)
            )
            .border(
                width = 1.dp,
                color = if (isSelected) color else Color.LightGray,
                shape = RoundedCornerShape(15.dp)
            )

    ){
        Row(
            modifier = Modifier.fillMaxWidth().padding(start = 10.dp, end = 18.dp, top = 18.dp, bottom = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(icon),
                contentDescription = heading,
                tint = if (isSelected) BackgroundColor else TextPrimary,
                modifier = Modifier.padding(20.dp).background(
                    color = if (!isSelected) color.copy(alpha = 0.3f) else color,
                    shape = RoundedCornerShape(6.dp)
                )
                    .size(60.dp)
            )
            Column {
                Text(
                    text = heading,
                    color = TextPrimary,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.SemiBold
                    )
                )
                Text(
                    text = subHeading,
                    color = TextPrimary,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = W400,
                        fontSize = 16.sp
                    )
                )
            }
        }
    }
}
