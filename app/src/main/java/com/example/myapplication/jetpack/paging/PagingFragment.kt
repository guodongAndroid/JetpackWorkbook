package com.example.myapplication.jetpack.paging

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentPagingBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Created by john.wick on 2024/4/25 9:19.
 */
class PagingFragment : Fragment() {

    private lateinit var binding: FragmentPagingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPagingBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userViewModel by viewModels<UserViewModel> { UserViewModelFactory() }

        val users = userViewModel.users

        val userAdapter = UserAdapter()
        binding.bindAdapter(userAdapter)
        binding.addUser()

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                users.collectLatest {
                    userAdapter.submitData(it)
                }
            }
        }

        /* lifecycleScope.launch {
            userAdapter.loadStateFlow.collect {
                binding.progressPrepend.isVisible = it.source.prepend is LoadState.Loading
                binding.progressAppend.isVisible = it.source.append is LoadState.Loading
            }
        } */
    }

    private fun FragmentPagingBinding.bindAdapter(userAdapter: UserAdapter) {
        rvUser.adapter = userAdapter.withLoadStateHeaderAndFooter(
            header = UserLoadStateAdapter(),
            footer = UserLoadStateAdapter()
        )
        rvUser.layoutManager = LinearLayoutManager(rvUser.context)
        rvUser.addItemDecoration(
            DividerItemDecoration(
                rvUser.context,
                DividerItemDecoration.VERTICAL
            )
        )
    }

    private fun FragmentPagingBinding.addUser() {
        btnAddUser.setOnClickListener { findNavController().navigate(R.id.action_pagingFragment_to_roomActivity) }
    }
}