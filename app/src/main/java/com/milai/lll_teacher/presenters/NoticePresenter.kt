package com.milai.lll_teacher.presenters

import com.milai.lll_teacher.contracts.NoticeContract
import com.milai.lll_teacher.models.entities.MessageNoticeInfo
import com.milai.lll_teacher.models.entities.SysNoticeInfo
import com.milai.lll_teacher.models.https.HttpApiClinet
import com.milai.lll_teacher.models.https.HttpCallback
import com.milai.lll_teacher.models.https.HttpResultFunc

/**
 * Created by Administrator on 2017/7/25.
 *
 * 主要功能：
 */
class NoticePresenter(val iView: NoticeContract.IView): BasicPresenter(),NoticeContract.IPresenter {

    val httpApi = HttpApiClinet.retrofit()

    //获取系统消息
    override fun getSysNotice(tid: String) {
        addSubscription(httpApi.getSysNoticeService(tid,1).map(HttpResultFunc<List<SysNoticeInfo>>())
                ,object : HttpCallback<List<SysNoticeInfo>>(){

            override fun onSuccess(model: List<SysNoticeInfo>) {
                iView.onSysNoticeGet(model)
            }

            override fun onFailure(msg: String?) {
                if (msg != null) {
                    iView.showError(msg)
                }
            }
        })
    }

    //获取私信
    override fun getMessageNotice(tid: String) {
        addSubscription(httpApi.getChatListService(tid,1,"").map(HttpResultFunc<List<MessageNoticeInfo>>())
                ,object :HttpCallback<List<MessageNoticeInfo>>(){
            override fun onSuccess(model: List<MessageNoticeInfo>) {
                iView.onMessageNoticeGet(model)
            }

            override fun onFailure(msg: String?) {
                if (msg != null) {
                    iView.showError(msg)
                }
            }
        })
    }
}