package com.webdoc.payments.JazzCash.ViewModel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.webdoc.payments.Essentials.Global
import com.webdoc.payments.JazzCash.ResponseModels.JazzCashResponseNew
import com.webdoc.payments.JazzCash.ResponseModels.jazzCashRequestModel
import com.webdoc.payments.Retrofit.jsonPlaceHolderApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec


class JazzCashViewModel  //todo : constructor
    (application: Application) : AndroidViewModel(application) {
    var messageDigest: String? = null

    //todo:Mutable Live Data
    private val MLD_callingJazzCashApi = MutableLiveData<JazzCashResponseNew?>()

    //todo: Live Data
    fun LD_callingJazzCash(): LiveData<JazzCashResponseNew?> {
        return MLD_callingJazzCashApi
    }

    fun CallingJazzCashApi(mobileNumber: String, cnic: String) {
        val amount = java.lang.String.valueOf(Global.price)
        val tempref = "T$currentlyDateTime"
        val SecurityKey = "s52suwc12f"
        val pp_Amount = amount + "00"
        val pp_BankID = ""
        val pp_BillReference = "billRef"
        val pp_Description = "this is transaction"
        val pp_Language = "EN"
        val pp_MerchantID = "MC18403"
        val pp_Password = "4tx1430us8"
        val pp_ProductID = ""
        val pp_SubMerchantID = ""
        val pp_TxnCurrency = "PKR"
        val pp_TxnDateTime = currentlyDateTime
        val pp_TxnExpiryDateTime = currentlyDateTimeNextday
        val ppmpf_1 = ""
        val ppmpf_2 = ""
        val ppmpf_3 = ""
        val ppmpf_4 = ""
        val ppmpf_5 = ""
        val pp_DiscountedAmount = ""
        val Key =
            (SecurityKey + "&" + pp_Amount + "&" + pp_BillReference + "&" + cnic + "&" + pp_Description
                    + "&" + pp_Language + "&" + pp_MerchantID + "&" + mobileNumber + "&" + pp_Password
                    + "&" + pp_TxnCurrency + "&" + pp_TxnDateTime + "&" + pp_TxnExpiryDateTime + "&" + tempref)
        try {
            generateHashWithHmac256(Key, SecurityKey)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        val pp_SecureHash = messageDigest
        jazzCashRetrofit2Api(
            pp_Language, pp_MerchantID, pp_SubMerchantID, pp_Password, pp_DiscountedAmount,
            pp_BankID, pp_ProductID,
            tempref, pp_Amount, pp_TxnCurrency, pp_TxnDateTime, pp_BillReference, pp_Description,
            pp_TxnExpiryDateTime, pp_SecureHash, ppmpf_1, ppmpf_2, ppmpf_3, ppmpf_4, ppmpf_5,
            mobileNumber,
            cnic
        )
    }


    private fun jazzCashRetrofit2Api(
        pp_language: String,
        pp_merchantID: String,
        pp_subMerchantID: String,
        pp_password: String,
        pp_discountedAmount: String,
        pp_bankID: String,
        pp_productID: String,
        pp_txnRefNo: String,
        pp_amount: String,
        pp_txnCurrency: String,
        pp_txnDateTime: String,
        pp_billReference: String,
        pp_description: String,
        pp_txnExpiryDateTime: String,
        pp_secureHash: String?,
        ppmpf_1: String,
        ppmpf_2: String,
        ppmpf_3: String,
        ppmpf_4: String,
        ppmpf_5: String,
        pp_mobileNumber: String,
        pp_cnic: String
    ) {
        val requestModel = jazzCashRequestModel()
        requestModel.pp_Amount = pp_amount
        requestModel.pp_Language = pp_language
        requestModel.pp_MerchantID = pp_merchantID
        requestModel.pp_SubMerchantID = pp_subMerchantID
        requestModel.pp_Password = pp_password
        requestModel.pp_DiscountedAmount = pp_discountedAmount
        requestModel.pp_BankID = pp_bankID
        requestModel.pp_ProductID = pp_productID
        requestModel.pp_TxnRefNo = pp_txnRefNo
        requestModel.pp_Amount = pp_amount
        requestModel.pp_TxnCurrency = pp_txnCurrency
        requestModel.pp_TxnDateTime = pp_txnDateTime
        requestModel.pp_BillReference = pp_billReference
        requestModel.pp_Description = pp_description
        requestModel.pp_TxnExpiryDateTime = pp_txnExpiryDateTime
        requestModel.pp_SecureHash = pp_secureHash
        requestModel.ppmpf_1 = ppmpf_1
        requestModel.ppmpf_2 = ppmpf_2
        requestModel.ppmpf_3 = ppmpf_3
        requestModel.ppmpf_4 = ppmpf_4
        requestModel.ppmpf_5 = ppmpf_5
        requestModel.pp_MobileNumber = pp_mobileNumber
        requestModel.pp_CNIC = pp_cnic


        // final JazzCashResponseNew jazzCashResponse = new JazzCashResponseNew(pp_language, pp_merchantID, pp_subMerchantID, pp_password, pp_discountedAmount, pp_bankID, pp_productID, pp_txnRefNo, pp_amount, pp_txnCurrency, pp_txnDateTime, pp_billReference, pp_description, pp_txnExpiryDateTime, pp_secureHash, ppmpf_1, ppmpf_2, ppmpf_3, ppmpf_4, ppmpf_5, pp_mobileNumber, pp_cnic);
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
            .baseUrl("https://sandbox.jazzcash.com.pk/ApplicationAPI/API/2.0/Purchase/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client) // Set HttpClient to be used by Retrofit
            .build()
        val jsonPlaceHolderApi = retrofit.create(jsonPlaceHolderApi::class.java)
        val call1 = jsonPlaceHolderApi.createJazzCashPayment(requestModel)
        call1.enqueue(object : Callback<JazzCashResponseNew> {
            override fun onResponse(
                call: Call<JazzCashResponseNew>,
                response: Response<JazzCashResponseNew>
            ) {
                val pAymentResponse = response.body()
                Toast.makeText(
                    Global.applicationContext,
                    response.body().toString(),
                    Toast.LENGTH_SHORT
                )
                Global.jazzCashResponseNew = pAymentResponse!!
                if (!response.isSuccessful) {
                    Toast.makeText(
                        Global.applicationContext,
                        response.body().toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                }
                MLD_callingJazzCashApi.postValue(pAymentResponse)
            }

            override fun onFailure(call: Call<JazzCashResponseNew>, t: Throwable) {
                Toast.makeText(Global.applicationContext, "onFailure called ", Toast.LENGTH_SHORT)
                    .show()
                Log.i("kh", t.message!!)
                call.cancel()
            }
        })
    }

    val currentlyDateTime: String
        get() {
            val dateFormat = SimpleDateFormat("yyyyMMddHHmmss")
            return dateFormat.format(Date())
        }
    val currentlyDateTimeNextday: String
        get() {
            val dateFormat = SimpleDateFormat("yyyyMMddHHmmss")
            val dt = currentlyDateTime
            val c = Calendar.getInstance()
            try {
                c.time = dateFormat.parse(dt)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            c.add(Calendar.DATE, 1)
            return dateFormat.format(c.time)
        }

    private fun generateHashWithHmac256(message: String, key: String) {
        try {
            val hashingAlgorithm = "HmacSHA256" //or "HmacSHA1", "HmacSHA512"
            val bytes = hmac(hashingAlgorithm, key.toByteArray(), message.toByteArray())
            messageDigest = bytesToHex(bytes)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    companion object {
        //todo:Methods
        fun bytesToHex(bytes: ByteArray): String {
            val hexArray = "0123456789abcdef".toCharArray()
            val hexChars = CharArray(bytes.size * 2)
            var j = 0
            var v: Int
            while (j < bytes.size) {
                v = (bytes[j].toInt() and 0xFF)
                hexChars[j * 2] = hexArray[v ushr 4]
                hexChars[j * 2 + 1] = hexArray[v and 0x0F]
                j++
            }
            return String(hexChars)
        }

        @Throws(NoSuchAlgorithmException::class, InvalidKeyException::class)
        fun hmac(algorithm: String?, key: ByteArray?, message: ByteArray?): ByteArray {
            val mac = Mac.getInstance(algorithm)
            mac.init(SecretKeySpec(key, algorithm))
            return mac.doFinal(message)
        }
    }
}

