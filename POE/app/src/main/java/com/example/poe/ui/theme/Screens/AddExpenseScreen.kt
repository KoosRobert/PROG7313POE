package com.example.poe.ui.theme.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Label
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Label
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseScreen(goBack: () -> Unit) {
    var amount by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("Select Date") }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Add Expense") }) }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(20.dp)) {

            // 1. Amount
            OutlinedTextField(
                value = amount,
                onValueChange = { amount = it },
                label = { Text("Amount (R)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            // 2. Date Selection (Required by brief)
            OutlinedCard(onClick = { /* Placeholder for DatePicker */ }, modifier = Modifier.fillMaxWidth()) {
                ListItem(
                    headlineContent = { Text(date) },
                    leadingContent = { Icon(Icons.Default.DateRange, null) }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 3. Category Selection (Required by brief)
            OutlinedCard(onClick = { /* Placeholder */ }, modifier = Modifier.fillMaxWidth()) {
                ListItem(
                    headlineContent = { Text("Select Category") },
                    trailingContent = { Icon(Icons.Default.ArrowDropDown, null) }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 4. Description
            OutlinedTextField(
                value = "", onValueChange = {},
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            // 5. Photo Attachment (Required by brief)
            OutlinedButton(
                onClick = { /* Placeholder */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.AddAPhoto, null)
                Spacer(Modifier.width(8.dp))
                Text("Attach Receipt Photo")
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(onClick = goBack, modifier = Modifier.fillMaxWidth()) {
                Text("Save Entry")
            }
        }
    }
}