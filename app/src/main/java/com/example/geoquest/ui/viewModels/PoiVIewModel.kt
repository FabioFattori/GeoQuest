package com.example.geoquest.ui.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.geoquest.apiService.ApiService
import com.example.geoquest.business.classes.DayPointOfInterest
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

class PoiVIewModel : ViewModel() {
    private val _poiList = mutableStateOf<List<DayPointOfInterest>>(emptyList())
    val poiList: State<List<DayPointOfInterest>> = _poiList
    val arePOIsLoading = mutableStateOf(true)

    fun fetchPoi(lat: Double, lon: Double) {
        arePOIsLoading.value = true
        viewModelScope.launch {
            try {
                val query = """
                    [out:json];
                    (
                      node["tourism"="museum"](around:1000,$lat,$lon);
                      node["historic"="monument"](around:1000,$lat,$lon);
                      node["tourism"="artwork"](around:1000,$lat,$lon);
                      node["leisure"="park"](around:1000,$lat,$lon);
                      node["amenity"="fountain"](around:1000,$lat,$lon);
                      node["historic"="memorial"](around:1000,$lat,$lon);
                      node["natural"="peak"](around:1000,$lat,$lon);
                    );
                    out center;
                """.trimIndent()

                val body =
                    "data=$query".toRequestBody("application/x-www-form-urlencoded".toMediaTypeOrNull())
                val response = ApiService.poiApi.getPoints(body)
                if (response.isSuccessful) {
                    val data = response.body()
                    _poiList.value = data?.elements?.map { it.toDayPoi() } ?: emptyList()
                    arePOIsLoading.value = false
                }
            } catch (e: Exception) {
                // Handle error
                e.printStackTrace()

                arePOIsLoading.value = false
            }
        }
    }
}
