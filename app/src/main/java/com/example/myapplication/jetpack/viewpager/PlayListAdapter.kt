package com.example.myapplication.jetpack.viewpager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * Created by john.wick on 2024/4/26 10:36.
 */
class PlayListAdapter(
    fragment: Fragment,
    private val names: Array<String>,
    private val colors: Array<String>,
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return names.size
    }

    override fun createFragment(position: Int): Fragment {
        return PlayListFragment.newInstance(names[position], colors[position])
    }
}