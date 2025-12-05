package com.example.virtualcloset.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.virtualcloset.data.model.ClothingItem

@Composable
fun OutfitMixerScreen(
    clothes: List<ClothingItem>,
    onSaveOutfit: (String, List<Long>) -> Unit
) {
    Text("Outfit mixer coming soon!", modifier = Modifier.padding(20.dp))
}