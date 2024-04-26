package com.example.myapplication.jetpack.room

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityRoomBinding
import com.example.myapplication.databinding.DialogRoomAddUserBinding
import com.example.myapplication.databinding.DialogRoomGetUserBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * Created by john.wick on 2024/4/23 13:13.
 */
class RoomActivity : AppCompatActivity() {

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, RoomActivity::class.java))
        }
    }

    private val allUsers = mutableListOf<User>()
    private val userAdapter = UserAdapter(allUsers)

    private val userViewModel by viewModels<UserViewModel> { UserViewModelFactory() }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRoomBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        binding.bindMenu()

        binding.btnAddUser.setOnClickListener {
            binding.toggleMenu()
            lifecycleScope.launch {
                val user = addUser() ?: return@launch
                val userId = userViewModel.insert(user)
                user.id = userId
                allUsers.add(user)
                userAdapter.notifyItemRangeInserted(allUsers.lastIndex, 1)
                binding.rvUser.smoothScrollToPosition(allUsers.lastIndex)
            }
        }

        binding.btnGetUser.setOnClickListener {
            binding.toggleMenu()
            lifecycleScope.launch {
                val userId = getUser() ?: return@launch
                val user = userViewModel.getUserById(userId)
                if (user == null) {
                    Toast.makeText(
                        this@RoomActivity,
                        "没有找到UserId($userId)的用户",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@launch
                }

                AlertDialog.Builder(this@RoomActivity)
                    .setTitle("Show User")
                    .setMessage("UserId(${user.id}) ${user.name}(${user.age})")
                    .setPositiveButton("确定") { _, _ -> }
                    .show()
            }
        }

        binding.btnLoadUser.setOnClickListener {
            binding.toggleMenu()
            userViewModel.loadAllUser()
        }

        userAdapter.setOnItemDeleteClickListener { position, user ->
            lifecycleScope.launch {
                if (deleteConfirmation(user) && userViewModel.delete(user) > 0) {
                    allUsers.remove(user)
                    userAdapter.notifyItemRemoved(position)
                }
            }
        }

        userAdapter.setOnItemEditClickListener { position, user ->
            lifecycleScope.launch {
                val newUser = editUser(user) ?: return@launch
                userViewModel.update(newUser).takeIf { it > 0 }?.also {
                    allUsers[position] = newUser
                    userAdapter.notifyItemChanged(position)
                }
            }
        }

        with(binding.rvUser) {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(this.context)
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(this.context, LinearLayoutManager.VERTICAL))
        }

        lifecycleScope.launch {
            userViewModel.state.collect { isLoading ->
                binding.tvLoading.isVisible = isLoading
            }
        }

        lifecycleScope.launch {
            userViewModel.allUsers.collect { users ->
                allUsers.clear()
                allUsers.addAll(users)
                userAdapter.notifyDataSetChanged()
            }
        }
    }

    private suspend fun editUser(user: User): User? = suspendCoroutine { continuation ->
        val binding = DialogRoomAddUserBinding.inflate(LayoutInflater.from(this))

        binding.edUserName.setText(user.name)
        binding.edUserAge.setText(user.age.toString())

        AlertDialog.Builder(this)
            .setTitle("${getString(R.string.room_edit_user)}(${user.id})")
            .setView(binding.root)
            .setPositiveButton("确定") { _, _ ->
                val username = binding.edUserName.text.toString()
                val userAge = try {
                    binding.edUserAge.text.toString().toInt()
                } catch (e: NumberFormatException) {
                    -1
                }

                if (username.isEmpty()) {
                    Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                if (userAge < 0) {
                    Toast.makeText(this, "请输入年龄", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                continuation.resume(User(user.id, username, userAge))
            }
            .setNegativeButton("取消") { _, _ ->
                continuation.resume(null)
            }
            .show()
    }

    private fun ActivityRoomBinding.bindMenu() {
        btnMenu.setOnClickListener {
            toggleMenu()
        }
    }

    private fun ActivityRoomBinding.toggleMenu() {
        createAnimatorSet().start()
    }

    private fun ActivityRoomBinding.createAnimatorSet(): AnimatorSet = if (btnLoadUser.isVisible) {
        val loadAnimator = btnLoadUser.createValueAnimator(0, true)
        val getAnimator = btnGetUser.createValueAnimator(45, true)
        val addAnimator = btnAddUser.createValueAnimator(90, true)
        AnimatorSet().apply {
            playSequentially(addAnimator, getAnimator, loadAnimator)
        }
    } else {
        val loadAnimator = btnLoadUser.createValueAnimator(0, false)
        val getAnimator = btnGetUser.createValueAnimator(45, false)
        val addAnimator = btnAddUser.createValueAnimator(90, false)
        AnimatorSet().apply {
            playSequentially(loadAnimator, getAnimator, addAnimator)
        }
    }.apply {
        duration = 300L
    }

    private fun FloatingActionButton.createValueAnimator(
        angle: Int,
        reverse: Boolean
    ): ValueAnimator {
        val animator = if (reverse) ValueAnimator.ofFloat(1F, 0F) else ValueAnimator.ofFloat(0F, 1F)
        animator.addUpdateListener { animation ->
            val value = animation.animatedValue as Float
            val lp = this.layoutParams as ConstraintLayout.LayoutParams
            lp.circleRadius = (200 * value).toInt()
            lp.circleAngle = angle * value
            this.layoutParams = lp
        }

        animator.doOnStart {
            this.isVisible = true
        }

        animator.doOnEnd {
            if (reverse) {
                this.isVisible = false
            }
        }

        return animator
    }

    private suspend fun deleteConfirmation(user: User): Boolean = suspendCoroutine { continuation ->
        AlertDialog.Builder(this)
            .setTitle("删除确认")
            .setMessage("确认删除UserId(${user.id}) ${user.name}(${user.age})?")
            .setPositiveButton("删除") { _, _ ->
                continuation.resume(true)
            }
            .setNegativeButton("取消") { _, _ ->
                continuation.resume(false)
            }
            .show()
    }

    private suspend fun getUser() = suspendCoroutine { continuation ->
        val binding = DialogRoomGetUserBinding.inflate(LayoutInflater.from(this))
        AlertDialog.Builder(this)
            .setTitle(R.string.room_get_user)
            .setView(binding.root)
            .setPositiveButton("获取") { _, _ ->
                val userId = try {
                    binding.edUserId.text.toString().toInt()
                } catch (e: NumberFormatException) {
                    -1
                }

                if (userId < 0) {
                    Toast.makeText(this, "请输入用户ID", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                continuation.resume(userId)
            }
            .setNegativeButton("取消") { _, _ ->
                continuation.resume(null)
            }
            .show()
    }

    private suspend fun addUser() = suspendCoroutine { continuation ->
        val binding = DialogRoomAddUserBinding.inflate(LayoutInflater.from(this))
        AlertDialog.Builder(this)
            .setTitle(R.string.room_add_user)
            .setView(binding.root)
            .setPositiveButton("添加") { _, _ ->
                val username = binding.edUserName.text.toString()
                val userAge = try {
                    binding.edUserAge.text.toString().toInt()
                } catch (e: NumberFormatException) {
                    -1
                }

                if (username.isEmpty()) {
                    Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                if (userAge < 0) {
                    Toast.makeText(this, "请输入年龄", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                continuation.resume(User(0L, username, userAge))
            }
            .setNegativeButton("取消") { _, _ ->
                continuation.resume(null)
            }
            .show()
    }
}