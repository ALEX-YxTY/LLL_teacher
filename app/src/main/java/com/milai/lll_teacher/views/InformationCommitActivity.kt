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
import me.iwf.photopicker.PhotoPicker
import android.support.v4.app.ActivityCompat
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat
import android.widget.Toast
import com.milai.lll_teacher.custom.util.DialogUtils

class InformationCommitActivity : AppCompatActivity() {

    val tel:String by lazy{ intent.getStringExtra("tel")}
    val verify: String by lazy { intent.getStringExtra("verify") }
    val psw: String by lazy { intent.getStringExtra("psw") }
    val webView: WebView by lazy{findViewById(R.id.wv) as WebView }
    //调起相机的回复
    var type: Int = 1 //标识是否5.0以上 1-5.0以下  2-5.0及以上
    var mUploadMessage: ValueCallback<Uri>? = null
    var mUploadMessages: ValueCallback<Array<Uri>>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)
        findViewById(R.id.bt_back).setOnClickListener{ onBackPressed()}
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
            override fun onShowFileChooser(webView: WebView?, filePathCallback: ValueCallback<Array<Uri>>?
                                           , fileChooserParams: FileChooserParams?): Boolean {
                Log.d("test","onCLickPicker")
                type = 2
                mUploadMessages = filePathCallback
                choosePicture()
//                if (mUploadMessage != null) {
//                    mUploadMessage?.onReceiveValue(null)
//                }
//                Log.i("UPFILE", "file chooser params：" + fileChooserParams.toString())
//                mUploadMessage = filePathCallback
//                val i = Intent(Intent.ACTION_GET_CONTENT)
//                i.addCategory(Intent.CATEGORY_OPENABLE)
//                if (fileChooserParams != null && fileChooserParams.acceptTypes != null
//                        && fileChooserParams.acceptTypes.length > 0) {
//                    i.type = fileChooserParams.acceptTypes[0]
//                } else {
//                    i.type = "*/*"
//                }
//                startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE)
//                return true
                return super.onShowFileChooser(webView, filePathCallback, fileChooserParams)
            }

            //android>4.4 使用此方法
            override fun openFileChooser(uploadMsg: ValueCallback<Uri>?, p1: String?, p2: String?) {
                Log.d("test","onCLickPicker below5.0")
                type = 1
                mUploadMessage = uploadMsg
                choosePicture()
            }
        })
        webView.loadUrl("http://lll.domobile.net/Home/Index/addinfo?tel=$tel&verify=$verify&pwd=$psw")
    }

    //选择图片 调用PhotoPicker
    private fun choosePicture() {
        PhotoPicker.builder()
                .setPhotoCount(1)
                .setShowCamera(true)
                .setPreviewEnabled(true)
                .start(this,PhotoPicker.REQUEST_CODE)
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (null == mUploadMessage && type == 1) {
            return
        }
        if (null == mUploadMessages && type == 2) {
            return
        }
        if (resultCode == RESULT_OK && requestCode == PhotoPicker.REQUEST_CODE && type == 1) {
            val photos = intent.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS)
            val result = Uri.parse(photos[0])
            mUploadMessage?.onReceiveValue(result)
            mUploadMessage = null
        } else if (resultCode == RESULT_OK && requestCode == PhotoPicker.REQUEST_CODE && type == 2) {
            val photos = intent.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS)
            Log.d("test", "photos?: ${null==photos} ${photos.toArray()}")
            val result = Uri.parse(photos[0])
            mUploadMessages?.onReceiveValue(arrayOf(result))
            mUploadMessages = null
        } else {
            if (type == 1) {
                mUploadMessage?.onReceiveValue(null)
            } else {
                mUploadMessages?.onReceiveValue(null)
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
        }
    }
}
