package com.example.myapplication.jetpack.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.ItemLoadStateBinding

/**
 * Created by john.wick on 2024/4/25 17:19.
 */
class UserLoadStateViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context)
        .inflate(R.layout.item_load_state, parent, false)
) {
    private val binding = ItemLoadStateBinding.bind(itemView)

    fun bind(loadState: LoadState) {
        binding.progressLoading.isVisible = loadState is LoadState.Loading
    }
}