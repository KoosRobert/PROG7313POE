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

        // ---------------- LOGIN ----------------

        composable("login") {

            LoginScreen(

                onLoginSuccess = {
                    navController.navigate("dashboard")
                },

                onRegisterClick = {
                    navController.navigate("register")
                }
            )
        }

        // ---------------- REGISTER ----------------

        composable("register") {

            RegisterScreen(

                onRegisterSuccess = {
                    navController.navigate("dashboard")
                },

                goToLogin = {
                    navController.popBackStack()
                }
            )
        }

        // ---------------- DASHBOARD ----------------

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

        // ---------------- ADD EXPENSE ----------------

        composable("addExpense") {

            AddExpenseScreen(

                goBack = {
                    navController.popBackStack()
                }
            )
        }

        // ---------------- CATEGORIES ----------------

        composable("categories") {

            CategoriesScreen(

                goBack = {
                    navController.popBackStack()
                }
            )
        }

        // ---------------- REPORTS ----------------

        composable("reports") {

            ReportsScreen(

                goBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}