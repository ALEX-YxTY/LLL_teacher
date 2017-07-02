package com.meishipintu.lll_office.views

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.meishipintu.lll_office.R
import com.meishipintu.lll_office.modles.entities.JobInfo
import com.meishipintu.lll_office.views.adapters.JobAdapter

class JobManagerActivity : AppCompatActivity(),View.OnClickListener{

    val dataList: MutableList<JobInfo> = mutableListOf()
    val adapter: JobAdapter by lazy { JobAdapter(this,dataList) }

    val tvOnline:TextView by lazy { findViewById(R.id.tv_online) as TextView }
    val tvOffline:TextView by lazy { findViewById(R.id.tv_offline) as TextView }

    var select = 0 //标记当前选择 0-在线职位 1-已下线

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_manager)
        val tvTitle = findViewById(R.id.tv_title) as TextView
        tvTitle.text = "职位管理"
        findViewById(R.id.bt_back).setOnClickListener(this)
        tvOnline.setOnClickListener(this)
        tvOffline.setOnClickListener(this)
        findViewById(R.id.bt_new_job).setOnClickListener(this)

        val rv = findViewById(R.id.rv) as RecyclerView
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = this.adapter
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.bt_back -> onBackPressed()
            R.id.tv_online -> {
                if (select != 0) {
                    //TODO 获取在线数据
                    tvOffline.setTextColor(0xff505d67.toInt())
                    tvOnline.setTextColor(0xffFF763F.toInt())
                    select = 0
                }
            }
            R.id.tv_offline ->{
                if (select != 1) {
                    //TODO 获取已下线数据
                    tvOffline.setTextColor(0xffFF763F.toInt())
                    tvOnline.setTextColor(0xff505d67.toInt())
                    select = 1
                }
            }
            R.id.bt_new_job ->{
                startActivity(Intent(this,NewJobActivity::class.java))
            }
        }
    }
}
