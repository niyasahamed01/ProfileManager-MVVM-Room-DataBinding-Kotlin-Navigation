package com.niyas.profilemanager.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.niyas.profilemanager.R
import com.niyas.profilemanager.adapter.PhotoCarouselAdapter
import com.niyas.profilemanager.databinding.FragmentProfileDetailBinding
import com.niyas.profilemanager.roomdb.Profile

class ProfileDetailFragment : Fragment() {

    private var _binding: FragmentProfileDetailBinding? = null
    private val binding get() = _binding!!

    private val args: ProfileDetailFragmentArgs by navArgs()
    private lateinit var viewPagerAdapter: PhotoCarouselAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_profile_detail, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val profile = args.profile
        setData(profile)
        setupImageCarousel(profile)
        setOnClickListener()
    }

    private fun setData(profile: Profile) {
        binding.profileName.text = profile.name
        binding.profileDescription.text = profile.description
        binding.titleText.text = profile.title
    }

    private fun setOnClickListener() {
        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }


    @SuppressLint("SetTextI18n")
    private fun setupImageCarousel(profile: Profile) {
        val imageUrls = listOf(
            profile.imageUrl,
            R.drawable.actor,
            R.drawable.actor,
            R.drawable.actor,
            R.drawable.actor
        ) // If there are multiple images, update this list
        viewPagerAdapter = PhotoCarouselAdapter(imageUrls)
        binding.viewPager.adapter = viewPagerAdapter
        // Set the initial image count
        binding.imageCounter.text = "1/${imageUrls.size}"
        setupDots(imageUrls.size)
        setupPageChangeListener(imageUrls.size)
    }

    private fun setupDots(size: Int) {
        // Dynamically create dot indicators
        binding.dotsLayout.removeAllViews()
        for (i in 0 until size) {
            val dot = TextView(context).apply {
                text = "•"
                textSize = 30f
                setTextColor(
                    if (i == 0) ContextCompat.getColor(requireContext(), R.color.selected_dot)
                    else ContextCompat.getColor(requireContext(), R.color.unselected_dot)
                )
            }
            binding.dotsLayout.addView(dot)
        }
    }

    private fun setupPageChangeListener(size: Int) {
        // Set up page change listener to update image counter and dots
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            @SuppressLint("SetTextI18n")
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                // Update the image counter
                binding.imageCounter.text = "${position + 1}/$size"

                // Update the dot indicators
                updateDots(position)
            }
        })
    }

    private fun updateDots(currentPosition: Int) {
        for (i in 0 until binding.dotsLayout.childCount) {
            val dot = binding.dotsLayout.getChildAt(i) as TextView
            dot.setTextColor(
                if (i == currentPosition)
                    ContextCompat.getColor(requireContext(), R.color.selected_dot)
                else
                    ContextCompat.getColor(requireContext(), R.color.unselected_dot)
            )
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}