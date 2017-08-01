package com.meishipintu.lll_office.views.fragments

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.meishipintu.lll_office.OfficeApplication
import com.meishipintu.lll_office.R
import com.meishipintu.lll_office.contract.NoticeContract
import com.meishipintu.lll_office.modles.entities.Message
import com.meishipintu.lll_office.modles.entities.MessageNoticeInfo
import com.meishipintu.lll_office.modles.entities.SysNoticeInfo
import com.meishipintu.lll_office.presenters.NoticePresenter
import com.meishipintu.lll_office.views.adapters.MessageAdapter
import com.meishipintu.lll_office.views.adapters.SysNoticAdapter

/**
 * Created by Administrator on 2017/7/25.
 *
 * 主要功能：
 */
class NoticeFragment:BasicFragment(),NoticeContract.IView {

    var messageList= mutableListOf<MessageNoticeInfo>()
    var noticeList = mutableListOf<SysNoticeInfo>()
    val messageAdaper: MessageAdapter by lazy { MessageAdapter(this.activity,this.messageList) }
    val noticeAdapter: SysNoticAdapter by lazy{ SysNoticAdapter(this.activity, noticeList) }

    var fragView: View? = null
    var type:Int = 1    //type=1 私信，type=2 通知

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (presenter == null) {
            presenter = NoticePresenter(this)
        }
        if (arguments != null) {
            type = arguments.get("type") as Int
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (fragView == null) {
            fragView = inflater?.inflate(R.layout.frag_notice, container, false)
            initUI()
        }
        return fragView
    }

    private fun initUI() {
        val recyclerView = fragView?.findViewById(R.id.rv) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this.activity)
        if (type == 1) {
            recyclerView.adapter = messageAdaper
            (presenter as NoticeContract.IPresenter).getMessageNotice(OfficeApplication.userInfo?.uid!!)
        } else {
            recyclerView.adapter = noticeAdapter
            (presenter as NoticeContract.IPresenter).getSysNotice(OfficeApplication.userInfo?.uid!!)
        }
    }

    override fun onError(e: String) {
        toast(e)
    }

    override fun onSysNoticeGet(dataList: List<SysNoticeInfo>) {
        noticeList.clear()
        noticeList.addAll(dataList)
        noticeAdapter.notifyDataSetChanged()
    }

    override fun onMessageNoticeGet(dataList: List<MessageNoticeInfo>) {
        messageList.clear()
        messageList.addAll(dataList)
        messageAdaper.notifyDataSetChanged()
    }
}