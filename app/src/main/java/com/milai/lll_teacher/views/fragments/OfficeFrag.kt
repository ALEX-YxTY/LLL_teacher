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
import com.milai.lll_teacher.custom.view.CanLoadMoreRecyclerView
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

class OfficeFrag : BasicFragment(),View.OnClickListener,OfficeContract.IView,CanLoadMoreRecyclerView.StateChangedListener{

    var dataList = ArrayList<OfficeInfo>()
    var adapter: OfficeAdapter? = null

    var fragView: View? = null
    val rv:CanLoadMoreRecyclerView by lazy { fragView?.findViewById(R.id.rv) as CanLoadMoreRecyclerView }

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
        view.findViewById(R.id.bt_search).setOnClickListener(this)
        adapter = OfficeAdapter(this.activity, dataList)
        rv.listener = this
        rv.setAdapter(adapter!!)
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

    override fun onDataGet(dataList: List<OfficeInfo>,page:Int) {
        if (page == 1) {
            //首次加载
            this.dataList.clear()
            this.dataList.addAll(dataList)
            rv.onLoadSuccess(page)
            adapter?.notifyDataSetChanged()
        }else if (dataList.isNotEmpty()) {
            //load more 并且有数据
            this.dataList.addAll(dataList)
            rv.onLoadSuccess(page)
            adapter?.notifyDataSetChanged()
        } else {
            //load more 没数据
            rv.dismissProgressBar()
            rv.dismissLoading()
        }
    }

    override fun showError(err: String) {
        toast(err)
    }

    override fun onLoadError() {
        rv.dismissProgressBar()
        rv.dismissLoading()
    }

    //from CanLoadMore.StateChangedListener
    override fun onLoadMore(page: Int) {
        (presenter as OfficeContract.IPresenter).getOffice(page)
    }
}
