package com.meishipintu.lll_office.views.adapters

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.meishipintu.lll_office.R
import com.meishipintu.lll_office.modles.entities.Message
import com.meishipintu.lll_office.views.ChatDetailActivity

/**
 * Created by Administrator on 2017/6/23.
 *
 * 主要功能：
 */
class MessageAdapter(ctx: Context, dataList:MutableList<Message>): BasicAdapter(ctx,dataList) {

    override fun getSpecialView(container: ViewGroup?): RecyclerView.ViewHolder {
        return MessageViewHolder(LayoutInflater.from(ctx).inflate(R.layout.item_message, container, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (getItemViewType(position) == TYPE_NORMAL) {
            val messageViewHolder = holder as MessageViewHolder
            //TODO bindview
            messageViewHolder.itemView.setOnClickListener{
                val intent = Intent(ctx, ChatDetailActivity::class.java)
                intent.putExtra("message", dataList[position] as Message)
                ctx.startActivity(intent)
            }
        }
    }
}