package com.example.myapplication.jetpack.viewpager

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication.databinding.FragmentPlayListBinding

/**
 * Created by john.wick on 2024/4/26 10:25.
 */
class PlayListFragment private constructor() : Fragment() {

    companion object {
        private const val EXTRAS_KEY_NAME = "name"
        private const val EXTRAS_KEY_COLOR = "color"

        fun newInstance(name: String, color: String) = PlayListFragment().apply {
            arguments = Bundle().apply {
                putString(EXTRAS_KEY_NAME, name)
                putString(EXTRAS_KEY_COLOR, color)
            }
        }
    }

    private lateinit var binding: FragmentPlayListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlayListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvPlayListName.text = arguments?.getString(EXTRAS_KEY_NAME, "歌单") ?: "歌单"
        binding.root.setBackgroundColor(
            Color.parseColor(
                arguments?.getString(
                    EXTRAS_KEY_COLOR,
                    "#FFFFFF"
                ) ?: "#FFFFFF"
            )
        )
    }
}