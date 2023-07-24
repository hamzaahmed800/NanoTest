package com.nano.test.repository

import com.nano.test.data.network.APIInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiService: APIInterface
) : MainRepo {

    override suspend fun getAllProducts() = flow{
        val allProducts = apiService.getAllProducts()
        emit(allProducts)
    }.flowOn(Dispatchers.IO)

    override suspend fun getSingleProduct(productId: String) = flow{
        val singleProduct = apiService.getSingleProduct(productId)
        emit(singleProduct)
    }.flowOn(Dispatchers.IO)

}