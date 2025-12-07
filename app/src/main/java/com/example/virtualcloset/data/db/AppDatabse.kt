package com.example.virtualcloset.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.virtualcloset.data.model.ClothingItem
import com.example.virtualcloset.data.model.Outfit

@Database(
    entities = [ClothingItem::class, Outfit::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun clothingDao(): ClothingDao
    abstract fun outfitDao(): OutfitDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "virtual_closet_db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}
