package com.example.virtualcloset.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.rememberAsyncImagePainter
import com.example.virtualcloset.data.model.ClothingItem
import com.example.virtualcloset.data.model.Outfit

@Composable
fun SavedOutfitsScreen(
    outfits: List<Outfit>,
    clothes: List<ClothingItem>,
    onDelete: (Outfit) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Saved Outfits",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (outfits.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No saved outfits yet.\nCreate some in Mix Outfit!",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(outfits, key = { it.id }) { outfit ->
                    var showDeleteDialog by remember { mutableStateOf(false) }

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            // Header with name and delete button
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = outfit.name,
                                    style = MaterialTheme.typography.titleLarge,
                                    modifier = Modifier.weight(1f)
                                )
                                IconButton(onClick = { showDeleteDialog = true }) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Delete outfit",
                                        tint = MaterialTheme.colorScheme.error
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            // Display clothing items
                            val itemIds = outfit.itemIds.split(",").mapNotNull { it.toLongOrNull() }
                            val outfitItems = itemIds.mapNotNull { id ->
                                clothes.find { it.id == id }
                            }

                            if (outfitItems.isEmpty()) {
                                Text(
                                    text = "Some items were deleted",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.error
                                )
                            } else {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    outfitItems.forEach { item ->
                                        Card(
                                            modifier = Modifier
                                                .weight(1f)
                                                .aspectRatio(0.75f)
                                        ) {
                                            Column {
                                                Image(
                                                    painter = rememberAsyncImagePainter(item.imageUri.toUri()),
                                                    contentDescription = item.name,
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .weight(1f),
                                                    contentScale = ContentScale.Crop
                                                )
                                                Column(
                                                    modifier = Modifier.padding(8.dp)
                                                ) {
                                                    Text(
                                                        text = item.name,
                                                        style = MaterialTheme.typography.bodySmall,
                                                        maxLines = 1
                                                    )
                                                    Text(
                                                        text = item.category,
                                                        style = MaterialTheme.typography.labelSmall,
                                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    // Delete confirmation dialog
                    if (showDeleteDialog) {
                        AlertDialog(
                            onDismissRequest = { showDeleteDialog = false },
                            title = { Text("Delete Outfit") },
                            text = { Text("Are you sure you want to delete '${outfit.name}'?") },
                            confirmButton = {
                                TextButton(
                                    onClick = {
                                        onDelete(outfit)
                                        showDeleteDialog = false
                                    }
                                ) {
                                    Text("Delete", color = MaterialTheme.colorScheme.error)
                                }
                            },
                            dismissButton = {
                                TextButton(onClick = { showDeleteDialog = false }) {
                                    Text("Cancel")
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}