package com.meishipintu.lll_office.views.adapters

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.meishipintu.lll_office.R
import com.meishipintu.lll_office.modles.entities.JobInfo
import com.meishipintu.lll_office.views.JobDetailActivity

/**
 * Created by Administrator on 2017/6/22.
 *
 * 主要功能：
 */
class JobAdapter(ctx: Context, dataList: List<JobInfo>): BasicAdapter(ctx,dataList) {

    override fun getSpecialView(container: ViewGroup?): RecyclerView.ViewHolder {
        return JobInfoViewHolder(LayoutInflater.from(ctx).inflate(R.layout.item_job, container, false))
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (getItemViewType(position) == TYPE_NORMAL) {
            val job = dataList[position] as JobInfo
            val jobInfoViewHolder = holder as JobInfoViewHolder
            jobInfoViewHolder.jobName.text = job.job_name
            jobInfoViewHolder.address.text = "南京市 ${job.work_area}"
            jobInfoViewHolder.officeName.text = job.organization.name
            jobInfoViewHolder.money.text = job.money
            jobInfoViewHolder.itemView.setOnClickListener{
                val intent = Intent(ctx, JobDetailActivity::class.java)
                intent.putExtra("job", dataList[position] as JobInfo)
                ctx.startActivity(intent)
            }
        }
    }
}