package com.meishipintu.lll_office.views

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.meishipintu.lll_office.Cookies
import com.meishipintu.lll_office.OfficeApplication
import com.meishipintu.lll_office.R
import com.meishipintu.lll_office.contract.JobDetailContract
import com.meishipintu.lll_office.customs.utils.DateUtil
import com.meishipintu.lll_office.modles.entities.JobInfo
import com.meishipintu.lll_office.presenters.JobManagerPresenter
import com.umeng.socialize.ShareAction
import com.umeng.socialize.UMShareAPI
import com.umeng.socialize.UMShareListener
import com.umeng.socialize.bean.SHARE_MEDIA
import com.umeng.socialize.media.UMImage
import com.umeng.socialize.media.UMWeb

class JobDetailActivity : BasicActivity(),View.OnClickListener,JobDetailContract.IView{

    val ivShare: ImageView by lazy { findViewById(R.id.iv_share) as ImageView }
    private val umShareListener: UMShareListener by lazy {object: UMShareListener {
        override fun onResult(p0: SHARE_MEDIA?) {
            Toast.makeText(this@JobDetailActivity, " 分享成功", Toast.LENGTH_SHORT).show()
        }

        override fun onCancel(p0: SHARE_MEDIA?) {
            Toast.makeText(this@JobDetailActivity,"分享被取消", Toast.LENGTH_SHORT).show()
        }

        override fun onError(p0: SHARE_MEDIA?, p1: Throwable?) {
            Toast.makeText(this@JobDetailActivity,"分享发生错误", Toast.LENGTH_SHORT).show()
            if (p1 != null) {
                Log.d("MyResumeActivity", "share error: ${p1.message}")
            }
        }

        override fun onStart(p0: SHARE_MEDIA?) {
        }

    }}

    val jobId:Int by lazy { intent.getIntExtra("jobId", 0)}
    /**
     * type =1 不显示上下线和邀请功能，type=2 显示上下线功能
     */
    val type: Int by lazy { intent.getIntExtra("type", 1) }
    var status =1 //1-在线 2-下线
    var jobInfo:JobInfo? = null
    val courses = Cookies.getConstant(2)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_detail)
        presenter = JobManagerPresenter(this)
        initUI()
    }

    private fun initUI() {
        val tvTitle = findViewById(R.id.tv_title) as TextView
        tvTitle.text = "职位详情"
        ivShare.setOnClickListener(this)
        findViewById(R.id.bt_back).setOnClickListener(this)
        findViewById(R.id.include_office).visibility=View.GONE
        if (type == 1) {
            findViewById(R.id.iv_share).visibility = View.GONE
        }
        (presenter as JobDetailContract.IPresenter).getJobDetail(jobId)
    }

    //from JobDetailContract.IView
    override fun onJobDetailGet(jobInfo: JobInfo) {
        this.status = jobInfo.status
        this.jobInfo = jobInfo
        val sexs = arrayOf("性别无要求", "男", "女")
        val experiences = Cookies.getConstant(5)
        val areas = Cookies.getConstant(1)
        val grades = Cookies.getConstant(3)
        val gradeDetail = Cookies.getConstant(11)

        val tvStatusChange = findViewById(R.id.tv_offline) as TextView
        val btInvite = findViewById(R.id.bt_invite)
        if (type == 1) {
            tvStatusChange.visibility = View.GONE
            btInvite.visibility = View.GONE
        } else {
            tvStatusChange.text=if(jobInfo.status==1) "下线" else "上线"
            tvStatusChange.setOnClickListener(this)
            btInvite.setOnClickListener(this)
            btInvite.visibility = if (jobInfo.status > 1) View.GONE else View.VISIBLE
        }
        (findViewById(R.id.job_name) as TextView).text = jobInfo?.job_name
        (findViewById(R.id.tv_course_grade) as TextView).text = "${grades[jobInfo!!.grade]} " +
                "${gradeDetail[jobInfo?.grade_detail]}" +
                " ${courses[jobInfo!!.course]}"
        (findViewById(R.id.tv_sex) as TextView).text = "${sexs[jobInfo.sex]}"
        (findViewById(R.id.tv_experience) as TextView).text = "教学经验${experiences[jobInfo.require_year]}"
        val tvCertificate = findViewById(R.id.tv_certificate) as TextView
        tvCertificate.visibility = if(jobInfo.have_certificate==0) View.GONE else View.VISIBLE
        (findViewById(R.id.tv_money) as TextView).text = jobInfo.money
        (findViewById(R.id.tv_time) as TextView).text = DateUtil.stampToDate(jobInfo.create_time)

        var area:String
        area = if(jobInfo.work_area==0) {
            "${areas[jobInfo.work_area]} ${jobInfo.work_address}"
        } else {
            "南京市${jobInfo.work_area} ${jobInfo.work_address}"
        }
        (findViewById(R.id.tv_address_detail) as TextView).text = area
        (findViewById(R.id.tv_job_detail) as TextView).text = jobInfo.require
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.bt_back -> onBackPressed()
            R.id.bt_invite ->{
                //进入邀请界面
                val intent = Intent(this, InviteActivity::class.java)
                intent.putExtra("jobId", jobId)
                startActivity(intent)
            }
            R.id.tv_offline ->{
                (presenter as JobManagerPresenter).changeStatus(jobId.toString(),if(status==1) 2 else 1)
            }
            R.id.iv_share ->{
                val umWeb = UMWeb("http://lll.domobile.net/Home/Index/pstinfo?id=$jobId" +
                        "&actionId=${OfficeApplication.userInfo?.uid}&type=8&flag=2")
                umWeb.title = "${OfficeApplication.userInfo?.name?:""}正在招聘${courses[jobInfo!!.course]}老师，海量职位尽在拉力郎共享师资。"
                umWeb.setThumb(UMImage(this,R.mipmap.office_share))
                ShareAction(this@JobDetailActivity).setDisplayList(SHARE_MEDIA.WEIXIN,SHARE_MEDIA.WEIXIN_CIRCLE)
                        .setCallback(umShareListener).withMedia(umWeb).open()
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data)
    }
}
