package com.milai.lll_teacher.views

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.milai.lll_teacher.R
import com.milai.lll_teacher.custom.view.CustomLabelMultiSelectCenterView
import com.milai.lll_teacher.custom.view.CustomLabelSelectListener

class TestActivity : AppCompatActivity(), CustomLabelSelectListener{

    private val selectList = ArrayList<Integer>()
    private val btCommit by lazy { findViewById(R.id.bt_commit) as Button }

    private val evaluationSelect:CustomLabelMultiSelectCenterView by lazy {
        findViewById(R.id.multi_select) as CustomLabelMultiSelectCenterView
    }

    private val evaluationList:List<String> by lazy { listOf("认真","耐心","专业技能强","课堂效果好","送快递费金额偶像","幽默","测试label","测试","测试的labellabellabellabel") }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_evaluate)

        evaluationSelect.setData(evaluationList)
        evaluationSelect.setListener(this)
        btCommit.setOnClickListener({
            var stringBf = StringBuilder()
            for (item in selectList) {
                stringBf.append(evaluationList[item.toInt()])
            }
        })

    }

    override fun select(index: Int) {
        selectList.add(Integer(index))
        checkSelect()
    }

    override fun remove(index: Int) {
        selectList.remove(Integer(index))
        checkSelect()
    }

    override fun isSelect(index: Int ): Boolean {
        return selectList.contains(Integer(index))
    }

    fun checkSelect() {
        btCommit.isEnabled = (selectList.size > 0)
    }

}
