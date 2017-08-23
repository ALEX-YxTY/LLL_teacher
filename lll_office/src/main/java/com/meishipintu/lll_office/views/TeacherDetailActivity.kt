package com.meishipintu.lll_office.views

import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.meishipintu.lll_office.OfficeApplication
import com.meishipintu.lll_office.R
import com.meishipintu.lll_office.contract.TeacherDetailContract
import com.meishipintu.lll_office.modles.entities.TeacherInfo
import com.meishipintu.lll_office.presenters.TeachPresenter

class TeacherDetailActivity : BasicActivity(),View.OnClickListener,TeacherDetailContract.IView {

    val teacher:TeacherInfo by lazy { intent.getSerializableExtra("teacher") as TeacherInfo }
    //type=1 底部收藏，type=2 底部邀请
    val type:Int by lazy{ intent.getIntExtra("type", 1)}
    val jobId:Int by lazy{ intent.getIntExtra("jobId", -1)}

    val ivButton:ImageView by lazy{ findViewById(R.id.iv_special) as ImageView}
    val tvButton:TextView by lazy{ findViewById(R.id.tv_action) as TextView}
    var isCollected = false     //标记是否已经收藏
    val glide:RequestManager by lazy{ Glide.with(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_detail)
        presenter = TeachPresenter(this)
        (presenter as TeacherDetailContract.IPresenter).doActionStatistic(OfficeApplication.userInfo?.uid!!, teacher.uid)
        val tvTitle = findViewById(R.id.tv_title)as TextView
        tvTitle.text = "教师详情"
        if (type == 1) {
            (presenter as TeacherDetailContract.IPresenter).isCollectedTeacher(OfficeApplication.userInfo?.uid!!, teacher.uid)
        } else {
            ivButton.visibility = View.GONE
            tvButton.text = "邀请TA面试"
        }
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
        webView.loadUrl("http://lll.domobile.net/Home/Index/detail?uid=${teacher.uid}&flag=1")
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.bt_back -> onBackPressed()
            R.id.bt_collect -> {
                if (type == 1) {
                    if (isCollected) {
                        //取消收藏
                        (presenter as TeacherDetailContract.IPresenter).uncollectTeacher(OfficeApplication.userInfo?.uid!!
                                , teacher.uid)
                    } else {
                        //添加收藏
                        (presenter as TeacherDetailContract.IPresenter).collectTeacher(OfficeApplication.userInfo?.uid!!
                                , teacher.uid)
                    }
                } else {
                    if (jobId > 0) {
                        //邀请面试
                        (presenter as TeacherDetailContract.IPresenter).inviteInterview(jobId, teacher.uid, OfficeApplication.userInfo?.uid!!)
                    }
                }
            }
        }
    }

    //from TeacherDetailContract.IView
    override fun onError(e: String) {
        toast(e)
    }

    //from TeacherDetailContract.IView
    override fun onIsCollected(isCollected: Boolean) {
        this.isCollected = isCollected
        if (isCollected) {
            glide.load(R.drawable.icon_like_white).error(R.drawable.icon_like_pre).into(ivButton)
            tvButton.text = "已收藏"
        } else {
            glide.load(R.drawable.icon_like_pre).error(R.drawable.icon_like_pre).into(ivButton)
            tvButton.text = "收藏"
        }
    }

    //from TeacherDetailContract.IView
    override fun collectSuccess(isCollect: Boolean) {
        this.isCollected = isCollect
        if (isCollect) {
            glide.load(R.drawable.icon_like_white).error(R.drawable.icon_like_pre).into(ivButton)
            tvButton.text = "已收藏"
        } else {
            glide.load(R.drawable.icon_like_pre).error(R.drawable.icon_like_pre).into(ivButton)
            tvButton.text = "收藏"
        }
    }

    //from TeacherDetailContract.IView
    override fun onInviteSuccess() {
        toast("邀请面试成功，请至我的面试查看")
    }

}
