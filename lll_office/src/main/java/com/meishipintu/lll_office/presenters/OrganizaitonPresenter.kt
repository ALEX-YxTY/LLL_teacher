package com.meishipintu.lll_office.presenters

import com.meishipintu.lll_office.contract.OfficeDetailContract
import com.meishipintu.lll_office.contract.OtherOrganizationContract
import com.meishipintu.lll_office.modles.HttpApiClinet
import com.meishipintu.lll_office.modles.HttpCallback
import com.meishipintu.lll_office.modles.HttpResultFunc
import com.meishipintu.lll_office.modles.entities.JobInfo
import com.meishipintu.lll_office.modles.entities.OfficeInfo
import com.meishipintu.lll_office.views.BasicView
import com.meishipintu.lll_office.views.BasicViewLoadError

/**
 * Created by Administrator on 2017/7/19.
 *
 * 主要功能：
 */
class OrganizaitonPresenter(val iView:BasicView):BasicPresenter()
        ,OtherOrganizationContract.IPresenter,OfficeDetailContract.IPresenter {

    val httpApi = HttpApiClinet.retrofit()

    override fun getOrganization(oid: String,page:Int) {
        addSubscription(httpApi.getOtherOrganizationService(oid,page).map(HttpResultFunc())
                ,object :HttpCallback<List<OfficeInfo>>(){
            override fun onSuccess(model: List<OfficeInfo>) {
                (iView as OtherOrganizationContract.IView).onOrganizationGet(model,page)
            }

            override fun onFailure(msg: String?) {
                (iView as BasicViewLoadError).onLoadError()
                if (msg != null) {
                    iView.onError(msg)
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
                    iView.onError(msg)
                }
            }
        })
    }
}