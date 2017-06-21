package com.milai.lll_teacher.views.fragments

import android.content.Intent
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import com.milai.lll_teacher.R
import com.milai.lll_teacher.custom.view.TjClickListener
import com.milai.lll_teacher.custom.view.TjPop
import com.milai.lll_teacher.views.SearchActivity

/**
 * Created by Administrator on 2017/6/21.
 *
 *
 * 主要功能：
 */

class JobFragment : Fragment(),TjClickListener{

    var popShow = 0 //0-无显示，1-显示推荐，2-显示地区，3-显示要求
    var tj = 0 //0-推荐，1-全部
    var area = 0 //0-全部，index-区序号
    var year = 0    //0-全部，1-25:30 ，2-30:35 , 3-35:40， 4-40:45 ， 5-45:50， 6-50：55 ，7->55
    var workYear =0 //0-全部，1-<1, 2-1:3, 3-3:5, 4-5:10, 5->10
    var education = 0   //0-全部，1-中专及以下，2-高中，3-大专，4-本科，5-硕士，6-博士

    var popTj: TjPop? = null
    var popArea: PopupWindow? = null
    var popRequire: PopupWindow? = null

    var tvTj:TextView?=null
    var tvArea:TextView?=null
    var tvRequire:TextView?=null

    override fun onCreateView(inflater: LayoutInflater?, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.frag_job, container, false)
        tvTj = view.findViewById(R.id.tv_1) as TextView
        tvArea = view.findViewById(R.id.tv_2) as TextView
        tvRequire = view.findViewById(R.id.tv_3) as TextView

        initListener(view)
        return view
    }

    private fun initListener(view:View) {
        val tab = view.findViewById(R.id.ll_tab) as LinearLayout
        view.findViewById(R.id.rl_search).setOnClickListener{
            //启动搜索页
            val intent = Intent(this@JobFragment.activity, SearchActivity::class.java)
            intent.putExtra("from", 1)  //来源 1-职位 2-机构
            startActivity(intent)
        }
        view.findViewById(R.id.rl_tj).setOnClickListener({
            if (popTj == null) {
                popTj = TjPop(this@JobFragment.activity,this@JobFragment)
            }
            popTj?.showPopDropDown(tab)
        })
    }

    //from TjClickListener
    override fun onClick(index: Int, name: String) {
        tj = index
        tvTj?.text = name
    }
}
