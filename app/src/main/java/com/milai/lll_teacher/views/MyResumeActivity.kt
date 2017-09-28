package com.milai.lll_teacher.views

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.milai.lll_teacher.MyApplication
import com.milai.lll_teacher.R
import com.tencent.smtt.sdk.WebChromeClient
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient
import com.umeng.socialize.ShareAction
import com.umeng.socialize.UMShareAPI
import com.umeng.socialize.UMShareListener
import com.umeng.socialize.bean.SHARE_MEDIA
import com.umeng.socialize.media.UMImage
import com.umeng.socialize.media.UMWeb

class MyResumeActivity : AppCompatActivity() {

    val ivShare:ImageView by lazy { findViewById(R.id.iv_share) as ImageView }
    private val umShareListener:UMShareListener by lazy {object:UMShareListener{
        override fun onResult(p0: SHARE_MEDIA?) {
            Toast.makeText(this@MyResumeActivity, " 分享成功", Toast.LENGTH_SHORT).show()
        }

        override fun onCancel(p0: SHARE_MEDIA?) {
            Toast.makeText(this@MyResumeActivity,"分享被取消",Toast.LENGTH_SHORT).show()
        }

        override fun onError(p0: SHARE_MEDIA?, p1: Throwable?) {
            Toast.makeText(this@MyResumeActivity,"分享发生错误",Toast.LENGTH_SHORT).show()
            if (p1 != null) {
                Log.d("MyResumeActivity", "share error: ${p1.message}")
            }
        }

        override fun onStart(p0: SHARE_MEDIA?) {
        }

    }}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resume_edit)
        val tvTitle = findViewById(R.id.tv_title) as TextView
        tvTitle.text = "我的简历"
        findViewById(R.id.bt_back).setOnClickListener{ onBackPressed()}
        ivShare.visibility = View.VISIBLE
        val umWeb = UMWeb("http://lll.domobile.net/Home/Index/tcinfo?uid=${MyApplication.userInfo?.uid}" +
                "&actionId=${MyApplication.userInfo?.uid}&type=4&flag=1")
        umWeb.title = "欢迎查看我的简历"
        umWeb.setThumb(UMImage(this,R.mipmap.teacher_share))
        ivShare.setOnClickListener{
            ShareAction(this@MyResumeActivity).setDisplayList(SHARE_MEDIA.WEIXIN,SHARE_MEDIA.WEIXIN_CIRCLE)
                    .setCallback(umShareListener).withMedia(umWeb).open()
        }
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data)
    }
}
