package com.fanpower.lib.api

import android.app.Activity
import android.content.Context
import android.util.Log
import com.fanpower.lib.api.model.*
import com.fanpower.lib.interfaces.AdsCallback
import com.fanpower.lib.interfaces.FanPickCallback
import com.fanpower.lib.interfaces.SuccessFailureCallback
import com.fanpower.lib.utils.Constants.Generic.PrePickAdId

import com.fanpower.lib.utils.SharedPrefs
import com.fanpower.lib.utils.Utilities
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiManager {

    companion object {

        private val TAG = "ApiManager"

        fun getAuthToken(context: Context, successFailureCallback: SuccessFailureCallback) {

            if (!Utilities.isOnline(context)) {
                successFailureCallback.onFailure(MessageResponse("No working internet connection."))
                return
            }

            // val adminToken = SharedPrefs.Utils.getAdminToken(context)
            val adminToken = SharedPrefs.Utils.getTokenForJWTRequest(context)
            Log.i(TAG, "getAuthToken: admin token is " + adminToken)
            val responseExpenseCategorys: Call<String> = ApiFactory.getInstance()!!.getAuthToken(
                adminToken
            )
            responseExpenseCategorys.enqueue(object : Callback<String?> {
                override fun onResponse(call: Call<String?>, response: Response<String?>) {

                    if (response != null) {
                        Log.i(TAG, "onResponse: " + response.body().toString())
                        SharedPrefs.Utils.saveAuthToken(context, response.body())
                        successFailureCallback.onSuccess()

                    }
                }

                override fun onFailure(call: Call<String?>, t: Throwable) {
                    Log.i(TAG, "onFailure: " + t.message)
                    successFailureCallback.onFailure(MessageResponse(t.message.toString()))
                }
            })
        }

        fun getIPLocation(context: Context, successFailureCallback: SuccessFailureCallback) {
            var ipAddress = Utilities.getIpv4HostAddress()
         //   ipAddress = "69.180.241.210" //TODO remove this
            val responseExpenseCategorys: Call<IPAddressResponse> = ApiFactory.getInstance()!!
                .getIPLocation(SharedPrefs.Utils.getPublisherToken(context), ipAddress)
            responseExpenseCategorys.enqueue(object : Callback<IPAddressResponse?> {
                override fun onResponse(
                    call: Call<IPAddressResponse?>,
                    response: Response<IPAddressResponse?>
                ) {
                    var ipAddressResponse = response.body()
                    if (ipAddressResponse != null) {
                        if (ipAddressResponse.error != null) { // ip address is invalid
                            Log.i(TAG, "onResponse: failed")
                        } else { // normal response
                            SharedPrefs.Utils.saveIPAddress(context, ipAddressResponse)
                        }
                    }
                }

                override fun onFailure(call: Call<IPAddressResponse?>, t: Throwable) {
                    Log.i(TAG, "onFailure: " + t.message)

                }
            })
        }

        fun getPublisher(context: Context, successFailureCallback: SuccessFailureCallback) {
            if (!Utilities.isOnline(context)) {
                successFailureCallback.onFailure(MessageResponse("No working internet connection."))
                return
            }

            val publisherId = SharedPrefs.Utils.getPublisherId(context)
            val responseExpenseCategorys: Call<Publisher> = ApiFactory.getInstance()!!
                .getPublisher(SharedPrefs.Utils.getPublisherToken(context), publisherId)
            responseExpenseCategorys.enqueue(object : Callback<Publisher?> {
                override fun onResponse(call: Call<Publisher?>, response: Response<Publisher?>) {

                    var publisher: Publisher? = response.body()
                    Log.i(TAG, "onResponse: " + response)

                    if (response.body() != null) {
                        Utilities.parsePublisherColors(publisher,context)
                        SharedPrefs.Utils.savePublisher(context, publisher)
                    }
                    successFailureCallback.onSuccess()

                }

                override fun onFailure(call: Call<Publisher?>, t: Throwable) {
                    Log.i(TAG, "onFailure: " + t.message)
                    successFailureCallback.onFailure(MessageResponse(t.message.toString()))
                }
            })
        }

        fun authenticateFan(
            context: Activity,
            authenticateBody: AuthenticateBody,
            successFailureCallback: SuccessFailureCallback
        ) {
            if (!Utilities.isOnline(context)) {
                successFailureCallback.onFailure(MessageResponse("No working internet connection."))
                return
            }

            val publisherToken = SharedPrefs.Utils.getPublisherToken(context)

            val responseAuthFan: Call<String> =
                ApiFactory.getInstance()!!.authenticateFan(publisherToken, authenticateBody)
            responseAuthFan.enqueue(object : Callback<String?> {
                override fun onResponse(call: Call<String?>, response: Response<String?>) {
                    Log.i(TAG, "onResponse: " + response)
                    Log.i(TAG, "onResponse: code " + response.code())


//                        val messageResponse : MessageResponse? = response.body() as MessageResponse
//                        if (messageResponse != null) { // there's a message
//                            successFailureCallback.onFailure(messageResponse)
//                        } else {
//                            successFailureCallback.onSuccess()
//                        }


                    if (response.code() >= 400) {
                        if (response.code() == 500) {
                            successFailureCallback.onFailure(MessageResponse("Too many attempts. Try again later."))
                        } else {
                            successFailureCallback.onFailure(MessageResponse("Something went wrong. try again later"))
                        }

                    } else {
                        successFailureCallback.onSuccess()
                    }
                }

                override fun onFailure(call: Call<String?>, t: Throwable) {
                    Log.i(TAG, "onFailure: " + t.message)
                    successFailureCallback.onFailure(MessageResponse(t.message.toString()))

                }
            })
        }


        fun verifyFan(
            context: Activity,
            verifyFanBody: VerifyFanBody,
            successFailureCallback: SuccessFailureCallback
        ) {
            if (!Utilities.isOnline(context)) {
                successFailureCallback.onFailure(MessageResponse("No working internet connection."))
                return
            }

            val publisherToken = SharedPrefs.Utils.getPublisherToken(context)

            val responseVerifyFan: Call<VerifyFanResponse> =
                ApiFactory.getInstance()!!.verifyFan(publisherToken, verifyFanBody)
            responseVerifyFan.enqueue(object : Callback<VerifyFanResponse?> {
                override fun onResponse(
                    call: Call<VerifyFanResponse?>,
                    response: Response<VerifyFanResponse?>
                ) {
                    Log.i(TAG, "onResponse: " + response)
                    Log.i(TAG, "onResponse: code " + response.code())

                    var verifyFanBody = response.body()
                    Log.i(TAG, "onResponse: message is " + verifyFanBody?.message)


                    if (verifyFanBody != null) {
                        if (verifyFanBody?.message != null) { // there's a message
                            successFailureCallback.onFailure(MessageResponse(verifyFanBody.message))
                        } else {
                            Log.i(TAG, "onResponse: verify response")
                            SharedPrefs.Utils.saveFanId(context, verifyFanBody.id)
                            getFanProfile(context)
                            successFailureCallback.onSuccess()
                        }
                    } else {
                        successFailureCallback.onFailure(MessageResponse("Something went wrong. Try again later"))
                    }

                }

                override fun onFailure(call: Call<VerifyFanResponse?>, t: Throwable) {
                    Log.i(TAG, "onFailure: " + t.message)
                    successFailureCallback.onFailure(MessageResponse(t.message.toString()))

                }
            })
        }

        fun getPublicAds(context: Activity, adId: Int, adsCallback: AdsCallback) {
            val publisherId = SharedPrefs.Utils.getPublisherId(context)
            val publisherToken = SharedPrefs.Utils.getPublisherToken(context)

            val responseVerifyFan: Call<List<AdsResponseItem>> =
                ApiFactory.getInstance()!!.getPublicAds(
                    adId, publisherToken, publisherId, "12"
                )
            responseVerifyFan.enqueue(object : Callback<List<AdsResponseItem>?> {
                override fun onResponse(
                    call: Call<List<AdsResponseItem>?>,
                    response: Response<List<AdsResponseItem>?>
                ) {
                    Log.i(TAG, "onResponse: " + response)
                    Log.i(TAG, "onResponse: code " + response.code())

                    adsCallback.onSuccess(response.body())

                }

                override fun onFailure(call: Call<List<AdsResponseItem>?>, t: Throwable) {
                    Log.i(TAG, "onFailure: " + t.message)
                    adsCallback.onFailure(MessageResponse(t.message.toString()))

                }
            })

        }

        fun getFanProfile(context: Context) {
            val fanId = SharedPrefs.Utils.getFanId(context)
            val publisherToken = SharedPrefs.Utils.getPublisherToken(context)

            Log.i(TAG, "getFanProfile: fan id is " + fanId)

            val responseVerifyFan: Call<Profile> =
                ApiFactory.getInstance()!!.getFanProfile(publisherToken, fanId.toString())
            responseVerifyFan.enqueue(object : Callback<Profile> {
                override fun onResponse(call: Call<Profile>, response: Response<Profile>) {
                    Log.i(TAG, "onResponse: fan profile " + response)
                    Log.i(TAG, "onResponse: code " + response.code())

                    SharedPrefs.Utils.saveFanProfile(context, response.body())
                }

                override fun onFailure(call: Call<Profile>, t: Throwable) {
                    Log.i(TAG, "onFailure: fan profile" + t.message)

                }
            })

        }

        fun createFanPick(
            propId: String,context: Activity,
            successFailureCallback: SuccessFailureCallback,
            pickId: String,pickTitle: String
        ) {

            var fanPickBody = FanPickBody()
            var ipAddressResponse =
                SharedPrefs.Utils.getIPAddress(context, IPAddressResponse::class.java)
//            var proposition = Proposition()

            fanPickBody.pick_id = pickId;
            fanPickBody.prop_id = propId
            fanPickBody.source_url = SharedPrefs.Utils.getSourceUrl(context).toString()
            fanPickBody.publisher_id = SharedPrefs.Utils.getPublisherId(context).toString()!!
            fanPickBody.proposition = Proposition()
            fanPickBody.proposition.id = propId
            fanPickBody.fan_id = SharedPrefs.Utils.getFanId(context).toString()

            fanPickBody.pick = PickCreateParam(pickTitle)
            fanPickBody.publisherInfo = PublisherInfo("PickUp")


            if (ipAddressResponse != null) {
                fanPickBody.ip_address = ipAddressResponse.ipAddress
                fanPickBody.fanCity = ipAddressResponse.fanCity; fanPickBody.fanState =
                    ipAddressResponse.fanState
                fanPickBody.fanCountry = ipAddressResponse.fanCountry; fanPickBody.fanZipCode =
                    ipAddressResponse.fanZipCode
                fanPickBody.fanGeoLocation = ipAddressResponse.fanGeoLocation
                fanPickBody.fanTimeZone = ipAddressResponse.fanTimeZone
            }

            val responseVerifyFan: Call<CreateFanPickResponse> = ApiFactory.getInstance()!!
                .createFanPick(SharedPrefs.Utils.getPublisherToken(context), fanPickBody)
            responseVerifyFan.enqueue(object : Callback<CreateFanPickResponse> {
                override fun onResponse(
                    call: Call<CreateFanPickResponse>,
                    response: Response<CreateFanPickResponse>
                ) {
                    successFailureCallback.onSuccess()

                    var responseBack = response.body()
                    Log.i(TAG, "onResponse: resoponseback create " + responseBack)

                    if (responseBack != null) {
                        var associateFanPickBody = AssociateFanPickBody(
                            PrePickAdId, SharedPrefs.Utils.getFanId(context).toString(),
                            responseBack.fanPick,
                            propId,
                           // SharedPrefs.Utils.getPropId(context).toString(),
                            SharedPrefs.Utils.getPublisherToken(context).toString()
                        )

                        Log.i(TAG, "onResponse: going to associate pick with fan")
                        associateFanWithPick(context, associateFanPickBody)
                    }
                }

                override fun onFailure(call: Call<CreateFanPickResponse>, t: Throwable) {
                    Log.i(TAG, "onFailure: create fanpick " + t.message)
                    successFailureCallback.onFailure(MessageResponse(""))
                }
            })
        }

        fun associateFanWithPick(context: Activity, associateFanPickBody: AssociateFanPickBody) {
            val responseAssociateFanPickBody: Call<String> =
                ApiFactory.getInstance()!!.associateFanWithPick(
                    SharedPrefs.Utils.getPublisherToken(context),
                    associateFanPickBody
                )
            responseAssociateFanPickBody.enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    Log.i(TAG, "onResponse: associate response success")
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.i(TAG, "onFailure: associate response failure")
                }
            })
        }

        fun getFanPicks(context: Activity, propIdOfQuestion: String, fanPickCallback: FanPickCallback) {
            val responseAssociateFanPickBody: Call<List<FanPickItem>> =
                ApiFactory.getInstance()!!.getFanPicks(
                    SharedPrefs.Utils.getPublisherToken(context),
                    SharedPrefs.Utils.getFanId(context).toString(),
                    propIdOfQuestion
                )
            responseAssociateFanPickBody.enqueue(object : Callback<List<FanPickItem>> {
                override fun onResponse(
                    call: Call<List<FanPickItem>>,
                    response: Response<List<FanPickItem>>
                ) {
                    Log.i(TAG, "onResponse: associate response success")

                    var fanPickItems: List<FanPickItem>? = response.body()
                    fanPickCallback.onSuccess(fanPickItems)


                }

                override fun onFailure(call: Call<List<FanPickItem>>, t: Throwable) {
                    Log.i(TAG, "onFailure: associate response failure")
                }
            })
        }


//        fun getProps(context: Context){
//            val responseVerifyFan: Call<PropsResponse> = ApiFactory.getInstance()!!.getProps(SharedPrefs.Utils.getPropId(context).toString(),5,1)
//       //     val responseVerifyFan: Call<String> = ApiFactory.getInstance()!!.getProps(5,1)
//            responseVerifyFan.enqueue(object : Callback<PropsResponse?> {
//                override fun onResponse(call: Call<PropsResponse?>, response: Response<PropsResponse?>) {
//                    Log.i(TAG, "onResponse: " + response )
//                    Log.i(TAG, "onResponse: code " + response.code())
//
//                }
//
//                override fun onFailure(call: Call<PropsResponse?>, t: Throwable) {
//                    Log.i(TAG, "onFailure: " + t.message)
//                //    successFailureCallback.onFailure()
//
//                }
//            })
//        }
//
    }
}