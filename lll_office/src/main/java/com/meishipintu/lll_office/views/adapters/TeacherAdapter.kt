package com.meishipintu.lll_office.views.adapters

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.meishipintu.lll_office.Cookies
import com.meishipintu.lll_office.R
import com.meishipintu.lll_office.customs.utils.NumberUtil
import com.meishipintu.lll_office.modles.entities.TeacherInfo
import com.meishipintu.lll_office.views.TeacherDetailActivity

/**
 * Created by Administrator on 2017/6/22.
 *
 * 主要功能：
 */
class TeacherAdapter(ctx: Context, dataList:List<TeacherInfo>, val type:Int): BasicAdapter(ctx,dataList) {

    constructor(ctx: Context, dataList: List<TeacherInfo>, type: Int, plus: Int, job: Int) : this(ctx, dataList, type) {
        jobid = job
        flag = plus
    }

    val courses = Cookies.getConstant(2)    //获取学科数据
    val grades = Cookies.getConstant(3)     //获取年级数据
    val status = Cookies.getConstant(10)    //求职状态数据
    val glide = Glide.with(ctx)
    /**
     * 标记特殊显示
     * flag=1，显示收藏红心
     * flag=0，不做任何显示
     */
    var flag = 0
    var jobid = -1

    override fun getSpecialView(container: ViewGroup?): RecyclerView.ViewHolder {
        return TeacherInfoViewHolder(LayoutInflater.from(ctx).inflate(R.layout.item_teacher, container, false))
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (getItemViewType(position) == TYPE_NORMAL) {
            val teacher = dataList[position]as TeacherInfo
            val teacherInfoViewHolder = holder as TeacherInfoViewHolder
            teacherInfoViewHolder.teacherName.text = "${teacher.name}老师"
            teacherInfoViewHolder.course.text = "${courses[teacher.course]} ${grades[teacher.grade]}   ${status[teacher.qz_status]}"
            teacherInfoViewHolder.number.text = "${teacher.total_number} 人评价"
            teacherInfoViewHolder.socre.text = NumberUtil.formatNumberInOne(teacher.total_score.toDouble()
                    / teacher.total_number)
            glide.load(teacher.avatar).error(if(teacher.sex==1) R.drawable.teacher_default_female
                else R.drawable.teacher_default).into(teacherInfoViewHolder.head)
            if (teacher.total_number > 0) {
                //评价大于1人
                teacherInfoViewHolder.star.rating = (teacher.total_score.toFloat() / teacher.total_number)
            } else {
                teacherInfoViewHolder.star.rating = 0f
            }
            when (flag) {
                1 -> glide.load(R.drawable.icon_lile_orange).into(teacherInfoViewHolder.special)
            }
            teacherInfoViewHolder.itemView.setOnClickListener{
                val intent = Intent(ctx, TeacherDetailActivity::class.java)
                intent.putExtra("teacher", teacher )
                intent.putExtra("type", type)  //1-从普通页进入，点击收藏 2-点击邀约
                intent.putExtra("jobId", jobid)
                ctx.startActivity(intent)
            }
        }
    }
}