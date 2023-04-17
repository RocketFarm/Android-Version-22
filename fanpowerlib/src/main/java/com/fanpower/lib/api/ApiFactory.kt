package com.fanpower.lib.api

import com.fanpower.lib.utils.Constants.Api.ENDPOINT_URL
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

class ApiFactory {

    companion object {

        private val TAG = "Api factory"
        private var instance: ApiInterface? = null


        fun getInstance(): ApiInterface? {
            if (instance != null) return instance
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build()

             var gson =  GsonBuilder()
                .setLenient()
                .create()

//        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
            val retrofit = Retrofit.Builder()
                .baseUrl(ENDPOINT_URL)
                .client(client)
                  .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
            return retrofit.create(ApiInterface::class.java).also { instance = it }
        }

        class AddHeaderInterceptor : Interceptor {
            @Throws(IOException::class)
            override fun intercept(chain: Chain): Response {
                val builder = chain.request().newBuilder()
//            builder.addHeader(
//                "Authorization",
//                "" + SharedPrefs.getAuthToken(McCannApplication.getInstance())
//            )
                return chain.proceed(builder.build())
            }
        }
    }
}