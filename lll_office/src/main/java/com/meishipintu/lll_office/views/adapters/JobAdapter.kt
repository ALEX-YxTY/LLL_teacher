package com.meishipintu.lll_office.views.adapters

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.meishipintu.lll_office.Cookies
import com.meishipintu.lll_office.OfficeApplication
import com.meishipintu.lll_office.R
import com.meishipintu.lll_office.customs.utils.DateUtil
import com.meishipintu.lll_office.modles.entities.JobInfo
import com.meishipintu.lll_office.views.JobDetailActivity

/**
 * Created by Administrator on 2017/6/22.
 *
 * 主要功能：
 */
class JobAdapter(ctx: Context, dataList: List<JobInfo>, val type: Int): BasicAdapter(ctx,dataList) {

    val area = Cookies.getConstant(1)
    val glide = Glide.with(ctx)

    override fun getSpecialView(container: ViewGroup?): RecyclerView.ViewHolder {
        return JobInfoViewHolder(LayoutInflater.from(ctx).inflate(R.layout.item_job, container, false))
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (getItemViewType(position) == TYPE_NORMAL) {
            val jobInfoViewHolder = (holder as JobInfoViewHolder)
            val job = dataList[position] as JobInfo

            glide.load(OfficeApplication.userInfo?.avatar).error(R.drawable.organization_default).into(jobInfoViewHolder.head)
            jobInfoViewHolder.jobName.text = job.job_name
            jobInfoViewHolder.address.text = if (job.work_area == 0) "全南京" else "南京市 ${area[job.work_area]}"
            jobInfoViewHolder.money.text = job.money
            jobInfoViewHolder.officeName.text = OfficeApplication.userInfo?.name
            jobInfoViewHolder.time.text = DateUtil.stampToDate(job.create_time)

            jobInfoViewHolder.itemView.setOnClickListener{
                val intent = Intent(ctx, JobDetailActivity::class.java)
                intent.putExtra("jobId", (dataList[position] as JobInfo).id)
                intent.putExtra("type", type)   //通知职位详情页是否要显示上下线及邀请功能
                ctx.startActivity(intent)
            }
        }
    }
}