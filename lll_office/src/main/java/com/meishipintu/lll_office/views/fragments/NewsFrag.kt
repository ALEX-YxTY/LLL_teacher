package com.meishipintu.lll_office.views.fragments

import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.meishipintu.lll_office.R

/**
 * Created by Administrator on 2017/6/29.
 *
 * 主要功能：
 */
class NewsFrag:Fragment(){

    var rootView: View? = null
    val rvNews:RecyclerView by lazy { rootView?.findViewById(R.id.rv_news) as RecyclerView }
    val vpAdd:ViewPager by lazy{ rootView?.findViewById(R.id.vp_add) as ViewPager}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View? {
        if (rootView == null) {
            rootView = inflater!!.inflate(R.layout.frag_news, container, false)
            val textView = rootView?.findViewById(R.id.tv_title) as TextView
            textView.text = "资讯"
            initAd()
            initRv()

        }
        return rootView
    }

    private fun initRv() {

    }

    private fun initAd() {

    }

}
