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

class CustomNumPickeDialog2(context: Context, @StyleRes themeResId: Int, val show: Array<String>, val listener: OnOkClickListener) : AlertDialog(context, themeResId) {


    override fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_num_picker)
        val np = findViewById(R.id.np) as NumberPicker?
        findViewById(R.id.ok)!!.setOnClickListener { listener.onOkClick(np!!.value) }
        np!!.displayedValues = show
        np.minValue = 0
        np.maxValue = show.size - 1
    }

    interface OnOkClickListener {

        fun onOkClick(vlueChoose: Int)
    }
}
