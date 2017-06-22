package com.milai.lll_teacher.custom.view

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.PopupWindow
import android.widget.SimpleAdapter
import com.milai.lll_teacher.R

/**
 * Created by Administrator on 2017/6/22.
 *
 * 主要功能：
 */
class AreaPop(val ctx: Context, val mListenr: MenuClickListener, val areas: Array<String>) : PopupWindow(ctx) {


    init {
        val view = LayoutInflater.from(ctx).inflate(R.layout.pop_area, null)
        contentView = view
        width = ViewGroup.LayoutParams.MATCH_PARENT
        height = ViewGroup.LayoutParams.WRAP_CONTENT
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.isFocusable = true
        this.isOutsideTouchable = true
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(ColorDrawable(0))
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.animationStyle = R.style.popAnimation

        initUI()
    }

    private fun initUI() {
        val lvCity = contentView.findViewById(R.id.list_city) as ListView
        val hashMap = HashMap<String, String>()
        hashMap.put("city","南京")
        lvCity.adapter = SimpleAdapter(ctx, listOf(hashMap), R.layout.item_city, arrayOf("city")
                , intArrayOf(R.id.tv_city))

        val llArea = contentView.findViewById(R.id.list_area) as RecyclerView
        val myAreaAdapter = MyAreaAdapter(areas, ctx)
        llArea.layoutManager = LinearLayoutManager(ctx)
        llArea.adapter = myAreaAdapter

        contentView.findViewById(R.id.bt_reset).setOnClickListener{
            myAreaAdapter.resetSelect()
        }
        contentView.findViewById(R.id.bt_filtrate).setOnClickListener{
            mListenr.onArerSelect(myAreaAdapter.select,areas[myAreaAdapter.select])
            this.dismiss()
        }
    }

    fun showPopDropDown(view: View) {
        if (!isShowing) {
            this.showAsDropDown(view)
        } else {
            this.dismiss()
        }
    }

    override fun dismiss() {
        super.dismiss()
        mListenr.onDismiss(2)
    }
}