package com.niyas.profilemanager.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.niyas.profilemanager.R
import com.niyas.profilemanager.databinding.ItemProfileBinding
import com.niyas.profilemanager.roomdb.Profile

class ProfileAdapter(private val clickListener: ProfileClickListener) :
    RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder>() {

    private var profiles = emptyList<Profile>()

    inner class ProfileViewHolder(private val binding: ItemProfileBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(profile: Profile) {
            binding.profile = profile
            binding.executePendingBindings()

            // Load image using Glide
            Glide.with(binding.root.context)
                .load(profile.imageUrl) // Load image from URL
                .placeholder(R.drawable.placeholder) // Optional placeholder
                .error(R.drawable.placeholder) // Optional error image
                .into(binding.profileImage) // Replace with your ImageView ID
            // Handle profile removal
            binding.yesButton.setOnClickListener {
                clickListener.onYesClicked(profile)
            }

            binding.noButton.setOnClickListener {
                clickListener.onNoClicked(profile)
            }

            binding.profileImage.setOnClickListener {
                clickListener.onProfileClicked(profile)
            }
            binding.profileDescription.setOnClickListener {
                clickListener.onProfileClicked(profile)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        val binding = ItemProfileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProfileViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        holder.bind(profiles[position])
    }

    override fun getItemCount() = profiles.size

    fun setProfiles(profiles: List<Profile>) {
        this.profiles = profiles
        notifyDataSetChanged()
    }

    interface ProfileClickListener {
        fun onYesClicked(profile: Profile)
        fun onNoClicked(profile: Profile)
        fun onProfileClicked(profile: Profile)
    }
}