package com.findmydoctor.ctrlpluscare.ui.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Password
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.findmydoctor.ctrlpluscare.R
import com.findmydoctor.ctrlpluscare.ui.navigation.AppRoute
import com.findmydoctor.ctrlpluscare.ui.resuablecomponents.CommonRoundCornersButton
import com.findmydoctor.ctrlpluscare.ui.theme.BackgroundColor
import com.findmydoctor.ctrlpluscare.ui.theme.PrimaryBlue
import com.findmydoctor.ctrlpluscare.ui.theme.TextDisabled
import com.findmydoctor.ctrlpluscare.ui.theme.TextPrimary
import com.findmydoctor.ctrlpluscare.ui.theme.TextSecondary

@Composable
fun LoginScreen() {

    var phoneNumber by remember { mutableStateOf("") }
    var otp by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .background(
                    color = PrimaryBlue,
                    shape = RoundedCornerShape(
                        bottomStart = 150.dp, bottomEnd = 150.dp
                    )
                )
        ) {
            Text(
                "Welcome Back! Please Log in",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 19.sp,
                    fontWeight = FontWeight.SemiBold
                ),
                color = BackgroundColor,
                modifier = Modifier
                    .align(Alignment.Center)
                    .offset(y = 9.dp)

            )
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .offset(y = 170.dp),
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .background(
                        color = BackgroundColor
                    )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 12.dp, end = 10.dp)
                ) {
                    Spacer(Modifier.height(30.dp))
                    CommonTextField(
                        value = phoneNumber,
                        onValueChange = {
                            phoneNumber = it
                        },
                        placeholder = "Enter your phone number",
                        title = "Phone Number",
                        icon = Icons.Outlined.Phone,
                        keyboardType = KeyboardType.Phone
                    )
                    Spacer(Modifier.height(15.dp))
                    CommonTextField(
                        value = otp,
                        onValueChange = {
                            otp = it
                        },
                        placeholder = "Enter your OTP",
                        title = "OTP",
                        icon = Icons.Outlined.Password,
                        keyboardType = KeyboardType.Phone
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = "Resend OTP",
                        color = PrimaryBlue,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = W500
                        ),
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(end = 10.dp)
                    )
                    Spacer(Modifier.height(15.dp))
                    CommonRoundCornersButton(
                        text = "Login",
                        tint = PrimaryBlue,
                        onClick = {

                        },
                        paddingValues = 10.dp
                    )
                    Spacer(Modifier.height(30.dp))

                }
            }

        }
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(start = 30.dp, end = 30.dp)
                .offset(y = 550.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Don't have an account? ",
                color = TextPrimary,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Sign up"
                , color = PrimaryBlue
                , style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                modifier = Modifier.clickable{

                }
            )
        }
    }
}


@Composable
fun CommonTextField(
    value : String,
    onValueChange : (String) -> Unit,
    placeholder : String,
    title : String,
    icon : ImageVector,
    keyboardType: KeyboardType
){
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = title,
            color = TextPrimary,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 16.sp
            ),
            modifier = Modifier.padding(bottom = 3.dp)
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = placeholder,
                    color = TextDisabled.copy(alpha = 0.7f),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 15.sp
                    )
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = TextPrimary
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = TextPrimary.copy(0.5f),
                unfocusedBorderColor = TextSecondary,
                focusedTextColor = TextPrimary,
                unfocusedTextColor = TextPrimary,
                cursorColor = TextPrimary.copy(0.7f)
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = keyboardType
            )
        )
    }

}