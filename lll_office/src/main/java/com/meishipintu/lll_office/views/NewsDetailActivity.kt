package com.meishipintu.lll_office.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.meishipintu.lll_office.OfficeApplication
import com.meishipintu.lll_office.R
import com.meishipintu.lll_office.presenters.StatisticPresenter
import com.umeng.socialize.ShareAction
import com.umeng.socialize.UMShareAPI
import com.umeng.socialize.UMShareListener
import com.umeng.socialize.bean.SHARE_MEDIA
import com.umeng.socialize.media.UMImage
import com.umeng.socialize.media.UMWeb

class NewsDetailActivity : BasicActivity() {

    val ivShare: ImageView by lazy { findViewById(R.id.iv_share) as ImageView }
    private val umShareListener: UMShareListener by lazy {object: UMShareListener {
        override fun onResult(p0: SHARE_MEDIA?) {
            Toast.makeText(this@NewsDetailActivity, " 分享成功", Toast.LENGTH_SHORT).show()
        }

        override fun onCancel(p0: SHARE_MEDIA?) {
            Toast.makeText(this@NewsDetailActivity,"分享被取消", Toast.LENGTH_SHORT).show()
        }

        override fun onError(p0: SHARE_MEDIA?, p1: Throwable?) {
            Toast.makeText(this@NewsDetailActivity,"分享发生错误", Toast.LENGTH_SHORT).show()
            if (p1 != null) {
                Log.d("MyResumeActivity", "share error: ${p1.message}")
            }
        }

        override fun onStart(p0: SHARE_MEDIA?) {
        }

    }}

    val url:String by lazy{ intent.getStringExtra("url")}
    val newsId:String by lazy{ intent.getIntExtra("newsId", 0).toString()}
    val newsName:String by lazy{ intent.getStringExtra("newsName")}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)
        presenter = StatisticPresenter()
        (presenter as StatisticPresenter).doNewsStatistic(OfficeApplication.userInfo?.uid!!,1,newsId)
        val tvTitle = findViewById(R.id.tv_title) as TextView
        ivShare.visibility = View.VISIBLE
        val umWeb = UMWeb(url)
        umWeb.title = newsName
        umWeb.description = "拉力郎师资"
        umWeb.setThumb(UMImage(this,R.mipmap.office_share))
        ivShare.setOnClickListener{
            ShareAction(this@NewsDetailActivity).setDisplayList(SHARE_MEDIA.WEIXIN,SHARE_MEDIA.WEIXIN_CIRCLE)
                    .setCallback(umShareListener).withMedia(umWeb).open()
        }
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data)
    }
}
