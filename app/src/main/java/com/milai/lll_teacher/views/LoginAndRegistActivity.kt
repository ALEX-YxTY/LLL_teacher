package com.milai.lll_teacher.views

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import butterknife.ButterKnife
import com.milai.lll_teacher.R

class LoginAndRegistActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_and_regist)
        ButterKnife.bind(this)
    }
}
