package com.meishipintu.lll_office.views

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import com.meishipintu.lll_office.OfficeApplication
import com.meishipintu.lll_office.R
import com.meishipintu.lll_office.contract.OtherOrganizationContract
import com.meishipintu.lll_office.modles.entities.OfficeInfo
import com.meishipintu.lll_office.presenters.OrganizaitonPresenter
import com.milai.lll_teacher.views.adapters.OfficeAdapter

class OtherOrganizationActivity : BasicActivity(),OtherOrganizationContract.IView {

    val dataList = mutableListOf<OfficeInfo>()
    val adapter:OfficeAdapter by lazy{ OfficeAdapter(this, dataList)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collection)
        presenter = OrganizaitonPresenter(this)
        val tvTitle = findViewById(R.id.tv_title) as TextView
        tvTitle.text = "其他机构"
        findViewById(R.id.bt_back).setOnClickListener{ onBackPressed()}
        val rv = findViewById(R.id.rv) as RecyclerView
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = this.adapter
        (presenter as OtherOrganizationContract.IPresenter).getOrganization(OfficeApplication.userInfo?.uid!!)
    }

    //from OtherOrganizationContract.IView
    override fun onError(e: String) {
        toast(e)
    }

    //from OtherOrganizationContract.IView
    override fun onOrganizationGet(dataList: List<OfficeInfo>) {
        this.dataList.clear()
        this.dataList.addAll(dataList)
        adapter.notifyDataSetChanged()
    }

}
