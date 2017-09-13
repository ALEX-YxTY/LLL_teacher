package com.meishipintu.lll_office.views.plugs

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.youth.banner.loader.ImageLoader

/**
 * Created by Administrator on 2017/9/13.
 *
 * 主要功能：
 */
class GuideLoader(val ctx: Context):ImageLoader() {

    val glide = Glide.with(ctx)!!

    override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
        glide.load(path as Int).crossFade().into(imageView)
    }

}