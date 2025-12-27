package com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.patienthomescreen

import android.util.Log
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
import androidx.compose.ui.platform.LocalContext
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
import com.findmydoctor.ctrlpluscare.ui.resuablecomponents.DoctorCard
import com.findmydoctor.ctrlpluscare.ui.resuablecomponents.RequestLocationPermissionOnce
import com.findmydoctor.ctrlpluscare.ui.resuablecomponents.calculateDistance
import com.findmydoctor.ctrlpluscare.ui.screens.patientscreens.patientprofilescreen.PatientProfileUiState
import com.findmydoctor.ctrlpluscare.ui.theme.BackgroundColor
import com.findmydoctor.ctrlpluscare.ui.theme.EmergencyRed
import com.findmydoctor.ctrlpluscare.ui.theme.PrimaryBlue
import com.findmydoctor.ctrlpluscare.ui.theme.TextDisabled
import com.findmydoctor.ctrlpluscare.ui.theme.TextPrimary
import com.findmydoctor.ctrlpluscare.utils.fetchUserLocation
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun PatientHomeScreen(navController: NavHostController,viewModel: PatientHomeScreenViewModel = koinViewModel ()) {

    val uiState by viewModel.uiState.collectAsState()
    val profile by viewModel.profile.collectAsState()

    var searchQuery by remember { mutableStateOf("") }

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.getNearbyDoctors()
        viewModel.getPatientProfile()
    }
    var locationRequested by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (!locationRequested) {
            locationRequested = true
        }
    }



    if (locationRequested) {
        RequestLocationPermissionOnce(
            onGranted = {
                fetchUserLocation(context) { lat, lng ->
                    viewModel.saveUserLocation(lat, lng)
                }
            },
            onDenied = {
                Log.e("Location", "User denied location permission")
            }
        )
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
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(110.dp) // 🔒 SAME HEIGHT FOR ALL STATES
                    ) {
                        when (profile) {


                            is PatientProfileUiState.Error -> {
                                Row(
                                    modifier = Modifier.fillMaxSize(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Unable to load profile",
                                        color = Color.Red,
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                }
                            }


                            PatientProfileUiState.Idle -> {
                                Row(
                                    modifier = Modifier.fillMaxSize(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column {
                                        Text("Welcome!", color = TextPrimary)
                                        Spacer(Modifier.height(6.dp))
                                        Box(
                                            modifier = Modifier
                                                .width(140.dp)
                                                .height(18.dp)
                                                .background(Color.Transparent)
                                        )
                                    }

                                    Box(
                                        modifier = Modifier
                                            .size(80.dp)
                                            .clip(CircleShape)
                                            .background(Color.Transparent)
                                    )
                                }
                            }


                            PatientProfileUiState.Loading -> {
                                Row(
                                    modifier = Modifier.fillMaxSize(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                        Box(
                                            modifier = Modifier
                                                .width(120.dp)
                                                .height(20.dp)
                                                .background(Color.LightGray.copy(alpha = 0.4f))
                                        )
                                        Box(
                                            modifier = Modifier
                                                .width(160.dp)
                                                .height(18.dp)
                                                .background(Color.LightGray.copy(alpha = 0.4f))
                                        )
                                        Box(
                                            modifier = Modifier
                                                .width(200.dp)
                                                .height(16.dp)
                                                .background(Color.LightGray.copy(alpha = 0.3f))
                                        )
                                    }

                                    Box(
                                        modifier = Modifier
                                            .size(80.dp)
                                            .clip(CircleShape)
                                            .background(Color.LightGray.copy(alpha = 0.4f))
                                    )
                                }
                            }


                            is PatientProfileUiState.PatientProfileLoaded -> {
                                val data =
                                    (profile as PatientProfileUiState.PatientProfileLoaded)
                                        .patientProfileResponse.data

                                Row(
                                    modifier = Modifier.fillMaxSize(),
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
                                            text = data.name,
                                            color = TextPrimary.copy(alpha = 0.9f),
                                            style = MaterialTheme.typography.headlineSmall.copy(
                                                fontWeight = W400
                                            )
                                        )
                                        Text(
                                            text = data.address,
                                            color = TextPrimary.copy(alpha = 0.5f),
                                            style = MaterialTheme.typography.titleLarge.copy(
                                                fontWeight = W400,
                                                fontSize = 19.5.sp
                                            )
                                        )
                                    }

                                    AsyncImage(
                                        model = data.imageLink,
                                        contentDescription = null,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .size(80.dp)
                                            .clip(CircleShape)
                                    )
                                }
                            }
                        }
                    }



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
                        title = "Discovery",
                        onClick = {
                            navController.navigate(AppRoute.PatientDiscoveryPage.route)
                        }
                    )
                    GeneralRoundCornerButtons(
                        color = PrimaryBlue,
                        icon = R.drawable.bandage,
                        title = "Schedule",
                        onClick = {
                            navController.navigate(AppRoute.PatientScheduleScreen.route)
                        }
                    )
                    GeneralRoundCornerButtons(
                        color = EmergencyRed,
                        icon = R.drawable.ambulance,
                        title = "Emergency",
                        onClick = {
                            navController.navigate(AppRoute.PatientEmergencyScreen.route)
                        }
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
fun GeneralRoundCornerButtons(
    color: Color,
    icon : Int,
    title : String,
    onClick:()-> Unit
){
    Column(
        modifier = Modifier.background(
            color = color,
            shape = RoundedCornerShape(30.dp)
        ).padding(top = 15.dp, start = 8.dp, end = 8.dp, bottom = 10.dp).width(89.dp).clickable{
            onClick()
        },
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