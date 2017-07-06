package com.meishipintu.lll_office.presenters

import com.meishipintu.lll_office.contract.JobManagerContract
import com.meishipintu.lll_office.modles.HttpApiClinet
import com.meishipintu.lll_office.modles.HttpCallback
import com.meishipintu.lll_office.modles.HttpResultFunc
import com.meishipintu.lll_office.modles.entities.JobInfo

/**
 * Created by Administrator on 2017/7/6.
 *
 * 功能介绍：
 */
class JobManagerPresenter(val iView:JobManagerContract.IView):BasicPresenter(), JobManagerContract.IPresenter{

    override fun getDataList(uid: String, status: Int) {
        addSubscription(HttpApiClinet.retrofit().getOfficeJobService(uid,status).map(HttpResultFunc<List<JobInfo>>())
                ,object :HttpCallback<List<JobInfo>>(){
            override fun onSuccess(model: List<JobInfo>) {
                iView.onDateGet(model)
            }

            override fun onFailure(msg: String?) {
                if (msg != null) {
                    iView.onError(msg)
                }
            }
        })
    }
}