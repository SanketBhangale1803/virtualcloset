package com.example.virtualcloset.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(onNavigate: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Button(onClick = { onNavigate("closet") }, modifier = Modifier.fillMaxWidth()) { Text("My Closet") }
        Button(onClick = { onNavigate("add") }, modifier = Modifier.fillMaxWidth()) { Text("Add Clothing") }
        Button(onClick = { onNavigate("mix") }, modifier = Modifier.fillMaxWidth()) { Text("Mix Outfit") }
        Button(onClick = { onNavigate("saved") }, modifier = Modifier.fillMaxWidth()) { Text("Saved Outfits") }
    }
}