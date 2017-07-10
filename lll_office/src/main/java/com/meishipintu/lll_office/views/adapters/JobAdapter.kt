package com.meishipintu.lll_office.views.adapters

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.meishipintu.lll_office.Constant
import com.meishipintu.lll_office.Cookies
import com.meishipintu.lll_office.OfficeApplication
import com.meishipintu.lll_office.R
import com.meishipintu.lll_office.modles.entities.JobInfo
import com.meishipintu.lll_office.views.JobDetailActivity

/**
 * Created by Administrator on 2017/6/22.
 *
 * 主要功能：
 */
class JobAdapter(ctx: Context, dataList: List<JobInfo>): BasicAdapter(ctx,dataList) {

    val area = Cookies.getConstant(1)
    val name = OfficeApplication.userInfo?.name

    override fun getSpecialView(container: ViewGroup?): RecyclerView.ViewHolder {
        return JobInfoViewHolder(LayoutInflater.from(ctx).inflate(R.layout.item_job, container, false))
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (getItemViewType(position) == TYPE_NORMAL) {
            val job = dataList[position] as JobInfo
            val jobInfoViewHolder = holder as JobInfoViewHolder
            jobInfoViewHolder.jobName.text = job.job_name
            jobInfoViewHolder.address.text = if (job.work_area == 0) "全南京" else "南京市 ${area[job.work_area]}"
            jobInfoViewHolder.officeName.text = name
            jobInfoViewHolder.money.text = job.money
            jobInfoViewHolder.itemView.setOnClickListener{
                val intent = Intent(ctx, JobDetailActivity::class.java)
                intent.putExtra("job", dataList[position] as JobInfo)
                (ctx as AppCompatActivity).startActivityForResult(intent, Constant.CHANGE_JOB_STATE)
            }
        }
    }
}