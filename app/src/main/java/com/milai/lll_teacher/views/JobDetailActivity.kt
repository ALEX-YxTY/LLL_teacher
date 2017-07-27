package com.milai.lll_teacher.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.milai.lll_teacher.Cookies
import com.milai.lll_teacher.MyApplication
import com.milai.lll_teacher.R
import com.milai.lll_teacher.contracts.JobDetailContact
import com.milai.lll_teacher.custom.util.DateUtil
import com.milai.lll_teacher.models.entities.JobInfo
import com.milai.lll_teacher.models.entities.OfficeInfo
import com.milai.lll_teacher.presenters.JobPresenter

class JobDetailActivity : BasicActivity() ,View.OnClickListener,JobDetailContact.IView{

    val jobId:Int by lazy { intent.getIntExtra("jobId", 0)}
    val oid:String by lazy { intent.getStringExtra("oid")}
    //from 1:默认情况，2：隐藏投递简历
    val from:Int by lazy{ intent.getIntExtra("from", 1)}
    /**
     * type：定义职位详情页面从哪里进入，从总职位列表进入，会自带机构信息，type=1
     *          从机构发布的职位进入，不再带机构信息，避免陷入循环，type=2
     */
    val type:Int by lazy { intent.getIntExtra("type", 2) }
    val star:ImageView by lazy { findViewById(R.id.bt_collect) as ImageView }
    var jobInfo: JobInfo? = null
    var officeInfo: OfficeInfo? = null

    val glide:RequestManager by lazy{ Glide.with(this) }
    var isCollect = false       //标记是否已收藏

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_detail)
        presenter = JobPresenter(this)
        (presenter as JobDetailContact.IPresenter).isJobCollect(jobId, MyApplication.userInfo?.uid!!)
        (presenter as JobDetailContact.IPresenter).getJobDetail(jobId)
        if (type != 2) {
            (presenter as JobDetailContact.IPresenter).getOfficeDetail(oid)
        } else {
            findViewById(R.id.include_office).visibility = View.GONE
        }
        initUI()
    }

    private fun initUI() {
        val title = findViewById(R.id.tv_title) as TextView
        title.text = "职位详情"
        findViewById(R.id.bt_back).setOnClickListener(this)
        findViewById(R.id.bt_contact).setOnClickListener(this)
        findViewById(R.id.bt_append).setOnClickListener(this)
        findViewById(R.id.include_office).setOnClickListener(this)
        star.setOnClickListener(this)
        if (from == 2) {
            findViewById(R.id.bt_append).visibility = View.GONE
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.bt_back -> onBackPressed()
            R.id.bt_collect ->{
                //添加删除收藏
                (presenter as JobDetailContact.IPresenter).addJobCollect(jobInfo!!.id, isCollect, MyApplication.userInfo?.uid!!)
            }
            R.id.bt_contact ->{
                //进入沟通页
                val intent = Intent(this, ChatDetailActivity::class.java)
                intent.putExtra("jobId", jobInfo!!.id)
                intent.putExtra("oid", jobInfo!!.oid)
                intent.putExtra("teacher", MyApplication.userInfo?.uid)
                startActivity(intent)
            }
            R.id.bt_append ->{
                //投递简历
                (presenter as JobDetailContact.IPresenter).sendResume(jobInfo!!.id, MyApplication.userInfo?.uid!!, jobInfo!!.oid)
            }
            R.id.include_office ->{
                //进入机构详情页面
                if (this.officeInfo != null) {
                    val intent = Intent(this, OfficeDetailActivity::class.java)
                    intent.putExtra("office", this.officeInfo)
                    startActivity(intent)
                }
            }
        }
    }

    //from JobDetailContract.IView
    override fun showError(err: String) {
        toast(err)
    }

    //from JobDetailContract.IView
    override fun isJobCollected(isCollected: Boolean) {
        Log.d("test", "is the job collected $isCollected")
        this.isCollect = isCollected
        if (isCollected) {
            glide.load(R.drawable.icon_star_fill).into(star)
        } else {
            glide.load(R.drawable.icon_star_unfill).into(star)
        }
    }

    //from JobDetailContract.IView
    override fun onJobCollected(isCollected: Boolean) {
        if (isCollected) {
            isCollect = true
            toast("添加收藏成功")
            glide.load(R.drawable.icon_star_fill).into(star)
        } else {
            isCollect = false
            toast("取消收藏成功")
            glide.load(R.drawable.icon_star_unfill).into(star)
        }
    }

    //from JobDetailContract.IView
    override fun onResumeSendSuccess() {
        toast("简历投递成功！")
    }

    //from JobDetailContract.IView
    override fun onJobDetailGet(jobInf: JobInfo) {
        this.jobInfo = jobInf
        val grades = Cookies.getConstant(3)
        val courses = Cookies.getConstant(2)
        val sexs = arrayOf("性别无要求", "男", "女")
        val experiences = Cookies.getConstant(5)
        val areas = Cookies.getConstant(1)


        (findViewById(R.id.job_name) as TextView).text = jobInfo?.job_name
        (findViewById(R.id.tv_course_grade) as TextView).text = "${grades[jobInfo!!.grade]}" +
                " ${courses[jobInfo!!.course]}"
        (findViewById(R.id.tv_sex) as TextView).text = "${sexs[jobInfo!!.sex]}"
        (findViewById(R.id.tv_experience) as TextView).text = "${experiences[jobInfo!!.require_year]}"
        val tvCertificate = findViewById(R.id.tv_certificate) as TextView
        tvCertificate.visibility = if(jobInfo!!.have_certificate==0) View.GONE else View.VISIBLE
        (findViewById(R.id.tv_money) as TextView).text = jobInfo?.money
        (findViewById(R.id.tv_time) as TextView).text = DateUtil.stampToDate(jobInfo!!.create_time)

        var area:String
        if(jobInfo?.work_area==0) {area = "${areas[jobInfo!!.work_area]} ${jobInfo!!.work_address}"} else {
            area = "南京市${jobInfo!!.work_area} ${jobInfo!!.work_address}"
        }
        (findViewById(R.id.tv_address_detail) as TextView).text = area
        (findViewById(R.id.tv_job_detail) as TextView).text = jobInfo!!.require
        if (jobInfo!!.other_demand.isNullOrEmpty()) {
            findViewById(R.id.rl_other).visibility = View.GONE
        } else {
            (findViewById(R.id.tv_other_demand)as TextView).text = jobInfo!!.other_demand
        }
        (presenter as JobDetailContact.IPresenter).getOfficeDetail(jobInf.oid)

    }

    //from JobContract.IView
    override fun onOfficeInfoGet(officeInfo: OfficeInfo) {
        this.officeInfo = officeInfo
        (findViewById(R.id.office_name) as TextView).text = this.officeInfo?.name
        (findViewById(R.id.tv_address) as TextView).text = this.officeInfo?.address
        if (this.officeInfo?.postion != null) {
            (findViewById(R.id.tv_decs) as TextView).text = "热招：  ${this.officeInfo?.postion?.job_name}" +
                    "  等${this.officeInfo?.count}个职位"
        } else {
            (findViewById(R.id.tv_decs) as TextView).text = "暂无职位招聘"
        }
        val officeHead = findViewById(R.id.iv_head) as ImageView
        Glide.with(this).load(this.officeInfo?.avatar).error(R.drawable.organization_default).into(officeHead)
    }
}
