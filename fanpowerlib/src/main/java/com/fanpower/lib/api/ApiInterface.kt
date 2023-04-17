package com.fanpower.lib.api

import com.fanpower.lib.api.model.*
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {


//    @GET("v1/publisher/location-token")
//    fun getPublisher(@Query("token") token: String?): Call<Publisher>

    @GET("v1/auth/jwt")
    fun getAuthToken(@Query("token") token : String?) :  Call<String>

    @GET("/v1/ip-lookup")
    fun getIPLocation(@Query("token") publisherToken : String?, @Query("ip") ipAddress : String) :Call<IPAddressResponse>

    @GET("/v1/publisher")
    fun getPublisher(@Query("token") publisherToken: String?,@Query("publisher_id") publisherId: Int?) : Call<Publisher>

    @POST("/v1/fans/auth")
    fun authenticateFan(@Query("token") publisherToken: String?, @Body  authenticateBody : AuthenticateBody) : Call<String>

    @POST("/v1/fans/validate")
    fun validateEmail(@Query("token") publisherToken: String?, @Body emailValidateBody: EmailValidateBody) : Call<String>

    @GET("v1/fans/profile")
    fun getFanProfile(@Query("token") publisherToken: String?,@Query("fan_id") fanId: String) : Call<Profile>

    @POST("/v1/fans/auth/verify")
    fun verifyFan(@Query("token") publisherToken: String?, @Body  verifyFanBody: VerifyFanBody) : Call<VerifyFanResponse>

    @GET("/v2/carousels/publisher/{publisher_id}/mobile")
    fun getCarouselData(@Path("publisher_id") publisherId: Int?,@Query("token") publisherToken: String?) : Call<CarouselResponse>

    @GET("/v1/props")
    fun getProps(@Query("id") propId : String?): Call<ArrayList<Prop>>

    @GET("/v1/adsPublic")
    fun getPublicAds(@Query("ad_zone_id") adZoneId : Int,@Query("token") publisherToken : String?,@Query("publisher_id") publisherId : Int?,@Query("tags") tags : String) : Call<List<AdsResponseItem>>

    @POST("/v1/fans/pick")
    fun createFanPick(@Query("token") publisherToken: String?, @Body fanPickBody: FanPickBody) : Call<CreateFanPickResponse>

    @POST("v1/fans/fanpick")
    fun associateFanWithPick(@Query("token") publisherToken: String?, @Body associateFanPickBody: AssociateFanPickBody) : Call<String>

    @GET("/v1/fanpicks")
    fun getFanPicks(@Query("token") publisherToken: String?,@Query("fanId") fanId: String,@Query("prop") propId: String) : Call<List<FanPickItem>>

    @GET("/v2/referrals/{publisher_id}/fan/{fan_id}")
    fun getReferrals(@Path("publisher_id") publisherId: Int?,@Path("fan_id") fanId : String,@Query("token") publisherToken: String,
    @Query("url") shareTargetUrl : String, @Query("prop_id") propId : String) : Call<ReferralResponse>

}