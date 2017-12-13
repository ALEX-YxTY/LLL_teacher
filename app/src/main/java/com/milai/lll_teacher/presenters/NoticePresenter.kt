package com.milai.lll_teacher.presenters

import com.milai.lll_teacher.contracts.NoticeContract
import com.milai.lll_teacher.models.entities.*
import com.milai.lll_teacher.models.https.HttpApiClinet
import com.milai.lll_teacher.models.https.HttpCallback
import com.milai.lll_teacher.models.https.HttpResultFunc
import com.milai.lll_teacher.views.BasicViewLoadError

/**
 * Created by Administrator on 2017/7/25.
 *
 * 主要功能：
 */
class NoticePresenter(val iView: NoticeContract.IView): BasicPresenter(),NoticeContract.IPresenter {

    val httpApi = HttpApiClinet.retrofit()

    //获取系统消息
    override fun getSysNotice(tid: String,page:Int) {
        addSubscription(httpApi.getSysNoticeService(tid,2,page).map(HttpResultFunc<List<SysNoticeInfo>>())
                ,object : HttpCallback<List<SysNoticeInfo>>(){

            override fun onSuccess(model: List<SysNoticeInfo>) {
                iView.onSysNoticeGet(model,page)
            }

            override fun onFailure(msg: String?) {
                (iView as BasicViewLoadError).onLoadError()
                if (msg != null) {
                    iView.showError(msg)
                }
            }

        })
    }

    //获取私信
    override fun getMessageNotice(tid: String,page:Int) {
        addSubscription(httpApi.getChatListService(tid,1,"",page).map(HttpResultFunc<List<MessageNoticeInfo>>())
                ,object :HttpCallback<List<MessageNoticeInfo>>(){
            override fun onSuccess(model: List<MessageNoticeInfo>) {
                iView.onMessageNoticeGet(model,page)
            }

            override fun onFailure(msg: String?) {
                (iView as BasicViewLoadError).onLoadError()
                if (msg != null) {
                    iView.showError(msg)
                }
            }
        })
    }

    //获取最新系统消息
    override fun getNewestSysId(tid: String) {
        addSubscription(httpApi.getNewestIdService(1,2,tid), object : HttpCallback<HttpResult<NewsId>>() {
            override fun onSuccess(model: HttpResult<NewsId>) {
                when {
                    model.status == 1 -> iView.onNewestSysIdGet(model.data.id)
                    model.status == 2 -> iView.onNewestSysIdGet(-1)
                    else -> iView.showError(model.msg)
                }
            }

            override fun onFailure(msg: String?) {
                if (msg != null) {
                    iView.showError(msg)
                }
            }

        })
    }

    //获取最新私信消息
    override fun getNewestMessId(tid: String) {
        addSubscription(httpApi.getNewestIdService(1,1,tid), object : HttpCallback<HttpResult<NewsId>>() {
            override fun onSuccess(model: HttpResult<NewsId>) {
                when {
                    model.status == 1 -> iView.onNewestMessIdGet(model.data.id)
                    model.status == 2 -> iView.onNewestMessIdGet(-1)
                    else -> iView.showError(model.msg)
                }
            }

            override fun onFailure(msg: String?) {
                if (msg != null) {
                    iView.showError(msg)
                }
            }
        })
    }

    //获取新版本信息
    override fun getNewsetVersiton() {
        addSubscription(httpApi.getNewestVersion(1).map(HttpResultFunc())
                ,object :HttpCallback<VersionInfo>(){
            override fun onSuccess(model: VersionInfo) {
                iView.onVersionGet(model)
            }

            override fun onFailure(msg: String?) {
                if (msg != null) {
                    iView.showError(msg)
                }
            }

        })
    }

    //获取新闻
    override fun getAdvertisement() {
        addSubscription(httpApi.getAdvertisement().map(HttpResultFunc())
                ,object :HttpCallback<List<AdInfo>>(){
            override fun onSuccess(model: List<AdInfo>) {
                iView.onAdGet(model[0])
            }

            override fun onFailure(msg: String?) {
                if (msg != null) {
                    iView.showError(msg)
                }
            }
        })
    }

}