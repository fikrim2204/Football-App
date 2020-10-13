package rpl1pnp.fikri.footballmatchschedule.ui.match.nextmatch

import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import rpl1pnp.fikri.footballmatchschedule.model.Events
import rpl1pnp.fikri.footballmatchschedule.model.EventsResponse
import rpl1pnp.fikri.footballmatchschedule.model.SearchResponse
import rpl1pnp.fikri.footballmatchschedule.network.ApiRepository
import rpl1pnp.fikri.footballmatchschedule.network.TheSportDBApi
import rpl1pnp.fikri.footballmatchschedule.util.CoroutineContextProvider
import rpl1pnp.fikri.footballmatchschedule.view.NextMatchView

class NextMatchPresenter(
    private val viewNext: NextMatchView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {
    private var events: MutableList<Events> = mutableListOf()

    fun getListMatch(idLeague: String?) {
        viewNext.showLoading()

        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository.doRequest(
                    TheSportDBApi.getNextMatch(idLeague)
                ).await(),
                EventsResponse::class.java
            )

            viewNext.hideLoading()
            viewNext.showListMatch(data.events)
        }
    }

    fun getSearchMatch(query: String?) {
        viewNext.showLoading()

        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository.doRequest(
                    TheSportDBApi.getSearch(query)
                ).await(),
                SearchResponse::class.java
            )

            if (data.event.isNullOrEmpty()) {
                events.clear()
                viewNext.hideLoading()
                viewNext.searchMatch(events)
                viewNext.nullData()
            } else {
                events.clear()
                for (element in data.event) {
                    if (element.sport == "Soccer" && element.homeScore == null) {
                        events.add(element)
                        viewNext.hideLoading()
                        viewNext.searchMatch(events)
                    }
                }
            }
        }
    }
}