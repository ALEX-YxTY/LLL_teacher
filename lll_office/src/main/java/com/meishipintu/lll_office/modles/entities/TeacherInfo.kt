package com.meishipintu.lll_office.modles.entities

import java.io.Serializable

/**
 * Created by Administrator on 2017/6/29.
 *
 * 主要功能：
 */
data class TeacherInfo(val id: Int, val uid: String, val tel: String, val password: String, val name: String
                       , val sex: Int, val birthday: String, val identityid: String, val course: String
                       , val style: String, val avatar: String, val certification: String, val create_time: String
                       , val grade: String, val score_number: Int, val total_score: Int):Serializable

