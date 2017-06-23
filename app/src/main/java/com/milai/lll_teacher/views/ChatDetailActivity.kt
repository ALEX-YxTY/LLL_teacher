package com.milai.lll_teacher.views

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.widget.EditText
import android.widget.TextView
import com.milai.lll_teacher.R

class ChatDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_detail)
        initUI()
    }

    private fun initUI() {
        findViewById(R.id.bt_back).setOnClickListener{
            onBackPressed()
        }
        val tvTitle = findViewById(R.id.tv_title) as TextView
        val rv = findViewById(R.id.rv) as RecyclerView
        val et = findViewById(R.id.et_message) as EditText
    }
}
