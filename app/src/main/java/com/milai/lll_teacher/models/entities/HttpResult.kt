package com.milai.lll_teacher.models.entities

import com.google.gson.annotations.SerializedName

/**
 * Created by Administrator on 2017/7/4.
 *
 * 主要功能：
 */
data class HttpResult<T> (
        @SerializedName("status") var status:Int
        ,@SerializedName("msg") var msg:String
        ,@SerializedName("data") var data:T)