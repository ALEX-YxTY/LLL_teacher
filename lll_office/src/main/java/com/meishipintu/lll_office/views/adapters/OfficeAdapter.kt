package com.milai.lll_teacher.views.adapters

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.meishipintu.lll_office.R
import com.meishipintu.lll_office.modles.entities.OfficeInfo
import com.meishipintu.lll_office.views.OfficeDetailActivity
import com.meishipintu.lll_office.views.adapters.BasicAdapter
import com.meishipintu.lll_office.views.adapters.OfficeInfoViewHolder

/**
 * Created by Administrator on 2017/6/22.
 *
 * 主要功能：
 */
class OfficeAdapter(ctx: Context, dataList: List<OfficeInfo>)
    : BasicAdapter(ctx,dataList) {

    val glide = Glide.with(ctx)

    override fun getSpecialView(container: ViewGroup?): RecyclerView.ViewHolder {
        return OfficeInfoViewHolder(LayoutInflater.from(ctx).inflate(R.layout.item_office, container, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (getItemViewType(position) == TYPE_NORMAL) {
            val officeInfoViewHolder = holder as OfficeInfoViewHolder
            val officeInfo = dataList[position] as OfficeInfo
            officeInfoViewHolder.officeName.text = officeInfo.name
            officeInfoViewHolder.address.text = officeInfo.address
            if (officeInfo.postion != null) {
                officeInfoViewHolder.desc.text = "热招：  ${officeInfo.postion.job_name}  等${officeInfo.count}个职位"
            } else {
                officeInfoViewHolder.desc.text = "暂无职位招聘"
            }
            glide.load(officeInfo.avatar).error(R.drawable.organization_default).into(officeInfoViewHolder.ivHead)
            officeInfoViewHolder.itemView.setOnClickListener{
                val intent = Intent(ctx, OfficeDetailActivity::class.java)
                intent.putExtra("office", dataList[position] as OfficeInfo)
                ctx.startActivity(intent)
            }
        }
    }


}