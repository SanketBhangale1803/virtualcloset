package com.example.virtualcloset.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.rememberAsyncImagePainter
import com.example.virtualcloset.data.model.ClothingItem
import com.example.virtualcloset.data.model.Outfit

@Composable
fun SavedOutfitsScreen(outfits: List<Outfit>, clothes: List<ClothingItem>) {
    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        items(outfits) { outfit ->
            Card(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                Row(modifier = Modifier.padding(8.dp), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    val itemIds = outfit.itemIds.split(",").mapNotNull { it.toLongOrNull() }
                    itemIds.forEach { id ->
                        clothes.find { it.id == id }?.let { item ->
                            Image(
                                painter = rememberAsyncImagePainter(item.imageUri.toUri()),
                                contentDescription = item.name,
                                modifier = Modifier.size(80.dp)
                            )
                        }
                    }
                    Text(outfit.name, modifier = Modifier.padding(start = 8.dp))
                }
            }
        }
    }
}