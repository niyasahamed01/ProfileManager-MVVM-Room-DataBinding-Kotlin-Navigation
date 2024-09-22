package com.niyas.profilemanager.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.niyas.profilemanager.R
import com.niyas.profilemanager.adapter.ProfileAdapter
import com.niyas.profilemanager.databinding.FragmentProfileListBinding
import com.niyas.profilemanager.roomdb.Profile
import com.niyas.profilemanager.viewmodel.ProfileViewModel

class ProfileListFragment : Fragment(), ProfileAdapter.ProfileClickListener {

    private var _binding: FragmentProfileListBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ProfileViewModel
    private lateinit var adapter: ProfileAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_profile_list, container, false
        )

        viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        adapter = ProfileAdapter(this)
        binding.profileRecyclerView.adapter = adapter

        viewModel.allProfiles.observe(viewLifecycleOwner, Observer { profiles ->
            profiles?.let { adapter.setProfiles(it) }
            toggleNoDataMessage(profiles.isEmpty())

        })

        setOnClickListener()
        return binding.root
    }


    private fun toggleNoDataMessage(isEmpty: Boolean) {
        if (isEmpty) {
            binding.tvNoData.visibility = View.VISIBLE
            binding.profileRecyclerView.visibility = View.GONE
        } else {
            binding.tvNoData.visibility = View.GONE
            binding.profileRecyclerView.visibility = View.VISIBLE
        }
    }

    private fun setOnClickListener() {

        binding.ivMore.setOnClickListener {
            val action = ProfileListFragmentDirections.actionProfileListFragmentToGestureFragment()
            findNavController().navigate(action)
        }

        binding.tvNoData.setOnClickListener {
            viewModel.refreshProfiles()
        }
    }

    override fun onProfileClicked(profile: Profile) {
        val action =
            ProfileListFragmentDirections.actionProfileListFragmentToProfileDetailFragment(profile)
        findNavController().navigate(action)
    }

    override fun onYesClicked(profile: Profile) {
        viewModel.deleteProfile(profile)
        Toast.makeText(requireContext(), "${profile.name} removed", Toast.LENGTH_SHORT).show()
    }

    override fun onNoClicked(profile: Profile) {
        viewModel.deleteProfile(profile)
        Toast.makeText(requireContext(), "${profile.name} removed", Toast.LENGTH_SHORT).show()
    }
}
