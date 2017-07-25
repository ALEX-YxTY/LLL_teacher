package com.milai.lll_teacher.models.entities

import java.io.Serializable

/**
 * Created by Administrator on 2017/6/12.
 *
 * 功能介绍：
 */
data class UserInfo(val id: Int, val uid: String, val tel: String, val name: String, val sex:Int
                    ,val birthday:String, val identityid:String, val course:Int, val avatar: String
                    , val certification: String, val create_time: Long, val grade: Int
                    , val email: String, val work_year: Int): Serializable