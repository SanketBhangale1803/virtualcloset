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

@Composable
fun ClosetScreen(clothes: List<ClothingItem>) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        items(clothes) { item ->

            Card(modifier = Modifier.fillMaxWidth()) {

                Column(modifier = Modifier.padding(16.dp)) {

                    Image(
                        painter = rememberAsyncImagePainter(item.imageUri),
                        contentDescription = null,
                        modifier = Modifier
                            .height(220.dp)
                            .fillMaxWidth()
                    )

                    Spacer(Modifier.height(10.dp))

                    Text(text = item.name, style = MaterialTheme.typography.titleMedium)
                    Text(text = "Category: ${item.category}")
                }
            }
        }
    }
}