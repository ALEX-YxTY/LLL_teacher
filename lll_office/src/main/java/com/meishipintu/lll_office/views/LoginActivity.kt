package com.meishipintu.lll_office.views

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.TextView
import com.meishipintu.lll_office.Constant
import com.meishipintu.lll_office.Cookies
import com.meishipintu.lll_office.R
import com.meishipintu.lll_office.RxBus
import com.meishipintu.lll_office.contract.AuthorContract
import com.meishipintu.lll_office.customs.CustomEditText
import com.meishipintu.lll_office.modles.entities.BusMessage
import com.meishipintu.lll_office.presenters.AuthorPresenter

class LoginActivity : BasicActivity(),AuthorContract.IView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        presenter = AuthorPresenter(this)
        val tvTitle = findViewById(R.id.tv_title) as TextView
        tvTitle.text = "登录"
        val etTel = findViewById(R.id.et_tel) as CustomEditText
        val etPsw = findViewById(R.id.et_psw) as CustomEditText
        val btLogin = findViewById(R.id.bt_login) as Button
        val tvForgetPsw = findViewById(R.id.forget_psw) as TextView
        findViewById(R.id.bt_back).setOnClickListener({
            onBackPressed()
        })
        etTel.setListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                btLogin.isEnabled = (s != null && s.toString().trim().isNotEmpty() && etPsw.text != null
                        && etPsw.text.toString().trim().isNotEmpty())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }


        })
        etPsw.setListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                btLogin.isEnabled = (s != null && s.toString().trim().isNotEmpty() && etTel.text != null
                        && etTel.text.toString().trim().isNotEmpty())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
        tvForgetPsw.setOnClickListener({
            //跳转忘记密码页面
            val intent = Intent(this@LoginActivity, RegistActivity::class.java)
            intent.putExtra("from", 2) //from=1 注册  from=2 找回密码
            startActivity(intent)
        })
        btLogin.setOnClickListener({
            //登录
            (presenter as AuthorPresenter).login(etTel.text.toString(), etPsw.text.toString())
        })
    }

    //from AuthorContract.IView
    override fun onError(e: String) {
        toast(e)
    }

    //from AuthorContract.IView
    override fun onSuccess() {
        //重新进入首页
        startActivity(Intent(this, MainActivity::class.java))
        //退出登录页和登录注册页
        RxBus.send(BusMessage(Constant.LOGIN_SUCCESS))
        this.finish()
    }
}
