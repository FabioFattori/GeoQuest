package com.example.geoquest
//
//import android.R
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.geoquest.ui.theme.GeoQuestTheme
import com.example.geoquest.ui.viewModels.PlayerViewModel
import com.example.geoquest.utilities.PreferenceManager
import com.example.geoquest.utilities.navigation.AppNavHost


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GeoQuestTheme(darkTheme = true) {
                val navController = rememberNavController()
                // initialize store for the app
                PreferenceManager.init(context = LocalContext.current)
                AppNavHost(
                    navController = navController,
                    modifier = Modifier
                )
            }
        }
    }
}

@Composable
fun PlayerList(viewModel: PlayerViewModel = viewModel(), padding: PaddingValues) {
    LazyColumn(
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
        contentPadding = padding,
    ) {
        items(viewModel.players.size) { player ->
            Text(player.toString(), color = MaterialTheme.colorScheme.primary)
        }
    }

}
