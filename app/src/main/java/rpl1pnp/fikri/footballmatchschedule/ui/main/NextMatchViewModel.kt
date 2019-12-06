package rpl1pnp.fikri.footballmatchschedule.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import rpl1pnp.fikri.footballmatchschedule.model.EventsResponse
import rpl1pnp.fikri.footballmatchschedule.network.ApiRepositori
import rpl1pnp.fikri.footballmatchschedule.network.TheSportDBApi

class NextMatchViewModel : ViewModel() {

    private val nextMatch = MutableLiveData<String>()
    val gson = Gson()
    val apiRepositori = ApiRepositori()
    var isLoading = false

    fun loadData(idLeague: String?) {
        isLoading = true
        doAsync {
            val data = gson.fromJson(apiRepositori.doRequest(TheSportDBApi.getNextMatch(idLeague)),
                EventsResponse::class.java)

            uiThread {
                isLoading = false

            }
        }
    }

    fun observeNextMatch(): MutableLiveData<String> {
        return nextMatch
    }
}