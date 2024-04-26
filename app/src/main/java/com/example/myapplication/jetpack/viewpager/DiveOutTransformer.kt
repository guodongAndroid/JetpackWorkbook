package com.example.myapplication.jetpack.viewpager

import android.view.View
import androidx.core.view.isVisible
import androidx.viewpager2.widget.ViewPager2.PageTransformer

/**
 * Created by john.wick on 2024/4/26 14:23.
 */

private const val MIN_SCALE = 0.72F
private const val MIN_ALPHA = 0.5F

class DiveOutTransformer : PageTransformer {

    override fun transformPage(page: View, position: Float) {
        when {
            position < -1F -> {
                page.isVisible = false
                page.scaleX = MIN_SCALE
                page.scaleY = MIN_SCALE
                page.alpha = MIN_ALPHA
                page.translationX = -position * page.width
            }

            position <= 0 -> {
                page.isVisible = true
                page.scaleX = 1F + (1F - MIN_SCALE) * position
                page.scaleY = 1F + (1F - MIN_SCALE) * position
                page.alpha = 1F + (1F - MIN_ALPHA) * position
                page.translationX = -position * page.width
            }

            else -> {
                page.translationX = 0F
            }
        }
    }
}