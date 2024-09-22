package com.niyas.profilemanager.roomdb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ProfileDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(profiles: List<Profile>)

    @Query("SELECT * FROM profile_table")
    suspend fun getAllProfiles(): List<Profile>

    @Query("SELECT * FROM profile_table")
    fun getAllProfilesLiveData(): LiveData<List<Profile>>

    @Delete
    suspend fun delete(profile: Profile)
}