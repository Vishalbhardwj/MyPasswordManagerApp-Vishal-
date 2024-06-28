package com.example.mypasswordmanagerapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController

@Composable
fun SignUpFingerPrint (navController: NavController, biometricAuthenticator: BiometricAuthenticator){
    // A surface container using the 'background' color from the theme
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val activity = LocalContext.current as FragmentActivity
        var message by remember {
            mutableStateOf("")
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextButton(
                onClick = {
                    biometricAuthenticator.promptBiometricAuth(
                        title = "Login",
                        subTitle = "Use your fingerprint",
                        negativeButtonText = "Cancel",
                        fragmentActivity = activity,
                        onSuccess = {
                            message = "Success"
                            navController.navigate(Routes.HomeScreen)
                        },
                        onError = { _, errorString ->
                            message = errorString.toString()
                        },
                        onFailed = {
                            message = "Verification error"
                        }
                    )
                }) {
                Text(text = "Sign in with fingerprint")
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = message)
        }
    }
}