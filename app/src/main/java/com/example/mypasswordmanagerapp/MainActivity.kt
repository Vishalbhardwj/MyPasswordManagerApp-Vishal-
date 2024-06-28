package com.example.mypasswordmanagerapp

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement

import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mypasswordmanagerapp.Db.PasswordEntity
import com.example.mypasswordmanagerapp.ui.theme.MyPasswordManagerAppTheme
import javax.crypto.spec.SecretKeySpec
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
//import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults

import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


class MainActivity : FragmentActivity() {
    private lateinit var navController:NavHostController
    private val biometricAuthenticator: BiometricAuthenticator by lazy {
        BiometricAuthenticator(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val biometricAuthenticator = BiometricAuthenticator(this)

        setContent {

            val passwordViewModel: PasswordViewModel = viewModel(factory = PasswordViewModelFactory(application))
//            val secretKey = EncryptionUtil.generateSecretKey() // Generate or retrieve the secret key

            MainScreen(passwordViewModel)

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: PasswordViewModel) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    var showAddBottomSheet by remember { mutableStateOf(false) }
    var showEditBottomSheet by remember { mutableStateOf(false) }
    var selectedPassword by rememberSaveable { mutableStateOf<PasswordEntity?>(null) }

    val scaffoldStateAPS = rememberBottomSheetScaffoldState()
    val scopeAPS = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddBottomSheet = true
                    scopeAPS.launch {
                        scaffoldStateAPS.bottomSheetState.expand()
                    }
                          },
                modifier = Modifier
                    .padding(16.dp)
                    .size(60.dp)

            ) {
//
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { padding ->
        Column(modifier = Modifier
            .padding(padding)
            .fillMaxSize()) {
            PasswordListScreen(
                viewModel,
                scaffoldState = scaffoldState,
                scope = scope,
                onEditPasswordClick = { password ->
                    selectedPassword = password
                    showEditBottomSheet = true
                    scope.launch {
                        scaffoldState.bottomSheetState.expand()
                    }
                }
            )
        }
    }






//    val scaffoldState = rememberBottomSheetScaffoldState()
//    val scope = rememberCoroutineScope()
//
//    BottomSheetScaffold(
//        scaffoldState = scaffoldState,
//        sheetContent = {
//            EditPasswordScreen(viewModel, selectedPassword!!) {
//                    showEditBottomSheet = false
//                }
//        },
//        sheetPeekHeight = 0.dp
//    ) {
//        Box(
//            modifier = Modifier.fillMaxSize(),
//            contentAlignment = Alignment.Center
//        ) {
//            Button(onClick = {
//                scope.launch {
//                    scaffoldState.bottomSheetState.expand()
//                }
//            }) {
//                Text(text = "Open sheet")
//            }
//        }
//    }





    if (showAddBottomSheet) {

    BottomSheetScaffold(
        scaffoldState = scaffoldStateAPS,
        sheetContent = {
            AddPasswordScreen(viewModel) {
                showAddBottomSheet = false
            }
        },
        sheetPeekHeight = 0.dp
    ) {} }

   if (showEditBottomSheet && selectedPassword != null) {
            BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            EditPasswordScreen(viewModel, selectedPassword!!) {
                    showEditBottomSheet = false
                }
        },
        sheetPeekHeight = 0.dp
    ) {} }

}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordListScreen(
    viewModel: PasswordViewModel,
    onEditPasswordClick: (PasswordEntity) -> Unit,
    scaffoldState: BottomSheetScaffoldState,
    scope: CoroutineScope) {
    val passwords by viewModel.passwords.observeAsState(emptyList())
    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        items(passwords) { password ->
            PasswordItem(
                password = password,
                scaffoldState = scaffoldState,
                scope = scope,
                onEditClick = { onEditPasswordClick(password) }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordItem(
    password: PasswordEntity,
    scaffoldState: BottomSheetScaffoldState,
    scope: CoroutineScope,
    onEditClick: () -> Unit
    ) {
    Card(modifier = Modifier
        .padding(8.dp)
        .clickable {
            onEditClick()
        }) {
        Row(modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Column {
                Text("Account: ${password.accountType}")
                Text("Username: ${password.usernameEmail}")
            }
            Icon(
                painter = painterResource(id = R.drawable.arrow),
                contentDescription = "Edit Password",
                modifier = Modifier
                    .size(15.dp) // Set the size of the icon
                    .clickable {
//                        scope.launch {
//                            scaffoldState.bottomSheetState.expand()
//                        }
                        onEditClick()
                    }
            )
        }
    }
}


@Composable
fun AddPasswordScreen(viewModel: PasswordViewModel, function: () -> Unit) {
    var accountType by remember { mutableStateOf("") }
    var usernameEmail by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = accountType,
            onValueChange = { accountType = it },
            label = { Text("Account Type") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            value = usernameEmail,
            onValueChange = { usernameEmail = it },
            label = { Text("Username/Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(10.dp))
        Box(modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.BottomCenter
        ){
            Button(
                onClick = {
                    viewModel.addPassword(accountType, usernameEmail, password)
//                onDismiss()
                },colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .padding(top = 16.dp)
//                    .align(Alignment.End)
            ) {
                Text("Add New Account")
            }
        }

    }
}



@Composable
fun EditPasswordScreen(viewModel: PasswordViewModel, password: PasswordEntity, onDismiss: () -> Unit) {
    var accountType by rememberSaveable { mutableStateOf(password.accountType) }
    var usernameEmail by rememberSaveable { mutableStateOf(password.usernameEmail) }
    var passwordValue by rememberSaveable { mutableStateOf(password.password) }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = accountType,
            onValueChange = { accountType = it },
            label = { Text("Account Type") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(10.dp))
        TextField(
            value = usernameEmail,
            onValueChange = { usernameEmail = it },
            label = { Text("Username/Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(10.dp))
        TextField(
            value = passwordValue,
            onValueChange = { passwordValue = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(modifier = Modifier.padding(top = 16.dp)) {
            Button(
                onClick = {
                    viewModel.updatePassword(password.copy(accountType = accountType, usernameEmail = usernameEmail, password = passwordValue))
                    onDismiss()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            ) {
                Text("Edit")
            }
            Button(
                onClick = {
                    viewModel.deletePassword(password)
                    onDismiss()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red,
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            ) {
                Text("Delete")
            }
        }
    }
}