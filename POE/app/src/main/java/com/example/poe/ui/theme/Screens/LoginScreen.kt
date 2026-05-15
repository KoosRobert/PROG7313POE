package com.example.poe.ui.theme.Screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.poe.data.local.DatabaseProvider
import com.example.poe.data.local.SessionManager
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(

    onLoginSuccess: () -> Unit,

    onRegisterClick: () -> Unit

) {

    var loginInput by remember { mutableStateOf("") }

    var password by remember { mutableStateOf("") }

    var errorMessage by remember { mutableStateOf("") }

    var successMessage by remember { mutableStateOf("") }

    val context = LocalContext.current

    val sessionManager = SessionManager(context)

    val scope = rememberCoroutineScope()

    val database = DatabaseProvider.getDatabase(context)

    val expenseDao = database.expenseDao()

    Scaffold(

        topBar = {

            TopAppBar(
                title = { Text("Login") }
            )
        }

    ) { padding ->

        Column(

            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 30.dp)
                .verticalScroll(rememberScrollState()),

            verticalArrangement = Arrangement.Center,

            horizontalAlignment = Alignment.CenterHorizontally

        ) {

            Text(
                text = "Welcome Back",
                style = MaterialTheme.typography.headlineLarge
            )

            Text(
                text = "Login to continue",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(

                value = loginInput,

                onValueChange = {
                    loginInput = it
                },

                label = {
                    Text("Username or Email")
                },

                modifier = Modifier.fillMaxWidth(),

                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(

                value = password,

                onValueChange = {
                    password = it
                },

                label = {
                    Text("Password")
                },

                visualTransformation =
                    PasswordVisualTransformation(),

                modifier = Modifier.fillMaxWidth(),

                shape = RoundedCornerShape(12.dp)

            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(

                onClick = {

                    when {

                        loginInput.isBlank() -> {

                            errorMessage =
                                "Username or Email is required"

                            successMessage = ""
                        }

                        password.isBlank() -> {

                            errorMessage =
                                "Password is required"

                            successMessage = ""
                        }

                        else -> {

                            errorMessage = ""

                            scope.launch {

                                try {

                                    val user = expenseDao.loginUser(
                                        loginInput.trim(),
                                        password.trim()
                                    )

                                    if (user != null) {

                                        sessionManager.saveLogin(
                                            user.username
                                        )

                                        successMessage =
                                            "Login Successful"

                                        Toast.makeText(
                                            context,
                                            "Welcome ${user.username}",
                                            Toast.LENGTH_LONG
                                        ).show()

                                        onLoginSuccess()

                                    } else {

                                        errorMessage =
                                            "Invalid username/email or password"
                                    }

                                } catch (e: Exception) {

                                    errorMessage =
                                        "Database Error: ${e.message}"
                                }
                            }
                        }
                    }
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),

                shape = RoundedCornerShape(12.dp)

            ) {

                Text("Login")
            }

            if (errorMessage.isNotEmpty()) {

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error
                )
            }

            if (successMessage.isNotEmpty()) {

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = successMessage,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            TextButton(

                onClick = onRegisterClick

            ) {

                Text(
                    "Don't have an account? Register"
                )
            }
        }
    }
}