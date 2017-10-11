package com.meishipintu.lll_office.views

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.meishipintu.lll_office.Constant
import com.meishipintu.lll_office.OfficeApplication
import com.meishipintu.lll_office.R
import com.meishipintu.lll_office.contract.JobManagerContract
import com.meishipintu.lll_office.modles.entities.JobInfo
import com.meishipintu.lll_office.presenters.JobManagerPresenter
import com.meishipintu.lll_office.views.adapters.JobAdapter

class JobManagerActivity : BasicActivity(),View.OnClickListener,JobManagerContract.IView{


    val dataList: MutableList<JobInfo> = mutableListOf()
    val adapter: JobAdapter by lazy { JobAdapter(this,dataList,2) } //type=2 显示上下线功能

    val tvOnline:TextView by lazy { findViewById(R.id.tv_online) as TextView }
    val tvOffline:TextView by lazy { findViewById(R.id.tv_offline) as TextView }

    var select = 0 //标记当前选择 0-在线职位 1-已下线

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_manager)
        presenter = JobManagerPresenter(this)
        val tvTitle = findViewById(R.id.tv_title) as TextView
        tvTitle.text = "职位管理"
        findViewById(R.id.bt_back).setOnClickListener(this)
        tvOnline.setOnClickListener(this)
        tvOffline.setOnClickListener(this)
        findViewById(R.id.bt_new_job).setOnClickListener(this)

        val rv = findViewById(R.id.rv) as RecyclerView
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = this.adapter
        getData(1)
    }

    private fun getData(status: Int) {
        dataList.clear()        //先清空显示
        adapter.notifyDataSetChanged()
        if (OfficeApplication.userInfo != null) {
            (presenter as JobManagerPresenter).getDataList(OfficeApplication.userInfo!!.uid, status)
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.bt_back -> onBackPressed()
            R.id.tv_online -> {
                if (select != 0) {
                    getData(1)
                    tvOffline.setTextColor(0xff505d67.toInt())
                    tvOnline.setTextColor(0xffFF763F.toInt())
                    select = 0
                }
            }
            R.id.tv_offline ->{
                if (select != 1) {
                    getData(2)
                    tvOffline.setTextColor(0xffFF763F.toInt())
                    tvOnline.setTextColor(0xff505d67.toInt())
                    select = 1
                }
            }
            R.id.bt_new_job ->{
                //发布新职位
                startActivityForResult(Intent(this,NewJobActivity::class.java), Constant.START_NEW_JOB)
            }
        }
    }

    //当邀约成功时调用
    override fun onInviteSuccess() {
        //空实现
    }

    //from JobManagerContract.IView
    override fun onDateGet(dataList: List<JobInfo>) {
        this.dataList.clear()
        this.dataList.addAll(dataList)
        adapter.notifyDataSetChanged()
    }

    //from JobManagerContract.IView
    override fun onError(e: String) {
        toast(e)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constant.START_NEW_JOB && resultCode == Activity.RESULT_OK) {
            //刷新左边页并显示
            getData(1)
            tvOffline.setTextColor(0xff505d67.toInt())
            tvOnline.setTextColor(0xffFF763F.toInt())
            select = 0
        }else if (requestCode == Constant.CHANGE_JOB_STATE && resultCode == Activity.RESULT_OK) {
            //刷新左边页并显示
            getData(1)
            tvOffline.setTextColor(0xff505d67.toInt())
            tvOnline.setTextColor(0xffFF763F.toInt())
            select = 0
        }
    }
}
