package com.meishipintu.lll_office.customs.utils

import android.content.Context
import android.os.Bundle
import android.support.annotation.StyleRes
import android.support.v7.app.AlertDialog
import android.widget.NumberPicker
import com.meishipintu.lll_office.R


/**
 * Created by Administrator on 2017/3/21.
 *
 *
 * 主要功能：
 */

class CustomNumPickeDialog2(context: Context, @StyleRes themeResId: Int, private val showFirst: Array<String>
                            , private val showSecond: Array<String>, val listener: OnOk2ClickListener) : AlertDialog(context, themeResId) {


    override fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_two_num_picker)
        val npFirst = findViewById(R.id.np_first) as NumberPicker?
        val npSecond = findViewById(R.id.np_second) as NumberPicker?
        findViewById(R.id.ok)!!.setOnClickListener { listener.onOkClick(npFirst!!.value,npSecond!!.value) }
        npFirst!!.displayedValues = showFirst
        npFirst.minValue = 0
        npFirst.maxValue = showFirst.size - 1
        npFirst.value = 0
        npFirst.setOnValueChangedListener(NumberPicker.OnValueChangeListener { picker, oldVal, newVal ->
            when (newVal) {
                0 -> npSecond?.maxValue = 0
                1 -> npSecond?.maxValue = showSecond.size - 1
                2,3 -> npSecond?.maxValue = 4
            }
        })
        npSecond!!.displayedValues = showSecond
        npSecond.minValue = 0
        npSecond.maxValue = 0
        npSecond.value = 0
    }

    interface OnOk2ClickListener {
        fun onOkClick(vlueChooseFirst: Int, vlueChooseSecond: Int)
    }
}
