package com.meishipintu.lll_office.views.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * Created by Administrator on 2017/6/21.
 *
 *
 * 主要功能：
 */

class MyviewPagerAdapter(fm: FragmentManager, val dataList:List<Fragment>) : FragmentPagerAdapter(fm) {

    override fun getItem(i: Int): Fragment? {
        return dataList[i]
    }

    override fun getCount(): Int {
        return dataList.size
    }
}
