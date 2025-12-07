package com.example.virtualcloset.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.virtualcloset.data.ClothingRepository
import com.example.virtualcloset.data.model.Outfit
import kotlinx.coroutines.launch

@Composable
fun SavedOutfitsScreen(
    outfits: List<Outfit>,
    repo: ClothingRepository
) {
    val scope = rememberCoroutineScope()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        items(outfits) { outfit ->

            var clothingUris by remember { mutableStateOf(listOf<String>()) }

            LaunchedEffect(outfit.id) {
                scope.launch {
                    val ids = outfit.itemIds.split(",").mapNotNull { it.toLongOrNull() }
                    val items = repo.getClothingItemsByIds(ids)
                    clothingUris = items.map { it.imageUri }
                }
            }

            Card(modifier = Modifier.fillMaxWidth()) {

                Column(modifier = Modifier.padding(16.dp)) {

                    Text(
                        text = outfit.name,
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(Modifier.height(10.dp))

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {

                        clothingUris.forEach { uri ->
                            Image(
                                painter = rememberAsyncImagePainter(uri),
                                contentDescription = null,
                                modifier = Modifier.size(100.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}