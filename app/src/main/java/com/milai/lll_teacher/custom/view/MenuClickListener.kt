package com.milai.lll_teacher.custom.view

/**
 * Created by Administrator on 2017/6/21.
 *
 * 主要功能：
 */
interface MenuClickListener {

    fun onTjClick(index: Int, name: String)

    fun onArerSelect(index: Int, name: String)

    fun onRequireSelect(indexCourse: Int, indexGrade: Int, indexExperience: Int)

    fun onDismiss(type:Int) //1-tj 2-area 3-require
}