package com.meishipintu.lll_office.views

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.format.DateUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.meishipintu.lll_office.R
import com.meishipintu.lll_office.customs.utils.DateUtil
import com.meishipintu.lll_office.modles.entities.JobInfo

class JobDetailActivity : AppCompatActivity(),View.OnClickListener{

    val jobInfo:JobInfo by lazy { intent.getSerializableExtra("job") as JobInfo }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_detail)

        initUI()
    }

    private fun initUI() {
        val tvTitle = findViewById(R.id.tv_title) as TextView
        tvTitle.text = "职位详情"
        val tvStatusChange = findViewById(R.id.tv_offline) as TextView
        tvStatusChange.text=if(jobInfo.status==1) "下线" else "上线"
        tvStatusChange.setOnClickListener(this)
        findViewById(R.id.bt_back).setOnClickListener(this)
        val btInvite = findViewById(R.id.bt_invite)
        btInvite.setOnClickListener(this)
        btInvite.visibility = if (jobInfo.status > 1) View.GONE else View.VISIBLE

        (findViewById(R.id.job_name) as TextView).text = jobInfo.job_name
        (findViewById(R.id.tv_course_grade) as TextView).text = "${jobInfo.grade} ${jobInfo.course}"
        (findViewById(R.id.tv_sex) as TextView).text = "${jobInfo.sex}"
        (findViewById(R.id.tv_experience) as TextView).text = "${jobInfo.require_year}"
        val tvCertificate = findViewById(R.id.tv_certificate) as TextView
        tvCertificate.visibility = if(jobInfo.have_certificate==0) View.GONE else View.VISIBLE
        (findViewById(R.id.tv_money) as TextView).text = jobInfo.money
        (findViewById(R.id.tv_time) as TextView).text = DateUtil.stampToDate(jobInfo.create_time)

        (findViewById(R.id.tv_address_detail) as TextView).text = "南京市${jobInfo.work_area}${jobInfo.work_address}"
        (findViewById(R.id.tv_job_detail) as TextView).text = jobInfo.require
        if (jobInfo.other_demand.isNullOrEmpty()) {
            findViewById(R.id.rl_other).visibility = View.GONE
        } else {
            (findViewById(R.id.tv_other_demand)as TextView).text = jobInfo.other_demand
        }

        (findViewById(R.id.office_name) as TextView).text = jobInfo.organization.name
        (findViewById(R.id.tv_address) as TextView).text = jobInfo.organization.address
        (findViewById(R.id.tv_decs) as TextView).text = "热招 "
        val officeHead = findViewById(R.id.iv_head) as ImageView
        Glide.with(this).load(jobInfo.organization.avatar).into(officeHead)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.bt_back -> onBackPressed()
            R.id.bt_invite ->{
                val intent = Intent(this, InviteInterviewActivity::class.java)
                intent.putExtra("jobId", jobInfo.id)
                startActivity(intent)
            }
            R.id.tv_offline ->{
                //TODO 职位下线
            }
        }
    }
}
