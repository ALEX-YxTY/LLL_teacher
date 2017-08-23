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
import com.meishipintu.lll_office.OfficeApplication
import com.meishipintu.lll_office.R
import com.meishipintu.lll_office.contract.MyInterviewContract
import com.meishipintu.lll_office.customs.CanLoadMoreRecyclerView
import com.meishipintu.lll_office.customs.MenuClickListener
import com.meishipintu.lll_office.customs.TjPop
import com.meishipintu.lll_office.modles.entities.DeliverInfo
import com.meishipintu.lll_office.modles.entities.TeacherInfo
import com.meishipintu.lll_office.presenters.DeliverPresenter
import com.meishipintu.lll_office.views.adapters.DeliverAdapter

class MyInterviewActivity : BasicActivity(),View.OnClickListener,MenuClickListener
        ,MyInterviewContract.IView, CanLoadMoreRecyclerView.StateChangedListener{

    val rv:CanLoadMoreRecyclerView by lazy { findViewById(R.id.rv)as CanLoadMoreRecyclerView }
    val tvAll:TextView by lazy { findViewById(R.id.tv_all)as TextView }
    val tvAlready:TextView by lazy { findViewById(R.id.tv_already)as TextView }
    val tvUnInterView:TextView by lazy { findViewById(R.id.tv_1)as TextView }
    val ivUnInterView:ImageView by lazy { findViewById(R.id.iv_1)as ImageView }
    val rlUnInterView:RelativeLayout by lazy { findViewById(R.id.rl_not_interview)as RelativeLayout }

    val popTj: TjPop by lazy { TjPop(this,this,"未面试","未面试 特邀") }
    val back:View by lazy { findViewById(R.id.back)}
    val tab :View by lazy { findViewById(R.id.ll_tab) }

    val dataList:MutableList<DeliverInfo> = mutableListOf()
    val adapter:DeliverAdapter by lazy { DeliverAdapter(this,dataList) }

    var select = 0  //标注当前选择栏
    var status = 0   //标记当前选择状态,0-全部 1-未面试，2-已面试
    var type = 0     //标记类型，0-不限 1-主动投递 2-邀约
    var uid: String = OfficeApplication.userInfo?.uid?:""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_interview)
        presenter = DeliverPresenter(this)
        findViewById(R.id.bt_back).setOnClickListener(this)
        val tvTitle = findViewById(R.id.tv_title)as TextView
        tvTitle.text = "我的面试"
        tvAll.setOnClickListener(this)
        tvAlready.setOnClickListener(this)
        rlUnInterView.setOnClickListener(this)

        rv.listener = this
    }


    override fun onResume() {
        super.onResume()
        rv.setAdapter(adapter)
        changeSelect(0)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.bt_back -> {
                onBackPressed()
            }
            R.id.tv_all ->{
                //全部数据
                status = 0
                type = 0
                rv.reLoad()
                changeSelect(0)
            }
            R.id.tv_already ->{
                status = 2
                type = 0
                rv.reLoad()
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

    private fun changeSelect(index:Int){
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
        //1-未面试  0-未面试邀约
        status = 1
        type = 2-index
        rv.reLoad()
        changeSelect(1)
        tvUnInterView.text = if (index == 1) "未面试" else "未面试 邀约"
    }

    override fun onRequireSelect(indexYear: Int, indexWorkYear: Int, indexEducation: Int) {
    }

    override fun onDismiss(type: Int) {
        back.visibility= View.GONE
    }

    //from MyInterviewContract.IView
    override fun onError(e: String) {
        toast(e)
    }

    override fun onLoadMore(page: Int) {
        (presenter as MyInterviewContract.IPresenter).getDeliverHistory(OfficeApplication.userInfo?.uid!!
                , status = status, type = type, page = page)
    }

    override fun onLoadError() {
        rv.dismissProgressBar()
        rv.dismissLoading()
    }

    //from MyInterviewContract.IView
    override fun onDeleverDataGet(dataList: List<DeliverInfo>,page:Int) {
        if (page == 1) {
            //首次加载
            this.dataList.clear()
            this.dataList.addAll(dataList)
            rv.onLoadSuccess(page)
            adapter.notifyDataSetChanged()
        }else if (dataList.isNotEmpty()) {
            //load more 并且有数据
            this.dataList.addAll(dataList)
            rv.onLoadSuccess(page)
            adapter.notifyDataSetChanged()
        } else {
            //load more 没数据
            rv.dismissProgressBar()
            rv.dismissLoading()
        }
    }

}
