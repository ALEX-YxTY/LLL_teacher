package com.meishipintu.lll_office.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import com.meishipintu.lll_office.*
import com.meishipintu.lll_office.modles.entities.BusMessage

class UpdateActivity : BasicActivity(),View.OnClickListener {

    var select = 0      //当前选择项
    var descs = Cookies.getConstant(12)
    val idList:List<View> by lazy {
        listOf(findViewById(R.id.rl_1), findViewById(R.id.rl_2), findViewById(R.id.rl_3), findViewById(R.id.rl_4))
    }
    val levels = Cookies.getConstant(7)
    val tvMoney:TextView by lazy { findViewById(R.id.tv_money) as TextView }
    val tvDetail:TextView by lazy{findViewById(R.id.level_detail) as TextView}
    val levelNow:Int by lazy{ intent.getIntExtra("levelNow", 0)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)
        RxBus.getObservable(BusMessage::class.java).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    (type) -> run{
                    when (type) {
                        Constant.PAY_SUCCESS -> {
                            //支付成功，退回
                            this.finish()
                         }
                    }
                }
                },{},{},{
                    t: Disposable -> disposables.add(t)
                })

        initUI()
    }

    private fun initUI() {
        val rl1 = findViewById(R.id.rl_1) as RelativeLayout
        val rl2 = findViewById(R.id.rl_2) as RelativeLayout
        val rl3 = findViewById(R.id.rl_3) as RelativeLayout
        val rl4 = findViewById(R.id.rl_4) as RelativeLayout

        val title = findViewById(R.id.tv_title) as TextView
        title.text = "升级账号"
        val ivHead = findViewById(R.id.iv_head) as ImageView
        Glide.with(this).load(OfficeApplication.userInfo?.avatar).error(R.drawable.organization_default)
                .into(ivHead)
        val officeName = findViewById(R.id.office_name) as TextView
        officeName.text = OfficeApplication.userInfo?.name
        val accountLevel = findViewById(R.id.account_level) as TextView
        Log.d("test","levelnow: $levelNow")
        if (levelNow > 0) {
            accountLevel.text = levels[levelNow - 1].substring(0, 4)
        } else {
            accountLevel.text = "普通会员"
        }
        when (levelNow) {
            0 ->{
                rl1.setOnClickListener(this)
                rl2.setOnClickListener(this)
                rl3.setOnClickListener(this)
                rl4.setOnClickListener(this)
                changeTo(0)
            }
            1 ->{
                rl1.setOnClickListener(this)
                rl2.setOnClickListener(this)
                rl3.setOnClickListener(this)
                rl4.setOnClickListener(this)
                changeTo(1)
            }
            2 ->{
                rl1.isClickable = false
                rl2.setOnClickListener(this)
                rl3.setOnClickListener(this)
                rl4.setOnClickListener(this)
                changeTo(2)
            }
            3 ->{
                rl1.isClickable = false
                rl2.isClickable = false
                rl3.setOnClickListener(this)
                rl4.setOnClickListener(this)
                changeTo(3)
            }
        }
        findViewById(R.id.bt_pay).setOnClickListener(this)
    }

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

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.rl_1 -> { if(select!=0) changeTo(0)}
            R.id.rl_2 -> {if(select!=1) changeTo(1)}
            R.id.rl_3 -> {if(select!=2) changeTo(2)}
            R.id.rl_4 -> {if(select!=3) changeTo(3)}
            R.id.bt_pay -> {
                if (levelNow == 0) {
                    //先去完善信息
                    val intent = Intent(this, updateInformationActivity::class.java)
                    Log.d("test", "level: $select, levelWant: ${levels[select]}")
                    intent.putExtra("level", select)
                    startActivity(intent)
                } else {
                    //直接去付款
                    val intent = Intent(this, PayActivity::class.java)
                    intent.putExtra("money",levels[select].split("&")[1].toFloat() )
                    intent.putExtra("levelWant", select)
                    startActivity(intent)
                }

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
        tvDetail.text = descs[index]
    }
}
