package rpl1pnp.fikri.footballapps.ui.search.searchmatch

import android.util.Log
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import rpl1pnp.fikri.footballapps.model.Events
import rpl1pnp.fikri.footballapps.model.SearchMatchResponse
import rpl1pnp.fikri.footballapps.network.ApiRepository
import rpl1pnp.fikri.footballapps.network.TheSportDBApi
import rpl1pnp.fikri.footballapps.util.CoroutineContextProvider
import rpl1pnp.fikri.footballapps.view.SearchMatchView

class SearchMatchPresenter(
    private val matchView: SearchMatchView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {
    private var events: MutableList<Events> = mutableListOf()

    fun getSearchMatch(query: String?) {
        matchView.showLoading()
        Log.d("pb_searchmatch", "Active")

        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository.doRequestAsync(
                    TheSportDBApi.getSearch(query)
                ).await(),
                SearchMatchResponse::class.java
            )

            if (data.event.isNullOrEmpty()) {
                events.clear()
                matchView.hideLoading()
                Log.d("pb_searchmatch", "NON-Active")
                matchView.searchMatch(events)
                matchView.nullData()
            } else {
                events.clear()
                for (element in data.event) {
                    if (element.sport == "Soccer") {
                        events.add(element)
                        matchView.hideLoading()
                        Log.d("pb_searchmatch", "NON-Active")
                        matchView.searchMatch(events)
                    }
                }
            }
        }
    }
}