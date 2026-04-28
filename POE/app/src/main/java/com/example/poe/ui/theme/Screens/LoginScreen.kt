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
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(

    onLoginSuccess: () -> Unit,

    onRegisterClick: () -> Unit

) {

    var username by remember { mutableStateOf("") }

    var password by remember { mutableStateOf("") }

    var errorMessage by remember { mutableStateOf("") }

    var successMessage by remember { mutableStateOf("") }

    val context = LocalContext.current

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

            // ---------------- USERNAME ----------------

            OutlinedTextField(

                value = username,

                onValueChange = {
                    username = it
                },

                label = {
                    Text("Username")
                },

                modifier = Modifier.fillMaxWidth(),

                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // ---------------- PASSWORD ----------------

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

            // ---------------- LOGIN BUTTON ----------------

            Button(

                onClick = {

                    when {

                        username.isBlank() -> {

                            errorMessage =
                                "Username is required"

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
                                        username.trim(),
                                        password.trim()
                                    )

                                    if (user != null) {

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
                                            "Invalid username or password"
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

            // ---------------- ERROR MESSAGE ----------------

            if (errorMessage.isNotEmpty()) {

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error
                )
            }

            // ---------------- SUCCESS MESSAGE ----------------

            if (successMessage.isNotEmpty()) {

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = successMessage,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // ---------------- REGISTER BUTTON ----------------

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