# FanpowerPackage
Kotlin package to display Fanpower widget

# Adding the Fanpower Kotlin Package to your project
Add the following in your module level gradle file

```
dependencies {
      implementation 'com.github.RocketFarm:Android-Version-22:0.0.3'
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

## Example Usage
 Initializing the widget

```
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
          var fanPowerView = findViewById<FanPowerView>(R.id.fanPowerView)
        
         fanPowerView.initView("your-tokenForJwtRequest",
            0, // your-publisherId
            "your-publisherToken",
            "your-shareUrl",
            supportFragmentManager,
            0f, // top margin from fanpower widget
            0f, // bottom margin from fanpower widget
            0, // widget height
            webView
        )
    }
}
```
tokenForJwtRequest, publisherToken, and publisherId should be supplied to you by FanPower. ShareUrl is a URL that users will share when they use the widget's share feature. It is also used to create the referral URL. supportFragmentManager is the FragmentManager of your activity that you need to provide. 
Top margin is the margin widget has from the top of scroll. Bottom margin is the margin widget has from bottom and widget height is the the total hieght of fan power view on screen. 

# Using the widget with WebView 

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

And See the implementation of WebActivityFanPower in sample app to see how you need to implement WebView along with your Fan Power Widget. You need to follow the exact steps(code) as given in the activity and then your widget would work just fine with WebView. 
