package com.nano.test.di

import com.nano.test.data.network.APIInterface
import com.nano.test.repository.*
import com.nano.test.util.AppConstants
import com.intuit.sdp.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import okio.IOException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Singleton
    @Provides
    fun providesOkHttpClientBuilder(logging: HttpLoggingInterceptor): OkHttpClient{
        val httpClientBuilder =
            OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(object : Interceptor {
                    @Throws(IOException::class)
                    override fun intercept(chain: Interceptor.Chain): Response {
                        val requestBuilder: Request.Builder = chain.request().newBuilder()
                        requestBuilder.header("Content-Type", "application/json")
                        if(!AppConstants.JWT_TOKEN.isEmpty()){
                            requestBuilder.header("Authorization", "Bearer ${AppConstants.JWT_TOKEN}")
                        }
                        return chain.proceed(requestBuilder.build())
                    }
                })
                .addInterceptor(logging)
        return httpClientBuilder.build()
    }

    @Singleton
    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory{
        return GsonConverterFactory.create()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, gson: GsonConverterFactory): Retrofit{
        return Retrofit.Builder()
            .baseUrl(AppConstants.Url.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gson)
            .build()
    }

    @Singleton
    @Provides
    fun provideAPIInterface(retrofit: Retrofit): APIInterface {
        return retrofit.create(APIInterface::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(apiService: APIInterface):
            AuthRepository = AuthRepository(apiService)

    @Provides
    @Singleton
    fun provideMainRepository(apiService: APIInterface):
            MainRepository = MainRepository(apiService)

}