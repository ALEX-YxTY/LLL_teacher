package com.meishipintu.lll_office.views

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.meishipintu.lll_office.*
import com.meishipintu.lll_office.contract.NoticeActivityContract
import com.meishipintu.lll_office.customs.utils.DialogUtils
import com.meishipintu.lll_office.customs.utils.MyAsy
import com.meishipintu.lll_office.modles.entities.BusMessage
import com.meishipintu.lll_office.modles.entities.VersionInfo
import com.meishipintu.lll_office.presenters.NoticePresenter
import com.meishipintu.lll_office.views.adapters.MyviewPagerAdapter
import com.meishipintu.lll_office.views.fragments.MineFrag
import com.meishipintu.lll_office.views.fragments.NewsFrag
import com.meishipintu.lll_office.views.fragments.TeacherFrag
import com.umeng.socialize.UMShareAPI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainActivity : BasicActivity(), NoticeActivityContract.IView {

    val disposables: CompositeDisposable by lazy{ CompositeDisposable() }

    val vp: ViewPager by lazy { findViewById(R.id.vp) as ViewPager }
    val tabLayout: TabLayout by lazy { findViewById(R.id.tabLayout) as TabLayout }
    var clickTime: Long = 0
    val uid = OfficeApplication.userInfo?.uid

    var downLoadUrl:String?=null    //标记新版本的下载地址
    var versionName:String?=null    //标记最新版版本名

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter = NoticePresenter(this)
        (presenter as NoticeActivityContract.IPresenter).getNewsetVersiton()
        RxBus.getObservable(BusMessage::class.java).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    (type) -> run{
                    when (type) {
                        Constant.LOGOUT -> {
                            startActivity(Intent(this,LoginAndRegisterActivity::class.java))
                            this.finish()
                        }
                    }
                }
                },{},{},{
                    t: Disposable -> disposables.add(t)
                })
        initViewPager()
    }

    override fun onResume() {
        Log.d("test", "uid $uid")
        super.onResume()
        if (uid != null) {
            (presenter as NoticeActivityContract.IPresenter).getNewestMessId(uid)
            (presenter as NoticeActivityContract.IPresenter).getNewestSysId(uid)
        }
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
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.CAMERA)) {                    //系统申请权限框不再弹出
                DialogUtils.showCustomDialog(this, "本应用需要获取使用相机权限", { dialog, _ ->
                    ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA)
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
        val iconList = listOf(R.drawable.selector_icon_news, R.drawable.selector_icon_teacher
                , R.drawable.selector_icon_mine)
        val nameList = listOf("资讯", "教师", "我的")

        val newsFrag = NewsFrag()
        val teacherFrag = TeacherFrag()
//        val activityFrag = ActivityFrag()
        val mineFrag = MineFrag()

        val dataList = listOf(newsFrag, teacherFrag, mineFrag)
        vp.adapter = MyviewPagerAdapter(supportFragmentManager, dataList as List<Fragment>)
        tabLayout.setupWithViewPager(vp)
        for (it: Int in 0 until tabLayout.tabCount) {
            val tab = tabLayout.getTabAt(it)
            tab?.setCustomView(R.layout.item_tab)
            val textView:TextView? = tab?.customView?.findViewById(R.id.tv_icon) as TextView?
            textView?.text = nameList[it]
            val imageView = tab?.customView?.findViewById(R.id.iv_icon) as ImageView
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

    override fun onError(e: String) {
        toast(e)
    }

    override fun onNewestMessIdGet(id: Int) {
        Log.d("test","main activity messageId get :Cookies get ${Cookies.getNewestMesId(uid!!)}, net get $id while uid-$uid")

        if (id > 0 && id > Cookies.getNewestMesId(uid)) {
            //显示红点
            tabLayout.getTabAt(2)?.customView?.findViewById(R.id.red_point)?.visibility = View.VISIBLE
        } else {
            //隐藏红点
            tabLayout.getTabAt(2)?.customView?.findViewById(R.id.red_point)?.visibility = View.GONE
        }
    }

    override fun onNewestSysIdGet(id: Int) {
        Log.d("test","main activity sysNoticeId get :Cookies get ${Cookies.getNewestSysId(uid!!)}, net get $id while uid-$uid")

        if (uid != null) {
            if (id > 0 && id > Cookies.getNewestSysId(uid)) {
                //显示红点
                tabLayout.getTabAt(2)?.customView?.findViewById(R.id.red_point)?.visibility = View.VISIBLE
            } else {
                //隐藏红点
                tabLayout.getTabAt(2)?.customView?.findViewById(R.id.red_point)?.visibility = View.GONE
            }
        }
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data)
    }
}
