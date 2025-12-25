package com.findmydoctor.ctrlpluscare.ui.resuablecomponents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Send
import androidx.compose.material.icons.filled.CheckCircleOutline
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.findmydoctor.ctrlpluscare.ui.theme.SuccessGreen
import com.findmydoctor.ctrlpluscare.ui.theme.TextDisabled
import com.findmydoctor.ctrlpluscare.ui.theme.TextPrimary
import com.findmydoctor.ctrlpluscare.ui.theme.TextSecondary


@Composable
fun CommonAuthTextField(
    value : String,
    onValueChange : (String) -> Unit,
    placeholder : String,
    title : String,
    icon : ImageVector,
    isTrailingIcon : Boolean = false,
    onTrailingIconClick : () -> Unit = {},
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
            onValueChange = { newValue ->
                if (newValue.length <= 10 && newValue.all { it.isDigit() }) {
                    onValueChange(newValue)
                }
            },
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
            trailingIcon = {
                if (isTrailingIcon){
                    Icon(
                        imageVector = Icons.Default.CheckCircleOutline,
                        contentDescription = title,
                        tint = SuccessGreen,
                        modifier = Modifier.clickable{
                            onTrailingIconClick()
                        }
                    )
                }
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
