package com.tonic.firebaseauth.api

import android.util.Log
import com.google.gson.Gson
import com.tonic.getwebservice.Send.HttpXMLGetPara
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.util.concurrent.TimeUnit

class ApiFunc {
    private val mTAG = ApiFunc::class.java.name//紀錄class name(log呈現可使用)

    private val baseIP = "http://10.192.183.126/webservice/WebService1.asmx/"//webservice位址

    private val apiStrGetXMLCount = baseIP + "OdbcGetValueTest2"

    private object ContentType {

        const val title = "Content-Type"
        const val xxxForm = "application/x-www-form-urlencoded"

    }//ContentType

    fun getXML(para: HttpXMLGetPara, callback: Callback) {
        Log.e(mTAG, "ApiFunc->getReceipt")
        //postWithParaStrandTimeOut(apiStrGetAssemblyCount, para.x,para.y, callback)
        postWithParaStrandTimeOut(apiStrGetXMLCount, para.x, para.y, callback)

    }
    //private fun postWithParaStrandTimeOut(url: String, jsonStr: String, callback: Callback) {
    private fun postWithParaStrandTimeOut(url: String, x: String, y: Int, callback: Callback) {
        Log.e(mTAG, "->postWithParaPJsonStrandTimeOut")


        val body = FormBody.Builder()
            .add("x", x)
            .add("y", y.toString())
            //.add("p_json", jsonStr)
            .build()

        val request = Request.Builder()
            .url(url)
            .post(body)
            .addHeader(ContentType.title, ContentType.xxxForm)
            .build()



        val client = OkHttpClient().newBuilder()
            //.connectTimeout(5000, TimeUnit.MILLISECONDS) //5 secs
            //.readTimeout(5000, TimeUnit.MILLISECONDS) //5 secs
            //.writeTimeout(5000, TimeUnit.MILLISECONDS) //5 secs
            .connectTimeout(10, TimeUnit.SECONDS) //5 secs
            .readTimeout(20, TimeUnit.SECONDS) //5 secs
            .writeTimeout(20, TimeUnit.SECONDS) //5 secs
            .retryOnConnectionFailure(false)
            .build()


        try {
            val response = client.newCall(request).enqueue(callback)



            Log.d("pPara_pjson_timeout", "response = $response")

            client.dispatcher.executorService.shutdown()
            client.connectionPool.evictAll()
            client.cache?.close()

        } catch (e: IOException) {
            e.printStackTrace()
        }


    }
}