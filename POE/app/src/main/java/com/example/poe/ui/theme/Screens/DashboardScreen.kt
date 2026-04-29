package com.example.poe.ui.theme.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp

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
                },

                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(

                    containerColor =
                        MaterialTheme.colorScheme.primaryContainer
                )
            )
        },

        floatingActionButton = {

            ExtendedFloatingActionButton(

                onClick = goToAddExpense,

                icon = {

                    Icon(
                        Icons.Default.Add,
                        contentDescription = null
                    )
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
                .padding(horizontal = 20.dp)

        ) {

            Spacer(modifier = Modifier.height(24.dp))

            // ---------------- SUMMARY CARD ----------------

            ElevatedCard(

                modifier = Modifier.fillMaxWidth(),

                colors = CardDefaults.elevatedCardColors(

                    containerColor =
                        MaterialTheme.colorScheme.surfaceVariant
                )
            ) {

                Column(
                    modifier = Modifier.padding(24.dp)
                ) {

                    Text(

                        "Remaining Balance",

                        style =
                            MaterialTheme.typography.labelLarge
                    )

                    Text(

                        "R6,500.00",

                        style =
                            MaterialTheme.typography.displaySmall,

                        color =
                            MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    LinearProgressIndicator(

                        progress = { 0.35f },

                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp),

                        strokeCap = StrokeCap.Round
                    )

                    Row(

                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),

                        horizontalArrangement =
                            Arrangement.SpaceBetween

                    ) {

                        Text(
                            "Spent: R3,500",
                            style =
                                MaterialTheme.typography.bodySmall
                        )

                        Text(
                            "Limit: R10,000",
                            style =
                                MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(

                "Quick Actions",

                style =
                    MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            // ---------------- CATEGORIES ----------------

            OutlinedCard(

                onClick = goToCategories,

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)

            ) {

                ListItem(

                    headlineContent = {
                        Text("Categories")
                    },

                    supportingContent = {
                        Text("Manage where your money goes")
                    },

                    leadingContent = {

                        Icon(
                            Icons.Default.List,
                            contentDescription = null
                        )
                    },

                    trailingContent = {

                        Icon(
                            Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            null
                        )
                    }
                )
            }

            // ---------------- REPORTS ----------------

            OutlinedCard(

                onClick = goToReports,

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)

            ) {

                ListItem(

                    headlineContent = {
                        Text("Spending Reports")
                    },

                    supportingContent = {
                        Text("Analyze monthly trends")
                    },

                    leadingContent = {

                        Icon(
                            Icons.Default.Info,
                            contentDescription = null
                        )
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
