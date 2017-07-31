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
import com.meishipintu.lll_office.customs.MenuClickListener
import com.meishipintu.lll_office.customs.RequirePopWithSearch
import com.meishipintu.lll_office.customs.SearchListener
import com.meishipintu.lll_office.modles.entities.TeacherInfo
import com.meishipintu.lll_office.presenters.TeachPresenter
import com.meishipintu.lll_office.views.adapters.TeacherAdapter

class InviteActivity : BasicActivity(),View.OnClickListener, MenuClickListener,SearchListener
        ,TeacherCollectionContract.IView,TeacherContract.IView {

    val jobId:Int by lazy{ intent.getIntExtra("jobId", -1)}
    val tvCollect:TextView by lazy{ findViewById(R.id.tv_collect) as TextView}
    val tvSearch:TextView by lazy{ findViewById(R.id.tv_search) as TextView}

    val dataList = mutableListOf<TeacherInfo>()
    val adapter:TeacherAdapter by lazy{ TeacherAdapter(this, dataList, 2, 1, jobId)} //type=2 底部邀约
    var requireSearchPop: RequirePopWithSearch? = null

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
        val rv = findViewById(R.id.rv_teacher) as RecyclerView
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = this.adapter
        (presenter as TeacherCollectionContract.IPresenter).getTeacherCollectiion(OfficeApplication.userInfo?.uid!!)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.bt_back -> onBackPressed()
            R.id.tv_collect ->{
                tvCollect.setTextColor(orangeInt)
                tvSearch.setTextColor(blackInt)
                //获取收藏列表
                (presenter as TeacherCollectionContract.IPresenter).getTeacherCollectiion(OfficeApplication.userInfo?.uid!!)
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
                    back?.visibility = View.GONE
                } else {
                    back?.visibility = View.VISIBLE
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
        back?.startAnimation(AnimationUtils.loadAnimation(this,if(type==1) R.anim.popin_anim
        else R.anim.popout_anim))
    }

    //from SearchListener
    override fun onSearch(keyWord: String) {
        (presenter as TeacherContract.IPresenter).searchTeacher(keyWord)
        if (requireSearchPop?.isShowing as Boolean) {
            requireSearchPop?.dismiss()
            startBackAnimation(2)
            back?.visibility = View.GONE
        }

    }

    //from MenuClickListener
    override fun onTjClick(index: Int, name: String) {
        //无实现
    }

    //from MenuClickListener
    override fun onRequireSelect(indexCourse: Int, indexGrade: Int, indexExperience: Int) {
        (presenter as TeacherContract.IPresenter).doSearch(experience = indexExperience, course = indexCourse, grade = indexGrade) //根据年龄，科目，年级筛选
        Log.d("test",requireSearchPop?.getSelect())
    }

    //from MenuClickListener
    override fun onDismiss(type: Int) {
        back?.visibility= View.GONE
    }

    override fun onError(e: String) {
        toast(e)
    }

    //from TeacherContract.IView
    override fun onDateGet(dataList: List<TeacherInfo>) {
        refreshList(dataList)
    }

    private fun refreshList(dataList: List<TeacherInfo>) {
        this.dataList.clear()
        this.dataList.addAll(dataList)
        adapter.notifyDataSetChanged()
    }

    //from TeacherCollectionContract.IView
    override fun onTeacherCollectionGet(dataList: List<TeacherInfo>) {
        refreshList(dataList)
    }

}
