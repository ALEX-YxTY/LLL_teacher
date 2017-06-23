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
import com.milai.lll_teacher.views.SearchActivity
import com.milai.lll_teacher.views.adapters.OfficeAdapter

/**
 * Created by Administrator on 2017/6/21.
 *
 *
 * 主要功能：
 */

class OfficeFrag : Fragment(),View.OnClickListener,OfficeContract.IView{

    var dataList: MutableList<OfficeInfo>? = null
    var adapter: OfficeAdapter? = null


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.frag_office,container,false)
        initUI(view)
        return view
    }

    private fun initUI(view: View?) {
        val title = view?.findViewById(R.id.tv_title) as TextView
        title.text = "机构"
        view.findViewById(R.id.bt_back).visibility = GONE
        view.findViewById(R.id.bt_search).setOnClickListener(this)
        val rv = view.findViewById(R.id.rv) as RecyclerView
        dataList = mutableListOf()
        adapter = OfficeAdapter(this.activity, dataList!!)
        rv.layoutManager = LinearLayoutManager(this.activity)
        rv.adapter = adapter
        onDataGet(mutableListOf())
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
        for (i: Int in 1..10) {
            this.dataList?.add(OfficeInfo())
        }
        adapter?.notifyDataSetChanged()
    }

    override fun showError(err: String) {
    }
}
