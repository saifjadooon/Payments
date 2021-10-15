package com.webdoc.payments.EasyPaisaOtc.ViewModel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.webdoc.payments.EasyPaisaOtc.RequestModel.OtcRequestModel
import com.webdoc.payments.EasyPaisaOtc.ResponseModel.OtcPaymentResponse
import com.webdoc.payments.Essentials.Global
import com.webdoc.payments.Retrofit.jsonPlaceHolderApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class OtcViewModel  //todo:Constructor
    (application: Application) : AndroidViewModel(application) {
    //todo:Mutable Live Data

    private val MLD_otcPAymentApiCalling: MutableLiveData<OtcPaymentResponse?> =
        MutableLiveData<OtcPaymentResponse?>()

    //todo:Livedata

    fun LD_btn_next_click(): LiveData<OtcPaymentResponse?> {
        return MLD_otcPAymentApiCalling
    }

    //todo:methods
    fun otcPAymentApiCalling(mobilenumber: String, email: String) {
        val sdf2 = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val currentDateandTimeNew = sdf2.format(Date())
        Global.eqDateTime = currentDateandTimeNew

        //todo: adding 1 day in current code
        val sdf1 = SimpleDateFormat("yyyyMMdd HHmmss", Locale.getDefault())
        val currentDateandTime = sdf1.format(Date())
        //Global.eqDateTime = currentDateandTime;
        val format = SimpleDateFormat("yyyyMMdd HHmmss")
        var myDate: Date? = null
        try {
            myDate = format.parse(currentDateandTime)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        myDate = addDays(myDate, 2)
        val newDate = sdf1.format(myDate) //todo:new date with one day + <<<<
        val orderId = randomNumberString
        Global.order_id = orderId
        val storeId = "86961"
        val transactionAmount: String = Global.price.toString()
        val transactionType = "OTC"
        OTC_easyPaisaRetrofit2Api(
            orderId, storeId, transactionAmount, transactionType,
            mobilenumber, email, newDate
        )
    }

    private fun OTC_easyPaisaRetrofit2Api(
        orderId: String,
        storeId: String,
        transactionAmount: String,
        transactionType: String,
        mobileAccountNo: String,
        emailAddress: String,
        tokenExpiry: String
    ) {
        val requestModel = OtcRequestModel()
        requestModel.orderId = orderId
        requestModel.storeId = storeId
        requestModel.transactionAmount = transactionAmount
        requestModel.msisdn = mobileAccountNo
        requestModel.emailAddress = emailAddress
        requestModel.transactionType = transactionType
        requestModel.tokenExpiry = tokenExpiry
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
        val call1: Call<OtcPaymentResponse> = jsonPlaceHolderApi.createOTCpayment(requestModel)
        call1.enqueue(object : Callback<OtcPaymentResponse> {
            override fun onResponse(
                call: Call<OtcPaymentResponse>,
                response: Response<OtcPaymentResponse>
            ) {
                val pAymentResponse: OtcPaymentResponse? = response.body()
                Global.otcPaymentResponse = pAymentResponse!!
                if (!response.isSuccessful()) {
                    Toast.makeText(
                        Global.applicationContext,
                        response.body().toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                }
                MLD_otcPAymentApiCalling.postValue(pAymentResponse)
            }

            override fun onFailure(call: Call<OtcPaymentResponse>, t: Throwable) {
                Toast.makeText(Global.applicationContext, "onFailure called ", Toast.LENGTH_SHORT)
                    .show()
                call.cancel()
            }
        })
    }

    companion object {
        var sixDigitNumber = 0
        fun addDays(date: Date?, days: Int): Date {
            val cal = Calendar.getInstance()
            cal.time = date
            cal.add(Calendar.DATE, days) //minus number would decrement the days
            return cal.time
        }

        // It will generate 6 digit random Number.
        // from 0 to 999999
        val randomNumberString: String
            // this will convert any number sequence into 6 character.
            get() {
                // It will generate 6 digit random Number.
                // from 0 to 999999
                val rnd = Random()
                sixDigitNumber = rnd.nextInt(999999)
                // this will convert any number sequence into 6 character.
                return String.format("%06d", sixDigitNumber)
            }
    }
}
