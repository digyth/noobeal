package com.digyth.noobeal.controller

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Point
import android.view.MotionEvent
import android.view.View
import android.webkit.WebView
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import com.digyth.noobeal.R
import com.digyth.noobeal.view.ImagesDialog
import com.digyth.noobeal.webkit.NoobealWebView
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.*
import org.jsoup.Jsoup
import kotlin.math.abs

class BrowserController(private val ctx: Context, private val webView: NoobealWebView) :
    IBrowserController, View.OnLongClickListener, AdapterView.OnItemClickListener {

    private val dialog = BottomSheetDialog(ctx)
    private val bridge = JsBridge(webView)

    init {
        webView.setOnLongClickListener(this)
        dialog.setContentView(R.layout.web_view_menu)
        dialog.findViewById<ListView>(R.id.bottom_menu)?.onItemClickListener = this

    }

    override fun onSelect(element: WebView.HitTestResult) {

    }

    override fun expandRange() {
        Toast.makeText(ctx, "Expand range", Toast.LENGTH_SHORT).show()
    }

    override fun onLongClick(v: View): Boolean {
        if(webView.hitTestResult.type != WebView.HitTestResult.UNKNOWN_TYPE) {
            dialog.show()
            return true
        }
        return false
    }

    override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        when (position) {
            2 -> {
                bridge.showImages()
                dialog.hide()
            }
        }
    }
}