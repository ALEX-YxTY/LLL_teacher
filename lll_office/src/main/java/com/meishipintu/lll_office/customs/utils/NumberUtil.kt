package com.meishipintu.lll_office.customs.utils

import java.math.RoundingMode
import java.text.DecimalFormat

/**
 * Created by Administrator on 2017/7/1.
 *
 * 功能介绍：
 */
object NumberUtil{
    fun formatNumberInTwo(number:Int):String{
        val df = DecimalFormat("0.00")
        df.roundingMode = RoundingMode.FLOOR
        return df.format(number)
    }

    fun formatNumberInTwo(number:Float):String{
        val df = DecimalFormat("0.00")
        df.roundingMode = RoundingMode.HALF_UP
        return df.format(number)
    }

    fun formatNumberInOne(number:Double):String{
        val df = DecimalFormat("0.0")
        df.roundingMode = RoundingMode.FLOOR
        return df.format(number)
    }
}