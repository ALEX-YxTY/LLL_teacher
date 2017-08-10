package com.milai.lll_teacher.views.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.meishipintu.lll_office.views.adapters.HistoryViewHolder
import com.milai.lll_teacher.R

/**
 * Created by Administrator on 2017/8/10.
 *
 * 主要功能：
 */
class TestAdapter(ctx: Context, dataList:List<Int>):BasicAdapter(ctx,dataList) {
    override fun getSpecialView(container: ViewGroup?): RecyclerView.ViewHolder {
        return HistoryViewHolder(LayoutInflater.from(ctx).inflate(R.layout.item_history, container, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (getItemViewType(position) == TYPE_NORMAL) {
            val historyViewHolder = holder as HistoryViewHolder
            val searchName = dataList[position] as Integer
            historyViewHolder.name.text = searchName.toString()
        }
    }
}