package com.milai.lll_teacher.views

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import com.milai.lll_teacher.MyApplication
import com.milai.lll_teacher.R
import com.milai.lll_teacher.contracts.CollectionContract
import com.milai.lll_teacher.models.entities.JobInfo
import com.milai.lll_teacher.presenters.JobPresenter
import com.milai.lll_teacher.views.adapters.JobAdapter

/**
 * 收藏的职位页面
 */
class CollectionActivity : BasicActivity(), CollectionContract.IView {

    val rv:RecyclerView by lazy { findViewById(R.id.rv) as RecyclerView }

    val jobList = mutableListOf<JobInfo>()
    val jobAdapter:JobAdapter by lazy { JobAdapter(this,jobList,1) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collection)
        presenter = JobPresenter(this)
        val title = findViewById(R.id.tv_title) as TextView
        title.text = "收藏的职位"
        findViewById(R.id.bt_back).setOnClickListener{ onBackPressed() }
        val rv = findViewById(R.id.rv) as RecyclerView
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = jobAdapter
        (presenter as JobPresenter).getJobCollection(MyApplication.userInfo?.uid!!)
    }

    //from CollectionContract.IView
    override fun showError(err: String) {
        toast(err)
    }

    //from CollectionContract.IView
    override fun onJobCollectionGet(dataList: List<JobInfo>) {
        this.jobList.clear()
        this.jobList.addAll(dataList)
        jobAdapter.notifyDataSetChanged()
    }
}
