package com.meishipintu.lll_office.views.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.meishipintu.lll_office.R
import com.meishipintu.lll_office.modles.entities.SysNotice
import com.milai.lll_teacher.views.adapters.SysNoticeViewHolder

/**
 * Created by Administrator on 2017/6/23.
 *
 * 主要功能：
 */
class SysNoticAdapter(ctx: Context, dataList: MutableList<SysNotice>) : BasicAdapter(ctx, dataList) {

    override fun getSpecialView(container: ViewGroup?): RecyclerView.ViewHolder {
        return SysNoticeViewHolder(LayoutInflater.from(ctx).inflate(R.layout.item_sys_notice, container, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (getItemViewType(position) == TYPE_NORMAL) {
            val sysNoticeViewHolder = holder as SysNoticeViewHolder
            //TODO BindView
        }
    }
}