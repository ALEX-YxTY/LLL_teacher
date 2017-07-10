package com.meishipintu.lll_office.presenters

import com.meishipintu.lll_office.contract.TeacherContract
import com.meishipintu.lll_office.modles.HttpApiClinet
import com.meishipintu.lll_office.modles.HttpCallback
import com.meishipintu.lll_office.modles.HttpResultFunc
import com.meishipintu.lll_office.modles.entities.TeacherInfo
import com.meishipintu.lll_office.views.BasicView

/**
 * Created by Administrator on 2017/7/5.
 *
 * 主要功能：
 */
class TeachPresenter(val iView:TeacherContract.IView):BasicPresenter(),TeacherContract.IPresenter {

    override fun doSearch(tj: Boolean, year: Int, course: Int, grade: Int, decending: Boolean) {
        addSubscription(HttpApiClinet.retrofit().getTeacherService(tj,year,course,grade,decending)
                .map(HttpResultFunc<List<TeacherInfo>>()), object : HttpCallback<List<TeacherInfo>>(){
            override fun onSuccess(model: List<TeacherInfo>) {
                iView.onDateGet(model)
            }

            override fun onFailure(msg: String?) {
                if (msg != null) {
                    iView.onError(msg)
                }
            }

        })
    }
}