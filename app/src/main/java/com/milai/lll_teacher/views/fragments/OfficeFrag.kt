package com.milai.lll_teacher.views.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.milai.lll_teacher.R
import com.milai.lll_teacher.models.entities.OfficeInfo
import com.milai.lll_teacher.views.adapters.OfficeAdapter

/**
 * Created by Administrator on 2017/6/21.
 *
 *
 * 主要功能：
 */

class OfficeFrag : Fragment(),View.OnClickListener{

    var dataList: MutableList<OfficeInfo>? = null
    var adapter: OfficeAdapter? = null


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.frag_office,container,false)
        initUI(view)
        return view
    }

    private fun initUI(view: View?) {
        view?.findViewById(R.id.bt_back)?.setOnClickListener(this)
        view?.findViewById(R.id.bt_search)?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.bt_back -> activity.onBackPressed()
        }
    }
}
