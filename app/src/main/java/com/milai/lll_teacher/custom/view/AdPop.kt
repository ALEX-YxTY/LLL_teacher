package com.milai.lll_teacher.custom.view

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupWindow
import com.bumptech.glide.Glide
import com.milai.lll_teacher.R

/**
 * Created by Administrator on 2017/12/12.
 *
 * 主要功能：
 */
class AdPop(val ctx: Context, val url: String) : PopupWindow(ctx) {

    init {
        val view = LayoutInflater.from(ctx).inflate(R.layout.pop_ad, null)
        contentView = view
        width = ViewGroup.LayoutParams.MATCH_PARENT
        height = ViewGroup.LayoutParams.MATCH_PARENT
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
        val ivAd = contentView.findViewById(R.id.iv_ad) as ImageView
        Glide.with(ctx).load(url).into(ivAd)
        contentView.findViewById(R.id.iv_close).setOnClickListener{
            dismiss()
        }
        contentView.findViewById(R.id.main).setOnClickListener{
            dismiss()
        }
    }

    fun showPop(view: View) {
        if (!isShowing) {
            this.showAtLocation(view, android.view.Gravity.CENTER, 0, 0)
        } else {
            this.dismiss()
        }
    }
}