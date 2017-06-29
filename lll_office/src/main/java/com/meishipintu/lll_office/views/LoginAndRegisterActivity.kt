package com.meishipintu.lll_office.views

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.meishipintu.lll_office.R

class LoginAndRegisterActivity : AppCompatActivity(),View.OnClickListener{

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
            R.id.bt_login -> startActivity(Intent(this, LoginActivity::class.java))
            R.id.bt_register -> startActivity(Intent(this, RegistActivity::class.java))
        }
    }
}
