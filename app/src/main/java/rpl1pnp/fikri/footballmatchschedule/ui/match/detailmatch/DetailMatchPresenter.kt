package rpl1pnp.fikri.footballmatchschedule.ui.match.detailmatch

import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import rpl1pnp.fikri.footballmatchschedule.model.EventsResponse
import rpl1pnp.fikri.footballmatchschedule.model.TeamResponse
import rpl1pnp.fikri.footballmatchschedule.network.ApiRepository
import rpl1pnp.fikri.footballmatchschedule.network.TheSportDBApi
import rpl1pnp.fikri.footballmatchschedule.util.CoroutineContextProvider
import rpl1pnp.fikri.footballmatchschedule.view.DetailMatchView

class DetailMatchPresenter(
    private val view: DetailMatchView,
    private val apiRepository: ApiRepository,
    private val gson: Gson,
    private val context: CoroutineContextProvider = CoroutineContextProvider()
) {
    fun getEvent(eventId: String?) {
        view.showLoading()

        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository.doRequestAsync(
                    TheSportDBApi.getDetailMatch(eventId)
                ).await(),
                EventsResponse::class.java
            )

            view.hideLoading()
            view.showDetail(data.events)
        }
    }

    fun getLogo(teamId: String?, isHomeTeam: Boolean) {
        view.showLoading()

        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository.doRequestAsync(
                    TheSportDBApi.getTeams(
                        teamId
                    )
                ).await(),
                TeamResponse::class.java
            )

            view.hideLoading()
            view.getLogoTeam(data.teams, isHomeTeam)
        }
    }
}