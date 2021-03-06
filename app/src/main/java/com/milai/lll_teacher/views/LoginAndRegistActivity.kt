package com.milai.lll_teacher.views

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.milai.lll_teacher.Constant
import com.milai.lll_teacher.R
import com.milai.lll_teacher.RxBus
import com.milai.lll_teacher.models.entities.BusMessage
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class LoginAndRegistActivity : AppCompatActivity() ,View.OnClickListener{

    val disposables: CompositeDisposable by lazy{ CompositeDisposable() }

    var clickTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_and_regist)
        findViewById(R.id.bt_back).setOnClickListener(this)
        findViewById(R.id.bt_login).setOnClickListener(this)
        findViewById(R.id.bt_register).setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.bt_back -> onBackPressed()
            R.id.bt_login -> startActivity(Intent(this@LoginAndRegistActivity, LoginActivity::class.java))
            R.id.bt_register -> {
                startActivity(Intent(this@LoginAndRegistActivity,ContractorActivity::class.java))
            }
        }
    }

    override fun onBackPressed() {
        this.finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }
}
