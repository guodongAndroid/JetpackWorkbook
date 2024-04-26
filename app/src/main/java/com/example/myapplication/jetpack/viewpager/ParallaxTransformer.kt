package com.example.myapplication.jetpack.viewpager

import android.view.View
import androidx.viewpager2.widget.ViewPager2

/**
 * Created by john.wick on 2024/4/26 14:43.
 */

private const val SCROLL_FACTOR = 0.75F

class ParallaxTransformer : ViewPager2.PageTransformer {

    override fun transformPage(page: View, position: Float) {
        when {
            position <= -1F || position >= 1F -> {
                page.scrollX = 0
            }
            else -> {
                page.scrollX = (page.width * position * SCROLL_FACTOR).toInt()
            }
        }
    }
}