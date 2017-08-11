package com.milai.lll_teacher.views

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
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
import com.milai.lll_teacher.custom.view.CanLoadMoreRecyclerView
import com.milai.lll_teacher.custom.view.CustomLabelSelectListener
import com.milai.lll_teacher.custom.view.CustomLabelSingleSelectCenterView
import com.milai.lll_teacher.models.entities.JobInfo
import com.milai.lll_teacher.models.entities.OfficeInfo
import com.milai.lll_teacher.presenters.JobPresenter
import com.milai.lll_teacher.presenters.OfficePresenter
import com.milai.lll_teacher.views.adapters.JobAdapter
import com.milai.lll_teacher.views.adapters.OfficeAdapter
import com.milai.lll_teacher.views.adapters.OnItemClickListener

class SearchActivity : BasicActivity(), OnItemClickListener,SearchContract.IView,CustomLabelSelectListener
        ,CanLoadMoreRecyclerView.StateChangedListener {

    val from:Int by lazy{ intent.getIntExtra("from", 1)}    //from=1 搜索职位 from=2 搜索机构

    val rv: CanLoadMoreRecyclerView by lazy{ findViewById(R.id.rv) as CanLoadMoreRecyclerView }

    val jobDataList = mutableListOf<JobInfo>()
    val jobAdapter :JobAdapter by lazy{ JobAdapter(this, jobDataList, 1)} //进入教室详情显示收藏

    val officeDataList = mutableListOf<OfficeInfo>()
    val officeAdapter :OfficeAdapter by lazy{ OfficeAdapter(this, officeDataList)}

    val etSearch: EditText by lazy{ findViewById(R.id.et_search) as EditText }
    lateinit var history:MutableList<String> //搜索记录
    val historyAdapter: HistoryAdapter by lazy{ HistoryAdapter(this, history, this) }

    var isHistory = true
    var byKeyWork = true    //标记是否根据关键字搜索
    var course = 0          //选中的科目
    var keyWords = ""       //标记当前keyword

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        val courses = Cookies.getConstant(2)
        val labelSelect = findViewById(R.id.labels) as CustomLabelSingleSelectCenterView
        if (from == 1) {
            //职位搜索
            presenter = JobPresenter(this)
            history = Cookies.getJobHistory()
            etSearch.hint = "搜索你感兴趣的职位"
        } else {
            //机构搜索
            presenter = OfficePresenter(this)
            history = Cookies.getOfficeHistory()
            etSearch.hint = "搜索你感兴趣的机构"
            labelSelect.visibility = View.GONE
            findViewById(R.id.tv1).visibility = View.GONE
        }
        labelSelect.setData(courses.subList(1,6))
        labelSelect.setListener(this)
        etSearch.setOnEditorActionListener(TextView.OnEditorActionListener{ _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (etSearch.text.toString().isNullOrEmpty()) {
                    toast("搜索项不能为空")
                } else {
                    //从关键词搜索
                    isHistory = false
                    byKeyWork = true
                    labelSelect.clearSelect()
                    keyWords = etSearch.text.toString()
                    if (from == 1) {
                        rv.setAdapter(jobAdapter)
                    } else {
                        rv.setAdapter(officeAdapter)
                    }
                    if (!(history.size > 0 && keyWords == history[0])) {
                        history.add(0, keyWords)
                    }
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
        rv.listener = this
        loadHistory()
    }

    //载入历史记录
    private fun loadHistory() {
        rv.setAdapter(historyAdapter)
    }

    //from CanLoadMore.StateChangedListener
    override fun onLoadMore(page: Int) {
        if (!isHistory) {
            //非历史记录加载
            if (from == 1) {
                //搜索职位
                if (byKeyWork) {
                    //通过关键字
                    (presenter as JobContract.IPresenter).getJobByKeyWord(keyWords, page)
                } else {
                    //通过条目
                    (presenter as SearchContract.IPresenter).getJobByCourse(course, page)
                }
            } else {
                //通过关键字搜索机构
                (presenter as OfficeContract.IPresenter).searchOfficeByKeyword(keyWords, page)
            }
        } else {
            //历史消息一次载入，不需要加载
            rv.dismissLoading()
            rv.dismissProgressBar()
        }
    }

    //from CustomSelectListener
    //点击热门条目
    override fun select(index: Int) {
        //职位搜索，点击科目
        isHistory = false
        byKeyWork = false
        course = index
        rv.setAdapter(jobAdapter)
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
    //点击历史条目
    override fun onItemClick(name: String) {
        isHistory = false
        byKeyWork = true
        keyWords = name
        if (from == 1) {
            //职位搜索
            rv.setAdapter(jobAdapter)
        } else {
            //机构搜索
            rv.setAdapter(officeAdapter)
        }
    }

    //from SearchContract.IView
    override fun showError(err: String) {
        toast(err)
    }

    //from BasicViewLoadError
    override fun onLoadError() {
        rv.dismissLoading()
        rv.dismissProgressBar()
    }

    //from SearchContract.IView
    override fun onJobGet(dataList: List<JobInfo>, page: Int) {
        if (page == 1) {
            //首次加载
            this.jobDataList.clear()
            this.jobDataList.addAll(dataList)
            rv.onLoadSuccess(page)
            jobAdapter.notifyDataSetChanged()
        }else if (dataList.isNotEmpty()) {
            //load more 并且有数据
            this.jobDataList.addAll(dataList)
            rv.onLoadSuccess(page)
            jobAdapter.notifyDataSetChanged()
        } else {
            //load more 没数据
            rv.dismissProgressBar()
            rv.dismissLoading()
        }
    }

    //from SearchContract.IView
    override fun onOfficeGet(dataList: List<OfficeInfo>, page: Int) {
        if (page == 1) {
            //首次加载
            this.officeDataList.clear()
            this.officeDataList.addAll(dataList)
            rv.onLoadSuccess(page)
            officeAdapter.notifyDataSetChanged()
        } else if (dataList.isNotEmpty()) {
            //load more 并且有数据
            this.officeDataList.addAll(dataList)
            rv.onLoadSuccess(page)
            officeAdapter.notifyDataSetChanged()
        } else {
            //load more 没数据
            rv.dismissProgressBar()
            rv.dismissLoading()
        }
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
