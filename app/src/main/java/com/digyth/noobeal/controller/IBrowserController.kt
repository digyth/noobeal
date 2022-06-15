package com.digyth.noobeal.controller

import android.webkit.WebView

interface IBrowserController {

    fun onSelect(element:WebView.HitTestResult)

    fun expandRange()

}