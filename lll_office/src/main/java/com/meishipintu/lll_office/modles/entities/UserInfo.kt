package com.meishipintu.lll_office.modles.entities

import java.io.Serializable

/**
 * Created by Administrator on 2017/7/3.
 *
 * 功能介绍：
 */
data class UserInfo(val id: Int,val uid: String,  val tel: String, val name: String, val address: String
                    , val contact: String, val contact_tel: String, val introduce_detail: String, val avatar: String
                    , val level: Int, val certification: String, val create_time: Long, val limit_time: Long
                    , val job_time_remain: Int, val interview_time_remain: Int):Serializable
