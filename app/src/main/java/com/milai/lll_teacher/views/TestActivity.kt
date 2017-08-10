package com.milai.lll_teacher.views

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import com.milai.lll_teacher.R
import com.milai.lll_teacher.custom.view.CanLoadMoreRecyclerView
import com.milai.lll_teacher.views.adapters.TestAdapter

class TestActivity : AppCompatActivity(),CanLoadMoreRecyclerView.StateChangedListener {


    val dataList:MutableList<Int> by lazy{ mutableListOf<Int>()}
    val adapter:TestAdapter by lazy{ TestAdapter(this, dataList) }
    val canLoadMoreRecyclerView:CanLoadMoreRecyclerView by lazy{findViewById(R.id.recyclerView) as CanLoadMoreRecyclerView }

    val handler:Handler by lazy{object:Handler(){
        override fun handleMessage(msg: Message?) {
            when (msg?.what) {
                0 ->{
                    //加载数据
                    val page = msg.arg1
                    if (page == 1) {
                        dataList.clear()
                    }
                    (1..10).mapTo(dataList) { 10 * page + it }
                    //加载完成
                    canLoadMoreRecyclerView.onLoadSuccess(page)
                    adapter.notifyDataSetChanged()
                }
                1-> {
                    //等待1秒
                    val obtainMessage = obtainMessage(0)
                    obtainMessage.arg1 = msg.arg1
                    sendMessageDelayed(obtainMessage, 1000)
                }
            }
        }
    }}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        initUI()
    }

    private fun initUI() {
        canLoadMoreRecyclerView.listener = this
        canLoadMoreRecyclerView.setAdapter(adapter)
    }

    override fun onRefreshing() {
        load(1)
    }

    override fun onLoadMore(page: Int) {
        load(page)
    }

    private fun load(page: Int) {
        val message = handler.obtainMessage(1)
        message.arg1 = page
        handler.sendMessage(message)
    }
}
