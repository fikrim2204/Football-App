package rpl1pnp.fikri.footballapps.ui.match.prevmatch

import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import rpl1pnp.fikri.footballapps.model.Events
import rpl1pnp.fikri.footballapps.model.EventsResponse
import rpl1pnp.fikri.footballapps.model.SearchResponse
import rpl1pnp.fikri.footballapps.network.ApiRepository
import rpl1pnp.fikri.footballapps.network.TheSportDBApi
import rpl1pnp.fikri.footballapps.util.CoroutineContextProvider
import rpl1pnp.fikri.footballapps.view.PreviousMatchView

class PreviousMatchPresenter(
    private val viewPrev: PreviousMatchView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {
    private var events: MutableList<Events> = mutableListOf()

    fun getListMatch(idLeague: String?) {
        viewPrev.showLoading()

        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository.doRequestAsync(
                    TheSportDBApi.getLastMatch(idLeague)
                ).await(),
                EventsResponse::class.java
            )

            viewPrev.hideLoading()
            viewPrev.showListMatch(data.events)
        }
    }

    fun getSearchMatch(query: String?) {
        viewPrev.showLoading()

        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository.doRequestAsync(
                    TheSportDBApi.getSearch(query)
                ).await(),
                SearchResponse::class.java
            )

            if (data.event.isNullOrEmpty()) {
                events.clear()
                viewPrev.hideLoading()
                viewPrev.searchMatch(events)
                viewPrev.nullData()
            } else {
                events.clear()
                for (element in data.event) {
                    if (element.sport == "Soccer" && element.homeScore != null) {
                        events.add(element)
                        viewPrev.hideLoading()
                        viewPrev.searchMatch(events)
                    }
                }
            }
        }
    }
}