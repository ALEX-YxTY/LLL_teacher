package com.meishipintu.lll_office.views

import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.meishipintu.lll_office.Cookies
import com.meishipintu.lll_office.OfficeApplication
import com.meishipintu.lll_office.R
import com.meishipintu.lll_office.contract.TeacherDetailContract
import com.meishipintu.lll_office.modles.entities.TeacherInfo
import com.meishipintu.lll_office.presenters.TeachPresenter
import com.umeng.socialize.ShareAction
import com.umeng.socialize.UMShareAPI
import com.umeng.socialize.UMShareListener
import com.umeng.socialize.bean.SHARE_MEDIA
import com.umeng.socialize.media.UMImage
import com.umeng.socialize.media.UMWeb

class TeacherDetailActivity : BasicActivity(),View.OnClickListener,TeacherDetailContract.IView {

    val ivShare: ImageView by lazy { findViewById(R.id.iv_share) as ImageView }
    private val umShareListener: UMShareListener by lazy {object: UMShareListener {
        override fun onResult(p0: SHARE_MEDIA?) {
            Toast.makeText(this@TeacherDetailActivity, " 分享成功", Toast.LENGTH_SHORT).show()
        }

        override fun onCancel(p0: SHARE_MEDIA?) {
            Toast.makeText(this@TeacherDetailActivity,"分享被取消", Toast.LENGTH_SHORT).show()
        }

        override fun onError(p0: SHARE_MEDIA?, p1: Throwable?) {
            Toast.makeText(this@TeacherDetailActivity,"分享发生错误", Toast.LENGTH_SHORT).show()
            if (p1 != null) {
                Log.d("MyResumeActivity", "share error: ${p1.message}")
            }
        }

        override fun onStart(p0: SHARE_MEDIA?) {
        }

    }}

    val teacher:TeacherInfo by lazy { intent.getSerializableExtra("teacher") as TeacherInfo }
    //type=1 底部收藏+邀约，type=2 底部邀请
    val type:Int by lazy{ intent.getIntExtra("type", 1)}
    val jobId:Int by lazy{ intent.getIntExtra("jobId", -1)}

    val btInvite:TextView by lazy{ findViewById(R.id.bt_invite) as TextView}
    val ivButton:ImageView by lazy{ findViewById(R.id.iv_special) as ImageView}
    val tvButton:TextView by lazy{ findViewById(R.id.tv_action) as TextView}
    var isCollected = false     //标记是否已经收藏
    val glide:RequestManager by lazy{ Glide.with(this) }
    val courses = Cookies.getConstant(2)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_detail)
        presenter = TeachPresenter(this)
        (presenter as TeacherDetailContract.IPresenter).doActionStatistic(OfficeApplication.userInfo?.uid!!, teacher.uid)
        val tvTitle = findViewById(R.id.tv_title)as TextView
        tvTitle.text = "教师详情"
        ivShare.visibility = View.VISIBLE
        ivShare.setOnClickListener(this)
        if (type == 1) {
            (presenter as TeacherDetailContract.IPresenter).isCollectedTeacher(OfficeApplication.userInfo?.uid!!, teacher.uid)
            //显示立即邀约
            btInvite.visibility = View.VISIBLE
        } else {
            ivButton.visibility = View.GONE
            tvButton.text = "邀请TA面试"
        }
        btInvite.setOnClickListener(this)
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
            R.id.iv_share ->{
                val umWeb = UMWeb("http://lll.domobile.net/Home/Index/tcinfo?uid=${teacher.uid}" +
                        "&actionId=${OfficeApplication.userInfo?.uid}&type=7&flag=2")
                umWeb.title = "我在拉力郎共享师资发现一位${teacher.name}老师，拥有*年${courses[teacher.course]}教学经验，点击查看"
                umWeb.setThumb(UMImage(this,R.mipmap.office_share))
                ShareAction(this@TeacherDetailActivity).setDisplayList(SHARE_MEDIA.WEIXIN,SHARE_MEDIA.WEIXIN_CIRCLE)
                        .setCallback(umShareListener).withMedia(umWeb).open()
            }
            R.id.bt_invite ->{
                //进入机构职位列表页面
                val intent = Intent(this, JobOnlineListActivity::class.java)
                intent.putExtra("tid",teacher.uid)
                startActivity(intent)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data)
    }

}
