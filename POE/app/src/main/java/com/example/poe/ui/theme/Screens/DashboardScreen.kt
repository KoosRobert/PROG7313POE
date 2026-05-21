package com.example.poe.ui.theme.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.poe.data.local.DatabaseProvider
import com.example.poe.data.local.SessionManager
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(

    goToAddExpense: () -> Unit,

    goToCategories: () -> Unit,

    goToReports: () -> Unit,

    onLogout: () -> Unit,

    darkMode: Boolean,

    onThemeToggle: () -> Unit

) {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val database = DatabaseProvider.getDatabase(context)
    val dao = database.expenseDao()

    val sessionManager = SessionManager(context)

    val loggedInUsername =
        sessionManager.getLoggedInUser()

    var income by remember { mutableStateOf("") }
    var minGoal by remember { mutableStateOf("") }
    var maxGoal by remember { mutableStateOf("") }

    var successMessage by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    var remainingBalance by remember { mutableStateOf(0.0) }

    // ---------------- LOAD USER DATA ----------------

    LaunchedEffect(Unit) {

        if (loggedInUsername != null) {

            val user =
                dao.getLoggedInUser(loggedInUsername)

            if (user != null) {

                income =
                    if (user.income == 0.0) ""
                    else user.income.toString()

                minGoal =
                    if (user.minGoal == 0.0) ""
                    else user.minGoal.toString()

                maxGoal =
                    if (user.maxGoal == 0.0) ""
                    else user.maxGoal.toString()

<<<<<<< HEAD
                // GET ALL EXPENSES

                val expenses =
                    dao.getAllExpenses()

                expenses.collect { expenseList ->

                    val totalExpenses =
                        expenseList.sumOf { it.amount }

                    remainingBalance =
                        user.income - totalExpenses
                }
=======
                remainingBalance =
                    user.income
>>>>>>> main
            }
        }
    }

    Scaffold(

        topBar = {

            CenterAlignedTopAppBar(

                title = {

                    Text(
                        "BudgetBuddy",
                        style = MaterialTheme.typography.headlineSmall
                    )
                },

                actions = {

                    IconButton(
                        onClick = onThemeToggle
                    ) {

                        Icon(
                            imageVector =
                                if (darkMode)
                                    Icons.Default.LightMode
                                else
                                    Icons.Default.DarkMode,

                            contentDescription = "Theme Toggle"
                        )
                    }

                    TextButton(
                        onClick = onLogout
                    ) {

                        Text("Logout")
                    }
                }
            )
        },

        floatingActionButton = {

            ExtendedFloatingActionButton(

                onClick = goToAddExpense,

                icon = {
                    Icon(Icons.Default.Add, null)
                },

                text = {
                    Text("Add Expense")
                }
            )
        }

    ) { paddingValues ->

        Column(

            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(20.dp)
                .verticalScroll(rememberScrollState())

        ) {

            // ---------------- SUMMARY CARD ----------------

            ElevatedCard(
                modifier = Modifier.fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier.padding(24.dp)
                ) {

                    Text(
                        "Remaining Balance",
                        style = MaterialTheme.typography.labelLarge
                    )

                    Text(
                        "R %.2f".format(remainingBalance),
                        style = MaterialTheme.typography.displaySmall,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    LinearProgressIndicator(
                        progress = {

                            val max =
                                maxGoal.toFloatOrNull() ?: 1f

                            val current =
                                remainingBalance.toFloat()

                            (current / max)
                                .coerceIn(0f, 1f)
                        },

                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp),

                        strokeCap = StrokeCap.Round
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        "Min Goal: R$minGoal"
                    )

                    Text(
                        "Max Goal: R$maxGoal"
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ---------------- INCOME ----------------

            OutlinedTextField(

                value = income,

                onValueChange = {
                    income = it
                },

                label = {
                    Text("Monthly Income")
                },

                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            // ---------------- MIN GOAL ----------------

            OutlinedTextField(

                value = minGoal,

                onValueChange = {
                    minGoal = it
                },

                label = {
                    Text("Minimum Goal")
                },

                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            // ---------------- MAX GOAL ----------------

            OutlinedTextField(

                value = maxGoal,

                onValueChange = {
                    maxGoal = it
                },

                label = {
                    Text("Maximum Goal")
                },

                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            // ---------------- SAVE BUTTON ----------------

            Button(

                onClick = {

                    when {

                        income.isBlank() -> {
                            errorMessage =
                                "Income is required"
                        }

                        minGoal.isBlank() -> {
                            errorMessage =
                                "Minimum goal is required"
                        }

                        maxGoal.isBlank() -> {
                            errorMessage =
                                "Maximum goal is required"
                        }

                        else -> {

                            scope.launch {

                                try {

                                    dao.updateUserGoals(

                                        username =
                                            loggedInUsername ?: "",

                                        income =
                                            income.toDouble(),

                                        minGoal =
                                            minGoal.toDouble(),

                                        maxGoal =
                                            maxGoal.toDouble()
                                    )

                                    remainingBalance =
                                        income.toDouble()

                                    successMessage =
                                        "Goals saved successfully"

                                    errorMessage = ""

                                } catch (e: Exception) {

                                    errorMessage =
                                        "Failed to save goals"
                                }
                            }
                        }
                    }
                },

                modifier = Modifier.fillMaxWidth()

            ) {

                Text("Save Goals")
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (errorMessage.isNotEmpty()) {

                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error
                )
            }

            if (successMessage.isNotEmpty()) {

                Text(
                    text = successMessage,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ---------------- QUICK ACTIONS ----------------

            Text(
                "Quick Actions",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedCard(
                onClick = goToCategories,
                modifier = Modifier.fillMaxWidth()
            ) {

                ListItem(

                    headlineContent = {
                        Text("Categories")
                    },

                    supportingContent = {
                        Text("Manage categories")
                    },

                    leadingContent = {
                        Icon(Icons.Default.List, null)
                    },

                    trailingContent = {
                        Icon(
                            Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            null
                        )
                    }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedCard(
                onClick = goToReports,
                modifier = Modifier.fillMaxWidth()
            ) {

                ListItem(

                    headlineContent = {
                        Text("Reports")
                    },

                    supportingContent = {
                        Text("View spending reports")
                    },

                    leadingContent = {
                        Icon(Icons.Default.Info, null)
                    },

                    trailingContent = {
                        Icon(
                            Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            null
                        )
                    }
                )
            }
        }
    }
}

//Android Developers, 2024. *Jetpack Compose documentation*. [online] Available at: https://developer.android.com/jetpack/compose [Accessed 29 April 2026].
//
//Android Developers, 2024. *Navigation in Jetpack Compose*. [online] Available at: https://developer.android.com/jetpack/compose/navigation [Accessed 29 April 2026].
//
//Android Developers, 2024. *Room Persistence Library*. [online] Available at: https://developer.android.com/training/data-storage/room [Accessed 29 April 2026].
//
//Google, 2024. *Material Design 3 Guidelines*. [online] Available at: https://m3.material.io/ [Accessed 29 April 2026].
//
//Kotlin, 2024. *Kotlin Programming Language Documentation*. [online] Available at: https://kotlinlang.org/docs/home.html [Accessed 29 April 2026].
//
//
//Google, 2026. *Google Gemini*. [online] Available at: https://gemini.google.com/share/fb3b1b39dd99 [Accessed 29 April 2026].
//
//Stack Overflow, 2024. *Android development discussions and solutions*. [online] Available at: https://stackoverflow.com/ [Accessed 29 April 2026].

//Artificial Intelligence (AI) tools were used during the development of this project to assist with research, debugging, code explanations, UI improvement suggestions, and Android development guidance.Google Gemini were used to help understand troubleshooting Android Studio errors.
//
//All generated content, code implementations, testing, modifications, and final decisions were reviewed, edited, and integrated by the student to meet the project requirements.
