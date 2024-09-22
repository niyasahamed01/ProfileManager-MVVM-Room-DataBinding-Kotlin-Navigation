package com.niyas.profilemanager.model

import androidx.lifecycle.LiveData
import com.niyas.profilemanager.roomdb.Profile
import com.niyas.profilemanager.roomdb.ProfileDao

class ProfileRepository(private val profileDao: ProfileDao) {

    val allProfiles: LiveData<List<Profile>> = profileDao.getAllProfilesLiveData()

    suspend fun insertProfiles(profiles: List<Profile>) {
        profileDao.insert(profiles)
    }

    suspend fun deleteProfile(profile: Profile) {
        profileDao.delete(profile)
    }

    suspend fun getAllProfiles(): List<Profile> {
        return profileDao.getAllProfiles()
    }
}