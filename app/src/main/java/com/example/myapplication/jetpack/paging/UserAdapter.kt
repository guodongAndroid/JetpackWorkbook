package com.example.myapplication.jetpack.paging

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemRoomUserBinding
import com.example.myapplication.jetpack.room.User

/**
 * Created by john.wick on 2024/4/25 10:22.
 */
class UserAdapter : PagingDataAdapter<User, UserViewHolder>(USER_DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder =
        UserViewHolder(
            ItemRoomUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        getItem(position)?.run { holder.bind(this) }
    }

    companion object {
        private val USER_DIFF_CALLBACK = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }
        }
    }
}

class UserViewHolder(private val binding: ItemRoomUserBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(user: User) {
        with(binding) {
            tvItemRoomUserId.text = user.id.toString()
            tvItemRoomUserName.text = user.name
            tvItemRoomUserAge.text = user.age.toString()

            btnItemRoomUserEdit.visibility = View.GONE
            btnItemRoomUserDelete.visibility = View.GONE
        }
    }
}