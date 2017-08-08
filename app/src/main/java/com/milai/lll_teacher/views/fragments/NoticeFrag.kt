package com.milai.lll_teacher.views.fragments

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.milai.lll_teacher.MyApplication
import com.milai.lll_teacher.models.entities.MessageNoticeInfo
import com.milai.lll_teacher.R
import com.milai.lll_teacher.contracts.NoticeContract
import com.milai.lll_teacher.models.entities.Message
import com.milai.lll_teacher.models.entities.SysNoticeInfo
import com.milai.lll_teacher.presenters.NoticePresenter
import com.milai.lll_teacher.views.adapters.MessageAdapter
import com.milai.lll_teacher.views.adapters.SysNoticAdapter

/**
 * Created by Administrator on 2017/6/21.
 *
 * 主要功能：
 */
class NoticeFrag :BasicFragment(),NoticeContract.IView{

    var fragview: View? = null
    var messageList= mutableListOf<MessageNoticeInfo>()
    var noticeList = mutableListOf<SysNoticeInfo>()
    val messageAdaper: MessageAdapter by lazy { MessageAdapter(this.activity,this.messageList) }
    val noticeAdapter: SysNoticAdapter by lazy{ SysNoticAdapter(this.activity, noticeList)}

    val recyclerView:RecyclerView by lazy{ fragview?.findViewById(R.id.rv) as RecyclerView}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (presenter == null) {
            presenter = NoticePresenter(this)
        }
    }
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

        recyclerView.layoutManager = LinearLayoutManager(this.activity)
        recyclerView.adapter = messageAdaper
        Log.d("test", "uid:${MyApplication.userInfo?.uid!!}")
        (presenter as NoticeContract.IPresenter).getMessageNotice(MyApplication.userInfo?.uid!!)

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
                    (presenter as NoticeContract.IPresenter).getMessageNotice(MyApplication.userInfo?.uid!!)
                } else {
                    (presenter as NoticeContract.IPresenter).getSysNotice(MyApplication.userInfo?.uid!!)
                }
            }
        })

    }

    override fun showError(err: String) {
        toast(err)
    }

    override fun onSysNoticeGet(dataList: List<SysNoticeInfo>) {
        recyclerView.adapter = noticeAdapter
        noticeList.clear()
        noticeList.addAll(dataList)
        noticeAdapter.notifyDataSetChanged()
    }

    override fun onMessageNoticeGet(dataList: List<MessageNoticeInfo>) {
        recyclerView.adapter = messageAdaper
        messageList.clear()
        messageList.addAll(dataList)
        messageAdaper.notifyDataSetChanged()
    }


}
