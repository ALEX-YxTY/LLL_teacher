package com.milai.lll_teacher.views

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.EditText
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
        val titel = findViewById(R.id.tv_title) as TextView
        (title as TextView).text = officeInfo.name
    }

    //from ChatDetailContract.IView
    override fun onChaListGet(dataList: List<ChatDetail>) {
        this.data.clear()
        this.data.addAll(dataList)
        adapter.notifyDataSetChanged()
    }

    //from ChatDetailContract.IView
    override fun onSendChatSuccess() {
        (presenter as ChatDetailContract.IPresenter).getChatList(teacherId, job.id)
    }
}
