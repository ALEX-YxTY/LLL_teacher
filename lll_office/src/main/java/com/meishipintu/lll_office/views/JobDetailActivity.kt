package com.meishipintu.lll_office.views

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.meishipintu.lll_office.Cookies
import com.meishipintu.lll_office.OfficeApplication
import com.meishipintu.lll_office.R
import com.meishipintu.lll_office.contract.JobDetailContract
import com.meishipintu.lll_office.customs.utils.DateUtil
import com.meishipintu.lll_office.modles.entities.JobInfo
import com.meishipintu.lll_office.modles.entities.UserInfo
import com.meishipintu.lll_office.presenters.JobManagerPresenter

class JobDetailActivity : BasicActivity(),View.OnClickListener,JobDetailContract.IView{

    val jobInfo:JobInfo by lazy { intent.getSerializableExtra("job") as JobInfo }
    val officeInfo: UserInfo? = OfficeApplication.userInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_detail)
        presenter = JobManagerPresenter(this)
        initUI()
    }

    private fun initUI() {
        val grades = Cookies.getConstant(3)
        val courses = Cookies.getConstant(2)
        val sexs = arrayOf("性别无要求", "男", "女")
        val experiences = Cookies.getConstant(5)
        val areas = Cookies.getConstant(1)


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
        (findViewById(R.id.tv_course_grade) as TextView).text = "${grades[jobInfo.grade]} ${courses[jobInfo.course]}"
        (findViewById(R.id.tv_sex) as TextView).text = "${sexs[jobInfo.sex]}"
        (findViewById(R.id.tv_experience) as TextView).text = "${experiences[jobInfo.require_year]}"
        val tvCertificate = findViewById(R.id.tv_certificate) as TextView
        tvCertificate.visibility = if(jobInfo.have_certificate==0) View.GONE else View.VISIBLE
        (findViewById(R.id.tv_money) as TextView).text = jobInfo.money
        (findViewById(R.id.tv_time) as TextView).text = DateUtil.stampToDate(jobInfo.create_time)

        var area:String
        if(jobInfo.work_area==0) {area = "${areas[jobInfo.work_area]} ${jobInfo.work_address}"} else {
            area = "南京市${jobInfo.work_area} ${jobInfo.work_address}"
        }
        (findViewById(R.id.tv_address_detail) as TextView).text = area
        (findViewById(R.id.tv_job_detail) as TextView).text = jobInfo.require
        if (jobInfo.other_demand.isNullOrEmpty()) {
            findViewById(R.id.rl_other).visibility = View.GONE
        } else {
            (findViewById(R.id.tv_other_demand)as TextView).text = jobInfo.other_demand
        }

        (findViewById(R.id.office_name) as TextView).text = officeInfo?.name
        (findViewById(R.id.tv_address) as TextView).text = officeInfo?.address
        (findViewById(R.id.tv_decs) as TextView).text = "热招 "
        val officeHead = findViewById(R.id.iv_head) as ImageView
        Glide.with(this).load(officeInfo?.avatar).into(officeHead)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.bt_back -> onBackPressed()
            R.id.bt_invite ->{
                //TODO 去邀请
            }
            R.id.tv_offline ->{
                (presenter as JobManagerPresenter).changeStatus(jobInfo.id.toString(),if(jobInfo.status==1) 2 else 1)
            }
        }
    }

    override fun onError(e: String) {
        toast(e)
    }

    override fun onStatusChanged(statusNew: Int) {
        if (statusNew == 1) {
            toast("该任务已上线")
        } else {
            toast("该任务已下线")
        }
        setResult(Activity.RESULT_OK)
        this.finish()

    }
}
