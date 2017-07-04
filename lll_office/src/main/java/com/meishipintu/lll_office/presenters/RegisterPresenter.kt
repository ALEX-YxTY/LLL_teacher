package com.meishipintu.lll_office.presenters

import android.content.Context
import com.meishipintu.lll_office.contract.RegisterContract
import com.meishipintu.lll_office.modles.HttpApiClinet
import com.meishipintu.lll_office.modles.HttpCallback
import com.meishipintu.lll_office.modles.HttpResultFunc
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function

/**
 * Created by Administrator on 2017/7/3.
 *
 * 功能介绍：
 */
class RegisterPresenter(val iView:RegisterContract.IView):RegisterContract.IPresenter ,BasicPresenter(){

    override fun getVCode(mobile:String) {
        addSubscription(HttpApiClinet.retrofit().getVCodeService(mobile).map(HttpResultFunc<String>())
                ,object : HttpCallback<String>(){
            override fun onSuccess(vcode: String) {
                iView.onVCodeGet(vcode)
            }

            override fun onFailure(msg: String?) {
                if (msg != null) {
                    iView.onError(msg)
                }
            }
        })
    }
}