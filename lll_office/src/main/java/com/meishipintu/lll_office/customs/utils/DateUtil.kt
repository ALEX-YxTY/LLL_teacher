package com.meishipintu.lll_office.customs.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Administrator on 2017/7/7.
 *
 * 主要功能：
 */
object DateUtil{

    //timestamp -> MM-dd
    fun stampToDate(timeStamp: String): String {
        val simpleDateFormat = SimpleDateFormat("MM-dd")
        return simpleDateFormat.format(Date(timeStamp.toLong() * 1000))
    }

    //如果是今天之前的时间，返回timestamp -> MM-dd hh:mm，今天返回timeStamp -> 今天 hh:mm
    fun  stampToDate2(timeStamp: Long): String {
        val calanderTime = Calendar.getInstance()
        calanderTime.timeInMillis = timeStamp * 1000
        val calanderNow = Calendar.getInstance()
        if (calanderTime.get(Calendar.YEAR)<calanderNow.get(Calendar.YEAR)
                ||calanderTime.get(Calendar.MONTH)<calanderNow.get(Calendar.MONTH)
                ||calanderTime.get(Calendar.DAY_OF_MONTH)<calanderNow.get(Calendar.DAY_OF_MONTH)) {
            val simpleDateFormat = SimpleDateFormat("MM-dd HH:mm")
            return simpleDateFormat.format(Date(timeStamp * 1000))
        } else {
            return stampToDate3(timeStamp)
        }
    }

    //timestamp -> hh:mm
    fun  stampToDate3(timeStamp: Long): String {
        val simpleDateFormat = SimpleDateFormat("HH:mm")
        return "今天 ${simpleDateFormat.format(Date(timeStamp * 1000))}"
    }

    //timestamp -> MM-dd hh:mm:ss
    fun  stampToDate4(timeStamp: Long): String {
        val simpleDateFormat = SimpleDateFormat("MM-dd  HH:mm:ss")
        return simpleDateFormat.format(Date(timeStamp * 1000))
    }
}