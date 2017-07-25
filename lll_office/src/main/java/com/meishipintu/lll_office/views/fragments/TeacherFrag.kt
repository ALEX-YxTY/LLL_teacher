package com.meishipintu.lll_office.views.fragments

import android.content.Intent
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.TextView
import com.meishipintu.lll_office.Cookies
import com.meishipintu.lll_office.R
import com.meishipintu.lll_office.contract.TeacherContract
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
class TeacherFrag:BasicFragment(), MenuClickListener, TeacherContract.IView{

    var tj = true //是否推荐
    var course = 0 //科目
    var grade = 0   //学科
    var experience = 0 //经验要求

    var rv: RecyclerView? = null
    var dataList = mutableListOf<TeacherInfo>()
    val teacherAdapter: TeacherAdapter by lazy { TeacherAdapter(this.activity, dataList, 1) }

    var popTj: TjPop? = null
    var popRequire: RequirePop? = null

    var tvTj: TextView?=null
    var tvArea: TextView?=null
    var tvRequire: TextView?=null
    var back: View? = null

    var rootView: View? = null

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
            rv = rootView?.findViewById(R.id.rv) as RecyclerView
            initListener(rootView)
            initList()
        }
        return rootView
    }

    private fun initList() {
        rv?.layoutManager = LinearLayoutManager(this.activity)
        rv?.adapter = teacherAdapter
        (presenter as TeacherContract.IPresenter).doSearch()    //默认全部查询
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
            (presenter as TeacherContract.IPresenter).doSearch(decending = true) //所有教师根据降序排列
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
        (presenter as TeacherContract.IPresenter).doSearch(tj = tj) //根据是否筛选推荐
    }


    //from MenuClickListener
    override fun onRequireSelect(indexCourse: Int, indexGrade: Int, indexExperience: Int) {
        course = indexCourse
        grade = indexGrade
        experience = indexExperience
        (presenter as TeacherContract.IPresenter).doSearch(experience = experience, course = course, grade = grade) //根据年龄，科目，年级筛选
    }

    //from MenuClickListener
    override fun onDismiss(type: Int) {
        back?.visibility= View.GONE
    }

    //from JobContract.IView
    override fun onDateGet(dataList: List<TeacherInfo>) {
        this.dataList.clear()
        this.dataList.addAll(dataList)
        teacherAdapter.notifyDataSetChanged()
    }

    override fun onError(e: String) {
        toast(e)
    }

}
