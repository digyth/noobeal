package com.digyth.noobeal.webkit

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.util.AttributeSet
import android.util.Log
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

            override fun shouldOverrideUrlLoading(
                view: WebView,
                request: WebResourceRequest
            ): Boolean {
                return handleUri(request.url)
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

    fun handleUri(uri: Uri): Boolean {
        if (uri.scheme != "http" && uri.scheme != "https" && uri.scheme != "javascript") {
            val intent = Intent(Intent.ACTION_VIEW, uri)
            if (isInstall(intent)) {
                context.startActivity(intent)
            }
            return true
        }
        return false
    }

    private fun isInstall(intent: Intent): Boolean {
        return context.packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY).size > 0
    }

    fun setNoobealWebListener(listener: NoobealWebListener) {
        this.listener = listener
    }

    override fun getVisibility(): Int {
        return VISIBLE
    }

    override fun loadUrl(url: String) {
        super.loadUrl(if (Uri.parse(url).scheme == null) "https://$url" else url)
    }

}