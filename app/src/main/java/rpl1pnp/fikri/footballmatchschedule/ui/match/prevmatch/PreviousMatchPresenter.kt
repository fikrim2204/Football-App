package rpl1pnp.fikri.footballmatchschedule.ui.match.prevmatch

import android.util.Log
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import rpl1pnp.fikri.footballmatchschedule.model.Events
import rpl1pnp.fikri.footballmatchschedule.model.EventsResponse
import rpl1pnp.fikri.footballmatchschedule.model.SearchResponse
import rpl1pnp.fikri.footballmatchschedule.network.ApiRepository
import rpl1pnp.fikri.footballmatchschedule.network.TheSportDBApi
import rpl1pnp.fikri.footballmatchschedule.util.CoroutineContextProvider
import rpl1pnp.fikri.footballmatchschedule.view.PreviousMatchView

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
                apiRepository.doRequest(
                    TheSportDBApi.getPreviousMatch(idLeague)
                ).await(),
                EventsResponse::class.java
            )

            viewPrev.hideLoading()
            viewPrev.showListMatch(data.events)
            Log.d("goal", "${data.events}")
        }
    }

    fun getSearchMatch(query: String?) {
        viewPrev.showLoading()

        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository.doRequest(
                    TheSportDBApi.getSearch(query)
                ).await(),
                SearchResponse::class.java
            )

            if (data.event.isNullOrEmpty()) {
                events.clear()
                viewPrev.hideLoading()
                viewPrev.nullData()
            } else {
                events.clear()
                for (element in data.event) {
                    if (element.sport == "Soccer" && element.homeScore != null) {
                        Log.d("goal", "${element.homeGoalDetail}")
                        events.add(element)
                        viewPrev.hideLoading()
                        viewPrev.searchMatch(events)
                    }
                }
            }
        }
    }
}