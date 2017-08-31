package com.milai.lll_teacher.views.adapters

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.milai.lll_teacher.Cookies
import com.milai.lll_teacher.R
import com.milai.lll_teacher.custom.util.DateUtil
import com.milai.lll_teacher.models.entities.JobInfo
import com.milai.lll_teacher.views.JobDetailActivity

/**
 * Created by Administrator on 2017/6/22.
 *
 *  type=1 job包含organization信息，type=2 job不包含organization信息
 */
class JobAdapter(ctx: Context, dataList: List<JobInfo>, val type: Int, val avatar: String? = null, val officeName: String? = null):BasicAdapter(ctx,dataList) {

    val area = Cookies.getConstant(1)
    val glide = Glide.with(ctx)

    override fun getSpecialView(container: ViewGroup?): RecyclerView.ViewHolder {
        return JobInfoViewHolder(LayoutInflater.from(ctx).inflate(R.layout.item_job, container, false))
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (getItemViewType(position) == TYPE_NORMAL) {
            val jobInfoViewHolder = (holder as JobInfoViewHolder)
            val job = dataList[position] as JobInfo
            jobInfoViewHolder.jobName.text = job.job_name
            jobInfoViewHolder.address.text = if (job.work_area == 0) "全南京" else "南京市 ${area[job.work_area]}"
            if (type == 1) {
                jobInfoViewHolder.officeName.text = job.organization.name
                glide.load(job.organization.avatar).error(R.drawable.organization_default).into(jobInfoViewHolder.hear)
            } else {
                jobInfoViewHolder.officeName.text = officeName
                glide.load(avatar).error(R.drawable.organization_default).into(jobInfoViewHolder.hear)
            }
            jobInfoViewHolder.money.text = job.money
            jobInfoViewHolder.time.text = DateUtil.stampToDate(job.create_time)

            jobInfoViewHolder.itemView.setOnClickListener{
                val intent = Intent(ctx, JobDetailActivity::class.java)
                intent.putExtra("oid",job.oid)
                intent.putExtra("jobId", job.id)
                intent.putExtra("type", type)   //通知职位详情页是否要显示机构信息
                ctx.startActivity(intent)
            }
        }
    }
}