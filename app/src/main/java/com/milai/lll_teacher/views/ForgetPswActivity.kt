package com.milai.lll_teacher.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.milai.lll_teacher.R
import com.milai.lll_teacher.custom.util.StringUtils
import com.milai.lll_teacher.custom.view.CustomEditText
import java.lang.ref.WeakReference

class ForgetPswActivity : AppCompatActivity() {

    var vcodeGet:String = ""
    var timeRemain: Int = 60
    val handler:MyHandler by lazy { MyHandler(this@ForgetPswActivity) }
    val btVcode by lazy { findViewById(R.id.bt_vcode) as Button}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_psw)

        val tvTitle = findViewById(R.id.tv_title) as TextView
        val etTel = findViewById(R.id.et_tel) as CustomEditText
        val etVcode = findViewById(R.id.et_vcode) as CustomEditText
        val btNext = findViewById(R.id.bt_login) as Button
        findViewById(R.id.bt_back).setOnClickListener({
            onBackPressed()
        })

        tvTitle.text = "找回密码"
        btVcode.setOnClickListener({
            if (StringUtils.isTel(etTel.text)) {
                handler.sendEmptyMessage(1)
                btVcode.text = "60s"
                btVcode.isEnabled= false
            } else {
                Toast.makeText(this@ForgetPswActivity, R.string.correct_tel, Toast.LENGTH_SHORT)
            }
        })
        btNext.setOnClickListener{
            if (etTel.text != "" && etVcode.text == vcodeGet) {
                //TODO 跳转重设密码
                startActivity(Intent(this@ForgetPswActivity, ResetPswActivity::class.java))
            } else {
                Toast.makeText(this@ForgetPswActivity, R.string.error_vcode, Toast.LENGTH_SHORT)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeMessages(1)
        btVcode.isEnabled = true
    }

    class MyHandler internal constructor(ctx: ForgetPswActivity):Handler(){
        private val reference: WeakReference<ForgetPswActivity> = WeakReference(ctx)

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
                }
            }
        }
    }
}


