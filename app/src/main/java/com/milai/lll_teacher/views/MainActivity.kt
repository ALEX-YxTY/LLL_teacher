package com.milai.lll_teacher.views

import android.support.v4.view.ViewPager
import android.widget.RelativeLayout
import com.milai.lll_teacher.R


class MainActivity : android.support.v7.app.AppCompatActivity() {

    val vp:ViewPager by lazy { findViewById(R.id.vp) as ViewPager }

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.milai.lll_teacher.R.layout.activity_main)

    }


}
