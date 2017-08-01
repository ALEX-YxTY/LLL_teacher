package com.meishipintu.lll_office.views.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.meishipintu.lll_office.R
import com.meishipintu.lll_office.contract.NewsContract
import kotlinx.android.synthetic.*

/**
 * Created by Administrator on 2017/6/29.
 *
 * 主要功能：
 */
class ActivityFrag:BasicFragment(){

    var rootView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (presenter == null) {

        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (rootView == null) {
            rootView = inflater!!.inflate(R.layout.frag_active, container, false)
            val textView = rootView?.findViewById(R.id.tv_title) as TextView
            textView.text = "活动"
            initRv()
            if (presenter != null) {
            }
        }
        return  rootView
    }

    private fun initRv() {
        val rv = rootView?.findViewById(R.id.rv_active) as RecyclerView
        rv.layoutManager = LinearLayoutManager(this.activity)

    }
}
