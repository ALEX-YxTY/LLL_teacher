package com.milai.lll_teacher.views

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import com.milai.lll_teacher.R
import com.milai.lll_teacher.models.entities.JobInfo
import com.milai.lll_teacher.models.entities.OfficeInfo
import com.milai.lll_teacher.views.adapters.JobAdapter
import com.milai.lll_teacher.views.adapters.OfficeAdapter

/**
 * 收藏的职位、关注的机构页面
 */
class CollectionActivity : AppCompatActivity() {

    var from = 1    //1-收藏的职位，2-关注的机构

    val rv:RecyclerView by lazy { findViewById(R.id.rv) as RecyclerView }

    val jobList:List<JobInfo> = mutableListOf()
    val officeList:List<OfficeInfo> = mutableListOf()
    val jobAdapter:JobAdapter by lazy { JobAdapter(this,jobList,1) }
    val officeAdapter:OfficeAdapter by lazy { OfficeAdapter(this,officeList) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collection)
        from = intent.getIntExtra("from", 1)
        val title = findViewById(R.id.tv_title) as TextView
        title.text = if(from==1) "收藏的职位" else "关注的机构"

    }
}
