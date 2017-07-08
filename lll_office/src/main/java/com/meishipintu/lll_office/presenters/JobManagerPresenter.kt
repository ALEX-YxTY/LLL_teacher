package com.meishipintu.lll_office.presenters

import com.meishipintu.lll_office.contract.JobDetailContract
import com.meishipintu.lll_office.contract.JobManagerContract
import com.meishipintu.lll_office.modles.HttpApiClinet
import com.meishipintu.lll_office.modles.HttpCallback
import com.meishipintu.lll_office.modles.HttpResultFunc
import com.meishipintu.lll_office.modles.entities.JobInfo
import com.meishipintu.lll_office.views.BasicView

/**
 * Created by Administrator on 2017/7/6.
 *
 * 功能介绍：
 */
class JobManagerPresenter(val iView:BasicView):BasicPresenter()
        , JobManagerContract.IPresenter,JobDetailContract.IPresenter{

    val httpApi = HttpApiClinet.retrofit()

    override fun getDataList(uid: String, status: Int) {
        addSubscription(httpApi.getOfficeJobService(uid,status).map(HttpResultFunc<List<JobInfo>>())
                ,object :HttpCallback<List<JobInfo>>(){
            override fun onSuccess(model: List<JobInfo>) {
                (iView as JobManagerContract.IView).onDateGet(model)
            }

            override fun onFailure(msg: String?) {
                if (msg != null) {
                    (iView as JobManagerContract.IView).onError(msg)
                }
            }
        })
    }

    override fun changeStatus(jobId: String, type: Int) {
        addSubscription(httpApi.changeJobStatusService(jobId,type).map(HttpResultFunc<JobInfo>())
        ,object :HttpCallback<JobInfo>(){

            override fun onFailure(msg: String?) {
                if (msg != null) {
                    (iView as JobDetailContract.IView).onError(msg)
                }
            }

            override fun onSuccess(model: JobInfo) {
                (iView as JobDetailContract.IView).onStatusChanged(model.status)
            }

        })
    }
}