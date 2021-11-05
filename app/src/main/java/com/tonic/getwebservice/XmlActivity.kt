package com.tonic.getwebservice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import com.tonic.getwebservice.databinding.ActivityXmlBinding
import java.io.IOException
import java.util.concurrent.TimeUnit


class XmlActivity : AppCompatActivity() {
    private lateinit var binding: ActivityXmlBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_xml)

        binding = ActivityXmlBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnQuerry.setOnClickListener {

        }
    }

    fun callAPIXML(date: String, count: Int){
        var para = HttpXMLGetPara()
        para.x = date
        para.y = count
    }
}
class HttpXMLGetPara {
    //ex ("2021/11/04","1")
    var x = ""//日期
    var y = 0//第幾筆
}
class ApiFunc{
    private val baseIP = "http://10.192.183.126/webservice/WebService1.asmx/"
    private val apiStrGetXMLData = baseIP + "OdbcGetValueTestJson"
    private object ContentType {

        const val title = "Content-Type"
        const val xxxForm = "application/x-www-form-urlencoded"

    }
    fun getXML(para: HttpXMLGetPara,callback: Callback){
        postWithParaStrandTimeOut(apiStrGetXMLData,para.)
    }
    private fun postWithParaStrandTimeOut(url: String, x:String, y:Int, callback:Callback){
        val body = FormBody.Builder()
            .add("date", x)
            .add("count",y.toString())
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

        try{
            val response = client.newCall(request).enqueue(callback)
            client.dispatcher.executorService.shutdown()
            client.connectionPool.evictAll()
            client.cache?.close()
        }catch (e: IOException) {
            e.printStackTrace()
        }
    }
}














