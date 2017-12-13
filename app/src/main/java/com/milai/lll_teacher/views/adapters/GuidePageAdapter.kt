package com.milai.lll_teacher.views.adapters

import android.app.Activity
import android.content.Intent
import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.milai.lll_teacher.Cookies
import com.milai.lll_teacher.views.MainActivity

/**
 * Created by Administrator on 2017/9/13.
 *
 * 主要功能：
 */
class GuidePageAdapter(val ctx: Activity, val list: List<Int>) : PagerAdapter() {

    private val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)

    override fun isViewFromObject(view: View?, `object`: Any?): Boolean {
        return view==`object`
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun instantiateItem(container: ViewGroup?, position: Int): Any {
        val imageView = ImageView(ctx)
        imageView.setImageResource(list[position])
        imageView.layoutParams = params
        if (position == list.size - 1) {
            imageView.setOnClickListener{
                val intent = Intent(ctx, MainActivity::class.java)
                intent.putExtra("firstLogin", true)
                ctx.startActivity(intent)
                Cookies.setFirstLogin()
                ctx.finish()
            }
        }
        container?.addView(imageView)
        return imageView
    }

    override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {
        container?.removeView(`object` as View)
    }
}