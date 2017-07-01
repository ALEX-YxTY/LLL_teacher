package com.meishipintu.lll_office.views.adapters

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.meishipintu.lll_office.R
import com.meishipintu.lll_office.modles.entities.TeacherInfo
import com.meishipintu.lll_office.views.TeacherDetailActivity

/**
 * Created by Administrator on 2017/6/22.
 *
 * 主要功能：
 */
class TeacherAdapter(ctx: Context, dataList:List<TeacherInfo>): BasicAdapter(ctx,dataList) {

    override fun getSpecialView(container: ViewGroup?): RecyclerView.ViewHolder {
        return TeacherInfoViewHolder(LayoutInflater.from(ctx).inflate(R.layout.item_teacher, container, false))
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (getItemViewType(position) == TYPE_NORMAL) {
            val teacherInfoViewHolder = holder as TeacherInfoViewHolder
            //TODO bind
            teacherInfoViewHolder.itemView.setOnClickListener{
                val intent = Intent(ctx, TeacherDetailActivity::class.java)
                intent.putExtra("teacher", dataList[position] as TeacherInfo)
                ctx.startActivity(intent)
            }
        }
    }
}