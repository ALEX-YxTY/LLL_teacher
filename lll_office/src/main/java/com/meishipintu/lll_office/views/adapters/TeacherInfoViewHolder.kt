package com.meishipintu.lll_office.views.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.meishipintu.lll_office.R

/**
 * Created by Administrator on 2017/6/22.
 *
 * 主要功能：
 */
class TeacherInfoViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    val head = view.findViewById(R.id.iv_head) as ImageView
    val teacherName = view.findViewById(R.id.teacher_name) as TextView
    val course = view.findViewById(R.id.tv_course) as TextView
    val star = view.findViewById(R.id.star) as RatingBar
    val socre = view.findViewById(R.id.tv_score) as TextView
    val number = view.findViewById(R.id.tv_number) as TextView
    val special = view.findViewById(R.id.iv_special) as ImageView
}