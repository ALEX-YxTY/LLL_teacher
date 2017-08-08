package com.milai.lll_teacher.views

import android.app.Dialog
import android.content.DialogInterface
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
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Environment
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.Toast
import com.milai.lll_teacher.custom.util.DialogUtils
import com.milai.lll_teacher.custom.view.ChooseHeadViewDialog
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import com.milai.lll_teacher.Constant
import com.tencent.smtt.export.external.interfaces.JsResult
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream


class InformationCommitActivity : AppCompatActivity() {

    val tel:String by lazy{ intent.getStringExtra("tel")}
    val verify: String by lazy { intent.getStringExtra("verify") }
    val psw: String by lazy { intent.getStringExtra("psw") }
    val webView: WebView by lazy{findViewById(R.id.wv) as WebView }

    //调起相机相册的回复，接收返回file：// URI
    var mUploadMessage: ValueCallback<Uri>? = null
    var mUploadMessageAboveLollipop: ValueCallback<Array<Uri>>? = null

    lateinit var tempFile:File  //存储原始文件
    lateinit var cropFile:File  //存储裁剪后的文件
    lateinit var finalFile:File     //存储最终上传的文件
    var photoURI: Uri? = null       //保存content开头Uri
    var cropURI: Uri? = null        //保存裁剪之后的file开头Uri

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
        tempFile = File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "temp.jpg")
        photoURI = FileProvider.getUriForFile(this, "com.milai.lll_teacher", tempFile)
        /**
         *  调用相机，获取压缩后的图片
         *  如需获取不压缩图片，需要intent.intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
         *  此时还分当前SDK是否>7.0，如果小于7.0可以file//类型Uri存储照片，如果>=7.0，则只接受content://类型Uri
         *  还需要配置FileProvider来提供更存储Uri
         */
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val currentapiVersion = android.os.Build.VERSION.SDK_INT
        if (currentapiVersion < 24) {
            //7.0以前版本
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile))
        } else {
            /* 这句要记得写：这是申请权限，之前因为没有添加这个，打开裁剪页面时，一直提示“无法修改低于50*50像素的图片”，
                开始还以为是图片的问题呢，结果发现是因为没有添加FLAG_GRANT_READ_URI_PERMISSION。
                如果关联了源码，点开FileProvider的getUriForFile()看看（下面有），注释就写着需要添加权限。
            */
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            //获取相机元图片，不经过压缩，并保存在uir位置
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            //调用系统相机
        }
        startActivityForResult(intent, Constant.TAKE_PHOTO)
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
            when (requestCode) {
                Constant.CHOOSE_PICTURE_FROM_ALBUN -> {
                    //相册选择图片返回
                    val contentUri = data?.data
                    startPhotoCrop(contentUri) // 开始对图片进行裁剪处理
                }
                Constant.TAKE_PHOTO -> {
                    //拍照返回
                    if (Build.VERSION.SDK_INT < 24) {
                        startPhotoCrop(Uri.fromFile(tempFile)) // 开始对图片进行裁剪处理
                    } else {
                        startPhotoCrop(photoURI)
                    }
                }
                Constant.CROP_SMALL_PICTURE -> {
                    finalFile = File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "${System.currentTimeMillis()}.jpg")
                    //裁剪返回,调用压缩并保存到tempFile文件
                    compressBitmapToFile(cropFile, finalFile)
                }
            }
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

    //启动裁剪图片
    private fun startPhotoCrop(contentUri: Uri?) {
        if (contentUri != null) {
            val intent = Intent("com.android.camera.action.CROP")
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.setDataAndType(contentUri, "image/*")
            // 设置可裁剪
            intent.putExtra("crop", "true")
            // aspectX aspectY 是宽高的比例
            //intent.putExtra("aspectX", 1)
            //intent.putExtra("aspectY", 1)
            // outputX outputY 是裁剪图片宽高
            //intent.putExtra("outputX", 500)
            //intent.putExtra("outputY", 120)
            intent.putExtra("return-data", false)
            cropFile = File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "crop.jpg")
            cropURI = Uri.fromFile(cropFile)
            Log.d("test", "cropUri:${cropURI?.path}")
            intent.putExtra(MediaStore.EXTRA_OUTPUT, cropURI)
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString())
            startActivityForResult(intent, Constant.CROP_SMALL_PICTURE)
        }
    }

    //压缩图片并保存
    fun compressBitmapToFile(fileFrom: File, fileTo: File) {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true       //这个参数设置为true才有效，
        BitmapFactory.decodeFile(fileFrom.absolutePath, options)
        //通过计算获取压缩比例，并设置在options.inSampleSize中（inSampleSize必须>1）
        //压缩比例为宽高中的长边压缩到700像素
        if (options.outHeight > options.outWidth) {
            options.inSampleSize = options.outHeight / 500
        } else {
            options.inSampleSize = options.outWidth / 500
        }
        options.inJustDecodeBounds = false
        var bitmap = BitmapFactory.decodeFile(fileFrom.absolutePath, options)

        val baos = ByteArrayOutputStream()
        // 把质量压缩后的数据存放到baos中
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100 ,baos)
        try {
            val fos = FileOutputStream(fileTo)
            fos.write(baos.toByteArray())
            fos.flush()
            fos.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
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
