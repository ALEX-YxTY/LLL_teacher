package com.meishipintu.lll_office.modles.entities

import java.io.Serializable

/**
 * Created by Administrator on 2017/7/13.
 *
 * 主要功能：chatDetail 数据类的内部数据类
 */
data class Avatar(val organization_avatar:String,val teacher_avatar:String, val sex:Int):Serializable {
}