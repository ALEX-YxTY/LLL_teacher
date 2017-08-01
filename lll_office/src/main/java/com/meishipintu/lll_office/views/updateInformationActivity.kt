package com.meishipintu.lll_office.views

import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
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
import com.meishipintu.lll_office.Constant
import com.meishipintu.lll_office.Cookies
import com.meishipintu.lll_office.OfficeApplication
import com.meishipintu.lll_office.R
import com.meishipintu.lll_office.contract.UpdateInfoContract
import com.meishipintu.lll_office.customs.ChooseHeadViewDialog
import com.meishipintu.lll_office.customs.utils.DialogUtils
import com.meishipintu.lll_office.customs.utils.NumberUtil
import com.meishipintu.lll_office.customs.utils.StringUtils
import com.meishipintu.lll_office.customs.utils.UriUtils
import com.meishipintu.lll_office.modles.entities.UserInfo
import com.meishipintu.lll_office.presenters.UpdateInfoPresenter
import java.io.File


class updateInformationActivity : BasicActivity(),View.OnClickListener, UpdateInfoContract.IView {


    //调起相机的回复
    lateinit var tempFile:File
    var upload = false  //标记是否上传图片成功

    val money:Float by lazy { this.intent.getStringExtra("levelWant").split("&")[1].toFloat() }
    val levelWant:Int by lazy{ this.intent.getIntExtra("level", 0) }

    val etName:EditText by lazy { findViewById(R.id.et_name)as EditText }
    val etAddress:EditText by lazy { findViewById(R.id.et_address)as EditText }
    val etContactor:EditText by lazy { findViewById(R.id.et_contactor)as EditText }
    val etContact:EditText by lazy { findViewById(R.id.et_contact)as EditText }
    val etIntro:EditText by lazy { findViewById(R.id.et_intro)as EditText }
    val ivAdd:ImageView by lazy{ findViewById(R.id.iv_add) as ImageView}

    var photoURI: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_information)
        presenter = UpdateInfoPresenter(this)
        initUI()
    }

    private fun initUI() {
        val title = findViewById(R.id.tv_title) as TextView
        title.text = "信息填写"
        findViewById(R.id.bt_back).setOnClickListener(this)
        findViewById(R.id.bt_pay).setOnClickListener(this)
        ivAdd.setOnClickListener(this)
        val tvMoney = findViewById(R.id.tv_money) as TextView
        tvMoney.text = "¥ ${NumberUtil.formatNumberInTwo(money)}"

        if (OfficeApplication.userInfo != null) {
            val etName = findViewById(R.id.et_name) as EditText
            etName.setText(OfficeApplication.userInfo?.name!!)
            val etAddress = findViewById(R.id.et_address) as EditText
            etAddress.setText(OfficeApplication.userInfo?.address!!)
            val etContactor = findViewById(R.id.et_contactor) as EditText
            etContactor.setText(OfficeApplication.userInfo?.contact!!)
            val etContactTel = findViewById(R.id.et_contact) as EditText
            etContactTel.setText(OfficeApplication.userInfo?.contact_tel!!)
            val etIntro = findViewById(R.id.et_intro) as EditText
            etIntro.setText(OfficeApplication.userInfo?.introduce_detail!!)
            val ivAdd = findViewById(R.id.iv_add) as ImageView
            Glide.with(this).load(OfficeApplication.userInfo?.avatar).error(R.drawable.icon_add).into(ivAdd)
            if (!OfficeApplication.userInfo?.avatar.isNullOrEmpty()) {
                upload = true
            }
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.bt_back -> onBackPressed()
            R.id.bt_pay -> {
                if (StringUtils.isNullOrEmpty(arrayOf(etName.text.toString(), etAddress.text.toString()
                        , etContactor.text.toString(), etContact.text.toString(), etIntro.text.toString()))) {
                    toast("内容不能为空")
                    return
                }
                if (!upload) {
                    toast("请上传营业执照")
                    return
                }
                if (photoURI == null) {
                    (presenter as UpdateInfoPresenter).updateOfficeInfo(OfficeApplication.userInfo?.uid!!
                            , etName.text.toString(), etAddress.text.toString(), etContactor.text.toString()
                            , etContact.text.toString(), etIntro.text.toString())
                } else {
                    val certificateFile: File
                    if ("file".equals(photoURI?.scheme)) {
                        certificateFile = File(photoURI?.path)
                        Log.d("test", "name: ${certificateFile.absolutePath}")
                    } else {
                        Log.d("test", "name: ${tempFile.absolutePath}")
                        certificateFile = tempFile
                    }
                    (presenter as UpdateInfoPresenter).updateOfficeInfo(OfficeApplication.userInfo?.uid!!
                            ,etName.text.toString(),etAddress.text.toString(),etContactor.text.toString()
                            ,etContact.text.toString(),etIntro.text.toString(),certificateFile)
                }
            }
            R.id.iv_add -> {
                choosePicture()
            }
        }
    }

    //选择图片 调用PhotoPicker
    private fun choosePicture() {
        tempFile = File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "certificate.jpg")
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
        Log.d("test","permission has: $hasStoragePermission")

        if (hasStoragePermission != PackageManager.PERMISSION_GRANTED) {        //未授权
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.CAMERA)) {                    //系统申请权限框不再弹出
                DialogUtils.showCustomDialog(this, "本应用需要获取使用相机权限", { dialog, _ ->
                    ActivityCompat.requestPermissions(this@updateInformationActivity, arrayOf(android.Manifest.permission.CAMERA)
                            , Constant.REQUEST_CAMERA_PERMISSION)
                    dialog.dismiss()
                }) { dialog, _ -> dialog.dismiss() }
                return
            }
            //系统框弹出时直接申请
            ActivityCompat.requestPermissions(this@updateInformationActivity, arrayOf(android
                    .Manifest.permission.CAMERA), Constant.REQUEST_CAMERA_PERMISSION)
            return
        }

        startCamera()
    }

    //启动相机
    private fun startCamera() {
        /* getUriForFile(Context context, String authority, File file):此处的authority需要和manifest里面保持一致。
                photoURI打印结果：content://cn.lovexiaoai.myapp.fileprovider/camera_photos/Pictures/Screenshots/testImg.png 。
                这里的camera_photos:对应filepaths.xml中的name
            */
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

    override fun onError(e: String) {
        toast(e)
    }

    override fun onUploadSuccess(userInfo: UserInfo) {
        Cookies.saveUserInfo(userInfo)
        OfficeApplication.userInfo = userInfo
        val intent = Intent(this, PayActivity::class.java)
        intent.putExtra("money", money)
        intent.putExtra("levelWant", levelWant)
        startActivity(intent)
        this.finish()
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
                    if (data != null) {
                        val extras = data.extras
                        if (extras != null) {
                            val photo = extras.getParcelable<Bitmap>("data")
                            Glide.with(this).load(photo).into(ivAdd)
                            //将剪切后图片保存在缓存文件中
                            UriUtils.saveBitmap(photo, tempFile)
                            upload = true
                        }
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
            // 设置裁剪
            intent.putExtra("crop", "true")
            // aspectX aspectY 是宽高的比例
//            intent.putExtra("aspectX", 1)
//            intent.putExtra("aspectY", 1)
            // outputX outputY 是裁剪图片宽高
            intent.putExtra("outputX", 500)
//            intent.putExtra("outputY", 120)
            intent.putExtra("return-data", true)
            startActivityForResult(intent, Constant.CROP_SMALL_PICTURE)
        }
    }
}
