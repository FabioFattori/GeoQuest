package com.example.geoquest.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun LoginScreen(modifier: Modifier,onRegisterRedirect: () -> Unit){
    Scaffold(modifier = modifier) { innerPadding ->
        Column(modifier = modifier.padding(innerPadding)) {
            Text("sono la login page")
            Button(onClick = { onRegisterRedirect() }) {
                Text("Vai al Register")
            }
        }
    }
}
