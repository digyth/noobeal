package com.digyth.noobeal.controller

import android.content.Context
import android.view.View
import android.webkit.WebView
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import com.digyth.noobeal.R
import com.digyth.noobeal.webkit.NoobealWebView
import com.google.android.material.bottomsheet.BottomSheetDialog

class BrowserController(val ctx: Context, val webView: NoobealWebView) : IBrowserController,
    View.OnLongClickListener,AdapterView.OnItemClickListener {

    private val dialog = BottomSheetDialog(ctx)

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
        if (webView.hitTestResult.type != WebView.HitTestResult.UNKNOWN_TYPE) {
            dialog.show()
        }
        return true
    }

    override fun onItemClick(parent:AdapterView<*>, view:View, position:Int, id:Long) {

    }

}