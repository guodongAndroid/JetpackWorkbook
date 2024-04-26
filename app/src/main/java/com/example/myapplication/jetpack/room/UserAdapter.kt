package com.example.myapplication.jetpack.room

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemRoomUserBinding

/**
 * Created by john.wick on 2024/4/23 14:07.
 */

typealias OnItemDeleteClickListener = (Int, User) -> Unit
typealias OnItemEditClickListener = (Int, User) -> Unit

class UserAdapter(private val users: MutableList<User>) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private var onItemDeleteClickListener: OnItemDeleteClickListener? = null
    private var onItemEditClickListener: OnItemEditClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding =
            ItemRoomUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding,
            { position ->
                onItemDeleteClickListener?.invoke(position, users[position])
            },
            { position ->
                onItemEditClickListener?.invoke(position, users[position])
            })
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bindData(users[position])
    }

    fun setOnItemDeleteClickListener(l: OnItemDeleteClickListener) {
        this.onItemDeleteClickListener = l
    }

    fun setOnItemEditClickListener(l: OnItemEditClickListener) {
        this.onItemEditClickListener = l
    }

    class UserViewHolder(
        private val itemViewBinding: ItemRoomUserBinding,
        private val onItemDeleteClickListener: (position: Int) -> Unit,
        private val onItemEditClickListener: (position: Int) -> Unit,
    ) : RecyclerView.ViewHolder(itemViewBinding.root) {

        init {
            itemViewBinding.btnItemRoomUserDelete.setOnClickListener {
                onItemDeleteClickListener.invoke(adapterPosition)
            }

            itemViewBinding.btnItemRoomUserEdit.setOnClickListener {
                onItemEditClickListener.invoke(adapterPosition)
            }
        }

        fun bindData(user: User) {
            itemViewBinding.tvItemRoomUserId.text = user.id.toString()
            itemViewBinding.tvItemRoomUserName.text = user.name
            itemViewBinding.tvItemRoomUserAge.text = user.age.toString()
        }
    }
}