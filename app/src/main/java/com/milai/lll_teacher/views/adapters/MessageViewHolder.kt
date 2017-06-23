package com.milai.lll_teacher.views.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.milai.lll_teacher.R
import org.w3c.dom.Text

/**
 * Created by Administrator on 2017/6/23.
 *
 * 主要功能：
 */
class MessageViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val ivHead = view.findViewById(R.id.iv_head) as ImageView
    val officeName = view.findViewById(R.id.office_name) as TextView
    val address = view.findViewById(R.id.tv_address) as TextView
    val recently = view.findViewById(R.id.tv_final) as TextView
    val date = view.findViewById(R.id.tv_date) as TextView

}