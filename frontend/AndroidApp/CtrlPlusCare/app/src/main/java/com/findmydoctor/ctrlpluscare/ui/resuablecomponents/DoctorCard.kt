package com.findmydoctor.ctrlpluscare.ui.resuablecomponents

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight.Companion.W400
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.findmydoctor.ctrlpluscare.data.dto.Doctor
import com.findmydoctor.ctrlpluscare.ui.theme.PrimaryBlue
import com.findmydoctor.ctrlpluscare.ui.theme.TextDisabled
import com.findmydoctor.ctrlpluscare.ui.theme.TextPrimary
import com.findmydoctor.ctrlpluscare.utils.fetchUserLocation


@Composable
fun DoctorCard(doctor: Doctor,onClick:()-> Unit,border: Boolean = true,directions: Boolean = false,onDirectionClick:()-> Unit = {},isClickable : Boolean = true ) {
    val context = LocalContext.current

    var userLocation by remember {
        mutableStateOf<List<Double>?>(null)
    }
    LaunchedEffect(Unit) {
        fetchUserLocation(
            context = context,
            onLocationFetched = { lat, lng ->
                // ⚠️ Mongo format: [longitude, latitude]
                userLocation = listOf(lng, lat)
            }
        )
    }

    val distance = remember(userLocation) {
        userLocation?.let { point2 ->
            calculateDistance(
                point1 = doctor.clinicLocation.coordinates, // [lng, lat]
                point2 = point2
            )
        }
    }

    Card(
        modifier = Modifier.fillMaxWidth()
            .padding(bottom = if (isClickable) 15.dp else 0.dp)
            .clickable(
                enabled = isClickable,
            ){
                onClick()
            },
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        border = BorderStroke(
            width = if (border)1.dp else 0.dp,
            color = if (border) TextDisabled.copy(alpha = 0.5f) else Color.Transparent
        )

    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(10.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            AsyncImage(
                model = doctor.profileUrl,
                contentDescription = "",
                modifier = Modifier.height(100.dp).width(100.dp)
            )
            Column(modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(5.dp)) {
                Text(
                    text = doctor.name,
                    color = TextPrimary,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = W400,
                        fontSize = 19.5.sp
                    )
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${doctor.specialty} | ",
                        color = TextDisabled,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = W400,
                            fontSize = 15.sp
                        )
                    )
                    Text(
                        text = "Rs ${doctor.consultationFee}",
                        color = TextPrimary,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = W600,
                            fontSize = 15.sp
                        )
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.LocationOn,
                        contentDescription = "",
                        tint = TextDisabled
                    )
                    Text(
                        text = "${distance?.toInt()} km away ",
                        color = TextPrimary,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = W400
                            ,
                            fontSize = 15.sp
                        )
                    )
                }
                if(directions){
                    Text(
                        text = "Get Direction (Maps)",
                        color = PrimaryBlue,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = W400,
                            fontSize = 15.sp
                        ),
                        modifier = Modifier.clickable{
                            onDirectionClick()
                        }
                    )
                }

            }
        }


    }
}