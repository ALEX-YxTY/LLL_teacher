package com.milai.lll_teacher.views.fragments

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.milai.lll_teacher.Cookies
import com.milai.lll_teacher.MyApplication
import com.milai.lll_teacher.R
import com.milai.lll_teacher.contracts.NoticeContract
import com.milai.lll_teacher.custom.view.CanLoadMoreRecyclerView
import com.milai.lll_teacher.models.entities.AdInfo
import com.milai.lll_teacher.models.entities.MessageNoticeInfo
import com.milai.lll_teacher.models.entities.SysNoticeInfo
import com.milai.lll_teacher.models.entities.VersionInfo
import com.milai.lll_teacher.presenters.NoticePresenter
import com.milai.lll_teacher.views.LoginAndRegistActivity
import com.milai.lll_teacher.views.adapters.MessageAdapter
import com.milai.lll_teacher.views.adapters.SysNoticAdapter

/**
 * Created by Administrator on 2017/6/21.
 *
 * 主要功能：
 */
class NoticeFrag :BasicFragment(),NoticeContract.IView,CanLoadMoreRecyclerView.StateChangedListener{

    var fragview: View? = null
    var messageList= mutableListOf<MessageNoticeInfo>()
    var noticeList = mutableListOf<SysNoticeInfo>()
    val messageAdaper: MessageAdapter by lazy { MessageAdapter(this.activity,this.messageList) }
    val noticeAdapter: SysNoticAdapter by lazy{ SysNoticAdapter(this.activity, noticeList)}

    val recyclerView:CanLoadMoreRecyclerView by lazy{ fragview?.findViewById(R.id.rv) as CanLoadMoreRecyclerView}
    val tabLayout: TabLayout by lazy {fragview?.findViewById(R.id.tabLayout) as TabLayout }

    var newestSysIdGet: Int = 0     //保存获取到的最新系统ID
    var isMessage:Boolean = true    //是否是消息列表

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (presenter == null) {
            presenter = NoticePresenter(this)
        }
    }
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (fragview == null) {
            fragview = inflater?.inflate(R.layout.frag_notice, container, false)
        }
        return fragview
    }

    override fun onResume() {
        super.onResume()
        if (fragview != null && MyApplication.userInfo != null) {
            initUI()
            (presenter as NoticeContract.IPresenter).getNewestSysId(MyApplication.userInfo?.uid!!)
            (presenter as NoticeContract.IPresenter).getNewestMessId(MyApplication.userInfo?.uid!!)
        }
    }

    private fun initUI() {
        val tvTitle = fragview?.findViewById(R.id.tv_title) as TextView
        tvTitle.text = "我的消息"
        //初始化recyclerview
        recyclerView.listener = this
        recyclerView.setAdapter(messageAdaper)

        //初始化tablayout
        tabLayout.removeAllTabs()
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
                    isMessage = true
                    recyclerView.setAdapter(messageAdaper)
                } else {
                    isMessage = false
                    recyclerView.setAdapter(noticeAdapter)
                    if (newestSysIdGet > 0) {
                        Cookies.saveNewestSysId(newestSysIdGet, MyApplication.userInfo?.uid ?: "")
                    }
                }
            }
        })

    }

    //from NoticeContract.IView
    override fun showError(err: String) {
        toast(err)
    }

    //from NoticeContract.IView
    override fun onLoadError() {
        recyclerView.dismissLoading()
        recyclerView.dismissProgressBar()
    }

    override fun onVersionGet(versionInfo: VersionInfo) {
        //空实现
    }

    //from CanLoadMore.Listener
    override fun onLoadMore(page: Int) {
        if (isMessage) {
            //message
            (presenter as NoticeContract.IPresenter).getMessageNotice(MyApplication.userInfo?.uid?:"",page)
        } else {
            //notice
            (presenter as NoticeContract.IPresenter).getSysNotice(MyApplication.userInfo?.uid?:"",page)
        }
    }

    override fun onSysNoticeGet(dataList: List<SysNoticeInfo>, page: Int) {
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

    override fun onMessageNoticeGet(dataList: List<MessageNoticeInfo>, page: Int) {
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

    override fun onNewestMessIdGet(id: Int) {
        Log.d("test","NoticeFrag messageId get :Cookies get ${Cookies.getNewestMesId(MyApplication.userInfo?.uid!!)}, net get $id")

        if (id > 0 && id > Cookies.getNewestMesId(MyApplication.userInfo?.uid!!)) {
            Cookies.saveNewestMesId(id, MyApplication.userInfo?.uid?:"")
            //显示红点
            tabLayout.getTabAt(0)?.customView?.findViewById(R.id.red_point)?.visibility = View.VISIBLE
        } else {
            //隐藏红点
            tabLayout.getTabAt(0)?.customView?.findViewById(R.id.red_point)?.visibility = View.GONE
        }
    }

    override fun onNewestSysIdGet(id: Int) {
        Log.d("test","NoticeFrag sysId get :Cookies get ${Cookies.getNewestSysId(MyApplication.userInfo?.uid!!)}, net get $id")
        if (id > 0 && id > Cookies.getNewestSysId(MyApplication.userInfo?.uid!!)) {
            newestSysIdGet = id
            //显示红点
            tabLayout.getTabAt(1)?.customView?.findViewById(R.id.red_point)?.visibility = View.VISIBLE
        } else {
            //隐藏红点
            tabLayout.getTabAt(1)?.customView?.findViewById(R.id.red_point)?.visibility = View.GONE
        }
    }

    override fun onAdGet(info: AdInfo) {
        //null
    }

}
