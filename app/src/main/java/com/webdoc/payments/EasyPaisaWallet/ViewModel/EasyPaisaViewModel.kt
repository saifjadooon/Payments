package com.webdoc.payments.EasyPaisaWallet.ViewModel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.webdoc.ibcc.Payment.PaymentMethods.EasyPaisa.RequestModel.EasyPaisaRequestModel
import com.webdoc.payments.EasyPaisaWallet.ResponseModel.EasypaisaPAymentResponse
import com.webdoc.payments.Essentials.Global
import com.webdoc.payments.Retrofit.jsonPlaceHolderApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

var sixDigitNumber = 0

class EasyPaisaViewModel  //todo constructor
    (application: Application) : AndroidViewModel(application) {
    //todo: Mutable Live Data
    private val MLD_btn_nextClick: MutableLiveData<EasypaisaPAymentResponse?> =
        MutableLiveData<EasypaisaPAymentResponse?>()

    //todo: Live Data
    fun LD_btn_next_click(): LiveData<EasypaisaPAymentResponse?> {
        return MLD_btn_nextClick
    }

    //todo: Methods
    fun btnNextClick(mobilenumber: String, email: String) {
        val orderId: String = getRandomNumberString().toString()
        Global.order_id = getCurrentlyDateTime();
        val storeId = "86961"
        val transactionAmount: String = java.lang.String.valueOf(Global.price)
        val transactionType = "MA"
        easyPaisaRetrofit2Api(
            orderId, storeId, transactionAmount, transactionType,
            mobilenumber, email
        )
    }

    fun getRandomNumberString(): String? {
        // It will generate 6 digit random Number.
        // from 0 to 999999
        val rnd = Random()
        sixDigitNumber =
            rnd.nextInt(999999)
        // this will convert any number sequence into 6 character.
        return String.format(
            "%06d",
            sixDigitNumber
        )
    }

    private fun easyPaisaRetrofit2Api(
        orderId: String,
        storeId: String,
        transactionAmount: String,
        transactionType: String,
        mobileAccountNo: String,
        emailAddress: String
    ) {
        val requestModel = EasyPaisaRequestModel()
        requestModel.orderId = orderId
        requestModel.storeId = storeId
        requestModel.transactionAmount = transactionAmount
        requestModel.mobileAccountNo = mobileAccountNo
        requestModel.emailAddress = emailAddress
        requestModel.transactionType = transactionType
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        var client: OkHttpClient? = OkHttpClient()
        client = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.MINUTES)
            .addInterceptor(interceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://easypay.easypaisa.com.pk/easypay-service/rest/v4/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client) // Set HttpClient to be used by Retrofit
            .build()
        val jsonPlaceHolderApi: jsonPlaceHolderApi = retrofit.create(jsonPlaceHolderApi::class.java)
        val call1: Call<EasypaisaPAymentResponse> = jsonPlaceHolderApi.createpayment(requestModel)
        call1.enqueue(object : Callback<EasypaisaPAymentResponse> {
            override fun onResponse(
                call: Call<EasypaisaPAymentResponse>,
                response: Response<EasypaisaPAymentResponse>
            ) {
                val pAymentResponse: EasypaisaPAymentResponse? = response.body()
                Global.easypaisaPAymentResponse = pAymentResponse!!
                if (!response.isSuccessful()) {
                    Toast.makeText(
                        Global.applicationContext,
                        response.body().toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                }
                MLD_btn_nextClick.postValue(pAymentResponse)
            }

            override fun onFailure(call: Call<EasypaisaPAymentResponse>, t: Throwable) {
                Toast.makeText(Global.applicationContext, "onFailure called ", Toast.LENGTH_SHORT)
                    .show()
                call.cancel()
            }
        })
    }


    fun getCurrentlyDateTime(): String? {
        val dateFormat = SimpleDateFormat("yyyyMMddHHmmss")
        return dateFormat.format(Date())
    }

}

