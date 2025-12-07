package com.example.virtualcloset.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.virtualcloset.utils.ImageUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddClothingScreen(
    onSave: (name: String, category: String, uri: Uri) -> Unit
) {
    val context = LocalContext.current
    var name by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var expanded by remember { mutableStateOf(false) }

    // Predefined categories
    val categories = listOf(
        "Shirts",
        "T-Shirts",
        "Tops",
        "Pants",
        "Jeans",
        "Shorts",
        "Dresses",
        "Skirts",
        "Jackets",
        "Coats",
        "Sweaters",
        "Hoodies",
        "Hats",
        "Caps",
        "Shoes",
        "Sneakers",
        "Boots",
        "Accessories",
        "Other"
    )

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            // Take persistable URI permission
            ImageUtils.takePersistableUriPermission(context, it)
            imageUri = it
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Add New Clothing Item",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Item Name") },
            placeholder = { Text("e.g., Blue Denim Jacket") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // Category Dropdown
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it }
        ) {
            OutlinedTextField(
                value = category,
                onValueChange = {},
                readOnly = true,
                label = { Text("Category") },
                placeholder = { Text("Select a category") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                categories.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(item) },
                        onClick = {
                            category = item
                            expanded = false
                        }
                    )
                }
            }
        }

        // Image picker button
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                if (imageUri != null) {
                    Image(
                        painter = rememberAsyncImagePainter(imageUri),
                        contentDescription = "Selected image",
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Text(
                        text = "No image selected",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        Button(
            onClick = { launcher.launch("image/*") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = if (imageUri != null) "Change Image" else "Select Image")
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                if (imageUri != null) {
                    onSave(name, category, imageUri!!)
                }
            },
            enabled = name.isNotBlank() && category.isNotBlank() && imageUri != null,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Clothing Item")
        }
    }
}