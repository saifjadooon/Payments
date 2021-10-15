package com.webdoc.payments.EasyPaisaCrdeitDebit.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class EasyPaisaCreditdebitViewModel(application: Application) : AndroidViewModel(application) {
    private val MLD_btn_nextClick = MutableLiveData<String>()
    fun LD_btn_next_click(): LiveData<String> {
        return MLD_btn_nextClick
    }

    fun btnNextClick(mobileNumber: String?, email: String?) {}
}
