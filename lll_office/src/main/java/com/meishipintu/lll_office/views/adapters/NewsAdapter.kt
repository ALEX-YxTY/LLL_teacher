package com.meishipintu.lll_office.views.adapters

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.meishipintu.lll_office.R
import com.meishipintu.lll_office.modles.entities.NewsInfo
import com.meishipintu.lll_office.views.NewsDetailActivity

/**
 * Created by Administrator on 2017/8/1.
 *
 * 主要功能：
 */
class NewsAdapter(ctx: Context, dataList:List<NewsInfo>):BasicAdapter(ctx,dataList) {

    val glide = Glide.with(ctx)

    override fun getSpecialView(container: ViewGroup?): RecyclerView.ViewHolder {
        return NewsViewHolder(LayoutInflater.from(ctx).inflate(R.layout.item_news,container,false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (getItemViewType(position) == TYPE_NORMAL) {
            val newsViewHolder = holder as NewsViewHolder
            val newsData = dataList[position] as NewsInfo
            glide.load(newsData.logo).error(R.drawable.organization_default).into(newsViewHolder.img)
            newsViewHolder.title.text = newsData.title
            newsViewHolder.subTitle.text = newsData.sub_title
            newsViewHolder.itemView.setOnClickListener{
                val intent = Intent(ctx, NewsDetailActivity::class.java)
                intent.putExtra("url", newsData.content)
                intent.putExtra("newsId",newsData.id)
                ctx.startActivity(intent)
            }
        }
    }
}