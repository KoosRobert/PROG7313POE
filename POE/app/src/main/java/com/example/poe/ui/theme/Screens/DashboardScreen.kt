package com.example.poe.ui.theme.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
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
    goToReports: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("BudgetBuddy", style = MaterialTheme.typography.headlineSmall) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = goToAddExpense,
                icon = { Icon(Icons.Default.Add, contentDescription = null) },
                text = { Text("Add Expense") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // Financial Summary Card
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.elevatedCardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text("Remaining Balance", style = MaterialTheme.typography.labelLarge)
                    Text(
                        "R6,500.00",
                        style = MaterialTheme.typography.displaySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    // Visual Budget Progress
                    LinearProgressIndicator(
                        progress = { 0.35f },
                        modifier = Modifier.fillMaxWidth().height(8.dp),
                        strokeCap = StrokeCap.Round,
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Spent: R3,500", style = MaterialTheme.typography.bodySmall)
                        Text("Limit: R10,000", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
            Text("Quick Actions", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(16.dp))

            // Professional Navigation Grid/List
            OutlinedCard(
                onClick = goToCategories,
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
            ) {
                ListItem(
                    headlineContent = { Text("Categories") },
                    supportingContent = { Text("Manage where your money goes") },
                    leadingContent = { Icon(Icons.Default.List, contentDescription = null) },
                    trailingContent = { Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, null) }
                )
            }

            OutlinedCard(
                onClick = goToReports,
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
            ) {
                ListItem(
                    headlineContent = { Text("Spending Reports") },
                    supportingContent = { Text("Analyze monthly trends") },
                    leadingContent = { Icon(Icons.Default.Info, contentDescription = null) },
                    trailingContent = { Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, null) }
                )
            }
        }
    }
}