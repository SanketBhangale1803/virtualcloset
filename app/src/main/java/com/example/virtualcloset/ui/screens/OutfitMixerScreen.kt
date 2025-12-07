package com.example.virtualcloset.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.graphicsLayer
import androidx.core.net.toUri
import coil.compose.rememberAsyncImagePainter
import com.example.virtualcloset.data.model.ClothingItem
import kotlin.math.roundToInt

@Composable
fun OutfitMixerScreen(
    clothes: List<ClothingItem>,
    onSaveOutfit: (String, List<Long>) -> Unit
) {
    var outfitName by remember { mutableStateOf("") }

    // Each clothing item will have its own transform state
    val itemStates = remember(clothes) {
        clothes.map { mutableStateOf(ClothingTransform()) }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        OutlinedTextField(
            value = outfitName,
            onValueChange = { outfitName = it },
            label = { Text("Outfit Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
        ) {
            // Default mannequin
            Image(
                painter = rememberAsyncImagePainter("file:///android_asset/mannequin.png"),
                contentDescription = "Mannequin",
                modifier = Modifier.fillMaxSize()
            )

            // Overlay clothing images with drag & zoom gestures
            clothes.forEachIndexed { index, item ->
                val transform = itemStates[index].value

                Image(
                    painter = rememberAsyncImagePainter(item.imageUri.toString()),
                    contentDescription = item.name,
                    modifier = Modifier
                        .offset {
                            IntOffset(
                                transform.offsetX.roundToInt(),
                                transform.offsetY.roundToInt()
                            )
                        }
                        .graphicsLayer(
                            scaleX = transform.scale,
                            scaleY = transform.scale
                        )
                        .pointerInput(Unit) {
                            detectTransformGestures { _, pan, zoom, _ ->
                                val newTransform = transform.copy(
                                    offsetX = transform.offsetX + pan.x,
                                    offsetY = transform.offsetY + pan.y,
                                    scale = (transform.scale * zoom).coerceIn(0.1f, 3f)
                                )
                                itemStates[index].value = newTransform
                            }
                        }
                        .size(150.dp) // initial clothing image size
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { onSaveOutfit(outfitName, clothes.map { it.id }) },
            enabled = outfitName.isNotBlank() && clothes.isNotEmpty()
        ) {
            Text("Save Outfit")
        }
    }
}

data class ClothingTransform(
    val offsetX: Float = 0f,
    val offsetY: Float = 0f,
    val scale: Float = 1f
)