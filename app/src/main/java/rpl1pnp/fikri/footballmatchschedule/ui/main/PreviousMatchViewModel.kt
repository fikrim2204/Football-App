package rpl1pnp.fikri.footballmatchschedule.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import rpl1pnp.fikri.footballmatchschedule.model.EventsResponse
import rpl1pnp.fikri.footballmatchschedule.network.ApiRepositori
import rpl1pnp.fikri.footballmatchschedule.network.TheSportDBApi

class PreviousMatchViewModel : ViewModel() {

    private val prevMatch = MutableLiveData<EventsResponse>()
    val gson = Gson()
    val apiRepositori = ApiRepositori()
    val loading = MutableLiveData<Boolean>()

    fun loadData(idLeague: String?) {
        loading.value = true
        doAsync {
            try {
                val data = gson.fromJson(
                    apiRepositori.doRequest(TheSportDBApi.getPreviousMatch(idLeague)),
                    EventsResponse::class.java
                )

                uiThread {
                    loading.value = false
                    prevMatch.value = data
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun isLoading(): MutableLiveData<Boolean> {
        return loading
    }

    fun observePrevMatch(): MutableLiveData<EventsResponse> {
        return prevMatch
    }
}