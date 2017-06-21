package com.milai.lll_teacher.custom.view

import android.app.ActionBar
import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow

import com.milai.lll_teacher.R

/**
 * Created by Administrator on 2017/6/21.
 *
 *
 * 主要功能：
 */

class TjPop2(private val context: Context, private val listener: TjClickListener) : PopupWindow(context) {

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.item_tj, null)
        contentView = view
        width = ViewGroup.LayoutParams.MATCH_PARENT
        height = ViewGroup.LayoutParams.WRAP_CONTENT
    }
}
