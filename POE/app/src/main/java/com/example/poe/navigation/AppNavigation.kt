package com.example.poe.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.*
import androidx.compose.material3.Scaffold
import com.example.poe.data.local.SessionManager
import com.example.poe.ui.theme.Screens.*

@Composable
fun AppNavigation() {

    val navController = rememberNavController()
    val context = LocalContext.current
    val sessionManager = SessionManager(context)

    val startDestination =
        if (sessionManager.isLoggedIn()) "dashboard" else "login"

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        // ---------------- LOGIN ----------------

        composable("login") {

            LoginScreen(

                onLoginSuccess = {

                    navController.navigate("dashboard") {

                        popUpTo("login") { inclusive = true }
                    }
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

                    navController.navigate("login") {

                        popUpTo("register") { inclusive = true }
                    }
                },

                goToLogin = {
                    navController.popBackStack()
                }
            )
        }

        // ---------------- DASHBOARD ----------------

        composable("dashboard") {

            Scaffold(
                bottomBar = {
                    BottomNavBar(navController)
                }
            ) { _ ->

                DashboardScreen(

                    goToAddExpense = {
                        navController.navigate("addExpense")
                    },

                    goToCategories = {
                        navController.navigate("categories")
                    },

                    goToReports = {
                        navController.navigate("reports")
                    },

                    onLogout = {

                        sessionManager.logout()

                        navController.navigate("login") {
                            popUpTo(0)
                        }
                    }
                )
            }
        }

        // ---------------- ADD EXPENSE ----------------

        composable("addExpense") {
            AddExpenseScreen(
                goBack = { navController.popBackStack() }
            )
        }

        // ---------------- CATEGORIES ----------------

        composable("categories") {
            CategoriesScreen(
                goBack = { navController.popBackStack() }
            )
        }

        // ---------------- REPORTS ----------------

        composable("reports") {
            ReportsScreen(
                goBack = { navController.popBackStack() }
            )
        }
    }
}