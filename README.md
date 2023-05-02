# FanpowerPackage
Kotlin package to display Fanpower widget

# Adding the Fanpower Kotlin Package to your project
Add the following in your module level gradle file

```
dependencies {
      implementation 'com.github.RocketFarm:Android-Version-22:0.0.4'
}
```

Also, need to add the following code in settings.gradle of your project 

```
 jcenter()
 maven { url "https://jitpack.io" }
```
Your code would look something like this
```
//... other code
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        jcenter()
        maven { url "https://jitpack.io" }
    }
}
// ...other code
```

# Example Usage
 

## XML for inline FanPower widget

Your XML should excatly look like this in order to support inline FanPower widget

```
//...... other code

 <WebView
        android:descendantFocusability="afterDescendants"
        android:id="@+id/webView_"
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <com.fanpower.lib.ui.views.FanPowerView
            android:visibility="gone"
            android:id="@+id/fanPowerView_"
            android:layout_width="match_parent"
            android:layout_height="match_parent">

        </com.fanpower.lib.ui.views.FanPowerView>

    </WebView>

//........

```

# How to use FanPower Widget in your app with webview

```

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val Html =Utils.testHTMLWithId // any HTML you want to render in your webview

        webView.requestFocus();
        webView.getSettings().setLightTouchEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setGeolocationEnabled(true);

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
}     


 fun addFanPowerView(rects : List<Rect>,webViewHeight : Float){
        if(rects.size == 0)
            return

        var widgetHeight : Int= Utils.pxFromDp(this,620f).toInt() // provide your size in the function
        var topMargin = Utils.pxFromDp(this,rects.get(0).top.toFloat()) - Utils.pxFromDp(this,rects.get(0).height().toFloat()) - Utils.pxFromDp(this,40f)
        var bottomMargin = webViewHeight - (topMargin + widgetHeight)

       // main init code 
       fanPowerView.initView("your-tokenForJwtRequest",
            0, // your-publisherId
            "your-publisherToken",
            "your-shareUrl",
            listOf(), // if you need hardcoded prop ids to load rather than carousel api to get props then pass any number of prop ids in this list. 
            supportFragmentManager,
            0f, // top margin from fanpower widget
            0f, // bottom margin from fanpower widget
            0, // widget height
            webView
        ) 

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

```

tokenForJwtRequest, publisherToken, and publisherId should be supplied to you by FanPower. ShareUrl is a URL that users will share when they use the widget's share feature. It is also used to create the referral URL. supportFragmentManager is the FragmentManager of your activity that you need to provide. 
Top margin is the margin widget has from the top of scroll. Bottom margin is the margin widget has from bottom and widget height is the the total hieght of fan power view on screen. 

And See the implementation of WebActivityFanPower in sample app for details.


## Example Usage without inline view
Use the following init method to simply use FanPower widget in any fragment, activity or anywhere in xml (not in webview)

```
   fanPowerView.initView("your-tokenForJwtRequest",
            0, // your-publisherId
            "your-publisherToken",
            "your-shareUrl",
            supportFragmentManager)

```



