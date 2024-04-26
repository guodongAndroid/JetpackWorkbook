package com.example.myapplication.jetpack.viewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentViewpagerBinding
import com.google.android.material.tabs.TabLayoutMediator

/**
 * Created by john.wick on 2024/4/26 10:25.
 */
class ViewPagerFragment : Fragment() {

    private lateinit var binding: FragmentViewpagerBinding

    private lateinit var names: Array<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentViewpagerBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.bindViewPager()
        binding.bindTabLayout()
    }

    private fun FragmentViewpagerBinding.bindTabLayout() {
        TabLayoutMediator(tabLayout, viewpager) { tab, position ->
            tab.orCreateBadge.number = position
            tab.text = names[position]
        }.attach()
    }

    private fun FragmentViewpagerBinding.bindViewPager() {
        names = resources.getStringArray(R.array.play_list_names)
        val colors = resources.getStringArray(R.array.play_list_colors)

        viewpager.adapter =
            PlayListAdapter(this@ViewPagerFragment, names, colors)

        viewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        val recycleView = viewpager.getChildAt(0) as RecyclerView
        val paddingHorizontal = 40
        val paddingVertical = 20
        recycleView.setPadding(
            paddingHorizontal,
            paddingVertical,
            paddingHorizontal,
            paddingVertical
        )
        recycleView.clipToPadding = false

        val reverseDrawingOrder = true
        recycleView.setChildDrawingOrderCallback { childCount, i -> if (reverseDrawingOrder) childCount - 1 - i else i }

        val transform = CompositePageTransformer()
        transform.addTransformer(ScalePageTransform())
        // transform.addTransformer(RiseInTransformer())
        // transform.addTransformer(DiveOutTransformer())
        // transform.addTransformer(StackTransformer())
        transform.addTransformer(ParallaxTransformer())
        transform.addTransformer(MarginPageTransformer(20))
        viewpager.setPageTransformer(transform)

        viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                Toast.makeText(
                    viewpager.context,
                    "选中了[${names[position]}]",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}