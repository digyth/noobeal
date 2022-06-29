package com.digyth.noobeal.webkit

import android.graphics.Bitmap
import android.webkit.WebView

interface NoobealWebListener {

    fun onLoadUrl(url: String)

    fun onPageStarted(view: WebView, url: String, favicon: Bitmap?)

    fun onPageFinished(view: WebView, url: String)

    fun onProgressChanged(view: WebView, newProgress: Int)
}