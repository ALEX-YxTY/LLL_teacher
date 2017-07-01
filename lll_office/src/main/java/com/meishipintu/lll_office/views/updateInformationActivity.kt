package com.meishipintu.lll_office.views

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.meishipintu.lll_office.R
import com.meishipintu.lll_office.customs.utils.NumberUtil
import com.meishipintu.lll_office.customs.utils.StringUtils
import com.meishipintu.lll_office.customs.utils.ToastUtil

class updateInformationActivity : AppCompatActivity(),View.OnClickListener {

    val money:Int by lazy { this.intent.getIntExtra("money", 49) }
    val etName:EditText by lazy { findViewById(R.id.et_name)as EditText }
    val etAddress:EditText by lazy { findViewById(R.id.et_address)as EditText }
    val etContactor:EditText by lazy { findViewById(R.id.et_contactor)as EditText }
    val etContact:EditText by lazy { findViewById(R.id.et_contact)as EditText }
    val etIntro:EditText by lazy { findViewById(R.id.et_intro)as EditText }
    var isPicUpload = false     //表示图片是否上传成功


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_information)
        initUI()
    }

    private fun initUI() {
        val title = findViewById(R.id.tv_title) as TextView
        title.text = "信息填写"
        findViewById(R.id.bt_back).setOnClickListener(this)
        findViewById(R.id.bt_pay).setOnClickListener(this)
        findViewById(R.id.iv_add).setOnClickListener(this)
        val tvMoney = findViewById(R.id.tv_money) as TextView
        tvMoney.text = "¥ ${NumberUtil.formatNumberInTwo(money)}"

    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.bt_back -> onBackPressed()
            R.id.bt_pay -> {
                //TODO 输入检查
                if (StringUtils.isNullOrEmpty(arrayOf(etName.text.toString(), etAddress.text.toString()
                        , etContactor.text.toString(), etContact.text.toString()))) {
                    ToastUtil.show(this,"内容不能为空",true)
                }
                val intent = Intent(this, PayActivity::class.java)
                intent.putExtra("money", money)
                startActivity(intent)
            }
            R.id.iv_add -> {
                //TODO 弹窗上传pic
            }
        }
    }

}
