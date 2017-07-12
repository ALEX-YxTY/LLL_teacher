package com.milai.lll_teacher

import android.content.Context
import android.content.SharedPreferences
import android.util.Base64
import android.util.Log
import com.milai.lll_teacher.models.entities.UserInfo
import java.io.*


/**
 * Created by Administrator on 2017/7/4.
 *
 * 主要功能：
 */
object Cookies {

    fun getPreference():SharedPreferences? {
        return MyApplication.instance?.getSharedPreferences(MyApplication::class.java.`package`.name
                , Context.MODE_PRIVATE)
    }

    fun saveUserInfo(userInfo: UserInfo) {
        val editor = getPreference()?.edit()
        // 创建字节输出流
        val baos = ByteArrayOutputStream()
        try {
            // 创建对象输出流，并封装字节流
            val oos = ObjectOutputStream(baos)
            // 将对象写入字节流
            oos.writeObject(userInfo)
            // 将字节流编码成base64的字符窜
            val oAuth_Base64 = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT)
            editor?.putString("user", oAuth_Base64)
            editor?.apply()
            oos.close()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                baos.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    fun getUserInfo() :UserInfo?{
        var userInfo: UserInfo? = null
        val sharedPreferences = getPreference()
        val productBase64 = sharedPreferences?.getString("user", "")

        if (!productBase64.isNullOrEmpty()) {
            //读取字节
            val base64 = Base64.decode(productBase64, Base64.DEFAULT)
            //封装到字节流
            val bais = ByteArrayInputStream(base64)
            try {
                //再次封装
                val bis = ObjectInputStream(bais)
                try {
                    //读取对象
                    userInfo = bis.readObject() as UserInfo?
                } catch (e: ClassNotFoundException) {
                    e.printStackTrace()
                }
                bis.close()
            } catch (e: StreamCorruptedException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                try {
                    bais.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        return userInfo
    }

    fun clearUserInfo() {
        val editor = getPreference()?.edit()
        editor?.remove("user")
        editor?.apply()
    }

    fun saveConstant(type:Int,data:Array<String>){
        val editor = getPreference()?.edit()
        Log.d("constant", "type=$type and data=${data.toArrayString()}")
        editor?.putString("constant$type", data.toArrayString())
        editor?.apply()
    }

    /**
     * type取值：
     * 1 区域
     * 2 学科
     * 3年级
     * 4年龄范围
     * 5工作年限范围
     * 6学历
     * 7套餐
     * 8评价
     * 9评语
     */
    fun getConstant(type: Int): List<String> {
        if (getPreference() != null) {
            return getPreference()!!.getString("constant$type", "lsjdfkljs").split(",")
        } else {
            return listOf()
        }

    }

}

//扩展方法
private fun <T> Array<T>.toArrayString() :String{
    var sbf = StringBuilder()
    for (data: T in this) {
        sbf.append("${data.toString()},")
    }
    sbf.deleteCharAt(sbf.lastIndex)
    return sbf.toString()
}