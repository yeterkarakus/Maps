package com.yeterkarakus.maps.maps


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yeterkarakus.maps.api.RetrofitAPI
import com.yeterkarakus.maps.data.SearchNearby
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapsViewModel @Inject constructor(private val retrofit : RetrofitAPI)
    : ViewModel() {
    private val _searchList = MutableLiveData<SearchNearby>()
    val searchList : LiveData<SearchNearby>
        get() = _searchList


        fun getData(query : String,lat : Number,lng : Number, limit : String,
                    language : String , region : String ){
            viewModelScope.launch {
                val data = retrofit.searchNearby(query, lat, lng, limit, region, language)
                if (data.isSuccessful) {

                    _searchList.postValue(data.body())

                }
            }
        }
    }