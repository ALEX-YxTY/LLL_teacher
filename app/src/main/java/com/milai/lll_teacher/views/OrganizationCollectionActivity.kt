package com.milai.lll_teacher.views

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import com.milai.lll_teacher.MyApplication
import com.milai.lll_teacher.R
import com.milai.lll_teacher.contracts.OrganizationCollectionContract
import com.milai.lll_teacher.custom.view.CanLoadMoreRecyclerView
import com.milai.lll_teacher.models.entities.OfficeInfo
import com.milai.lll_teacher.presenters.OfficePresenter
import com.milai.lll_teacher.views.adapters.OfficeAdapter

/**
 * 收藏机构页面
 */

class OrganizationCollectionActivity : BasicActivity(), OrganizationCollectionContract.IView
        ,CanLoadMoreRecyclerView.StateChangedListener {

    val officeList = mutableListOf<OfficeInfo>()
    val officeAdapter: OfficeAdapter by lazy { OfficeAdapter(this,officeList) }
    val rv:CanLoadMoreRecyclerView by lazy{ findViewById(R.id.rv) as CanLoadMoreRecyclerView }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collection)
        presenter = OfficePresenter(this)
        val title = findViewById(R.id.tv_title) as TextView
        title.text = "收藏的机构"
        findViewById(R.id.bt_back).setOnClickListener{ onBackPressed() }
        rv.listener = this
    }

    override fun onResume() {
        super.onResume()
        rv.setAdapter(officeAdapter)
    }

    //from OrganizationCollectionContract.IView
    override fun showError(err: String) {
        toast(err)
    }

    override fun onLoadError() {
        rv.dismissProgressBar()
        rv.dismissLoading()
    }

    override fun onLoadMore(page: Int) {
        (presenter as OrganizationCollectionContract.IPresenter)
                .getOrganizationCollection(MyApplication.userInfo?.uid!!, page)
    }

    //from OrganizationCollectionContract.IView
    override fun onOrganizationCollectionGet(dataList: List<OfficeInfo>, page: Int) {
        if (page == 1) {
            //首次加载
            this.officeList.clear()
            this.officeList.addAll(dataList)
            rv.onLoadSuccess(page)
            officeAdapter.notifyDataSetChanged()
        }else if (dataList.isNotEmpty()) {
            //load more 并且有数据
            this.officeList.addAll(dataList)
            rv.onLoadSuccess(page)
            officeAdapter.notifyDataSetChanged()
        } else {
            //load more 没数据
            rv.dismissProgressBar()
            rv.dismissLoading()
        }
    }
}
