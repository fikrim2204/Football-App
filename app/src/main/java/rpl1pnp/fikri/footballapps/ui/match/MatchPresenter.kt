package rpl1pnp.fikri.footballapps.ui.match

import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import rpl1pnp.fikri.footballapps.model.EventsResponse
import rpl1pnp.fikri.footballapps.network.ApiRepository
import rpl1pnp.fikri.footballapps.network.TheSportDBApi
import rpl1pnp.fikri.footballapps.util.CoroutineContextProvider
import rpl1pnp.fikri.footballapps.view.MatchView

class MatchPresenter(
    private val viewMatch: MatchView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {

    fun getListNextMatch(idLeague: String?) {
        viewMatch.showLoading()

        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository.doRequestAsync(
                    TheSportDBApi.getNextMatch(idLeague)
                ).await(),
                EventsResponse::class.java
            )

            if (data.events.isNullOrEmpty()) {
                viewMatch.hideLoading()
                viewMatch.checkisNullData(true)
            } else {
                viewMatch.hideLoading()
                viewMatch.showListNextMatch(data.events)
            }
        }
    }

    fun getListLastMatch(idLeague: String?) {
        viewMatch.showLoading()

        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository.doRequestAsync(
                    TheSportDBApi.getLastMatch(idLeague)
                ).await(),
                EventsResponse::class.java
            )

            if (data.events.isNullOrEmpty()) {
                viewMatch.hideLoading()
                viewMatch.checkisNullData(true)
            } else {
                viewMatch.hideLoading()
                viewMatch.showListLastMatch(data.events)
            }
        }
    }
}