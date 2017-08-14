package com.milai.lll_teacher.views.fragments

import android.content.Intent
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.TextView
import com.milai.lll_teacher.Cookies
import com.milai.lll_teacher.R
import com.milai.lll_teacher.contracts.JobContract
import com.milai.lll_teacher.custom.view.*
import com.milai.lll_teacher.models.entities.JobInfo
import com.milai.lll_teacher.presenters.JobPresenter
import com.milai.lll_teacher.views.SearchActivity
import com.milai.lll_teacher.views.adapters.BasicLayoutManager
import com.milai.lll_teacher.views.adapters.JobAdapter
import com.milai.lll_teacher.views.adapters.LayoutLoadMoreListener

/**
 * Created by Administrator on 2017/6/21.
 *
 *
 * 主要功能：
 */

class JobFragment : BasicFragment(), MenuClickListener,JobContract.IView,CanLoadMoreRecyclerView.StateChangedListener{

    var tj = 1 //是否推荐
    var area = 0 //0-全部，index-区序号
    var courese = 0//学科 默认不限
    var grade = 0   //年级 默认不限
    var experience = 0 //经验，默认不限

    var rv: CanLoadMoreRecyclerView? = null
    var dataList = ArrayList<JobInfo>()
    val jobAdapter:JobAdapter by lazy { JobAdapter(this.activity, dataList as List<JobInfo>,1) }  //type=1 job包含机构信息

    var popTj: TjPop? = null
    var popArea: AreaPop? = null
    var popRequire: RequirePop? = null

    var tvTj:TextView?=null
    var tvArea:TextView?=null
    var tvRequire:TextView?=null
    var back: View? = null

    var fragView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (presenter == null) {
            presenter = JobPresenter(this)
        }
    }
    override fun onCreateView(inflater: LayoutInflater?, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View? {
        if (fragView == null) {
            fragView = inflater!!.inflate(R.layout.frag_job, container, false)
            tvTj = fragView?.findViewById(R.id.tv_1) as TextView
            tvArea = fragView?.findViewById(R.id.tv_2) as TextView
            tvRequire = fragView?.findViewById(R.id.tv_3) as TextView
            rv = fragView?.findViewById(R.id.rv) as CanLoadMoreRecyclerView
            initListener(fragView!!)
            initList()
        }
        return fragView
    }

    private fun initList() {
        rv?.setAdapter(jobAdapter)
        rv?.listener = this
        (presenter as JobContract.IPresenter).doSearch()
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
    override fun onTjClick(index: Int, name: String) {
        tj = index
        tvTj?.text = name
        area = 0
        courese = 0
        grade = 0
        experience = 0
        //还原其他标签
        popArea?.clearSelect()
        popRequire?.clearSelect()
        //重新载入页面
        rv?.reLoad()
    }

    //from MenuClickListener
    override fun onArerSelect(index: Int, name: String) {
        area = index
        tj = 0
        courese = 0
        grade = 0
        experience = 0
        //还原其他标签
        tvTj?.text = "全部"
        popTj?.setIndexNow(1)
        popRequire?.clearSelect()
        //重新载入页面
        rv?.reLoad()
    }

    //from MenuClickListener
    override fun onRequireSelect(indexCourse: Int, indexGrade: Int, indexExperience: Int) {
        courese = indexCourse
        grade = indexGrade
        experience = indexExperience
        tj = 0
        area = 0
        //还原其他标签
        tvTj?.text = "全部"
        popTj?.setIndexNow(1)
        popArea?.clearSelect()
        //重新载入页面
        rv?.reLoad()
    }

    //from MenuClickListener
    override fun onDismiss(type: Int) {
        back?.visibility=View.GONE
    }

    //from JobContract.IView
    override fun showError(err: String) {
        toast(err)
    }

    override fun onLoadError() {
        Log.d("test", "on load error")
        rv?.dismissProgressBar()
        rv?.dismissLoading()
    }

    //from JobContract.IView
    override fun onDateGet(dataList: List<JobInfo>, page: Int) {
        Log.d("test", "dataList size ${dataList.size} and page $page")
        if (page == 1) {
            //首次加载
            this.dataList.clear()
            this.dataList.addAll(dataList)
            rv?.onLoadSuccess(page)
            jobAdapter.notifyDataSetChanged()
        }else if (dataList.isNotEmpty()) {
            //load more 并且有数据
            this.dataList.addAll(dataList)
            rv?.onLoadSuccess(page)
            jobAdapter.notifyDataSetChanged()
        } else {
            //load more 没数据
            rv?.dismissProgressBar()
            rv?.dismissLoading()
        }
    }

    //from CanLoadMoreRecyclerView.StateChangedListener
    override fun onLoadMore(page: Int) {
        (presenter as JobContract.IPresenter).doSearch(tj = tj, area = area, course = courese, grade = grade
                , experience = experience, page = page)
    }
}
