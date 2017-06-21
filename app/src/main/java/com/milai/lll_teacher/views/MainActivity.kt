package com.milai.lll_teacher.views

import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.milai.lll_teacher.R
import com.milai.lll_teacher.views.adapters.MyviewPagerAdapter
import com.milai.lll_teacher.views.fragments.JobFragment
import com.milai.lll_teacher.views.fragments.MineFrag
import com.milai.lll_teacher.views.fragments.NoticeFrag
import com.milai.lll_teacher.views.fragments.OfficeFrag


class MainActivity : android.support.v7.app.AppCompatActivity() {

    val vp:ViewPager by lazy { findViewById(R.id.vp) as ViewPager }
    val tabLayout:TabLayout by lazy { findViewById(R.id.tabLayout) as TabLayout }

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.milai.lll_teacher.R.layout.activity_main)
        initViewPager()
    }

    private fun initViewPager() {
        val iconList = listOf(R.drawable.selector_icon_job, R.drawable.selector_icon_office
                , R.drawable.selector_icon_notice, R.drawable.selector_icon_mine)
        val nameList = listOf("职位", "机构", "消息", "我的")

        val jobFrag = JobFragment()
        val officeFrag = OfficeFrag()
        val noticeFrag = NoticeFrag()
        val mineFrag = MineFrag()
        val dataList = listOf(jobFrag, officeFrag, noticeFrag, mineFrag)
        vp.adapter = MyviewPagerAdapter(supportFragmentManager, dataList)
        tabLayout.setupWithViewPager(vp)
        for (it: Int in 0..tabLayout.tabCount - 1) {
            val tab = tabLayout.getTabAt(it)
            tab?.setCustomView(R.layout.item_tab)
            val textView = tab?.customView?.findViewById(R.id.tv_icon) as TextView
            textView.text = nameList[it]
            val imageView = tab.customView?.findViewById(R.id.iv_icon) as ImageView
            imageView.setImageResource(iconList[it])

        }
    }


}
