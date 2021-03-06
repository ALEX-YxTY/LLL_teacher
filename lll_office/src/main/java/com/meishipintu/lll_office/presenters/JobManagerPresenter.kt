package com.meishipintu.lll_office.presenters

import com.meishipintu.lll_office.contract.JobDetailContract
import com.meishipintu.lll_office.contract.JobManagerContract
import com.meishipintu.lll_office.contract.NewJobContract
import com.meishipintu.lll_office.contract.TeacherDetailContract
import com.meishipintu.lll_office.modles.HttpApiClinet
import com.meishipintu.lll_office.modles.HttpCallback
import com.meishipintu.lll_office.modles.HttpResultFunc
import com.meishipintu.lll_office.modles.entities.HttpResult
import com.meishipintu.lll_office.modles.entities.JobInfo
import com.meishipintu.lll_office.views.BasicInviteView
import com.meishipintu.lll_office.views.BasicView

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
        //type=1 上线 type=2 下线
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
                        , grade: Int, grade_detail:Int, sex: Int, requireYear: Int, money: String
                        , certificate: Int, require: String,tel:String) {
        addSubscription(httpApi.addJobService(name,oid,workArea,workAddress,course,grade,grade_detail,sex
                ,requireYear,money,certificate,require,tel),object :HttpCallback<HttpResult<Any>>(){
            override fun onSuccess(result: HttpResult<Any>) {
                if (result.status == 1) {
                    (iView as NewJobContract.IView).onJobAddSucess()
                } else {
                    iView.onError(result.msg)
                }
            }

            override fun onFailure(msg: String?) {
                if (msg != null) {
                    iView.onError(msg)
                } else {
                    iView.onError("系统错误，请稍后重试")
                }
            }

        })
    }

    //获取职位详情
    override fun getJobDetail(pid: Int) {
        addSubscription(httpApi.getPositionDetailServie(pid).map(HttpResultFunc<JobInfo>())
                ,object :HttpCallback<JobInfo>(){
            override fun onSuccess(model: JobInfo) {
                (iView as JobDetailContract.IView).onJobDetailGet(model)
            }

            override fun onFailure(msg: String?) {
                if (msg != null) {
                    iView.onError(msg)
                }
            }

        })
    }

    //判断是否已投递，
    override fun isDieliverPosition(tid: String, pid: String) {
        addSubscription(httpApi.isDeliverPosition(tid,pid),object :HttpCallback<HttpResult<List<Any>>>(){
            override fun onSuccess(model: HttpResult<List<Any>>) {
                (iView as JobManagerContract.IView).onDeliverGet(model.status == 1 || model.status == 2, pid)
            }

            override fun onFailure(msg: String?) {
                if (msg != null) {
                    iView.onError("网络错误，请稍后重试")
                }
            }
        })
    }

    //主动邀约教师
    override fun inviteInterview(jobId: Int, tid: String, oid: String) {
        //type=2 机构主动邀请教师面试
        addSubscription(httpApi.sendResumeService(jobId,tid,oid,5).map(HttpResultFunc<Any>())
                ,object :HttpCallback<Any>(){
            override fun onSuccess(model: Any) {
                (iView as BasicInviteView).onInviteSuccess(jobId)
            }

            override fun onFailure(msg: String?) {
                if (msg != null) {
                    iView.onError(msg)
                }
            }
        })
    }

}