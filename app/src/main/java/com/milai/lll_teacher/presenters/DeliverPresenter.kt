package com.milai.lll_teacher.presenters

import com.milai.lll_teacher.contracts.InterviewListContract
import com.milai.lll_teacher.models.entities.DeliverInfo
import com.milai.lll_teacher.models.https.HttpApiClinet
import com.milai.lll_teacher.models.https.HttpCallback
import com.milai.lll_teacher.models.https.HttpResultFunc
import com.milai.lll_teacher.views.BasicView
import com.milai.lll_teacher.views.BasicViewLoadError

/**
 * Created by Administrator on 2017/7/27.
 *
 * 主要功能：
 */
class DeliverPresenter(val iView: BasicView) :BasicPresenter(), InterviewListContract.IPresenter{
    val httpApi = HttpApiClinet.retrofit()

    override fun getDeliverHistory(tid: String, type: Int, status: Int,page:Int) {
        addSubscription(httpApi.getTeacherDeliverServie(tid,type,status,page).map(HttpResultFunc<List<DeliverInfo>>())
                ,object :HttpCallback<List<DeliverInfo>>(){

            override fun onSuccess(model: List<DeliverInfo>) {
                (iView as InterviewListContract.IView).onDeliverHistoryGet(model,page)
            }

            override fun onFailure(msg: String?) {
                (iView as BasicViewLoadError).onLoadError()
                if (msg != null) {
                    iView.showError(msg)
                }
            }
        })
    }
}