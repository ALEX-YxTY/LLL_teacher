package com.meishipintu.lll_office.modles.entities

import java.io.Serializable

/**
 * Created by Administrator on 2017/7/25.
 *
 * 主要功能：
 */
data class MessageNoticeInfo(val chat_create_time: Long, val content: String, val organization_name: String
                             , val organization_avatar: String, val job_name: String, val work_area: Int
                             , val postion_create_time: Long, val money: String, val name: String
                             , val avatar: String, val id: Int, val pid: Int, val oid: String, val tid: String, val sex:Int):Serializable