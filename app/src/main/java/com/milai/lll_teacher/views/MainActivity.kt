package com.milai.lll_teacher.views

import android.content.Intent
import android.content.pm.PackageManager
import android.support.design.widget.TabLayout
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import cn.jpush.android.api.JPushInterface
import com.milai.lll_teacher.*
import com.milai.lll_teacher.contracts.NoticeContract
import com.milai.lll_teacher.custom.util.DateUtil
import com.milai.lll_teacher.custom.util.DialogUtils
import com.milai.lll_teacher.custom.util.MyAsy
import com.milai.lll_teacher.custom.view.AdPop
import com.milai.lll_teacher.models.entities.*
import com.milai.lll_teacher.presenters.NoticePresenter
import com.milai.lll_teacher.views.adapters.MyviewPagerAdapter
import com.milai.lll_teacher.views.fragments.*
import com.tencent.bugly.Bugly
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class MainActivity : BasicActivity(),NoticeContract.IView {

    val disposables: CompositeDisposable by lazy{ CompositeDisposable() }

    val vp:ViewPager by lazy { findViewById(R.id.vp) as ViewPager }
    val tabLayout:TabLayout by lazy { findViewById(R.id.tabLayout) as TabLayout }
    var clickTime: Long = 0

    var downLoadUrl:String?=null    //标记新版本的下载地址
    var versionName:String?=null    //标记最新版版本名

    var newestSysId = -2         //记录获取到的最新系统信息id，默认-2，因为无消息时获取到为-1
    var newestMessId = -2        //记录获取到的最新私信信息id，默认-2，因为无消息时获取到为-1

    var firstLogin:Boolean = false  //是否是首次登陆
    val llmain:LinearLayout by lazy{ findViewById(R.id.ll_main) as LinearLayout}

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.milai.lll_teacher.R.layout.activity_main)
        presenter = NoticePresenter(this)
        (presenter as NoticeContract.IPresenter).getNewsetVersiton()
        firstLogin = intent.getBooleanExtra("firstLogin", false)
        RxBus.getObservable(BusMessage::class.java).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    (type) -> run{
                    when (type) {
                        Constant.LOGOUT -> {
                            vp.currentItem = 0
                            tabLayout.getTabAt(0)?.select()
                            //退出登录后启动注册登录页面，并退出主页
                            JPushInterface.setAlias(this@MainActivity, "", null)
                            startActivity(Intent(this,LoginAndRegistActivity::class.java))
                        }
                    }
                }
                },{},{},{
                    t: Disposable -> disposables.add(t)
                })
        initViewPager()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        //在window获取焦点时调用显示popupwindow，否则页面未渲染成功无法显示
        if (firstLogin) {
            //只有在首次登陆才调广告页面
            (presenter as NoticeContract.IPresenter).getAdvertisement()
        }
    }

    override fun onAdGet(info: AdInfo) {
        when (info.type) {
            1 -> {
                //只显示1次
                if (Cookies.getAdShow(info.id) < 0) {
                    //未显示过
                    showAd(info)
                    Cookies.saveAdShow(info.id)
                }
            }
            3 -> {
                //一天只显示一次
                Log.i("test","hasn't show?:"+checkTime(info.id))
                if (checkTime(info.id)) {
                    showAd(info)
                    Cookies.saveAdShow(info.id)
                }
            }
            2 -> {
                //每次登陆都显示
                showAd(info)
                Cookies.saveAdShow(info.id)
            }
        }
        firstLogin = false
    }

    //检查上次广告播放时间
    private fun checkTime(id: Int): Boolean {

        return if (Cookies.getAdShow(id) < 0) {
            //没有播放过
            true
        }else DateUtil.compileTime(Cookies.getAdShow(id))
    }

    //显示广告dialog
    private fun showAd(info: AdInfo) {
        val popAd = AdPop(this, info.img)
        popAd.showPop(llmain)
    }

    override fun onVersionGet(versionInfo: VersionInfo) {
        val packageInfo = this.packageManager.getPackageInfo(this.packageName, 0)
        val versionCodeNow = packageInfo.versionCode
        if (versionInfo.verison_name > versionCodeNow) {
            DialogUtils.showCustomDialog(this,"发现新版本","最新版本：${versionInfo.verison} " +
                    "\n 更新内容：${versionInfo.verison_detail}", { dialog, _ ->
                dialog.dismiss()
                downLoadUrl = versionInfo.download_url
                versionName = versionInfo.verison
                downLoadWapper()
            })
        }
    }

    //下载权限封装方法
    fun downLoadWapper() {
        val hasStoragePermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)

        if (hasStoragePermission != PackageManager.PERMISSION_GRANTED) {        //未授权
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {                    //系统申请权限框不再弹出
                DialogUtils.showCustomDialog(this, "本应用需要获取内存读写的权限", { dialog, _ ->
                    ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            , Constant.REQUEST_STORAGE_PERMISSION)
                    dialog.dismiss()
                }) { dialog, _ -> dialog.dismiss() }
                return
            }
            //系统框弹出时直接申请
            ActivityCompat.requestPermissions(this, arrayOf(android
                    .Manifest.permission.WRITE_EXTERNAL_STORAGE), Constant.REQUEST_STORAGE_PERMISSION)
            return
        }
        if (downLoadUrl != null) {
            MyAsy(this,versionName?:"last").execute(downLoadUrl)
        }
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
        for (it: Int in 0 until dataList.size) {
            val tab = tabLayout.newTab()
            tab.setCustomView(R.layout.item_tab)
            val textView = tab.customView?.findViewById(R.id.tv_icon) as TextView
            textView.text = nameList[it]
            val imageView = tab.customView?.findViewById(R.id.iv_icon) as ImageView
            imageView.setImageResource(iconList[it])
            tabLayout.addTab(tab)
        }
        //默认选择第一个
        tabLayout.getTabAt(0)?.select()
        vp.addOnPageChangeListener(object :ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                tabLayout.getTabAt(position)?.select()
            }

        })
        tabLayout.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (MyApplication.userInfo == null && (tab?.position == 2 || tab?.position == 3)) {
                    vp.currentItem = 0
                    tabLayout.getTabAt(0)?.select()
                    startActivity(Intent(this@MainActivity,LoginAndRegistActivity::class.java))
                } else {
                    vp.currentItem = tab?.position?:0
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        if (MyApplication.userInfo != null) {
            (presenter as NoticeContract.IPresenter).getNewestMessId(MyApplication.userInfo?.uid!!)
            (presenter as NoticeContract.IPresenter).getNewestSysId(MyApplication.userInfo?.uid!!)
        } else {
            //隐藏红点
            tabLayout.getTabAt(2)?.customView?.findViewById(R.id.red_point)?.visibility = View.GONE
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

    //改变红点显示
    private fun changeRedPoint() {
        if (newestMessId > Cookies.getNewestMesId(MyApplication.userInfo?.uid!!) || newestSysId > Cookies.getNewestSysId(MyApplication.userInfo?.uid!!)) {
            //显示红点
            tabLayout.getTabAt(2)?.customView?.findViewById(R.id.red_point)?.visibility = View.VISIBLE
        } else {
            //隐藏红点
            tabLayout.getTabAt(2)?.customView?.findViewById(R.id.red_point)?.visibility = View.GONE
        }
    }

    override fun onNewestMessIdGet(id: Int) {
//        Log.d("test","main activity messageId get :Cookies get ${Cookies.getNewestMesId(MyApplication.userInfo?.uid!!)}, net get $id while uid=${MyApplication.userInfo?.uid}")
        //记录最新id
        newestMessId = id
        changeRedPoint()
    }


    override fun onNewestSysIdGet(id: Int) {
//        Log.d("test","main activity sysNoticeId get :Cookies get ${Cookies.getNewestSysId(MyApplication.userInfo?.uid!!)}, net get $id while uid=${MyApplication.userInfo?.uid}")
        newestSysId = id
        changeRedPoint()

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

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            Constant.REQUEST_STORAGE_PERMISSION ->{
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    //拒绝授权
                    Toast.makeText(this, "没有内存读写权限，将有部分功能无法使用，请在系统设置中增加应用的相应授权", Toast.LENGTH_SHORT)
                            .show()
                } else {
                    downLoadWapper()
                }
            }
        }
    }
}
