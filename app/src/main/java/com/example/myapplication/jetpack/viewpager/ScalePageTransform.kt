package com.example.myapplication.jetpack.viewpager

import android.view.View
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs

/**
 * Created by john.wick on 2024/4/26 11:07.
 */
class ScalePageTransform : ViewPager2.PageTransformer {

    companion object {
        private const val DEFAULT_MIN_SCALE = 0.85F
        private const val DEFAULT_MIN_ALPHA = 0.5F
        private const val DEFAULT_SCALE = 0.5F
    }

    override fun transformPage(page: View, position: Float) {
        page.elevation = -abs(position)

        val width = page.width
        val height = page.height

        page.pivotX = width / 2F
        page.pivotY = height / 2F

        when {
            position < -1 -> {
                page.alpha = DEFAULT_MIN_ALPHA
                page.scaleX = DEFAULT_MIN_SCALE
                page.scaleY = DEFAULT_MIN_SCALE
                page.pivotX = width.toFloat()
            }

            position <= 1 -> {
                when {
                    position < 0 -> {
                        val scaleFactor = 1 + (1 - DEFAULT_MIN_SCALE) * position
                        val alphaFactor = 1 + (1 - DEFAULT_MIN_ALPHA) * position
                        page.alpha = alphaFactor
                        page.scaleX = scaleFactor
                        page.scaleY = scaleFactor
                        page.pivotX = width * (DEFAULT_SCALE + DEFAULT_SCALE * -position)
                    }

                    else -> {
                        val scaleFactor = 1 - (1 - DEFAULT_MIN_SCALE) * position
                        val alphaFactor = 1 - (1 - DEFAULT_MIN_ALPHA) * position
                        page.alpha = alphaFactor
                        page.scaleX = scaleFactor
                        page.scaleY = scaleFactor
                        page.pivotX = width * DEFAULT_SCALE * (1 - position)
                    }
                }
            }

            else -> {
                page.alpha = DEFAULT_MIN_ALPHA
                page.scaleX = DEFAULT_MIN_SCALE
                page.scaleY = DEFAULT_MIN_SCALE
                page.pivotX = 0F
            }
        }

    }
}