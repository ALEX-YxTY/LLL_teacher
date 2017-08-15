package com.meishipintu.lll_office.views

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import com.meishipintu.lll_office.OfficeApplication
import com.meishipintu.lll_office.R
import com.meishipintu.lll_office.contract.OtherOrganizationContract
import com.meishipintu.lll_office.customs.CanLoadMoreRecyclerView
import com.meishipintu.lll_office.modles.entities.OfficeInfo
import com.meishipintu.lll_office.presenters.OrganizaitonPresenter
import com.milai.lll_teacher.views.adapters.OfficeAdapter

class OtherOrganizationActivity : BasicActivity(),OtherOrganizationContract.IView,CanLoadMoreRecyclerView.StateChangedListener {

    val dataList = mutableListOf<OfficeInfo>()
    val adapter:OfficeAdapter by lazy{ OfficeAdapter(this, dataList)}
    val rv:CanLoadMoreRecyclerView by lazy{ findViewById(R.id.rv) as CanLoadMoreRecyclerView }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collection)
        presenter = OrganizaitonPresenter(this)
        val tvTitle = findViewById(R.id.tv_title) as TextView
        tvTitle.text = "其他机构"
        findViewById(R.id.bt_back).setOnClickListener{ onBackPressed()}
        rv.listener = this
    }

    override fun onResume() {
        super.onResume()
        rv.setAdapter(adapter)
    }

    //from OtherOrganizationContract.IView
    override fun onError(e: String) {
        toast(e)
    }

    override fun onLoadError() {
        rv.dismissLoading()
        rv.dismissProgressBar()
    }

    override fun onLoadMore(page: Int) {
        (presenter as OtherOrganizationContract.IPresenter).getOrganization(OfficeApplication.userInfo?.uid ?: ""
                , page)
    }

    //from OtherOrganizationContract.IView
    override fun onOrganizationGet(dataList: List<OfficeInfo>, page: Int) {
        if (page == 1) {
            //首次加载
            this.dataList.clear()
            this.dataList.addAll(dataList)
            rv.onLoadSuccess(page)
            adapter.notifyDataSetChanged()
        }else if (dataList.isNotEmpty()) {
            //load more 并且有数据
            this.dataList.addAll(dataList)
            rv.onLoadSuccess(page)
            adapter.notifyDataSetChanged()
        } else {
            //load more 没数据
            rv.dismissProgressBar()
            rv.dismissLoading()
        }
    }
}
