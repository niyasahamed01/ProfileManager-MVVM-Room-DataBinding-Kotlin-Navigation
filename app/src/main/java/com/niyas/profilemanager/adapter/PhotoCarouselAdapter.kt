package com.niyas.profilemanager.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.niyas.profilemanager.R
import com.niyas.profilemanager.databinding.ItemProfileDetailBinding

class PhotoCarouselAdapter(private val imageUrls: List<Int>) :
    RecyclerView.Adapter<PhotoCarouselAdapter.ImageViewHolder>() {

    inner class ImageViewHolder(val binding: ItemProfileDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(imageUrl: Int) {
            Glide.with(binding.root.context)
                .load(imageUrl)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(binding.carouselImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ItemProfileDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(imageUrls[position])

    }

    override fun getItemCount(): Int {
        return imageUrls.size
    }
}