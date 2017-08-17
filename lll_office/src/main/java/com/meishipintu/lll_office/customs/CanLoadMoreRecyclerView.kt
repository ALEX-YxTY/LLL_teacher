package com.meishipintu.lll_office.customs

import android.content.Context
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import com.meishipintu.lll_office.R

/**
 * Created by Administrator on 2017/8/10.
 *
 * 自定义RecyclerView，包裹SwipeRefreshingLayout、recyclerview 和 loading progressbar
 */

class CanLoadMoreRecyclerView : RelativeLayout {

    val swipe:SwipeRefreshLayout by lazy { findViewById(R.id.swipe) as SwipeRefreshLayout }
    val rv:RecyclerView by lazy{ findViewById(R.id.recyclerview) as RecyclerView}
    val pb:ProgressBar by lazy{ findViewById(R.id.pb) as ProgressBar}
    val layoutManager by lazy{ LinearLayoutManager(context)}

    var listener: StateChangedListener? = null  //状态监听，分为刷新接口和加载更多接口
    var page: Int = 0       //标记当前页数，默认初始值为0，首次加载数据后为1

    constructor(context: Context) : super(context) {
        initUi()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initUi()
    }

    private fun initUi() {
        LayoutInflater.from(context).inflate(R.layout.custom_recyclerview, this, true)
        swipe.setOnRefreshListener {
            listener?.onLoadMore(1)
        }
        swipe.setColorSchemeResources(R.color.themeOrange, R.color.orange_grey)
        rv.layoutManager = layoutManager
    }

    //设置adapter并载入首页
    fun setAdapter(adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>) {
        rv.adapter = adapter
        rv.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                //dy!=0 避免每次notifyDataSetChanged时调用onScrolled
                if (dy != 0 && layoutManager.findLastCompletelyVisibleItemPosition() == layoutManager.itemCount - 1) {
                    pb.visibility = View.VISIBLE
                    listener?.onLoadMore(page + 1)
                }
            }
        })
        //载入首页
        reLoad()
    }

    //取消progressbar
    fun dismissProgressBar() {
        if (swipe.isRefreshing) {
            swipe.isRefreshing = false
        }
    }

    //取消loading
    fun dismissLoading() {
        if (pb.visibility == View.VISIBLE) {
            pb.visibility = View.GONE
        }
    }

    //加载成功的回调
    fun onLoadSuccess(page: Int) {
        dismissLoading()
        dismissProgressBar()
        this.page = page
        Log.d("test", "now page is $page")
    }

    //再次载入，不改变adapter
    fun reLoad(){
        //复原page为0
        page = 0
        //首次加载,调出swipe
        swipe.isRefreshing = true
        listener?.onLoadMore(page + 1)
    }

    //是的recyclerView滚动到底部
    fun scrollToEnd() {
        if (layoutManager.itemCount > 0) {
            rv.scrollToPosition(layoutManager.itemCount - 1)
        }
    }

    interface StateChangedListener{
        fun onLoadMore(page: Int)
    }

}
