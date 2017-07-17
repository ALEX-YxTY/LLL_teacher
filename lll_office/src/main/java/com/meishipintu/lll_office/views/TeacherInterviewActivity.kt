package com.meishipintu.lll_office.views

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import com.meishipintu.lll_office.R
import com.meishipintu.lll_office.modles.entities.DeliverInfo
import com.meishipintu.lll_office.modles.entities.JobInfo
import com.meishipintu.lll_office.modles.entities.TeacherInfo

/**
 * 教师详情页  未面试/已面试
 */

class TeacherInterviewActivity : BasicActivity(),View.OnClickListener {

    val teacher: TeacherInfo by lazy { intent.getSerializableExtra("teacher") as TeacherInfo }
    val job: JobInfo by lazy { intent.getSerializableExtra("job") as JobInfo }
    val status:Int by lazy{ intent.getIntExtra("status", 1)} //1-未面试 2-已面试 3-已评价

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_interview)
        val tvTitle = findViewById(R.id.tv_title)as TextView
        tvTitle.text = "教师详情"
        findViewById(R.id.bt_back).setOnClickListener(this)
        findViewById(R.id.bt_contact).setOnClickListener(this)
        findViewById(R.id.bt_append).setOnClickListener(this)
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
            R.id.bt_contact -> {
                //进入沟通页
                val intent = Intent(this, ChatDetailActivity::class.java)
                intent.putExtra("job", job)
                intent.putExtra("teacher", teacher.uid)
                startActivity(intent)
            }
            R.id.bt_append -> {
                if (status == 1) {
                    //去面试

                }else if (status == 2) {
                    //去评价

                } else {
                    findViewById(R.id.bt_append).visibility = View.GONE
                }
            }
        }
    }
}
