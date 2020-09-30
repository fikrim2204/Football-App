package rpl1pnp.fikri.footballmatchschedule.ui.match.nextmatch

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import rpl1pnp.fikri.footballmatchschedule.model.EventsResponse
import rpl1pnp.fikri.footballmatchschedule.model.SearchResponse
import rpl1pnp.fikri.footballmatchschedule.network.ApiService

class NextMatchViewModel : ViewModel() {

    private val nextMatch = MutableLiveData<EventsResponse>()
    private val searchMatch = MutableLiveData<SearchResponse>()
    private val apiService = ApiService()
    private val loading = MutableLiveData<Boolean>()

    fun observeNextMatch(): MutableLiveData<EventsResponse> {
        return nextMatch
    }

    fun observeLoading(): MutableLiveData<Boolean> {
        return loading
    }

    fun observeSearch(): MutableLiveData<SearchResponse> {
        return searchMatch
    }

    fun loadData(idLeague: String?) {
        loading.value = true
        apiService.getApiService().nextMatch(idLeague).enqueue(object : Callback<EventsResponse> {
            override fun onFailure(call: Call<EventsResponse>, t: Throwable) {
                loading.value = false
                nextMatch.value = null
            }

            override fun onResponse(
                call: Call<EventsResponse>,
                response: Response<EventsResponse>
            ) {
                if (response.isSuccessful) {
                    loading.value = false
                    nextMatch.value = response.body()
                } else {
                    loading.value = false
                    nextMatch.value = null
                }
            }
        })
    }

    fun getSearch(query: String?, idLeague: String?) {
        loading.value = true
        apiService.getApiService().search(query).enqueue(object : Callback<SearchResponse> {
            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                loading.value = false
                searchMatch.value = null
            }

            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                if (response.isSuccessful) {
                    loading.value = false
                    val data = response.body()
                    if (data?.event != null) {
                        for (element in data.event) {
                            if (element.sport == "Soccer" && element.idLeague == idLeague) {
                                loading.value = false
                                searchMatch.value = response.body()
                            } else {
                                loading.value = false
                                searchMatch.value = null
                            }
                        }
                    } else {
                        loading.value = false
                        searchMatch.value = null
                    }
                } else {
                    loading.value = false
                    searchMatch.value = null
                }
            }
        })
    }

//    fun loadData(idLeague: String?) {
//        loading.value = true
//        doAsync {
//            try {
//                val data = gson.fromJson(
//                    apiRepositori.doRequest(TheSportDBApi.getNextMatch(idLeague)),
//                    EventsResponse::class.java
//                )
//
//                uiThread {
//                    nextMatch.value = data
//                    loading.value = false
//                }
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }
//    }
//
//    fun getSearch(query: String?, idLeague: String?) {
//        loading.value = true
//        doAsync {
//            try {
//                val data = gson.fromJson(
//                    apiRepositori.doRequest(TheSportDBApi.getSearch(query)),
//                    SearchResponse::class.java
//                )
//
//                uiThread {
//                    for (element in data.event) {
//                        if (element.sport == "Soccer" && element.idLeague == idLeague) {
//                            searchMatch.value = data
//                            loading.value = false
//                            Log.d("NextMatchViewModel", "data : $data")
//                        }
//                    }
//                }
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }
//    }
}