package com.webdoc.payments.EasyPaisaWallet

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.webdoc.payments.EasyPaisaWallet.ResponseModel.EasypaisaPAymentResponse
import com.webdoc.payments.EasyPaisaWallet.ViewModel.EasyPaisaViewModel
import com.webdoc.payments.Essentials.Global
import com.webdoc.payments.R
import com.webdoc.payments.databinding.ActivityEasyPaisaBinding

var binding: ActivityEasyPaisaBinding? = null
var viewModel: EasyPaisaViewModel? = null
var mobileNumber: String? = null
var email: kotlin.String? = null

class EasyPaisaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_easy_paisa)

        initViews()
        clickListeneres()
        observers()
    }

    private fun clickListeneres() {
        binding!!.etAccountNo.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (binding
                    !!.etAccountNo.getText()!!.length === 11
                ) {
                    Global.hideKeyboard(this@EasyPaisaActivity)
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
                    //   Global.utils.hideKeyboard(this@EasyPaisaActivity)
                    binding!!.btnNext.setVisibility(View.VISIBLE)
                } else {
                    binding!!.btnNext.setVisibility(View.GONE)
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })

        binding!!.btnNext.setOnClickListener(View.OnClickListener {
            //  Global.utils.showCustomLoadingDialog(this@EasyPaisaActivity)
            mobileNumber = binding!!.etAccountNo.getText().toString()
            email = binding!!.etCnic.getText().toString()
            viewModel!!.btnNextClick(mobileNumber!!, email!!)
        })
    }

    private fun observers() {

        //todo: when next button is click befor easy paisa payment this observer will call====>>>>
        viewModel!!.LD_btn_next_click().observe(this, { s ->
            if (s is EasypaisaPAymentResponse) {
                if (s.responseCode.equals("0000")) {

                    //Payment sccesfull


                } else if (s.responseCode.equals("0001")) {
                    //system Error"

                } else if (s.responseCode.equals("0013")) {
                    //Low account balance

                } else {
                    //payment fail")
                }
            }
        })
    }

    fun initViews() {
        //todo:binding xml fine with java file
        binding = ActivityEasyPaisaBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        //todo:connecting View Model with activityyu
        viewModel = ViewModelProviders.of(this).get(EasyPaisaViewModel::class.java)
        Global.applicationContext = this@EasyPaisaActivity
        // binding.tvPkgPrice.setText(java.lang.String.valueOf(Global.price))
    }
}