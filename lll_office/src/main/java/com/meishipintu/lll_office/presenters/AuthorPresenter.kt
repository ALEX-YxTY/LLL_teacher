package com.meishipintu.lll_office.presenters

import com.meishipintu.lll_office.Cookies
import com.meishipintu.lll_office.OfficeApplication
import com.meishipintu.lll_office.contract.AuthorContract
import com.meishipintu.lll_office.customs.utils.Encoder
import com.meishipintu.lll_office.modles.HttpApiClinet
import com.meishipintu.lll_office.modles.HttpCallback
import com.meishipintu.lll_office.modles.HttpResultFunc
import com.meishipintu.lll_office.modles.entities.UserInfo


/**
 * Created by Administrator on 2017/7/4.
 *
 * 主要功能：
 */
class AuthorPresenter(val iView:AuthorContract.IView):BasicPresenter(),AuthorContract.IPresenter {

    val callBack:HttpCallback<UserInfo> by lazy { object :HttpCallback<UserInfo>(){
        override fun onSuccess(userInfo: UserInfo) {
            Cookies.saveUserInfo(userInfo)
            OfficeApplication.userInfo = userInfo
            iView.onSuccess()
        }

        override fun onFailure(msg: String?) {
            if (msg != null) {
                iView.onError(msg)
            }
        }
    } }
    //注册
    override fun regist(tel: String, name: String, password: String, vcode: String) {
        addSubscription(HttpApiClinet.retrofit().registerService(tel,name,password,vcode).map(HttpResultFunc<UserInfo>())
                , callBack)
    }

    //登录
    override fun login(account: String, psw: String) {
        addSubscription(HttpApiClinet.retrofit().loginService(account, Encoder.md5(psw))
                .map(HttpResultFunc<UserInfo>()),callBack)
    }
}