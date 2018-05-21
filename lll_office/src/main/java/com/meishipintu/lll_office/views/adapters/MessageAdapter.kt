package com.meishipintu.lll_office.views.adapters

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.meishipintu.lll_office.R
import com.meishipintu.lll_office.customs.utils.DateUtil
import com.meishipintu.lll_office.modles.entities.JobInfo
import com.meishipintu.lll_office.modles.entities.Message
import com.meishipintu.lll_office.modles.entities.MessageNoticeInfo
import com.meishipintu.lll_office.views.ChatDetailActivity

/**
 * Created by Administrator on 2017/6/23.
 *
 * 主要功能：
 */
class MessageAdapter(ctx: Context, dataList:MutableList<MessageNoticeInfo>): BasicAdapter(ctx,dataList) {

    val glide = Glide.with(ctx)

    override fun getSpecialView(container: ViewGroup?): RecyclerView.ViewHolder {
        return MessageViewHolder(LayoutInflater.from(ctx).inflate(R.layout.item_message, container, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (getItemViewType(position) == TYPE_NORMAL) {
            val messageViewHolder = holder as MessageViewHolder
            val messageInfo = dataList[position] as MessageNoticeInfo

            glide.load(messageInfo.avatar).error(if(messageInfo.sex==1)R.drawable.teacher_default_female else R.drawable.teacher_default).into(messageViewHolder.ivHead)
            messageViewHolder.officeName.text = "${messageInfo.name}老师"
            messageViewHolder.address.text = messageInfo.job_name
            messageViewHolder.recently.text = messageInfo.content
            messageViewHolder.date.text = DateUtil.stampToDate(messageInfo.chat_create_time.toString())
            messageViewHolder.itemView.setOnClickListener{
                val intent = Intent(ctx, ChatDetailActivity::class.java)
                intent.putExtra("oid", messageInfo.oid)
                intent.putExtra("jobId", messageInfo.pid)
                intent.putExtra("teacher", messageInfo.tid)
                ctx.startActivity(intent)
            }
        }
    }
}