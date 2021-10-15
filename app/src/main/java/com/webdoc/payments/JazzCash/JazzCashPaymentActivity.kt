package com.webdoc.payments.JazzCash

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.webdoc.payments.Essentials.Global
import com.webdoc.payments.JazzCash.ResponseModels.JazzCashResponseNew
import com.webdoc.payments.JazzCash.ViewModel.JazzCashViewModel
import com.webdoc.payments.R
import com.webdoc.payments.databinding.ActivityJazzCashPaymentBinding

class JazzCashPaymentActivity : AppCompatActivity() {

    var binding: ActivityJazzCashPaymentBinding? = null
    var viewModel: JazzCashViewModel? = null
    var mobileNumber: String? = null
    var cnic: kotlin.String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jazz_cash_payment)

        initViews()
        clickListeners()
        observer()
    }

    private fun observer() {
        viewModel!!.LD_callingJazzCash().observe(this, { s ->
            if (s is JazzCashResponseNew) {
                if (s.ppResponseCode.equals("000")) {

                    Toast.makeText(
                        this@JazzCashPaymentActivity,
                        "Payment succesfull",
                        Toast.LENGTH_LONG

                        // now call your savepayment Api here
                    )
                } else {

                    //todo.Payment fail

                }

            }
        })
    }

    private fun initViews() {
        Global.applicationContext = this@JazzCashPaymentActivity
        binding = ActivityJazzCashPaymentBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        viewModel =
            ViewModelProviders.of(this@JazzCashPaymentActivity)[JazzCashViewModel::class.java]

        binding!!.tvAmount.setText(Global.price)

    }

    private fun clickListeners() {
        binding!!.etAccountNo.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (binding!!.etAccountNo.text!!.length === 11) {
                    Global.hideKeyboard(this@JazzCashPaymentActivity)
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
                if (binding!!.etCnic.text!!.length === 6) {
                    Global.hideKeyboard(this@JazzCashPaymentActivity)
                    binding!!.btnNext.visibility = View.VISIBLE
                } else {
                    binding!!.btnNext.visibility = View.GONE
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })
        binding!!.btnNext.setOnClickListener {

            Toast.makeText(this, "please wait", Toast.LENGTH_SHORT)
            mobileNumber = binding!!.etAccountNo.text.toString()
            cnic = binding!!.etCnic.text.toString()
            viewModel!!.CallingJazzCashApi(mobileNumber!!, cnic!!)
        }
    }
}

private fun TextView.setText(price: Any) {

}
