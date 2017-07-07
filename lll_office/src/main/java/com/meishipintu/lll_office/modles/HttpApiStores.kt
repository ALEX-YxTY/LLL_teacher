package com.meishipintu.lll_office.modles

import com.meishipintu.lll_office.modles.entities.HttpResult
import com.meishipintu.lll_office.modles.entities.JobInfo
import com.meishipintu.lll_office.modles.entities.TeacherInfo
import com.meishipintu.lll_office.modles.entities.UserInfo
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
    fun getTeacherService(@Field("year") year: Int, @Field("course") course: Int
                          , @Field("grade") grade: Int): Observable<HttpResult<List<TeacherInfo>>>

    //登录接口
    @FormUrlEncoded
    @POST("Home/Organization/organization_login")
    fun loginService(@Field("account") account: String, @Field("password") psw: String):Observable<HttpResult<UserInfo>>


    //发布职位接口
    @FormUrlEncoded
    @POST("Home/organization/addPosition")
    fun addPositionService(@Field("job_name") jobName:String,@Field("oid") oid:String,@Field("work_area") area:Int
                           ,@Field("work_address") address:String,@Field("course") course:Int,@Field("grade") grade:Int
                           ,@Field("sex") sex:Int,@Field("year") year:Int,@Field("require_year") rYear:Int
                           ,@Field("money") money:String,@Field("require") require:String,@Field("other_demand") other:String)
            :Observable<ResponseBody>

    //获取机构发布的职位
    @FormUrlEncoded
    @POST("Home/Organization/getOrganizationPosition")
    fun getOfficeJobService(@Field("oid") oid:String,@Field("status") status:Int):Observable<HttpResult<List<JobInfo>>>

    //修改职位状态接口
    @FormUrlEncoded
    @POST("Home/Organization/updatePostionStatus")
    fun changeJobStatusService(@Field("id") jid:String,@Field("status") status:Int):Observable<HttpResult<JobInfo>>
}