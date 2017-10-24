package com.milai.lll_teacher.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
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
import com.umeng.socialize.ShareAction
import com.umeng.socialize.UMShareAPI
import com.umeng.socialize.UMShareListener
import com.umeng.socialize.bean.SHARE_MEDIA
import com.umeng.socialize.media.UMImage
import com.umeng.socialize.media.UMWeb

class JobDetailActivity : BasicActivity() ,View.OnClickListener,JobDetailContact.IView{

    val ivShare:ImageView by lazy { findViewById(R.id.iv_share) as ImageView }
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
    val oid:String by lazy { intent.getStringExtra("oid")}
    //from 1:默认情况，2：隐藏投递简历， 3：从消息进入
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
    val courses = Cookies.getConstant(2)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_detail)
        presenter = JobPresenter(this)
        //添加统计，判断收藏，获取详情
        (presenter as JobDetailContact.IPresenter).addStatistic(MyApplication.userInfo?.uid!!, jobId)
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
        ivShare.visibility = View.VISIBLE
        ivShare.setOnClickListener(this)
        star.setOnClickListener(this)
        when (from) {
            2 -> findViewById(R.id.bt_append).visibility = View.GONE
            3 -> {
                findViewById(R.id.bt_append).visibility = View.GONE
                findViewById(R.id.bt_contact).visibility = View.VISIBLE
            }
            else -> //判断是否已经投递
                (presenter as JobPresenter).isJobDeliver(jobId, MyApplication.userInfo?.uid!!)
        }
    }

    override fun onJobDeliver(isDeliver: Boolean) {
            findViewById(R.id.bt_append).visibility = if(isDeliver) View.GONE else View.VISIBLE
            findViewById(R.id.bt_contact).visibility = if(!isDeliver) View.GONE else View.VISIBLE
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
            R.id.iv_share -> {
                val umWeb = UMWeb("http://lll.domobile.net/Home/Index/pstinfo?id=$jobId" +
                        "&actionId=${MyApplication.userInfo?.uid}&type=5&flag=1")
                umWeb.title = "${officeInfo?.name ?: "这家机构"}正在招聘${if (jobInfo!!.course != 0) courses[jobInfo!!.course] else ""}老师，海量职位尽在拉力郎共享师资"

                umWeb.description = "拉力郎师资"
                umWeb.setThumb(UMImage(this,R.mipmap.teacher_share))
                ShareAction(this@JobDetailActivity)
                        .setDisplayList(SHARE_MEDIA.WEIXIN,SHARE_MEDIA.WEIXIN_CIRCLE)
                        .withMedia(umWeb)
                        .setCallback(umShareListener).open()
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
        findViewById(R.id.bt_append).visibility =  View.GONE
        findViewById(R.id.bt_contact).visibility = View.VISIBLE
    }

    //from JobDetailContract.IView
    override fun onJobDetailGet(jobInf: JobInfo) {
        this.jobInfo = jobInf
        val sexs = arrayOf("性别无要求", "男", "女")
        val experiences = Cookies.getConstant(5)
        val areas = Cookies.getConstant(1)
        val grades = Cookies.getConstant(3)
        val gradeDetail = Cookies.getConstant(11)
        (findViewById(R.id.job_name) as TextView).text = jobInfo?.job_name
        (findViewById(R.id.tv_course_grade) as TextView).text = "${grades[jobInfo!!.grade]} " +
                "${gradeDetail[jobInfo?.grade_detail?:0]}" +
                " ${courses[jobInfo!!.course]}"
        (findViewById(R.id.tv_sex) as TextView).text = "${sexs[jobInfo!!.sex]}"
        (findViewById(R.id.tv_experience) as TextView).text = "教学经验${experiences[jobInfo!!.require_year]}"
        val tvCertificate = findViewById(R.id.tv_certificate) as TextView
        tvCertificate.visibility = if(jobInfo!!.have_certificate==0) View.GONE else View.VISIBLE
        (findViewById(R.id.tv_money) as TextView).text = jobInfo?.money
        (findViewById(R.id.tv_time) as TextView).text = DateUtil.stampToDate(jobInfo!!.create_time)
        var reviewTime = if (jobInfo?.ll_count!! > 9999) {
            "${jobInfo?.ll_count!! / 10000} 万次"
        } else {
            "${jobInfo?.ll_count!! } 次"
        }
        (findViewById(R.id.tv_review_time) as TextView).text = reviewTime
        var area:String
        area = if(jobInfo?.work_area==0) {
            "${areas[jobInfo!!.work_area]} ${jobInfo!!.work_address}"
        } else {
            "南京市${areas[jobInfo!!.work_area]} ${jobInfo!!.work_address}"
        }
        (findViewById(R.id.tv_address_detail) as TextView).text = area
        (findViewById(R.id.tv_job_detail) as TextView).text = jobInfo!!.require
        if (jobInfo!!.other_demand.isNullOrEmpty()) {
            findViewById(R.id.rl_other).visibility = View.GONE
        } else {
            findViewById(R.id.rl_other).visibility = View.VISIBLE
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data)
    }
}
