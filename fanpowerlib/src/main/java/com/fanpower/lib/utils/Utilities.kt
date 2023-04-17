package com.fanpower.lib.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.fanpower.lib.api.model.Publisher
import java.net.Inet4Address
import java.net.NetworkInterface


class Utilities {

    companion object{

        private const val TAG = "Utilities"

        fun isValidEmail(target: CharSequence?): Boolean {
            return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
        }

        fun pxToDp(px: Int): Int {
            return (px / Resources.getSystem().displayMetrics.density).toInt()
        }

        fun isOnline(context: Context): Boolean {
            val connectivityManager =
                context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (connectivityManager != null) {
                val capabilities =
                    connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                if (capabilities != null) {
                    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                        return true
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                        return true
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                        Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                        return true
                    }
                }
            }

//            context.runOnUiThread(Runnable {
//                Toast.makeText(context,"No working internet connection.",Toast.LENGTH_SHORT).show()
//            })


            return false
        }

        fun getIpv4HostAddress(): String {
            NetworkInterface.getNetworkInterfaces()?.toList()?.map { networkInterface ->
                networkInterface.inetAddresses?.toList()?.find {
                    !it.isLoopbackAddress && it is Inet4Address
                }?.let { return it.hostAddress }
            }
            return ""
        }

//        fun getReferralString(context: Activity,title : String): String? {
//            var referralUrl = SharedPrefs.Utils.getReferralUrl(context)
//            if(referralUrl != null){
//                if(title != null){
//                    return "$title  $referralUrl #makeyourpick"
//                }
//            } else{
//                if(title != null){
//                    return "$title ${Constants.Generic.publisherShareUrl} #makeyourpick"
//                }
//            }
//            return ""
//        }

        fun openUrl(context: Context,url : String){
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context?.startActivity(browserIntent)
        }


        fun getActivity(view: View): Activity? {
            var context = view.context
            while (context is ContextWrapper) {
                if (context is Activity) {
                    return context
                }
                context = context.baseContext
            }
            return null
        }

//        private fun getActivity(): Activity? {
//            var context: Context? = getContext()
//            while (context is ContextWrapper) {
//                if (context is Activity) {
//                    return context
//                }
//                context = context.baseContext
//            }
//            return null
//        }


        public fun parsePublisherColors(publisherResponse: Publisher?,context: Context){


            if(publisherResponse != null)  {
                val primaryColor = if (publisherResponse.primary_color == null) {
                    Color.parseColor("#1D0A2C")
                } else {
                    Log.i(TAG, "parsePublisherColors: in else primary")
                    Color.parseColor(publisherResponse.primary_color)
                }
                Log.i(TAG, "parsePublisherColors: primary color is " + primaryColor)
                SharedPrefs.Utils.savePrimaryColor(context,primaryColor)

                val secondaryColor = if (publisherResponse.secondary_color == null) {
                    Color.parseColor("#44CA97")
                } else {
                    Color.parseColor(publisherResponse.secondary_color)
                }
                SharedPrefs.Utils.saveSecondaryColor(context,secondaryColor)

                val iconColor = if (publisherResponse.icon_color == null) {
                    Color.parseColor("#FA5757")
                } else {
                    Color.parseColor(publisherResponse.icon_color)
                }


                val textLinkColor = if (publisherResponse.text_link_color == null) {
                    Color.parseColor("#F8F7FA")
                } else {
                    Color.parseColor(publisherResponse.text_link_color)
                }
                SharedPrefs.Utils.saveTextLinkColor(context,textLinkColor)

//                viewModel.textLinkColor = textLinkColor
//                termsAndConditionsLabel.setTextColor(textLinkColor)
//                learnMoreLabel.setTextColor(textLinkColor)

                val backgroundColor = if (publisherResponse.background_color == null) {
                    Color.parseColor("#1E1E1E")
                } else {
                    Color.parseColor(publisherResponse.background_color)
                }
                SharedPrefs.Utils.saveBackgroundColor(context,backgroundColor)

//                contentView.setBackgroundColor(backgroundColor)
//
//                collectionView.adapter?.notifyDataSetChanged()
            }
        }

        fun getPickerStrokeBackground(v: View, backgroundColor: Int, borderColor: Int) {
            val shape = GradientDrawable()
            shape.shape = GradientDrawable.RECTANGLE
            shape.cornerRadii = floatArrayOf(88f, 88f, 88f, 88f, 88f, 88f, 88f, 88f)
           // shape.setColor(backgroundColor)
            shape.setStroke(3, borderColor)
            v.background = shape
        }

        fun getVerifyTitleBackground(v: View, backgroundColor: Int, borderColor: Int) {
            val shape = GradientDrawable()
            shape.shape = GradientDrawable.RECTANGLE
            shape.cornerRadii = floatArrayOf(35f, 35f, 35f, 35f, 0f, 0f, 0f, 0f)
             shape.setColor(backgroundColor)
          //  shape.setStroke(0, borderColor)
            v.background = shape
        }

        fun pxFromDp(context: Context, dp: Float): Float {
            return dp * context.resources.displayMetrics.density
        }

        fun showKeyboard(mEtSearch: EditText, context: Context) {
            mEtSearch.requestFocus()
            val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
        }

        public fun showKeyboard(activity: Activity) {
            val view = activity.currentFocus
            val methodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            assert(view != null)
            methodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }

    }





}