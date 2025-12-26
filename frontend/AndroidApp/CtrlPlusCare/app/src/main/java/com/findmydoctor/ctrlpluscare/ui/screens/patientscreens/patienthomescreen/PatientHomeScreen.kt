package com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.patienthomescreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.Dehaze
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight.Companion.W400
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.findmydoctor.ctrlpluscare.R
import com.findmydoctor.ctrlpluscare.data.dto.Doctor
import com.findmydoctor.ctrlpluscare.ui.navigation.AppRoute
import com.findmydoctor.ctrlpluscare.ui.resuablecomponents.calculateDistance
import com.findmydoctor.ctrlpluscare.ui.theme.BackgroundColor
import com.findmydoctor.ctrlpluscare.ui.theme.EmergencyRed
import com.findmydoctor.ctrlpluscare.ui.theme.PrimaryBlue
import com.findmydoctor.ctrlpluscare.ui.theme.TextDisabled
import com.findmydoctor.ctrlpluscare.ui.theme.TextPrimary
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun PatientHomeScreen(navController: NavHostController,viewModel: PatientHomeScreenViewModel = koinViewModel ()) {

    val uiState by viewModel.uiState.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.getNearbyDoctors(77.1025,28.7041)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(start = 10.dp, end = 10.dp),
            horizontalAlignment = Alignment.Start
        ) {
            item{
                Row(
                    modifier = Modifier.fillMaxWidth().padding(end = 5.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
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
                    AsyncImage(
                        model = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTbgk0yfCOe55931lf6q0osfhGRU-fnH8Im1g&s",
                        contentDescription = "Random PFP",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)

                    )

                }
            }

            item {
                Spacer(Modifier.height(20.dp))
            }

            item {
                CustomSearchBar(
                    query = searchQuery,
                    onQueryChange = {
                        searchQuery = it
                    },
                    onFilterClick = {

                    },
                    placeholder = "Search",
                    startPaddingValue = 5.dp,
                    endPaddingValues = 5.dp
                )
                Spacer(
                    Modifier.height(20.dp)
                )
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth().padding(start = 5.dp, end = 5.dp)
                    ,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    GeneralRoundCornerButtons(
                        color = PrimaryBlue,
                        icon = R.drawable.doctoricon,
                        title = "Discovery"
                    )
                    GeneralRoundCornerButtons(
                        color = PrimaryBlue,
                        icon = R.drawable.bandage,
                        title = "Schedule"
                    )
                    GeneralRoundCornerButtons(
                        color = EmergencyRed,
                        icon = R.drawable.ambulance,
                        title = "Emergency"
                    )
                }
            }
            item {
                Spacer(Modifier.height(10.dp))
            }
            item {
                Text(
                    text = if (searchQuery == "") "Nearby Doctors" else "Search Results",
                    color = TextPrimary,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = W400,
                        fontSize = 19.5.sp
                    )
                )
            }
            item {
                Spacer(Modifier.height(10.dp))
            }
            when(uiState){
                is PatientHomeScreenUiState.Error -> {
                    item {
                        Text("Couldn't Load Doctors")
                    }
                }
                PatientHomeScreenUiState.Idle -> {

                }
                PatientHomeScreenUiState.Loading -> {
                    item {
                        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()){
                            CircularProgressIndicator(
                                color = PrimaryBlue
                            )
                        }
                    }
                }
                is PatientHomeScreenUiState.NearByDoctorsLoaded -> {
                    val data = (uiState as PatientHomeScreenUiState.NearByDoctorsLoaded).doctors.data
                    val filteredData = data.filter { doctor->
                        doctor.name.contains(searchQuery.trim(), ignoreCase = true) ||
                                doctor.specialty.contains(searchQuery.trim(), ignoreCase = true)||
                                doctor.clinicAddress.contains(searchQuery.trim(), ignoreCase = true)
                    }
                    items(filteredData) {
                        DoctorCard(doctor = it, onClick = {
                            viewModel.saveCurrentDoctor(it)
                            navController.navigate(AppRoute.DoctorInfoScreen.route)
                        })
                    }
                }
            }
            item {
                Spacer(Modifier.height(60.dp))
            }


        }

    }
}

@Composable
fun DoctorCard(doctor: Doctor,onClick:()-> Unit,border: Boolean = true,directions: Boolean = false,onDirectionClick:()-> Unit = {},isClickable : Boolean = true ) {
    val distance = calculateDistance(
        point1 = doctor.clinicLocation.coordinates,
        point2 = listOf(77.1027,28.70)
    )
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
                model = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTbgk0yfCOe55931lf6q0osfhGRU-fnH8Im1g&s",
                contentDescription = "",
                modifier = Modifier.height(100.dp)
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
                        text = "${distance.toInt()} metres away ",
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

@Composable
fun GeneralRoundCornerButtons(
    color: Color,
    icon : Int,
    title : String
){
    Column(
        modifier = Modifier.background(
            color = color,
            shape = RoundedCornerShape(30.dp)
        ).padding(top = 15.dp, start = 8.dp, end = 8.dp, bottom = 10.dp).width(89.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(icon),
            tint = BackgroundColor,
            modifier = Modifier.size(60.dp),
            contentDescription = title
        )
        Text(
            title,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 14.sp
            ),
            color = BackgroundColor
        )
    }

}

@Composable
fun CustomSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onFilterClick: ()-> Unit,
    placeholder: String,
    startPaddingValue : Dp,
    endPaddingValues: Dp,

){
    Row(Modifier
        .fillMaxWidth()
        .padding(start = startPaddingValue, end = endPaddingValues)) {
        OutlinedTextField(
            value = query,
            onValueChange = onQueryChange,
            modifier = Modifier
                .fillMaxWidth(),
            placeholder = {
                Text(
                    placeholder,
                    color = TextDisabled,
                    modifier = Modifier.padding(start = 5.dp)
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = "Search",
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .size(30.dp),
                    tint = TextDisabled
                )
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Dehaze,
                    contentDescription = "Description",
                    tint = TextDisabled,
                    modifier = Modifier.padding(end = 10.dp)
                        /*.clickable{onFilterClick()}*/
                )
            },
            shape = RoundedCornerShape(50.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = PrimaryBlue.copy(alpha = 0.6f),
                unfocusedBorderColor = TextDisabled,
                disabledBorderColor = TextDisabled,
                cursorColor = TextDisabled
            )
        )
    }

}