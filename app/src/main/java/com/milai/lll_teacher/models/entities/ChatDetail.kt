package com.milai.lll_teacher.models.entities

import java.io.Serializable

/**
 * Created by Administrator on 2017/7/13.
 *  type=1 教师发给机构，type=2 机构发给教师
 *  sex=0 男， sex=1 女
 * 主要功能：聊天详情数据类
 */
data class ChatDetail(val id: Int, val pid: Int, val tid: String, val oid: String, val content: String
                      , val type: Int, val create_time: Long, val avatar: Avatar, val sex: Int):Serializable