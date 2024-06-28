package com.example.mypasswordmanagerapp

import android.os.Bundle
import androidx.activity.compose.setContent
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mypasswordmanagerapp.ui.theme.MyPasswordManagerAppTheme



class MainActivity : FragmentActivity() {
    private lateinit var navController:NavHostController
    private val biometricAuthenticator: BiometricAuthenticator by lazy {
        BiometricAuthenticator(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val biometricAuthenticator = BiometricAuthenticator(this)

        setContent {

             navController = rememberNavController()

            MyPasswordManagerAppTheme {
                navController = rememberNavController()
                NavHost(navController = navController, startDestination = Routes.SignUpFingerPrint, builder = {
                    composable(Routes.SignUpFingerPrint){
                        SignUpFingerPrint(navController = navController, biometricAuthenticator = biometricAuthenticator)
                    }
                    composable(Routes.HomeScreen){
                        HomeScreen(navController = navController)
                    }
                })

            }
        }
    }


}