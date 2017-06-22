package com.milai.lll_teacher.custom.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.milai.lll_teacher.R
import com.milai.lll_teacher.models.entities.JobInfo

class JobDetailActivity : AppCompatActivity() ,View.OnClickListener{

    var job: JobInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_detail2)
        job = intent.getSerializableExtra("job") as JobInfo
        initUI()
    }

    private fun initUI() {
        val title = findViewById(R.id.tv_title) as TextView
        title.text = "职位详情"
        findViewById(R.id.bt_back).setOnClickListener(this)
        findViewById(R.id.bt_collect).setOnClickListener(this)
        findViewById(R.id.bt_contact).setOnClickListener(this)
        findViewById(R.id.bt_join).setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.bt_back -> onBackPressed()
            R.id.bt_collect ->{}
            R.id.bt_contact ->{}
            R.id.bt_join ->{}
        }
    }

}
