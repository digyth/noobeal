package com.digyth.noobeal.webkit

import android.content.Context
import android.graphics.Bitmap
import android.util.AttributeSet
import android.view.ActionMode
import android.webkit.*
import android.widget.Toast

class NoobealWebView : WebView {

    private var listener: NoobealWebListener? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        //settings
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        settings.allowFileAccess = true
        settings.allowContentAccess = true
        settings.cacheMode = WebSettings.LOAD_NO_CACHE
        //client
        webViewClient = object : WebViewClient() {

            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                listener?.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                listener?.onPageFinished(view, url)
            }

        }
        webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                listener?.onProgressChanged(view, newProgress)
            }

            override fun onJsAlert(
                view: WebView?,
                url: String?,
                message: String?,
                result: JsResult?
            ): Boolean {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                result?.confirm()
                return true
            }
        }
    }

    fun setNoobealWebListener(listener: NoobealWebListener) {
        this.listener = listener
    }

    override fun loadUrl(url: String) {
        super.loadUrl(listener?.loadUrl(url) ?: url)
    }

    override fun getVisibility(): Int {
        return VISIBLE
    }


}