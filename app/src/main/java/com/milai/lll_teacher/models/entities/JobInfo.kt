package com.milai.lll_teacher.models.entities

import java.io.Serializable

/**
 * Created by Administrator on 2017/6/22.
 *
"id": "5",
"oid": "c20ad4d76fe97759aa27a0c99bff6710","work_address": "古平岗1号","course": "1","require": "这是一个要求",
"money": "3300/月","require_year": "2","other_demand": "其他要求","status": "1",
"create_time": "1499335988","grade": "3","sex": "1","year": "33","work_area": "0","job_name": "一个职位",
"organization": {
    "id": "12","uid": "c20ad4d76fe97759aa27a0c99bff6710","tel": "13812345678",
    "name": "测试1","password": "25d55ad283aa400af464c76d713c07ad","address": null,
    "contact": null,"contact_tel": null,"introduce_detail": null,"avatar": null,
    "level": "0","certification": null,"create_time": "1499335925","limit_time": null,
    "job_time_remain": null,"interview_time_remain": null
}

 * 主要功能：
 */
data class JobInfo(val id: Int, val job_name: String, val oid: String, val work_area: Int, val work_address: String
                   , val course: Int, val grade: Int, val grade_detail: Int, val sex: Int, val year: Int, var status: Int
                   , val require_year: Int, val require: String, val money: String, val other_demand: String
                   , val organization: OfficeInfo, val have_certificate: Int, val create_time: String, val ll_count: Int, val tel:String): Serializable

