package com.milai.lll_teacher.models.https

import com.milai.lll_teacher.models.entities.*
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

    //重设密码
    @FormUrlEncoded
    @POST("Home/Api/forgetTeacherPassword")
    fun resetPswService(@Field("tel")tel:String,@Field("verify")vcode:String,@Field("password")psw:String):Observable<HttpResult<Any>>

    //教师登录
    @FormUrlEncoded
    @POST("Home/Api/teacher_login")
    fun loginService(@Field("tel") tel:String,@Field("password") psw:String):Observable<HttpResult<UserInfo>>

    //获取常数接口，type=1~9
    @FormUrlEncoded
    @POST("Home/Base/cs")
    fun getConstantArraysService(@Field("type") type:Int) :Observable<HttpResult<Array<String>>>

    //职位查询和筛选接口
    @FormUrlEncoded
    @POST("Home/organization/getAllPosition")
    fun getJobService(@Field("is_tj") isTj: Int, @Field("work_area") area: Int, @Field("course") course: Int
                      , @Field("grade") grade: Int, @Field("require_year") experience: Int,@Field("page") page:Int)
            : Observable<HttpResult<List<JobInfo>>>

    //查询所有机构
    @FormUrlEncoded
    @POST("Home/organization/getAllOrganization")
    fun getOrganizationgService(@Field("page") page: Int):Observable<HttpResult<List<OfficeInfo>>>

    //查询机构详情
    @FormUrlEncoded
    @POST("Home/Organization/getOrganizationDetail")
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
    fun getJobCollectService(@Field("tid") tid: String, @Field("page") page: Int): Observable<HttpResult<List<JobInfo>>>

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
    fun getOfficeCollectionService( @Field("tid") teacherId: String,@Field("page") page:Int): Observable<HttpResult<List<OfficeInfo>>>

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

    //获取系统通知
    @FormUrlEncoded
    @POST("Home/Api/getSystemNotice")
    fun getSysNoticeService(@Field("uid")oid:String,@Field("type")type:Int,@Field("page") page:Int):Observable<HttpResult<List<SysNoticeInfo>>>

    //获取私信通知
    @FormUrlEncoded
    @POST("Home/Api/getChatList")
    fun getChatListService(@Field("tid")tid:String,@Field("type")type:Int,@Field("oid") oid:String
                           ,@Field("page") page:Int):Observable<HttpResult<List<MessageNoticeInfo>>>

    //通过职位id获取职位详情
    @FormUrlEncoded
    @POST("Home/Organization/getPostionDetail")
    fun getPositionDetailServie(@Field("id")pid:Int):Observable<HttpResult<JobInfo>>

    //查询教师投递记录
    //tyep=1 投递记录 type=2 面试邀约
    //status 0-全部 1-未面试 2-已面试
    @FormUrlEncoded
    @POST("Home/Api/getTeacherDeliver")
    fun getTeacherDeliverServie(@Field("tid")tid:String, @Field("type") type:Int, @Field("status") status:Int
                                ,@Field("page")page:Int):Observable<HttpResult<List<DeliverInfo>>>

    //关键字搜索职位
    @FormUrlEncoded
    @POST("Home/Api/searchPositionList")
    fun getJobByKeyWorkService(@Field("content") keyWord: String, @Field("page") page: Int): Observable<HttpResult<List<JobInfo>>>

    //关键字搜索机构
    @FormUrlEncoded
    @POST("Home/Api/searchOrganizationList")
    fun getOfficeByKeyWorkService(@Field("content") keyWord:String, @Field("page") page: Int):Observable<HttpResult<List<OfficeInfo>>>

    //获取教师详情
    @FormUrlEncoded
    @POST("Home/Api/getTeacherDetail")
    fun getTeacherDetailService(@Field("uid") uid: String): Observable<HttpResult<UserInfo>>

    //获取最新消息接口
    //type=2 机构 type=1 老师
    //flag=1 查聊天记录  2 系统通知
    //返回state=1 有数据返回，state=2 无数据记录
    @FormUrlEncoded
    @POST("Home/Api/getNewsId")
    fun getNewestIdService(@Field("type") type: Int, @Field("flag") flag: Int, @Field("uid") uid: String): Observable<HttpResult<NewsId>>

    //获取最新版本信息
    @FormUrlEncoded
    @POST("Home/Api/getSystemVerision")
    fun getNewestVersion(@Field("type") type: Int): Observable<HttpResult<VersionInfo>>
}