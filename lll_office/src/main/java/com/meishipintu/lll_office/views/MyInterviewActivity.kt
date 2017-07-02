package com.meishipintu.lll_office.views

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.meishipintu.lll_office.R
import com.meishipintu.lll_office.customs.MenuClickListener
import com.meishipintu.lll_office.customs.TjPop
import com.meishipintu.lll_office.modles.entities.TeacherInfo
import com.meishipintu.lll_office.views.adapters.TeacherAdapter

class MyInterviewActivity : AppCompatActivity(),View.OnClickListener,MenuClickListener {


    val rv:RecyclerView by lazy { findViewById(R.id.rv)as RecyclerView }
    val tvAll:TextView by lazy { findViewById(R.id.tv_all)as TextView }
    val tvAlready:TextView by lazy { findViewById(R.id.tv_already)as TextView }
    val tvUnInterView:TextView by lazy { findViewById(R.id.tv_1)as TextView }
    val ivUnInterView:ImageView by lazy { findViewById(R.id.iv_1)as ImageView }
    val rlUnInterView:RelativeLayout by lazy { findViewById(R.id.rl_not_interview)as RelativeLayout }

    val popTj: TjPop by lazy { TjPop(this,this,"未面试","未面试 特邀") }
    val back:View by lazy { findViewById(R.id.back)}
    val tab :View by lazy { findViewById(R.id.ll_tab) }

    val dataList:MutableList<TeacherInfo> = mutableListOf()
    val adapter:TeacherAdapter by lazy { TeacherAdapter(this,dataList) }

    var select = 0  //标注当前选择栏

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_interview)
        findViewById(R.id.bt_back).setOnClickListener(this)
        val tvTitle = findViewById(R.id.tv_title)as TextView
        tvTitle.text = "我的面试"
        tvAll.setOnClickListener(this)
        tvAlready.setOnClickListener(this)
        rlUnInterView.setOnClickListener(this)

        initRv()
    }

    private fun initRv() {
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = this.adapter
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.bt_back -> {
                onBackPressed()
            }
            R.id.tv_all ->{
                //全部数据
                changeSelect(0)
            }
            R.id.tv_already ->{
                //已面试数据
                changeSelect(2)
            }
            R.id.rl_not_interview ->{
                if (popTj.isShowing) {
                    startBackAnimation(2)
                    back.visibility = View.GONE
                } else {
                    back.visibility = View.VISIBLE
                    startBackAnimation(1)
                }
                popTj.showPopDropDown(tab)
            }

        }
    }

    fun changeSelect(index:Int){
        if (select != index) {
            when(select){
                0 -> tvAll.setTextColor(0xff505d67.toInt())
                1 -> {
                    tvUnInterView.setTextColor(0xff505d67.toInt())
                    ivUnInterView.setImageResource(R.drawable.icon_pulldown_grey)
                }
                2 -> tvAlready.setTextColor(0xff505d67.toInt())
            }
            select = index
            when(select){
                0 -> tvAll.setTextColor(0xffFF763F.toInt())
                1 -> {
                    tvUnInterView.setTextColor(0xffFF763F.toInt())
                    ivUnInterView.setImageResource(R.drawable.icon_pulldown_orange)
                }
                2 -> tvAlready.setTextColor(0xffFF763F.toInt())
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

    override fun onTjClick(index: Int, name: String) {
        //0-未面试  1-未面试邀约
        changeSelect(1)
        tvUnInterView.text = if(index==0) "未面试" else "未面试 邀约"
    }

    override fun onArerSelect(index: Int, name: String) {
    }

    override fun onRequireSelect(indexYear: Int, indexWorkYear: Int, indexEducation: Int) {
    }

    override fun onDismiss(type: Int) {
        back.visibility= View.GONE
    }

}
