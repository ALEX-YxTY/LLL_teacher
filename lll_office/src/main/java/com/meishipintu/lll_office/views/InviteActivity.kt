package com.meishipintu.lll_office.views

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.TextView
import com.meishipintu.lll_office.Cookies
import com.meishipintu.lll_office.OfficeApplication
import com.meishipintu.lll_office.R
import com.meishipintu.lll_office.contract.TeacherCollectionContract
import com.meishipintu.lll_office.contract.TeacherContract
import com.meishipintu.lll_office.customs.CanLoadMoreRecyclerView
import com.meishipintu.lll_office.customs.MenuClickListener
import com.meishipintu.lll_office.customs.RequirePopWithSearch
import com.meishipintu.lll_office.customs.SearchListener
import com.meishipintu.lll_office.modles.entities.TeacherInfo
import com.meishipintu.lll_office.presenters.TeachPresenter
import com.meishipintu.lll_office.views.adapters.TeacherAdapter

class InviteActivity : BasicActivity(),View.OnClickListener, MenuClickListener,SearchListener
        ,TeacherCollectionContract.IView,TeacherContract.IView,CanLoadMoreRecyclerView.StateChangedListener {

    val jobId:Int by lazy{ intent.getIntExtra("jobId", -1)}
    val tvCollect:TextView by lazy{ findViewById(R.id.tv_collect) as TextView}
    val tvSearch:TextView by lazy{ findViewById(R.id.tv_search) as TextView}
    val rv :CanLoadMoreRecyclerView by lazy{ findViewById(R.id.rv_teacher) as CanLoadMoreRecyclerView}

    val dataList = mutableListOf<TeacherInfo>()
    val collectionAdapter:TeacherAdapter by lazy{ TeacherAdapter(this, dataList, 2, 1, jobId)} //type=2 底部邀约
    val searchAdapter:TeacherAdapter by lazy{ TeacherAdapter(this, dataList, 2, 0, jobId)}
    var requireSearchPop: RequirePopWithSearch? = null

    var isCollection = true         //标记当前是否从收藏中载入
    var byKeyWord = false           //来标记是否从关键字搜索载入
    var keyWord = ""                //记录搜索关键字
    var course = 0                  //默认学科不限
    var grade = 0                   //默认年级不限
    var experience = 0              //默认经验不限

    //背景蒙版
    val back :View by lazy{ findViewById(R.id.back) as View}
    val tab:View by lazy{findViewById(R.id.ll_tab) as View}

    val blackInt:Int by lazy{resources.getColor(R.color.text_black1)}
    val orangeInt:Int by lazy{resources.getColor(R.color.themeOrange)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invite)
        presenter = TeachPresenter(this)
        initUI()
    }

    private fun initUI() {
        val tvTitle = findViewById(R.id.tv_title) as TextView
        tvTitle.text = "邀请TA面试"
        findViewById(R.id.bt_back).setOnClickListener(this)
        tvCollect.setOnClickListener(this)
        tvSearch.setOnClickListener(this)
        tvCollect.setTextColor(orangeInt)
        rv.listener = this
    }

    override fun onResume() {
        super.onResume()
        if (isCollection) {
            rv.setAdapter(collectionAdapter)
        } else {
            rv.setAdapter(searchAdapter)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.bt_back -> onBackPressed()
            R.id.tv_collect ->{
                tvCollect.setTextColor(orangeInt)
                tvSearch.setTextColor(blackInt)
                //获取收藏列表
                isCollection = true
                rv.setAdapter(collectionAdapter)
            }
            R.id.tv_search ->{
                tvCollect.setTextColor(blackInt)
                tvSearch.setTextColor(orangeInt)
                //显示需求和搜索弹窗
                val courses = Cookies.getConstant(2)
                val grades = Cookies.getConstant(3)
                val experiences = Cookies.getConstant(5)
                if (requireSearchPop == null) {
                    requireSearchPop = RequirePopWithSearch(this, this, courses
                            , grades, experiences, this)
                }
                if (requireSearchPop?.isShowing as Boolean) {
                    startBackAnimation(2)
                    back.visibility = View.GONE
                } else {
                    back.visibility = View.VISIBLE
                    startBackAnimation(1)
                }
                requireSearchPop?.showPopDropDown(tab)
            }
        }
    }

    /**
     * type=1 show enter animation while type=2 show exit animation
     */
    private fun startBackAnimation(type: Int) {
        back.startAnimation(AnimationUtils.loadAnimation(this,if(type==1) R.anim.popin_anim
        else R.anim.popout_anim))
    }

    //from SearchListener
    override fun onSearch(keyWord: String) {
        isCollection = false
        byKeyWord = true
        this.keyWord = keyWord
        rv.setAdapter(searchAdapter)
        if (requireSearchPop?.isShowing as Boolean) {
            requireSearchPop?.dismiss()
            startBackAnimation(2)
            back.visibility = View.GONE
        }
    }

    //from MenuClickListener
    override fun onTjClick(index: Int, name: String) {
        //无实现
    }

    //from MenuClickListener
    override fun onRequireSelect(indexCourse: Int, indexGrade: Int, indexExperience: Int) {
        course = indexCourse
        grade = indexGrade
        experience = indexExperience
        isCollection = false
        byKeyWord = false
        rv.setAdapter(searchAdapter)
    }

    //from MenuClickListener
    override fun onDismiss(type: Int) {
        back.visibility= View.GONE
    }

    override fun onError(e: String) {
        toast(e)
    }

    override fun onLoadMore(page: Int) {
        if (isCollection) {
            //从收藏载入
            (presenter as TeacherCollectionContract.IPresenter)
                    .getTeacherCollectiion(OfficeApplication.userInfo?.uid ?: "", page)
        } else if (byKeyWord) {
            //从关键字搜索载入
            (presenter as TeacherContract.IPresenter).searchTeacher(keyWord, page)
        } else {
            //从筛选载入
            (presenter as TeacherContract.IPresenter).doSearch(course = course, grade = grade, experience = experience, page = page)
        }
    }

    override fun onLoadError() {
        rv.dismissLoading()
        rv.dismissProgressBar()
    }

    //from TeacherContract.IView
    override fun onDateGet(dataList: List<TeacherInfo>,page:Int) {
        refreshList(dataList,page)
    }

    private fun refreshList(dataList: List<TeacherInfo>,page: Int) {
        val adapter = if(isCollection) collectionAdapter else searchAdapter
        when {
            page == 1 -> {
                //首次加载
                this.dataList.clear()
                this.dataList.addAll(dataList)
                rv.onLoadSuccess(page)
                adapter.notifyDataSetChanged()
            }
            dataList.isNotEmpty() -> {
                //load more 并且有数据
                this.dataList.addAll(dataList)
                rv.onLoadSuccess(page)
                adapter.notifyDataSetChanged()
            }
            else -> {
                //load more 没数据
                rv.dismissProgressBar()
                rv.dismissLoading()
            }
        }
    }

    //from TeacherCollectionContract.IView
    override fun onTeacherCollectionGet(dataList: List<TeacherInfo>,page :Int) {
        refreshList(dataList,page)
    }

}
