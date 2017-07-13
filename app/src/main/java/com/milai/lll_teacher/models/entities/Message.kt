package com.milai.lll_teacher.models.entities

import java.io.Serializable

/**
 * Created by Administrator on 2017/6/23.
 *
 *  "id": "1",
"pid": "15",
"tid": "c81e728d9d4c2f636f067f89cc14862c",
"oid": "c51ce410c124a10e0db5e4b97fc2af39",
"content": "你好",
"type": "1",
"create_time": 0
 * 主要功能：
 */
data class Message(val id: Int, val pid: Int, val tid: String, val oid: String, val content: String
                   , val type: Int, val create_time: Long) :Serializable