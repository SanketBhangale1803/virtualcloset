package com.example.virtualcloset.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AddClothingScreen1(onSave: (name: String, category: String, uri: String) -> Unit) {
    var name by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var uri by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Add Clothing Item", style = MaterialTheme.typography.headlineSmall)
        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Name") })
        OutlinedTextField(value = category, onValueChange = { category = it }, label = { Text("Category") })
        OutlinedTextField(value = uri, onValueChange = { uri = it }, label = { Text("Image URI") })
        Button(onClick = { onSave(name, category, uri) }, enabled = name.isNotBlank() && uri.isNotBlank() && category.isNotBlank()) {
            Text("Save")
        }
    }
}