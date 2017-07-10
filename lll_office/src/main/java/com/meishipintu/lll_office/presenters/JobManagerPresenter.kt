package com.meishipintu.lll_office.presenters

import android.util.Log
import com.meishipintu.lll_office.contract.JobDetailContract
import com.meishipintu.lll_office.contract.JobManagerContract
import com.meishipintu.lll_office.contract.NewJobContract
import com.meishipintu.lll_office.modles.HttpApiClinet
import com.meishipintu.lll_office.modles.HttpCallback
import com.meishipintu.lll_office.modles.HttpResultFunc
import com.meishipintu.lll_office.modles.entities.HttpResult
import com.meishipintu.lll_office.modles.entities.JobInfo
import com.meishipintu.lll_office.views.BasicView
import okhttp3.ResponseBody

/**
 * Created by Administrator on 2017/7/6.
 *
 * 功能介绍：
 */
class JobManagerPresenter(val iView:BasicView):BasicPresenter()
        , JobManagerContract.IPresenter,JobDetailContract.IPresenter
        ,NewJobContract.IPresenter{

    val httpApi = HttpApiClinet.retrofit()

    //查询机构职位
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

    //改变职位状态
    override fun changeStatus(jobId: String, type: Int) {
        addSubscription(httpApi.changeJobStatusService(jobId,type).map(HttpResultFunc<JobInfo>())
        ,object :HttpCallback<JobInfo>(){

            override fun onFailure(msg: String?) {
                if (msg != null) {
                    iView.onError(msg)
                }
            }

            override fun onSuccess(model: JobInfo) {
                (iView as JobDetailContract.IView).onStatusChanged(model.status)
            }
        })
    }

    override fun getOrganizationDetail(uid: String) {

    }

    //添加职位
    override fun addJob(name: String, oid: String, workArea: Int, workAddress: String, course: Int
                        , grade: Int, sex: Int, requireYear: Int, money: String, certificate: Int
                        , require: String, other: String) {
        addSubscription(httpApi.addJobService(name,oid,workArea,workAddress,course,grade,sex
                ,requireYear,money,certificate,require,other),object :HttpCallback<HttpResult<Any>>(){
            override fun onSuccess(result: HttpResult<Any>) {
                if (result.status == 1) {
                    (iView as NewJobContract.IView).onJobAddSucess()
                } else {
                    iView.onError(result.msg)
                }
            }

            override fun onFailure(msg: String?) {
                if (msg != null) {
                    iView .onError(msg)
                }
            }

        })
    }
}