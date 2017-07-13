package com.milai.lll_teacher.models.entities

import java.io.Serializable

/**
 * Created by Administrator on 2017/7/13.
 *
 * 主要功能：
 */
data class ChatDetail(val id:Int,val pid:Int,val tid:String,val oid:String,val content:String
                      ,val type:Int,val create_time:Long, val avatar: Avatar):Serializable