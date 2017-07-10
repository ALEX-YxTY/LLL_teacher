package com.milai.lll_teacher.custom.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupWindow
import com.milai.lll_teacher.R
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView


/**
 * Created by Administrator on 2017/6/21.
 *
 * 主要功能：
 */
class TjPop(val ctx: Context, val mListener: MenuClickListener) : PopupWindow(ctx) {

    init {
        val view = LayoutInflater.from(ctx).inflate(R.layout.pop_tj, null)
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
        val tvTj = contentView.findViewById(R.id.tv_tj) as TextView
        val ivTj = contentView.findViewById(R.id.iv_tj) as ImageView
        val tvAll = contentView.findViewById(R.id.tv_all) as TextView
        val ivAll = contentView.findViewById(R.id.iv_all) as ImageView
        contentView.findViewById(R.id.ll_tj).setOnClickListener{
            tvTj.setTextColor(ctx.resources.getColor(R.color.themeOrange))
            ivTj.visibility = View.VISIBLE
            tvAll.setTextColor(ctx.resources.getColor(R.color.text_black3))
            ivAll.visibility = View.INVISIBLE
            mListener.onTjClick(true,"推荐")
            dismiss()
        }
        contentView.findViewById(R.id.ll_all).setOnClickListener{
            tvAll.setTextColor(ctx.resources.getColor(R.color.themeOrange))
            ivAll.visibility = View.VISIBLE
            tvTj.setTextColor(ctx.resources.getColor(R.color.text_black3))
            ivTj.visibility = View.INVISIBLE
            mListener.onTjClick(false,"全部")
            dismiss()
        }
    }

    fun showPopDropDown(parent: View) {
        if (!isShowing) {
            this.showAsDropDown(parent)
        } else {
            this.dismiss()
        }
    }

    override fun dismiss() {
        super.dismiss()
        mListener.onDismiss(1)
    }
}

