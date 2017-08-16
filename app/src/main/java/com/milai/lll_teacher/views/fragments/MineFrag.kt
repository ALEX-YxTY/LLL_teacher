package com.milai.lll_teacher.views.fragments

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
import com.milai.lll_teacher.Cookies
import com.milai.lll_teacher.MyApplication
import com.milai.lll_teacher.R
import com.milai.lll_teacher.contracts.MineContract
import com.milai.lll_teacher.models.entities.UserInfo
import com.milai.lll_teacher.presenters.AuthorPresenter
import com.milai.lll_teacher.views.*

/**
 * Created by Administrator on 2017/6/21.
 *
 * 主要功能：
 */
class MineFrag : BasicFragment(),View.OnClickListener,MineContract.IView{

    var fragView: View? = null
    val Edit_RESUME = 200   //编辑简历页面requestCode

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (presenter == null) {
            presenter = AuthorPresenter(this)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (fragView == null) {
            fragView = inflater?.inflate(R.layout.frag_mine, container, false)
        }
        initUI()
        return fragView
    }

    private fun initUI() {
        val title = fragView?.findViewById(R.id.tv_title) as TextView
        title.text = "我的"
        val headView = fragView?.findViewById(R.id.iv_head) as ImageView
        Glide.with(this).load(MyApplication.userInfo?.avatar).error(R.drawable.teacher_default).into(headView)
        headView.setOnClickListener(this)
        val userName = fragView?.findViewById(R.id.tv_user_name) as TextView
        userName.text = MyApplication.userInfo?.name
        val userLevel = fragView?.findViewById(R.id.tv_user_level) as TextView
        val userStates = fragView?.findViewById(R.id.iv_status) as LinearLayout

        fragView?.findViewById(R.id.rl_my_resume)?.setOnClickListener(this)
        fragView?.findViewById(R.id.rl_apply_job)?.setOnClickListener(this)
        fragView?.findViewById(R.id.rl_my_interview)?.setOnClickListener(this)
        fragView?.findViewById(R.id.rl_my_job_collection)?.setOnClickListener(this)
        fragView?.findViewById(R.id.rl_my_attention_office)?.setOnClickListener(this)
        fragView?.findViewById(R.id.rl_setting)?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.iv_head, R.id.rl_my_resume -> {
                this@MineFrag.startActivityForResult(Intent(this.activity,ResumeEditActivity::class.java),Edit_RESUME)
             }
            R.id.rl_apply_job -> {
                val intent = Intent(this.activity, InterviewListActivity::class.java)
                intent.putExtra("from", 1)      //我的投递记录
                startActivity(intent)
            }
            R.id.rl_my_interview -> {
                val intent = Intent(this.activity, InterviewListActivity::class.java)
                intent.putExtra("from", 2)      //我的面试邀约
                startActivity(intent)
            }
            R.id.rl_my_job_collection -> {
                //收藏的职位
                val intent = Intent(this.activity, CollectionActivity::class.java)
                startActivity(intent)
            }
            R.id.rl_my_attention_office -> {
                //收藏的机构
                val intent = Intent(this.activity, OrganizationCollectionActivity::class.java)
                startActivity(intent)
            }
            R.id.rl_setting -> {
                startActivity(Intent(this.activity, SettingActivity::class.java))
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == Edit_RESUME) {
            (presenter as MineContract.IPresenter).getUserInfo(MyApplication.userInfo?.uid!!)
        }
    }

    //from MineContract.IView
    override fun showError(err: String) {
        toast(err)
    }

    //from MineContract.IView
    override fun onUserInfoGet(userInfo: UserInfo) {
        Cookies.saveUserInfo(userInfo)
        MyApplication.userInfo = userInfo
        initUI()
    }
}