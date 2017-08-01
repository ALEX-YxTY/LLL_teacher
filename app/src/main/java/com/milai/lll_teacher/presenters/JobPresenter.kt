package com.milai.lll_teacher.presenters

import android.util.Log
import com.milai.lll_teacher.contracts.CollectionContract
import com.milai.lll_teacher.contracts.JobContract
import com.milai.lll_teacher.contracts.JobDetailContact
import com.milai.lll_teacher.contracts.SearchContract
import com.milai.lll_teacher.models.entities.JobInfo
import com.milai.lll_teacher.models.entities.OfficeInfo
import com.milai.lll_teacher.models.https.HttpApiClinet
import com.milai.lll_teacher.models.https.HttpCallback
import com.milai.lll_teacher.models.https.HttpResultFunc
import com.milai.lll_teacher.views.BasicView

/**
 * Created by Administrator on 2017/7/10.
 *
 * 主要功能：
 */
class JobPresenter(val iView: BasicView) :BasicPresenter(),JobContract.IPresenter
        ,JobDetailContact.IPresenter,CollectionContract.IPresenter,SearchContract.IPresenter {


    val httpApi = HttpApiClinet.retrofit()

    //查找筛选职位
    override fun doSearch(tj: Int, area: Int, course: Int, grade: Int, experience: Int) {
        addSubscription(httpApi.getJobService(tj,area,course,grade,experience).map(HttpResultFunc<List<JobInfo>>())
                ,object :HttpCallback<List<JobInfo>>(){

            override fun onSuccess(model: List<JobInfo>) {
                (iView as JobContract.IView).onDateGet(model)
            }

            override fun onFailure(msg: String?) {
                if (msg != null) {
                    iView.showError(msg)
                }
            }
        })
    }

    //判断职位是否已收藏
    override fun isJobCollect(id: Int, uid: String) {
        addSubscription(httpApi.isJobCollectedService(id,uid).map(HttpResultFunc<List<Any>>())
                ,object:HttpCallback<List<Any>>(){
            override fun onSuccess(model: List<Any>) {
                if (model.isNotEmpty()) {
                    (iView as JobDetailContact.IView).isJobCollected(true)
                } else {
                    (iView as JobDetailContact.IView).isJobCollected(false)
                }
            }

            override fun onFailure(msg: String?) {
                if (msg != null) {
                    iView.showError(msg)
                }
            }

        })
    }

    //添加/删除职位收藏
    override fun addJobCollect(id: Int, hasCollect: Boolean, teacherId: String) {
        if (!hasCollect) {
            addSubscription(httpApi.addJobCollectionService(id, teacherId).map(HttpResultFunc<Any>())
                    ,object :HttpCallback<Any>(){
                override fun onSuccess(model: Any) {
                    (iView as JobDetailContact.IView).onJobCollected(true)
                }

                override fun onFailure(msg: String?) {
                    if (msg != null) {
                        iView.showError(msg)
                    }
                }
            })
        } else {
            addSubscription(httpApi.delectJobCollectionService(id,teacherId).map(HttpResultFunc<Any>())
                    ,object :HttpCallback<Any>(){
                override fun onSuccess(model: Any) {
                    (iView as JobDetailContact.IView).onJobCollected(false)
                }

                override fun onFailure(msg: String?) {
                    if (msg != null) {
                        iView.showError(msg)
                    }
                }

            })
        }
    }

    //投递简历
    override fun sendResume(jobId: Int, uid: String, oid: String) {
        addSubscription(httpApi.sendResumeService(jobId,uid,oid,1).map(HttpResultFunc<Any>())
                ,object :HttpCallback<Any>(){
            override fun onSuccess(model: Any) {
                (iView as JobDetailContact.IView).onResumeSendSuccess()
            }

            override fun onFailure(msg: String?) {
                if (msg != null) {
                    iView.showError(msg)
                }
            }
        })
    }

    //获取收藏的职位
    override fun getJobCollection(tid: String) {
        addSubscription(httpApi.getJobCollectService(tid).map(HttpResultFunc<List<JobInfo>>())
                ,object :HttpCallback<List<JobInfo>>(){
            override fun onSuccess(model: List<JobInfo>) {
                Log.d("test","the dataList size is ${model.size}")
                (iView as CollectionContract.IView).onJobCollectionGet(model)
            }

            override fun onFailure(msg: String?) {
                if (msg != null) {
                    iView.showError(msg)
                }
            }

        })
    }

    //获取职位详情
    override fun getJobDetail(pid: Int) {
        addSubscription(httpApi.getPositionDetailServie(pid).map(HttpResultFunc<JobInfo>())
                ,object :HttpCallback<JobInfo>(){
            override fun onSuccess(model: JobInfo) {
                (iView as JobDetailContact.IView).onJobDetailGet(model)
            }

            override fun onFailure(msg: String?) {
                if (msg != null) {
                    iView.showError(msg)
                }
            }

        })
    }

    //获取机构详情
    override fun getOfficeDetail(oid: String) {
        addSubscription(httpApi.getOrganizationDetaioService(oid).map(HttpResultFunc<OfficeInfo>())
                ,object :HttpCallback<OfficeInfo>(){
            override fun onSuccess(model: OfficeInfo) {
                (iView as JobDetailContact.IView).onOfficeInfoGet(model)
            }

            override fun onFailure(msg: String?) {
                if (msg != null) {
                    iView.showError(msg)
                }
            }

        })
    }

    //根据关键词搜索职位
    override fun getJobByKeyWord(keyword: String) {
        addSubscription(httpApi.getJobByKeyWorkService(keyword).map(HttpResultFunc<List<JobInfo>>())
                ,object :HttpCallback<List<JobInfo>>(){

            override fun onSuccess(model: List<JobInfo>) {
                (iView as SearchContract.IView).onJobGet(model)
            }

            override fun onFailure(msg: String?) {
                if (msg != null) {
                    iView.showError(msg)
                }
            }
        })
    }

    //单独根据科目搜索职位
    override fun getJobByCourse(courseIndex: Int) {
        addSubscription(httpApi.getJobService(0,0,courseIndex,0,0).map(HttpResultFunc<List<JobInfo>>())
                ,object :HttpCallback<List<JobInfo>>(){

            override fun onSuccess(model: List<JobInfo>) {
                (iView as SearchContract.IView).onJobGet(model)
            }

            override fun onFailure(msg: String?) {
                if (msg != null) {
                    iView.showError(msg)
                }
            }
        })
    }
}