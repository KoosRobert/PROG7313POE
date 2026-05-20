package com.example.poe.ui.theme.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.poe.data.local.CategoryEntity
import com.example.poe.data.local.DatabaseProvider
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

// ─────────────────────────────────────────────
//  CategoriesScreen
//  • Loads categories from Room DB on launch
//  • Persists new categories to DB
//  • Allows deletion of existing categories
// ─────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesScreen(goBack: () -> Unit) {

    val context = LocalContext.current
    val scope   = rememberCoroutineScope()
    val dao     = DatabaseProvider.getDatabase(context).expenseDao()

    var categoryName  by remember { mutableStateOf("") }
    var errorMessage  by remember { mutableStateOf("") }
    val categories    = remember { mutableStateListOf<CategoryEntity>() }

    // ── Observe categories from DB ─────────────────────────
    LaunchedEffect(Unit) {
        dao.getAllCategories().collectLatest { dbCategories ->
            categories.clear()
            categories.addAll(dbCategories)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Categories") },
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
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {

            // ── Add Category ──────────────────────────────
            Text(
                "Add Category",
                style      = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value         = categoryName,
                onValueChange = {
                    categoryName = it
                    errorMessage = ""
                },
                label      = { Text("Category Name") },
                modifier   = Modifier.fillMaxWidth(),
                singleLine = true
            )

            if (errorMessage.isNotEmpty()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    when {
                        categoryName.isBlank() -> {
                            errorMessage = "Category name cannot be empty"
                        }
                        categories.any {
                            it.name.equals(categoryName.trim(), ignoreCase = true)
                        } -> {
                            errorMessage = "Category already exists"
                        }
                        else -> {
                            scope.launch {
                                dao.insertCategory(
                                    CategoryEntity(name = categoryName.trim())
                                )
                                categoryName = ""
                                errorMessage = ""
                            }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                Text("Add Category")
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ── Category List ─────────────────────────────
            Text(
                "Your Categories (${categories.size})",
                style      = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(10.dp))

            if (categories.isEmpty()) {
                Text(
                    "No categories yet. Add one above.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier            = Modifier.weight(1f)
                ) {
                    items(categories, key = { it.id }) { category ->
                        OutlinedCard(modifier = Modifier.fillMaxWidth()) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment     = Alignment.CenterVertically
                            ) {
                                Text(
                                    category.name,
                                    style    = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier.weight(1f)
                                )
                                IconButton(
                                    onClick = {
                                        scope.launch {
                                            dao.deleteCategory(category)
                                        }
                                    }
                                ) {
                                    Icon(
                                        Icons.Default.Delete,
                                        contentDescription = "Delete ${category.name}",
                                        tint               = MaterialTheme.colorScheme.error
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}