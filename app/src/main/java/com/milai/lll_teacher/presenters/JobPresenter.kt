package com.milai.lll_teacher.presenters

import android.util.Log
import com.milai.lll_teacher.contracts.CollectionContract
import com.milai.lll_teacher.contracts.JobContract
import com.milai.lll_teacher.contracts.JobDetailContact
import com.milai.lll_teacher.contracts.SearchContract
import com.milai.lll_teacher.models.entities.HttpResult
import com.milai.lll_teacher.models.entities.JobInfo
import com.milai.lll_teacher.models.entities.OfficeInfo
import com.milai.lll_teacher.models.https.HttpApiClinet
import com.milai.lll_teacher.models.https.HttpCallback
import com.milai.lll_teacher.models.https.HttpResultFunc
import com.milai.lll_teacher.views.BasicView
import com.milai.lll_teacher.views.BasicViewLoadError

/**
 * Created by Administrator on 2017/7/10.
 *
 * 主要功能：
 */
class JobPresenter(val iView: BasicView) :BasicPresenter(),JobContract.IPresenter
        ,JobDetailContact.IPresenter,CollectionContract.IPresenter,SearchContract.IPresenter {


    val httpApi = HttpApiClinet.retrofit()

    //查找筛选职位
    override fun doSearch(tj: Int, area: Int, course: Int, grade: Int, experience: Int, page: Int) {
        addSubscription(httpApi.getJobService(tj, area, course, grade, experience, page).map(HttpResultFunc<List<JobInfo>>())
                , object : HttpCallback<List<JobInfo>>() {

            override fun onSuccess(model: List<JobInfo>) {
                (iView as JobContract.IView).onDateGet(model, page)
            }

            override fun onFailure(msg: String?) {
                (iView as BasicViewLoadError).onLoadError()
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
        addSubscription(httpApi.sendResumeService(jobId,uid,oid,4).map(HttpResultFunc<Any>())
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
    override fun getJobCollection(tid: String, page: Int) {
        addSubscription(httpApi.getJobCollectService(tid,page).map(HttpResultFunc<List<JobInfo>>())
                , object : HttpCallback<List<JobInfo>>() {
            override fun onSuccess(model: List<JobInfo>) {
                Log.d("test", "the dataList size is ${model.size}")
                (iView as CollectionContract.IView).onJobCollectionGet(model,page)
            }

            override fun onFailure(msg: String?) {
                (iView as BasicViewLoadError).onLoadError()
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
    override fun getJobByKeyWord(keyword: String, page: Int) {
        addSubscription(httpApi.getJobByKeyWorkService(keyword,page).map(HttpResultFunc<List<JobInfo>>())
                , object : HttpCallback<List<JobInfo>>() {

            override fun onSuccess(model: List<JobInfo>) {
                (iView as SearchContract.IView).onJobGet(model, page)
            }

            override fun onFailure(msg: String?) {
                if (msg != null) {
                    iView.showError(msg)
                }
                (iView as BasicViewLoadError).onLoadError()
            }
        })
    }

    //单独根据科目搜索职位
    override fun getJobByCourse(courseIndex: Int,page:Int) {
        addSubscription(httpApi.getJobService(0,0,courseIndex,0,0,page).map(HttpResultFunc<List<JobInfo>>())
                ,object :HttpCallback<List<JobInfo>>(){

            override fun onSuccess(model: List<JobInfo>) {
                (iView as SearchContract.IView).onJobGet(model, page)
            }

            override fun onFailure(msg: String?) {
                (iView as JobContract.IView).onLoadError()
                if (msg != null) {
                    iView.showError(msg)
                }
            }
        })
    }

    //判断是否投递或是否邀约
    override fun isJobDeliver(pid: Int, tid: String) {
        addSubscription(httpApi.isDeliverPosition(tid,pid.toString()),object :HttpCallback<HttpResult<Any>>(){
            override fun onSuccess(model: HttpResult<Any>) {
                when (model.status) {
                    3 -> {
                        (iView as JobDetailContact.IView).onJobDeliver(false)
                    }
                    else -> (iView as JobDetailContact.IView).onJobDeliver(true)
                }
            }

            override fun onFailure(msg: String?) {
                if (msg != null) {
                    iView.showError(msg)
                }
            }
        })
    }

    //添加职位浏览的统计
    //flag=1教师端，flag=2 机构端
    //type=1 新闻事件  type=2 浏览简历 type=3 浏览职位
    //id_detail 事件详情，新闻为新闻id 简历为uid  职位pid
    override fun addStatistic(uid: String, pid: Int) {
        addSubscription(httpApi.doActionSattistic(uid,1,3,pid.toString()).map(HttpResultFunc())
                ,object :HttpCallback<Any>(){
            override fun onSuccess(model: Any) {
            }

            override fun onFailure(msg: String?) {
            }
        })
    }
}