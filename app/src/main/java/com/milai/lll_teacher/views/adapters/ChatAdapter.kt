package com.milai.lll_teacher.views.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.meishipintu.lll_office.customs.utils.DateUtil
import com.milai.lll_teacher.R
import com.milai.lll_teacher.models.entities.ChatDetail

/**
 * Created by Administrator on 2017/6/23.
 *
 * 主要功能：
 */
class ChatAdapter(val ctx:Context,val dataList:List<ChatDetail>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val TYPE_EMPTY: Int = 1
    val TYPE_LEFT:Int = 2
    val TYPE_RIGHT:Int = 3

    var timeLast = 0L   //标记最后一条消息的时间
    val glide = Glide.with(ctx)

    override fun onCreateViewHolder(container: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        if (viewType==TYPE_EMPTY) {
            return EmptyViewHolder(LayoutInflater.from(ctx).inflate(R.layout.item_empty, container, false))
        } else if (viewType == TYPE_LEFT) {
            return ChatViewHolder(LayoutInflater.from(ctx).inflate(R.layout.item_talk_left, container, false))
        } else {
            return ChatViewHolder(LayoutInflater.from(ctx).inflate(R.layout.item_talk_right, container, false))
        }
    }

    override fun getItemCount(): Int {
        if(dataList.isEmpty()) {
            return 1
        } else {
            return dataList.size
        }
    }

    override fun getItemViewType(position: Int): Int {
        if(dataList.isEmpty()&&position==0) {
            return TYPE_EMPTY
        } else if (dataList[position].type == 1) {
            return TYPE_RIGHT
        } else {
            return TYPE_LEFT
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        Log.d("chat","now bind view position: $position")
        Log.d("chat","getItemCount: $itemCount")
        if (getItemViewType(position) != TYPE_EMPTY) {
            val chatViewHolder = holder as ChatViewHolder
            val chatDetail = dataList[position]
            chatViewHolder.content.text = chatDetail.content
            if (chatDetail.create_time - timeLast > 30 * 60) {
                //间隔半小时以上，显示时间，不足半小时，不显示
                chatViewHolder.date.visibility = View.VISIBLE
                chatViewHolder.date.text = DateUtil.stampToDate2(chatDetail.create_time)
            } else {
                chatViewHolder.date.visibility = View.GONE
            }

            if (getItemViewType(position) == TYPE_LEFT) {
                glide.load(chatDetail.avatar.organization_avatar).placeholder(R.drawable.organization_default)
                        .error(R.drawable.organization_default).into(chatViewHolder.ivHead)
            } else {
                glide.load(chatDetail.avatar.teacher_avatar).placeholder(R.drawable.teacher_default)
                        .error(R.drawable.teacher_default).into(chatViewHolder.ivHead)
            }
            //渲染完毕后，将本条消息时间最为最后时间保存
            this.timeLast = chatDetail.create_time
        }
    }
}