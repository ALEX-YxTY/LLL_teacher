package com.meishipintu.lll_office.views

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.meishipintu.lll_office.Cookies
import com.meishipintu.lll_office.OfficeApplication
import com.meishipintu.lll_office.R
import com.meishipintu.lll_office.modles.HttpApiClinet
import com.meishipintu.lll_office.modles.HttpCallback
import com.meishipintu.lll_office.modles.HttpResultFunc
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.lang.ref.WeakReference
import android.widget.Toast
import com.meishipintu.lll_office.modles.entities.PayResult


class UpdateActivity : BasicActivity(),View.OnClickListener {

    var select = 0      //当前选择项
    val idList:List<View> by lazy {
        listOf(findViewById(R.id.rl_1), findViewById(R.id.rl_2), findViewById(R.id.rl_3), findViewById(R.id.rl_4))
    }
    val levels = Cookies.getConstant(7)
    val tvMoney:TextView by lazy { findViewById(R.id.tv_money) as TextView }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)
        initUI()
    }

    private fun initUI() {
        val title = findViewById(R.id.tv_title) as TextView
        title.text = "升级账号"
        val ivHead = findViewById(R.id.iv_head) as ImageView
        Glide.with(this).load(OfficeApplication.userInfo?.avatar).error(R.drawable.organization_default)
                .into(ivHead)
        val officeName = findViewById(R.id.office_name) as TextView
        officeName.text = OfficeApplication.userInfo?.name
        val accountLevel = findViewById(R.id.account_level) as TextView
        var name:String
        when (OfficeApplication.userInfo?.level) {
            2 -> name = levels[1]
            3 -> name = levels[2]
            4 -> name = levels[3]
            else -> name = levels[0]
        }
        accountLevel.text = name.substring(0,4)
        findViewById(R.id.rl_1).setOnClickListener(this)
        findViewById(R.id.rl_2).setOnClickListener(this)
        findViewById(R.id.rl_3).setOnClickListener(this)
        findViewById(R.id.rl_4).setOnClickListener(this)
        findViewById(R.id.bt_pay).setOnClickListener(this)
    }

    val disposables: CompositeDisposable by lazy{ CompositeDisposable() }
    val SDK_PAY_FLAG = 1
    var handler:MyHandler? = null
    var orderID: String = ""

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

    class MyHandler internal constructor(ctx: UpdateActivity): Handler(){

        private val reference: WeakReference<UpdateActivity> = WeakReference(ctx)

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
                    } else if("6001"==resultStatus){
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(activity, "支付取消", Toast.LENGTH_SHORT).show()
                        if (!activity.orderID.isNullOrEmpty()) {
                            activity.addSubscription(HttpApiClinet.retrofit().cancelOrderService(activity.orderID)
                                    .map(HttpResultFunc<Any>()),object :HttpCallback<Any>(){
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

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.rl_1 -> { if(select!=0) changeTo(0)}
            R.id.rl_2 -> {if(select!=1) changeTo(1)}
            R.id.rl_3 -> {if(select!=2) changeTo(2)}
            R.id.rl_4 -> {if(select!=3) changeTo(3)}
            R.id.bt_pay -> {
                //先去完善信息
                val intent = Intent(this, updateInformationActivity::class.java)
                intent.putExtra("level",levels[select])
                startActivity(intent)

//                if (handler == null) {
//                    handler = MyHandler(this)
//                }
//                //直接支付
//                addSubscription(HttpApiClinet.retrofit().getOrderStr("会员升级","01"
//                        ,0,levels[select].split("&")[0],levels[select].split("&")[1].toFloat()
//                        ,select+1,OfficeApplication.userInfo?.uid!!).map(HttpResultFunc<OrderInfo>())
//                        ,object :HttpCallback<OrderInfo>(){
//                    override fun onSuccess(orderInfo: OrderInfo) {
//                        Log.d("test","resutl: ${orderInfo.url}")
//                        val payRunnable = Runnable {
//                            val alipay = PayTask(this@UpdateActivity)
//                            val result = alipay.payV2(orderInfo.url, true)
//                            orderID = orderInfo.order_id
//                            Log.d("test", result.toString())
//
//                            val msg = handler?.obtainMessage()
//                            msg?.what = SDK_PAY_FLAG
//                            msg?.obj = result
//                            handler?.sendMessage(msg)
//                        }
//
//                        val payThread = Thread(payRunnable)
//                        payThread.start()
//                    }
//
//                    override fun onFailure(msg: String?) {
//                        if (msg != null) {
//                            toast("获取支付信息失败，请稍后重试")
//                        }
//                    }
//                })

            }
        }
    }



    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }

    private fun changeTo(index: Int) {
        idList[select].setBackgroundResource(R.drawable.shape_tv_lable_unselect_raidus8)
        select = index
        idList[select].setBackgroundResource(R.drawable.shape_tv_lable_radiu8)
        tvMoney.text = "¥ ${levels[select].split("&")[1]}"
    }
}
