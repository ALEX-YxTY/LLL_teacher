package com.milai.lll_teacher.views.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.milai.lll_teacher.models.entities.SysNoticeInfo
import com.milai.lll_teacher.R
import com.milai.lll_teacher.custom.util.DateUtil

/**
 * Created by Administrator on 2017/6/23.
 *
 * 主要功能：
 */
class SysNoticAdapter(ctx: Context, dataList: MutableList<SysNoticeInfo>) : BasicAdapter(ctx, dataList) {

    override fun getSpecialView(container: ViewGroup?): RecyclerView.ViewHolder {
        return SysNoticeViewHolder(LayoutInflater.from(ctx).inflate(R.layout.item_sys_notice, container, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (getItemViewType(position) == TYPE_NORMAL) {
            val sysNoticeViewHolder = holder as SysNoticeViewHolder
            val notice = dataList[position] as SysNoticeInfo
            sysNoticeViewHolder.title.text = notice.title
            sysNoticeViewHolder.content.text = notice.content
            sysNoticeViewHolder.time.text = DateUtil.stampToDate4(notice.create_time)
        }
    }
}
