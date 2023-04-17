package com.fanpower.lib.utils

import android.content.Context
import android.preference.PreferenceManager
import com.fanpower.lib.api.model.IPAddressResponse
import com.fanpower.lib.api.model.Profile
import com.fanpower.lib.api.model.Publisher
import com.fanpower.lib.utils.Constants.SharedPref.AdminTokenPref
import com.fanpower.lib.utils.Constants.SharedPref.AuthTokenPref
import com.fanpower.lib.utils.Constants.SharedPref.FanIdPref
import com.fanpower.lib.utils.Constants.SharedPref.IPAddressPref
import com.fanpower.lib.utils.Constants.SharedPref.IsFirstRun
import com.fanpower.lib.utils.Constants.SharedPref.IsMVPPref
import com.fanpower.lib.utils.Constants.SharedPref.PrimaryColorPref
import com.fanpower.lib.utils.Constants.SharedPref.ProfilePref
import com.fanpower.lib.utils.Constants.SharedPref.PropIdPref
import com.fanpower.lib.utils.Constants.SharedPref.PublisherIdPref
import com.fanpower.lib.utils.Constants.SharedPref.PublisherPref
import com.fanpower.lib.utils.Constants.SharedPref.PublisherTokenPref
import com.fanpower.lib.utils.Constants.SharedPref.ReferralUrlPref
import com.fanpower.lib.utils.Constants.SharedPref.SecondaryColorPref
import com.fanpower.lib.utils.Constants.SharedPref.TokenForJwtReqPref
import com.fanpower.lib.utils.Constants.SharedPref.SourceUrlPref
import com.fanpower.lib.utils.Constants.SharedPref.TextLinkColorPref
import com.fanpower.lib.utils.Constants.SharedPref.backgroundColorPref
import com.google.gson.Gson


class SharedPrefs {

    object Utils{

        fun getIsOnMVP(context: Context?): Boolean {
            val prefs = PreferenceManager.getDefaultSharedPreferences(context)
            return prefs.getBoolean(IsMVPPref, true)
        }

        fun saveIsOnMVP(context: Context?, isMvp: Boolean) {
            PreferenceManager.getDefaultSharedPreferences(context).edit()
                .putBoolean(IsMVPPref, isMvp).commit()
        }

        fun getIsFirstRun(context: Context?): Boolean {
            val prefs = PreferenceManager.getDefaultSharedPreferences(context)
            return prefs.getBoolean(IsFirstRun, true)
        }

        fun saveIsFirstRun(context: Context?, isMvp: Boolean) {
            PreferenceManager.getDefaultSharedPreferences(context).edit()
                .putBoolean(IsFirstRun, isMvp).commit()
        }

//        fun isUserVerified(context: Context?) : Boolean{
//            val prefs = PreferenceManager.getDefaultSharedPreferences(context)
//            return prefs.getBoolean(IsVerifiedPref, false)
//        }
//
//        fun saveIsUserVerified(context: Context?, isVerified: Boolean) {
//            PreferenceManager.getDefaultSharedPreferences(context).edit()
//                .putBoolean(IsVerifiedPref, isVerified).commit()
//        }

        fun isLoggedIn(context: Context?): Boolean{
            if(getFanId(context).isNullOrEmpty()){
                return false
            }
                return true
        }

        fun getPublisherToken(context: Context?) : String?{
            val prefs = PreferenceManager.getDefaultSharedPreferences(context)
            return prefs.getString(PublisherTokenPref, "")
        }

        fun savePublisherToken(context: Context?, publisherToken: String?) {
            PreferenceManager.getDefaultSharedPreferences(context).edit()
                .putString(PublisherTokenPref, publisherToken).commit()
        }

        fun getTokenForJWTRequest(context: Context?) : String?{
            val prefs = PreferenceManager.getDefaultSharedPreferences(context)
            return prefs.getString(TokenForJwtReqPref, "")
        }

        fun saveTokenForJWTRequest(context: Context?, publisherToken: String?) {
            PreferenceManager.getDefaultSharedPreferences(context).edit()
                .putString(TokenForJwtReqPref, publisherToken).commit()
        }


        fun getSourceUrl(context: Context?) : String?{
            val prefs = PreferenceManager.getDefaultSharedPreferences(context)
            return prefs.getString(SourceUrlPref, "")
        }

        fun saveSourceUrl(context: Context?, publisherToken: String?) {
            PreferenceManager.getDefaultSharedPreferences(context).edit()
                .putString(SourceUrlPref, publisherToken).commit()
        }


        fun getPropId(context: Context?) : Int{
            val prefs = PreferenceManager.getDefaultSharedPreferences(context)
            return prefs.getInt(PropIdPref, 0)
        }

        fun savePropId(context: Context?, propId: Int) {
            PreferenceManager.getDefaultSharedPreferences(context).edit()
                .putInt(PropIdPref, propId).commit()
        }

        fun getFanId(context: Context?) : String?{
            val prefs = PreferenceManager.getDefaultSharedPreferences(context)
            return prefs.getString(FanIdPref, "")
        }

        fun saveFanId(context: Context?, propId: String?) {
            PreferenceManager.getDefaultSharedPreferences(context).edit()
                .putString(FanIdPref, propId).commit()
        }


        fun getAuthToken(context: Context?) : String?{
            val prefs = PreferenceManager.getDefaultSharedPreferences(context)
            return prefs.getString(AuthTokenPref, null)
        }

        fun saveAuthToken(context: Context?, publisherToken: String?) {
            PreferenceManager.getDefaultSharedPreferences(context).edit()
                .putString(AuthTokenPref, publisherToken).commit()
        }

        fun getAdminToken(context: Context?) : String?{
            val prefs = PreferenceManager.getDefaultSharedPreferences(context)
            return prefs.getString(AdminTokenPref, "")
        }

        fun saveAdminToken(context: Context?, adminToken: String) {
            PreferenceManager.getDefaultSharedPreferences(context).edit()
                .putString(AdminTokenPref, adminToken).commit()
        }

        fun getReferralUrl(context: Context?) : String?{
            val prefs = PreferenceManager.getDefaultSharedPreferences(context)
            return prefs.getString(ReferralUrlPref, "")
        }

        fun saveReferralUrl(context: Context?, adminToken: String) {
            PreferenceManager.getDefaultSharedPreferences(context).edit()
                .putString(ReferralUrlPref, adminToken).commit()
        }

        fun getPublisherId(context: Context?) : Int?{
            val prefs = PreferenceManager.getDefaultSharedPreferences(context)
            return prefs.getInt(PublisherIdPref, 0)
        }

        fun savePublisherId(context: Context?, publisherToken: Int) {
            PreferenceManager.getDefaultSharedPreferences(context).edit()
                .putInt(PublisherIdPref, publisherToken).commit()
        }


        fun getPrimaryColor(context: Context?) : Int{
            val prefs = PreferenceManager.getDefaultSharedPreferences(context)
            return prefs.getInt(PrimaryColorPref, 0)
        }

        fun savePrimaryColor(context: Context?, publisherToken: Int) {
            PreferenceManager.getDefaultSharedPreferences(context).edit()
                .putInt(PrimaryColorPref, publisherToken).commit()
        }

        fun getSecondaryColor(context: Context?) : Int{
            val prefs = PreferenceManager.getDefaultSharedPreferences(context)
            return prefs.getInt(SecondaryColorPref, 0)
        }

        fun saveSecondaryColor(context: Context?, publisherToken: Int) {
            PreferenceManager.getDefaultSharedPreferences(context).edit()
                .putInt(SecondaryColorPref, publisherToken).commit()
        }

        fun getBackgroundColor(context: Context?) : Int{
            val prefs = PreferenceManager.getDefaultSharedPreferences(context)
            return prefs.getInt(backgroundColorPref, 0)
        }

        fun saveBackgroundColor(context: Context?, publisherToken: Int) {
            PreferenceManager.getDefaultSharedPreferences(context).edit()
                .putInt(backgroundColorPref, publisherToken).commit()
        }

        fun getTextLinkColor(context: Context?) : Int{
            val prefs = PreferenceManager.getDefaultSharedPreferences(context)
            return prefs.getInt(TextLinkColorPref, 0)
        }

        fun saveTextLinkColor(context: Context?, publisherToken: Int) {
            PreferenceManager.getDefaultSharedPreferences(context).edit()
                .putInt(TextLinkColorPref, publisherToken).commit()
        }

        fun <Publisher> getPublisher(context: Context?, classType: Class<Publisher>?): Publisher? {
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            if (sharedPreferences.contains(PublisherPref)) {
                val gson = Gson()
                return gson.fromJson(
                    sharedPreferences.getString(PublisherPref, ""), classType
                )
            }
            return null
        }

        fun savePublisher(context: Context?, publisher: Publisher?) {
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            val sharedPreferencesEditor = sharedPreferences.edit()
            val gson = Gson()
            val serializedObject = gson.toJson(publisher)
            sharedPreferencesEditor.putString(PublisherPref, serializedObject)
            sharedPreferencesEditor.apply()

        }


        fun <Profile> getFanProfile(context: Context?, classType: Class<Profile>?): Profile? {
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            if (sharedPreferences.contains(ProfilePref)) {
                val gson = Gson()
                return gson.fromJson(
                    sharedPreferences.getString(ProfilePref, ""), classType
                )
            }
            return null
        }

        fun saveFanProfile(context: Context?, profile: Profile?) {
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            val sharedPreferencesEditor = sharedPreferences.edit()
            val gson = Gson()
            val serializedObject = gson.toJson(profile)
            sharedPreferencesEditor.putString(ProfilePref, serializedObject)
            sharedPreferencesEditor.apply()

        }

        fun <IPAddressResponse> getIPAddress(context: Context?, classType: Class<IPAddressResponse>?): IPAddressResponse? {
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            if (sharedPreferences.contains(IPAddressPref)) {
                val gson = Gson()
                return gson.fromJson(
                    sharedPreferences.getString(IPAddressPref, ""), classType
                )
            }
            return null
        }

        fun saveIPAddress(context: Context?, publisher: IPAddressResponse?) {
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            val sharedPreferencesEditor = sharedPreferences.edit()
            val gson = Gson()
            val serializedObject = gson.toJson(publisher)
            sharedPreferencesEditor.putString(IPAddressPref, serializedObject)
            sharedPreferencesEditor.apply()

        }

    }
}