package com.milai.lll_teacher.views

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import com.milai.lll_teacher.R
import com.milai.lll_teacher.views.adapters.GuidePageAdapter

class GuideActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide)
        initGuide()
    }

    private fun initGuide() {
        val images = listOf(R.drawable.start1, R.drawable.start2, R.drawable.start3)

        val llIndicator = findViewById(R.id.ll_indicator) as LinearLayout
        var indicatorIndex = 0      //指示当前圆点
        for (i: Int in 0 until images.size) {
            val indicator = View(this)
            val params = LinearLayout.LayoutParams(15, 15)
            when (i) {
                indicatorIndex -> indicator.setBackgroundResource(R.drawable.shape_cicle_orange)
                else ->{
                    indicator.setBackgroundResource(R.drawable.shape_cicle_grey)
                    params.leftMargin = 20
                }
            }
            indicator.layoutParams = params
            indicator.isEnabled = false
            llIndicator.addView(indicator)
        }
        val vp = findViewById(R.id.banner) as ViewPager
        vp.setOnClickListener{ Log.d("test", "onclick") }
        vp.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                llIndicator.getChildAt(indicatorIndex).setBackgroundResource(R.drawable.shape_cicle_grey)
                llIndicator.getChildAt(position).setBackgroundResource(R.drawable.shape_cicle_orange)
                indicatorIndex = position
            }

            override fun onPageScrollStateChanged(state: Int) {
            }

        })
        vp.adapter = GuidePageAdapter(this, images)
    }
}