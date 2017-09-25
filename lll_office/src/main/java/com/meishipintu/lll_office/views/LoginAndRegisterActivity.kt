package com.meishipintu.lll_office.views

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.meishipintu.lll_office.Constant
import com.meishipintu.lll_office.R
import com.meishipintu.lll_office.RxBus
import com.meishipintu.lll_office.modles.entities.BusMessage
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class LoginAndRegisterActivity : BasicActivity(),View.OnClickListener{

    var clickTime: Long = 0
    val disposables: CompositeDisposable by lazy{ CompositeDisposable() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_and_regist)
        RxBus.getObservable(BusMessage::class.java).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    (type) -> run{
                    when (type) {
                        Constant.LOGIN_SUCCESS,Constant.REGIST_SUCCESS -> {
                            //登陆成功后退出此页面
                            this.finish()
                        }
                    }
                }
                },{},{},{
                    t: Disposable -> disposables.add(t)
                })
        findViewById(R.id.bt_back).setOnClickListener(this)
        findViewById(R.id.bt_login).setOnClickListener(this)
        findViewById(R.id.bt_register).setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.bt_back -> {
                onBackPressed()
            }
            R.id.bt_login -> startActivity(Intent(this, LoginActivity::class.java))
            R.id.bt_register -> {
                startActivity(Intent(this, ContractorActivity::class.java))
            }
        }
    }

    override fun onBackPressed() {
        if (System.currentTimeMillis() - clickTime > 1000) {
            toast("再次点击退出程序")
            clickTime = System.currentTimeMillis()
        } else {
            this.finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }
}
