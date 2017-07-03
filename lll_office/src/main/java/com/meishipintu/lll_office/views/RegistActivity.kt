package com.meishipintu.lll_office.views

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.TextView
import com.meishipintu.lll_office.R
import com.meishipintu.lll_office.contract.RegisterContract
import com.meishipintu.lll_office.customs.CustomEditText
import com.meishipintu.lll_office.customs.utils.StringUtils
import com.meishipintu.lll_office.presenters.RegisterPresenter
import java.lang.ref.WeakReference

/**
 *  注册页面和找回密码页面。
 *  from=1 注册  from=2 找回密码
 */
class RegistActivity : AppCompatActivity() {

    var vcodeGet = ""
    val btVcode by lazy { findViewById(R.id.bt_vcode) as Button }
    var timeRemain: Int = 60
    val handler:MyHandler by lazy { MyHandler(this) }
    val from:Int by lazy { intent.getIntExtra("from", 1) }

    val presenter:RegisterContract.IPresenter by lazy { RegisterPresenter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        findViewById(R.id.bt_back).setOnClickListener{ onBackPressed()}
        val tvTitle = findViewById(R.id.tv_title) as TextView
        tvTitle.text = if(from==1) "注册" else "找回密码"
        setListener()
    }

    private fun setListener() {
        val etTel = findViewById(R.id.et_tel) as CustomEditText
        val etVcode = findViewById(R.id.et_vcode) as CustomEditText
        val btRegister = findViewById(R.id.bt_login) as Button
        btRegister.text = if(from==1) "注册" else "下一步"
        val textWatcher: TextWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                btRegister.isEnabled = StringUtils.isTel(etTel.text) && !StringUtils.isNullOrEmpty(etVcode.text)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        }
        etTel.setListener(textWatcher)
        etVcode.setListener(textWatcher)

        btVcode.setOnClickListener{
            if (vcodeGet == etVcode.text) {
                //TODO 请求验证码
                //启动读秒
                handler.sendEmptyMessage(1)
                btVcode.isEnabled = false
                btVcode.text = "$timeRemain s"
            }
        }
        btRegister.setOnClickListener{
            if (etVcode.text == vcodeGet) {
                val intent:Intent
                if(from==1) {
                    intent = Intent(this,SetPsActivity::class.java)
                } else {
                    intent = Intent(this, ReSetPswActivity::class.java)
                }
                startActivity(intent)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeMessages(1)
        btVcode.isEnabled = true
    }

    class MyHandler internal constructor(ctx: RegistActivity): Handler(){

        private val reference: WeakReference<RegistActivity> = WeakReference(ctx)

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
