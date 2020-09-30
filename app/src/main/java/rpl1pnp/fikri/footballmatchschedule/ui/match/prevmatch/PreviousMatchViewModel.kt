package rpl1pnp.fikri.footballmatchschedule.ui.match.prevmatch

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import rpl1pnp.fikri.footballmatchschedule.model.EventsResponse
import rpl1pnp.fikri.footballmatchschedule.model.SearchResponse
import rpl1pnp.fikri.footballmatchschedule.network.ApiService

class PreviousMatchViewModel : ViewModel() {

    private val prevMatch = MutableLiveData<EventsResponse>()
    private val searchMatch = MutableLiveData<SearchResponse>()
    private val apiService = ApiService()
    private val loading = MutableLiveData<Boolean>()


    fun observePrevMatch(): MutableLiveData<EventsResponse> {
        return prevMatch
    }

    fun observeLoading(): MutableLiveData<Boolean> {
        return loading
    }

    fun observeSearch(): MutableLiveData<SearchResponse> {
        return searchMatch
    }

    fun loadData(idLeague: String?) {
        loading.value = true
        apiService.getApiService().prevMatch(idLeague).enqueue(object : Callback<EventsResponse> {
            override fun onFailure(call: Call<EventsResponse>, t: Throwable) {
                loading.value = false
                prevMatch.value = null
            }

            override fun onResponse(
                call: Call<EventsResponse>,
                response: Response<EventsResponse>
            ) {
                if (response.isSuccessful) {
                    loading.value = false
                    prevMatch.value = response.body()
                } else {
                    loading.value = false
                    prevMatch.value = null
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
                    val code = response.code()
                    Log.d(TAG, "Data event : $data")
                    Log.d(TAG, "Data value : $code")
                    if (data?.event != null) {
                        for (element in data.event) {
                            Log.d(
                                TAG,
                                "element : ${element.eventName}, ${element.sport}, ${element.idLeague}, $idLeague"
                            )
                            if (element.sport == "Soccer" && element.idLeague == idLeague) {
                                loading.value = false
                                searchMatch.value = response.body()
                                Log.d(TAG, "Response body : ${response.body()}")

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
}