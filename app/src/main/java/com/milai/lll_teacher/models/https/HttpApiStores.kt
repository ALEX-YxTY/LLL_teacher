package com.milai.lll_teacher.models.https

import com.milai.lll_teacher.models.entities.HttpResult
import com.milai.lll_teacher.models.entities.JobInfo
import com.milai.lll_teacher.models.entities.OfficeInfo
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Created by Administrator on 2017/7/4.
 *
 * 主要功能：
 */
interface HttpApiStores {

    //发送验证码
    @FormUrlEncoded
    @POST("Home/Base/getVerifyCodeByMobile")
    fun getVCodeService(@Field("mobile")mobile:String):Observable<HttpResult<String>>

    //获取常数接口，type=1~9
    @FormUrlEncoded
    @POST("Home/Base/cs")
    fun getConstantArraysService(@Field("type") type:Int) :Observable<HttpResult<Array<String>>>

    //职位查询和筛选接口
    @FormUrlEncoded
    @POST("Home/organization/getAllPosition")
    fun getJobService(@Field("isTj") isTj: Boolean, @Field("area") area: Int, @Field("course") course: Int
                      , @Field("grade") grade: Int, @Field("experience") experience: Int)
            : Observable<HttpResult<List<JobInfo>>>

    //查询所有机构
    @POST("Home/organization/getAllOrganization")
    fun getOrganizationgService():Observable<HttpResult<List<OfficeInfo>>>

    //查询机构发布的职位
    @FormUrlEncoded
    @POST("Home/Organization/getOrganizationPosition")
    fun getOfficeJobService(@Field("oid") oid:String,@Field("status") status:Int):Observable<HttpResult<List<JobInfo>>>

}