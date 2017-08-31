package com.meishipintu.lll_office.views

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.meishipintu.lll_office.R
import com.meishipintu.lll_office.contract.OfficeDetailContract
import com.meishipintu.lll_office.modles.entities.JobInfo
import com.meishipintu.lll_office.modles.entities.OfficeInfo
import com.meishipintu.lll_office.presenters.OrganizaitonPresenter
import com.meishipintu.lll_office.views.adapters.JobAdapter

class OfficeDetailActivity : BasicActivity(),OfficeDetailContract.IView {

    val office: OfficeInfo by lazy { intent.getSerializableExtra("office") as OfficeInfo }
    val scrollView: ScrollView by lazy { findViewById(R.id.scroll) as ScrollView }

    val glide: RequestManager by lazy{ Glide.with(this)}
    val dataList = mutableListOf<JobInfo>()
    val adapter: JobAdapter by lazy{ JobAdapter(this, dataList,1,office.avatar?:"")}   //type=1 不显示上下线功能

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_office_detail)
        presenter = OrganizaitonPresenter(this)
        initTab()
        initUI()
        initList()
    }

    private fun initList() {
        val rv = findViewById(R.id.rv_job) as RecyclerView
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter
        (presenter as OfficeDetailContract.IPresenter).getOrganizationPosition(office.uid)
    }

    private fun initTab() {
        val tabLayout = findViewById(R.id.tabLayout) as TabLayout
        tabLayout.addTab(tabLayout.newTab().setCustomView(R.layout.item_tab_2))
        tabLayout.addTab(tabLayout.newTab().setCustomView(R.layout.item_tab_2))
        (tabLayout.getTabAt(0)?.customView?.findViewById(R.id.tv_tab)as TextView).text = "机构概况"
        (tabLayout.getTabAt(1)?.customView?.findViewById(R.id.tv_tab)as TextView).text = "热招职位"
        tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab?.position == 0) {
                    scrollView.visibility = View.VISIBLE
                } else {
                    scrollView.visibility = View.GONE
                }
            }
        })
    }

    private fun initUI() {
        val ivBig = findViewById(R.id.iv_big) as ImageView
        glide.load(office.avatar).error(R.drawable.organization_default).into(ivBig)
        val ivHead = findViewById(R.id.iv_head) as ImageView
        glide.load(office.avatar).error(R.drawable.organization_default).into(ivHead)
        val tvName = findViewById(R.id.office_name) as TextView
        tvName.text = office.name
        val tvAddress = findViewById(R.id.tv_address) as TextView
        tvAddress.text = office.address
        val tvDesc = findViewById(R.id.tv_decs) as TextView
        tvDesc.text = "热招  职位"
        val tvIntroduce = findViewById(R.id.tv_introduce) as TextView
        tvIntroduce.text = office.introduce_detail
        findViewById(R.id.bt_back).setOnClickListener{ onBackPressed()}
        scrollView.visibility = View.VISIBLE
    }

    //from OfficeDetailContract.IView
    override fun onPositionGet(data: List<JobInfo>) {
        dataList.clear()
        dataList.addAll(data)
        adapter.notifyDataSetChanged()
    }

    //from OfficeDetailContract.IView
    override fun onError(e: String) {
        toast(e)
    }
}
