package com.example.poe.ui.theme.Screens

import android.app.DatePickerDialog
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.poe.data.local.DatabaseProvider
import com.example.poe.data.local.ExpenseEntity
import kotlinx.coroutines.launch
import java.util.Calendar
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import com.example.poe.data.local.CategoryEntity
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseScreen(
    goBack: () -> Unit
) {

    var amount by remember { mutableStateOf("") }

    var description by remember { mutableStateOf("") }

    var category by remember { mutableStateOf("") }

    var date by remember { mutableStateOf("") }

    var errorMessage by remember { mutableStateOf("") }

    var successMessage by remember { mutableStateOf("") }

    val context = LocalContext.current

    val scope = rememberCoroutineScope()

    val database = DatabaseProvider.getDatabase(context)

    val expenseDao = database.expenseDao()

    // ---------------- CALENDAR ----------------

    val calendar = Calendar.getInstance()

    val datePickerDialog = DatePickerDialog(

        context,

        { _, year, month, dayOfMonth ->

            date =
                String.format(
                    "%04d-%02d-%02d",
                    year,
                    month + 1,
                    dayOfMonth
                )
        },

        calendar.get(Calendar.YEAR),

        calendar.get(Calendar.MONTH),

        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Scaffold(

        topBar = {

            TopAppBar(
                title = {
                    Text("Add Expense")
                }
            )
        }

    ) { padding ->

        Column(

            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp)
                .verticalScroll(rememberScrollState())

        ) {

            // ---------------- AMOUNT ----------------

            OutlinedTextField(

                value = amount,

                onValueChange = {
                    amount = it
                },

                label = {
                    Text("Amount")
                },

                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            // ---------------- DESCRIPTION ----------------

            OutlinedTextField(

                value = description,

                onValueChange = {
                    description = it
                },

                label = {
                    Text("Description")
                },

                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            // ---------------- CATEGORY ----------------

// ---------------- CATEGORY DROPDOWN ----------------

            var expanded by remember {
                mutableStateOf(false)
            }

            var categories by remember {
                mutableStateOf<List<CategoryEntity>>(emptyList())
            }

// LOAD CATEGORIES

            LaunchedEffect(Unit) {

                categories =
                    expenseDao.getCategoriesList()
            }

            ExposedDropdownMenuBox(

                expanded = expanded,

                onExpandedChange = {
                    expanded = !expanded
                }

            ) {

                OutlinedTextField(

                    value = category,

                    onValueChange = {},

                    readOnly = true,

                    label = {
                        Text("Category")
                    },

                    trailingIcon = {

                        Icon(

                            imageVector =
                                if (expanded)
                                    Icons.Default.ArrowDropUp
                                else
                                    Icons.Default.ArrowDropDown,

                            contentDescription = null
                        )
                    },

                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )

                ExposedDropdownMenu(

                    expanded = expanded,

                    onDismissRequest = {
                        expanded = false
                    }

                ) {

                    categories.forEach { categoryItem ->

                        DropdownMenuItem(

                            text = {
                                Text(categoryItem.name)
                            },

                            onClick = {

                                category =
                                    categoryItem.name

                                expanded = false
                            }
                        )
                    }
                }
            }

            // ---------------- DATE ----------------

            OutlinedTextField(

                value = date,

                onValueChange = {},

                readOnly = true,

                label = {
                    Text("Expense Date")
                },

                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(

                onClick = {
                    datePickerDialog.show()
                },

                modifier = Modifier.fillMaxWidth()

            ) {

                Text("Select Date")
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ---------------- SAVE BUTTON ----------------

            Button(

                onClick = {

                    when {

                        amount.isBlank() -> {

                            errorMessage =
                                "Amount is required"

                            successMessage = ""
                        }

                        amount.toDoubleOrNull() == null -> {

                            errorMessage =
                                "Enter valid amount"

                            successMessage = ""
                        }

                        amount.toDouble() <= 0 -> {

                            errorMessage =
                                "Amount must be greater than 0"

                            successMessage = ""
                        }

                        description.isBlank() -> {

                            errorMessage =
                                "Description required"

                            successMessage = ""
                        }

                        category.isBlank() -> {

                            errorMessage =
                                "Category required"

                            successMessage = ""
                        }

                        date.isBlank() -> {

                            errorMessage =
                                "Date required"

                            successMessage = ""
                        }

                        else -> {

                            scope.launch {

                                try {

                                    expenseDao.insertExpense(

                                        ExpenseEntity(

                                            amount =
                                                amount.toDouble(),

                                            description =
                                                description,

                                            category =
                                                category,

                                            date =
                                                date
                                        )
                                    )

                                    Toast.makeText(

                                        context,

                                        "Expense Saved",

                                        Toast.LENGTH_SHORT

                                    ).show()

                                    successMessage =
                                        "Expense added successfully"

                                    errorMessage = ""

                                    amount = ""
                                    description = ""
                                    category = ""
                                    date = ""

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

            Spacer(modifier = Modifier.height(12.dp))

            // ---------------- ERROR ----------------

            if (errorMessage.isNotEmpty()) {

                Text(

                    text = errorMessage,

                    color =
                        MaterialTheme.colorScheme.error
                )
            }

            // ---------------- SUCCESS ----------------

            if (successMessage.isNotEmpty()) {

                Text(

                    text = successMessage,

                    color =
                        MaterialTheme.colorScheme.primary
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