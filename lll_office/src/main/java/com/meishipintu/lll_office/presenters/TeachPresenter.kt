package com.meishipintu.lll_office.presenters

import android.util.Log
import com.meishipintu.lll_office.contract.TeacherCollectionContract
import com.meishipintu.lll_office.contract.TeacherContract
import com.meishipintu.lll_office.contract.TeacherDetailContract
import com.meishipintu.lll_office.modles.HttpApiClinet
import com.meishipintu.lll_office.modles.HttpCallback
import com.meishipintu.lll_office.modles.HttpResultFunc
import com.meishipintu.lll_office.modles.entities.TeacherInfo
import com.meishipintu.lll_office.views.BasicView
import com.meishipintu.lll_office.views.BasicViewLoadError

/**
 * Created by Administrator on 2017/7/5.
 *
 * 主要功能：
 */
class TeachPresenter(val iView:BasicView):BasicPresenter(),TeacherContract.IPresenter
        ,TeacherDetailContract.IPresenter,TeacherCollectionContract.IPresenter {

    val httpApi = HttpApiClinet.retrofit()

    //筛选查找教师
    override fun doSearch(tj: Int, year: Int, course: Int, grade: Int, decending: Int,page:Int) {
        addSubscription(httpApi.getTeacherService(tj,year,course,grade,decending,page)
                .map(HttpResultFunc<List<TeacherInfo>>()), object : HttpCallback<List<TeacherInfo>>(){
            override fun onSuccess(model: List<TeacherInfo>) {
                (iView as TeacherContract.IView).onDateGet(model, page)
            }

            override fun onFailure(msg: String?) {
                (iView as BasicViewLoadError).onLoadError()
                if (msg != null) {
                    iView.onError(msg)
                }
            }
        })
    }

    //判断教师是否收藏
    override fun isCollectedTeacher(oid: String, tid: String) {
        addSubscription(httpApi.isCollectedTeacherService(tid,oid).map(HttpResultFunc<List<Any>>())
                ,object :HttpCallback<List<Any>>(){
            override fun onSuccess(model: List<Any>) {
                (iView as TeacherDetailContract.IView).onIsCollected(model.size > 0)
            }

            override fun onFailure(msg: String?) {
                if (msg != null) {
                    iView.onError(msg)
                }
            }

        })
    }

    //添加教师收藏
    override fun collectTeacher(oid: String, tid: String) {
        addSubscription(httpApi.addTeacherCollectionService(tid,oid).map(HttpResultFunc<Any>())
                ,object :HttpCallback<Any>(){
            override fun onSuccess(model: Any) {
                (iView as TeacherDetailContract.IView).collectSuccess(true)
            }

            override fun onFailure(msg: String?) {
                if (msg != null) {
                    iView.onError(msg)
                }
            }
        })
    }

    //删除教师收藏
    override fun uncollectTeacher(oid: String, tid: String) {
        addSubscription(httpApi.deleteTeacherCollectionService(tid,oid).map(HttpResultFunc<Any>())
                ,object :HttpCallback<Any>(){
            override fun onSuccess(model: Any) {
                (iView as TeacherDetailContract.IView).collectSuccess(false)
            }

            override fun onFailure(msg: String?) {
                if (msg != null) {
                    iView.onError(msg)
                }
            }

        })
    }

    //获取教师收藏列表
    override fun getTeacherCollectiion(oid: String,page:Int) {
        addSubscription(httpApi.getTeacherCollectService(oid,page).map(HttpResultFunc<List<TeacherInfo>>())
                ,object :HttpCallback<List<TeacherInfo>>(){
            override fun onSuccess(model: List<TeacherInfo>) {
                Log.d("test","model size:${model.size}")
                (iView as TeacherCollectionContract.IView).onTeacherCollectionGet(model,page)
            }

            override fun onFailure(msg: String?) {
                (iView as BasicViewLoadError).onLoadError()
                if (msg != null) {
                    iView.onError(msg)
                }
            }

        })
    }

    //主动邀约教师
    override fun inviteInterview(jobId: Int, tid: String, oid: String) {
        //type=2 机构主动邀请教师面试
        addSubscription(httpApi.sendResumeService(jobId,tid,oid,2).map(HttpResultFunc<Any>())
                ,object :HttpCallback<Any>(){
            override fun onSuccess(model: Any) {
                (iView as TeacherDetailContract.IView).onInviteSuccess()
                //TODO 刷新用户数据
            }

            override fun onFailure(msg: String?) {
                if (msg != null) {
                    iView.onError(msg)
                }
            }
        })
    }

    //根据关键字查找教师
    override fun searchTeacher(keyWord: String,page:Int) {
        addSubscription(httpApi.getTeacherByKeyWorkService(keyWord).map(HttpResultFunc<List<TeacherInfo>>())
                ,object :HttpCallback<List<TeacherInfo>>(){
            override fun onSuccess(model: List<TeacherInfo>) {
                (iView as TeacherContract.IView).onDateGet(model,page)
            }

            override fun onFailure(msg: String?) {
                (iView as BasicViewLoadError).onLoadError()
                if (msg != null) {
                    iView.onError(msg)
                }
            }
        })
    }

    //添加浏览简历统计
    override fun doActionStatistic(uid: String, tid: String) {
        addSubscription(httpApi.doActionSattistic(uid,2,2,tid).map(HttpResultFunc())
                ,object :HttpCallback<Any>(){
            override fun onSuccess(model: Any) {
            }

            override fun onFailure(msg: String?) {
            }

        })
    }
}