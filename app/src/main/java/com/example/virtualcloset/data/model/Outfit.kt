package com.example.virtualcloset.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "outfits")
data class Outfit(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val itemIds: String // comma-separated ClothingItem ids
)