package com.example.geoquest

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.geoquest.ui.components.layout.Helmet
import com.example.geoquest.ui.theme.GeoQuestTheme
import com.example.geoquest.utilities.Languages
import com.example.geoquest.utilities.LocaleHelper
import com.example.geoquest.utilities.PreferenceManager
import com.example.geoquest.utilities.navigation.GameNavigator
import com.mapbox.android.core.permissions.PermissionsListener
import com.mapbox.android.core.permissions.PermissionsManager

class GameActivity : ComponentActivity(), PermissionsListener {
    lateinit var permissionsManager: PermissionsManager
    lateinit var isDark: MutableState<Boolean>
    lateinit var currentLang: MutableState<String>
    lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        if (PermissionsManager.areLocationPermissionsGranted(context = this)) {
            // Permission sensitive logic called here, such as activating the Maps SDK's LocationComponent to show the device's location
        } else {
            permissionsManager = PermissionsManager(this)
            permissionsManager.requestLocationPermissions(this)
        }

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        isDark = mutableStateOf(PreferenceManager.getTheme())
        currentLang = mutableStateOf(PreferenceManager.getLanguage())

    }

    @Deprecated("This method has been deprecated in favor of using the Activity Result API\n      which brings increased type safety via an {@link ActivityResultContract} and the prebuilt\n      contracts for common intents available in\n      {@link androidx.activity.result.contract.ActivityResultContracts}, provides hooks for\n      testing, and allow receiving results in separate, testable classes independent from your\n      activity. Use\n      {@link #registerForActivityResult(ActivityResultContract, ActivityResultCallback)} passing\n      in a {@link RequestMultiplePermissions} object for the {@link ActivityResultContract} and\n      handling the result in the {@link ActivityResultCallback#onActivityResult(Object) callback}.")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults)
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onExplanationNeeded(permissionsToExplain: List<String>) {

    }

    override fun onPermissionResult(granted: Boolean) {
        if (granted) {
            Log.d("DIOCANE", "Permission granted")
        } else {
            Log.d("DIOCANE", "Permission not granted")
        }

        // need to do this cause i need to reload the home screen
        navController.navigate("home") {
            popUpTo("home") { inclusive = true }
            launchSingleTop = true
        }
    }

    override fun onStart() {
        super.onStart()
        setContent {
            navController = rememberNavController()
            val localizedContext =
                LocaleHelper.updateLocale(this, Languages.getLanguageFromCode(currentLang.value))

            CompositionLocalProvider(LocalContext provides localizedContext) {
                GeoQuestTheme(darkTheme = isDark.value) {

                    Helmet(
                        modifier = Modifier.fillMaxSize(),
                        navigator = navController,
                    ) {
                        GameNavigator(
                            navController = navController,
                            modifier = Modifier,
                            isDark = isDark,
                            currentLanguage = currentLang
                        )
                    }
                }
            }
        }

    }
}

