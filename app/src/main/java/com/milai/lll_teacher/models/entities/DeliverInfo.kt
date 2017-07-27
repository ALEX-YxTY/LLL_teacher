package com.milai.lll_teacher.models.entities

import java.io.Serializable

/**
 * Created by Administrator on 2017/7/14.
 *
 * 主要功能：
 */
data class DeliverInfo(val id:Int,val pid:Int,val tid:String,var status:Int,val type:Int
                       ,val create_time:Long,val score:String,val evaluate:String,val oid:String
                       ,val position:JobInfo,val organization:OrganizationBasic):Serializable