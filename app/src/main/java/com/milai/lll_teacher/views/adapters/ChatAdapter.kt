package com.milai.lll_teacher.views.adapters

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.milai.lll_teacher.MyApplication
import com.milai.lll_teacher.R
import com.milai.lll_teacher.models.entities.ChatDetail
import com.milai.lll_teacher.models.entities.Message
import com.milai.lll_teacher.views.ChatDetailActivity

/**
 * Created by Administrator on 2017/6/23.
 *
 * 主要功能：
 */
class ChatAdapter(val ctx:Context,val dataList:MutableList<ChatDetail>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val TYPE_LEFT:Int = 1
    val TYPE_RIGHT:Int = 2
    val TYPE_EMPTY: Int = 3

    override fun onCreateViewHolder(container: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        if (viewType==TYPE_EMPTY) {
            return EmptyViewHolder(LayoutInflater.from(ctx).inflate(R.layout.item_empty, container, false))
        } else if (viewType == TYPE_LEFT) {
            return EmptyViewHolder(LayoutInflater.from(ctx).inflate(R.layout.item_talk_left, container, false))
        } else {
            return EmptyViewHolder(LayoutInflater.from(ctx).inflate(R.layout.item_talk_right, container, false))
        }
    }

    override fun getItemCount(): Int {
        return if(dataList.isEmpty()) 1 else dataList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if(dataList.isEmpty()&&position==0) {
            TYPE_EMPTY
        } else if (dataList[position].type == 1) {
            TYPE_RIGHT
        } else {
            TYPE_LEFT
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (getItemViewType(position) != TYPE_EMPTY) {
            val messageViewHolder = holder as MessageViewHolder

        }
    }
}