package com.milai.lll_teacher.custom.util

import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.support.v4.content.FileProvider
import android.util.Log
import android.widget.Toast
import com.milai.lll_teacher.R

import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

/**
 * Created by Administrator on 2016/8/29.
 */
class MyAsy(val context: Context, val versionCode: String) : AsyncTask<String, String, File>() {
    val mProgressDialog: ProgressDialog = ProgressDialog(context)

    //点击调用界面
    override fun onPreExecute() {
        mProgressDialog.setMessage("下载文件...")
        mProgressDialog.isIndeterminate = false
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
        mProgressDialog.setCancelable(false)
        mProgressDialog.show()
    }

    //点击调用后界面
    override fun onPostExecute(file: File?) {
        mProgressDialog.dismiss()
        if (file != null) {
            val builder = AlertDialog.Builder(context, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT).setTitle("下载完成")
                    .setMessage("新版本已经下载完成，是否安装？")
                    .setPositiveButton("确定", { dialog, _ ->
                        installApk(context, file)
                        dialog.dismiss()
                    }).setNegativeButton(R.string.cancel, null)
            builder.show()
        } else {
            Toast.makeText(context, "下载错误，请稍后重试", Toast.LENGTH_SHORT)
                    .show()
        }
    }

    override fun doInBackground(vararg params: String): File? {
        val fileName = "lll_t.apk"
        val tmpFile = File(context.getExternalFilesDir(android.os.Environment.DIRECTORY_DOWNLOADS), versionCode)
        if (!tmpFile.exists()) {
            tmpFile.mkdir()
        }
        val file = File(context.getExternalFilesDir(android.os.Environment.DIRECTORY_DOWNLOADS),"$versionCode/$fileName")
        val url: URL
        try {
            url = URL(params[0])
            val conn = url.openConnection() as HttpURLConnection
            conn.connect()
            if (conn.responseCode != HttpURLConnection.HTTP_OK) {
                Log.i("test", "网络错误，service return：" + conn.responseCode
                        + ";" + conn.responseMessage)
            } else {
                var inS = conn.inputStream
                var fos = FileOutputStream(file)
                try {
                    val buf = ByteArray(1024)
                    //计算文件长度
                    val lenghtOfFile = conn.contentLength
                    (context as Activity).runOnUiThread { mProgressDialog.max = lenghtOfFile }
                    val all = lenghtOfFile.toFloat() / 1024f / 1024.0f
                    var total = 0
                    var numRead = inS.read(buf)
                    while (numRead != -1) {
                        total += numRead
                        mProgressDialog.progress = total
                        val percent = total.toFloat() / 1024f / 1024.0f
                        mProgressDialog.setProgressNumberFormat(String.format("%.2fMb/%.2fMb", percent, all))
                        fos.write(buf, 0, numRead)
                        numRead = inS.read(buf)
                    }

                    return file

                } catch (e: IOException) {
                    e.printStackTrace()
                } finally {
                    fos.flush()
                    fos.close()
                    inS.close()
                }
            }
            conn.disconnect()
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return null
    }

    override fun onProgressUpdate(vararg progress: String) {

        mProgressDialog.progress = Integer.parseInt(progress[0])
    }

    /* 安装apk */
    fun installApk(context: Context, file: File) {
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        if (android.os.Build.VERSION.SDK_INT < 24) {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive")
        } else {
            intent.setDataAndType(FileProvider.getUriForFile(context, context.packageName, file), "application/vnd.android.package-archive")
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)  //添加这一句表示对目标应用临时授权该Uri所代表的文件
        }
        context.startActivity(intent)
    }

}
