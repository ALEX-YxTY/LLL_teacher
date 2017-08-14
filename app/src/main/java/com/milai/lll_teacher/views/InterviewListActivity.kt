package com.milai.lll_teacher.views

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import com.milai.lll_teacher.MyApplication
import com.milai.lll_teacher.R
import com.milai.lll_teacher.contracts.InterviewListContract
import com.milai.lll_teacher.custom.view.CanLoadMoreRecyclerView
import com.milai.lll_teacher.models.entities.DeliverInfo
import com.milai.lll_teacher.presenters.DeliverPresenter
import com.milai.lll_teacher.views.adapters.DeliverAdapter

/**
 *  我的投递记录和我的面试邀约界面
 */

class InterviewListActivity : BasicActivity(), InterviewListContract.IView,CanLoadMoreRecyclerView.StateChangedListener{

    val from:Int by lazy{intent.getIntExtra("from", 1)}    //1-我的投递记录，2-我的面试邀约
    val dataList = mutableListOf<DeliverInfo>()
    val adapter:DeliverAdapter by lazy{ DeliverAdapter(this, dataList)}
    val rv:CanLoadMoreRecyclerView by lazy{ findViewById(R.id.rv) as CanLoadMoreRecyclerView }
    var state:Int = 0 //标记查询状态，默认0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interview_list)
        presenter = DeliverPresenter(this)
        val title = findViewById(R.id.tv_title) as TextView
        title.text = if(from==1) "我的投递记录" else "我的面试邀约"
        initUI()
    }

    private fun initUI() {
        //initRv
        rv.listener = this
        rv.setAdapter(adapter)
        //初始化tablayout
        val tabLayout = findViewById(R.id.tabLayout) as TabLayout
        tabLayout.addTab(tabLayout.newTab().setCustomView(R.layout.item_tab_2))
        tabLayout.addTab(tabLayout.newTab().setCustomView(R.layout.item_tab_2))
        tabLayout.addTab(tabLayout.newTab().setCustomView(R.layout.item_tab_2))
        (tabLayout.getTabAt(0)?.customView?.findViewById(R.id.tv_tab)as TextView).text = "全部"
        (tabLayout.getTabAt(1)?.customView?.findViewById(R.id.tv_tab)as TextView).text = "未面试"
        (tabLayout.getTabAt(2)?.customView?.findViewById(R.id.tv_tab)as TextView).text = "已面试"
        tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                //0-全部 1-未面试 2-已面试
                state = tab?.position ?: 0
                rv.reLoad()
            }
        })
    }

    //from InterviewContract.IView
    override fun showError(err: String) {
        toast(err)
    }

    override fun onLoadError() {
        rv.dismissProgressBar()
        rv.dismissLoading()
    }

    override fun onLoadMore(page: Int) {
        (presenter as InterviewListContract.IPresenter).getDeliverHistory(MyApplication.userInfo?.uid!!
                , type = from, status = state, page = page)
    }

    //from InterviewContract.IView
    override fun onDeliverHistoryGet(dataList: List<DeliverInfo>, page: Int) {
        if (page == 1) {
            //首次加载
            this.dataList.clear()
            this.dataList.addAll(dataList)
            rv.onLoadSuccess(page)
            adapter.notifyDataSetChanged()
        }else if (dataList.isNotEmpty()) {
            //load more 并且有数据
            this.dataList.addAll(dataList)
            rv.onLoadSuccess(page)
            adapter.notifyDataSetChanged()
        } else {
            //load more 没数据
            rv.dismissProgressBar()
            rv.dismissLoading()
        }
    }
}
