package com.example.geoquest.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Accessibility
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Key
import androidx.compose.material.icons.rounded.LockReset
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.geoquest.components.Logo
import androidx.compose.ui.unit.sp
import com.example.geoquest.components.GenericInput
import com.example.geoquest.components.GenericInputProps
import com.example.geoquest.ui.theme.TextType
import com.example.geoquest.ui.theme.getGradient
import com.example.geoquest.ui.theme.getSize

@Composable
fun RegisterScreen(modifier: Modifier,onLoginRedirect:() -> Unit){

    val playerName = remember { mutableStateOf("") }




        Column(
            modifier = modifier.padding(10.dp,40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Logo(modifier)
            Text("Registrati",
                fontSize = getSize(TextType.BigTitle),
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(top = 30.dp, bottom = 30.dp))
            GenericInput(
                modifier = modifier,
                props = GenericInputProps(
                    icon = Icons.Rounded.Accessibility,
                    label = "Nome Personaggio",
                    text = playerName
                )
            )
            GenericInput(
                modifier = modifier,
                props = GenericInputProps(
                    icon = Icons.Rounded.Email,
                    label = "Email",
                    text = playerName
                )
            )
            GenericInput(
                modifier = modifier,
                props = GenericInputProps(
                    icon = Icons.Rounded.Key,
                    label = "Password",
                    text = playerName
                )
            )
            GenericInput(
                modifier = modifier,
                props = GenericInputProps(
                    icon = Icons.Rounded.LockReset,
                    label = "Conferma Password",
                    text = playerName
                )
            )
            Button(
                onClick = { onLoginRedirect() }
            ) {
                Text("Vai al Login")
            }
        }
}
