package com.milai.lll_teacher.views.adapters

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.milai.lll_teacher.R
import com.milai.lll_teacher.custom.view.JobDetailActivity
import com.milai.lll_teacher.models.entities.JobInfo

/**
 * Created by Administrator on 2017/6/22.
 *
 * 主要功能：
 */
class JobAdapter(ctx: Context, dataList:List<JobInfo>):BasicAdapter(ctx,dataList) {

    override fun getSpecialView(container: ViewGroup?): RecyclerView.ViewHolder {
        return JobInfoViewHolder(LayoutInflater.from(ctx).inflate(R.layout.item_job, container, false))
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (getItemViewType(position) == TYPE_NORMAL) {
            val jobInfoViewHolder = holder as JobInfoViewHolder
            //TODO bind
            jobInfoViewHolder.itemView.setOnClickListener{
                val intent = Intent(ctx, JobDetailActivity::class.java)
                intent.putExtra("job", dataList[position] as JobInfo)
                ctx.startActivity(intent)
            }
        }
    }
}