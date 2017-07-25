package com.meishipintu.lll_office.presenters

import com.meishipintu.lll_office.contract.NoticeContract
import com.meishipintu.lll_office.modles.HttpApiClinet
import com.meishipintu.lll_office.modles.HttpCallback
import com.meishipintu.lll_office.modles.HttpResultFunc
import com.meishipintu.lll_office.modles.entities.MessageNoticeInfo
import com.meishipintu.lll_office.modles.entities.SysNoticeInfo

/**
 * Created by Administrator on 2017/7/25.
 *
 * 主要功能：
 */
class NoticePresenter(val iView:NoticeContract.IView):BasicPresenter(),NoticeContract.IPresenter {

    val httpApi = HttpApiClinet.retrofit()

    override fun getSysNotice(oid: String) {
        addSubscription(httpApi.getSysNoticeService(oid,1).map(HttpResultFunc<List<SysNoticeInfo>>())
                ,object :HttpCallback<List<SysNoticeInfo>>(){

            override fun onSuccess(model: List<SysNoticeInfo>) {
                iView.onSysNoticeGet(model)
            }

            override fun onFailure(msg: String?) {
                if (msg != null) {
                    iView.onError(msg)
                }
            }

        })
    }

    override fun getMessageNotice(oid: String) {
        addSubscription(httpApi.getChatListService("",2,oid).map(HttpResultFunc<List<MessageNoticeInfo>>())
                ,object :HttpCallback<List<MessageNoticeInfo>>(){
            override fun onSuccess(model: List<MessageNoticeInfo>) {
                iView.onMessageNoticeGet(model)
            }

            override fun onFailure(msg: String?) {
                if (msg != null) {
                    iView.onError(msg)
                }
            }

        })
    }
}