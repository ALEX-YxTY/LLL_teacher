package com.meishipintu.lll_office.customs.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Administrator on 2017/7/7.
 *
 * 主要功能：
 */
object DateUtil{

    //timestamp -> mm-dd
    fun stampToDate(timeStamp: String): String {
        val simpleDateFormat = SimpleDateFormat("MM-dd")
        return simpleDateFormat.format(Date(timeStamp.toLong() * 1000))
    }
}