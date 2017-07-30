package com.meishipintu.lll_office.customs

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import com.meishipintu.lll_office.R

/**
 * Created by Administrator on 2017/6/22.
 *
 * 主要功能：
 */
class RequirePop(val ctx: Context, val mListener: MenuClickListener, val courses: List<String>
                 , val grades: List<String>, val experiences: List<String>) : PopupWindow(ctx) {

    val courseSelect:CustomLabelSelectView by lazy{contentView.findViewById(R.id.selectview_course)
            as CustomLabelSelectView}
    val gradeSelect:CustomLabelSelectView by lazy{contentView.findViewById(R.id.selectview_grade)
            as CustomLabelSelectView}
    val experienceSelect :CustomLabelSelectView by lazy{contentView.findViewById(R.id.selectview_experience)
            as CustomLabelSelectView}

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

        contentView.findViewById(R.id.bt_reset).setOnClickListener{
            courseSelect.setSelect(0)
            gradeSelect.setSelect(0)
            experienceSelect.setSelect(0)
        }
        contentView.findViewById(R.id.bt_filtrate).setOnClickListener{
            mListener.onRequireSelect(courseSelect.selectIndex, gradeSelect.selectIndex, experienceSelect.selectIndex)
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

    fun clearSelect() {
        courseSelect.setSelect(0)
        gradeSelect.setSelect(0)
        experienceSelect.setSelect(0)
    }

    override fun dismiss() {
        super.dismiss()
        mListener.onDismiss(3)
    }
}