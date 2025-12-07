package com.example.virtualcloset.data

import com.example.virtualcloset.data.db.ClothingDao
import com.example.virtualcloset.data.db.OutfitDao
import com.example.virtualcloset.data.model.ClothingItem
import com.example.virtualcloset.data.model.Outfit
import kotlinx.coroutines.flow.Flow

class ClothingRepository(
    private val clothingDao: ClothingDao,
    private val outfitDao: OutfitDao
) {
    fun getClothes(): Flow<List<ClothingItem>> = clothingDao.getAll()
    suspend fun addClothing(item: ClothingItem) = clothingDao.insert(item)
    suspend fun deleteClothing(item: ClothingItem) = clothingDao.delete(item)

    fun getOutfits(): Flow<List<Outfit>> = outfitDao.getAll()
    suspend fun addOutfit(outfit: Outfit) = outfitDao.insert(outfit)
    suspend fun deleteOutfit(outfit: Outfit) = outfitDao.delete(outfit)
    suspend fun getClothingItemsByIds(ids: List<Long>): List<ClothingItem> {
        return ids.mapNotNull { id ->
            clothingDao.getById(id)
        }
    }
}