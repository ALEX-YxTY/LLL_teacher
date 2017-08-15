package com.meishipintu.lll_office.customs

import android.app.ProgressDialog
import android.content.Context
import android.content.res.Resources
import android.view.View
import android.view.WindowManager
import com.meishipintu.lll_office.R



/**
 * Created by Administrator on 2017/8/15.
 *
 * 主要功能：
 */
class CustomProgressDialog:ProgressDialog {

    constructor(context: Context) : super(context) {
        initUI()
    }

    constructor(context: Context, themeInt: Int) : super(context, themeInt){
        initUI()
    }

    private fun initUI() {
        this.isIndeterminate = true
        this.setCancelable(false)
    }

    fun dialogShow() {
        if (!isShowing) {
            this.show()
            val view = View.inflate(context, R.layout.custom_loading_dialog, null)
            setContentView(view)
            //设置dialog宽高
            if (this.window != null) {
                val params = this.window .attributes
                params.width = (180*context.resources.displayMetrics.density+0.5f).toInt()
                params.height = WindowManager.LayoutParams.WRAP_CONTENT
                this.window.attributes = params
            }
        }
    }

    fun dialogDismiss() {
        if (isShowing) {
            this.dismiss()
        }
    }
}