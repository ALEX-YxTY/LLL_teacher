package com.milai.lll_teacher

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

/**
 * Created by Administrator on 2017/7/6.
 *
 * 主要功能：
 */
object RxBus {
    val bus: Subject<Any> = PublishSubject.create<Any>().toSerialized()

    fun send(obj:Any){
        bus.onNext(obj)
    }

    fun <T> getObservable(eventType: Class<T>): Observable<T> {
        return bus.ofType(eventType)
    }
}