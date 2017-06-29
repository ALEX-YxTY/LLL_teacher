package com.meishipintu.lll_office.views

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.TextView
import com.meishipintu.lll_office.R
import com.meishipintu.lll_office.customs.CustomEditText
import com.meishipintu.lll_office.customs.utils.StringUtils

class SetPsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_ps)
        findViewById(R.id.bt_back).setOnClickListener{ onBackPressed()}
        val tvTitle = findViewById(R.id.tv_title) as TextView
        tvTitle.text = "设置密码"
        setListener()
    }

    private fun setListener() {
        val etAccount = findViewById(R.id.et_account) as CustomEditText
        val etPsw = findViewById(R.id.et_psw) as CustomEditText
        val etPswRe = findViewById(R.id.et_psw_re) as CustomEditText
        val btRegister = findViewById(R.id.bt_login) as Button
        val textWatcher: TextWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                btRegister.isEnabled = !StringUtils.isNullOrEmpty(etAccount.text) && !StringUtils.isNullOrEmpty(etPsw.text)
                        && (etPsw.text == etPswRe.text)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        }
        etAccount.setListener(textWatcher)
        etPsw.setListener(textWatcher)
        etPswRe.setListener(textWatcher)
        btRegister.setOnClickListener{
            //TODO 注册 成功后跳转主页
            startActivity(Intent(this,MainActivity::class.java))
        }
    }
}
