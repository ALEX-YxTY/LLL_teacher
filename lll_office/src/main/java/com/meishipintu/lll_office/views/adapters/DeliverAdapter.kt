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
import com.meishipintu.lll_office.modles.entities.DeliverInfo
import com.meishipintu.lll_office.modles.entities.TeacherInfo
import com.meishipintu.lll_office.views.ChatDetailActivity
import com.meishipintu.lll_office.views.JobDetailActivity
import com.meishipintu.lll_office.views.TeacherDetailActivity
import com.meishipintu.lll_office.views.TeacherInterviewActivity

/**
 * Created by Administrator on 2017/7/17.
 *
 * 主要功能：
 */
class DeliverAdapter(ctx: Context, dataList:List<DeliverInfo>): BasicAdapter(ctx,dataList) {

    val courses = Cookies.getConstant(2)    //获取学科数据
    val grades = Cookies.getConstant(3)     //获取年级数据
    val glide = Glide.with(ctx)

    override fun getSpecialView(container: ViewGroup?): RecyclerView.ViewHolder {
        return DeliverInfoViewHolder(LayoutInflater.from(ctx).inflate(R.layout.item_teacher_interview, container, false))
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (getItemViewType(position) == TYPE_NORMAL) {
            val teacher = (dataList[position] as DeliverInfo).teacher
            val deliverInfoViewHolder = holder as DeliverInfoViewHolder
            deliverInfoViewHolder.teacherName.text = "${teacher.name}老师"
            deliverInfoViewHolder.course.text = "${courses[teacher.course]} ${grades[teacher.grade]}"
            deliverInfoViewHolder.number.text = "${teacher.total_number} 人评价"
            deliverInfoViewHolder.socre.text = NumberUtil.formatNumberInOne(teacher.total_score.toDouble()
                    / teacher.total_number)
            deliverInfoViewHolder.jobName.text = (dataList[position] as DeliverInfo).postion.job_name
            if (teacher.total_number > 0) {
                deliverInfoViewHolder.star.rating = teacher.total_score.toFloat()/ teacher.total_number
            }
            glide.load(teacher.avatar).error(if(teacher.sex==1) R.drawable.teacher_default_female
                else R.drawable.teacher_default).into(deliverInfoViewHolder.head)
            deliverInfoViewHolder.chat.setOnClickListener{
                //进入沟通页
                val intent = Intent(ctx, ChatDetailActivity::class.java)
                intent.putExtra("jobId", (dataList[position] as DeliverInfo).postion.id)
                intent.putExtra("oid", (dataList[position] as DeliverInfo).postion.oid)
                intent.putExtra("teacher", teacher.uid)
                ctx.startActivity(intent)
            }
            deliverInfoViewHolder.itemView.setOnClickListener{
                //进入教师详情页面
                val intent = Intent(ctx, TeacherInterviewActivity::class.java)
                intent.putExtra("deliver", dataList[position] as DeliverInfo )
                ctx.startActivity(intent)
            }
        }
    }
}