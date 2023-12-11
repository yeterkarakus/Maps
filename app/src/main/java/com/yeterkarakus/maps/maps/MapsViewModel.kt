package com.yeterkarakus.maps.maps


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yeterkarakus.maps.repository.MapsRepository
import com.yeterkarakus.maps.data.searchdata.SearchNearby
import com.yeterkarakus.maps.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapsViewModel @Inject constructor(private val mapsRepository: MapsRepository) : ViewModel() {

    private val _searchList = MutableLiveData<Result<SearchNearby>>()
    val searchList: LiveData<Result<SearchNearby>>
        get() = _searchList

    fun getData(query: String, lat: Number, lng: Number, limit: String, language: String, region: String
    ) {
            _searchList.value=Result.loading(null)
            viewModelScope.launch {

                val data = mapsRepository.searchLocation(query, lat, lng, limit, language, region)
                _searchList.value = data
        }
    }
}
