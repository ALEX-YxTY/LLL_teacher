package com.meishipintu.lll_office.views

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.meishipintu.lll_office.R

class DownloadCodeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_download_code)
        val tvTitle = findViewById(R.id.tv_title) as TextView
        tvTitle.text = "下载二维码"
        findViewById(R.id.bt_back).setOnClickListener{
            onBackPressed()
        }
    }
}
