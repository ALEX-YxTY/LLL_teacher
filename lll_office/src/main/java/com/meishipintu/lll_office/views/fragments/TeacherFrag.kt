package com.meishipintu.lll_office.views.fragments

import android.content.Intent
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.TextView
import com.meishipintu.lll_office.Cookies
import com.meishipintu.lll_office.R
import com.meishipintu.lll_office.contract.TeacherContract
import com.meishipintu.lll_office.customs.CanLoadMoreRecyclerView
import com.meishipintu.lll_office.customs.MenuClickListener
import com.meishipintu.lll_office.customs.RequirePop
import com.meishipintu.lll_office.customs.TjPop
import com.meishipintu.lll_office.modles.entities.TeacherInfo
import com.meishipintu.lll_office.presenters.TeachPresenter
import com.meishipintu.lll_office.views.SearchActivity
import com.meishipintu.lll_office.views.adapters.TeacherAdapter


/**
 * Created by Administrator on 2017/6/29.
 *
 * 主要功能：
 */
class TeacherFrag:BasicFragment(), MenuClickListener, TeacherContract.IView,CanLoadMoreRecyclerView.StateChangedListener{

    var rv: CanLoadMoreRecyclerView? = null
    var dataList = mutableListOf<TeacherInfo>()
    val teacherAdapter: TeacherAdapter by lazy { TeacherAdapter(this.activity, dataList, 1) }

    var popTj: TjPop? = null
    var popRequire: RequirePop? = null

    var tvTj: TextView?=null
    var tvArea: TextView?=null
    var tvRequire: TextView?=null
    var back: View? = null

    var rootView: View? = null

    var tj:Int=1        //默认tj=1
    var course: Int = 0     //默认学科不限
    var grade:Int=0
    var experience:Int = 0
    var decending: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = TeachPresenter(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View? {
        if (rootView == null) {
            rootView = inflater!!.inflate(R.layout.frag_teacher, container, false)
            tvTj = rootView?.findViewById(R.id.tv_1) as TextView
            tvArea = rootView?.findViewById(R.id.tv_2) as TextView
            tvRequire = rootView?.findViewById(R.id.tv_3) as TextView
            rv = rootView?.findViewById(R.id.rv) as CanLoadMoreRecyclerView
            initListener(rootView)
            initList()
        }
        return rootView
    }

    private fun initList() {
        rv?.listener = this
        rv?.setAdapter(teacherAdapter)
    }

    //筛选菜单的监听
    private fun initListener(view: View?) {
        back = view?.findViewById(R.id.back) as View
        val tab = view.findViewById(R.id.ll_tab) as LinearLayout
        view.findViewById(R.id.rl_search).setOnClickListener{
            //启动搜索页
            val intent = Intent(this.activity, SearchActivity::class.java)
            intent.putExtra("from", 1)  //来源 1-职位 2-机构
            startActivity(intent)
        }
        view.findViewById(R.id.rl_tj)?.setOnClickListener({
            if (popTj == null) {
                popTj = TjPop(this.activity,this,"推荐","全部")
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
        view.findViewById(R.id.rl_most).setOnClickListener{
            tj = 0
            course = 0
            grade = 0
            experience = 0
            decending = 1
            //还原其他标签
            tvTj?.text = "全部"
            popTj?.setIndexNow(1)
            popRequire?.clearSelect()

            rv?.reLoad()
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
        tvTj?.text = name
        tj = index
        decending = 0
        course = 0
        grade = 0
        experience = 0
        // 还原其他设置
        popRequire?.clearSelect()
        //重新载入页面
        rv?.reLoad()
    }


    //from MenuClickListener
    override fun onRequireSelect(indexCourse: Int, indexGrade: Int, indexExperience: Int) {
        tj = 0
        decending = 0
        course = indexCourse
        grade = indexGrade
        experience = indexExperience
        //还原其他
        tvTj?.text = "全部"
        popTj?.setIndexNow(1)

        rv?.reLoad()
    }

    //from MenuClickListener
    override fun onDismiss(type: Int) {
        back?.visibility= View.GONE
    }

    //from JobContract.IView
    override fun onDateGet(dataList: List<TeacherInfo>,page:Int) {
        if (page == 1) {
            //首次加载
            this.dataList.clear()
            this.dataList.addAll(dataList)
            rv?.onLoadSuccess(page)
            teacherAdapter.notifyDataSetChanged()
        }else if (dataList.isNotEmpty()) {
            //load more 并且有数据
            this.dataList.addAll(dataList)
            rv?.onLoadSuccess(page)
            teacherAdapter.notifyDataSetChanged()
        } else {
            //load more 没数据
            rv?.dismissProgressBar()
            rv?.dismissLoading()
        }
    }

    override fun onError(e: String) {
        toast(e)
    }

    override fun onLoadMore(page: Int) {
        (presenter as TeacherContract.IPresenter).doSearch(tj = tj, course = course, grade = grade
                , experience = experience, decending = decending, page = page)
    }

    override fun onLoadError() {
        rv?.dismissProgressBar()
        rv?.dismissLoading()
    }

}
