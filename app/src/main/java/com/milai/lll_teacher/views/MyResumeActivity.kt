package com.milai.lll_teacher.views

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.milai.lll_teacher.MyApplication
import com.milai.lll_teacher.R
import com.tencent.smtt.sdk.WebChromeClient
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient

class MyResumeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resume_edit)
        findViewById(R.id.bt_back).setOnClickListener{ onBackPressed()}
        findViewById(R.id.preview).visibility = View.GONE
        initWebView()
    }

    private fun initWebView() {
        val webView = findViewById(R.id.webview) as WebView
        val settings = webView.settings
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        settings.javaScriptCanOpenWindowsAutomatically = true
        settings.allowFileAccess = true// 设置允许访问文件数据
        webView.setWebViewClient(WebViewClient())
        webView.setWebChromeClient(WebChromeClient())
        webView.loadUrl("http://lll.domobile.net/Home/Index/detail?uid=${MyApplication.userInfo?.uid}")
    }
}
