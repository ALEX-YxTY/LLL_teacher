package com.milai.lll_teacher.views.fragments

import android.content.Intent
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.TextView
import com.milai.lll_teacher.Cookies
import com.milai.lll_teacher.R
import com.milai.lll_teacher.contracts.JobContract
import com.milai.lll_teacher.custom.view.AreaPop
import com.milai.lll_teacher.custom.view.MenuClickListener
import com.milai.lll_teacher.custom.view.RequirePop
import com.milai.lll_teacher.custom.view.TjPop
import com.milai.lll_teacher.models.entities.JobInfo
import com.milai.lll_teacher.presenters.JobPresenter
import com.milai.lll_teacher.views.SearchActivity
import com.milai.lll_teacher.views.adapters.JobAdapter

/**
 * Created by Administrator on 2017/6/21.
 *
 *
 * 主要功能：
 */

class JobFragment : BasicFragment(), MenuClickListener,JobContract.IView {

    var tj = true //是否推荐
    var area = 0 //0-全部，index-区序号
    var course =0 //学科
    var grade = 0   //年级
    var experience =0 //经验要求

    var rv: RecyclerView? = null
    var dataList = ArrayList<JobInfo>()
    val jobAdapter:JobAdapter by lazy { JobAdapter(this.activity,dataList,1) }  //type=1 job包含机构信息

    var popTj: TjPop? = null
    var popArea: AreaPop? = null
    var popRequire: RequirePop? = null

    var tvTj:TextView?=null
    var tvArea:TextView?=null
    var tvRequire:TextView?=null
    var back: View? = null

    val presenter:JobContract.IPresenter by lazy { JobPresenter(this) }

    override fun onCreateView(inflater: LayoutInflater?, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.frag_job, container, false)
        tvTj = view.findViewById(R.id.tv_1) as TextView
        tvArea = view.findViewById(R.id.tv_2) as TextView
        tvRequire = view.findViewById(R.id.tv_3) as TextView
        rv = view.findViewById(R.id.rv) as RecyclerView
        initListener(view)
        initList()
        return view
    }

    private fun initList() {
        rv?.layoutManager = LinearLayoutManager(this.activity)
        rv?.adapter = jobAdapter
        presenter.doSearch()
    }

    //筛选菜单的监听
    private fun initListener(view:View) {
        back = view.findViewById(R.id.back) as View
        val tab = view.findViewById(R.id.ll_tab) as LinearLayout
        view.findViewById(R.id.rl_search).setOnClickListener{
            //启动搜索页
            val intent = Intent(this@JobFragment.activity, SearchActivity::class.java)
            intent.putExtra("from", 1)  //来源 1-职位 2-机构
            startActivity(intent)
        }
        view.findViewById(R.id.rl_tj)?.setOnClickListener({
            if (popTj == null) {
                popTj = TjPop(this@JobFragment.activity,this@JobFragment)
            }
            if (popTj?.isShowing as Boolean) {
                startBackAnimation(2)
                back?.visibility = View.GONE
            } else {
                back?.visibility = View.VISIBLE
                startBackAnimation(1)
            }
            popTj?.showPopDropDown(tab)
        })
        view.findViewById(R.id.rl_area).setOnClickListener{
            val areas = Cookies.getConstant(1)
            if (popArea == null) {
                popArea = AreaPop(this.activity,this, areas.toTypedArray())
            }
            if (popArea?.isShowing as Boolean) {
                startBackAnimation(2)
                back?.visibility = View.GONE
            } else {
                back?.visibility = View.VISIBLE
                startBackAnimation(1)
            }
            popArea?.showPopDropDown(tab)
        }
        view.findViewById((R.id.rl_require)).setOnClickListener{
            val courses = Cookies.getConstant(2)
            val grades = Cookies.getConstant(3)
            val experiences = Cookies.getConstant(5)
            if (popRequire == null) {
                popRequire = RequirePop(this.activity, this, courses, grades, experiences)
            }
            if (popRequire?.isShowing as Boolean) {
                startBackAnimation(2)
                back?.visibility = View.GONE
            } else {
                back?.visibility = View.VISIBLE
                startBackAnimation(1)
            }
            popRequire?.showPopDropDown(tab)
        }
    }

    /**
     * type=1 show enter animation while type=2 show exit animation
     */
    private fun startBackAnimation(type: Int) {
        back?.startAnimation(AnimationUtils.loadAnimation(this.activity,if(type==1) R.anim.popin_anim
        else R.anim.popout_anim))
    }

    //from MenuClickListener
    override fun onTjClick(index: Boolean, name: String) {
        tj = index
        tvTj?.text = name
        presenter.doSearch(tj = index)
    }

    //from MenuClickListener
    override fun onArerSelect(index: Int, name: String) {
        area = index
        tvArea?.text = name
        presenter.doSearch(area = index)
    }

    //from MenuClickListener
    override fun onRequireSelect(indexCourse: Int, indexGrade: Int, indexExperience: Int) {
        course = indexCourse
        grade = indexGrade
        experience = indexExperience
        presenter.doSearch(course = course, grade = grade, experience = experience)
    }

    //from MenuClickListener
    override fun onDismiss(type: Int) {
        back?.visibility=View.GONE
    }

    //from JobContract.IView
    override fun showError(err: String) {
        toast(err)
    }

    //from JobContract.IView
    override fun onDateGet(dataList: List<JobInfo>) {
        this.dataList.clear()
        this.dataList.addAll(dataList)
        jobAdapter.notifyDataSetChanged()
    }
}
