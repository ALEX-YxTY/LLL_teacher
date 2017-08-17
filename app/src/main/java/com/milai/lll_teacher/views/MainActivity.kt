package com.milai.lll_teacher.views

import android.content.Intent
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.milai.lll_teacher.*
import com.milai.lll_teacher.contracts.NoticeContract
import com.milai.lll_teacher.models.entities.BusMessage
import com.milai.lll_teacher.models.entities.MessageNoticeInfo
import com.milai.lll_teacher.models.entities.SysNoticeInfo
import com.milai.lll_teacher.presenters.NoticePresenter
import com.milai.lll_teacher.views.adapters.MyviewPagerAdapter
import com.milai.lll_teacher.views.fragments.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class MainActivity : BasicActivity(),NoticeContract.IView {

    val disposables: CompositeDisposable by lazy{ CompositeDisposable() }

    val vp:ViewPager by lazy { findViewById(R.id.vp) as ViewPager }
    val tabLayout:TabLayout by lazy { findViewById(R.id.tabLayout) as TabLayout }
    val uid = MyApplication.userInfo?.uid!!
    var clickTime: Long = 0

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.milai.lll_teacher.R.layout.activity_main)
        presenter = NoticePresenter(this)
        RxBus.getObservable(BusMessage::class.java).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    (type) -> run{
                    when (type) {
                        Constant.LOGOUT -> {
                            //退出登录后启动注册登录页面，并退出主页
                            startActivity(Intent(this,LoginAndRegistActivity::class.java))
                            this.finish()
                        }
                    }
                }
                },{},{},{
                    t: Disposable -> disposables.add(t)
                })
        initViewPager()
    }


    private fun initViewPager() {
        val iconList = listOf(R.drawable.selector_icon_job, R.drawable.selector_icon_office
                , R.drawable.selector_icon_notice, R.drawable.selector_icon_mine)
        val nameList = listOf("职位", "机构", "消息", "我的")

        val jobFrag = JobFragment()
        val officeFrag = OfficeFrag()
        val noticeFrag = NoticeFrag()
        val mineFrag = MineFrag()
        val dataList = listOf(jobFrag, officeFrag, noticeFrag, mineFrag)
        vp.adapter = MyviewPagerAdapter(supportFragmentManager, dataList as List<BasicFragment>)
        tabLayout.setupWithViewPager(vp)
        for (it: Int in 0..tabLayout.tabCount - 1) {
            val tab = tabLayout.getTabAt(it)
            tab?.setCustomView(R.layout.item_tab)
            val textView = tab?.customView?.findViewById(R.id.tv_icon) as TextView
            textView.text = nameList[it]
            val imageView = tab.customView?.findViewById(R.id.iv_icon) as ImageView
            imageView.setImageResource(iconList[it])
        }
    }

    override fun onResume() {
        super.onResume()
        (presenter as NoticeContract.IPresenter).getNewestMessId(uid)
        (presenter as NoticeContract.IPresenter).getNewestSysId(uid)
    }

    override fun onBackPressed() {
        if (System.currentTimeMillis() - clickTime > 1000) {
            Toast.makeText(this,"再次点击退出程序", Toast.LENGTH_SHORT).show()
            clickTime = System.currentTimeMillis()
        } else {
            this.finish()
            RxBus.send(BusMessage(Constant.EXIT))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }

    override fun onNewestMessIdGet(id: Int) {
        if (id > 0 && id > Cookies.getNewestMesId(uid)) {
            //显示红点
            tabLayout.getTabAt(2)?.customView?.findViewById(R.id.red_point)?.visibility = View.VISIBLE
        } else {
            //隐藏红点
            tabLayout.getTabAt(2)?.customView?.findViewById(R.id.red_point)?.visibility = View.GONE
        }
    }

    override fun onNewestSysIdGet(id: Int) {
        if (id > 0 && id > Cookies.getNewestSysId(uid)) {
            //显示红点
            tabLayout.getTabAt(2)?.customView?.findViewById(R.id.red_point)?.visibility = View.VISIBLE
        } else {
            //隐藏红点
            tabLayout.getTabAt(2)?.customView?.findViewById(R.id.red_point)?.visibility = View.GONE
        }
    }

    override fun showError(err: String) {
        toast(err)
    }

    override fun onLoadError() {
        //none
    }

    override fun onSysNoticeGet(dataList: List<SysNoticeInfo>, page: Int) {
        //none
    }

    override fun onMessageNoticeGet(dataList: List<MessageNoticeInfo>, page: Int) {
        //none
    }
}
