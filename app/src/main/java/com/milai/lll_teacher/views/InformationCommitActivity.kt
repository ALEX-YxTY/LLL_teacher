package com.milai.lll_teacher.views

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.milai.lll_teacher.R
import com.milai.lll_teacher.custom.util.StringUtils

class InformationCommitActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_information_commit)
        findViewById(R.id.bt_back).setOnClickListener{ onBackPressed()}
        val tvTitle = findViewById(R.id.tv_title) as TextView
        tvTitle.text = "信息填写"
        initListener()
    }

    private fun initListener() {
        val etName = findViewById(R.id.et_name) as TextView
        val tvSex = findViewById(R.id.tv_sex) as TextView
        val etBirth = findViewById(R.id.et_birth) as TextView
        val etId = findViewById(R.id.et_Id) as TextView

        findViewById(R.id.bt_register).setOnClickListener{
            if (StringUtils.isNullOrEmpty(listOf(etName.text.toString(), tvSex.text.toString()
                    , etBirth.text.toString(), etId.text.toString()))) {
                Toast.makeText(this@InformationCommitActivity, R.string.input_empty, Toast.LENGTH_SHORT)
            } else {
                //TODO 调用注册链接
                startActivity(Intent(this@InformationCommitActivity, MainActivity::class.java)) //启动主页
            }
        }
    }
}
