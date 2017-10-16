package com.meishipintu.lll_office.views.adapters

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.meishipintu.lll_office.Constant
import com.meishipintu.lll_office.Cookies
import com.meishipintu.lll_office.OfficeApplication
import com.meishipintu.lll_office.R
import com.meishipintu.lll_office.customs.utils.DateUtil
import com.meishipintu.lll_office.customs.utils.DialogUtils
import com.meishipintu.lll_office.modles.HttpApiClinet
import com.meishipintu.lll_office.modles.HttpApiStores
import com.meishipintu.lll_office.modles.entities.JobInfo
import com.meishipintu.lll_office.views.JobDetailActivity

/**
 * Created by Administrator on 2017/6/22.
 *
 * 主要功能：
 */
class JobAdapter(ctx: Context, dataList: List<JobInfo>, val type: Int): BasicAdapter(ctx,dataList) {

    constructor(ctx: Context, dataList: List<JobInfo>, type: Int, avatar: String) : this(ctx,dataList,type) {
        this.avatar = avatar
    }

    //直接邀约adapter的构造方法
    constructor(ctx: Context, dataList: List<JobInfo>, type: Int, listener:OnItemClickListener) : this(ctx, dataList, type){
        this.listener = listener
    }

    var listener: OnItemClickListener? = null
    var avatar: String? = null
    val area = Cookies.getConstant(1)
    val glide = Glide.with(ctx)

    override fun getSpecialView(container: ViewGroup?): RecyclerView.ViewHolder {
        return JobInfoViewHolder(LayoutInflater.from(ctx).inflate(R.layout.item_job, container, false))
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (getItemViewType(position) == TYPE_NORMAL) {
            val jobInfoViewHolder = (holder as JobInfoViewHolder)
            val job = dataList[position] as JobInfo
            if (avatar == null) {
                avatar = OfficeApplication.userInfo?.avatar
            }
            glide.load(avatar).error(R.drawable.organization_default).into(jobInfoViewHolder.head)
            jobInfoViewHolder.jobName.text = job.job_name
            jobInfoViewHolder.address.text = if (job.work_area == 0) "全南京" else "南京市 ${area[job.work_area]}"
            jobInfoViewHolder.money.text = job.money
            jobInfoViewHolder.officeName.text = OfficeApplication.userInfo?.name
            jobInfoViewHolder.time.text = DateUtil.stampToDate(job.create_time)

            jobInfoViewHolder.itemView.setOnClickListener{
                if (type == 3) {
                    //弹窗，直接邀约,返回职位id
                    this.listener?.onItemClick(job.id.toString())
                } else {
                    val intent = Intent(ctx, JobDetailActivity::class.java)
                    intent.putExtra("jobId", job.id)
                    intent.putExtra("type", type)   //通知职位详情页是否要显示上下线及邀请功能
                    (ctx as AppCompatActivity).startActivityForResult(intent, Constant.CHANGE_JOB_STATE)
                }
            }
        }
    }
}