package com.milai.lll_teacher

/**
 * Created by Administrator on 2017/7/6.
 *
 * 主要功能：
 */
object Constant {
    const val EXIT:Int = 1
    const val LOGOUT: Int = 2
    const val CHANGE_PSW: Int=3    //修改密码 requestCode
    const val LOGIN_SUCCESS: Int = 4    //登录成功BusMessage

    const val REQUEST_STORAGE_PERMISSION: Int = 100 //请求存储权限
    const val REQUEST_CAMERA_PERMISSION: Int = 101  //请求相机权限requestCode
    const val CHOOSE_PICTURE_FROM_ALBUN: Int = 110  //从相册选取requestCode
    const val TAKE_PHOTO: Int = 120     //从相机拍照requestCode

}