package com.meishipintu.lll_office.views

import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.meishipintu.lll_office.OfficeApplication
import com.meishipintu.lll_office.R
import com.meishipintu.lll_office.customs.CircleImageView
import com.meishipintu.lll_office.modles.entities.UserInfo

class EditInfoActivity : BasicActivity(),View.OnClickListener {

    val glide:RequestManager by lazy { Glide.with(this) }
    val userInfo:UserInfo? by lazy{ OfficeApplication.userInfo}

    val ivHead:CircleImageView by lazy{findViewById(R.id.iv_head) as CircleImageView}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_info)
        initUI()
    }

    private fun initUI() {
        val tvTitle = findViewById(R.id.tv_title) as TextView
        tvTitle.text = "资料修改"
        findViewById(R.id.bt_back).setOnClickListener(this)
        glide.load(userInfo?.avatar).error(R.drawable.organization_default).into(ivHead)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.bt_back -> onBackPressed()

        }
    }

}
