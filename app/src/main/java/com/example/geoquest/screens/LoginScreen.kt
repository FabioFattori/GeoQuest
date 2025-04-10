package com.example.geoquest.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Accessibility
import androidx.compose.material.icons.rounded.Key
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.geoquest.components.ButtonProps
import com.example.geoquest.components.CustomButton
import com.example.geoquest.components.GenericInput
import com.example.geoquest.components.GenericInputProps
import com.example.geoquest.components.Logo
import com.example.geoquest.ui.theme.TextType
import com.example.geoquest.ui.theme.getSize

@Composable
fun LoginScreen(modifier: Modifier, onRegisterRedirect: () -> Unit) {
    val playerName = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    Column(
        modifier = modifier.padding(20.dp, 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Logo(modifier)
        Text(
            "Accedi",
            fontSize = getSize(TextType.BigTitle),
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(top = 30.dp, bottom = 30.dp)
        )
        GenericInput(
            props = GenericInputProps(
                icon = Icons.Rounded.Accessibility,
                label = "Nome Personaggio",
                text = playerName
            )
        )

        GenericInput(
            props = GenericInputProps(
                icon = Icons.Rounded.Key,
                label = "Password",
                text = password,
                isPassword = true
            )
        )

        CustomButton(
            props = ButtonProps(
                label = "Accedi",
                onClick = onRegisterRedirect
            ),
            modifier = Modifier.padding(
                top = 15.dp
            )
        )

        CustomButton(
            props = ButtonProps(
                label = "Ho un Account",
                onClick = onRegisterRedirect
            ),
            modifier = Modifier.padding(
                top = 25.dp
            )
        )
    }
}
