package com.meishipintu.lll_office.views.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.meishipintu.lll_office.*
import com.meishipintu.lll_office.contract.MineContract
import com.meishipintu.lll_office.contract.NoticeActivityContract
import com.meishipintu.lll_office.customs.CircleImageView
import com.meishipintu.lll_office.modles.entities.BusMessage
import com.meishipintu.lll_office.modles.entities.UserInfo
import com.meishipintu.lll_office.modles.entities.VersionInfo
import com.meishipintu.lll_office.presenters.NoticePresenter
import com.meishipintu.lll_office.views.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by Administrator on 2017/6/29.
 *
 * 主要功能：
 */
class MineFrag:BasicFragment(),View.OnClickListener,NoticeActivityContract.IView,MineContract.IView{

    var fragView: View? = null
    val levelNow:Int? by lazy{ OfficeApplication.userInfo?.level}
    val uid = OfficeApplication.userInfo?.uid
    val redPoint: View? by lazy { fragView?.findViewById(R.id.red_point) }
    val disposables: CompositeDisposable by lazy{ CompositeDisposable() }

    var newestSysId = -2         //记录获取到的最新系统信息id，默认-2，因为无消息时获取到为-1
    var newestMessId = -2        //记录获取到的最新私信信息id，默认-2，因为无消息时获取到为-1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (presenter == null) {
            presenter = NoticePresenter(this)
        }
        RxBus.getObservable(BusMessage::class.java).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    (type) -> run{
                    when (type) {
                        Constant.UpdateSuccess ->{

                        }
                        Constant.EditInfo_Success ->{

                        }
                    }
                }
                },{},{},{
                    t: Disposable -> disposables.add(t)
                })
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (fragView == null) {
            fragView = inflater?.inflate(R.layout.frag_mine, container, false)
        }
        setListener()
        return fragView
    }

    private fun setListener() {
        fragView?.findViewById(R.id.rl_job_manage)?.setOnClickListener(this)
        fragView?.findViewById(R.id.rl_my_interview)?.setOnClickListener(this)
        fragView?.findViewById(R.id.rl_my_collection)?.setOnClickListener(this)
        fragView?.findViewById(R.id.rl_notice_center)?.setOnClickListener(this)
        fragView?.findViewById(R.id.rl_other_office)?.setOnClickListener(this)
        fragView?.findViewById(R.id.rl_setting)?.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
        if (uid != null) {
            Log.d("test","getInfo when visible")
            (presenter as NoticeActivityContract.IPresenter).getNewestSysId(uid!!)
            (presenter as NoticeActivityContract.IPresenter).getNewestMessId(uid)
            //刷新用户信息
            (presenter as MineContract.IPresenter).getUserInfo(uid)
        }
    }

    private fun initUI() {
        val levels = Cookies.getConstant(7)

        val title = fragView?.findViewById(R.id.tv_title) as TextView
        title.text = "我的"
        val headView = fragView?.findViewById(R.id.iv_head) as CircleImageView
        Glide.with(this).load(OfficeApplication.userInfo?.avatar).error(R.drawable.organization_default).into(headView)
        val userName = fragView?.findViewById(R.id.tv_user_name) as TextView
        userName.text = OfficeApplication.userInfo?.name
        val userLevel = fragView?.findViewById(R.id.tv_user_level) as TextView
        val timesRemain = fragView?.findViewById(R.id.tv_times_remain) as TextView
        when (OfficeApplication.userInfo?.status) {
            0 -> {
                userLevel.text = "正在审核中"
                timesRemain.visibility = View.INVISIBLE
            }
            2 -> {
                userLevel.text = "审核未通过"
                timesRemain.visibility = View.INVISIBLE
            }
            1 -> {
                if (levelNow!! > 0) {
                    userLevel.text = levels[levelNow!! - 1].substring(0, 4)
                } else {
                    userLevel.text = "普通会员"
                }
                timesRemain.text = "剩余发布职位：${OfficeApplication.userInfo?.job_time_remain}个   " +
                        "剩余邀约次数：${OfficeApplication.userInfo?.interview_time_remain}次"
            }
        }

        val userStates = fragView?.findViewById(R.id.iv_status) as LinearLayout
        if (OfficeApplication.userInfo?.status != 1) {
            userStates.visibility = View.GONE
        } else {
            userStates.setOnClickListener(this)
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.iv_status ->{
                //升级账号
                val intent = Intent(this.activity, UpdateActivity::class.java)
                intent.putExtra("levelNow", levelNow)
                startActivity(intent)
            }
            R.id.rl_job_manage ->{
                //职位管理
                startActivity(Intent(this.activity, JobManagerActivity::class.java))
            }
            R.id.rl_my_interview ->{
                //我的面试
                startActivity(Intent(this.activity,MyInterviewActivity::class.java))
            }
            R.id.rl_my_collection ->{
                //我的收藏
                val intent = Intent(this.activity, CollectionActivity::class.java)
                startActivity(intent)
            }
            R.id.rl_notice_center ->{
                //消息中心
                startActivity(Intent(this.activity, NoticeActivity::class.java))
            }
            R.id.rl_other_office ->{
                //其他机构
                val intent = Intent(this.activity, OtherOrganizationActivity::class.java)
                startActivity(intent)
            }
            R.id.rl_setting ->{
                //设置
                startActivity(Intent(this.activity,SettingActivity::class.java))
            }
        }
    }

    override fun onError(e: String) {
        toast(e)
    }

    //改变红点显示
    private fun changeRedPoint() {
        if (newestMessId > Cookies.getNewestMesId(uid!!) || newestSysId > Cookies.getNewestSysId(uid)) {
            //显示红点
            redPoint?.visibility = View.VISIBLE
        } else {
            //隐藏红点
            redPoint?.visibility = View.GONE
        }
    }

    override fun onNewestMessIdGet(id: Int) {
//        Log.d("test","sys id get=$id, id save is ${Cookies.getNewestMesId(uid!!)}")
        newestMessId = id
        changeRedPoint()
    }

    override fun onNewestSysIdGet(id: Int) {
//        Log.d("test","sys id get=$id, id save is ${Cookies.getNewestSysId(uid!!)}")
        newestSysId = id
        changeRedPoint()
    }

    override fun onVersionGet(versionInfo: VersionInfo) {
        //空实现
    }

    override fun onUserInfoGet(userInfo: UserInfo) {
        Cookies.saveUserInfo(userInfo)
        OfficeApplication.userInfo = userInfo
        initUI()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }
}
