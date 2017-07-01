package com.meishipintu.lll_office.views

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.meishipintu.lll_office.R

class UpdateActivity : AppCompatActivity(),View.OnClickListener {

    var select = 0      //当前选择项
    val idList:List<View> by lazy {
        listOf(findViewById(R.id.rl_1), findViewById(R.id.rl_2), findViewById(R.id.rl_3), findViewById(R.id.rl_4))
    }
    val moneys = listOf(49, 149, 249, 349)
    val tvMoney:TextView by lazy { findViewById(R.id.tv_money) as TextView }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)
        initUI()


    }

    private fun initUI() {
        val ivHead = findViewById(R.id.iv_head) as ImageView
        val officeName = findViewById(R.id.office_name) as TextView
        val accountLevel = findViewById(R.id.account_level) as TextView
        findViewById(R.id.rl_1).setOnClickListener(this)
        findViewById(R.id.rl_2).setOnClickListener(this)
        findViewById(R.id.rl_3).setOnClickListener(this)
        findViewById(R.id.rl_4).setOnClickListener(this)
        findViewById(R.id.bt_pay).setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.rl_1 -> { if(select!=0) changeTo(0)}
            R.id.rl_2 -> {if(select!=1) changeTo(1)}
            R.id.rl_3 -> {if(select!=2) changeTo(2)}
            R.id.rl_4 -> {if(select!=3) changeTo(3)}
            R.id.bt_pay -> {
                val intent = Intent(this, updateInformationActivity::class.java)
                intent.putExtra("money", moneys[select])
                startActivity(intent)
            }
        }
    }

    private fun changeTo(index: Int) {
        idList[select].setBackgroundResource(R.drawable.shape_tv_lable_unselect_raidus8)
        select = index
        idList[select].setBackgroundResource(R.drawable.shape_tv_lable_radiu8)
        tvMoney.text = "¥ ${moneys[select]}"
    }
}
