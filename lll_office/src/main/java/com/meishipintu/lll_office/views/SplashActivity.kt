package com.meishipintu.lll_office.views

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import com.meishipintu.lll_office.Cookies
import com.meishipintu.lll_office.R
import java.lang.ref.WeakReference

class SplashActivity : AppCompatActivity() {

    val handler: SplashActivity.MyHandler by lazy { SplashActivity.MyHandler(this@SplashActivity) }
    var timeRemian = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        handler.sendEmptyMessage(1)
    }

    class MyHandler internal constructor(ctx: SplashActivity): Handler(){

        private val reference: WeakReference<SplashActivity> = WeakReference(ctx)

        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            var activity = reference.get()
            if (msg.what == 1 && activity != null) {
                if (activity.timeRemian > 0) {
                    activity.timeRemian--
                    this.sendEmptyMessageDelayed(1, 1000)
                } else {
                    when {
                        Cookies.isFirstLogin() -> activity.startActivity(Intent(activity, GuideActivity::class.java))
                        Cookies.getUserInfo() != null -> activity.startActivity(Intent(activity, MainActivity::class.java))
                        else -> activity.startActivity(Intent(activity,LoginAndRegisterActivity::class.java))
                    }
                    activity.finish()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeMessages(1)
    }
}
