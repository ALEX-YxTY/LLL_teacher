package com.meishipintu.lll_office.modles.entities

import java.io.Serializable

/**
 * Created by Administrator on 2017/8/18.
 *
 * 主要功能：
 */
data class VersionInfo(val verison:String, val verison_name:Int,val verison_detail:String,val download_url:String)
    :Serializable