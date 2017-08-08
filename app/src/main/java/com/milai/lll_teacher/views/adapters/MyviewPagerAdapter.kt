package com.milai.lll_teacher.views.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.milai.lll_teacher.views.fragments.BasicFragment

/**
 * Created by Administrator on 2017/6/21.
 *
 *
 * 主要功能：
 */

class MyviewPagerAdapter(fm: FragmentManager,val dataList:List<BasicFragment>) : FragmentPagerAdapter(fm) {

    override fun getItem(i: Int): Fragment? {
        return dataList[i]
    }

    override fun getCount(): Int {
        return dataList.size
    }
}
