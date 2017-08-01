package com.meishipintu.lll_office.presenters

import com.meishipintu.lll_office.contract.UpdateInfoContract
import com.meishipintu.lll_office.modles.HttpApiClinet
import com.meishipintu.lll_office.modles.HttpCallback
import com.meishipintu.lll_office.modles.HttpResultFunc
import com.meishipintu.lll_office.modles.entities.UserInfo
import com.meishipintu.lll_office.views.BasicView
import okhttp3.MediaType
import java.io.File
import okhttp3.RequestBody
import okhttp3.MultipartBody



/**
 * Created by Administrator on 2017/7/28.
 *
 * 主要功能：
 */
class UpdateInfoPresenter(val iView:BasicView):BasicPresenter(),UpdateInfoContract.IPresenter {

    val httpApi = HttpApiClinet.retrofit()

    override fun updateOfficeInfo(uid: String, name: String, address: String, contact: String, contactTel: String
                                  , introduce: String, certificate: File) {
        //将file类型转化为MultipartBody.part类型
        val photoRequestBody = RequestBody.create(MediaType.parse("image/*"), certificate)
        val photo = MultipartBody.Part.createFormData("certification", "certificate.jpg", photoRequestBody)
        val uidRequest = RequestBody.create(null, uid)
        val nameRequest = RequestBody.create(null, name)
        val addressRequest = RequestBody.create(null, address)
        val contactRequest = RequestBody.create(null, contact)
        val contactTelRequest = RequestBody.create(null, contactTel)
        val introduceRequest = RequestBody.create(null, introduce)
        addSubscription(httpApi.updateOfficeInfoService(uidRequest, addressRequest, nameRequest
                , contactRequest, contactTelRequest, introduceRequest, photo).map(HttpResultFunc<UserInfo>())
                ,object :HttpCallback<UserInfo>(){
            override fun onSuccess(model: UserInfo) {
                (iView as UpdateInfoContract.IView).onUploadSuccess(model)
            }

            override fun onFailure(msg: String?) {
                if (msg != null) {
                    iView.onError(msg)
                }
            }
        })
    }

    override fun updateOfficeInfo(uid: String, name: String, address: String, contact: String, contactTel: String
                                  , introduce: String) {
        addSubscription(httpApi.updateOfficeInfoService(uid, address, name
                , contact, contactTel, introduce).map(HttpResultFunc<UserInfo>())
                ,object :HttpCallback<UserInfo>(){
            override fun onSuccess(model: UserInfo) {
                (iView as UpdateInfoContract.IView).onUploadSuccess(model)
            }

            override fun onFailure(msg: String?) {
                if (msg != null) {
                    iView.onError(msg)
                }
            }
        })
    }
}