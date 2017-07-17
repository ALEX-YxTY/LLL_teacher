package com.meishipintu.lll_office.views

import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import com.meishipintu.lll_office.R
import com.meishipintu.lll_office.modles.entities.TeacherInfo


class TeacherDetailActivity : BasicActivity(),View.OnClickListener {

    val teacher:TeacherInfo by lazy { intent.getSerializableExtra("teacher") as TeacherInfo }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_detail)
        val tvTitle = findViewById(R.id.tv_title)as TextView
        tvTitle.text = "教师详情"
        findViewById(R.id.bt_back).setOnClickListener(this)
        findViewById(R.id.bt_collect).setOnClickListener(this)
        initWebView()
    }

    private fun initWebView() {
        val webView = findViewById(R.id.wv) as WebView
        val settings = webView.settings
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        webView.setWebChromeClient(WebChromeClient())
        webView.setWebViewClient(WebViewClient())
        webView.loadUrl("http://lll.domobile.net/Home/Index/detail?uid=${teacher.uid}")
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.bt_back -> onBackPressed()
            R.id.bt_collect -> {
                //TODO 添加教师收藏
            }
        }
    }
}
