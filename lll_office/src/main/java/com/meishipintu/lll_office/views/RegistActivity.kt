package com.meishipintu.lll_office.views

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.TextView
import com.meishipintu.lll_office.Constant
import com.meishipintu.lll_office.R
import com.meishipintu.lll_office.RxBus
import com.meishipintu.lll_office.contract.RegisterContract
import com.meishipintu.lll_office.customs.CustomEditText
import com.meishipintu.lll_office.customs.utils.StringUtils
import com.meishipintu.lll_office.modles.entities.BusMessage
import com.meishipintu.lll_office.presenters.RegisterPresenter
import java.lang.ref.WeakReference

/**
 *  注册页面和找回密码页面。
 *  from=1 注册  from=2 找回密码
 */
class RegistActivity : BasicActivity(),RegisterContract.IView {

    val REGISTER = 10 //注册requestCode

    var vcodeGet = ""
    val btVcode by lazy { findViewById(R.id.bt_vcode) as Button }
    var timeRemain: Int = 60
    val handler:MyHandler by lazy { MyHandler(this) }
    val from:Int by lazy { intent.getIntExtra("from", 1) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        findViewById(R.id.bt_back).setOnClickListener{ onBackPressed()}
        presenter = RegisterPresenter(this)
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
            if (StringUtils.isTel(etTel.text)) {
                (presenter as RegisterPresenter).getVCode(etTel.text.toString())
                //启动读秒
                handler.sendEmptyMessage(1)
                btVcode.isEnabled = false
                btVcode.text = "$timeRemain s"
            } else {
                toast("请输入正确的手机号码")
            }
        }
        btRegister.setOnClickListener{
            if (etVcode.text == vcodeGet) {
                val intent:Intent
                if(from==1) {
                    intent = Intent(this,SetPsActivity::class.java)
                    intent.putExtra("mobile",etTel.text.toString())
                    intent.putExtra("vcode", vcodeGet)
                    startActivityForResult(intent,REGISTER)
                } else {
                    intent = Intent(this, ReSetPswActivity::class.java)
                    intent.putExtra("tel",etTel.text.toString())
                    intent.putExtra("verify", vcodeGet)
                    startActivity(intent)
                    this.finish()
                }
            }
        }
    }

    //from RegistContract.IView
    override fun onVCodeGet(vcode: String) {
        vcodeGet = vcode
    }

    //from RegistContract.IView
    override fun onError(e: String) {
        toast(e)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeMessages(1)
        btVcode.isEnabled = true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REGISTER && resultCode == Activity.RESULT_OK) {
            //重新进入首页
            startActivity(Intent(this, MainActivity::class.java))
            //退出注册页和登录注册页
            RxBus.send(BusMessage(Constant.REGIST_SUCCESS))
            this.finish()
        }
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
