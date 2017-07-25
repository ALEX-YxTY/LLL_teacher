package com.meishipintu.lll_office.views

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.meishipintu.lll_office.R
import com.meishipintu.lll_office.views.adapters.MyviewPagerAdapter
import com.meishipintu.lll_office.views.fragments.NoticeFragment

class NoticeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notice)
        initUI()
    }

    private fun initUI() {
        val tvTitle = findViewById(R.id.tv_title) as TextView
        tvTitle.text = "我的消息"
        findViewById(R.id.bt_back).setOnClickListener{ onBackPressed()}

        //初始化tablayout
        val tabLayout = findViewById(R.id.tabLayout) as TabLayout
        val viewPager = findViewById(R.id.vp) as ViewPager
        val messageFrag = NoticeFragment()
        val bundle1 = Bundle()
        bundle1.putInt("type", 1)
        messageFrag.arguments = bundle1
        val sysFrag = NoticeFragment()
        val bundle2 = Bundle()
        bundle2.putInt("type", 2)
        sysFrag.arguments = bundle2
        val dataList = listOf(messageFrag,sysFrag)
        val nameList = listOf("私信","通知")
        viewPager.adapter = MyviewPagerAdapter(supportFragmentManager, dataList)
        tabLayout.setupWithViewPager(viewPager)
        for (it: Int in 0..tabLayout.tabCount - 1) {
            val tab = tabLayout.getTabAt(it)
            tab?.setCustomView(R.layout.item_tab_2)
            val textView = tab?.customView?.findViewById(R.id.tv_tab) as TextView
            textView.text = nameList[it]
        }
    }

}
