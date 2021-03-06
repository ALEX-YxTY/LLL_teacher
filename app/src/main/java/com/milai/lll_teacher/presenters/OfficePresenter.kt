package com.milai.lll_teacher.presenters

import com.milai.lll_teacher.contracts.OfficeContract
import com.milai.lll_teacher.contracts.OfficeDetailContract
import com.milai.lll_teacher.contracts.OrganizationCollectionContract
import com.milai.lll_teacher.contracts.SearchContract
import com.milai.lll_teacher.models.entities.JobInfo
import com.milai.lll_teacher.models.entities.OfficeInfo
import com.milai.lll_teacher.models.https.HttpApiClinet
import com.milai.lll_teacher.models.https.HttpCallback
import com.milai.lll_teacher.models.https.HttpResultFunc
import com.milai.lll_teacher.views.BasicView
import com.milai.lll_teacher.views.BasicViewLoadError

/**
 * Created by Administrator on 2017/7/11.
 *
 * 主要功能：
 */
class OfficePresenter(val iView:BasicView) : BasicPresenter(), OfficeContract.IPresenter
        ,OfficeDetailContract.IPresenter,OrganizationCollectionContract.IPresenter {

    val httpApi = HttpApiClinet.retrofit()

    override fun getOffice(page:Int) {
        addSubscription(httpApi.getOrganizationgService(page).map(HttpResultFunc<List<OfficeInfo>>())
                ,object:HttpCallback<List<OfficeInfo>>(){
            override fun onSuccess(model: List<OfficeInfo>) {
                (iView as OfficeContract.IView).onDataGet(model, page)
            }

            override fun onFailure(msg: String?) {
                (iView as BasicViewLoadError).onLoadError()
                if (msg != null) {
                    iView.showError(msg)
                }
            }
        })
    }

    //获取机构发布的职位
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

    //判断机构是否已经被收藏
    override fun isOfficeCollect(officeId: String, teacherId: String) {
        addSubscription(httpApi.isOfficeCollectedService(officeId,teacherId).map(HttpResultFunc<List<Any>>())
                ,object :HttpCallback<List<Any>>(){
            override fun onSuccess(model: List<Any>) {
                if (model.isNotEmpty()) {
                    (iView as OfficeDetailContract.IView).isOfficeCollected(true)
                } else {
                    (iView as OfficeDetailContract.IView).isOfficeCollected(false)
                }
            }

            override fun onFailure(msg: String?) {
                if (msg != null) {
                    iView.showError(msg)
                }
            }

        })
    }

    //添加或删除机构的收藏
    override fun addOrganizationCollection(officeId: String, teacherId: String, isAddded: Boolean) {
        if (isAddded) {
            //删除
            addSubscription(httpApi.deletOfficeCollectionService(officeId,teacherId).map(HttpResultFunc<Any>())
                    ,object :HttpCallback<Any>(){
                override fun onSuccess(model: Any) {
                    (iView as OfficeDetailContract.IView).onOfficeCollectResult(false)
                }

                override fun onFailure(msg: String?) {
                    if(msg!= null){
                        iView.showError(msg)
                    }
                }

            })
        } else {
            //添加
            addSubscription(httpApi.addOfficeCollectionService(officeId,teacherId).map(HttpResultFunc<Any>())
                    ,object :HttpCallback<Any>(){
                override fun onSuccess(model: Any) {
                    (iView as OfficeDetailContract.IView).onOfficeCollectResult(true)
                }

                override fun onFailure(msg: String?) {
                    if(msg!= null){
                        iView.showError(msg)
                    }
                }

            })
        }
    }

    //查询教师收藏的机构
    override fun getOrganizationCollection(teacherId: String, page: Int) {
        addSubscription(httpApi.getOfficeCollectionService(teacherId, page).map(HttpResultFunc<List<OfficeInfo>>())
                , object : HttpCallback<List<OfficeInfo>>() {
            override fun onSuccess(model: List<OfficeInfo>) {
                (iView as OrganizationCollectionContract.IView).onOrganizationCollectionGet(model,page)
            }

            override fun onFailure(msg: String?) {
                (iView as BasicViewLoadError).onLoadError()
                if (msg != null) {
                    iView.showError(msg)
                }
            }
        })
    }

    //搜索机构通过关键字
    override fun searchOfficeByKeyword(keyword: String, page: Int) {
        addSubscription(httpApi.getOfficeByKeyWorkService(keyword,page).map(HttpResultFunc<List<OfficeInfo>>())
                , object : HttpCallback<List<OfficeInfo>>() {
            override fun onSuccess(model: List<OfficeInfo>) {
                (iView as SearchContract.IView).onOfficeGet(model, page)
            }

            override fun onFailure(msg: String?) {
                if (msg != null) {
                    iView.showError(msg)
                }
                (iView as BasicViewLoadError).onLoadError()
            }

        })
    }

}