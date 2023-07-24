package com.nano.test.data.network

import com.intuit.sdp.BuildConfig
import com.nano.test.util.AppConstants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

object RetrofitHelper {

    fun getInstance(): APIInterface{
        return getRetrofit().create(APIInterface::class.java)
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(AppConstants.Url.BASE_URL)
            .client(getOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun getOkHttpClient(): OkHttpClient {
        val httpClientBuilder =
            if(BuildConfig.DEBUG){
            OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(object : Interceptor {
                    @Throws(IOException::class)
                    override fun intercept(chain: Interceptor.Chain): Response {
                        val requestBuilder: Request.Builder = chain.request().newBuilder()
                        requestBuilder.header("Content-Type", "application/json")
                        if(!AppConstants.JWT_TOKEN.isEmpty()){
                            requestBuilder.header("Bearer ", AppConstants.JWT_TOKEN)
                        }
                        return chain.proceed(requestBuilder.build())
                    }
                })
                .addInterceptor(
                    HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY))
        }else{
            OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(object : Interceptor {
                    @Throws(IOException::class)
                    override fun intercept(chain: Interceptor.Chain): Response {
                        val requestBuilder: Request.Builder = chain.request().newBuilder()
                        requestBuilder.header("Content-Type", "application/json")
                        if(!AppConstants.JWT_TOKEN.isEmpty()){
                            requestBuilder.header("Bearer ", AppConstants.JWT_TOKEN)
                        }
                        return chain.proceed(requestBuilder.build())
                    }
                })
        }
        return httpClientBuilder.build()
    }
}