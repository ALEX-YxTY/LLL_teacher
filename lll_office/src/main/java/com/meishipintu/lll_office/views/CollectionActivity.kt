package com.meishipintu.lll_office.views

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import com.meishipintu.lll_office.OfficeApplication
import com.meishipintu.lll_office.R
import com.meishipintu.lll_office.contract.TeacherCollectionContract
import com.meishipintu.lll_office.modles.entities.OfficeInfo
import com.meishipintu.lll_office.modles.entities.TeacherInfo
import com.meishipintu.lll_office.presenters.TeachPresenter
import com.meishipintu.lll_office.views.adapters.TeacherAdapter
import com.milai.lll_teacher.views.adapters.OfficeAdapter

class CollectionActivity : BasicActivity(),TeacherCollectionContract.IView {

    val teacherList= mutableListOf<TeacherInfo>()
    val teacherAdapter:TeacherAdapter by lazy { TeacherAdapter(this,teacherList,1) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collection)
        presenter = TeachPresenter(this)
        val tvTitle = findViewById(R.id.tv_title) as TextView
        tvTitle.text = "我收藏的老师"
        findViewById(R.id.bt_back).setOnClickListener{ onBackPressed() }
        val rv = findViewById(R.id.rv) as RecyclerView
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = teacherAdapter
    }

    override fun onResume() {
        super.onResume()
        (presenter as TeachPresenter).getTeacherCollectiion(OfficeApplication.userInfo?.uid!!)
    }

    //from TeacherCollectionContract.IView
    override fun onError(e: String) {
        toast(e)
    }

    //from TeacherCollectionContract.IView
    override fun onTeacherCollectionGet(dataList: List<TeacherInfo>) {
        this.teacherList.clear()
        teacherList.addAll(dataList)
        teacherAdapter.notifyDataSetChanged()
    }
}
