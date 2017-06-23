package com.milai.lll_teacher.views.adapters

import android.support.v7.widget.RecyclerView
import android.view.TextureView
import android.view.View
import android.widget.TextView
import com.milai.lll_teacher.R

/**
 * Created by Administrator on 2017/6/23.
 *
 * 主要功能：
 */
class SysNoticeViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val title = view.findViewById(R.id.notice_title) as TextView
    val time = view.findViewById(R.id.notice_time) as TextView
    val content = view.findViewById(R.id.notice_content) as TextView
}