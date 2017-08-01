package com.meishipintu.lll_office.views.adapters

import android.media.Image
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
class NewsViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.tv_title) as TextView
        val subTitle: TextView = view.findViewById(R.id.tv_subTitle) as TextView
        val img:ImageView  = view.findViewById(R.id.iv_news) as ImageView
}