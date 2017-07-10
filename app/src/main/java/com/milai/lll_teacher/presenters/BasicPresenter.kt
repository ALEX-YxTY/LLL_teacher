package com.milai.lll_teacher.presenters

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by Administrator on 2017/7/4.
 *
 * 主要功能：
 */
open class BasicPresenter: BasicPresenterImp {

    val disposables: CompositeDisposable by lazy{ CompositeDisposable() }


    //添加订阅
    open fun <M>addSubscription(observable: Observable<M>, subscriber: Observer<M>) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    result -> subscriber.onNext(result)
                },{
                    e:Throwable -> subscriber.onError(e)
                },{
                    -> subscriber.onComplete()
                },{
                    t: Disposable -> disposables.add(t)
                })
    }

    override fun unsubscribe() {
        disposables.clear()
    }
}