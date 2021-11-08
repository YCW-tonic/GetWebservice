package com.tonic.getwebservice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.tonic.firebaseauth.api.ApiFunc
import com.tonic.getwebservice.Send.HttpXMLGetPara
import com.tonic.getwebservice.databinding.ActivityJsonBinding
import okhttp3.*
import java.io.IOException


class JsonActivity : AppCompatActivity() {
    private val mTAG = JsonActivity::class.java.name
    private lateinit var binding: ActivityJsonBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_json)

        binding = ActivityJsonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnQuerry.setOnClickListener {
            val dateInput = binding.editTextDate.text.toString()
            val countInput = binding.editTextCount.text.toString().toInt()
            callAPIXML(dateInput, countInput)
        }
    }

    fun callAPIXML(date: String, count: Int){
        var para = HttpXMLGetPara()
        para.x = date
        para.y = count
        ApiFunc().getXML(para, XMLCallback)
    }
    private var XMLCallback: Callback = object : Callback{
        override fun onFailure(call: Call, e: IOException) {
            Log.e(mTAG,"err msg = $e")
        }

        @Throws(IOException::class)
        override fun onResponse(call: Call, response: Response) {
            Log.e(mTAG, "response = ${response.body!!.string()}")
        }
    }
}
















