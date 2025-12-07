package com.example.virtualcloset.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@Composable
fun AddClothingScreen(
    onSave: (name: String, category: String, uri: Uri) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? -> imageUri = uri }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Name") })
        OutlinedTextField(value = category, onValueChange = { category = it }, label = { Text("Category") })
        Button(onClick = { launcher.launch("image/*") }) {
            Text(text = if (imageUri != null) "Change Image" else "Pick Image")
        }
        imageUri?.let {
            Image(painter = rememberAsyncImagePainter(it), contentDescription = null, modifier = Modifier.size(150.dp))
        }
        Button(
            onClick = { if(imageUri != null) onSave(name, category, imageUri!!) },
            enabled = name.isNotBlank() && category.isNotBlank() && imageUri != null
        ) {
            Text("Save Clothing")
        }
    }
}