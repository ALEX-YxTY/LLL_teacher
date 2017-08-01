package com.meishipintu.lll_office.modles.entities

import java.io.Serializable

/**
 * Created by Administrator on 2017/8/1.
 *
 * 主要功能：
 */
data class NewsInfo(val id: Int, val title: String, val logo: String, val content: String
                    , val sub_title: String):Serializable
