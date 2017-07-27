package com.milai.lll_teacher.views

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.TextView
import com.milai.lll_teacher.Cookies
import com.milai.lll_teacher.MyApplication
import com.milai.lll_teacher.R
import com.milai.lll_teacher.RxBus
import com.milai.lll_teacher.contracts.LoginContract
import com.milai.lll_teacher.custom.util.Encoder
import com.milai.lll_teacher.custom.util.StringUtils
import com.milai.lll_teacher.custom.view.CustomEditText
import com.milai.lll_teacher.models.entities.BusMessage
import com.milai.lll_teacher.models.entities.UserInfo
import com.milai.lll_teacher.presenters.AuthorPresenter

class LoginActivity : BasicActivity(),LoginContract.IView {

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
        etTel.setListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                btLogin.isEnabled = (StringUtils.isTel(s.toString()) && !s.toString().isNullOrEmpty()
                        && !etPsw.text.isNullOrEmpty()
                        && etPsw.text.toString().length >= 6)
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
        tvForgetPsw.setOnClickListener({
            //跳转忘记密码页面
            startActivityForResult(Intent(this, ForgetPswActivity::class.java),Constant.CHANGE_PSW)
        })
        btLogin.setOnClickListener({
            //登录
            (presenter as LoginContract.IPresenter).login(etTel.text, Encoder.md5(etPsw.text))
        })
    }

    //from LoginContract.IView
    override fun showError(err: String) {
        toast(err)
    }

    //from LoginContract.IView
    override fun onLoginSuccess(user: UserInfo) {
        //保存登录
        Cookies.saveUserInfo(user)
        MyApplication.userInfo = user
        //重新进入首页
        startActivity(Intent(this, MainActivity::class.java))
        //退出登录页和登录注册页
        this.finish()
        RxBus.send(BusMessage(Constant.LOGIN_SUCCESS))
    }

}
