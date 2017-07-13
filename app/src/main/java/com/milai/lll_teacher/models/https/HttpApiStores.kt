package com.milai.lll_teacher.models.https

import com.milai.lll_teacher.models.entities.ChatDetail
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

    //查询机构详情
    @FormUrlEncoded
    @POST("Home/Organization/getOrganizationDetail ")
    fun getOrganizationDetaioService(@Field("uid") uid:String):Observable<HttpResult<OfficeInfo>>

    //查询机构发布的职位
    @FormUrlEncoded
    @POST("Home/Organization/getOrganizationPosition")
    fun getOfficeJobService(@Field("oid") oid:String,@Field("status") status:Int):Observable<HttpResult<List<JobInfo>>>

    //查询教师是否已收藏某职位
    @FormUrlEncoded
    @POST("Home/Api/getTeacherIsLikedPosition")
    fun isJobCollectedService(@Field("pid") pid:Int, @Field("tid") uid:String):Observable<HttpResult<List<Any>>>

    //添加收藏职位
    @FormUrlEncoded
    @POST("Home/Api/addTeacherLikedPosition")
    fun addJobCollectionService(@Field("pid") jobId:Int,@Field("tid") uid:String):Observable<HttpResult<Any>>

    //删除收藏职位
    @FormUrlEncoded
    @POST("Home/Api/deleteTeacherLikedPosition")
    fun delectJobCollectionService(@Field("pid") jobId:Int, @Field("tid") uid:String):Observable<HttpResult<Any>>


    //查询收藏职位列表
    @FormUrlEncoded
    @POST("Home/Api/getTeacherLikedPosition")
    fun getJobCollectService(@Field("tid") tid:String):Observable<HttpResult<List<JobInfo>>>

    //教师主动投递简历
    @FormUrlEncoded
    @POST("Home/Api/sendResume")
    fun sendResumeService(@Field("pid") jobId: Int, @Field("tid") teacherId: String, @Field("oid") oid: String
                          , @Field("type") type: Int):Observable<HttpResult<Any>>

    //教师添加收藏机构
    @FormUrlEncoded
    @POST("Home/Api/addTeacherLikedOrganization")
    fun addOfficeCollectionService(@Field("oid") officeId: String, @Field("tid") teacherId: String): Observable<HttpResult<Any>>

    //教师删除收藏机构
    @FormUrlEncoded
    @POST("Home/Api/deleteTeacherLikedOrganization")
    fun deletOfficeCollectionService(@Field("oid") officeId: String, @Field("tid") teacherId: String): Observable<HttpResult<Any>>

    //教师查询收藏的机构
    @FormUrlEncoded
    @POST("Home/Api/getTeacherLikedOrganization")
    fun getOfficeCollectionService( @Field("tid") teacherId: String): Observable<HttpResult<List<OfficeInfo>>>

    //教师是否收藏了机构
    @FormUrlEncoded
    @POST("Home/Api/getTeacherIsLikedOrganization")
    fun isOfficeCollectedService(@Field("oid") officeId: String,  @Field("tid") teacherId: String): Observable<HttpResult<List<Any>>>

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