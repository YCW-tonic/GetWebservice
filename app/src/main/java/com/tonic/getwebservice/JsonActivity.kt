package com.tonic.getwebservice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import com.tonic.firebaseauth.api.ApiFunc
import com.tonic.getwebservice.Send.HttpJsonGetPara
import com.tonic.getwebservice.Send.HttpXMLGetPara
import com.tonic.getwebservice.databinding.ActivityJsonBinding
import okhttp3.*
import java.io.IOException
import org.json.JSONException
import org.json.JSONObject
import org.json.XML


class JsonActivity : AppCompatActivity() {
    private val mTAG = JsonActivity::class.java.name
    private lateinit var binding: ActivityJsonBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_json)

        binding = ActivityJsonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnQuerry.setOnClickListener {
//            val dateInput = binding.editTextDate.text.toString()
//            val countInput = binding.editTextCount.text.toString().toInt()
            val dateInput = "2021/11/10"
            val countInput = 2
            callAPIJson(dateInput, countInput)
        }
    }

    fun callAPIJson(date: String, count: Int){
        var para = HttpJsonGetPara()
        para.jsonData1 = date
        para.jsonData2 = count.toString()
        ApiFunc().getJson(para, JsonCallback)
    }
    private var JsonCallback: Callback = object : Callback{
        override fun onFailure(call: Call, e: IOException) {
            Log.e(mTAG,"err msg = $e")
        }

        @Throws(IOException::class)
        override fun onResponse(call: Call, response: Response) {
            //Log.e(mTAG, "response = ${response.body!!.string()}")
            //var result:String = response.body!!.string()
            //val res = restoreToJsonStr(response.body!!.string())
            runOnUiThread {
                Log.e(mTAG, "===>upLoadReceiptPointCallback : onResponse start")
                try {   // trans to list data
                    var jsonObj: JSONObject? = null
                    try {
                        val sampleXml = ("<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                                + "<mobilegate>"
                                + "<timestamp>232423423423</timestamp>"
                                + "<txn>" + "Transaction" + "</txn>"
                                + "<amt>" + 0 + "</amt>"
                                + "</mobilegate>")
                        jsonObj = XML.toJSONObject(sampleXml,true)
                    } catch (e: JSONException) {
                        Log.e("JSON exception", e.message!!)
                        e.printStackTrace()
                    }

                    Log.d("XML", response.body!!.string())

                    Log.d("JSON", jsonObj.toString())
                    //val jsonStr = ReceiveTransform.addToJsonArrayStr(response.body()!!.string())
                    //Log.e(mTAG, "res = $res")
//                    if (res != "Error" && !checkServerErrorString(res)) {
//
//                        //==== receive single record start ====
//                        val rjReceiptUpload: RJReceiptUpload =
//                            Gson().fromJson(res, RJReceiptUpload::class.java) as RJReceiptUpload
//
//
//                        val poLineInt = Integer.valueOf(rjReceiptUpload.pmn02)
//                        val po = rjReceiptUpload.pmn01
//                        val uploadResult = rjReceiptUpload.result
//                        val receiveNum = rjReceiptUpload.rva01// error mess when fail
//                        val receiveLine = rjReceiptUpload.rvb02
//                        //start find the raw data in list
//                        Log.e(mTAG, "po = $po")
//                        Log.e(mTAG, "uploadResult = $uploadResult")
//                        Log.e(mTAG, "receiveNum = $receiveNum")
//                        Log.e(mTAG, "receiveLine = $receiveLine")
//                        if (itemReceipt != null) {
//
//                            if (uploadResult == "1") { //success
//
//                                Log.e(
//                                    mTAG,
//                                    "itemReceipt!!.poNumSplit = ${itemReceipt!!.poNumSplit}, po = $po"
//                                )
//                                Log.e(
//                                    mTAG,
//                                    "poLineInt = $poLineInt, itemReceipt!!.poLineInt = ${itemReceipt!!.poLineInt}"
//                                )
//
//                                if (itemReceipt!!.poNumSplit == po && poLineInt == itemReceipt!!.poLineInt) {
//                                    itemReceipt!!.state = ItemReceipt.ItemState.UPLOADED
//                                    itemReceipt!!.receiveNum = receiveNum
//                                    itemReceipt!!.receiveLine = receiveLine
//                                    receiveNumUploadSuccess = receiveNum
//                                    //ReceiptList.replaceItem(item, j)
//
//                                    val successIntent = Intent()
//                                    successIntent.action =
//                                        Constants.ACTION.ACTION_RECEIPT_UPLOAD_SUCCESS
//                                    sendBroadcast(successIntent)
//
//                                } else { //po is different
//                                    itemReceipt!!.state = ItemReceipt.ItemState.UPLOADED
//                                    itemReceipt!!.receiveNum = receiveNum
//                                    itemReceipt!!.receiveLine = receiveLine
//                                    receiveNumUploadSuccess = receiveNum
//
//                                    val successIntent = Intent()
//                                    successIntent.action =
//                                        Constants.ACTION.ACTION_RECEIPT_UPLOAD_SUCCESS
//                                    sendBroadcast(successIntent)
//
//                                    /*val poDiffIntent = Intent()
//                                poDiffIntent.action = Constants.ACTION.ACTION_RECEIPT_UPLOAD_RETURN_PO_DIFF
//                                sendBroadcast(poDiffIntent)
//
//                                itemReceipt!!.state = ItemReceipt.ItemState.UPLOADED
//                                Log.e(mTAG, getString(R.string.upload_receipt_failed_po_not_match))
//                                toast(getString(R.string.upload_receipt_failed_po_not_match))*/
//                                }
//
//
//                            } else { // upload failed, uploadResult == 0
//                                itemReceipt!!.state = ItemReceipt.ItemState.UPLOAD_FAILED
//
//                                val errorString: String =
//                                    getString(R.string.receipt_upload_error) + " $receiveNum " + rjReceiptUpload.rva01
//                                toast(errorString)
//
//                                val failedIntent = Intent()
//                                failedIntent.action = Constants.ACTION.ACTION_RECEIPT_UPLOAD_FAILED
//                                failedIntent.putExtra("REASON", rjReceiptUpload.rva01)
//                                sendBroadcast(failedIntent)
//                            }// upload fail
//
//
//                        } else {
//                            Log.e(mTAG, "itemReceipt = null")
//                        }
//                    } else { //checkServerErrorString = true
//                        toast(getString(R.string.toast_server_error))
//
//                        val exceptionIntent = Intent()
//                        exceptionIntent.action = Constants.ACTION.ACTION_SERVER_ERROR
//                        sendBroadcast(exceptionIntent)
//                    }


                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.e(mTAG, "===>Exception ")
                    // itemReceiptFromScanDifferentHeader = null;
                    //String temp = ex.toString();

//                    itemReceipt!!.state = ItemReceipt.ItemState.UPLOAD_FAILED
//
//                    val exceptionIntent = Intent()
//                    exceptionIntent.action = Constants.ACTION.ACTION_SERVER_ERROR
//                    sendBroadcast(exceptionIntent)
//
//                    runOnUiThread {
//                        //mLoadingView.setStatus(LoadingView.GONE)
//                        //setUIToEditMode()
//                        toast(getString(R.string.toast_server_error))
//                    }
                }

            }
        }
    }

    fun restoreToJsonStr(str: String): String {
        /*var str = str

        //return  "{dataList:"+str + "}";
        str = str.substring(1, str.length - 1)
        return str*/
        //val jsonStr = str.substring(1, str.length - 1)
        //return str.substring(1, str.length - 1)

        return try {
            str.substring(1, str.length - 1)
        } catch (e: StringIndexOutOfBoundsException) {
            e.printStackTrace()
            "Error"
        }

    }
}
















