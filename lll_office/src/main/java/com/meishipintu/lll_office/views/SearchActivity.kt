package com.meishipintu.lll_office.views

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import com.meishipintu.lll_office.Cookies
import com.meishipintu.lll_office.R
import com.meishipintu.lll_office.contract.TeacherContract
import com.meishipintu.lll_office.customs.CustomLabelSelectListener
import com.meishipintu.lll_office.customs.CustomLabelSingleSelectCenterView
import com.meishipintu.lll_office.modles.entities.TeacherInfo
import com.meishipintu.lll_office.presenters.TeachPresenter
import com.meishipintu.lll_office.views.adapters.HistoryAdapter
import com.meishipintu.lll_office.views.adapters.OnItemClickListener
import com.meishipintu.lll_office.views.adapters.TeacherAdapter

class SearchActivity : BasicActivity(),CustomLabelSelectListener ,TeacherContract.IView,OnItemClickListener{

    val courses = Cookies.getConstant(2)
    val rv:RecyclerView by lazy{ findViewById(R.id.rv) as RecyclerView }

    val dataList = mutableListOf<TeacherInfo>()
    val adapter :TeacherAdapter by lazy{ TeacherAdapter(this, dataList, 1)} //进入教室详情显示收藏

    val etSearch:EditText by lazy{ findViewById(R.id.et_search) as EditText }
    lateinit var history:MutableList<String> //搜索记录
    val historyAdapter: HistoryAdapter by lazy{ HistoryAdapter(this, history, this) }

    var isHistory = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        presenter = TeachPresenter(this)
        history = Cookies.getHistory()
        val labelSelect = findViewById(R.id.labels) as CustomLabelSingleSelectCenterView
        labelSelect.setData(courses.subList(1,6))
        labelSelect.setListener(this)
        etSearch.setOnEditorActionListener(TextView.OnEditorActionListener{_,actionId,_ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                labelSelect.clearSelect()
                val searchName = etSearch.text.toString()
                (presenter as TeacherContract.IPresenter).searchTeacher(searchName)
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
        (presenter as TeacherContract.IPresenter).doSearch(course = index+1)
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

    override fun onError(e: String) {
        toast(e)
    }

    override fun onDateGet(dataList: List<TeacherInfo>) {
        if (isHistory) {
            isHistory = false
            rv.adapter = this.adapter
        }
        etSearch.setText("")
        this.dataList.clear()
        this.dataList.addAll(dataList)
        adapter.notifyDataSetChanged()
    }

    //历史记录OnItemClickListener
    override fun onItemClick(name: String) {
        (presenter as TeacherContract.IPresenter).searchTeacher(name)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (history.size > 5) {
            history = history.subList(0, 5)
        }
        Cookies.saveHistory(history.toTypedArray())
    }
}
