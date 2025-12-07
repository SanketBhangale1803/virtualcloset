package com.example.virtualcloset.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.rememberAsyncImagePainter
import com.example.virtualcloset.data.model.ClothingItem
import kotlin.random.Random

@Composable
fun OutfitMixerScreen(
    clothes: List<ClothingItem>,
    onSaveOutfit: (String, List<Long>) -> Unit
) {
    var outfitName by remember { mutableStateOf("") }
    var randomItems by remember { mutableStateOf<List<ClothingItem>>(emptyList()) }

    // Generate random outfit on first load
    LaunchedEffect(clothes) {
        if (clothes.isNotEmpty() && randomItems.isEmpty()) {
            randomItems = generateRandomOutfit(clothes)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Mix & Match Outfit",
            style = MaterialTheme.typography.headlineMedium
        )

        if (clothes.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Add some clothing items first!",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            // Display random outfit items
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    if (randomItems.isEmpty()) {
                        Text(
                            text = "Click 'Generate Random Outfit' to start!",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    } else {
                        Text(
                            text = "Your Random Outfit:",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )

                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            items(randomItems) { item ->
                                Card(
                                    modifier = Modifier
                                        .width(180.dp)
                                        .height(240.dp)
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
                                                style = MaterialTheme.typography.titleSmall,
                                                maxLines = 1
                                            )
                                            Text(
                                                text = item.category,
                                                style = MaterialTheme.typography.bodySmall,
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

            // Generate button
            Button(
                onClick = {
                    randomItems = generateRandomOutfit(clothes)
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = clothes.size >= 2
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Generate Random Outfit")
            }

            // Outfit name input
            OutlinedTextField(
                value = outfitName,
                onValueChange = { outfitName = it },
                label = { Text("Outfit Name") },
                placeholder = { Text("e.g., Casual Friday") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Save button
            Button(
                onClick = {
                    onSaveOutfit(outfitName, randomItems.map { it.id })
                    outfitName = ""
                },
                enabled = outfitName.isNotBlank() && randomItems.isNotEmpty(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Outfit")
            }
        }
    }
}

private fun generateRandomOutfit(clothes: List<ClothingItem>): List<ClothingItem> {
    if (clothes.size < 2) return clothes

    // Define category groups for matching
    val tops = listOf("Shirts", "T-Shirts", "Tops", "Sweaters", "Hoodies", "Jackets", "Coats")
    val bottoms = listOf("Pants", "Jeans", "Shorts", "Skirts")
    val footwear = listOf("Shoes", "Sneakers", "Boots")
    val headwear = listOf("Hats", "Caps")
    val dresses = listOf("Dresses")
    val accessories = listOf("Accessories")

    val topItems = clothes.filter { it.category in tops }
    val bottomItems = clothes.filter { it.category in bottoms }
    val footwearItems = clothes.filter { it.category in footwear }
    val headwearItems = clothes.filter { it.category in headwear }
    val dressItems = clothes.filter { it.category in dresses }
    val accessoryItems = clothes.filter { it.category in accessories }

    val outfit = mutableListOf<ClothingItem>()

    // Strategy 1: Dress-based outfit (dress + shoes/accessories)
    if (dressItems.isNotEmpty() && Random.nextBoolean()) {
        outfit.add(dressItems.random())

        if (footwearItems.isNotEmpty()) {
            outfit.add(footwearItems.random())
        }
        if (accessoryItems.isNotEmpty() && Random.nextBoolean()) {
            outfit.add(accessoryItems.random())
        }
    }
    // Strategy 2: Top + Bottom outfit
    else if (topItems.isNotEmpty() && bottomItems.isNotEmpty()) {
        outfit.add(topItems.random())
        outfit.add(bottomItems.random())

        // Add shoes if available
        if (footwearItems.isNotEmpty() && Random.nextBoolean()) {
            outfit.add(footwearItems.random())
        }

        // Add hat/accessory if available
        if (headwearItems.isNotEmpty() && Random.nextBoolean()) {
            outfit.add(headwearItems.random())
        } else if (accessoryItems.isNotEmpty() && Random.nextBoolean()) {
            outfit.add(accessoryItems.random())
        }
    }
    // Fallback: Pick 2-3 random items
    else {
        val shuffled = clothes.shuffled()
        outfit.addAll(shuffled.take(Random.nextInt(2, 4.coerceAtMost(clothes.size + 1))))
    }

    // Ensure at least 2 items
    return if (outfit.size < 2) {
        clothes.shuffled().take(2)
    } else {
        outfit
    }
}