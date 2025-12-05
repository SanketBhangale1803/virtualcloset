package com.example.virtualcloset.data.db

import androidx.room.*
import com.example.virtualcloset.data.model.ClothingItem
import kotlinx.coroutines.flow.Flow

@Dao
interface ClothingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: ClothingItem)

    @Query("SELECT * FROM clothing_items ORDER BY id DESC")
    fun getAll(): Flow<List<ClothingItem>>

    @Delete
    suspend fun delete(item: ClothingItem)
}