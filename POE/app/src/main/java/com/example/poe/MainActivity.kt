package com.example.poe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import com.example.poe.ui.theme.Screens.*

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            // Start at LOGIN
            var currentScreen by remember { mutableStateOf(Screen.LOGIN) }

            when (currentScreen) {

                Screen.LOGIN -> LoginScreen(
                    onLoginClick = { currentScreen = Screen.DASHBOARD },
                    // Make sure your LoginScreen has a goToRegister parameter!
                    // If you didn't add it yet, add "onRegisterClick: () -> Unit" to LoginScreen
                    onRegisterClick = { currentScreen = Screen.REGISTER }
                )

                Screen.REGISTER -> RegisterScreen(
                    onRegisterSuccess = { currentScreen = Screen.DASHBOARD },
                    goToLogin = { currentScreen = Screen.LOGIN }
                )

                Screen.DASHBOARD -> DashboardScreen(
                    goToAddExpense = { currentScreen = Screen.ADD_EXPENSE },
                    goToCategories = { currentScreen = Screen.CATEGORIES },
                    goToReports = { currentScreen = Screen.REPORTS }
                )

                Screen.ADD_EXPENSE -> AddExpenseScreen(
                    goBack = { currentScreen = Screen.DASHBOARD }
                )

                Screen.CATEGORIES -> CategoriesScreen(
                    goBack = { currentScreen = Screen.DASHBOARD }
                )

                Screen.REPORTS -> ReportsScreen(
                    goBack = { currentScreen = Screen.DASHBOARD }
                )
            }
        }
    }
}