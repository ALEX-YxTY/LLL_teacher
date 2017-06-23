package com.milai.lll_teacher.views

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.milai.lll_teacher.R
import com.milai.lll_teacher.models.entities.OfficeInfo

class OfficeDetailActivity : AppCompatActivity() {

    val office:OfficeInfo by lazy { intent.getSerializableExtra("offcie") as OfficeInfo }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_office_detail)
        initUI()

    }

    private fun initUI() {

    }
}
