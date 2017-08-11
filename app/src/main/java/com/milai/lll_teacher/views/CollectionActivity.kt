package com.milai.lll_teacher.views

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.TextView
import com.milai.lll_teacher.MyApplication
import com.milai.lll_teacher.R
import com.milai.lll_teacher.contracts.CollectionContract
import com.milai.lll_teacher.custom.view.CanLoadMoreRecyclerView
import com.milai.lll_teacher.models.entities.JobInfo
import com.milai.lll_teacher.presenters.JobPresenter
import com.milai.lll_teacher.views.adapters.JobAdapter

/**
 * 收藏的职位页面
 */
class CollectionActivity : BasicActivity(), CollectionContract.IView,CanLoadMoreRecyclerView.StateChangedListener {


    val rv:CanLoadMoreRecyclerView by lazy { findViewById(R.id.rv) as CanLoadMoreRecyclerView }

    val jobList = mutableListOf<JobInfo>()
    val jobAdapter:JobAdapter by lazy { JobAdapter(this,jobList,1) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collection)
        presenter = JobPresenter(this)
        val title = findViewById(R.id.tv_title) as TextView
        title.text = "收藏的职位"
        findViewById(R.id.bt_back).setOnClickListener{ onBackPressed() }
        rv.listener = this
    }

    override fun onResume() {
        super.onResume()
        rv.setAdapter(jobAdapter)
    }

    //from CollectionContract.IView
    override fun showError(err: String) {
        toast(err)
    }

    //from CollectionContract.IView
    override fun onLoadError() {
        rv.dismissLoading()
        rv.dismissProgressBar()
    }

    //from CanLoadMore.StateChangedListener
    override fun onLoadMore(page: Int) {
        (presenter as JobPresenter).getJobCollection(MyApplication.userInfo?.uid!!,page)
    }

    //from CollectionContract.IView
    override fun onJobCollectionGet(dataList: List<JobInfo>,page:Int) {
        if (page == 1) {
            //首次加载
            this.jobList.clear()
            this.jobList.addAll(dataList)
            rv.onLoadSuccess(page)
            jobAdapter.notifyDataSetChanged()
        }else if (dataList.isNotEmpty()) {
            //load more 并且有数据
            this.jobList.addAll(dataList)
            rv.onLoadSuccess(page)
            jobAdapter.notifyDataSetChanged()
        } else {
            //load more 没数据
            rv.dismissProgressBar()
            rv.dismissLoading()
        }
    }
}
