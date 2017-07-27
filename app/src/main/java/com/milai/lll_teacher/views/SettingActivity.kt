package com.milai.lll_teacher.views

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.milai.lll_teacher.Cookies
import com.milai.lll_teacher.MyApplication
import com.milai.lll_teacher.R
import com.milai.lll_teacher.RxBus
import com.milai.lll_teacher.models.entities.BusMessage

class SettingActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        val tvTitle = findViewById(R.id.tv_title) as TextView
        tvTitle.text = "我的设置"
        findViewById(R.id.bt_back).setOnClickListener(this)
        findViewById(R.id.bt_logout).setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.bt_back -> onBackPressed()
            R.id.bt_logout -> {
                //清除数据
                Cookies.clearUserInfo()
                MyApplication.userInfo = null
                RxBus.send(BusMessage(Constant.LOGOUT))
                this.finish()
            }
        }
    }
}
