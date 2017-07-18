package com.meishipintu.lll_office.views

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.bumptech.glide.Glide
import com.meishipintu.lll_office.Cookies
import com.meishipintu.lll_office.R
import com.meishipintu.lll_office.contract.InterviewContract
import com.meishipintu.lll_office.customs.CustomLabelMultiSelectCenterView
import com.meishipintu.lll_office.customs.CustomLabelSelectListener
import com.meishipintu.lll_office.modles.entities.DeliverInfo
import com.meishipintu.lll_office.presenters.DeliverPresenter

class EvaluateActivity : BasicActivity(),View.OnClickListener,InterviewContract.IView {

    val deliverInfo:DeliverInfo by lazy{ intent.getSerializableExtra("deliver") as DeliverInfo}
    val labelSelect = mutableListOf<Int>()
    val ratingBar:RatingBar by lazy{ findViewById(R.id.star) as RatingBar }
    val button: Button by lazy{ findViewById(R.id.bt_commit) as Button}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_evaluate)
        presenter = DeliverPresenter(this)
        initUI()
    }

    private fun initUI() {
        //获取学科字段
        val courses = Cookies.getConstant(2)
        //获取评价语
        val scores = Cookies.getConstant(8)
        //获取评价标签
        val labels = Cookies.getConstant(9)

        val teacher = deliverInfo.teacher
        val ivHead = findViewById(R.id.iv_head) as ImageView
        Glide.with(this).load(teacher.avatar).placeholder(R.drawable.teacher_default).error(R.drawable.teacher_default)
                .into(ivHead)
        val teacherName = findViewById(R.id.teacher_name) as TextView
        teacherName.text = teacher.name
        val tvCourse = findViewById(R.id.tv_course) as TextView
        tvCourse.text = courses[teacher.course]
        val tvEvaluate = findViewById(R.id.tv_evaluate) as TextView
        button.setOnClickListener(this)
        val multiSelect = findViewById(R.id.multi_select) as CustomLabelMultiSelectCenterView
        multiSelect.setData(labels)
        multiSelect.setListener(object :CustomLabelSelectListener{
            override fun select(index: Int) {
                labelSelect.add(index)
                if (labelSelect.size > 0) {
                    button.isEnabled = true
                }
            }
            override fun remove(index: Int) {
                labelSelect.remove(index)
                if (labelSelect.size == 0) {
                    button.isEnabled = false
                }
            }

            override fun isSelect(index: Int): Boolean {
                return labelSelect.contains(index)
            }

        })
        ratingBar.onRatingBarChangeListener = RatingBar.OnRatingBarChangeListener { ratingBar, rating, fromUser ->
            run {
                if (rating > 0) {
                    multiSelect.visibility = View.VISIBLE
                }
                if (fromUser) {
                    tvEvaluate.text = scores[rating.toInt()-1]
                }
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.bt_commit ->{
                if (ratingBar.rating < 1) {
                    toast("请先选择评分")
                }else if (labelSelect.size == 0) {
                    toast("请选择评语")
                } else {
                    (presenter as InterviewContract.IPresenter).updateDeliverStatus(deliverInfo.id
                            ,3,ratingBar.rating.toInt(),labelSelect.toArrayString())
                }
            }
        }
    }

    //from InterviewContract.IView
    override fun onError(e: String) {
        toast(e)
    }

    //from InterviewContract.IView
    override fun onStatusUpdateSuccess(status: Int) {
        toast("添加评价成功")
        this.finish()
    }

}

private fun <E> MutableList<E>.toArrayString(): String {
    var sbf = StringBuilder()
    for (data: E in this) {
        sbf.append("${data.toString()},")
    }
    sbf.deleteCharAt(sbf.lastIndex)
    return sbf.toString()
}
