package com.meishipintu.lll_office.customs

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.EditText

/**
 * Created by Administrator on 2017/10/25.
 *
 * 主要功能：
 */
class CanScrollEditText(context: Context,attributes: AttributeSet) : EditText(context,attributes) {

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_MOVE)
            this.parent.requestDisallowInterceptTouchEvent(true)
        return super.onTouchEvent(event)
    }
}