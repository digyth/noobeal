package com.digyth.noobeal

import android.graphics.Bitmap
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.WebView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.digyth.noobeal.databinding.ActivityMainBinding
import com.digyth.noobeal.webkit.NoobealWebListener
import com.digyth.noobeal.webkit.NoobealWebView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var webView: NoobealWebView

    private var lastBack = 0L
    private var loading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        // Setup the action bar.
        val toggleButton = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.appBarMain.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        toggleButton.syncState()
        binding.drawerLayout.addDrawerListener(toggleButton)

        binding.appBarMain.webViewUrl.setOnEditorActionListener { v, _, _ ->
            webView.loadUrl(v.text.toString())
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        initWebView(menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_refresh -> if (loading) {
                webView.stopLoading()
            }else {
                webView.reload()
            }
            R.id.action_forward -> if(webView.canGoForward()){
                webView.goForward()
            }else {
                Toast.makeText(this, getString(R.string.no_forward_warning), Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
            return
        }
        if (System.currentTimeMillis() - lastBack < 2000) {
            super.finish()
        } else {
            lastBack = System.currentTimeMillis()
            Toast.makeText(this, "再按一次退出APP", Toast.LENGTH_SHORT).show()
        }
    }

    fun initWebView(menu: Menu) {
        webView = binding.appBarMain.contentMain.webView
        webView.setNoobealWebListener(object : NoobealWebListener {

            val urlEditor = binding.appBarMain.webViewUrl
            val refreshBtn = menu.findItem(R.id.action_refresh)
            val progressBar = binding.appBarMain.contentMain.webViewProgress

            override fun loadUrl(url: String): String {
                val newUrl = if (url.startsWith("http")) {
                    url
                } else {
                    "https://$url"
                }
                urlEditor.setText(newUrl)
                return newUrl
            }

            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
                refreshBtn.icon = getDrawable(R.drawable.ic_toolbar_cancel)
                loading = true
            }

            override fun onPageFinished(view: WebView, url: String) {
                refreshBtn.icon = getDrawable(R.drawable.ic_toolbar_refresh)
                loading = false
            }

            override fun onProgressChanged(view: WebView, newProgress: Int) {
                if (newProgress == 100) {
                    progressBar.visibility = View.GONE
                } else {
                    progressBar.visibility = View.VISIBLE
                    progressBar.progress = newProgress
                }
            }

        })
        //load url
        webView.loadUrl(getString(R.string.default_url))
    }
}