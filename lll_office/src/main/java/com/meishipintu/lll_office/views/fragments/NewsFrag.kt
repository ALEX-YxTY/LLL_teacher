package com.meishipintu.lll_office.views.fragments

import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.meishipintu.lll_office.R
import com.meishipintu.lll_office.contract.NewsContract
import com.meishipintu.lll_office.customs.CanLoadMoreRecyclerView
import com.meishipintu.lll_office.modles.entities.AdInfo
import com.meishipintu.lll_office.modles.entities.NewsInfo
import com.meishipintu.lll_office.presenters.NewsPresenter
import com.meishipintu.lll_office.views.adapters.NewsAdapter
import com.meishipintu.lll_office.views.plugs.MyImageLoader
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import com.youth.banner.listener.OnBannerListener

/**
 * Created by Administrator on 2017/6/29.
 *
 * 主要功能：
 */
class NewsFrag:BasicFragment(),NewsContract.IView,CanLoadMoreRecyclerView.StateChangedListener{

    var rootView: View? = null
    val rvNews:CanLoadMoreRecyclerView by lazy { rootView?.findViewById(R.id.rv_news) as CanLoadMoreRecyclerView }
    val vpAdd:Banner by lazy{ rootView?.findViewById(R.id.banner) as Banner}

    val newsList = mutableListOf<NewsInfo>()
    val newsAdapter:NewsAdapter by lazy{ NewsAdapter(this.activity, newsList)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (presenter == null) {
            presenter = NewsPresenter(this)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View? {
        if (rootView == null) {
            rootView = inflater!!.inflate(R.layout.frag_news, container, false)
            val textView = rootView?.findViewById(R.id.tv_title) as TextView
            textView.text = "资讯"
            initAd()
            initRv()
            if (presenter != null) {
                (presenter as NewsContract.IPresenter).getAds()
            }
        }
        return rootView
    }

    //新闻列表
    private fun initRv() {
        rvNews.listener = this
        rvNews.setAdapter(newsAdapter)
    }

    //图片轮播
    private fun initAd() {
        //设置banner样式  BannerConfig.NOT_INDICATOR 无指示器 BannerConfig.CIRCLE_INDICATOR 圆形只是
        //BannerConfig.NUM_INDICATOR 数字指示 NUM_INDICATOR_TITLE 带标题，还需要通过setBannerTitles设置标题
        vpAdd.setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
        //设置指示器位置
        vpAdd.setIndicatorGravity(BannerConfig.RIGHT)
        vpAdd.setImageLoader(MyImageLoader(this.activity))
        //设置banner动画
        vpAdd.setBannerAnimation(Transformer.Default)
        //设置自动轮播，默认为true
        vpAdd.isAutoPlay(true)
        //设置轮播时间
        vpAdd.setDelayTime(3000)
        vpAdd.setOnBannerListener(object :OnBannerListener{
            override fun OnBannerClick(position: Int) {

            }

        })
    }

    //from NewsContract.IView
    override fun onError(e: String) {
        toast(e)
    }

    override fun onLoadMore(page: Int) {
        (presenter as NewsContract.IPresenter).getNews(page)
    }

    override fun onLoadError() {
        rvNews.dismissLoading()
        rvNews.dismissProgressBar()
    }

    //from NewsContract.IView
    override fun onAdsGet(adList: List<AdInfo>) {
        Log.d("test", "adList.size ${adList.size}")
        vpAdd.setImages(adList)
        //开始自动播放
        vpAdd.start()
    }

    //from NewsContract.IView
    override fun onNewsGet(newsList: List<NewsInfo>,page: Int) {
        if (page == 1) {
            //首次加载
            this.newsList.clear()
            this.newsList.addAll(newsList)
            rvNews.onLoadSuccess(page)
            newsAdapter.notifyDataSetChanged()
        }else if (newsList.isNotEmpty()) {
            //load more 并且有数据
            this.newsList.addAll(newsList)
            rvNews.onLoadSuccess(page)
            newsAdapter.notifyDataSetChanged()
        } else {
            //load more 没数据
            rvNews.dismissProgressBar()
            rvNews.dismissLoading()
        }
    }

    override fun onPause() {
        super.onPause()
        vpAdd.stopAutoPlay()
    }

    override fun onResume() {
        super.onResume()
        vpAdd.startAutoPlay()
    }
}
