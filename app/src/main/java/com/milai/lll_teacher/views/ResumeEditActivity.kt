package com.milai.lll_teacher.views

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.milai.lll_teacher.MyApplication
import com.milai.lll_teacher.R
import com.milai.lll_teacher.custom.util.PicGetUtil
import com.tencent.smtt.sdk.ValueCallback
import com.tencent.smtt.sdk.WebChromeClient
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient
import java.io.File

class ResumeEditActivity : AppCompatActivity(),PicGetUtil.SuccessListener {

    //调起相机相册的回复，接收返回file：// URI
    var mUploadMessage: ValueCallback<Uri>? = null
    var mUploadMessageAboveLollipop: ValueCallback<Array<Uri>>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resume_edit)
        findViewById(R.id.bt_back).setOnClickListener{ onBackPressed()}
        findViewById(R.id.preview).setOnClickListener{
            startActivity(Intent(this,MyResumeActivity::class.java))
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
        webView.setWebChromeClient(object: WebChromeClient(){

            //above Lollipop
            override fun onShowFileChooser(webView: WebView?, filePathCallback: ValueCallback<Array<Uri>>?
                                           , fileChooserParams: FileChooserParams?): Boolean {
                Log.d("test","onCLickPicker")
                mUploadMessageAboveLollipop = filePathCallback
                PicGetUtil.choosePicture(this@ResumeEditActivity,this@ResumeEditActivity)
                //返回true 反则会出现多次调用showFileChooser的错误
                return true
            }

            //android>4.4 使用此方法
            override fun openFileChooser(uploadMsg: ValueCallback<Uri>?, p1: String?, p2: String?) {
                Log.d("test","onCLickPicker below5.0")
                mUploadMessage = uploadMsg
                PicGetUtil.choosePicture(this@ResumeEditActivity,this@ResumeEditActivity)

            }
        })
        webView.loadUrl("http://lll.domobile.net/Home/Index/resume?uid=${MyApplication.userInfo?.uid}")
    }

    //from PicGetUtil.SuccessListener
    override fun onSuccess(file: File) {
        if (null != mUploadMessage) {
            //4.4-5.0
            mUploadMessage?.onReceiveValue(Uri.fromFile(file))
            mUploadMessage = null
        }else if (null != mUploadMessageAboveLollipop) {
            val uris = arrayOf(Uri.fromFile(file))
            mUploadMessageAboveLollipop?.onReceiveValue(uris)
            mUploadMessageAboveLollipop = null
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK) {
            PicGetUtil.onActivityResult(this, requestCode, resultCode, data)
        } else {
            if (null != mUploadMessage) {
                //4.4-5.0
                mUploadMessage?.onReceiveValue(null)
                mUploadMessage = null
            }else if (null != mUploadMessageAboveLollipop) {
                mUploadMessageAboveLollipop?.onReceiveValue(null)
                mUploadMessageAboveLollipop = null
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        PicGetUtil.onPermissiionRequestResult(this, requestCode, permissions, grantResults)
    }
}
