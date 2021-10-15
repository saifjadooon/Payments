package com.webdoc.payments.JazzCash.ResponseModels

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class JazzCashResponseNew {
    @SerializedName("pp_Amount")
    @Expose
    var ppAmount: String? = null

    @SerializedName("pp_AuthCode")
    @Expose
    var ppAuthCode: String? = null

    @SerializedName("pp_BillReference")
    @Expose
    var ppBillReference: String? = null

    @SerializedName("pp_Language")
    @Expose
    var ppLanguage: String? = null

    @SerializedName("pp_MerchantID")
    @Expose
    var ppMerchantID: String? = null

    @SerializedName("pp_ResponseCode")
    @Expose
    var ppResponseCode: String? = null

    @SerializedName("pp_ResponseMessage")
    @Expose
    var ppResponseMessage: String? = null

    @SerializedName("pp_RetreivalReferenceNo")
    @Expose
    var ppRetreivalReferenceNo: String? = null

    @SerializedName("pp_SubMerchantID")
    @Expose
    var ppSubMerchantID: String? = null

    @SerializedName("pp_TxnCurrency")
    @Expose
    var ppTxnCurrency: String? = null

    @SerializedName("pp_TxnDateTime")
    @Expose
    var ppTxnDateTime: String? = null

    @SerializedName("pp_TxnRefNo")
    @Expose
    var ppTxnRefNo: String? = null

    @SerializedName("pp_MobileNumber")
    @Expose
    var ppMobileNumber: String? = null

    @SerializedName("pp_CNIC")
    @Expose
    var ppCNIC: String? = null

    @SerializedName("pp_DiscountedAmount")
    @Expose
    var ppDiscountedAmount: String? = null

    @SerializedName("ppmpf_1")
    @Expose
    var ppmpf1: String? = null

    @SerializedName("ppmpf_2")
    @Expose
    var ppmpf2: String? = null

    @SerializedName("ppmpf_3")
    @Expose
    var ppmpf3: String? = null

    @SerializedName("ppmpf_4")
    @Expose
    var ppmpf4: String? = null

    @SerializedName("ppmpf_5")
    @Expose
    var ppmpf5: String? = null

    @SerializedName("pp_SecureHash")
    @Expose
    var ppSecureHash: String? = null
}