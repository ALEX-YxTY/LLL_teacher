package com.milai.lll_teacher.custom.view

import android.content.Context
import android.content.res.ColorStateList
import android.drm.DrmStore
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import com.milai.lll_teacher.R

/**
 * Created by Administrator on 2017/8/10.
 *
 * 自定义RecyclerView，包裹SwipeRefreshingLayout、recyclerview 和 loading progressbar
 */

class CanLoadMoreRecyclerView : RelativeLayout {

    val swipe:SwipeRefreshLayout by lazy { findViewById(R.id.swipe) as SwipeRefreshLayout }
    val rv:RecyclerView by lazy{ findViewById(R.id.rv) as RecyclerView}
    val pb:ProgressBar by lazy{ findViewById(R.id.pb) as ProgressBar}
    val layoutManager by lazy{ LinearLayoutManager(context)}

    var listener: StateChangedListener? = null  //状态监听，分为刷新接口和加载更多接口
    var page: Int = 0       //标记当前页数，默认初始值为0，首次加载数据后为1

    constructor(context: Context) : super(context) {
        initUi()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initUi()
//        val attributes = context.obtainStyledAttributes(attrs, R.styleable.CustomEditText)
//        if (attributes != null) {
//            val title = attributes.getString(R.styleable.CustomEditText_title)
//
//            //最后记得要回收
//            attributes.recycle()
//        }
    }

    private fun initUi() {
        LayoutInflater.from(context).inflate(R.layout.custom_recyclerview, this, true)
        swipe.setOnRefreshListener {
            listener?.onRefreshing()
        }
        swipe.setColorSchemeResources(R.color.themeOrange, R.color.text_black1)
        swipe.setDistanceToTriggerSync(500)
//        pb.indeterminateTintList = ColorStateList()
    }

    //设置adapter
    fun setAdapter(adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>) {
        rv.layoutManager = layoutManager
        rv.adapter = adapter
        rv.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                //dy!=0 避免每次notifyDataSetChanged时调用onScrolled
                if (dy != 0 && layoutManager.findLastCompletelyVisibleItemPosition() == layoutManager.itemCount - 1) {
                    pb.visibility = View.VISIBLE
                    listener?.onLoadMore(page + 1)
                    Log.d("test", "load more ${page + 1}")
                }
            }
        })

        //首次加载
        pb.visibility = View.VISIBLE
        listener?.onLoadMore(page + 1)
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
        Log.d("test","now page is $page")
    }

    interface StateChangedListener{
        fun onRefreshing()

        fun onLoadMore(page: Int)
    }

}
