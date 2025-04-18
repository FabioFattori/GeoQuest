package com.example.geoquest.ui.screens

import android.content.Intent
import android.util.Log
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.geoquest.LoginActivity
import com.example.geoquest.R
import com.example.geoquest.apiService.ApiService
import com.example.geoquest.business.models.User
import com.example.geoquest.ui.components.baseComponents.ButtonProps
import com.example.geoquest.ui.components.baseComponents.ButtonShapes
import com.example.geoquest.ui.components.baseComponents.CustomButton
import com.example.geoquest.ui.components.baseComponents.DialogMode
import com.example.geoquest.ui.components.baseComponents.Select
import com.example.geoquest.ui.components.baseComponents.SimpleDialog
import com.example.geoquest.ui.theme.TextType
import com.example.geoquest.ui.theme.getSize
import com.example.geoquest.ui.viewModels.LanguageViewModel
import com.example.geoquest.ui.viewModels.ThemeViewModel
import com.example.geoquest.ui.viewModels.factories.ThemeViewModelFactory
import com.example.geoquest.utilities.Languages
import com.example.geoquest.utilities.PreferenceManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private fun handleThemeChange(
    selectedOption: String, themeViewModel: ThemeViewModel, darkValue: String
) {
    val isDark = selectedOption == darkValue
    if (PreferenceManager.getTheme() != isDark) {
        themeViewModel.toggleTheme()
        PreferenceManager.saveTheme(isDark)
    }
}

private fun saveLanguage(
    newLang: Languages, currentLang: MutableState<String>, languageViewModel: LanguageViewModel
) {
    if (currentLang.value != newLang.code) {
        languageViewModel.toggleLanguage()
        PreferenceManager.saveLanguage(newLang)
        currentLang.value = languageViewModel.currentLang
    }
}


@Composable
fun SettingsScreen(
    modifier: Modifier, isDark: MutableState<Boolean>, currentLanguage: MutableState<String>
) {
    val needToGoToLoginPage = remember { mutableStateOf<Boolean>(false) }
    // fucking hell android compose....
    val context = LocalContext.current
    val activity = LocalActivity.current
    // Dialogs
    val openExitDialog = remember { mutableStateOf(false) }
    val openDeleteDialog = remember { mutableStateOf(false) }

    // theme handling
    val themeOptions = listOf(
        stringResource(R.string.light_theme),
        stringResource(R.string.dark_theme),
    )

    val themeText = stringResource(id = R.string.change_theme)

    val themeViewModel: ThemeViewModel = viewModel(
        factory = ThemeViewModelFactory(
            isDark
        )
    )

    val currentThemeSelected =
        remember {
            mutableStateOf<String>(
                if (!PreferenceManager.getTheme()) themeOptions[0]
                else themeOptions[1]
            )
        }

    // Language Handling
    val languageOptions = listOf(
        stringResource(R.string.en), stringResource(R.string.it)
    )
    val languageText = stringResource(id = R.string.change_language)
    val currentLang = remember {
        mutableStateOf<String>(
            if (PreferenceManager.getLanguage() == Languages.ENGLISH.code) languageOptions[0]
            else languageOptions[1]
        )
    }

    // function to reload theme label to be translated
    LaunchedEffect(currentLanguage.value) {
        currentThemeSelected.value =
            if (!PreferenceManager.getTheme()) themeOptions[0] else themeOptions[1]
    }


    val selectWidth = 100.dp

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(40.dp)
    ) {
        Text(
            stringResource(R.string.settings_title),
            fontSize = getSize(TextType.Title),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(50.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth()
        ) {
            Text(
                themeText,
                fontSize = getSize(TextType.Normal),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxHeight(),
                textAlign = TextAlign.Center
            )
            Select(
                modifier = Modifier
                    .width(
                        selectWidth
                    )
                    .fillMaxHeight(),
                options = themeOptions,
                onOptionSelected = { selectedOption ->
                    handleThemeChange(
                        selectedOption, themeViewModel, themeOptions[1]
                    )
                },
                selectedOption = currentThemeSelected
            )
        }

        Spacer(modifier = Modifier.height(15.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth()
        ) {
            Text(
                languageText,
                fontSize = getSize(TextType.Normal),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxHeight(),
                textAlign = TextAlign.Center
            )
            Select(
                modifier = Modifier
                    .width(
                        selectWidth
                    )
                    .fillMaxHeight(),
                options = languageOptions,
                onOptionSelected = { selectedOption ->
                    if (selectedOption == languageOptions[0]) {
                        // english
                        saveLanguage(Languages.ENGLISH, currentLanguage, LanguageViewModel())
                    } else {
                        saveLanguage(Languages.ITALIAN, currentLanguage, LanguageViewModel())
                    }
                },
                selectedOption = currentLang
            )
        }

        Spacer(Modifier.height(140.dp))

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomButton(
                props = ButtonProps(
                    label = stringResource(R.string.exit),
                    onClick = {
                        openExitDialog.value = true
                    }
                ),
                buttonShape = ButtonShapes.RoundedRect,
                modifier = Modifier.width(120.dp)
            )
            Spacer(Modifier.height(30.dp))
            CustomButton(
                props = ButtonProps(
                    label = stringResource(R.string.deleteData),
                    onClick = {
                        openDeleteDialog.value = true
                    }
                ),
                buttonShape = ButtonShapes.RoundedRect
            )
        }
    }

    when {
        openExitDialog.value -> {
            SimpleDialog(
                text = stringResource(R.string.exitText),
                dialogMode = DialogMode.Info,
                onOk = {
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            val res = ApiService.retrofit.logoutUser()
                            Log.d("SUCCESS", "response => $res")
                            needToGoToLoginPage.value = true
                        } catch (ex: Exception) {
                            Log.d("ERROR", "error:${ex.message}")
                        }
                    }
                },
                onNo = {
                    openExitDialog.value = false
                }
            )
        }

        openDeleteDialog.value -> {
            SimpleDialog(
                text = stringResource(R.string.deleteText),
                dialogMode = DialogMode.Warning,
                onOk = {
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            val user = PreferenceManager.getObject("user", User::class.java)
                            if (user == null) {
                                throw Exception("NO USER FOUND")
                            }
                            val res = ApiService.retrofit.deleteUser(user.id)
                            Log.d("SUCCESS", "response => $res")
                            needToGoToLoginPage.value = true
                        } catch (ex: Exception) {
                            Log.d("ERROR", "error:${ex.message}")
                        }
                    }
                },
                onNo = {
                    openDeleteDialog.value = false
                }
            )
        }
    }

    LaunchedEffect(needToGoToLoginPage.value) {
        if (needToGoToLoginPage.value) {
            PreferenceManager.clearAll()
            val intent = Intent(context, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
            activity?.finish()
        }
    }
}
