package com.webdoc.payments.Retrofit;

import com.webdoc.ibcc.Payment.PaymentMethods.EasyPaisa.RequestModel.EasyPaisaRequestModel;
import com.webdoc.ibcc.Payment.PaymentMethods.JSBankWallet.RequestModel.JsReqModel;
import com.webdoc.ibcc.Payment.PaymentMethods.JSBankWallet.ResponseModel.JsBankAuthApi;
import com.webdoc.ibcc.Payment.PaymentMethods.JSBankWallet.ResponseModel.jsDebitInquiryResult.JsDebitInquiryResult;
import com.webdoc.payments.EasyPaisaOtc.RequestModel.OtcRequestModel;
import com.webdoc.payments.EasyPaisaOtc.ResponseModel.OtcPaymentResponse;
import com.webdoc.payments.EasyPaisaWallet.ResponseModel.EasypaisaPAymentResponse;
import com.webdoc.payments.JazzCash.ResponseModels.JazzCashResponseNew;
import com.webdoc.payments.JazzCash.ResponseModels.jazzCashRequestModel;
import com.webdoc.payments.JsBankWallet.RequestModels.JsThirdAPiReqModel;
import com.webdoc.payments.JsBankWallet.ResponseModels.JsDebitPaymentResponse.JsDebitPaymentResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface jsonPlaceHolderApi {

    //todo: JazzCash Payment
    @POST("DoMWalletTransaction")
    Call<JazzCashResponseNew> createJazzCashPayment(@Body jazzCashRequestModel jazzCashResponseNew);

    //todo : EasyPaisa Payment
    @Headers({
            "Credentials:SUJDQzo0ZjlhMmMyN2Q5MDMxZDBlNjRiOTlmOGE1NjZkMTRiZA=="
    })
    @POST("initiate-ma-transaction")
    Call<EasypaisaPAymentResponse> createpayment(@Body EasyPaisaRequestModel pAymentResponse);

    //TODO : jS BANK WALLET
    @Headers({
            "Authorization:Basic dHlIWHBqNGFRSHNYVGkwczBFTlNBc0ZsSTVyU2c3Mnk6UEhVa2dmM0h3MWk0R3NhTg==",
            "Cookie:__cfduid=db120ff1902d91d7c034d3f8f1f91a9f11613366925"
    })
    @GET("esb/oauth-t24?grant_type=client_credentials")
    Call<JsBankAuthApi> jsAuthApi();


    @POST("v2/debitinquiry2-blb2")
    Call<JsDebitInquiryResult> addToPlaylist(@Header("Authorization") String auth, @Header("Content-Type") String content_type, @Body JsReqModel jsReqModel);


    @POST("debitpayment-blb2")
    Call<JsDebitPaymentResponse> JsPaymentFinal(@Header("Authorization") String auth, @Header("Content-Type") String content_type, @Body JsThirdAPiReqModel jsPaymentFinal);

    //todo:// easypaisa OTC payment
    @Headers({
            "Credentials:SUJDQzo0ZjlhMmMyN2Q5MDMxZDBlNjRiOTlmOGE1NjZkMTRiZA=="
    })
    @POST("initiate-otc-transaction")
    Call<OtcPaymentResponse> createOTCpayment(@Body OtcRequestModel otcRequestModel);
}




