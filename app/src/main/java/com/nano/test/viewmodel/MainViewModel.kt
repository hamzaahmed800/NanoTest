package com.nano.test.viewmodel

import android.app.Application
import com.nano.test.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    app: Application,
    private val mainRepository: MainRepository
) : BaseViewModel(app){

//    private val _dashboardResponse = MutableLiveData<Event<Resource<DashboardResponse>>>()
//    val dashboardResponse: LiveData<Event<Resource<DashboardResponse>>>
//        get() = _dashboardResponse
//
//    private val _saleTrendingResponse = MutableLiveData<Event<Resource<HourlyNanoResponse>>>()
//    val saleTrendingResponse: LiveData<Event<Resource<HourlyNanoResponse>>>
//        get() = _saleTrendingResponse
//
//    fun dashboardData(body: RequestBodies.DashboardBody) = viewModelScope.launch {
//        dashboard(body)
//    }
//    private suspend fun dashboard(body: RequestBodies.DashboardBody) {
//        _dashboardResponse.postValue(Event(Resource.Loading()))
//        try {
//            if (InternetUtil.hasInternetConnection(getApplication<Nano>())) {
//                dashboardRepository.dashboard(body).collect { response ->
//                    if(response.body() != null && response.body()!!.status == UNAUTHORIZED){
//                        _dashboardResponse.postValue(
//                            Event(
//                                Resource.TokenExpire(
//                                    response.body()!!.message,null
//                                )
//                            )
//                        )
//                    }else{
//                        _dashboardResponse.postValue(handleLoginResponse(response))
//                    }
//                }
//            } else {
//                _dashboardResponse.postValue(
//                    Event(
//                        Resource.Error(
//                            getApplication<Nano>().getString(
//                                R.string.no_internet_connection
//                            )
//                        )
//                    )
//                )
//            }
//        } catch (t: Throwable) {
//            when (t) {
//                is IOException -> {
//                    _dashboardResponse.postValue(
//                        Event(
//                            Resource.Error(
//                                getApplication<Nano>().getString(
//                                    R.string.network_failure
//                                )
//                            )
//                        )
//                    )
//                }
//                else -> {
//                    _dashboardResponse.postValue(
//                        Event(
//                            Resource.Error(
//                                getApplication<Nano>().getString(
//                                    R.string.conversion_error
//                                )
//                            )
//                        )
//                    )
//                }
//            }
//        }
//    }
//
//    private fun handleHomeResponse(response: Response<DashboardResponse>): Event<Resource<DashboardResponse>>? {
//        if (response.isSuccessful) {
//            response.body()?.let { resultResponse ->
//                return Event(Resource.Success(resultResponse))
//            }
//        }
//        return Event(Resource.Error(response.message()))
//    }

    override fun onCleared() {
        super.onCleared()
    }

}