package com.webdoc.payments.Essentials

import android.R
import android.app.Activity
import android.util.Base64
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.google.android.material.snackbar.Snackbar
import com.webdoc.ibcc.Payment.PaymentMethods.JSBankWallet.ResponseModel.JsBankAuthApi
import com.webdoc.ibcc.Payment.PaymentMethods.JSBankWallet.ResponseModel.jsDebitInquiryResult.JsDebitInquiryResult
import com.webdoc.payments.EasyPaisaOtc.ResponseModel.OtcPaymentResponse
import com.webdoc.payments.EasyPaisaWallet.ResponseModel.EasypaisaPAymentResponse
import com.webdoc.payments.JazzCash.ResponseModels.JazzCashResponseNew
import com.webdoc.payments.JsBankWallet.ResponseModels.JsDebitPaymentResponse.JsDebitPaymentResponse
import com.webdoc.payments.RequestModels.PaymentModel
import java.security.KeyFactory
import java.security.spec.X509EncodedKeySpec
import java.util.*
import java.util.regex.Pattern
import javax.crypto.Cipher


object Global {

    var eqDateTime: String? = null
    var order_id: String? = null
    var newToken: String? = null
    var EasyPaisaCreditDebitOrderId: String? = null
    val price: Any = 10
    var JS_Wallet_Account_Number: kotlin.String? = null
    var paymentTitle = ArrayList<PaymentModel>()
    var applicationContext: Activity? = null
    var jazzCashResponseNew: JazzCashResponseNew = JazzCashResponseNew()
    var easypaisaPAymentResponse: EasypaisaPAymentResponse = EasypaisaPAymentResponse()
    var authApiResp = JsBankAuthApi()
    var paymentInquiry = JsDebitInquiryResult()
    var jsPaymentFinal: JsDebitPaymentResponse = JsDebitPaymentResponse()
    var otcPaymentResponse: OtcPaymentResponse = OtcPaymentResponse()


    fun hideKeyboard(activity: Activity) {
        val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view = activity.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    //todo:email validation check
    fun isEmailValid(email: String?): Boolean {
        val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
        val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(email)
        return matcher.matches()
    }

    fun enccriptData(strToEncrypt: String): String? {
        var encoded: String? = ""
        var encrypted: ByteArray? = null
        try {
            val publicBytes = Base64.decode(
                "\"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAiO1lWgkTZeDWQgXlDF8t92YLYZm/ENvCvKPJNuj9WZfGCF5RIUFaYolb/HAhoAHKxgYRUS81WFfHuMROT+B/d0cW+Ii/sqLzTfFjepExonCj1I8m4WLdBAdZCRlWLo+bdO39OpxfK14XaPmRMdb8+uTpZ0hZBhDzZDnXChCm4fgsn63ZT2VEHdHX8PgmKTViR4VXsvyZCkT60FiEix2JdLCuSGF+tPr9GQnlSDJK4vRCZl+/TD/IaIbeAFWcx0Y6kdLpUBBUHbxY8cXcsr/HfJ6/WMEBSlUCOvbZhrx41yC/182WMPppaqCDeDamDV2T+ufzrQkT1nU40gm9h7uoXwIDAQAB\"",
                Base64.DEFAULT
            )
            val keySpec = X509EncodedKeySpec(publicBytes)
            val keyFactory = KeyFactory.getInstance("RSA")
            val pubKey = keyFactory.generatePublic(keySpec)
            val cipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING") //or try with "RSA"
            cipher.init(Cipher.ENCRYPT_MODE, pubKey)
            encrypted = cipher.doFinal(strToEncrypt.toByteArray())
            encoded = Base64.encodeToString(encrypted, Base64.DEFAULT)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return encoded
    }

    fun showErrorSnakeBar(activity: Activity, msg: String?) {
        val snackbar = Snackbar.make(
            activity.findViewById(R.id.content),
            msg!!, Snackbar.LENGTH_LONG
        )
            .setAction("Action", null)
            .setActionTextColor(activity.resources.getColor(R.color.white))
        val sbView = snackbar.view
        sbView.setBackgroundColor(activity.resources.getColor(R.color.holo_red_dark))
        snackbar.show()
    }


}
