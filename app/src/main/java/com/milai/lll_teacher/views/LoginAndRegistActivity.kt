package com.milai.lll_teacher.views

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.milai.lll_teacher.R
import com.milai.lll_teacher.RxBus
import com.milai.lll_teacher.models.entities.BusMessage

class LoginAndRegistActivity : AppCompatActivity() ,View.OnClickListener{

    var clickTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_and_regist)
        findViewById(R.id.bt_back).setOnClickListener(this)
        findViewById(R.id.bt_login).setOnClickListener(this)
        findViewById(R.id.bt_register).setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.bt_back -> onBackPressed()
            R.id.bt_login -> startActivity(Intent(this@LoginAndRegistActivity, LoginActivity::class.java))
            R.id.bt_register -> {
                val intent = Intent(this, RegisterActivity::class.java)
                intent.putExtra("from", 1)  //from=1 注册  from=2 找回密码
                startActivity(intent)
            }
        }
    }

    override fun onBackPressed() {
        if (System.currentTimeMillis() - clickTime > 1000) {
            Toast.makeText(this,"再次点击退出程序", Toast.LENGTH_SHORT).show()
            clickTime = System.currentTimeMillis()
        } else {
            this.finish()
            RxBus.send(BusMessage(Constant.EXIT))
        }
    }
}
