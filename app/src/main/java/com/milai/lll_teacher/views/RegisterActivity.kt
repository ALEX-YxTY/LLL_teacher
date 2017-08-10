package com.milai.lll_teacher.views

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.milai.lll_teacher.R
import com.milai.lll_teacher.contracts.ForgetPswContract
import com.milai.lll_teacher.custom.util.Encoder
import com.milai.lll_teacher.custom.util.StringUtils
import com.milai.lll_teacher.custom.view.CustomEditText
import com.milai.lll_teacher.presenters.AuthorPresenter
import java.lang.ref.WeakReference

class RegisterActivity : BasicActivity(),ForgetPswContract.IView {

    var vcodeGet = ""
    val btVcode by lazy { findViewById(R.id.bt_vcode) as Button }
    var timeRemain: Int = 60
    val handler:MyHandler by lazy { MyHandler(this@RegisterActivity) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        presenter = AuthorPresenter(this)
        findViewById(R.id.bt_back).setOnClickListener{ onBackPressed()}
        val tvTitle = findViewById(R.id.tv_title) as TextView
        tvTitle.text = "注册"
        setListener()
    }

    private fun setListener() {
        val etTel = findViewById(R.id.et_tel) as CustomEditText
        val etVcode = findViewById(R.id.et_vcode) as CustomEditText
        val etPsw = findViewById(R.id.et_psw) as CustomEditText
        val btRegister = findViewById(R.id.bt_login) as Button
        val textWatcher:TextWatcher= object :TextWatcher{
            override fun afterTextChanged(s: Editable) {
                btRegister.isEnabled = StringUtils.isTel(etTel.text) && !etVcode.text.isNullOrEmpty()
                        && !etPsw.text.isNullOrEmpty()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        }
        etTel.setListener(textWatcher)
        etPsw.setListener(textWatcher)
        etVcode.setListener(textWatcher)

        btVcode.setOnClickListener{
            (presenter as ForgetPswContract.IPresenter).getVCode(etTel.text)
            //启动读秒
            btVcode.isEnabled = false
            btVcode.text = "$timeRemain s"
            handler.sendEmptyMessage(1)
        }
        btRegister.setOnClickListener{
            if (etVcode.text == vcodeGet) {
                val intent = Intent(this@RegisterActivity, InformationCommitActivity::class.java)
                intent.putExtra("tel", etTel.text)
                intent.putExtra("verify", etVcode.text)
                intent.putExtra("psw",Encoder.md5(etPsw.text))
                startActivity(intent)
                this.finish()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeMessages(1)
        btVcode.isEnabled = true
    }

    //from ForgetPswContract.IView
    override fun showError(err: String) {
        toast(err)
    }

    //from ForgetPswContract.IView
    override fun onVCodeGet(vcode: String) {
        vcodeGet = vcode
//        (findViewById(R.id.et_vcode) as CustomEditText).text = vcode
    }


    class MyHandler internal constructor(ctx: RegisterActivity): Handler(){

        private val reference: WeakReference<RegisterActivity> = WeakReference(ctx)

        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            var activity = reference.get()
            if (msg.what == 1 && activity != null) {
                if (activity.timeRemain > 0) {
                    sendEmptyMessageDelayed(1, 1000)
                    activity.btVcode.text = (activity.timeRemain--).toString()
                } else {
                    activity.btVcode.text = "发送验证码"
                    activity.btVcode.isEnabled = true
                    activity.timeRemain = 60
                }
            }
        }
    }
}
