package com.example.poe.ui.theme.Screens

<<<<<<< HEAD
import android.app.DatePickerDialog
=======
>>>>>>> main
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
<<<<<<< HEAD
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.poe.data.local.DatabaseProvider
import com.example.poe.data.local.ExpenseEntity
import kotlinx.coroutines.flow.collectLatest
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportsScreen(
    goBack: () -> Unit
) {

    val context = LocalContext.current

    val database = DatabaseProvider.getDatabase(context)

    val dao = database.expenseDao()

    var allExpenses by remember {
        mutableStateOf<List<ExpenseEntity>>(emptyList())
    }

    var filteredExpenses by remember {
        mutableStateOf<List<ExpenseEntity>>(emptyList())
    }

    var startDate by remember {
        mutableStateOf("")
    }

    var endDate by remember {
        mutableStateOf("")
    }

    // ---------------- LOAD ALL EXPENSES ----------------

    LaunchedEffect(Unit) {

        dao.getAllExpenses().collectLatest {

            allExpenses = it

            filteredExpenses = it
        }
    }

    // ---------------- CALENDAR ----------------

    val calendar = Calendar.getInstance()

    val startDatePicker = DatePickerDialog(

        context,

        { _, year, month, dayOfMonth ->

            startDate =
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

    val endDatePicker = DatePickerDialog(

        context,

        { _, year, month, dayOfMonth ->

            endDate =
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

    // ---------------- TOTAL ----------------

    val totalSpent =
        filteredExpenses.sumOf { it.amount }

    // ---------------- CATEGORY TOTALS ----------------

    val groupedCategories =
        filteredExpenses.groupBy { it.category }

    Scaffold(

        topBar = {

            TopAppBar(

                title = {
                    Text("Spending Reports")
                },

                navigationIcon = {

                    IconButton(
                        onClick = goBack
                    ) {

                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
=======
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
fun ReportsScreen(goBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Spending Reports") },
                navigationIcon = {
                    IconButton(onClick = goBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
>>>>>>> main
                    }
                }
            )
        }
<<<<<<< HEAD

    ) { padding ->

        Column(

            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp)

        ) {

            // ---------------- DATE FILTERS ----------------

            OutlinedTextField(

                value = startDate,

                onValueChange = {},

                readOnly = true,

                label = {
                    Text("Start Date")
                },

                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(

                onClick = {
                    startDatePicker.show()
                },

                modifier = Modifier.fillMaxWidth()

            ) {

                Text("Select Start Date")
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(

                value = endDate,

                onValueChange = {},

                readOnly = true,

                label = {
                    Text("End Date")
                },

                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(

                onClick = {
                    endDatePicker.show()
                },

                modifier = Modifier.fillMaxWidth()

            ) {

                Text("Select End Date")
=======
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(20.dp)) {

            Text("Select View Period", style = MaterialTheme.typography.titleSmall)
            Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedCard(modifier = Modifier.weight(1f), onClick = {}) {
                    Text("From: 01/03/26", modifier = Modifier.padding(12.dp), style = MaterialTheme.typography.bodySmall)
                }
                OutlinedCard(modifier = Modifier.weight(1f), onClick = {}) {
                    Text("To: 31/03/26", modifier = Modifier.padding(12.dp), style = MaterialTheme.typography.bodySmall)
                }
>>>>>>> main
            }

            Spacer(modifier = Modifier.height(16.dp))

<<<<<<< HEAD
            // ---------------- SEARCH BUTTON ----------------

            Button(

                onClick = {

                    filteredExpenses =
                        allExpenses.filter {

                            if (
                                startDate.isBlank() ||
                                endDate.isBlank()
                            ) {

                                true

                            } else {

                                it.date >= startDate &&
                                        it.date <= endDate
                            }
                        }
                },

                modifier = Modifier.fillMaxWidth()

            ) {

                Text("Search")
            }

            Spacer(modifier = Modifier.height(20.dp))

            // ---------------- TOTAL CARD ----------------

            ElevatedCard(
                modifier = Modifier.fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier.padding(20.dp)
                ) {

                    Text("Total Spent")

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        "R %.2f".format(totalSpent),
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // ---------------- GRAPH ----------------

            Text(
                "Expense Graph",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(

                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),

                horizontalArrangement =
                    Arrangement.spacedBy(8.dp),

                verticalAlignment =
                    Alignment.Bottom

            ) {

                filteredExpenses.take(7).forEach { expense ->

                    val graphHeight =
                        (expense.amount * 3)
                            .coerceAtMost(180.0)
                            .dp

                    Column(
                        horizontalAlignment =
                            Alignment.CenterHorizontally
                    ) {

                        Box(

                            modifier = Modifier
                                .width(35.dp)
                                .height(graphHeight)
                                .background(
                                    MaterialTheme.colorScheme.primary,
                                    RoundedCornerShape(8.dp)
                                )
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            expense.category.take(3)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // ---------------- CATEGORY TOTALS ----------------

            Text(
                "Category Totals",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(12.dp))

            groupedCategories.forEach { (category, expenses) ->

                val categoryTotal =
                    expenses.sumOf { it.amount }

                Card(

                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)

                ) {

                    Row(

                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),

                        horizontalArrangement =
                            Arrangement.SpaceBetween

                    ) {

                        Text(category)

                        Text(
                            "R %.2f".format(categoryTotal)
                        )
=======
            // Graph Card
            ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Daily Spending Graph", style = MaterialTheme.typography.labelLarge)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .padding(top = 12.dp)
                            .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Chart visualization will appear here", color = MaterialTheme.colorScheme.onSurfaceVariant)
>>>>>>> main
                    }
                }
            }

<<<<<<< HEAD
            Spacer(modifier = Modifier.height(20.dp))

            // ---------------- SCROLLABLE EXPENSE LIST ----------------

            Text(
                "Expenses",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn(

                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)

            ) {

                items(filteredExpenses) { expense ->

                    Card(

                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)

                    ) {

                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {

                            Text(
                                expense.description,
                                style = MaterialTheme.typography.titleSmall
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text("Category: ${expense.category}")

                            Text("Date: ${expense.date}")

                            Text(
                                "Amount: R ${expense.amount}"
                            )
                        }
                    }
                }
            }
=======
            Spacer(modifier = Modifier.height(16.dp))

            // Category Summary
            Text("Category Trends", style = MaterialTheme.typography.titleSmall)
            LinearProgressIndicator(progress = { 0.7f }, modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp))
            Text("Groceries: R 2,500 of R 3,000", style = MaterialTheme.typography.bodySmall)
>>>>>>> main
        }
    }
}