package com.webdoc.payments.EasyPaisaCrdeitDebit.ViewModel

import android.app.Application
import android.graphics.Bitmap
import android.util.Log
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


class EpWebviewViewModel(application: Application) : AndroidViewModel(application) {
    var wb: WebView? = null
    private val MLD_open_webview = MutableLiveData<String>()

    //todo: Live Data
    fun LD_open_webview(): LiveData<String> {
        return MLD_open_webview
    }


    private inner class MyWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            return false
        }
    }

    fun openEasyPaisacreditDebitWebView(webView: WebView?, finalUrl: String?) {
        wb = webView
        wb!!.settings.javaScriptEnabled = true
        wb!!.settings.loadWithOverviewMode = true
        wb!!.settings.useWideViewPort = true
        wb!!.settings.domStorageEnabled = true
        wb!!.settings.builtInZoomControls = true
        wb!!.settings.pluginState = WebSettings.PluginState.ON
        wb!!.webViewClient = MyWebViewClient()
        wb!!.loadUrl(finalUrl!!)
        Log.i("finalurl", finalUrl)
        wb!!.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                Log.d("WebView", "your current url when webpage loading..$url")
            }

            override fun onPageFinished(view: WebView, url: String) {
                Log.d("WebView", "your current url$url")
                if (url.contains("AUTH_TXN")) {
                    //todo: payment success
                    MLD_open_webview.postValue("Payment Succesfull")
                } else if (url.contains("status")) {
                    //todo: FAilure
                    MLD_open_webview.postValue("Payment Fail")
                }
                super.onPageFinished(view, url)
            }

            override fun onLoadResource(view: WebView, url: String) {
                // TODO Auto-generated method stub
                super.onLoadResource(view, url)
            }

            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                println("when you click on any interlink on webview that time you got url :-$url")
                return super.shouldOverrideUrlLoading(view, url)
            }
        }
    }

}
