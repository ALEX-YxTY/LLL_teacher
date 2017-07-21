package com.milai.lll_teacher.views

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.TextView
import com.milai.lll_teacher.R
import com.milai.lll_teacher.contracts.ResetPswContract
import com.milai.lll_teacher.custom.view.CustomEditText
import com.milai.lll_teacher.presenters.AuthorPresenter

class ResetPswActivity : BasicActivity(),ResetPswContract.IView {

    val vcode:String by lazy{intent.getStringExtra("verify")}
    val tel:String by lazy{intent.getStringExtra("tel")}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_psw)
        presenter = AuthorPresenter(this)
        val tvTitle = findViewById(R.id.tv_title) as TextView
        tvTitle.text = "设置新密码"
        findViewById(R.id.bt_back).setOnClickListener{ onBackPressed() }
        val etPsw = findViewById(R.id.et_tel) as CustomEditText
        val etPsw2 = findViewById(R.id.et_psw) as CustomEditText
        val btReset = findViewById(R.id.bt_login) as Button
        etPsw.setListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                btReset.isEnabled = (!s.toString().isNullOrEmpty()
                        && !etPsw2.text.isNullOrEmpty()
                        && s.toString().length >= 6 )
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
        etPsw2.setListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                btReset.isEnabled = (!s.toString().isNullOrEmpty()
                        && !etPsw.text.isNullOrEmpty()
                        && s.toString().length >= 6 )
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
        btReset.setOnClickListener{
            if (etPsw.text == etPsw2.text) {
                //重设密码
                (presenter as ResetPswContract.IPresenter).resetPsw(tel, vcode, etPsw.text)
            } else {
                toast("两次输入密码不一致")
            }
        }
    }

    //from ResetPswContract.IView
    override fun showError(err: String) {
        toast(err)
    }

    //from ResetPswContract.IView
    override fun onReSetSuccsee() {
        toast("修改密码成功")
        this.finish()
    }
}
