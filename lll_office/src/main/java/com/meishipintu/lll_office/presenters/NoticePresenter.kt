package com.meishipintu.lll_office.presenters

import com.meishipintu.lll_office.contract.NoticeActivityContract
import com.meishipintu.lll_office.contract.NoticeContract
import com.meishipintu.lll_office.modles.HttpApiClinet
import com.meishipintu.lll_office.modles.HttpCallback
import com.meishipintu.lll_office.modles.HttpResultFunc
import com.meishipintu.lll_office.modles.entities.*
import com.meishipintu.lll_office.views.BasicView
import com.meishipintu.lll_office.views.BasicViewLoadError

/**
 * Created by Administrator on 2017/7/25.
 *
 * 主要功能：
 */
class NoticePresenter(val iView:BasicView):BasicPresenter(),NoticeContract.IPresenter
        ,NoticeActivityContract.IPresenter{

    val httpApi = HttpApiClinet.retrofit()

    override fun getSysNotice(oid: String,page:Int) {
        addSubscription(httpApi.getSysNoticeService(oid,1,page).map(HttpResultFunc<List<SysNoticeInfo>>())
                ,object :HttpCallback<List<SysNoticeInfo>>(){

            override fun onSuccess(model: List<SysNoticeInfo>) {
                (iView as NoticeContract.IView).onSysNoticeGet(model,page)
            }

            override fun onFailure(msg: String?) {
                (iView as BasicViewLoadError).onLoadError()
                if (msg != null) {
                    iView.onError(msg)
                }
            }
        })
    }

    override fun getMessageNotice(oid: String,page:Int) {
        addSubscription(httpApi.getChatListService("",2,oid,page).map(HttpResultFunc<List<MessageNoticeInfo>>())
                ,object :HttpCallback<List<MessageNoticeInfo>>(){
            override fun onSuccess(model: List<MessageNoticeInfo>) {
                (iView as NoticeContract.IView).onMessageNoticeGet(model,page)
            }

            override fun onFailure(msg: String?) {
                (iView as BasicViewLoadError).onLoadError()
                if (msg != null) {
                    iView.onError(msg)
                }
            }

        })
    }

    //获取最新系统消息
    override fun getNewestSysId(tid: String) {
        addSubscription(httpApi.getNewestIdService(1,2,tid), object : HttpCallback<HttpResult<NewsId>>() {
            override fun onSuccess(model: HttpResult<NewsId>) {
                if (model.status == 1) {
                    (iView as NoticeActivityContract.IView).onNewestSysIdGet(model.data.id)
                }else if (model.status == 2) {
                    (iView as NoticeActivityContract.IView).onNewestSysIdGet(-1)
                } else {
                    iView.onError(model.msg)
                }
            }

            override fun onFailure(msg: String?) {
                if (msg != null) {
                    iView.onError(msg)
                }
            }

        })
    }

    //获取最新私信消息
    override fun getNewestMessId(tid: String) {
        addSubscription(httpApi.getNewestIdService(1,1,tid), object : HttpCallback<HttpResult<NewsId>>() {
            override fun onSuccess(model: HttpResult<NewsId>) {
                if (model.status == 1) {
                    (iView as NoticeActivityContract.IView).onNewestMessIdGet(model.data.id)
                }else if (model.status == 2) {
                    (iView as NoticeActivityContract.IView).onNewestMessIdGet(-1)
                } else {
                    iView.onError(model.msg)
                }
            }

            override fun onFailure(msg: String?) {
                if (msg != null) {
                    iView.onError(msg)
                }
            }

        })
    }

    override fun getNewsetVersiton() {
        addSubscription(httpApi.getNewestVersion(2).map(HttpResultFunc<VersionInfo>())
                ,object :HttpCallback<VersionInfo>(){
            override fun onSuccess(model: VersionInfo) {
                (iView as NoticeActivityContract.IView).onVersionGet(model)
            }

            override fun onFailure(msg: String?) {
                if (msg != null) {
                    iView.onError(msg)
                }
            }

        })
    }
}