package com.webdoc.payments

import android.os.Bundle
import android.widget.Adapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.webdoc.payments.Adapters.PaymentAdapter
import com.webdoc.payments.Essentials.Global
import com.webdoc.payments.RequestModels.PaymentModel
import com.webdoc.payments.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    var binding: ActivityMainBinding? = null
    var paymentMethodsAdapter: Adapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        clickListeneres()
    }

    private fun clickListeneres() {

    }

    private fun initViews() {
        //todo:binding xml fine with java file
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        //RECYCLER VIEW
        val layoutManager =
            LinearLayoutManager(MainActivity@ this, LinearLayoutManager.VERTICAL, false)
        binding!!.rvPaymentMethods.setLayoutManager(layoutManager)
        binding!!.rvPaymentMethods.setHasFixedSize(true)
        paymentMethodsAdapter = PaymentAdapter(MainActivity@ this)
        binding!!.rvPaymentMethods.setAdapter(paymentMethodsAdapter as PaymentAdapter)

        val logo =
            intArrayOf(
                R.drawable.bank_alfalah_logo,
                R.drawable.bank_alfalah_logo,
                R.drawable.jazzcash,
                R.drawable.easypaisa_icon,
                R.drawable.js_bank_logo,
                R.drawable.credit,
                R.drawable.easypaisa_icon,
                R.drawable.easypaisa_icon
            )
        val title = arrayOf(
            "Bank Alfalah Account",
            "Bank Alfalah Wallet",
            "Jazz Cash Wallet",
            "Easy Paisa Wallet",
            "Js Wallet",
            "Credit/Debit Card",
            "OTC Through EasyPaisa",
            "Easy Paisa Credit Debit"
        )

        for (i in logo.indices) {
            val model = PaymentModel(logo[i], title[i])
            Global.paymentTitle.add(model)
        }


    }
}