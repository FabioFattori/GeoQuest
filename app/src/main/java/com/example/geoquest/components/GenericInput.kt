package com.example.geoquest.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.LockOpen
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.geoquest.ui.theme.TextType
import com.example.geoquest.ui.theme.blackText
import com.example.geoquest.ui.theme.getGradient
import com.example.geoquest.ui.theme.getSize
import com.example.geoquest.ui.theme.robotoFamily

data class GenericInputProps(
    val icon: ImageVector,
    val label: String,
    val text: MutableState<String>,
    val isPassword: Boolean = false,
    val isEmail: Boolean = false,
)

@Composable
fun GenericInput(props: GenericInputProps) {
    var isVisible = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.padding(bottom = 20.dp)
    ) {
        TextField(
            visualTransformation = if (isVisible.value && props.isPassword) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions =
                if (!props.isPassword && !props.isEmail) KeyboardOptions(keyboardType = KeyboardType.Text)
                else if (props.isPassword) KeyboardOptions(keyboardType = KeyboardType.Password)
                else KeyboardOptions(keyboardType = KeyboardType.Email),
            value = props.text.value,
            onValueChange = { props.text.value = it },
            placeholder = {
                Text(
                    text = props.label,
                    fontSize = getSize(TextType.Normal),
                    color = blackText
                )
            },
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedTextColor = MaterialTheme.colorScheme.background,
                unfocusedTextColor = MaterialTheme.colorScheme.background,
                cursorColor = MaterialTheme.colorScheme.background,
                focusedIndicatorColor = MaterialTheme.colorScheme.background,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.background,
                focusedContainerColor = MaterialTheme.colorScheme.inversePrimary,
                unfocusedContainerColor = MaterialTheme.colorScheme.inversePrimary,
                disabledIndicatorColor = MaterialTheme.colorScheme.inversePrimary,
                disabledContainerColor = MaterialTheme.colorScheme.inversePrimary
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(
                    height = 70.dp
                )
                .clip(
                    shape = CircleShape
                )
                .border(
                    width = 2.dp,
                    brush = getGradient(),
                    shape = CircleShape
                ),
            textStyle = TextStyle(
                fontSize = getSize(TextType.Normal),
                color = blackText,
                fontFamily = robotoFamily
            ),
            leadingIcon = {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .clip(CircleShape)
                        .padding(
                            start = 10.dp,
                            end = 10.dp
                        )
                        .size(
                            width = 45.dp,
                            height = 45.dp
                        )
                        .background(
                            brush = getGradient(),
                            shape = CircleShape
                        )
                ) {
                    Icon(
                        tint = Color.White,
                        imageVector = props.icon,
                        contentDescription = props.label,
                        modifier = Modifier
                            .size(
                                width = 30.dp,
                                height = 30.dp
                            )
                    )
                }
            },
            trailingIcon = {
                if (props.isPassword) {
                    IconButton(
                        onClick = {
                            isVisible.value = !isVisible.value
                        }
                    ) {
                        Icon(
                            imageVector = if (!isVisible.value) {
                                Icons.Rounded.Lock
                            } else {
                                Icons.Rounded.LockOpen
                            },
                            contentDescription = "Visibility Icon",
                            tint = Color(0xFF1A1A1A),
                            modifier = Modifier
                                .size(
                                    width = 30.dp,
                                    height = 30.dp
                                )
                        )
                    }
                }
            }
        )
    }
}
