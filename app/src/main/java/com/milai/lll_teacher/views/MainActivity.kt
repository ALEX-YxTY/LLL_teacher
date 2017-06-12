package com.milai.lll_teacher.views

import butterknife.ButterKnife

class MainActivity : android.support.v7.app.AppCompatActivity() {

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.milai.lll_teacher.R.layout.activity_main)
        ButterKnife.bind(this)

    }
}
