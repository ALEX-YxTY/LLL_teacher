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
    //1-教师详情（收藏） 2-教师详情（邀约） 3-教师详情（未面试） 4-教师详情（已面试）
    val type:Int by lazy { intent.getIntExtra("type",1) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_detail)
        val tvTitle = findViewById(R.id.tv_title)as TextView
        tvTitle.text = "教师详情"
        findViewById(R.id.bt_back).setOnClickListener(this)
        initWebView()
    }

    private fun initWebView() {
        val webView = findViewById(R.id.wv) as WebView
        val settings = webView.settings
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        webView.setWebChromeClient(WebChromeClient())
        webView.setWebViewClient(WebViewClient())
        webView.loadUrl("http://www.baidu.com")
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.bt_back -> onBackPressed()

        }
    }
}
