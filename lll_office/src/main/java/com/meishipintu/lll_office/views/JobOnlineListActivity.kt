package com.meishipintu.lll_office.views

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import com.meishipintu.lll_office.OfficeApplication
import com.meishipintu.lll_office.R
import com.meishipintu.lll_office.contract.JobManagerContract
import com.meishipintu.lll_office.customs.utils.DialogUtils
import com.meishipintu.lll_office.modles.entities.DeliverInfo
import com.meishipintu.lll_office.modles.entities.JobInfo
import com.meishipintu.lll_office.presenters.JobManagerPresenter
import com.meishipintu.lll_office.views.adapters.JobAdapter
import com.meishipintu.lll_office.views.adapters.OnItemClickListener

class JobOnlineListActivity : BasicActivity() , JobManagerContract.IView,OnItemClickListener{

    val dataList: MutableList<JobInfo> = mutableListOf()
    val tid:String by lazy { intent.getStringExtra("tid") }
    val adapter: JobAdapter by lazy { JobAdapter(this, dataList, 3, this) } //type=3 点击直接邀约

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_online_list)
        presenter = JobManagerPresenter(this)
        findViewById(R.id.bt_back).setOnClickListener{ onBackPressed()}
        val textView = findViewById(R.id.tv_title) as TextView
        textView.text = "已上线职位"
        initList()
    }

    private fun initList() {
        val rv = findViewById(R.id.rv_job) as RecyclerView
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = this.adapter
        getData(1)
    }

    private fun getData(status: Int) {
        if (OfficeApplication.userInfo != null) {
            (presenter as JobManagerPresenter).getDataList(OfficeApplication.userInfo!!.uid, status)
        }
    }

    override fun onDateGet(dataList: List<JobInfo>) {
        this.dataList.clear()
        this.dataList.addAll(dataList)
        adapter.notifyDataSetChanged()
    }


    //职位条目点击
    override fun onItemClick(pid: String) {
        //判断是否已投递
        (presenter as JobManagerContract.IPresenter).isDieliverPosition(tid,pid)
    }

    //判断是否已投递回调
    override fun onDeliverGet(isDieliver: Boolean, pid: String) {
        if (!isDieliver) {
            DialogUtils.showCustomDialog(this, "是否使用一次主动邀约机会邀请该教师参加此职位的面试？", { dialog, _ ->
                (presenter as JobManagerPresenter).inviteInterview(pid.toInt(), tid, OfficeApplication.userInfo?.uid!!)
                dialog.dismiss()
            })
        } else {
            //进入私信界面
            val intent = Intent(this, ChatDetailActivity::class.java)
            intent.putExtra("jobId", pid.toInt())
            intent.putExtra("oid", OfficeApplication.userInfo?.uid!!)
            intent.putExtra("teacher",  tid)
            startActivity(intent)
        }
    }

    //邀约成功回调
    override fun onInviteSuccess(jobId:Int) {
        toast("邀约成功!")
        //进入私信界面
        val intent = Intent(this, ChatDetailActivity::class.java)
        intent.putExtra("jobId", jobId)
        intent.putExtra("oid", OfficeApplication.userInfo?.uid!!)
        intent.putExtra("teacher",  tid)
        startActivity(intent)
    }

    override fun onError(e: String) {
        toast(e)
    }

}
