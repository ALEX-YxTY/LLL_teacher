package com.meishipintu.lll_office.views

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import com.meishipintu.lll_office.R
import com.meishipintu.lll_office.customs.utils.NumberUtil

class PayActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay)

        val money = intent.getIntExtra("money", 49)
        val tvTitle = findViewById(R.id.tv_title)as TextView
        tvTitle.text = "支付"
        findViewById(R.id.bt_back).setOnClickListener{ onBackPressed()}
        val tvMoney = findViewById(R.id.tv_money) as TextView
        tvMoney.text = "¥${NumberUtil.formatNumberInTwo(money)}"
        val btPay = findViewById(R.id.bt_pay) as Button
        btPay.text = "确认支付 ¥${NumberUtil.formatNumberInTwo(money)}"
        btPay.setOnClickListener{
            //TODO 调起支付
        }
    }
}
