package com.milai.lll_teacher.custom.view

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
class AreaViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    val tv = view.findViewById(R.id.tv_area) as TextView
    val iv = view.findViewById(R.id.iv_select) as ImageView

}