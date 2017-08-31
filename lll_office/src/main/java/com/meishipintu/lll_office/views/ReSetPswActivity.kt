package com.meishipintu.lll_office.views

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.TextView
import com.meishipintu.lll_office.Constant
import com.meishipintu.lll_office.R
import com.meishipintu.lll_office.RxBus
import com.meishipintu.lll_office.contract.ReSetPswContract
import com.meishipintu.lll_office.customs.CustomEditText
import com.meishipintu.lll_office.customs.utils.Encoder
import com.meishipintu.lll_office.customs.utils.StringUtils
import com.meishipintu.lll_office.presenters.AuthorPresenter

class ReSetPswActivity : BasicActivity(),ReSetPswContract.IView {

    val vcode:String by lazy{intent.getStringExtra("verify")}
    val tel:String by lazy{intent.getStringExtra("tel")}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_re_set_psw)
        presenter = AuthorPresenter(this)
        findViewById(R.id.bt_back).setOnClickListener{ onBackPressed()}
        val tvTitle = findViewById(R.id.tv_title) as TextView
        tvTitle.text = "设置新密码"
        setListener()
    }

    private fun setListener() {
        val etPsw = findViewById(R.id.et_psw) as CustomEditText
        val etPswRe = findViewById(R.id.et_psw_re) as CustomEditText
        val btRegister = findViewById(R.id.bt_login) as Button
        val textWatcher: TextWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                btRegister.isEnabled = !StringUtils.isNullOrEmpty(etPsw.text) && etPsw.text.length > 5
                        && (etPsw.text == etPswRe.text)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        }
        etPsw.setListener(textWatcher)
        etPswRe.setListener(textWatcher)
        btRegister.setOnClickListener{
            if (etPsw.text == etPswRe.text) {
                // 重设密码
                (presenter as ReSetPswContract.IPresenter).resetPsw(tel, vcode, Encoder.md5(etPsw.text))
            } else {
                toast("两次输入密码不一致")
            }
        }
    }

    override fun onError(e: String) {
        toast(e)
    }

    //from ResetPswContract.IView
    override fun onReSetSuccsee() {
        toast("修改密码成功")
        this.finish()
    }
}
