package com.milai.lll_teacher.presenters

import com.milai.lll_teacher.contracts.JobContract
import com.milai.lll_teacher.models.entities.JobInfo
import com.milai.lll_teacher.models.https.HttpApiClinet
import com.milai.lll_teacher.models.https.HttpCallback
import com.milai.lll_teacher.models.https.HttpResultFunc
import com.milai.lll_teacher.views.BasicView

/**
 * Created by Administrator on 2017/7/10.
 *
 * 主要功能：
 */
class JobPresenter(val iView: BasicView) :BasicPresenter(),JobContract.IPresenter {

    val httpApi = HttpApiClinet.retrofit()

    //查找筛选职位
    override fun doSearch(tj: Boolean, area: Int, course: Int, grade: Int, experience: Int) {
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
}