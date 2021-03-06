package com.meishipintu.lll_office.views.adapters

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.meishipintu.lll_office.R
import com.meishipintu.lll_office.customs.utils.DateUtil
import com.meishipintu.lll_office.modles.entities.ChatDetail
import com.meishipintu.lll_office.views.TeacherDetailActivity

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
        return when (viewType) {
            TYPE_EMPTY -> EmptyViewHolder(LayoutInflater.from(ctx).inflate(R.layout.item_empty, container, false))
            TYPE_LEFT -> ChatViewHolder(LayoutInflater.from(ctx).inflate(R.layout.item_talk_left, container, false))
            else -> ChatViewHolder(LayoutInflater.from(ctx).inflate(R.layout.item_talk_right, container, false))
        }
    }

    override fun getItemCount(): Int {
        return if(dataList.isEmpty()) {
            1
        } else {
            dataList.size
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if(dataList.isEmpty()&&position==0) {
            TYPE_EMPTY
        } else if (dataList[position].type == 1) {
            TYPE_LEFT
        } else {
            TYPE_RIGHT
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        Log.d("chat","now bind view position: $position")
        Log.d("chat","getItemCount: $itemCount")
        if (getItemViewType(position) != TYPE_EMPTY) {
            val chatViewHolder = holder as ChatViewHolder
            val chatDetail = dataList[position]
            chatViewHolder.content.text = chatDetail.content
            if (position == 0) {
                //第一条必显示时间
                chatViewHolder.date.visibility = View.VISIBLE
                chatViewHolder.date.text = DateUtil.stampToDate2(chatDetail.create_time)
            }else if (chatDetail.create_time - timeLast > 30 * 60) {
                //间隔半小时以上，显示时间，不足半小时，不显示
                chatViewHolder.date.visibility = View.VISIBLE
                chatViewHolder.date.text = DateUtil.stampToDate2(chatDetail.create_time)
            } else {
                chatViewHolder.date.visibility = View.GONE
            }

            if (getItemViewType(position) == TYPE_LEFT) {
                glide.load(chatDetail.avatar.teacher_avatar).error(if(chatDetail.avatar.sex==1) R.drawable.teacher_default_female
                    else R.drawable.teacher_default)
                        .into(chatViewHolder.ivHead)
            } else {
                glide.load(chatDetail.avatar.organization_avatar).placeholder(R.drawable.organization_default)
                        .error(R.drawable.organization_default).into(chatViewHolder.ivHead)
            }
            //渲染完毕后，将本条消息时间最为最后时间保存,全部渲染后timeLast还原为0
            if (position == dataList.size - 1) {
                this.timeLast = 0
            } else {
                this.timeLast = chatDetail.create_time
            }
            chatViewHolder.ivHead.setOnClickListener {
                val intent = Intent(ctx, TeacherDetailActivity::class.java)
                intent.putExtra("teacherId", chatDetail.tid )
                intent.putExtra("type", 4)  //1-从普通页进入，点击收藏 2-点击邀约 4-底部完全不显示
                ctx.startActivity(intent)
            }
        }
    }
}