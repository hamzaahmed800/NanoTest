package com.nano.test.repository

import com.nano.test.data.RequestBodies
import com.nano.test.data.network.APIInterface
import com.nano.test.util.AppConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val apiService: APIInterface
): AuthRepo {

    override suspend fun authUser(body: RequestBodies.LoginBody) = flow {
        val bookingResponse =
            apiService.login(
                body.username,body.password
            )
        emit(bookingResponse)
    }.flowOn(Dispatchers.IO)


}