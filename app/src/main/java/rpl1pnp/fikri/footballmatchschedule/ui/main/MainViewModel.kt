package rpl1pnp.fikri.footballmatchschedule.ui.main

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import rpl1pnp.fikri.footballmatchschedule.model.SearchResponse
import rpl1pnp.fikri.footballmatchschedule.network.ApiService

class MainViewModel {
    private val searchMatch = MutableLiveData<SearchResponse>()
    val gson = Gson()
    private val apiService = ApiService()
    private val loading = MutableLiveData<Boolean>()

    fun observeLoading(): MutableLiveData<Boolean> {
        return loading
    }

    fun observeSearch(): MutableLiveData<SearchResponse> {
        return searchMatch
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
                    Log.d(ContentValues.TAG, "Data event : $data")
                    if (data != null) {
                        for (element in data.event) {
                            Log.d(
                                ContentValues.TAG,
                                "element : ${element.eventName}, ${element.sport}, ${element.idLeague}, $idLeague"
                            )
                            if (element.sport == "Soccer" && element.idLeague == idLeague) {
                                loading.value = false
                                searchMatch.value = response.body()
                                Log.d(ContentValues.TAG, "Response body : ${response.body()}")

                            } else {
                                loading.value = false
                                searchMatch.value = null
                            }
                        }
                    }
                } else {
                    loading.value = false
                    searchMatch.value = null
                }
            }
        })
    }
}