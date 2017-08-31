package com.meishipintu.lll_office.presenters

import com.meishipintu.lll_office.Cookies
import com.meishipintu.lll_office.OfficeApplication
import com.meishipintu.lll_office.contract.AuthorContract
import com.meishipintu.lll_office.contract.ReSetPswContract
import com.meishipintu.lll_office.customs.utils.Encoder
import com.meishipintu.lll_office.modles.HttpApiClinet
import com.meishipintu.lll_office.modles.HttpApiStores
import com.meishipintu.lll_office.modles.HttpCallback
import com.meishipintu.lll_office.modles.HttpResultFunc
import com.meishipintu.lll_office.modles.entities.UserInfo
import com.meishipintu.lll_office.views.BasicView


/**
 * Created by Administrator on 2017/7/4.
 *
 * 主要功能：
 */
class AuthorPresenter(val iView:BasicView):BasicPresenter(),AuthorContract.IPresenter
        ,ReSetPswContract.IPresenter {

    val httpApi: HttpApiStores by lazy { HttpApiClinet.retrofit() }

    val callBack:HttpCallback<UserInfo> by lazy { object :HttpCallback<UserInfo>(){

        override fun onSuccess(userInfo: UserInfo) {
            //保存登录
            Cookies.saveUserInfo(userInfo)
            OfficeApplication.userInfo = userInfo
            (iView as AuthorContract.IView).onSuccess()
        }

        override fun onFailure(msg: String?) {
            if (msg != null) {
                iView.onError(msg)
            }
        }
    } }

    //注册
    override fun regist(tel: String, name: String, password: String, vcode: String) {
        addSubscription(httpApi.registerService(tel,name,password,vcode).map(HttpResultFunc<UserInfo>())
                , callBack)
    }

    //登录
    override fun login(account: String, psw: String) {
        addSubscription(httpApi.loginService(account, Encoder.md5(psw))
                .map(HttpResultFunc<UserInfo>()),callBack)
    }

    //修改密码
    override fun resetPsw(tel: String, vcode: String, psw: String) {
        addSubscription(httpApi.resetPswService(tel,vcode,psw).map(HttpResultFunc<Any>())
                ,object:HttpCallback<Any>(){
            override fun onSuccess(model: Any) {
                (iView as ReSetPswContract.IView).onReSetSuccsee()
            }

            override fun onFailure(msg: String?) {
                if (msg != null) {
                    iView.onError(msg)
                }
            }

        })
    }
}