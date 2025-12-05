package com.example.virtualcloset.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.virtualcloset.data.model.ClothingItem

@Composable
fun ClosetScreen(
    clothes: List<ClothingItem>,
    onDelete: ((ClothingItem) -> Unit)? = null
) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("My Closet", style = MaterialTheme.typography.headlineSmall, modifier = Modifier.padding(bottom = 12.dp))
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(clothes) { item ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(item.name, style = MaterialTheme.typography.bodyLarge)
                            Text(item.category, style = MaterialTheme.typography.bodySmall)
                        }
                        onDelete?.let {
                            Text(
                                "Delete",
                                color = MaterialTheme.colorScheme.error,
                                modifier = Modifier.clickable { it(item) }
                            )
                        }
                    }
                }
            }
        }
    }
}