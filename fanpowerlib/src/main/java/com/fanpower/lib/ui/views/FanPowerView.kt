package com.fanpower.lib.ui.views


import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.webkit.WebView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.fanpower.lib.R
import com.fanpower.lib.adapter.ViewPagerAdapter
import com.fanpower.lib.api.ApiFactory
import com.fanpower.lib.api.ApiManager
import com.fanpower.lib.api.model.*
import com.fanpower.lib.databinding.FanPowerViewLibBinding
import com.fanpower.lib.interfaces.SuccessFailureCallback
import com.fanpower.lib.interfaces.VerificationPopUpShownCallback
import com.fanpower.lib.ui.activity.WebViewActivity
import com.fanpower.lib.ui.fragment.QuestionsFragment
import com.fanpower.lib.utils.Constants
import com.fanpower.lib.utils.Constants.Extra.UrlExtra
import com.fanpower.lib.utils.SharedPrefs
import com.fanpower.lib.utils.Utilities
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class FanPowerView : RelativeLayout {

    private val TAG = "FanPowerView"

    private var binding: FanPowerViewLibBinding

    private lateinit var fragmentManager : FragmentManager
    private  var topMarginInScrollView : Float = 0f
    private  var bottomMarginInScrollView : Float = 0f
    private var widgetHeight : Int = 0


    //  private  var activity: Activity? = null
//    private lateinit var context : Context

    private lateinit var propIds: List<PropId>
    private var publisherId: Int = 0
    private var publisherToken: String = ""
    private var publisher: Publisher? = null
    private var tokenForJwtRequest: String = ""
    private var shareUrl: String = ""
    private lateinit var webView : WebView

    private var urlToShare = ""


    private var props = ArrayList<Prop>()
    private var hasUserPickedArray = ArrayList<Boolean>()
    private var dots: List<ImageView>? = null

    private var currentAlpha = 0f
    //    private var compassNeedle: ImageView? = null

    constructor(context: Context) : super(context) {
//        Log.d(TAG, "CompassView(context) called")
//
//        binding = ActivityMainBinding.inflate(LayoutInflater.from(context), this, true)
//
////            val inflater =
////                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
////            binding = ActivityMainBinding.inflate(inflater);
//        //  this.activity = activity
//        Log.d(TAG, "Inflation started from constructor.")
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        Log.d(TAG, "CompassView(context, attrs) called")
//        binding = ActivityMainBinding.inflate(LayoutInflater.from(context), this, true)
        Log.d(TAG, "Inflation started from constructor.")
    }

    fun initView(
        tokenForJwtRequest: String,
        publisherId: Int,
        publisherToken: String,
        shareUrl: String,
        fragmentManager : FragmentManager,
        topMargin: Float,
        bottomMargin : Float,
        widgetHeight : Int,
        webView : WebView
    ) {
      //  this.activity = Utilities.getActivity(this)
     //   this.context = context
        this.tokenForJwtRequest = tokenForJwtRequest
     //   this.propIds = propIds
        this.publisherId = publisherId
        this.shareUrl = shareUrl
        this.publisherToken = publisherToken
        this.fragmentManager = fragmentManager
        this.topMarginInScrollView = topMargin
        this.bottomMarginInScrollView = bottomMargin
        this.widgetHeight = widgetHeight
        this.webView = webView

        SharedPrefs.Utils.saveAdminToken(context, publisherToken)
        SharedPrefs.Utils.savePublisherId(context, publisherId)
        SharedPrefs.Utils.savePublisherToken(context, publisherToken)
        //   SharedPrefs.Utils.savePropId(context,propId)
        SharedPrefs.Utils.saveTokenForJWTRequest(context, tokenForJwtRequest)
        SharedPrefs.Utils.saveSourceUrl(context, this.shareUrl)

        Utilities.getActivity(this)?.getWindow()?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)



//        if(SharedPrefs.Utils.getIsFirstRun(context)){
//            binding.popupBg.visibility = VISIBLE
//        }else{
//            binding.popupBg.visibility = GONE
//        }

        setUpWidgetMargins()
        setUpBtns()
        //    setupViewPager()
        setUpApis()


    }

    private fun setUpWidgetMargins(){
        Log.i(TAG, "setUpWidgetMargins: scrollview height " + (topMarginInScrollView + bottomMarginInScrollView + widgetHeight))

 //       binding.topLayout.layoutParams.width = LayoutParams.MATCH_PARENT
       binding.baseLayout.layoutParams.height = (topMarginInScrollView + bottomMarginInScrollView + widgetHeight).toInt()

   //     binding.scrollView.isEnabled = false


        binding.mainWidgetView.translationX = 0f
        binding.mainWidgetView.translationY = topMarginInScrollView
        binding.mainWidgetView.layoutParams.height = widgetHeight

   //     binding.mainWidgetView.translationY = Utilities.pxFromDp(context,topMarginInScrollView)
//        binding.topLayout.translationZ = 2000f
//        binding.topLayout.elevation = 2000f
//
//


//        (binding.topLayout.getParent() as View).requestLayout()
//        binding.topLayout.bringToFront()
//        binding.topLayout.invalidate();


   //     binding.mainWidgetView.layoutParams.height = widgetHeight.toInt()
    }

    private fun setUpBtns() {
        binding.go.setOnClickListener {
            SharedPrefs.Utils.saveIsFirstRun(context,false)
            Log.i(TAG, "setupViewPager: onclick")
            val fadeOutAnim = AnimationUtils.loadAnimation(context, R.anim.fade_out)
            binding.popupBg.startAnimation(fadeOutAnim)

            fadeOutAnim.setAnimationListener(object : Animation.AnimationListener {
                // All the other override functions
                override fun onAnimationStart(p0: Animation?) {

                }

                override fun onAnimationEnd(p0: Animation?) {
                    binding.popupBg.visibility = View.GONE
                }

                override fun onAnimationRepeat(p0: Animation?) {

                }
            })
        }

        binding.shareBtn.setOnClickListener {
            binding.sharePopup.visibility = View.VISIBLE
            val slideUpAnim = AnimationUtils.loadAnimation(context, R.anim.slide_up_animaton)
            binding.sharePopUpCard.startAnimation(slideUpAnim)

            val fadeInAnim = AnimationUtils.loadAnimation(context, R.anim.fade_in)
            binding.bgTintShare.visibility = VISIBLE
            binding.bgTintShare.startAnimation(fadeInAnim)


        }
//        getReferrals()



        binding.termsAndConditionBtn.setOnClickListener {
            val intent = Intent(context, WebViewActivity::class.java)
            intent.putExtra(UrlExtra, "https://fanpower.io/terms/")
            Log.i(TAG, "setUpBtns: get activity " + Utilities.getActivity(rootView))
            Utilities.getActivity(  this)?.startActivity(intent)
         //   Utilities.openUrl(context, "https://fanpower.io/terms/")
        }

        binding.learnMoreBtn.setOnClickListener {
            val intent = Intent(context, WebViewActivity::class.java)
            intent.putExtra(UrlExtra, "https://fanpower.io")
            Utilities.getActivity(this)?.startActivity(intent)
       //     Utilities.openUrl(context, "https://fanpower.io")
        }

        binding.facebookBtn.setOnClickListener {

            var title = ""
            Log.i(TAG, "setUpBtns: twiter link " + urlToShare)
            if (props != null && props.size >= binding.viewPager.currentItem && props.size != 0) {
                title = props.get(binding.viewPager.currentItem).proposition
            }

          //  title = title + "&hashtags=makeyourpick "

            var urlToShare = SharedPrefs.Utils.getSourceUrl(context)

            Log.i(TAG, "setUpBtns: url to share " + urlToShare)

            var intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, urlToShare)
            var facebookAppFound = false
            val matches: List<ResolveInfo> = context.getPackageManager()!!.queryIntentActivities(intent, 0)
            for (info in matches) {
                if (info.activityInfo.packageName.lowercase(Locale.getDefault())
                        .startsWith("com.facebook.katana")
                ) {
                    intent.setPackage(info.activityInfo.packageName)
                    facebookAppFound = true
                    break
                }
            }
            if (!facebookAppFound) {
                val sharerUrl = "https://www.facebook.com/sharer/sharer.php?u=$urlToShare"
                intent = Intent(Intent.ACTION_VIEW, Uri.parse(sharerUrl))
            }

            context.startActivity(intent)
        }

        binding.twitterBtn.setOnClickListener {
            var title = ""
            Log.i(TAG, "setUpBtns: twiter link " + urlToShare)
            if (props != null && props.size >= binding.viewPager.currentItem && props.size != 0 ) {
                title = props.get(binding.viewPager.currentItem).proposition
            }


//            if(hasUserPickedArray.get(binding.viewPager.currentItem) == false) {
//             //   title = title + "&hashtags=makeyourpick "
//                title = title
//            }else{
//                title = title
//            }

            var url = SharedPrefs.Utils.getSourceUrl(context)

            Log.i(TAG, "setUpBtns: url is " + url)

            try {
                val tweetIntent = Intent(Intent.ACTION_SEND)
                tweetIntent.setPackage("com.twitter.android")
                tweetIntent.data =
                    Uri.parse("https://twitter.com/intent/tweet?text=" + title + " &url= " + url)
                tweetIntent.putExtra(Intent.EXTRA_TEXT, "your message")
                context.startActivity(tweetIntent)
            } catch (e: ActivityNotFoundException) {
                val tweetIntent = Intent(Intent.ACTION_VIEW)
                tweetIntent.data =
                    Uri.parse("https://twitter.com/intent/tweet?text=" + title + " &url= " + url)
                context.startActivity(tweetIntent)
            }
        }

        binding.smsBtn.setOnClickListener {
            var title = ""
            Log.i(TAG, "setUpBtns: twi4ter link " + urlToShare)
            if (props != null && props.size >= binding.viewPager.currentItem && props.size != 0) {
                title = props.get(binding.viewPager.currentItem).proposition
            }

            var url = SharedPrefs.Utils.getSourceUrl(context)
            Log.i(TAG, "setUpBtns: url to share is " + url)

            val smsIntent = Intent(Intent.ACTION_SENDTO)
            smsIntent.data = Uri.parse("smsto:")
//            if(hasUserPickedArray.get(binding.viewPager.currentItem) == false) {
//                smsIntent.putExtra("sms_body", title + " " + url )
//            }else{
//                smsIntent.putExtra("sms_body", title + " " + url)
//            }

            smsIntent.putExtra("sms_body", title + " " + url )

            context.startActivity(smsIntent)
        }

        binding.crossBtn.setOnClickListener {
            val fadeOutAnim = AnimationUtils.loadAnimation(context, R.anim.fade_out)
            binding.sharePopup.startAnimation(fadeOutAnim)

            fadeOutAnim.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(p0: Animation?) {

                }

                override fun onAnimationEnd(p0: Animation?) {
                    binding.sharePopup.visibility = GONE
                }

                override fun onAnimationRepeat(p0: Animation?) {

                }
            })
        }
    }

    private fun getReferrals() {

        var currentPropId = "";
        Log.i(TAG, "getReferrals: props size " + props.size)
        if(props != null && props.size>= binding.viewPager.currentItem && props.size != 0){
            Log.i(TAG, "getReferrals: prop id " + props.get(binding.viewPager.currentItem).id)
            currentPropId = props.get(binding.viewPager.currentItem).id
        }

        val responseVerifyFan: Call<ReferralResponse> = ApiFactory.getInstance()!!.getReferrals(
            SharedPrefs.Utils.getPublisherId(context),
            SharedPrefs.Utils.getFanId(context).toString(),
            SharedPrefs.Utils.getPublisherToken(context).toString(),
            SharedPrefs.Utils.getSourceUrl(context).toString(),
            currentPropId
        )
        responseVerifyFan.enqueue(object : Callback<ReferralResponse?> {
            override fun onResponse(
                call: Call<ReferralResponse?>,
                response: Response<ReferralResponse?>
            ) {
                var referralResponse = response.body()
                Log.i(TAG, "onResponse: referral " + referralResponse)
                if (referralResponse != null) {

                    urlToShare = referralResponse.referral_url
                    SharedPrefs.Utils.saveSourceUrl(context, referralResponse.referral_url)
                    Log.i(TAG, "onResponse: url is " + referralResponse.referral_url)
                }
            }

            override fun onFailure(call: Call<ReferralResponse?>, t: Throwable) {

            }
        })
    }


    /* Remaining constructors here */

    init {
        Log.d(TAG, "Kotlin init block called.")
        binding = FanPowerViewLibBinding.inflate(LayoutInflater.from(context))
        addView(binding.root)
        //binding = ActivityMainBinding.inflate(LayoutInflater.from(context), null, false)

    }

    private fun setupViewPager() {
        if (Utilities.getActivity(this)?.isFinishing == true) {
            return
        }
        //  setUpDummyData()

        var adapter = ViewPagerAdapter(fragmentManager)

        // LoginFragment is the name of Fragment and the Login
        // is a title of tab

        for ((index, value) in props.withIndex()) {
     //   for (x in props) {
            var bundle = Bundle()
            bundle.putSerializable(Constants.Extra.PropExtra, value)

            var frag = QuestionsFragment(object : VerificationPopUpShownCallback {
                override fun enableScroll() {
                    binding.viewPager.isPagingEnabled = true
                    getReferrals()
                }

                override fun disableScroll() {
                    binding.viewPager.isPagingEnabled = false
                }

                override fun userPicked() {
                    Log.i(TAG, "userPicked: index is " + index)
                    if(hasUserPickedArray.size >= index) {
                        hasUserPickedArray.set(index, true)
                    }
                }
            },webView);

            frag.arguments = bundle

            adapter.addFragment(frag)
        }

        // setting adapter to view pager.
        binding.viewPager.setAdapter(adapter)
        addDots()

        binding.viewPager.offscreenPageLimit = 10

        //    binding.viewPager.adapter = viewPagerAdapter
            //    binding.dotsIndicator.attachTo(binding.viewPager)


    }



    public override fun onFinishInflate() {
        super.onFinishInflate()
        Log.d(TAG, "onFinishInflate() called.")
        //    compassNeedle = findViewById(R.id.compass_needle)
    }



    companion object {
        private const val TAG = "Compass_View_Kotlin"
    }


    private fun setUpApis() {
        getIPLocationApi()
        if (SharedPrefs.Utils.getAuthToken(context) != null) {
            getPublisherDataFromServer()
        } else {
            ApiManager.getAuthToken(context, object : SuccessFailureCallback {
                override fun onSuccess() {
                    if (context == null) {
                        return
                    }
                    getPublisherDataFromServer()

                }

                override fun onFailure(messageResponse: MessageResponse) {
                    if (context == null) {
                        return
                    }
                    if (messageResponse != null) {
                        Utilities.getActivity(rootView)?.runOnUiThread {
                            Toast.makeText(context, messageResponse.message, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            })
        }
    }

    private fun getIPLocationApi() {
        ApiManager.getIPLocation(context, object : SuccessFailureCallback {
            override fun onSuccess() {

            }

            override fun onFailure(messageResponse: MessageResponse) {

            }
        })
    }

   private fun getPublisherDataFromServer() {
     //  binding.progressbar.visibility = View.VISIBLE
//        publisher = SharedPrefs.Utils.getPublisher(context,Publisher::class.java)
//        if(publisher != null){
//            Log.i(TAG, "getPublisherDataFromServer: publisher is not null " + publisher)
//            randerUiWithPublisher()
//            getProps()
//        }else {

        ApiManager.getPublisher(context, object : SuccessFailureCallback {
            override fun onSuccess() {
                binding.progressbar.visibility = View.GONE
                if (context == null) {
                    return
                }
                publisher = SharedPrefs.Utils.getPublisher(context, Publisher::class.java)
                if (publisher != null) {
                    binding.mainLayout.visibility = View.VISIBLE
                    randerUiWithPublisher()
                    getCarouselData()

                    if (SharedPrefs.Utils.isLoggedIn(context)) {
                        ApiManager.getFanProfile(context)
                    }
                }
            }

            override fun onFailure(messageResponse: MessageResponse) {
                binding.progressbar.visibility = View.GONE
            }
        })
        //  }
    }

    private fun getCarouselData(){
        if (!Utilities.isOnline(context)) {
            return
        }
        binding.progressbar.visibility = View.VISIBLE

        val responseCarousel: Call<CarouselResponse> =
            ApiFactory.getInstance()!!.getCarouselData(SharedPrefs.Utils.getPublisherId(context),
            SharedPrefs.Utils.getPublisherToken(context))
        responseCarousel.enqueue(object : Callback<CarouselResponse>{

            override fun onResponse(call: Call<CarouselResponse>, response: Response<CarouselResponse>) {
                binding.progressbar.visibility = View.GONE
                val carouselResponse = response.body()
                if(carouselResponse != null ){
                    propIds = carouselResponse.prop_ids
                    getProps()
                }
            }

            override fun onFailure(call: Call<CarouselResponse>, t: Throwable) {
                binding.progressbar.visibility = View.GONE
            }
        })

      //  getProps()
    }


    private fun getProps() {
        if (!Utilities.isOnline(context)) {
            return
        }

        props.clear()
        hasUserPickedArray.clear()

        binding.progressbar.visibility = View.VISIBLE

        for (x in propIds) {

            val responseVerifyFan: Call<ArrayList<Prop>> =
                ApiFactory.getInstance()!!.getProps(x.prop_id.toString())
            //     val responseVerifyFan: Call<String> = ApiFactory.getInstance()!!.getProps(5,1)
            responseVerifyFan.enqueue(object : Callback<ArrayList<Prop>?> {
                override fun onResponse(
                    call: Call<ArrayList<Prop>?>,
                    response: Response<ArrayList<Prop>?>
                ) {
                    binding.progressbar.visibility = View.GONE
                    Log.i(TAG, "onResponse: " + response)
                    Log.i(TAG, "onResponse: code " + response.code())

                    if (response != null) {
                        props.add(response.body()!!.get(0))
                        hasUserPickedArray.add(false)
                      //  props = response.body()!!
//                        if (response.body()!!.size > 0) {
//                            props.add(response.body()!!.get(0))
//                        }
                        setupViewPager()
                        getReferrals()
                    }
                }

                override fun onFailure(call: Call<ArrayList<Prop>?>, t: Throwable) {
                    binding.progressbar.visibility = View.GONE
                    Log.i(TAG, "onFailure: " + t.message)
                    //    successFailureCallback.onFailure()

                }
            })
        }
    }

    private fun randerUiWithPublisher() {
        Log.i(TAG, "randerUiWithPublisher: going to load " + publisher?.logo_url)
        Picasso.get().load(publisher?.picker_logo_url).into(binding.headerImg)
        binding.headerImg.visibility = View.VISIBLE

        binding.mainLayout.setBackgroundColor(SharedPrefs.Utils.getBackgroundColor(context))
        binding.learnMoreBtn.setTextColor(SharedPrefs.Utils.getTextLinkColor(context))
        binding.termsAndConditionBtn.setTextColor(SharedPrefs.Utils.getTextLinkColor(context))


    }

    private fun addDots() {
        if(props.size == 0){
            return
        }

        dots = ArrayList<ImageView>()
      //  val dotsLayout = findViewById<View>(R.id.dots) as LinearLayout

        binding.dots.removeAllViews()


      //  Log.i(TAG, "addDots: props size " + props.size)
        for (i in 0 until props.size) {
            Log.i(TAG, "addDots: in dots loop")
            val dot = ImageView(context)
            dot.setImageDrawable(resources.getDrawable(R.drawable.dots_unselected))
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.marginEnd = 20
            binding.dots.addView(dot, params)
            (dots as ArrayList<ImageView>).add(dot)
        }
        binding.viewPager.setOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                selectDot(position)
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })

        if(props.size>= binding.viewPager.currentItem) {
            selectDot(binding.viewPager.currentItem)
        }
    }

    private fun selectDot(idx: Int) {
        val res = resources
        for (i in 0 until props.size) {
            val drawableId: Int =
                if (i == idx) R.drawable.dots_selected else R.drawable.dots_unselected
            val drawable = res.getDrawable(drawableId)
            dots?.get(i)?.setImageDrawable(drawable)
        }
    }

    public fun logout(){
        SharedPrefs.Utils.saveFanId(context,null)
    }
}
