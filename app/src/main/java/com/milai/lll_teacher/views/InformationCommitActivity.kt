package com.milai.lll_teacher.views

import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.milai.lll_teacher.R
import com.milai.lll_teacher.custom.util.DialogUtils
import com.milai.lll_teacher.custom.view.ChooseHeadViewDialog
import com.tencent.smtt.sdk.ValueCallback
import com.tencent.smtt.sdk.WebChromeClient
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient




class InformationCommitActivity : AppCompatActivity() {

    val tel:String by lazy{ intent.getStringExtra("tel")}
    val verify: String by lazy { intent.getStringExtra("verify") }
    val psw: String by lazy { intent.getStringExtra("psw") }
    val webView: WebView by lazy{findViewById(R.id.wv) as WebView }
    //调起相机的回复
    var isFromCamera:Boolean = true //标识是否从相机返回
    var mUploadMessage: ValueCallback<Uri>? = null
    var mUploadMessageAboveLolipop: ValueCallback<Array<Uri>>? = null

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

            //above Lollipop
            override fun onShowFileChooser(webView: WebView?, filePathCallback: ValueCallback<Array<Uri>>?
                                           , fileChooserParams: FileChooserParams?): Boolean {
                Log.d("test","onCLickPicker above Lollipop")
                mUploadMessageAboveLolipop = filePathCallback
                choosePicture()
                return super.onShowFileChooser(webView, filePathCallback, fileChooserParams)
            }

            //android>4.4 使用此方法
            override fun openFileChooser(uploadMsg: ValueCallback<Uri>?, p1: String?, p2: String?) {
                Log.d("test","onCLickPicker below5.0")
                mUploadMessage = uploadMsg
                choosePicture()
            }

        })
        webView.loadUrl("http://lll.domobile.net/Home/Index/addinfo?tel=$tel&verify=$verify&pwd=$psw")
    }

    //选择图片 调用PhotoPicker
    private fun choosePicture() {
        ChooseHeadViewDialog(this, R.style.CustomDialog, object : ChooseHeadViewDialog.OnItemClickListener {
            override fun onClickCamera(view: View, dialog: Dialog) {
                dialog.dismiss()
//                cameraWapper()                             //申请相机权限
            }

            override fun onClickAlbum(view: View, dialog: Dialog) {
                dialog.dismiss()
                //调用相册
                isFromCamera = false
                val intent = Intent.createChooser(Intent()
                        .setAction(Intent.ACTION_GET_CONTENT).setType("image/*"), "选择相册")
                startActivityForResult(intent, Constant.CHOOSE_PICTURE)
            }
        }).show()
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                Constant.CHOOSE_PICTURE ->{
                    val fileUri = data.data
                    //对返回的Uri地址进行转换
//                    fileUri = UriUtils.convertUri(fileUri, this);
                    onActivityCallBack(isFromCamera,fileUri)
//                    startPhotoCrop(fileUri) // 开始对图片进行裁剪处理
                }
                Constant.CROP_SMALL_PICTURE ->{
                    //裁剪返回
                }
            }
        }
    }

    private fun onActivityCallBack(fromCamera: Boolean, fileUri: Uri?) {
        if (fromCamera) {

        }
        if (mUploadMessageAboveLolipop != null && fileUri != null) {
            val urls = arrayOf(fileUri)
            mUploadMessageAboveLolipop?.onReceiveValue(urls)
            mUploadMessageAboveLolipop = null
        }else if (mUploadMessage != null && fileUri != null) {
            mUploadMessage?.onReceiveValue(fileUri)
            mUploadMessage = null
        } else {
            mUploadMessage?.onReceiveValue(null)
            mUploadMessageAboveLolipop?.onReceiveValue(null)
            Toast.makeText(this, "无法获取数据", Toast.LENGTH_LONG).show()
        }

    }


    //裁剪图片Uri
    fun startPhotoCrop(uri: Uri?) {
        if (uri == null) {
            Log.d("tag", "The uri is not exist.")
            return
        }
        val intent = Intent("com.android.camera.action.CROP")
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.setDataAndType(uri, "image/*")
        // 设置裁剪
        intent.putExtra("crop", "true")
        //是否保持比例
//        intent.putExtra("scale", true)
        // aspectX aspectY 是宽高的比例
//        intent.putExtra("aspectX", 1)
//        intent.putExtra("aspectY", 1)
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 300)
//        intent.putExtra("outputY", 120)
        intent.putExtra("return-data", true)
        startActivityForResult(intent, Constant.CROP_SMALL_PICTURE)
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
