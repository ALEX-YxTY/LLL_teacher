package com.milai.lll_teacher.views.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.milai.lll_teacher.R
import com.milai.lll_teacher.views.CollectionActivity

/**
 * Created by Administrator on 2017/6/21.
 *
 * 主要功能：
 */
class MineFrag : Fragment(),View.OnClickListener{
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
        val headView = fragView?.findViewById(R.id.iv_head) as ImageView
        headView.setOnClickListener(this)
        val userName = fragView?.findViewById(R.id.tv_user_name) as TextView
        val userLevel = fragView?.findViewById(R.id.tv_user_level) as TextView

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
                    //TODO 简历页
             }
            R.id.rl_apply_job -> {}
            R.id.rl_my_interview -> {}
            R.id.rl_my_job_collection -> {
                val intent = Intent(this.activity, CollectionActivity::class.java)
                intent.putExtra("from", 1)
                startActivity(intent)
            }
            R.id.rl_my_attention_office -> {
                val intent = Intent(this.activity, CollectionActivity::class.java)
                intent.putExtra("from", 2)
                startActivity(intent)
            }
            R.id.rl_setting -> {}
        }
    }
}