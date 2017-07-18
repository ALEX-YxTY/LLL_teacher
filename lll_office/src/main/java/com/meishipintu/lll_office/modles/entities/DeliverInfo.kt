package com.meishipintu.lll_office.modles.entities

import java.io.Serializable

/**
 * Created by Administrator on 2017/7/14.
 *
 * 主要功能：
 */
data class DeliverInfo(val id:Int,val pid:Int,val tid:String,var status:Int,val type:Int
                       ,val create_time:Long,val score:String,val evaluate:String,val oid:String
                       ,val postion:JobInfo,val teacher:TeacherInfo):Serializable