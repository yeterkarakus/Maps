package com.yeterkarakus.maps.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yeterkarakus.maps.data.rewievsdata.Reviews
import com.yeterkarakus.maps.repository.MapsRepository
import com.yeterkarakus.maps.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.reflect.Constructor
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor( private val mapsRepository: MapsRepository) : ViewModel() {

    private val _reviews = MutableLiveData<Result<Reviews>>()

    val reviews : LiveData<Result<Reviews>>
            get() = _reviews



    fun getReviews(businessId : String, limit : Number , language : String , region : String) {

        _reviews.value = Result.loading(null)
        viewModelScope.launch {
           val dataReviews = mapsRepository.reviews(businessId,limit,language, region)
            _reviews.value = dataReviews
        }


    }

}