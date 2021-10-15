package com.webdoc.payments.EasyPaisaCrdeitDebit.Activities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.webdoc.payments.EasyPaisaCrdeitDebit.ViewModel.EasyPaisaCreditdebitViewModel
import com.webdoc.payments.Essentials.Global
import com.webdoc.payments.R
import com.webdoc.payments.databinding.ActivityEasyPaisaCreditDebitBinding

class EasyPaisaCreditDebitActivity : AppCompatActivity() {
    var binding: ActivityEasyPaisaCreditDebitBinding? = null
    var viewModel: EasyPaisaCreditdebitViewModel? = null
    var mobileNumber: String? = null
    var email: kotlin.String? = null
    var url: kotlin.String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_easy_paisa_credit_debit)


        initViews()
        clickListeners()


    }
    private fun clickListeners() {
        binding!!.etAccountNo.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (binding!!.etAccountNo.text!!.length === 11) {
                    Global.hideKeyboard(this@EasyPaisaCreditDebitActivity)
                    binding!!.ccEnterCnic.visibility = View.VISIBLE
                } else {
                    binding!!.ccEnterCnic.visibility = View.GONE
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })
        binding!!.etCnic.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (Global.isEmailValid(binding!!.etCnic.text.toString())) {
                    Global.hideKeyboard(this@EasyPaisaCreditDebitActivity)
                    binding!!.btnNext.visibility = View.VISIBLE
                } else {
                    binding!!.btnNext.visibility = View.GONE
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })
        binding!!.btnNext.setOnClickListener {
           // Global.utils.showCustomLoadingDialog(this@EasyPaisaCreditDebitActivity)
            mobileNumber = binding!!.etAccountNo.text.toString()
            email = binding!!.etCnic.text.toString()
            val intent = Intent(Global.applicationContext, WebviewActivity::class.java)
            intent.putExtra("mobileNumber", mobileNumber)
            intent.putExtra("email", email)
            startActivity(intent)
        }
    }

    fun initViews() {
        //todo:binding xml fine with java file
        binding = ActivityEasyPaisaCreditDebitBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        //todo:connecting View Model with activityyu
        viewModel = ViewModelProviders.of(this)[EasyPaisaCreditdebitViewModel::class.java]
        Global.applicationContext = this@EasyPaisaCreditDebitActivity
             //binding!!.tvPkgPrice.setText(Global.price)
    }

}