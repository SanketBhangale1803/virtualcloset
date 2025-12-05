package com.example.virtualcloset.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.virtualcloset.data.model.Outfit

@Composable
fun SavedOutfitsScreen(outfits: List<Outfit>) {
    Column(modifier = Modifier.fillMaxSize().padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        outfits.forEach { outfit ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Text("Outfit: ${outfit.name}", modifier = Modifier.padding(12.dp))
            }
        }
    }
}