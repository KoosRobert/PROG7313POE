package com.example.poe.ui.theme.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseScreen(goBack: () -> Unit) {

    var amount by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var startTime by remember { mutableStateOf("") }
    var endTime by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Expense") }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp)
        ) {

            OutlinedTextField(
                value = amount,
                onValueChange = { amount = it },
                label = { Text("Amount") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = category,
                onValueChange = { category = it },
                label = { Text("Category") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = date,
                onValueChange = { date = it },
                label = { Text("Date") },
                placeholder = { Text("YYYY-MM-DD") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = startTime,
                onValueChange = { startTime = it },
                label = { Text("Start Time") },
                placeholder = { Text("HH:MM") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = endTime,
                onValueChange = { endTime = it },
                label = { Text("End Time") },
                placeholder = { Text("HH:MM") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    // DATABASE SAVE LATER
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Expense")
            }

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = goBack,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Back")
            }
        }
    }
}