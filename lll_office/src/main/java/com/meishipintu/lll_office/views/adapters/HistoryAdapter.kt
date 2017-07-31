package com.meishipintu.lll_office.views.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.meishipintu.lll_office.R

/**
 * Created by Administrator on 2017/7/31.
 *
 * 主要功能：
 */
class HistoryAdapter(ctx: Context, dataList: List<String>,val listener:OnItemClickListener) : BasicAdapter(ctx, dataList) {
    override fun getSpecialView(container: ViewGroup?): RecyclerView.ViewHolder {
        return HistoryViewHolder(LayoutInflater.from(ctx).inflate(R.layout.item_history, container, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (getItemViewType(position) == TYPE_NORMAL) {
            val historyViewHolder = holder as HistoryViewHolder
            val searchName = dataList[position] as String
            historyViewHolder.name.text = searchName
            historyViewHolder.itemView.setOnClickListener {
                listener.onItemClick(searchName)
            }
        }
    }

}