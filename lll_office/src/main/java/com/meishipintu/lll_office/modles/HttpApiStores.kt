package com.meishipintu.lll_office.modles

import com.meishipintu.lll_office.modles.entities.*
import io.reactivex.Observable
import okhttp3.ResponseBody
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

    //机构注册
    @FormUrlEncoded
    @POST("Home/organization/organization_regist")
    fun registerService(@Field("tel") tel:String,@Field("name") name:String,@Field("password") password:String
                        ,@Field("verify") verify:String):Observable<HttpResult<UserInfo>>

    //教师查询及筛选
    @FormUrlEncoded
    @POST("Home/Api/getAllTeacher")
    fun getTeacherService(@Field("isTj") tj: Boolean, @Field("year") year: Int, @Field("course") course: Int
                          , @Field("grade") grade: Int, @Field("decending") isDecending: Boolean): Observable<HttpResult<List<TeacherInfo>>>

    //登录接口
    @FormUrlEncoded
    @POST("Home/Organization/organization_login")
    fun loginService(@Field("account") account: String, @Field("password") psw: String):Observable<HttpResult<UserInfo>>


    //发布职位接口
    @FormUrlEncoded
    @POST("Home/organization/addPosition")
    fun addJobService(@Field("job_name") jobName:String,@Field("oid") oid:String,@Field("work_area") area:Int
                           ,@Field("work_address") address:String,@Field("course") course:Int,@Field("grade") grade:Int
                           ,@Field("sex") sex:Int,@Field("require_year") rYear:Int,@Field("money") money:String
                           ,@Field("have_certificate") have_certificate:Int
                           ,@Field("require") require:String,@Field("other_demand") other:String)
            :Observable<HttpResult<Any>>

    //获取机构发布的职位
    @FormUrlEncoded
    @POST("Home/Organization/getOrganizationPosition")
    fun getOfficeJobService(@Field("oid") oid:String,@Field("status") status:Int):Observable<HttpResult<List<JobInfo>>>

    //修改职位状态接口
    @FormUrlEncoded
    @POST("Home/Organization/updatePostionStatus")
    fun changeJobStatusService(@Field("id") jid:String,@Field("status") status:Int):Observable<HttpResult<JobInfo>>

    //获取常数接口，type=1~9
    @FormUrlEncoded
    @POST("Home/Base/cs")
    fun getConstantArraysService(@Field("type") type:Int) :Observable<HttpResult<Array<String>>>

    //获取机构被投递记录
    @FormUrlEncoded
    @POST("Home/Organization/getOrganizationDeliver")
    fun getDeliverHistoryService(@Field("oid") oid: String, @Field("status") status: Int, @Field("type") type: Int)
            : Observable<HttpResult<List<DeliverInfo>>>

    //查询机构详情
    @FormUrlEncoded
    @POST("Home/Organization/getOrganizationDetail ")
    fun getOrganizationDetaioService(@Field("uid") uid:String):Observable<HttpResult<OfficeInfo>>

    //获取聊天详情
    @FormUrlEncoded
    @POST("Home/Api/getChatDetail")
    fun getChatDetailService(@Field("tid") teacherId:String,@Field("pid") jobId:Int):Observable<HttpResult<List<ChatDetail>>>

    //发送新的聊天
    @FormUrlEncoded
    @POST("Home/Api/chat")
    fun sendChatService(@Field("pid") jobId:Int,@Field("tid") teacherId:String, @Field("oid") officeId:String
                        ,@Field("content") content:String,@Field("type") type:Int):Observable<HttpResult<Any>>
}