package com.niyas.profilemanager.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Profile::class], version = 3)
abstract class ProfileDatabase : RoomDatabase() {
    abstract fun profileDao(): ProfileDao

    companion object {
        @Volatile private var instance: ProfileDatabase? = null

        fun getDatabase(context: Context): ProfileDatabase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    ProfileDatabase::class.java,
                    "profile_database"
                ).build().also { instance = it }
            }
        }
    }
}