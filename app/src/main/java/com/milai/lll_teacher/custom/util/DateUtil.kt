package com.milai.lll_teacher.custom.util

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
        if ((System.currentTimeMillis() / (1000 * 60 * 60 * 24) - (timeStamp / (60 * 60 * 24)) > 1)) {
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