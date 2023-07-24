package com.nano.test.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nano.test.R
import com.nano.test.Nano
import com.nano.test.data.RequestBodies
import com.nano.test.data.model.auth.LoginResponse
import com.nano.test.repository.AuthRepository
import com.nano.test.util.Event
import com.nano.test.util.InternetUtil
import com.nano.test.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    app: Application,
    private val authRepository: AuthRepository,
) : BaseViewModel(app){

    private val _loginResponse = MutableLiveData<Event<Resource<LoginResponse>>>()
    val loginResponse: LiveData<Event<Resource<LoginResponse>>>
        get() = _loginResponse

    fun authUser(body: RequestBodies.LoginBody) = viewModelScope.launch {
        login(body)
    }

    private suspend fun login(body: RequestBodies.LoginBody) {
        _loginResponse.postValue(Event(Resource.Loading()))
        try {
            if (InternetUtil.hasInternetConnection(getApplication<Nano>())) {
                authRepository.authUser(body).collect { response ->
                    _loginResponse.postValue(handleLoginResponse(response))
                }
            } else {
                _loginResponse.postValue(
                    Event(
                        Resource.Error(
                            getApplication<Nano>().getString(
                                R.string.no_internet_connection
                            )
                        )
                    )
                )
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> {
                    _loginResponse.postValue(
                        Event(
                            Resource.Error(
                                getApplication<Nano>().getString(
                                    R.string.network_failure
                                )
                            )
                        )
                    )
                }
                else -> {
                    _loginResponse.postValue(
                        Event(
                            Resource.Error(
                                getApplication<Nano>().getString(
                                    R.string.conversion_error
                                )
                            )
                        )
                    )
                }
            }
        }
    }

    private fun handleLoginResponse(response: Response<LoginResponse>): Event<Resource<LoginResponse>>? {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Event(Resource.Success(resultResponse))
            }
        }
        return Event(Resource.Error(response.message()))
    }

    override fun onCleared() {
        super.onCleared()
    }

}