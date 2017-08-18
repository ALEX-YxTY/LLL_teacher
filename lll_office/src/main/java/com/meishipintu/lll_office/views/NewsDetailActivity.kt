package com.meishipintu.lll_office.views

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import com.meishipintu.lll_office.OfficeApplication
import com.meishipintu.lll_office.R
import com.meishipintu.lll_office.presenters.StatisticPresenter

class NewsDetailActivity : BasicActivity() {

    val url:String by lazy{ intent.getStringExtra("url")}
    val newsId:String by lazy{ intent.getIntExtra("newsId", 0).toString()}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)
        presenter = StatisticPresenter()
        (presenter as StatisticPresenter).doNewsStatistic(OfficeApplication.userInfo?.uid!!,1,newsId)
        val tvTitle = findViewById(R.id.tv_title) as TextView
        tvTitle.text = "新闻详情"
        initWv()
    }

    private fun initWv() {
        val webView = findViewById(R.id.wv) as WebView
        val settings = webView.settings
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        settings.javaScriptCanOpenWindowsAutomatically = true
        settings.allowFileAccess = true// 设置允许访问文件数据
        webView.setWebViewClient(WebViewClient())
        webView.setWebChromeClient(WebChromeClient())
        webView.loadUrl(url)
    }
}
