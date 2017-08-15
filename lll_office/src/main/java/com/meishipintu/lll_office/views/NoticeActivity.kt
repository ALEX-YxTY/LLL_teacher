package com.meishipintu.lll_office.views

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.TextView
import com.meishipintu.lll_office.Cookies
import com.meishipintu.lll_office.OfficeApplication
import com.meishipintu.lll_office.R
import com.meishipintu.lll_office.contract.NoticeActivityContract
import com.meishipintu.lll_office.presenters.NoticePresenter
import com.meishipintu.lll_office.views.adapters.MyviewPagerAdapter
import com.meishipintu.lll_office.views.fragments.NoticeFragment

class NoticeActivity : BasicActivity(),NoticeActivityContract.IView {

    val tabLayout:TabLayout by lazy{ findViewById(R.id.tabLayout) as TabLayout }
    val uid = OfficeApplication.userInfo?.uid
    var newestSysIdGet:Int = 0      //记录最新系统消息id号

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notice)
        presenter = NoticePresenter(this)
    }

    override fun onResume() {
        super.onResume()
        initUI()
    }

    private fun initUI() {
        val tvTitle = findViewById(R.id.tv_title) as TextView
        tvTitle.text = "我的消息"
        findViewById(R.id.bt_back).setOnClickListener{ onBackPressed()}
        //初始化tablayout
        val viewPager = findViewById(R.id.vp) as ViewPager
        val messageFrag = NoticeFragment()
        val bundle1 = Bundle()
        bundle1.putInt("type", 1)
        messageFrag.arguments = bundle1
        val sysFrag = NoticeFragment()
        val bundle2 = Bundle()
        bundle2.putInt("type", 2)
        sysFrag.arguments = bundle2
        val dataList = listOf(messageFrag,sysFrag)
        val nameList = listOf("私信","通知")
        viewPager.adapter = MyviewPagerAdapter(supportFragmentManager, dataList)
        tabLayout.setupWithViewPager(viewPager)
        (presenter as NoticeActivityContract.IPresenter).getNewestSysId(uid!!)
        (presenter as NoticeActivityContract.IPresenter).getNewestMessId(uid)
        tabLayout.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (newestSysIdGet > 0 && tab?.position == 1) {
                    Cookies.saveNewestSysId(newestSysIdGet, uid)
                }
            }
        })

        for (it: Int in 0..tabLayout.tabCount - 1) {
            val tab = tabLayout.getTabAt(it)
            tab?.setCustomView(R.layout.item_tab_2)
            val textView = tab?.customView?.findViewById(R.id.tv_tab) as TextView
            textView.text = nameList[it]
        }
    }

    override fun onError(e: String) {
        toast(e)
    }

    override fun onNewestMessIdGet(id: Int) {
        Log.d("test","message id get=$id, uid is $uid, id save is ${Cookies.getNewestMesId(uid!!)}")
        if (id > 0 && id > Cookies.getNewestMesId(uid)) {
            Cookies.saveNewestMesId(id, uid)
            //显示红点
            tabLayout.getTabAt(0)?.customView?.findViewById(R.id.red_point)?.visibility = View.VISIBLE
        } else {
            //隐藏红点
            tabLayout.getTabAt(0)?.customView?.findViewById(R.id.red_point)?.visibility = View.GONE
        }
    }

    override fun onNewestSysIdGet(id: Int) {
        Log.d("test","sys id get=$id, id save is ${Cookies.getNewestSysId(uid!!)}")
        if (id > 0 && id > Cookies.getNewestSysId(uid)) {
            newestSysIdGet = id
            //显示红点
            tabLayout.getTabAt(1)?.customView?.findViewById(R.id.red_point)?.visibility = View.VISIBLE
        } else {
            //隐藏红点
            tabLayout.getTabAt(1)?.customView?.findViewById(R.id.red_point)?.visibility = View.GONE
        }
    }
}
