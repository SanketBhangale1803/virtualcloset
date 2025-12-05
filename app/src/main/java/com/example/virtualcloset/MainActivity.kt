package com.example.virtualcloset

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.example.virtualcloset.data.ClothingRepository
import com.example.virtualcloset.data.db.AppDatabase
import com.example.virtualcloset.ui.theme.VirtualClosetTheme
import com.example.virtualcloset.viewmodel.ClothingViewModel
import com.example.virtualcloset.viewmodel.OutfitViewModel
import com.example.virtualcloset.navigation.AppNavHost

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // enable edge-to-edge
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val db = AppDatabase.getInstance(this)
        val repo = ClothingRepository(db.clothingDao(), db.outfitDao())
        val clothingVM = ClothingViewModel(repo)
        val outfitVM = OutfitViewModel(repo)

        setContent {
            VirtualClosetTheme {
                val navController = rememberNavController()
                AppNavHost(navController, clothingVM, outfitVM)
            }
        }
    }
}