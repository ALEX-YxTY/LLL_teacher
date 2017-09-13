package com.meishipintu.lll_office

import android.content.Context
import android.content.SharedPreferences
import android.util.Base64
import android.util.Log
import com.meishipintu.lll_office.modles.entities.JobInfo
import com.meishipintu.lll_office.modles.entities.UserInfo
import java.io.*


/**
 * Created by Administrator on 2017/7/4.
 *
 * 主要功能：
 */
object Cookies {

    fun getPreference():SharedPreferences? {
        return OfficeApplication.instance?.getSharedPreferences(OfficeApplication::class.java.`package`.name
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
     * 10求职状态
     * 11年级名称
     * 12 会员套餐介绍
     */
    fun getConstant(type: Int): List<String> {
        return if (getPreference() != null) {
            getPreference()!!.getString("constant$type", "").split(",")
        } else {
            listOf()
        }

    }

    fun getHistory():MutableList<String> {
        if (getPreference() != null) {
            val historyString = getPreference()!!.getString("history", "")

            if (historyString == "") {
                return mutableListOf()

            } else {
                return historyString.split(",").toMutableList()
            }
        } else {
            return mutableListOf()
        }
    }

    fun saveHistory(history: Array<String>) {
        val editor = getPreference()?.edit()
        editor?.putString("history", history.toArrayString())
        editor?.apply()
    }

    //获取最新sysNoticeId
    fun getNewestSysId(uid:String):Int {
        if (getPreference() != null) {
            val sysId = getPreference()!!.getInt("${uid}sysId", 0)
            return sysId
        } else {
            return 0
        }
    }

    //保存最新sysNoticeId
    fun saveNewestSysId(id: Int,uid:String) {
        val editor = getPreference()?.edit()
        editor?.putInt("${uid}sysId", id)
        editor?.apply()
    }

    //获取最新MessageId
    fun getNewestMesId(uid: String): Int {
        return if (getPreference() != null) {
            val mesId = getPreference()!!.getInt("${uid}mesId", 0)
            mesId
        } else {
            0
        }
    }

    //保存最新sysNoticeId
    fun saveNewestMesId(id: Int, uid: String) {
        val editor = getPreference()?.edit()
        editor?.putInt("${uid}mesId", id)
        editor?.apply()
    }

    //保存最后发布的职位
    fun saveJobAdd(job: JobInfo) {
        val editor = getPreference()?.edit()
        // 创建字节输出流
        val baos = ByteArrayOutputStream()
        try {
            // 创建对象输出流，并封装字节流
            val oos = ObjectOutputStream(baos)
            // 将对象写入字节流
            oos.writeObject(job)
            // 将字节流编码成base64的字符窜
            val ojob_Base64 = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT)
            editor?.putString("newJob", ojob_Base64)
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

    //获取保存的新发布职位
    fun getJobAdd(): JobInfo? {
        var job: JobInfo? = null
        val sharedPreferences = getPreference()
        val productBase64 = sharedPreferences?.getString("newJob", "")

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
                    job = bis.readObject() as JobInfo?
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
        return job
    }

    fun isFirstLogin(): Boolean {
        return if (getPreference() != null) {
            val isFirstLogin = getPreference()!!.getBoolean("isFirstLogin", true)
            isFirstLogin
        } else {
            true
        }
    }

    fun setFirstLogin() {
        val editor = getPreference()?.edit()
        editor?.putBoolean("isFirstLogin", false)
        editor?.apply()
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
