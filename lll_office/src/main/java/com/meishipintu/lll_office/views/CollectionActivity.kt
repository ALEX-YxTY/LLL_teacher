package com.meishipintu.lll_office.views

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.meishipintu.lll_office.R
import com.meishipintu.lll_office.modles.entities.OfficeInfo
import com.meishipintu.lll_office.modles.entities.TeacherInfo
import com.meishipintu.lll_office.views.adapters.TeacherAdapter
import com.milai.lll_teacher.views.adapters.OfficeAdapter

class CollectionActivity : AppCompatActivity() {

    val from:Int by lazy { intent.getIntExtra("type", 1) }  //1-机构收藏  2-教师收藏
    val teacherList:List<TeacherInfo> = mutableListOf()
    val officeList:List<OfficeInfo> = mutableListOf()
    val jobAdapter:TeacherAdapter by lazy { TeacherAdapter(this,teacherList) }
    val officeAdapter:OfficeAdapter by lazy { OfficeAdapter(this,officeList) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collection)

        val tvTitle = findViewById(R.id.tv_title) as TextView
        tvTitle.text = if (from == 1) "其他机构" else "我收藏的老师"
        findViewById(R.id.bt_back).setOnClickListener{ onBackPressed() }

    }
}
