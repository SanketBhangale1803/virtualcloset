package com.example.virtualcloset.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.virtualcloset.data.ClothingRepository
import com.example.virtualcloset.data.model.Outfit
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class OutfitViewModel(private val repo: ClothingRepository) : ViewModel() {

    // Expose outfits as a StateFlow so Compose can observe changes
    val outfits: StateFlow<List<Outfit>> = repo.getOutfits()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Add a new outfit; itemIds are converted to comma-separated string for storage
    fun addOutfit(name: String, itemIds: List<Long>) = viewModelScope.launch {
        val outfit = Outfit(name = name, itemIds = itemIds.joinToString(","))
        repo.addOutfit(outfit)
    }
}