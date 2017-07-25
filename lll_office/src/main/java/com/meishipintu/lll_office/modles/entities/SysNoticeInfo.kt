package com.meishipintu.lll_office.modles.entities

import java.io.Serializable

/**
 * Created by Administrator on 2017/7/25.
 *
 * 主要功能：
 */
data class SysNoticeInfo(val id:Int, val title: String, val create_time: Long, val content: String
                         ):Serializable