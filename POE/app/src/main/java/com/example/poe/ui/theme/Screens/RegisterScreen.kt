package com.example.poe.ui.theme.Screens

import android.util.Patterns
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
import com.example.poe.data.local.UserEntity
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    goToLogin: () -> Unit
) {

    var email by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var errorMessage by remember { mutableStateOf("") }
    var successMessage by remember { mutableStateOf("") }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val database = DatabaseProvider.getDatabase(context)
    val expenseDao = database.expenseDao()

    Scaffold(

        topBar = {

            TopAppBar(
                title = { Text("Register") }
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
                text = "Create Account",
                style = MaterialTheme.typography.headlineLarge
            )

            Text(
                text = "Start tracking your budget today",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(32.dp))

            // ---------------- EMAIL ----------------

            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                },
                label = {
                    Text("Email Address")
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

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

            Spacer(modifier = Modifier.height(12.dp))

            // ---------------- CONFIRM PASSWORD ----------------

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = {
                    confirmPassword = it
                },
                label = {
                    Text("Confirm Password")
                },
                visualTransformation =
                    PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // ---------------- REGISTER BUTTON ----------------

            Button(

                onClick = {

                    when {

                        email.isBlank() -> {

                            errorMessage =
                                "Email address is required"

                            successMessage = ""
                        }

                        !Patterns.EMAIL_ADDRESS.matcher(email)
                            .matches() -> {

                            errorMessage =
                                "Please enter a valid email address"

                            successMessage = ""
                        }

                        username.isBlank() -> {

                            errorMessage =
                                "Username is required"

                            successMessage = ""
                        }

                        username.length < 4 -> {

                            errorMessage =
                                "Username must be at least 4 characters"

                            successMessage = ""
                        }

                        password.isBlank() -> {

                            errorMessage =
                                "Password is required"

                            successMessage = ""
                        }

                        password.length < 6 -> {

                            errorMessage =
                                "Password must be at least 6 characters"

                            successMessage = ""
                        }

                        confirmPassword.isBlank() -> {

                            errorMessage =
                                "Please confirm your password"

                            successMessage = ""
                        }

                        password != confirmPassword -> {

                            errorMessage =
                                "Passwords do not match"

                            successMessage = ""
                        }

                        else -> {

                            errorMessage = ""

                            scope.launch {

                                try {

                                    expenseDao.insertUser(

                                        UserEntity(
                                            email = email.trim(),
                                            username = username.trim(),
                                            password = password.trim()
                                        )
                                    )

                                    successMessage =
                                        "Registration successful"

                                    Toast.makeText(
                                        context,
                                        "Account Created",
                                        Toast.LENGTH_LONG
                                    ).show()

                                    onRegisterSuccess()

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

                Text("Register")
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

            // ---------------- LOGIN BUTTON ----------------

            TextButton(
                onClick = goToLogin
            ) {

                Text(
                    "Already have an account? Log In"
                )
            }
        }
    }
}