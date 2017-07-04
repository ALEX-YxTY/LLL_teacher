package com.meishipintu.lll_office.presenters

import com.meishipintu.lll_office.Cookies
import com.meishipintu.lll_office.OfficeApplication
import com.meishipintu.lll_office.contract.SetPswContract
import com.meishipintu.lll_office.modles.HttpApiClinet
import com.meishipintu.lll_office.modles.HttpCallback
import com.meishipintu.lll_office.modles.HttpResultFunc
import com.meishipintu.lll_office.modles.entities.UserInfo


/**
 * Created by Administrator on 2017/7/4.
 *
 * 主要功能：
 */
class SetPswPresenter(val iView:SetPswContract.IView):BasicPresenter(),SetPswContract.IPresenter {

    override fun regist(tel: String, name: String, password: String, vcode: String) {
        addSubscription(HttpApiClinet.retrofit().registerService(tel,name,password,vcode).map(HttpResultFunc<UserInfo>())
                , object :HttpCallback<UserInfo>(){
            override fun onSuccess(userInfo: UserInfo) {
                Cookies.saveUserInfo(userInfo)
                OfficeApplication.userInfo = userInfo
                iView.onRegisterSuccsee(userInfo)
            }

            override fun onFailure(msg: String?) {
                if (msg != null) {
                    iView.onError(msg)
                }
            }
        })
    }

    override fun unsubscribe() {
        disposables.clear()
    }
}