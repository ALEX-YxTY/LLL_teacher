package com.meishipintu.lll_office.views

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.meishipintu.lll_office.Constant
import com.meishipintu.lll_office.Cookies
import com.meishipintu.lll_office.R
import com.meishipintu.lll_office.RxBus
import com.meishipintu.lll_office.modles.entities.BusMessage
import com.meishipintu.lll_office.views.fragments.ActivityFrag
import com.meishipintu.lll_office.views.fragments.MineFrag
import com.meishipintu.lll_office.views.fragments.NewsFrag
import com.meishipintu.lll_office.views.fragments.TeacherFrag
import com.meishipintu.lll_office.views.adapters.MyviewPagerAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    val disposables: CompositeDisposable by lazy{ CompositeDisposable() }

    val vp: ViewPager by lazy { findViewById(R.id.vp) as ViewPager }
    val tabLayout: TabLayout by lazy { findViewById(R.id.tabLayout) as TabLayout }
    var clickTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        RxBus.getObservable(BusMessage::class.java).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    (type) -> run{
                    when (type) {
                        Constant.EXIT -> this@MainActivity.finish()
                        Constant.LOGOUT -> startActivity(Intent(this,LoginAndRegisterActivity::class.java))
                    }
                }
                },{},{},{
                    t: Disposable -> disposables.add(t)
                })
        if (Cookies.getUserInfo() != null) {
            initViewPager()
        } else {
            startActivity(Intent(this,LoginAndRegisterActivity::class.java))
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (Cookies.getUserInfo() != null) {
            initViewPager()
        }
    }

    private fun initViewPager() {
        val iconList = listOf(R.drawable.selector_icon_news, R.drawable.selector_icon_teacher
                , R.drawable.selector_icon_activity, R.drawable.selector_icon_mine)
        val nameList = listOf("资讯", "教师", "活动", "我的")

        val newsFrag = NewsFrag()
        val teacherFrag = TeacherFrag()
        val activityFrag = ActivityFrag()
        val mineFrag = MineFrag()

        val dataList = listOf(newsFrag, teacherFrag, activityFrag, mineFrag)
        vp.adapter = MyviewPagerAdapter(supportFragmentManager, dataList)
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
            Toast.makeText(this,"再次点击退出程序",Toast.LENGTH_SHORT).show()
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
