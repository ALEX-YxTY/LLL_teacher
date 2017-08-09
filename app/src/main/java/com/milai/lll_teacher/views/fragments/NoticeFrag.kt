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
import com.milai.lll_teacher.Cookies
import com.milai.lll_teacher.MyApplication
import com.milai.lll_teacher.models.entities.MessageNoticeInfo
import com.milai.lll_teacher.R
import com.milai.lll_teacher.contracts.NoticeContract
import com.milai.lll_teacher.models.entities.Message
import com.milai.lll_teacher.models.entities.SysNoticeInfo
import com.milai.lll_teacher.presenters.NoticePresenter
import com.milai.lll_teacher.views.adapters.MessageAdapter
import com.milai.lll_teacher.views.adapters.SysNoticAdapter
import okhttp3.Cookie

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
    val tabLayout:TabLayout by lazy {fragview?.findViewById(R.id.tabLayout) as TabLayout }
    val uid = MyApplication.userInfo?.uid

    var newestSysIdGet: Int = 0     //保存获取到的最新系统ID
    var currentPage:Int = 0         //标记当前页

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
        (presenter as NoticeContract.IPresenter).getNewestSysId(uid!!)
        (presenter as NoticeContract.IPresenter).getNewestMessId(uid)
        return fragview
    }

    private fun initUI() {
        val tvTitle = fragview?.findViewById(R.id.tv_title) as TextView
        tvTitle.text = "我的消息"
        //初始化recyclerview

        recyclerView.layoutManager = LinearLayoutManager(this.activity)
        recyclerView.adapter = messageAdaper

        //初始化tablayout
        tabLayout.addTab(tabLayout.newTab().setCustomView(R.layout.item_tab_2))
        tabLayout.addTab(tabLayout.newTab().setCustomView(R.layout.item_tab_2))
        (presenter as NoticeContract.IPresenter).getMessageNotice(uid!!)

        (tabLayout.getTabAt(0)?.customView?.findViewById(R.id.tv_tab)as TextView).text = "私信"
        (tabLayout.getTabAt(1)?.customView?.findViewById(R.id.tv_tab)as TextView).text = "通知"
        tabLayout.addOnTabSelectedListener(object:TabLayout.OnTabSelectedListener{

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                //重置页码
                currentPage = 0
                if (tab?.position == 0) {
                    (presenter as NoticeContract.IPresenter).getMessageNotice(uid)
                } else {
                    (presenter as NoticeContract.IPresenter).getSysNotice(uid)
                    if (newestSysIdGet > 0) {
                        Cookies.saveNewestSysId(newestSysIdGet, uid)
                    }
                }
            }
        })

    }

    override fun showError(err: String) {
        toast(err)
    }

    override fun onSysNoticeGet(dataList: List<SysNoticeInfo>) {
        if (dataList.isNotEmpty()) {
            currentPage++
            recyclerView.adapter = noticeAdapter
            noticeList.clear()
            noticeList.addAll(dataList)
            noticeAdapter.notifyDataSetChanged()
        }
    }

    override fun onMessageNoticeGet(dataList: List<MessageNoticeInfo>) {
        if (dataList.isNotEmpty()) {
            currentPage++
            recyclerView.adapter = messageAdaper
            messageList.clear()
            messageList.addAll(dataList)
            messageAdaper.notifyDataSetChanged()
        }
    }

    override fun onNewestMessIdGet(id: Int) {
        if (id > 0 && id > Cookies.getNewestMesId(uid!!)) {
            Cookies.saveNewestMesId(id, uid)
            //显示红点
            tabLayout.getTabAt(0)?.customView?.findViewById(R.id.red_point)?.visibility = View.VISIBLE
        } else {
            //隐藏红点
            tabLayout.getTabAt(0)?.customView?.findViewById(R.id.red_point)?.visibility = View.GONE
        }
    }

    override fun onNewestSysIdGet(id: Int) {
        if (id > 0 && id > Cookies.getNewestSysId(uid!!)) {
            newestSysIdGet = id
            //显示红点
            tabLayout.getTabAt(1)?.customView?.findViewById(R.id.red_point)?.visibility = View.VISIBLE
        } else {
            //隐藏红点
            tabLayout.getTabAt(1)?.customView?.findViewById(R.id.red_point)?.visibility = View.GONE
        }
    }

}
