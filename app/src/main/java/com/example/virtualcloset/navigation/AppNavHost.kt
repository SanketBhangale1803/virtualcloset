package com.example.virtualcloset.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import com.example.virtualcloset.ui.components.MainScaffold
import com.example.virtualcloset.ui.screens.*
import com.example.virtualcloset.viewmodel.ClothingViewModel
import com.example.virtualcloset.viewmodel.OutfitViewModel

@Composable
fun AppNavHost(
    navController: NavHostController,
    clothingVM: ClothingViewModel,
    outfitVM: OutfitViewModel
) {
    val clothesState = clothingVM.clothes.collectAsState()
    val outfitsState = outfitVM.outfits.collectAsState()

    MainScaffold(navController = navController) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(paddingValues)
        ) {

            composable("home") {
                HomeScreen(onNavigate = { navController.navigate(it) })
            }

            composable("closet") {
                ClosetScreen(
                    clothes = clothesState.value,
                    onDelete = { clothingVM.deleteClothing(it) }
                )
            }

            composable("add") {
                AddClothingScreen1 { name, category, uri ->
                    clothingVM.addClothing(name, category, uri)
                    navController.navigate("closet") {
                        popUpTo("closet") { inclusive = true }
                    }
                }
            }

            composable("mix") {
                OutfitMixerScreen(
                    clothes = clothesState.value,
                    onSaveOutfit = { name: String, itemIds: List<Long> ->
                        outfitVM.addOutfit(name, itemIds)
                        navController.navigate("saved") {
                            popUpTo("saved") { inclusive = true }
                        }
                    }
                )
            }

            composable("saved") {
                SavedOutfitsScreen(outfits = outfitsState.value)
            }
        }
    }
}