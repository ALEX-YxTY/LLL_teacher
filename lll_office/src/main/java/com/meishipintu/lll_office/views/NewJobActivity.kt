package com.meishipintu.lll_office.views

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.meishipintu.lll_office.Cookies
import com.meishipintu.lll_office.R
import com.meishipintu.lll_office.contract.NewJobContract
import com.meishipintu.lll_office.customs.utils.CustomNumPickeDialog
import com.meishipintu.lll_office.presenters.JobManagerPresenter

class NewJobActivity : BasicActivity(),View.OnClickListener,NewJobContract.IView {

    val courses = Cookies.getConstant(2)
    val grades = Cookies.getConstant(3)
    val areas = Cookies.getConstant(1)
    val experiences = Cookies.getConstant(5)
    val certificates = arrayOf("无要求", "有")
    val sexs = arrayOf("不限", "男", "女")

    var courseSelect = 0
    var gradeSelect = 0
    var areaSelect = 0
    var experienceSelect = 0
    var sexSelect = 0           //0-不限 1-男 2-女
    var certificateSelect = 0   //0-无要求 1-有


    private val etJobName: EditText by lazy { findViewById(R.id.et_job_name) as EditText }
    private val etMoney: EditText by lazy { findViewById(R.id.et_money) as EditText }
    private val etAddress: EditText by lazy { findViewById(R.id.et_address) as EditText }
    private val etJobRequire: EditText by lazy { findViewById(R.id.et_job_require) as EditText }
    private val etOtherRequire: EditText by lazy { findViewById(R.id.et_other_require) as EditText }

    val tvCourse: TextView by lazy { findViewById(R.id.tv_course) as TextView }
    val tvGrade: TextView by lazy { findViewById(R.id.tv_grade) as TextView }
    val tvWorkArea: TextView by lazy { findViewById(R.id.tv_work_area) as TextView }
    val tvWorkYear: TextView by lazy { findViewById(R.id.tv_work_year) as TextView }
    val tvSex: TextView by lazy { findViewById(R.id.tv_sex) as TextView }
    val tvCertification: TextView by lazy { findViewById(R.id.tv_certification) as TextView }

    var dialog: CustomNumPickeDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_job)
        presenter = JobManagerPresenter(this)
        val tvTitle = findViewById(R.id.tv_title) as TextView
        tvTitle.text = "发布职位"
        findViewById(R.id.bt_back).setOnClickListener(this)
        findViewById(R.id.rl_course).setOnClickListener(this)
        findViewById(R.id.rl_grade).setOnClickListener(this)
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
                dialog = CustomNumPickeDialog(this@NewJobActivity, R.style.DialogNoAction, courses.toTypedArray()
                        , CustomNumPickeDialog.OnOkClickListener { vlueChoose ->
                    courseSelect = vlueChoose
                    tvCourse.text = courses[vlueChoose]
                    dialog?.dismiss()
                })
                dialog?.show()
            }
            //选择年级
            R.id.rl_grade -> {
                dialog = CustomNumPickeDialog(this@NewJobActivity, R.style.DialogNoAction, grades.toTypedArray()
                        , CustomNumPickeDialog.OnOkClickListener { vlueChoose ->
                    gradeSelect = vlueChoose
                    tvGrade.text = grades[vlueChoose]
                    dialog?.dismiss()
                })
                dialog?.show()
            }
            //工作区域
            R.id.rl_work_area -> {
                dialog = CustomNumPickeDialog(this@NewJobActivity, R.style.DialogNoAction, areas.toTypedArray()
                        , CustomNumPickeDialog.OnOkClickListener { vlueChoose ->
                    areaSelect = vlueChoose
                    tvWorkArea.text = areas[vlueChoose]
                    dialog?.dismiss()
                })
                dialog?.show()
            }
            //性别
            R.id.rl_sex -> {
                dialog = CustomNumPickeDialog(this@NewJobActivity, R.style.DialogNoAction, sexs
                        , CustomNumPickeDialog.OnOkClickListener { vlueChoose ->
                    sexSelect = vlueChoose
                    tvSex.text = sexs[vlueChoose]
                    dialog?.dismiss()
                })
                dialog?.show()
            }
            //教学经验要求
            R.id.rl_work_year -> {
                dialog = CustomNumPickeDialog(this@NewJobActivity, R.style.DialogNoAction, experiences.toTypedArray()
                        , CustomNumPickeDialog.OnOkClickListener { vlueChoose ->
                    experienceSelect = vlueChoose
                    tvWorkYear.text = experiences[vlueChoose]
                    dialog?.dismiss()
                })
                dialog?.show()
            }
            //教师资格证有无
            R.id.rl_certification -> {
                dialog = CustomNumPickeDialog(this@NewJobActivity, R.style.DialogNoAction, certificates
                        , CustomNumPickeDialog.OnOkClickListener { vlueChoose ->
                    certificateSelect = vlueChoose
                    tvCertification.text = certificates[vlueChoose]
                    dialog?.dismiss()
                })
                dialog?.show()
            }
            //发布职位
            R.id.bt_release -> {
                //验证输入
                if (arrayOf(etJobName.text, etAddress.text, etMoney.text, etJobRequire.text).isNullOrEmpty()) {
                    toast("输入项不能为空")
                } else {
                    Log.d("require:","${etJobRequire.text.toString()} and ${etOtherRequire.text.toString()}")
                    (presenter as JobManagerPresenter).addJob(etJobName.text.toString(), Cookies.getUserInfo()!!.uid
                            , areaSelect, etAddress.text.toString(), courseSelect, gradeSelect, sexSelect
                            , experienceSelect, etMoney.text.toString(), certificateSelect, etJobRequire.text.toString()
                            , etOtherRequire.text.toString())
                }
            }
        }

    }

    override fun onJobAddSucess() {
        //添加职位成功
        toast("添加职位成功")
        setResult(Activity.RESULT_OK)
        this.finish()
    }

    override fun onError(e: String) {
        toast(e)
    }

    private fun <T> Array<T>.isNullOrEmpty(): Boolean {
        return this.any { it.toString().isNullOrEmpty() }
    }
}




