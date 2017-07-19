package com.meishipintu.lll_office.modles.entities

import java.io.Serializable

/**
 * Created by Administrator on 2017/6/22.
 *
{
"id": "13",
"uid": "c51ce410c124a10e0db5e4b97fc2af39",
"tel": "15205148532",
"name": "yxty",
"password": "ca78b952bb54739a1fc0cd8832b61b6f",
"address": null,
"contact": null,
"contact_tel": null,
"introduce_detail": null,
"avatar": null,
"level": "0",
"certification": null,
"create_time": "1499336257",
"limit_time": "0",
"job_time_remain": "0",
"interview_time_remain": "0"
}
 *
 * 主要功能：
 */
data class OfficeInfo(val id: Int, val uid: String, val tel: String, val name: String, val address: String
                      , val contact: String, val contact_tel: String, val introduce_detail: String
                      , val avatar: String, val level: Int, val certification: String, val limit_time: String
                      , val job_time_remain: Int, val interview_time_remain: Int,val count:Int
                      , val postion:JobInfo): Serializable