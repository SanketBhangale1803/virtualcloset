package com.example.virtualcloset.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.virtualcloset.data.model.ClothingItem
import androidx.core.net.toUri

@Composable
fun ClosetScreen(
    clothes: List<ClothingItem>,
    onDelete: (ClothingItem) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(clothes) { item ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Row(modifier = Modifier.padding(12.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Image(
                        painter = rememberAsyncImagePainter(item.imageUri.toUri()),
                        contentDescription = item.name,
                        modifier = Modifier.size(80.dp)
                    )
                    Column(modifier = Modifier.weight(1f)) {
                        Text(item.name, style = MaterialTheme.typography.titleMedium)
                        Text(item.category, style = MaterialTheme.typography.bodyMedium)
                    }
                    Button(onClick = { onDelete(item) }) {
                        Text("Delete")
                    }
                }
            }
        }
    }
}