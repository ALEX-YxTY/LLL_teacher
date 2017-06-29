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
import com.meishipintu.lll_office.modles.entities.TeacherInfo

/**
 * Created by Administrator on 2017/6/29.
 *
 * 主要功能：
 */
class TeacherFrag:Fragment(){

//
//    var tj = 0 //0-推荐，1-全部
//    var area = 0 //0-全部，index-区序号
//    var year = 0    //0-全部，1-25:30 ，2-30:35 , 3-35:40， 4-40:45 ， 5-45:50， 6-50：55 ，7->55
//    var workYear =0 //0-全部，1-<1, 2-1:3, 3-3:5, 4-5:10, 5->10
//    var education = 0   //0-全部，1-中专及以下，2-高中，3-大专，4-本科，5-硕士，6-博士
//
//    var rv: RecyclerView? = null
//    var dataList: MutableList<TeacherInfo>? = null
//    val jobAdapter:JobAdapter by lazy { JobAdapter(this.activity,dataList!!) }
//
//    var popTj: TjPop? = null
//    var popArea: AreaPop? = null
//    var popRequire: RequirePop? = null
//
//    var tvTj: TextView?=null
//    var tvArea: TextView?=null
//    var tvRequire: TextView?=null
//    var back: View? = null
//
//    override fun onCreateView(inflater: LayoutInflater?, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View? {
//        val view = inflater!!.inflate(R.layout.frag_teacher, container, false)
//        tvTj = view.findViewById(R.id.tv_1) as TextView
//        tvArea = view.findViewById(R.id.tv_2) as TextView
//        tvRequire = view.findViewById(R.id.tv_3) as TextView
//        rv = view.findViewById(R.id.rv) as RecyclerView
//        initListener(view)
//        initList()
//        return view
//    }
//
//    private fun initList() {
//        //TODO 调查询接口
//        rv?.layoutManager = LinearLayoutManager(this.activity)
//        dataList = ArrayList<JobInfo>()
//        rv?.adapter = jobAdapter
//        onDateGet(mutableListOf())
//    }
//
//    //筛选菜单的监听
//    private fun initListener(view: View) {
//        back = view.findViewById(R.id.back) as View
//        val tab = view.findViewById(R.id.ll_tab) as LinearLayout
//        view.findViewById(R.id.rl_search).setOnClickListener{
//            //启动搜索页
//            val intent = Intent(this@JobFragment.activity, SearchActivity::class.java)
//            intent.putExtra("from", 1)  //来源 1-职位 2-机构
//            startActivity(intent)
//        }
//        view.findViewById(R.id.rl_tj)?.setOnClickListener({
//            if (popTj == null) {
//                popTj = TjPop(this@JobFragment.activity,this@JobFragment)
//            }
//            if (popTj?.isShowing as Boolean) {
//                startBackAnimation(2)
//                back?.visibility = View.GONE
//            } else {
//                back?.visibility = View.VISIBLE
//                startBackAnimation(1)
//            }
//            popTj?.showPopDropDown(tab)
//        })
//        view.findViewById(R.id.rl_area).setOnClickListener{
//            if (popArea == null) {
//                popArea = AreaPop(this.activity,this, arrayOf("全南京", "鼓楼区", "玄武区", "秦淮区"
//                        , "建邺区", "雨花台区", "江宁区", "浦口区", "高淳", "溧水", "六合"))
//            }
//            if (popArea?.isShowing as Boolean) {
//                startBackAnimation(2)
//                back?.visibility = View.GONE
//            } else {
//                back?.visibility = View.VISIBLE
//                startBackAnimation(1)
//            }
//            popArea?.showPopDropDown(tab)
//        }
//        view.findViewById((R.id.rl_require)).setOnClickListener{
//            if (popRequire == null) {
//                popRequire = RequirePop(this.activity, this, listOf("全部", "25-30岁", "30-35岁", "35-40岁"
//                        , "40-45岁", "45-50岁", "50-55岁", "55岁以上"), listOf("全部", "1年以内", "1-3年", "3-5年"
//                        , "5-10年", "10年以上"), listOf("全部", "中专及以下", "高中", "大专", "本科", "硕士", "博士"))
//            }
//            if (popRequire?.isShowing as Boolean) {
//                startBackAnimation(2)
//                back?.visibility = View.GONE
//            } else {
//                back?.visibility = View.VISIBLE
//                startBackAnimation(1)
//            }
//            popRequire?.showPopDropDown(tab)
//        }
//    }
//
//    /**
//     * type=1 show enter animation while type=2 show exit animation
//     */
//    private fun startBackAnimation(type: Int) {
//        back?.startAnimation(AnimationUtils.loadAnimation(this.activity,if(type==1) R.anim.popin_anim
//        else R.anim.popout_anim))
//    }
//
//    //from MenuClickListener
//    override fun onTjClick(index: Int, name: String) {
//        tj = index
//        tvTj?.text = name
//        //TODO 调查询接口
//    }
//
//    //from MenuClickListener
//    override fun onArerSelect(index: Int, name: String) {
//        area = index
//        tvArea?.text = name
//        //TODO 调查询接口
//    }
//
//    //from MenuClickListener
//    override fun onRequireSelect(indexYear: Int, indexWorkYear: Int, indexEducation: Int) {
//        year = indexYear
//        workYear = indexWorkYear
//        education = indexEducation
//        //TODO 调查询接口
//    }
//
//    //from MenuClickListener
//    override fun onDismiss(type: Int) {
//        back?.visibility= View.GONE
//    }
//
//    //from JobContract.IView
//    override fun showError(err: String) {
//    }
//
//    //from JobContract.IView
//    override fun onDateGet(dataList: List<JobInfo>) {
//        for (i: Int in 1..10) {
//            this.dataList?.add(JobInfo())
//        }
//        jobAdapter.notifyDataSetChanged()
//    }
}
