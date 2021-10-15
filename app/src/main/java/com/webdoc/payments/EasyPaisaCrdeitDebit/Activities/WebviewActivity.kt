package com.webdoc.payments.EasyPaisaCrdeitDebit.Activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.webdoc.payments.EasyPaisaCrdeitDebit.ViewModel.EpWebviewViewModel
import com.webdoc.payments.Essentials.Global
import com.webdoc.payments.R
import com.webdoc.payments.databinding.ActivityWebviewBinding
import java.io.UnsupportedEncodingException
import java.net.URLEncoder
import java.text.SimpleDateFormat
import java.util.*

class WebviewActivity : AppCompatActivity() {
    var binding: ActivityWebviewBinding? = null
    var viewModel: EpWebviewViewModel? = null
    var intentt: Intent? = null
    var url: String? = null
    var phoneNo: kotlin.String? = null
    var email: kotlin.String? = null
    var finalUrl: kotlin.String? = null
    var orderId: kotlin.String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)

        initViews()
        observers()
    }

    private fun observers() {
        viewModel!!.LD_open_webview().observe(this, { s ->


            //userId,Courier amount ,bank charges,webdoc amount ,
            if (s is String) {
                if (s.equals("Payment Succesfull")) {

                } else if (s.equals("Payment Fail")) {
                    Global.showErrorSnakeBar(this@WebviewActivity, "PAYMENT FAIL!")
                    binding!!.webView.removeAllViews()
                    binding!!.webView.destroy()
                    finish()
                    // Global.utils.hideCustomLoadingDialog()
                }
            }
        })
    }

    fun initViews() {
        //todo:binding xml fine with java file
        binding = ActivityWebviewBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        //todo:connecting View Model with activityyu
        viewModel = ViewModelProviders.of(this@WebviewActivity)[EpWebviewViewModel::class.java]
        Global.applicationContext = this@WebviewActivity
        intentt = getIntent()
        phoneNo = intentt!!.getStringExtra("mobileNumber")
        email = intentt!!.getStringExtra("email")
        orderId = getCurrentlyDateTime()
        Global.order_id = orderId
        Global.EasyPaisaCreditDebitOrderId = orderId
        val amount: String = java.lang.String.valueOf(Global.price)
        var EmailEncoded: String? = null
        try {
            EmailEncoded = URLEncoder.encode(email, "utf-8")
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }
        finalUrl =
            "https://portal.webdoc.com.pk/transection/Easypaisa/cc.php?email=$EmailEncoded&cellNumber=$phoneNo&amount=$amount&orderId=$orderId"
        viewModel!!.openEasyPaisacreditDebitWebView(binding!!.webView, finalUrl)
    }

    fun getCurrentlyDateTime(): String {
        val dateFormat = SimpleDateFormat("yyyyMMddHHmmss")
        return dateFormat.format(Date())
    }

    fun getCurrentlyDateTimenew(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        return dateFormat.format(Date())
    }

}