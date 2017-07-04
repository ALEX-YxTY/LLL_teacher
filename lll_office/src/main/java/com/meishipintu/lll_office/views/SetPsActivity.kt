package com.meishipintu.lll_office.views

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.TextView
import com.meishipintu.lll_office.R
import com.meishipintu.lll_office.contract.SetPswContract
import com.meishipintu.lll_office.customs.CustomEditText
import com.meishipintu.lll_office.customs.utils.Encoder
import com.meishipintu.lll_office.customs.utils.StringUtils
import com.meishipintu.lll_office.modles.entities.UserInfo
import com.meishipintu.lll_office.presenters.SetPswPresenter

class SetPsActivity : BasicActivity(),SetPswContract.IView{

    val presenter:SetPswPresenter by lazy { SetPswPresenter(this) }

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
                        && etPsw.text.length > 5 && (etPsw.text == etPswRe.text)
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
            presenter.regist(intent.getStringExtra("mobile"),etAccount.text.toString()
                    ,Encoder.md5(etPsw.text.toString()),intent.getStringExtra("vcode"))
        }
    }

    //from SetPswContract.IView
    override fun onError(e: String) {
        toast(e)
    }

    //from SetPswContract.IView
    override fun onRegisterSuccsee(userInfo: UserInfo) {
        startActivity(Intent(this,MainActivity::class.java))
    }
}
