package com.nano.test.repository

import com.nano.test.data.RequestBodies
import com.nano.test.data.model.auth.LoginResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface AuthRepo {

    suspend fun authUser(body: RequestBodies.LoginBody): Flow<Response<LoginResponse>>

}