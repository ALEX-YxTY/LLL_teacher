package com.milai.lll_teacher.custom.view

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.milai.lll_teacher.R

/**
 * Created by Administrator on 2017/6/22.
 *
 * 主要功能：
 */
class MyAreaAdapter(val areas: Array<String>, val ctx: Context) :RecyclerView.Adapter<AreaViewHolder>(){

    var select = 0  //标记当前选择项

    override fun onCreateViewHolder(p0: ViewGroup?, p1: Int): AreaViewHolder {
        return AreaViewHolder(LayoutInflater.from(ctx).inflate(R.layout.item_area, p0, false))
    }

    override fun onBindViewHolder(p0: AreaViewHolder?, p1: Int) {
        p0!!.tv.text = areas[p1]
        if (p1 == select) {
            p0!!.tv.setTextColor(ctx.resources.getColor(R.color.themeOrange))
            p0!!.iv.visibility = View.VISIBLE
        } else {
            p0!!.tv.setTextColor(ctx.resources.getColor(R.color.text_black3))
            p0!!.iv.visibility = View.INVISIBLE
        }
        p0.itemView.setOnClickListener{
            select = p1
            this.notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return areas.size
    }

    fun getSelectItem():Int {
        return select
    }

    fun resetSelect() {
        select = 0
        notifyDataSetChanged()
    }
}