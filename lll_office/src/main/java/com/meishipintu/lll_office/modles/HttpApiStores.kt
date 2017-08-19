package com.meishipintu.lll_office.modles

import com.meishipintu.lll_office.modles.entities.*
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

/**
 * Created by Administrator on 2017/7/4.
 *
 * 主要功能：
 */
interface HttpApiStores {

    //发送验证码
    @FormUrlEncoded
    @POST("Home/Base/getVerifyCodeByMobile")
    fun getVCodeService(@Field("mobile") mobile: String): Observable<HttpResult<String>>

    //机构注册
    @FormUrlEncoded
    @POST("Home/organization/organization_regist")
    fun registerService(@Field("tel") tel: String, @Field("name") name: String, @Field("password") password: String
                        , @Field("verify") verify: String): Observable<HttpResult<UserInfo>>

    //教师查询及筛选
    @FormUrlEncoded
    @POST("Home/Api/getAllTeacher")
    fun getTeacherService(@Field("is_tj") tj: Int, @Field("require_year") year: Int, @Field("course") course: Int
                          , @Field("grade") grade: Int, @Field("is_px") isDecending: Int
                          , @Field("page") page: Int): Observable<HttpResult<List<TeacherInfo>>>

    //根据关键字搜索教师
    @FormUrlEncoded
    @POST("Home/Api/searchTeacherList")
    fun getTeacherByKeyWorkService(@Field("content") keyWord: String): Observable<HttpResult<List<TeacherInfo>>>

    //登录接口
    @FormUrlEncoded
    @POST("Home/Organization/organization_login")
    fun loginService(@Field("account") account: String, @Field("password") psw: String): Observable<HttpResult<UserInfo>>


    //发布职位接口
    @FormUrlEncoded
    @POST("Home/organization/addPosition")
    fun addJobService(@Field("job_name") jobName: String, @Field("oid") oid: String, @Field("work_area") area: Int
                      , @Field("work_address") address: String, @Field("course") course: Int, @Field("grade") grade: Int
                      , @Field("sex") sex: Int, @Field("require_year") rYear: Int, @Field("money") money: String
                      , @Field("have_certificate") have_certificate: Int
                      , @Field("require") require: String, @Field("other_demand") other: String)
            : Observable<HttpResult<Any>>

    //获取机构发布的职位
    @FormUrlEncoded
    @POST("Home/Organization/getOrganizationPosition")
    fun getOfficeJobService(@Field("oid") oid: String, @Field("status") status: Int): Observable<HttpResult<List<JobInfo>>>

    //修改职位状态接口
    @FormUrlEncoded
    @POST("Home/Organization/updatePostionStatus")
    fun changeJobStatusService(@Field("id") jid: String, @Field("status") status: Int): Observable<HttpResult<JobInfo>>

    //获取常数接口，type=1~9
    @FormUrlEncoded
    @POST("Home/Base/cs")
    fun getConstantArraysService(@Field("type") type: Int): Observable<HttpResult<Array<String>>>

    //获取机构被投递记录
    @FormUrlEncoded
    @POST("Home/Organization/getOrganizationDeliver")
    fun getDeliverHistoryService(@Field("oid") oid: String, @Field("status") status: Int, @Field("type") type: Int
                                 , @Field("page") page: Int)
            : Observable<HttpResult<List<DeliverInfo>>>

    //查询机构详情
    @FormUrlEncoded
    @POST("Home/Organization/getOrganizationDetail ")
    fun getOrganizationDetailService(@Field("uid") uid: String): Observable<HttpResult<UserInfo>>

    //获取聊天详情
    @FormUrlEncoded
    @POST("Home/Api/getChatDetail")
    fun getChatDetailService(@Field("tid") teacherId: String, @Field("pid") jobId: Int): Observable<HttpResult<List<ChatDetail>>>

    //发送新的聊天
    @FormUrlEncoded
    @POST("Home/Api/chat")
    fun sendChatService(@Field("pid") jobId: Int, @Field("tid") teacherId: String, @Field("oid") officeId: String
                        , @Field("content") content: String, @Field("type") type: Int): Observable<HttpResult<Any>>

    //变更投递记录的状态（已面试，已评价等）
    @FormUrlEncoded
    @POST("Home/Api/updateInterviewStatus")
    fun updateDeliverStatusService(@Field("id") deliverId: Int, @Field("status") status: Int, @Field("score") score: Int
                                   , @Field("evaluate") evaluat: String): Observable<HttpResult<DeliverInfo>>

    //机构是否收藏该教师
    @FormUrlEncoded
    @POST("Home/Api/getOrganizationLikedTeacher")
    fun isCollectedTeacherService(@Field("tid") teacherId: String, @Field("oid") uid: String): Observable<HttpResult<List<Any>>>

    //机构收藏教师
    @FormUrlEncoded
    @POST("Home/Api/addTeacherLike")
    fun addTeacherCollectionService(@Field("tid") teacherId: String, @Field("oid") uid: String): Observable<HttpResult<Any>>

    //取消机构收藏教师
    @FormUrlEncoded
    @POST("Home/Api/deleteTeacherLike")
    fun deleteTeacherCollectionService(@Field("tid") teacherId: String, @Field("oid") uid: String): Observable<HttpResult<Any>>

    //获取机构收藏教师列表
    @FormUrlEncoded
    @POST("Home/Api/getTeacherLike")
    fun getTeacherCollectService(@Field("oid") uid: String, @Field("page") page: Int): Observable<HttpResult<List<TeacherInfo>>>

    //机构邀请教师面试
    @FormUrlEncoded
    @POST("Home/Api/sendResume")
    fun sendResumeService(@Field("pid") jobId: Int, @Field("tid") teacherId: String, @Field("oid") oid: String
                          , @Field("type") type: Int): Observable<HttpResult<Any>>

    //获取其他机构
    @FormUrlEncoded
    @POST("Home/organization/getAllOrganization")
    fun getOtherOrganizationService(@Field("uid") oid: String, @Field("page") page: Int): Observable<HttpResult<List<OfficeInfo>>>

    //教师添加收藏机构
    @FormUrlEncoded
    @POST("Home/Api/addTeacherLikedOrganization")
    fun addOfficeCollectionService(@Field("oid") officeId: String, @Field("tid") teacherId: String): Observable<HttpResult<Any>>

    //教师删除收藏机构
    @FormUrlEncoded
    @POST("Home/Api/deleteTeacherLikedOrganization")
    fun deletOfficeCollectionService(@Field("oid") officeId: String, @Field("tid") teacherId: String): Observable<HttpResult<Any>>

    //教师是否收藏了机构
    @FormUrlEncoded
    @POST("Home/Api/getTeacherIsLikedOrganization")
    fun isOfficeCollectedService(@Field("oid") officeId: String, @Field("tid") teacherId: String): Observable<HttpResult<List<Any>>>

    //获取系统通知
    @FormUrlEncoded
    @POST("Home/Api/getSystemNotice")
    fun getSysNoticeService(@Field("uid") oid: String, @Field("type") type: Int, @Field("page") page: Int): Observable<HttpResult<List<SysNoticeInfo>>>

    //获取私信通知
    @FormUrlEncoded
    @POST("Home/Api/getChatList")
    fun getChatListService(@Field("tid") tid: String, @Field("type") type: Int, @Field("oid") oid: String, @Field("page") page: Int)
            : Observable<HttpResult<List<MessageNoticeInfo>>>

    //通过职位id获取职位详情
    @FormUrlEncoded
    @POST("Home/Organization/getPostionDetail")
    fun getPositionDetailServie(@Field("id") pid: Int): Observable<HttpResult<JobInfo>>

    //获取教师详情
    @FormUrlEncoded
    @POST("Home/Api/getTeacherDetail")
    fun getTeacherDetailServie(@Field("uid") tid: String): Observable<HttpResult<TeacherInfo>>

    //获取订单orderStr
    @FormUrlEncoded
    @POST("Home/Payment/pay")
    fun getOrderStr(@Field("subject") subject: String, @Field("type") type: String, @Field("aid") aid: Int
                    , @Field("body") name: String, @Field("money") money: Float, @Field("level") level: Int
                    , @Field("oid") uid: String): Observable<HttpResult<OrderInfo>>

    //订单取消接口
    @FormUrlEncoded
    @POST("Home/Payment/cancelOrder")
    fun cancelOrderService(@Field("order_id") order_id: String): Observable<HttpResult<Any>>

    //修改机构信息接口
    //上传单个文件
    @Multipart
    @POST("Home/organization/organization_update")
    fun updateOfficeInfoService(@Part("uid") uid: RequestBody, @Part("address") address: RequestBody
                                , @Part("name") name: RequestBody, @Part("contact") contact: RequestBody
                                , @Part("contact_tel") contactTel: RequestBody, @Part("introduce_detail") introduce: RequestBody
                                , @Part file1: MultipartBody.Part): Observable<HttpResult<UserInfo>>

    //修改机构信息接口2
    //不上传图片
    @FormUrlEncoded
    @POST("Home/organization/organization_update")
    fun updateOfficeInfoService(@Field("uid") uid: String, @Field("address") address: String
                                , @Field("name") name: String, @Field("contact") contact: String
                                , @Field("contact_tel") contactTel: String
                                , @Field("introduce_detail") introduce: String): Observable<HttpResult<UserInfo>>

    //修改机构信息接口3
    //上传两个图片
    @Multipart
    @POST("Home/organization/organization_update")
    fun updateOfficeInfoService(@Part("uid") uid: RequestBody, @Part("address") address: RequestBody
                                , @Part("name") name: RequestBody, @Part("contact") contact: RequestBody
                                , @Part("contact_tel") contactTel: RequestBody, @Part("introduce_detail") introduce: RequestBody
                                , @Part file1: MultipartBody.Part, @Part file2: MultipartBody.Part): Observable<HttpResult<UserInfo>>

    //获取新闻内容
    @FormUrlEncoded
    @POST("Home/Api/getNews")
    fun getNewsService(@Field("page") page: Int): Observable<HttpResult<List<NewsInfo>>>

    //获取广告轮播内容
    @POST("Home/Api/getAdver")
    fun getAdsService(): Observable<HttpResult<List<AdInfo>>>

    //获取最新消息接口
    //type=2 机构 type=1 老师
    //flag=1 查聊天记录  2 系统通知
    //返回state=1 有数据返回，state=2 无数据记录
    @FormUrlEncoded
    @POST("Home/Api/getNewsId")
    fun getNewestIdService(@Field("type") type: Int, @Field("flag") flag: Int, @Field("uid") uid: String)
            : Observable<HttpResult<NewsId>>

    //获取最新版本信息
    @FormUrlEncoded
    @POST("Home/Api/getSystemVerision")
    fun getNewestVersion(@Field("type") type: Int): Observable<HttpResult<VersionInfo>>

    //动作统计接口
    //flag=1教师端，flag=2 机构端
    //type=1 新闻事件
    //id_detail 事件详情，新闻为新闻id
    @FormUrlEncoded
    @POST("Home/Api/log")
    fun doActionSattistic(@Field("uid") uid:String,@Field("flag") flag:Int,@Field("type") type:Int
                          ,@Field("id_detail") detail:String):Observable<HttpResult<Any>>

}