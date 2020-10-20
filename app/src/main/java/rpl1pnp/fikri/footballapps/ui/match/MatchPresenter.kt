package rpl1pnp.fikri.footballapps.ui.match

import com.google.gson.Gson
import rpl1pnp.fikri.footballapps.model.EventResponse
import rpl1pnp.fikri.footballapps.network.ApiRepository
import rpl1pnp.fikri.footballapps.network.TheSportDBApi
import rpl1pnp.fikri.footballapps.view.MatchView

class MatchPresenter(
    private val viewMatch: MatchView,
    private val apiRepository: ApiRepository,
    private val gson: Gson
) {

    suspend fun getListNextMatch(idLeague: String?) {
        viewMatch.showLoading()

        val data = gson.fromJson(
            apiRepository.doRequestAsync(
                TheSportDBApi.getNextMatch(idLeague)
            ).await(),
            EventResponse::class.java
        )

        viewMatch.hideLoading()
            viewMatch.showListNextMatch(data.events)
        }

    suspend fun getListLastMatch(idLeague: String?) {
        viewMatch.showLoading()

        val data = gson.fromJson(
            apiRepository.doRequestAsync(
                TheSportDBApi.getLastMatch(idLeague)
            ).await(),
            EventResponse::class.java
        )

        viewMatch.hideLoading()
            viewMatch.showListLastMatch(data.events)
        }
    }