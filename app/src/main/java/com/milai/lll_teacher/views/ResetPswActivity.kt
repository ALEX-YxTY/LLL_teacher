package com.milai.lll_teacher.views

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.milai.lll_teacher.R
import com.milai.lll_teacher.custom.view.CustomEditText

class ResetPswActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_psw)

        val tvTitle = findViewById(R.id.tv_title) as TextView
        tvTitle.text = "设置新密码"
        findViewById(R.id.bt_back).setOnClickListener{ onBackPressed() }
        val etPsw = findViewById(R.id.et_tel) as CustomEditText
        val etPsw2 = findViewById(R.id.et_psw) as CustomEditText
        findViewById(R.id.bt_login).setOnClickListener{
            if (etPsw.text == etPsw2.text) {
                //TODO 重设密码接口
            }
        }
    }
}
