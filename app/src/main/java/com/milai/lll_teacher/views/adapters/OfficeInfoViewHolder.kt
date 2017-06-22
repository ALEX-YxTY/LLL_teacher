package com.milai.lll_teacher.views.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.milai.lll_teacher.R

/**
 * Created by Administrator on 2017/6/22.
 *
 * 主要功能：
 */
class OfficeInfoViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val ivHead = view.findViewById(R.id.iv_head) as ImageView
    val officeName = view.findViewById(R.id.office_name) as TextView
    val address = view.findViewById(R.id.tv_address) as TextView
    val desc = view.findViewById(R.id.tv_decs) as TextView
}