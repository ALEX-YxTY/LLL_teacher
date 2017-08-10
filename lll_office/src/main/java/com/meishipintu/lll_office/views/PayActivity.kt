package com.meishipintu.lll_office.views

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.alipay.sdk.app.PayTask
import com.meishipintu.lll_office.*
import com.meishipintu.lll_office.customs.utils.NumberUtil
import com.meishipintu.lll_office.modles.HttpApiClinet
import com.meishipintu.lll_office.modles.HttpCallback
import com.meishipintu.lll_office.modles.HttpResultFunc
import com.meishipintu.lll_office.modles.entities.BusMessage
import com.meishipintu.lll_office.modles.entities.OrderInfo
import com.meishipintu.lll_office.modles.entities.PayResult
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.lang.ref.WeakReference

class PayActivity : BasicActivity() {

    val disposables: CompositeDisposable by lazy{ CompositeDisposable() }
    val SDK_PAY_FLAG = 1
    var handler:MyHandler? = null
    var orderID: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay)

        val levels = Cookies.getConstant(7)
        val money = intent.getFloatExtra("money", 49.0f)
        val levelWant = intent.getIntExtra("levelWant", 0)

        val tvTitle = findViewById(R.id.tv_title)as TextView
        tvTitle.text = "支付"
        findViewById(R.id.bt_back).setOnClickListener{ onBackPressed()}
        val tvMoney = findViewById(R.id.tv_money) as TextView
        tvMoney.text = "¥${NumberUtil.formatNumberInTwo(money)}"
        val btPay = findViewById(R.id.bt_pay) as Button
        btPay.text = "确认支付 ¥${NumberUtil.formatNumberInTwo(money)}"
        btPay.setOnClickListener{
            if (handler == null) {
                handler = MyHandler(this)
            }
            //直接支付
            addSubscription(HttpApiClinet.retrofit().getOrderStr("会员升级", "01"
                    , 0, levels[levelWant].split("&")[0], levels[levelWant].split("&")[1].toFloat()
                    , levelWant+1, OfficeApplication.userInfo?.uid!!).map(HttpResultFunc<OrderInfo>())
                    , object : HttpCallback<OrderInfo>() {
                override fun onSuccess(orderInfo: OrderInfo) {
                    Log.d("test", "resutl: ${orderInfo.url}")
                    val payRunnable = Runnable {
                        val alipay = PayTask(this@PayActivity)
                        val result = alipay.payV2(orderInfo.url, true)
                        orderID = orderInfo.order_id
                        Log.d("test", result.toString())

                        val msg = handler?.obtainMessage()
                        msg?.what = SDK_PAY_FLAG
                        msg?.obj = result
                        handler?.sendMessage(msg)
                    }

                    val payThread = Thread(payRunnable)
                    payThread.start()
                }

                override fun onFailure(msg: String?) {
                    if (msg != null) {
                        toast("获取支付信息失败，请稍后重试")
                    }
                }
            })
        }
    }


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

    class MyHandler internal constructor(ctx: PayActivity): Handler(){

        private val reference: WeakReference<PayActivity> = WeakReference(ctx)

        override fun handleMessage(msg: Message) {
            var activity = reference.get()
            when (msg.what) {
                activity?.SDK_PAY_FLAG ->{
                    val payResult = PayResult(msg.obj as Map<String, String>)
                    /**
                    对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    val resultInfo = payResult.result// 同步返回需要验证的信息
                    Log.d("test","resultInfo: $resultInfo")
                    val resultStatus = payResult.resultStatus
                    // 判断resultStatus 为9000则代表支付成功
                    if ("9000" == resultStatus) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(activity, "支付成功", Toast.LENGTH_SHORT).show()
                        activity.finish()
                        RxBus.send(BusMessage(Constant.PAY_SUCCESS))
                    } else if("6001"==resultStatus){
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(activity, "支付取消", Toast.LENGTH_SHORT).show()
                        if (!activity.orderID.isNullOrEmpty()) {
                            activity.addSubscription(HttpApiClinet.retrofit().cancelOrderService(activity.orderID)
                                    .map(HttpResultFunc<Any>()),object : HttpCallback<Any>(){
                                override fun onSuccess(model: Any) {
                                }

                                override fun onFailure(msg: String?) {
                                }
                            })
                        }
                    }
                }
            }
        }
    }
}
