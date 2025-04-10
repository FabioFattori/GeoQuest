package com.example.geoquest.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.geoquest.components.Logo

@Composable
fun RegisterScreen(modifier: Modifier,onLoginRedirect:() -> Unit){
    Scaffold(modifier = modifier) { innerPadding ->
        Column(modifier.padding(innerPadding).padding(10.dp,20.dp)) {
            Button(onClick = { onLoginRedirect() }) {
                Text("Vai al Login")
            }
            Logo(modifier)
        }
    }
}
