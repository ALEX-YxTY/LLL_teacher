package com.milai.lll_teacher.views

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.milai.lll_teacher.R

class DownloadCodeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_download_code)
        val tvTitle = findViewById(R.id.tv_title) as TextView
        tvTitle.text = "下载二维码"
        val tvCode = findViewById(R.id.tv_code) as TextView
        tvCode.text = "扫描二维码，下载拉力郎教师端"
        findViewById(R.id.bt_back).setOnClickListener{
            onBackPressed()
        }
    }
}
