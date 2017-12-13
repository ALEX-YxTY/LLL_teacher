package com.meishipintu.lll_office.modles.entities

import android.text.TextUtils
import android.R.attr.keySet



/**
 * Created by Administrator on 2017/7/28.
 *
 * 主要功能：
 */
class PayResult(rawResult: Map<String, String>?) {
    /**
     * @return the resultStatus
     */
    var resultStatus: String? = null
        private set
    /**
     * @return the result
     */
    var result: String? = null
        private set
    /**
     * @return the memo
     */
    var memo: String? = null
        private set

    init {
        if (rawResult != null) {
            for (key in rawResult!!.keys) {
                when {
                    TextUtils.equals(key, "resultStatus") -> resultStatus = rawResult[key]
                    TextUtils.equals(key, "result") -> result = rawResult[key]
                    TextUtils.equals(key, "memo") -> memo = rawResult[key]
                }
            }
        }
    }
}