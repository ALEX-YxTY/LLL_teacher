package com.meishipintu.lll_office.views.plugs

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.meishipintu.lll_office.modles.entities.AdInfo
import com.youth.banner.loader.ImageLoader

/**
 * Created by Administrator on 2017/8/1.
 *
 * 主要功能：
 */
class MyImageLoader(val ctx: Context):ImageLoader() {

    val glide=Glide.with(ctx)

    override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
        val adInfo = path as AdInfo
        //crossFade 淡入淡出
        glide.load(adInfo.img).crossFade().into(imageView)
    }
}