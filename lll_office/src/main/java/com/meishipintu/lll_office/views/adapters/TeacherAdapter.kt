package com.meishipintu.lll_office.views.adapters

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.meishipintu.lll_office.R
import com.meishipintu.lll_office.customs.utils.NumberUtil
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
            val teacher = dataList[position]as TeacherInfo
            val teacherInfoViewHolder = holder as TeacherInfoViewHolder
            teacherInfoViewHolder.teacherName.text = teacher.name
            teacherInfoViewHolder.course.text = "${teacher.course} ${teacher.grade}"
            teacherInfoViewHolder.number.text = "${teacher.total_number} 人评价"
            teacherInfoViewHolder.socre.text = NumberUtil.formatNumberInOne(teacher.total_score.toDouble()
                    / teacher.total_number)

            teacherInfoViewHolder.itemView.setOnClickListener{
                val intent = Intent(ctx, TeacherDetailActivity::class.java)
                intent.putExtra("teacher", teacher )
                intent.putExtra("type", 1)
                ctx.startActivity(intent)
            }
        }
    }
}