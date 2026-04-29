package com.example.poe.ui.theme.Screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.poe.data.local.DatabaseProvider
import com.example.poe.data.local.ExpenseEntity
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseScreen(goBack: () -> Unit) {

    var amount by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var startTime by remember { mutableStateOf("") }
    var endTime by remember { mutableStateOf("") }

    var errorMessage by remember { mutableStateOf("") }
    var successMessage by remember { mutableStateOf("") }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val database = DatabaseProvider.getDatabase(context)
    val expenseDao = database.expenseDao()

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

                    when {

                        amount.isBlank() -> {
                            errorMessage = "Amount is required"
                            successMessage = ""
                        }
                        //////here
                        amount.toDoubleOrNull() == null -> {

                            errorMessage =
                                "Enter a valid numeric amount"

                            successMessage = ""
                        }

                        amount.toDouble() <= 0 -> {

                            errorMessage =
                                "Amount must be greater than 0"

                            successMessage = ""
                        }

                        description.isBlank() -> {
                            errorMessage = "Description is required"
                            successMessage = ""
                        }

                        category.isBlank() -> {
                            errorMessage = "Category is required"
                            successMessage = ""
                        }

                        date.isBlank() -> {
                            errorMessage = "Date is required"
                            successMessage = ""
                        }

                        startTime.isBlank() -> {
                            errorMessage = "Start time is required"
                            successMessage = ""
                        }

                        endTime.isBlank() -> {
                            errorMessage = "End time is required"
                            successMessage = ""
                        }

                        else -> {

                            errorMessage = ""

                            scope.launch {

                                try {

                                    expenseDao.insertExpense(
                                        ExpenseEntity(
                                            amount = amount.toDouble(),
                                            description = description,
                                            category = category,
                                            date = date,
                                            startTime = startTime,
                                            endTime = endTime
                                        )
                                    )

                                    successMessage =
                                        "Expense saved successfully"

                                    Toast.makeText(
                                        context,
                                        "Expense Saved",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    amount = ""
                                    description = ""
                                    category = ""
                                    date = ""
                                    startTime = ""
                                    endTime = ""

                                } catch (e: Exception) {

                                    errorMessage =
                                        "Failed to save expense"
                                }
                            }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Expense")
            }

            Spacer(modifier = Modifier.height(10.dp))

            if (errorMessage.isNotEmpty()) {

                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error
                )
            }

            if (successMessage.isNotEmpty()) {

                Text(
                    text = successMessage,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = goBack,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Back")
            }
        }
    }
}