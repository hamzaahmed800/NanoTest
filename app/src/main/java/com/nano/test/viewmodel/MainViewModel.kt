package com.nano.test.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nano.test.Nano
import com.nano.test.R
import com.nano.test.data.model.product.AllProductResponse
import com.nano.test.data.model.product.SingleProductResponse
import com.nano.test.repository.MainRepository
import com.nano.test.util.Event
import com.nano.test.util.InternetUtil
import com.nano.test.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    app: Application,
    private val mainRepository: MainRepository
) : BaseViewModel(app){

    private val _allProductResponse = MutableLiveData<Event<Resource<AllProductResponse>>>()
    val allProducts: LiveData<Event<Resource<AllProductResponse>>>
        get() = _allProductResponse

    private val _singleProductResponse = MutableLiveData<Event<Resource<SingleProductResponse>>>()
    val singleProductResponse: LiveData<Event<Resource<SingleProductResponse>>>
        get() = _singleProductResponse

    fun getAllProducts() = viewModelScope.launch {
        allProducts()
    }

    fun getProductDetails(productId: String) = viewModelScope.launch {
        singleProduct(productId)
    }

    private suspend fun allProducts() {
        _allProductResponse.postValue(Event(Resource.Loading()))
        try {
            if (InternetUtil.hasInternetConnection(getApplication<Nano>())) {
                mainRepository.getAllProducts().collect { response ->
                    _allProductResponse.postValue(handleAllProductsResponse(response))
                }
            } else {
                _allProductResponse.postValue(
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
                    _allProductResponse.postValue(
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
                    _allProductResponse.postValue(
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

    private suspend fun singleProduct(productId: String) {
        _singleProductResponse.postValue(Event(Resource.Loading()))
        try {
            if (InternetUtil.hasInternetConnection(getApplication<Nano>())) {
                mainRepository.getSingleProduct(productId).collect { response ->
                    _singleProductResponse.postValue(handleSingleProductsResponse(response))
                }
            } else {
                _singleProductResponse.postValue(
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
                    _singleProductResponse.postValue(
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
                    _singleProductResponse.postValue(
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
    private fun handleAllProductsResponse(response: Response<AllProductResponse>)
            : Event<Resource<AllProductResponse>>? {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Event(Resource.Success(resultResponse))
            }
        }
        return Event(Resource.Error(response.message()))
    }

    private fun handleSingleProductsResponse(response: Response<SingleProductResponse>)
            : Event<Resource<SingleProductResponse>>? {
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