package com.meishipintu.lll_office.views

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.widget.ImageView
import android.widget.TextView
import com.meishipintu.lll_office.R
import com.meishipintu.lll_office.views.fragments.ActivityFrag
import com.meishipintu.lll_office.views.fragments.MineFrag
import com.meishipintu.lll_office.views.fragments.NewsFrag
import com.meishipintu.lll_office.views.fragments.TeacherFrag
import com.meishipintu.lll_office.views.adapters.MyviewPagerAdapter

class MainActivity : AppCompatActivity() {

    val vp: ViewPager by lazy { findViewById(R.id.vp) as ViewPager }
    val tabLayout: TabLayout by lazy { findViewById(R.id.tabLayout) as TabLayout }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViewPager()
    }

    private fun initViewPager() {
        val iconList = listOf(R.drawable.selector_icon_news, R.drawable.selector_icon_teacher
                , R.drawable.selector_icon_activity, R.drawable.selector_icon_mine)
        val nameList = listOf("资讯", "教师", "活动", "我的")

        val newsFrag = NewsFrag()
        val teacherFrag = TeacherFrag()
        val activityFrag = ActivityFrag()
        val mineFrag = MineFrag()

        val dataList = listOf(newsFrag, teacherFrag, activityFrag, mineFrag)
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
