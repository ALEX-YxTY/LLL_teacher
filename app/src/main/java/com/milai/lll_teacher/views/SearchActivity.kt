package com.milai.lll_teacher.views

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import com.milai.lll_teacher.views.adapters.HistoryAdapter
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
import com.milai.lll_teacher.views.adapters.OnItemClickListener

class SearchActivity : BasicActivity(), OnItemClickListener,SearchContract.IView,CustomLabelSelectListener {

    val from:Int by lazy{ intent.getIntExtra("from", 1)}    //from=1 搜索职位 from=2 搜索机构

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
        val courses = Cookies.getConstant(2)
        val labelSelect = findViewById(R.id.labels) as CustomLabelSingleSelectCenterView
        if (from == 1) {
            //职位搜索
            presenter = JobPresenter(this)
            history = Cookies.getJobHistory()
        } else {
            //机构搜索
            presenter = OfficePresenter(this)
            history = Cookies.getOfficeHistory()
            labelSelect.visibility = View.GONE
            findViewById(R.id.tv1).visibility = View.GONE
        }
        labelSelect.setData(courses.subList(1,6))
        labelSelect.setListener(this)
        etSearch.setOnEditorActionListener(TextView.OnEditorActionListener{ _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                labelSelect.clearSelect()
                val searchName = etSearch.text.toString()
                if (from == 1) {
                    (presenter as JobContract.IPresenter).getJobByKeyWord(searchName)
                } else {
                    (presenter as OfficeContract.IPresenter).searchOfficeByKeyword(searchName)
                }
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

    //from CustomSelectListener
    override fun select(index: Int) {
        etSearch.setText("")
        (presenter as SearchContract.IPresenter).getJobByCourse(index+1)
    }

    //from CustomSelectListener
    override fun remove(index: Int) {
        //空实现
    }

    //from CustomSelectListener
    override fun isSelect(index: Int): Boolean {
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
