package com.webdoc.payments.EasyPaisaOtc

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.webdoc.payments.EasyPaisaOtc.ResponseModel.OtcPaymentResponse
import com.webdoc.payments.EasyPaisaOtc.ViewModel.OtcViewModel
import com.webdoc.payments.Essentials.Global
import com.webdoc.payments.R
import com.webdoc.payments.databinding.ActivityOtcPaymentBinding

class OtcPaymentActivity : AppCompatActivity() {

    var binding: ActivityOtcPaymentBinding? = null
    var viewModel: OtcViewModel? = null
    var mobileNumber: String? = null
    var email: kotlin.String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otc_payment)

        initViews()
        clickListeneres()
        observers()
    }

    fun initViews() {
        //todo:binding xml fine with java file
        binding = ActivityOtcPaymentBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        //todo:connecting View Model with activity
        viewModel = ViewModelProviders.of(this)[OtcViewModel::class.java]

        Global.applicationContext = this@OtcPaymentActivity
        // binding.tvPkgPrice.setText(java.lang.String.valueOf(Global.price))
    }

    fun clickListeneres() {
        binding!!.etAccountNo.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (binding!!.etAccountNo.getText()!!.length === 11) {
                    Global.hideKeyboard(this@OtcPaymentActivity)
                    binding!!.ccEnterCnic.setVisibility(View.VISIBLE)
                } else {
                    binding!!.ccEnterCnic.setVisibility(View.GONE)
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })
        binding!!.etCnic.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (Global.isEmailValid(binding!!.etCnic.getText().toString())) {
                    Global.hideKeyboard(this@OtcPaymentActivity)
                    binding!!.btnNext.setVisibility(View.VISIBLE)
                } else {
                    binding!!.btnNext.setVisibility(View.GONE)
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })
        binding!!.btnNext.setOnClickListener(View.OnClickListener {
            //   Global.showCustomLoadingDialog(this@OtcPaymentActivity)
            mobileNumber = binding!!.etAccountNo.getText().toString()
            email = binding!!.etCnic.getText().toString()
            viewModel!!.otcPAymentApiCalling(mobileNumber!!, email!!)
        })
    }

    fun observers() {

        //todo: when next button is click befor easy paisa payment this observer will call====>>>>
        viewModel!!.LD_btn_next_click().observe(this, { s ->
            if (s is OtcPaymentResponse) {
                if (s.responseCode.equals("0000")) {
                    //case_id = Global.caseId;

                }
            } else if (s!!.responseCode.equals("0001")) {
                Global.showErrorSnakeBar(this@OtcPaymentActivity, s.responseDesc)
            } else if (s!!.responseCode.equals("0013")) {
                Global.showErrorSnakeBar(this@OtcPaymentActivity, s.responseDesc)
            } else if (s!!.responseCode.equals("0008")) {
                Global.showErrorSnakeBar(this@OtcPaymentActivity, s.responseDesc)
            } else {
                Global.showErrorSnakeBar(this@OtcPaymentActivity, s.responseDesc)
            }
        });


    }
}