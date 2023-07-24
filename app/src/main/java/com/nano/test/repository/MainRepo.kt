package com.nano.test.repository

import com.nano.test.data.model.product.AllProductResponse
import com.nano.test.data.model.product.SingleProductResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface MainRepo {

    suspend fun getAllProducts(): Flow<Response<AllProductResponse>>

    suspend fun getSingleProduct(productId: String): Flow<Response<SingleProductResponse>>

}