package com.example.myapplication.jetpack.viewpager

import android.view.View
import androidx.viewpager2.widget.ViewPager2

/**
 * Created by john.wick on 2024/4/26 14:31.
 */
class StackTransformer : ViewPager2.PageTransformer {

    override fun transformPage(page: View, position: Float) {
        page.translationX = if (position >= 0F) 0F else -position * page.width
    }
}