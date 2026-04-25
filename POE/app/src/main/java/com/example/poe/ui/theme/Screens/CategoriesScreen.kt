package com.example.poe.ui.theme.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Category
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesScreen(goBack: () -> Unit) {
    var totalMonthlyGoal by remember { mutableStateOf("10000") }
    val categories = listOf("Groceries", "Transport", "Entertainment", "Utilities")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Budget Goals") },
                navigationIcon = {
                    IconButton(onClick = goBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {

            Text("Set Total Monthly Budget", style = MaterialTheme.typography.titleMedium)
            OutlinedTextField(
                value = totalMonthlyGoal,
                onValueChange = { totalMonthlyGoal = it },
                prefix = { Text("R ") },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                label = { Text("Total Limit") }
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))
            Text("Category Limits", style = MaterialTheme.typography.titleMedium)

            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.weight(1f)) {
                items(categories) { category ->
                    OutlinedCard(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(category, fontWeight = FontWeight.Bold)
                                Text("Limit: R 0.00", style = MaterialTheme.typography.bodySmall)
                            }
                            TextButton(onClick = { /* Placeholder */ }) {
                                Text("Set Limit")
                            }
                        }
                    }
                }
            }

            Button(onClick = goBack, modifier = Modifier.fillMaxWidth().padding(top = 16.dp)) {
                Text("Save All Goals")
            }
        }
    }
}