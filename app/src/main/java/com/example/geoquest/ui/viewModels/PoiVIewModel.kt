package com.example.geoquest.ui.viewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.geoquest.apiService.ApiService
import com.example.geoquest.apiService.dto.requests.CreatePoiRequest
import com.example.geoquest.business.classes.DayPointOfInterest
import com.example.geoquest.business.models.CollectedPoi
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

class PoiVIewModel : ViewModel() {
    private val _poiList = mutableStateOf<List<DayPointOfInterest>>(emptyList())
    val poiList: State<List<DayPointOfInterest>> = _poiList
    private val _collectedPoiList = mutableStateOf<List<CollectedPoi>>(emptyList())
    val collectedPoiList : State<List<CollectedPoi>> = _collectedPoiList
    val arePOIsLoading = mutableStateOf(true)


    fun fetchPoi(lat: Double, lon: Double, needToLoad: Boolean = true,playerId: Int) {
        if (needToLoad) {
            arePOIsLoading.value = true
        }
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
                }

                getPoiCollected(playerId)
            } catch (e: Exception) {
                // Handle error
                e.printStackTrace()

                arePOIsLoading.value = false
            }
        }
    }

    private suspend fun getPoiCollected(playerId: Int){
        val response = ApiService.retrofit.getAllCollectedPoi(playerId)
        if (response.isSuccessful) {
            val data = response.body()
            _collectedPoiList.value = data ?: emptyList()
            arePOIsLoading.value = false
        }
    }

    fun onPoiCollect(collectedPoi: DayPointOfInterest, playerId: Int) {
        viewModelScope.launch {
            try {
                val response = ApiService.retrofit.collectPoi(
                    CreatePoiRequest(
                        playerId = playerId,
                        latitude = collectedPoi.position.lat,
                        longitude = collectedPoi.position.lon
                    )
                )
                if (response.isSuccessful) {
                    getPoiCollected(playerId)
                }
            } catch (e: Exception) {
                // Handle error
                e.printStackTrace()

                arePOIsLoading.value = false
            }
        }
    }
}
