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
import com.meishipintu.lll_office.customs.CustomProgressDialog
import com.meishipintu.lll_office.customs.utils.*
import com.meishipintu.lll_office.modles.entities.UserInfo
import com.meishipintu.lll_office.presenters.UpdateInfoPresenter
import java.io.File

class updateInformationActivity : BasicActivity(),View.OnClickListener, UpdateInfoContract.IView
        ,PicGetUtil.SuccessListener {

    val levels = Cookies.getConstant(7)

    val levelWant:Int by lazy{ this.intent.getIntExtra("level", 0) }
    val money:Float by lazy{ levels[levelWant].split("&")[1].toFloat()}

    val etName:EditText by lazy { findViewById(R.id.et_name)as EditText }
    val etAddress:EditText by lazy { findViewById(R.id.et_address)as EditText }
    val etContactor:EditText by lazy { findViewById(R.id.et_contactor)as EditText }
    val etContact:EditText by lazy { findViewById(R.id.et_contact)as EditText }
    val etIntro:EditText by lazy { findViewById(R.id.et_intro)as EditText }
    val ivAdd:ImageView by lazy{ findViewById(R.id.iv_add) as ImageView}

    var upload = false  //标记是否上传图片成功
    var tempFile: File? = null      //保存最终存储的图片

    val glide:RequestManager by lazy{ Glide.with(this) }
    val progressDialog: CustomProgressDialog by lazy { CustomProgressDialog(this,R.style.CustomProgressDialog) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_information)
        presenter = UpdateInfoPresenter(this)
        initUI()
    }

    private fun initUI() {
        Log.d("test", "levelWant: $levelWant, money: ${levels[levelWant]}")

        val title = findViewById(R.id.tv_title) as TextView
        title.text = "信息填写"
        findViewById(R.id.bt_back).setOnClickListener(this)
        findViewById(R.id.bt_pay).setOnClickListener(this)
        ivAdd.setOnClickListener(this)
        val tvMoney = findViewById(R.id.tv_money) as TextView
        tvMoney.text = "¥ ${NumberUtil.formatNumberInTwo(money)}"

        if (OfficeApplication.userInfo != null) {
            val etName = findViewById(R.id.et_name) as EditText
            etName.setText(OfficeApplication.userInfo?.name)
            val etAddress = findViewById(R.id.et_address) as EditText
            etAddress.setText(OfficeApplication.userInfo?.address)
            val etContactor = findViewById(R.id.et_contactor) as EditText
            etContactor.setText(OfficeApplication.userInfo?.contact)
            val etContactTel = findViewById(R.id.et_contact) as EditText
            etContactTel.setText(OfficeApplication.userInfo?.contact_tel)
            val etIntro = findViewById(R.id.et_intro) as EditText
            etIntro.setText(OfficeApplication.userInfo?.introduce_detail)
            val ivAdd = findViewById(R.id.iv_add) as ImageView
            glide.load(OfficeApplication.userInfo?.certification).error(R.drawable.icon_add).into(ivAdd)
            if (!OfficeApplication.userInfo?.certification.isNullOrEmpty()) {
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
                progressDialog.dialogShow()
                if (tempFile == null) {
                    (presenter as UpdateInfoPresenter).updateOfficeInfoWithoutPic(OfficeApplication.userInfo?.uid!!
                            , etName.text.toString(), etAddress.text.toString(), etContactor.text.toString()
                            , etContact.text.toString(), etIntro.text.toString())
                } else {
                    //压缩后的图片都是保存在tempFile
                    (presenter as UpdateInfoPresenter).updateOfficeInfoCertificate(OfficeApplication.userInfo?.uid!!
                            ,etName.text.toString(),etAddress.text.toString(),etContactor.text.toString()
                            ,etContact.text.toString(),etIntro.text.toString(),tempFile!!)
                    //将支付按钮设为unable
                    findViewById(R.id.bt_pay).isEnabled = false
                }
            }
            R.id.iv_add -> {
                PicGetUtil.choosePicture(this, this)
            }
        }
    }

    //from PicGetUtils.SuccessListener
    override fun onSuccess(file: File) {
        //上传成功
        upload = true
        tempFile = file
        glide.load(tempFile).skipMemoryCache(true).into(ivAdd)
    }

    override fun onError(e: String) {
        progressDialog.dialogDismiss()
        toast(e)
        //将支付按钮设还原
        findViewById(R.id.bt_pay).isEnabled = true
    }

    override fun onUploadSuccess(userInfo: UserInfo) {
        progressDialog.dialogDismiss()
        Cookies.saveUserInfo(userInfo)
        OfficeApplication.userInfo = userInfo
        val intent = Intent(this, PayActivity::class.java)
        intent.putExtra("money",money )
        intent.putExtra("levelWant", levelWant)
        startActivity(intent)
        this.finish()
        //将支付按钮设还原
        findViewById(R.id.bt_pay).isEnabled = true
    }

    //权限申请回调
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        PicGetUtil.onPermissiionRequestResult(this, requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        PicGetUtil.onActivityResult(this, requestCode, resultCode, data)
    }
}
