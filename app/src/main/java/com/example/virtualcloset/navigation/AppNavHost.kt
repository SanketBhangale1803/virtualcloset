package com.example.virtualcloset.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.virtualcloset.ui.screens.*
import com.example.virtualcloset.viewmodel.ClothingViewModel
import com.example.virtualcloset.viewmodel.OutfitViewModel
import com.example.virtualcloset.data.ClothingRepository

@Composable
fun AppNavHost(
    navController: NavHostController,
    clothingVM: ClothingViewModel,
    outfitVM: OutfitViewModel,
    repo: ClothingRepository
) {
    val clothesState = clothingVM.clothes.collectAsState()
    val outfitsState = outfitVM.outfits.collectAsState()

    NavHost(navController = navController, startDestination = "home") {

        composable("home") {
            HomeScreen { route -> navController.navigate(route) }
        }

        composable("closet") {
            ClosetScreen(clothes = clothesState.value)
        }

        composable("add") {
            AddClothingScreen { name, category, uri ->
                clothingVM.addClothing(name, category, uri)
                navController.popBackStack()
            }
        }

        composable("mix") {
            OutfitMixerScreen(
                clothes = clothesState.value,
                onSaveOutfit = { name, ids ->
                    outfitVM.addOutfit(name, ids)
                    navController.popBackStack()
                }
            )
        }

        composable("saved") {
            SavedOutfitsScreen(
                outfits = outfitsState.value,
                repo = repo
            )
        }
    }
}