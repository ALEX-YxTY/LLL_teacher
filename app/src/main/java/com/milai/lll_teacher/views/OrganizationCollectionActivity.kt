package com.milai.lll_teacher.views

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import com.milai.lll_teacher.MyApplication
import com.milai.lll_teacher.R
import com.milai.lll_teacher.contracts.OrganizationCollectionContract
import com.milai.lll_teacher.models.entities.OfficeInfo
import com.milai.lll_teacher.presenters.OfficePresenter
import com.milai.lll_teacher.views.adapters.OfficeAdapter

/**
 * 收藏机构页面
 */

class OrganizationCollectionActivity : BasicActivity(), OrganizationCollectionContract.IView {

    val officeList = mutableListOf<OfficeInfo>()
    val officeAdapter: OfficeAdapter by lazy { OfficeAdapter(this,officeList) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collection)
        presenter = OfficePresenter(this)
        val title = findViewById(R.id.tv_title) as TextView
        title.text = "收藏的职位"
        findViewById(R.id.bt_back).setOnClickListener{ onBackPressed() }
        val rv = findViewById(R.id.rv) as RecyclerView
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = officeAdapter
        (presenter as OrganizationCollectionContract.IPresenter).getOrganizationCollection(MyApplication.userInfo?.uid!!)
    }

    //from OrganizationCollectionContract.IView
    override fun showError(err: String) {
        toast(err)
    }

    //from OrganizationCollectionContract.IView
    override fun onOrganizationCollectionGet(dataList: List<OfficeInfo>) {
        this.officeList.clear()
        this.officeList.addAll(dataList)
        officeAdapter.notifyDataSetChanged()
    }
}
