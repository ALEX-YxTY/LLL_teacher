package com.meishipintu.lll_office.customs

/**
 * Created by Administrator on 2017/6/21.
 *
 * 主要功能：
 */
interface MenuClickListener {

    fun onTjClick(index: Int, name: String)

    fun onRequireSelect(indexYear: Int, indexCourse: Int, indexGrade: Int)

    fun onDismiss(type:Int) //1-tj 2-area 3-require
}