package com.fanpower.power

import android.content.res.Resources
import android.graphics.Rect
import android.os.Bundle
import android.os.Handler
import android.util.Base64
import android.util.Log
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.fan.power.R
import com.fanpower.lib.ui.views.FanPowerView

import com.fanpower.power.Utility.Utils


class WebActivityFanPower : AppCompatActivity() {

    private  val TAG = "WebActivityFanPower"

    private lateinit var webView : WebView

    private lateinit var fanPowerView: FanPowerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_fan_power)
//
         webView = findViewById(R.id.webView_)

        fanPowerView = findViewById(R.id.fanPowerView_)

        val unencodedHtml =Utils.testHTMLWithId

        webView.requestFocus();
        webView.getSettings().setLightTouchEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setGeolocationEnabled(true);

        fanPowerView.visibility = View.VISIBLE

        val webSettings: WebSettings = webView.getSettings()
        webSettings.setNeedInitialFocus(false);

        val base64 = Base64.encodeToString(unencodedHtml.toByteArray(), Base64.DEFAULT)
        webView.loadData(base64, "text/html; charset=utf-8", "base64");
         val heightWebViewJSScript = "(function() {var pageHeight = 0;function findHighestNode(nodesList) { for (var i = nodesList.length - 1; i >= 0; i--) {if (nodesList[i].scrollHeight && nodesList[i].clientHeight) {var elHeight = Math.max(nodesList[i].scrollHeight, nodesList[i].clientHeight);pageHeight = Math.max(elHeight, pageHeight);}if (nodesList[i].childNodes.length) findHighestNode(nodesList[i].childNodes);}}findHighestNode(document.documentElement.childNodes); return pageHeight;})()"

        webView.webViewClient = object : WebViewClient() {

            override fun onPageFinished(view: WebView, url: String) {
                webView.evaluateJavascript(heightWebViewJSScript
                ) { height ->
                    if(height != null) {
                        var webViewHeight: Float =
                            (height.toInt() * Resources.getSystem().displayMetrics.density)
                        var ids: List<String> = listOf("fanpower")

                        Handler().postDelayed({
                            //doSomethingHere()
                            Utils.getWebIDRects(webView, ids) { rects ->
                                addFanPowerView(rects, webViewHeight)
                            }
                        }, 500)
                    }
                }
            }
        }
    }

    fun addFanPowerView(rects : List<Rect>,webViewHeight : Float){
        if(rects.size == 0)
            return

        var widgetHeight : Int= Utils.pxFromDp(this,620f).toInt() // provide your size in the function
        var topMargin = Utils.pxFromDp(this,rects.get(0).top.toFloat()) - Utils.pxFromDp(this,rects.get(0).height().toFloat()) - Utils.pxFromDp(this,50f)
        var bottomMargin = webViewHeight - (topMargin + widgetHeight)


        fanPowerView.initViewWithInline("your-tokenForJwtRequest",
            0, // your-publisherId
            "your-publisherToken",
            "your-shareUrl",
            listOf(),
            supportFragmentManager,
            topMargin,
            bottomMargin,
            widgetHeight,
            webView)


        fanPowerView.layoutParams.height = (topMargin + bottomMargin + widgetHeight).toInt()
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
            Handler().postDelayed({
                webView.scrollTo(0, 0)
                                  }, 500)
        } else
            super.onBackPressed()
    }
}