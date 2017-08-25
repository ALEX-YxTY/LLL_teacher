package com.meishipintu.lll_office.modles.entities

import com.google.gson.annotations.SerializedName

/**
 * Created by Administrator on 2017/7/4.
 *
 * 主要功能：
 */
data class HttpResult<T> (
        @SerializedName("status") var status:Int
        ,@SerializedName("msg") var msg:String
        ,@SerializedName("data") var data:T){
    /**
     * Returns a string representation of the object.
     */
    override fun toString(): String {
        return "HttpResult: "
    }
}