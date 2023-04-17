package com.fanpower.lib.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import com.fanpower.lib.R
import com.fanpower.lib.databinding.ActivityTestBinding
import com.fanpower.lib.databinding.ActivityWebViewBinding
import com.fanpower.lib.utils.Constants.Extra.UrlExtra

class WebViewActivity : AppCompatActivity() {


    private lateinit var binding: ActivityWebViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        var url = ""

        if(intent != null){
            url = intent.getStringExtra(UrlExtra).toString()
        }

        binding.webView.webViewClient = WebViewClient()
        binding.webView.loadUrl(url)
        binding.webView.settings.javaScriptEnabled = true

        binding.webView.settings.setSupportZoom(true)
    }

    override fun onBackPressed() {
        // if your webview can go back it will go back
        if (binding.webView.canGoBack())
            binding.webView.goBack()
        // if your webview cannot go back
        // it will exit the application
        else
            super.onBackPressed()
    }
}