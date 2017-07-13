package com.milai.lll_teacher.views.fragments

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TabHost
import android.widget.TextView
import com.milai.lll_teacher.R
import com.milai.lll_teacher.contracts.NoticeContract
import com.milai.lll_teacher.models.entities.Message
import com.milai.lll_teacher.models.entities.SysNotice
import com.milai.lll_teacher.views.adapters.MessageAdapter
import com.milai.lll_teacher.views.adapters.SysNoticAdapter

/**
 * Created by Administrator on 2017/6/21.
 *
 * 主要功能：
 */
class NoticeFrag :Fragment(),NoticeContract.IView{

    var fragview: View? = null
    var messageList= mutableListOf<Message>()
    var noticeList = mutableListOf<SysNotice>()
    val messageAdaper: MessageAdapter by lazy { MessageAdapter(this@NoticeFrag.activity,this.messageList) }
    val noticeAdapter: SysNoticAdapter by lazy{ SysNoticAdapter(this.activity, noticeList)}

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (fragview == null) {
            fragview = inflater?.inflate(R.layout.frag_notice, container, false)
            initUI()
        }
        return fragview
    }

    private fun initUI() {
        val tvTitle = fragview?.findViewById(R.id.tv_title) as TextView
        tvTitle.text = "我的消息"
        //初始化recyclerview
        val recyclerView = fragview?.findViewById(R.id.rv) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this.activity)
        recyclerView.adapter = messageAdaper
        onMessageGet(mutableListOf())
        onNoticeGet(mutableListOf())

        //初始化tablayout
        val tabLayout = fragview?.findViewById(R.id.tabLayout) as TabLayout
        tabLayout.addTab(tabLayout.newTab().setCustomView(R.layout.item_tab_2))
        tabLayout.addTab(tabLayout.newTab().setCustomView(R.layout.item_tab_2))
        (tabLayout.getTabAt(0)?.customView?.findViewById(R.id.tv_tab)as TextView).text = "私信"
        (tabLayout.getTabAt(1)?.customView?.findViewById(R.id.tv_tab)as TextView).text = "通知"
        tabLayout.addOnTabSelectedListener(object:TabLayout.OnTabSelectedListener{

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

    override fun showError(err: String) {
    }

    override fun onMessageGet(messages: List<Message>) {
        for (i: Int in 1..10) {
//            messageList.add(Message())
        }
        messageAdaper.notifyDataSetChanged()
    }

    override fun onNoticeGet(notices: List<SysNotice>) {
        for (i: Int in 1..10) {
            noticeList.add(SysNotice())
        }
        noticeAdapter.notifyDataSetChanged()
    }

}
