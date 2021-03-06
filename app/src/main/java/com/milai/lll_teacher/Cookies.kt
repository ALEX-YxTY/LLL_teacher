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
        return if (getPreference() != null) {
            getPreference()!!.getString("constant$type", "").split(",")
        } else {
            listOf()
        }

    }

    //获取职位搜索历史
    fun getJobHistory():MutableList<String> {
        if (getPreference() != null) {
            val historyString = getPreference()!!.getString("jobHistory", "")

            if (historyString == "") {
                return mutableListOf()

            } else {
                return historyString.split(",").toMutableList()
            }
        } else {
            return mutableListOf()
        }
    }

    //保存职位搜索历史
    fun saveJobHistory(history: Array<String>) {
        val editor = getPreference()?.edit()
        editor?.putString("jobHistory", history.toArrayString())
        editor?.apply()
    }

    //获取机构搜索历史
    fun getOfficeHistory():MutableList<String> {
        if (getPreference() != null) {
            val historyString = getPreference()!!.getString("officeHistory", "")

            if (historyString == "") {
                return mutableListOf()

            } else {
                return historyString.split(",").toMutableList()
            }
        } else {
            return mutableListOf()
        }
    }

    //保存机构搜索历史
    fun saveOfficeHistory(history: Array<String>) {
        val editor = getPreference()?.edit()
        editor?.putString("officeHistory", history.toArrayString())
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
        if (getPreference() != null) {
            val mesId = getPreference()!!.getInt("${uid}mesId", 0)
            return mesId
        } else {
            return 0
        }
    }

    //保存最新sysNoticeId
    fun saveNewestMesId(id: Int, uid: String) {
        val editor = getPreference()?.edit()
        editor?.putInt("${uid}mesId", id)
        editor?.apply()
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

    //获取id广告显示时间，未显示返回-1
    fun getAdShow(id: Int): Long {
        return if (getPreference() != null) {
            getPreference()!!.getLong("ad" + id, -1)
        } else {
            -1
        }
    }

    //记录id广告显示时间
    fun saveAdShow(id: Int) {
        val editor = getPreference()?.edit()
        editor?.putLong("ad" + id, System.currentTimeMillis())
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
