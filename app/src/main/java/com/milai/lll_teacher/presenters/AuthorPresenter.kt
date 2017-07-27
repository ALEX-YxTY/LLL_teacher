package com.milai.lll_teacher.presenters

import com.milai.lll_teacher.contracts.ForgetPswContract
import com.milai.lll_teacher.contracts.LoginContract
import com.milai.lll_teacher.contracts.ResetPswContract
import com.milai.lll_teacher.models.entities.UserInfo
import com.milai.lll_teacher.models.https.HttpApiClinet
import com.milai.lll_teacher.models.https.HttpCallback
import com.milai.lll_teacher.models.https.HttpResultFunc
import com.milai.lll_teacher.views.BasicView

/**
 * Created by Administrator on 2017/7/20.
 *
 * 主要功能：
 */
class AuthorPresenter(val iView:BasicView) :BasicPresenter(),LoginContract.IPresenter
        ,ForgetPswContract.IPresenter,ResetPswContract.IPresenter{

    val httpApi = HttpApiClinet.retrofit()

    //登录
    override fun login(tel: String, psw: String) {
        addSubscription(httpApi.loginService(tel,psw).map(HttpResultFunc<UserInfo>())
                ,object : HttpCallback<UserInfo>(){

            override fun onSuccess(model: UserInfo) {
                (iView as LoginContract.IView).onLoginSuccess(model)
            }

            override fun onFailure(msg: String?) {
                iView.showError("手机号或密码错误")
            }
        })
    }

    //获取验证码
    override fun getVCode(tel: String) {
        addSubscription(httpApi.getVCodeService(tel).map(HttpResultFunc<String>())
                ,object :HttpCallback<String>(){
            override fun onSuccess(model: String) {
                (iView as ForgetPswContract.IView).onVCodeGet(model)
            }

            override fun onFailure(msg: String?) {
                if (msg != null) {
                    iView.showError(msg)
                }
            }
        })
    }

    //重设密码
    override fun resetPsw(tel: String, vcode: String, psw: String) {
        addSubscription(httpApi.resetPswService(tel,vcode,psw).map(HttpResultFunc<Any>())
                ,object:HttpCallback<Any>(){
            override fun onSuccess(model: Any) {
                (iView as ResetPswContract.IView).onReSetSuccsee()
            }

            override fun onFailure(msg: String?) {
                if (msg != null) {
                    iView.showError(msg)
                }
            }

        })
    }
}