package com.meishipintu.lll_office.customs.utils

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.FileProvider
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.meishipintu.lll_office.Constant
import com.meishipintu.lll_office.R
import com.meishipintu.lll_office.customs.ChooseHeadViewDialog
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

/**
 * Created by Administrator on 2017/8/16.
 *
 * 主要功能：从拍照或相册获取图片的工具类
 */
object PicGetUtil {

    var tempFile: File? = null  //存放临时图片
    var tempURI: Uri? = null    //临时图片的URI
    var cropFile: File? = null  //存放裁剪后图片
    var cropURI: Uri? = null    //裁剪后图片的URI
    var successListener: SuccessListener? = null  //成功回调

    //选择图片 调用PhotoPicker
    fun choosePicture(context: Activity, listener: SuccessListener) {
        successListener = listener
        tempFile = File(context.getExternalFilesDir(android.os.Environment.DIRECTORY_PICTURES), "temp.jpg")
        ChooseHeadViewDialog(context, R.style.CustomDialog, object : ChooseHeadViewDialog.OnItemClickListener {
            override fun onClickCamera(view: View, dialog: Dialog) {
                dialog.dismiss()
                startCameraWapper(context, tempFile!!)
            }

            override fun onClickAlbum(view: View, dialog: Dialog) {
                dialog.dismiss()
                //调用相册
                val intent = Intent.createChooser(Intent()
                        .setAction(Intent.ACTION_GET_CONTENT).setType("image/*"), "选择相册")
                context.startActivityForResult(intent, Constant.CHOOSE_PICTURE_FROM_ALBUN)
            }
        }).show()
    }

    //相机权限申请包装方法
    fun startCameraWapper(context: Activity, file: File) {
        val hasStoragePermission = ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA)

        if (hasStoragePermission != PackageManager.PERMISSION_GRANTED) {        //未授权
            if (!ActivityCompat.shouldShowRequestPermissionRationale(context, android.Manifest.permission.CAMERA)) {                    //系统申请权限框不再弹出
                DialogUtils.showCustomDialog(context, "本应用需要获取使用相机权限", { dialog, _ ->
                    ActivityCompat.requestPermissions(context, arrayOf(android.Manifest.permission.CAMERA)
                            , Constant.REQUEST_CAMERA_PERMISSION)
                    dialog.dismiss()
                }) { dialog, _ -> dialog.dismiss() }
                return
            }
            //系统框弹出时直接申请
            ActivityCompat.requestPermissions(context, arrayOf(android
                    .Manifest.permission.CAMERA), Constant.REQUEST_CAMERA_PERMISSION)
            return
        }
        startCamera(context, file)
    }

    //启动相机
    private fun startCamera(context: Activity,file:File) {
        /* getUriForFile(Context context, String authority, File file):此处的authority需要和manifest里面保持一致。
                photoURI打印结果：content://cn.lovexiaoai.myapp.fileprovider/camera_photos/Pictures/Screenshots/testImg.png 。
                这里的camera_photos:对应filepaths.xml中的name
            */
        tempURI = FileProvider.getUriForFile(context, context.packageName, file)
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
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file))
        } else {
            /* 这句要记得写：这是申请权限，之前因为没有添加这个，打开裁剪页面时，一直提示“无法修改低于50*50像素的图片”，
                开始还以为是图片的问题呢，结果发现是因为没有添加FLAG_GRANT_READ_URI_PERMISSION。
                如果关联了源码，点开FileProvider的getUriForFile()看看（下面有），注释就写着需要添加权限。
            */
            //获取相机元图片，不经过压缩，并保存在uir位置
            intent.putExtra(MediaStore.EXTRA_OUTPUT, tempURI)
            //调用系统相机
        }
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        context.startActivityForResult(intent, Constant.TAKE_PHOTO)
    }

    //启动裁剪图片
    private fun startPhotoCrop(context: Activity, contentUri: Uri?) {
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
            cropFile = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "crop.jpg")
            cropURI = Uri.fromFile(cropFile)
            Log.d("test", "cropUri:${cropURI?.path}")
            intent.putExtra(MediaStore.EXTRA_OUTPUT, cropURI)
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString())
            context.startActivityForResult(intent, Constant.CROP_SMALL_PICTURE)
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
            successListener?.onSuccess(fileTo)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun onActivityResult(context: Activity, requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == AppCompatActivity.RESULT_OK) {
            when (requestCode) {
                Constant.CHOOSE_PICTURE_FROM_ALBUN -> {
                    //相册选择图片返回
                    val contentUri = data?.data
                    startPhotoCrop(context, contentUri) // 开始对图片进行裁剪处理
                }
                Constant.TAKE_PHOTO -> {
                    //拍照返回
                    if (Build.VERSION.SDK_INT < 24) {
                        startPhotoCrop(context, Uri.fromFile(tempFile)) // 开始对图片进行裁剪处理
                    } else {
                        startPhotoCrop(context, tempURI)
                    }
                }
                Constant.CROP_SMALL_PICTURE -> {
                    val finalFile = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "${System.currentTimeMillis()}.jpg")
                    //裁剪返回,调用压缩并保存到tempFile文件
                    compressBitmapToFile(cropFile!!, finalFile)
                }
            }
        }
    }

    fun onPermissiionRequestResult(context: Activity,requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            Constant.REQUEST_CAMERA_PERMISSION ->{
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    //拒绝授权
                    Toast.makeText(context, "没有相机权限，将有部分功能无法使用，请在系统设置中增加应用的相应授权", Toast.LENGTH_SHORT)
                            .show()
                } else {
                    startCamera(context, tempFile!!)
                }
            }
        }
    }

    interface SuccessListener{
        fun onSuccess(file: File)
    }
}