package com.meishipintu.lll_office.views

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import com.meishipintu.lll_office.*
import com.meishipintu.lll_office.modles.entities.BusMessage

class SettingActivity : AppCompatActivity(),View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        val tvTitle = findViewById(R.id.tv_title) as TextView
        tvTitle.text = "我的设置"
        findViewById(R.id.bt_back).setOnClickListener(this)
        findViewById(R.id.bt_logout).setOnClickListener(this)
        findViewById(R.id.rl_edit_info).setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.bt_back -> onBackPressed()
            R.id.rl_edit_info -> startActivityForResult(Intent(this, EditInfoActivity::class.java),Constant.EditInfo)
            R.id.bt_logout -> {
                //清除数据
                Cookies.clearUserInfo()
                OfficeApplication.userInfo = null
                RxBus.send(BusMessage(Constant.LOGOUT))
                this.finish()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == Constant.EditInfo) {
            //修改资料成功
            setResult(Activity.RESULT_OK)
        }
    }
}
