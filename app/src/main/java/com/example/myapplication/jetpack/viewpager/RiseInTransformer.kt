package com.example.myapplication.jetpack.viewpager

import android.view.View
import androidx.core.view.isVisible
import androidx.viewpager2.widget.ViewPager2

/**
 * Created by john.wick on 2024/4/26 13:39.
 */

private const val MIN_SCALE = 0.72F
private const val MIN_ALPHA = 0.5F

class RiseInTransformer : ViewPager2.PageTransformer {

    override fun transformPage(page: View, position: Float) {
        when {
            position < 0F -> {
                page.translationX = 0F
            }

            position <= 1F -> {
                page.isVisible = true
                page.translationX = -position * page.width
                page.scaleX = 1F - (1F - MIN_SCALE) * position
                page.scaleY = 1F - (1F - MIN_SCALE) * position
                page.alpha = 1F - (1F - MIN_ALPHA) * position
            }

            else -> {
                page.isVisible = false
                page.translationX = -position * page.width
                page.scaleX = MIN_SCALE
                page.scaleY = MIN_SCALE
                page.alpha = 1F
            }
        }
    }
}