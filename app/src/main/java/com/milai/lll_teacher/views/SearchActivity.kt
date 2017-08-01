package com.milai.lll_teacher.views

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import com.meishipintu.lll_office.views.adapters.HistoryAdapter
import com.meishipintu.lll_office.views.adapters.OnItemClickListener
import com.milai.lll_teacher.Cookies
import com.milai.lll_teacher.R
import com.milai.lll_teacher.contracts.JobContract
import com.milai.lll_teacher.contracts.OfficeContract
import com.milai.lll_teacher.contracts.SearchContract
import com.milai.lll_teacher.custom.view.CustomLabelSelectListener
import com.milai.lll_teacher.custom.view.CustomLabelSingleSelectCenterView
import com.milai.lll_teacher.models.entities.JobInfo
import com.milai.lll_teacher.models.entities.OfficeInfo
import com.milai.lll_teacher.presenters.JobPresenter
import com.milai.lll_teacher.presenters.OfficePresenter
import com.milai.lll_teacher.views.adapters.JobAdapter
import com.milai.lll_teacher.views.adapters.OfficeAdapter

class SearchActivity : BasicActivity(), CustomLabelSelectListener, OnItemClickListener,SearchContract.IView {

    val from:Int by lazy{ intent.getIntExtra("from", 1)}    //from=1 搜索职位 from=2 搜索机构

    val courses = Cookies.getConstant(2)
    val rv: RecyclerView by lazy{ findViewById(R.id.rv) as RecyclerView }

    val jobDataList = mutableListOf<JobInfo>()
    val jobAdapter :JobAdapter by lazy{ JobAdapter(this, jobDataList, 1)} //进入教室详情显示收藏

    val officeDataList = mutableListOf<OfficeInfo>()
    val officeAdapter :OfficeAdapter by lazy{ OfficeAdapter(this, officeDataList)}

    val etSearch: EditText by lazy{ findViewById(R.id.et_search) as EditText }
    lateinit var history:MutableList<String> //搜索记录
    val historyAdapter: HistoryAdapter by lazy{ HistoryAdapter(this, history, this) }

    var isHistory = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        val labelSelect = findViewById(R.id.labels) as CustomLabelSingleSelectCenterView
        if (from == 1) {
            //职位搜索
            presenter = JobPresenter(this)
            history = Cookies.getJobHistory()
            labelSelect.setData(courses.subList(1,6))
            labelSelect.setListener(this)
        } else {
            //机构搜索
            presenter = OfficePresenter(this)
            history = Cookies.getOfficeHistory()
            labelSelect.visibility = View.GONE
            findViewById(R.id.tv1).visibility = View.GONE
        }
        etSearch.setOnEditorActionListener(TextView.OnEditorActionListener{ _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                labelSelect.clearSelect()
                val searchName = etSearch.text.toString()
                (presenter as JobContract.IPresenter).getJobByKeyWord(searchName)
                if (!(history.size > 0 && searchName == history[0])) {
                    history.add(0, searchName)
                }
                //消费该事件
                return@OnEditorActionListener true
            }
            false
        })
        findViewById(R.id.tv_cancel).setOnClickListener{ this.finish()}
        initRv()
    }

    private fun initRv() {
        rv.layoutManager = LinearLayoutManager(this)
        loadHistory()
    }

    //载入历史记录
    private fun loadHistory() {
        rv.adapter = historyAdapter
    }

    //from CustomLabelSelectListener
    override fun select(index: Int) {
        etSearch.setText("")
        //来自职位搜索
        (presenter as JobContract.IPresenter).doSearch(course = index + 1)
    }

    //from CustomLabelSelectListener
    override fun remove(index: Int) {
        //无需实现
    }

    //from CustomLabelSelectListener
    override fun isSelect(index: Int): Boolean {
        //无需实现
        return false
    }

    //from OnItemClickListener
    override fun onItemClick(name: String) {
        if (from == 1) {
            //搜索职位
            (presenter as JobContract.IPresenter).getJobByKeyWord(name)
        } else {
            (presenter as OfficeContract.IPresenter).searchOfficeByKeyword(name)
        }
    }

    //from SearchContract.IView
    override fun showError(err: String) {
        toast(err)
    }

    //from SearchContract.IView
    override fun onJobGet(dataList: List<JobInfo>) {
        if (isHistory) {
            isHistory = false
            rv.adapter = this.jobAdapter
        }
        etSearch.setText("")
        this.jobDataList.clear()
        this.jobDataList.addAll(dataList)
        jobAdapter.notifyDataSetChanged()
    }

    //from SearchContract.IView
    override fun onOfficeGet(dataList: List<OfficeInfo>) {
        if (isHistory) {
            isHistory = false
            rv.adapter = this.officeAdapter
        }
        etSearch.setText("")
        this.officeDataList.clear()
        this.officeDataList.addAll(dataList)
        officeAdapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (history.size > 5) {
            history = history.subList(0, 5)
        }
        if (from == 1) {
            Cookies.saveJobHistory(history.toTypedArray())
        } else {
            Cookies.saveOfficeHistory(history.toTypedArray() )
        }
    }
}
