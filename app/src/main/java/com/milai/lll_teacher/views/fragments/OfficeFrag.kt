package com.milai.lll_teacher.views.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.ViewGroup
import android.widget.TextView
import com.milai.lll_teacher.R
import com.milai.lll_teacher.contracts.OfficeContract
import com.milai.lll_teacher.models.entities.OfficeInfo
import com.milai.lll_teacher.presenters.OfficePresenter
import com.milai.lll_teacher.views.SearchActivity
import com.milai.lll_teacher.views.adapters.BasicLayoutManager
import com.milai.lll_teacher.views.adapters.LayoutLoadMoreListener
import com.milai.lll_teacher.views.adapters.OfficeAdapter

/**
 * Created by Administrator on 2017/6/21.
 *
 *
 * 主要功能：
 */

class OfficeFrag : BasicFragment(),View.OnClickListener,OfficeContract.IView,LayoutLoadMoreListener{

    var dataList = ArrayList<OfficeInfo>()
    var adapter: OfficeAdapter? = null

    var fragView: View? = null
    var currentPage = 0 //标记当前页

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (presenter == null) {
            presenter = OfficePresenter(this)
        }
    }
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (fragView == null) {
            fragView = inflater?.inflate(R.layout.frag_office,container,false)
            initUI(fragView)
        }
        return fragView
    }

    private fun initUI(view: View?) {
        val title = view?.findViewById(R.id.tv_title) as TextView
        title.text = "机构"
        view.findViewById(R.id.bt_back).visibility = GONE
        view.findViewById(R.id.bt_search).setOnClickListener(this)
        val rv = view.findViewById(R.id.rv) as RecyclerView
        adapter = OfficeAdapter(this.activity, dataList!!)
        val layoutManager = BasicLayoutManager(this.activity, this)
        rv.layoutManager = layoutManager
        rv.adapter = adapter
        rv.addOnScrollListener(object :RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                layoutManager.shouldLoadMore()
            }
        })
        (presenter as OfficeContract.IPresenter).getOffice()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.bt_search -> {
                val intent = Intent(this.activity, SearchActivity::class.java)
                intent.putExtra("from", 2)  //来源 1-职位 2-机构
                startActivity(intent)
            }
        }
    }

    override fun onDataGet(dataList: List<OfficeInfo>) {
        if (dataList.isNotEmpty()) {
            currentPage++
            this.dataList.clear()
            this.dataList.addAll(dataList)
            adapter?.notifyDataSetChanged()
        }
    }

    override fun showError(err: String) {
        toast(err)
    }

    //from LayoutLoadMoreListener
    override fun onLoadMore() {
        (presenter as OfficeContract.IPresenter).getOffice(currentPage + 1)
    }
}
