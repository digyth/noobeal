package com.digyth.noobeal.controller

import android.content.Context
import android.webkit.JavascriptInterface
import android.webkit.ValueCallback
import android.webkit.WebView
import android.widget.Toast
import com.digyth.noobeal.R
import com.digyth.noobeal.gson
import com.digyth.noobeal.toStringArray
import com.digyth.noobeal.view.ImagesDialog
import com.google.gson.JsonArray
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSyntaxException

class JsBridge(private val webView: WebView) : ImagesDialog.Listener {

    private val imageValueCallback = object : ValueCallback<String> {
        override fun onReceiveValue(value: String?) {
            if (value == null) {
                Toast.makeText(webView.context, "网页未加载完成", Toast.LENGTH_SHORT).show()
                return
            }
            try {
                val images = gson.fromJson(value, JsonArray::class.java)
                println(images.toStringArray().contentToString())
                if (images.size() > 0) {
                    ImagesDialog.show(
                        webView.context,
                        "Images",
                        images.toStringArray(),
                        this@JsBridge
                    )
                }
            } catch (e: JsonSyntaxException) {
                Toast.makeText(webView.context, "网页未加载完成", Toast.LENGTH_SHORT).show()
            }
        }

    }

    init {
        webView.addJavascriptInterface(Interfaces, "noobeal")
    }

    /***
     *
     * show image list in the selected area
     *
     * @author digyth
     */
    fun showImages() {
        webView.evaluateJavascript(
            "javascript:noobeal.getUrls('img')",
            imageValueCallback
        )
    }

    private object Interfaces {

        /**
         * print debug info
         *
         * @param message debug info
         * @author digyth
         */
        @JavascriptInterface
        fun debugPrint(message: String) {
            println(message)
        }
    }

    override fun onCancel() {

    }

    /**
     * expand range to collect images
     */
    override fun onExpand() {
        webView.evaluateJavascript("javascript:noobeal.expandRange()", imageValueCallback)
    }

}