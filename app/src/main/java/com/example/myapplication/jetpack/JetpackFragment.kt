package com.example.myapplication.jetpack

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentJetpackBinding

/**
 * Created by john.wick on 2024/4/24 10:29.
 */
class JetpackFragment : Fragment() {

    private lateinit var binding: FragmentJetpackBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentJetpackBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLifecycle.setOnClickListener { navigate(R.id.action_jetpackFragment_to_lifecycleActivity) }
        binding.btnViewModel.setOnClickListener { navigate(R.id.action_jetpackFragment_to_viewModelActivity) }
        binding.btnLiveData.setOnClickListener { navigate(R.id.action_jetpackFragment_to_liveDataActivity) }
        binding.btnRoom.setOnClickListener { navigate(R.id.action_jetpackFragment_to_roomActivity) }
        binding.btnDatastore.setOnClickListener { navigate(R.id.action_jetpackFragment_to_dataStoreFragment) }
        binding.btnPaging.setOnClickListener { navigate(R.id.action_jetpackFragment_to_pagingFragment) }
        binding.btnViewpager.setOnClickListener { navigate(R.id.action_jetpackFragment_to_viewPagerFragment) }
    }

    private fun navigate(@IdRes resId: Int) {
        findNavController().navigate(resId)
    }
}