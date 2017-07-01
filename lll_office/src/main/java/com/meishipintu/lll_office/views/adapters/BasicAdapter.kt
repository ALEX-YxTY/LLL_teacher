package com.meishipintu.lll_office.views.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.meishipintu.lll_office.R

/**
 * Created by Administrator on 2017/6/22.
 *
 * 主要功能：
 */
abstract class BasicAdapter(val ctx: Context, val dataList:List<Any>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val TYPE_NORMAL:Int = 1
    val TYPE_EMPTY: Int = 2

    override fun onCreateViewHolder(container: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        if (viewType==TYPE_EMPTY) {
            return EmptyViewHolder(LayoutInflater.from(ctx).inflate(R.layout.item_empty, container, false))
        } else {
            return getSpecialView(container)
        }
    }

    abstract fun getSpecialView(container: ViewGroup?): RecyclerView.ViewHolder

    override fun getItemCount(): Int {
        return if(dataList.isEmpty()) 1 else dataList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if(dataList.isEmpty()&&position==0) TYPE_EMPTY else TYPE_NORMAL
    }
}