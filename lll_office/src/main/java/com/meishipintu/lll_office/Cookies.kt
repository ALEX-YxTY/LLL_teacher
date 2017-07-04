package com.meishipintu.lll_office

import android.content.Context
import android.content.SharedPreferences
import com.meishipintu.lll_office.modles.entities.UserInfo
import android.R.id.edit
import android.util.Base64
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

}