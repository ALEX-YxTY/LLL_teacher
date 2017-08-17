package com.meishipintu.lll_office.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.meishipintu.lll_office.Cookies
import com.meishipintu.lll_office.OfficeApplication
import com.meishipintu.lll_office.R
import com.meishipintu.lll_office.contract.ChatDetailContract
import com.meishipintu.lll_office.customs.CanLoadMoreRecyclerView
import com.meishipintu.lll_office.customs.utils.DateUtil
import com.meishipintu.lll_office.modles.entities.ChatDetail
import com.meishipintu.lll_office.modles.entities.JobInfo
import com.meishipintu.lll_office.modles.entities.TeacherInfo
import com.meishipintu.lll_office.presenters.MessagePresenter
import com.meishipintu.lll_office.views.adapters.ChatAdapter

class ChatDetailActivity : BasicActivity(), ChatDetailContract.IView{

    val oid:String by lazy{ intent.getStringExtra("oid")}
    val jobId: Int by lazy{ intent.getIntExtra("jobId", 0)}
    val teacherId:String by lazy{ intent.getStringExtra("teacher")}

    val data = mutableListOf<ChatDetail>()
    val adapter: ChatAdapter by lazy { ChatAdapter(this,data) }

    val et: EditText by lazy { findViewById(R.id.et_message) as EditText }
    val rlJob: RelativeLayout by lazy { findViewById(R.id.item_job) as RelativeLayout }
    val rv: RecyclerView by lazy{ findViewById(R.id.rv) as RecyclerView }
    val layoutmanager:RecyclerView.LayoutManager by lazy{ LinearLayoutManager(this)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_detail)
        presenter = MessagePresenter(this)
        (presenter as ChatDetailContract.IPresenter).getTeacherInfo(teacherId)
        (presenter as ChatDetailContract.IPresenter).getJobInfo(jobId)
        initList()
        initUI()
    }

    private fun initUI() {
        findViewById(R.id.bt_back).setOnClickListener{
            onBackPressed()
        }
        findViewById(R.id.tv_add_chat).setOnClickListener{
            if (et.text.toString().isNullOrEmpty()) {
                toast("输入内容不能为空")
            } else {
                (presenter as ChatDetailContract.IPresenter).sendChat(jobId, teacherId, oid, et.text.toString())
                et.setText("")
            }
        }
        rlJob.setOnClickListener{
            //跳转职位详情，不带机构信息
            val intent = Intent(this, JobDetailActivity::class.java)
            intent.putExtra("jobId", jobId)
            intent.putExtra("type",1)   //不显示上下线和邀请功能
            startActivity(intent)
        }
    }

    private fun initList() {
        rv.layoutManager = layoutmanager
        rv.adapter = adapter
        (presenter as ChatDetailContract.IPresenter).getChatList(teacherId, jobId)
    }

    //from ChatDetailContract.IView
    override fun onError(e: String) {
        toast(e)
    }

    //from ChatDetailContract.IView
    override fun onTeacherInfoGet(teacherInfo: TeacherInfo) {
        val title = findViewById(R.id.tv_title) as TextView
        title.text = teacherInfo.name
    }

    //from ChatDetailContract.IView
    override fun onChaListGet(dataList: List<ChatDetail>) {
        if (dataList.isNotEmpty()) {
            //有数据
            this.data.clear()
            this.data.addAll(dataList)
            adapter.notifyDataSetChanged()
            if (layoutmanager.itemCount > 0) {
                rv.smoothScrollToPosition(layoutmanager.itemCount - 1)
            }
        }
    }

    //from ChatDetailContract.IView
    override fun onSendChatSuccess() {
        et.clearFocus()
        changSoftInputWindow(true)
        (presenter as ChatDetailContract.IPresenter).getChatList(teacherId, jobId)
    }

    //from ChatDetailContract.IView
    override fun onJobInfoGet(jobInfo: JobInfo) {
        val area = Cookies.getConstant(1)

        val iv = findViewById(R.id.iv_head) as ImageView
        Glide.with(this).load(OfficeApplication.userInfo?.avatar).error(R.drawable.organization_default).into(iv)
        val tvJobName = findViewById(R.id.job_name) as TextView
        tvJobName.text = jobInfo.job_name
        val tvOffice = findViewById(R.id.tv_office) as TextView
        tvOffice.text = OfficeApplication.userInfo?.name
        val tvAddress = findViewById(R.id.tv_address) as TextView
        tvAddress.text = if (jobInfo.work_area == 0) "全南京" else "南京市 ${area[jobInfo.work_area]}"
        val tvMoney = findViewById(R.id.tv_money) as TextView
        tvMoney.text = jobInfo.money
        val tvTime = findViewById(R.id.tv_time) as TextView
        tvTime.text = DateUtil.stampToDate(jobInfo.create_time)
    }


    fun changSoftInputWindow(close: Boolean) {
        val inputService = getSystemService (Context.INPUT_METHOD_SERVICE) as InputMethodManager
        //如果软键盘在显示，则强制隐藏
        if (close && inputService.isActive) {
            inputService.hideSoftInputFromWindow(et.windowToken, 0)
        }
        if (!close) {
            inputService.showSoftInput(et, InputMethodManager.SHOW_FORCED)
        }
    }
}
