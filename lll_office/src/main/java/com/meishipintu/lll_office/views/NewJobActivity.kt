package com.meishipintu.lll_office.views

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.meishipintu.lll_office.Cookies
import com.meishipintu.lll_office.R
import com.meishipintu.lll_office.customs.utils.CustomNumPickeDialog

class NewJobActivity : BasicActivity(),View.OnClickListener {

    val courses = Cookies.getConstant(2)
    val grades = Cookies.getConstant(3)
    val areas = Cookies.getConstant(1)
    val experiences = Cookies.getConstant(5)

    var courseSelect = 0
    var gradeSelect = 0
    var areaSelect = 0
    var experienceSelect = 0


    val etJobName:EditText by lazy { findViewById(R.id.et_job_name)as EditText }
    val etMoney:EditText by lazy { findViewById(R.id.et_money)as EditText }
    val etAddress:EditText by lazy { findViewById(R.id.et_address)as EditText }
    val etJobRequire:EditText by lazy { findViewById(R.id.et_job_require)as EditText }
    val etOtherRequire:EditText by lazy { findViewById(R.id.et_other_require)as EditText }

    val tvCourse:TextView by lazy { findViewById(R.id.tv_course)as TextView }

    var dialog: CustomNumPickeDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_job)
        val tvTitle = findViewById(R.id.tv_title) as TextView
        tvTitle.text = "发布职位"
        findViewById(R.id.bt_back).setOnClickListener(this)
        findViewById(R.id.rl_course).setOnClickListener(this)
        findViewById(R.id.rl_work_area).setOnClickListener(this)
        findViewById(R.id.rl_sex).setOnClickListener(this)
        findViewById(R.id.rl_work_year).setOnClickListener(this)
        findViewById(R.id.rl_certification).setOnClickListener(this)
        findViewById(R.id.bt_release).setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.bt_back -> onBackPressed()

            //选择科目
            R.id.rl_course -> {
                Log.d("test","couse is null= ${if(courses==null) courses==null else courses.size}")
                dialog = CustomNumPickeDialog(this@NewJobActivity, R.style.DialogNoAction, courses.toTypedArray(), object:CustomNumPickeDialog.OnOkClickListener{
                    override fun onOkClick(vlueChoose: Int) {
                        courseSelect = vlueChoose
                        tvCourse.text = courses[vlueChoose]
                        dialog?.dismiss()
                    }
                })
                dialog?.show()
            }
            //选择年级
            R.id.rl_grade ->{}
            //工作区域
            R.id.rl_work_area ->{}
            //性别
            R.id.rl_sex ->{}
            //教学经验要求
            R.id.rl_work_year ->{}
            //教师资格证有无
            R.id.rl_certification ->{}
            //发布职位
            R.id.bt_release ->{}
        }
    }
}
