package com.meishipintu.lll_office.views

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import com.meishipintu.lll_office.R
import com.meishipintu.lll_office.modles.entities.Message
import com.meishipintu.lll_office.modles.entities.SysNotice
import com.meishipintu.lll_office.views.adapters.MessageAdapter
import com.meishipintu.lll_office.views.adapters.SysNoticAdapter

class NoticeActivity : AppCompatActivity() {

    var messageList= mutableListOf<Message>()
    var noticeList = mutableListOf<SysNotice>()
    val messageAdaper: MessageAdapter by lazy { MessageAdapter(this,this.messageList) }
    val noticeAdapter: SysNoticAdapter by lazy{ SysNoticAdapter(this, noticeList)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notice)
        initUI()
    }


    private fun initUI() {
        val tvTitle = findViewById(R.id.tv_title) as TextView
        tvTitle.text = "我的消息"
        findViewById(R.id.bt_back).setOnClickListener{ onBackPressed()}
        //初始化recyclerview
        val recyclerView = findViewById(R.id.rv) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = messageAdaper
        onMessageGet(mutableListOf())
        onNoticeGet(mutableListOf())

        //初始化tablayout
        val tabLayout = findViewById(R.id.tabLayout) as TabLayout
        tabLayout.addTab(tabLayout.newTab().setCustomView(R.layout.item_tab_2))
        tabLayout.addTab(tabLayout.newTab().setCustomView(R.layout.item_tab_2))
        (tabLayout.getTabAt(0)?.customView?.findViewById(R.id.tv_tab)as TextView).text = "私信"
        (tabLayout.getTabAt(1)?.customView?.findViewById(R.id.tv_tab)as TextView).text = "通知"
        tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab?.position == 0) {
                    recyclerView.adapter = messageAdaper
                } else {
                    recyclerView.adapter = noticeAdapter
                }
            }
        })

    }


    fun onMessageGet(messages: List<Message>) {
        for (i: Int in 1..10) {
            messageList.add(Message())
        }
        messageAdaper.notifyDataSetChanged()
    }

    fun onNoticeGet(notices: List<SysNotice>) {
        for (i: Int in 1..10) {
            noticeList.add(SysNotice())
        }
        noticeAdapter.notifyDataSetChanged()
    }

}
