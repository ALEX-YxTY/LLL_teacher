package com.meishipintu.lll_office.views.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.meishipintu.lll_office.Cookies
import com.meishipintu.lll_office.OfficeApplication
import com.meishipintu.lll_office.R
import com.meishipintu.lll_office.customs.CircleImageView
import com.meishipintu.lll_office.views.*

/**
 * Created by Administrator on 2017/6/29.
 *
 * 主要功能：
 */
class MineFrag:Fragment(),View.OnClickListener{

    var fragView: View? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (fragView == null) {
            fragView = inflater?.inflate(R.layout.frag_mine, container, false)
            initUI()
        }
        return fragView
    }

    private fun initUI() {
        val title = fragView?.findViewById(R.id.tv_title) as TextView
        title.text = "我的"
        val headView = fragView?.findViewById(R.id.iv_head) as CircleImageView
        Glide.with(this).load(OfficeApplication.userInfo?.avatar).error(R.drawable.organization_default).into(headView)
        val userName = fragView?.findViewById(R.id.tv_user_name) as TextView
        userName.text = OfficeApplication.userInfo?.name
        val userLevel = fragView?.findViewById(R.id.tv_user_level) as TextView
        val timesRemain = fragView?.findViewById(R.id.tv_times_remain) as TextView
//        timesRemain.text =
        val userStates = fragView?.findViewById(R.id.iv_status) as LinearLayout
        if (Cookies.getUserInfo()?.level != 0) {
            userStates.visibility = View.GONE
        } else {
            userStates.setOnClickListener(this)
        }

        fragView?.findViewById(R.id.rl_job_manage)?.setOnClickListener(this)
        fragView?.findViewById(R.id.rl_my_interview)?.setOnClickListener(this)
        fragView?.findViewById(R.id.rl_my_collection)?.setOnClickListener(this)
        fragView?.findViewById(R.id.rl_notice_center)?.setOnClickListener(this)
        fragView?.findViewById(R.id.rl_other_office)?.setOnClickListener(this)
        fragView?.findViewById(R.id.rl_setting)?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.iv_status ->{
                //升级账号
                startActivity(Intent(this.activity, UpdateActivity::class.java))
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
}
