package com.milai.lll_teacher.custom.util

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.FileProvider
import android.util.Log
import android.view.View
import com.milai.lll_teacher.Constant
import com.milai.lll_teacher.R
import com.milai.lll_teacher.custom.view.ChooseHeadViewDialog
import java.io.File

/**
 * Created by Administrator on 2017/8/16.
 *
 * 主要功能：从拍照或相册获取图片的工具类
 */
object PicGetUtil {
    //选择图片 调用PhotoPicker
    fun choosePicture(context: Activity) {
        val tempFile = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "temp.jpg")
        ChooseHeadViewDialog(context, R.style.CustomDialog, object : ChooseHeadViewDialog.OnItemClickListener {
            override fun onClickCamera(view: View, dialog: Dialog) {
                dialog.dismiss()
                startCameraWapper(context,tempFile)
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
        Log.d("test", "permission has: $hasStoragePermission")

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
        val photoURI = FileProvider.getUriForFile(context, context.packageName, file)
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
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            //调用系统相机
        }
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        context.startActivityForResult(intent, Constant.TAKE_PHOTO)
    }
}