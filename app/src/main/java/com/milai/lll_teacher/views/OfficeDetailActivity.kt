package com.milai.lll_teacher.views

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
import com.milai.lll_teacher.MyApplication
import com.milai.lll_teacher.R
import com.milai.lll_teacher.contracts.OfficeDetailContract
import com.milai.lll_teacher.models.entities.JobInfo
import com.milai.lll_teacher.models.entities.OfficeInfo
import com.milai.lll_teacher.presenters.OfficePresenter
import com.milai.lll_teacher.views.adapters.JobAdapter

class OfficeDetailActivity : BasicActivity(),OfficeDetailContract.IView {


    val office:OfficeInfo by lazy { intent.getSerializableExtra("office") as OfficeInfo }
    val scrollView: ScrollView by lazy { findViewById(R.id.scroll) as ScrollView}
    val star:ImageView by lazy { findViewById(R.id.bt_collect) as ImageView }

    val glide: RequestManager by lazy{ Glide.with(this)}
    var isCollected = false   //标记是否已经收藏0
    val dataList = mutableListOf<JobInfo>()
    val adapter:JobAdapter by lazy{ JobAdapter(this, dataList,2)}   //type=2 job不包含机构信息

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_office_detail)
        presenter = OfficePresenter(this)
        (presenter as OfficeDetailContract.IPresenter).isOfficeCollect(office.uid, MyApplication.userInfo?.uid!!)
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
        tabLayout.addOnTabSelectedListener(object:TabLayout.OnTabSelectedListener{

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
        val ivHead = findViewById(R.id.iv_head) as ImageView
        val tvName = findViewById(R.id.office_name) as TextView
        tvName.text = office.name
        val tvAddress = findViewById(R.id.tv_address) as TextView
        tvAddress.text = office.address
        val tvDesc = findViewById(R.id.tv_decs) as TextView
        tvDesc.text = "热招  职位"
        val tvIntroduce = findViewById(R.id.tv_introduce) as TextView
        tvIntroduce.text = office.introduce_detail
        star.setOnClickListener{
            (presenter as OfficeDetailContract.IPresenter).addOrganizationCollection(office.uid
                    , MyApplication.userInfo?.uid!!, isCollected)
        }
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
    override fun showError(err: String) {
        toast(err)
    }

    //from OfficeDetailContract.IView
    override fun isOfficeCollected(isCollect: Boolean) {
        this.isCollected = isCollect
        if (isCollect) {
            glide.load(R.drawable.icon_star_fill).into(star)
        }
    }

    //from OfficeDetailContract.IView
    override fun onOfficeCollectResult(isAdd: Boolean) {
        if (isAdd) {
            toast("添加收藏成功")
            isCollected = true
            glide.load(R.drawable.icon_star_fill).into(star)
        } else {
            toast("取消收藏成功")
            isCollected = false
            glide.load(R.drawable.icon_star_unfill).into(star)
        }
    }

}
