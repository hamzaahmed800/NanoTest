package com.nano.test.data.network

import com.nano.test.data.model.auth.LoginResponse
import com.nano.test.data.model.product.AllProductResponse
import com.nano.test.data.model.product.SingleProductResponse
import com.nano.test.util.AppConstants
import retrofit2.Response
import retrofit2.http.*

interface APIInterface {

    @POST(AppConstants.Url.LOGIN)
    @FormUrlEncoded
    suspend fun login(
        @Field("email") username: String,
        @Field("password") password: String
    ): Response<LoginResponse>

    @GET(AppConstants.Url.GET_ALL_PRODUCTS)
    suspend fun getAllProducts(): Response<AllProductResponse>

    @GET("${AppConstants.Url.GET_SINGLE_PRODUCT}/{id}")
    suspend fun getSingleProduct(
        @Path("id") productId: String
    ): Response<SingleProductResponse>

}