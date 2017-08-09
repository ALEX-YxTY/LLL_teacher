package com.meishipintu.lll_office.views.adapters

import android.content.Context
import android.support.v7.widget.LinearLayoutManager

/**
 * Created by Administrator on 2017/8/8.
 *
 * 主要功能：
 */
class BasicLayoutManager(ctx: Context, val listener: LayoutLoadMoreListener):LinearLayoutManager(ctx) {

    fun shouldLoadMore() {
        if (itemCount > 0) {
            if (findLastCompletelyVisibleItemPosition() == itemCount - 1) {
                listener.onLoadMore()
            }
        }
    }
}