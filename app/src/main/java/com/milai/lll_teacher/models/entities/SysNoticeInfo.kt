package com.milai.lll_teacher.models.entities

import java.io.Serializable

/**
 * Created by Administrator on 2017/7/25.
 *
 * 主要功能：
 */
data class SysNoticeInfo(val id: Int, val title: String, val create_time: Long, val content: String
                         , val type:Int, val uid: String, val pid: Int, val xid: String):Serializable
