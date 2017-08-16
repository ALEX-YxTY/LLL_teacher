package com.meishipintu.lll_office.views

import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.FileProvider
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.meishipintu.lll_office.Constant
import com.meishipintu.lll_office.Cookies
import com.meishipintu.lll_office.OfficeApplication
import com.meishipintu.lll_office.R
import com.meishipintu.lll_office.contract.UpdateInfoContract
import com.meishipintu.lll_office.customs.ChooseHeadViewDialog
import com.meishipintu.lll_office.customs.CircleImageView
import com.meishipintu.lll_office.customs.CustomProgressDialog
import com.meishipintu.lll_office.customs.utils.DialogUtils
import com.meishipintu.lll_office.customs.utils.StringUtils
import com.meishipintu.lll_office.modles.entities.UserInfo
import com.meishipintu.lll_office.presenters.UpdateInfoPresenter
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

class EditInfoActivity : BasicActivity(),View.OnClickListener, UpdateInfoContract.IView {

    val glide:RequestManager by lazy { Glide.with(this) }
    val userInfo:UserInfo? by lazy{ OfficeApplication.userInfo}

    val ivHead:CircleImageView by lazy{findViewById(R.id.iv_head) as CircleImageView}
    val ivCertificate:ImageView by lazy{findViewById(R.id.iv_certificate) as ImageView}
    val etName:EditText by lazy{ findViewById(R.id.et_office_name) as EditText }
    val etContactor:EditText by lazy{ findViewById(R.id.et_contactor) as EditText }
    val etTel:EditText by lazy{ findViewById(R.id.et_contact_number) as EditText }
    val etAdd:EditText by lazy{ findViewById(R.id.et_office_address) as EditText }
    val etDetail:EditText by lazy{ findViewById(R.id.et_detail) as EditText }

    var isAvatar: Boolean = true        //标记当前是否选择的是头像
    var avatarSuccess = false            //标记头像上传成功
    var certificateSuccess = false            //标记证件照上传成功

    lateinit var tempAvatarFile:File    //头像文件
    lateinit var tempCertificateFile:File   //证书文件
    lateinit var tempFile:File           //存放临时图片
    lateinit var cropFile:File          //裁剪后的文件
    var photoURI: Uri? = null       //保存content开头Uri
    var cropURI: Uri? = null        //保存裁剪之后的file开头Uri

    val progressDialog:CustomProgressDialog by lazy { CustomProgressDialog(this,R.style.CustomProgressDialog) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_info)
        presenter = UpdateInfoPresenter(this)
        initUI()
    }

    private fun initUI() {
        val tvTitle = findViewById(R.id.tv_title) as TextView
        tvTitle.text = "资料修改"
        findViewById(R.id.bt_back).setOnClickListener(this)
        findViewById(R.id.bt_save).setOnClickListener(this)
        ivHead.setOnClickListener(this)
        ivCertificate.setOnClickListener(this)
        glide.load(userInfo?.avatar).error(R.drawable.organization_default).into(ivHead)
        glide.load(userInfo?.certification).into(ivCertificate)
        etName.setText(userInfo?.name)
        etAdd.setText(userInfo?.address)
        etContactor.setText(userInfo?.contact)
        etTel.setText(userInfo?.contact_tel)
        etDetail.setText(userInfo?.introduce_detail)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.bt_back -> onBackPressed()
            R.id.bt_save ->{
                //保存修改
                if (StringUtils.isNullOrEmpty(arrayOf(etName.text.toString(), etAdd.text.toString()
                        , etContactor.text.toString(), etTel.text.toString(), etDetail.text.toString()))) {
                    toast("内容不能为空")
                    return
                }
                Log.d("test", "avatar upload $avatarSuccess,certificate upload $certificateSuccess")
                progressDialog.dialogShow()
                if (avatarSuccess && certificateSuccess) {
                    (presenter as UpdateInfoContract.IPresenter).updateOfficeInfo(OfficeApplication.userInfo?.uid!!
                            ,etName.text.toString(),etAdd.text.toString(),etContactor.text.toString()
                            ,etTel.text.toString(),etDetail.text.toString(),tempCertificateFile,tempAvatarFile)
                }else if (avatarSuccess) {
                    (presenter as UpdateInfoContract.IPresenter).updateOfficeInfoAvatar(OfficeApplication.userInfo?.uid!!
                            ,etName.text.toString(),etAdd.text.toString(),etContactor.text.toString()
                            ,etTel.text.toString(),etDetail.text.toString(),tempAvatarFile)
                }else if (certificateSuccess) {
                    (presenter as UpdateInfoContract.IPresenter).updateOfficeInfoCertificate(OfficeApplication.userInfo?.uid!!
                            , etName.text.toString(), etAdd.text.toString(), etContactor.text.toString()
                            , etTel.text.toString(), etDetail.text.toString(), tempCertificateFile)
                } else {
                    (presenter as UpdateInfoContract.IPresenter).updateOfficeInfoWithoutPic(OfficeApplication.userInfo?.uid!!
                            ,etName.text.toString(),etAdd.text.toString(),etContactor.text.toString()
                            ,etTel.text.toString(),etDetail.text.toString())
                }


            }
            R.id.iv_head ->{
                isAvatar = true
                choosePicture()
            }
            R.id.iv_certificate ->{
                isAvatar = false
                choosePicture()
            }
        }
    }

    //选择图片 调用PhotoPicker
    private fun choosePicture() {
        tempFile = File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "temp.jpg")

        ChooseHeadViewDialog(this, R.style.CustomDialog, object : ChooseHeadViewDialog.OnItemClickListener {
            override fun onClickCamera(view: View, dialog: Dialog) {
                dialog.dismiss()
                startCameraWapper()
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
    fun startCameraWapper() {
        val hasStoragePermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
        if (hasStoragePermission != PackageManager.PERMISSION_GRANTED) {        //未授权
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.CAMERA)) {                    //系统申请权限框不再弹出
                DialogUtils.showCustomDialog(this, "本应用需要获取使用相机权限", { dialog, _ ->
                    ActivityCompat.requestPermissions(this@EditInfoActivity, arrayOf(android.Manifest.permission.CAMERA)
                            , Constant.REQUEST_CAMERA_PERMISSION)
                    dialog.dismiss()
                }) { dialog, _ -> dialog.dismiss() }
                return
            }
            //系统框弹出时直接申请
            ActivityCompat.requestPermissions(this@EditInfoActivity, arrayOf(android
                    .Manifest.permission.CAMERA), Constant.REQUEST_CAMERA_PERMISSION)
            return
        }
        startCamera()
    }

    //启动相机
    private fun startCamera() {
        photoURI = FileProvider.getUriForFile(this, "com.meishipintu.lll_office", tempFile)
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

    //权限申请回调
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        Log.d("test","permission Granted: ${grantResults[0]}")
        when (requestCode) {
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                Constant.CHOOSE_PICTURE_FROM_ALBUN ->{
                    //相册选择图片返回
                    val contentUri = data?.data
                    startPhotoCrop(contentUri) // 开始对图片进行裁剪处理
                }
                Constant.TAKE_PHOTO ->{

                    //拍照返回
                    if (Build.VERSION.SDK_INT < 24) {
                        startPhotoCrop(Uri.fromFile(tempFile)) // 开始对图片进行裁剪处理
                    } else {
                        startPhotoCrop(photoURI)
                    }
                }
                Constant.CROP_SMALL_PICTURE ->{
                    //裁剪返回,调用压缩并保存到tempFile文件
                    if (isAvatar) {
                        tempAvatarFile = File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "avatar${System.currentTimeMillis()}.jpg")
                        compressBitmapToFile(cropFile, tempAvatarFile)
                    } else {
                        tempCertificateFile = File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "certificate${System.currentTimeMillis()}.jpg")
                        compressBitmapToFile(cropFile, tempCertificateFile)
                    }
                }
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
            options.inSampleSize = options.outHeight / 700
        } else {
            options.inSampleSize = options.outWidth / 700
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
            if (isAvatar) {
                glide.load(tempAvatarFile).skipMemoryCache(true)
                        .into(ivHead)
                avatarSuccess = true
            } else {
                glide.load(tempCertificateFile).skipMemoryCache(true)
                        .into(ivCertificate)
                certificateSuccess = true
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onError(e: String) {
        progressDialog.dialogDismiss()
        toast(e)
    }

    override fun onUploadSuccess(userInfo: UserInfo) {
        progressDialog.dialogDismiss()
        Cookies.saveUserInfo(userInfo)
        OfficeApplication.userInfo = userInfo
        toast("资料修改成功！")
    }
}
