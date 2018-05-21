package com.milai.lll_teacher.presenters

import com.milai.lll_teacher.contracts.ChatDetailContract
import com.milai.lll_teacher.models.entities.ChatDetail
import com.milai.lll_teacher.models.entities.JobInfo
import com.milai.lll_teacher.models.entities.OfficeInfo
import com.milai.lll_teacher.models.https.HttpApiClinet
import com.milai.lll_teacher.models.https.HttpCallback
import com.milai.lll_teacher.models.https.HttpResultFunc
import com.milai.lll_teacher.views.BasicView
import com.milai.lll_teacher.views.BasicViewLoadError

/**
 * Created by Administrator on 2017/7/13.
 *
 * 主要功能：
 */
class MessagePresenter(val iView:BasicView) : BasicPresenter(),ChatDetailContract.IPresenter {

    val httpApi = HttpApiClinet.retrofit()

    //获取机构详情
    override fun getOfficeInfo(oid: String) {
        addSubscription(httpApi.getOrganizationDetaioService(oid).map(HttpResultFunc<OfficeInfo>())
                ,object: HttpCallback<OfficeInfo>(){
            override fun onSuccess(model: OfficeInfo) {
                (iView as ChatDetailContract.IView).onOfficeInfoGet(model)
            }

            override fun onFailure(msg: String?) {
                if (msg != null) {
                    iView.showError(msg)
                }
            }
        })
    }

    //获取职位详情
    override fun getJobDetailInfo(pid: Int) {
        addSubscription(httpApi.getPositionDetailServie(pid).map(HttpResultFunc<JobInfo>())
                ,object :HttpCallback<JobInfo>(){
            override fun onSuccess(model: JobInfo) {
                (iView as ChatDetailContract.IView).onJobInfoGet(model)
            }

            override fun onFailure(msg: String?) {
                if (msg != null) {
                    iView.showError(msg)
                }
            }

        })
    }

    //获取通讯列表
    override fun getChatList(tid: String, pid: Int) {
        addSubscription(httpApi.getChatDetailService(tid,pid).map(HttpResultFunc())
                ,object :HttpCallback<List<ChatDetail>>(){

            override fun onSuccess(model: List<ChatDetail>) {
                (iView as ChatDetailContract.IView).onChaListGet(model)
            }

            override fun onFailure(msg: String?) {
                if (msg != null) {
                    iView.showError(msg)
                }
            }

        })
    }

    //发送chat内容
    override fun sendChat(pid: Int, tid: String, oid: String, content: String) {
        addSubscription(httpApi.sendChatService(pid,tid,oid,content,1).map(HttpResultFunc<Any>())
                ,object :HttpCallback<Any>(){

            override fun onSuccess(model: Any) {
                (iView as ChatDetailContract.IView).onSendChatSuccess()
            }

            override fun onFailure(msg: String?) {
                if (msg != null) {
                    iView.showError(msg)
                }
            }
        })
    }

}