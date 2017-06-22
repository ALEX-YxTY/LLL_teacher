package com.milai.lll_teacher.custom.view

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import com.milai.lll_teacher.R

/**
 * Created by Administrator on 2017/6/22.
 *
 * 主要功能：
 */
class RequirePop(val ctx: Context, val mListener: MenuClickListener, val years: List<String>
                 , val workYears: List<String>, val educations: List<String>) : PopupWindow(ctx) {
    init {
        val view = LayoutInflater.from(ctx).inflate(R.layout.pop_require, null)
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
        val yearSelect = contentView.findViewById(R.id.selectview_year) as CustomLabelSelectView
        yearSelect.setData(years)
        val workYearSelect = contentView.findViewById(R.id.selectview_workyear) as CustomLabelSelectView
        workYearSelect.setData(workYears)
        val educationSelect = contentView.findViewById(R.id.selectview_education) as CustomLabelSelectView
        educationSelect.setData(educations)
        contentView.findViewById(R.id.bt_reset).setOnClickListener{
            yearSelect.setSelect(0)
            workYearSelect.setSelect(0)
            educationSelect.setSelect(0)
        }
        contentView.findViewById(R.id.bt_filtrate).setOnClickListener{
            mListener.onRequireSelect(yearSelect.selectIndex, workYearSelect.selectIndex, educationSelect.selectIndex)
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
        mListener.onDismiss(3)
    }
}