package com.meishipintu.lll_office.views.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.meishipintu.lll_office.R

/**
 * Created by Administrator on 2017/6/22.
 *
 * 主要功能：
 */
class JobInfoViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val head = view.findViewById(R.id.iv_head) as ImageView
        val jobName = view.findViewById(R.id.job_name) as TextView
        val officeName = view.findViewById(R.id.tv_office) as TextView
        val address = view.findViewById(R.id.tv_address) as TextView
        val money = view.findViewById(R.id.tv_money) as TextView
        val time = view.findViewById(R.id.tv_time) as TextView
}