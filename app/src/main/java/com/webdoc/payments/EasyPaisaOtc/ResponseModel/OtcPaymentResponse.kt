package com.webdoc.payments.EasyPaisaOtc.ResponseModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class OtcPaymentResponse {
    @SerializedName("orderId")
    @Expose
    var orderId: String? = null

    @SerializedName("storeId")
    @Expose
    var storeId: String? = null

    @SerializedName("paymentToken")
    @Expose
    var paymentToken: String? = null

    @SerializedName("transactionId")
    @Expose
    var transactionId: String? = null

    @SerializedName("transactionDateTime")
    @Expose
    var transactionDateTime: String? = null

    @SerializedName("paymentTokenExpiryDateTime")
    @Expose
    var paymentTokenExpiryDateTime: String? = null

    @SerializedName("responseCode")
    @Expose
    var responseCode: String? = null

    @SerializedName("responseDesc")
    @Expose
    var responseDesc: String? = null

    @SerializedName("shopifyRedirectURL")
    @Expose
    var shopifyRedirectURL: String? = null
}
