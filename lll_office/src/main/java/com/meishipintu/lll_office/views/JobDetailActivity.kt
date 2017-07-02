package com.meishipintu.lll_office.views

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import com.meishipintu.lll_office.R
import com.meishipintu.lll_office.modles.entities.JobInfo

class JobDetailActivity : AppCompatActivity(),View.OnClickListener{

    val jobInfo:JobInfo by lazy { intent.getSerializableExtra("job") as JobInfo }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_detail)

        initUI()
    }

    private fun initUI() {
        val tvTitle = findViewById(R.id.tv_title) as TextView
        tvTitle.text = "职位详情"
        findViewById(R.id.bt_back).setOnClickListener(this)
        val btInvite = findViewById(R.id.bt_invite)
        btInvite.setOnClickListener(this)
//        btInvite.visibility = if(jobInfo.isOutOfTime()) View.GONE else View.VISIBLE
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.bt_back -> onBackPressed()
            R.id.bt_invite ->{
                //TODO 邀请界面
            }
        }
    }
}
