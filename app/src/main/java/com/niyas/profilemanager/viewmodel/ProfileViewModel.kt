package com.niyas.profilemanager.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.niyas.profilemanager.R
import com.niyas.profilemanager.model.ProfileRepository
import com.niyas.profilemanager.roomdb.Profile
import com.niyas.profilemanager.roomdb.ProfileDatabase
import kotlinx.coroutines.launch

class ProfileViewModel(private val application: Application) : AndroidViewModel(application) {

    private val repository: ProfileRepository
    val allProfiles: LiveData<List<Profile>>

    init {
        val profileDao = ProfileDatabase.getDatabase(application).profileDao()
        repository = ProfileRepository(profileDao)
        allProfiles = repository.allProfiles

        // Populate the database with static profiles if it's empty
        insertStaticProfiles(application)
    }

    private fun insertStaticProfiles(application: Application) = viewModelScope.launch {
        if (repository.getAllProfiles().isEmpty()) {
             val staticProfiles = listOf(
                Profile(id = 1, name = "John Doe", description = application.getString(R.string.profile_john_doe), imageUrl = R.drawable.actor, "M9837832"),
                Profile(id = 2, name = "Jane Smith", description = application.getString(R.string.profile_jane_smith), imageUrl = R.drawable.actor_two, "M9837833"),
                Profile(id = 3, name = "Alice Johnson", description = application.getString(R.string.profile_alice_johnson), imageUrl = R.drawable.actor_three, "M9837834"),
                Profile(id = 4, name = "Bob Brown", description = application.getString(R.string.profile_bob_brown), imageUrl = R.drawable.actor_four, "M9837835"),
                Profile(id = 5, name = "Charlie Green", description = application.getString(R.string.profile_charlie_green), imageUrl = R.drawable.actor_five, "M9837836")
            )
            repository.insertProfiles(staticProfiles)
        }
    }

    fun refreshProfiles() {
        insertStaticProfiles(application)
    }

    fun deleteProfile(profile: Profile) = viewModelScope.launch {
        repository.deleteProfile(profile)
    }
}