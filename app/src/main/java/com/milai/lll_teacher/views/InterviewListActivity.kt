package com.milai.lll_teacher.views

import android.app.job.JobInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import com.milai.lll_teacher.R
import com.milai.lll_teacher.views.adapters.JobAdapter

/**
 *  我的投递记录和我的面试邀约界面
 */

class InterviewListActivity : AppCompatActivity() {

    var from = 1    //1-我的投递记录，2-我的面试邀约
    val dataList = mutableListOf<com.milai.lll_teacher.models.entities.JobInfo>()
    val adapter:JobAdapter by lazy{ JobAdapter(this, dataList)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interview_list)
        from = intent.getIntExtra("from", 1)
        val title = findViewById(R.id.tv_title) as TextView
        title.text = if(from==1) "我的投递记录" else "我的面试邀约"
        initUI()
    }

    private fun initUI() {
        //initRv
        val rv = findViewById(R.id.rv) as RecyclerView
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter

        //初始化tablayout
        val tabLayout = findViewById(R.id.tabLayout) as TabLayout
        tabLayout.addTab(tabLayout.newTab().setCustomView(R.layout.item_tab_2))
        tabLayout.addTab(tabLayout.newTab().setCustomView(R.layout.item_tab_2))
        tabLayout.addTab(tabLayout.newTab().setCustomView(R.layout.item_tab_2))
        (tabLayout.getTabAt(0)?.customView?.findViewById(R.id.tv_tab)as TextView).text = "全部"
        (tabLayout.getTabAt(1)?.customView?.findViewById(R.id.tv_tab)as TextView).text = if(from==1) "已沟通" else "未面试"
        (tabLayout.getTabAt(1)?.customView?.findViewById(R.id.tv_tab)as TextView).text = "已面试"
        tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab?.position == 0) {

                }
            }
        })
    }
}
