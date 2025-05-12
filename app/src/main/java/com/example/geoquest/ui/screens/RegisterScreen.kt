package com.example.geoquest.ui.screens

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Accessibility
import androidx.compose.material.icons.rounded.AlternateEmail
import androidx.compose.material.icons.rounded.Key
import androidx.compose.material.icons.rounded.LockReset
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.geoquest.GameActivity
import com.example.geoquest.apiService.ApiService
import com.example.geoquest.apiService.dto.requests.NewUser
import com.example.geoquest.apiService.dto.responses.RegisterAndLoginResponse
import com.example.geoquest.ui.components.baseComponents.ButtonProps
import com.example.geoquest.ui.components.baseComponents.CustomButton
import com.example.geoquest.ui.components.baseComponents.GenericInput
import com.example.geoquest.ui.components.baseComponents.GenericInputProps
import com.example.geoquest.ui.components.layout.Logo
import com.example.geoquest.ui.theme.TextType
import com.example.geoquest.ui.theme.getSize
import com.example.geoquest.ui.viewModels.UserViewModel
import kotlinx.coroutines.launch
import com.example.geoquest.R

@Composable
fun RegisterScreen(
    modifier: Modifier,
    onLoginRedirect: () -> Unit,
    snackBarHostState: SnackbarHostState,
    userViewModel: UserViewModel = UserViewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val regString = stringResource(R.string.register)
    val plString = stringResource(R.string.playerName)
    val confPassString = stringResource(R.string.confPassword)
    val reg200String = stringResource(R.string.register200)
    val gotAccountString = stringResource(R.string.gotAccount)

    Column(
        modifier = modifier.padding(20.dp, 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Logo()
        Text(
            regString,
            fontSize = getSize(TextType.BigTitle),
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(top = 30.dp, bottom = 30.dp)
        )
        GenericInput(
            props = GenericInputProps(
                icon = Icons.Rounded.Accessibility,
                label = plString,
                text = userViewModel.playerName
            )
        )
        GenericInput(
            props = GenericInputProps(
                icon = Icons.Rounded.AlternateEmail,
                label = "Email",
                text = userViewModel.email,
                isEmail = true
            )
        )
        GenericInput(
            props = GenericInputProps(
                icon = Icons.Rounded.Key,
                label = "Password",
                text = userViewModel.password,
                isPassword = true
            )
        )
        GenericInput(
            props = GenericInputProps(
                icon = Icons.Rounded.LockReset,
                label = confPassString,
                text = userViewModel.passwordConfirmation,
                isPassword = true
            )
        )

        CustomButton(
            props = ButtonProps(
                label = regString, onClick = {
                    coroutineScope.launch {
                        val check = userViewModel.checkRegisterData()
                        if (check.first) {
                            val newUser = NewUser(
                                playerName = userViewModel.playerName.value,
                                email = userViewModel.email.value,
                                password = userViewModel.password.value
                            )
                            try {
                                val response = ApiService.retrofit.registerUser(newUser)
                                if (response.isSuccessful) {
                                    snackBarHostState.showSnackbar(reg200String)
                                    userViewModel.storeResponse(response.body() as RegisterAndLoginResponse)
                                    context.startActivity(
                                        Intent(
                                            context,
                                            GameActivity::class.java
                                        ).apply {
                                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                        })
                                } else {
                                    snackBarHostState.showSnackbar("Errore: ${response.code()} - ${response.message()}")
                                }
                            } catch (e: Exception) {
                                snackBarHostState.showSnackbar("Errore di rete: ${e.localizedMessage}")
                            }
                        } else {
                            snackBarHostState.showSnackbar(check.second)
                        }

                    }
                }), modifier = Modifier.padding(
                top = 15.dp
            )
        )

        CustomButton(
            props = ButtonProps(
                label = gotAccountString, onClick = onLoginRedirect
            ), modifier = Modifier.padding(
                top = 25.dp
            )
        )
    }
}
