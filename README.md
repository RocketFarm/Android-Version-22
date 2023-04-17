# FanpowerPackage
Kotlin package to display Fanpower widget

# Adding the Fanpower Kotlin Package to your project
Add the following in your module level gradle file

```
dependencies {
      implementation 'com.github.RocketFarm:fanpower-android-package:0.1.3'
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

        fanPowerView.initView(
            "your-tokenForJwtRequest",
            0, // your-publisherId
            "your-publisherToken",
            "your-shareUrl",
            supportFragmentManager
        )
    }
}
```
tokenForJwtRequest, publisherToken, and publisherId should be supplied to you by FanPower. ShareUrl is a URL that users will share when they use the widget's share feature. It is also used to create the referral URL. supportFragmentManager is the FragmentManager of your activity that you need to provide. 

