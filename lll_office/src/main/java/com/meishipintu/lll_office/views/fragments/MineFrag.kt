package com.meishipintu.lll_office.views.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.meishipintu.lll_office.R

/**
 * Created by Administrator on 2017/6/29.
 *
 * 主要功能：
 */
class MineFrag:Fragment(),View.OnClickListener{

    var fragView: View? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (fragView == null) {
            fragView = inflater?.inflate(R.layout.frag_mine, container, false)
            initUI()
        }
        return fragView
    }

    private fun initUI() {
        val title = fragView?.findViewById(R.id.tv_title) as TextView
        title.text = "我的"
        val headView = fragView?.findViewById(R.id.iv_head) as ImageView
        headView.setOnClickListener(this)
        val userName = fragView?.findViewById(R.id.tv_user_name) as TextView
        val userLevel = fragView?.findViewById(R.id.tv_user_level) as TextView
        val timesRemain = fragView?.findViewById(R.id.tv_times_remain) as TextView
        val userStates = fragView?.findViewById(R.id.iv_status) as LinearLayout

        fragView?.findViewById(R.id.rl_job_manage)?.setOnClickListener(this)
        fragView?.findViewById(R.id.rl_my_interview)?.setOnClickListener(this)
        fragView?.findViewById(R.id.rl_my_collection)?.setOnClickListener(this)
        fragView?.findViewById(R.id.rl_notice_center)?.setOnClickListener(this)
        fragView?.findViewById(R.id.rl_other_office)?.setOnClickListener(this)
        fragView?.findViewById(R.id.rl_setting)?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.iv_head ->{}
            R.id.rl_job_manage ->{}
            R.id.rl_my_interview ->{}
            R.id.rl_my_collection ->{}
            R.id.rl_notice_center ->{}
            R.id.rl_other_office ->{}
            R.id.rl_setting ->{}
        }
    }
}
