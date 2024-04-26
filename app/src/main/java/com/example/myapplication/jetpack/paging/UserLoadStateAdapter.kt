package com.example.myapplication.jetpack.paging

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

/**
 * Created by john.wick on 2024/4/25 17:23.
 */
class UserLoadStateAdapter : LoadStateAdapter<UserLoadStateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): UserLoadStateViewHolder {
        return UserLoadStateViewHolder(parent)
    }

    override fun onBindViewHolder(holder: UserLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }
}