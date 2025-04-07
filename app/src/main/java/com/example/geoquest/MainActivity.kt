package com.example.geoquest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.geoquest.ui.theme.GeoQuestTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.geoquest.viewModels.PlayerViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GeoQuestTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PlayerList(padding = innerPadding)
                }
            }
        }
    }
}

@Composable
fun PlayerList(viewModel: PlayerViewModel = viewModel(),padding: PaddingValues) {
    LazyColumn(contentPadding = padding) {
        items(viewModel.players.size) { player ->
            Text(player.toString())
        }
    }
}
