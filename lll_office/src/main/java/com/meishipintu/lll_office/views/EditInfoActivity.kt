package com.meishipintu.lll_office.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.meishipintu.lll_office.Cookies
import com.meishipintu.lll_office.OfficeApplication
import com.meishipintu.lll_office.R
import com.meishipintu.lll_office.contract.UpdateInfoContract
import com.meishipintu.lll_office.customs.CircleImageView
import com.meishipintu.lll_office.customs.CustomProgressDialog
import com.meishipintu.lll_office.customs.utils.PicGetUtil
import com.meishipintu.lll_office.customs.utils.StringUtils
import com.meishipintu.lll_office.modles.entities.UserInfo
import com.meishipintu.lll_office.presenters.UpdateInfoPresenter
import java.io.File

class EditInfoActivity : BasicActivity(),View.OnClickListener, UpdateInfoContract.IView,PicGetUtil.SuccessListener {

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
                PicGetUtil.choosePicture(this, this)
            }
            R.id.iv_certificate ->{
                isAvatar = false
                PicGetUtil.choosePicture(this, this)
            }
        }
    }

    override fun onSuccess(file: File) {
        if (isAvatar) {
            glide.load(file).skipMemoryCache(true)
                    .into(ivHead)
            tempAvatarFile = file
            avatarSuccess = true
        } else {
            glide.load(file).skipMemoryCache(true)
                    .into(ivCertificate)
            tempCertificateFile = file
            certificateSuccess = true
        }
    }


    //权限申请回调
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        PicGetUtil.onPermissiionRequestResult(this, requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        PicGetUtil.onActivityResult(this, requestCode, resultCode, data)
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
