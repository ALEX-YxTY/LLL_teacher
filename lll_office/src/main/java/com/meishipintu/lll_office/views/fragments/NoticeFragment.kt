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
import com.meishipintu.lll_office.customs.CanLoadMoreRecyclerView
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
class NoticeFragment:BasicFragment(),NoticeContract.IView,CanLoadMoreRecyclerView.StateChangedListener {

    var messageList= mutableListOf<MessageNoticeInfo>()
    var noticeList = mutableListOf<SysNoticeInfo>()
    val messageAdaper: MessageAdapter by lazy { MessageAdapter(this.activity,this.messageList) }
    val noticeAdapter: SysNoticAdapter by lazy{ SysNoticAdapter(this.activity, noticeList) }

    var fragView: View? = null
    val recyclerView:CanLoadMoreRecyclerView by lazy{ fragView?.findViewById(R.id.rv) as CanLoadMoreRecyclerView }

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
        recyclerView.listener = this
        if (type == 1) {
            recyclerView.setAdapter(messageAdaper)
        } else {
            recyclerView.setAdapter(noticeAdapter)
        }
    }

    override fun onError(e: String) {
        toast(e)
    }

    override fun onLoadMore(page: Int) {
        if (type == 1) {
            //载入私信
            (presenter as NoticeContract.IPresenter).getMessageNotice(OfficeApplication.userInfo?.uid!!,page)
        } else {
            //载入通知
            (presenter as NoticeContract.IPresenter).getSysNotice(OfficeApplication.userInfo?.uid!!,page)
        }
    }

    override fun onLoadError() {
        recyclerView.dismissProgressBar()
        recyclerView.dismissLoading()
    }

    override fun onSysNoticeGet(dataList: List<SysNoticeInfo>,page:Int) {
        if (page == 1) {
            //首次加载
            this.noticeList.clear()
            this.noticeList.addAll(dataList)
            recyclerView.onLoadSuccess(page)
            noticeAdapter.notifyDataSetChanged()
        }else if (dataList.isNotEmpty()) {
            //load more 并且有数据
            this.noticeList.addAll(dataList)
            recyclerView.onLoadSuccess(page)
            noticeAdapter.notifyDataSetChanged()
        } else {
            //load more 没数据
            recyclerView.dismissProgressBar()
            recyclerView.dismissLoading()
        }
    }

    override fun onMessageNoticeGet(dataList: List<MessageNoticeInfo>,page:Int) {
        if (page == 1) {
            //首次加载
            this.messageList.clear()
            this.messageList.addAll(dataList)
            recyclerView.onLoadSuccess(page)
            messageAdaper.notifyDataSetChanged()
        }else if (dataList.isNotEmpty()) {
            //load more 并且有数据
            this.messageList.addAll(dataList)
            recyclerView.onLoadSuccess(page)
            messageAdaper.notifyDataSetChanged()
        } else {
            //load more 没数据
            recyclerView.dismissProgressBar()
            recyclerView.dismissLoading()
        }
    }
}