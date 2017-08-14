package com.meishipintu.lll_office.presenters

import com.meishipintu.lll_office.contract.NewsContract
import com.meishipintu.lll_office.modles.HttpApiClinet
import com.meishipintu.lll_office.modles.HttpCallback
import com.meishipintu.lll_office.modles.HttpResultFunc
import com.meishipintu.lll_office.modles.entities.AdInfo
import com.meishipintu.lll_office.modles.entities.NewsInfo
import com.meishipintu.lll_office.views.BasicView
import com.meishipintu.lll_office.views.BasicViewLoadError

/**
 * Created by Administrator on 2017/8/1.
 *
 * 主要功能：
 */
class NewsPresenter(val iView:BasicView):BasicPresenter(),NewsContract.IPresenter {

    val httpApi = HttpApiClinet.retrofit()
    override fun getAds() {
        addSubscription(httpApi.getAdsService().map(HttpResultFunc<List<AdInfo>>())
                ,object:HttpCallback<List<AdInfo>>(){
            override fun onSuccess(model: List<AdInfo>) {
                (iView as NewsContract.IView).onAdsGet(model)
            }

            override fun onFailure(msg: String?) {
                if (msg != null) {
                    iView.onError(msg)
                }
            }
        })
    }

    override fun getNews(page:Int) {
        addSubscription(httpApi.getNewsService(page).map(HttpResultFunc<List<NewsInfo>>())
                ,object :HttpCallback<List<NewsInfo>>(){
            override fun onSuccess(model: List<NewsInfo>) {
                (iView as NewsContract.IView).onNewsGet(model, page)
            }

            override fun onFailure(msg: String?) {
                (iView as BasicViewLoadError).onLoadError()
                if (msg != null) {
                    iView.onError(msg)
                }
            }
        })
    }
}