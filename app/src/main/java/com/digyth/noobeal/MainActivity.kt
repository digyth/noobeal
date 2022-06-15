package com.digyth.noobeal

import android.graphics.Bitmap
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.WebView
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.digyth.noobeal.controller.BrowserController
import com.digyth.noobeal.controller.IBrowserController
import com.digyth.noobeal.databinding.ActivityMainBinding
import com.digyth.noobeal.webkit.NoobealWebListener
import com.digyth.noobeal.webkit.NoobealWebView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    TextView.OnEditorActionListener, NoobealWebListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var menu: Menu
    private lateinit var webView: NoobealWebView
    private lateinit var webViewController: IBrowserController

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
        binding.navView.setNavigationItemSelectedListener(this)
        binding.appBarMain.webViewUrl.setOnEditorActionListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        this.menu = menu
        menuInflater.inflate(R.menu.main, menu)
        initWebView()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_refresh -> if (loading) {
                webView.stopLoading()
            } else {
                webView.reload()
            }
            R.id.action_forward -> if (webView.canGoForward()) {
                webView.goForward()
            } else {
                Toast.makeText(this, getString(R.string.no_forward_warning), Toast.LENGTH_SHORT)
                    .show()
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

    private fun initWebView() {
        webView = binding.appBarMain.contentMain.webView
        webViewController = BrowserController(this, webView)
        webView.setNoobealWebListener(this)
        //load url
        webView.loadUrl(getString(R.string.default_url))
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_exit -> finish()
        }
        return true
    }

    override fun onEditorAction(v: TextView, actionId: Int, event: KeyEvent): Boolean {
        webView.loadUrl(v.text.toString())
        return true
    }


    private val urlEditor: EditText by lazy { binding.appBarMain.webViewUrl }
    private val refreshBtn: MenuItem by lazy { menu.findItem(R.id.action_refresh) }
    private val progressBar: ProgressBar by lazy { binding.appBarMain.contentMain.webViewProgress }

    override fun loadUrl(url: String): String {
        if (url.startsWith("javascript:")) return url
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
        view.loadUrl(Constants.code_js_inject)
    }

    override fun onProgressChanged(view: WebView, newProgress: Int) {
        if (newProgress == 100) {
            progressBar.visibility = View.GONE
        } else {
            progressBar.visibility = View.VISIBLE
            progressBar.progress = newProgress
        }
    }
}