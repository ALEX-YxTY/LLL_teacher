package com.meishipintu.lll_office.customs

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.PopupWindow
import android.widget.TextView
import com.meishipintu.lll_office.R

/**
 * Created by Administrator on 2017/6/22.
 *
 * 主要功能：
 */
class RequirePopWithSearch(val ctx: Context, val mListener: MenuClickListener, val courses: List<String>
                 , val grades: List<String>, val experiences: List<String>, val searchListener:SearchListener) : PopupWindow(ctx) {

    val courseSelect:CustomLabelSelectView by lazy{contentView.findViewById(R.id.selectview_course) as CustomLabelSelectView}
    val gradeSelect:CustomLabelSelectView by lazy{contentView.findViewById(R.id.selectview_grade) as CustomLabelSelectView}
    val experienceSelect :CustomLabelSelectView by lazy{contentView.findViewById(R.id.selectview_experience) as CustomLabelSelectView}

    init {
        val view = LayoutInflater.from(ctx).inflate(R.layout.pop_require_with_search, null)
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

        courseSelect.setData(courses)
        gradeSelect.setData(grades)
        experienceSelect.setData(experiences)
        val etSearch = contentView.findViewById(R.id.et_search) as EditText
        etSearch.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchListener.onSearch(etSearch.text.toString())
                //消费该事件
                return@OnEditorActionListener true
            }
            false
        })
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

    fun getSelect():String {
        return "course: ${courseSelect.selectIndex},grade:${gradeSelect.selectIndex},experience:${experienceSelect.selectIndex}"
    }

    override fun dismiss() {
        super.dismiss()
        mListener.onDismiss(3)
    }
}