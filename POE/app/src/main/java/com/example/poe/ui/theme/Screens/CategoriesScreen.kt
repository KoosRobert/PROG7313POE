<<<<<<< HEAD
package com.example.poe.ui.theme.Screens
=======
﻿package com.example.poe.ui.theme.Screens
>>>>>>> main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
<<<<<<< HEAD
import androidx.compose.ui.unit.dp
=======
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.poe.data.local.DatabaseProvider
import com.example.poe.data.local.CategoryEntity
import kotlinx.coroutines.launch
import android.widget.Toast
>>>>>>> main

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesScreen(goBack: () -> Unit) {

    var categoryName by remember { mutableStateOf("") }
<<<<<<< HEAD

    val categories = remember {
        mutableStateListOf(
            "Groceries",
            "Transport",
            "Entertainment"
        )
=======
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val database = DatabaseProvider.getDatabase(context)
    val expenseDao = database.expenseDao()

    var categories by remember { mutableStateOf<List<CategoryEntity>>(emptyList()) }

    LaunchedEffect(Unit) {
        categories = expenseDao.getCategoriesList()
>>>>>>> main
    }

    Scaffold(
        topBar = {
            TopAppBar(
<<<<<<< HEAD
                title = { Text("Categories") }
=======
                title = { 
                    Text(
                        text = "Categories",
                        fontSize = 20.sp
                    )
                }
>>>>>>> main
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
                value = categoryName,
                onValueChange = { categoryName = it },
<<<<<<< HEAD
                label = { Text("New Category") },
=======
                label = { Text("New Category Name") },
                placeholder = { Text("e.g., Groceries, Transport, Rent") },
>>>>>>> main
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = {
                    if (categoryName.isNotBlank()) {
<<<<<<< HEAD
                        categories.add(categoryName)
                        categoryName = ""
=======
                        scope.launch {
                            try {
                                expenseDao.insertCategory(
                                    CategoryEntity(name = categoryName.trim())
                                )
                                categories = expenseDao.getCategoriesList()
                                categoryName = ""
                                Toast.makeText(
                                    context,
                                    "Category added successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } catch (e: Exception) {
                                Toast.makeText(
                                    context,
                                    "Failed to add category: ${e.message}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    } else {
                        Toast.makeText(
                            context,
                            "Please enter a category name",
                            Toast.LENGTH_SHORT
                        ).show()
>>>>>>> main
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add Category")
            }

            Spacer(modifier = Modifier.height(20.dp))

<<<<<<< HEAD
            LazyColumn {

                items(categories) { category ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 5.dp)
                    ) {
                        Text(
                            text = category,
                            modifier = Modifier.padding(16.dp)
                        )
=======
            if (categories.isEmpty()) {
                Text(
                    text = "No categories yet. Add your first category above!",
                    color = MaterialTheme.colorScheme.error
                )
            } else {
                LazyColumn {
                    items(categories) { category ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 5.dp)
                        ) {
                            Text(
                                text = category.name,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
>>>>>>> main
                    }
                }
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
<<<<<<< HEAD
}
=======
}
>>>>>>> main
