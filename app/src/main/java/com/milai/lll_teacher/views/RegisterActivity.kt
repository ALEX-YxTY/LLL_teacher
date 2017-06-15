package com.milai.lll_teacher.views

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.milai.lll_teacher.R
import com.milai.lll_teacher.contracts.RegisterContract
import com.milai.lll_teacher.models.entities.UserInfo

class RegisterActivity : AppCompatActivity() ,RegisterContract.iView{



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_register)
        setContentView(R.layout.activity_office_detail)

    }

    override fun showError(err: String) {
    }

    override fun onRegisterSucess(userInfo: UserInfo) {
    }

    override fun onVCode(vCode: String) {
    }
}
