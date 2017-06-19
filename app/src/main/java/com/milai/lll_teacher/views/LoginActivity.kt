package com.milai.lll_teacher.views

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.milai.lll_teacher.R
import com.milai.lll_teacher.custom.view.CustomEditText

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val tvTitle = findViewById(R.id.tv_title) as TextView
        tvTitle.text = "登录"
        val etTel = findViewById(R.id.et_tel) as CustomEditText
        val etPsw = findViewById(R.id.et_psw) as CustomEditText
        val btLogin = findViewById(R.id.bt_login) as Button
        etTel.setListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                btLogin.isEnabled = (s != null && s.toString().trim().isNotEmpty() && etPsw.text != null
                        && etPsw.text.toString().trim().isNotEmpty())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
        etPsw.setListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                btLogin.isEnabled = (s != null && s.toString().trim().isNotEmpty() && etTel.text != null
                        && etTel.text.toString().trim().isNotEmpty())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
        btLogin.setOnClickListener({
            //TODO 登录
            //成功，跳转主页面
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
        })
    }
}
