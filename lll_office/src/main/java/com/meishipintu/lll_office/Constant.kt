package com.meishipintu.lll_office

/**
 * Created by Administrator on 2017/7/6.
 *
 * 主要功能：
 */
object Constant {
    const val EXIT:Int = 1
    const val LOGOUT: Int = 2
    const val LOGIN_SUCCESS: Int=3     //登录成功BusMessage
    const val REGIST_SUCCESS: Int=3     //登录成功BusMessage
    const val START_NEW_JOB: Int = 100  //requestCode 发布新任务
    const val CHANGE_JOB_STATE: Int=101 //requestCode 任务上下线

    const val REQUEST_CAMERA_PERMISSION: Int = 101  //请求相机权限requestCode
    const val CHOOSE_PICTURE_FROM_ALBUN: Int = 110  //从相册选取requestCode
    const val TAKE_PHOTO: Int = 120     //从相机拍照requestCode
    const val CROP_SMALL_PICTURE: Int = 130 //调用裁剪
}