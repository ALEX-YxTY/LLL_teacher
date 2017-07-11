package com.milai.lll_teacher.presenters

import com.milai.lll_teacher.contracts.JobContract
import com.milai.lll_teacher.contracts.OfficeContract
import com.milai.lll_teacher.contracts.OfficeDetailContract
import com.milai.lll_teacher.models.entities.JobInfo
import com.milai.lll_teacher.models.entities.OfficeInfo
import com.milai.lll_teacher.models.https.HttpApiClinet
import com.milai.lll_teacher.models.https.HttpCallback
import com.milai.lll_teacher.models.https.HttpResultFunc
import com.milai.lll_teacher.views.BasicView

/**
 * Created by Administrator on 2017/7/11.
 *
 * 主要功能：
 */
class OfficePresenter(val iView:BasicView) : BasicPresenter(), OfficeContract.IPresenter
        ,OfficeDetailContract.IPresenter {

    val httpApi = HttpApiClinet.retrofit()

    override fun getOffice() {
        addSubscription(httpApi.getOrganizationgService().map(HttpResultFunc<List<OfficeInfo>>())
                ,object:HttpCallback<List<OfficeInfo>>(){
            override fun onSuccess(model: List<OfficeInfo>) {
                (iView as OfficeContract.IView).onDataGet(model)
            }

            override fun onFailure(msg: String?) {
                if (msg != null) {
                    iView.showError(msg)
                }
            }
        })
    }

    override fun getOrganizationPosition(uid: String) {
        addSubscription(httpApi.getOfficeJobService(uid,1).map(HttpResultFunc<List<JobInfo>>())
                ,object:HttpCallback<List<JobInfo>>(){
            override fun onSuccess(model: List<JobInfo>) {
                (iView as OfficeDetailContract.IView).onPositionGet(model)
            }

            override fun onFailure(msg: String?) {
                if (msg != null) {
                    iView.showError(msg)
                }
            }
        })
    }

}