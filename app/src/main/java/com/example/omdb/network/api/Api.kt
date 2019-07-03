package com.example.omdb.network.api

import java.util.concurrent.TimeUnit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object Api {

    val BASEURL = "https://www.omdbapi.com"
    private var mHttpClient: OkHttpClient.Builder? = null
    private var mRetrofit: Retrofit? = null

    private val retrofit: Retrofit
        get() {
            if (mHttpClient == null) {
                mHttpClient = OkHttpClient.Builder()
            }

            mHttpClient!!.connectTimeout(5, TimeUnit.MINUTES)
            mHttpClient!!.writeTimeout(5, TimeUnit.MINUTES)
            mHttpClient!!.readTimeout(5, TimeUnit.MINUTES)


            val client = mHttpClient!!.build()


            val mBuilder = Retrofit.Builder()
                    .baseUrl(BASEURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())

            if (mRetrofit == null) {
                mRetrofit = mBuilder.client(client).build()
            }

            return this.mRetrofit!!
        }


    fun OMDbApi(): OMDbApi {
        return retrofit.create(OMDbApi::class.java)
    }
}
