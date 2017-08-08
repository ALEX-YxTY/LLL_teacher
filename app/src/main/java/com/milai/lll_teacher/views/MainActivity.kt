package com.milai.lll_teacher.views

import android.content.Intent
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.milai.lll_teacher.Constant
import com.milai.lll_teacher.R
import com.milai.lll_teacher.RxBus
import com.milai.lll_teacher.models.entities.BusMessage
import com.milai.lll_teacher.views.adapters.MyviewPagerAdapter
import com.milai.lll_teacher.views.fragments.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class MainActivity : android.support.v7.app.AppCompatActivity() {

    val disposables: CompositeDisposable by lazy{ CompositeDisposable() }

    val vp:ViewPager by lazy { findViewById(R.id.vp) as ViewPager }
    val tabLayout:TabLayout by lazy { findViewById(R.id.tabLayout) as TabLayout }

    var clickTime: Long = 0

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.milai.lll_teacher.R.layout.activity_main)
        RxBus.getObservable(BusMessage::class.java).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    (type) -> run{
                    when (type) {
//                        Constant.EXIT -> this@MainActivity.finish()
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
}
