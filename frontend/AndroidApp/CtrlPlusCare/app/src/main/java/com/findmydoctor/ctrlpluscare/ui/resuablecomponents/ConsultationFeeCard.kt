package com.findmydoctor.ctrlpluscare.ui.resuablecomponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CurrencyRupee
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight.Companion.W400
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.findmydoctor.ctrlpluscare.ui.theme.BackgroundColor
import com.findmydoctor.ctrlpluscare.ui.theme.PrimaryBlue
import com.findmydoctor.ctrlpluscare.ui.theme.TextDisabled
import com.findmydoctor.ctrlpluscare.ui.theme.TextPrimary


@Composable
fun ConsultationFeeCard(fees: Int){
    Row(
        Modifier
            .background(
                color = PrimaryBlue.copy(0.1f),
                shape = RoundedCornerShape(10.dp)
            )
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(
            imageVector = Icons.Outlined.CurrencyRupee,
            contentDescription = "Rupee",
            tint = BackgroundColor,
            modifier = Modifier
                .background(
                    color = PrimaryBlue,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(12.dp)
                .size(26.dp)
        )
        Column {
            Text(
                text = "Consultation Fee",
                color = TextDisabled,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = W400,
                    fontSize = 14.sp
                )
            )
            Text(
                text = "₹ $fees Per Appointment",
                color = TextPrimary,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = W600,
                    fontSize = 16.sp
                )
            )
        }
    }
}