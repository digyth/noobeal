package com.digyth.noobeal.controller

import android.webkit.JavascriptInterface
import android.webkit.WebView

class JsBridge(webView: WebView) {

    init {
        webView.addJavascriptInterface(Interfaces, "noobeal")
    }

    fun test(){

    }

    private object Interfaces {

        @JavascriptInterface
        fun debugPrint(message: String) {
            println(message)
        }
    }

}