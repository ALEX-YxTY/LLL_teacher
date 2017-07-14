package com.milai.lll_teacher.views

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import com.milai.lll_teacher.R
import com.milai.lll_teacher.contracts.ChatDetailContract
import com.milai.lll_teacher.models.entities.ChatDetail
import com.milai.lll_teacher.models.entities.JobInfo
import com.milai.lll_teacher.models.entities.OfficeInfo
import com.milai.lll_teacher.presenters.MessagePresenter
import com.milai.lll_teacher.views.adapters.ChatAdapter

class ChatDetailActivity : BasicActivity(),ChatDetailContract.IView {

    val job:JobInfo by lazy{ intent.getSerializableExtra("job") as JobInfo}
    val teacherId:String by lazy{ intent.getStringExtra("teacher")}

    val data = mutableListOf<ChatDetail>()
    val adapter:ChatAdapter by lazy { ChatAdapter(this,data) }

    val et:EditText by lazy { findViewById(R.id.et_message) as EditText }
    val rlJob:RelativeLayout by lazy { findViewById(R.id.item_job) as RelativeLayout }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_detail)
        presenter = MessagePresenter(this)
        (presenter as ChatDetailContract.IPresenter).getOfficeInfo(job.oid)
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
                (presenter as ChatDetailContract.IPresenter).sendChat(job.id, teacherId, job.oid, et.text.toString())
            }
        }
        rlJob.setOnClickListener{
            //跳转职位详情，不带机构信息
            val intent = Intent(this, JobDetailActivity::class.java)
            intent.putExtra("job", job)
            if (job.organization != null) {
                intent.putExtra("type", 1)   //通知职位详情页是否要显示机构信息
            } else {
                intent.putExtra("type", 2)
            }
            startActivity(intent)
        }
    }

    private fun initList() {
        (presenter as ChatDetailContract.IPresenter).getChatList(teacherId, job.id)
        val rv = findViewById(R.id.rv) as RecyclerView
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter
    }

    //from ChatDetailContract.IView
    override fun showError(err: String) {
        toast(err)
    }

    //from ChatDetailContract.IView
    override fun onOfficeInfoGet(officeInfo: OfficeInfo) {
        val title = findViewById(R.id.tv_title) as TextView
        title.text = officeInfo.name
    }

    //from ChatDetailContract.IView
    override fun onChaListGet(dataList: List<ChatDetail>) {
        this.data.clear()
        this.data.addAll(dataList)
        adapter.notifyDataSetChanged()
    }

    //from ChatDetailContract.IView
    override fun onSendChatSuccess() {
        et.setText("")
        et.clearFocus()
        changSoftInputWindow(true)
        (presenter as ChatDetailContract.IPresenter).getChatList(teacherId, job.id)
    }

    fun changSoftInputWindow(close: Boolean) {
        val inputService = getSystemService (Context.INPUT_METHOD_SERVICE) as InputMethodManager
        //如果软键盘在显示，则强制隐藏
        if (close && inputService.isActive()) {
            inputService.hideSoftInputFromWindow(et.getWindowToken(), 0)
        }
        if (!close) {
            inputService.showSoftInput(et, InputMethodManager.SHOW_FORCED)
        }
    }
}
