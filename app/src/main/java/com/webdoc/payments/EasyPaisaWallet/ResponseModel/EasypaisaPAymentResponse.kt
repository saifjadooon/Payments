package com.webdoc.payments.EasyPaisaWallet.ResponseModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class EasypaisaPAymentResponse {
    @SerializedName("orderId")
    @Expose
    var orderId: String? = null

    @SerializedName("storeId")
    @Expose
    var storeId: String? = null

    @SerializedName("transactionId")
    @Expose
    var transactionId: String? = null

    @SerializedName("transactionDateTime")
    @Expose
    var transactionDateTime: String? = null

    @SerializedName("responseCode")
    @Expose
    var responseCode: String? = null

    @SerializedName("responseDesc")
    @Expose
    var responseDesc: String? = null

    constructor(
        orderId: String?,
        storeId: String?,
        transactionAmount: String?,
        transactionType: String?,
        mobileAccountNo: String?,
        emailAddress: String?
    ) {
    }

    constructor() {}
}