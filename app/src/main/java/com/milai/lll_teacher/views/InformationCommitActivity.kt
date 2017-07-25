package com.milai.lll_teacher.views

import android.app.Dialog
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
import android.graphics.Bitmap
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.Toast
import com.milai.lll_teacher.custom.util.DialogUtils
import com.milai.lll_teacher.custom.view.ChooseHeadViewDialog
import android.provider.MediaStore


class InformationCommitActivity : AppCompatActivity() {

    val tel:String by lazy{ intent.getStringExtra("tel")}
    val verify: String by lazy { intent.getStringExtra("verify") }
    val psw: String by lazy { intent.getStringExtra("psw") }
    val webView: WebView by lazy{findViewById(R.id.wv) as WebView }
    //调起相机的回复
    var fromCamera: Boolean = false //是否选择从照相选择图片
    var mUploadMessage: ValueCallback<Uri>? = null
    var mUploadMessageAboveLollipop: ValueCallback<Array<Uri>>? = null

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
                Log.d("test","onCLickPicker")
                mUploadMessageAboveLollipop = filePathCallback
                choosePicture()
                //返回true 反则会出现多次调用showFileChooser的错误
                return true
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
                cameraWapper()                             //申请相机权限
            }

            override fun onClickAlbum(view: View, dialog: Dialog) {
                dialog.dismiss()
                //调用相册
                fromCamera = false
                val intent = Intent.createChooser(Intent()
                        .setAction(Intent.ACTION_GET_CONTENT).setType("image/*"), "选择相册")
                startActivityForResult(intent, Constant.CHOOSE_PICTURE_FROM_ALBUN)
            }
        }).show()
    }

    //相机权限申请包装方法
    fun cameraWapper() {
        val hasStoragePermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
        if (hasStoragePermission != PackageManager.PERMISSION_GRANTED) {        //未授权
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.CAMERA)) {                    //系统申请权限框不再弹出
                DialogUtils.showCustomDialog(this, "本应用需要获取使用相机权限", { dialog, which ->
                    ActivityCompat.requestPermissions(this@InformationCommitActivity, arrayOf(android.Manifest.permission.CAMERA)
                            , Constant.REQUEST_CAMERA_PERMISSION)
                    dialog.dismiss()
                }) { dialog, which -> dialog.dismiss() }
                return
            }
            //系统框弹出时直接申请
            ActivityCompat.requestPermissions(this@InformationCommitActivity, arrayOf(android
                    .Manifest.permission.CAMERA), Constant.REQUEST_CAMERA_PERMISSION)
            return
        }

        startCamera()
    }

    //启动相机
    private fun startCamera() {
        /**
         *  调用相机，获取压缩后的图片
         *  如需获取不压缩图片，需要intent.intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
         *  此时还分当前SDK是否>7.0，如果小于7.0可以file//类型Uri存储照片，如果>=7.0，则只接受content://类型Uri
         *  还需要配置FileProvider来提供更存储Uri
         */
        //TODO 还是要原图然后压缩，有空修改
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        //调用系统相机
        startActivityForResult(intent, Constant.TAKE_PHOTO)
        fromCamera = true
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        var resultUri:Uri?
        if (fromCamera) {
            //对Uri进行转换
            resultUri = Uri.parse(MediaStore.Images.Media.insertImage(contentResolver, data?.extras?.get("data") as Bitmap
                    , null, null))
        } else {
            //相册返回
            resultUri = data?.data
        }

        if (null != mUploadMessage) {
            //4.4-5.0
            if (null == data) {
                mUploadMessage?.onReceiveValue(null)
            } else {
                //相册选择图片返回
                mUploadMessage?.onReceiveValue(resultUri)
            }
            mUploadMessage = null
        }else if (null != mUploadMessageAboveLollipop) {
            if (null == data) {
                mUploadMessageAboveLollipop?.onReceiveValue(null)
            } else {
                val uris = arrayOf(resultUri!!)
                mUploadMessageAboveLollipop?.onReceiveValue(uris)
            }
            mUploadMessageAboveLollipop = null
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

            Constant.REQUEST_CAMERA_PERMISSION ->{
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    //拒绝授权
                    Toast.makeText(this, "没有相机权限，将有部分功能无法使用，请在系统设置中增加应用的相应授权", Toast.LENGTH_SHORT)
                            .show()
                } else {
                    startCamera()
                }
            }
        }
    }
}
