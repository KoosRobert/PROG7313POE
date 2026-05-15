package com.example.poe.ui.theme.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesScreen(goBack: () -> Unit) {

    var categoryName by remember { mutableStateOf("") }

    val categories = remember {
        mutableStateListOf(
            "Groceries",
            "Transport",
            "Entertainment"
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Categories") }
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
                label = { Text("New Category") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = {
                    if (categoryName.isNotBlank()) {
                        categories.add(categoryName)
                        categoryName = ""
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add Category")
            }

            Spacer(modifier = Modifier.height(20.dp))

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
}