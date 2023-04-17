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

  //  private lateinit var binding: ActivityWebViewBinding

    private lateinit var webView : WebView

    private lateinit var fanPowerView: FanPowerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_fan_power)
//
         webView = findViewById(R.id.webView_)

        fanPowerView = findViewById<FanPowerView>(R.id.fanPowerView_)




        webView.getSettings().setJavaScriptEnabled(true);
        val unencodedHtml =Utils.testHTMLWithId

        webView.requestFocus();
        webView.getSettings().setLightTouchEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setGeolocationEnabled(true);
        webView.setSoundEffectsEnabled(true);
   //     webView.setWebChromeClient(MyWebChromeClient());

        fanPowerView.visibility = View.VISIBLE

        val webSettings: WebSettings = webView.getSettings()
        webSettings.setNeedInitialFocus(false);

        val base64 = Base64.encodeToString(unencodedHtml.toByteArray(), Base64.DEFAULT)
        webView.loadData(base64, "text/html; charset=utf-8", "base64");

//        webView.setOnFocusChangeListener(OnFocusChangeListener { arg0, hasFocus ->
//            if (!hasFocus) {
//                Log.i(TAG, "onCreate:webview don't have focus")
//                webView.requestFocus()
//                webView.setFocusable(true);
//            }else{
//                Log.i(TAG, "onCreate:webview have focus")
//            }
//        })



         val heightWebViewJSScript = "(function() {var pageHeight = 0;function findHighestNode(nodesList) { for (var i = nodesList.length - 1; i >= 0; i--) {if (nodesList[i].scrollHeight && nodesList[i].clientHeight) {var elHeight = Math.max(nodesList[i].scrollHeight, nodesList[i].clientHeight);pageHeight = Math.max(elHeight, pageHeight);}if (nodesList[i].childNodes.length) findHighestNode(nodesList[i].childNodes);}}findHighestNode(document.documentElement.childNodes); return pageHeight;})()"

        webView.webViewClient = object : WebViewClient() {

            override fun onPageFinished(view: WebView, url: String) {
                webView.evaluateJavascript(heightWebViewJSScript
                ) { height ->
                    if(height != null) {
                        var webViewHeight: Float =
                            (height.toInt() * Resources.getSystem().displayMetrics.density)
                        Log.i(TAG, "onPageFinished: heighr " + webViewHeight)

                        var ids: List<String> = listOf("fanpower")

                        Handler().postDelayed({
                            //doSomethingHere()
                            Utils.getWebIDRects(webView, ids) { rects ->
                                // Do something with the list of Rect objects
                                Log.i(TAG, "onPageFinished: inside callback")
                                for (rect in rects) {
                                    Log.d(TAG, rect.toString())
                                }

                                addFanPowerView(rects, webViewHeight)
                            }
                        }, 300)
                    }
                    // params.height
                }
            }
        }

    }


    fun addFanPowerView(rects : List<Rect>,webViewHeight : Float){
        Log.i(TAG, "addFanPowerView: rects " + rects.size)
        if(rects.size == 0){
            return
        }

        var widgetHeight : Int= Utils.pxFromDp(this,640f).toInt()

        Log.i(TAG, "addFanPowerView: widget height " + widgetHeight)

        var topMargin = Utils.pxFromDp(this,rects.get(0).top.toFloat() - rects.get(0).height()-Utils.pxFromDp(this,13f).toInt())
   //     var topMargin = Utils.pxFromDp(this,rects.get(0).top.toFloat())
        var bottomMargin = webViewHeight - (topMargin + widgetHeight)

      //  Log.i(TAG, "addFanPowerView: x is " + rects.get(0).exactCenterX() + " y is " + rects.get(0).top)
        Log.i(TAG, "addFanPowerView: converted top of the view  is " + topMargin)
        Log.i(TAG, "addFanPowerView: bottom margin is " + bottomMargin)
        Log.i(TAG, "addFanPowerView: widget height is " + widgetHeight)

        Log.i(TAG, "addFanPowerView: webview height " + webView.contentHeight * Resources.getSystem().displayMetrics.density)

        fanPowerView.initView("your-tokenForJwtRequest",
            0, // your-publisherId
            "your-publisherToken",
            "your-shareUrl",
            supportFragmentManager,
            0f,
            0f,
            0,
            webView
        )

        fanPowerView.layoutParams.height = (topMargin + bottomMargin + widgetHeight).toInt()
    }



    override fun onBackPressed() {
        // if your webview can go back it will go back
        if (webView.canGoBack()) {
            webView.goBack()
            Handler().postDelayed({
                webView.scrollTo(0, 0)
                                  }, 500)

            // if your webview cannot go back
            // it will exit the application
        } else
            super.onBackPressed()
    }
}