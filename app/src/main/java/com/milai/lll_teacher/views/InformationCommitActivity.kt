package com.milai.lll_teacher.views

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.milai.lll_teacher.R
import com.tencent.smtt.sdk.ValueCallback
import com.tencent.smtt.sdk.WebChromeClient
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient
import android.support.v4.app.ActivityCompat
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat
import android.widget.Toast
import com.milai.lll_teacher.custom.util.DialogUtils
import com.milai.lll_teacher.Constant
import com.milai.lll_teacher.custom.util.PicGetUtil
import com.tencent.smtt.export.external.interfaces.JsResult
import java.io.File

class InformationCommitActivity : AppCompatActivity(),PicGetUtil.SuccessListener {

    val tel:String by lazy{ intent.getStringExtra("tel")}
    val verify: String by lazy { intent.getStringExtra("verify") }
    val psw: String by lazy { intent.getStringExtra("psw") }
    val webView: WebView by lazy{findViewById(R.id.wv) as WebView }

    //调起相机相册的回复，接收返回file：// URI
    var mUploadMessage: ValueCallback<Uri>? = null
    var mUploadMessageAboveLollipop: ValueCallback<Array<Uri>>? = null

    var finalFile: File? = null     //存储最终上传的文件

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)
        findViewById(R.id.bt_back).setOnClickListener{ this.finish()}
        val tvTitle = findViewById(R.id.tv_title) as TextView
        tvTitle.text = "信息填写"
        writeStoragePermissionWapper()
    }

    //修改存储权限包装方法
    private fun writeStoragePermissionWapper() {
        val hasStoragePermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (hasStoragePermission != PackageManager.PERMISSION_GRANTED) {        //未授权
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                //系统申请权限框不再弹出
                DialogUtils.showCustomDialog(this, "本应用需要获取读写外部存储的权限", { dialog, _ ->
                    ActivityCompat.requestPermissions(this@InformationCommitActivity, arrayOf(android
                            .Manifest.permission.WRITE_EXTERNAL_STORAGE), Constant.REQUEST_STORAGE_PERMISSION)
                    dialog.dismiss()
                }, { dialog, _ -> dialog.dismiss() })
                return
            }
            //系统框弹出时直接申请
            ActivityCompat.requestPermissions(this@InformationCommitActivity, arrayOf(android
                    .Manifest.permission.WRITE_EXTERNAL_STORAGE), Constant.REQUEST_STORAGE_PERMISSION)
            return
        } else {
            initWebView()
        }
    }

    private fun initWebView() {
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
                PicGetUtil.choosePicture(this@InformationCommitActivity,this@InformationCommitActivity)
                //返回true 反则会出现多次调用showFileChooser的错误
                return true
            }

            //android>4.4 使用此方法
            override fun openFileChooser(uploadMsg: ValueCallback<Uri>?, p1: String?, p2: String?) {
                Log.d("test","onCLickPicker below5.0")
                mUploadMessage = uploadMsg
                PicGetUtil.choosePicture(this@InformationCommitActivity,this@InformationCommitActivity)
            }

            override fun onJsAlert(webView: WebView?, url: String?, message: String?, result: JsResult?): Boolean {
                if (url =="http://lll.domobile.net/Home/Index/success" && message =="注册成功") {
                    DialogUtils.showCustomDialog(this@InformationCommitActivity, "注册成功",{ dialog, _ ->
                        dialog.dismiss()
                        this@InformationCommitActivity.finish()
                    })
                    return true
                }
                return super.onJsAlert(webView, url, message, result)
            }

            override fun onJsConfirm(p0: WebView?, p1: String?, p2: String?, p3: JsResult?): Boolean {
                Log.d("test", "on confirm")
                return super.onJsConfirm(p0, p1, p2, p3)
            }
        })
        webView.loadUrl("http://lll.domobile.net/Home/Index/addinfo?tel=$tel&verify=$verify&pwd=$psw")
    }

    //from PicGetUtil.SuccessListener
    override fun onSuccess(file: File) {
        finalFile = file
        if (null != mUploadMessage) {
            //4.4-5.0
            mUploadMessage?.onReceiveValue(Uri.fromFile(finalFile))
            mUploadMessage = null
        }else if (null != mUploadMessageAboveLollipop) {
            val uris = arrayOf(Uri.fromFile(finalFile))
            mUploadMessageAboveLollipop?.onReceiveValue(uris)
            mUploadMessageAboveLollipop = null
        }
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
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
        when (requestCode) {
            Constant.REQUEST_STORAGE_PERMISSION ->{
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    //拒绝授权
                    Toast.makeText(this, "没有读写内存卡权限，将有部分功能无法使用，请在系统设置中增加应用的相应授权", Toast.LENGTH_SHORT)
                            .show()
                } else {
                    initWebView()
                }
            }
            else -> PicGetUtil.onPermissiionRequestResult(this,requestCode,permissions,grantResults)
        }
    }
}
