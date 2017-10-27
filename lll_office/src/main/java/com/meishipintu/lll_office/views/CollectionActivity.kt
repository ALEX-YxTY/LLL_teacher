package com.meishipintu.lll_office.views

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import com.meishipintu.lll_office.OfficeApplication
import com.meishipintu.lll_office.R
import com.meishipintu.lll_office.contract.TeacherCollectionContract
import com.meishipintu.lll_office.customs.CanLoadMoreRecyclerView
import com.meishipintu.lll_office.modles.entities.OfficeInfo
import com.meishipintu.lll_office.modles.entities.TeacherInfo
import com.meishipintu.lll_office.presenters.TeachPresenter
import com.meishipintu.lll_office.views.adapters.TeacherAdapter
import com.milai.lll_teacher.views.adapters.OfficeAdapter

class CollectionActivity : BasicActivity(),TeacherCollectionContract.IView,CanLoadMoreRecyclerView.StateChangedListener {

    val teacherList= mutableListOf<TeacherInfo>()
    val teacherAdapter:TeacherAdapter by lazy { TeacherAdapter(this, teacherList, 1, 2, -1) }
    val rv:CanLoadMoreRecyclerView by lazy{findViewById(R.id.rv) as CanLoadMoreRecyclerView }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collection)
        presenter = TeachPresenter(this)
        val tvTitle = findViewById(R.id.tv_title) as TextView
        tvTitle.text = "我收藏的老师"
        findViewById(R.id.bt_back).setOnClickListener{ onBackPressed() }
        rv.listener = this
    }

    override fun onResume() {
        super.onResume()
        rv.setAdapter(teacherAdapter)
    }

    //from TeacherCollectionContract.IView
    override fun onError(e: String) {
        toast(e)
    }

    override fun onLoadError() {
        rv.dismissProgressBar()
        rv.dismissLoading()
    }

    override fun onLoadMore(page: Int) {
        (presenter as TeachPresenter).getTeacherCollectiion(OfficeApplication.userInfo?.uid!!,page)
    }

    //from TeacherCollectionContract.IView
    override fun onTeacherCollectionGet(dataList: List<TeacherInfo>,page:Int) {
        if (page == 1) {
            //首次加载
            this.teacherList.clear()
            this.teacherList.addAll(dataList)
            rv.onLoadSuccess(page)
            teacherAdapter.notifyDataSetChanged()
        }else if (dataList.isNotEmpty()) {
            //load more 并且有数据
            this.teacherList.addAll(dataList)
            rv.onLoadSuccess(page)
            teacherAdapter.notifyDataSetChanged()
        } else {
            //load more 没数据
            rv.dismissProgressBar()
            rv.dismissLoading()
        }
    }
}
