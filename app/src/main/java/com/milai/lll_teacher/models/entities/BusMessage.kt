package com.milai.lll_teacher.models.entities

import java.io.Serializable

/**
 * Created by Administrator on 2017/7/6.
 *
 * 主要功能：RxBus传递包装类，可传递当前消息类型以及1个int参数和1个string参数
 */
data class BusMessage(val type: Int, val arg1: Int = 0, val arg2: String = ""):Serializable
