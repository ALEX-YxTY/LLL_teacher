package com.meishipintu.lll_office.views

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import com.meishipintu.lll_office.Cookies
import com.meishipintu.lll_office.R
import com.meishipintu.lll_office.contract.TeacherContract
import com.meishipintu.lll_office.customs.CanLoadMoreRecyclerView
import com.meishipintu.lll_office.customs.CustomLabelSelectListener
import com.meishipintu.lll_office.customs.CustomLabelSingleSelectCenterView
import com.meishipintu.lll_office.modles.entities.TeacherInfo
import com.meishipintu.lll_office.presenters.TeachPresenter
import com.meishipintu.lll_office.views.adapters.HistoryAdapter
import com.meishipintu.lll_office.views.adapters.OnItemClickListener
import com.meishipintu.lll_office.views.adapters.TeacherAdapter

class SearchActivity : BasicActivity(),CustomLabelSelectListener ,TeacherContract.IView,OnItemClickListener
        ,CanLoadMoreRecyclerView.StateChangedListener{

    val courses = Cookies.getConstant(2)
    val rv:CanLoadMoreRecyclerView by lazy{ findViewById(R.id.rv) as CanLoadMoreRecyclerView }

    val dataList = mutableListOf<TeacherInfo>()
    val adapter :TeacherAdapter by lazy{ TeacherAdapter(this, dataList, 1)} //进入教室详情显示收藏

    val etSearch:EditText by lazy{ findViewById(R.id.et_search) as EditText }
    lateinit var history:MutableList<String> //搜索记录
    val historyAdapter: HistoryAdapter by lazy{ HistoryAdapter(this, history, this) }

    var isHistory = true
    var byKeyWork = true    //标记是否根据关键字搜索
    var course = 0          //选中的科目
    var keyWords = ""       //标记当前keyword

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
                if (etSearch.text.toString().isNullOrEmpty()) {
                    toast("搜索项不能为空")
                } else {
                    //从关键词搜索
                    byKeyWork = true
                    labelSelect.clearSelect()
                    keyWords = etSearch.text.toString()
                    if (isHistory) {
                        isHistory = false
                        rv.setAdapter(adapter)
                    } else {
                        rv.reLoad()
                    }
                    if (!(history.size > 0 && keyWords == history[0])) {
                        history.add(0, keyWords)
                    }
                    //消费该事件
                    return@OnEditorActionListener true
                }

            }
            false
        })
        findViewById(R.id.tv_cancel).setOnClickListener{ this.finish()}
        initRv()
    }

    private fun initRv(){
        rv.listener = this
        loadHistory()
    }

    //载入历史记录
    private fun loadHistory() {
        rv.setAdapter(historyAdapter)
    }

    //from CustomLabelSelectListener
    override fun select(index: Int) {
        //教师搜索，根据科目
        isHistory = false
        byKeyWork = false
        course = index + 1   //学科常数从0开始，选项从1开始
        rv.setAdapter(adapter)
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

    override fun onLoadError() {
        rv.dismissLoading()
        rv.dismissProgressBar()
    }

    override fun onLoadMore(page: Int) {
        if (!isHistory) {
            //非历史记录加载
            //搜索职位
            if (byKeyWork) {
                //通过关键字
                (presenter as TeacherContract.IPresenter).searchTeacher(keyWords, page)
            } else {
                //通过条目
                (presenter as TeacherContract.IPresenter).doSearch(course = course, page = page)
            }
        } else {
            //历史消息一次载入，不需要加载
            rv.dismissLoading()
            rv.dismissProgressBar()
        }
    }

    override fun onDateGet(dataList: List<TeacherInfo>,page:Int) {
        if (page == 1) {
            //首次加载
            this.dataList.clear()
            this.dataList.addAll(dataList)
            rv.onLoadSuccess(page)
            adapter.notifyDataSetChanged()
        }else if (dataList.isNotEmpty()) {
            //load more 并且有数据
            this.dataList.addAll(dataList)
            rv.onLoadSuccess(page)
            adapter.notifyDataSetChanged()
        } else {
            //load more 没数据
            rv.dismissProgressBar()
            rv.dismissLoading()
        }
    }

    //历史记录或搜索条目OnItemClickListener
    override fun onItemClick(name: String) {
        isHistory = false
        byKeyWork = true
        keyWords = name
        //职位搜索
        rv.setAdapter(adapter)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (history.size > 5) {
            history = history.subList(0, 5)
        }
        if (history.size > 0) {
            Cookies.saveHistory(history.toTypedArray())
        }
    }
}
