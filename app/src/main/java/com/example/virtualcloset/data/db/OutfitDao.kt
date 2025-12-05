package com.example.virtualcloset.data.db

import androidx.room.*
import com.example.virtualcloset.data.model.Outfit
import kotlinx.coroutines.flow.Flow

@Dao
interface OutfitDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(outfit: Outfit)

    @Query("SELECT * FROM outfits ORDER BY id DESC")
    fun getAll(): Flow<List<Outfit>>

    @Delete
    suspend fun delete(outfit: Outfit)
}