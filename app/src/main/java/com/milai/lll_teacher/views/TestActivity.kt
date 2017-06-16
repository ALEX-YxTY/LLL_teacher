package com.milai.lll_teacher.views

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.milai.lll_teacher.R
import com.milai.lll_teacher.custom.view.CustomLabelSelectView

class TestActivity : AppCompatActivity() {

    private val yearSelect: CustomLabelSelectView by lazy {
        findViewById(R.id.selectview_year) as CustomLabelSelectView
    }
    private val workYearSelect:CustomLabelSelectView by lazy {
        findViewById(R.id.selectview_workyear) as CustomLabelSelectView
    }
    private val educationSelect:CustomLabelSelectView by lazy {
        findViewById(R.id.selectview_education) as CustomLabelSelectView
    }
    private val yearList:List<String> by lazy { listOf("全部", "25-30岁", "30-35岁", "35-40岁", "40-45岁", "45-50岁", "50-55岁", "55岁以上") }
    private val workYearList:List<String> by lazy { listOf("全部", "一年以内", "1-3岁", "3-5岁", "5-10岁", "10年以上") }
    private val educationList:List<String> by lazy { listOf("全部", "中专及以下", "高中", "大专", "本科", "硕士", "博士") }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.frag_filtrate)

        yearSelect.setData(yearList)
        workYearSelect.setData(workYearList)
        educationSelect.setData(educationList)

        findViewById(R.id.bt_reset).setOnClickListener({
            yearSelect.setSelect(0)
            workYearSelect.setSelect(0)
            educationSelect.setSelect(0)
        })

        findViewById(R.id.bt_filtrate).setOnClickListener({
            Log.d("test","year: ${yearList[yearSelect.selectIndex]}, workYear: ${workYearList[workYearSelect.selectIndex]}" +
                    ", education: ${educationList[educationSelect.selectIndex]}")
        })

    }
}
