package com.meishipintu.lll_office.views

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.TextView
import com.meishipintu.lll_office.R
import com.meishipintu.lll_office.contract.InterviewContract
import com.meishipintu.lll_office.modles.entities.DeliverInfo
import com.meishipintu.lll_office.modles.entities.JobInfo
import com.meishipintu.lll_office.modles.entities.TeacherInfo
import com.meishipintu.lll_office.presenters.DeliverPresenter

/**
 * 教师详情页  未面试/已面试
 */

class TeacherInterviewActivity : BasicActivity(),View.OnClickListener,InterviewContract.IView {

    val deliverInfo:DeliverInfo by lazy{ intent.getSerializableExtra("deliver") as DeliverInfo}
    val button:Button by lazy {findViewById(R.id.bt_append) as Button}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_interview)
        presenter = DeliverPresenter(this)
        val tvTitle = findViewById(R.id.tv_title)as TextView
        tvTitle.text = "教师详情"
        findViewById(R.id.bt_back).setOnClickListener(this)
        findViewById(R.id.bt_contact).setOnClickListener(this)
        button.text = if (deliverInfo.status == 1) "面试完成" else "去评价"
        button.setOnClickListener(this)
        initWebView()
    }

    private fun initWebView() {
        val webView = findViewById(R.id.wv) as WebView
        val settings = webView.settings
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        webView.setWebChromeClient(WebChromeClient())
        webView.setWebViewClient(WebViewClient())
        webView.loadUrl("http://lll.domobile.net/Home/Index/detail?uid=${deliverInfo.teacher.uid}")
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.bt_back -> onBackPressed()
            R.id.bt_contact -> {
                //进入沟通页
                val intent = Intent(this, ChatDetailActivity::class.java)
                intent.putExtra("job", deliverInfo.postion)
                intent.putExtra("teacher", deliverInfo.teacher.uid)
                startActivity(intent)
            }
            R.id.bt_append -> {
                if (deliverInfo.status == 1) {
                    //去面试,status=2 完成面试
                    (presenter as DeliverPresenter).updateDeliverStatus(deliverInfo.id, 2, 0, "")
                }else if (deliverInfo.status == 2) {
                    //去评价
                    val intent = Intent(this, EvaluateActivity::class.java)
                    intent.putExtra("deliver", deliverInfo)
                    startActivity(intent)
                    finish()
                } else {
                    findViewById(R.id.bt_append).visibility = View.GONE
                }
            }
        }
    }

    //from InterviewContract.IView
    override fun onError(e: String) {
        toast(e)
    }

    //from InterviewContract.IView
    override fun onStatusUpdateSuccess(status: Int) {
        deliverInfo.status = status
        button.text = if (deliverInfo.status == 1) "面试完成" else "去评价"
    }
}
