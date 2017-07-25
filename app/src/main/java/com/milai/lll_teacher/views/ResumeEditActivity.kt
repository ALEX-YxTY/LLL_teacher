package com.milai.lll_teacher.views

import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import com.milai.lll_teacher.MyApplication
import com.milai.lll_teacher.R
import com.milai.lll_teacher.custom.view.ChooseHeadViewDialog
import com.tencent.smtt.sdk.ValueCallback
import com.tencent.smtt.sdk.WebChromeClient
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient

class ResumeEditActivity : AppCompatActivity() {

    //调起相机的回复
    var fromCamera: Boolean = false //是否选择从照相选择图片
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
        webView.loadUrl("http://lll.domobile.net/Home/Index/resume?uid=${MyApplication.userInfo?.uid}")
    }

    //选择图片 调用PhotoPicker
    private fun choosePicture() {
        ChooseHeadViewDialog(this, R.style.CustomDialog, object : ChooseHeadViewDialog.OnItemClickListener {
            override fun onClickCamera(view: View, dialog: Dialog) {
                dialog.dismiss()
                fromCamera = true
                startCamera()
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
}
