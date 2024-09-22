package com.niyas.profilemanager.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.niyas.profilemanager.adapter.GestureAdapter
import com.niyas.profilemanager.databinding.FragmentGestureBinding
import com.niyas.profilemanager.roomdb.Profile
import com.niyas.profilemanager.util.OnProfileActionListener
import com.niyas.profilemanager.util.VerticalPageTransformer
import com.niyas.profilemanager.viewmodel.ProfileViewModel


class GestureFragment : Fragment(), OnProfileActionListener {

    private var _binding: FragmentGestureBinding? = null
    private val binding get() = _binding!!
    private val profileViewModel: ProfileViewModel by activityViewModels()
    private lateinit var gestureAdapter: GestureAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentGestureBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()

        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupViewPager() {
        gestureAdapter = GestureAdapter(this) // Pass the fragment as the listener
        binding.viewPager2.adapter = gestureAdapter
        binding.viewPager2.offscreenPageLimit = 5

        binding.viewPager2.setPageTransformer(VerticalPageTransformer(5)) // Set the transformer
        profileViewModel.allProfiles.observe(viewLifecycleOwner) { profiles ->
            gestureAdapter.submitList(profiles)
        }
    }

    override fun onProfileAccepted(profile: Profile) {
        profileViewModel.deleteProfile(profile)
        Toast.makeText(requireContext(), "${profile.name} removed", Toast.LENGTH_SHORT).show()
    }

    override fun onProfileRejected(profile: Profile) {
        profileViewModel.deleteProfile(profile)
        Toast.makeText(requireContext(), "${profile.name} removed", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}