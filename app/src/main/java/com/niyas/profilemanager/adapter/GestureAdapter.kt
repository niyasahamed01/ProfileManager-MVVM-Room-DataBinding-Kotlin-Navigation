package com.niyas.profilemanager.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.niyas.profilemanager.R
import com.niyas.profilemanager.databinding.ItemGestureBinding
import com.niyas.profilemanager.roomdb.Profile
import com.niyas.profilemanager.util.OnProfileActionListener


class GestureAdapter(
    private val actionListener: OnProfileActionListener
) : RecyclerView.Adapter<GestureAdapter.ProfileViewHolder>() {

    private var profiles = mutableListOf<Profile>()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newProfiles: List<Profile>) {
        profiles = newProfiles.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        val binding = ItemGestureBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        // Ensure the layout for each page in ViewPager2 is match_parent
        binding.root.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        return ProfileViewHolder(binding,actionListener)
    }

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        val profile = profiles[position]
        holder.bind(profile)

    }

    override fun getItemCount(): Int = profiles.size

    class ProfileViewHolder(
        private val binding: ItemGestureBinding,
        private val actionListener: OnProfileActionListener
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(profile: Profile) {
            binding.profile = profile
            binding.executePendingBindings()

            Glide.with(binding.root.context)
                .load(profile.imageUrl)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(binding.profileImage)


            binding.yesButton.setOnClickListener {
                actionListener.onProfileAccepted(profile)
            }

            binding.noButton.setOnClickListener {
                actionListener.onProfileRejected(profile)
            }

        }
    }
}
