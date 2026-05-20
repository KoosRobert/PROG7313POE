package com.example.poe.ui.theme.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.poe.data.local.DatabaseProvider
import com.example.poe.data.local.ExpenseEntity
import kotlinx.coroutines.launch

// ─────────────────────────────────────────────
//  ReportsScreen
//  • Filters expenses by a user-supplied date range
//  • Shows a scrollable list of matching entries
//  • Calculates and displays per-category totals
//  • Progress bars show each category vs. the max budget goal
// ─────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportsScreen(goBack: () -> Unit) {

    val context = LocalContext.current
    val scope   = rememberCoroutineScope()

    val dao = DatabaseProvider.getDatabase(context).expenseDao()

    // ── date range inputs ──────────────────────────────────
    var startDate by remember { mutableStateOf("") }
    var endDate   by remember { mutableStateOf("") }

    // ── results ───────────────────────────────────────────
    var expenses       by remember { mutableStateOf<List<ExpenseEntity>>(emptyList()) }
    var categoryTotals by remember { mutableStateOf<Map<String, Double>>(emptyMap()) }
    var maxGoal        by remember { mutableStateOf(0.0) }

    // ── UI state ──────────────────────────────────────────
    var errorMessage by remember { mutableStateOf("") }
    var hasSearched  by remember { mutableStateOf(false) }

    // ── date validation regex  YYYY-MM-DD ─────────────────
    val dateRegex = Regex("""\d{4}-\d{2}-\d{2}""")

    // ── helper: run the query ─────────────────────────────
    fun loadData() {
        errorMessage = ""

        when {
            startDate.isBlank() -> { errorMessage = "Start date is required"; return }
            endDate.isBlank()   -> { errorMessage = "End date is required";   return }
            !dateRegex.matches(startDate) -> {
                errorMessage = "Start date must be YYYY-MM-DD"; return
            }
            !dateRegex.matches(endDate) -> {
                errorMessage = "End date must be YYYY-MM-DD"; return
            }
            startDate > endDate -> {
                errorMessage = "Start date must be before end date"; return
            }
        }

        scope.launch {
            try {
                // 1. Fetch filtered expenses
                val result = dao.getExpensesBetweenDates(startDate, endDate)
                    .let { flow ->
                        // getExpensesBetweenDates returns a Flow – collect one emission
                        var list = emptyList<ExpenseEntity>()
                        flow.collect { list = it }
                        list
                    }
                expenses = result

                // 2. Build category → total map
                categoryTotals = result
                    .groupBy { it.category }
                    .mapValues { (_, items) -> items.sumOf { it.amount } }

                // 3. Try to read the user's max goal for the progress bars
                //    (stored on the UserEntity – we grab it via the session)
                val sessionManager = com.example.poe.data.local.SessionManager(context)
                val username = sessionManager.getLoggedInUser()
                if (username != null) {
                    val user = dao.getLoggedInUser(username)
                    maxGoal = user?.maxGoal ?: 0.0
                }

                hasSearched = true

            } catch (e: Exception) {
                errorMessage = "Error loading data: ${e.message}"
            }
        }
    }

    // ─────────────────────────────────────────────────────
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Spending Reports") },
                navigationIcon = {
                    IconButton(onClick = goBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor    = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            // ── Date range inputs ──────────────────────────
            Text(
                "Select Date Range",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = startDate,
                onValueChange = { startDate = it },
                label       = { Text("Start Date") },
                placeholder = { Text("YYYY-MM-DD") },
                modifier    = Modifier.fillMaxWidth(),
                singleLine  = true
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = endDate,
                onValueChange = { endDate = it },
                label       = { Text("End Date") },
                placeholder = { Text("YYYY-MM-DD") },
                modifier    = Modifier.fillMaxWidth(),
                singleLine  = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick  = { loadData() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                Text("Apply Filter")
            }

            // ── Error ──────────────────────────────────────
            if (errorMessage.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text  = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ── Results (shown only after a successful search) ──
            if (hasSearched) {

                // ── Category Totals Card ───────────────────
                ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(20.dp)) {

                        Text(
                            "Category Totals",
                            style      = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        if (categoryTotals.isEmpty()) {
                            Text(
                                "No expenses found for this period.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        } else {
                            categoryTotals.forEach { (category, total) ->
                                val progress = if (maxGoal > 0)
                                    (total / maxGoal).coerceIn(0.0, 1.0).toFloat()
                                else 0f

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment     = Alignment.CenterVertically
                                ) {
                                    Text(
                                        category,
                                        style    = MaterialTheme.typography.bodyMedium,
                                        modifier = Modifier.weight(1f)
                                    )
                                    Text(
                                        "R %.2f".format(total),
                                        style      = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Medium,
                                        color      = MaterialTheme.colorScheme.primary
                                    )
                                }

                                LinearProgressIndicator(
                                    progress  = { progress },
                                    modifier  = Modifier
                                        .fillMaxWidth()
                                        .height(6.dp)
                                        .padding(bottom = 4.dp),
                                    strokeCap = StrokeCap.Round
                                )

                                Spacer(modifier = Modifier.height(6.dp))
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // ── Expense List ───────────────────────────
                Text(
                    "Entries (${expenses.size})",
                    style      = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(10.dp))

                if (expenses.isEmpty()) {
                    Text(
                        "No entries found for this period.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                } else {
                    // Use a fixed-height list instead of LazyColumn inside scroll
                    expenses.forEach { expense ->
                        ExpenseListItem(expense)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

// ── Reusable expense card ─────────────────────────────────
@Composable
private fun ExpenseListItem(expense: ExpenseEntity) {
    OutlinedCard(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {

            Row(
                modifier              = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment     = Alignment.CenterVertically
            ) {
                Text(
                    expense.description,
                    style      = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    modifier   = Modifier.weight(1f)
                )
                Text(
                    "R %.2f".format(expense.amount),
                    style      = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color      = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier              = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    expense.category,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    expense.date,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            if (expense.startTime.isNotBlank() || expense.endTime.isNotBlank()) {
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    "${expense.startTime} – ${expense.endTime}",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
