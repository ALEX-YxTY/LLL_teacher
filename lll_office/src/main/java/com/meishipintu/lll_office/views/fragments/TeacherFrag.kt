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

    var tj = 0 //0-推荐，1-全部
    var year = 0    //0-全部，1-25:30 ，2-30:35 , 3-35:40， 4-40:45 ， 5-45:50， 6-50：55 ，7->55
    var course =0 //0-全部，1-数学 2-语文 3-英语 4-物理 5-化学 6-生物 7-历史 8-政治 9-地理 10-奥数 11-艺体
    var grade = 0   //0-全部 1-高中 2-大专 3-本科 4-硕士 5-博士
    var decending = false //true-按评分降序排列 false-默认排列

    var rv: RecyclerView? = null
    var dataList = mutableListOf<TeacherInfo>()
    val teacherAdapter: TeacherAdapter by lazy { TeacherAdapter(this.activity,dataList) }

    var popTj: TjPop? = null
    var popRequire: RequirePop? = null

    var tvTj: TextView?=null
    var tvArea: TextView?=null
    var tvRequire: TextView?=null
    var back: View? = null

    var rootView: View? = null

    val presenter:TeacherContract.IPresenter by lazy { TeachPresenter(this) }

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
        presenter.doSearch()    //默认全部查询
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
            if (popRequire == null) {
                popRequire = RequirePop(this.activity, this, listOf("全部", "25-30岁", "30-35岁", "35-40岁"
                        , "40-45岁", "45-50岁", "50-55岁", "55岁以上"), listOf("全部","数学","语文"
                        ,"英语","物理","化学", "生物","历史","政治","地理","奥数","艺体")
                        , listOf("全部" ,"高中", "大专", "本科", "硕士", "博士"))
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
        //TODO 调查询接口 推荐查询模式未定
    }

    //from MenuClickListener
    override fun onRequireSelect(indexYear: Int, indexCourse: Int, indexGrade: Int) {
        year = indexYear
        course = indexCourse
        grade = indexGrade
        presenter.doSearch(year = year, course = course, grade = grade) //根据年龄，科目，年级筛选
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

    override fun onDestroy() {
        super.onDestroy()
        presenter.unsubscribe()
    }

}
