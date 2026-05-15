package com.example.poe.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Dashboard : BottomNavItem("dashboard", "Dashboard", Icons.Default.Home)
    object Expenses : BottomNavItem("addExpense", "Expenses", Icons.Default.Add)
    object Categories : BottomNavItem("categories", "Categories", Icons.Default.List)
    object Reports : BottomNavItem("reports", "Reports", Icons.Default.Info)
}