package com.example.virtualcloset.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.virtualcloset.data.ClothingRepository
import com.example.virtualcloset.data.model.ClothingItem
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ClothingViewModel(private val repo: ClothingRepository) : ViewModel() {

    // Expose the clothes as a StateFlow so Compose can observe changes
    val clothes: StateFlow<List<ClothingItem>> = repo.getClothes()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Add a new clothing item
    fun addClothing(name: String, category: String, imageUri: String) = viewModelScope.launch {
        val newItem = ClothingItem(name = name, category = category, imageUri = imageUri)
        repo.addClothing(newItem)
    }

    // Delete a clothing item
    fun deleteClothing(item: ClothingItem) = viewModelScope.launch {
        repo.deleteClothing(item)
    }
}