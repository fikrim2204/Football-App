package rpl1pnp.fikri.footballapps.ui.match.nextmatch

import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import rpl1pnp.fikri.footballapps.model.Event
import rpl1pnp.fikri.footballapps.model.EventResponse
import rpl1pnp.fikri.footballapps.model.SearchMatchResponse
import rpl1pnp.fikri.footballapps.network.ApiRepository
import rpl1pnp.fikri.footballapps.network.TheSportDBApi
import rpl1pnp.fikri.footballapps.util.CoroutineContextProvider
import rpl1pnp.fikri.footballapps.view.NextMatchView

class NextMatchPresenter(
    //DeleteSoon
    private val viewNext: NextMatchView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {
    private var events: MutableList<Event> = mutableListOf()

    fun getListMatch(idLeague: String?) {
        viewNext.showLoading()

        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository.doRequestAsync(
                    TheSportDBApi.getNextMatch(idLeague)
                ).await(),
                EventResponse::class.java
            )

            viewNext.hideLoading()
            viewNext.showListMatch(data.events)
        }
    }

    fun getSearchMatch(query: String?) {
        viewNext.showLoading()

        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository.doRequestAsync(
                    TheSportDBApi.getSearchMatch(query)
                ).await(),
                SearchMatchResponse::class.java
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