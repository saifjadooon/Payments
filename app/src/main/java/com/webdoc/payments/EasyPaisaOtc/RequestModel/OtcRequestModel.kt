package com.webdoc.payments.EasyPaisaOtc.RequestModel


class OtcRequestModel {
    var orderId: String? = null
    var storeId: String? = null
    var transactionAmount: String? = null
    var transactionType: String? = null
    var msisdn: String? = null
    var emailAddress: String? = null
    var tokenExpiry: String? = null

    fun getmsisdn(): String? {
        return msisdn
    }

    fun setmsisdn(msisdn: String?) {
        this.msisdn = msisdn
    }
}
