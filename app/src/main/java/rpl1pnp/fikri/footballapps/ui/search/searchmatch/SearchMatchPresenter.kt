package rpl1pnp.fikri.footballapps.ui.search.searchmatch

import com.google.gson.Gson
import rpl1pnp.fikri.footballapps.model.Event
import rpl1pnp.fikri.footballapps.model.SearchMatchResponse
import rpl1pnp.fikri.footballapps.network.ApiRepository
import rpl1pnp.fikri.footballapps.network.TheSportDBApi
import rpl1pnp.fikri.footballapps.view.SearchMatchView

class SearchMatchPresenter(
    private val searchMatchView: SearchMatchView,
    private val apiRepository: ApiRepository,
    private val gson: Gson
) {
    private var events: MutableList<Event> = mutableListOf()

    suspend fun getSearchMatch(query: String?) {
        searchMatchView.showLoading()

        val data = gson.fromJson(
            apiRepository.doRequestAsync(
                TheSportDBApi.getSearchMatch(query)
            ).await(),
            SearchMatchResponse::class.java
        )

        if (data.event.isNullOrEmpty()) {
            events.clear()
            searchMatchView.hideLoading()
            searchMatchView.nullData()
        } else {
            events.clear()
            for (element in data.event) {
                if (element.sport == "Soccer") {
                    events.add(element)
                    searchMatchView.hideLoading()
                    searchMatchView.searchMatch(events)
                }
            }
        }
    }
}