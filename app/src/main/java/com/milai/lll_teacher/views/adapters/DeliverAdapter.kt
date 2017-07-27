package com.milai.lll_teacher.views.adapters

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.milai.lll_teacher.Cookies
import com.milai.lll_teacher.R
import com.milai.lll_teacher.custom.util.DateUtil
import com.milai.lll_teacher.models.entities.DeliverInfo
import com.milai.lll_teacher.views.JobDetailActivity
import com.milai.lll_teacher.views.adapters.BasicAdapter

/**
 * Created by Administrator on 2017/7/17.
 *
 * 主要功能：
 */
class DeliverAdapter(ctx: Context, dataList:List<DeliverInfo>): BasicAdapter(ctx,dataList) {

    val area = Cookies.getConstant(1)    //获取区域数据
    val glide = Glide.with(ctx)

    override fun getSpecialView(container: ViewGroup?): RecyclerView.ViewHolder {
        return JobInfoViewHolder(LayoutInflater.from(ctx).inflate(R.layout.item_job, container, false))
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (getItemViewType(position) == TYPE_NORMAL) {
            val organization = (dataList[position] as DeliverInfo).organization
            val job = (dataList[position] as DeliverInfo).position
            val deliverInfoViewHolder = holder as JobInfoViewHolder
            deliverInfoViewHolder.jobName.text = job.job_name
            deliverInfoViewHolder.officeName.text = organization.name
            glide.load(organization.avatar).error(R.drawable.organization_default).into(deliverInfoViewHolder.hear)
            deliverInfoViewHolder.money.text = job.money
            deliverInfoViewHolder.time.text = DateUtil.stampToDate(job.create_time)
            deliverInfoViewHolder.address.text = if (job.work_area == 0) "全南京" else "南京市 ${area[job.work_area]}"

            deliverInfoViewHolder.itemView.setOnClickListener{
                //进入教师详情页面
                val intent = Intent(ctx, JobDetailActivity::class.java)
                intent.putExtra("jobId", job.id)
                intent.putExtra("oid", job.oid)
                intent.putExtra("from", 2)  //隐藏投递简历按钮
                ctx.startActivity(intent)
            }
        }
    }
}