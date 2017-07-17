package com.meishipintu.lll_office.presenters

import com.meishipintu.lll_office.contract.ChatDetailContract
import com.meishipintu.lll_office.modles.HttpApiClinet
import com.meishipintu.lll_office.modles.HttpCallback
import com.meishipintu.lll_office.modles.HttpResultFunc
import com.meishipintu.lll_office.modles.entities.ChatDetail
import com.meishipintu.lll_office.modles.entities.OfficeInfo
import com.meishipintu.lll_office.views.BasicView


/**
 * Created by Administrator on 2017/7/13.
 *
 * 主要功能：
 */
class MessagePresenter(val iView: BasicView) : BasicPresenter(), ChatDetailContract.IPresenter {

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
                    iView.onError(msg)
                }
            }
        })
    }

    //获取通讯列表
    override fun getChatList(tid: String, pid: Int) {
        addSubscription(httpApi.getChatDetailService(tid,pid).map(HttpResultFunc<List<ChatDetail>>())
                ,object :HttpCallback<List<ChatDetail>>(){

            override fun onSuccess(model: List<ChatDetail>) {
                (iView as ChatDetailContract.IView).onChaListGet(model)
            }

            override fun onFailure(msg: String?) {
                if (msg != null) {
                    iView.onError(msg)
                }
            }

        })
    }

    //发送chat内容
    override fun sendChat(pid: Int, tid: String, oid: String, content: String) {
        //type=1 教师发起，type=2 机构发起
        addSubscription(httpApi.sendChatService(pid,tid,oid,content,2).map(HttpResultFunc<Any>())
                ,object :HttpCallback<Any>(){

            override fun onSuccess(model: Any) {
                (iView as ChatDetailContract.IView).onSendChatSuccess()
            }

            override fun onFailure(msg: String?) {
                if (msg != null) {
                    iView.onError(msg)
                }
            }

        })
    }

}