package com.meishipintu.lll_office.views.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.meishipintu.lll_office.R

/**
 * Created by Administrator on 2017/6/23.
 *
 * 主要功能：
 */
class ChatViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val ivHead = view.findViewById(R.id.iv_head) as ImageView
    val content = view.findViewById(R.id.tv_content) as TextView
    val date = view.findViewById(R.id.tv_date) as TextView
}