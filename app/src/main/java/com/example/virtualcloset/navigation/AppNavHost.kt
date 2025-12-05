package com.example.virtualcloset.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import com.example.virtualcloset.ui.screens.*
import com.example.virtualcloset.viewmodel.ClothingViewModel
import com.example.virtualcloset.viewmodel.OutfitViewModel
import com.example.virtualcloset.ui.screens.ClosetScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    clothingVM: ClothingViewModel,
    outfitVM: OutfitViewModel
) {
    val clothesState = clothingVM.clothes.collectAsState()
    val outfitsState = outfitVM.outfits.collectAsState()

    NavHost(navController = navController, startDestination = "home") {

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
                navController.popBackStack()
            }
        }

        composable("mix") {
            OutfitMixerScreen(
                clothes = clothesState.value,
                onSaveOutfit = { name, itemIds ->
                    outfitVM.addOutfit(name, itemIds)
                    navController.popBackStack()
                }
            )
        }

        composable("saved") {
            SavedOutfitsScreen(outfits = outfitsState.value)
        }
    }
}