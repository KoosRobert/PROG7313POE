package com.example.poe.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
import com.example.poe.ui.theme.Screens.*


@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {

        composable("login") {
            LoginScreen(
                onLoginClick = {
                    navController.navigate("dashboard")
                },
                onRegisterClick = {
                    navController.navigate("register")
                }
            )
        }

        composable("dashboard") {
            DashboardScreen(
                goToAddExpense = {
                    navController.navigate("addExpense")
                },
                goToCategories = {
                    navController.navigate("categories")
                },
                goToReports = {
                    navController.navigate("reports")
                }
            )
        }

        composable("addExpense") {
            AddExpenseScreen(
                goBack = {
                    navController.popBackStack()
                }
            )
        }

        composable("categories") {
            CategoriesScreen(
                goBack = {
                    navController.popBackStack()
                }
            )
        }

        composable("reports") {
            ReportsScreen(
                goBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}